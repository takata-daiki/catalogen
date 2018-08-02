package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;

import skala.erp.bmp.baseenums.ItemType;
import skala.erp.bmp.server.dao.InventTableDAO;
import skala.erp.bmp.server.dao.ProdBOMDAO;
import skala.erp.bmp.server.dao.ProdTableDAO;
import skala.erp.bmp.shared.entity.InventTable;
import skala.erp.bmp.shared.entity.ProdBOM;
import skala.erp.bmp.shared.entity.ProdTable;

public class ProdTableInRangeExellExporter extends ExcelExporter {
	ProdBOMDAO prodBOMDAO;

	ProdTableDAO prodTableDAO;

	InventTableDAO inventTableDAO;

	List<TableRecord> writeOffItems;
	List<TableRecord> produceItems;

	public ProdTableInRangeExellExporter(ProdTableDAO prodTableDAO,
			ProdBOMDAO prodBOMDAO, InventTableDAO inventTableDAO) {
		this.prodBOMDAO = prodBOMDAO;
		this.prodTableDAO = prodTableDAO;
		this.inventTableDAO = inventTableDAO;
	}

	Map<String, Double> qtyies;

	public String getReportFilename(Date fromDate, Date toDate,
			String dataAreaId) {
		qtyies = new HashMap<String, Double>();
		writeOffItems = new ArrayList<ProdTableInRangeExellExporter.TableRecord>();
		produceItems = new ArrayList<ProdTableInRangeExellExporter.TableRecord>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String filename = "production-" + dateFormat.format(fromDate) + "-"
				+ dateFormat.format(toDate) + ".xls";

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

		addHeaderRow(styles.get("title"), sheet, rowIndex,
				"Производство в период с " + dateFormat.format(fromDate)
						+ " по " + dateFormat.format(toDate));
		rowIndex++;

		HSSFRow inventHeaderRow = sheet.createRow(rowIndex);
		inventHeaderRow.setHeightInPoints(30);

		Cell cell = inventHeaderRow.createCell(0);
		cell.setCellValue("Номенклатура");
		cell.setCellStyle(styles.get("header"));

		cell = inventHeaderRow.createCell(1);
		cell.setCellValue("Операция");
		cell.setCellStyle(styles.get("header"));

		cell = inventHeaderRow.createCell(2);
		cell.setCellValue("Количество");
		cell.setCellStyle(styles.get("header"));

		cell = inventHeaderRow.createCell(3);
		cell.setCellValue("Ед. изм.");
		cell.setCellStyle(styles.get("header"));

		rowIndex++;

		List<ProdTable> prods = prodTableDAO
				.getAllCompletedProdOrdersInDateRange(fromDate, toDate,
						dataAreaId);
		for (ProdTable prodTable : prods) {
			InventTable prodItem = inventTableDAO.find(prodTable.getBOMId(),
					dataAreaId);
			List<ProdBOM> boms = prodBOMDAO.findByProdId(prodTable.getProdId(),
					dataAreaId);
			for (ProdBOM bom : boms) {
				InventTable item = inventTableDAO.find(bom.getItemId(),
						dataAreaId);
				// System.out.println(item + " " + bom.getQty());
				TableRecord record = new TableRecord();
				record.itemName = item.getName();
				record.UOMId = item.getUOMId();
				if (ItemType.Service.getIntValue() == prodItem.getItemType()) {
					if (bom.getPercentage() < 0)
						record.oper = "Списано";
					else
						record.oper = "Произведено";
				} else {
					record.oper = "Списано";
				}
				String key = record.itemName + " " + record.oper;
				if (!qtyies.containsKey(key)) {
					qtyies.put(key, Math.abs(bom.getQty()));
					if (record.oper.equals("Произведено"))
						produceItems.add(record);
					else
						writeOffItems.add(record);

				} else {
					double value = qtyies.get(key);
					qtyies.put(key, value + Math.abs(bom.getQty()));
				}
			}
			if (prodItem.getItemType() != ItemType.Service.getIntValue()) {
				TableRecord record = new TableRecord();
				record.itemName = prodItem.getName();
				// System.out.println(prodItem.getName() + " "
				// + prodTable.getQtyStUp());
				record.UOMId = prodItem.getUOMId();
				record.oper = "Произведено";
				String key = record.itemName + " " + record.oper;
				if (!qtyies.containsKey(key)) {
					qtyies.put(key, prodTable.getQtyStUp());
					produceItems.add(record);
				} else {
					double value = qtyies.get(key);
					qtyies.put(key, value + prodTable.getQtyStUp());
				}
			}

		}

		for (TableRecord record : produceItems) {
			HSSFRow currentRow = sheet.createRow(rowIndex);
			currentRow.setHeightInPoints(30);

			String key = record.itemName + " " + record.oper;

			cell = currentRow.createCell(0);
			cell.setCellStyle(styles.get("cell_left"));
			cell.setCellValue(record.itemName);

			cell = currentRow.createCell(1);
			cell.setCellValue(record.oper);
			cell.setCellStyle(styles.get("cell"));

			cell = currentRow.createCell(2);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(qtyies.get(key));

			cell = currentRow.createCell(3);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(record.UOMId);
			rowIndex++;
		}

		for (TableRecord record : writeOffItems) {
			HSSFRow currentRow = sheet.createRow(rowIndex);
			currentRow.setHeightInPoints(30);

			String key = record.itemName + " " + record.oper;

			cell = currentRow.createCell(0);
			cell.setCellStyle(styles.get("cell_left"));
			cell.setCellValue(record.itemName);

			cell = currentRow.createCell(1);
			cell.setCellValue(record.oper);
			cell.setCellStyle(styles.get("cell"));

			cell = currentRow.createCell(2);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(qtyies.get(key));

			cell = currentRow.createCell(3);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(record.UOMId);
			rowIndex++;
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			hwb.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}

	class TableRecord {
		String itemName;
		String oper;
		String UOMId;
	}

}
