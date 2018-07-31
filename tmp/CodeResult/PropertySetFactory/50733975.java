package com.atlassian.streams.jira.upgrade;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.gadgets.dashboard.Color;
import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.ofbiz.OfBizDelegator;
import com.atlassian.jira.portal.OfbizPortletConfigurationStore;
import com.atlassian.jira.portal.PortletAccessManager;
import com.atlassian.jira.portal.PortletConfiguration;
import com.atlassian.jira.portal.PortletConfigurationImpl;
import com.atlassian.jira.portal.PortletConfigurationManager;
import com.atlassian.jira.portal.PortletConfigurationStore;
import com.atlassian.jira.propertyset.JiraPropertySetFactory;
import com.atlassian.jira.upgrade.util.SimpleLegacyPortletUpgradeTask;
import com.atlassian.jira.util.Consumer;
import com.atlassian.jira.util.NotNull;
import com.atlassian.jira.util.collect.EnclosedIterable;
import com.atlassian.sal.api.message.Message;
import com.atlassian.sal.api.upgrade.PluginUpgradeTask;
import com.opensymphony.module.propertyset.PropertySet;

public class UpgradeTo_v1 implements PluginUpgradeTask
{
    private static final Logger log = Logger.getLogger(UpgradeTo_v1.class);

    private static final String PROJECT_ID_KEY = "projectid";
    private static final String PROJECT_KEY_KEY = "keys";
    private static final String MULTIVALUE_SEPARATOR = "|";

    private final PortletConfigurationStore portletConfigurationStore;
    private final JiraPropertySetFactory propertySetFactory;
    private final OfBizDelegator delegator;
    private final PortletAccessManager portletAccessManager;
    private final PortletConfigurationManager portletConfigurationManager;
    private final ProjectManager projectManager;

    public UpgradeTo_v1(final JiraPropertySetFactory propertySetFactory, final OfBizDelegator delegator,
        final PortletAccessManager portletAccessManager, final PortletConfigurationManager portletConfigurationManager,
        ProjectManager projectManager)
    {
        this.projectManager = projectManager;
        this.portletConfigurationStore = ComponentManager.getComponentInstanceOfType(PortletConfigurationStore.class);
        this.propertySetFactory = propertySetFactory;
        this.delegator = delegator;
        this.portletAccessManager = portletAccessManager;
        this.portletConfigurationManager = portletConfigurationManager;
    }

    public Collection<Message> doUpgrade() throws Exception
    {
        new RenameStreamsTask(delegator, propertySetFactory, portletAccessManager,
            portletConfigurationManager).doUpgrade();
        final SimpleLegacyPortletUpgradeTask portletUpgrade = new SimpleLegacyPortletUpgradeTask(
            "com.atlassian.streams.streams-jira-plugin:activityfeed",
            URI.create(
                "rest/gadgets/1.0/g/com.atlassian.streams.streams-jira-plugin:activitystream-gadget/gadgets/activitystream-gadget.xml"));

        // First get all the portletConfigurations in the database.
        final EnclosedIterable<PortletConfiguration> iterable = portletConfigurationStore
            .getAllPortletConfigurations();
        iterable.foreach(new Consumer<PortletConfiguration>()
        {
            public void consume(@NotNull final PortletConfiguration pc)
            {
                // for each portletconfiguration, check if it's key matches the portlet key we want to upgrade
                if (pc.getKey() != null && pc.getKey().startsWith(portletUpgrade.getPortletKey()))
                {
                    log.info("Upgrading portletconfig with id '" + pc.getId() + "'");
                    // first lets convert the preferences for this portlet to
                    // the new prefs format used for gadgets.
                    final Map<String, String> prefs;
                    try
                    {
                        prefs = portletUpgrade.convertUserPrefs(pc.getProperties());
                    }
                    catch (final ObjectConfigurationException e)
                    {
                        throw new RuntimeException(e);
                    }

                    // Project ids need to be converted to keys
                    if (prefs.containsKey(PROJECT_ID_KEY))
                    {
                        String[] projectids = prefs.get(PROJECT_ID_KEY).split("\\" + MULTIVALUE_SEPARATOR);
                        List<String> keys = new ArrayList<String>();
                        for (String idString : projectids)
                        {
                            if (idString.trim().length() > 0)
                            {
                                try
                                {
                                    long projectId = Long.parseLong(idString);
                                    Project project = projectManager.getProjectObj(projectId);
                                    if (project != null)
                                    {
                                        keys.add(project.getKey());
                                    }
                                }
                                catch (NumberFormatException nfe)
                                {
                                    log.warn("Bad project ids value found: " + idString +
                                        ". This portlet configuration may not be migrated properly.");
                                }
                            }
                        }
                        if (keys.size() > 0)
                        {
                            StringBuffer sb = new StringBuffer();
                            String sep = "";
                            for (String key : keys)
                            {
                                sb.append(sep);
                                sb.append(key);
                                sep = MULTIVALUE_SEPARATOR;
                            }
                            prefs.put(PROJECT_KEY_KEY, sb.toString());
                        }
                    }

                    // then create essentially a copy of the old portletConfig.
                    // This new copy no longer needs to have
                    // the portletKey and propertySet set to any values. It
                    // however does require the GadgetUri and user prefs to be
                    // set.
                    final PortletConfiguration newConfig = new PortletConfigurationImpl(pc.getId(), pc
                        .getDashboardPageId(), null, null, pc.getColumn(), pc.getRow(), null, portletUpgrade
                        .getGadgetUri(), Color.color1, prefs);
                    // Now lets store this new config back to the database.
                    portletConfigurationStore.store(newConfig);
                    // clear out the old properties for this portlet
                    removePropertySet(pc);
                }
            }
        });

        return null;
    }

    private void removePropertySet(final PortletConfiguration pc)
    {
        final PropertySet livePropertySet = propertySetFactory.buildNoncachingPropertySet(
            OfbizPortletConfigurationStore.TABLE, pc.getId());
        @SuppressWarnings("unchecked")
        final Collection<String> keys = livePropertySet.getKeys();
        for (final String propertyKey : keys)
        {
            livePropertySet.remove(propertyKey);
        }
    }

    public String getPluginKey()
    {
        return "com.atlassian.streams.streams-jira-plugin";
    }

    public int getBuildNumber()
    {
        return 1;
    }

    public String getShortDescription()
    {
        return "Converts stream portlets to stream gadget.";
    }

}