package by.q64.promo.excelgen.service.promoter.sheet;

import by.q64.promo.excelgen.constructor.SheetTemplateConstructor;
import by.q64.promo.excelgen.constructor.entity.properties.*;
import by.q64.promo.excelgen.constructor.tools.ExcelGenTools;
import by.q64.promo.excelgen.service.promoter.source.PromoterSalesContainer;
import by.q64.promo.excelgen.service.promoter.source.ShipmentsContainer;
import by.q64.promo.excelgen.service.promoter.source.spec.ShipmentSourceSpec;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 18.09.2014.
 */
@Service("commonSalesSheetGenerator")
public class CommonSalesSheetGenerator implements SalesSheetGenerator {
    @Autowired
    @Qualifier("salesSheetTemplateConstructor")
    private SheetTemplateConstructor<PromoterSalesContainer> salesSheetTemplateConstructor;
    @Autowired
    private ExcelGenTools excelGenTools;

    private String title;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public Workbook generateSalesSheet(ShipmentsContainer shipmentSourceSpecs,
                                       Collection<PromoterSalesContainer> promoterSalesContainers, Workbook workbook) {
        MainHeaderProperties mainHeaderProperties = new MainHeaderProperties();
        mainHeaderProperties.setHeaderDepth((short) 2);
        mainHeaderProperties.setAutoFilter(true);
        mainHeaderProperties.setHeaderRowsHeights(Arrays.asList((short) excelGenTools.getWidthFromAbstractUnits(10),
                (short) excelGenTools.getWidthFromAbstractUnits(4)));
        CellStyle commonHeaderCellStyle = getCommonHeaderCellStyle(workbook);
        CellStyle salesHeaderCellStyle = workbook.createCellStyle();
        salesHeaderCellStyle.cloneStyleFrom(commonHeaderCellStyle);
        salesHeaderCellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        salesHeaderCellStyle.setFont(font);

        mainHeaderProperties.setHeaderRangeManager(new HeaderRangeManager(
                new HeaderRangeProperties(commonHeaderCellStyle, 0, 6),
                new HeaderRangeProperties(salesHeaderCellStyle, 7,
                        6 + (shipmentSourceSpecs.getActualShipments().size() +
                                shipmentSourceSpecs.getOtherShipments().size() + 1) * 2)
        ));

        SheetTemplateProperties<PromoterSalesContainer> sheetTemplateProperties = new SheetTemplateProperties<>();
        sheetTemplateProperties.setTitle(title);
        sheetTemplateProperties.setStartCol((short) 0);
        sheetTemplateProperties.setStartRow((short) 0);
        sheetTemplateProperties.setDefaultRowHeight((short) excelGenTools.getWidthFromAbstractUnits(4));

        sheetTemplateProperties.setColumnPropertiesList(generateColumnProperties(shipmentSourceSpecs, promoterSalesContainers));
        sheetTemplateProperties.setMainHeaderProperties(mainHeaderProperties);

        sheetTemplateProperties.setDataSource(promoterSalesContainers);

        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setWrapText(true);
        sheetTemplateProperties.setDataCellStyle(dataCellStyle);

        Sheet sheet = salesSheetTemplateConstructor.generateSheet(workbook, sheetTemplateProperties);
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
        short borderColorIndex = IndexedColors.WHITE.getIndex();
        headerMainCellStyle.setBottomBorderColor(borderColorIndex);
        headerMainCellStyle.setTopBorderColor(borderColorIndex);
        headerMainCellStyle.setLeftBorderColor(borderColorIndex);
        headerMainCellStyle.setRightBorderColor(borderColorIndex);
        return headerMainCellStyle;
    }

    private List<ColumnProperties<PromoterSalesContainer>> generateColumnProperties(ShipmentsContainer shipmentSourceSpecs,
                                                                                    Collection<PromoterSalesContainer> promoterSalesContainers) {
        List<ColumnProperties<PromoterSalesContainer>> propertieses = new ArrayList<>(
                shipmentSourceSpecs.getActualShipments().size() + shipmentSourceSpecs.getOtherShipments().size() + 1
                        + staticHeader.size());
        propertieses.addAll(staticHeader);
        List<AdvancedColumnProperties<PromoterSalesContainer>> salesColumnProperties = shipmentSourceSpecs.getActualShipments().stream().map(ss -> createSalesHeaderShipGroup(ss, 3)).collect(Collectors.toList());
        salesColumnProperties.add(createSalesHeaderShipGroup(shipmentSourceSpecs.getOtherShipmentsFull(), 5));
        salesColumnProperties.addAll(shipmentSourceSpecs.getOtherShipments().stream().map(shipmentSourceSpec -> createSalesHeaderShipGroup(shipmentSourceSpec, 0)).collect(Collectors.toList()));
        propertieses.add(new AbstractColumnProperties<>(PromoterSalesContainer::getSalesMap, salesColumnProperties));
        return propertieses;
    }

    private AdvancedColumnProperties<PromoterSalesContainer> createSalesHeaderShipGroup(ShipmentSourceSpec ss, int width) {
        return new AdvancedColumnProperties<PromoterSalesContainer>(ss.getName(), false, Arrays.asList(
                new AdvancedColumnProperties<>("шт.", false, width, ss.getName() + "count"),
                new AdvancedColumnProperties<>("цена", false, width, ss.getName() + "price")
        ));
    }

    private final List<ColumnProperties<PromoterSalesContainer>> staticHeader = Arrays.asList(
            new ColumnProperties<PromoterSalesContainer>("Город", true, 15, psc -> psc.getFlowInfoSpec().getRegion()),
            new ColumnProperties<PromoterSalesContainer>("Партнер", true, 15, psc -> psc.getFlowInfoSpec().getUnit()),
            new ColumnProperties<PromoterSalesContainer>("Адрес магазина", true, 15, psc -> psc.getFlowInfoSpec().getUnitRegion()),
            new ColumnProperties<PromoterSalesContainer>("Дата", true, 15, psc -> psc.getFlowInfoSpec().getDate().toString()),
            new ColumnProperties<PromoterSalesContainer>("Промоутер", true, 15, psc -> psc.getFlowInfoSpec().getPromoter()),
            new ColumnProperties<PromoterSalesContainer>("Колличество продаж", false, 15, psc -> String.valueOf(psc.getSumCount())),
            new ColumnProperties<PromoterSalesContainer>("Сумма цен проданных моделей", false, 15, psc -> String.valueOf(psc.getSumPrice()))
    );

}
