package jandcode.excelreport.impl;

import jandcode.utils.*;
import jandcode.utils.error.*;
import jandcode.excelreport.*;
import jandcode.excelreport.impl.databinder.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.joda.time.*;

import java.util.*;

public class ReportSheetImpl extends ReportSheet {

    protected HSSFSheet shTemplate;
    protected HSSFSheet shResult;
    protected int shResultIdx;
    protected HSSFWorkbook wbResult;
    protected HSSFWorkbook wbTemplate;

    // текущие позиции для вывода
    protected int curR;
    protected int curC;

    protected ListNamed<BandInfo> bands;
    protected int maxCol;
    protected SubstVars substVars = new SubstVars();

    // стили в формате  [стиль-шаблона]->стиль-результата
    protected Map<CellStyle, StyleInfo> cellStyles;

    class SubstVars implements ISubstVar {

        Object data;
        DataBinder dataBinder = new DataBinderDelegate();

        void setData(Object data) {
            this.data = data;
        }

        public String onSubstVar(String v) {
            try {
                if (UtString.empty(v)) {
                    return "";
                }
                int a = v.indexOf(':');
                if (a == -1) {
                    return dataBinder.getVarValue(data, v, "");
                } else {
                    return dataBinder.getVarValue(data, v.substring(0, a), v.substring(a + 1));
                }
            } catch (Exception e) {
                return "";
            }
        }
    }


    class BandInfo extends Named {
        int c1, c2, r1, r2;
        boolean wholeCols; // все колонки
        List<Cell> cells = new ArrayList<Cell>();
        List<Row> rows = new ArrayList<Row>();
        public List<CellRangeAddress> merges = new ArrayList<CellRangeAddress>();
    }

    class StyleInfo {
        CellStyle tmlStyle;
        CellStyle resStyle;
        boolean isDate;
        boolean isNumber;
    }

    public ReportSheetImpl(HSSFSheet shTemplate, HSSFSheet shResult) {
        this.shTemplate = shTemplate;
        this.shResult = shResult;
        this.wbTemplate = this.shTemplate.getWorkbook();
        this.wbResult = this.shResult.getWorkbook();
        this.shResultIdx = this.wbResult.getSheetIndex(this.shResult);
        //
        parseSheet();
    }

    protected void parseSheet() {
        shResult.setDefaultColumnWidth(shTemplate.getDefaultColumnWidth());
        shResult.setDefaultRowHeight(shTemplate.getDefaultRowHeight());
        //
        ArrayList<Cell> cells = new ArrayList<Cell>();
        // all cells
        for (Row row : shTemplate) {
            for (Cell cell : row) {
                cells.add(cell);
                maxCol = Math.max(maxCol, cell.getColumnIndex());
            }
        }
        //
        bands = new ListNamed<BandInfo>();
        int numNames = shTemplate.getWorkbook().getNumberOfNames();
        for (int i = 0; i < numNames; i++) {
            HSSFName nm = shTemplate.getWorkbook().getNameAt(i);

            AreaReference aref = new AreaReference(nm.getRefersToFormula());

            BandInfo band = new BandInfo();
            band.setName(nm.getNameName());
            band.c1 = aref.getFirstCell().getCol();
            band.c2 = aref.getLastCell().getCol();
            band.r1 = aref.getFirstCell().getRow();
            band.r2 = aref.getLastCell().getRow();
            band.wholeCols = aref.isWholeColumnReference();
            //
            for (int j = band.r1; j <= band.r2; j++) {
                band.rows.add(shTemplate.getRow(j));
            }
            // cells for band
            for (Cell cell : cells) {
                int ri = cell.getRowIndex();
                int ci = cell.getColumnIndex();

                if (ri >= band.r1 && ri <= band.r2 && ci >= band.c1 && ci <= band.c2) {
                    band.cells.add(cell);
                }
            }
            // merges for band
            int mergCnt = shTemplate.getNumMergedRegions();
            for (int k = 0; k < mergCnt; k++) {
                CellRangeAddress mr = shTemplate.getMergedRegion(k);
                int ri = mr.getFirstRow();
                int ci = mr.getFirstColumn();

                if (ri >= band.r1 && ri <= band.r2 && ci >= band.c1 && ci <= band.c2) {
                    band.merges.add(mr);
                }
            }
            //
            bands.add(band);
        }

        // стили
        cellStyles = new HashMap<CellStyle, StyleInfo>();
        for (short i = 0; i < wbTemplate.getNumCellStyles(); i++) {
            HSSFCellStyle tmlStyle = wbTemplate.getCellStyleAt(i);
            HSSFCellStyle resStyle = wbResult.createCellStyle();
            resStyle.cloneStyleFrom(tmlStyle);
            StyleInfo styleInfo = new StyleInfo();
            styleInfo.tmlStyle = tmlStyle;
            styleInfo.resStyle = resStyle;
            styleInfo.isDate = isStyleDate(resStyle);
            styleInfo.isNumber = isStyleNumber(resStyle);
            cellStyles.put(tmlStyle, styleInfo);
        }

        // columns widths & styles
        for (int i = 0; i <= maxCol; i++) {
            HSSFCellStyle st = shTemplate.getColumnStyle(i);
            if (st != null) {
                shResult.setDefaultColumnStyle(i, getResultCellStyle(st).resStyle);
            }
            shResult.setColumnWidth(i, shTemplate.getColumnWidth(i));
            if (shTemplate.isColumnHidden(i)) {
                shResult.setColumnHidden(i, true);
            }
        }

    }

