package org.openscada.deploy.iolist.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openscada.deploy.iolist.model.Item;
import org.openscada.deploy.iolist.model.ModelFactory;

public class SpreadSheetPoiHelper extends GenericSpreadSheetHelper
{
    private final Workbook workbook;

    private final Sheet sheet;

    private final CellStyle ackRequiredStyle;

    private final CellStyle headerStyle;

    private final CellStyle strikeoutStyle;

    protected SpreadSheetPoiHelper ()
    {
        this.workbook = new HSSFWorkbook ();
        this.sheet = this.workbook.createSheet ( "IO-List" );

        this.ackRequiredStyle = this.workbook.createCellStyle ();
        this.ackRequiredStyle.setFillForegroundColor ( IndexedColors.RED.getIndex () );
        this.ackRequiredStyle.setFillPattern ( CellStyle.SOLID_FOREGROUND );

        final Font headerFont = this.workbook.createFont ();
        headerFont.setBoldweight ( Font.BOLDWEIGHT_BOLD );

        final Font strikeoutFont = this.workbook.createFont ();
        strikeoutFont.setStrikeout ( true );

        this.headerStyle = this.workbook.createCellStyle ();
        this.headerStyle.setFont ( headerFont );

        this.strikeoutStyle = this.workbook.createCellStyle ();
        this.strikeoutStyle.setFont ( strikeoutFont );

        // enable auto filter
        this.sheet.setAutoFilter ( new CellRangeAddress ( 0, 0, 0, Header.values ().length - 1 ) );

        // fit to one page width
        this.sheet.setAutobreaks ( true );
        final PrintSetup ps = this.sheet.getPrintSetup ();
        ps.setLandscape ( true );
        ps.setPaperSize ( PrintSetup.A4_PAPERSIZE );
        ps.setFitWidth ( (short)1 );
        ps.setFitHeight ( (short)0 );
        ps.setFooterMargin ( 0.25 );

        this.sheet.setMargin ( Sheet.LeftMargin, 0.25 );
        this.sheet.setMargin ( Sheet.RightMargin, 0.25 );
        this.sheet.setMargin ( Sheet.TopMargin, 0.25 );
        this.sheet.setMargin ( Sheet.BottomMargin, 0.5 );

        final Footer footer = this.sheet.getFooter ();
        footer.setRight ( "Page " + HeaderFooter.page () + " of " + HeaderFooter.numPages () );

        // freeze area
        this.sheet.createFreezePane ( 0, 1, 0, 1 );

        // repeat headers
        this.workbook.setRepeatingRowsAndColumns ( 0, -1, -1, 0, 1 );
    }

    public static void writeSpreadsheet ( final File file, final Collection<? extends Item> items ) throws Exception
    {
        final SpreadSheetPoiHelper helper = new SpreadSheetPoiHelper ();
        helper.writeHeader ();
        helper.writeItems ( items );
        helper.autoAdjust ( helper.sheet );
        helper.write ( file );
    }

    private void write ( final File file ) throws Exception
    {
        final FileOutputStream fileOut = new FileOutputStream ( file );
        this.workbook.write ( fileOut );
        fileOut.close ();
    }

    private void autoAdjust ( final Sheet sheet )
    {
        for ( int i = 0; i < Header.values ().length; i++ )
        {
            sheet.autoSizeColumn ( i );
        }
    }

    private Cell createCell ( final Sheet sheet, final int row, final int column )
    {
        Row rowData = sheet.getRow ( row );
        if ( rowData == null )
        {
            rowData = sheet.createRow ( row );
        }
        return rowData.createCell ( column );
    }

    @Override
    protected void strikeThroughRow ( final int rowIndex ) throws Exception
    {
        final Row row = this.sheet.getRow ( rowIndex );
        for ( final Iterator<Cell> i = row.cellIterator (); i.hasNext (); )
        {
            final Cell cell = i.next ();
            cell.setCellStyle ( this.strikeoutStyle );
        }
    }

    @Override
    protected void addHeaderCell ( final String string, final int index ) throws Exception
    {
        final Cell cell = createCell ( this.sheet, 0, index );

        cell.setCellValue ( string );
        cell.setCellStyle ( this.headerStyle );
    }

    @Override
    protected void addData ( final int row, final int column, final Double data, final boolean ack )
    {
        final Cell cell = createCell ( this.sheet, row, column );

        if ( data != null )
        {
            cell.setCellValue ( data );
            if ( ack )
            {
                cell.setCellStyle ( this.ackRequiredStyle );
            }
        }
        else
        {
            cell.setCellType ( Cell.CELL_TYPE_BLANK );
        }
    }

    @Override
    protected void addData ( final int row, final int column, final String data, final boolean ack )
    {
        final Cell cell = createCell ( this.sheet, row, column );

        if ( data != null )
        {
            cell.setCellValue ( data );
            if ( ack )
            {
                cell.setCellStyle ( this.ackRequiredStyle );
            }
        }
        else
        {
            cell.setCellType ( Cell.CELL_TYPE_BLANK );
        }
    }

