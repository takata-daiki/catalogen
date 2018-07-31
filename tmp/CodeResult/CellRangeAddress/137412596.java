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
import skala.erp.bmp.baseenums.EmplPosition;
import skala.erp.bmp.client.util.NumberToWordsRussian;
import skala.erp.bmp.server.dao.BankInfoDAO;
import skala.erp.bmp.server.dao.CompanyTableDAO;
import skala.erp.bmp.server.dao.CounteragentInfoDAO;
import skala.erp.bmp.server.dao.CustTableDAO;
import skala.erp.bmp.server.dao.EmplPositionTableDAO;
import skala.erp.bmp.server.dao.EmplTableDAO;
import skala.erp.bmp.server.dao.InventPartyTableDAO;
import skala.erp.bmp.server.dao.SalesLineDAO;
import skala.erp.bmp.server.dao.SalesTableDAO;
import skala.erp.bmp.server.dao.UnitOfMeasureDAO;
import skala.erp.bmp.shared.entity.BankInfo;
import skala.erp.bmp.shared.entity.CompanyTable;
import skala.erp.bmp.shared.entity.CounteragentInfo;
import skala.erp.bmp.shared.entity.CustTable;
import skala.erp.bmp.shared.entity.EmplPositionTable;
import skala.erp.bmp.shared.entity.SalesLine;
import skala.erp.bmp.shared.entity.SalesTable;

public class TTNExcelExporter extends ExcelExporter {

	SalesLineDAO salesLineDAO;

	SalesTableDAO salesTableDAO;

	InventPartyTableDAO inventPartyTableDAO;

	CustTableDAO custTableDAO;

	CompanyTableDAO companyTableDAO;

	CompanyTable company;

	CustTable customer;

	CounteragentInfoDAO counteragentInfoDAO;

	UnitOfMeasureDAO unitOfMeasureDAO;

	EmplPositionTableDAO emplPositionTableDAO;

	EmplTableDAO emplTableDAO;

	BankInfoDAO bankInfoDAO;

	double totalQty;

	double totalPriceWithVat;

	double totalPriceWithoutVat;

	double totalVatAmount;

	double pageTotalQty;

	double pageTotalPriceWithVat;

	double pageTotalPriceWithoutVat;

	double pageTotalVatAmount;

	public TTNExcelExporter(SalesLineDAO salesLineDAO,
			SalesTableDAO salesTableDAO,
			InventPartyTableDAO inventPartyTableDAO,
			CompanyTableDAO companyTableDAO, UnitOfMeasureDAO unitOfMeasureDAO,
			EmplPositionTableDAO emplPositionTableDAO,
			CounteragentInfoDAO counteragentInfoDAO, CustTableDAO custTableDAO,
			EmplTableDAO emplTableDAO, BankInfoDAO bankInfoDAO) {
		this.inventPartyTableDAO = inventPartyTableDAO;
		this.bankInfoDAO = bankInfoDAO;
		this.emplTableDAO = emplTableDAO;
		this.counteragentInfoDAO = counteragentInfoDAO;
		this.salesLineDAO = salesLineDAO;
		this.salesTableDAO = salesTableDAO;
		this.unitOfMeasureDAO = unitOfMeasureDAO;
		this.emplPositionTableDAO = emplPositionTableDAO;
		this.companyTableDAO = companyTableDAO;
		this.custTableDAO = custTableDAO;
	}

