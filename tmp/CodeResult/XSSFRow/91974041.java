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
import Objekte.Zahlungen;
import Sort.SortBetreuerinnen;
import datenbank.Datenbank;

public class ExcelBetreuerinZahlungen {

	FileOutputStream fileOutputStream = null;

	public void erstelleExcel(ArrayList<String> spalten) {
		try {

			fileOutputStream = new FileOutputStream("Betreuerinnen Zahlungen.xlsx");

			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook
					.createSheet("Betreuerinnen Zahlungen");

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

			int countzahlungen = 0;// Damit ich weiß, wie of sich Zahlungen
									// wiederholen müssen

			for (Betreuerin bet : betreuerinnen) {
				ArrayList<Zahlungen> z = bet.getZahlungen();
				if (z != null) {
					if (z.size() > countzahlungen) {
						countzahlungen = z.size();
					}
				}
			}

			XSSFRow row = sheet.createRow(0);

			sheet.addMergedRegion(new CellRangeAddress(0, // first
					// row
					// (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					spalten.size() * countzahlungen + 1 // last column (0-based)
			));

			XSSFCell headerCell0 = row.createCell(0);
			headerCell0.setCellStyle(headerStyle);
			headerCell0.setCellValue("Betreuerinnen ZENTRALLISTE");

			XSSFRow row1 = sheet.createRow(1);

			sheet.addMergedRegion(new CellRangeAddress(1, // first
					// row
					// (0-based)
					1, // last row (0-based)
					0, // first column (0-based)
					1 // last column (0-based)
			));

			XSSFCell betreuerinnenCell = row1.createCell(0);
			betreuerinnenCell.setCellStyle(headerStyle);
			betreuerinnenCell.setCellValue("Betreuerinnen");

			for (int i = 0; i < countzahlungen; i++) {
				sheet.addMergedRegion(new CellRangeAddress(1, // first
						// row
						// (0-based)
						1, // last row (0-based)
						2 + (spalten.size() * i), // first column (0-based)
						1 + (spalten.size() * (i + 1)) // last column (0-based)
				));

				XSSFCell celltemp = row1.createCell(2 + (spalten.size() * i));
				celltemp.setCellStyle(headerStyle);
				celltemp.setCellValue("Zahlung " + (i + 1));
			}

			// Zweite Zeile
			XSSFRow headerRow = sheet.createRow(2);

			XSSFCell nrCell = headerRow.createCell(0);
			nrCell.setCellStyle(headerStyle);
			nrCell.setCellValue("Nr");

			XSSFCell nachnameCell = headerRow.createCell(1);
			nachnameCell.setCellStyle(headerStyle);
			nachnameCell.setCellValue("Nachname");
			for (int i = 0; i < countzahlungen; i++) {
				int count = (spalten.size() * i) + 2;

				for (String spaltenname : spalten) {
					XSSFCell headerCell1 = headerRow.createCell(count);
					headerCell1.setCellStyle(headerStyle);
					headerCell1.setCellValue(spaltenname);
					count++;
				}
			}
			for (int i = 0; i < betreuerinnen.size(); i++) {

				// Neue Zeile
				XSSFRow dataRow = sheet.createRow(i + 3);

				Betreuerin b = betreuerinnen.get(i);
				ArrayList<Zahlungen> zahlungen = b.getZahlungen();

				XSSFCell betreuerinnen_cell = dataRow.createCell(0);
				betreuerinnen_cell.setCellStyle(dataStyle);
				betreuerinnen_cell.setCellValue(new XSSFRichTextString(""
						+ b.getNummer()));

				XSSFCell betreuerinnen_cell1 = dataRow.createCell(1);
				betreuerinnen_cell1.setCellStyle(dataStyle);
				betreuerinnen_cell1.setCellValue(new XSSFRichTextString(""
						+ b.getNachname()));

				if (zahlungen != null) {

					for (int z = 0; z < countzahlungen; z++) {

						if (z < zahlungen.size()) {

							Zahlungen zahlung = zahlungen.get(z);

							for (int j = 0; j < spalten.size(); j++) {

								// Write data in data row
								XSSFCell cell1 = dataRow.createCell(j
										+ spalten.size() * z + 2);
								cell1.setCellStyle(dataStyle);

								if (spalten.get(j).equals("Vereinbarungsdatum")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getVereinbarung_datum()));
								}
								if (spalten.get(j).equals("Vermittlungssumme")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getVereinbarung()));
								}
								if (spalten.get(j).equals("Von")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getVon()));
								}
								if (spalten.get(j).equals("Bis")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getBis()));
								}
								if (spalten.get(j).equals("Rechnungsnummer")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getRechnungsnummer()));
								}
								if (spalten.get(j).equals("Rechnungsdatum")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getRechnungsdatum()));
								}
								if (spalten.get(j).equals("Bezahldatum")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getBezahlt_datum()));
								}
								if (spalten.get(j).equals("Bar/Bank")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getBar_bank()));
								}
								if (spalten.get(j).equals("Kontoauszug")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getKontoauszug()));
								}
							}
						}
					}
				}
			}

			for (int i = 0; i <= spalten.size() * countzahlungen + 1; i++) {
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
