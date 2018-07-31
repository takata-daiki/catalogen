package eu.lsem.bakalarka.webfrontend.action.secure;

import eu.lsem.bakalarka.webfrontend.action.BaseActionBean;
import eu.lsem.bakalarka.dao.ThesesDao;
import eu.lsem.bakalarka.service.ChartGenerator;
import eu.lsem.bakalarka.model.ChartTypes;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

public class ThesesStatisticsActionBean extends BaseActionBean {

    @SpringBean
    private ThesesDao thesesDao;
    @SpringBean
    private ChartGenerator chartGenerator;
    private static final int PIE_CHART_WIDTH = 400, PIE_CHART_HEIGHT = 400;

    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution("/WEB-INF/pages/secure/thesesStatistics.jsp");
    }

    public Resolution getUncompleteMetadataGraph() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.UNCOMPLETE_METADATA_CHART));
    }

    public Resolution getMissingDataGraph() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.MISSING_DATA_CHART));
    }


    public Resolution getNotSelectedDocumentationGraph() {
        return new StreamingResolution("image/png", chartGenerator.getChart(ChartTypes.NOT_SELECTED_DOCUMENTATION_CHART));
    }
}
