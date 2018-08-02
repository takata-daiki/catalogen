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

import Objekte.BetreuungFamilie;
import Objekte.Familie;
import Objekte.Zahlungen;
import Sort.SortDatumFamilie;
import datenbank.Datenbank;

public class ExcelFamilienAU {
	FileOutputStream fileOutputStream = null;

	@SuppressWarnings("deprecation")
	public void erstelleExcel(ArrayList<String> spalten, int patient1,
			int patient2, int kontaktperson1, int kontaktperson2, String von, String bis) {
		try {

			fileOutputStream = new FileOutputStream("Familien Angefangen.xlsx");

			patient1 = patient1 + 1;
			
			spalten.remove("P Aufgehรถrt");
			
			if(spalten.remove("  Nr")){
				patient1 = patient1 - 1;
			}
			if(spalten.remove("P Nachname")){
				patient1 = patient1 - 1;
			}
			
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

			XSSFRow zeile = sheet.createRow(0);

			sheet.addMergedRegion(new CellRangeAddress(0, // first
					// row
					// (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					spalten.size()+1 // last column (0-based)
			));

			XSSFCell headerCell0 = zeile.createCell(0);
			headerCell0.setCellStyle(headerStyle);
			headerCell0.setCellValue("Familien ZENTRALTABELLE");

			XSSFRow zweiteZeile = sheet.createRow(2);
			int count = 2;

			for (String spaltenname : spalten) {
				if (!spaltenname.equals("Aufgehรถrt")) {
					XSSFCell spaltenCells = zweiteZeile.createCell(count);
					spaltenCells.setCellStyle(headerStyle);
					spaltenCells.setCellValue(spaltenname.substring(2));
					count++;
				}
			}

			XSSFCell headerCell00 = zweiteZeile.createCell(0);
			headerCell00.setCellStyle(headerStyle);
			headerCell00.setCellValue("Aufgehรถrt");

			XSSFCell spaltenCells = zweiteZeile.createCell(1);
			spaltenCells.setCellStyle(headerStyle);
			spaltenCells.setCellValue("Familien");

			SortDatumFamilie sort = new SortDatumFamilie();
			ArrayList<Familie> sortierte_liste = sort
					.sortFamilie("Aufgehรถrt", familien, von, bis);

			for (int i = 0; i < sortierte_liste.size(); i++) {

				Familie f = sortierte_liste.get(i);

				// Neue Zeile
				XSSFRow dataRow = sheet.createRow(i + 3);

				XSSFCell cell1f = dataRow.createCell(0);
				cell1f.setCellStyle(dataStyle);
				cell1f.setCellValue(new XSSFRichTextString(f.getAufgehort()));

				XSSFCell cell0 = dataRow.createCell(1);
				cell0.setCellStyle(dataStyle);
				cell0.setCellValue(new XSSFRichTextString(f.getNummer()+" "+f.getNachname_patient()));

				

				for (int j = 0; j < spalten.size(); j++) {

					// Write data in data row
					XSSFCell cell1 = dataRow.createCell(j + 2);
					cell1.setCellStyle(dataStyle);

					if (spalten.get(j).equals("K1 Beziehung")) {
						cell1.setCellValue(new XSSFRichTextString(""
								+ f.getBeziehungsstand1()));
					}
					if (spalten.get(j).equals("K2 Beziehung")) {
						cell1.setCellValue(new XSSFRichTextString(""
								+ f.getBeziehungsstand2()));
					}
					if (spalten.get(j).equals("  Nr")) {
						cell1.setCellValue(new XSSFRichTextString(""
								+ f.getNummer()));
					}
					if (spalten.get(j).equals("P Nachname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getNachname_patient()));
					}
					if (spalten.get(j).equals("P Betreuerin 1")) {
						ArrayList<BetreuungFamilie> al = f.getBetreuung();
						if (al != null && al.size() >= 1) {
							BetreuungFamilie neu = al.get(al.size() - 1);
							cell1.setCellValue(new XSSFRichTextString(neu
									.getBetreuerin_nummer()
									+ " "
									+ neu.getBetreuerin_nachname()));
						}
					}
					if (spalten.get(j).equals("P Betreuerin 2")) {
						ArrayList<BetreuungFamilie> al = f.getBetreuung();
						if (al != null && al.size() >= 2) {
							BetreuungFamilie neu = al.get(al.size() - 2);
							cell1.setCellValue(new XSSFRichTextString(neu
									.getBetreuerin_nummer()
									+ " "
									+ neu.getBetreuerin_nachname()));
						}
					}
					if (spalten.get(j).equals("P Betreuerin 3")) {
						ArrayList<BetreuungFamilie> al = f.getBetreuung();
						if (al != null && al.size() >= 3) {
							BetreuungFamilie neu = al.get(al.size() - 3);
							cell1.setCellValue(new XSSFRichTextString(neu
									.getBetreuerin_nummer()
									+ " "
									+ neu.getBetreuerin_nachname()));
						}
					}
					if (spalten.get(j).equals("P Betreuerin 4")) {
						ArrayList<BetreuungFamilie> al = f.getBetreuung();
						if (al != null && al.size() >= 4) {
							BetreuungFamilie neu = al.get(al.size() - 4);
							cell1.setCellValue(new XSSFRichTextString(neu
									.getBetreuerin_nummer()
									+ " "
									+ neu.getBetreuerin_nachname()));
						}
					}
					if (spalten.get(j).equals("K1 Nachname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getNachname_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Nachname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getNachname_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Vorname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getVorname_patient()));
					}
					if (spalten.get(j).equals("K1 Vorname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getVorname_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Vorname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getVorname_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Geburtsdatum")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getGeburtsdatum_patient()));
					}
					if (spalten.get(j).equals("K1 Geburtsdatum")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getGeburtsdatum_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Geburtsdatum")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getGeburtsdatum_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Adresse")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getAdresse_patient()));
					}
					if (spalten.get(j).equals("K1 Adresse")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getAdresse_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Adresse")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getAdresse_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Stadt")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getStadt_patient()));
					}
					if (spalten.get(j).equals("K1 Stadt")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getStadt_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Stadt")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getStadt_kontaktperson2()));
					}
					if (spalten.get(j).equals("P PLZ")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getPlz_patient()));
					}
					if (spalten.get(j).equals("K1 PLZ")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getPlz_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 PLZ")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getPlz_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Email")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getEmail_patient()));
					}
					if (spalten.get(j).equals("K1 Email")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getEmail_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Email")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getEmail_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Bundesland")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getBunesland_patient()));
					}
					if (spalten.get(j).equals("K1 Bundesland")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getBunesland_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Bundesland")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getBunesland_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Handy")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHandy_patient()));
					}
					if (spalten.get(j).equals("K1 Handy")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHandy_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Handy")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHandy_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Festnetz")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getFestnetz_patient()));
					}
					if (spalten.get(j).equals("K1 Festnetz")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getFestnetz_kontaktperson1()));
					}
					if (spalten.get(j).equals("K2 Festnetz")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getFestnetz_kontaktperson2()));
					}
					if (spalten.get(j).equals("P Kontakt")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getKontakt()));
					}
					if (spalten.get(j).equals("P Angefangen")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getAngefangen()));
					}
					if (spalten.get(j).equals("P Hausarzt Name")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHausarzt_name()));
					}
					if (spalten.get(j).equals("P Hausarzt Telefon")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHausarzt_telefon()));
					}
					if (spalten.get(j).equals("P Hausarzt Adresse")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHausarzt_adresse()));
					}
					if (spalten.get(j).equals("P Hausarzt Stadt")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHausarzt_stadt()));
					}
					if (spalten.get(j).equals("P Hausarzt PLZ")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHausarzt_plz()));
					}
					if (spalten.get(j).equals("P2 Diagnose")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getDiagnosep2()));
					}
					if (spalten.get(j).equals("P Infos")) {
						cell1.setCellValue(new XSSFRichTextString(f.getArea()));
					}
					if (spalten.get(j).equals("P2 Nachname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getNachname_patient2()));
					}
					if (spalten.get(j).equals("P2 Vorname")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getVorname_patient2()));
					}
					if (spalten.get(j).equals("P2 Geburtsdatum")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getGeburtsdatum_patient2()));
					}
					if (spalten.get(j).equals("P2 Adresse")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getAdresse_patient2()));
					}
					if (spalten.get(j).equals("P2 Stadt")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getStadt_patient2()));
					}
					if (spalten.get(j).equals("P2 PLZ")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getPlz_patient2()));
					}
					if (spalten.get(j).equals("P2 Bundesland")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getBunesland_patient2()));
					}
					if (spalten.get(j).equals("P2 Handy")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getHandy_patient2()));
					}
					if (spalten.get(j).equals("P2 Festnetz")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getFestnetz_patient2()));
					}
					if (spalten.get(j).equals("P2 Email")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getEmail_patient2()));
					}
					if (spalten.get(j).equals("P2 Angefangen")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getAngefangen2()));
					}
					if (spalten.get(j).equals("P2 Aufgehรถrt")) {
						cell1.setCellValue(new XSSFRichTextString(f
								.getAufgehort2()));
					}
				}
			}

			XSSFRow row = sheet.createRow(1);

			if (patient1 == 0) {

				if (patient2 == 0) {
					if (kontaktperson1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								kontaktperson1 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Kontaktperson 1");
					}
					if (kontaktperson2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								kontaktperson1, // first column (0-based)
								kontaktperson1 + kontaktperson2 - 1 // last
																	// column
																	// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(kontaktperson1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 2");
					}
				} else if (kontaktperson1 == 0) {
					if (patient2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								patient2 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 2");
					}
					if (kontaktperson2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient2, // first column (0-based)
								patient2 + kontaktperson2 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient2);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 2");
					}
				} else if (kontaktperson2 == 0) {
					if (patient2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
								patient2 - 1));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 2");
					}
					if (kontaktperson1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient2, // first column (0-based)
								patient2 + kontaktperson1 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient2);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 1");
					}
				} else {
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
							patient2 - 1));

					XSSFCell headerCell1 = row.createCell(0);
					headerCell1.setCellStyle(headerStyle);
					headerCell1.setCellValue("Patient 2");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient2,
							patient2 + kontaktperson1 - 1));

					XSSFCell headerCell2 = row.createCell(patient2);
					headerCell2.setCellStyle(headerStyle);
					headerCell2.setCellValue("Kontaktperson 1");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient2
							+ kontaktperson1, patient2 + kontaktperson1
							+ kontaktperson2 - 1));

					XSSFCell headerCell3 = row.createCell(patient2
							+ kontaktperson1);
					headerCell3.setCellStyle(headerStyle);
					headerCell3.setCellValue("Kontaktperson 2");
				}
			} else if (patient2 == 0) {

				if (patient1 == 0) {
					if (kontaktperson1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								kontaktperson1 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Kontaktperson 1");
					}
					if (kontaktperson2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								kontaktperson1, // first column (0-based)
								kontaktperson1 + kontaktperson2 - 1 // last
																	// column
																	// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(kontaktperson1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 2");
					}
				} else if (kontaktperson1 == 0) {
					if (patient1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								patient1 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 1");
					}
					if (kontaktperson2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient1, // first column (0-based)
								patient1 + kontaktperson2 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 2");
					}
				} else if (kontaktperson2 == 0) {
					if (patient1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
								patient1 - 1));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 1");
					}
					if (kontaktperson1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient1, // first column (0-based)
								patient1 + kontaktperson1 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 1");
					}
				} else {
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
							patient1 - 1));

					XSSFCell headerCell1 = row.createCell(0);
					headerCell1.setCellStyle(headerStyle);
					headerCell1.setCellValue("Patient 1");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1,
							patient1 + kontaktperson1 - 1));

					XSSFCell headerCell2 = row.createCell(patient1);
					headerCell2.setCellStyle(headerStyle);
					headerCell2.setCellValue("Kontaktperson 1");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1
							+ kontaktperson1, patient1 + kontaktperson1
							+ kontaktperson2 - 1));

					XSSFCell headerCell3 = row.createCell(patient1
							+ kontaktperson1);
					headerCell3.setCellStyle(headerStyle);
					headerCell3.setCellValue("Kontaktperson 2");
				}
			} else if (kontaktperson1 == 0) {

				if (patient1 == 0) {
					if (patient2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								patient2 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 2");
					}
					if (kontaktperson2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient2, // first column (0-based)
								patient2 + kontaktperson2 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient2);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 2");
					}
				} else if (patient2 == 0) {
					if (patient1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								patient1 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 1");
					}
					if (kontaktperson2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient1, // first column (0-based)
								patient1 + kontaktperson2 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 2");
					}
				} else if (kontaktperson2 == 0) {
					if (patient1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
								patient1 - 1));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 1");
					}
					if (patient2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient1, // first column (0-based)
								patient1 + patient2 - 1 // last
														// column
														// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Patient 2");
					}
				} else {
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
							patient1 - 1));

					XSSFCell headerCell1 = row.createCell(0);
					headerCell1.setCellStyle(headerStyle);
					headerCell1.setCellValue("Patient 1");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1,
							patient1 + patient2 - 1));

					XSSFCell headerCell2 = row.createCell(patient1);
					headerCell2.setCellStyle(headerStyle);
					headerCell2.setCellValue("Patient 2");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1
							+ patient2, patient1 + patient2 + kontaktperson2
							- 1));

					XSSFCell headerCell3 = row.createCell(patient1 + patient2);
					headerCell3.setCellStyle(headerStyle);
					headerCell3.setCellValue("Kontaktperson 2");
				}
			} else if (kontaktperson2 == 0) {

				if (patient1 == 0) {
					if (patient2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								patient2 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 2");
					}
					if (kontaktperson1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient2, // first column (0-based)
								patient2 + kontaktperson1 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient2);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 1");
					}
				} else if (patient2 == 0) {
					if (patient1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								0, // first column (0-based)
								patient1 - 1 // last column (0-based)
						));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 1");
					}
					if (kontaktperson1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient1, // first column (0-based)
								patient1 + kontaktperson1 - 1 // last
																// column
																// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Kontaktperson 1");
					}
				} else if (kontaktperson1 == 0) {
					if (patient1 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
								patient1 - 1));

						XSSFCell headerCell1 = row.createCell(0);
						headerCell1.setCellStyle(headerStyle);
						headerCell1.setCellValue("Patient 1");
					}
					if (patient2 != 0) {
						sheet.addMergedRegion(new CellRangeAddress(1, // first
																		// row
																		// (0-based)
								1, // last row (0-based)
								patient1, // first column (0-based)
								patient1 + patient2 - 1 // last
														// column
														// (0-based)
						));
						XSSFCell headerCell6 = row.createCell(patient1);
						headerCell6.setCellStyle(headerStyle);
						headerCell6.setCellValue("Patient 2");
					}
				} else {
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
							patient1 - 1));

					XSSFCell headerCell1 = row.createCell(0);
					headerCell1.setCellStyle(headerStyle);
					headerCell1.setCellValue("Patient 1");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1,
							patient1 + patient2 - 1));

					XSSFCell headerCell2 = row.createCell(patient1);
					headerCell2.setCellStyle(headerStyle);
					headerCell2.setCellValue("Patient 2");

					sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1
							+ patient2, patient1 + patient2 + kontaktperson1
							- 1));

					XSSFCell headerCell3 = row.createCell(patient1 + patient2);
					headerCell3.setCellStyle(headerStyle);
					headerCell3.setCellValue("Kontaktperson 1");
				}
			} else {
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,
						patient1 - 1));

				XSSFCell headerCell1 = row.createCell(0);
				headerCell1.setCellStyle(headerStyle);
				headerCell1.setCellValue("Patient 1");

				sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1,
						patient1 + patient2 - 1));

				XSSFCell headerCell2 = row.createCell(patient1);
				headerCell2.setCellStyle(headerStyle);
				headerCell2.setCellValue("Patient 2");

				sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1
						+ patient2, patient1 + patient2 + kontaktperson1 - 1));

				XSSFCell headerCell3 = row.createCell(patient1 + patient2);
				headerCell3.setCellStyle(headerStyle);
				headerCell3.setCellValue("Kontaktperson 1");

				sheet.addMergedRegion(new CellRangeAddress(1, 1, patient1
						+ patient2 + kontaktperson1, patient1 + patient2
						+ kontaktperson1 + kontaktperson2 - 1));

				XSSFCell headerCell4 = row.createCell(patient1 + patient2
						+ kontaktperson1);
				headerCell4.setCellStyle(headerStyle);
				headerCell4.setCellValue("Kontaktperson 2");
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
