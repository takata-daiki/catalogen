package cz.iglu.varad.poiexcel;

import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

/**
 * When a value of cell A is computed using a value in cell B then in MS Office
 * changing B don't automatically recalculate (refresh) a value of A.
 * You have to force this recalculation as shown bellow.
 * (OpenOffice recalculates the values automatically when opening XLS files)
 *
 * This example uses Apache POI v3.5-FINAL
 *
 * @author Radek Varbuchta
 */
public class App {

    // Contains: A2=0,B2=2,C2=SUM(A2:B2)
    private static final String XLS_TEMPLATE = "/sheet.xls";
    private static final String OUTPUT = "c:/excel/OUT.xls";

    public static void main(String[] args) throws Exception {
        InputStream is = App.class.getResourceAsStream(XLS_TEMPLATE);
        if (is == null) {
            throw new IllegalStateException("InputStream of " + XLS_TEMPLATE + "cannot be null.");
        }

        Workbook wb = new HSSFWorkbook(is);

        // set new value to A2
        Sheet sheet1 = wb.getSheetAt(0);
        sheet1.getRow(1).getCell(0).setCellValue(10);


        // http://poi.apache.org/spreadsheet/eval.html#EvaluateAll
        // refresh start
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        evaluator.clearAllCachedResultValues();
        for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = wb.getSheetAt(sheetNum);
            for (Row r : sheet) {
                for (Cell c : r) {
                    if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                        evaluator.evaluateFormulaCell(c);
                    }
                }
            }
        } // refresh end

        is.close();

        FileOutputStream fileOut = new FileOutputStream(OUTPUT);
        wb.write(fileOut);
        fileOut.close();
    }
}
