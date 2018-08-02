package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.util.CellRangeAddress;

import skala.erp.bmp.server.dao.InventTableDAO;
import skala.erp.bmp.server.dao.ProdBOMDAO;
import skala.erp.bmp.server.dao.ProdTableDAO;
import skala.erp.bmp.shared.entity.InventTable;
import skala.erp.bmp.shared.entity.ProdBOM;
import skala.erp.bmp.shared.entity.ProdTable;

public class ProdTableExcellExporter extends ExcelExporter {

	ProdBOMDAO prodBOMDAO;

	ProdTableDAO prodTableDAO;

	InventTableDAO inventTableDAO;

	public ProdTableExcellExporter(ProdTableDAO prodTableDAO,
			ProdBOMDAO prodBOMDAO, InventTableDAO inventTableDAO) {
		this.prodBOMDAO = prodBOMDAO;
		this.prodTableDAO = prodTableDAO;
		this.inventTableDAO = inventTableDAO;
	}

	public String getProdOrderReportFilename(String orderId, String dataAreaId) {
		String filename = "prodOrder-" + orderId + ".xls";

		HSSFWorkbook hwb = new HSSFWorkbook();
		Map<String, CellStyle> styles = createStyles(hwb);
		hwb.createInformationProperties();
		SummaryInformation summaryInfo = hwb.getSummaryInformation();
		summaryInfo.setAuthor("BMP-System");
		HSSFSheet sheet = hwb.createSheet("Производство");

		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		int rowIndex = 0;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");

		ProdTable prod = prodTableDAO.find(orderId, dataAreaId);

		InventTable producedItem = inventTableDAO.find(prod.getBOMId(),
				dataAreaId);

		addHeaderRow(
				styles.get("title"),
				sheet,
				rowIndex,
				"Производственный заказ №" + orderId + " от "
						+ dateFormat.format(prod.getStUpDate()));
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex, prod.getBOMId()
				+ " (" + producedItem.getName() + ")");
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex, "Количество: "
				+ prod.getQtyStUp() + " " + producedItem.getUOMId());
		rowIndex++;

		HSSFRow inventHeaderRow = sheet.createRow(rowIndex);
		inventHeaderRow.setHeightInPoints(30);
		Cell cell = inventHeaderRow.createCell(0);
		cell.setCellValue("Номенклатура");
		cell.setCellStyle(styles.get("header"));
		fillEmptyCells(inventHeaderRow, styles.get("header"), 1, 4);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$E$" + (rowIndex + 1)));

		cell = inventHeaderRow.createCell(5);
		cell.setCellValue("Количество");
		cell.setCellStyle(styles.get("header"));
		cell = inventHeaderRow.createCell(6);
		cell.setCellValue("Ед. изм.");
		cell.setCellStyle(styles.get("header"));
		rowIndex++;

		List<ProdBOM> list = prodBOMDAO.findByProdId(orderId, dataAreaId);

		for (ProdBOM bom : list) {
			InventTable item = inventTableDAO.find(bom.getItemId(), dataAreaId);
			HSSFRow currentRow = sheet.createRow(rowIndex);
			currentRow.setHeightInPoints(30);
			cell = currentRow.createCell(0);
			cell.setCellStyle(styles.get("cell_left"));
			cell.setCellValue(item.getName());
			fillEmptyCells(currentRow, styles.get("cell_left"), 1, 4);
			sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"
					+ (rowIndex + 1) + ":$E$" + (rowIndex + 1)));
			cell = currentRow.createCell(5);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(bom.getQty());
			cell = currentRow.createCell(6);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(item.getUOMId());
			rowIndex++;
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			hwb.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}
}
