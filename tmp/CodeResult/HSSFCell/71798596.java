package com.hybrid;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.selenium.*;

public class Hybrid_Framework extends SeleneseTestCase{
	public String xTC[][], xTS[][], xTD[][]; // 2-D array to hold Test Cases and Steps. GLOBAL.
	public int xTC_rows, xTC_cols, xTS_rows, xTS_cols, xTD_rows, xTD_cols;
	public String vTDID, vExecute, vURL, vBrowser, vName, vUserName, vPswd, vEmail, vCity, vZip, vTechnology, vTitle;
	public String vKeyword, vIP1, vIP2;
	public String vResult, vError; 
	public String xlPath;
	@Before
	public void setUp() throws Exception {
		xlPath = "C:/Selenium/Jul5/HF/Automation Plan.xls";
		System.out.println("@Before");
		xlR_TC(xlPath); // Read Test Cases
		xlR_TS(xlPath); // Read Test Steps
		xlR_TD(xlPath); // Read Test Data
		//selenium = new DefaultSelenium("localhost", 1236, "*iehta", "http://www.dice.com");
		selenium = new DefaultSelenium("localhost", 1236, "*chrome", "http://www.chasestudentloans.com/");
		selenium.start();
		selenium.setSpeed("1000"); // set sel speed of run
		selenium.windowMaximize(); // make browser max.
		selenium.windowFocus(); // focus on that window
	}
	
	@Test
	public void testBlock() throws Exception{
		String vTCID, vTSID;
		String vTC_flag;
		// Go to each Test Data row
        for (int k = 1; k < xTD_rows; k++) {
		// Test Data being initialized
		vTDID =xTD[k][0];
		vExecute =xTD[k][1];
		vURL = xTD[k][2];
		vBrowser =xTD[k][3];
		vName =xTD[k][4];
		vUserName =xTD[k][5];
		vPswd =xTD[k][6];
		vEmail =xTD[k][7];
		vCity =xTD[k][8];
		vZip =xTD[k][9];
		vTechnology =xTD[k][10];
		vTitle = xTD[k][11];
			if (vExecute.equals("Y")){
				// Go to each Test Case row
		        for (int i = 1; i < xTC_rows; i++) {
		        	if(xTC[i][3].equals("Y")){ // Only if TC is set to Y
		        		vTCID = xTC[i][0]; // Get TCID
		        		System.out.println("Now executing TC# " + vTCID);
		        		vTC_flag = "Pass"; // default that TC has passed
		        		// Go to each Test Step row
		                for (int j = 1; j < xTS_rows; j++) {
		                	vTSID = xTS[j][0]; // Get TSID 
		                	if(vTCID.equals(vTSID)){ // Compare TC and TS ID's
		                    	System.out.println("Now executing TS# " + xTS[j][2]);
		                		vKeyword = xTS[j][4]; 
		                		vIP1 = xTS[j][5];
		                		vIP2 = xTS[j][6];
		                		System.out.println("Keyword to execute is " + vKeyword);
		                		System.out.println("IP1 is " + vIP1 + " IP2 is " + vIP2);
		                		vResult = "Pass";
		                		vError = "None";
		                		vResult = keyword_executor(); // Main execution
		                		xTS[j][7] = vResult;
		                		xTS[j][8] = vError;
		                		if (vResult.equals("Fail")){
		                			vTC_flag = "Fail";
		                			selenium.captureScreenshot("C:/Selenium/Jul5/HF/Result/err_img_td_"+k+"_tc_"+i+"_ts_"+xTS[j][2]+".jpg"); // to a specific jpg file
		                		}
		                	}
			             }
		                xTC[i][4] = vTC_flag;
			        }	
		        }
			}
			String xlPath_tcres = "C:/Selenium/Jul5/HF/Result/TC_Res_"+vTDID+"_.xls";
			String xlPath_tsres = "C:/Selenium/Jul5/HF/Result/TS_Res_"+vTDID+"_.xls";
			System.out.println("@After writing results into excel");
			xlwrite_tc(xlPath_tcres, xTC);
			xlwrite_ts(xlPath_tsres, xTS);
        }
	}
	@After
	public void tearDown() throws Exception{
	//public void afterBlock(){
		System.out.println("All done");
		selenium.stop();
	}
	public String keyword_executor() throws Exception{
		// Call the corresponding keyword to execute
		String vElse;
		vElse = "True";
		try { // Java, try the below code, if it fails, catch the error.
			get_IP1_IP2();
			if(vKeyword.equals("sOpen")){
				vElse = "False";
				return sOpen(vIP1, vIP2);
			}
			if(vKeyword.equals("sType")){
				sType(vIP1, vIP2);
				vElse = "False";
				return "Pass";
			}
			if(vKeyword.equals("sVerifyElementPresent")){
				vElse = "False";
				return sVerifyElementPresent(vIP1);
			}
			if(vKeyword.equals("sGetAndCompareText")){
				vElse = "False";
				return sGetAndCompareText(vIP1, vIP2);
			}
			if(vKeyword.equals("sGetValueAndCompare")){
				vElse = "False";
				return sGetValueAndCompare(vIP1, vIP2);
			}
			if(vKeyword.equals("sClickAndWait")){
				sClickAndWait(vIP1, vIP2);
				vElse = "False";
				return "Pass";
			}
			if(vKeyword.equals("sVerifyTextAny")){
				vElse = "False";
				return sVerifyTextAny(vIP1);
			}
			if (vElse.equals("True")){
				System.out.println("Keyword is missing" + vKeyword);
				return "False-Keyword Missing";
			}
		}
		catch (SeleniumException myerror){
			//System.out.println("Error occurred when executing keyword " + vKeyword);
			//System.out.println("Error is : " + myerror);
			vError = String.valueOf(myerror);
			return "Fail";
		}
		return "UNKNOWN ISSUE";
	}
	
