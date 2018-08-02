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
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.birt.core.exception.BirtException;
import org.junit.Test;

public class AutoColWidthsTest extends ReportRunner {
	
	@Test
	public void testRunReport() throws BirtException, IOException {

		InputStream inputStream = runAndRenderReport("AutoColWidths.rptdesign", "xlsx");
		assertNotNull(inputStream);
		try {
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			assertNotNull(workbook);
			
			assertEquals( 1, workbook.getNumberOfSheets() );
			assertEquals( "AutoColWidths Test Report", workbook.getSheetAt(0).getSheetName());
			
			Sheet sheet = workbook.getSheetAt(0);
			assertEquals(23, this.firstNullRow(sheet));
			
			assertEquals( 6127,                    sheet.getColumnWidth( 0 ) );
			assertEquals( 2048,                    sheet.getColumnWidth( 1 ) );
			assertEquals( 4999,                    sheet.getColumnWidth( 2 ) );
			assertEquals( 3812,                    sheet.getColumnWidth( 3 ) );
			assertEquals( 3812,                    sheet.getColumnWidth( 4 ) );
			assertEquals( 2048,                    sheet.getColumnWidth( 5 ) );
			assertThat( sheet.getColumnWidth( 6 ), both( greaterThan( 3000 ) ).and( lessThan( 3200 ) ) );
			assertThat( sheet.getColumnWidth( 7 ), both( greaterThan( 2100 ) ).and( lessThan( 2900 ) ) );
			assertEquals( 2048,                    sheet.getColumnWidth( 8 ) );
						
			DataFormatter formatter = new DataFormatter();
			
			assertEquals( "1",                     formatter.formatCellValue(sheet.getRow(2).getCell(1)));
			assertEquals( "2019-10-11 13:18:46",   formatter.formatCellValue(sheet.getRow(2).getCell(2)));
			assertEquals( "3.1415926536",          formatter.formatCellValue(sheet.getRow(2).getCell(3)));
			assertEquals( "3.1415926536",          formatter.formatCellValue(sheet.getRow(2).getCell(4)));
			assertEquals( "false",                 formatter.formatCellValue(sheet.getRow(2).getCell(5)));

		} finally {
			inputStream.close();
		}
	}
	
}
