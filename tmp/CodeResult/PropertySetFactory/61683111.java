package com.atlassian.jconnect.jira;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jconnect.jira.customfields.BuiltInField;
import com.atlassian.jconnect.jira.customfields.CustomFieldHelper;
import com.atlassian.jconnect.jira.customfields.CustomFieldSchemeHelper;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.FieldManager;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.propertyset.JiraPropertySetFactory;
import com.atlassian.query.Query;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.opensymphony.module.propertyset.PropertySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link JMCProjectService}.
 */
public class DefaultJMCProjectService implements JMCProjectService {

    private final CustomFieldHelper customFieldHelper;
    private final CustomFieldSchemeHelper customFieldSchemeHelper;
    private final FieldManager fieldManager;
    private final JiraPropertySetFactory propertySetFactory;
    private final SearchService searchService;
    private final CustomFieldManager customFieldManager;

    private static final String PROPERTY_API_KEY = "jmc.api.key";
    private static final String PROPERTY_API_KEY_ENABLED = "jmc.api.key.enabled";
    private static final String PROPERTY_CRASHES_ENABLED = "jmc.crashes.enabled";

    private static final Logger log = LoggerFactory.getLogger(DefaultJMCProjectService.class);

    private Map<Long, PropertySet> configCache = new MapMaker().concurrencyLevel(8).makeComputingMap(new Function<Long, PropertySet>() {
        public PropertySet apply(@Nullable Long pid) {
            return propertySetFactory.buildCachingPropertySet(PluginInfo.JMC_PLUGIN_ID, pid, true);
        }
    });

    public DefaultJMCProjectService(CustomFieldHelper customFieldHelper,
                                    CustomFieldSchemeHelper customFieldSchemeHelper,
                                    FieldManager fieldManager,
                                    JiraPropertySetFactory propertySetFactory,
                                    SearchService searchService, CustomFieldManager customFieldManager) {
        this.customFieldHelper = customFieldHelper;
        this.customFieldSchemeHelper = customFieldSchemeHelper;
        this.fieldManager = fieldManager;
        this.propertySetFactory = propertySetFactory;
        this.searchService = searchService;
        this.customFieldManager = customFieldManager;
    }

    public boolean reindexRequiredFor(Project project, User user) {

        Query query = JqlQueryBuilder.newBuilder().where()
                .project().eq(project.getId())
                .buildQuery();
        try {
            // if there is at least 1 issue a re-index is required
            return this.searchService.searchCount(user, query) > 0;
        } catch (SearchException e) {
            log.warn(e.getMessage(), e);
            return false;
        }
    }

    public String generateApiKeyFor(Project project) {
        final PropertySet propertySet = getPropertySet(project);
        return addApiKey(propertySet);
    }

    public boolean toggleApiKeyFor(Project project, boolean enable) {
        final PropertySet propertySet = getPropertySet(project);
        setApiKeyEnabledState(propertySet, enable);
        return enable;
    }

    public boolean isApiKeyEnabledFor(Project project) {
        final PropertySet propertySet = getPropertySet(project);
        return propertySet.getBoolean(PROPERTY_API_KEY_ENABLED);
    }

    public boolean isCrashesEnabledFor(Project project) {
        final PropertySet propertySet = getPropertySet(project);
        
        // for backwards compatibility, if this prop is missing, default to ENABLED.
        if (!propertySet.exists(PROPERTY_CRASHES_ENABLED))
        {
            return true;
        }
        return propertySet.getBoolean(PROPERTY_CRASHES_ENABLED);
    }

    public boolean toggleCrashesFor(Project project, boolean enable) {
        final PropertySet propertySet = getPropertySet(project);
        propertySet.setBoolean(PROPERTY_CRASHES_ENABLED, enable);
        return enable;
    }

    public String lookupApiKeyFor(Project project) {
        final PropertySet propertySet = getPropertySet(project);
        return propertySet.getString(PROPERTY_API_KEY);
    }

    public String generateOrRetrieveAPIKeyFor(Project project) {
        final PropertySet propertySet = getPropertySet(project);
        setApiKeyEnabledState(propertySet, true);
        String currentKey = propertySet.getString(PROPERTY_API_KEY);
        if (currentKey == null) {
            currentKey = addApiKey(propertySet);
        }
        return currentKey;
    }

    private PropertySet getPropertySet(Project project) {
        return configCache.get(project.getId());
    }

    private void setApiKeyEnabledState(PropertySet propertySet, boolean enable) {
        propertySet.setBoolean(PROPERTY_API_KEY_ENABLED, enable);
    }

    private String addApiKey(PropertySet propertySet) {
        final String apiKey = UUID.randomUUID().toString();
        propertySet.setString(PROPERTY_API_KEY, apiKey);
        return apiKey;
    }

    public boolean toggleForJiraConnect(Project project) {

        final boolean isEnabled = isJiraConnectProject(project);
        // if project is currently enabled, it will be disabled. ie. custom fields removed
        customFieldSchemeHelper.toggleFieldsToProjectScheme(!isEnabled, project, BuiltInField.values());
        fieldManager.refresh();
        return isJiraConnectProject(project); // check the status of the project
    }

    public boolean isJiraConnectProject(Project project) {
        // a project is a JMC project if it contains all custom fields
        return customFieldHelper.containsAllFields(project, customFieldHelper.getAllBuiltInCustomFields());
    }

    public long countJMCIssues(User user) {

        final CustomField uuidField = this.customFieldManager.getCustomFieldObjectByName(BuiltInField.UUID.fieldName());
        Query query = JqlQueryBuilder.newBuilder().where().customField(uuidField.getIdAsLong()).notEqEmpty().buildQuery();
        try {
            return this.searchService.searchCount(user, query);
        } catch (SearchException e) {
            log.error(e.getMessage(), e);
        }
        return -1;
    }
}
