/********************************************************************************
* (C) Copyright 2011, by James Talbut.
*
*   This program is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   This program is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
*   [Java is a trademark or registered trademark of Sun Microsystems, Inc.
*   in the United States and other countries.]
********************************************************************************/

package uk.co.spudsoft.birt.emitters.excel.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.birt.core.exception.BirtException;
import org.junit.Test;

public class SingleSheetsReportTest extends ReportRunner {
	
	public SingleSheetsReportTest() {
		singleSheet = true;
	}

	@Test
	public void testThreeTablesNoNastinessPdfCheck() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("MultiSheets1.rptdesign", "pdf");
		inputStream.close();
	}

	@Test
	public void testThreeTablesNoNastiness() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportAsOne("MultiSheets1.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(11, firstNullRow(workbook.getSheetAt(0)));
			
			assertEquals( false, workbook.getSheetAt(0).isDisplayGridlines());
			assertEquals( false, workbook.getSheetAt(0).isDisplayRowColHeadings());
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testThreeTablesRenderPaginationBug() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportDefaultTask("MultiSheets1.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(11, firstNullRow(workbook.getSheetAt(0)));
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testThreeTablesRenderCustomTask() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportCustomTask("MultiSheets1.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(11, firstNullRow(workbook.getSheetAt(0)));
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testBreakInSubTable() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportCustomTask("MultiSheetsBreakInSubTable.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Multiple Sheets - Break in sub-", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(11, firstNullRow(workbook.getSheetAt(0)));		
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testNoNames() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportCustomTask("MultiSheetsNoNames.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(11, firstNullRow(workbook.getSheetAt(0)));
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testTwoNames() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportCustomTask("MultiSheetsTwoNames.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());

			assertEquals(11, firstNullRow(workbook.getSheetAt(0)));
			
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testBigTableDefaultInterval() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportCustomTask("MultiSheetsBigTableFortyInterval.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(196, firstNullRow(workbook.getSheetAt(0)));
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testBigTableZeroInterval() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReportCustomTask("MultiSheetsBigTableZeroInterval.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(192, firstNullRow(workbook.getSheetAt(0)));
			
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testBigTableZeroIntervalWithPagination() throws BirtException, IOException {

		htmlPagination = true;
		InputStream inputStream = runAndRenderReportCustomTask("MultiSheetsBigTableZeroInterval.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName());
			
			assertEquals(196, firstNullRow(workbook.getSheetAt(0)));
		} finally {
			inputStream.close();
		}
	}
}
