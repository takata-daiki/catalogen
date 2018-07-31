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
import skala.erp.bmp.client.util.Global;
import skala.erp.bmp.client.util.NumberToWordsRussian;
import skala.erp.bmp.server.dao.CompanyTableDAO;
import skala.erp.bmp.server.dao.CounteragentInfoDAO;
import skala.erp.bmp.server.dao.InventTransactionDAO;
import skala.erp.bmp.server.dao.ItemCodeDAO;
import skala.erp.bmp.server.dao.M15LineDAO;
import skala.erp.bmp.server.dao.M15TableDAO;
import skala.erp.bmp.server.dao.UnitOfMeasureDAO;
import skala.erp.bmp.shared.entity.CompanyTable;
import skala.erp.bmp.shared.entity.CounteragentInfo;
import skala.erp.bmp.shared.entity.InventTransaction;
import skala.erp.bmp.shared.entity.ItemCode;
import skala.erp.bmp.shared.entity.M15Table;

public class M15ExcellExporter extends ExcelExporter {

	double totalQty;

	M15TableDAO m15TableDAO;
	M15LineDAO m15LineDAO;
	CompanyTableDAO companyTableDAO;
	UnitOfMeasureDAO unitOfMeasureDAO;
	CounteragentInfoDAO counteragentInfoDAO;
	ItemCodeDAO itemCodeDAO;
	InventTransactionDAO inventTransactionDAO;

	public M15ExcellExporter(M15TableDAO m15TableDAO, M15LineDAO m15LineDAO,
			CompanyTableDAO companyTableDAO, UnitOfMeasureDAO unitOfMeasureDAO,
			CounteragentInfoDAO counteragentInfoDAO, ItemCodeDAO itemCodeDAO,
			InventTransactionDAO inventTransactionDAO) {
		this.companyTableDAO = companyTableDAO;
		this.m15LineDAO = m15LineDAO;
		this.m15TableDAO = m15TableDAO;
		this.itemCodeDAO = itemCodeDAO;
		this.counteragentInfoDAO = counteragentInfoDAO;
		this.inventTransactionDAO = inventTransactionDAO;
		this.unitOfMeasureDAO = unitOfMeasureDAO;
	}

	public String getM15Filename(String saleId, String host, String dataAreaId) {
		totalQty = 0;
		M15Table m15Table = m15TableDAO.find(saleId, dataAreaId);
		String filename = "m-15-" + m15Table.getM15Number() + ".xls";
		List<InventTransaction> transactions = inventTransactionDAO
				.getAllTransactionsBySourceDocument(
						String.format("%sâ„–%s", Global.m15Desc, saleId),
						dataAreaId);

		CompanyTable company = companyTableDAO.find(dataAreaId);
		CompanyTable reciever = companyTableDAO.find(m15Table.getM15Reciever());
		CounteragentInfo companyInfo = counteragentInfoDAO.find(dataAreaId,
				CounteragentType.Company.type(), dataAreaId);

		HSSFWorkbook hwb = null;
		try {
			hwb = createWorkbook("m15template.xls", host);
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Not found";
		}

		HSSFSheet sheet = hwb.getSheetAt(0);
		sheet.setAutobreaks(false);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		setCellValue(sheet, "BQ2", saleId);
		setCellValue(sheet, "M5", String.format("%s %s", BuisnessEntityType
				.getEnumById(company.getBuisnessEntityType()).rusId(), company
				.getCompanyName()));
		setCellValue(sheet, "EB5", companyInfo.getOkpo());
		setCellValue(sheet, "AH9", dateFormat.format(m15Table.getM15Date()));
		setCellValue(sheet, "G12", String.format("%s %s", BuisnessEntityType
				.getEnumById(reciever.getBuisnessEntityType()).rusId(),
				reciever.getCompanyName()));

		HSSFSheet sheetWithRows = hwb.getSheetAt(1);

		float headerHeight = 0;
		for (int i = 0; i <= 12; i++) {
			HSSFRow row = sheet.getRow(i);
			headerHeight += row.getHeightInPoints() / 0.75;
		}
		float footerHeight = 0;
		for (int i = 16; i <= 26; i++) {
			HSSFRow row = sheet.getRow(i);
			footerHeight += row.getHeightInPoints() / 0.75;
		}
		float tableHeaderHeight = 0;
		for (int i = 13; i <= 15; i++) {
			HSSFRow row = sheet.getRow(i);
			tableHeaderHeight += row.getHeightInPoints() / 0.75;
		}
		float totalHeightReport = footerHeight + headerHeight
				+ tableHeaderHeight + ROW_HEIGHT * transactions.size();

		int needPages = (int) Math.ceil(totalHeightReport
				/ MAX_LIST_LANDSCAPE_HEIGHT);

		int needShift = transactions.size() + 3 * (needPages - 1) + needPages;
		int[] rowsAtPage = new int[needPages];
		for (int i = 0; i < needPages; i++) {
			rowsAtPage[i] = transactions.size() / needPages;
		}
		rowsAtPage[0] += transactions.size() % needPages;

		int rowIndex = 16;
		sheet.shiftRows(rowIndex, sheet.getLastRowNum(), needShift);
		int rowsAlreadyAdded = 0;
		int pageNumber = 0;

		for (int i = 0; i < transactions.size(); i++) {
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

			createRow(hwb, sourceRow, sheet, rowIndex, transactions.get(i));
			rowsAlreadyAdded++;
			rowIndex++;
			if (rowsAtPage[pageNumber] == rowsAlreadyAdded) {
				rowsAlreadyAdded = 0;
				pageNumber++;
				if (pageNumber == needPages) {
					break;
				}
				sheet.setRowBreak(rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(13), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(14), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(15), sheet, rowIndex);
				rowIndex++;
			}
		}

		NumberToWordsRussian numberToWordsRussian = new NumberToWordsRussian();

		setCellValue(
				sheet,
				String.format("P%d", rowIndex + 3),
				numberToWordsRussian.toWords(new BigDecimal(transactions.size())));

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

	private void createRow(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex, InventTransaction transaction) {
		HSSFRow row = sheet.createRow(rowIndex);
		row.setHeightInPoints(ROW_HEIGHT);
		String UOMId = transaction.getInventTable().getUOMId();
		String UOMCode = unitOfMeasureDAO.find(UOMId,
				transaction.getDataAreaId()).getUOMCode();

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);
		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 1, 9);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$J$" + (rowIndex + 1)));

