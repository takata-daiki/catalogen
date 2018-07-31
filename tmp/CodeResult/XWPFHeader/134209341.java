/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intersourcellc.smopmaker.api.render;

import com.intersourcellc.smopmaker.api.config.EnvironmentConfig;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *  Create a SMOP file in DOCX format from the given data 
 * @author jimt
 */
public class SmopDocumentFactory 
{
    
    protected final Logger logger = LoggerFactory.getLogger( getClass() );
 
	private static final String BULLET_LIST_FONT = "Courier New";
	private static final int BULLET_LIST_SIZE = 11;
	
    private File outputFilename;
    private OutputStream outputStream;
    
    private Date date = new Date();
    private String title = null;
    private LinkedHashMap<String,String> macros = new LinkedHashMap<String, String>();
    
    /** Used for unit tesitng mode only */
    private String webappRoot;
    
    /** A reference to the Spring-created environment config */
    private EnvironmentConfig environmentConfig;
    
     /** If this is set, this file will be written to when the SMOP is created */
    public void setOutputFilename ( File outputFilename)
    {
        this.outputFilename = outputFilename;
    }
    
    public void addMacro ( String key, String value)
    {
        macros.put(key, value);
    }
    
    public void setTitle ( String t)
    {
        this.title = t;
    }    
    
    public void setDate ( Date date )
    {
        this.date = date;
    }
    
    /** If this is set, this output stream will be written to when the SMOP is created */
    public void setOutputStream ( OutputStream os)
    {
        this.outputStream = os;
    }
    
    public SmopDocumentFactory  (EnvironmentConfig envConfig)
    {
        this.environmentConfig = envConfig;
		
	
    }
    	
	/** Probably not used anymore.. Add key,value pairs to table */
	private void macroReplaceTable ( XWPFDocument doc ) throws Exception
	{
     
		for (XWPFTable table : doc.getTables()) 
		{

			int rowNumber = 0;
			for ( XWPFTableRow row : table.getRows() )
			{
				rowNumber++;
				for ( XWPFTableCell cell : row.getTableCells())
				{
					
					String thisCellValue = cell.getText();
					if ( thisCellValue != null)
					{
						for ( String macro : macros.keySet())
						{
							String value = macros.get(macro);
							if ( value == null )
							{
								value ="";
							}
								
							thisCellValue = thisCellValue.replace(macro,value);
							
						}
					
						cell.removeParagraph(0);
						cell.addParagraph();
						
						XWPFParagraph paragraph = cell.getParagraphs().get(0);
						if ( paragraph == null)
						{
							throw new Exception ( "Paragraph not found");
						}
						
						XWPFRun thisRun = paragraph.createRun();
						
						thisRun.setFontFamily("Arial");
						
						if ( rowNumber == 1)
						{ 
							thisRun.setBold(true);
							thisRun.setFontSize(12);						
							paragraph.setAlignment(ParagraphAlignment.CENTER);
					
						}
						else
						{
							thisRun.setFontSize(10);
						}
						
						thisRun.setText(thisCellValue);
					}
					
				}
			
			}
		}
	}

	
    /** Create the SMOP document, write to the output stream and/or file if they are set */
    public void createSmopDocument() throws Exception
    {
  
        XWPFDocument doc = new XWPFDocument(POIXMLDocument.openPackage(getImagePath() + File.separator + "SmopTemplate.docx"));

        // Macro replace the header
        List<XWPFHeader> headers = doc.getHeaderList();
        for (XWPFHeader header : headers) {
            macroReplaceDocument(header.getParagraphs());
        }

        // Macro replace the body
        macroReplaceDocument(doc.getParagraphs());
		macroReplaceTable(doc);
		
		
        // Ensure there is somewhere to write to
        if ( outputStream == null && outputFilename == null)
        {
            throw new Exception ( "Both outputFilename and outputStream are null, createSmop has nothing to do");
        }
        
        // Write the output file, if available
        if (outputStream != null) {
            OutputStream os = outputStream;
            
            doc.write(os);
            os.flush();
            os.close();
        }
        
        // Write to output file, if available
        if (outputFilename != null) 
        {
            FileOutputStream fos = new FileOutputStream(outputFilename);
            
            doc.write(fos);
            fos.flush();
            fos.close();
        }

        logger.debug("Wrote output file " + outputFilename);

    }

