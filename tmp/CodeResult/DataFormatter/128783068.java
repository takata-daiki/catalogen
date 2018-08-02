/*    
 *  <Quantitative project management tool.>
 *
 *  Copyright (C) 2012 IPA, Japan.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tracipf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;



/**
 * Convert an Excel2007 file to a Excel2003 file.
 *
 * usage: java ExcelDowngradeConverter INFILE SHEET-INDEX OUTFILE
 */
public class ExcelDowngradeConverter
{
	public static void main(final String[] args)
	{
		try {
			final File infile = new File(args[0]);
			final int sheet_index = Integer.parseInt(args[1]);
			final File outfile = new File(args[2]);

			ExcelDowngradeConverter converter = new ExcelDowngradeConverter();
			converter.convert(infile, sheet_index, outfile);
		}
		catch (Exception e) {
			System.exit(1);
		}
	}

	public void convert(File source, int sheet_index, File destfile)
		throws FileNotFoundException, IOException, InvalidFormatException
	{
		Workbook workbook = this.openWorkbook(source);
		Sheet sheet = workbook.getSheetAt(sheet_index - 1);
		DataFormatter formatter = new DataFormatter(java.util.Locale.JAPAN);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		Workbook dest_workbook = new HSSFWorkbook();
		Sheet dest_sheet = dest_workbook.createSheet();
		DataFormat dest_format = dest_workbook.getCreationHelper().createDataFormat();
		
		final int lastRowNum = sheet.getLastRowNum();
		for (int r = 0 ; r <= lastRowNum ; r++) {
			Row row = sheet.getRow(r);
			if (row != null) {
				Row dest_row = dest_sheet.createRow(r);

				final int lastCellNum = row.getLastCellNum();
				for (int c = 0 ; c < lastCellNum ; c++) {
					Cell cell = row.getCell(c);
					if (cell != null) {
						if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
							cell = evaluator.evaluateInCell(cell);
						}
						Cell dest_cell = dest_row.createCell(c);
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							dest_cell.setCellValue(cell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							dest_cell.setCellValue(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							dest_cell.setCellValue(cell.getStringCellValue());
							break;
						}
						CellStyle style = cell.getCellStyle();
						if (style != null) {
							CellStyle dest_style = dest_workbook.createCellStyle();
							dest_style.setDataFormat(dest_format.getFormat(style.getDataFormatString()));
							dest_cell.setCellStyle(dest_style);
						}
					}
				}
			}
		}
		
		FileOutputStream out = new FileOutputStream(destfile);
		dest_workbook.write(out);
		out.close();
	}

	public static Workbook openWorkbook(File file)
		throws FileNotFoundException, IOException, InvalidFormatException
	{
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			return WorkbookFactory.create(in);
		}
		finally {
			if (in != null) in.close();
		}
	}
}