		cell = row.createCell(10);
		sourceCell = sourceRow.getCell(1);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 11, 23);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$K$" + (rowIndex + 1)
				+ ":$X$" + (rowIndex + 1)));

		cell = row.createCell(24);
		sourceCell = sourceRow.getCell(2);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(transaction.getInventTable().getName());
		fillEmptyCells(row, newCellStyle, 25, 37);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$Y$" + (rowIndex + 1)
				+ ":$AL$" + (rowIndex + 1)));

		cell = row.createCell(38);
		sourceCell = sourceRow.getCell(3);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		ItemCode code = itemCodeDAO.find(transaction.getItemId(),
				transaction.getDataAreaId());
		if (code == null)
			cell.setCellValue("");
		else
			cell.setCellValue(code.getCode());
		fillEmptyCells(row, newCellStyle, 39, 45);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AM$" + (rowIndex + 1)
				+ ":$AT$" + (rowIndex + 1)));

		cell = row.createCell(46);
		sourceCell = sourceRow.getCell(4);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(UOMCode);
		fillEmptyCells(row, newCellStyle, 47, 52);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AU$" + (rowIndex + 1)
				+ ":$BA$" + (rowIndex + 1)));

		cell = row.createCell(53);
		sourceCell = sourceRow.getCell(5);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(UOMId);
		fillEmptyCells(row, newCellStyle, 54, 64);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$BB$" + (rowIndex + 1)
				+ ":$BM$" + (rowIndex + 1)));

		double qty = Math.abs(transaction.getTransactonQty());

		cell = row.createCell(65);
		sourceCell = sourceRow.getCell(6);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(qty);
		fillEmptyCells(row, newCellStyle, 66, 73);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$BN$" + (rowIndex + 1)
				+ ":$BV$" + (rowIndex + 1)));
		totalQty += qty;

		cell = row.createCell(74);
		sourceCell = sourceRow.getCell(7);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(qty);
		fillEmptyCells(row, newCellStyle, 75, 80);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$BW$" + (rowIndex + 1)
				+ ":$CC$" + (rowIndex + 1)));

		cell = row.createCell(81);
		sourceCell = sourceRow.getCell(8);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 82, 88);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$CD$" + (rowIndex + 1)
				+ ":$CK$" + (rowIndex + 1)));

		cell = row.createCell(89);
		sourceCell = sourceRow.getCell(9);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 90, 98);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$CL$" + (rowIndex + 1)
				+ ":$CU$" + (rowIndex + 1)));

		cell = row.createCell(99);
		sourceCell = sourceRow.getCell(10);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 100, 106);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$CV$" + (rowIndex + 1)
				+ ":$DC$" + (rowIndex + 1)));

		cell = row.createCell(107);
		sourceCell = sourceRow.getCell(11);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 108, 115);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$DD$" + (rowIndex + 1)
				+ ":$DL$" + (rowIndex + 1)));

		cell = row.createCell(116);
		sourceCell = sourceRow.getCell(12);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 117, 122);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$DM$" + (rowIndex + 1)
				+ ":$DS$" + (rowIndex + 1)));

		cell = row.createCell(123);
		sourceCell = sourceRow.getCell(13);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 124, 131);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$DT$" + (rowIndex + 1)
				+ ":$EB$" + (rowIndex + 1)));

		cell = row.createCell(132);
		sourceCell = sourceRow.getCell(14);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(transaction.getTransNum());
		fillEmptyCells(row, newCellStyle, 133, 148);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$EC$" + (rowIndex + 1)
				+ ":$ES$" + (rowIndex + 1)));

	}
}
