package com.jeasonzhao.commons.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import com.jeasonzhao.commons.basic.IntegerPair;

public class FontUnderLine extends IntegerPair
{
	private static final long serialVersionUID = 1L;
	protected FontUnderLine(int nId)
    {
        super(nId,null);
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public static final FontUnderLine NONE = new FontUnderLine(HSSFFont.U_NONE);
    public static final FontUnderLine DOUBLE = new FontUnderLine(HSSFFont.U_DOUBLE);
    public static final FontUnderLine DOUBLE_ACCOUNTING = new FontUnderLine(HSSFFont.U_DOUBLE_ACCOUNTING);
    public static final FontUnderLine SINGLE = new FontUnderLine(HSSFFont.U_SINGLE);
    public static final FontUnderLine SINGLE_ACCOUNTING = new FontUnderLine(HSSFFont.U_SINGLE_ACCOUNTING);
}
