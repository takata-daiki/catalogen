package ca.uwaterloo.fes.mia.v1.utility;

import uchicago.src.collection.DoubleMatrix;
import java.sql.*;


import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.record.formula.functions.Cell;
import org.apache.poi.hssf.record.formula.functions.Row;
import org.apache.poi.hssf.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar; 


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Checksum;

public class Utility {
	private static DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG);
	private static ArrayList<Double> objects;
	
	
	
	
	 
    public static void debugOut(String str) {
    	System.out.println("[" + df.format(new Date())
    			+ "] [Memory:" 
    			+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
    			+ "] " + str);
    }
    
    public static void mySQLDB(int run, String[] params){
    	  Connection conn = null;


    	 try {
    		 String userName = "MIAClient";
             String password = "mia";
             String url = "jdbc:mysql://localhost/miaresults";
             Class.forName ("com.mysql.jdbc.Driver").newInstance ();
             conn = DriverManager.getConnection (url, userName, password);
             debugOut("Connected to MySQLDB");
           
             
             try
             {
                 Statement s = conn.createStatement ();
                 s.executeUpdate (
                         "INSERT INTO tblRun (run, params)"
                         + " VALUES"
                         + "('" + run + "', 'Test')");
                 
                 //s.close ();
             }
             catch (SQLException e)
             {
                 System.err.println ("Error message: " + e.getMessage ());
                 System.err.println ("Error number: " + e.getErrorCode ());
             }

		} catch (Exception e) {
			debugOut("Oh noz");
		}
    }
   
    /*
    public static void updateMySQLDB(int run, int step, String variable){

        try
        {
     //       Statement s = conn.createStatement ();
            s.executeUpdate (
                    "INSERT INTO tblRun (run, params)"
                    + " VALUES"
                    + "('" + run + "', 'Test')");
            
            //s.close ();
        }
        catch (SQLException e)
        {
            System.err.println ("Error message: " + e.getMessage ());
            System.err.println ("Error number: " + e.getErrorCode ());
        }
    }*/

   public static ArrayList<Double> readFromFile(String fileName){
       BufferedReader reader = null;
       objects = new ArrayList<Double>();
       try
       {
           reader = new BufferedReader(new FileReader(fileName));
           //reader.
           String text = null;
           // repeat until all lines are read
           while ((text = reader.readLine()) != null)
           {
        	  // System.out.println(text);
               objects.add(Double.parseDouble(text));
           }
       } catch (FileNotFoundException e)
       {
           e.printStackTrace(); 
       } catch (IOException e)
       {
           e.printStackTrace();
       } finally
       {
           try
           {
               if (reader != null)
               {
                   reader.close();
               }
           } catch (IOException e)
           {
               e.printStackTrace();
           }
       }   
       return objects;
   }
   
   public static ArrayList<Double> readFromFileSecondary(String fileName){
       BufferedReader reader = null;
       objects = new ArrayList<Double>();
       try
       {
           reader = new BufferedReader(new FileReader(fileName));
           //reader.
           String text = null;
           // repeat until all lines are read
           while ((text = reader.readLine()) != null)
           {
        	  // System.out.println(text);
               objects.add(Double.parseDouble(text));
           }
       } catch (FileNotFoundException e)
       {
           e.printStackTrace(); 
       } catch (IOException e)
       {
           e.printStackTrace();
       } finally
       {
           try
           {
               if (reader != null)
               {
                   reader.close();
               }
           } catch (IOException e)
           {
               e.printStackTrace();
           }
       }   
       return objects;
   }
   
   /* work book info! DONT ANGER HIM, i realize its depreciated who cares
    * var 0 Date
    * var 1 Vector Population
    */
