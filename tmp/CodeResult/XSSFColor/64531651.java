/*************************************************************
 *  (C) Copyright 2011, 2012 James Talbut.
 *  jim-emitters@spudsoft.co.uk
 * 
 *  This file is part of The SpudSoft BIRT Excel Emitters.
 *  The SpudSoft BIRT Excel Emitters are free software: you can 
 *  redistribute them and/or modify them under the terms of the 
 *  GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The SpudSoft BIRT Excel Emitters are distributed in the hope 
 *  that they will be useful, but WITHOUT ANY WARRANTY; 
 *  without even the implied warranty of MERCHANTABILITY or 
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with the SpudSoft BIRT Excel Emitters.
 *  If not, see <http://www.gnu.org/licenses/>.
 * 
 *************************************************************/

package uk.co.spudsoft.birt.emitters.excel.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.birt.core.exception.BirtException;
import org.junit.Test;

public class NestedTables2ReportTest extends ReportRunner {
	
	@Test
	public void testRunReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("NestedTables2.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Nested Tables Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals(12, firstNullRow(sheet));

			assertEquals( 1,  sheet.getRow(0).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 1,  sheet.getRow(0).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(1).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(1).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(2).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(2).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 1,  sheet.getRow(3).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(3).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(3).getCell(2).getNumericCellValue(), 0.0 );

			assertEquals( 2,  sheet.getRow(4).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(4).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 4,  sheet.getRow(5).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 4,  sheet.getRow(5).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(6).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(6).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(7).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 4,  sheet.getRow(7).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(7).getCell(2).getNumericCellValue(), 0.0 );

			assertEquals( 3,  sheet.getRow(8).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(8).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(9).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(9).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 9,  sheet.getRow(10).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 9,  sheet.getRow(10).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(11).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(11).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 9,  sheet.getRow(11).getCell(2).getNumericCellValue(), 0.0 );
			
			XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
			assertEquals( "FF800000", bgColour.getARGBHex() );
			XSSFColor baseColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFont().getXSSFColor();
			assertEquals( "FF000000", baseColour.getARGBHex() );
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportXls() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("NestedTables2.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Nested Tables Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals(12, firstNullRow(sheet));

			assertEquals( 1,  sheet.getRow(0).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 1,  sheet.getRow(0).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(1).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(1).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(2).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(2).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 1,  sheet.getRow(3).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(3).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(3).getCell(2).getNumericCellValue(), 0.0 );

			assertEquals( 2,  sheet.getRow(4).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(4).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 4,  sheet.getRow(5).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 4,  sheet.getRow(5).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(6).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(6).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 2,  sheet.getRow(7).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 4,  sheet.getRow(7).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(7).getCell(2).getNumericCellValue(), 0.0 );

			assertEquals( 3,  sheet.getRow(8).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(8).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(9).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(9).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 9,  sheet.getRow(10).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 9,  sheet.getRow(10).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 3,  sheet.getRow(11).getCell(0).getNumericCellValue(), 0.0 );
			assertEquals( 6,  sheet.getRow(11).getCell(1).getNumericCellValue(), 0.0 );
			assertEquals( 9,  sheet.getRow(11).getCell(2).getNumericCellValue(), 0.0 );
			
			short bgColour = ((HSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillBackgroundColor();
			assertEquals( "0:0:0", workbook.getCustomPalette().getColor(bgColour).getHexString() );
			short baseColour = workbook.getFontAt(((HSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFontIndex()).getColor();
			assertEquals( "FFFF:FFFF:FFFF", workbook.getCustomPalette().getColor(baseColour).getHexString() );
		} finally {
			inputStream.close();
		}
	}
}
