package com.jeasonzhao.commons.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

public class WorkBook
{
    private org.apache.poi.hssf.usermodel.HSSFWorkbook m_workBook = null;
    private WorkBook()
    {
    }

    public WorkBook(String strFileName)
        throws java.io.IOException
    {
        open(strFileName);
    }

    public static WorkBook file(String strFileName)
        throws java.io.IOException
    {
        return new WorkBook(strFileName);
    }

    public org.apache.poi.hssf.usermodel.HSSFWorkbook getHSSFWorkbook()
    {
        return m_workBook;
    }

    private FileInputStream m_fileStream = null;
    public void close()
    {
        if(null != m_fileStream)
        {
            try
            {
                m_fileStream.close();
            }
            catch(Exception ex)
            {

            }
        }
        m_fileStream = null;
    }

    public void open(String strFileName)
        throws java.io.IOException
    {
        try
        {
            m_fileStream = new FileInputStream(strFileName);
            m_workBook = new org.apache.poi.hssf.usermodel.HSSFWorkbook(m_fileStream,false);
        }
        catch(OfficeXmlFileException ex)
        {
            close();
            throw new java.io.IOException("File Type  Error:" + ex.getMessage());
        }
        catch(FileNotFoundException ex)
        {
            close();
            throw new java.io.IOException("File " + strFileName + " Error:" + ex.getMessage());
        }
        catch(IOException ex)
        {
            close();
            throw new java.io.IOException("File " + strFileName + " Error:" + ex.getMessage());
        }
        catch(IndexOutOfBoundsException ex)
        {
            close();
            throw new java.io.IOException("File  " + strFileName + " Error:" + ex.getMessage());
        }

    }

    public int sheetsCount()
    {
        return m_workBook == null ? -1 : m_workBook.getNumberOfSheets();
    }

    public Sheet sheetAt(int nIndex)
    {
        org.apache.poi.hssf.usermodel.HSSFSheet s = m_workBook == null ? null :
            m_workBook.getSheetAt(nIndex);
        return null == s ? null : new Sheet(this,s);
    }

    public Sheet sheet(String strSheetName)
    {
        org.apache.poi.hssf.usermodel.HSSFSheet s = m_workBook == null ? null :
            m_workBook.getSheet(strSheetName);
        return null == s ? null : new Sheet(this,s);
    }

    public int indexOfSheet(String str)
    {
        return null == m_workBook ? -1 :
            m_workBook.getSheetIndex(str);
    }

    public String sheetName(int nIndex)
    {
        return null == m_workBook ? null :
            m_workBook.getSheetName(nIndex);
    }

    public void saveAs(String strFileName)
        throws IOException
    {
        if(null != m_workBook)
        {
            FileOutputStream fileOut = new FileOutputStream(strFileName);
            m_workBook.write(fileOut);
            fileOut.close();
        }
    }

    public void saveAs(OutputStream stream)
        throws IOException
    {
        if(null != m_workBook)
        {
            m_workBook.write(stream);
        }
    }

    public void setSheetName(int nIndex,String str)
    {
        if(null != m_workBook)
        {
            m_workBook.setSheetName(nIndex,str);
        }
    }

    public void newFile()
    {
        m_workBook = new org.apache.poi.hssf.usermodel.HSSFWorkbook();
    }

    public static WorkBook create()
    {
        WorkBook ret = new WorkBook();
        ret.newFile();
        return ret;
    }

    public java.util.Iterator<Sheet> sheetIterator()
    {
        Vector<Sheet> vect = new Vector<Sheet>();
        for(int n = 0;n < this.m_workBook.getNumberOfSheets();n++)
        {
            vect.addElement(new Sheet(this,m_workBook.getSheetAt(n)));
        }
        return vect.iterator();
    }

    public java.util.Iterator<String> sheetNameIterator()
    {
        Vector<String> vect = new Vector<String>();
        for(int n = 0;n < this.m_workBook.getNumberOfSheets();n++)
        {
            vect.addElement(this.sheetName(n));
        }
        return vect.iterator();
    }

    public Sheet createSheet(String strSheetName)
    {
        if(null != m_workBook)
        {
            org.apache.poi.hssf.usermodel.HSSFSheet s = m_workBook.createSheet(strSheetName);
            return null == s ? null : new Sheet(this,s);
        }
        else
        {
            return null;
        }
    }

    public CellStyle createStyle()
    {
        return CellStyle.createStyle(this);
    }
}
