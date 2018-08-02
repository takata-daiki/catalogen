package by.q64.promo.excelgen.service.utils.properties;

import by.q64.promo.excelgen.constructor.entity.properties.SheetTemplateProperties;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by Pavel on 06.10.2014.
 */
public interface SheetPropertiesTemplateFactory {
    public <N> SheetTemplateProperties<N> get(Workbook workbook, SheetPropertiesTemplateType type);
}
