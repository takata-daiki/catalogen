package com.burningcode.jira.plugin.customfields.searchers;

import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.jira.issue.customfields.converters.MultiUserConverter;
import com.atlassian.jira.issue.customfields.searchers.UserPickerSearcher;
import com.atlassian.jira.issue.customfields.searchers.transformer.CustomFieldInputHelper;
import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.jql.resolver.UserResolver;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.util.EmailFormatter;
import com.atlassian.jira.web.FieldVisibilityManager;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

@Scanned
public class ComponentWatchersFieldSearcher extends UserPickerSearcher {
    public ComponentWatchersFieldSearcher(
            @ComponentImport UserResolver userResolver,
            @ComponentImport JqlOperandResolver operandResolver,
            @ComponentImport JiraAuthenticationContext context,
            @ComponentImport MultiUserConverter userConverter,
            @ComponentImport UserSearchService userSearchService,
            @ComponentImport CustomFieldInputHelper customFieldInputHelper,
            @ComponentImport UserManager userManager,
            @ComponentImport FieldVisibilityManager fieldVisibilityManager,
            @ComponentImport EmailFormatter emailFormatter) {
        super(userResolver, operandResolver, context, userConverter, userSearchService, customFieldInputHelper, userManager, fieldVisibilityManager, emailFormatter);
    }
}
