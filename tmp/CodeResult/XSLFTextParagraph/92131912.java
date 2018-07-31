package by.q64.promo.excelgen.service.marketing;

import by.q64.promo.excelgen.service.marketing.source.MarketingReportData;
import by.q64.promo.excelgen.service.marketing.source.MarketingReportDataOneCity;
import by.q64.promo.excelgen.service.marketing.source.MarketingSourceByCity;
import by.q64.promo.excelgen.service.marketing.source.settings.MarketingReportSettings;
import by.q64.promo.excelgen.service.utils.OtherUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.function.Function;

/**
 * Created by Pavel.Sirotkin on 25.12.2014.
 */
@Service("marketingReportFirstSlideGenerator")
public class MarketingReportFirstSlideGeneratorImpl implements MarketingReportPartGenerator {
    private static final String imageName = "/firstSlideImage.png";

    @Autowired
    private OtherUtils otherUtils;

    private static final Logger LOGGER = Logger.getLogger(MarketingReportFirstSlideGeneratorImpl.class);

    @Override
    public XMLSlideShow generate(XMLSlideShow ppt, MarketingReportSettings reportSettings,
                                 MarketingReportDataOneCity marketingReportDataOneCity, Function<String, byte[]> pictureGetter) {
        String title = marketingReportDataOneCity.getMarketingReportData().getReportTitle();

        MarketingSourceByCity cityData = marketingReportDataOneCity.getMarketingSourceByCity();
        MarketingReportData mData = marketingReportDataOneCity.getMarketingReportData();

        // fill the placeholders
        XSLFSlide slide1 = ppt.createSlide();


        XSLFTextBox txt1 = slide1.createTextBox();
        txt1.setAnchor(new java.awt.Rectangle(250, 250, 450, 200));
        XSLFTextParagraph p1 = txt1.addNewTextParagraph();
        XSLFTextRun r1 = p1.addNewTextRun();
        r1.setText(title);
        r1.setFontFamily("Verdana");
        r1.setFontColor(new Color(255, 0, 102));
        r1.setFontSize(32);
        r1.setBold(true);


        XSLFTextBox txt2 = slide1.createTextBox();
        txt2.setAnchor(new java.awt.Rectangle(420, 480, 200, 40));
        XSLFTextParagraph p2 = txt2.addNewTextParagraph();
        XSLFTextRun r2 = p2.addNewTextRun();
        r2.setText(marketingReportDataOneCity.getMarketingSourceByCity().getCityName()
                + "\n" + otherUtils.getMonthName(mData.getMonth()) + " " + mData.getYear());
        r2.setFontFamily("Verdana");
        r2.setFontColor(Color.black);
        r2.setFontSize(16);


        try {
            drawImage(ppt, slide1);
        } catch (IOException e) {
            LOGGER.error("", e);
        }

        return ppt;
    }

    private void drawImage(XMLSlideShow ppt, XSLFSlide slide) throws IOException {
        byte[] bytes = IOUtils.toByteArray(this.getClass().getResourceAsStream(imageName));
        int idx = ppt.addPicture(bytes, XSLFPictureData.PICTURE_TYPE_PNG);
        XSLFPictureShape pic = slide.createPicture(idx);

        pic.setAnchor(new Rectangle(30, 80, 210, 340));
    }
}
