package Excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

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
import Objekte.Zahlungen;
import Sort.SortDatumBetreuerin;
import datenbank.Datenbank;

public class ExcelBetreuerinnenAN {
	FileOutputStream fileOutputStream = null;

	public void erstelleExcel(ArrayList<String> spalten, String suchevon, String suchebis) {
		try {

			fileOutputStream = new FileOutputStream("Betreuerinnen Angefangen.xlsx");
			
			spalten.remove("Angefangen");
			spalten.remove("Nr");
			spalten.remove("Nachname");
			
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

			XSSFRow row = sheet.createRow(0);

			sheet.addMergedRegion(new CellRangeAddress(0, // first
					// row
					// (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					spalten.size() +1 // last column (0-based)
			));

			XSSFCell headerCell0 = row.createCell(0);
			headerCell0.setCellStyle(headerStyle);
			headerCell0.setCellValue("Betreuerinnen ZENTRALTABELLE");

			XSSFRow headerRow = sheet.createRow(1);
			int count = 2;

			for (String spaltenname : spalten) {
				if (!spaltenname.equals("Angefangen")) {
					XSSFCell headerCell1 = headerRow.createCell(count);
					headerCell1.setCellStyle(headerStyle);
					headerCell1.setCellValue(spaltenname);
					count++;
				}
			}

			XSSFCell headerCell00 = headerRow.createCell(0);
			headerCell00.setCellStyle(headerStyle);
			headerCell00.setCellValue("Angefangen");

			XSSFCell headerCell1 = headerRow.createCell(1);
			headerCell1.setCellStyle(headerStyle);
			headerCell1.setCellValue("Betreuerinnen");

			SortDatumBetreuerin sort = new SortDatumBetreuerin();
			ArrayList<Betreuerin> sortierte_liste = sort
					.sortBetreuerin("Angefangen", betreuerinnen, suchevon, suchebis);

			for (int i = 0; i < sortierte_liste.size(); i++) {

				Betreuerin b = sortierte_liste.get(i);

				// Neue Zeile
				XSSFRow dataRow = sheet.createRow(i + 2);

				XSSFCell cell1 = dataRow.createCell(0);
				cell1.setCellStyle(dataStyle);
				cell1.setCellValue(new XSSFRichTextString(b.getAngefangen()));

				XSSFCell cell0 = dataRow.createCell(1);
				cell0.setCellStyle(dataStyle);
				cell0.setCellValue(new XSSFRichTextString(b.getNummer()+" "+b.getNachname()));

				for (int j = 0; j < spalten.size(); j++) {

					// Write data in data row
					XSSFCell cell = dataRow.createCell(j + 2);
					cell.setCellStyle(dataStyle);

					if (spalten.get(j).equals("Vorname")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getVorname()));
					}
					if (spalten.get(j).equals("Geburtsdatum")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getGeburtsdatum()));
					}
					if (spalten.get(j).equals("Geburtsname")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getGeburtsname()));
					}
					if (spalten.get(j).equals("Adresse")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getAdresse()));
					}
					if (spalten.get(j).equals("Stadt")) {
						cell.setCellValue(new XSSFRichTextString(b.getStadt()));
					}
					if (spalten.get(j).equals("PLZ")) {
						cell.setCellValue(new XSSFRichTextString(b.getPlz()));
					}
					if (spalten.get(j).equals("Email")) {
						cell.setCellValue(new XSSFRichTextString(b.getEmail()));
					}
					if (spalten.get(j).equals("SK Handy")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getSkHandy()));
					}
					if (spalten.get(j).equals("AT Handy")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getAtHandy()));
					}
					if (spalten.get(j).equals("SK Festnetz")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getSkFestnetz()));
					}
					if (spalten.get(j).equals("Personalausweis")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getPersonalausweis()));
					}
					if (spalten.get(j).equals("Reisepass")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getReisepass()));
					}
					if (spalten.get(j).equals("Personalstand")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getPersonalstand()));
					}
					if (spalten.get(j).equals("Nationalität")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getNationalitat()));
					}
					if (spalten.get(j).equals("Registernummer")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getRegisternummer()));
					}
					if (spalten.get(j).equals("Steuernummer")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getSteuernummer()));
					}
					if (spalten.get(j).equals("VSNR")) {
						cell.setCellValue(new XSSFRichTextString(b.getVsnr()));
					}
					if (spalten.get(j).equals("MGNR")) {
						cell.setCellValue(new XSSFRichTextString(b.getMgnr()));
					}
					if (spalten.get(j).equals("Mutter Nachname")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getMutter_nachname()));
					}
					if (spalten.get(j).equals("Mutter Vorname")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getMutter_vorname()));
					}
					if (spalten.get(j).equals("Vater Nachname")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getVater_nachname()));
					}
					if (spalten.get(j).equals("Vater Vorname")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getVater_vorname()));
					}
					if (spalten.get(j).equals("Kontakt")) {
						cell.setCellValue(new XSSFRichTextString(b
								.getKontakt()));
					}
					if (spalten.get(j).equals("Info")) {
						cell.setCellValue(new XSSFRichTextString(b.getArea()));
					}
					if (spalten.get(j).equals("Aufgehört")) {
						cell.setCellValue(new XSSFRichTextString(b.getAufgehort()));
					}
				}
			}

			for (int i = 0; i <= spalten.size() + 1; i++) {
				sheet.autoSizeColumn(i);
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