public static void writeToWorkbook(int i, int step, int var){
	try {
		InputStream myxls = new FileInputStream("data/results.xls");
		HSSFWorkbook wb     = new HSSFWorkbook(myxls);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row     = sheet.getRow(step);
		HSSFCell c = row.getCell(var);
		c.setCellValue(String.valueOf(i));	
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void creatNewWorkbook() throws IOException{
	try {
		// create a new file
		FileOutputStream out = new FileOutputStream("data/results.xls");
		// create a new workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		// create a new sheet
		HSSFSheet s = wb.createSheet();
		// declare a row object reference
		HSSFRow r = null;
		// declare a cell object reference
		HSSFCell c = null;
		// create 3 cell styles
		HSSFCellStyle cs = wb.createCellStyle();
		HSSFCellStyle cs2 = wb.createCellStyle();
		HSSFCellStyle cs3 = wb.createCellStyle();
		HSSFDataFormat df = wb.createDataFormat();
		// create 2 fonts objects
		HSSFFont f = wb.createFont();
		HSSFFont f2 = wb.createFont();

		//set font 1 to 12 point type
		f.setFontHeightInPoints((short) 12);
		//make it blue
		f.setColor( (short)0xc );
		// make it bold
		//arial is the default font
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		//set font 2 to 10 point type
		f2.setFontHeightInPoints((short) 10);
		//make it red
		f2.setColor( (short)HSSFFont.COLOR_RED );
		//make it bold
		f2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		f2.setStrikeout( true );

		//set cell stlye
		cs.setFont(f);
		//set the cell format 
		cs.setDataFormat(df.getFormat("#,##0.0"));

		//set a thin border
		cs2.setBorderBottom(cs2.BORDER_THIN);
		//fill w fg fill color
		cs2.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
		//set the cell format to text see DataFormat for a full list
		cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

		// set the font
		cs2.setFont(f2);

		// set the sheet name in Unicode
		//wb.setSheetName(0, "\u0422\u0435\u0441\u0442\u043E\u0432\u0430\u044F " + 
		//                   "\u0421\u0442\u0440\u0430\u043D\u0438\u0447\u043A\u0430" );
		// in case of plain ascii
		 wb.setSheetName(0, "HSSF Test");
		// create a sheet with 30 rows (0-29)
		 
		 DateFormat datef = new SimpleDateFormat("ddMMMyyyy");
		 GregorianCalendar modelDate = new GregorianCalendar(1992, 01, 01);
		 
		int rownum;
		for (rownum = (short) 0; rownum < 3300; rownum++)
		{
		    // create a row
		    r = s.createRow(rownum);
		    // on every other row
	

		    //r.setRowNum(( short ) rownum);
		    // create 10 cells (0-9) (the += 2 becomes apparent later
		    for (short cellnum = (short) 0; cellnum < 1; cellnum += 2)
		    {
		        // create a numeric cell
		        c = r.createCell(cellnum);
		        // do some goofy math to demonstrate decimals
		        
		       

		      
		        
		        c.setCellValue(datef.format(modelDate.getTime()));
		        modelDate.add(modelDate.DATE, 1);
		        
		        String cellValue;

		        // create a string cell (see why += 2 in the
		        c = r.createCell((short) (cellnum + 1));
		        
		   
		 
		          //  c.setCellStyle(cs2);
		            // set the cell's string value to "\u0422\u0435\u0441\u0442"
		            c.setCellValue( "" );
		  


		    }
		}




		wb.write(out);
		out.close();

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

}
    
    /**
     * Append string to end of file.
     * 
     * @param str Text to append.
     * @param fileName The filename to open.
     */
    public static void writeToFile(String str, String fileName) {
    	try {
			BufferedWriter w = new BufferedWriter(
					new FileWriter(fileName, true));
			w.write(str);
			w.newLine();
			w.flush();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * <p>Reads a generic, headerless (data-only) delimited file into a DoubleMatrix.
     * 
     * @param fileName The delimited file.
     * @param width The number of columns of data to read.
     * @param height The number of rows of data to read.
     * @return DoubleMatrix The data contained in the delimited file.
     */
    public static DoubleMatrix readDelimitedFile(String fileName, int width, int height) {
        DoubleMatrix matrix = null;
        try {
            Reader r = new BufferedReader(new FileReader(fileName));
            StreamTokenizer st = new StreamTokenizer(r);
            st.eolIsSignificant(true);
            st.parseNumbers();
            st.whitespaceChars(',', ',');
            st.whitespaceChars('\t', '\t');
            st.whitespaceChars(' ', ' ');
            
            int row = 0;
            int col = 0;
            matrix = new DoubleMatrix(width, height);
            while (st.ttype != StreamTokenizer.TT_EOF) {
                st.nextToken();
                if (st.ttype == StreamTokenizer.TT_NUMBER) {
                    matrix.putDoubleAt(col, row, st.nval);
                    col++;
                } else if (st.ttype == StreamTokenizer.TT_EOL) {
                    row++;
                    col = 0;
                }
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return matrix;
    }
    
    public static long getChecksumValue(Checksum checksum, String fname) {
        try {
            BufferedInputStream is = new BufferedInputStream(
                new FileInputStream(fname));
            byte[] bytes = new byte[1024];
            int len = 0;

            while ((len = is.read(bytes)) >= 0) {
                checksum.update(bytes, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checksum.getValue();
    }
}