    /** Needs to be set for unit testing mode */
    public void setTestingWebappRoot ( String root)
    {
        webappRoot = root;
    }
    
    /** Gets the path to load the images from */
    public String getImagePath () throws Exception
    {
        
        String path; 
        logger.debug("Servlet context set to " + environmentConfig.getServletContext());
        if ( environmentConfig.getServletContext() != null)
        {
            path = environmentConfig.getServletContext().getRealPath( File.separator + "WEB-INF" + File.separator + "docTemplates");
        }
        else
        {
            logger.warn ( "SERVLET CONTEXT WAS NULL!  SETTING DOC TEMPLATE PATH TO DEBUG PATH");
            
            if ( webappRoot == null)
            {
                throw new Exception ( "Servlet context was null and webappRoot not set manually for unit test mode, no way to get the webapp root path");
            }
            else
            {
                path = webappRoot;
            }
        }
        
        logger.debug ( "Returning image path as " + path);
        
        return path;
    }
    
    /** Run the macro replacement */
    private void macroReplaceDocument( List<XWPFParagraph> paragraphs)
    {
		List<XWPFParagraph> clone = new ArrayList<>();
		clone.addAll(paragraphs);

		
		CTDecimalNumber num = CTDecimalNumber.Factory.newInstance();
		num.setVal(BigInteger.valueOf(7));

		CTDecimalNumber ilvl = CTDecimalNumber.Factory.newInstance();
		ilvl.setVal(BigInteger.valueOf(0));

		
		for ( XWPFParagraph paragraph : clone)
        {
            List<XWPFRun> runs = paragraph.getRuns();
            
            for ( XWPFRun run : runs)
            {
				
				if ( run.getText(0) != null && run.getText(0).contains("$$$DEVICE_BULLET_LIST$$$"))
				{
					
					String deviceList = macros.get("$$$DEVICE_LIST$$$");
					if ( deviceList != null )
					{
						StringTokenizer lines = new StringTokenizer ( deviceList, ",");
						ArrayList<String> reversedList = new ArrayList<>();
						while ( lines.hasMoreTokens())
						{
								reversedList.add(lines.nextToken());
						}

						Collections.reverse(reversedList);

						Iterator<String> i = reversedList.listIterator();

						run.getParagraph().getCTP().getPPr().getNumPr().setIlvl(ilvl);
						run.getParagraph().getCTP().getPPr().getNumPr().setNumId(num);
						run.setText(i.next(),0);
						run.setFontFamily(BULLET_LIST_FONT);
						run.setFontSize(BULLET_LIST_SIZE);
		
						run.getParagraph().setStyle("List Paragraph");
						XWPFRun lastRun = run;


						while ( i.hasNext())
						{


							XWPFParagraph p =  lastRun.getParagraph().getDocument().insertNewParagraph(lastRun.getParagraph().getCTP().newCursor()); 
							
							run.getParagraph().setStyle("List Paragraph");

							// Setup the paragraph as a bullet
							CTPPr ppr = p.getCTP().addNewPPr();
							CTNumPr nump = ppr.addNewNumPr();
							nump.addNewIlvl();
							nump.setIlvl(ilvl);
							nump.addNewNumId();
							nump.setNumId(num);

							// Insert the text
							XWPFRun newXWPFRun = p.createRun();

							newXWPFRun.setText(i.next());
							newXWPFRun.setFontFamily(BULLET_LIST_FONT);
							newXWPFRun.setFontSize(BULLET_LIST_SIZE);
							lastRun = newXWPFRun;
						}
					}

				}
				for ( String macroKey : macros.keySet())
				{
				
					if ( run.getText(0) != null && run.getText(0).contains(macroKey))
					{
                    
						StringTokenizer lines = new StringTokenizer ( macros.get(macroKey), "\n");
						String newString = run.getText(0).replace(macroKey, lines.nextToken());
						run.setText( newString,0);

						while ( lines.hasMoreTokens())
						{

							run.addBreak();
							CTR newRun = paragraph.getCTP().addNewR();

							XWPFRun newXWPFRun = paragraph.getRuns().get(paragraph.getRuns().size()-1);
							newXWPFRun.setFontFamily("Courier New");
							newXWPFRun.setFontSize(11);
							newXWPFRun.setText(lines.nextToken());


						}
						
					}
				}
				
			
            }
        }
    }
    
  
     
    
}
