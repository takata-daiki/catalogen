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
import Objekte.Betreuung;
import Objekte.BetreuungFamilie;
import Objekte.Familie;
import Objekte.Zahlungen;
import Sort.SortDatumFamilieBetreuung;
import datenbank.Datenbank;

public class ExcelFamilieBetreuungAN {
	FileOutputStream fileOutputStream = null;

	public void erstelleExcel(ArrayList<String> spalten, String von, String bis) {
		try {

			fileOutputStream = new FileOutputStream("Familien Angefangen.xlsx");

			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook.createSheet("Familien");

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

			Datenbank<Familie> db = new Datenbank<Familie>();
			ObservableList<Familie> familien = db
					.filltable(new Familie());

			XSSFRow row = sheet.createRow(0);

			sheet.addMergedRegion(new CellRangeAddress(0, // first
					// row
					// (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					spalten.size() // last column (0-based)
			));

			XSSFCell headerCell0 = row.createCell(0);
			headerCell0.setCellStyle(headerStyle);
			headerCell0.setCellValue("Familien ZENTRALTABELLE");

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
			headerCell1.setCellValue("Familien");

			SortDatumFamilieBetreuung sort = new SortDatumFamilieBetreuung();
			ArrayList<LinkedHashMap<String, BetreuungFamilie>> sortierte_liste = sort
					.sortBetreuungFamilie("Angefangen", familien, von, bis);

			for (int i = 0; i < sortierte_liste.size(); i++) {

				LinkedHashMap<String, BetreuungFamilie> lhm = sortierte_liste.get(i);

				Iterator<String> keyIterator2 = lhm.keySet().iterator();

				// Das Element von der Liste
				String listfamilie = keyIterator2.next();
				BetreuungFamilie listBetreuung = lhm.get(listfamilie);

				// Neue Zeile
				XSSFRow dataRow = sheet.createRow(i + 2);

				XSSFCell cell1 = dataRow.createCell(0);
				cell1.setCellStyle(dataStyle);
				cell1.setCellValue(new XSSFRichTextString(listBetreuung
						.getAngefangen()));

				XSSFCell cell0 = dataRow.createCell(1);
				cell0.setCellStyle(dataStyle);
				cell0.setCellValue(new XSSFRichTextString(listfamilie));

				spalten.remove("Angefangen");

				for (int j = 0; j < spalten.size(); j++) {

					// Write data in data row
					XSSFCell cell = dataRow.createCell(j + 2);
					cell.setCellStyle(dataStyle);

					if (spalten.get(j).equals("Betreuerin Nr")) {
						cell.setCellValue(new XSSFRichTextString(""
								+ listBetreuung.getBetreuerin_nummer()));
					}
					if (spalten.get(j).equals("Betreuerin Nachname")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getBetreuerin_nachname()));
					}
					if (spalten.get(j).equals("Aufgehรถrt")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getAufgehort()));
					}
					if (spalten.get(j).equals("Angemeldet")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getAngemeldet()));
					}
					if (spalten.get(j).equals("Abgemeldet")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getAbgemeldet()));
					}
					if (spalten.get(j).equals("Fahrgeld")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getFahrgeld()));
					}
					if (spalten.get(j).equals("Taggeld")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getVerdienst()));
					}
					if (spalten.get(j).equals("SVA")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getSva()));
					}
					if (spalten.get(j).equals("Referenz")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getReferenz()));
					}
					if (spalten.get(j).equals("Information")) {
						cell.setCellValue(new XSSFRichTextString(listBetreuung
								.getInformation()));
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
