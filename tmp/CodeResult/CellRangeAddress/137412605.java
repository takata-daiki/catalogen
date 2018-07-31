package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import skala.erp.bmp.baseenums.BuisnessEntityType;
import skala.erp.bmp.baseenums.CounteragentType;
import skala.erp.bmp.baseenums.InventJournalType;
import skala.erp.bmp.client.util.Global;
import skala.erp.bmp.client.util.NumberToWordsRussian;
import skala.erp.bmp.server.dao.CompanyTableDAO;
import skala.erp.bmp.server.dao.CounteragentInfoDAO;
import skala.erp.bmp.server.dao.InventJournalTableDAO;
import skala.erp.bmp.server.dao.InventJournalTransDAO;
import skala.erp.bmp.server.dao.InventPartyTableDAO;
import skala.erp.bmp.server.dao.ItemCodeDAO;
import skala.erp.bmp.server.dao.ShiftJournalDAO;
import skala.erp.bmp.server.dao.UnitOfMeasureDAO;
import skala.erp.bmp.shared.entity.CompanyTable;
import skala.erp.bmp.shared.entity.CounteragentInfo;
import skala.erp.bmp.shared.entity.InventJournalTable;
import skala.erp.bmp.shared.entity.InventJournalTrans;
import skala.erp.bmp.shared.entity.ItemCode;
import skala.erp.bmp.shared.entity.ShiftJournal;

public class MX18ExcellExporter extends ExcelExporter {

	ItemCodeDAO itemCodeDAO;

	InventJournalTableDAO inventJournalTableDAO;

	InventJournalTransDAO inventJournalTransDAO;

	ShiftJournalDAO shiftJournalDAO;

	CompanyTableDAO companyTableDAO;

	CounteragentInfoDAO counteragentInfoDAO;

	InventPartyTableDAO inventPartyTableDAO;

	UnitOfMeasureDAO unitOfMeasureDAO;

	double totalQty;

	double totalCost;

	double pageTotalQty;

	double pageTotalCost;

	String transactionSource;

	public MX18ExcellExporter(InventJournalTableDAO inventJournalTableDAO,
			InventJournalTransDAO inventJournalTransDAO,
			ShiftJournalDAO shiftJournalDAO, CompanyTableDAO companyTableDAO,
			InventPartyTableDAO inventPartyTableDAO,
			UnitOfMeasureDAO unitOfMeasureDAO,
			CounteragentInfoDAO counteragentInfoDAO, ItemCodeDAO itemCodeDAO) {
		this.inventJournalTableDAO = inventJournalTableDAO;
		this.counteragentInfoDAO = counteragentInfoDAO;
		this.itemCodeDAO = itemCodeDAO;
		this.inventJournalTransDAO = inventJournalTransDAO;
		this.shiftJournalDAO = shiftJournalDAO;
		this.inventPartyTableDAO = inventPartyTableDAO;
		this.companyTableDAO = companyTableDAO;
		this.unitOfMeasureDAO = unitOfMeasureDAO;
	}

