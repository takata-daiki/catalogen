package by.q64.promo.excelgen.service.marketing;

import by.q64.promo.excelgen.service.marketing.source.MarketingReportDataOneCity;
import by.q64.promo.excelgen.service.marketing.source.settings.MarketingReportSettings;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.util.function.Function;

/**
 * Created by Pavel.Sirotkin on 25.12.2014.
 */
public interface MarketingReportPartGenerator {
    XMLSlideShow generate(XMLSlideShow ppt, MarketingReportSettings reportSettings,
                          MarketingReportDataOneCity marketingReportDataOneCity,
                          Function<String, byte[]> pictureGetter);
}