	public String getTTNFileName(String saleId, String host, String dataAreaId) {

		totalPriceWithoutVat = pageTotalPriceWithoutVat = 0;
		totalPriceWithVat = pageTotalPriceWithVat = 0;
		totalQty = pageTotalQty = 0;
		totalVatAmount = pageTotalVatAmount = 0;
		company = companyTableDAO.find(dataAreaId);
		CounteragentInfo companyInfo = counteragentInfoDAO.find(dataAreaId,
				CounteragentType.Company.type(), dataAreaId);
		BankInfo companyBank = bankInfoDAO.find(companyInfo.getBik());
		if (companyBank == null)
			companyBank = new BankInfo();

		SalesTable salesTable = salesTableDAO.find(saleId, dataAreaId);
		String filename = "TTN-" + salesTable.getSchetFactNum() + ".xls";
		List<SalesLine> salesLines = salesLineDAO.findAllBySaleId(saleId,
				dataAreaId);
		EmplPositionTable genAccountant = emplPositionTableDAO
				.getEmplWithPositionByDate(
						EmplPosition.GenAccountant.getIntValue(),
						salesTable.getSaleDate(), dataAreaId);
		String companyString = String
				.format("%s \"%s\", ИНН %s, %s, %s, %s, тел.: %s, БИК %s, р/с %s, к/с %s",
						BuisnessEntityType.getEnumById(
								company.getBuisnessEntityType()).fullName(),
						company.getCompanyName(), companyInfo.getInn(),
						companyInfo.getPostIndex(), companyInfo.getCity(),
						companyInfo.getAddress(), companyInfo.getPhone(),
						companyBank.getBankName(), companyInfo.getBik(),
						companyInfo.getAccount(), companyBank.getCorrAccount());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String date = dateFormat.format(salesTable.getSaleDate());
		String[] splitedDate = date.split("\\.");
		customer = custTableDAO.find(salesTable.getCustId(), dataAreaId);
		CounteragentInfo custInfo = counteragentInfoDAO.find(
				customer.getAccountNum(), CounteragentType.Customer.type(),
				dataAreaId);
		BankInfo custBank = bankInfoDAO.find(custInfo.getBik());
		if (custBank == null)
			custBank = new BankInfo();
		String custString = String
				.format("%s \"%s\", ИНН %s, %s, %s, %s, тел.: %s, %s, БИК %s, р/с %s, к/с %s",
						BuisnessEntityType.getEnumById(
								customer.getBuisnessEntityType()).fullName(),
						customer.getName(), custInfo.getInn(),
						custInfo.getPostIndex(), custInfo.getCity(),
						custInfo.getAddress(), custInfo.getPhone(),
						custBank.getBankName(), custInfo.getBik(),
						custInfo.getAccount(), custBank.getCorrAccount());
		HSSFWorkbook hwb = null;
		try {
			hwb = createWorkbook("ttnTemplate.xls", host);
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Not found";
		}

		HSSFSheet sheet = hwb.getSheetAt(0);
		sheet.setAutobreaks(false);
		HSSFSheet sheetWithRows = hwb.getSheetAt(2);

		setCellValue(sheet, "AL6", salesTable.getSchetFactNum());
		setCellValue(sheet, "AL7", splitedDate[0]);
		setCellValue(sheet, "AN7", splitedDate[1]);
		setCellValue(sheet, "AP7", splitedDate[2]);
		setCellValue(sheet, "G9", companyString);
		setCellValue(sheet, "AL9", companyInfo.getOkpo());
		setCellValue(sheet, "G11", custString);
		setCellValue(sheet, "AL10", custInfo.getOkpo());
		setCellValue(sheet, "F13", custString);
		setCellValue(sheet, "AL12", custInfo.getOkpo());

		float headerHeight = 0;
		for (int i = 0; i <= 14; i++) {
			HSSFRow row = sheet.getRow(i);

			headerHeight += row.getHeightInPoints();
		}
		float footerHeight = 0;
		for (int i = 17; i <= 34; i++) {
			HSSFRow row = sheet.getRow(i);

			footerHeight += row.getHeightInPoints();
		}
		float tableHeaderHeight = 0;
		for (int i = 15; i <= 16; i++) {
			HSSFRow row = sheet.getRow(i);

			tableHeaderHeight += row.getHeightInPoints();
		}

		float totalHeightReport = footerHeight + headerHeight
				+ tableHeaderHeight + ROW_HEIGHT * (1 + salesLines.size());
		int needPages = (int) Math.ceil(totalHeightReport / MAX_LIST_LANDSCAPE_HEIGHT);
		int needShift = salesLines.size() + 2 * (needPages - 1) + needPages;
		int[] rowsAtPage = new int[needPages];
		for (int i = 0; i < needPages; i++) {
			rowsAtPage[i] = salesLines.size() / needPages;
		}
		rowsAtPage[0] += salesLines.size() % needPages;
		int rowIndex = 17;
		sheet.shiftRows(rowIndex, sheet.getLastRowNum(), needShift);
		int rowsAlreadyAdded = 0;
		int pageNumber = 0;
		for (int i = 0; i < salesLines.size(); i++) {
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

			createRow(hwb, sourceRow, sheet, rowIndex, salesLines.get(i));
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
				pageTotalPriceWithoutVat = 0;
				pageTotalPriceWithVat = 0;
				pageTotalQty = 0;
				pageTotalVatAmount = 0;
				copyRow(hwb, sheet.getRow(15), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(16), sheet, rowIndex);
				rowIndex++;

			}
		}
		NumberToWordsRussian numberToWordsRussian = new NumberToWordsRussian();
		setCellValue(sheet, String.format("K%d", rowIndex + 1), totalQty);
		setCellValue(sheet, String.format("AK%d", rowIndex + 1),
				totalPriceWithVat);
		setCellValue(sheet, String.format("J%d", rowIndex + 2), needPages);
		setCellValue(sheet, String.format("E%d", rowIndex + 3),
				numberToWordsRussian.toWords(new BigDecimal(salesLines.size())));
		setCellValue(sheet, String.format("F%d", rowIndex + 5),
				numberToWordsRussian.toWords(new BigDecimal(salesLines.size())));
		setCellValue(sheet, String.format("AK%d", rowIndex + 6), totalVatAmount);
		setCellValue(sheet, String.format("AK%d", rowIndex + 7),
				totalPriceWithVat);
		setCellValue(sheet, String.format("G%d", rowIndex + 11),
				numberToWordsRussian.toWords(new BigDecimal(totalPriceWithVat)));

