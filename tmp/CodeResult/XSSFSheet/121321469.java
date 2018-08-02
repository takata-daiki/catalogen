
import java.io.File;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellFill;

/*
 * Copyright (C) 2015 are
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author are
 */
public class XLColorTest {
  
          
  public static void main(String[] args) throws IOException, InvalidFormatException {
    XSSFWorkbook wb=new XSSFWorkbook(new File("/home/are/test1.xlsx"));
    XSSFSheet sheet=wb.getSheetAt(0);
    XSSFCellStyle cellStyle = sheet.getRow(0).getCell(0).getCellStyle();
    StylesTable stylesSource = wb.getStylesSource();
    for (int i=0; i<stylesSource.getNumCellStyles(); i++)
      if (stylesSource.getStyleAt(i).equals(cellStyle))
        System.out.println("Found cellstule with index " + i + " vs. " + cellStyle.getIndex());
    
    System.out.println("Fill ID: " + cellStyle.getCoreXf().getFillId());
    
    System.out.println("Index: " + sheet.getRow(0).getCell(0).getCellStyle().getIndex());
    
    //for (short i=0; i<stylesSource.getNumCellStyles(); i++) {
      XSSFCellFill fill = stylesSource.getFillAt(cellStyle.getIndex()+1);
      
      if (fill.getFillBackgroundColor()!=null)
        System.out.println("Index: " + cellStyle.getIndex() + " "+ fill.getFillBackgroundColor().getARGBHex());
      
      if (fill.getFillForegroundColor()!=null)
      System.out.println("Index: " +cellStyle.getIndex() + " "+ fill.getFillForegroundColor().getARGBHex());
    //}
  }
}
