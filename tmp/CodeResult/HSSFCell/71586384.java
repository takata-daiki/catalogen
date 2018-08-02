/**  
* @(#)ExcelHandler.Java 1.00 2012/05/22  
*  
* Copyright (c) 2012 清华大学自动化系 Bigeye 实验室版权所有  
* Department of Automation, Tsinghua University. All rights reserved.
*    
* @author 宋成儒 
*   
* This software aims to extract title, time, source and text content 
* from news webpages. We grab news webpages from Baidu Rss and stored 
* them in Mysql database. To support the web demo of this project, we
* also provide with interfaces for web communication .  
*/ 
package com.Frank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/** ExcelHandler类支持将数据库中的数据导出到excel中，以便人工标注，
 * 也支持将标注后的结果导回数据库中 */
public class ExcelHandler {
	private String tablename;
	private DatabaseUtils daUtils;
	
	/** 生成函数
	 * @param table 导入导出涉及的数据表名 */
	public ExcelHandler(String table) {
		tablename = table;
		daUtils = new DatabaseUtils();
		try {
			daUtils.ps = daUtils.conn.prepareStatement("Update " + tablename + " SET TextTitle=?, Source=?, Time=?, Text=?, Video=? where Url=?");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 导出数据库数据到excel文件中
	 * @param file excel文件的存储位置
	 * @param res 包含要导出数据的sql结果集 
	 * @return 导出是否成功 */
	public boolean writeExcel(String file, ResultSet res)
	{		
		HSSFWorkbook workbook = new HSSFWorkbook();          
	    HSSFSheet sheet1 = workbook.createSheet("sheet1");
	    int count = 0;
	    try {
	    	while(res.next())
		    {
		    	HSSFRow row = sheet1.createRow(count); 
		    	row.createCell(0).setCellValue(res.getString("Url")); 
		    	count++;
		    }
	    	FileOutputStream fileOut = new FileOutputStream(file);     
		    workbook.write(fileOut);      
		    fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Output Failed!");
			return false;
		}  
		System.out.println("Output Ended!");
	    return true;
	}   
	   
	/** 导入人工标注的数据到数据库中
	 * @param is excel文件数据流
	 * @return 导入是否成功 */
	public boolean readExcel(InputStream is) {
		int selectCol[]={0,9,7,8,10,6};
		String fields[]={"","","","","","",""};
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			
			for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
				if (null != workbook.getSheetAt(numSheets)) {
					HSSFSheet aSheet = workbook.getSheetAt(numSheets);
					
					for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
						if (null != aSheet.getRow(rowNumOfSheet)) {
							HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
							
							fields[6] = "" + (rowNumOfSheet+1);
							for (int index = 0; index < 6; index++) {
								if (null != aRow.getCell(selectCol[index])) {
									HSSFCell aCell = aRow.getCell(selectCol[index]);

									if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
										if (HSSFDateUtil.isCellDateFormatted(aCell)) {
											double d = aCell.getNumericCellValue();
											Date date = HSSFDateUtil.getJavaDate(d);
											SimpleDateFormat sFormat = new SimpleDateFormat("yyyy年MM月dd");
											fields[index]=sFormat.format(date);
										} else {
											fields[index]="" + (long)aCell.getNumericCellValue();
										}
									}
									else {
										fields[index]=aCell.getStringCellValue();
									}
									fields[index]=fields[index].trim();
									
									if(fields[index].length() == 0) {
										if(index==5)
											fields[index]="0";
										else 
											fields[index]=" ";
									}
								}
								else {
									if(index==5)
										fields[index]="0";
									else 
										fields[index]=" ";
								}
							}
							
							daUtils.ps.setString(1, fields[1]);
							daUtils.ps.setString(2, fields[2]);
							daUtils.ps.setString(3, fields[3]);
							daUtils.ps.setString(4, fields[4]);
							daUtils.ps.setString(5, fields[5]);
							daUtils.ps.setString(6, fields[0]);
							daUtils.ps.executeUpdate();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/** 根据TFIDF值对关键词进行过滤
	 * @param is excel文件数据流 */
	public void tfidfFilter(InputStream is) {
		String currstr = "";
		double val[] = new double[11];
		int total = 0;
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			
			for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
				if (null != workbook.getSheetAt(numSheets)) {
					HSSFSheet aSheet = workbook.getSheetAt(numSheets);
					
					for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
						if (null != aSheet.getRow(rowNumOfSheet)) {
							HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
							
							for (int index = 0; index <= aRow.getLastCellNum(); index++) {
								if (null != aRow.getCell(index)) {
									HSSFCell aCell = aRow.getCell(index);
									if(aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
										currstr = aCell.getStringCellValue();
									} else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
										val[index-1] = aCell.getNumericCellValue();
									}
								}
							}
							
							int count = 0;
							for(double value : val) 
								if(value >= 0.2)
									count++;
							if(count >= 7) {
								total++;
								System.out.println(currstr);
							}
						}
					}
				}
			}
			System.out.println();
			System.out.println(total);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
//	public static void main(String args[]) {
//		ExcelHandler exHandler = new ExcelHandler("");
//		try {
//			exHandler.tfidfFilter(new FileInputStream("F:/Android/OnlineDemo/output/Extract-Tfidf.xls"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
