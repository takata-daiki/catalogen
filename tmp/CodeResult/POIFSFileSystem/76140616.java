package com.springtour.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Test {

	public static void main(String[] args) throws Exception {
		FileInputStream fs = new FileInputStream("d:/prepare_for_exportTxn.xls");
		POIFSFileSystem ps = new POIFSFileSystem(fs);
		HSSFWorkbook wb = new HSSFWorkbook(ps);
		HSSFSheet sheet = wb.getSheetAt(1);
		HSSFRow row = sheet.getRow(0);
		FileOutputStream out = new FileOutputStream(
				"d:/prepare_for_exportTxn.xls");
		row = sheet.createRow((short) (sheet.getLastRowNum() + 1));
		row.createCell((short) 0).setCellValue(22);
		row.createCell((short) 1).setCellValue(11);
		row.createCell((short) 2).setCellValue(11);
		row.createCell((short) 3).setCellValue(11);
		row.createCell((short) 4).setCellValue(11);
		row.createCell((short) 5).setCellValue(11);
		row.createCell((short) 6).setCellValue(11);
		row.createCell((short) 7).setCellValue(11);
		row.createCell((short) 8).setCellValue(11);
		row.createCell((short) 9).setCellValue(33);
		out.flush();
		wb.write(out);
		out.close();
	}
}