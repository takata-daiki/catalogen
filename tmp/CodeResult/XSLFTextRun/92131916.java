package by.q64.promo.excelgen.service.marketing;

import by.q64.promo.excelgen.service.marketing.source.*;
import by.q64.promo.excelgen.service.marketing.source.settings.MarketingReportSettings;
import by.q64.promo.excelgen.service.utils.PowerpointCommonUtils;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Pavel.Sirotkin on 25.12.2014.
 */
@Service("marketingReportInCityGenerator")
public class MarketingReportInCityGeneratorImpl implements MarketingReportPartGenerator {
    private static final String[] previewTableHeader = new String[]{"№", "City", "Shop", "Address"};


    @Autowired
    private PowerpointCommonUtils powerpointCommonUtils;

    @Override
    public XMLSlideShow generate(XMLSlideShow ppt, MarketingReportSettings reportSettings,
                                 MarketingReportDataOneCity marketingReportDataOneCity,
                                 Function<String, byte[]> pictureGetter) {
        MarketingReportData mData = marketingReportDataOneCity.getMarketingReportData();
        MarketingSourceByCity marketingSourceByCity = marketingReportDataOneCity.getMarketingSourceByCity();
        List<PreviewReportingDocument> previewData = marketingSourceByCity.getPreviewData();
        String cityName = marketingSourceByCity.getCityName();
        List<? extends MarketingSlideInfo> slides = marketingSourceByCity.getSlides();

        int curPage = reportSettings.getCurPage();
        ppt = generatePreview(ppt, previewData, cityName, curPage);
        ppt = generateDetailShops(ppt, slides, pictureGetter, mData);
        return ppt;
    }

    private XMLSlideShow generatePreview(XMLSlideShow ppt, List<PreviewReportingDocument> previewData, String cityName,
                                         int repDocsStartNum) {

        XSLFSlide slide = ppt.createSlide();

        XSLFTextBox txt1 = slide.createTextBox();
        txt1.setAnchor(new java.awt.Rectangle(20, 40, 600, 50));
        XSLFTextParagraph p1 = txt1.addNewTextParagraph();
        XSLFTextRun r1 = p1.addNewTextRun();
        r1.setText("Reporting documents");
        r1.setFontFamily("Verdana");
        r1.setFontColor(new Color(255, 0, 102));
        r1.setFontSize(32);
        r1.setBold(true);

        XSLFTextBox txt2 = slide.createTextBox();
        txt2.setAnchor(new java.awt.Rectangle(20, 80, 600, 50));
        XSLFTextParagraph p2 = txt2.addNewTextParagraph();
        XSLFTextRun r2 = p2.addNewTextRun();
        r2.setText("Address program: " + cityName);
        r2.setFontColor(Color.black);
        r2.setFontFamily("Verdana");
        r2.setFontSize(18);
        r2.setBold(true);


        //generate table
        XSLFTable table = slide.createTable();
        table.setAnchor(new Rectangle(30, 120, 100, 100));

        XSLFTableRow headerRow = table.addRow();
        headerRow.setHeight(20);


        // header
        for (int i = 0; i < previewTableHeader.length; i++) {
            XSLFTableCell th = headerRow.addCell();
            XSLFTextParagraph p = th.addNewTextParagraph();
            p.setTextAlign(TextAlign.CENTER);
            XSLFTextRun r = p.addNewTextRun();
            r.setText(previewTableHeader[i]);
            r.setBold(true);
            r.setFontColor(Color.white);
            r.setFontSize(11);
            th.setFillColor(Color.RED);
            th.setBorderBottom(2);
            th.setBorderBottomColor(Color.white);

            table.setColumnWidth(i, 160);
        }
        // fill data

        for (PreviewReportingDocument dataRow : previewData) {
            XSLFTableRow row = table.addRow();

            XSLFTableCell nCell = row.addCell();
            XSLFTextParagraph nCellP = nCell.addNewTextParagraph();
            XSLFTextRun nCellR = nCellP.addNewTextRun();
            nCellR.setText(String.valueOf(repDocsStartNum++));
            nCellR.setFontSize(11);

            XSLFTableCell cityCell = row.addCell();
            XSLFTextParagraph cityCellP = cityCell.addNewTextParagraph();
            XSLFTextRun cityCellR = cityCellP.addNewTextRun();
            cityCellR.setText(cityName);
            cityCellR.setFontSize(11);

            XSLFTableCell netCell = row.addCell();
            XSLFTextParagraph netCellP = netCell.addNewTextParagraph();
            XSLFTextRun netCellR = netCellP.addNewTextRun();
            netCellR.setText(dataRow.getNetwork());
            netCellR.setFontSize(11);

            XSLFTableCell addressCell = row.addCell();
            XSLFTextParagraph addressCellP = addressCell.addNewTextParagraph();
            XSLFTextRun addressCellR = addressCellP.addNewTextRun();
            addressCellR.setText(dataRow.getAddress());
            addressCellR.setFontSize(11);

            row.setHeight(11);
        }

        table.setColumnWidth(0, 160);
        table.setColumnWidth(1, 160);
        table.setColumnWidth(2, 160);
        table.setColumnWidth(3, 160);

        return ppt;
    }

