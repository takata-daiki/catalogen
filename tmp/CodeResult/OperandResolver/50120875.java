package com.atlassian.jira.toolkit.customfield.searchers;

import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.jira.issue.customfields.converters.UserConverter;
import com.atlassian.jira.issue.customfields.searchers.transformer.CustomFieldInputHelper;
import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.jql.resolver.UserResolver;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.web.FieldVisibilityManager;

/**
 * @since JIRA 4.0
 */
public class UserPickerSearcher extends com.atlassian.jira.issue.customfields.searchers.UserPickerSearcher
{

    public UserPickerSearcher(final UserResolver userResolver,
            final JqlOperandResolver operandResolver,
            final JiraAuthenticationContext context,
            final UserConverter userConverter,
            final UserSearchService userSearchService,
            final CustomFieldInputHelper customFieldInputHelper,
            final UserManager userManager,
            final FieldVisibilityManager fieldVisibilityManager)
    {
        super(userResolver, operandResolver, context, userConverter,
                userSearchService, customFieldInputHelper, userManager, fieldVisibilityManager);
    }
}
