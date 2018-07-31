package com.googlecode.jsu.helpers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.googlecode.jsu.helpers.checkers.CheckerCompositeFactory;

/**
 * Return object for checking conditions.
 *
 * @author <A href="mailto:abashev at gmail dot com">Alexey Abashev</A>
 * @version $Id: ConditionCheckerFactory.java,v 1.3 2011-06-21 14:55:02 mbrizi Exp $
 */
public class ConditionCheckerFactory {
    public static final ConditionType GREATER = new ConditionType(1, ">", "greater than", "G");
    public static final ConditionType GREATER_EQUAL = new ConditionType(2, ">=", "greater than or equal to", "GE");
    public static final ConditionType EQUAL = new ConditionType(3, "=", "equal to", "E");
    public static final ConditionType LESS_EQUAL = new ConditionType(4, "<=", "less than or equal to", "LE");
    public static final ConditionType LESS = new ConditionType(5, "<", "less than", "L");
    public static final ConditionType NOT_EQUAL = new ConditionType(6, "!=", "not equal to", "NE");

    public static final ComparisonType STRING = new ComparisonType(1, "String", "String");
    public static final ComparisonType NUMBER = new ComparisonType(2, "Number", "Number");
    public static final ComparisonType DATE = new ComparisonType(3, "Date with time", "Date");
    public static final ComparisonType DATE_WITHOUT_TIME = new ComparisonType(4, "Date without time", "DateWithoutTime");

    /** Template for checker class. */
    private static final String PACKAGE = ConditionCheckerFactory.class.getPackage().getName();
    private static final String CONDITION_CLASS_TEMPLATE = PACKAGE + ".checkers.Snipet";
    private static final String COMPARISON_CLASS_TEMPLATE = PACKAGE + ".checkers.Converter";

    /** Cache for searching through conditions */
    @SuppressWarnings("serial")
    private static final Map<Integer, ConditionType> CONDITIONS_CACHE =
        new LinkedHashMap<Integer, ConditionType>(6) {{
            put(GREATER.getId(), GREATER);
            put(GREATER_EQUAL.getId(), GREATER_EQUAL);
            put(EQUAL.getId(), EQUAL);
            put(LESS_EQUAL.getId(), LESS_EQUAL);
            put(LESS.getId(), LESS);
            put(NOT_EQUAL.getId(), NOT_EQUAL);
    }};

    /** Cache for searching through types */
    @SuppressWarnings("serial")
    private static final Map<Integer, ComparisonType> COMPARISONS_CACHE =
        new LinkedHashMap<Integer, ComparisonType>(4) {{
            put(STRING.getId(), STRING);
            put(NUMBER.getId(), NUMBER);
            put(DATE.getId(), DATE);
            put(DATE_WITHOUT_TIME.getId(), DATE_WITHOUT_TIME);
    }};

    private final Logger log = Logger.getLogger(ConditionCheckerFactory.class);
    private final CheckerCompositeFactory checkerCompositeFactory = new CheckerCompositeFactory();

    public ConditionChecker getChecker(ComparisonType type, ConditionType condition) {
        String conditionClassName = CONDITION_CLASS_TEMPLATE + condition.getMnemonic();
        String comparisonClassName = COMPARISON_CLASS_TEMPLATE + type.getMnemonic();

        if (log.isDebugEnabled()) {
            log.debug(
                    "Using class [" + conditionClassName +
                    "] for condition [" + condition.getValue() +
                    "]; class [" + comparisonClassName +
                    "] for type [" + type.getValue() +
                    "]"
            );
        }

        return checkerCompositeFactory.getComposite(comparisonClassName, conditionClassName);
    }

    /**
     * Get all possible condition types.
     *
     * @return
     */
    public List<ConditionType> getConditionTypes() {
        return new ArrayList<ConditionType>(CONDITIONS_CACHE.values());
    }

    /**
     * Get all possible comparison types.
     * @return
     */
    public List<ComparisonType> getComparisonTypes() {
        return new ArrayList<ComparisonType>(COMPARISONS_CACHE.values());
    }

    /**
     * Find condition by id.
     *
     * @param id
     * @return
     */
    public ConditionType findConditionById(String id) {
        return CONDITIONS_CACHE.get(Integer.valueOf(id));
    }

    /**
     * Find comparison by id.
     *
     * @param id
     * @return
     */
    public ComparisonType findComparisonById(String id) {
        return COMPARISONS_CACHE.get(Integer.valueOf(id));
    }
}
