package skala.erp.bmp.server.excellexporters;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

import skala.erp.bmp.server.dao.MX18LineDAO;
import skala.erp.bmp.server.dao.MX18TableDAO;
import skala.erp.bmp.shared.entity.MX18Line;
import skala.erp.bmp.shared.entity.MX18Table;

public class MX18StatReport extends ExcelExporter {

	MX18LineDAO mx18LineDAO;
	MX18TableDAO mx18TableDAO;

	Map<String, Double> totalQties;

	public MX18StatReport(MX18TableDAO mx18TableDAO, MX18LineDAO mx18LineDAO) {
		this.mx18LineDAO = mx18LineDAO;
		this.mx18TableDAO = mx18TableDAO;
	}

	public String getStatReportInDateRange(Date fromDate, Date toDate,
			String dataAreaId) {

		totalQties = new HashMap<String, Double>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
		String filename = "mx18stat-" + dateFormat.format(fromDate) + "-"
				+ dateFormat.format(toDate) + ".xls";

		HSSFWorkbook hwb = new HSSFWorkbook();
		Map<String, CellStyle> styles = createStyles(hwb);
		hwb.createInformationProperties();
		SummaryInformation summaryInfo = hwb.getSummaryInformation();
		summaryInfo.setAuthor("BMP-System");
		HSSFSheet sheet = hwb.createSheet("MX-18 - Статистика");

		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		int rowIndex = 0;

		List<MX18Table> list = mx18TableDAO.getAllPostedInDateRange(fromDate,
				toDate, dataAreaId);
		for (MX18Table mx18 : list) {
			addHeaderRow(
					styles.get("title"),
					sheet,
					rowIndex,
					"Накладная № " + mx18.getMx18Number() + " от "
							+ dateFormat.format(mx18.getMx18Date()));
			rowIndex++;

			HSSFRow inventHeaderRow = sheet.createRow(rowIndex);
			inventHeaderRow.setHeightInPoints(30);

			Cell cell = inventHeaderRow.createCell(0);
			cell.setCellValue("Номенклатура");
			cell.setCellStyle(styles.get("header"));

			cell = inventHeaderRow.createCell(1);
			cell.setCellValue("Количество");
			cell.setCellStyle(styles.get("header"));

			cell = inventHeaderRow.createCell(2);
			cell.setCellValue("Ед. изм.");
			cell.setCellStyle(styles.get("header"));
			rowIndex++;

			List<MX18Line> lines = mx18LineDAO.getAllByNumber(
					mx18.getMx18Number(), dataAreaId);
			for (MX18Line line : lines) {
				HSSFRow currentRow = sheet.createRow(rowIndex);
				currentRow.setHeightInPoints(30);

				String key = line.getItem().getName() + "-"
						+ line.getItem().getUOMId();

				cell = currentRow.createCell(0);
				cell.setCellStyle(styles.get("cell_left"));
				cell.setCellValue(line.getItem().getName());

				cell = currentRow.createCell(1);
				cell.setCellValue(line.getQty());
				cell.setCellStyle(styles.get("cell"));

				cell = currentRow.createCell(2);
				cell.setCellValue(line.getItem().getUOMId());
				cell.setCellStyle(styles.get("cell"));

				if (!totalQties.containsKey(key)) {
					totalQties.put(key, line.getQty());
				} else {
					double value = totalQties.get(key);
					totalQties.put(key, value + line.getQty());
				}
				rowIndex++;

			}
		}

		rowIndex++;
		String[] items = totalQties.keySet().toArray(new String[0]);
		Arrays.sort(items);

		addHeaderRow(styles.get("title"), sheet, rowIndex,
				"Всего передано за период с " + dateFormat.format(fromDate)
						+ " по " + dateFormat.format(toDate));
		rowIndex++;

		for (String item : items) {
			HSSFRow currentRow = sheet.createRow(rowIndex);
			currentRow.setHeightInPoints(30);

			String[] splitedKey = item.split("\\-");
			Cell cell = currentRow.createCell(0);
			cell.setCellStyle(styles.get("simple_text"));
			cell.setCellValue(splitedKey[0] + " - "
					+ String.format("%.2f", totalQties.get(item)) + " "
					+ splitedKey[1]);
			rowIndex++;
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
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
