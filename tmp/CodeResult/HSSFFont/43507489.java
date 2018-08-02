/**
 * Utility class for retrieving CMS resources and group permission
 */
package com.bp.pensionline.reporting.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.opencms.file.CmsFile;
import org.opencms.file.CmsFolder;
import org.opencms.file.CmsObject;
import org.opencms.file.types.CmsResourceTypeJsp;
import org.opencms.file.types.CmsResourceTypeXmlContent;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.main.OpenCms;
import org.opencms.security.CmsAccessControlEntry;
import org.opencms.security.CmsPermissionSet;
import org.opencms.security.CmsPrincipal;
import org.opencms.security.I_CmsPrincipal;

import com.bp.pensionline.util.SystemAccount;

/**
 * @author AS5920G
 *
 */
public class ContentPermissionReportUtil {
	public static final Log LOG = CmsLog.getLog(ContentPermissionReportUtil.class);
	
	public static String buildTable(HttpServletRequest request) {
		StringBuffer buf = new StringBuffer();

		buf.append("<table width='100%' cellspacing='0' cellpadding='0'>");
		buf.append("<thead>");
		buf.append("	<tr height='20px'>");
		buf.append("		<td width='30%' style='background-color:#037e01; color:#FFFFFF; font-weight: bold'>&nbsp;&nbsp;Resource</td>");
		buf.append("		<td width='70%' style='background-color:#037e01; color:#FFFFFF; font-weight: bold'>Restriction</td>");
		buf.append("	</tr>");
		buf.append("</thead>");
		buf.append("<tbody>");
		buf.append("	<tr height='20px'>");
		buf.append("		<td colspan='2' style='padding: 0px;'>");
		buf.append("		<div style='width:100%; height:600px; overflow-x: hidden; overflow-y: scroll;'>");
		buf.append("		<table class='publishtable' cellpadding='0' cellspacing='1' style='width: 100%; table-layout: fixed;'>");
		List<String> l = getResourceAndPermission();
		int idx = -1;
		for (int i=0; i<l.size(); i++) {
			String s = l.get(i);
			String[] p = s.split("@");
			idx = p[0].indexOf("/sites/default");
			if (idx != -1){ 
				p[0] = p[0].substring(14);
			}
			buf.append("	<tr>");
			buf.append("		<td width='30%' style='word-wrap: break-word; break-word: break-all;'>");
			buf.append("			<a href='/content/pl"+p[0]+"'>"+p[0]);
			buf.append("		</td>");
			buf.append("		<td width='70%' style='word-wrap: break-word; break-word: break-all;'>");
			buf.append(				p[1]);
			buf.append("		</td>");
			buf.append("	</tr>");
		}
		buf.append("		</table>");
		buf.append("		</td>");
		buf.append("	</tr>");
		buf.append("</tbody>");
		buf.append("</table>");
		
		request.getSession().setAttribute("xlsContent", l);
//		try {
//			HSSFWorkbook doc = createXLSSource(l);
//			byte[] b = doc.getBytes();
//			//request.getSession().removeAttribute("xlsContent");
//			request.getSession().setAttribute("xlsContent", doc);
//			FileOutputStream stream = new FileOutputStream("D:/resource_restriction.xls");
//			doc.write(stream);
//			stream.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		return buf.toString();
	}
		
	//create excel document
	public static HSSFWorkbook createXLSSource(List<String> l) {
		LOG.info("createXLSSource():BEGIN");
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet = wb.createSheet("Resources Restriction");
		
		HSSFCellStyle headingStyle = wb.createCellStyle();
		HSSFFont headingFont = wb.createFont();
		headingFont.setFontHeightInPoints((short)10);
		headingFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headingFont.setColor(HSSFColor.WHITE.index);
		headingStyle.setFont(headingFont);
		headingStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		headingStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headingStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		headingStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		HSSFCellStyle rowStyle = wb.createCellStyle();
		rowStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		rowStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		rowStyle.setWrapText(true);
		
		HSSFRow horzTitle = sheet.createRow(0);
		HSSFCell source = horzTitle.createCell(0);
		source.setCellStyle(headingStyle);
		source.setCellValue(new HSSFRichTextString("Resource"));
		HSSFCell restriction = horzTitle.createCell(1);
		restriction.setCellStyle(headingStyle);
		restriction.setCellValue(new HSSFRichTextString("Restriction"));
		
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 10000);
		
		int idx = -1;
		LOG.info("Records: "+l.size());
		for (int i=0; i<l.size(); i++) {
			String s = l.get(i);
			String[] p = s.split("@");
			idx = p[0].indexOf("/sites/default");
			if (idx != -1) p[0] = p[0].substring(14);
			HSSFRow row = sheet.createRow(i+1);
			HSSFCell c1 = row.createCell(0);
			c1.setCellStyle(rowStyle);
			c1.setCellValue(new HSSFRichTextString(p[0]));
			HSSFCell c2 = row.createCell(1);
			c2.setCellStyle(rowStyle);
			c2.setCellValue(new HSSFRichTextString(p[1]));
		}
		
