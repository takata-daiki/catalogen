package com.atlassian.jconnect.jira.customfields;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Set;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.jql.operand.QueryLiteral;
import com.atlassian.jira.jql.validator.ClauseValidator;
import com.atlassian.jira.util.I18nHelper;
import com.atlassian.jira.util.MessageSet;
import com.atlassian.jira.util.MessageSetImpl;
import com.atlassian.jira.util.NotNull;
import com.atlassian.query.clause.TerminalClause;
import com.atlassian.query.operator.Operator;

/**
 * Clause validator for location queries.
 *
 */
public class LocationSearchClauseValidator implements ClauseValidator {

    // TODO should use SupportedOperatorsValidator

    private final JqlOperandResolver operandResolver;
    private final Set<Operator> supported;
    final I18nHelper i18n;

    public LocationSearchClauseValidator(Set<Operator> supportedOperators, JqlOperandResolver operandResolver, I18nHelper i18n) {
        this.operandResolver = operandResolver;
        this.i18n = i18n;
        this.supported = checkNotNull(supportedOperators);
    }

    public MessageSet validate(User searcher, @NotNull TerminalClause terminalClause) {
        final MessageSet errors = new MessageSetImpl();
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
