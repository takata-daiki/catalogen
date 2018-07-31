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
import Objekte.ZahlungenFamilie;
import Sort.SortFamilien;
import datenbank.Datenbank;

public class ExcelFamilieZahlungen {

	FileOutputStream fileOutputStream = null;

	public void erstelleExcel(ArrayList<String> spalten) {
		try {

			fileOutputStream = new FileOutputStream("Familie Zahlungen.xlsx");

			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook
					.createSheet("Familie Zahlungen");

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
			
			SortFamilien sb = new SortFamilien();
			familien = sb.sortFamilieNr(familien);

			int countzahlungen = 0;// Damit ich weiß, wie of sich Zahlungen
									// wiederholen müssen

			for (Familie bet : familien) {
				ArrayList<ZahlungenFamilie> z = bet.getZahlungen();
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
			headerCell0.setCellValue("Familien ZENTRALLISTE");

			XSSFRow row1 = sheet.createRow(1);

			sheet.addMergedRegion(new CellRangeAddress(1, // first
					// row
					// (0-based)
					1, // last row (0-based)
					0, // first column (0-based)
					1 // last column (0-based)
			));

			XSSFCell familienCell = row1.createCell(0);
			familienCell.setCellStyle(headerStyle);
			familienCell.setCellValue("Familien");

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
			for (int i = 0; i < familien.size(); i++) {

				// Neue Zeile
				XSSFRow dataRow = sheet.createRow(i + 3);

				Familie b = familien.get(i);
				ArrayList<ZahlungenFamilie> zahlungen = b.getZahlungen();

				XSSFCell familien_cell = dataRow.createCell(0);
				familien_cell.setCellStyle(dataStyle);
				familien_cell.setCellValue(new XSSFRichTextString(""
						+ b.getNummer()));

				XSSFCell familien_cell1 = dataRow.createCell(1);
				familien_cell1.setCellStyle(dataStyle);
				familien_cell1.setCellValue(new XSSFRichTextString(""
						+ b.getNachname_patient()));

				if (zahlungen != null) {

					for (int z = 0; z < countzahlungen; z++) {

						if (z < zahlungen.size()) {

							ZahlungenFamilie zahlung = zahlungen.get(z);

							for (int j = 0; j < spalten.size(); j++) {

								// Write data in data row
								XSSFCell cell1 = dataRow.createCell(j
										+ spalten.size() * z + 2);
								cell1.setCellStyle(dataStyle);

								if (spalten.get(j).equals("Monat")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getMonat()));
								}
								if (spalten.get(j).equals("Betrag")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getBetrag()));
								}
								if (spalten.get(j).equals("Rechnungsinfo")) {
									cell1.setCellValue(new XSSFRichTextString(
											zahlung.getRechnungsinfo()));
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
