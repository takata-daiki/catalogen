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
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.birt.core.exception.BirtException;
import org.junit.Test;

public class BasicReportTest extends ReportRunner {

	@Test
	public void testRunReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("Simple.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertNotNull( sheet.getRow(0) );
			assertNotNull( sheet.getRow(1) );
			assertNotNull( sheet.getRow(2) );
			assertNotNull( sheet.getRow(3) );
			assertNull( sheet.getRow(4) );
			
			assertEquals( 1.0, sheet.getRow(1).getCell(0).getNumericCellValue(), 0.001);
			assertEquals( 2.0, sheet.getRow(1).getCell(1).getNumericCellValue(), 0.001);
			assertEquals( 3.0, sheet.getRow(1).getCell(2).getNumericCellValue(), 0.001);
			assertEquals( 2.0, sheet.getRow(2).getCell(0).getNumericCellValue(), 0.001);
			assertEquals( 4.0, sheet.getRow(2).getCell(1).getNumericCellValue(), 0.001);
			assertEquals( 6.0, sheet.getRow(2).getCell(2).getNumericCellValue(), 0.001);
			assertEquals( 3.0, sheet.getRow(3).getCell(0).getNumericCellValue(), 0.001);
			assertEquals( 6.0, sheet.getRow(3).getCell(1).getNumericCellValue(), 0.001);
			assertEquals( 9.0, sheet.getRow(3).getCell(2).getNumericCellValue(), 0.001);
			
			assertEquals( 3510, sheet.getColumnWidth(0) );
			assertEquals( 3510, sheet.getColumnWidth(1) );
			assertEquals( 3510, sheet.getColumnWidth(2) );			
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpeg() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertNotNull( sheet.getRow(0) );
			assertNotNull( sheet.getRow(1) );
			assertNotNull( sheet.getRow(2) );
			assertNotNull( sheet.getRow(3) );
			assertNotNull( sheet.getRow(4) );
			assertNotNull( sheet.getRow(5) );
			assertNull( sheet.getRow(6) );
			
			assertEquals( 1.0, sheet.getRow(2).getCell(0).getNumericCellValue(), 0.001);
			assertEquals( 2.0, sheet.getRow(2).getCell(1).getNumericCellValue(), 0.001);
			assertEquals( 3.0, sheet.getRow(2).getCell(2).getNumericCellValue(), 0.001);
			assertEquals( 2.0, sheet.getRow(3).getCell(0).getNumericCellValue(), 0.001);
			assertEquals( 4.0, sheet.getRow(3).getCell(1).getNumericCellValue(), 0.001);
			assertEquals( 6.0, sheet.getRow(3).getCell(2).getNumericCellValue(), 0.001);
			assertEquals( 3.0, sheet.getRow(4).getCell(0).getNumericCellValue(), 0.001);
			assertEquals( 6.0, sheet.getRow(4).getCell(1).getNumericCellValue(), 0.001);
			assertEquals( 9.0, sheet.getRow(4).getCell(2).getNumericCellValue(), 0.001);
			
			assertEquals( 5266, sheet.getColumnWidth(0) );
			assertEquals( 3510, sheet.getColumnWidth(1) );
			assertEquals( 3510, sheet.getColumnWidth(2) );
			
			assertEquals( 960, sheet.getRow(0).getHeight() );
			assertEquals( 300, sheet.getRow(1).getHeight() );
			assertEquals( 300, sheet.getRow(2).getHeight() );
			assertEquals( 300, sheet.getRow(3).getHeight() );
			assertEquals( 300, sheet.getRow(4).getHeight() );
			assertEquals( 2160, sheet.getRow(5).getHeight() );
			
			// Unfortunately it's not currently possible/easy to check the dimensions of images using POI
			// So the XL file has to be opened manually for verification
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpegXls() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
			
			// Unfortunately it's not currently possible/easy to check the dimensions of images using POI
			// So the XL file has to be opened manually for verification
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpegXlsFormulasRenderOption() throws BirtException, IOException {

		displayFormulas = true;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( true, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsFormulasReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegDisplayFormulas.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( true, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpegXlsGridlinesRenderOption() throws BirtException, IOException {

		displayGridlines = false;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( false, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsGridlinesReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegHideGridlines.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( false, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpegXlsRowColHeadingsRenderOption() throws BirtException, IOException {

		displayRowColHeadings = false;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( false, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsRowColHeadingsReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegHideRowColHeadings.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( false, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}


	@Test
	public void testRunReportWithJpegXlsZerosRenderOption() throws BirtException, IOException {

		displayZeros = false;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( false, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsZerosReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegHideZeros.rptdesign", "xls");
		assertNotNull(inputStream);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( false, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpegXlsxFormulasRenderOption() throws BirtException, IOException {

		displayFormulas = true;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( true, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsxFormulasReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegDisplayFormulas.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( true, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpegXlsxGridlinesRenderOption() throws BirtException, IOException {

		displayGridlines = false;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( false, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsxGridlinesReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegHideGridlines.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( false, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testRunReportWithJpegXlsxRowColHeadingsRenderOption() throws BirtException, IOException {

		displayRowColHeadings = false;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( false, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsxRowColHeadingsReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegHideRowColHeadings.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( false, sheet.isDisplayRowColHeadings() );
			assertEquals( true, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}


	@Test
	public void testRunReportWithJpegXlsxZerosRenderOption() throws BirtException, IOException {

		displayZeros = false;
		InputStream inputStream = runAndRenderReport("SimpleWithJpeg.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( false, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}
	
	@Test
	public void testRunReportWithJpegXlsxZerosReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("SimpleWithJpegHideZeros.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals( false, sheet.isDisplayFormulas() );
			assertEquals( true, sheet.isDisplayGridlines() );
			assertEquals( true, sheet.isDisplayRowColHeadings() );
			assertEquals( false, sheet.isDisplayZeros() );
			performSimpleWithJpegTests(sheet);
		} finally {
			inputStream.close();
		}
	}

	public void performSimpleWithJpegTests(Sheet sheet) {
		assertNotNull( sheet.getRow(0) );
		assertNotNull( sheet.getRow(1) );
		assertNotNull( sheet.getRow(2) );
		assertNotNull( sheet.getRow(3) );
		assertNotNull( sheet.getRow(4) );
		assertNotNull( sheet.getRow(5) );
		assertNull( sheet.getRow(6) );
		
		assertEquals( 1.0, sheet.getRow(2).getCell(0).getNumericCellValue(), 0.001);
		assertEquals( 2.0, sheet.getRow(2).getCell(1).getNumericCellValue(), 0.001);
		assertEquals( 3.0, sheet.getRow(2).getCell(2).getNumericCellValue(), 0.001);
		assertEquals( 2.0, sheet.getRow(3).getCell(0).getNumericCellValue(), 0.001);
		assertEquals( 4.0, sheet.getRow(3).getCell(1).getNumericCellValue(), 0.001);
		assertEquals( 6.0, sheet.getRow(3).getCell(2).getNumericCellValue(), 0.001);
		assertEquals( 3.0, sheet.getRow(4).getCell(0).getNumericCellValue(), 0.001);
		assertEquals( 6.0, sheet.getRow(4).getCell(1).getNumericCellValue(), 0.001);
		assertEquals( 9.0, sheet.getRow(4).getCell(2).getNumericCellValue(), 0.001);
		
		assertEquals( 5266, sheet.getColumnWidth(0) );
		assertEquals( 3510, sheet.getColumnWidth(1) );
		assertEquals( 3510, sheet.getColumnWidth(2) );
		
		assertEquals( 960, sheet.getRow(0).getHeight() );
		assertEquals( 2160, sheet.getRow(5).getHeight() );
	}

}
