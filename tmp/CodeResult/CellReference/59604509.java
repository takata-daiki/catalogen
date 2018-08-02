/**
* Writes spreadsheet data in a Writer.
* (YK: in future it may evolve in a full-featured API for streaming data in Excel)
*/
	
/*
from the BigGridDemo code (http://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/xssf/usermodel/examples/BigGridDemo.java)
in order to enable generation of large xlsx files with limited memory
*/


package art.output;

import java.io.*;
import java.util.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.commons.lang.StringEscapeUtils; //to escape xml characters


/**
* Writes spreadsheet data in a Writer.
* (YK: in future it may evolve in a full-featured API for streaming data in Excel)
 */
public class SpreadsheetWriter {
	private Writer _out;
	private int _rownum;

    /**
     * 
     * @param out
     */
    public SpreadsheetWriter(Writer out){
		_out = out;
	}

    /**
     * 
     * @throws IOException
     */
    public void beginSheet() throws IOException {
		_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" );
		_out.write("<sheetData>\n");
	}

    /**
     * 
     * @throws IOException
     */
    public void endSheet() throws IOException {
		_out.write("</sheetData>");
		_out.write("</worksheet>");
	}

	/**
	* Insert a new row
	*
     * @param rownum 0-based row number
     * @throws IOException  
	*/
	public void insertRow(int rownum) throws IOException {
		_out.write("<row r=\""+(rownum+1)+"\">\n");
		this._rownum = rownum;
	}
	
    /**
     * 
     * @throws IOException
     */
    public void endRow() throws IOException {
		_out.write("</row>\n");
	}

    /**
     * 
     * @param columnIndex
     * @param value
     * @param styleIndex
     * @throws IOException
     */
    public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
		String ref = new CellReference(_rownum, columnIndex).formatAsString();
		_out.write("<c r=\""+ref+"\" t=\"inlineStr\"");
		if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
		_out.write(">");						
		_out.write("<is><t>"+StringEscapeUtils.escapeXml(value)+"</t></is>");
		_out.write("</c>");
	}

    /**
     * 
     * @param columnIndex
     * @param value
     * @throws IOException
     */
    public void createCell(int columnIndex, String value) throws IOException {
		createCell(columnIndex, value, -1);
	}
	
    /**
     * 
     * @param columnIndex
     * @param value
     * @param styleIndex
     * @throws IOException
     */
    public void createCell(int columnIndex, Date value, int styleIndex) throws IOException {
		createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
	}
	
	//DateUtil.getExcelDate returns a double
    /**
     * 
     * @param columnIndex
     * @param value
     * @param styleIndex
     * @throws IOException
     */
    public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
		String ref = new CellReference(_rownum, columnIndex).formatAsString();
		_out.write("<c r=\""+ref+"\" t=\"n\"");
		if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
		_out.write(">");
		_out.write("<v>"+value+"</v>");
		_out.write("</c>");
	}
	
    /**
     * 
     * @param columnIndex
     * @param value
     * @param styleIndex
     * @throws IOException
     */
    public void createCell(int columnIndex, Double value, int styleIndex) throws IOException {
		String ref = new CellReference(_rownum, columnIndex).formatAsString();
		_out.write("<c r=\""+ref+"\" t=\"n\"");
		if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
		_out.write(">");
		_out.write("<v>"+value+"</v>");
		_out.write("</c>");
	}

    /**
     * 
     * @param columnIndex
     * @param value
     * @param styleIndex
     * @throws IOException
     */
    public void createCell(int columnIndex, Long value, int styleIndex) throws IOException {
		String ref = new CellReference(_rownum, columnIndex).formatAsString();
		_out.write("<c r=\""+ref+"\" t=\"n\"");
		if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
		_out.write(">");
		_out.write("<v>"+value+"</v>");
		_out.write("</c>");
	}
}