		LOG.info("createXLSSource():END");
		return wb;
	}
	
	private static List<String> getResourceAndPermission() {
		List<String> list = new ArrayList<String>();
		CmsObject admin = SystemAccount.getPublishingAdminCmsObject();
		try {
			List files = admin.getFilesInFolder("/sites/default/");
			List folders = admin.getSubFolders("/sites/default/");
			List groups = OpenCms.getOrgUnitManager().getGroups(admin, "/", false);
			for (int i=0; i<files.size(); i++) {
				CmsFile file = (CmsFile)files.get(i);
				if (file.getTypeId() != CmsResourceTypeJsp.getStaticTypeId()) {
					String p = file.getRootPath(); //string start with source path

					//retrieve group permission on resource
					String restriction = "";
					Iterator itAces = admin.getAccessControlEntries(p, false).iterator();
					while (itAces.hasNext()) {
		                CmsAccessControlEntry curEntry = (CmsAccessControlEntry)itAces.next();
		                I_CmsPrincipal principal = CmsPrincipal.readPrincipalIncludingHistory(admin, 
		                																      curEntry.getPrincipal());
		                if (!curEntry.isInherited()) {
		                    // check if the entry have -r, -v
		                	CmsPermissionSet pm = curEntry.getPermissions();
		                	if (!(pm.requiresReadPermission() && pm.requiresViewPermission())) {
		                		restriction += "," + principal.getName();
		                	}
		                }
		            }
					if (restriction.indexOf(',')!=-1) {
						p += "@"+restriction.substring(1);
					} else {
						p += "@No restrictions ";
					}
					list.add(p);
				}
			}
			
			//continue with sub folders
			for (int i=0; i<folders.size(); i++) {
				list.addAll(recursive(admin, ((CmsFolder)folders.get(i)).getRootPath()));
			}
			
			//continue with galleries /system/galleries/
			list.addAll(recursive(admin, "/system/galleries/"));
			
		} catch (CmsException cme) {
			LOG.error("Exception "+cme.toString());
		} catch (Exception e) {
			LOG.error("Exception "+e.toString());
			e.printStackTrace();
		}
		
		return list;
	}
	
	private static List<String> recursive(CmsObject admin, String path) {
		List<String> list = new ArrayList<String>();
		try {
			String r = "";
			Iterator iter = admin.getAccessControlEntries(path, false).iterator();
			while (iter.hasNext()) {
                CmsAccessControlEntry curEntry = (CmsAccessControlEntry)iter.next();
                I_CmsPrincipal principal = CmsPrincipal.readPrincipalIncludingHistory(admin, 
                																      curEntry.getPrincipal());
                if (!curEntry.isInherited()) {
                    // check if the entry have -r, -v
                	CmsPermissionSet pm = curEntry.getPermissions();
                	if (!(pm.requiresReadPermission() && pm.requiresViewPermission())) {
                		r += "," + principal.getName();
                	}
                }
            }
			if (r.indexOf(',')!=-1) {
				r = r.substring(1);
			} else {
				r ="No specifically assigned restrictions, but they do inherit from the parent";
			}
			list.add(path+"@"+r);
			
			List files = admin.getFilesInFolder(path);
			List folders = admin.getSubFolders(path);
			
			//work with files in folder
			for (int i=0; i<files.size(); i++) {
				CmsFile file = (CmsFile)files.get(i);
				if (!CmsResourceTypeXmlContent.isXmlContent(file)) {
					String p = file.getRootPath(); //string start with source path

					//retrieve group permission on resource
					String restriction = "";
					Iterator itAces = admin.getAccessControlEntries(p, false).iterator();
					while (itAces.hasNext()) {
		                CmsAccessControlEntry curEntry = (CmsAccessControlEntry)itAces.next();
		                I_CmsPrincipal principal = CmsPrincipal.readPrincipalIncludingHistory(admin, 
		                																      curEntry.getPrincipal());
		                if (!curEntry.isInherited()) {
		                    // check if the entry have -r, -v
		                	CmsPermissionSet pm = curEntry.getPermissions();
		                	if (!(pm.requiresReadPermission() && pm.requiresViewPermission())) {
		                		restriction += "," + principal.getName();
		                	}
		                }
		            }
					if (restriction.indexOf(',')!=-1) {
						p += "@"+restriction.substring(1);
					} else {
						p += "@No specifically assigned restrictions, but they do inherit from the parent";
					}
					list.add(p);
				}
			}
			
			//continue with sub folders
			for (int i=0; i<folders.size(); i++) {
				list.addAll(recursive(admin, ((CmsFolder)folders.get(i)).getRootPath()));
			}
			
		} catch (CmsException cme) {
			LOG.error("Exception "+cme.toString());
		} catch (Exception e) {
			LOG.error("Exception "+e.toString());
			e.printStackTrace();
		}
		return list;
	}
}
