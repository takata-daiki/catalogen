package by.q64.promo.excelgen.service.schedule.sheet;

import by.q64.promo.excelgen.constructor.SheetTemplateConstructor;
import by.q64.promo.excelgen.constructor.entity.OptionalApply;
import by.q64.promo.excelgen.constructor.entity.properties.ColumnProperties;
import by.q64.promo.excelgen.constructor.entity.properties.MainHeaderProperties;
import by.q64.promo.excelgen.constructor.entity.properties.SheetTemplateProperties;
import by.q64.promo.excelgen.constructor.tools.ExcelGenTools;
import by.q64.promo.excelgen.service.schedule.source.DayProperties;
import by.q64.promo.excelgen.service.schedule.source.ShopScheduleRowContainer;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Pavel on 22.09.2014.
 */
@Service
public class CityWeekScheduleSheetGeneratorImpl implements CityWeekScheduleSheetGenerator {

    @Autowired
    @Qualifier("cityWeekScheduleSheetTemplateConstructor")
    private SheetTemplateConstructor cityWeekScheduleSheetTemplateConstructor;

    @Autowired
    private ExcelGenTools excelGenTools;

    @Autowired
    private WorkbookColorManagerFactory workbookColorManagerFactory;

    @Override
    public Workbook generate(HSSFWorkbook workbook, String title, List<String> datesColumns,
                             List<ShopScheduleRowContainer> scheduleRowContainers) {

        MainHeaderProperties mainHeaderProperties = new MainHeaderProperties();
        mainHeaderProperties.setHeaderDepth((short) 2);
        mainHeaderProperties.setAutoFilter(true);
        mainHeaderProperties.setHeaderRowsHeights(Arrays.asList((short) excelGenTools.getWidthFromAbstractUnits(3),
                (short) excelGenTools.getWidthFromAbstractUnits(3)));

        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerMainCellStyle = workbook.createCellStyle();
        headerMainCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerMainCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerMainCellStyle.setWrapText(true);
        headerMainCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerMainCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerMainCellStyle.setFont(font);
        headerMainCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerMainCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerMainCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerMainCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        short borderColorIndex = IndexedColors.WHITE.getIndex();
        headerMainCellStyle.setBottomBorderColor(borderColorIndex);
        headerMainCellStyle.setTopBorderColor(borderColorIndex);
        headerMainCellStyle.setLeftBorderColor(borderColorIndex);
        headerMainCellStyle.setRightBorderColor(borderColorIndex);

        mainHeaderProperties.setMainHeaderStyle(headerMainCellStyle);

        SheetTemplateProperties<ShopScheduleRowContainer> sheetTemplateProperties = new SheetTemplateProperties<>();

        sheetTemplateProperties.setStartCol((short) 0);
        sheetTemplateProperties.setStartRow((short) 0);
        sheetTemplateProperties.setDefaultRowHeight((short) excelGenTools.getWidthFromAbstractUnits(4));

        sheetTemplateProperties.setMainHeaderProperties(mainHeaderProperties);


        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setWrapText(true);
        dataCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        dataCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        sheetTemplateProperties.setDataCellStyle(dataCellStyle);


        sheetTemplateProperties.setColumnPropertiesList(generateColumnProperties(datesColumns, workbook));
        sheetTemplateProperties.setDataSource(scheduleRowContainers);
        sheetTemplateProperties.setTitle(title);


        cityWeekScheduleSheetTemplateConstructor.generateSheet(workbook, sheetTemplateProperties);
        return workbook;
    }


    private List<ColumnProperties<ShopScheduleRowContainer>> generateColumnProperties(List<String> datesColumns, HSSFWorkbook hssfWorkbook) {
        WorkbookColorManager workbookColorManager = workbookColorManagerFactory.get(hssfWorkbook);

        ArrayList<ColumnProperties<ShopScheduleRowContainer>> result = new ArrayList<ColumnProperties<ShopScheduleRowContainer>>();
        result.add(netColumn);
        result.add(addressColumn);

        for (int i = 0; i < weekdays.length; i++) {
            final int finalI = i;
            result.add(new ColumnProperties<ShopScheduleRowContainer>(weekdays[i], Arrays.asList(
                    new ColumnProperties<>(datesColumns.get(i), 11, ssrc -> {
                        DayProperties dayProperties = ssrc.getDayPropertieses().get(finalI);
                        return dayProperties.getPromoter() + "\n" + dayProperties.getStart() + " - " + dayProperties.getEnd();
                    }, null, Arrays.<OptionalApply<ShopScheduleRowContainer>>asList(new OptionalApply<>(v -> true, (cell, ssrc) -> {
                        DayProperties dayProperties = ssrc.getDayPropertieses().get(finalI);
                        String color = dayProperties.getColor();
                        if (color != null) {
                            CellStyle cellStyle = hssfWorkbook.createCellStyle();
                            cellStyle.cloneStyleFrom(cell.getCellStyle());

                            cellStyle.setFillForegroundColor(workbookColorManager.getColorIndex(color));
                            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                            cell.setCellStyle(cellStyle);
                        }
                        return ssrc;
                    })))
            )));
        }


        result.add(supervisorColumn);
        result.add(promoFormColumn);
        return result;
    }

    private final ColumnProperties<ShopScheduleRowContainer> netColumn = new ColumnProperties<>("Сеть", 40, ShopScheduleRowContainer::getNet),
            addressColumn = new ColumnProperties<>("Адрес", 40, ShopScheduleRowContainer::getShop),
            supervisorColumn = new ColumnProperties<>("Супервайзер", 40, ShopScheduleRowContainer::getSupervisor),
            promoFormColumn = new ColumnProperties<>("Форма", 40, ShopScheduleRowContainer::getPromoFormAsString);
    private final String[] weekdays = {"пн", "вт", "ср", "чт", "пт", "сб", "вс"};
}

@Component
class WorkbookColorManagerFactory {
    public WorkbookColorManager get(HSSFWorkbook workbook) {
        return new WorkbookColorManager(workbook);
    }
}

class WorkbookColorManager {
    private HSSFPalette palette;
    private short curColorIndex = 41; // numbers before is occupied
    private Map<String, Short> colors = new HashMap<>();

    WorkbookColorManager(HSSFWorkbook workbook) {
        palette = workbook.getCustomPalette();
    }

    public Short getColorIndex(String sixNumbers) {
        Short colorIndex = colors.get(sixNumbers);
        if (colorIndex == null) {
            colorIndex = createNewColor(sixNumbers);
        }
        return colorIndex;
    }

    protected Short createNewColor(String sixNumbers) {
        String[] strParts = sixNumbers.split("(?<=\\G.{2})");
        int red = Integer.valueOf(strParts[0], 16);
        int green = Integer.valueOf(strParts[1], 16);
        int blue = Integer.valueOf(strParts[2], 16);
        palette.setColorAtIndex((byte) curColorIndex, (byte) red, (byte) green, (byte) blue);
        colors.put(sixNumbers, curColorIndex);
        return curColorIndex++;
    }

}

