package plugins.adufour.workbooks;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Results table for Icy. This class relies on the Apache POI library, and acts as a high-level
 * wrapper for the {@link Sheet} class
 * 
 * @author Alexandre Dufour
 */
public class IcySpreadSheet
{
    private final Sheet            sheet;
    
    private final FormulaEvaluator evaluator;
    
    public IcySpreadSheet(Sheet sheet)
    {
        this.sheet = sheet;
        this.evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
    }
    
    /**
     * Copies a cell into another (only works for valid source and target cells)
     * 
     * @param sourceCell
     *            cannot be null
     * @param targetCell
     *            cannot be null
     */
    private static void copyCell(Cell sourceCell, Cell targetCell) throws NullPointerException
    {
        targetCell.setCellComment(sourceCell.getCellComment());
        switch (sourceCell.getCellType())
        {
        case Cell.CELL_TYPE_BOOLEAN:
            targetCell.setCellValue(sourceCell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR:
            targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
            targetCell.setCellFormula(sourceCell.getCellFormula());
            break;
        case Cell.CELL_TYPE_NUMERIC:
            targetCell.setCellValue(sourceCell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_STRING:
            targetCell.setCellValue(sourceCell.getStringCellValue());
            break;
        default:
            System.err.println("Unknown cell type: " + sourceCell.getCellType());
        }
    }
    
    /**
     * @param source
     *            the index of the row to copy (NB: the first row is at index 0)
     * @param target
     *            the index of the row where the source row will be pasted (NB: the first row is at
     *            index 0)
     */
    public void copyColumn(int sourceColumn, int targetColumn)
    {
        Row source = sheet.getRow(sourceColumn);
        Row target = sheet.getRow(targetColumn);
        
        if (source == null)
        {
            if (target != null) sheet.removeRow(target);
            return;
        }
        
        if (target == null) sheet.createRow(targetColumn);
        
        int nCells = source.getLastCellNum();
        
        for (int i = 0; i < nCells; i++)
        {
            Cell sourceCell = source.getCell(i, Row.RETURN_BLANK_AS_NULL);
            
            if (sourceCell != null)
            {
                Cell targetCell = target.getCell(i, Row.CREATE_NULL_AS_BLANK);
                copyCell(sourceCell, targetCell);
            }
        }
    }
    
    /**
     * @param source
     *            the index of the row to copy (NB: the first row is at index 0)
     * @param target
     *            the index of the row where the source row will be pasted (NB: the first row is at
     *            index 0)
     */
    public void copyRow(int sourceRow, int targetRow)
    {
        Row source = sheet.getRow(sourceRow);
        Row target = getOrCreateRow(targetRow);
        
        if (source == null)
        {
            if (target != null) sheet.removeRow(target);
            return;
        }
        
        if (target == null) sheet.createRow(targetRow);
        
        int nCells = source.getLastCellNum();
        
        for (int i = 0; i < nCells; i++)
        {
            Cell sourceCell = source.getCell(i, Row.RETURN_BLANK_AS_NULL);
            
            if (sourceCell != null)
            {
                Cell targetCell = target.getCell(i, Row.CREATE_NULL_AS_BLANK);
                copyCell(sourceCell, targetCell);
            }
        }
    }
    
    /**
     * Deletes the cell at the specified row and column (NB: the first row or column index is 0).
     * 
     * @param rowIndex
     *            the row index of the cell to delete (starting at 0)
     * @param columnIndex
     *            the column index of the cell to delete (starting at 0)
     */
    public void deleteCell(int rowIndex, int columnIndex)
    {
        Row row = sheet.getRow(rowIndex);
        if (row == null) return;
        
        Cell cell = row.getCell(columnIndex);
        if (cell != null)
        {
            row.removeCell(cell);
            evaluator.notifyDeleteCell(cell);
        }
    }
    
    /**
     * Deletes the column at the specified index (NB: the first column is at index 0).
     * 
     * @param columnIndex
     *            the index of the column to remove (starting at 0)
     * @param shiftData
     *            set to <code>true</code> to shift all other columns up, or <code>false</code> to
     *            leave the column empty
     */
    public void deleteColumn(int columnIndex, boolean shiftData)
    {
        int lastRow = sheet.getLastRowNum();
        
        for (int r = 0; r <= lastRow; r++)
        {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            
            int lastColumn = row.getLastCellNum() - 1;
            
            if (columnIndex > lastColumn) continue;
            
            Cell cell = row.getCell(columnIndex);
            
            if (cell != null) row.removeCell(cell);
            
            if (shiftData && columnIndex < lastColumn)
            {
                for (int i = columnIndex; i < lastColumn; i++)
                    copyCell(row.getCell(i + 1), row.getCell(i, Row.CREATE_NULL_AS_BLANK));
                row.removeCell(row.getCell(lastColumn));
            }
        }
    }
    
    /**
     * Deletes the row at the specified index (NB: the first row is at index 0).
     * 
     * @param rowIndex
     *            the index of the row to remove (starting at 0)
     * @param shiftData
     *            set to <code>true</code> to shift all other lines up, or <code>false</code> to
     *            leave the line empty
     */
    public void deleteRow(int rowIndex, boolean shiftData)
    {
        int lastRow = sheet.getLastRowNum();
        
        // don't do anything if the column does not exist
        if (rowIndex >= lastRow) return;
        
        Row rowToRemove = sheet.getRow(rowIndex);
        
        if (rowToRemove != null) sheet.removeRow(rowToRemove);
        
        if (shiftData && rowIndex < lastRow)
        {
            for (int i = rowIndex + 1; i <= lastRow; i++)
                copyRow(i + 1, i);
            
            sheet.removeRow(sheet.getRow(lastRow));
        }
    }
    
    /**
     * @param row
     * @param column
     * @return the background color of the specified cell
     */
    public Color getFillColor(int row, int column)
    {
        Cell cell = getOrCreateRow(row).getCell(column, Row.CREATE_NULL_AS_BLANK);
        
        org.apache.poi.ss.usermodel.Color color = cell.getCellStyle().getFillForegroundColorColor();
        if (color instanceof HSSFColor)
        {
            short[] rgb = ((HSSFColor) color).getTriplet();
            if (rgb[0] != 0 || rgb[1] != 0 || rgb[2] != 0)
            {
                return new Color(rgb[0], rgb[1], rgb[2]);
            }
        }
        else if (color instanceof XSSFColor)
        {
            byte[] rgb = ((XSSFColor) color).getRGB();
            if (rgb[0] != 0 || rgb[1] != 0 || rgb[2] != 0)
            {
                return new Color(rgb[0] & 0xff, rgb[1] & 0xff, rgb[2] & 0xff);
            }
        }
        
        return null;
    }
    
    /**
     * @param rowIndex
     *            the 0-based row index of the cell
     * @param columnIndex
     *            the 0-based column index of the cell
     * @return the cell formula at the specified location (or an empty string if the cell does not
     *         hold a formula)
     */
    public String getFormula(int rowIndex, int columnIndex)
    {
        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";
        
        Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        if (cell == null) return "";
        
        return cell.getCellType() == Cell.CELL_TYPE_FORMULA ? cell.getCellFormula() : "";
    }
    
    /**
     * @param rowIndex
     *            the 0-based row index of the cell
     * @param columnIndex
     *            the 0-based column index of the cell
     * @return the cell at the specified location (or an empty string if the cell is empty)
     */
    public Object getValue(int rowIndex, int columnIndex)
    {
        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";
        
        Cell cell = row.getCell(columnIndex, Row.RETURN_BLANK_AS_NULL);
        if (cell == null) return "";
        
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) return cell.getStringCellValue();
        
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) return cell.getBooleanCellValue();
        
        return evaluator.evaluate(cell).getNumberValue();
    }
    