    public void setSheetTitle(String title) {
        shResult.getWorkbook().setSheetName(shResultIdx, title);
    }

    public boolean hasBand(String nameBand) {
        return bands.find(nameBand) != null;
    }

    public void out(String nameBand, Object data) {
        substVars.setData(data);
        //
        BandInfo band = bands.get(nameBand);
        int baseRow = curR;
        for (Row row : band.rows) {
            if (row == null) {
                Row emptyRow = shResult.createRow(curR);
                Cell emptyCell = emptyRow.createCell(0);
                emptyRow.setHeight(shTemplate.getDefaultRowHeight());
                curR++;   // тут не заполненная строка
                continue;
            }
            Row newRow = shResult.createRow(curR);
            newRow.setHeight(row.getHeight());
            //
            curR++;
            for (Cell cell : band.cells) {
                if (cell.getRowIndex() == row.getRowNum()) {
                    Cell newCell = newRow.createCell(cell.getColumnIndex());
                    copyCell(cell, newCell);
                }
            }
        }
        // merges
        if (!band.merges.isEmpty()) {
            for (CellRangeAddress mr : band.merges) {
                CellRangeAddress mrNew = new CellRangeAddress(
                        mr.getFirstRow() + baseRow,
                        mr.getLastRow() + baseRow,
                        mr.getFirstColumn(),
                        mr.getLastColumn()
                );

                shResult.addMergedRegion(mrNew);
            }
        }

    }

    protected void copyCell(Cell oldCell, Cell newCell) {
        // Copy style from old cell and apply to new cell
        StyleInfo styleInfo = getResultCellStyle(oldCell.getCellStyle());
        newCell.setCellStyle(styleInfo.resStyle);

        // If there is a cell comment, copy
        if (newCell.getCellComment() != null) {
            newCell.setCellComment(oldCell.getCellComment());
        }

        // If there is a cell hyperlink, copy
        if (oldCell.getHyperlink() != null) {
            newCell.setHyperlink(oldCell.getHyperlink());
        }

        String s, s1;
        // Set the cell data value
        switch (oldCell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                s = oldCell.getRichStringCellValue().getString();
                if (s.startsWith("=")) {
                    // формула
                    s = s.substring(1);
                    s1 = UtString.substVar(s, substVars);
                    if (!UtString.empty(s1)) {
                        try {
                            newCell.setCellType(Cell.CELL_TYPE_FORMULA);
                            newCell.setCellFormula(s1);
                        } catch (FormulaParseException e) {
                            newCell.setCellValue("!ERROR");
                        }
                    }
                } else {
                    s1 = UtString.substVar(s, substVars);
                    if (styleInfo.isDate) {
                        LocalDateTime dt = UtCnv.toDateTime(s1);
                        if (!UtDate.isEmpty(dt)) {
                            newCell.setCellValue(dt.toDate());
                        } else {
                            newCell.setCellValue(s1);
                        }
                    } else if (styleInfo.isNumber) {
                        double v = UtCnv.toDouble(s1, Double.MIN_VALUE);
                        if (v != Double.MIN_VALUE) {
                            newCell.setCellValue(v);
                        } else {
                            newCell.setCellValue(s1);
                        }
                    } else {
                        newCell.setCellValue(s1);
                    }
                }
                break;
        }
    }

    protected StyleInfo getResultCellStyle(CellStyle tmlStyle) {
        StyleInfo st = cellStyles.get(tmlStyle);
        if (st != null) {
            return st;
        }
        throw new XError("Style not found!");
    }

    protected boolean isStyleNumber(CellStyle style) {
        String s = style.getDataFormatString();
        if (s == null) {
            return false;
        }
        return s.indexOf('0') != -1 || s.indexOf('#') != -1;
    }

    protected boolean isStyleDate(CellStyle style) {
        String s = style.getDataFormatString();
        if (s == null) {
            return false;
        }
        return DateUtil.isADateFormat(style.getFontIndex(), style.getDataFormatString());
    }
}
