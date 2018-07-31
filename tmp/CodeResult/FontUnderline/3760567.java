package com.jeasonzhao.commons.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;

public class CellStyle
{
    private HSSFCellStyle m_style = null;
    private HSSFFont m_font = null;
    private WorkBook m_workBook = null;
    private CellStyle()
    {

    }

    public static CellStyle createStyle(WorkBook workBook)
    {
        CellStyle ret = new CellStyle();
        ret.m_style = workBook.getHSSFWorkbook().createCellStyle();
        ret.m_font = workBook.getHSSFWorkbook().getFontAt(ret.m_style.getFontIndex());
        ret.m_style.setFont(ret.m_font);
        ret.m_workBook = workBook;
        ret.createFont();
        return ret;
    }

    public static CellStyle getCellStyle(WorkBook workBook,short nStyleId)
    {
        CellStyle ret = new CellStyle();
        ret.m_style = workBook.getHSSFWorkbook().getCellStyleAt(nStyleId);
        ret.m_font = workBook.getHSSFWorkbook().getFontAt(ret.m_style.getFontIndex());
        ret.m_style.setFont(ret.m_font);
        ret.m_workBook = workBook;
        return ret;
    }

    public void bind(Cell ...cells)
    {
        if(null == cells)
        {
            return;
        }
        else
        {
            for(Cell c : cells)
            {
                c.getHSSFCell().setCellStyle(this.m_style);
            }
        }
    }

    public void bind(HSSFCell ...cells)
    {
        if(cells == null)
        {
            return;
        }
        for(HSSFCell c : cells)
        {
            c.setCellStyle(this.m_style);
        }
    }

    public short getDataFormat()
    {
        return m_style.getDataFormat();
    }

    public short getFontIndex()
    {
        return m_style.getFontIndex();
    }

    public CellStyle createFont()
    {
        m_font = this.m_workBook.getHSSFWorkbook().createFont();
        m_style.setFont(m_font);
        return this;
    }

    public boolean getHidden()
    {
        return m_style.getHidden();
    }

    public boolean getLocked()
    {
        return m_style.getLocked();
    }

    public TextAlign getAlignment()
    {
        return(TextAlign) TextAlign.findConstant(TextAlign.class,m_style.getAlignment());
    }

    public boolean getWrapText()
    {
        return m_style.getWrapText();
    }

    public VerticalAligns getVerticalAlignment()
    {
        return(VerticalAligns) VerticalAligns.findConstant(VerticalAligns.class,m_style.getVerticalAlignment());
    }

    public short getRotation()
    {
        return m_style.getRotation();
    }

    public short getIndention()
    {
        return m_style.getIndention();
    }

    public BorderStyle getBorderLeft()
    {
        short n = m_style.getBorderLeft();
        return(BorderStyle) BorderStyle.findConstant(BorderStyle.class,n);
    }

    public BorderStyle getBorderRight()
    {
        short n = m_style.getBorderRight();
        return(BorderStyle) BorderStyle.findConstant(BorderStyle.class,n);
    }

    public BorderStyle getBorderTop()
    {
        short n = m_style.getBorderTop();
        return(BorderStyle) BorderStyle.findConstant(BorderStyle.class,n);
    }

    public BorderStyle getBorderBottom()
    {
        short n = m_style.getBorderBottom();
        return(BorderStyle) BorderStyle.findConstant(BorderStyle.class,n);
    }

    public short getLeftBorderColor()
    {
        return m_style.getLeftBorderColor();
    }

    public short getRightBorderColor()
    {
        return m_style.getRightBorderColor();
    }

    public short getTopBorderColor()
    {
        return m_style.getTopBorderColor();
    }

    public short getBottomBorderColor()
    {
        return m_style.getBottomBorderColor();
    }

    public short getFillPattern()
    {
        return m_style.getFillPattern();
    }

    public short getFillBackgroundColor()
    {
        return m_style.getFillBackgroundColor();
    }

    public short getFillForegroundColor()
    {
        return m_style.getFillForegroundColor();
    }

    public void setBuiltinFormat(short fmt)
    {
        m_style.setDataFormat(fmt);
    }

    public void setBuiltinFormat(String str)
    {
        short n = HSSFDataFormat.getBuiltinFormat(str);
        if(n > 0)
        {
            m_style.setDataFormat(n);
        }
    }

    public void setHidden(boolean hidden)
    {
        m_style.setHidden(hidden);
    }

