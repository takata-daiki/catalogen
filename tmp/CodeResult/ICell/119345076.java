/* 
 *	Copyright Washington University in St Louis 2006
 *	All rights reserved
 * 	
 * 	@author Mohana Ramaratnam (Email: mramarat@wustl.edu)

*/

package org.apache.turbine.app.cnda_xnat.modules.screens;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;
import org.nrg.xdat.base.BaseElement;
import org.nrg.xdat.model.XnatAbstractresourceI;
import org.nrg.xdat.om.CndaRadiologyreaddata;
import org.nrg.xdat.om.XnatAbstractresource;
import org.nrg.xdat.om.XnatMrsessiondata;
import org.nrg.xdat.turbine.modules.screens.XDATScreen_pdf;
import org.nrg.xdat.turbine.utils.TurbineUtils;
import org.nrg.xft.XFTItem;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class XDATScreen_radReadPDFScreen extends XDATScreen_pdf {
	 XFTItem item = null;

	protected ByteArrayOutputStream buildPdf (RunData data) throws 	Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try { 
			CndaRadiologyreaddata rad_read = (CndaRadiologyreaddata)BaseElement.GetGeneratedItem(item);
			XnatMrsessiondata mr = rad_read.getMrSessionData();
			Document document = new Document();
			PdfWriter.getInstance(document, baos);
			//document.addTitle("Research Radiological Assessment");

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		    Date now = Calendar.getInstance().getTime();
			DateFormat ydateFormat = new SimpleDateFormat("yyyy-MM-dd");


		HeaderFooter footer  = new HeaderFooter(new Phrase("Document Production Date: " + dateFormat.format(now),new Font(Font.TIMES_ROMAN,9,Font.BOLD)),false);
			footer.setAlignment(Element.ALIGN_RIGHT);
			document.setFooter(footer);
			document.open();
			

			Font titleFont = new Font (Font.HELVETICA, 18, Font.BOLD, 
	                 new Color (0, 0, 128));


			Paragraph title = new Paragraph ("Research Radiological Assessment", titleFont);
			   title.setAlignment (Element.ALIGN_CENTER);
			   title.setSpacingAfter (18.0f);
			   document.add(title);
			

			final String notice = "Important: Please note this radiological assessment was conducted for research purposes only and may not have included images appropriate to clinical assessment." ;
			Paragraph p = new Paragraph(notice, new Font(Font.TIMES_ROMAN,8,Font.BOLD));
			p.setSpacingAfter(9f);
			document.add(p);
			
		    PdfPTable table = new PdfPTable (4);
		    table.addCell(makeCell("Subject:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(mr.getSubjectData().getLabel(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL))); 
		    table.addCell(makeCell("Session Id:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(mr.getLabel(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    if (mr.getSubjectData().getDOB() != null)  {
		    	table.addCell(makeCell("Date of Birth:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); 
		    	table.addCell(makeCell(ydateFormat.format(mr.getSubjectData().getDOB()),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    } 
		    else
		    {
		    	table.addCell(makeCell("Date of Birth:",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
		    	table.addCell(makeCell("NA",new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    }

	    	table.addCell(makeCell("Date of Scan:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(""+mr.getProperty("date"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    table.addCell(makeCell("Age at Scan:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(mr.getSubjectAge(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    table.addCell(makeCell("Type:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(""+mr.getProperty("session_type"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    table.addCell(makeCell("Cohort:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(mr.getSubjectData().getCohort(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    table.addCell(makeCell("Scanner:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(""+mr.getProperty("scanner"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    table.addCell(makeCell(" ",new Font(Font.TIMES_ROMAN,10,Font.NORMAL))); table.addCell(makeCell("",new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    table.addCell(makeCell("Date of Assessment:",new Font(Font.TIMES_ROMAN,10,Font.BOLD))); table.addCell(makeCell(""+rad_read.getProperty("date"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
		    table.setSpacingAfter(9f);
		    document.add(table);
		    
		    document.add(Chunk.NEWLINE);
			
		    PdfPTable table1 = new PdfPTable (2);
			table1.addCell(makeCell("Reader:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getReader(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.addCell(makeCell("Exam:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getExam(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.addCell(makeCell("History:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getHistory(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.addCell(makeCell("Technique:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getTechnique(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.addCell(makeCell("Comparison:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getComparison(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));		    
			table1.addCell(makeCell("Finding:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getFinding(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.addCell(makeCell("Diagnosis/Impression:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getDiagnosis(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.addCell(makeCell("Status:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getStatusText(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.addCell(makeCell("Recommend Further Evaluation:", new Font(Font.TIMES_ROMAN,11,Font.BOLD)));table1.addCell(makeCell(rad_read.getFollowupRecommended().toString(), new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
			table1.setSpacingAfter(9f);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);
			document.add(table1);
			
			p = new Paragraph("Key Images", new Font(Font.TIMES_ROMAN,11,Font.BOLD));
			p.setSpacingAfter(9f);
			document.add(p);
			
			List<XnatAbstractresourceI> outFiles = rad_read.getOut_file();
			if (outFiles.size()>0) {
				table = new PdfPTable (3);
				table.setSpacingAfter(9f);
				int currentRowImages = 0; 
				Rectangle border = getBorder();	
				PdfPCell iCell = null, cell = null;
				for(int i=0; i<outFiles.size(); i++) {
					XnatAbstractresource absRsc = (XnatAbstractresource)outFiles.get(i);
					ArrayList<File> files = absRsc.getCorrespondingFiles(mr.getArchivePath());
					for (int j = 0; j <files.size(); j++) {
						PdfPTable itable = new PdfPTable (1);
						Image image = Image.getInstance(files.get(j).getAbsolutePath());
						iCell = new PdfPCell();
						iCell.setImage(image);
						iCell.setBorder(Rectangle.NO_BORDER);	
						itable.addCell(iCell);
						cell = new PdfPCell(new Phrase(absRsc.getLabel(),new Font(Font.TIMES_ROMAN,11,Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						itable.addCell(cell);
						PdfPCell nestedCell = new PdfPCell(itable);
						nestedCell.setBorder(Rectangle.NO_BORDER);
						table.addCell(nestedCell);
						currentRowImages++;	
						System.out.println("Total images is " + currentRowImages);
					}
					if ((currentRowImages == outFiles.size()) && currentRowImages%3!=0){
					   for (int k =0; k<(3-currentRowImages%3);k++) {
					     PdfPCell cell1 = new PdfPCell(new Phrase(" "));
					     cell1.setBorder(Rectangle.NO_BORDER); 		
					     table.addCell(cell1);
					   }	
					}
				}
				document.add(table);
			}
			
						
			document.close();
			
		}catch(Exception e) {
		  e.printStackTrace();
		}
		return baos;
	}
	
    public void doBuildTemplate(RunData data, Context context)	{
        try {
            item = (XFTItem)TurbineUtils.GetItemBySearch(data);
        } catch (Exception e1) {}
		if (item == null)		{
			data.setMessage("Error: No item found.");
			TurbineUtils.OutputPassedParameters(data,context,this.getClass().getName());
		}else{
			try {
				finalProcessing(data,context);
			} catch (Exception e) {
				data.setMessage(e.toString());
			}
		}
	}

	
	private  PdfPCell makeCell(String text, Font font) {
	Rectangle border = getBorder();
        Paragraph p = null;
        if (font == null)
        	p = new Paragraph(text);
        else
         p = new Paragraph(text, font);

        PdfPCell cell = new PdfPCell(p);
        cell.cloneNonPositionParameters(border);
        cell.setUseBorderPadding(true);
        cell.setPadding(2f);
        return cell;
     }
	
    private Rectangle getBorder() {
	Rectangle border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(0f);
        border.setBorderWidthBottom(0f);
        border.setBorderWidthRight(0f);
        border.setBorderWidthTop(0f);
        border.setBorderColor(Color.BLACK);
	return border;
   }		
	
}
