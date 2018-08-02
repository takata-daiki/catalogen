package com.FSS.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;

import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;

public class test {
	public static void main(String args[]) throws FileNotFoundException,
			IOException {
		HWPFDocument doc = new HWPFDocument(new FileInputStream(
				"e:\\Document\\b7???????2009?????????.doc"));
		Range r = doc.getRange(); // ??word?????
		int lenParagraph = r.numParagraphs();// ?????
		// Paragraph p;
		String firstP = null;
		StringBuffer secondP = new StringBuffer();//????????;
		int count = 0;
		for (int x = 0; x < lenParagraph; x++) {
			Paragraph p = r.getParagraph(x);
			String text = p.text();
			
			if (text.trim().length() == 0){
				if(count>=2)
					break;
				continue;
			}
				
			else {
				count++;
				if(count == 1)
					firstP = text;
				else{
					secondP.append(text.substring(0, text.length()-1));
				}
					
			}
		}
		System.out.println(secondP);
		for (int x = 0; x < lenParagraph; x++) {
			Paragraph p = r.getParagraph(x);
			String text = p.text();
			if(text.matches(".*???.*\\s"))
				System.out.println(text);
		}
	}
}
