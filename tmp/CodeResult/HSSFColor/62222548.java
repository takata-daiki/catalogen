/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsxk;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author Administrator
 */
public class poiSample{
  public static void main(String[] args){
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet();

    HSSFRow row = sheet.createRow(1);
    HSSFCell cell1 = row.createCell((short)1);
    HSSFCell cell2 = row.createCell((short)2);

    HSSFCellStyle style1 = workbook.createCellStyle();
    style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
    style1.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
    style1.setTopBorderColor(HSSFColor.GOLD.index);
    style1.setLeftBorderColor(HSSFColor.PLUM.index);
    cell1.setCellStyle(style1);

    HSSFCellStyle style2 = workbook.createCellStyle();
    style2.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
    style2.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
    style2.setBottomBorderColor(HSSFColor.ORANGE.index);
    style2.setRightBorderColor(HSSFColor.SKY_BLUE.index);
    cell2.setCellStyle(style2);

    cell1.setCellValue("U & L");
    cell2.setCellValue("B & R");

    FileOutputStream out = null;
    try{
      out = new FileOutputStream("e:/sample.xls");
      workbook.write(out);
    }catch(IOException e){
      System.out.println(e.toString());
    }finally{
      try {
        out.close();
      }catch(IOException e){
        System.out.println(e.toString());
      }
    }
  }
}

