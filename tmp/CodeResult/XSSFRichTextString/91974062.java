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
import Objekte.Betreuung;
import Objekte.Familie;
import Objekte.Zahlungen;
import Sort.SortBetreuerinnen;
import datenbank.Datenbank;

public class ExcelBetreuerinBetreuung {

	FileOutputStream fileOutputStream = null;

	public void erstelleExcel(ArrayList<String> spalten) {
		try {

			/*fileOutputStream = new FileOutputStream(File.separator + "Users"
					+ File.separator + "emilmusil" + File.separator + "Desktop"
					+ File.separator + "Betreuerinnen Betreuung.xlsx");*/
			
			fileOutputStream = new FileOutputStream("Betreuerinnen Betreuung.xlsx");

			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook
					.createSheet("Betreuerinnen");

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

			int countBetreuung = 0;// Damit ich weiß, wie of sich Betreuung
									// wiederholen müssen

			for (Betreuerin bet : betreuerinnen) {
				ArrayList<Betreuung> z = bet.getBetreuung();
				if (z != null) {
					if (z.size() > countBetreuung) {
						countBetreuung = z.size();
					}
				}
			}

			XSSFRow row = sheet.createRow(0);

			sheet.addMergedRegion(new CellRangeAddress(0, // first
					// row
					// (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					spalten.size() * countBetreuung + 1 // last column (0-based)
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

			for (int i = 0; i < countBetreuung; i++) {
				sheet.addMergedRegion(new CellRangeAddress(1, // first
						// row
						// (0-based)
						1, // last row (0-based)
						2 + (spalten.size() * i), // first column (0-based)
						1 + (spalten.size() * (i + 1)) // last column (0-based)
				));

				XSSFCell celltemp = row1.createCell(2 + (spalten.size() * i));
				celltemp.setCellStyle(headerStyle);
				celltemp.setCellValue("Betreuung " + (i + 1));
			}

			// Zweite Zeile
			XSSFRow headerRow = sheet.createRow(2);

			XSSFCell nrCell = headerRow.createCell(0);
			nrCell.setCellStyle(headerStyle);
			nrCell.setCellValue("Nr");

			XSSFCell nachnameCell = headerRow.createCell(1);
			nachnameCell.setCellStyle(headerStyle);
			nachnameCell.setCellValue("Nachname");
			for (int i = 0; i < countBetreuung; i++) {
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
				ArrayList<Betreuung> betreuungen = b.getBetreuung();

				XSSFCell betreuerinnen_cell = dataRow.createCell(0);
				betreuerinnen_cell.setCellStyle(dataStyle);
				betreuerinnen_cell.setCellValue(new XSSFRichTextString(""
						+ b.getNummer()));

				XSSFCell betreuerinnen_cell1 = dataRow.createCell(1);
				betreuerinnen_cell1.setCellStyle(dataStyle);
				betreuerinnen_cell1.setCellValue(new XSSFRichTextString(""
						+ b.getNachname()));

				if (betreuungen != null) {

					for (int z = 0; z < countBetreuung; z++) {

						if (z < betreuungen.size()) {

							Betreuung betreuung = betreuungen.get(z);

							for (int j = 0; j < spalten.size(); j++) {

								// Write data in data row
								XSSFCell cell1 = dataRow.createCell(j
										+ spalten.size() * z + 2);
								cell1.setCellStyle(dataStyle);

								if (spalten.get(j).equals("Familie Nr")) {
									cell1.setCellValue(new XSSFRichTextString(""+
											betreuung.getFamilie_nummer()));
								}
								if (spalten.get(j).equals("Familie Nachname")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getFamilie_nachname()));
								}
								if (spalten.get(j).equals("Angefangen")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getAngefangen()));
								}
								if (spalten.get(j).equals("Aufgehort")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getAufgehort()));
								}
								if (spalten.get(j).equals("Angemeldet")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getAngemeldet()));
								}
								if (spalten.get(j).equals("Abgemeldet")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getAbgemeldet()));
								}
								if (spalten.get(j).equals("Fahrgeld")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getFahrgeld()));
								}
								if (spalten.get(j).equals("Taggeld")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getVerdienst()));
								}
								if (spalten.get(j).equals("SVA")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getSva()));
								}
								if (spalten.get(j).equals("Referenz")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getReferenz()));
								}
								if (spalten.get(j).equals("Information")) {
									cell1.setCellValue(new XSSFRichTextString(
											betreuung.getInformation()));
								}
							}
						}
					}
				}
			}

			for (int i = 0; i <= spalten.size() * countBetreuung + 1; i++) {
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
