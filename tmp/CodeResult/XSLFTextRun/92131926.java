package by.q64.promo.excelgen.service.marketing.source.settings;

import org.apache.log4j.Logger;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;

import java.awt.*;

/**
 * Created by Pavel on 18.01.2015.
 */
public class MarketingReportSettings {
    private int curPage = 1;
    private static final String backgroundPath = "/background.png";

    private static final Logger LOGGER = Logger.getLogger(MarketingReportSettings.class);

        public void fillBackgroundAndIncrementIndex(XSLFSlide slide){
//            XSLFTextBox txt = slide.createTextBox();
//            txt.setAnchor(new java.awt.Rectangle(690, 540, 50, 50));
//
//            XSLFTextParagraph p = txt.addNewTextParagraph();
//
//            XSLFTextRun r1 = p.addNewTextRun();
//            r1.setText(String.valueOf(curPage++));
//            r1.setFontColor(Color.white);
//            r1.setFontSize(24);

    }

    public int getCurPage() {
        return curPage;
    }

}
