/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 huliqing, huliqing.cn@gmail.com
 *
 * This file is part of QBlog.
 * QBlog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QBlog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with QBlog.  If not, see <http://www.gnu.org/licenses/>.
 *
 * ?????QBlog?????
 * ?????????????????????????????.
 * QBlog????????????????????????????????
 * ????????????????????LGPL3????????????.
 * ??LGPL????????COPYING?COPYING.LESSER???
 * ????QBlog????????LGPL??????
 * ??????????? http://www.gnu.org/licenses/ ???
 *
 * - Author: Huliqing
 * - Contact: huliqing.cn@gmail.com
 * - License: GNU Lesser General Public License (LGPL)
 * - Blog and source code availability: http://www.huliqing.name/
 */

package name.huliqing.qblog.web.system;

import com.google.appengine.api.datastore.Text;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.parsers.ParserConfigurationException;
import name.huliqing.qblog.ConfigManager;
import name.huliqing.qblog.Messenger;
import name.huliqing.qblog.QBlog;
import name.huliqing.qblog.backup.Restore;
import name.huliqing.qblog.backup.ZipHelper;
import name.huliqing.qblog.backup.converter.ArticleSecurityConverter;
import name.huliqing.qblog.backup.converter.GroupConverter;
import name.huliqing.qblog.backup.converter.TextConverter;
import name.huliqing.qblog.entity.ConfigEn;
import name.huliqing.qblog.entity.FolderEn;
import name.huliqing.qblog.entity.HelpEn;
import name.huliqing.qblog.entity.ModuleEn;
import name.huliqing.qblog.entity.PageEn;
import name.huliqing.qblog.entity.PageModuleEn;
import name.huliqing.qblog.enums.ArticleSecurity;
import name.huliqing.qblog.enums.Group;
import name.huliqing.qblog.enums.Security;
import name.huliqing.qblog.service.FolderSe;
import name.huliqing.qblog.service.HelpSe;
import name.huliqing.qblog.service.ModuleSe;
import name.huliqing.qblog.service.PageModuleSe;
import name.huliqing.qblog.service.PageSe;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author huliqing
 */
@ManagedBean
@RequestScoped
public class StartWe {
    private final static Logger logger = Logger.getLogger(StartWe.class.getName());

    /**
     * ???QBlog??
     */
    public void initializeQBlog() {
        
        String bakHelperPath = QBlog.getRealPath() + "/bak/helper.zip";
        String bakConfigPath = QBlog.getRealPath() + "/bak/config.zip";
        boolean error = false;

        // Import helper
        File helper = new File(bakHelperPath);
        if (checkFile(helper, "helper.zip")) {
            try {
                Document helpDoc = convertAsDoc(helper);
                restoreHelperDoc(helpDoc);
            } catch (Exception e) {
                error = true;
                logger.log(Level.SEVERE, "helper.zip????????", e);
                Messenger.sendError("helper.zip????????");
            }
        }

        // Import config
        File config = new File(bakConfigPath);
        if (checkFile(config, "config.zip")) {
            try {
                Document configDoc = convertAsDoc(config);
                restoreConfigDoc(configDoc);
            } catch (Exception e) {
                error = true;
                logger.log(Level.SEVERE, "??config.zip????????", e);
                Messenger.sendError("??config.zip????????");
            }
        }

        if (error) {
            Messenger.sendError("????????????????????????" +
                    "???????????????Email??:" +
                    "huliqing.cn@gmail.com");
        } else {
            Messenger.sendInfo("???????,?????????QBlog,???" +
                    "??????????????????????????Email" +
                    "??: huliqing.cn@gmail.com");
        }
    }

    private boolean checkFile(File file, String filename) {
        if (!file.exists()) {
            logger.severe("?????:" + filename);
            Messenger.sendError("?????:" + filename + ",????????" +
                    "?????QBlog???????.bak/" + filename);
            return false;
        }
        if (!file.canRead()) {
            logger.severe("??????:" + filename);
            Messenger.sendError("??????:" + filename + ",????????" +
                    "?????????????");
            return false;
        }
        return true;
    }