    public void setLocked(boolean locked)
    {
        m_style.setLocked(locked);
    }

    public CellStyle setAlignment(TextAlign align)
    {
        m_style.setAlignment((short) align.getId());
        return this;
    }

    public CellStyle setWrapText(boolean wrapped)
    {
        m_style.setWrapText(wrapped);
        return this;
    }

    public CellStyle setVerticalAlignment(VerticalAligns align)
    {
        m_style.setVerticalAlignment((short) align.getId());
        return this;
    }

    public CellStyle setRotation(short rotation)
    {
        m_style.setRotation(rotation);
        return this;
    }

    public CellStyle setIndention(short indent)
    {
        m_style.setIndention(indent);
        return this;
    }

    private CellStyle setFillPattern(short fp)
    {
        m_style.setFillPattern(fp);
        return this;
    }

    public CellStyle setBorderLeft(BorderStyle border)
    {
        m_style.setBorderLeft((short) border.getId());
        return this;
    }

    public CellStyle setBorderRight(BorderStyle border)
    {
        m_style.setBorderRight((short) border.getId());
        return this;
    }

    public CellStyle setBorderTop(BorderStyle border)
    {
        m_style.setBorderTop((short) border.getId());
        return this;
    }

    public CellStyle setBorderBottom(BorderStyle border)
    {
        m_style.setBorderBottom((short) border.getId());
        return this;
    }

    public CellStyle setLeftBorderColor(short color)
    {
        m_style.setLeftBorderColor(color);
        return this;
    }

    public CellStyle setRightBorderColor(short color)
    {
        m_style.setRightBorderColor(color);
        return this;
    }

    public CellStyle setTopBorderColor(short color)
    {
        m_style.setTopBorderColor(color);
        return this;
    }

    public CellStyle setBottomBorderColor(short color)
    {
        m_style.setBottomBorderColor(color);
        return this;
    }

    public CellStyle setLeftBorderColor(String color)
    {
        m_style.setLeftBorderColor(ColorHelper.getIndex(color));
        return this;
    }

    public CellStyle setRightBorderColor(String color)
    {
        m_style.setRightBorderColor(ColorHelper.getIndex(color));
        return this;
    }

    public CellStyle setTopBorderColor(String color)
    {
        m_style.setTopBorderColor(ColorHelper.getIndex(color));
        return this;
    }

    public CellStyle setBottomBorderColor(String color)
    {
        m_style.setBottomBorderColor(ColorHelper.getIndex(color));
        return this;
    }

//    private void setFillBackgroundColor(short bg)
//    {
//        m_style.setFillBackgroundColor(bg);
//    }
//
//    private void setFillForegroundColor(short bg)
//    {
//        m_style.setFillForegroundColor(bg);
//    }
//
//    private void setFillBackgroundColor(String bg)
//    {
//        m_style.setFillBackgroundColor(ColorHelper.getIndex(bg));
//    }

    private CellStyle setFillForegroundColor(String bg)
    {
        m_style.setFillForegroundColor(ColorHelper.getIndex(bg));
        return this;
    }

    public CellStyle setFontName(String name)
    {
        if(null != m_font)
        {
            m_font.setFontName(name);
        }
        return this;
    }

    public String getFontName()
    {
        return null == m_font ? null : m_font.getFontName();
    }

//    public short getFontIndex()
//    {
//        return null == m_font ? (short)0 : m_font.getIndex();
//    }

    public CellStyle setFontHeight(short height)
    {
        if(null != m_font)
        {
            m_font.setFontHeight(height);
        }
        return this;
    }

    public CellStyle setFontHeightInPoints(short height)
    {
        if(null != m_font)
        {
            m_font.setFontHeightInPoints(height);
        }
        return this;
    }

    public short getFontHeight()
    {
        return null == m_font ? (short) 0 : m_font.getFontHeight();
    }

    public short getFontHeightInPoints()
    {
        return null == m_font ? (short) 0 : m_font.getFontHeightInPoints();
    }

    public CellStyle setItalic(boolean italic)
    {
        if(null != m_font)
        {
            m_font.setItalic(italic);
        }
        return this;
    }

    public boolean isItalic()
    {
        return null == m_font ? false : m_font.getItalic();
    }

    public CellStyle setStrikeout(boolean strikeout)
    {
//        if(null != m_font)
        {
            m_font.setStrikeout(strikeout);
        }
        return this;
    }

