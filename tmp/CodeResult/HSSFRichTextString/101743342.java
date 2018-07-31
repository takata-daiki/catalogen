/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.transfer.excel;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.commons.transfer.exporter.PropertyExtractor;

/**
 * 写到excel中的工具
 * 
 * @author songshuquan,chaostone
 * @version $Id: $
 */
public class ExcelTools {
  DecimalFormat numberformat = new DecimalFormat("#0.00");

  /**
   * <p>
   * Constructor for ExcelTools.
   * </p>
   */
  public ExcelTools() {
  }

  /**
   * <p>
   * toExcel.
   * </p>
   * 
   * @param datas a {@link java.util.Collection} object.
   * @param propertyShowKeys a {@link java.lang.String} object.
   * @return a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   * @throws java.lang.Exception if any.
   */
  public HSSFWorkbook toExcel(Collection<Object[]> datas, String propertyShowKeys) throws Exception {
    // 建立新HSSFWorkbook对象
    HSSFWorkbook wb = new HSSFWorkbook();
    return toExcel(wb, "export data", datas, propertyShowKeys);
  }

  /**
   * 将一个对象数组的集合导出成excel
   * 
   * @param datas a {@link java.util.Collection} object.
   * @param propertyShowKeys a {@link java.lang.String} object.
   * @throws java.lang.Exception if any.
   * @param wb a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   * @param sheetName a {@link java.lang.String} object.
   * @return a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   */
  public HSSFWorkbook toExcel(HSSFWorkbook wb, String sheetName, Collection<Object[]> datas,
      String propertyShowKeys) throws Exception {
    HSSFSheet sheet = wb.createSheet(sheetName);
    HSSFRow row = null;
    HSSFCell cell = null;

    /** **************** 取得传入的list列名称和显示名称 ********** */
    String[] pShowKeys = Tokenizer2StringArray(propertyShowKeys, ",");
    // 创建�?行（标题)
    row = sheet.createRow(0); // 建立新行
    // 显示标题列名
    for (int i = 0; i < pShowKeys.length; i++) {
      cell = row.createCell(i); // 建立新cell
      // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
      cell.setCellValue(new HSSFRichTextString(pShowKeys[i]));
    }
    // 逐行取数
    int rowId = 1;// 数据行号（从2行开始填充数�?)
    for (Iterator<Object[]> iter = datas.iterator(); iter.hasNext(); rowId++) {
      row = sheet.createRow(rowId); // 建立新行
      Object[] objs = iter.next();
      // 生成每一�?
      for (int j = 0; j < objs.length; j++) {
        cell = row.createCell(j); // 建立新cell
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(new HSSFRichTextString((objs[j] == null) ? "" : objs[j].toString()));
      }
    }
    return wb;
  }

  /**
   * List数据集导出生成Excel文件
   * 
   * @param list 对象数据列表
   * @param propertyKeys 对象属字符串，中间以","间隔
   * @param propertyShowKeys 显示字段的名字字符串，中间以","间隔
   * @return 返回单个HSSFWorkbook（excel）
   * @throws java.lang.Exception if any.
   * @param exporter a {@link org.beangle.commons.transfer.exporter.PropertyExtractor} object.
   * @param <T> a T object.
   */
  public <T> HSSFWorkbook object2Excel(Collection<T> list, String propertyKeys, String propertyShowKeys,
      PropertyExtractor exporter) throws Exception {
    HSSFWorkbook wb = new HSSFWorkbook(); // 建立新HSSFWorkbook对象
    object2Excel(wb, "export result", list, propertyKeys, propertyShowKeys, exporter);
    return wb;
  }

  /**
   * 将数据导出excel指定名称的数据页�?.
   * 
   * @param wb a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   * @param sheetName a {@link java.lang.String} object.
   * @param list a {@link java.util.Collection} object.
   * @param propertyKeys a {@link java.lang.String} object.
   * @param propertyShowKeys a {@link java.lang.String} object.
   * @param exporter a {@link org.beangle.commons.transfer.exporter.PropertyExtractor} object.
   * @throws java.lang.Exception if any.
   * @param <T> a T object.
   * @return a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   */
  public <T extends Object> HSSFWorkbook object2Excel(HSSFWorkbook wb, String sheetName, Collection<T> list,
      String propertyKeys, String propertyShowKeys, PropertyExtractor exporter) throws Exception {
    HSSFSheet sheet = wb.createSheet(sheetName);
    HSSFRow row = null;
    HSSFCell cell = null;
    Object cellVal = null;

    /** **************** 取得传入的list列名称和显示名称 ********** */
    String[] pKeys = Tokenizer2StringArray(propertyKeys, ",");
    String[] pShowKeys = Tokenizer2StringArray(propertyShowKeys, ",");
    /** *************** insert data to excel*************** */
    // 创建（标题)
    row = sheet.createRow(0); // 建立新行
    // 显示标题列名�?
    for (int i = 0; i < pShowKeys.length; i++) {
      cell = row.createCell(i); // 建立新cell
      // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
      cell.setCellValue(new HSSFRichTextString(pShowKeys[i]));
    }
    // 逐行取数�?
    int rowId = 1;// 数据行号（从�?2行开始填充数�?)
    for (Iterator<T> iter = list.iterator(); iter.hasNext(); rowId++) {
      row = sheet.createRow(rowId); // 建立新行
      T obj = iter.next();
      // 生成每一
      for (int i = 0; i < pKeys.length; i++) {
        cell = row.createCell(i); // 建立新cell
        cellVal = exporter.getPropertyValue(obj, pKeys[i]);
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        String cellValue = "";
        if (null != cellVal) {
          cellValue = cellVal.toString();
        }
        if (cellVal instanceof Float) {
          cellValue = numberformat.format(cellVal);
        }
        cell.setCellValue(new HSSFRichTextString(cellValue));
      }
    }
    return wb;
  }

  /**
   * <p>
   * object2Excel.
   * </p>
   * 
   * @param list a {@link java.util.List} object.
   * @param propertyKeys a {@link java.lang.String} object.
   * @param propertyShowKeys a {@link java.lang.String} object.
   * @param <T> a T object.
   * @return a {@link org.apache.poi.hssf.usermodel.HSSFWorkbook} object.
   * @throws java.lang.Exception if any.
   */
  public <T> HSSFWorkbook object2Excel(List<T> list, String propertyKeys, String propertyShowKeys)
      throws Exception {
    return object2Excel(list, propertyKeys, propertyShowKeys, new DefaultPropertyExtractor());

  }

  /**
   * 将StringTokenizer类型数据转化生成字符串数�?
   * 
   * @param sourceStr 解析","间隔的字符串，变成字符串数组
   * @param strDot
   */
  private String[] Tokenizer2StringArray(String sourceStr, String strDot) {
    StringTokenizer st = new StringTokenizer(sourceStr, strDot);
    int size = st.countTokens();
    String[] strArray = new String[size];
    for (int i = 0; i < size; i++) {
      strArray[i] = st.nextToken();
    }
    return strArray;
  }
}
