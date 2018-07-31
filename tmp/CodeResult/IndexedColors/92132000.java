package by.q64.promo.excelgen.service.sellout.sheet;

import by.q64.promo.excelgen.constructor.tools.ExcelGenTools;
import by.q64.promo.excelgen.service.sellout.source.SellOutCommonInfoContainer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by Pavel on 09.10.2014.
 */
@Service("sellOutLastRowPanelConstructor")
public class SellOutLastRowPanelConstructor implements SellOutPartConstructor {
    @Autowired
    private ExcelGenTools excelGenTools;
    @Override
    public int construct(Sheet sheet, SellOutCommonInfoContainer sellOutCommonInfoContainer,
                         Integer rowNumber, Integer colStart) {

        Row row = sheet.getRow(rowNumber);
        short lastCellNum = row.getLastCellNum();
        row.setHeight((short) excelGenTools.getWidthFromAbstractUnits(2));

        Workbook workbook = sheet.getWorkbook();
        CellStyle style1 = workbook.createCellStyle();
        CellStyle style2 = workbook.createCellStyle();

        style1.setWrapText(true);
        style1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style1.setBorderBottom(CellStyle.BORDER_THIN);
        style1.setBorderTop(CellStyle.BORDER_THIN);
        style1.setBorderLeft(CellStyle.BORDER_THIN);
        style1.setBorderRight(CellStyle.BORDER_THIN);
        short borderColorIndex = IndexedColors.WHITE.getIndex();
        style1.setBottomBorderColor(borderColorIndex);
        style1.setTopBorderColor(borderColorIndex);
        style1.setLeftBorderColor(borderColorIndex);
        style1.setRightBorderColor(borderColorIndex);

        style2.cloneStyleFrom(style1);

        Font font1 = workbook.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font1.setColor(IndexedColors.GREEN.getIndex());

        Font font2 = workbook.createFont();
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font2.setColor(IndexedColors.RED.getIndex());

        style1.setFont(font1);
        style2.setFont(font2);

        for (int i = colStart; i <= lastCellNum - 3; i++) {
            row.getCell(i).setCellStyle(style1);
        }
        //fill total sales and counts
        row.getCell(lastCellNum - 1).setCellStyle(style2);
        row.getCell(lastCellNum - 2).setCellStyle(style2);
        return rowNumber;
    }
}
