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

import com.ibm.icu.util.Calendar;

import skala.erp.bmp.baseenums.BuisnessEntityType;
import skala.erp.bmp.baseenums.CounteragentType;
import skala.erp.bmp.baseenums.EmplPosition;
import skala.erp.bmp.server.dao.CompanyTableDAO;
import skala.erp.bmp.server.dao.CounteragentInfoDAO;
import skala.erp.bmp.server.dao.CustContractObjectDAO;
import skala.erp.bmp.server.dao.CustTableDAO;
import skala.erp.bmp.server.dao.EmplPositionTableDAO;
import skala.erp.bmp.server.dao.EmplTableDAO;
import skala.erp.bmp.server.dao.InventPartyTableDAO;
import skala.erp.bmp.server.dao.ItemCodeDAO;
import skala.erp.bmp.server.dao.SalesLineDAO;
import skala.erp.bmp.server.dao.SalesTableDAO;
import skala.erp.bmp.server.dao.UnitOfMeasureDAO;
import skala.erp.bmp.shared.entity.CompanyTable;
import skala.erp.bmp.shared.entity.CounteragentInfo;
import skala.erp.bmp.shared.entity.CustTable;
import skala.erp.bmp.shared.entity.EmplPositionTable;
import skala.erp.bmp.shared.entity.ItemCode;
import skala.erp.bmp.shared.entity.SalesLine;
import skala.erp.bmp.shared.entity.SalesTable;

public class SchetFacturaExcellExporter extends ExcelExporter {
	ItemCodeDAO itemCodeDAO;

	SalesLineDAO salesLineDAO;

	SalesTableDAO salesTableDAO;

	InventPartyTableDAO inventPartyTableDAO;

	CompanyTableDAO companyTableDAO;

	CompanyTable company;

	CustTable customer;

	CounteragentInfoDAO counteragentInfoDAO;

	EmplPositionTableDAO emplPositionTableDAO;

	UnitOfMeasureDAO unitOfMeasureDAO;

	CustTableDAO custTableDAO;

	EmplTableDAO emplTableDAO;

	CustContractObjectDAO custContractObjectDAO;

	double totalPriceWithVat;

	double totalPriceWithoutVat;

	double totalVatAmount;

	double pageTotalPriceWithVat;

	double pageTotalPriceWithoutVat;

	double pageTotalVatAmount;

	public SchetFacturaExcellExporter(SalesLineDAO salesLineDAO,
			SalesTableDAO salesTableDAO,
			InventPartyTableDAO inventPartyTableDAO,
			CompanyTableDAO companyTableDAO, UnitOfMeasureDAO unitOfMeasureDAO,
			EmplPositionTableDAO emplPositionTableDAO,
			CounteragentInfoDAO counteragentInfoDAO, CustTableDAO custTableDAO,
			EmplTableDAO emplTableDAO,
			CustContractObjectDAO custContractObjectDAO, ItemCodeDAO itemCodeDAO) {
		this.inventPartyTableDAO = inventPartyTableDAO;
		this.emplTableDAO = emplTableDAO;
		this.itemCodeDAO = itemCodeDAO;
		this.counteragentInfoDAO = counteragentInfoDAO;
		this.salesLineDAO = salesLineDAO;
		this.salesTableDAO = salesTableDAO;
		this.unitOfMeasureDAO = unitOfMeasureDAO;
		this.companyTableDAO = companyTableDAO;
		this.emplPositionTableDAO = emplPositionTableDAO;
		this.custTableDAO = custTableDAO;
		this.custContractObjectDAO = custContractObjectDAO;
	}

