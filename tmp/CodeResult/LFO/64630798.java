/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.android.project.ui.layout;

import com.android.ide.common.rendering.api.RenderSession;
import com.android.ide.common.rendering.api.Result;
import com.android.ide.common.resources.configuration.DeviceConfigHelper;
import com.android.ide.common.resources.configuration.FolderConfiguration;
import com.android.ide.common.resources.configuration.UiModeQualifier;
import com.android.resources.UiMode;
import com.android.sdklib.devices.State;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.io.Closeables;
import java.awt.image.BufferedImage;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.android.core.sdk.DalvikPlatform;
import org.netbeans.modules.android.project.AndroidProject;
import org.netbeans.modules.android.project.layout.RenderServiceFactory;
import org.netbeans.modules.android.project.layout.RenderingService;
import org.netbeans.modules.android.project.layout.ResourceRepositoryManager;
import org.netbeans.modules.android.project.layout.ThemeData;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.RequestProcessor;

/**
 *
 * @author radim
 */
public class PreviewController {
  private static final Logger LOG = Logger.getLogger(PreviewController.class.getName());
  
  private static final RequestProcessor RP_LAYOUT_RENDERER = new RequestProcessor("Android Layout renderer", 1);

  public interface Repainter {
    /** Initiates repaint of a layout described by file-object using passed platform and configuration. */
    void paint(FileObject fo, DalvikPlatform platform, FolderConfiguration folderConfig, ThemeData theme);
  }
  
  // confined to EDT (unless running in test runner)
  private final PreviewModel model;
  private final Repainter repainter;

  public PreviewController(PreviewModel model) {
    this(model, new RepaintLayoutRunnable(model));
  }
  
  PreviewController(PreviewModel model, Repainter repainter) {
    this.model = model;
    this.repainter = repainter;
  }
  
  public void updateFileObject(FileObject fo) {
    FileObject oldFo = model.getFileObject();
    if (Objects.equal(fo, oldFo)) {
      return;
    }
    if (isLayoutFile(fo)) {
      model.setFileObject(fo);
      repainter.paint(model.getFileObject(), model.getPlatform(), makeFolderConfiguration(), model.getTheme());
    }
  }
  
  public void updateUiMode(UiMode uiMode) {
    model.setUiMode(uiMode);
    repainter.paint(model.getFileObject(), model.getPlatform(), makeFolderConfiguration(), model.getTheme());
  }

  void updateTheme(ThemeData themeData) {
    model.setTheme(themeData);
    repainter.paint(model.getFileObject(), model.getPlatform(), makeFolderConfiguration(), model.getTheme());
  }
  
  public void updateLocaleConfig(ResourceRepositoryManager.LocaleConfig locale) {
    model.setLocaleConfig(locale);
    repainter.paint(model.getFileObject(), model.getPlatform(), makeFolderConfiguration(), model.getTheme());
  }
  
  private static boolean isLayoutFile(FileObject fo) {
    if (fo == null || !"xml".equals(fo.getExt())) {
      return false;
    }
    Project p = FileOwnerQuery.getOwner(fo);
    if (p == null) {
      return false;
    }
    AndroidProject ap = p.getLookup().lookup(AndroidProject.class);
    if (ap == null) {
      return false;
    }
    FileObject resFolder = p.getProjectDirectory().getFileObject("res");
    if (resFolder == null || !FileUtil.isParentOf(resFolder, fo)) {
      return false;
    }
    String folderName = fo.getParent().getNameExt();
    return "layout".equals(folderName) || folderName.startsWith("layout-");
  }

  void updatePlatform(DalvikPlatform dalvikPlatform) {
    model.setPlatform(dalvikPlatform);
    repainter.paint(model.getFileObject(), model.getPlatform(), makeFolderConfiguration(), model.getTheme());
  }

  void updateConfiguration(State deviceConfig) {
    model.setDeviceConfig(deviceConfig);
    repainter.paint(model.getFileObject(), model.getPlatform(), makeFolderConfiguration(), model.getTheme());
  }
  
  @VisibleForTesting FolderConfiguration makeFolderConfiguration() {
    FolderConfiguration folderConfig = new FolderConfiguration();
    if (model.getDeviceConfig() != null) {
      try {
        FolderConfiguration deviceStateConfig = DeviceConfigHelper.getFolderConfig(model.getDeviceConfig());
        folderConfig.set(deviceStateConfig);
      } catch (NullPointerException ex) {
        LOG.log(Level.FINE, null, ex);
      }
      folderConfig.setUiModeQualifier(new UiModeQualifier(model.getUiMode()));
      folderConfig.setLanguageQualifier(model.getLocaleConfig().getLanguage());
      folderConfig.setRegionQualifier(model.getLocaleConfig().getRegion());
    }
    return folderConfig;
  }
  
  private static class RepaintLayoutRunnable implements Runnable, Repainter {

    private final RequestProcessor.Task renderingTask = RP_LAYOUT_RENDERER.create(this);
    
    private final PreviewModel model;
    private final Object lock = new Object();
    private FileObject fo;
    private DalvikPlatform platform;
    private FolderConfiguration folderConfig;
    private ThemeData theme;

    public RepaintLayoutRunnable(PreviewModel model) {
      this.model = model;
    }
    
    /** Request layout repaint. */
    @Override
    public void paint(FileObject fo, DalvikPlatform platform, FolderConfiguration folderConfig, ThemeData theme) {
      assert SwingUtilities.isEventDispatchThread();
      LOG.fine("repaint requested");
      synchronized (lock) {
        this.fo = fo;
        this.platform = platform;
        this.folderConfig = folderConfig;
        this.theme = theme;
        model.setState(PreviewModel.PreviewState.LOADING);
        renderingTask.schedule(0);
      }
    }
    
    @Override
    public void run() {
      assert !SwingUtilities.isEventDispatchThread();
    
      FileObject lFo;
      DalvikPlatform lPlatform;
      FolderConfiguration lFolderConfig;
      ThemeData lTheme;
      synchronized (lock) {
        lFo = fo;
        lPlatform = platform;
        lFolderConfig = folderConfig;
        lTheme = theme;
      }
      try {
        if (lPlatform == null || lFolderConfig == null || lFo == null) {
          return;
        }
        Project p = FileOwnerQuery.getOwner(lFo);
        if (p == null) {
          return;
        }
        AndroidProject ap = p.getLookup().lookup(AndroidProject.class);
        if (ap == null) {
          return;
        }
        RenderingService service = RenderServiceFactory.createService(
            ap,
            lFolderConfig,
            lPlatform);
        Reader layoutReader = new InputStreamReader(lFo.getInputStream());

        try {
          final RenderSession session = service.createRenderSession(ap,
              layoutReader, lTheme.themeName, lTheme.isProjectTheme, lFo.getName());

          // get the status of the render
          final Result result = session.getResult();
          SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
              if (!result.isSuccess()) {
                LOG.log(Level.INFO, result.getErrorMessage());
                // XXX this can override another load
                model.setState(PreviewModel.PreviewState.ERROR);
              } else {
                model.setState(PreviewModel.PreviewState.OK);
              }
              final BufferedImage img = session.getImage();
              model.setImage(img);
            }
          });
        } finally {
          Closeables.closeQuietly(layoutReader);
        }
      } catch (Exception ex) {
        LOG.log(Level.INFO, null, ex);
      }
    }
  }
}
