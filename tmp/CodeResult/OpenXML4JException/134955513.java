package org.nuclos.common2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.nuclos.common.NuclosFile;
import org.nuclos.common.RigidFile;


public abstract class DocumentFile extends RigidFile {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(DocumentFile.class);
	private String text;
	
	protected DocumentFile(RigidFile file) {
		super(file);
	}
	
	protected DocumentFile(String sFileName, Object documentFilePk) {
		super(sFileName, documentFilePk);
	}

	protected DocumentFile(String sFileName, Object documentFilePk, byte[] abContents) {
		super(sFileName, documentFilePk, abContents);
	}
	
	public String extractText() {
		return extractText(this);
	}
	
	public static String extractText(RigidFile rFile) {
		return extractText(rFile.getFilename(), rFile.getContents());
	}
	
	public static String extractText(NuclosFile nFile) {
		return extractText(nFile.getName(), nFile.getContent());
	}
	
	public static String extractText(String filename, byte[] contents) {
		String text = "";
		
		if (contents == null || contents.length == 0) {
			return text;
		}
		
		InputStream is = new ByteArrayInputStream(contents);
		try {
			String sFiletype = File.getFiletype(File.getExtension(filename));
			if (File.TYPE_PDF.equals(sFiletype)) {
				text = parsePDF(is);
			
			} else if (File.TYPE_TXT.equals(sFiletype)) {
				text = parseTxt(is);
				
			} else {
				try {
					text = ExtractorFactory.createExtractor(is).getText();
					
				} catch (IllegalArgumentException iae) {		
				}
			} 
							
		} catch (IOException | XmlException | OpenXML4JException e) {
			LOG.warn("Error on file parsing: ", e);
			
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException io2) {
			}
		}
		
		return text.replaceAll("\n", " ").trim();
	}
	
	private static String parsePDF(InputStream fi) throws IOException{
		PDFParser parser = new PDFParser(new org.apache.pdfbox.io.RandomAccessBuffer(fi));
		parser.parse();
		
		COSDocument cd = parser.getDocument();  
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument pddoc = new PDDocument(cd);
		String text = stripper.getText(pddoc);
		
		pddoc.close();
		cd.close();
		return text;
	}
	
	/**
	 * 
	 * 
	 */
	private static String parseTxt(InputStream bi) throws IOException {
		byte[] bytes = IOUtils.toByteArray(bi);
		return new String(bytes, "UTF-8");
	}
	
	@Override
	protected void contentsChanged() {
		text = null;
	}
	
	public String getText() {
		if (text == null) {
			text = extractText();
		}
		
		return text;
	}
	
}