	public void get_IP1_IP2(){
		// Assign test data variables to the inputs
		if (vIP1.equals("vURL")){
			vIP1 = vURL;
		}
		if (vIP1.equals("vUserName")){
			vIP1 = vUserName;
		}
		if (vIP1.equals("vPswd")){
			vIP1 = vPswd;
		}
		if (vIP1.equals("vCity")){
			vIP1 = vCity;
		}
		if (vIP1.equals("vZip")){
			vIP1 = vZip;
		}
		if (vIP1.equals("vTechnology")){
			vIP1 = vTechnology;
		}
		if (vIP1.equals("vTitle")){
			vIP1 = vTitle;
		}
		if (vIP2.equals("vURL")){
			vIP2 = vURL;
		}
		if (vIP2.equals("vUserName")){
			vIP2 = vUserName;
		}
		if (vIP2.equals("vPswd")){
			vIP2 = vPswd;
		}
		if (vIP2.equals("vCity")){
			vIP2 = vCity;
		}
		if (vIP2.equals("vZip")){
			vIP2 = vZip;
		}
		if (vIP2.equals("vTechnology")){
			vIP2 = vTechnology;
		}
		if (vIP2.equals("vTitle")){
			vIP2 = vTitle;
		}

	}
	
	public String sGetAndCompareText(String eID, String vStr1){
		String vStr2 = sGetText(eID);
		if (vStr1.equals(vStr2)){
			return "Pass";
		} else {
			return "Fail";
		}
	}
	public String sGetValueAndCompare(String eID, String vStr1){
		String vStr2 = selenium.getValue(eID);
		if (vStr1.equals(vStr2)){
			return "Pass";
		} else {
			return "Fail";
		}
	}

