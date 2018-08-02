package com.selman;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadMainFiles {

	public static List<Reference> readreferencesFile() throws IOException {

		String filesname = "C:\\Users\\stayyar\\Downloads\\references_for_selman.doc";
		
		POIFSFileSystem fs = null;
		String[] paragraphs =null;
		List<Reference> refObjectList = new ArrayList<Reference>();
		try {
			fs = new POIFSFileSystem(new FileInputStream(filesname));

			HWPFDocument doc = new HWPFDocument(fs);

			WordExtractor we = new WordExtractor(doc);

			paragraphs = we.getParagraphText();

			System.out.println("Word Document has " + paragraphs.length
					+ " paragraphs");
			Pattern regex = Pattern.compile("[0-9]{4}");//find the date

			for (int i = 0; i < paragraphs.length; i++) {
				paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");
				System.out.println("RAW PARAGRAPH: "+paragraphs[i]);
				Matcher regexMatcher = regex.matcher(paragraphs[i]);

                if (regexMatcher.find()) 
                {  
                	
                    String date=regexMatcher.group();
                    String authors=paragraphs[i].substring(0, paragraphs[i].indexOf(date));
                    Reference ref=new Reference();
					ref.setAuthor(authors);
					ref.setYear(date);
					ref.setDetail(paragraphs[i].toString());
					refObjectList.add(ref);
                    System.out.println("Ref List,authors and date: "+authors+" ==" +date);
                } 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return refObjectList;
		
	}
	public static List<Reference> readThesisExtractReferences() throws Exception {
		String filesname = readProperty("INPUT_FILE_NAME");
		
		POIFSFileSystem fs = null;
		List<String> references = new ArrayList<String>();
		List<Reference> refObjectList = new ArrayList<Reference>();
		Pattern regex = Pattern.compile("[({\\[].*?[\\]})]");//get the values between paranthesis.
		int k=0;
		try {
			fs = new POIFSFileSystem(new FileInputStream(filesname));

			HWPFDocument doc = new HWPFDocument(fs);

			WordExtractor we = new WordExtractor(doc);

			String[] paragraphs = we.getParagraphText();

			System.out.println("Thesis  Document has " + paragraphs.length
					+ " paragraphs");
			
			for (int i = 0; i < paragraphs.length; i++) {
				paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");
				Matcher regexMatcher = regex.matcher(paragraphs[i]);

                while (regexMatcher.find()) 
                {   k++;
                 	//remove paranthesis
                    String ref=regexMatcher.group().substring(1,regexMatcher.group().length()-1);  
                    //remove 'et al.' part to facilitate searching. there are lots of 'et al.' in a thesis.
                     String newRef=ref.replace("et al.","");
                    if(newRef.matches(".*[0-9]{4}.*"))//no number means no reference. because in each reference there should be a 4 digit date.
                             references.add(newRef);
                } 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(+k +" references found");
		for (String string : references) {
			System.out.println("RAW REFERENCE: "+string);
			try {
				String []splitty=string.split(";");//refs are seperated by ';'
				for (int i = 0; i < splitty.length; i++) {
					  String[] a=splitty[i].split(",");//eauthor and date in each ref seperated by ","
						 if(a.length==2){
						      Reference ref=new Reference();
							  ref.setAuthor(a[0]);
							  ref.setYear(a[1]);
							  refObjectList.add(ref);
							  System.out.println("reference: "+ref.toString());
						 }
						 else if (a.length==1) {//if comma is missed,we got the last 4 characters of a ref as date and the rest as author
							 String year=splitty[i].substring(splitty[i].length()-5, splitty[i].length()) ;
							 String author=splitty[i].substring(0, splitty[i].length()-5);
						      Reference ref=new Reference();
							  ref.setAuthor(author);
							  ref.setYear(year);
							  refObjectList.add(ref);
							  System.out.println("reference irregular: "+ref.toString());

						 }
						 else{
							 
							 System.out.println("problematic entry: "+string);
						 }

				}
//				  if(a.length>2)
//				  {
//					  for (int i = 0; i < a.length; i++) {
//						  System.out.println("authors and year: "+a[i]);
//					}
//				  }

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("XXXXXXXXXXno semi-column splitting for "+string);
			}
		}
		return refObjectList;

	}

	public static String readProperty(String property) throws Exception, IOException{
		
		Properties prop = new Properties();
		prop.load(ReadMainFiles.class.getClassLoader().getResourceAsStream("config.properties"));
		return prop.getProperty(property);
	}
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readreferencesFile();
		//readThesisExtractReferences();
	}

}