	public String getMX18FileName(String shiftId, String host, String dataAreaId) {
		totalCost = 0;
		totalQty = 0;
		CompanyTable company = companyTableDAO.find(dataAreaId);
		CounteragentInfo companyInfo = counteragentInfoDAO.find(dataAreaId,
				CounteragentType.Company.type(), dataAreaId);
		ShiftJournal shiftJournal = shiftJournalDAO.find(shiftId, dataAreaId);
		String sourceLocationId = shiftJournal.getInventDimFrom()
				.getInventLocationId();
		String destLocationId = shiftJournal.getInventDimTo()
				.getInventLocationId();
		String companyString = String.format("%s \"%s\", %s, %s, %s тел.: %s",
				BuisnessEntityType.getEnumById(company.getBuisnessEntityType())
						.fullName(), company.getCompanyName(), companyInfo
						.getPostIndex(), companyInfo.getCity(), companyInfo
						.getAddress(), companyInfo.getPhone());
		companyString = removeLinebreaksFromString(companyString);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String date = dateFormat.format(shiftJournal.getShiftDate());
		InventJournalTable transitJournal = null;
		for (InventJournalTable journalTable : shiftJournal
				.getInventJournalTables()) {
			if (journalTable.getInventJournalType() == InventJournalType.Transit
					.getIntValue()) {
				transitJournal = journalTable;
				break;
			}
		}
		String filename = "mx-18-" + transitJournal.getInventJournalId()
				+ ".xls";
		transactionSource = String.format("%s№%s", Global.transitJournalDesc,
				transitJournal.getInventJournalId());

		HSSFWorkbook hwb = null;
		try {
			hwb = createWorkbook("mx18template.xls", host);
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Not found";
		}

		HSSFSheet sheet = hwb.getSheetAt(0);
		HSSFSheet sheetWithRows = hwb.getSheetAt(1);

		setCellValue(sheet, "A7", companyString);
		setCellValue(sheet, "W7", companyInfo.getOkpo());
		setCellValue(sheet, "L11", shiftId);
		setCellValue(sheet, "O11", date);
		setCellValue(sheet, "A16", sourceLocationId);
		setCellValue(sheet, "E16", destLocationId);
		// List<InventJournalTrans> journalTrans = inventJournalTransDAO
		// .findAllJournalTrans(transitJournal.getInventJournalId(),
		// dataAreaId);
		List<InventJournalTrans> journalTrans = inventJournalTransDAO
				.getAllTransForPackedItemByJournalId(
						transitJournal.getInventJournalId(), dataAreaId);

		float headerHeight = 0;
		for (int i = 0; i <= 16; i++) {
			HSSFRow row = sheet.getRow(i);
			headerHeight += row.getHeightInPoints() / 0.75;
		}
		float footerHeight = 0;
		for (int i = 22; i <= 33; i++) {
			HSSFRow row = sheet.getRow(i);
			footerHeight += row.getHeightInPoints() / 0.75;
		}
		float tableHeaderHeight = 0;
		for (int i = 17; i <= 21; i++) {
			HSSFRow row = sheet.getRow(i);
			tableHeaderHeight += row.getHeightInPoints() / 0.75;
		}
		float totalHeightReport = footerHeight + headerHeight
				+ tableHeaderHeight + ROW_HEIGHT * journalTrans.size();

		int needPages = (int) Math.ceil(totalHeightReport / MAX_LIST_LANDSCAPE_HEIGHT);

		int needShift = journalTrans.size() + 5 * (needPages - 1) + needPages;
		int[] rowsAtPage = new int[needPages];
		for (int i = 0; i < needPages; i++) {
			rowsAtPage[i] = journalTrans.size() / needPages;
		}
		rowsAtPage[0] += journalTrans.size() % needPages;

		int rowIndex = 22;
		sheet.shiftRows(rowIndex, sheet.getLastRowNum(), needShift);
		int rowsAlreadyAdded = 0;
		int pageNumber = 0;
		for (int i = 0; i < journalTrans.size(); i++) {
			HSSFRow sourceRow;
			if (rowsAtPage[pageNumber] == 1) {
				sourceRow = sheetWithRows.getRow(0);
			} else if (rowsAlreadyAdded == 0) {
				sourceRow = sheetWithRows.getRow(1);
			} else if (rowsAlreadyAdded + 1 == rowsAtPage[pageNumber]) {
				sourceRow = sheetWithRows.getRow(3);
			} else {
				sourceRow = sheetWithRows.getRow(2);
			}

			createRow(hwb, sourceRow, sheet, rowIndex, journalTrans.get(i));
			rowsAlreadyAdded++;
			rowIndex++;
			if (rowsAtPage[pageNumber] == rowsAlreadyAdded) {
				rowsAlreadyAdded = 0;
				pageNumber++;
				sourceRow = sheetWithRows.getRow(4);
				createSummaryRow(hwb, sourceRow, sheet, rowIndex);
				if (pageNumber == needPages) {
					rowIndex++;
					break;
				}
				sheet.setRowBreak(rowIndex);
				rowIndex++;
				pageTotalQty = 0;
				pageTotalCost = 0;
				copyRow(hwb, sheet.getRow(17), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(18), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(19), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(20), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(21), sheet, rowIndex);
				rowIndex++;
			}
		}

		setCellValue(sheet, String.format("T%d", rowIndex + 1), totalQty);
		setCellValue(sheet, String.format("Y%d", rowIndex + 1), totalCost);

		NumberToWordsRussian numberToWordsRussian = new NumberToWordsRussian();

		setCellValue(
				sheet,
				String.format("G%d", rowIndex + 3),
				numberToWordsRussian.toWords(new BigDecimal(journalTrans.size())));
		setCellValue(sheet, String.format("B%d", rowIndex + 5),
				numberToWordsRussian.toWords(new BigDecimal(totalCost)));
		String stringTotalCost = String.format("%12.2f",
				Global.roundAtTwoSign(totalCost));
		String[] splitedTotalCost = stringTotalCost.split("\\,");
		setCellValue(sheet, String.format("V%d", rowIndex + 5),
				Integer.valueOf(splitedTotalCost[1]));

		hwb.removeSheetAt(1);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			hwb.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}

