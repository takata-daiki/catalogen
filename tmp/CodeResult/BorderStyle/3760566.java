package com.jeasonzhao.commons.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.jeasonzhao.commons.basic.IntegerPair;

public class BorderStyle extends IntegerPair
{
	private static final long serialVersionUID = 1L;
	protected BorderStyle(int nId)
    {
        super(nId);
    }

    public static final BorderStyle NONE = new BorderStyle(HSSFCellStyle.BORDER_NONE);
    public static final BorderStyle THIN = new BorderStyle(HSSFCellStyle.BORDER_THIN);
    public static final BorderStyle MEDIUM = new BorderStyle(HSSFCellStyle.BORDER_MEDIUM);

    public static final BorderStyle DASHED = new BorderStyle(HSSFCellStyle.BORDER_DASHED);
    public static final BorderStyle HAIR = new BorderStyle(HSSFCellStyle.BORDER_HAIR);
    public static final BorderStyle THICK = new BorderStyle(HSSFCellStyle.BORDER_THICK);
    public static final BorderStyle DOUBLE = new BorderStyle(HSSFCellStyle.BORDER_DOUBLE);
    public static final BorderStyle DOTTED = new BorderStyle(HSSFCellStyle.BORDER_DOTTED);
    public static final BorderStyle MEDIUM_DASHED = new BorderStyle(HSSFCellStyle.BORDER_MEDIUM_DASHED);
    public static final BorderStyle DASH_DOT = new BorderStyle(HSSFCellStyle.BORDER_DASH_DOT);
    public static final BorderStyle MEDIUM_DASH_DOT = new BorderStyle(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT);
    public static final BorderStyle DASH_DOT_DOT = new BorderStyle(HSSFCellStyle.BORDER_DASH_DOT_DOT);
    public static final BorderStyle MEDIUM_DASH_DOT_DOT = new BorderStyle(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT);
    public static final BorderStyle SLANTED_DASH_DOT = new BorderStyle(HSSFCellStyle.BORDER_SLANTED_DASH_DOT);
    public int hashCode()
    {
        return super.hashCode();
    }
}
