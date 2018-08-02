package org.openscada.atlantis.configurator.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public class DataLoaderXls
{

    private final HSSFWorkbook workbook;

    private final boolean useHeader;

    private final int skipHeaders;

    private final int skipPastHeaders;

    private final int skipStrikeoutColumn;

    public DataLoaderXls ( final File file ) throws Exception
    {
        this ( file, true, 0, 0, -1 );
    }

    public DataLoaderXls ( final File file, final boolean useHeader, final int skipHeaders ) throws Exception
    {
        this ( file, useHeader, skipHeaders, 0, -1 );
    }

    public DataLoaderXls ( final File file, final boolean useHeader, final int skipHeaders, final int skipPastHeaders, final int skipStrikeoutColumn ) throws Exception
    {
        final HSSFWorkbook wb = new HSSFWorkbook ( new FileInputStream ( file ) );

        this.skipStrikeoutColumn = skipStrikeoutColumn;
        this.workbook = wb;
        this.useHeader = useHeader;
        this.skipHeaders = skipHeaders;
        this.skipPastHeaders = skipPastHeaders;
    }

    public void load ( final int sheetIdx, final RowHandler rowHandler )
    {
        final HSSFSheet sheet = this.workbook.getSheetAt ( sheetIdx );

        Map<Integer, String> header = new HashMap<Integer, String> ();

        int i = 0;
        for ( final Row row : sheet )
        {
            if ( i < this.skipHeaders )
            {
                System.out.println ( String.format ( "Skipping row %s", row.getRowNum () ) );
                // skip
            }
            if ( i == this.skipHeaders )
            {
                if ( this.useHeader )
                {
                    System.out.println ( String.format ( "Loading row %s as header", row.getRowNum () ) );
                    header = loadRow ( sheet, row );
                }
            }
            if ( i > this.skipHeaders + this.skipPastHeaders )
            {
                if ( !isStrikeout ( row ) )
                {
                    rowHandler.handleRow ( i, mapRow ( header, loadRow ( sheet, row ) ) );
                }
                else
                {
                    System.out.println ( String.format ( "Skipping row #%s, since it is striked out", i ) );
                }
            }
            i++;
        }

    }

    private boolean isStrikeout ( final Row row )
    {
        if ( this.skipStrikeoutColumn < 0 )
        {
            return false;
        }

        final Cell col = row.getCell ( this.skipStrikeoutColumn );

        if ( col == null )
        {
            return false;
        }

        final CellStyle style = col.getCellStyle ();
        if ( style == null )
        {
            return false;
        }

        final HSSFFont font = this.workbook.getFontAt ( style.getFontIndex () );
        if ( font == null )
        {
            return false;
        }
        return font.getStrikeout ();
    }

    private static Map<String, String> mapRow ( final Map<Integer, String> header, final Map<Integer, String> data )
    {
        final Map<String, String> row = new HashMap<String, String> ();

        for ( final Map.Entry<Integer, String> cell : data.entrySet () )
        {
            final String key = header.get ( cell.getKey () );
            if ( key != null )
            {
                row.put ( key, cell.getValue () );
            }
            else
            {
                row.put ( cell.getKey () + "", cell.getValue () );
            }
        }

        return row;
    }

    private static String makeStringValue ( final HSSFWorkbook workbook, final Cell cell )
    {
        if ( cell == null )
        {
            return null;
        }

        final HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator ( workbook );

        switch ( cell.getCellType () )
        {
        case Cell.CELL_TYPE_BLANK:
            return "";
        case Cell.CELL_TYPE_FORMULA:
            return eval.evaluate ( cell ).getStringValue ();
        default:
            return cell.toString ();
        }
    }

    private static Map<Integer, String> loadRow ( final HSSFSheet sheet, final Row row )
    {
        final Map<Integer, String> rowData = new HashMap<Integer, String> ();

        final int count = row.getLastCellNum ();

        for ( int i = 0; i < count; i++ )
        {
            final Cell cell = row.getCell ( i );
            final String value = makeStringValue ( sheet.getWorkbook (), cell );
            rowData.put ( i, value );
        }

        return rowData;
    }
}
