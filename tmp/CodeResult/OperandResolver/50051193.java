package com.atlassian.jira.plugin.votersAndWatchers.searchers;

import com.atlassian.jira.bc.user.search.UserPickerSearchService;
import com.atlassian.jira.issue.customfields.converters.UserConverter;
import com.atlassian.jira.issue.customfields.searchers.UserPickerSearcher;
import com.atlassian.jira.issue.customfields.searchers.transformer.CustomFieldInputHelper;
import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.jql.resolver.UserResolver;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.web.FieldVisibilityManager;

/**
 * This class exists to work around <a href="http://jira.atlassian.com/browse/JRA-18986">JRA-18986</a>
 */
public class VotersAndWatchersUserPickerSearcher extends UserPickerSearcher
{
    public VotersAndWatchersUserPickerSearcher(UserResolver userResolver, JqlOperandResolver operandResolver, JiraAuthenticationContext context, UserConverter userConverter, UserPickerSearchService userPickerSearchService, CustomFieldInputHelper customFieldInputHelper, UserManager userManager, FieldVisibilityManager fieldVisibilityManager)
    {
        super(userResolver, operandResolver, context, userConverter, userPickerSearchService, customFieldInputHelper, userManager, fieldVisibilityManager);
    }
}
