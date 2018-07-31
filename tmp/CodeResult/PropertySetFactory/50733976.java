package com.atlassian.streams.jira.upgrade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ofbiz.core.entity.EntityExpr;
import org.ofbiz.core.entity.GenericValue;

import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.ofbiz.OfBizDelegator;
import com.atlassian.jira.portal.Portlet;
import com.atlassian.jira.portal.PortletAccessManager;
import com.atlassian.jira.portal.PortletConfiguration;
import com.atlassian.jira.portal.PortletConfigurationImpl;
import com.atlassian.jira.portal.PortletConfigurationManager;
import com.atlassian.jira.portal.PortletConfigurationStore;
import com.atlassian.jira.propertyset.JiraPropertySetFactory;
import com.atlassian.sal.api.message.Message;
import com.opensymphony.module.propertyset.PropertySet;


public class RenameStreamsTask
{

    private static final String TABLE = "PortletConfiguration"; // would use another one from OfbizPortletConfigurationStore, but it is private

    private static final Logger log = Logger.getLogger(RenameStreamsTask.class);
    private final PortletConfigurationStore portletConfigurationStore;
    private final OfBizDelegator delegator;
    private final JiraPropertySetFactory propertySetFactory;
    private final PortletAccessManager portletAccessManager;
    private final PortletConfigurationManager portletConfigurationManager;
    private static final String NUM_OF_ENTRIES = "numofentries";
    private static final String PROJECT_ID = "projectid";


    private static final class Columns
    {
        public static final String PORTALPAGE = "portalpage";
        public static final String PORTLETKEY = "portletId";
        public static final String COLUMN = "columnNumber";
        public static final String ROW = "position";
        public static final String ID = "id";
    }


    public RenameStreamsTask(final OfBizDelegator delegator, final JiraPropertySetFactory propertySetFactory, final PortletAccessManager portletAccessManager, final PortletConfigurationManager portletConfigurationManager)
    {
        this.portletConfigurationStore = ComponentManager.getComponentInstanceOfType(PortletConfigurationStore.class);
        this.delegator = delegator;
        this.propertySetFactory = propertySetFactory;
        this.portletAccessManager = portletAccessManager;
        this.portletConfigurationManager = portletConfigurationManager;
    }


    List<PortletConfiguration> getPortalConfigurations()
    {
        final List<EntityExpr> entityExpressions = new ArrayList<EntityExpr>(2);

        final List portletConfigsListGVs = delegator.findByOr(TABLE, entityExpressions, null);
        final List<PortletConfiguration> portletConfigs = new ArrayList<PortletConfiguration>(portletConfigsListGVs.size());

        for(final Object portletConfigsListGV : portletConfigsListGVs)
        {
            final GenericValue portletConfigGV = (GenericValue) portletConfigsListGV;
            portletConfigs.add(createConfigurationFromGv(portletConfigGV));
        }

        return filter(portletConfigs);
    }

    private List<PortletConfiguration> filter(final List<PortletConfiguration> portletConfigs)
    {
        final List<PortletConfiguration> result = new ArrayList<PortletConfiguration>(portletConfigs.size());
        for (final PortletConfiguration pc : portletConfigs)
        {
            final String key = pc.getKey();
            if(key != null && key.contains("com.atlassian.studio.jira-streams"))
            {
                result.add(pc);
            }
        }

        return result;
    }

    private PortletConfiguration createConfigurationFromGv(final GenericValue portletConfigGV)
    {
        final Long id = portletConfigGV.getLong(Columns.ID);
        final PropertySet portletConfiguration = propertySetFactory.buildMemoryPropertySet(portletConfigGV.getEntityName(), id);

        final String portletKey = portletConfigGV.getString(Columns.PORTLETKEY);
        final Portlet portlet = StringUtils.isNotEmpty(portletKey) ? portletAccessManager.getPortlet(portletKey) : null;

        return new PortletConfigurationImpl(id, portletConfigGV.getLong(Columns.PORTALPAGE),
                portletConfigGV.getString(Columns.PORTLETKEY), portlet, portletConfigGV.getInteger(Columns.COLUMN),
                portletConfigGV.getInteger(Columns.ROW), portletConfiguration,
                null, /* gadget uri */
                null, /* gadget color */
                new HashMap<String, String>() /* user prefs */);
    }

    public Collection<Message> doUpgrade() throws Exception
    {
        final List<PortletConfiguration> portletConfigurationList = getPortalConfigurations();

        for(final PortletConfiguration portlet :  portletConfigurationList)
        {
            log.info("Found activityfeed porlet, upgrading... (" + portlet.getKey() + ")");
            final String replace = "com.atlassian.streams.streams-jira-plugin:activityfeed";
            final Long pageId = portlet.getDashboardPageId();
            final Integer column = portlet.getColumn();
            final Integer row = portlet.getRow();
            final PropertySet propertySet = portlet.getProperties();

            final PortletConfiguration portletConfiguration = portletConfigurationStore.add(pageId, replace, column, row);
            final PropertySet ps = portletConfiguration.getProperties();

            final String numOfEntries = propertySet.getString(NUM_OF_ENTRIES);
            ps.setString(NUM_OF_ENTRIES,StringUtils.isEmpty(numOfEntries) ? "10" : numOfEntries);

            final String projectId = propertySet.getString(PROJECT_ID);
            if(!StringUtils.isEmpty(projectId))
            {
                ps.setString(PROJECT_ID, projectId);
            }

            final String userName = propertySet.getString("username");
            if(!StringUtils.isEmpty(userName))
            {
                ps.setString("username", userName);
            }

            portletConfigurationStore.delete(portlet);
            portletConfigurationManager.store(portletConfiguration);
            log.info("Finished upgrading. (" + portlet.getKey() + ")");
        }
        return null;
    }


    public String getShortDescription()
    {
        return "Converts existing stream portlets to use the new stream portlets plugin key.";
    }

}