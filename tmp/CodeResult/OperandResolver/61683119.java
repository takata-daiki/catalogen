package com.atlassian.jconnect.jira.customfields;

import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.jql.operand.QueryLiteral;
import com.atlassian.jira.jql.validator.ClauseValidator;
import com.atlassian.jira.util.I18nHelper;
import com.atlassian.jira.util.MessageSet;
import com.atlassian.jira.util.MessageSetImpl;
import com.atlassian.jira.util.NotNull;
import com.atlassian.jira.web.bean.I18nBean;
import com.atlassian.query.clause.TerminalClause;
import com.atlassian.query.operator.Operator;
import com.opensymphony.user.User;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Clause validator for location queries.
 *
 */
public class LocationSearchClauseValidator implements ClauseValidator {

    // TODO should use SupportedOperatorsValidator

    private final JqlOperandResolver operandResolver;
    private final Set<Operator> supported;

    public LocationSearchClauseValidator(Set<Operator> supportedOperators, JqlOperandResolver operandResolver) {
        this.operandResolver = operandResolver;
        this.supported = checkNotNull(supportedOperators);
    }

    public MessageSet validate(User searcher, @NotNull TerminalClause terminalClause) {
        final MessageSet errors = new MessageSetImpl();
        final I18nHelper i18n = new I18nBean(searcher);
        if (!supported.contains(terminalClause.getOperator())) {
            errors.addErrorMessage(i18n.getText("customfields.locationsearcher.error.unsupportedoperator",
                    terminalClause.getOperator().getDisplayString()));
        } else {
            validateOperand(errors, i18n, searcher, terminalClause);
        }
        return errors;
    }

    private void validateOperand(MessageSet errors, I18nHelper i18n, User searcher, TerminalClause terminalClause) {
        final List<QueryLiteral> literals = operandResolver.getValues(searcher, terminalClause.getOperand(), terminalClause);
        if (terminalClause.getOperator() == Operator.LIKE) {
            if (literals != null) {
                if (literals.size() != 1) {
                    errors.addErrorMessage(i18n.getText("customfields.locationsearcher.error.toomanyliterals", terminalClause));
                    return;
                }
                for (QueryLiteral queryLiteral : literals) {
                    LocationParser.validateAndParseLocationQuery(queryLiteral.getStringValue(), i18n, errors);
                }
            }
        }
    }


}