	public String getSchetFacturaFilename(String saleId, String host,
			String dataAreaId) {

		totalPriceWithoutVat = pageTotalPriceWithoutVat = 0;
		totalPriceWithVat = pageTotalPriceWithVat = 0;
		totalVatAmount = pageTotalVatAmount = 0;

		company = companyTableDAO.find(dataAreaId);
		CounteragentInfo companyInfo = counteragentInfoDAO.find(dataAreaId,
				CounteragentType.Company.type(), dataAreaId);
		String companyString = String.format("%s %s", BuisnessEntityType
				.getEnumById(company.getBuisnessEntityType()).fullName(),
				company.getCompanyName());
		SalesTable salesTable = salesTableDAO.find(saleId, dataAreaId);
		String filename = "schet-factura-" + salesTable.getSchetFactNum()
				+ ".xls";
		List<SalesLine> salesLines = salesLineDAO.findAllBySaleId(saleId,
				dataAreaId);
		EmplPositionTable genDirector = emplPositionTableDAO
				.getEmplWithPositionByDate(
						EmplPosition.GenDirector.getIntValue(),
						salesTable.getSaleDate(), dataAreaId);
		EmplPositionTable genAccountant = emplPositionTableDAO
				.getEmplWithPositionByDate(
						EmplPosition.GenAccountant.getIntValue(),
						salesTable.getSaleDate(), dataAreaId);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String strDate = "";
		try {
			strDate = dateFormat.format(salesTable.getPostDate());
		} catch (NullPointerException exception) {

		}
		String[] splitedDate = strDate.split("\\.");
		if (splitedDate.length < 3) {
			strDate = "";
		} else {
			String dateMonth = splitedDate[0];
			switch (Calendar.getInstance().get(Calendar.MONTH)) {
			case 0:
				dateMonth += " января";
				break;
			case 1:
				dateMonth += " февраля";
				break;
			case 2:
				dateMonth += " марта";
				break;
			case 3:
				dateMonth += " апреля";
				break;
			case 4:
				dateMonth += " мая";
				break;
			case 5:
				dateMonth += " июня";
				break;
			case 6:
				dateMonth += " июля";
				break;
			case 7:
				dateMonth += " августа";
				break;
			case 8:
				dateMonth += " сентября";
				break;
			case 9:
				dateMonth += " октября";
				break;
			case 10:
				dateMonth += " ноября";
				break;
			case 11:
				dateMonth += " декабря";
				break;
			}
			strDate = dateMonth + " " + splitedDate[2] + " г.";
		}

		customer = custTableDAO.find(salesTable.getCustId(), dataAreaId);
		CounteragentInfo custInfo = counteragentInfoDAO.find(
				salesTable.getCustId(), CounteragentType.Customer.type(),
				dataAreaId);
		String custString = String.format("%s %s", BuisnessEntityType
				.getEnumById(customer.getBuisnessEntityType()).fullName(),
				customer.getName());
		HSSFWorkbook hwb = null;
		try {
			hwb = createWorkbook("factureTemplate.xls", host);
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Not found";
		}

		HSSFSheet sheet = hwb.getSheetAt(0);
		sheet.setAutobreaks(false);
		HSSFSheet sheetWithRows = hwb.getSheetAt(1);

		setCellValue(sheet, "F2", salesTable.getSchetFactNum());
		setCellValue(sheet, "I2", strDate);
		setCellValue(sheet, "J4", companyString);
		setCellValue(sheet, "J5", String.format("%s, %s, %s",
				companyInfo.getPostIndex(), companyInfo.getCity(),
				companyInfo.getAddress()));
		String companyInnKpp;
		if (companyInfo.getKpp() != null)
			companyInnKpp = companyInfo.getInn() + "/" + companyInfo.getKpp();
		else
			companyInnKpp = companyInfo.getInn() + "/";
		setCellValue(sheet, "J6", companyInnKpp);

		setCellValue(sheet, "J7", "Он же");
		String consigneeString = "";

		CustTable consignee = custTableDAO.find(salesTable.getConsId(),
				dataAreaId);
		CounteragentInfo consigneeInfo = counteragentInfoDAO.find(
				consignee.getAccountNum(), CounteragentType.Customer.type(),
				dataAreaId);

		consigneeString = String.format("%s %s, %s, %s, %s", BuisnessEntityType
				.getEnumById(consignee.getBuisnessEntityType()).fullName(),
				consignee.getName(), consigneeInfo.getRealPostIndex(),
				consigneeInfo.getRealCity(), consigneeInfo.getRealAddress());

		consigneeString = removeLinebreaksFromString(consigneeString);

		setCellValue(sheet, "J8", consigneeString);
		setCellValue(sheet, "J10", custString);
		setCellValue(
				sheet,
				"J11",
				String.format("%s, %s, %s", custInfo.getPostIndex(),
						custInfo.getCity(), custInfo.getAddress()));

		String custInnKpp;
		if (custInfo.getKpp() != null)
			custInnKpp = custInfo.getInn() + "/" + custInfo.getKpp();
		else
			custInnKpp = custInfo.getInn() + "/";
		setCellValue(sheet, "J12", custInnKpp);

		float headerHeight = 0;
		for (int i = 0; i <= 13; i++) {
			HSSFRow row = sheet.getRow(i);
			headerHeight += row.getHeightInPoints();
		}
		float footerHeight = 0;
		for (int i = 17; i <= 39; i++) {
			HSSFRow row = sheet.getRow(i);
			footerHeight += row.getHeightInPoints();
		}
		float tableHeaderHeight = 0;
		for (int i = 14; i <= 16; i++) {
			HSSFRow row = sheet.getRow(i);
			tableHeaderHeight += row.getHeightInPoints();
		}

		float totalHeightReport = footerHeight + headerHeight
				+ tableHeaderHeight + ROW_HEIGHT * (salesLines.size());
		int needPages = (int) Math.ceil(totalHeightReport
				/ MAX_LIST_LANDSCAPE_HEIGHT);
		int needShift = salesLines.size() + 3 * (needPages - 1) + needPages;
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
			HSSFRow sourceRow = sheetWithRows.getRow(0);
			createRow(hwb, sourceRow, sheet, rowIndex, salesLines.get(i));
			rowsAlreadyAdded++;
			rowIndex++;
			if (rowsAtPage[pageNumber] == rowsAlreadyAdded) {
				rowsAlreadyAdded = 0;
				pageNumber++;
				sourceRow = sheetWithRows.getRow(1);
				createSummaryRow(hwb, sourceRow, sheet, rowIndex);
				if (pageNumber == needPages) {
					rowIndex++;
					break;
				}
				sheet.setRowBreak(rowIndex);
				rowIndex++;
				pageTotalPriceWithoutVat = 0;
				pageTotalPriceWithVat = 0;
				pageTotalVatAmount = 0;
				copyRow(hwb, sheet.getRow(14), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(15), sheet, rowIndex);
				rowIndex++;
				copyRow(hwb, sheet.getRow(16), sheet, rowIndex);
				rowIndex++;
			}
		}
		setCellValue(sheet, String.format("P%d", rowIndex + 1),
				totalPriceWithoutVat);
		setCellValue(sheet, String.format("Y%d", rowIndex + 1), totalVatAmount);
		setCellValue(sheet, String.format("AB%d", rowIndex + 1),
				totalPriceWithVat);

