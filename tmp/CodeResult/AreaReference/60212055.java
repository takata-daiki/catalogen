package org.comsoft.juniprint;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.comsoft.juniprint.utils.ExcelUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Простые тесты JUniPrint
 * 
 * @author <a href="mailto:faa@comsoft-corp.ru">Фомичев Артем</a> <br>
 *
 */
public class JUniPrintTest {

	@Test
	public void test() throws Exception {
		System.out.println("test...");
		HSSFWorkbook wb = null;
		try {
			InputStream templateStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("template1.xlt");
			try {
				POIFSFileSystem fs = new POIFSFileSystem(templateStream);
				wb = new HSSFWorkbook(fs);

				HSSFName nameDataBeg = ExcelUtils.getNamedRange(wb, "DataBeg");

				String nameShData = nameDataBeg.getSheetName();
				HSSFSheet shData = wb.getSheet(nameShData);

				AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
				int dataBegRow = areaDataBeg.getFirstCell().getRow();
				int dataBegCol = areaDataBeg.getFirstCell().getCol();

				Random rnd = new Random();

				//String[] cols = {"A", "B", "C"};

				for (int k = 0; k < 5; k++) {
					for (int i = 0; i < 5; i++) {
						int rowIndex = dataBegRow + k * 5 + i;
						HSSFRow row = getRow(shData, rowIndex);
						HSSFCell cell = getCell(row, dataBegCol);
						cell.setCellValue(k);
						for (int j = 1; j < 4; j++) {
							int colIndex = dataBegCol + j;
							cell = getCell(row, colIndex);
							//Object value = cols[j] + rnd.nextInt();
							//if(value != null){
							cell.setCellValue(rnd.nextDouble());
							//}
						}
					}
				}

				JUniPrint jUniPrint = new JUniPrint(wb);
				jUniPrint.init(null, null, null);
				jUniPrint.uniPrint(false);
				FileOutputStream outputStream = new FileOutputStream("target/output.xls");
				try {
					wb.write(outputStream);
				} finally {
					outputStream.close();
				}
			} finally {
				templateStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void hiddenDetailsAndGroupsFormulaTest() throws Exception {
		System.out.println("hiddenDetailsTest...");
		HSSFWorkbook wb = null;
		try {
			InputStream templateStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("hiddenDetailsTemplate.xlt");
			try {
				POIFSFileSystem fs = new POIFSFileSystem(templateStream);
				wb = new HSSFWorkbook(fs);

				HSSFName nameDataBeg = ExcelUtils.getNamedRange(wb, "DataBeg");

				String nameShData = nameDataBeg.getSheetName();
				HSSFSheet shData = wb.getSheet(nameShData);

				AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
				int dataBegRow = areaDataBeg.getFirstCell().getRow();
				int dataBegCol = areaDataBeg.getFirstCell().getCol();

				Random rnd = new Random();

				//String[] cols = {"A", "B", "C"};

				int groupsCount = 5;
				int detailGroupRowsCount = 5;

				for (int k = 0; k < groupsCount; k++) {
					for (int i = 0; i < detailGroupRowsCount; i++) {
						int rowIndex = dataBegRow + k * 5 + i;
						HSSFRow row = getRow(shData, rowIndex);
						HSSFCell cell = getCell(row, dataBegCol);
						cell.setCellValue(k);
						for (int j = 1; j < 4; j++) {
							int colIndex = dataBegCol + j;
							cell = getCell(row, colIndex);
							//Object value = cols[j] + rnd.nextInt();
							//if(value != null){
							cell.setCellValue(rnd.nextDouble());
							//}
						}
					}
				}

				JUniPrint jUniPrint = new JUniPrint(wb);
				jUniPrint.init(null, null, null);
				jUniPrint.uniPrint(false);
				FileOutputStream outputStream = new FileOutputStream("target/hiddenDetailsOutput.xls");
				try {
					wb.write(outputStream);
				} finally {
					outputStream.close();
				}

				int outputFirstRowCheck = 16;
				int outputColCheck = 5;
				int groupHeaderRowsCount = 3;
				int groupFooterRowsCount = 3;
				int groupStep = groupHeaderRowsCount + groupFooterRowsCount + detailGroupRowsCount;

				for (int i = 0; i < groupsCount; i++) {
					int groupHeaderRowNumber = outputFirstRowCheck + i * groupStep;
					HSSFRow groupHeaderRow = getRow(shData, groupHeaderRowNumber - 1);
					int groupFooterRowNumber = groupHeaderRowNumber + groupHeaderRowsCount + detailGroupRowsCount;
					HSSFRow groupFooterRow = getRow(shData, groupFooterRowNumber - 1);

					/* checking hidden detail rows */
					assertFalse(groupHeaderRow.getZeroHeight());
					assertFalse(groupFooterRow.getZeroHeight());

					for (int j = 0; j < detailGroupRowsCount; j++) {
						HSSFRow detailRow = getRow(shData, groupHeaderRowNumber + groupHeaderRowsCount + j - 1);
						assertTrue(detailRow.getZeroHeight());
					}

					/* checking groups formula */
					String groupHeaderCellFormula = getCell(groupHeaderRow, outputColCheck - 1).getCellFormula();
					String expectedFormula = "SUM(E" + (groupHeaderRowNumber + groupHeaderRowsCount) + ":E" + (groupHeaderRowNumber + groupHeaderRowsCount + detailGroupRowsCount - 1) + ")";
					System.out.println("groupHeaderCellFormula = " + groupHeaderCellFormula + ", expectedFormula = " + expectedFormula);
					assertTrue(StringUtils.isNotBlank(groupHeaderCellFormula));
					assertEquals(groupHeaderCellFormula, expectedFormula);

					String groupFooterCellFormula = getCell(groupFooterRow, outputColCheck - 1).getCellFormula();
					System.out.println("groupFooterCellFormula = " + groupFooterCellFormula + ", expectedFormula = " + expectedFormula);
					assertTrue(StringUtils.isNotBlank(groupFooterCellFormula));
					assertEquals(groupFooterCellFormula, expectedFormula);
				}

				int firstDetailRowNumber = outputFirstRowCheck + groupHeaderRowsCount;
				int lastDetailRowNumber = outputFirstRowCheck + groupsCount * groupStep - groupFooterRowsCount - 1;
				int group22FooterRowNumber = outputFirstRowCheck + groupsCount * groupStep;
				System.out.println("firstDetailRowNumber = " + firstDetailRowNumber + ", lastDetailRowNumber = " + lastDetailRowNumber + ", group22FooterRowNumber = " + group22FooterRowNumber);
				HSSFRow group22FooterRow = getRow(shData, group22FooterRowNumber - 1);
				String group22FooterCellFormula = getCell(group22FooterRow, outputColCheck - 1).getCellFormula();
				String expectedFormula = "SUMIF(A" + (firstDetailRowNumber) + ":A" + (lastDetailRowNumber) + ",\"=0\",E" + (firstDetailRowNumber) + ":E" + (lastDetailRowNumber) + ")";
				System.out.println("group22FooterCellFormula = " + group22FooterCellFormula + ", expectedFormula = " + expectedFormula);
				assertTrue(StringUtils.isNotBlank(group22FooterCellFormula));
				assertEquals(group22FooterCellFormula, expectedFormula);
			} finally {
				templateStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private HSSFRow getRow(HSSFSheet sh, int rowIndex){
		if(sh==null) return null;
		HSSFRow r = sh.getRow(rowIndex);
		if(r != null) return r;
		r = sh.createRow(rowIndex);
		return r;
	}

	private HSSFCell getCell(HSSFRow row, int columnIndex){
		if(row==null) return null;
		HSSFCell c = row.getCell(columnIndex);
		if(c != null) return c;
		c = row.createCell(columnIndex);
		return c;
	}

}
