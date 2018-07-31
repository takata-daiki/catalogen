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

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.birt.core.exception.BirtException;
import org.junit.Test;

public class Groupings extends ReportRunner {

	@Test
	public void testGroupings() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("Grouping.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 3, workbook.getNumberOfSheets() );

			XSSFSheet sheet0 = workbook.getSheetAt(0);
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			XSSFSheet sheet2 = workbook.getSheetAt(2);
			assertEquals( "HeaderAndFooter", sheet0.getSheetName());
			
			int rowNum0 = 1;
			int rowNum1 = 1;
			int rowNum2 = 1;
			for( int i = 1; i < 9; ++i ) {
				System.out.println( "i==" + i );
				assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 1, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
				for( int j = 0; j < i; ++j) {
					assertEquals( "rowNum=" + rowNum0, 1, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
					if( j < i - 1 ) {
						assertEquals( "rowNum=" + rowNum1, i == 1 ? 0 : 1, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
						assertEquals( "rowNum=" + rowNum2, i == 1 ? 0 : 1, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
					}
				}
				assertEquals( "rowNum=" + rowNum0, 1, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 1, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
			}
			assertThat( rowNum0, greaterThan( 50 ) );
			assertThat( rowNum1, greaterThan( 40 ) );
			assertThat( rowNum2, greaterThan( 40 ) );

		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testGroupingsBlockedByContext() throws BirtException, IOException {

		disableGrouping = Boolean.TRUE;
		InputStream inputStream = runAndRenderReport("Grouping.rptdesign", "xlsx");
		disableGrouping = null;
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 3, workbook.getNumberOfSheets() );

			XSSFSheet sheet0 = workbook.getSheetAt(0);
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			XSSFSheet sheet2 = workbook.getSheetAt(2);
			assertEquals( "HeaderAndFooter", sheet0.getSheetName());
			
			int rowNum0 = 1;
			int rowNum1 = 1;
			int rowNum2 = 1;
			for( int i = 1; i < 9; ++i ) {
				System.out.println( "i==" + i );
				assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
				for( int j = 0; j < i; ++j) {
					assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
					if( j < i - 1 ) {
						assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
						assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
					}
				}
				assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
			}
			assertThat( rowNum0, greaterThan( 50 ) );
			assertThat( rowNum1, greaterThan( 40 ) );
			assertThat( rowNum2, greaterThan( 40 ) );

		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testGroupingsBlockedByReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("GroupingDisabledAtReport.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 3, workbook.getNumberOfSheets() );

			XSSFSheet sheet0 = workbook.getSheetAt(0);
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			XSSFSheet sheet2 = workbook.getSheetAt(2);
			assertEquals( "HeaderAndFooter", sheet0.getSheetName());
			
			int rowNum0 = 1;
			int rowNum1 = 1;
			int rowNum2 = 1;
			for( int i = 1; i < 9; ++i ) {
				System.out.println( "i==" + i );
				assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
				for( int j = 0; j < i; ++j) {
					assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
					if( j < i - 1 ) {
						assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
						assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
					}
				}
				assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
			}
			assertThat( rowNum0, greaterThan( 50 ) );
			assertThat( rowNum1, greaterThan( 40 ) );
			assertThat( rowNum2, greaterThan( 40 ) );

		} finally {
			inputStream.close();
		}
	}

	@Test
	public void testGroupingsBlockedByTable() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("GroupingDisabledAtTable.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 3, workbook.getNumberOfSheets() );

			XSSFSheet sheet0 = workbook.getSheetAt(0);
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			XSSFSheet sheet2 = workbook.getSheetAt(2);
			assertEquals( "HeaderAndFooter", sheet0.getSheetName());
			
			int rowNum0 = 1;
			int rowNum1 = 1;
			int rowNum2 = 1;
			for( int i = 1; i < 9; ++i ) {
				System.out.println( "i==" + i );
				assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 1, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
				for( int j = 0; j < i; ++j) {
					assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
					if( j < i - 1 ) {
						assertEquals( "rowNum=" + rowNum1, i == 1 ? 0 : 1, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
						assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
					}
				}
				assertEquals( "rowNum=" + rowNum0, 0, sheet0.getRow( rowNum0++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum1, 0, sheet1.getRow( rowNum1++ ).getCTRow().getOutlineLevel() );
				assertEquals( "rowNum=" + rowNum2, 0, sheet2.getRow( rowNum2++ ).getCTRow().getOutlineLevel() );
			}
			assertThat( rowNum0, greaterThan( 50 ) );
			assertThat( rowNum1, greaterThan( 40 ) );
			assertThat( rowNum2, greaterThan( 40 ) );

		} finally {
			inputStream.close();
		}
	}
}
