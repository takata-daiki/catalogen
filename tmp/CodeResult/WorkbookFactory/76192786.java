package com.abc.myproject.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.common.collect.Lists;

/**
 * Simple apache poi wrapper. Write only support ".xls". Read support ".xls and .xlsx".
 * 
 * <pre>
 * <b>Example:</b>
 * 
 * Create:
 *         <code>SimpleWorkbook workbook = new SimpleWorkbook();
 *         workbook.createTitleRow("sheet01", "title01", "title02");
 *         for (short i = 1; i <= (short) 10; i++) {
 *             workbook.setData("sheet01", i, 0, "row:" + i + ",col:" + 0);
 *             workbook.setData("sheet01", i, 1, "row:" + i + ",col:" + 1);
 *         }
 *         String filename = "SimpleWorkbookTestFile." + workbook.getExtension();
 * 
 *             FileOutputStream fos = new FileOutputStream(filename);
 *             workbook.write(fos);
 *             fos.close();
 *             </code>
 * Read:
 *             FileInputStream fis = new FileInputStream(filename);
 *             SimpleWorkbook fileInDisk = SimpleWorkbook.fromStream(fis);
 *             fis.close();
 *             List<String> values = fileInDisk.values(new RowMapper<String>() {
 * 
 *                 
 *                 public String mapRow(List<String> celValues) {
 *                     return celValues.get(0) + "******" + celValues.get(1);
 *                 }
 *             });
 *             for (String v : values) {
 *                 System.out.println(v);
 *             }
 * 
 * 
 * </pre>
 * 
 * @author ezhengx
 * 
 */
public class SimpleWorkbook {

    private static final String YYYY_MM_DD = "yyyy/MM/dd";

    public enum FontStyle {
        NORMAL, BOLD, ITALICS, BOLD_ITALICS
    }

    private final Workbook            wb;

    private final Map<String, Sheet>  sheets;

    private Map<FontStyle, CellStyle> styles;

    private CellStyle                 dateStyle;

    public SimpleWorkbook() {
        this(new HSSFWorkbook());
    }

    private SimpleWorkbook(Workbook workbook) {
        wb = workbook;
        sheets = new HashMap<String, Sheet>();
        setupFonts();
        setupDateStyle();
    }

