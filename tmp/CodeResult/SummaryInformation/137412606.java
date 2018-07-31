package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;

import skala.erp.bmp.baseenums.InventJournalType;
import skala.erp.bmp.server.dao.InventJournalTableDAO;
import skala.erp.bmp.shared.entity.InventJournalTable;
import skala.erp.bmp.shared.entity.InventJournalTrans;

public class InventJournalExcelExporter extends ExcelExporter {

	InventJournalTableDAO inventJournalTableDAO;

	public InventJournalExcelExporter(
			InventJournalTableDAO inventJournalTableDAO) {
		this.inventJournalTableDAO = inventJournalTableDAO;
	}

	public String getExcelFileName(String journalId, String dataAreaId) {
		String fileName = "journal-" + journalId + ".xls";
		InventJournalTable journal = inventJournalTableDAO.find(journalId,
				dataAreaId);

		InventJournalTrans[] journalTrans = journal.getJournalTrans().toArray(
				new InventJournalTrans[0]);
		Arrays.sort(journalTrans, new Comparator<InventJournalTrans>() {

			@Override
			public int compare(InventJournalTrans o1, InventJournalTrans o2) {
				return ((Integer) o1.getLineNum()).compareTo(o2.getLineNum());
			}
		});

		HSSFWorkbook hwb = new HSSFWorkbook();
		Map<String, CellStyle> styles = createStyles(hwb);
		hwb.createInformationProperties();
		SummaryInformation summaryInfo = hwb.getSummaryInformation();
		summaryInfo.setAuthor("BMP-System");
		HSSFSheet sheet = hwb.createSheet("Журнал");

		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		// Title row
		String journalStateField = "";
		if (journal.isPosted()) {
			journalStateField = "Разнесен "
					+ journal.getPostedDateTime().toString();
		} else
			journalStateField = "Не разнесен";
		int rowIndex = 0;

		addHeaderRow(styles.get("title"), sheet, rowIndex, "Документ №"
				+ journalId);
		rowIndex++;

		addHeaderRow(
				styles.get("title"),
				sheet,
				rowIndex,
				"Тип: "
						+ InventJournalType.getEnumFromInt(
								journal.getInventJournalType()).getType());
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex,
				journal.getInventJournalDesc());
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex, journalStateField);
		rowIndex++;

		if (journal.getInventJournalType() == InventJournalType.Invent
				.getIntValue()) {
			HSSFRow inventHeaderRow = sheet.createRow(rowIndex);
			inventHeaderRow.setHeightInPoints(30);
			Cell cell = inventHeaderRow.createCell(0);
			cell.setCellValue("№");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(1);
			cell.setCellValue("Код");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(2);
			cell.setCellValue("Наименование");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(3);
			cell.setCellValue("Склад");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(4);
			cell.setCellValue("Док. количество");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(5);
			cell.setCellValue("Факт. количество");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(6);
			cell.setCellValue("Расхождение");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(7);
			cell.setCellValue("Ед. изм.");
			cell.setCellStyle(styles.get("header"));
			rowIndex++;

			for (InventJournalTrans trans : journalTrans) {
				HSSFRow currentRow = sheet.createRow(rowIndex);
				currentRow.setHeightInPoints(30);
				cell = currentRow.createCell(0);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getLineNum());
				cell = currentRow.createCell(1);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItemId());
				cell = currentRow.createCell(2);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItem().getName());
				cell = currentRow.createCell(3);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getInventDimTo().getInventLocationId());
				cell = currentRow.createCell(4);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getExpectQty());
				cell = currentRow.createCell(5);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getQty());
				cell = currentRow.createCell(6);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getQty() - trans.getExpectQty());
				cell = currentRow.createCell(7);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItem().getUOMId());

				rowIndex++;
			}
		} else if (journal.getInventJournalType() == InventJournalType.Transit
				.getIntValue()) {
			HSSFRow inventHeaderRow = sheet.createRow(rowIndex);
			inventHeaderRow.setHeightInPoints(30);
			Cell cell = inventHeaderRow.createCell(0);
			cell.setCellValue("№");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(1);
			cell.setCellValue("Код");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(2);
			cell.setCellValue("Наименование");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(3);
			cell.setCellValue("Скл. списания");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(4);
			cell.setCellValue("Скл. назначения");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(5);
			cell.setCellValue("Количество");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(6);
			cell.setCellValue("Ед. изм.");
			cell.setCellStyle(styles.get("header"));

			rowIndex++;

			for (InventJournalTrans trans : journalTrans) {
				HSSFRow currentRow = sheet.createRow(rowIndex);
				currentRow.setHeightInPoints(30);
				cell = currentRow.createCell(0);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getLineNum());
				cell = currentRow.createCell(1);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItemId());
				cell = currentRow.createCell(2);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItem().getName());
				cell = currentRow.createCell(3);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getInventDimFrom()
						.getInventLocationId());
				cell = currentRow.createCell(4);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getInventDimTo().getInventLocationId());
				cell = currentRow.createCell(5);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getQty());
				cell = currentRow.createCell(6);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItem().getUOMId());
				rowIndex++;
			}
		}

		else {
			HSSFRow inventHeaderRow = sheet.createRow(rowIndex);
			Cell cell = inventHeaderRow.createCell(0);
			inventHeaderRow.setHeightInPoints(30);
			cell.setCellValue("№");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(1);
			cell.setCellValue("Код");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(2);
			cell.setCellValue("Наименование");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(3);
			cell.setCellValue("Склад");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(4);
			cell.setCellValue("Количество");
			cell.setCellStyle(styles.get("header"));
			cell = inventHeaderRow.createCell(5);
			cell.setCellValue("Ед. изм.");
			cell.setCellStyle(styles.get("header"));

			rowIndex++;

			for (InventJournalTrans trans : journalTrans) {
				HSSFRow currentRow = sheet.createRow(rowIndex);
				currentRow.setHeightInPoints(30);
				cell = currentRow.createCell(0);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getLineNum());
				cell = currentRow.createCell(1);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItemId());
				cell = currentRow.createCell(2);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItem().getName());
				cell = currentRow.createCell(3);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getInventDimTo().getInventLocationId());
				cell = currentRow.createCell(4);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getQty());
				cell = currentRow.createCell(5);
				cell.setCellStyle(styles.get("cell"));
				cell.setCellValue(trans.getItem().getUOMId());
				rowIndex++;
			}
		}
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2, false);
		sheet.setColumnWidth(2, 28 * 256);
		sheet.setColumnWidth(3, 18 * 256);
		sheet.setColumnWidth(4, 14 * 256);
		sheet.setColumnWidth(5, 14 * 256);
		sheet.setColumnWidth(6, 9 * 256);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			hwb.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;

	}

	public String getPeriodReportFilename(Date start, Date finish,
			String inventLocationId, String dataAreaId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		String filename = "profitLoss(" + dateFormat.format(start) + " - "
				+ dateFormat.format(finish) + ").xls";

		Map<String, Double> map = inventJournalTableDAO.getProfitLossInPeriod(
				start, finish, inventLocationId, dataAreaId);

		HSSFWorkbook hwb = new HSSFWorkbook();
		hwb.createInformationProperties();
		SummaryInformation summaryInfo = hwb.getSummaryInformation();
		summaryInfo.setAuthor("BMP-System");
		Map<String, CellStyle> styles = createStyles(hwb);

		HSSFSheet sheet = hwb.createSheet("Журнал");

		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		int rowIndex = 0;
		addHeaderRow(styles.get("title"), sheet, rowIndex,
				"Отчет по суммарным убыткам/излишкам");
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex, "По складу "
				+ inventLocationId);
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex, "За период с "
				+ dateFormat.format(start) + " по " + dateFormat.format(finish));
		rowIndex++;

		addFourColumnTableRow(styles.get("header"), sheet, rowIndex, "Код",
				"Наименование", "Количество", "Ед. изм");
		rowIndex++;
		Iterator<Entry<String, Double>> iterator = map.entrySet().iterator();
		for (int i = 0; i < map.size(); i++) {
			Entry<String, Double> entry = iterator.next();
			String[] strings = entry.getKey().split("\\;");
			addFourColumnTableRow(styles.get("cell"), sheet, rowIndex,
					strings[0], strings[1], entry.getValue(), strings[2]);
			rowIndex++;
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			hwb.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}

	protected void addFourColumnTableRow(CellStyle style, HSSFSheet sheet,
			int rowIndex, String col1, String col2, String col3, String col4) {
		HSSFRow tableHeaderRow = sheet.createRow(rowIndex);
		tableHeaderRow.setHeightInPoints(30);

		Cell cell = tableHeaderRow.createCell(0);
		cell.setCellValue(col1);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 1, 1);
		cell = tableHeaderRow.createCell(2);
		cell.setCellValue(col2);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 3, 4);
		cell = tableHeaderRow.createCell(5);
		cell.setCellValue(col3);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 6, 6);
		cell = tableHeaderRow.createCell(7);
		cell.setCellValue(col4);
		cell.setCellStyle(style);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$B$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$C$" + (rowIndex + 1)
				+ ":$E$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$F$" + (rowIndex + 1)
				+ ":$G$" + (rowIndex + 1)));
	}

	protected void addFourColumnTableRow(CellStyle style, HSSFSheet sheet,
			int rowIndex, String col1, String col2, double col3, String col4) {
		HSSFRow tableHeaderRow = sheet.createRow(rowIndex);
		tableHeaderRow.setHeightInPoints(30);

		Cell cell = tableHeaderRow.createCell(0);
		cell.setCellValue(col1);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 1, 1);
		cell = tableHeaderRow.createCell(2);
		cell.setCellValue(col2);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 3, 4);
		cell = tableHeaderRow.createCell(5);
		cell.setCellValue(col3);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 6, 6);
		cell = tableHeaderRow.createCell(7);
		cell.setCellValue(col4);
		cell.setCellStyle(style);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$B$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$C$" + (rowIndex + 1)
				+ ":$E$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$F$" + (rowIndex + 1)
				+ ":$G$" + (rowIndex + 1)));
	}

	protected void addTwoColumnTableRow(CellStyle style, HSSFSheet sheet,
			int rowIndex, String col1, String col2) {
		HSSFRow tableHeaderRow = sheet.createRow(rowIndex);
		tableHeaderRow.setHeightInPoints(30);

		Cell cell = tableHeaderRow.createCell(0);
		cell.setCellValue(col1);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 1, 3);
		cell = tableHeaderRow.createCell(4);
		cell.setCellValue(col2);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 5, 7);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$D$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$E$" + (rowIndex + 1)
				+ ":$H$" + (rowIndex + 1)));
	}

	protected void addTwoColumnTableRow(CellStyle style, HSSFSheet sheet,
			int rowIndex, String col1, double col2) {
		HSSFRow tableHeaderRow = sheet.createRow(rowIndex);
		tableHeaderRow.setHeightInPoints(30);

		Cell cell = tableHeaderRow.createCell(0);
		cell.setCellValue(col1);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 1, 3);
		cell = tableHeaderRow.createCell(4);
		cell.setCellValue(col2);
		cell.setCellStyle(style);
		fillEmptyCells(tableHeaderRow, style, 5, 7);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$D$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$E$" + (rowIndex + 1)
				+ ":$H$" + (rowIndex + 1)));
	}

}
