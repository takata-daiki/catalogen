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

package name.huliqing.qblog.web.blog;

import com.google.appengine.api.datastore.Text;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import name.huliqing.common.XmlUtils;
import name.huliqing.qblog.Messenger;
import name.huliqing.qblog.QBlog;
import name.huliqing.qblog.backup.Backup;
import name.huliqing.qblog.backup.Restore;
import name.huliqing.qblog.backup.ZipHelper;
import name.huliqing.qblog.backup.converter.ArticleSecurityConverter;
import name.huliqing.qblog.backup.converter.GroupConverter;
import name.huliqing.qblog.backup.converter.TextConverter;
import name.huliqing.qblog.component.UIFileUpload;
import name.huliqing.qblog.entity.ArticleEn;
import name.huliqing.qblog.entity.ReplyEn;
import name.huliqing.qblog.entity.TagEn;
import name.huliqing.qblog.enums.ArticleSecurity;
import name.huliqing.qblog.enums.Group;
import name.huliqing.qblog.service.ArticleSe;
import name.huliqing.qblog.service.ReplySe;
import name.huliqing.qblog.service.TagSe;
import name.huliqing.qblog.upload.QFile;
import name.huliqing.qblog.web.BaseWe;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author huliqing
 */
@ManagedBean
@RequestScoped
public class ArticleBackupWe extends BaseWe{

    private boolean backupArticles = true;
    private boolean backupReplies = true;
    private boolean backupTags = true;
//    private boolean backupCategorys = true;
    private String backupName;

    // ????????????
    // ?????startDate,?????startDate??(??startDate)????????.
    // ????????????,??????????????????????????
    // ?createDate
    private Date startDate;

    // ????,???????
    // ?????endDate,?????endDate??(??endDate)????????.
    // ???startDate.
    // ???????startDate?endDate,??????????[startDate-endDate]?
    // ?????????
    // startDate,endDate???category???(category?????)
    private Date endDate;

    public ArticleBackupWe() {
        super();
    }

    public boolean isBackupArticles() {
        return backupArticles;
    }

    public void setBackupArticles(boolean backupArticles) {
        this.backupArticles = backupArticles;
    }

    public boolean isBackupTags() {
        return backupTags;
    }

    public void setBackupTags(boolean backupTags) {
        this.backupTags = backupTags;
    }

//    public boolean isBackupCategorys() {
//        return backupCategorys;
//    }
//
//    public void setBackupCategorys(boolean backupCategorys) {
//        this.backupCategorys = backupCategorys;
//    }

    public String getBackupName() {
        if (backupName == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmm");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            backupName = "qblog-bak-article-" + sdf.format(new Date());
        }
        return backupName;
    }

    public void setBackupName(String backupName) {
        this.backupName = backupName;
    }

    public boolean isBackupReplies() {
        return backupReplies;
    }

