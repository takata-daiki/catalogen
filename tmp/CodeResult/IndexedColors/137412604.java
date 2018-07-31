package skala.erp.bmp.server.excellexporters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class ExcelExporter {

	protected final int MAX_LIST_LANDSCAPE_HEIGHT = 720;

	protected final int MAX_LIST_PORTRAIT_HEIGTH = 1018;

	protected final int ROW_HEIGHT = 12;

	protected HSSFWorkbook createWorkbook(String filename, String host)
			throws FileNotFoundException {
		FileInputStream file = null;
		String templatePath;
		if (host.contains("127.0.0.1") || host.contains("localhost"))
			templatePath = "C:\\Workspace\\BMP\\war\\ExcelTemplates\\"
					+ filename;
		else
			templatePath = "/usr/share/ExcelTemplates/" + filename;
		file = new FileInputStream(new File(templatePath));
		HSSFWorkbook hwb = null;
		try {
			hwb = new HSSFWorkbook(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		SummaryInformation summaryInfo = hwb.getSummaryInformation();
		summaryInfo.setAuthor("BMP-System");
		return hwb;
	}

	protected Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 14);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		Font monthFont = wb.createFont();
		monthFont.setFontHeightInPoints((short) 12);
		monthFont.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(monthFont);
		style.setWrapText(true);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell_left", style);
		
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		styles.put("simple_text", style);

		return styles;
	}

	protected void fillEmptyCells(HSSFRow currentRow, CellStyle style,
			int begin, int end) {
		for (int i = begin; i <= end; i++) {
			Cell cell = currentRow.createCell(i);
			cell.setCellStyle(style);
		}
	}

	protected void addHeaderRow(CellStyle style, HSSFSheet sheet, int rowIndex,
			String content) {
		HSSFRow headerRow = sheet.createRow(rowIndex);
		Cell titleCell = headerRow.createCell(0);
		titleCell.setCellValue(content);
		titleCell.setCellStyle(style);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$" + (rowIndex + 1)
				+ ":$H$" + (rowIndex + 1)));
	}

	@SuppressWarnings("deprecation")
	protected void setCellValue(HSSFSheet sheet, String cellAddres, String value) {
		CellReference cellReference = new CellReference(cellAddres);
		HSSFRow currentRow = sheet.getRow(cellReference.getRow());
		Cell currentCell = currentRow.getCell(cellReference.getCol());
		currentCell.setCellValue(value);
	}

	@SuppressWarnings("deprecation")
	protected void setCellValue(HSSFSheet sheet, String cellAddres, double value) {
		CellReference cellReference = new CellReference(cellAddres);
		HSSFRow currentRow = sheet.getRow(cellReference.getRow());
		Cell currentCell = currentRow.getCell(cellReference.getCol());
		currentCell.setCellValue(value);
	}

	protected void copyRow(HSSFWorkbook hwb, HSSFRow sourceRow,
			HSSFSheet sheet, int rowIndex) {
		HSSFRow newRow = sheet.createRow(rowIndex);
		newRow.setHeight(sourceRow.getHeight());
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			CellStyle newCellStyle = hwb.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			;
			newCell.setCellStyle(newCellStyle);

			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			newCell.setCellType(oldCell.getCellType());

			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}

		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(
						newRow.getRowNum(),
						(newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress
								.getFirstRow())), cellRangeAddress
								.getFirstColumn(), cellRangeAddress
								.getLastColumn());
				sheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}

	public String removeLinebreaksFromString(String str) {
		str = str.replace("\n", " ");
		str = str.replace("\r", " ");
		return str;
	}

}
