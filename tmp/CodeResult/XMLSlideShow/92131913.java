package by.q64.promo.excelgen.service.marketing;

import by.q64.promo.excelgen.service.marketing.source.MarketingReportDataManyCities;
import by.q64.promo.excelgen.service.marketing.source.MarketingReportDataOneCity;
import by.q64.promo.excelgen.service.marketing.source.MarketingSourceByCity;
import by.q64.promo.excelgen.service.marketing.source.settings.MarketingReportSettings;
import org.apache.log4j.Logger;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Created by Pavel.Sirotkin on 25.12.2014.
 */
@Service
public class MarketingReportGeneratorImpl implements MarketingReportGenerator {
    @Autowired
    @Qualifier("marketingReportInCityGenerator")
    private MarketingReportPartGenerator cityGenerator;
    @Autowired
    @Qualifier("marketingReportFirstSlideGenerator")
    private MarketingReportPartGenerator firstSlideGenerator;
    @Autowired
    @Qualifier("marketingReportLastSlideGenerator")
    private MarketingReportPartGenerator lastSlideGenerator;
    @Autowired
    @Qualifier("marketingReportCostEstimationGenerator")
    private MarketingReportPartGenerator costEstimationGenerator;

    private static final Logger LOGGER = Logger.getLogger(MarketingReportGeneratorImpl.class);
    @Override
    public void generate(OutputStream outputStream, MarketingReportDataManyCities marketingReportDataManyCities,
                         Function<String, byte[]> pictureGetter) throws IOException {
        XMLSlideShow ppt = new XMLSlideShow(getClass().getResourceAsStream("/marketing_template.pptx"));
        MarketingReportSettings reportSettings = new MarketingReportSettings();


        MarketingReportDataOneCity commonSource = new MarketingReportDataOneCity(marketingReportDataManyCities.getCities().get(0),
                marketingReportDataManyCities.getMarketingReportData());

        firstSlideGenerator.generate(ppt, reportSettings,commonSource, pictureGetter);
        LOGGER.info("marketing report first slide generated");

        costEstimationGenerator.generate(ppt,reportSettings, commonSource, pictureGetter);
        LOGGER.info("marketing report cost estimation slide generated");

        for (MarketingSourceByCity marketingSourceByCity : marketingReportDataManyCities.getCities()) {
            MarketingReportDataOneCity sourceByCity =
                    new MarketingReportDataOneCity(marketingSourceByCity, marketingReportDataManyCities.getMarketingReportData());
            ppt = cityGenerator.generate(ppt, reportSettings, sourceByCity, pictureGetter);
            LOGGER.info("marketing report city generated : " + sourceByCity.getMarketingSourceByCity().getCityName());
        }

        lastSlideGenerator.generate(ppt,reportSettings, commonSource, pictureGetter);
        LOGGER.info("marketing report last slide generated");

        Arrays.stream(ppt.getSlides()).forEach(reportSettings::fillBackgroundAndIncrementIndex);

        ppt.write(outputStream);
        LOGGER.info("write marketing report to output stream");
    }
}