    private XMLSlideShow generateDetailShops(XMLSlideShow ppt, List<? extends MarketingSlideInfo> slides, Function<String, byte[]> pictureGetter, MarketingReportData mData) {
        for (MarketingSlideInfo slide : slides) {
            ppt = generateDetailShop(ppt, slide, pictureGetter, mData);
        }
        return ppt;
    }

    private XMLSlideShow generateDetailShop(XMLSlideShow ppt, MarketingSlideInfo slide, Function<String, byte[]> pictureGetter, MarketingReportData mData) {
        final int maxPictureWidth = 185, maxPictureHeight = 300, leftPicturePadding = 50, betweenDifference = 30,
                titleHeight = 70, subTitleHeight = 80, titlesSumHeight = titleHeight + subTitleHeight,
                fullPictureWidthPlace = maxPictureWidth + betweenDifference;

        XSLFSlide pptSlide = ppt.createSlide();

        XSLFTextBox txt1 = pptSlide.createTextBox();
        txt1.setAnchor(new java.awt.Rectangle(20, 40, 650, titleHeight));
        XSLFTextParagraph p1 = txt1.addNewTextParagraph();
        XSLFTextRun r1 = p1.addNewTextRun();
        r1.setText(slide.getUnitRegionNetworkName() + ", " + slide.getUnitRegionName());
        r1.setFontFamily("Verdana");
        r1.setFontColor(new Color(255, 0, 102));
        r1.setFontSize(24);
        r1.setBold(true);


        XSLFTextBox txt2 = pptSlide.createTextBox();
        txt2.setAnchor(new java.awt.Rectangle(20, titleHeight + 40, 650, subTitleHeight));
        XSLFTextParagraph p2 = txt2.addNewTextParagraph();
        XSLFTextRun r2 = p2.addNewTextRun();
        r2.setText(generateDatesOnDetailShop(slide, mData));
        r2.setFontFamily("Verdana");
        r2.setFontColor(new Color(255, 0, 102));
        r2.setFontSize(20);
        r2.setBold(true);

        String[] photos = {slide.getPhoto1(), slide.getPhoto2(), slide.getPhoto3()};
        for (int i = 0; i < 3; i++) {
            byte[] picBytes1 = pictureGetter.apply(photos[i]);
            if (picBytes1 == null)
                continue;
            int idx = ppt.addPicture(picBytes1, XSLFPictureData.PICTURE_TYPE_PNG);
            XSLFPictureShape pic = pptSlide.createPicture(idx);

            //calculate sizes
            double realWidth = pic.getAnchor().getWidth();
            double realHeight = pic.getAnchor().getHeight();
            double kWidth = maxPictureWidth / realWidth;
            double kHeight = maxPictureHeight / realHeight;
            double k = Math.min(kWidth, kHeight);


            int height = (int) (k * realHeight);
            int width = (int) (k *realWidth);

            int centerWidthPadding = (maxPictureWidth - width) / 2;
            int centerHeightPadding = (maxPictureHeight - height) / 2;

            pic.setAnchor(new Rectangle(leftPicturePadding + fullPictureWidthPlace * i + centerWidthPadding,
                    titlesSumHeight + 20 + centerHeightPadding,
                    width, height));
        }
        return ppt;
    }

    private String generateDatesOnDetailShop(MarketingSlideInfo slide,
                                           MarketingReportData mData){
        List<Integer> days = slide.getDays();
        String s = null;
        if(days.size() == 0){
            s = "Не работал";
        }else {
            s = "(" + days.stream().sorted()
                    .map(Object::toString).collect(Collectors.joining(", ")) +
                    "." + mData.getMonth() + "." + mData.getYear() + ")";
        }
        return s;
    }

}
