
import com.manticore.report.ExcelTools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.FormulaParser;
import org.apache.poi.ss.formula.FormulaParsingWorkbook;
import org.apache.poi.ss.formula.FormulaRenderingWorkbook;
import org.apache.poi.ss.formula.FormulaType;
import org.apache.poi.ss.formula.ptg.Ptg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Copyright (C) 2015 are
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 *
 * @author are
 */
public class ExcelFormulaParser {

  public static void main(String[] args) throws IOException, InvalidFormatException {
    Workbook wb = WorkbookFactory.create(new File("/home/are/RiskAssets.xlsx"));
    Sheet sheet = wb.getSheet("R1");
    FormulaParsingWorkbook parsingWorkbook = null;
    FormulaRenderingWorkbook renderingWorkbook = null;
    if (wb instanceof HSSFWorkbook) {
      parsingWorkbook = HSSFEvaluationWorkbook.create((HSSFWorkbook) wb);
      renderingWorkbook = HSSFEvaluationWorkbook.create((HSSFWorkbook) wb);
    } else if (wb instanceof XSSFWorkbook) {
      parsingWorkbook = XSSFEvaluationWorkbook.create((XSSFWorkbook) wb);
      renderingWorkbook = XSSFEvaluationWorkbook.create((XSSFWorkbook) wb);
    }
    for (int r = 13; r < 100000; r++) {
      ExcelTools.shiftRows(wb, sheet, parsingWorkbook, renderingWorkbook, r);
      
//      Row row = sheet.createRow(r);
//      Cell cell = row.createCell(1);
//
//      if (r > 1) {
//        cell.setCellFormula("B" + r + "+1");
//        String formula = cell.getCellFormula();
//        Ptg[] ptgs
//                = FormulaParser.parse(formula, parsingWorkbook, FormulaType.CELL,
//                        wb.getSheetIndex(sheet));
//      } else {
//        cell.setCellValue(1);
//      }
      System.out.println(r);
    }
    try {
      wb.write(new FileOutputStream("/tmp/test.xlsx"));
    } catch (IOException ex) {
      Logger.getLogger(ExcelFormulaParser.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
