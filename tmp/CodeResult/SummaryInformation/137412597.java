package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;

import skala.erp.bmp.server.dao.InventLocationDAO;
import skala.erp.bmp.server.dao.InventTableDAO;
import skala.erp.bmp.server.dao.InventTransactionDAO;
import skala.erp.bmp.shared.entity.InventLocation;
import skala.erp.bmp.shared.entity.InventTable;

public class InventLocationExcelExporter extends ExcelExporter {

	InventTableDAO inventTableDAO;

	InventLocationDAO inventLocationDAO;

	InventTransactionDAO inventTransactionDAO;

	public InventLocationExcelExporter(InventTableDAO inventTableDAO,
			InventLocationDAO inventLocationDAO,
			InventTransactionDAO inventTransactionDAO) {
		this.inventLocationDAO = inventLocationDAO;
		this.inventTableDAO = inventTableDAO;
		this.inventTransactionDAO = inventTransactionDAO;
	}

	private static final String[] charTable = new String[65536];

	static {
		charTable[' '] = "_";
		charTable['А'] = "A";
		charTable['Б'] = "B";
		charTable['В'] = "V";
		charTable['Г'] = "G";
		charTable['Д'] = "D";
		charTable['Е'] = "E";
		charTable['Ё'] = "E";
		charTable['Ж'] = "ZH";
		charTable['З'] = "Z";
		charTable['И'] = "I";
		charTable['Й'] = "I";
		charTable['К'] = "K";
		charTable['Л'] = "L";
		charTable['М'] = "M";
		charTable['Н'] = "N";
		charTable['О'] = "O";
		charTable['П'] = "P";
		charTable['Р'] = "R";
		charTable['С'] = "S";
		charTable['Т'] = "T";
		charTable['У'] = "U";
		charTable['Ф'] = "F";
		charTable['Х'] = "H";
		charTable['Ц'] = "C";
		charTable['Ч'] = "CH";
		charTable['Ш'] = "SH";
		charTable['Щ'] = "SH";
		charTable['Ъ'] = "'";
		charTable['Ы'] = "Y";
		charTable['Ь'] = "'";
		charTable['Э'] = "E";
		charTable['Ю'] = "U";
		charTable['Я'] = "YA";

		for (int i = 0; i < charTable.length; i++) {
			char idx = (char) i;
			char lower = new String(new char[] { idx }).toLowerCase().charAt(0);
			if (charTable[i] != null) {
				charTable[lower] = charTable[i].toLowerCase();
			}
		}
	}

	public String translitString(String forTranslit) {
		char charBuffer[] = forTranslit.toCharArray();
		StringBuilder sb = new StringBuilder(forTranslit.length());
		for (char symbol : charBuffer) {
			String replace = charTable[symbol];
			sb.append(replace == null ? symbol : replace);
		}
		return sb.toString();
	}

	public String getLocationSumAtDateFilename(String locationId,
			Date revisionDate, String dataAreaId) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String filename = "revision-" + translitString(locationId) + "("
				+ dateFormat.format(revisionDate) + ").xls";
		HSSFWorkbook hwb = new HSSFWorkbook();
		Map<String, CellStyle> styles = createStyles(hwb);
		hwb.createInformationProperties();
		SummaryInformation summaryInfo = hwb.getSummaryInformation();
		summaryInfo.setAuthor("BMP-System");
		HSSFSheet sheet = hwb.createSheet("Журнал");

		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		InventLocation location = inventLocationDAO
				.find(locationId, dataAreaId);

		int rowIndex = 0;

		addHeaderRow(styles.get("title"), sheet, rowIndex, "Складские запасы");
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex,
				"Склад: " + location.getName());
		rowIndex++;

		addHeaderRow(styles.get("title"), sheet, rowIndex, "На дату: "
				+ dateFormat.format(revisionDate));
		rowIndex++;

		HSSFRow inventHeaderRow = sheet.createRow(rowIndex);
		inventHeaderRow.setHeightInPoints(30);
		Cell cell = inventHeaderRow.createCell(0);
		cell.setCellValue("Номенклатура");
		cell.setCellStyle(styles.get("header"));
		cell = inventHeaderRow.createCell(1);
		cell.setCellValue("Группа");
		cell.setCellStyle(styles.get("header"));
		cell = inventHeaderRow.createCell(2);
		cell.setCellValue("Количество");
		cell.setCellStyle(styles.get("header"));
		cell = inventHeaderRow.createCell(3);
		cell.setCellValue("Ед. изм.");
		cell.setCellStyle(styles.get("header"));

		rowIndex++;

		List<InventTable> items = inventTableDAO
				.getAllWithDataAreaId(dataAreaId);
		for (InventTable item : items) {
			double qty = inventTransactionDAO.getItemSumOnLocationAtDate(
					item.getItemId(), locationId, revisionDate, dataAreaId);
			if (qty == 0)
				continue;
			HSSFRow currentRow = sheet.createRow(rowIndex);
			currentRow.setHeightInPoints(30);
			cell = currentRow.createCell(0);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(item.getName());
			cell = currentRow.createCell(1);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(item.getItemGroupId());
			cell = currentRow.createCell(2);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(qty);
			cell = currentRow.createCell(3);
			cell.setCellStyle(styles.get("cell"));
			cell.setCellValue(item.getUOMId());
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

}
