package com.jeasonzhao.commons.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import com.jeasonzhao.commons.basic.IntegerPair;

public class TextAlign extends IntegerPair
{
	private static final long serialVersionUID = 1L;
	protected TextAlign(int nId)
    {
        super(nId,null);
    }

    public static final TextAlign GENERAL = new TextAlign(HSSFCellStyle.ALIGN_GENERAL);
    public static final TextAlign LEFT = new TextAlign(HSSFCellStyle.ALIGN_LEFT);
    public static final TextAlign CENTER = new TextAlign(HSSFCellStyle.ALIGN_CENTER);
    public static final TextAlign RIGHT = new TextAlign(HSSFCellStyle.ALIGN_RIGHT);
    public static final TextAlign FILL = new TextAlign(HSSFCellStyle.ALIGN_FILL);
    public static final TextAlign JUSTIFY = new TextAlign(HSSFCellStyle.ALIGN_JUSTIFY);
    public static final TextAlign CENTER_SELECTION = new TextAlign(HSSFCellStyle.ALIGN_CENTER_SELECTION);
    public int hashCode()
    {
        return super.hashCode();
    }

}
