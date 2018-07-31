package com.jeasonzhao.commons.excel;

import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.CellValue;
import com.jeasonzhao.commons.utils.Algorithms;
import com.jeasonzhao.commons.utils.ConvertEx;
import com.jeasonzhao.commons.utils.DataTypes;

public class Cell
{
    private HSSFCell m_cell = null;
    private WorkBook m_workBook = null;
    private Sheet m_sheet = null;
    private Row m_row = null;
    public Cell(WorkBook workBook,Sheet sheet,Row row,HSSFCell cell)
    {
        m_cell = cell;
        m_workBook = workBook;
        m_sheet = sheet;
        m_row = row;
    }

    public String getSheetName()
    {
        return m_sheet.getName();
    }

    public String getExcelLoaction()
    {
        CellReference xr = new CellReference(this.getRowIndex(),this.getColumnIndex());
        return xr.formatAsString();
    }

    public int getRowIndex()
    {
        return this.m_cell.getRowIndex();
    }

    public int getColumnIndex()
    {
        return this.m_cell.getColumnIndex();
    }

    public Sheet getSheet()
    {
        return m_sheet;
    }

    public Row getRow()
    {
        return m_row;
    }

    public Cell setCellStyle(CellStyle c)
    {
        if(null != c)
        {
            c.bind(m_cell);
        }
        return this;
    }

    public Cell setType(CellType cellType)
    {
        m_cell.setCellType(cellType.getId());
        return this;
    }

    public CellType getType()
    {
        return(CellType) CellType.findConstant(CellType.class,m_cell.getCellType());
    }

    public Cell setValue(double value)
    {
        m_cell.setCellValue(value);
        return this;
    }

    public Cell setValue(int value)
    {
        m_cell.setCellValue((double) value);
        return this;
    }

    public Cell setValue(Date value)
    {
        m_cell.setCellValue(value);
        return this;
    }

    public Cell setValue(Calendar value)
    {
        m_cell.setCellValue(value);
        return this;
    }

    public Cell setValue(String value)
    {
        HSSFRichTextString text = new HSSFRichTextString(value);
        m_cell.setCellValue(text);
        return this;
    }

    public Cell setValue(Object value)
    {
        if(null == value)
        {
            this.m_cell.setCellValue((HSSFRichTextString)null);
            return this;
        }
        DataTypes dt = DataTypes.from(value.getClass());
        if(dt.equals(DataTypes.INT) || dt.equals(DataTypes.DOUBLE))
        {
            this.setValue(ConvertEx.toDouble(value));
        }
        else if(dt.equals(DataTypes.BOOL))
        {
            this.setValue(ConvertEx.toBool(value));
        }
        else if(dt.equals(DataTypes.DATE))
        {
            this.setValue(ConvertEx.toDate(value));
        }
        else
        {
            this.setValue(Algorithms.toString(value));
        }
        return this;
    }

    public Cell setFormula(String formula)
    {
        m_cell.setCellFormula(formula);
        return this;
    }

    public Cell setValue(boolean value)
    {
        m_cell.setCellValue(value);
        return this;
    }

    public String getFormula()
    {
        return m_cell.getCellFormula();
    }

    public boolean getBoolean()
    {
        return ConvertEx.toBool(this.getObject());
    }

    public double getDouble()
    {
        return ConvertEx.toDouble(this.getObject());
    }

    public java.util.Date getDate()
    {
        return ConvertEx.toDate(this.getObject());
    }

    public String getString()
    {
        return Algorithms.toString(this.getObject());
    }

    public Object getObject()
    {
        switch(m_cell.getCellType())
        {
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_BLANK:
                return "";
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_BOOLEAN:
                return Boolean.valueOf(m_cell.getBooleanCellValue());
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_ERROR:
                return null;
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_FORMULA:
                return getFormulaValue();
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_NUMERIC:
                return new Double(m_cell.getNumericCellValue());
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_STRING:
            default:
                org.apache.poi.hssf.usermodel.HSSFRichTextString text2 = m_cell.getRichStringCellValue();
                return text2.getString();
        }
    }

    private Object getFormulaValue()
    {
        if(null == m_cell || null == m_sheet || null == m_workBook)
        {
            return null;
        }
        HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(this.m_workBook.getHSSFWorkbook());
        CellValue obj = evaluator.evaluate(m_cell);
        if(null == obj)
        {
            return null;
        }
        switch(obj.getCellType())
        {
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_BLANK:
                return "";
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_BOOLEAN:
                return Boolean.valueOf(obj.getBooleanValue());
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_ERROR:
                return null;
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_NUMERIC:
                return new Double(obj.getNumberValue());
            case org.apache.poi.hssf.usermodel.HSSFCell.CELL_TYPE_STRING:
            default:
                return obj.getStringValue();
        }
    }

    public HSSFCell getHSSFCell()
    {
        return this.m_cell;
    }

    public CellStyle getCellStyle()
    {
        CellStyle ret = CellStyle.getCellStyle(this.m_workBook,m_cell.getCellStyle().getIndex());
        return ret;
    }

    public Cell setHyperLinkURL(String strAddress)
    {
        if(null != strAddress && strAddress.trim().length() > 0)
        {
            HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
            link.setAddress(strAddress);
            this.m_cell.setHyperlink(link);
        }
        return this;
    }

    public Cell setHyperLinkDocument(String sheetName,String strCell)
    {
        if(null == sheetName)
        {
            return this;
        }
        if(null == strCell)
        {
            strCell = "A1"; //Default is the first cell of the specified Sheet.
        }
        return setHyperLinkDocument("'" + sheetName + "'!" + strCell);
    }

    public Cell setHyperLinkDocument(Sheet sheet,String strCell)
    {
        return setHyperLinkDocument(null == sheet ? null : sheet.getName(),strCell);
    }

    public Cell setHyperLinkDocument(Sheet sheet,Cell cell)
    {
        return setHyperLinkDocument(null == sheet ? null : sheet.getName(),null == cell ? null : cell.getExcelLoaction());
    }

    public Cell setHyperLinkDocument(String sheetName,Cell cell)
    {
        return setHyperLinkDocument(sheetName,null == cell ? null : cell.getExcelLoaction());
    }

    public Cell setHyperLinkDocument(Sheet sheet,int nrow,int ncol)
    {
        CellReference xr = new CellReference(nrow,ncol);
        return setHyperLinkDocument(null == sheet ? null : sheet.getName(),xr.formatAsString());
    }

    public Cell setHyperLinkDocument(String strAddress)
    {
        if(null != strAddress && strAddress.trim().length() > 0)
        {
            HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
            link.setAddress(strAddress);
            this.m_cell.setHyperlink(link);
        }
        return this;
    }

    public Cell addComment(String strCommecnt)
    {
        return this.addComment(null,strCommecnt);
    }

    public Cell addComment(String strAuthor,String strCommecnt)
    {
        HSSFComment comment = this.m_cell.getCellComment();
        if(null == comment)
        {
            HSSFPatriarch hssfp = m_sheet.getHSSFPatriarch();
            comment = hssfp.createComment(new HSSFClientAnchor(0,0,0,0,
                (short) (this.getColumnIndex() + 1),this.getRowIndex(),
                (short) (this.getColumnIndex() + 5),this.getRowIndex() + 5));
            comment.setString(new HSSFRichTextString(strCommecnt));
            comment.setAuthor(strAuthor);
            m_cell.setCellComment(comment);
        }
        else
        {
            comment.setAuthor(strAuthor);
            comment.setString(new HSSFRichTextString(strCommecnt));
        }
        return this;
    }
}
