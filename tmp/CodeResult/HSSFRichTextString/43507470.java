package com.bp.pensionline.sqlreport.app.poi;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;


import org.apache.commons.logging.Log;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.opencms.main.CmsLog;

import com.bp.pensionline.database.DBConnector;




public class PLReportPOIProducer
{
	public static final Log LOG = CmsLog.getLog(PLReportPOIProducer.class);
	
	/**
	 * Generate a report section with data source and query provided
	 * @param dataSource
	 * @param query
	 * @return
	 */
	public HSSFWorkbook generateReportSectionToXLS(String database, String query)
	{
		HSSFWorkbook    workBook = null;
		
		Connection connection = null;
		try
		{
			connection = DBConnector.getInstance().establishConnection(database);
			
			System.out.println("connection: " + connection);
		}
		catch (SQLException sqle)
		{
			LOG.error("Error in establishing SQL connection to " + database + ": " + sqle.toString());
		} 
		
		// get the template file
		try
		{
			if (connection != null && query != null)
		    {
				workBook = new HSSFWorkbook ();
				HSSFSheet sheet = workBook.createSheet();
				
			    HSSFCellStyle cellStyleHeader = workBook.createCellStyle();
			    cellStyleHeader.setWrapText(true);
			    cellStyleHeader.setVerticalAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			    cellStyleHeader.setFillForegroundColor(HSSFColor.GREEN.index);
			    cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);			
			    HSSFFont headerCellFont = workBook.createFont();
			    headerCellFont.setFontHeightInPoints((short)10);
			    headerCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			    headerCellFont.setColor(HSSFColor.WHITE.index);
			    cellStyleHeader.setFont(headerCellFont);								
			    			    
			    HSSFCellStyle cellStyleData = workBook.createCellStyle();
			    cellStyleData.setWrapText(true);
			    cellStyleData.setVerticalAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			    HSSFFont dataCellFont = workBook.createFont();
			    dataCellFont.setFontHeightInPoints((short)10);
			    dataCellFont.setColor(HSSFColor.GREY_80_PERCENT.index);	
			    cellStyleData.setFont(dataCellFont);	    
	    
			    /*
			     * Start generate the report
			     */
		    
				PreparedStatement pstm = connection.prepareStatement(query);						
				
				ResultSet rs = pstm.executeQuery();
				// get column names from ResutlSet MetaData
				ResultSetMetaData rsMD = rs.getMetaData();
				
				int numCols = rsMD.getColumnCount();
				String[] columnClassNames = new String[numCols];
				// generate header
				int rowIndex = 0;
				HSSFRow headerRow = sheet.createRow(0);
				HSSFCell headerCell = null;
				
				for (int i = 0; i < numCols; i++)
				{
					String colName = rsMD.getColumnName(i + 1);			
					//System.out.println("colName: " + colName);
					String className = rsMD.getColumnClassName(i + 1);
					if (className != null){
						if(className.equals("java.math.BigInteger")) 
							className = "java.lang.Long";
						if(className.equals("java.sql.Clob")) 
							className = "java.lang.String";
					}
					columnClassNames[i] = className;
					
					headerCell = headerRow.createCell(i);			    	
			    	headerCell.setCellStyle(cellStyleHeader);
			    	headerCell.setCellValue(new HSSFRichTextString(colName));
				}
				
				
				// generate data rows
				rowIndex++;
				while (rs.next())
				{
					System.out.println("Add row: " + rowIndex);
					HSSFRow row = sheet.createRow(rowIndex++);
			
					for (int i = 0; i < columnClassNames.length; i++)
					{
						
						HSSFCell dataCell = row.createCell(i);
						String dataCellContent = rs.getString(i + 1);
						
						dataCell.setCellStyle(cellStyleData);
						sheet.autoSizeColumn((short)i);
						dataCell.setCellValue(new HSSFRichTextString(dataCellContent));
						
					}
				}
				
		    }
			
		}
		catch (Exception e)
		{
			LOG.error("Error while generating regulations to XLS: " + e.toString());
		}
		
		
		return workBook;
	}

	public byte[] generateReportSectionToCSV (String database, String query)
	{
		Connection connection = null;
		byte[] content = null;
		try
		{
			connection = getDBConn(database);//DBConnector.getInstance().establishConnection(database);
			
			System.out.println("connection: " + connection);
		}
		catch (SQLException sqle)
		{
			LOG.error("Error in establishing SQL connection to " + database + ": " + sqle.toString());
		} 
		
		// get the template file
		try
		{
			if (connection != null && query != null)
		    {				  
				ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
				OutputStreamWriter streamWriter = new OutputStreamWriter(byteOutputStream);
			    /*
			     * Start generate the report
			     */
		    
				PreparedStatement pstm = connection.prepareStatement(query);						
				
				ResultSet rs = pstm.executeQuery();
				// get column names from ResutlSet MetaData
				ResultSetMetaData rsMD = rs.getMetaData();
				
				int numCols = rsMD.getColumnCount();
				String[] columnClassNames = new String[numCols];
				// generate header
				int rowIndex = 0;
				
				StringBuffer headerRow = new StringBuffer();
				for (int i = 0; i < numCols; i++)
				{
					String colName = rsMD.getColumnName(i + 1);			
					//System.out.println("colName: " + colName);
					String className = rsMD.getColumnClassName(i + 1);
					if (className != null){
						if(className.equals("java.math.BigInteger")) 
							className = "java.lang.Long";
						if(className.equals("java.sql.Clob")) 
							className = "java.lang.String";
					}
					columnClassNames[i] = className;
					
					headerRow.append("\"" + colName + "\"");
					if (i < numCols - 1)
					{
						headerRow.append(",");
					}					
				}
				headerRow.append("\n");
				streamWriter.write(headerRow.toString());
				
				
				// generate data rows
				rowIndex++;
				while (rs.next())
				{
					StringBuffer dataRow = new StringBuffer();
					System.out.println("Add row: " + rowIndex);
			
					for (int i = 0; i < columnClassNames.length; i++)
					{						
						String dataCellContent = rs.getString(i + 1);
						dataRow.append("\"" + dataCellContent + "\"");
						if (i < numCols - 1)
						{
							dataRow.append(",");
						}
						
					}
					
					dataRow.append("\n");
					streamWriter.write(dataRow.toString());
					
					rowIndex++;
				}
				
				streamWriter.flush();
				content = byteOutputStream.toByteArray();
				//byteOutputStream.close();
		    }
			
		}
		catch (Exception e)
		{
			LOG.error("Error while generating regulations to XLS: " + e.toString());
		}
		
		return content;
	}
	
	public static void main(String[] args)
	{
		PLReportPOIProducer producer = new PLReportPOIProducer();
		
		String query = producer.readQueryFromFile("F:/before_se.txt");
		
		byte[] content = producer.generateReportSectionToCSV("aquila", query);
		if (content != null)
		{
			try
			{
				producer.outputToFileSystem("F:/section.csv", content);
				System.out.println("Export Done!");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
   private Connection getDBConn(String jndiEnv) throws SQLException{
	   
	   Connection conn = null;
	   try {
    	  
		   //log.info("Create a new connection for DBConnector, OracleConnectionCacheImpl");
		   
		   /* Create an instance of ConnCacheBean */
//		   ConnCacheBean connCacheBean = ConnCacheBean.getInstance();
//		      
//		   /* Get OracleDataSource from the ConnCacheBean */
//		   OracleDataSource ods = connCacheBean.getDataSource();
		   String driverName = "oracle.jdbc.driver.OracleDriver";
		   Class.forName(driverName);
//		   String url = CheckConfigurationKey.getStringValue("calcURL");
//		   String userName = CheckConfigurationKey.getStringValue("calcUserName");
//		   String password = CheckConfigurationKey.getStringValue("calcPassword");
//		   conn = DriverManager.getConnection(url, userName, password);
		   
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@127.1.1.204:4522:BP101S", "BPPL4CMS", "BPPL4CMS"); 
		   
	   } 
	   catch (Exception ex) {
		  //LOG.error("Get a connection from ods is fail", ex);
    	  ex.printStackTrace();
	   }
	  
	   return conn;
   }
   
   /**
   * 
   * @param fileName
   * @return
   */
   private String readQueryFromFile (String fileName)
   {
   		String query = null;
   		StringBuffer queryBuffer = null;
   	
   		try
   		{
	   		File queryFile = new File(fileName);
	   		if (queryFile.exists())
	   		{	
	   			queryBuffer = new StringBuffer();
	   			FileInputStream queryFileInputStream = new FileInputStream(queryFile);
	   			String NL = System.getProperty("line.separator");
	   		    Scanner scanner = new Scanner(queryFileInputStream, "UTF-8");
	   		    try 
	   		    {
	   		    	while (scanner.hasNextLine())
	   		    	{
	   		    		queryBuffer.append(scanner.nextLine() + NL);
	   		    	}
	   		    }
	   		    finally
	   		    {
	   		    	scanner.close();
	   		    }
	   			
	   			query = queryBuffer.toString();
	   		}
	   	}
	   	catch (Exception e)
	   	{
	   		LOG.error("Error while reading query from " + fileName +  ": " + e.toString());
	   	}
   	
	   	return query;
   }
   
	private void outputToFileSystem(String fileName, HSSFWorkbook workbook) throws IOException
	{
		File outputFile = new File(fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		workbook.write(fileOutputStream);
		
		fileOutputStream.close();
	}   
	
	public void outputToFileSystem(String fileName, byte[] reportContent) throws IOException
	{
		File outputFile = new File(fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		fileOutputStream.write(reportContent);
		fileOutputStream.flush();
		fileOutputStream.close();
	}	
}

	