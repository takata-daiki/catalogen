/* Decompiled through IntelliJad */
// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packfields(3) packimports(3) splitstr(64) radix(10) lradix(10) 
// Source File Name:   ExcelUtil.java

package com.dongwang.admin.util;

import org.apache.poi.hssf.usermodel.*;

import javax.swing.filechooser.FileSystemView;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    public ExcelUtil() {
    }

    public ExcelUtil createSheet(String name) {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(name);
        return this;
    }

    public void exportXLS(File path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportXLS(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExcelUtil createRow(int index) {
        row = sheet.createRow(index);
        sheet.setAutobreaks(true);
        return this;
    }

    public ExcelUtil setString(int index, String value) {
        HSSFCell cell = row.createCell(index);
        cell.setCellType(1);
        cell.getCellStyle().setAlignment((short) 2);
        cell.setCellType(1);
        cell.setCellValue(value);
        return this;
    }

    public ExcelUtil setInteger(int index, int value) {
        HSSFCell cell = row.createCell(index);
        cell.getCellStyle().setAlignment((short) 2);
        cell.setCellType(0);
        cell.setCellValue(value);
        return this;
    }

    public ExcelUtil setDate(int index, Date value) {
        if (value == null) {
            setString(index, "");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy\u5E74MM\u6708dd\u65E5");
            setString(index, sdf.format(value));
        }
        return this;
    }

    public ExcelUtil setTime(int index, Date value) {
        if (value == null) {
            setString(index, "");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy\u5E74MM\u6708dd\u65E5 HH\u65F6mm\u5206ss\u79D2");
            setString(index, sdf.format(value));
        }
        return this;
    }

    public void widthContro(int size) {
        for (int i = 0; i < size; i++)
            sheet.autoSizeColumn(i);

    }

    public void setWidth(int len, int size) {
        for (int i = 0; i < size; i++)
            sheet.setColumnWidth(i, len);

    }

    public String getDeskPath() {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        fsv.getHomeDirectory().setWritable(true);
        return fsv.getHomeDirectory().toString();
    }

    public List getSheets(File file) throws Exception {
        List rowLists = new ArrayList();
        java.io.InputStream fs = new FileInputStream(file);
        HSSFWorkbook workBook = new HSSFWorkbook(fs);
        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
            HSSFSheet sheet = workBook.getSheetAt(i);
            if (sheet != null) {
                List rowList = new ArrayList();
                for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
                    HSSFRow row = sheet.getRow(r);
                    if (row != null)
                        rowList.add(sheet.getRow(r));
                }

                rowLists.add(rowList);
            }
        }

        return rowLists;
    }

    public String getValue(HSSFCell cell) {
        String value = "";
        if (cell != null)
            switch (cell.getCellType()) {
                case 0: // '\0'
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
                    } else {
                        value = String.valueOf(cell.getNumericCellValue());
                        System.out.println(value);
                    }
                    break;

                case 1: // '\001'
                    value = cell.getRichStringCellValue().toString();
                    break;

                case 2: // '\002'
                    value = String.valueOf(cell.getNumericCellValue());
                    if (value.equals("NaN"))
                        value = cell.getRichStringCellValue().toString();
                    break;

                case 4: // '\004'
                    value = (new StringBuilder(" ")).append(cell.getBooleanCellValue()).toString();
                    break;

                case 3: // '\003'
                    value = "";
                    break;

                case 5: // '\005'
                    value = "";
                    break;

                default:
                    value = cell.getRichStringCellValue().toString();
                    break;
            }
        return value;
    }

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFRow row;
}