    private void setupDateStyle() {
        dateStyle = wb.createCellStyle();

        String dateformat = System.getProperty("sdp.export.date.format");
        dateStyle.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat(
                dateformat == null ? YYYY_MM_DD : dateformat));
    }

    private void setupFonts() {
        styles = new HashMap<FontStyle, CellStyle>();

        for (FontStyle fs : FontStyle.values()) {
            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();
            switch (fs) {
                case BOLD:
                    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                    break;
                case BOLD_ITALICS:
                    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                    font.setItalic(true);
                    break;
                case ITALICS:
                    font.setItalic(true);
                    break;
                case NORMAL:
                    break;
            }
            style.setFont(font);
            styles.put(fs, style);
        }
    }

    private void createSheet(String name) {
        if (!sheets.containsKey(name)) {
            sheets.put(name, wb.createSheet(name));
        }
    }

    public void setData(String sheetName, int rowNr, int colNr, String data) {
        Sheet sheet = getOrCreateSheet(sheetName);
        Cell cell = getOrCreateCell(sheet, rowNr, colNr);
        cell.setCellValue(wb.getCreationHelper().createRichTextString(data));
    }

    public void setData(String sheetName, int rowNr, int colNr, double data) {
        Sheet sheet = getOrCreateSheet(sheetName);
        Cell cell = getOrCreateCell(sheet, rowNr, colNr);
        cell.setCellValue(data);
    }

    public void setData(String sheetName, int rowNr, int colNr, Date data) {
        Sheet sheet = getOrCreateSheet(sheetName);
        Cell cell = getOrCreateCell(sheet, rowNr, colNr);
        cell.setCellValue(data);
        cell.setCellStyle(dateStyle);
    }

    public void setData(String sheetName, int rowNr, int colNr, boolean data) {
        Sheet sheet = getOrCreateSheet(sheetName);
        Cell cell = getOrCreateCell(sheet, rowNr, colNr);
        cell.setCellValue(data);
    }

    public void setCellFontStyle(String sheetName, int rowNr, int colNr, FontStyle fs) {
        Sheet sheet = getOrCreateSheet(sheetName);
        getOrCreateCell(sheet, rowNr, colNr).setCellStyle(styles.get(fs));
    }

    public void createTitleRow(String sheetName, String... columnNames) {
        for (int i = 0; i < columnNames.length; i++) {
            setData(sheetName, 0, i, columnNames[i]);
            setCellFontStyle(sheetName, 0, i, FontStyle.BOLD);
        }
    }

    private Sheet getOrCreateSheet(String sheetName) {
        if (!sheets.containsKey(sheetName)) {
            createSheet(sheetName);
        }
        return sheets.get(sheetName);
    }

    private Row getOrCreateRow(Sheet sheet, int rowNr) {
        Row row = sheet.getRow(rowNr);
        if (row == null) {
            row = sheet.createRow(rowNr);
        }
        return row;
    }

    private Cell getOrCreateCell(Sheet sheet, int rowNr, int colNr) {
        Row row = getOrCreateRow(sheet, rowNr);
        Cell cell = row.getCell(colNr);
        if (cell == null) {
            cell = row.createCell(colNr);
        }
        return cell;
    }

    public void write(OutputStream os) throws IOException {
        wb.write(os);
        os.flush();
    }

    public String getExtension() {
        return "xls";
    }

    public interface RowMapper<T> {
        T mapRow(List<String> celValues);
    }

    public <T> List<T> values(RowMapper<T> mapper) {
        Sheet sheet = null;
        Row row = null;
        List<T> bookData = Lists.newArrayList();
        DataFormatter formatter = new DataFormatter();

        int numSheets = this.wb.getNumberOfSheets();
        for (int i = 0; i < numSheets; i++) {

            sheet = this.wb.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {

                int lastRowNum = sheet.getLastRowNum();
                for (int j = 0; j <= lastRowNum; j++) {
                    row = sheet.getRow(j);
                    List<String> cellValues = Lists.newArrayList();

                    int lastCellNum = row.getLastCellNum();

                    for (int k = 0; k <= lastCellNum; k++) {
                        cellValues.add(formatter.formatCellValue(row.getCell(k)));
                    }
                    bookData.add(mapper.mapRow(cellValues));
                }
            }
        }
        return bookData;
    }

    public static SimpleWorkbook fromStream(InputStream workbookStream) {
        SimpleWorkbook sWorkbook = null;
        try {
            sWorkbook = new SimpleWorkbook(WorkbookFactory.create(workbookStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sWorkbook;
    }
    
    public static SimpleWorkbook mergeWorkbook(SimpleWorkbook... workbooks){
        SimpleWorkbook mergeWorkbook = new SimpleWorkbook();
        int num = 0;
        for(SimpleWorkbook workbook : workbooks){
            int numSheets = workbook.getWb().getNumberOfSheets();
            for (int i = 0; i < numSheets; i++) {
                Sheet sheet = workbook.getWb().getSheetAt(i);
                String sheetName = sheet.getSheetName();
                if(mergeWorkbook.getWb().getSheet(sheetName) != null){
                    num ++;
                    sheetName = sheetName + "(" + num + ")";
                }
                mergeWorkbook.createSheet(sheetName);
                if (sheet.getPhysicalNumberOfRows() > 0) {
                    int lastRowNum = sheet.getLastRowNum();
                    for (int j = 0; j <= lastRowNum; j++) {
                        Row row = sheet.getRow(j);
                        if(row != null){
                            int lastCellNum = row.getLastCellNum();
                            for (int k = 0; k <= lastCellNum; k++) {
                                Cell cell = row.getCell(k);
                                String cellValue = cell == null ? "" : cell.toString();
                                mergeWorkbook.setData(sheetName, j+1, k, cellValue == null ? "" : cellValue);
                            }
                        }
                    }
                }
            }
        }
        return mergeWorkbook;
    }

    public Workbook getWb() {
        return wb;
    }
}