    /**
     * @param rowIndex
     *            the 0-based row index
     * @return
     */
    private Row getOrCreateRow(int rowIndex)
    {
        Row row = sheet.getRow(rowIndex);
        return row != null ? row : sheet.createRow(rowIndex);
    }
    
    /**
     * @return the index of this sheet within its workbook (NB: the first index is 0)
     */
    public int getIndex()
    {
        return sheet.getWorkbook().getSheetIndex(sheet);
    }
    
    /**
     * @return the name of this sheet
     */
    public String getName()
    {
        return sheet.getSheetName();
    }
    
    /**
     * @return The number of rows in this table. Note that this number indicates the index of the
     *         last row, but does not necessarily indicate that all other rows contain data
     */
    public int getNumberOfRows()
    {
        return sheet.getLastRowNum() + 1;
    }
    
    /**
     * @return The number of columns in this table. Note that this number indicates the index of the
     *         last known column, but does not necessarily indicate that all other columns contain
     *         data, or that all rows use that many columns
     */
    public int getNumberOfColumns()
    {
        int nCols = 0;
        int lastRow = sheet.getLastRowNum();
        for (int i = 0; i <= lastRow; i++)
        {
            Row row = sheet.getRow(i);
            if (row != null) nCols = Math.max(nCols, row.getLastCellNum());
        }
        return nCols;
    }
    
    /**
     * @return the underlying {@link Sheet} implementation backing this object.
     */
    public Sheet getSheet()
    {
        return sheet;
    }
    
