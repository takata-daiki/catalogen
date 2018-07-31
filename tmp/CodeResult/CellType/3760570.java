package com.jeasonzhao.commons.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import com.jeasonzhao.commons.basic.IntegerPair;

public class CellType extends IntegerPair
{
	private static final long serialVersionUID = 1L;
	protected CellType(int nId)
    {
        super(nId,null);
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public static final CellType NUMERIC = new CellType(HSSFCell.CELL_TYPE_NUMERIC);
    public static final CellType STRING = new CellType(HSSFCell.CELL_TYPE_STRING);
    public static final CellType FORMULA = new CellType(HSSFCell.CELL_TYPE_FORMULA);
    public static final CellType BLANK = new CellType(HSSFCell.CELL_TYPE_BLANK);
    public static final CellType BOOLEAN = new CellType(HSSFCell.CELL_TYPE_BOOLEAN);
    public static final CellType ERROR = new CellType(HSSFCell.CELL_TYPE_ERROR);

}
