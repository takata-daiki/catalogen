package by.q64.promo.excelgen.service.sellout.sheet;

import by.q64.promo.excelgen.constructor.SheetTemplateConstructor;
import by.q64.promo.excelgen.constructor.entity.properties.*;
import by.q64.promo.excelgen.constructor.tools.ExcelGenTools;
import by.q64.promo.excelgen.service.sellout.source.SellOutCommonInfoContainer;
import by.q64.promo.excelgen.service.sellout.source.SellOutSourceContainer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Pavel on 25.09.2014.
 */
@Service
public class SellOutSheetGeneratorImpl implements SellOutMiniSheetGenerator {
    @Autowired
    @Qualifier("sellOutSheetTemplateConstructor")
    private SheetTemplateConstructor<SellOutSourceContainer> sheetTemplateConstructor;
    @Autowired
    private ExcelGenTools excelGenTools;
    @Autowired
    @Qualifier("sellOutWeekPanelConstructor")
    private SellOutPartConstructor sellOutWeekPanelConstructor;
    @Autowired
    @Qualifier("sellOutLastRowPanelConstructor")
    private SellOutPartConstructor sellOutLastRowPanelConstructor;
    @Autowired
    @Qualifier("sellOutPromotersInDaysPanelConstructor")
    private SellOutPartConstructor sellOutPromotersInDaysPanelConstructor;


    private final List<ColumnProperties<SellOutSourceContainer>> staticHeaderStart;
    private final List<ColumnProperties<SellOutSourceContainer>> staticHeaderEnd;
    private final Map<String, Function<SellOutCommonInfoContainer, String>> commonInfoCells = new LinkedHashMap<>();

    public SellOutSheetGeneratorImpl() {
        staticHeaderStart = Arrays.<ColumnProperties<SellOutSourceContainer>>asList(
                new ColumnProperties<>("Product Name", true, 24, SellOutSourceContainer::getModelName),
                new ColumnProperties<>("Price without VAT", true, 19, SellOutSourceContainer::getPriceWithoutTax),
                new ColumnProperties<>("Price with VAT", true, 19, SellOutSourceContainer::getPriceWithTax));

        staticHeaderEnd = Arrays.<ColumnProperties<SellOutSourceContainer>>asList(
                new ColumnProperties<>("Total", true, 19, s -> String.valueOf(s.getTotalSalesCount())),
                new ColumnProperties<>("Total sales in RUR up to date", true, 19, s -> String.valueOf(s.getTotalSalesPrice()))
        );

        commonInfoCells.put("Promoter Name", SellOutCommonInfoContainer::getPromoterName);
        commonInfoCells.put("Promoter Agency", SellOutCommonInfoContainer::getPromoterAgency);
        commonInfoCells.put("Store Name", SellOutCommonInfoContainer::getShopName);
        commonInfoCells.put("Store Address", SellOutCommonInfoContainer::getShopAddress);
        commonInfoCells.put("Country", SellOutCommonInfoContainer::getCountry);
    }

