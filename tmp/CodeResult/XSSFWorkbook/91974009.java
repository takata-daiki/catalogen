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
import Objekte.Familie;
import Objekte.Zahlungen;
import Objekte.ZahlungenFamilie;
import Sort.SortDatumFamilieZahlungen;
import datenbank.Datenbank;

public class ExcelFamilieZahlungenM {
	FileOutputStream fileOutputStream = null;

	public void erstelleExcel(ArrayList<String> spalten, String von, String bis) {
		try {

			fileOutputStream = new FileOutputStream("Familie Monat.xlsx");

			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook.createSheet("Familie");

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
					spalten.size()  // last column (0-based)
			));

			XSSFCell headerCell0 = row.createCell(0);
			headerCell0.setCellStyle(headerStyle);
			headerCell0.setCellValue("Familie ZENTRALTABELLE");

			XSSFRow headerRow = sheet.createRow(1);
			int count = 2;

			for (String spaltenname : spalten) {
				if (!spaltenname.equals("Monat")) {
					XSSFCell headerCell1 = headerRow.createCell(count);
					headerCell1.setCellStyle(headerStyle);
					headerCell1.setCellValue(spaltenname);
					count++;
				}
			}

			XSSFCell headerCell00 = headerRow.createCell(0);
			headerCell00.setCellStyle(headerStyle);
			headerCell00.setCellValue("Monat");
			
			XSSFCell headerCell1 = headerRow.createCell(1);
			headerCell1.setCellStyle(headerStyle);
			headerCell1.setCellValue("Familie");
			
			SortDatumFamilieZahlungen sort = new SortDatumFamilieZahlungen();
			ArrayList<LinkedHashMap<String, ZahlungenFamilie>> sortierte_liste = sort
					.sortZahlungenFamilie("Monat",
							familien, von, bis);

			for (int i = 0; i < sortierte_liste.size(); i++) {

				LinkedHashMap<String, ZahlungenFamilie> lhm = sortierte_liste.get(i);

				Iterator<String> keyIterator2 = lhm.keySet().iterator();

				// Das Element von der Liste
				String listfamilie = keyIterator2.next();
				ZahlungenFamilie listzahlung = lhm.get(listfamilie);

				// Neue Zeile
				XSSFRow dataRow = sheet.createRow(i + 2);


				XSSFCell cell1 = dataRow.createCell(0);
				cell1.setCellStyle(dataStyle);
				cell1.setCellValue(new XSSFRichTextString(listzahlung
						.getMonat()));
				
				XSSFCell cell0 = dataRow.createCell(1);
				cell0.setCellStyle(dataStyle);
				cell0.setCellValue(new XSSFRichTextString(listfamilie));
				
				spalten.remove("Monat");
				
				for (int j = 0; j < spalten.size(); j++) {

					// Write data in data row
					XSSFCell cell = dataRow.createCell(j+2);
					cell.setCellStyle(dataStyle);

					if (spalten.get(j).equals("Rechnungsdatum")) {
						cell.setCellValue(new XSSFRichTextString(listzahlung
								.getRechnungsdatum()));
					}
					if (spalten.get(j).equals("Betrag")) {
						cell.setCellValue(new XSSFRichTextString(listzahlung
								.getBetrag()));
					}
					if (spalten.get(j).equals("Rechnungsinfo")) {
						cell.setCellValue(new XSSFRichTextString(listzahlung
								.getRechnungsinfo()));
					}
					if (spalten.get(j).equals("Rechnungsnummer")) {
						cell.setCellValue(new XSSFRichTextString(listzahlung
								.getRechnungsnummer()));
					}
					if (spalten.get(j).equals("Bezahldatum")) {
						cell.setCellValue(new XSSFRichTextString(listzahlung
								.getBezahlt_datum()));
					}
					if (spalten.get(j).equals("Bar/Bank")) {
						cell.setCellValue(new XSSFRichTextString(listzahlung
								.getBar_bank()));
					}
					if (spalten.get(j).equals("Kontoauszug")) {
						cell.setCellValue(new XSSFRichTextString(listzahlung
								.getKontoauszug()));
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
