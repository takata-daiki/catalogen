package com.atlassian.jira.toolkit.customfield.searchers;

import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.jira.datetime.DateTimeFormatterFactory;
import com.atlassian.jira.issue.customfields.searchers.DateRangeSearcher;
import com.atlassian.jira.issue.customfields.searchers.transformer.CustomFieldInputHelper;
import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.jql.util.JqlLocalDateSupport;
import com.atlassian.jira.util.velocity.VelocityRequestContextFactory;
import com.atlassian.jira.web.FieldVisibilityManager;
import com.atlassian.jira.web.action.util.CalendarLanguageUtil;
import com.atlassian.velocity.VelocityManager;

/**
 * @since JIRA 4.0
 */
public class ResolvedDateSearcher extends DateRangeSearcher
{

    public ResolvedDateSearcher(JqlOperandResolver operandResolver, JqlLocalDateSupport jqlLocalDateSupport, CustomFieldInputHelper customFieldInputHelper,
            DateTimeFormatterFactory dateTimeFormatterFactory, VelocityRequestContextFactory velocityRenderContext, ApplicationProperties applicationProperties,
            VelocityManager velocityManager, CalendarLanguageUtil calendarUtils, FieldVisibilityManager fieldVisibilityManager)
    {
        super(operandResolver, jqlLocalDateSupport, customFieldInputHelper, dateTimeFormatterFactory, velocityRenderContext, applicationProperties, velocityManager, calendarUtils, fieldVisibilityManager);
    }
}
