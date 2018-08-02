package by.q64.promo.excelgen.service.marketing;

import by.q64.promo.excelgen.service.marketing.source.MarketingReportDataOneCity;
import by.q64.promo.excelgen.service.marketing.source.settings.MarketingReportSettings;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.function.Function;

/**
 * Created by Pavel on 26.12.2014.
 */
@Service("marketingReportCostEstimationGenerator")
public class MarketingReportCostEstimationGeneratorImpl implements MarketingReportPartGenerator {
    @Override
    public XMLSlideShow generate(XMLSlideShow ppt,MarketingReportSettings reportSettings,
                                 MarketingReportDataOneCity marketingReportDataOneCity, Function<String, byte[]> pictureGetter) {
        XSLFSlide slide1 = ppt.createSlide();

        XSLFTextBox txt1 = slide1.createTextBox();
        txt1.setAnchor(new java.awt.Rectangle(20, 40, 600, 50));

        XSLFTextParagraph p1 = txt1.addNewTextParagraph();

        XSLFTextRun r1 = p1.addNewTextRun();
        r1.setText("Cost estimation");
        r1.setFontFamily("Verdana");
        r1.setFontColor(new Color(255, 0, 102));
        r1.setFontSize(32);
        r1.setBold(true);

        return ppt;
    }
}