		String stringTotalCost = String.valueOf(totalPriceWithVat);
		String[] splitedTotalCost = stringTotalCost.split("\\.");
		if (splitedTotalCost[1].equals("0"))
			setCellValue(sheet, String.format("S%d", rowIndex + 12), "00");
		else
			setCellValue(sheet, String.format("S%d", rowIndex + 12),
					Integer.valueOf(splitedTotalCost[1]));
		if (genAccountant == null) {
			setCellValue(sheet, String.format("T%d", rowIndex + 14), "");
		} else
			setCellValue(sheet, String.format("T%d", rowIndex + 14),
					emplTableDAO.find(genAccountant.getEmplId(), dataAreaId)
							.getName());
		hwb.setPrintArea(0, 0, 43, 0, 34 + needShift);

		// Второй лист
		HSSFSheet secondSheet = hwb.getSheetAt(1);

		setCellValue(secondSheet, "F4", companyString);
		setCellValue(secondSheet, "I7", custString);
		setCellValue(secondSheet, "G14", companyInfo.getAddress() + " "
				+ companyInfo.getPhone());
		setCellValue(secondSheet, "AH14", custInfo.getAddress() + " "
				+ custInfo.getPhone());

		hwb.removeSheetAt(2);

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
		row.setHeightInPoints(ROW_HEIGHT);

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);
		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("Итого");
		fillEmptyCells(row, newCellStyle, 1, 9);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$J$" + (rowIndex + 1)));

		cell = row.createCell(10);
		sourceCell = sourceRow.getCell(3);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(pageTotalQty);
		fillEmptyCells(row, newCellStyle, 11, 12);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$K$" + (rowIndex + 1)
				+ ":$M$" + (rowIndex + 1)));

		cell = row.createCell(13);
		sourceCell = sourceRow.getCell(4);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 14, 16);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$N$" + (rowIndex + 1)
				+ ":$Q$" + (rowIndex + 1)));

		cell = row.createCell(17);
		sourceCell = sourceRow.getCell(5);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 18, 24);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$R$" + (rowIndex + 1)
				+ ":$Y$" + (rowIndex + 1)));

		cell = row.createCell(25);
		sourceCell = sourceRow.getCell(6);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 26, 27);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$Z$" + (rowIndex + 1)
				+ ":$AB$" + (rowIndex + 1)));

		cell = row.createCell(28);
		sourceCell = sourceRow.getCell(7);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 29, 30);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AC$" + (rowIndex + 1)
				+ ":$AE$" + (rowIndex + 1)));

		cell = row.createCell(31);
		sourceCell = sourceRow.getCell(8);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("X");
		fillEmptyCells(row, newCellStyle, 32, 32);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AF$" + (rowIndex + 1)
				+ ":$AG$" + (rowIndex + 1)));

		cell = row.createCell(33);
		sourceCell = sourceRow.getCell(9);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 34, 35);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AH$" + (rowIndex + 1)
				+ ":$AJ$" + (rowIndex + 1)));

		cell = row.createCell(36);
		sourceCell = sourceRow.getCell(10);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(pageTotalPriceWithVat);
		fillEmptyCells(row, newCellStyle, 37, 38);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AK$" + (rowIndex + 1)
				+ ":$AM$" + (rowIndex + 1)));

		cell = row.createCell(39);
		sourceCell = sourceRow.getCell(11);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 40, 43);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AN$" + (rowIndex + 1)
				+ ":$AR$" + (rowIndex + 1)));
	}

	private void createRow(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex, SalesLine salesLine) {
		HSSFRow row = sheet.createRow(rowIndex);
		row.setHeightInPoints(ROW_HEIGHT);

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);
		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 1, 2);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$C$" + (rowIndex + 1)));

		cell = row.createCell(3);
		sourceCell = sourceRow.getCell(1);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 4, 6);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$D$" + (rowIndex + 1)
				+ ":$G$" + (rowIndex + 1)));

		cell = row.createCell(7);
		sourceCell = sourceRow.getCell(2);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getItemId());
		fillEmptyCells(row, newCellStyle, 8, 9);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$H$" + (rowIndex + 1)
				+ ":$J$" + (rowIndex + 1)));

		cell = row.createCell(10);
		sourceCell = sourceRow.getCell(3);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getQty());
		fillEmptyCells(row, newCellStyle, 11, 12);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$K$" + (rowIndex + 1)
				+ ":$M$" + (rowIndex + 1)));
		totalQty += salesLine.getQty();
		pageTotalQty += salesLine.getQty();
		totalVatAmount += salesLine.getVatAmount();

		cell = row.createCell(13);
		sourceCell = sourceRow.getCell(4);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getSalePrice());
		fillEmptyCells(row, newCellStyle, 14, 16);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$N$" + (rowIndex + 1)
				+ ":$Q$" + (rowIndex + 1)));

		cell = row.createCell(17);
		sourceCell = sourceRow.getCell(5);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getItem().getName());
		fillEmptyCells(row, newCellStyle, 18, 24);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$R$" + (rowIndex + 1)
				+ ":$Y$" + (rowIndex + 1)));

		cell = row.createCell(25);
		sourceCell = sourceRow.getCell(6);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getItem().getUOMId());
		fillEmptyCells(row, newCellStyle, 26, 27);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$Z$" + (rowIndex + 1)
				+ ":$AB$" + (rowIndex + 1)));

		cell = row.createCell(28);
		sourceCell = sourceRow.getCell(7);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 29, 30);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AC$" + (rowIndex + 1)
				+ ":$AE$" + (rowIndex + 1)));

		cell = row.createCell(31);
		sourceCell = sourceRow.getCell(8);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 32, 32);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AF$" + (rowIndex + 1)
				+ ":$AG$" + (rowIndex + 1)));

		cell = row.createCell(33);
		sourceCell = sourceRow.getCell(9);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 34, 35);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AH$" + (rowIndex + 1)
				+ ":$AJ$" + (rowIndex + 1)));

		cell = row.createCell(36);
		sourceCell = sourceRow.getCell(10);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getAmount());
		fillEmptyCells(row, newCellStyle, 37, 38);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AK$" + (rowIndex + 1)
				+ ":$AM$" + (rowIndex + 1)));
		pageTotalPriceWithVat += salesLine.getAmount();
		totalPriceWithVat += salesLine.getAmount();

		cell = row.createCell(39);
		sourceCell = sourceRow.getCell(11);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 40, 43);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AN$" + (rowIndex + 1)
				+ ":$AR$" + (rowIndex + 1)));
	}

}
