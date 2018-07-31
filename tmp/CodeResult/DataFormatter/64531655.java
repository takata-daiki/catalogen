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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.birt.core.exception.BirtException;
import org.junit.Test;

public class CurrencyFormatsTest extends ReportRunner {
	
	@Test
	public void testRunReportXls() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("CurrencyFormats.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Currency Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals(5, this.firstNullRow(sheet));
			
			DataFormatter formatter = new DataFormatter();
			
			assertEquals( "£3141.59",              formatter.formatCellValue(sheet.getRow(1).getCell(1)));
			assertEquals( "$3141.59",              formatter.formatCellValue(sheet.getRow(2).getCell(1)));
			assertEquals( "¥3141.59",              formatter.formatCellValue(sheet.getRow(3).getCell(1)));
			assertEquals( "€3141.59",              formatter.formatCellValue(sheet.getRow(4).getCell(1)));
		} finally {
			inputStream.close();
		}
	}
	
	
	@Test
	public void testRunReportXlsx() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("CurrencyFormats.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Currency Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( 5, this.firstNullRow(sheet));
			
			DataFormatter formatter = new DataFormatter();
			
			assertEquals( "£3141.59",              formatter.formatCellValue(sheet.getRow(1).getCell(1)));
			assertEquals( "$3141.59",              formatter.formatCellValue(sheet.getRow(2).getCell(1)));
			assertEquals( "¥3141.59",              formatter.formatCellValue(sheet.getRow(3).getCell(1)));
			assertEquals( "€3141.59",              formatter.formatCellValue(sheet.getRow(4).getCell(1)));
		} finally {
			inputStream.close();
		}
	}
	
}
