package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
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
import skala.erp.bmp.server.dao.CompanyTableDAO;
import skala.erp.bmp.server.dao.CounteragentInfoDAO;
import skala.erp.bmp.server.dao.InventTransactionDAO;
import skala.erp.bmp.server.dao.ItemCodeDAO;
import skala.erp.bmp.server.dao.M4LineDAO;
import skala.erp.bmp.server.dao.M4TableDAO;
import skala.erp.bmp.server.dao.TaxTableDAO;
import skala.erp.bmp.server.dao.UnitOfMeasureDAO;
import skala.erp.bmp.shared.entity.CompanyTable;
import skala.erp.bmp.shared.entity.CounteragentInfo;
import skala.erp.bmp.shared.entity.InventTransaction;
import skala.erp.bmp.shared.entity.ItemCode;
import skala.erp.bmp.shared.entity.M4Table;

public class M4ExcelExporter extends ExcelExporter {

	ItemCodeDAO itemCodeDAO;

	CompanyTableDAO companyTableDAO;

	TaxTableDAO taxTableDAO;

	UnitOfMeasureDAO unitOfMeasureDAO;

	CompanyTable company;

	CounteragentInfoDAO counteragentInfoDAO;

	double totalQty;

	M4TableDAO m4TableDAO;
	M4LineDAO m4LineDAO;
	InventTransactionDAO inventTransactionDAO;

	public M4ExcelExporter(CompanyTableDAO companyTableDAO,
			TaxTableDAO taxTableDAO, UnitOfMeasureDAO unitOfMeasureDAO,
			CounteragentInfoDAO counteragentInfoDAO, ItemCodeDAO itemCodeDAO,
			M4TableDAO m4TableDAO, M4LineDAO m4LineDAO,
			InventTransactionDAO inventTransactionDAO) {
		this.companyTableDAO = companyTableDAO;
		this.itemCodeDAO = itemCodeDAO;
		this.counteragentInfoDAO = counteragentInfoDAO;
		this.taxTableDAO = taxTableDAO;
		this.unitOfMeasureDAO = unitOfMeasureDAO;
		this.m4LineDAO = m4LineDAO;
		this.inventTransactionDAO = inventTransactionDAO;
		this.m4TableDAO = m4TableDAO;
	}

