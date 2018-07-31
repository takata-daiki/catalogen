package com.atlassian.spuddy.termenum;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.IssueConstant;
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager;
import com.atlassian.jira.issue.index.DocumentConstants;
import com.atlassian.jira.issue.search.SearchProviderFactory;
import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.jql.operand.PredicateOperandResolver;
import com.atlassian.jira.jql.operand.QueryLiteral;
import com.atlassian.jira.jql.operator.OperatorClasses;
import com.atlassian.jira.jql.util.JqlDateSupport;
import com.atlassian.query.clause.WasClause;
import com.atlassian.query.history.AndHistoryPredicate;
import com.atlassian.query.history.HistoryPredicate;
import com.atlassian.query.history.TerminalHistoryPredicate;
import com.atlassian.query.operand.MultiValueOperand;
import com.atlassian.query.operand.Operand;
import com.atlassian.query.operator.Operator;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Factory class for validating and building the Lucene Was query.
 *
 *
 * @since v4.3
 */
public class CopiedWasClauseQueryFactory
{
    private final JqlOperandResolver operandResolver;
    private final ChangeHistoryManager changeHistoryManager;
    private final ConstantsManager constantsManager;
    final PredicateOperandResolver predicateOperandResolver;
    final JqlDateSupport jqlDateSupport;

    /**
     *
     * @param operandResolver   resolves {@link Operand}  and retrieves their values
     * @param changeHistoryManager  the {@link ChangeHistoryManager} that manages the cretaion of change items
     * @param constantsManager   th {@link ConstantsManager}, used to retrieve field ids from field names
     * @param predicateOperandResolver  resolves {@Link HistoryPredicate} values
     * @param jqlDateSupport Allows you to parse jql dates
     */
    public CopiedWasClauseQueryFactory(final JqlOperandResolver operandResolver, final ChangeHistoryManager changeHistoryManager, final ConstantsManager constantsManager, final PredicateOperandResolver predicateOperandResolver, final JqlDateSupport jqlDateSupport)
    {
        this.operandResolver = operandResolver;
        this.changeHistoryManager = changeHistoryManager;
        this.constantsManager = constantsManager;
        this.predicateOperandResolver = predicateOperandResolver;
        this.jqlDateSupport = jqlDateSupport;
    }


    public void makeQuery(BooleanQuery bq, String field, String toValue, Date fromDate, Date toDate)
    {
        if (field != null) {
            if (toValue != null) {
                bq.add(createTermQuery(DocumentConstants.CHANGE_TO, toValue, "status"), BooleanClause.Occur.MUST);
            }
        }
        if (fromDate != null && toDate != null) {
            String searchStart = jqlDateSupport.getIndexedValue(fromDate);
            String searchEnd = jqlDateSupport.getIndexedValue(toDate);
            // startSearch <= NEXT_CHANGE_DATE  AND  CHANGE_DATE <= searchEND
            bq.add(new TermRangeQuery(DocumentConstants.NEXT_CHANGE_DATE, searchStart, null, false, true), BooleanClause.Occur.MUST);
            bq.add(new TermRangeQuery(DocumentConstants.CHANGE_DATE, null, searchEnd, true, true), BooleanClause.Occur.MUST);
        }
    }

    private TermQuery createTermQuery(String documentField, String value, String field) {
        return new TermQuery(new Term(field.toLowerCase() + "." + documentField, encodeProtocol(value)));
    }

    private String encodeProtocol(String changeItem)
    {
        return DocumentConstants.CHANGE_HISTORY_PROTOCOL + (changeItem == null ? "" : changeItem.toLowerCase());
    }

}



