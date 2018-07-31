package eu.lsem.bakalarka.webfrontend.action.general;

import eu.lsem.bakalarka.dao.ThesesDao;
import eu.lsem.bakalarka.dao.categories.CategoryDao;
import eu.lsem.bakalarka.model.ChartTypes;
import eu.lsem.bakalarka.webfrontend.action.BaseActionBean;
import eu.lsem.bakalarka.service.ChartGenerator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;


public class ThesesStatisticsActionBean extends BaseActionBean {

    @SpringBean
    private ThesesDao thesesDao;
    @SpringBean
    private CategoryDao thesesCategoriesDao;
    @SpringBean
    private CategoryDao fieldsOfStudyDao;
    @SpringBean
    private CategoryDao formsOfStudyDao;
    @SpringBean
    private ChartGenerator chartGenerator;

    private static final int PIE_CHART_WIDTH = 400, PIE_CHART_HEIGHT = 400;
    private static final int COLUMN_CHART_WIDTH = 400, COLUMN_CHART_HEIGHT = 240;

    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution("/WEB-INF/pages/secure/thesesStatistics.jsp");
    }

    /**
     * Zobrazi kolacovy graf podle kategorii prace
     * @return
     */
    public Resolution getThesesCategoriesGraph() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.CATEGORIES_CHART));

    }

    /**
     * Zobrazi kolacovy graf podle oboru studia
     * @return
     */
    public Resolution getFieldsOfStudyGraph() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.FIELDS_CHART));
    }

    /**
     * Zobrazi kolacovy graf podle formy studia
     * @return
     */
    public Resolution getFormsOfStudyGraph() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.FORMS_CHART));
    }


    /**
     * Zobrazi sloupcovy graf poctu praci v jednotlivych letech
     * @return
     */
    public Resolution getThesesByYears() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.YEARS_CHART));
    }

    /**
     * Zobrazi sloupcovy graf, kde pro kazdy rok bude pocet praci v kazde kategorii prace
     * @return
     */
    public Resolution getThesesByYearsAndCategory() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.YEARS_AND_CATEGORIES_CHART));
    }

    /**
     * * Zobrazi sloupcovy graf, kde pro kazdy rok bude pocet praci v kazdem oboru studia
     * @return
     */
    public Resolution getThesesByYearAndFieldOfStudy() {
       return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.YEARS_AND_FIELDS_CHART));
    }

    /**
     * Zobrazi kolacovy graf, kde bude kazda kombinace obor / typ prace a procento z celkoveho poctu praci
     * @return
     */
    public Resolution getFieldsAndCategoriesGraph() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.CATEGORIES_AND_FIELDS_CHART));
    }


}
