/*
 * Copyright (c) 2012.
 * This is part of the project "PrometaJava"
 * from Sven Ruppert for infotraX GmbH, please contact chef@sven-ruppert.de
 */

package org.rapidpm.data.table.export;

import org.rapidpm.data.table.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Sven Ruppert - www.svenruppert.de
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 16.06.2010
 *        Time: 10:48:51
 */

public class Table2XLSX implements TableExport<ByteArrayOutputStream> {
    private static final Logger logger = Logger.getLogger(Table2XLSX.class);

    @Override
    public ByteArrayOutputStream export(final Table table) {
        final XSSFWorkbook workbook = new XSSFWorkbook();

        final XSSFSheet xssfSheet = workbook.createSheet(table.getTableName());
        final Collection<ColumnInformation> infoList = table.getColumnInfoList();
        final XSSFRow xssfHeaderRow = xssfSheet.createRow(0);
        for (final ColumnInformation information : infoList) {
            if (information.isExportable()) {
                final XSSFCell xssfCell = xssfHeaderRow.createCell(information.getSpaltenNr());
                xssfCell.setCellValue(information.getSpaltenName());
                xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("ColInfo not exportable " + information.getSpaltenName());
                }
            }
        }

        final List<Row> tableRowList = table.getRowList();
        for (final Row row : tableRowList) {
            final XSSFRow xssfRow = xssfSheet.createRow(row.getRowNr() + 1);
            final List<Cell> cellList = row.getCells();
            for (final Cell cell : cellList) {
                if (cell.getColInfo().isExportable()) {
                    final XSSFCell xssfCell = xssfRow.createCell(cell.getColInfo().getSpaltenNr());
                    final CellTypeEnum cellType = cell.getCellType();
                    if (cellType.equals(CellTypeEnum.RawData)) {
                        xssfCell.setCellValue(cell.getFormattedValue());
                        xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING); //TODO CellType in Abh?ngigkeit der ValueClass z.B. Number
                    } else if (cellType.equals(CellTypeEnum.RawLink)) {
                        final XSSFCreationHelper helper = workbook.getCreationHelper();
                        final XSSFHyperlink xssfHyperlink = helper.createHyperlink(Hyperlink.LINK_URL);
                        xssfHyperlink.setAddress(cell.getFormattedValue());
                        xssfHyperlink.setLabel(cell.getLabel());
                        xssfCell.setCellValue(cell.getLabel());
                        xssfCell.setHyperlink(xssfHyperlink);

                        final CellStyle hlink_style = createHyperLinkStyle(workbook);
                        xssfCell.setCellStyle(hlink_style);
                    } else {

                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Cell not exportable ");
                    }
                }

            }
        }

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            logger.error(e);
        }
        return outputStream;
    }

    private CellStyle createHyperLinkStyle(final XSSFWorkbook workbook) {
        final CellStyle hlink_style = workbook.createCellStyle();
        final Font hlink_font = workbook.createFont();
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        hlink_style.setFont(hlink_font);
        return hlink_style;
    }
}