	public String getM4ReportFilename(String postingId, String host,
			String dataAreaId) {
		totalQty = 0;
		String filename = "m-4-" + postingId + ".xls";
		company = companyTableDAO.find(dataAreaId);
		CounteragentInfo companyInfo = counteragentInfoDAO.find(dataAreaId,
				CounteragentType.Company.type(), dataAreaId);
		M4Table m4Table = m4TableDAO.find(postingId, dataAreaId);
		String companyString = String.format("%s %s", BuisnessEntityType
				.getEnumById(company.getBuisnessEntityType()).fullName(),
				company.getCompanyName());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String date = dateFormat.format(m4Table.getM4Date());
		String locationId = m4Table.getInventDim().getInventLocationId();
		CompanyTable vendor = companyTableDAO.find(dataAreaId);

		HSSFWorkbook hwb = null;
		try {
			hwb = createWorkbook("m4new.xls", host);
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Not found";
		}
		HSSFSheet sheet = hwb.getSheetAt(0);
		sheet.getPrintSetup().setFitWidth((short) 1);
		sheet.setAutobreaks(false);
		HSSFSheet sheetWithRows = hwb.getSheetAt(1);

		setCellValue(sheet, "BA5", postingId);
		setCellValue(sheet, "BV13", m4Table.getBaseDocument());
		setCellValue(sheet, "M8", companyString);
		setCellValue(sheet, "CT8", companyInfo.getOkpo());
		setCellValue(sheet, "M9", locationId);
		setCellValue(sheet, "A13", date);
		setCellValue(sheet, "Y13", vendor.getCompanyName());

		List<InventTransaction> transactions = inventTransactionDAO
				.getAllTransactionsBySourceDocument(
						String.format("%sâ„–%s", Global.m4Desc, postingId),
						dataAreaId);
		float headerHeight = 0;
		for (int i = 0; i <= 13; i++) {
			HSSFRow row = sheet.getRow(i);
			headerHeight += row.getHeightInPoints() / 0.75;
		}
		float footerHeight = 0;
		for (int i = 17; i <= 21; i++) {
			HSSFRow row = sheet.getRow(i);
			footerHeight += row.getHeightInPoints() / 0.75;
		}
		float tableHeaderHeight = 0;
		for (int i = 14; i <= 16; i++) {
			HSSFRow row = sheet.getRow(i);
			tableHeaderHeight += row.getHeightInPoints() / 0.75;
		}
		float totalHeightReport = footerHeight + headerHeight
				+ tableHeaderHeight + ROW_HEIGHT * transactions.size();

		int needPages = (int) Math.ceil(totalHeightReport
				/ MAX_LIST_PORTRAIT_HEIGTH);

		int needShift = transactions.size() + 3 * (needPages - 1);
		int[] rowsAtPage = new int[needPages];
		for (int i = 0; i < needPages; i++) {
			rowsAtPage[i] = transactions.size() / needPages;
		}
		rowsAtPage[0] += transactions.size() % needPages;

		int rowIndex = 17;
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
				copyRow(hwb, sheet.getRow(14), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(15), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(16), sheet, rowIndex);
				rowIndex++;
			}
		}
		setCellValue(sheet, String.format("BB%d", rowIndex + 1), totalQty);
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

	protected void createRow(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex, InventTransaction transaction) {
		HSSFRow row = sheet.createRow(rowIndex);

		String UOMId = transaction.getInventTable().getUOMId();

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);
		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(transaction.getInventTable().getName());
		fillEmptyCells(row, newCellStyle, 1, 16);

		cell = row.createCell(17);
		sourceCell = sourceRow.getCell(1);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		ItemCode code = itemCodeDAO.find(transaction.getItemId(),
				transaction.getDataAreaId());
		if (code == null)
			cell.setCellValue("");
		else
			cell.setCellValue(code.getCode());
		fillEmptyCells(row, newCellStyle, 18, 25);

		cell = row.createCell(26);
		sourceCell = sourceRow.getCell(2);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(unitOfMeasureDAO.find(UOMId,
				transaction.getDataAreaId()).getUOMCode());
		fillEmptyCells(row, newCellStyle, 27, 31);

		cell = row.createCell(32);
		sourceCell = sourceRow.getCell(3);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(UOMId);
		fillEmptyCells(row, newCellStyle, 33, 44);

		cell = row.createCell(45);
		sourceCell = sourceRow.getCell(4);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 46, 52);

		cell = row.createCell(53);
		sourceCell = sourceRow.getCell(5);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(transaction.getTransactonQty());
		fillEmptyCells(row, newCellStyle, 54, 60);
		totalQty += transaction.getTransactonQty();

		cell = row.createCell(61);
		sourceCell = sourceRow.getCell(6);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 62, 68);

		cell = row.createCell(69);
		sourceCell = sourceRow.getCell(7);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 70, 77);

		cell = row.createCell(78);
		sourceCell = sourceRow.getCell(8);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 79, 86);

		cell = row.createCell(87);
		sourceCell = sourceRow.getCell(9);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		fillEmptyCells(row, newCellStyle, 88, 94);

		cell = row.createCell(95);
		sourceCell = sourceRow.getCell(10);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 96, 101);

		cell = row.createCell(102);
		sourceCell = sourceRow.getCell(11);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(transaction.getTransNum());
		fillEmptyCells(row, newCellStyle, 103, 112);
		//
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$Q$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$R$" + (rowIndex + 1)
				+ ":$Z$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AA$" + (rowIndex + 1)
				+ ":$AF$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AG$" + (rowIndex + 1)
				+ ":$AS$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AT$" + (rowIndex + 1)
				+ ":$BA$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$BB$" + (rowIndex + 1)
				+ ":$BI$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$BJ$" + (rowIndex + 1)
				+ ":$BQ$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$BR$" + (rowIndex + 1)
				+ ":$BZ$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$CA$" + (rowIndex + 1)
				+ ":$CI$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$CJ$" + (rowIndex + 1)
				+ ":$CQ$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$CR$" + (rowIndex + 1)
				+ ":$CX$" + (rowIndex + 1)));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$CY$" + (rowIndex + 1)
				+ ":$DI$" + (rowIndex + 1)));
	}

}