    /**
     * Sets the fill color of the specified cell
     * 
     * @param row
     * @param column
     * @param color
     *            the color to assign
     */
    public void setFillColor(int row, int column, Color color)
    {
        Cell cell = getOrCreateRow(row).getCell(column, Row.CREATE_NULL_AS_BLANK);
        short colorIndex;
        
        Workbook book = sheet.getWorkbook();
        
        if (book instanceof HSSFWorkbook)
        {
            HSSFPalette palette = ((HSSFWorkbook) book).getCustomPalette();
            ((HSSFWorkbook) book).getNumCellStyles();
            colorIndex = palette.findSimilarColor(color.getRed(), color.getGreen(), color.getBlue()).getIndex();
            
            // look for an existing style
            boolean styleExists = false;
            try
            {
                int numStyles = book.getNumCellStyles();
                for (int i = 0; i < numStyles; i++)
                {
                    CellStyle cellStyle = book.getCellStyleAt(i);
                    if (cellStyle.getFillForegroundColor() == colorIndex)
                    {
                        cell.setCellStyle(cellStyle);
                        styleExists = true;
                        break;
                    }
                }
            }
            catch (ClassCastException ccE)
            {
                styleExists = false;
            }
            catch (IllegalStateException isE)
            {
                styleExists = false;
            }
            
            if (!styleExists)
            {
                CellStyle newStyle = book.createCellStyle();
                newStyle.setFillForegroundColor(colorIndex);
                cell.setCellStyle(newStyle);
            }
        }
        else if (book instanceof XSSFWorkbook)
        {
            XSSFColor newColor = new XSSFColor(color);
            
            // look for an existing style
            boolean styleExists = false;
            try
            {
                int numStyles = book.getNumCellStyles();
                for (int i = 0; i < numStyles; i++)
                {
                    XSSFCellStyle cellStyle = (XSSFCellStyle) book.getCellStyleAt(i);
                    
                    if (cellStyle.getFillForegroundXSSFColor() == newColor)
                    {
                        cell.setCellStyle(cellStyle);
                        styleExists = true;
                        break;
                    }
                }
            }
            catch (IllegalStateException e)
            {
                styleExists = false;
            }
            
            if (!styleExists)
            {
                XSSFCellStyle newStyle = (XSSFCellStyle) book.createCellStyle();
                newStyle.setFillForegroundColor(newColor);
                cell.setCellStyle(newStyle);
            }
        }
        cell.getCellStyle().setFillPattern(CellStyle.SOLID_FOREGROUND);
    }
    
    /**
     * Sets the formula for the specified cell. The formula follows the standard Excel-compatible
     * syntax with alphanumeric references to other cells (e.g. "=A1*A2"). <br/>
     * NOTE: this method is not responsible for checking the formula for potential syntax errors.
     * 
     * @param row
     * @param column
     * @param formula
     *            the formula
     * @throws FormulaParseException
     *             if the formula has errors
     */
    public void setFormula(int row, int column, String formula) throws FormulaParseException
    {
        Cell cell = getOrCreateRow(row).getCell(column, Row.CREATE_NULL_AS_BLANK);
        
        if (formula.startsWith("=")) formula = formula.substring(1);
        
        cell.setCellFormula(formula);
        
        evaluator.notifySetFormula(cell);
    }
    
    /**
     * Sets the cell value at the specified location. Values of the following types are supported:
     * <ul>
     * <li>{@link String} (plain text)</li>
     * <li>{@link Boolean} (<code>true</code> or <code>false</code>)</li>
     * <li>Any compatible {@link Number} (will be converted to double precision)</li>
     * </ul>
     * Other unsupported objects will be replaced by their String representation (via
     * {@link Object#toString()})<br/>
     * NOTE: to assign a formula to this cell, use {@link #setFormula(int, int, String)}.
     * 
     * @param row
     *            the index of the row containing the cell (NB: the first row is at index 0)
     * @param column
     *            the index of the column containing the cell (NB: the first column is at index 0)
     * @param value
     *            the value to assign to the cell. NB: the type of cell will be automatically
     *            guessed from the provided value, and if no standard format is found (number,
     *            boolean, text), its String representation will be used
     */
    public void setValue(int row, int column, Object value)
    {
        if (value == null)
        {
            deleteCell(row, column);
            return;
        }
        
        Cell cell = getOrCreateRow(row).getCell(column, Row.CREATE_NULL_AS_BLANK);
        
        // what is the value like?
        
        if (value instanceof String)
        {
            String text = (String) value;
            
            try
            {
                value = Double.parseDouble(text);
            }
            catch (NumberFormatException numberE)
            {
                // Parse boolean values manually (because Boolean.parseBoolean() never fails)
                if (text.equalsIgnoreCase("true"))
                {
                    value = true;
                }
                else if (text.equalsIgnoreCase("false"))
                {
                    value = false;
                }
            }
        }
        
        // a number?
        if (Number.class.isAssignableFrom(value.getClass()))
        {
            cell.setCellValue(((Number) value).doubleValue());
        }
        else if (value instanceof Boolean)
        {
            cell.setCellValue((Boolean) value);
        }
        // if not, default to a String representation
        else
        {
            cell.setCellValue(value.toString());
        }
        
        evaluator.notifyUpdateCell(cell);
    }
    