    public boolean isStrikeout()
    {
        return null == m_font ? false : m_font.getStrikeout();
    }

    public CellStyle setFontColor(short color)
    {
        if(null != m_font)
        {
            m_font.setColor(color);
        }
        return this;
    }

    public CellStyle setFontColor(String color)
    {
        if(null != m_font)
        {
            m_font.setColor(ColorHelper.getIndex(color));
        }
        return this;
    }

    public short getFontColor()
    {
        return null == m_font ? (short) 0 : m_font.getColor();
    }

    public CellStyle setBold(boolean b)
    {
        if(null != m_font)
        {
            m_font.setBoldweight(b ? HSSFFont.BOLDWEIGHT_BOLD : HSSFFont.BOLDWEIGHT_NORMAL);
        }
        return this;
    }

    public boolean isBold()
    {
        return null == m_font ? false : m_font.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD;
    }

//
//    public void setFontTypeOffset(short offset)
//    {
//        if(null != m_font)
//            m_font.setTypeOffset(offset);
//    }
//
//    public short getFontTypeOffset()
//    {
//        return null == m_font ? (short) 0 : m_font.getTypeOffset();
//    }

    public CellStyle setUnderline(FontUnderLine style)
    {
        if(null != m_font && null != style)
        {
            m_font.setUnderline((byte) style.getId());
        }
        return this;
    }

    public FontUnderLine getUnderline()
    {
        return null == m_font ? null : (FontUnderLine) FontUnderLine.findConstant(FontUnderLine.class,m_font.getUnderline());
    }

//    public byte getCharSet()
//    {
//        return null == m_font ? (byte) 0 : m_font.getCharSet();
//    }
//
//    public void setCharSet(byte charset)
//    {
//        if(null != m_font)
//            m_font.setCharSet(charset);
//    }

    public CellStyle setBorderStyle(BorderStyle style,String strColor)
    {
        this.setBorderBottom(style);
        this.setBorderTop(style);
        this.setBorderLeft(style);
        this.setBorderRight(style);
        this.setTopBorderColor(strColor);
        this.setBottomBorderColor(strColor);
        this.setLeftBorderColor(strColor);
        this.setRightBorderColor(strColor);
        return this;
    }

    public CellStyle setBorderStyle(BorderStyle style,short nColor)
    {
        this.setBorderBottom(style);
        this.setBorderTop(style);
        this.setBorderLeft(style);
        this.setBorderRight(style);
        this.setTopBorderColor(nColor);
        this.setBottomBorderColor(nColor);
        this.setLeftBorderColor(nColor);
        this.setRightBorderColor(nColor);
        return this;
    }

    public CellStyle borderThin(String strColor)
    {
        return setBorderStyle(BorderStyle.THIN,strColor);
    }

    public CellStyle borderSlashDashDot(String strColor)
    {
        return setBorderStyle(BorderStyle.SLANTED_DASH_DOT,strColor);
    }

    public CellStyle borderNone()
    {
        return setBorderStyle(BorderStyle.NONE,(short) 0);
    }

