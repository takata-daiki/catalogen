package by.q64.promo.excelgen.service.marketing;

import by.q64.promo.excelgen.service.marketing.source.MarketingReportDataOneCity;
import by.q64.promo.excelgen.service.marketing.source.settings.MarketingReportSettings;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.function.Function;

/**
 * Created by Pavel.Sirotkin on 25.12.2014.
 */
@Service("marketingReportLastSlideGenerator")
public class MarketingReportLastSlideGeneratorImpl implements MarketingReportPartGenerator {
    @Override
    public XMLSlideShow generate(XMLSlideShow ppt, MarketingReportSettings reportSettings,
                                 MarketingReportDataOneCity marketingReportDataOneCity,
                                 Function<String, byte[]> pictureGetter) {
        XSLFSlide slide1 = ppt.createSlide();

        XSLFTextBox txt1 = slide1.createTextBox();
        txt1.setAnchor(new java.awt.Rectangle(20, 250, 600, 50));

        XSLFTextParagraph p1 = txt1.addNewTextParagraph();

        XSLFTextRun r1 = p1.addNewTextRun();
        r1.setText("Thank you for your attention!");
        r1.setFontFamily("Verdana");
        r1.setFontColor(new Color(255, 0, 102));
        r1.setFontSize(32);
        r1.setBold(true);



        XSLFTextBox txt2 = slide1.createTextBox();
        txt2.setAnchor(new java.awt.Rectangle(20, 410, 600, 50));

        XSLFTextParagraph p2 = txt2.addNewTextParagraph();
        XSLFTextRun r2 = p2.addNewTextRun();
        r2.setText("Group of companies «Trast Group BTL»\n" +
                "tel. +7 (495) 223-45-96 \n" +
                "www.trast-group.ru\n");
        r2.setFontColor(Color.black);
        r2.setFontSize(19);
        r2.setFontFamily("Verdana");

        return ppt;
    }
}
