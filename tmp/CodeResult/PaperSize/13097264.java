/**
 * ReportGear(2011)
 */
package com.reportgear.report.print;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reportgear.core.util.UnitUtils;
import com.reportgear.report.core.background.ColorBackground;
import com.reportgear.report.model.ReportModel;
import com.reportgear.report.model.auxiliary.ColumnPropList;
import com.reportgear.report.model.auxiliary.RowPropList;
import com.reportgear.report.model.cell.Cell;
import com.reportgear.report.model.headerfooter.ReportHF;
import com.reportgear.report.setting.PaperSetting;
import com.reportgear.report.setting.ReportSettings;
import com.reportgear.report.view.grid.util.GraphHelper;
import com.reportgear.report.view.grid.util.PaintUtils;

/**
 * ?????
 * 
 * @version 1.0 2011-3-23
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public class ReportPage implements Printable {
	private static final Logger logger = LoggerFactory.getLogger(ReportPage.class.getClass());
	// ???
	private int currentPageNumber;
	// ???
	private int totalPages;
	// ???
	private int groupPageNumber;
	// ?????
	private int groupTotalPages;

	// ????
	private final ReportModel reportModel;
	// ????
	private final ReportSettings reportSettings;
	private final PaperSetting paperSetting;

	private final FT columnFT;
	private final FT rowFT;

	public ReportPage(ReportModel reportModel, FT rowFT, FT columnFT) {
		this.reportModel = reportModel;
		this.reportSettings = this.reportModel.getReportSettings();
		this.paperSetting = this.reportSettings.getPaperSetting();
		this.rowFT = rowFT;
		this.columnFT = columnFT;
	}

	/**
	 * ?????????Array
	 * 
	 * @return ??Array
	 */
	public int[] getColumnIndexArray() {
		int[] cwArr = new int[this.columnFT.getSize()];

		int ptr = 0;
		for (Integer col : this.columnFT) {
			cwArr[ptr++] = this.reportModel.getColumnWidth(col);
		}

		return cwArr;
	}

	/**
	 * ?????????Array
	 * 
	 * @return ??Array
	 */
	public int[] getRowIndexArray() {
		int[] rhArr = new int[this.rowFT.getSize()];

		int ptr = 0;
		for (Integer row : this.rowFT) {
			rhArr[ptr++] = this.reportModel.getRowHeight(row);
		}

		return rhArr;
	}

	/**
	 * 
	 * @return ????(inch)
	 */
	public double getFooterHeight() {
		return this.reportSettings.getFooterHeight();
	}

	/**
	 * 
	 * @return ????(inch)
	 */
	public double getHeaderHeight() {
		return this.reportSettings.getHeaderHeight();
	}

	/**
	 * @return ???(inch)
	 */
	public double getMarginBottom() {
		return this.paperSetting.getMargin().getBottom();
	}

	/**
	 * @return ???(inch)
	 */
	public double getMarginLeft() {
		return this.paperSetting.getMargin().getLeft();
	}

	/**
	 * @return ???(inch)
	 */
	public double getMarginRight() {
		return this.paperSetting.getMargin().getRight();
	}

	/**
	 * @return ???(inch)
	 */
	public double getMarginTop() {
		return this.paperSetting.getMargin().getTop();
	}

	/**
	 * @return ????
	 */
	public int getOrientation() {
		return this.paperSetting.getOrientation();
	}

	/**
	 * @return ????(inch)
	 */
	public double getPaperHeight() {
		PaperSize paperSize = this.paperSetting.getPaperSize();

		if (this.getOrientation() == PageFormat.PORTRAIT) {
			return Math.max(paperSize.getHeight(), paperSize.getWidth());
		} else {
			return Math.min(paperSize.getHeight(), paperSize.getWidth());
		}
	}

	/**
	 * @return ????(inch)
	 */
	public double getPaperWidth() {
		PaperSize paperSize = this.paperSetting.getPaperSize();

		if (this.getOrientation() == PageFormat.PORTRAIT) {
			return Math.min(paperSize.getHeight(), paperSize.getWidth());
		} else {
			return Math.max(paperSize.getHeight(), paperSize.getWidth());
		}
	}

	/**
	 * ??????
	 * 
	 * @param g
	 *            ????
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		ColorBackground.getInstance(Color.WHITE).paint(
				g2,
				new Rectangle2D.Double(0.0D, 0.0D, UnitUtils.inch2pixel(this.getPaperWidth()), UnitUtils
						.inch2pixel(this.getPaperHeight())));

		this.printPaint(g2, false);
	}

	// ??
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		double pageWidth = UnitUtils.inch2pt(this.getPaperWidth());
		double pageHeight = UnitUtils.inch2pt(this.getPaperHeight());

		double hScale = pageFormat.getImageableWidth() / pageWidth / UnitUtils.PIXEL_PER_PT;
		double vScale = pageFormat.getImageableHeight() / pageHeight / UnitUtils.PIXEL_PER_PT;

		Graphics2D g2 = (Graphics2D) g;

		double scale = Math.min(hScale, vScale);

		if (scale < 1.0D) {
			g2.scale(scale, scale);
		}

		this.printPaint(g2, false);

		return Printable.PAGE_EXISTS;
	}

	/**
	 * ????
	 * 
	 * @param g2
	 *            ????
	 * @param drawBackground
	 *            ??????
	 */
	public void printPaint(Graphics2D g2, boolean drawBackground) {
		double width = this.getPaperWidth() - this.getMarginLeft() - this.getMarginRight();

		// ??????
		this.paintHF(g2, width);
		// ????
		this.paintContent(g2, new Rectangle2D.Double(this.getMarginLeft(),
				this.getMarginTop() + this.getHeaderHeight(), width, this.getPaperHeight() - this.getMarginTop()
						- this.getMarginBottom() - this.getHeaderHeight() - this.getFooterHeight()), drawBackground);
	}

	/**
	 * ??????
	 * 
	 * @param g2
	 * @param width
	 */
	private void paintHF(Graphics2D g2, double width) {
		ReportHF header = this.reportSettings.getHeader();

		int pixelWidth = UnitUtils.inch2pixel(width);
		int pixelHeight;

		if (header != null) {
			pixelHeight = UnitUtils.inch2pixel(header.getHeight());
			header.paint(g2, new Rectangle2D.Double(UnitUtils.inch2pixel(this.getMarginLeft()), UnitUtils
					.inch2pixel(this.getMarginTop()), pixelWidth, pixelHeight), pixelWidth,
					this.getCurrentPageNumber(), this.getTotalPages(), this.getGroupPageNumber(), this
							.getGroupTotalPages());
		}

		ReportHF footer = this.reportSettings.getFooter();

		if (footer != null) {
			pixelHeight = UnitUtils.inch2pixel(footer.getHeight());
			footer.paint(g2,
					new Rectangle2D.Double(UnitUtils.inch2pixel(this.getMarginLeft()),

					UnitUtils.inch2pixel(this.getPaperHeight() - this.getMarginBottom()) - pixelHeight, pixelWidth,
							pixelHeight), pixelWidth, this.getCurrentPageNumber(), this.getTotalPages(), this
							.getGroupPageNumber(), this.getGroupTotalPages());
		}
	}

	/**
	 * ????
	 * 
	 * @param g2
	 *            ????
	 * @param rect
	 *            ????
	 * @param drawBackground
	 *            ??????
	 */
	private void paintContent(Graphics2D g2, Rectangle2D rect, boolean drawBackground) {
		int startX = UnitUtils.inch2pixel(rect.getX());
		int startY = UnitUtils.inch2pixel(rect.getY());

		g2.translate(startX, startY);
		this.paintCells(g2);
		g2.translate(-startX, -startY);
	}

	/**
	 * ???????
	 * 
	 * @param rect
	 *            ???????
	 */
	public void paintCells(Graphics2D g2) {
		Rectangle rect = new Rectangle(this.columnFT.from, this.rowFT.from, this.columnFT.to - this.columnFT.from,
				this.rowFT.to - this.rowFT.from);

		final Iterator<Cell> cellIterator = this.reportModel.intersect(rect.x, rect.y, rect.width, rect.height);

		ColumnPropList cwL = this.reportModel.getColumnPropList();
		RowPropList rhL = this.reportModel.getRowPropList();

		// ????X
		int fixedWidth = 0;
		List<Integer> fixedHeaderColumnsNotInCurrPage = new ArrayList<Integer>();
		for (int index : this.reportModel.getFixedHeaderColumns()) {
			// ???
			if (index < this.columnFT.from || index > this.columnFT.to) {
				fixedWidth += cwL.getValue(index);
				fixedHeaderColumnsNotInCurrPage.add(index);
			}
		}

		// ?????
		List<Integer> fixedfooterColumnsNotInCurrPage = new ArrayList<Integer>();
		for (int index : this.reportModel.getFixedFooterColumns()) {
			if (index < this.columnFT.from || index > this.columnFT.to) {
				fixedfooterColumnsNotInCurrPage.add(index);
			}
		}

		// ????Y
		int fixedHeight = 0;
		List<Integer> fixedHeaderRowsNotInCurrPage = new ArrayList<Integer>();
		for (int index : this.reportModel.getFixedHeaderRows()) {
			if (index < this.rowFT.from || index > this.rowFT.to) {
				fixedHeight += rhL.getValue(index);
				fixedHeaderRowsNotInCurrPage.add(index);
			}
		}

		// ?????
		List<Integer> fixedfooterRowsNotInCurrPage = new ArrayList<Integer>();
		for (int index : this.reportModel.getFixedFooterRows()) {
			if (index < this.rowFT.from || index > this.rowFT.to) {
				fixedfooterRowsNotInCurrPage.add(index);
			}
		}

		// ??????
		int fx = 0;
		for (int fixedIndex : fixedHeaderColumnsNotInCurrPage) {
			if (!fixedHeaderRowsNotInCurrPage.isEmpty()) {
				int hc = 0;
				for (int row : fixedHeaderRowsNotInCurrPage) {
					Cell cell = this.reportModel.getCell(fixedIndex, row);

					int y = hc;
					hc += rhL.getValue(row);
					int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
					int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
					Rectangle cellRect = new Rectangle(fx, y, w, h);

					PaintUtils.paintCellElement(g2, cell, cellRect);
				}
			}
			for (int i = this.rowFT.from, to = this.rowFT.to; i < to; i++) {
				Cell cell = this.reportModel.getCell(fixedIndex, i);

				int y = fixedHeight + rhL.getRangeValue(rect.y, cell.getRow());
				int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
				int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
				Rectangle cellRect = new Rectangle(fx, y, w, h);

				PaintUtils.paintCellElement(g2, cell, cellRect);
			}

			if (!fixedfooterRowsNotInCurrPage.isEmpty()) {
				int hc = fixedHeight + rhL.getRangeValue(this.rowFT.from, this.rowFT.to);
				for (int row : fixedfooterRowsNotInCurrPage) {
					Cell cell = this.reportModel.getCell(fixedIndex, row);

					int y = hc;
					hc += rhL.getValue(row);
					int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
					int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
					Rectangle cellRect = new Rectangle(fx, y, w, h);

					PaintUtils.paintCellElement(g2, cell, cellRect);
				}
			}

			fx += cwL.getValue(fixedIndex);
		}

		// ??????
		fx = fixedWidth + cwL.getRangeValue(this.columnFT.from, this.columnFT.to);
		for (int fixedIndex : fixedfooterColumnsNotInCurrPage) {
			if (!fixedHeaderRowsNotInCurrPage.isEmpty()) {
				int hc = 0;
				for (int row : fixedHeaderRowsNotInCurrPage) {
					Cell cell = this.reportModel.getCell(fixedIndex, row);

					int y = hc;
					hc += rhL.getValue(row);
					int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
					int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
					Rectangle cellRect = new Rectangle(fx, y, w, h);

					PaintUtils.paintCellElement(g2, cell, cellRect);
				}
			}

			for (int i = this.rowFT.from, to = this.rowFT.to; i < to; i++) {
				Cell cell = this.reportModel.getCell(fixedIndex, i);

				int y = fixedHeight + rhL.getRangeValue(rect.y, cell.getRow());
				int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
				int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
				Rectangle cellRect = new Rectangle(fx, y, w, h);

				PaintUtils.paintCellElement(g2, cell, cellRect);
			}

			if (!fixedfooterRowsNotInCurrPage.isEmpty()) {
				int hc = fixedHeight + rhL.getRangeValue(this.rowFT.from, this.rowFT.to);
				for (int row : fixedfooterRowsNotInCurrPage) {
					Cell cell = this.reportModel.getCell(fixedIndex, row);

					int y = hc;
					hc += rhL.getValue(row);
					int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
					int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
					Rectangle cellRect = new Rectangle(fx, y, w, h);

					PaintUtils.paintCellElement(g2, cell, cellRect);
				}
			}
			fx += cwL.getValue(fixedIndex);
		}

		// ??????
		int fy = 0;
		for (int fixedIndex : fixedHeaderRowsNotInCurrPage) {
			for (int i = this.columnFT.from, to = this.columnFT.to; i < to; i++) {
				Cell cell = this.reportModel.getCell(i, fixedIndex);

				int x = fixedWidth + cwL.getRangeValue(rect.x, cell.getColumn());
				int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
				int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
				Rectangle cellRect = new Rectangle(x, fy, w, h);

				PaintUtils.paintCellElement(g2, cell, cellRect);
			}

			fy += rhL.getValue(fixedIndex);
		}

		// ??????
		fy = fixedHeight + rhL.getRangeValue(this.rowFT.from, this.rowFT.to);
		for (int fixedIndex : fixedfooterRowsNotInCurrPage) {
			for (int i = this.columnFT.from, to = this.columnFT.to; i < to; i++) {
				Cell cell = this.reportModel.getCell(i, fixedIndex);

				int x = fixedWidth + cwL.getRangeValue(rect.x, cell.getColumn());
				int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
				int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
				Rectangle cellRect = new Rectangle(x, fy, w, h);

				PaintUtils.paintCellElement(g2, cell, cellRect);
			}

			fy += rhL.getValue(fixedIndex);
		}

		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();

			int x = fixedWidth + cwL.getRangeValue(rect.x, cell.getColumn());
			int y = fixedHeight + rhL.getRangeValue(rect.y, cell.getRow());
			int w = cwL.getRangeValue(cell.getColumn(), cell.getColumn() + cell.getColumnSpan());
			int h = rhL.getRangeValue(cell.getRow(), cell.getRow() + cell.getRowSpan());
			Rectangle cellRect = new Rectangle(x, y, w, h);
			PaintUtils.paintCellElement(g2, cell, cellRect);
		}
	}

	/**
	 * ??????
	 * 
	 * @return ????
	 */
	public int getCurrentPageNumber() {
		return this.currentPageNumber;
	}

	/**
	 * ??????
	 * 
	 * @param currentPageNumber
	 *            ??
	 */
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	/**
	 * 
	 * @return ???
	 */
	public int getTotalPages() {
		return this.totalPages;
	}

	/**
	 * ?????
	 * 
	 * @param totalPages
	 *            ???
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * ????
	 * 
	 * @param groupPageNumber
	 *            ????
	 */
	public void setGroupPageNumber(int groupPageNumber) {
		this.groupPageNumber = groupPageNumber;
	}

	/**
	 * ??????
	 * 
	 * @return ????
	 */
	public int getGroupPageNumber() {
		return groupPageNumber;
	}

	/**
	 * ?????
	 * 
	 * @param groupTotalPages
	 *            ?????
	 */
	public void setGroupTotalPages(int groupTotalPages) {
		this.groupTotalPages = groupTotalPages;
	}

	/**
	 * ?????
	 * 
	 * @return ?????
	 */
	public int getGroupTotalPages() {
		return groupTotalPages;
	}
}
