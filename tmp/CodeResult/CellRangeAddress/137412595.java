package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import skala.erp.bmp.baseenums.BuisnessEntityType;
import skala.erp.bmp.client.util.Global;
import skala.erp.bmp.server.dao.CompanyTableDAO;
import skala.erp.bmp.server.dao.CustPaymentDAO;
import skala.erp.bmp.server.dao.CustTableDAO;
import skala.erp.bmp.server.dao.CustTransDAO;
import skala.erp.bmp.server.dao.SalesLineDAO;
import skala.erp.bmp.server.dao.SalesTableDAO;
import skala.erp.bmp.shared.entity.CompanyTable;
import skala.erp.bmp.shared.entity.CustPayment;
import skala.erp.bmp.shared.entity.CustTable;
import skala.erp.bmp.shared.entity.SalesTable;

public class SverkaExcellExporter extends ExcelExporter {

	CompanyTableDAO companyTableDAO;

	CustTableDAO custTableDAO;

	SalesTableDAO salesTableDAO;

	SalesLineDAO salesLineDAO;

	CustPaymentDAO custPaymentDAO;

	CustTransDAO custTransDAO;

	double ownDebtTotal;
	double ownCreditTotal;

	SimpleDateFormat dateFormat;

	private double totalSaldo;

	public SverkaExcellExporter(CompanyTableDAO companyTableDAO,
			CustTableDAO custTableDAO, SalesTableDAO salesTableDAO,
			SalesLineDAO salesLineDAO, CustPaymentDAO custPaymentDAO,
			CustTransDAO custTransDAO) {
		this.companyTableDAO = companyTableDAO;
		this.custTableDAO = custTableDAO;
		this.custPaymentDAO = custPaymentDAO;
		this.custTransDAO = custTransDAO;
		this.salesLineDAO = salesLineDAO;
		this.salesTableDAO = salesTableDAO;
	}