    @Override
    protected void addSelectiveDataAck ( final int row, final int column, final boolean available, final Double value, final boolean ack )
    {
        final Cell cell = createCell ( this.sheet, row, column );

        if ( available && value == null )
        {
            cell.setCellValue ( "X" );
            if ( ack )
            {
                cell.setCellStyle ( this.ackRequiredStyle );
            }
        }
        else if ( available && value != null )
        {
            cell.setCellValue ( value );
            if ( ack )
            {
                cell.setCellStyle ( this.ackRequiredStyle );
            }
        }
        else
        {
            cell.setCellType ( Cell.CELL_TYPE_BLANK );
        }
    }

    @Override
    protected void addSelectiveData ( final int row, final int column, final boolean available, final String value, final boolean ackRequired )
    {
        final Cell cell = createCell ( this.sheet, row, column );

        if ( available && value == null )
        {
            cell.setCellValue ( "X" );
            if ( ackRequired )
            {
                cell.setCellStyle ( this.ackRequiredStyle );
            }
        }
        else if ( available && value != null )
        {
            cell.setCellValue ( value );
            if ( ackRequired )
            {
                cell.setCellStyle ( this.ackRequiredStyle );
            }
        }
        else
        {
            cell.setCellType ( Cell.CELL_TYPE_BLANK );
        }
    }

    public static List<Item> loadExcel ( final String fileName ) throws IOException
    {
        final List<Item> result = new LinkedList<Item> ();

        final HSSFWorkbook workbook = new HSSFWorkbook ( new FileInputStream ( fileName ) );

        final Sheet sheet = workbook.getSheetAt ( 0 );

        final Map<Integer, Header> header = loadHeader ( sheet );

        for ( int row = 1; row <= sheet.getLastRowNum (); row++ )
        {
            final Item item = convertToItem ( workbook, header, sheet.getRow ( row ), String.format ( "%s@%s", fileName, row ) );
            if ( item != null )
            {
                result.add ( item );
            }
        }

        return result;
    }

    private static Item convertToItem ( final HSSFWorkbook workbook, final Map<Integer, Header> header, final Row row, final String debugInformation )
    {
        if ( row == null || row.getLastCellNum () < 2 )
        {
            return null;
        }

        // ignore empty lines
        if ( row.getLastCellNum () < 3 || row.getCell ( 2 ) == null || row.getCell ( 2 ).getStringCellValue () == null || row.getCell ( 2 ).getStringCellValue ().length () == 0 )
        {
            return null;
        }

        final Item item = ModelFactory.eINSTANCE.createItem ();
        item.setDebugInformation ( debugInformation );

        final Map<Header, Value> mapped = makeRow ( workbook, header, row );

        for ( final Map.Entry<Header, Value> entry : mapped.entrySet () )
        {
            entry.getKey ().apply ( item, entry.getValue () );
        }

        return item;
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

    private static Map<Header, Value> makeRow ( final HSSFWorkbook workbook, final Map<Integer, Header> headers, final Row row )
    {
        final Map<Header, Value> result = new HashMap<Header, Value> ();

        for ( int i = 0; i < row.getLastCellNum (); i++ )
        {
            final Header header = headers.get ( i );
            if ( header != null && row.getCell ( i ) != null )
            {
                final Cell cell = row.getCell ( i );

                final Value value = new Value ();
                value.setValue ( makeStringValue ( workbook, cell ) );

                value.setBackgroundColor ( new RGB ( 255, 255, 255 ) );

                if ( cell.getCellStyle () != null )
                {
                    final HSSFFont font = workbook.getFontAt ( cell.getCellStyle ().getFontIndex () );
                    if ( font != null )
                    {
                        value.setStrikeThrough ( font.getStrikeout () );
                    }

                    final short color = cell.getCellStyle ().getFillForegroundColor ();
                    if ( color == HSSFColor.RED.index )
                    {
                        value.setBackgroundColor ( new RGB ( 255, 0, 0 ) );
                    }
                }

                result.put ( header, value );
            }
        }
        return result;
    }

    private static Map<Integer, Header> loadHeader ( final Sheet sheet )
    {
        final Map<Integer, Header> headerMap = new HashMap<Integer, Header> ();

        final Row row = sheet.getRow ( 0 );

        for ( int i = 0; i < row.getLastCellNum (); i++ )
        {
            try
            {
                final Header header = Header.valueOf ( row.getCell ( i ).getStringCellValue () );
                if ( header != null )
                {
                    headerMap.put ( i, header );
                }
            }
            catch ( final IllegalArgumentException e )
            {
                // ignore extra header
            }
        }
        return headerMap;
    }

}
