package Excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

import javafx.collections.ObservableList;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor.LAVENDER;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Objekte.Betreuerin;
import Objekte.Familie;
import Sort.SortBetreuerinnen;
import datenbank.Datenbank;

public class ExcelBetreuerinnen {

	FileOutputStream fileOutputStream = null;

	public void erstelleExcel(ArrayList<String> spalten) {
		try {

			fileOutputStream = new FileOutputStream("Betreuerinnen.xlsx");

			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen");

			// Font setting for sheet.
			XSSFFont font = xssfWorkbook.createFont();
			font.setBoldweight((short) 700);

			// Create Styles for sheet.
			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
			headerStyle.setFillForegroundColor(LAVENDER.index);
			headerStyle.setFont(font);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);

			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
			dataStyle.setWrapText(true);
			dataStyle.setAlignment(HorizontalAlignment.CENTER);

			Datenbank<Betreuerin> db = new Datenbank<Betreuerin>();
			ObservableList<Betreuerin> betreuerinnen = db
					.filltable(new Betreuerin());

			SortBetreuerinnen sb = new SortBetreuerinnen();
			betreuerinnen = sb.sortBetreuerinNr(betreuerinnen);
			
			XSSFRow row = sheet.createRow(0);

			sheet.addMergedRegion(new CellRangeAddress(0, // first
					// row
					// (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					spalten.size() - 1 // last column (0-based)
			));

			XSSFCell headerCell0 = row.createCell(0);
			headerCell0.setCellStyle(headerStyle);
			headerCell0.setCellValue("Betreuerinnen ZENTRALTABELLE");

			XSSFRow headerRow = sheet.createRow(1);
			int count = 0;

			for (String spaltenname : spalten) {
				XSSFCell headerCell1 = headerRow.createCell(count);
				headerCell1.setCellStyle(headerStyle);
				headerCell1.setCellValue(spaltenname);
				count++;
			}

			for (int i = 0; i < betreuerinnen.size(); i++) {

				// Neue Zeile
				XSSFRow dataRow = sheet.createRow(i + 2);
				Betreuerin b = betreuerinnen.get(i);

				for (int j = 0; j < spalten.size(); j++) {

					// Write data in data row
					XSSFCell cell1 = dataRow.createCell(j);
					cell1.setCellStyle(dataStyle);

					if (spalten.get(j).equals("Nr")) {
						cell1.setCellValue(new XSSFRichTextString(""
								+ b.getNummer()));
					}
					if (spalten.get(j).equals("Nachname")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getNachname()));
					}
					if (spalten.get(j).equals("Vorname")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getVorname()));
					}
					if (spalten.get(j).equals("Geburtsdatum")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getGeburtsdatum()));
					}
					if (spalten.get(j).equals("Geburtsname")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getGeburtsname()));
					}
					if (spalten.get(j).equals("Adresse")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getAdresse()));
					}
					if (spalten.get(j).equals("Stadt")) {
						cell1.setCellValue(new XSSFRichTextString(b.getStadt()));
					}
					if (spalten.get(j).equals("PLZ")) {
						cell1.setCellValue(new XSSFRichTextString(b.getPlz()));
					}
					if (spalten.get(j).equals("Email")) {
						cell1.setCellValue(new XSSFRichTextString(b.getEmail()));
					}
					if (spalten.get(j).equals("SK Handy")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getSkHandy()));
					}
					if (spalten.get(j).equals("AT Handy")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getAtHandy()));
					}
					if (spalten.get(j).equals("SK Festnetz")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getSkFestnetz()));
					}
					if (spalten.get(j).equals("Personalausweis")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getPersonalausweis()));
					}
					if (spalten.get(j).equals("Reisepass")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getReisepass()));
					}
					if (spalten.get(j).equals("Personalstand")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getPersonalstand()));
					}
					if (spalten.get(j).equals("Nationalität")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getNationalitat()));
					}
					if (spalten.get(j).equals("Registernummer")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getRegisternummer()));
					}
					if (spalten.get(j).equals("Steuernummer")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getSteuernummer()));
					}
					if (spalten.get(j).equals("VSNR")) {
						cell1.setCellValue(new XSSFRichTextString(b.getVsnr()));
					}
					if (spalten.get(j).equals("MGNR")) {
						cell1.setCellValue(new XSSFRichTextString(b.getMgnr()));
					}
					if (spalten.get(j).equals("Mutter Nachname")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getMutter_nachname()));
					}
					if (spalten.get(j).equals("Mutter Vorname")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getMutter_vorname()));
					}
					if (spalten.get(j).equals("Vater Nachname")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getVater_nachname()));
					}
					if (spalten.get(j).equals("Vater Vorname")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getVater_vorname()));
					}
					if (spalten.get(j).equals("Kontakt")) {
						cell1.setCellValue(new XSSFRichTextString(b
								.getKontakt()));
					}
					if (spalten.get(j).equals("Info")) {
						cell1.setCellValue(new XSSFRichTextString(b.getArea()));
					}
					if (spalten.get(j).equals("Angefangen")) {
						cell1.setCellValue(new XSSFRichTextString(b.getAngefangen()));
					}
					if (spalten.get(j).equals("Aufgehört")) {
						cell1.setCellValue(new XSSFRichTextString(b.getAufgehort()));
					}
					
					sheet.autoSizeColumn(j);
				}
			}

			// write in excel
			xssfWorkbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*
			 * Close File Output Stream
			 */
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