	public String getReportFilename(String custId, String dataAreaId,
			Date fromDate, Date toDate, String host) {
		ownDebtTotal = 0;
		ownCreditTotal = 0;

		totalSaldo = 0;
		dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		CustTable custTable = custTableDAO.find(custId, dataAreaId);
		String result = "akt-sverki-" + "(" + dateFormat.format(fromDate) + "-"
				+ dateFormat.format(toDate) + ")-"
				+ Global.translitString(custTable.getShortName()) + ".xls";
		CompanyTable companyTable = companyTableDAO.find(dataAreaId);
		HSSFWorkbook hwb = null;
		try {
			hwb = createWorkbook("aktSverkiTemplate.xls", host);
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Not found";
		}
		String custString = BuisnessEntityType.getEnumById(
				custTable.getBuisnessEntityType()).rusId()
				+ " " + custTable.getName();
		String companyString = BuisnessEntityType.getEnumById(
				companyTable.getBuisnessEntityType()).rusId()
				+ " " + companyTable.getCompanyName();
		HSSFSheet sheet = hwb.getSheetAt(0);
		HSSFSheet sheetWithRows = hwb.getSheetAt(1);

		setCellValue(sheet, "M4", dateFormat.format(toDate));
		setCellValue(sheet, "I5", companyString);
		setCellValue(sheet, "I6", custString);
		setCellValue(sheet, "I8", companyString);
		setCellValue(sheet, "G9", custString);
		setCellValue(sheet, "D11", companyString + ", руб.");
		setCellValue(sheet, "M11", custString + ", руб.");

		List<SalesTable> sales = salesTableDAO
				.getAllPostedSalesForClientInDateRange(custId, fromDate,
						toDate, dataAreaId);
		List<CustPayment> payments = custPaymentDAO
				.getAllPostedByCustIdInRange(custId, fromDate, toDate,
						dataAreaId);

		setCellValue(sheet, "A13", "Сальдо на " + dateFormat.format(fromDate));
		int rowIndex = 13;
		totalSaldo = custTransDAO.getCustSaldoAtDate(custId, fromDate,
				dataAreaId);
		if (totalSaldo >= 0) {
			setCellValue(sheet, "G13", totalSaldo);
		} else {
			setCellValue(sheet, "I13", -totalSaldo);
		}

		sheet.shiftRows(rowIndex, sheet.getLastRowNum(), sales.size()
				+ payments.size());

		int saleIndex = 0;
		int payIndex = 0;

		while (saleIndex < sales.size() || payIndex < payments.size()) {
			HSSFRow sourceRow = sheetWithRows.getRow(0);
			if (saleIndex >= sales.size()) {
				CustPayment payment = payments.get(payIndex);
				createRowForPayment(hwb, sourceRow, sheet, rowIndex, payment);
				payIndex++;
				rowIndex++;
			} else if (payIndex >= payments.size()) {
				SalesTable sale = sales.get(saleIndex);
				createRowForSale(hwb, sourceRow, sheet, rowIndex, sale);
				saleIndex++;
				rowIndex++;
			} else {
				SalesTable sale = sales.get(saleIndex);
				CustPayment payment = payments.get(payIndex);
				if (sale.getSaleDate().before(payment.getPayDate())) {
					createRowForSale(hwb, sourceRow, sheet, rowIndex, sale);
					saleIndex++;
					rowIndex++;
				} else if (sale.getSaleDate().after(payment.getPayDate())) {
					createRowForPayment(hwb, sourceRow, sheet, rowIndex,
							payment);
					payIndex++;
					rowIndex++;
				} else {
					createRowForSale(hwb, sourceRow, sheet, rowIndex, sale);
					saleIndex++;
					rowIndex++;
					createRowForPayment(hwb, sourceRow, sheet, rowIndex,
							payment);
					payIndex++;
					rowIndex++;
				}
			}
		}

		setCellValue(sheet, String.format("G%d", rowIndex + 1), ownDebtTotal);
		setCellValue(sheet, String.format("I%d", rowIndex + 1), ownCreditTotal);
		setCellValue(sheet, String.format("A%d", rowIndex + 2), "Сальдо на "
				+ dateFormat.format(toDate));
		if (totalSaldo >= 0) {
			setCellValue(sheet, String.format("G%d", rowIndex + 2), totalSaldo);
		} else {
			setCellValue(sheet, String.format("I%d", rowIndex + 2), -totalSaldo);
		}

		setCellValue(sheet, String.format("C%d", rowIndex + 4), companyString);
		setCellValue(sheet, String.format("B%d", rowIndex + 5),
				dateFormat.format(toDate));
		if (totalSaldo >= 0) {
			setCellValue(
					sheet,
					String.format("G%d", rowIndex + 5),
					BuisnessEntityType.getEnumById(
							companyTable.getBuisnessEntityType()).rusId());
			setCellValue(
					sheet,
					String.format("A%d", rowIndex + 6),
					String.format("\"%s\" %.2f руб.",
							companyTable.getCompanyName(), totalSaldo));
		} else {
			setCellValue(
					sheet,
					String.format("G%d", rowIndex + 5),
					BuisnessEntityType.getEnumById(
							custTable.getBuisnessEntityType()).rusId());
			setCellValue(sheet, String.format("A%d", rowIndex + 6),
					String.format("\"%s\" %.2f руб.", custTable.getName(),
							-totalSaldo));
		}

		hwb.removeSheetAt(1);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(result);
			hwb.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public void createRowForSale(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex, SalesTable salesTable) {
		double debt = salesLineDAO.getTotalPriceOfSale(salesTable.getSaleId(),
				salesTable.getDataAreaId());

		totalSaldo += debt;

		String document = "Продажа №" + salesTable.getSaleId();
		HSSFRow row = sheet.createRow(rowIndex);

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);

		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(dateFormat.format(salesTable.getSaleDate()));
		fillEmptyCells(row, newCellStyle, 1, 1);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$B$" + (rowIndex + 1)));

		cell = row.createCell(2);
		sourceCell = sourceRow.getCell(1);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(document);
		fillEmptyCells(row, newCellStyle, 3, 5);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$C$" + (rowIndex + 1)
				+ ":$F$" + (rowIndex + 1)));

		cell = row.createCell(6);
		sourceCell = sourceRow.getCell(2);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(debt);
		fillEmptyCells(row, newCellStyle, 7, 7);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$G$" + (rowIndex + 1)
				+ ":$H$" + (rowIndex + 1)));
		ownDebtTotal += debt;

		cell = row.createCell(8);
		sourceCell = sourceRow.getCell(3);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 9, 10);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$I$" + (rowIndex + 1)
				+ ":$K$" + (rowIndex + 1)));

		cell = row.createCell(11);
		sourceCell = sourceRow.getCell(4);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(12);
		sourceCell = sourceRow.getCell(5);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 13, 15);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$M$" + (rowIndex + 1)
				+ ":$P$" + (rowIndex + 1)));

		cell = row.createCell(16);
		sourceCell = sourceRow.getCell(6);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 17, 17);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$Q$" + (rowIndex + 1)
				+ ":$R$" + (rowIndex + 1)));

		cell = row.createCell(18);
		sourceCell = sourceRow.getCell(7);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 19, 19);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$S$" + (rowIndex + 1)
				+ ":$T$" + (rowIndex + 1)));
	}

	public void createRowForPayment(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex, CustPayment custPayment) {

		totalSaldo -= custPayment.getAmount();

		String document = "Оплата №" + custPayment.getPayId();
		HSSFRow row = sheet.createRow(rowIndex);

		Cell cell = row.createCell(0);
		Cell sourceCell = sourceRow.getCell(0);

		CellStyle newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(dateFormat.format(custPayment.getPayDate()));
		fillEmptyCells(row, newCellStyle, 1, 1);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$B$" + (rowIndex + 1)));

		cell = row.createCell(2);
		sourceCell = sourceRow.getCell(1);
		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(document);
		fillEmptyCells(row, newCellStyle, 3, 5);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$C$" + (rowIndex + 1)
				+ ":$F$" + (rowIndex + 1)));

		cell = row.createCell(6);
		sourceCell = sourceRow.getCell(2);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 7, 7);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$G$" + (rowIndex + 1)
				+ ":$H$" + (rowIndex + 1)));

		cell = row.createCell(8);
		sourceCell = sourceRow.getCell(3);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue(custPayment.getAmount());
		fillEmptyCells(row, newCellStyle, 9, 10);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$I$" + (rowIndex + 1)
				+ ":$K$" + (rowIndex + 1)));
		ownCreditTotal += custPayment.getAmount();

		cell = row.createCell(11);
		sourceCell = sourceRow.getCell(4);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");

		cell = row.createCell(12);
		sourceCell = sourceRow.getCell(5);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 13, 15);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$M$" + (rowIndex + 1)
				+ ":$P$" + (rowIndex + 1)));

		cell = row.createCell(16);
		sourceCell = sourceRow.getCell(6);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 17, 17);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$Q$" + (rowIndex + 1)
				+ ":$R$" + (rowIndex + 1)));

		cell = row.createCell(18);
		sourceCell = sourceRow.getCell(7);

		newCellStyle = hwb.createCellStyle();
		newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
		cell.setCellStyle(newCellStyle);
		cell.setCellValue("");
		fillEmptyCells(row, newCellStyle, 19, 19);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$S$" + (rowIndex + 1)
				+ ":$T$" + (rowIndex + 1)));
	}

}