	private void createSummaryRow(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.createRow(rowIndex);

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);

		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("Итого");
		fillEmptyCells(row, newCellStyle, 1, 14);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$O$" + (rowIndex + 1)));

		cell = row.createCell(15);
		sourceCell = sourceRow.getCell(15);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(16);
		sourceCell = sourceRow.getCell(16);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(17);
		sourceCell = sourceRow.getCell(17);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 18, 18);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$R$" + (rowIndex + 1)
				+ ":$S$" + (rowIndex + 1)));

		cell = row.createCell(19);
		sourceCell = sourceRow.getCell(19);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(pageTotalQty);
		fillEmptyCells(row, newCellStyle, 20, 20);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$T$" + (rowIndex + 1)
				+ ":$U$" + (rowIndex + 1)));

		cell = row.createCell(21);
		sourceCell = sourceRow.getCell(21);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("Х");
		fillEmptyCells(row, newCellStyle, 22, 23);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$V$" + (rowIndex + 1)
				+ ":$X$" + (rowIndex + 1)));

		cell = row.createCell(24);
		sourceCell = sourceRow.getCell(24);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(String.format("%.2f", pageTotalCost));
	}

	private void createRow(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex, InventJournalTrans trans) {
		HSSFRow row = sheet.createRow(rowIndex);

		String UOMId = trans.getItem().getUOMId();

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);

		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(trans.getItem().getName());
		fillEmptyCells(row, newCellStyle, 1, 1);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$B$" + (rowIndex + 1)));

		cell = row.createCell(2);
		sourceCell = sourceRow.getCell(2);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		ItemCode code = itemCodeDAO.find(trans.getItemId(),
				trans.getDataAreaId());
		if (code == null)
			cell.setCellValue("");
		else
			cell.setCellValue(code.getCode());

		cell = row.createCell(3);
		sourceCell = sourceRow.getCell(3);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 4, 4);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$D$" + (rowIndex + 1)
				+ ":$E$" + (rowIndex + 1)));

		cell = row.createCell(5);
		sourceCell = sourceRow.getCell(5);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(6);
		sourceCell = sourceRow.getCell(6);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(7);
		sourceCell = sourceRow.getCell(7);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(8);
		sourceCell = sourceRow.getCell(8);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(UOMId);
		fillEmptyCells(row, newCellStyle, 9, 9);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$I$" + (rowIndex + 1)
				+ ":$J$" + (rowIndex + 1)));

		cell = row.createCell(10);
		sourceCell = sourceRow.getCell(10);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(unitOfMeasureDAO.find(UOMId, trans.getDataAreaId())
				.getUOMCode());
		fillEmptyCells(row, newCellStyle, 11, 11);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$K$" + (rowIndex + 1)
				+ ":$L$" + (rowIndex + 1)));

		cell = row.createCell(12);
		sourceCell = sourceRow.getCell(12);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(UOMId);

		cell = row.createCell(13);
		sourceCell = sourceRow.getCell(13);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 14, 14);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$N$" + (rowIndex + 1)
				+ ":$O$" + (rowIndex + 1)));

		cell = row.createCell(15);
		sourceCell = sourceRow.getCell(15);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(16);
		sourceCell = sourceRow.getCell(16);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(17);
		sourceCell = sourceRow.getCell(17);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 18, 18);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$R$" + (rowIndex + 1)
				+ ":$S$" + (rowIndex + 1)));

		cell = row.createCell(19);
		sourceCell = sourceRow.getCell(19);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(trans.getQty());
		fillEmptyCells(row, newCellStyle, 20, 20);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$T$" + (rowIndex + 1)
				+ ":$U$" + (rowIndex + 1)));
		pageTotalQty += trans.getQty();
		totalQty += trans.getQty();

		double cost = inventPartyTableDAO
				.getMaxCostByTransactionDocumentAndItemId(transactionSource,
						trans.getItemId(), trans.getDataAreaId());
		if (cost == 0) {
			cost = inventPartyTableDAO.getLastPartyForItem(trans.getItemId(),
					trans.getDataAreaId()).getCost();
		}

		cell = row.createCell(21);
		sourceCell = sourceRow.getCell(21);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(String.format("%.2f", cost));
		fillEmptyCells(row, newCellStyle, 22, 23);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$V$" + (rowIndex + 1)
				+ ":$X$" + (rowIndex + 1)));

		cell = row.createCell(24);
		sourceCell = sourceRow.getCell(24);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(String.format("%.2f", trans.getQty() * cost));
		pageTotalCost += trans.getQty() * cost;
		totalCost += trans.getQty() * cost;

	}
}
