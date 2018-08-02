/*
 * Copyright (C) 2014 Andreas Reichel <andreas@manticore-projects.com>
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
package com.manticore.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.FormulaParsingWorkbook;
import org.apache.poi.ss.formula.FormulaRenderingWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Andreas Reichel <andreas@manticore-projects.com>
 */
public class FormulaTest {

    public static void main(String[] args) throws Exception {
        for (int k=0; k<10000; k++) {
        File f = new File("/tmp/test.xls");
        InputStream inputStream = new FileInputStream(f);
        final Workbook workbook = WorkbookFactory.create(inputStream);
        final FormulaParsingWorkbook parsingWorkbook = (workbook instanceof HSSFWorkbook)
                                                       ? HSSFEvaluationWorkbook.create((HSSFWorkbook) workbook)
                                                       : XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);
        final FormulaRenderingWorkbook renderingWorkbook = (workbook instanceof HSSFWorkbook)
                                                           ? HSSFEvaluationWorkbook.create((HSSFWorkbook) workbook)
                                                           : XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);
        
        Sheet sheet1 = workbook.getSheetAt(0);
        for (int i=1; i<8; i++) {
            sheet1.getRow(i).getCell(2).setCellValue(Math.random());
        }

        FormulaEvaluator evaluator = workbook.getCreationHelper()
            .createFormulaEvaluator();
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);
            for (Row r : sheet) {
                for (Cell c : r) {
                    if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                        evaluator.evaluateFormulaCell(c);
                    }
                }
            }
        }

        int namedCellIdx = workbook.getNameIndex("RatingResult");
        Name aNamedCell = workbook.getNameAt(namedCellIdx);

        // retrieve the cell at the named range and test its contents
        AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula());
        CellReference[] crefs = aref.getAllReferencedCells();
        for (int i = 0; i < crefs.length; i++) {
            Sheet sheet = workbook.getSheet(crefs[i].getSheetName());
            Row r = sheet.getRow(crefs[i].getRow());
            Cell cell = r.getCell(crefs[i].getCol());
            // extract the cell contents based on cell type etc.
            
            System.out.println(k + " Found RatingResult: " + cell.getNumericCellValue());
        }
    }
    }
}