    public CellStyle borderDashed(String strColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM_DASHED,strColor);
    }

    public CellStyle borderMediumDashDotDot(String strColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM_DASH_DOT_DOT,strColor);
    }

    public CellStyle borderMediumDashDot(String strColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM_DASH_DOT,strColor);
    }

    public CellStyle borderMedium(String strColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM,strColor);
    }

    public CellStyle borderHair(String strColor)
    {
        return setBorderStyle(BorderStyle.HAIR,strColor);
    }

    public CellStyle borderDouble(String strColor)
    {
        return setBorderStyle(BorderStyle.DOUBLE,strColor);
    }

    public CellStyle borderDotted(String strColor)
    {
        return setBorderStyle(BorderStyle.DOTTED,strColor);
    }

    public CellStyle borderThick(String strColor)
    {
        return setBorderStyle(BorderStyle.THICK,strColor);
    }

    public CellStyle borderDashDotDot(String strColor)
    {
        return setBorderStyle(BorderStyle.DASH_DOT_DOT,strColor);
    }

    public CellStyle borderDashDot(String strColor)
    {
        return setBorderStyle(BorderStyle.DASH_DOT,strColor);
    }

    public CellStyle borderThin(short nColor)
    {
        return setBorderStyle(BorderStyle.THIN,nColor);
    }

    public CellStyle borderSlashDashDot(short nColor)
    {
        return setBorderStyle(BorderStyle.SLANTED_DASH_DOT,nColor);
    }

    public CellStyle borderDashed(short nColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM_DASHED,nColor);
    }

    public CellStyle borderMediumDashDotDot(short nColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM_DASH_DOT_DOT,nColor);
    }

    public CellStyle borderMediumDashDot(short nColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM_DASH_DOT,nColor);
    }

    public CellStyle borderMedium(short nColor)
    {
        return setBorderStyle(BorderStyle.MEDIUM,nColor);
    }

    public CellStyle borderHair(short nColor)
    {
        return setBorderStyle(BorderStyle.HAIR,nColor);
    }

    public CellStyle borderDouble(short nColor)
    {
        return setBorderStyle(BorderStyle.DOUBLE,nColor);
    }

    public CellStyle borderDotted(short nColor)
    {
        return setBorderStyle(BorderStyle.DOTTED,nColor);
    }

    public CellStyle borderThick(short nColor)
    {
        return setBorderStyle(BorderStyle.THICK,nColor);
    }

    public CellStyle borderDashDotDot(short nColor)
    {
        return setBorderStyle(BorderStyle.DASH_DOT_DOT,nColor);
    }

    public CellStyle borderDashDot(short nColor)
    {
        return setBorderStyle(BorderStyle.DASH_DOT,nColor);
    }

    public CellStyle setFillColor(String strColor)
    {
        this.setFillForegroundColor(strColor);
        this.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return this;
    }

    public void copyTo(CellStyle style)
    {
        if(null == style || style.m_style == null || null == this.m_style)
        {
            return;
        }
        HSSFCellStyle src = this.m_style;
        HSSFCellStyle dest = style.m_style;
        dest.setDataFormat(src.getDataFormat());
        dest.setHidden(src.getHidden());
        dest.setLocked(src.getLocked());
        dest.setAlignment(src.getAlignment());
        dest.setWrapText(src.getWrapText());
        dest.setVerticalAlignment(src.getVerticalAlignment());
        dest.setRotation(src.getRotation());
        dest.setIndention(src.getIndention());
        dest.setBorderLeft(src.getBorderLeft());
        dest.setBorderRight(src.getBorderRight());
        dest.setBorderTop(src.getBorderTop());
        dest.setBorderBottom(src.getBorderBottom());
        dest.setLeftBorderColor(src.getLeftBorderColor());
        dest.setRightBorderColor(src.getRightBorderColor());
        dest.setTopBorderColor(src.getTopBorderColor());
        dest.setBottomBorderColor(src.getBottomBorderColor());
        dest.setFillPattern(src.getFillPattern());
        dest.setFillBackgroundColor(src.getFillBackgroundColor());
        dest.setFillForegroundColor(src.getFillForegroundColor());
        try
        {
            dest.setFont(style.m_workBook.getHSSFWorkbook().getFontAt(src.getFontIndex()));
        }
        catch(Exception excep)
        {
            HSSFFont f2 = style.m_workBook.getHSSFWorkbook().createFont();
            dest.setFont(f2);
            f2.setFontName(m_font.getFontName());
            f2.setFontHeight(m_font.getFontHeight());
            f2.setFontHeightInPoints(m_font.getFontHeightInPoints());
            f2.setItalic(m_font.getItalic());
            f2.setStrikeout(m_font.getStrikeout());
            f2.setColor(m_font.getColor());
            f2.setBoldweight(m_font.getBoldweight());
            f2.setTypeOffset(m_font.getTypeOffset());
            f2.setUnderline(m_font.getUnderline());
            f2.setCharSet(m_font.getCharSet());
        }
    }

    public CellStyle bold()
    {
        return this.setBold(true);
    }

    public CellStyle alignLeft()
    {
        return this.setAlignment(TextAlign.LEFT);
    }

    public CellStyle alignRight()
    {
        return this.setAlignment(TextAlign.RIGHT);
    }

    public CellStyle alignCenter()
    {
        return this.setAlignment(TextAlign.CENTER);
    }

    public CellStyle vAlignTop()
    {
        return this.setVerticalAlignment(VerticalAligns.TOP);
    }

    public CellStyle vAlignCenter()
    {
        return this.setVerticalAlignment(VerticalAligns.CENTER);
    }

    public CellStyle vAlignBottom()
    {
        return this.setVerticalAlignment(VerticalAligns.BOTTOM);
    }

}
