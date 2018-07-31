package de.dsteiner.wub.web.orderlist.report.excel;

import org.apache.commons.lang.Validate;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import de.dsteiner.wub.domain.Food;
import de.dsteiner.wub.domain.OrderListTotalFoodQuantity;

public abstract class SheetBuilderBase {

	//protected Log log = LogFactory.getLog(SheetBuilderBase.class);

	protected final HSSFCellStyle borderedCellStyle;
	protected final HSSFCellStyle borderedBoldCellStyle;
	protected final HSSFCellStyle borderedCurrencyCellStyle;
	protected final HSSFCellStyle borderedBoldUnderlinedCellStyle;

	protected final HSSFFont fatUnterlinedFont;
	protected final HSSFCellStyle borderedHeaderCellStyle;
	protected final HSSFWorkbook workbook;

	protected String sheetName;
	private HSSFPatriarch drawingPatriarch;
	private static final char[] A2Z = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };

	protected final HSSFSheet sheet;

	protected static final String COLUMN_NUMBER_STR = "Anzahl";
	protected static final String COLUMN_TOTAL_PRICE_STR = "Gesamtpreis";
	protected static final String COLUMN_UNIT_PRICE_STR = "Einzelpreis";
	protected static final String COLUMN_COMMON_FOOD_CONTRIBUTION_STR = "Einz.Umlage";
	

	public SheetBuilderBase(final HSSFWorkbook workbook, final String sheetName) {
		this.workbook = workbook;
		this.sheetName = sheetName;

		Validate.notNull(workbook);
		Validate.notNull(sheetName);

		borderedCellStyle = createBorderedCellStyle(workbook);
		borderedBoldCellStyle = createBorderedBoldFontCellStyle(workbook);
		borderedCurrencyCellStyle = createBorderedCurrencyCellStyle(workbook,
				null);
		fatUnterlinedFont = createFont(workbook, true, true);
		borderedBoldUnderlinedCellStyle = createBorderedCurrencyCellStyle(
				workbook, fatUnterlinedFont);
		borderedHeaderCellStyle = createBorderedHeaderCellStyle(workbook);

		sheet = workbook.createSheet(sheetName);
		sheet.setDefaultColumnWidth((short) 12);
	}

	/**
	 * returns the excel cell number (eg. C11, E4, AD1305 etc.) for this cell.
	 */
	public String getCellRefString(final int row, final int col) {
		final StringBuffer retval = new StringBuffer();
		int tempcellnum = col;
		do {
			retval.insert(0, A2Z[tempcellnum % 26]);
			tempcellnum = (tempcellnum / 26) - 1;
		} while (tempcellnum >= 0);
		retval.append(row + 1);
		return retval.toString();
	}

	public String getCellRefString(final int row, final int col,
			final String sheetName) {
		String ref = getCellRefString(row, col);
		if (sheetName != null) {
			ref = sheetName + "!" + ref;
		}
		return ref;
	}

	private HSSFFont createFont(final HSSFWorkbook workbook,
			final boolean bold, final boolean doubleUnderlined) {
		final HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		if (bold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		if (doubleUnderlined) {
			font.setUnderline(HSSFFont.U_DOUBLE);
		}
		return font;
	}

	private HSSFCellStyle createBorderedHeaderCellStyle(
			final HSSFWorkbook workbook) {
		final HSSFFont headerFont = createFont(workbook, true, false);

		final HSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		return headerCellStyle;
	}

	protected HSSFCell createCell(final short x, final String content,
			final HSSFRow row, final HSSFCellStyle cellStyle) {
		final HSSFCell cell = row.createCell(x);
		cell.setCellValue(content);
		cell.setCellStyle(cellStyle);
		return cell;
	}

	protected HSSFCell createNumberCell(final short x,
			final double euroAndCents, final HSSFRow row,
			final HSSFCellStyle cellStyle) {
		final HSSFCell cell = row.createCell(x);
		cell.setCellValue(euroAndCents);
		cell.setCellStyle(cellStyle);
		return cell;
	}

	protected HSSFCell createCell(final short x, final int content,
			final HSSFRow row, final HSSFCellStyle cellStyle) {
		final HSSFCell cell = row.createCell(x);
		cell.setCellValue(content);
		cell.setCellStyle(cellStyle);
		return cell;
	}

	private HSSFCellStyle createBorderedCellStyle(final HSSFWorkbook workbook) {
		final HSSFFont bodyCellFont = createFont(workbook, false, false);

		final HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
		bodyCellStyle.setFont(bodyCellFont);
		bodyCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		bodyCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		bodyCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		bodyCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		return bodyCellStyle;
	}

	private HSSFCellStyle createBorderedBoldFontCellStyle(
			final HSSFWorkbook workbook) {
		final HSSFCellStyle cellStyle = createBorderedHeaderCellStyle(workbook);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		return cellStyle;
	}

	private HSSFCellStyle createBorderedCurrencyCellStyle(
			final HSSFWorkbook workbook, final HSSFFont font) {
		// final CreationHelper createHelper = workbook.getCreationHelper();
		final HSSFCellStyle cellStyle = createBorderedCellStyle(workbook);

		if (font != null) {
			cellStyle.setFont(font);
		}
		// 3.0 ist buggy, geht nicht
		// final short format = workbook.createDataFormat().getFormat(
		// "#.##0.00\u20AC");
		cellStyle.setDataFormat((short) 4);
		return cellStyle;
	}

	protected double getPriceAsDouble(
			final OrderListTotalFoodQuantity totalFoodQuantity) {
		return getPriceAsDouble(totalFoodQuantity.getFood());
	}

	protected double getPriceAsDouble(final Food food) {
		double e = food.getEuro() == null ? 0.0 : food.getEuro();
		// auf 2 Stellen abschneiden.
		return ((int)(e*100))/100.0;
	}

	// protected HeaderColumn {
	// final protected String name;
	// private HeaderColumn(final String name)
	// {
	// this.name = name;
	// }
	// }

	public HSSFPatriarch getDrawingPatriarch() {
		if (drawingPatriarch == null) {
			drawingPatriarch = workbook.getSheet(sheetName)
					.createDrawingPatriarch();
		}
		return drawingPatriarch;
	}

	protected void createSimpleComment(final HSSFCell cell,
			final String commentString, final String author) {
		Validate.notNull(commentString);

		// must be the same instance per sheet!
		final HSSFPatriarch patr = this.getDrawingPatriarch();

		// anchor defines size and position of the comment in worksheet
		final HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,
				(short) 4, 2, (short) 6, 7);

		final HSSFComment comment = patr.createComment(anchor);

		// set text in the comment
		comment.setString(new HSSFRichTextString(commentString));

		if (author != null) {
			comment.setAuthor(author);
		}
		cell.setCellComment(comment);
	}
}