    public void setBackupReplies(boolean backupReplies) {
        this.backupReplies = backupReplies;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    // ---- Action

    /* Backup format as xml
     *
     * <backup version="{The version of QBlog}" date="{Date of backup}" >
     *      <ArticleEn class="{e.g. name.huliqing.qblog.entity.ArticleEn}" >
     *          <data />
     *          <data />
     *          <data />
     *      </ArticleEn>
     *
     *      <ReplyEn class="{e.g. name.huliqing.qblog.entity.ReplyEn}" >
     *          <data />
     *          <data />
     *          <data />
     *      </ReplyEn>
     *
     *      <TagEn class="{e.g. name.huliqing.qblog.entity.TagEn}">
     *          <data />
     *          <data />
     *          <data />
     *      </TagEn>
     *
     *      <CategoryEn class="{e.g. name.huliqing.qblog.entity.CategoryEn}">
     *          <data />
     *          <data />
     *          <data />
     *      </CategoryEn>
     * </backup>
     */

    public void backup() throws ParserConfigurationException,
            IllegalArgumentException,
            IllegalAccessException,
            UnsupportedEncodingException,
            IOException,
            TransformerException {
        if (!backupArticles && !backupReplies) {
            Messenger.sendError("?????????????????????????");
            return;
        }

        Document doc = XmlUtils.newDocument();
        doc.setXmlVersion("1.0");
        doc.setXmlStandalone(true);
        doc.setStrictErrorChecking(false);

        Backup backup = new Backup(doc);
        backup.setDate(new Date());
        backup.setVersion("QBlog1.0");
        backup.addConverter(Group.class, new GroupConverter());
        backup.addConverter(ArticleSecurity.class, new ArticleSecurityConverter());
        backup.addConverter(Text.class, new TextConverter());

        // Backup Articles
        if (backupArticles) {
            List<ArticleEn> aes = ArticleSe.exportByDateRange(startDate, endDate);
            backup.encode(aes);
        }

        if (backupReplies) {
            List<ReplyEn> res = ReplySe.findByDateRange(startDate, endDate);
            backup.encode(res);
        }

        if (backupTags) {
            List<TagEn> tes = TagSe.findAll();
            backup.encode(tes);
        }

        // remove
//        if (backupCategorys) {
//            List<CategoryEn> ces = CategorySe.findAll();
//            backup.encode(ces);
//        }

        ZipHelper.export(backupName, doc);
        QBlog.getFacesContext().responseComplete();
    }

    // ------------------------------------------------------------------ Import

    private UIFileUpload uiFileUpload;

    public UIFileUpload getUiFileUpload() {
        return uiFileUpload;
    }

    public void setUiFileUpload(UIFileUpload uiFileUpload) {
        this.uiFileUpload = uiFileUpload;
    }

    public void importBackup() throws SAXException,
            IllegalArgumentException,
            IllegalAccessException,
            InstantiationException,
            UnsupportedEncodingException,
            ParserConfigurationException,
            IOException {
        QFile file = this.uiFileUpload.getFile();
        restore(ZipHelper.importAsDoc(file.getBytes()));
    }

    private void restore(Document doc) throws ParserConfigurationException,
            SAXException,
            IOException,
            IllegalArgumentException,
            IllegalAccessException,
            InstantiationException {
        
        Restore restore = new Restore(doc);
        restore.addConverter(Group.class, new GroupConverter());
        restore.addConverter(ArticleSecurity.class, new ArticleSecurityConverter());
        restore.addConverter(Text.class, new TextConverter());
        
        // Restore articles
        List<ArticleEn> aes = (List<ArticleEn>) restore.restore(ArticleEn.class);
        if (aes != null && !aes.isEmpty()) {
            for (ArticleEn ae : aes) {
                ArticleSe.delete(ae.getArticleId()); // Delete old
                ArticleSe._import(ae);
            }
            Messenger.sendInfo("????, OK");
        }

        // Restore replies
        List<ReplyEn> res = (List<ReplyEn>) restore.restore(ReplyEn.class);
        if (res != null && !res.isEmpty()) {
            for (ReplyEn re : res) {
                ReplySe.delete(re.getReplyId()); // Delete old
                ReplySe._import(re);
            }
            Messenger.sendInfo("??????, OK");
        }

        // Restore tags
        List<TagEn> tes = (List<TagEn>) restore.restore(TagEn.class);
        if (tes != null && !tes.isEmpty()) {
            for (TagEn te : tes) {
                TagSe.delete(te.getName()); // Delete old
                TagSe.save(te);
            }
            Messenger.sendInfo("????, OK");
        }

        // remove
//        // Restore categorys
//        List<CategoryEn> ces = (List<CategoryEn>) restore.restore(CategoryEn.class);
//        if (ces != null && !ces.isEmpty()) {
//            for (CategoryEn ce : ces) {
//                CategorySe.delete(ce.getName());
//                CategorySe._import(ce);
//            }
//            Messenger.sendInfo("??Categorys, OK");
//        }
    }

}