    @Override
    public Workbook generate(Workbook workbook,
                             Collection<SellOutSourceContainer> sellOutSourceContainers, Collection<String> dates,
                             SellOutCommonInfoContainer sellOutCommonInfoContainer) {
        SellOutSourceContainer resultSourceContainer = sellOutCommonInfoContainer.getResultSourceContainer();
        MainHeaderProperties mainHeaderProperties = new MainHeaderProperties();
        mainHeaderProperties.setHeaderDepth((short) 1);
        mainHeaderProperties.setAutoFilter(false);
        mainHeaderProperties.setHeaderRowsHeights(Arrays.asList((short) excelGenTools.getWidthFromAbstractUnits(5)));
        CellStyle commonHeaderCellStyle = getCommonHeaderCellStyle(workbook);
        CellStyle salesHeaderCellStyle = workbook.createCellStyle();
        salesHeaderCellStyle.cloneStyleFrom(commonHeaderCellStyle);
        salesHeaderCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        salesHeaderCellStyle.setFont(font);

        mainHeaderProperties.setHeaderRangeManager(new HeaderRangeManager(
                new HeaderRangeProperties(commonHeaderCellStyle, 0, 2),
                new HeaderRangeProperties(salesHeaderCellStyle, 3, 2 + dates.size() + 2)
        ));

        SheetTemplateProperties<SellOutSourceContainer> sheetTemplateProperties = new SheetTemplateProperties<>();
        sheetTemplateProperties.setTitle(sellOutCommonInfoContainer.getShopAddress());
        sheetTemplateProperties.setStartCol((short) 0);
        sheetTemplateProperties.setStartRow((short) 8);
        sheetTemplateProperties.setDefaultRowHeight((short) excelGenTools.getWidthFromAbstractUnits(2));

        sheetTemplateProperties.setColumnPropertiesList(generateColumnProperties(dates, sellOutSourceContainers));
        sheetTemplateProperties.setMainHeaderProperties(mainHeaderProperties);
        ArrayList<SellOutSourceContainer> sellOutSourceContainers1 = new ArrayList<>(sellOutSourceContainers);
        sellOutSourceContainers1.add(resultSourceContainer);
        sheetTemplateProperties.setDataSource(sellOutSourceContainers1);

        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setWrapText(true);
        sheetTemplateProperties.setDataCellStyle(dataCellStyle);

        Sheet sheet = sheetTemplateConstructor.generateSheet(workbook, sheetTemplateProperties);
        generateCommonInfo(sheet, sellOutCommonInfoContainer, sheetTemplateProperties);
        return workbook;
    }

    private CellStyle getCommonHeaderCellStyle(Workbook workbook) {
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
        short borderColorIndex = IndexedColors.BLACK.getIndex();
        headerMainCellStyle.setBottomBorderColor(borderColorIndex);
        headerMainCellStyle.setTopBorderColor(borderColorIndex);
        headerMainCellStyle.setLeftBorderColor(borderColorIndex);
        headerMainCellStyle.setRightBorderColor(borderColorIndex);

        return headerMainCellStyle;
    }

    private void generateCommonInfo(Sheet sheet,
                                    SellOutCommonInfoContainer sellOutCommonInfoContainer,
                                    SheetTemplateProperties<SellOutSourceContainer> sheetTemplateProperties) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(IndexedColors.RED.getIndex());
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        short defaultRowHeight = (short) excelGenTools.getWidthFromAbstractUnits(3);
        Set<Map.Entry<String, Function<SellOutCommonInfoContainer, String>>> entries = commonInfoCells.entrySet();
        int curRow = 0;
        for (Map.Entry<String, Function<SellOutCommonInfoContainer, String>> entry : entries) {
            Row row = sheet.createRow(curRow);
            row.setHeight(defaultRowHeight);
            String key = entry.getKey();
            Function<SellOutCommonInfoContainer, String> value = entry.getValue();
            Cell cellKey = row.createCell(0);
            Cell cellVal = row.createCell(1);
            cellKey.setCellStyle(cellStyle);
            cellVal.setCellStyle(cellStyle);

            cellKey.setCellValue(key);
            cellVal.setCellValue(value.apply(sellOutCommonInfoContainer));

            sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, 1, 2));
            curRow++;
        }
        curRow = sellOutWeekPanelConstructor.construct(sheet, sellOutCommonInfoContainer, curRow, staticHeaderStart.size());
        curRow = sellOutPromotersInDaysPanelConstructor.construct(sheet, sellOutCommonInfoContainer, curRow + 1, staticHeaderStart.size() - 1);
        sellOutLastRowPanelConstructor.construct(sheet, sellOutCommonInfoContainer, sheet.getLastRowNum(), sheetTemplateProperties.getStartCol().intValue());
    }


    private List<ColumnProperties<SellOutSourceContainer>> generateColumnProperties(Collection<String> dates,
                                                                                    Collection<SellOutSourceContainer> sellOutSourceContainers) {
        List<ColumnProperties<SellOutSourceContainer>> propertieses = new ArrayList<>(dates.size() + staticHeaderStart.size());
        propertieses.addAll(staticHeaderStart);
        for (String date : dates) {
            propertieses.add(new ColumnProperties<SellOutSourceContainer>(date, true, 6, somsc -> {
                Integer sale = somsc.getSalesMap().get(date);
                if (sale != null) {
                    return sale.toString();
                } else {
                    return "";
                }
            }));
        }
        propertieses.addAll(staticHeaderEnd);
        return propertieses;
    }
}
