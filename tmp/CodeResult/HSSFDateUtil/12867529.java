package com.ett.common.tools;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 */

/**
 * ???Excel?????????1?7
 * ?????????????parseRow??
 * ??????new?1?7?????????????beginImport????
 * @author chen
 * @version 1.0
 * @since 1.5
 */
public abstract class BaseExcelImporter implements Importer {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	private int beginRowIndex=1;
	
	private int endRowIndex=1;
	
	/**
	 * ?1?7?????1?7
	 * @throws IOException
	 */
	public  void beginImport() throws IOException
	{
		log.debug("???????");
		InputStream myxls = new FileInputStream(this.path);
		HSSFWorkbook wb = new HSSFWorkbook(myxls);
		HSSFSheet sheet = wb.getSheetAt(0);
		//?????????????????1?7
		for (int i = beginRowIndex; i <= endRowIndex; i++) {
			try
			{
				parseRow(sheet.getRow(i));
			}
			catch(Exception ex)
			{
				log.debug("????"+i+"??????????");
				break;
			}
		}
		
		myxls.close();
		log.debug("???????1?7");
	}
	
	/**
	 * ?????????1?7
	 * ???1?7???????1?7
	 * @param row excel???1?7
	 */
	protected abstract void parseRow(HSSFRow row) throws Exception;
	
	
	/**
	 *???excel???????1?7 
	 */
	private String path;
	
	/**
	 * ?Excel???????1?7?????1?7,????
	 * ??????????1?7???????????????1?7
	 * ??????????
	 * @param cell ???1?7
	 * @return ???????1?7
	 */
	protected  Object readFromCell(HSSFCell cell) {
		Object result=null;
		if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			result=cell.getStringCellValue();
			log.debug("???????????1?7 " +result );
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				result=cell.getDateCellValue();
				log.debug("????????1?7???1?7"
						+ result);
			}
			else
			{
				result=cell.getNumericCellValue();
				log.debug("????????1?7???1?7 " +result );
			}
		} else {
			result=cell.getCellType();
			log.debug("??????????????1?7?????1?7"+result);
		}
		return result;

	}

	/**
	 * ???excel???????1?7
	 * @return 
	 */
	public String getPath() {
		return path;
	}

	/**
	 * ???excel???????1?7
	 * @param path 
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * excel??????0??
	 * @return
	 */
	public int getBeginRowIndex() {
		return beginRowIndex;
	}

	/**
	 * excel??????0??
	 * @param beginRowIndex
	 */
	public void setBeginRowIndex(int beginRowIndex) {
		this.beginRowIndex = beginRowIndex;
	}

	/**
	 * excel??????0??
	 * @return
	 */
	public int getEndRowIndex() {
		return endRowIndex;
	}

	/**
	 * excel??????0??
	 * @param endRowIndex
	 */
	public void setEndRowIndex(int endRowIndex) {
		this.endRowIndex = endRowIndex;
	}


}