    /**
     * Sets an entire column of values from a list of values
     * 
     * @param columnIndex
     *            the index of the target column (Note: the first column is at index 0)
     * @param columnValues
     *            an array of values to add, line by line. NB: numbers and string will be recognized
     *            as such, any other item will be converted to string using
     *            {@link Object#toString()}, while <code>null</code> values will result in an empty
     *            cell
     */
    public void setColumn(int columnIndex, Object... columnValues)
    {
        setColumn(columnIndex, null, columnValues);
    }
    
    /**
     * Sets an entire column of values from a list of values
     * 
     * @param columnHeader
     *            a header text placed in an extra row before the data (or <code>null</code> if not
     *            needed)
     * @param columnIndex
     *            the index of the target column (Note: the first column is at index 0)
     * @param columnValues
     *            an array of values to add, line by line. NB: numbers and string will be recognized
     *            as such, any other item will be converted to string using
     *            {@link Object#toString()}, while <code>null</code> values will result in an empty
     *            cell
     */
    public void setColumn(int columnIndex, String columnHeader, Object... columnValues)
    {
        int rowIndex = 0;
        if (columnHeader != null)
        {
            Row currentRow = getOrCreateRow(rowIndex);
            currentRow.getCell(columnIndex, Row.CREATE_NULL_AS_BLANK).setCellValue(columnHeader);
            rowIndex = 1;
        }
        
        for (Object obj : columnValues)
        {
            if (obj == null)
            {
                rowIndex++;
                continue;
            }
            
            setValue(rowIndex++, columnIndex, obj);
        }
    }
    
    /**
     * Convenience method that sets an entire row of values at the specified location in a given
     * workbook
     * 
     * @param rowIndex
     *            the index of the row to fill (Note: the first column is at index 0)
     * @param values
     *            the values to add, separated by commas, or a single array containing the values to
     *            add.<br/>
     *            NB: numbers and string will be recognized as such, any other item will be
     *            converted to string using {@link Object#toString()}, while <code>null</code>
     *            values will result in an empty cell
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setRow(int rowIndex, Object... values)
    {
        Iterable<?> list = null;
        
        // special case: rowValues could contain a single array
        if (values.length == 1 && values[0].getClass().isArray())
        {
            Object array = values[0];
            int n = Array.getLength(array);
            ArrayList arrayList = new ArrayList(n);
            for (int i = 0; i < n; i++)
                arrayList.add(Array.get(array, i));
            list = arrayList;
        }
        else
        {
            list = Arrays.asList(values);
        }
        
        setRow(rowIndex, null, list);
    }
    
    /**
     * Sets an entire row of values at the specified (0-based) row index
     * 
     * @param header
     *            a header text placed in the first column, before the data (or <code>null</code> if
     *            not needed)
     * @param rowIndex
     *            the index of the target row (Note: the first row is at index 0)
     * @param rowValues
     *            an array of values to add, column by column. NB: numbers and string will be
     *            recognized as such, any other item will be converted to string using
     *            {@link Object#toString()}, while <code>null</code> values will result in an empty
     *            cell
     */
    public void setRow(int rowIndex, String header, Iterable<?> rowValues)
    {
        int colIndex = 0;
        if (header != null) setValue(rowIndex, colIndex++, header);
        
        for (Object obj : rowValues)
            if (obj != null) setValue(rowIndex, colIndex++, obj);
    }
    
    /**
     * Removes all rows in this sheet
     */
    public void removeRows()
    {
        removeRows(0);
    }
    
    /**
     * Removes all rows in this sheet starting at the specified (0-based) row index
     * 
     * @param startRowIndex
     *            the (0-based) index of the first row to remove
     */
    public void removeRows(int startRowIndex)
    {
        for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++)
        {
            Row row = sheet.getRow(i);
            if (row != null) sheet.removeRow(row);
        }
    }
    
    /**
     * @param columnIndex
     *            the column to search
     * @return All values in the column corresponding to a number (empty cells and other cell
     *         formats are discarded)
     */
    public double[] getColumnValues(int columnIndex)
    {
        ArrayList<Double> values = new ArrayList<Double>(sheet.getLastRowNum() + 1);
        for (int i = 0; i <= sheet.getLastRowNum(); i++)
        {
            Object val = getValue(i, columnIndex);
            if (val instanceof Number) values.add(((Number) val).doubleValue());
        }
        if (values.isEmpty()) return null;
        
        double[] result = new double[values.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = values.get(i);
        return result;
    }
}