		if (genDirector == null) {
			setCellValue(sheet, String.format("M%d", rowIndex + 2), "");
		} else
			setCellValue(sheet, String.format("M%d", rowIndex + 2),
					emplTableDAO.find(genDirector.getEmplId(), dataAreaId)
							.getName());
		if (genAccountant == null) {
			setCellValue(sheet, String.format("AE%d", rowIndex + 2), "");
		} else
			setCellValue(sheet, String.format("AE%d", rowIndex + 2),
					emplTableDAO.find(genAccountant.getEmplId(), dataAreaId)
							.getName());

		setCellValue(sheet, String.format("A%d", rowIndex + 3), needPages);

		setCellValue(sheet, String.format("A%d", rowIndex + 21), companyString
				+ ", " + companyInnKpp);
		setCellValue(sheet, String.format("R%d", rowIndex + 21), custString
				+ ", " + custInnKpp);

		hwb.removeSheetAt(1);
		hwb.setPrintArea(0, 0, 34, 0, 39 + needShift);

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
			HSSFSheet sheet, int rowIndex, SalesLine salesLine) {
		HSSFRow row = sheet.createRow(rowIndex);
		row.setHeightInPoints(ROW_HEIGHT);
		String UOMId = salesLine.getItem().getUOMId();
		String UOMCode = unitOfMeasureDAO
				.find(UOMId, salesLine.getDataAreaId()).getUOMCode();

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);
		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getLineNum());

		cell = row.createCell(1);
		sourceCell = sourceRow.getCell(1);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		ItemCode code = itemCodeDAO.find(salesLine.getItemId(),
				salesLine.getDataAreaId());
		if (code == null)
			cell.setCellValue("");
		else
			cell.setCellValue(code.getCode());

		cell = row.createCell(2);
		sourceCell = sourceRow.getCell(2);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getItem().getName());
		fillEmptyCells(row, newCellStyle, 3, 7);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$C$" + (rowIndex + 1)
				+ ":$H$" + (rowIndex + 1)));

		cell = row.createCell(8);
		sourceCell = sourceRow.getCell(3);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(UOMCode);
		fillEmptyCells(row, newCellStyle, 9, 9);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$I$" + (rowIndex + 1)
				+ ":$J$" + (rowIndex + 1)));

		cell = row.createCell(10);
		sourceCell = sourceRow.getCell(4);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(UOMId);
		fillEmptyCells(row, newCellStyle, 11, 11);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$K$" + (rowIndex + 1)
				+ ":$L$" + (rowIndex + 1)));

		cell = row.createCell(12);
		sourceCell = sourceRow.getCell(5);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getQty());
		fillEmptyCells(row, newCellStyle, 13, 13);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$M$" + (rowIndex + 1)
				+ ":$N$" + (rowIndex + 1)));

		cell = row.createCell(14);
		sourceCell = sourceRow.getCell(6);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getSalePrice());

		cell = row.createCell(15);
		sourceCell = sourceRow.getCell(7);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		fillEmptyCells(row, newCellStyle, 16, 18);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$P$" + (rowIndex + 1)
				+ ":$S$" + (rowIndex + 1)));
		double rubPriceWithoutWat = (salesLine.getAmount() - salesLine
				.getVatAmount());
		cell.setCellValue(rubPriceWithoutWat);
		totalPriceWithoutVat += rubPriceWithoutWat;
		pageTotalPriceWithoutVat += rubPriceWithoutWat;

		cell = row.createCell(19);
		sourceCell = sourceRow.getCell(8);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("без акциза");
		fillEmptyCells(row, newCellStyle, 20, 21);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$T$" + (rowIndex + 1)
				+ ":$V$" + (rowIndex + 1)));

		cell = row.createCell(22);
		sourceCell = sourceRow.getCell(9);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(salesLine.getVatValue() + "%");
		fillEmptyCells(row, newCellStyle, 23, 23);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$W$" + (rowIndex + 1)
				+ ":$X$" + (rowIndex + 1)));

		cell = row.createCell(24);
		sourceCell = sourceRow.getCell(10);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		fillEmptyCells(row, newCellStyle, 25, 26);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$Y$" + (rowIndex + 1)
				+ ":$AA$" + (rowIndex + 1)));

		double rubVatAmount = salesLine.getVatAmount();
		cell.setCellValue(rubVatAmount);
		pageTotalVatAmount += rubVatAmount;
		totalVatAmount += rubVatAmount;

		cell = row.createCell(27);
		sourceCell = sourceRow.getCell(11);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);

		fillEmptyCells(row, newCellStyle, 28, 28);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AB$" + (rowIndex + 1)
				+ ":$AC$" + (rowIndex + 1)));
		double rubAmount = salesLine.getAmount();
		cell.setCellValue(rubAmount);
		pageTotalPriceWithVat += rubAmount;
		totalPriceWithVat += rubAmount;

		cell = row.createCell(29);
		sourceCell = sourceRow.getCell(12);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("-");
		fillEmptyCells(row, newCellStyle, 30, 30);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AD$" + (rowIndex + 1)
				+ ":$AE$" + (rowIndex + 1)));

		cell = row.createCell(31);
		sourceCell = sourceRow.getCell(13);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("-");
		fillEmptyCells(row, newCellStyle, 32, 32);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AF$" + (rowIndex + 1)
				+ ":$AG$" + (rowIndex + 1)));

		cell = row.createCell(33);
		sourceCell = sourceRow.getCell(14);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("-");
		fillEmptyCells(row, newCellStyle, 34, 34);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AH$" + (rowIndex + 1)
				+ ":$AI$" + (rowIndex + 1)));
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
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 1, 1);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$B$" + (rowIndex + 1)));

		cell = row.createCell(2);
		sourceCell = sourceRow.getCell(1);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("Итого");
		fillEmptyCells(row, newCellStyle, 2, 14);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$C$" + (rowIndex + 1)
				+ ":$O$" + (rowIndex + 1)));

		cell = row.createCell(15);
		sourceCell = sourceRow.getCell(2);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(pageTotalPriceWithoutVat);
		fillEmptyCells(row, newCellStyle, 16, 18);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$P$" + (rowIndex + 1)
				+ ":$S$" + (rowIndex + 1)));

		cell = row.createCell(19);
		sourceCell = sourceRow.getCell(3);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("X");
		fillEmptyCells(row, newCellStyle, 20, 23);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$T$" + (rowIndex + 1)
				+ ":$X$" + (rowIndex + 1)));

		cell = row.createCell(24);
		sourceCell = sourceRow.getCell(4);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(pageTotalVatAmount);
		fillEmptyCells(row, newCellStyle, 25, 26);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$Y$" + (rowIndex + 1)
				+ ":$AA$" + (rowIndex + 1)));

		cell = row.createCell(27);
		sourceCell = sourceRow.getCell(5);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(pageTotalPriceWithVat);
		fillEmptyCells(row, newCellStyle, 28, 28);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AB$" + (rowIndex + 1)
				+ ":$AC$" + (rowIndex + 1)));

		cell = row.createCell(29);
		sourceCell = sourceRow.getCell(6);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 30, 34);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$AD$" + (rowIndex + 1)
				+ ":$AI$" + (rowIndex + 1)));
	}

}