    private Document convertAsDoc(File file) throws
            FileNotFoundException,
            IOException,
            SAXException,
            ParserConfigurationException {
        FileInputStream fis = null;
        ByteArrayOutputStream arr = new ByteArrayOutputStream(2048);
        try {
            fis = new FileInputStream(file);
            byte[] buff = new byte[2048];
            int len;
            while ((len = fis.read(buff, 0, buff.length)) != -1) {
                arr.write(buff, 0, len);
            }
            Document doc = ZipHelper.importAsDoc(arr.toByteArray());
            arr.close();
            return doc;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(StartWe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void restoreHelperDoc(Document doc) throws
            IllegalArgumentException,
            IllegalAccessException,
            InstantiationException {

        Restore res = new Restore(doc);
        res.addConverter(Text.class, new TextConverter());
        List<HelpEn> hes = (List<HelpEn>) res.restore(HelpEn.class);
        if (hes != null) {
            for (HelpEn he : hes) {
                HelpSe.delete(he.getHelpId());
                HelpSe.save(he);
            }
            Messenger.sendInfo("??????????");
        }

    }

    private void restoreConfigDoc(Document doc) throws ParserConfigurationException,
            SAXException, IOException,
            IllegalArgumentException,
            IllegalAccessException,
            InstantiationException {

        Restore restore = new Restore(doc);
        restore.addConverter(Group.class, new GroupConverter());
        restore.addConverter(ArticleSecurity.class, new ArticleSecurityConverter());
        restore.addConverter(Text.class, new TextConverter());

        // restore config
        List<ConfigEn> ces = (List<ConfigEn>) restore.restore(ConfigEn.class);
        if (ces != null && !ces.isEmpty()) {
            for (ConfigEn ce : ces) {
                ConfigManager.getInstance().saveOrUpdate(ce);
            }
            Messenger.sendInfo("??????????");
        }

        // Restore pages
        List<PageEn> pes = (List<PageEn>) restore.restore(PageEn.class);
        if (pes != null && !pes.isEmpty()) {
            // ???Page
            PageSe.deleteAll();
            Messenger.sendInfo("????Page, OK");

            // ????Page
            for (PageEn pe : pes) {
                PageSe.save(pe);
                Messenger.sendInfo("?????" + pe.getName());
            }
            Messenger.sendInfo("???????");
        }

        // Restore modules
        List<ModuleEn> mes = (List<ModuleEn>) restore.restore(ModuleEn.class);
        if (mes != null && !mes.isEmpty()) {
            // ???Module
            ModuleSe.deleteAll();
            Messenger.sendInfo("????Module, OK");

            // ????Module
            for (ModuleEn me : mes) {
                ModuleSe._import(me);
                Messenger.sendInfo("?????" + me.getName());
            }
            Messenger.sendInfo("???????");
        }

        // Restore Page and modules
        List<PageModuleEn> pmes = (List<PageModuleEn>) restore.restore(PageModuleEn.class);
        if (pmes != null && !pmes.isEmpty()) {
            // ????
            PageModuleSe.deleteAll();

            // ????
            PageModuleSe.importAll(pmes);

            for (PageModuleEn pme : pmes) {
                Messenger.sendInfo("??????????Page Id=" + pme.getPageId()
                        + ", Group=" + pme.getModuleGroup().name()
                        + ", Module Id=" + pme.getModuleId()
                        + ", Sort=" + pme.getSort());
            }
            Messenger.sendInfo("??????????????");
        }

        // ???????????
        FolderEn album = FolderSe.find(1L);
        if (album == null) {
            Messenger.sendInfo("????????????...");
            album = new FolderEn();
            album.setFolderId(1L);
            album.setName("????");
            album.setCover("/_res/image/album-cover2.jpg");
            album.setSecurity(Security.PUBLIC);
            if (FolderSe.save(album)) {
                Messenger.sendInfo("????????????" + album.getName());
            } else {
                Messenger.sendError("??????!");
            }
        }
    }
}