	public String sVerifyElementPresent(String eID){
		// Purpose: Verifies if an element is present
		// I/P : ID
		// O/P : pass or fail
		if (selenium.isElementPresent(eID)){
			return "Pass";
		} else {
			return "Fail";
		}
	}
	public String sOpen(String vURL, String vTitle){
		// Purpose: Navigates the browser to a URL. For root we use "/"
		// I/P : Destination URL 
		// O/P : nothing
		selenium.open(vURL);
		if (selenium.getTitle().equals(vTitle)) {
			return "Pass";
		} else {
			return "Fail";
		}
			
		
	}
	public void sType(String eID, String sText){
		// Purpose: Puts a text in the edit box
		// I/P : Ele id and the text
		// O/P : nothing
		selenium.type(eID, sText);
	}
	public void sSelect(String eID, String sText){
		// Purpose: Selects an item from a drop down list
		// I/P : Ele id and the text
		// O/P : nothing
		//selenium.type(eID, "label="+sText);
		selenium.select(eID, sText);
	}
	public void sClick(String eID){
		// Purpose: click on Link, button, text, image ... doesnt matter
		// I/P : Ele id 
		// O/P : nothing
		selenium.click(eID);
	}
	public void sWait(String eTime){
		// Purpose: wait for n secs
		// I/P : Amount of time in ms
		// O/P : nothing
		selenium.waitForPageToLoad(eTime);
	}
	public void sClickAndWait(String eID, String eTime){
		// Purpose: click and wait on Link, button, text, image ... doesnt matter
		// I/P : Ele id and time 
		// O/P : nothing
		sClick(eID);
		sWait(eTime);
	}
	public String sGetText(String eID){
		// Purpose: get a text
		// I/P : eID
		// O/P : nothing
		return selenium.getText(eID);
	}
	public String sGetValue(String eID){
		// Purpose: gets the values inside elements
		// I/P : eID
		// O/P : nothing
		return selenium.getValue(eID);
	}
	public String sVerifyText(String eID, String sText){
		// Purpose: gets the values inside elements
		// I/P : eID
		// O/P : nothing
		if (selenium.getText(eID).equals(sText)) {
			return "Pass";
		} else {
			return "Fail";
		}
	}
	public String sVerifyTextAny(String sText){
		// Purpose: gets the values inside elements
		// I/P : eID
		// O/P : nothing
		if (selenium.isTextPresent(sText)) {
			return "Pass";
		} else {
			return "Fail";
		}
	}
	public void myprint(String mymessage){
		System.out.println(mymessage);
		System.out.println("~~~~~~~~~~~~~");
	}
	public void xlR_TC(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(1);	// Referring to 2nd sheet
		xTC_rows = mySheet.getLastRowNum()+1;
		xTC_cols = mySheet.getRow(0).getLastCellNum();
		myprint("Rows are " + xTC_rows);
		myprint("Cols are " + xTC_cols);
		xTC = new String[xTC_rows][xTC_cols]; // Giving the size of the array
        for (int i = 0; i < xTC_rows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xTC_cols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xTC[i][j] = value;
	               System.out.print(value);
	               System.out.print("@@");
	               }
	            System.out.println("");
	        }	
	}
	
	public void xlR_TS(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(2);	// Referring to 3rd sheet
		xTS_rows = mySheet.getLastRowNum()+1;
		xTS_cols = mySheet.getRow(0).getLastCellNum();
		myprint("TS Rows are " + xTS_rows);
		myprint("TS Cols are " + xTS_cols);
		xTS = new String[xTS_rows][xTS_cols]; // Giving the size of the array
        for (int i = 0; i < xTS_rows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xTS_cols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xTS[i][j] = value;
	               System.out.print(value);
	               System.out.print("@@");
	               }
	            System.out.println("");
	        }	
	}
	public void xlR_TD(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(3);	// Referring to 2nd sheet
		xTD_rows = mySheet.getLastRowNum()+1;
		xTD_cols = mySheet.getRow(0).getLastCellNum();
		myprint("Rows are " + xTD_rows);
		myprint("Cols are " + xTD_cols);
		xTD = new String[xTD_rows][xTD_cols]; // Giving the size of the array
        for (int i = 0; i < xTD_rows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xTD_cols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xTD[i][j] = value;
	               System.out.print(value);
	               System.out.print("@@");
	               }
	            System.out.println("");
	        }	
	}
	public static String cellToString(HSSFCell cell) {
	// This function will convert an object of type excel cell to a string value
        int type = cell.getCellType();
        Object result;
        switch (type) {
            case HSSFCell.CELL_TYPE_NUMERIC: //0
                result = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_STRING: //1
                result = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_FORMULA: //2
                throw new RuntimeException("We can't evaluate formulas in Java");
            case HSSFCell.CELL_TYPE_BLANK: //3
                result = "-";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN: //4
                result = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_ERROR: //5
                throw new RuntimeException ("This cell has an error");
            default:
                throw new RuntimeException("We don't support this cell type: " + type);
        }
        return result.toString();
    }
	public void xlwrite_tc(String xlPath, String[][] xldata) throws Exception {
		System.out.println("Inside XL Write for Test Case");
    	File outFile = new File(xlPath);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet osheet = wb.createSheet("TestCasesResults");
    	for (int myrow = 0; myrow < xTC_rows; myrow++) {
	        HSSFRow row = osheet.createRow(myrow);
	        for (int mycol = 0; mycol < xTC_cols; mycol++) {
	        	HSSFCell cell = row.createCell(mycol);
	        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	cell.setCellValue(xldata[myrow][mycol]);
	        }
	        FileOutputStream fOut = new FileOutputStream(outFile);
	        wb.write(fOut);
	        fOut.flush();
	        fOut.close();
    	}
    }
	public void xlwrite_ts(String xlPath, String[][] xldata) throws Exception {
		System.out.println("Inside XL Write for Test Step");
    	File outFile = new File(xlPath);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet osheet = wb.createSheet("TestStepsResults");
    	for (int myrow = 0; myrow < xTS_rows; myrow++) {
	        HSSFRow row = osheet.createRow(myrow);
	        for (int mycol = 0; mycol < xTS_cols; mycol++) {
	        	HSSFCell cell = row.createCell(mycol);
	        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	cell.setCellValue(xldata[myrow][mycol]);
	        }
	        FileOutputStream fOut = new FileOutputStream(outFile);
	        wb.write(fOut);
	        fOut.flush();
	        fOut.close();
    	}
    }
}
