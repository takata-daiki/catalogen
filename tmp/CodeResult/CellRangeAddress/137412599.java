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
import skala.erp.bmp.client.util.NumberToWordsRussian;
import skala.erp.bmp.server.dao.CompanyTableDAO;
import skala.erp.bmp.server.dao.CounteragentInfoDAO;
import skala.erp.bmp.server.dao.ItemCodeDAO;
import skala.erp.bmp.server.dao.MX18LineDAO;
import skala.erp.bmp.server.dao.MX18TableDAO;
import skala.erp.bmp.server.dao.UnitOfMeasureDAO;
import skala.erp.bmp.shared.entity.CompanyTable;
import skala.erp.bmp.shared.entity.CounteragentInfo;
import skala.erp.bmp.shared.entity.ItemCode;
import skala.erp.bmp.shared.entity.MX18Line;
import skala.erp.bmp.shared.entity.MX18Table;

public class MX18ForTransferExcellExporter extends ExcelExporter {

	ItemCodeDAO itemCodeDAO;

	CompanyTableDAO companyTableDAO;

	CounteragentInfoDAO counteragentInfoDAO;

	UnitOfMeasureDAO unitOfMeasureDAO;

	MX18TableDAO mx18TableDAO;

	MX18LineDAO mx18LineDAO;

	double totalQty;

	double pageTotalQty;

	public MX18ForTransferExcellExporter(ItemCodeDAO itemCodeDAO,
			MX18TableDAO mx18TableDAO, MX18LineDAO mx18LineDAO,
			CompanyTableDAO companyTableDAO,
			CounteragentInfoDAO counteragentInfoDAO,
			UnitOfMeasureDAO unitOfMeasureDAO) {
		this.companyTableDAO = companyTableDAO;
		this.counteragentInfoDAO = counteragentInfoDAO;
		this.itemCodeDAO = itemCodeDAO;
		this.mx18LineDAO = mx18LineDAO;
		this.mx18TableDAO = mx18TableDAO;
		this.unitOfMeasureDAO = unitOfMeasureDAO;
	}

	public String getMX18FileName(String saleId, String host, String dataAreaId) {
		totalQty = 0;
		CompanyTable company = companyTableDAO.find(dataAreaId);

		CounteragentInfo companyInfo = counteragentInfoDAO.find(dataAreaId,
				CounteragentType.Company.type(), dataAreaId);
		MX18Table mx18Table = mx18TableDAO.find(saleId, dataAreaId);
		CompanyTable reciever = companyTableDAO.find(mx18Table
				.getMx18Reciever());
		String companyString = String.format("%s %s, %s, %s, %s тел.: %s",
				BuisnessEntityType.getEnumById(company.getBuisnessEntityType())
						.fullName(), company.getCompanyName(), companyInfo
						.getPostIndex(), companyInfo.getCity(), companyInfo
						.getAddress(), companyInfo.getPhone());
		companyString = removeLinebreaksFromString(companyString);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String date = dateFormat.format(mx18Table.getMx18Date());
		String filename = "mx-18-" + saleId + ".xls";

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
		setCellValue(sheet, "L11", saleId);
		setCellValue(sheet, "O11", date);
		setCellValue(sheet, "A16", "Основное");
		setCellValue(sheet, "E16", String.format("%s %s", BuisnessEntityType
				.getEnumById(reciever.getBuisnessEntityType()).rusId(),
				reciever.getCompanyName()));
		List<MX18Line> lines = mx18LineDAO.getAllByNumber(saleId, dataAreaId);

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
				+ tableHeaderHeight + ROW_HEIGHT * lines.size();

		int needPages = (int) Math.ceil(totalHeightReport
				/ MAX_LIST_LANDSCAPE_HEIGHT);

		int needShift = lines.size() + 5 * (needPages - 1) + needPages;
		int[] rowsAtPage = new int[needPages];
		for (int i = 0; i < needPages; i++) {
			rowsAtPage[i] = lines.size() / needPages;
		}
		rowsAtPage[0] += lines.size() % needPages;

		int rowIndex = 22;
		sheet.shiftRows(rowIndex, sheet.getLastRowNum(), needShift);
		int rowsAlreadyAdded = 0;
		int pageNumber = 0;
		for (int i = 0; i < lines.size(); i++) {
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

			createRow(hwb, sourceRow, sheet, rowIndex, lines.get(i));
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

		NumberToWordsRussian numberToWordsRussian = new NumberToWordsRussian();

		setCellValue(sheet, String.format("G%d", rowIndex + 3),
				numberToWordsRussian.toWords(new BigDecimal(lines.size())));

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
		cell.setCellValue("");
	}

	private void createRow(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex, MX18Line line) {
		HSSFRow row = sheet.createRow(rowIndex);

		String UOMId = line.getItem().getUOMId();

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);

		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(line.getItem().getName());
		fillEmptyCells(row, newCellStyle, 1, 1);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$B$" + (rowIndex + 1)));

		cell = row.createCell(2);
		sourceCell = sourceRow.getCell(2);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		ItemCode code = itemCodeDAO
				.find(line.getItemId(), line.getDataAreaId());
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
		cell.setCellValue(unitOfMeasureDAO.find(UOMId, line.getDataAreaId())
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
		cell.setCellValue(line.getQty());
		fillEmptyCells(row, newCellStyle, 20, 20);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$T$" + (rowIndex + 1)
				+ ":$U$" + (rowIndex + 1)));
		pageTotalQty += line.getQty();
		totalQty += line.getQty();

		cell = row.createCell(21);
		sourceCell = sourceRow.getCell(21);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 22, 23);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$V$" + (rowIndex + 1)
				+ ":$X$" + (rowIndex + 1)));

		cell = row.createCell(24);
		sourceCell = sourceRow.getCell(24);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
	}

}
