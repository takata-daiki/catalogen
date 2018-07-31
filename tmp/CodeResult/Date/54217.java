/**
 * Date Created: Apr 20, 2009
 */

package forteresce.portprofile.gui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import forteresce.portprofile.config.constants.ProfileConstants;
import forteresce.portprofile.config.enums.SystemPropertiesEnum;
import forteresce.portprofile.gui.PortProfileFrame;
import forteresce.portprofile.gui.dialogs.PortProfileDialogs;
import forteresce.portprofile.gui.events.ShowProfilesEvent;
import forteresce.portprofile.gui.events.ShowWaitEvent;
import forteresce.portprofile.profiles.bean.ProfileBean;
import forteresce.portprofile.profiles.bean.ProfileBeanImpl;
import forteresce.portprofile.profiles.util.ProfileUtil;
import forteresce.portprofile.profiles.util.ZipUtil;

/**
 * A simple panel to be used as a place holder for the profile selection 
 * @author forteresce
 */
public class ProfilesImportPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 4622949131851593532L;
	private Logger log = Logger.getLogger(ProfilesImportPanel.class);

	private JFrame parentFrame;
	private JTextField fileField;
	private JTextField importProfileName;
	
	private static final String BROWSE_BUTTON_TEXT = "...";
	private static final String IMPORT_BUTTON_TEXT = "Import";

	public ProfilesImportPanel(JFrame parentFrame) {
		super();

		//store the reference to parent frame
		this.parentFrame = parentFrame;
		
		//the regular ones
		setMinimumSize(ProfileConstants.MAIN_PANEL_DIM);
		setLayout(new GridBagLayout());
		
		//add the components
		addComponents();
	}
	
	private void addComponents() {
		Dimension labelDimension = new Dimension(125, 25);
		Dimension fieldDimension = new Dimension(160, 25);

		JLabel selectLabel = new JLabel("Select a profile:");
		selectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		selectLabel.setPreferredSize(labelDimension);

		fileField = new JTextField();
		fileField.setPreferredSize(fieldDimension);
		
		JButton browseButton = new JButton(BROWSE_BUTTON_TEXT);
		browseButton.addActionListener(this);
		browseButton.setPreferredSize(new Dimension(25, 20));
		
		JLabel importAsLabel = new JLabel("Import As:");
		importAsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		importAsLabel.setPreferredSize(labelDimension);
		
		importProfileName = new JTextField();
		importProfileName.setPreferredSize(fieldDimension);
		importProfileName.setText("pp_" + System.currentTimeMillis());
		
		JButton importButton = new JButton("Import");
		importButton.addActionListener(this);
		
		//add the components to the pane
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 10, 5, 10);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(selectLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		add(fileField, constraints);

		constraints.gridx = 2;
		constraints.gridy = 0;
		add(browseButton, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		add(importAsLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(importProfileName, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		add(importButton, constraints);
	}
	
	/**
	 * All of the main logic is performed on the user action
	 */
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		String text = button.getText();

		if (BROWSE_BUTTON_TEXT.equals(text)) {
			fileField.setText(PortProfileDialogs.showFileChooser(parentFrame, new FileFilter() {
				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getName().endsWith(ProfileConstants.PROFILE_FILE_EXTN);
				}
				
				@Override
				public String getDescription() {
					return ProfileConstants.PROFILE_FILE_DESC;
				}
			}));
		} else {
			if (IMPORT_BUTTON_TEXT.equals(text) && validateInput()) {
				
				if (parentFrame instanceof PortProfileFrame) {
					((PortProfileFrame) parentFrame).dispatchEvent(new ShowWaitEvent(
							parentFrame, "Please wait while profile is uncompressed."));
				}
				
				ProfileImporter importer = new ProfileImporter();		
				importer.execute();		
			}
		}
	}

	private boolean validateInput() {
		String fileName = fileField.getText();
		if (fileName == null || "".equals(fileName)) {
		   PortProfileDialogs.showError(parentFrame, "Please select a file to import from.");
		   return false;
		}

		String profileName = importProfileName.getText();
		if (!profileName.matches("\\w+")) {
			PortProfileDialogs.showError(parentFrame, "Please enter a valid import as profile name.");
			return false;
		}
		
		try {
			if (null != ProfileUtil.getProfileByName(profileName)) {
				PortProfileDialogs.showError(parentFrame, "Conflicting profile name. Please enter another name.");
				return false;
			}
		} catch (Exception e) {
			log.error("Error checking for conflicting profiles.", e);
		}
		
		return true;
	}

	
	/**
	 * An inner swingworker class to do the importing of the profile
	 * @author forteresce
	 */
	class ProfileImporter extends SwingWorker<Boolean, Void> {
		private Logger log = Logger.getLogger(ProfileImporter.class);

		private static final int UNZIPPED_PROFILE = 1;
		private File newDir = null;

		@Override
		protected Boolean doInBackground() throws Exception {
			String profileZip = fileField.getText();
			
			// 1. Unzip the profile to tmp folder
			boolean result = ZipUtil.unzip(profileZip, SystemPropertiesEnum.JAVA_IO_TMPDIR.get());
			if (!result) {
				return Boolean.FALSE;
			} else {
				setProgress(UNZIPPED_PROFILE);
				publish(new Void[1]);
			}

			// 2. Add entry to the existing profiles
			String parentFolder = ZipUtil.getParentFolder(profileZip); 
			if (null == parentFolder) {
				return Boolean.FALSE;
			}

			File unzippedProfileDir = new File(SystemPropertiesEnum.JAVA_IO_TMPDIR.get() + SystemPropertiesEnum.FILE_SEPARATOR.get() + parentFolder);
			newDir = new File (ProfileUtil.generateProfilePath(importProfileName.getText()));
			
			FileUtils.copyDirectory(unzippedProfileDir, newDir);
			log.debug("Created profile in : " + newDir.getAbsolutePath());
			
			ProfileBean newProfile = new ProfileBeanImpl();
			newProfile.setName(importProfileName.getText());
			newProfile.setDefault("1");
			newProfile.setIsRelative("0");
			newProfile.setPath(newDir.getAbsolutePath());
			
			if (!ProfileUtil.addNewProfile(newProfile)) {
				return Boolean.FALSE;
			}
			
			return Boolean.TRUE;
		}
		
		@Override
		protected void process(List<Void> chunks) {
			if (parentFrame instanceof PortProfileFrame) {
				switch(getProgress()) {
					case UNZIPPED_PROFILE: ((PortProfileFrame) parentFrame).dispatchEvent(new ShowWaitEvent(parentFrame, "Done uncompressing.\nAdding new profile."));
												break;
					default: ((PortProfileFrame) parentFrame).dispatchEvent(new ShowWaitEvent(parentFrame, "Status Unknown."));
				}
			}
		}
		
		@Override
		protected void done() {
			super.done();
			try {
				if (parentFrame instanceof PortProfileFrame) {
					((PortProfileFrame) parentFrame) .dispatchEvent(new ShowProfilesEvent(parentFrame));
				}

				if(!get().booleanValue()) {
					switch(getProgress()) {
						case UNZIPPED_PROFILE: PortProfileDialogs.showError(parentFrame, "Error adding profile.");
													break;
						default: PortProfileDialogs.showError(parentFrame, "Error unzipping selected profile file.");
					}
					
				} else {
					PortProfileDialogs.showInfo(parentFrame, "Profile successfully imported.\nProfile Folder: " + newDir.getAbsolutePath());
				}
			} catch (InterruptedException e) {
				log.error(e);
			} catch (ExecutionException e) {
				log.error(e);
			}
		}
	}
}
