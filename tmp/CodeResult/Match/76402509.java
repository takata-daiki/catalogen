package gov.nasa.jpf.simplify;


import gov.nasa.jpf.symbc.numeric.BinaryRealExpression;
import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.MathFunction;
import gov.nasa.jpf.symbc.numeric.MathRealExpression;
import gov.nasa.jpf.symbc.numeric.RealConstant;
import gov.nasa.jpf.symbc.numeric.RealConstraint;
import gov.nasa.jpf.symbc.numeric.RealExpression;
import gov.nasa.jpf.symbc.numeric.SymbolicReal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static gov.nasa.jpf.symbc.numeric.MathFunction.*;



// Rewrite rules for RealExpressions.
//
// Rule Format (A,B,+,*,f,g = constants;  x,y = variables):
//   rule(realExprPattern, realExprResult, vars)
//   means
//     - recursively match "realExprPattern" against subexpressions of
//       a RealExpression, treating any occurrences of SymbolicReals
//       in "vars" as varibles to be bound.
//     - When a match occurs, replace it with "realExprResult",
//       filling in any vars with their values from the match.
//
// Example:
//   - rewrite(rule("x + 0", "x", ["x"]),
//             "5 == (y * (z + 0))")
//     binds x-->z and produces "t = (y * z)"
public class Match {
  public static MatchData match(RealConstraint pattern, RealConstraint constraint) {
    MatchData data = new MatchData();
    match(pattern, constraint, data);
    return data;
  }

  public static MatchData match(RealExpression pattern, RealExpression constraint) {
    MatchData data = new MatchData();
    match(pattern, constraint, data);
    return data;
  }

  public static boolean match(RealConstraint pattern, RealConstraint constraint, MatchData data) {
    if (pattern == null || constraint == null) {
      if (pattern != constraint) {
        data.failed(true);
      }
      return (! data.failed());
    } else {
      RealExpression patLeft = pattern.getLeft();
      Comparator patComp = pattern.getComparator();
      RealExpression patRight = pattern.getRight();
      RealConstraint patAnd = (RealConstraint)pattern.and;

      RealExpression constrLeft = constraint.getLeft();
      Comparator constrComp = constraint.getComparator();
      RealExpression constrRight = constraint.getRight();
      RealConstraint constrAnd = (RealConstraint)constraint.and;

      if (patComp.equals(constrComp)
          && match(patLeft, constrLeft, data)
          && match(patRight, constrRight, data)
          && match(patAnd, constrAnd, data)) {
        return (! data.failed());
      } else {
        data.failed(true);
        return (! data.failed());
      }
    }
  }

  protected static boolean match(RealExpression pat, RealExpression expr, MatchData data) {
    // System.out.format("Matching %s with %s with data %s\n", pat, expr, data);
    debugMatchPre(pat, expr, data, "match(RealExpression,RealExpression,Match)");
    if (data.failed()) {
      // System.out.println("   match failed from data: " + data);
      debugMatchResult(pat, expr, data, false, " FAILED ON PREVIOUS MATCH match(RealExpression,RealExpression,Match)");
      return false;
    } else if (isVariable(pat)) {
      // System.out.format("   %s is pattern variable\n", pat);
      boolean result = matchVar((RealPatternVariable)pat, expr, data);
      debugMatchResult(pat, expr, data, result, "VARIABLE MATCHING match(RealExpression,RealExpression,Match)");
      return result;
    } else if (pat instanceof SymbolicReal  &&  expr instanceof SymbolicReal) {
      boolean result = (((SymbolicReal)pat).getName()).equals(((SymbolicReal)expr).getName());
      debugMatchResult(pat, expr, data, result, "EQUAL SymbolicReals match(RealExpression,RealExpression,Match)");
      return result;
    } else if (pat instanceof RealConstant  &&  expr instanceof RealConstant) {
      boolean result = ((((RealConstant)pat).value()) == (((RealConstant)expr).value()));
      debugMatchResult(pat, expr, data, result, "EQUAL SymbolicReals match(RealExpression,RealExpression,Match)");
      return result;
    } else if (pat instanceof BinaryRealExpression && expr instanceof BinaryRealExpression) {
      // System.out.println("binaryrealexpression");
      boolean result = match((BinaryRealExpression)pat, (BinaryRealExpression) expr, data);
      debugMatchResult(pat, expr, data, result, "BinaryRealExpression match(RealExpression,RealExpression,Match)");
      return result;
    } else if (pat instanceof MathRealExpression && expr instanceof MathRealExpression) {
      // System.out.println("MathRealExpression");
      boolean result = match((MathRealExpression)pat, (MathRealExpression) expr, data);
      debugMatchResult(pat, expr, data, result, "MathRealExpression match(RealExpression,RealExpression,Match)");
      return result;
    } else if (pat instanceof DerivativeExpression && expr instanceof DerivativeExpression) {
      // System.out.println("DerivativeExpression");
      boolean result = match((DerivativeExpression)pat, (DerivativeExpression) expr, data);
      debugMatchResult(pat, expr, data, result, "DerivativeExpression match(RealExpression,RealExpression,Match)");
      return result;
    } else {
      // System.out.println("OTHERWISE failed");
      data.failed(true);
      debugMatchResult(pat, expr, data, (! data.failed()), "NO MATCH POSSIBLE match(RealExpression,RealExpression,Match)");
      return (! data.failed());
    }
  }


  protected static boolean match(BinaryRealExpression pat, BinaryRealExpression expr, MatchData data) {
    boolean matchOpSucceeded = (pat.getOp() == expr.getOp());
    debugMatchResult(pat, expr, data, matchOpSucceeded, "match(BinaryRealExpression) OPS");
    boolean matchLeftSucceeded = (match(pat.getLeft(), expr.getLeft(), data));
    debugMatchResult(pat.getLeft(), expr.getLeft(), data, matchLeftSucceeded, "match(BinaryRealExpression) LEFT");
    boolean matchRightSucceeded = (match(pat.getRight(), expr.getRight(), data));
    debugMatchResult(pat.getRight(), expr.getRight(), data, matchRightSucceeded, "match(BinaryRealExpression) RIGHT");
    boolean matchSucceeded = (matchOpSucceeded && matchLeftSucceeded && matchRightSucceeded);

    if (! matchSucceeded) {
      data.failed(true);
    }
    return matchSucceeded;
  }


  protected static boolean match(MathRealExpression pat, MathRealExpression expr, MatchData data) {
    MathFunction patFcn = pat.getOp();
    MathFunction exprFcn = expr.getOp();
    boolean matchSucceeded = false;

    if (patFcn == exprFcn) {
      if (patFcn == ATAN2 || patFcn == POW) {
        matchSucceeded = (match(pat.getArg1(), expr.getArg1(), data) &&
                          match(pat.getArg2(), expr.getArg2(), data));
      } else {
        matchSucceeded = match(pat.getArg1(), expr.getArg1(), data);
      }
    }
    if (! matchSucceeded) {
      data.failed(true);
    }
    return matchSucceeded;
  }


  protected static boolean match(DerivativeExpression pat,
                                 DerivativeExpression expr,
                                 MatchData data)
  {
    // System.out.format("pat var: %s  expr var: %s    match: %s\n",
    //                   pat.getIndepVar(), expr.getIndepVar(), match(pat.getIndepVar(), expr.getIndepVar(), data));
    // System.out.format("pat expr: %s  expr expr: %s    match: %s\n",
    //                   pat.getExpr(), expr.getExpr(), match(pat.getExpr(), expr.getExpr(), data));

    boolean matchSucceeded = (match(pat.getIndepVar(), expr.getIndepVar(), data)
                              && match(pat.getExpr(), expr.getExpr(), data));
    if (! matchSucceeded) {
      data.failed(true);
    }
    return matchSucceeded;
  }

  
  protected static boolean isVariable(RealExpression pat) {
    // System.out.format("isVariable pat: %s   type: %s\n", pat, pat.getClass());
    return (pat instanceof RealPatternVariable);
  }


  protected static boolean matchVar(RealPatternVariable var, RealExpression expr, MatchData data) {
    RealExpression binding = data.get(var);
    // System.out.format("Matching %s with %s with data %s  gives binding %s\n", var, expr, data, binding);

    if (! var.predicateMatches(expr)) {
      // System.out.format("var predicate %s does not match\n", var.getPredicate());
      data.failed(true);
      return false;
    } else if (binding == null) {
      // System.out.format("no binding, save and return true\n");
      data.put(var, expr);
      return true;
    } else if (binding.toString().equals(expr.toString())) {
      // System.out.format("has matching binding %s return true\n", binding);
      return true;
    } else {
      // System.out.format("otherwise, return false\n");
      data.failed(true);
      return false;
    }
  }


  public static class MatchData {
    private boolean _failed = false;

    private Map<String,RealExpression> _matches = new HashMap<String,RealExpression>();

    public boolean failed() {
      return _failed;
    }

    public void failed(boolean state) {
      if (state) {
      }
      _failed = state;
    }

    public RealExpression get(String var) {
      return _matches.get(var);
    }

    public RealExpression get(RealPatternVariable var) {
      return _matches.get(var.getName());
    }

    public void put(String var, RealExpression val) {
      _matches.put(var, val);
    }

    public void put(RealPatternVariable var, RealExpression val) {
      _matches.put(var.getName(), val);
    }

    public String toString() {
      return "[" + _failed + ", " + _matches + "]";
    }
  }

  // Help with debugging
  protected static boolean _debug = false;
  protected static Set<String> _patternsToDebug = new HashSet<String>();

  protected static void debugMatchPre(RealExpression pat, RealExpression expr, MatchData data, String method) {
    // System.out.format("Match.debugRuleToTry %s\n", _debug);
    if (_debug) {
      // System.out.format("Match.debugRuleToTry  rule %s  rule pattern %s  node %s   debug pats: %s\n",
      //                   rule, rule._patternExpr, node, _patternsToDebug);
      if (_patternsToDebug.contains(pat.toString())) {
        System.out.format("BEFORE: Match.%s: pattern %s matched against expression %s and with match data %s\n",
                          method, pat, expr, data);
      }
    }
  }

  protected static void debugMatchResult(RealExpression pat, RealExpression expr, MatchData data, boolean result, String method) {
    // System.out.format("Match.debugRuleApplied %s\n", _debug);
    if (_debug) {
      // System.out.format("Match.debugRuleApplied  rule %s  node %s   debug pats: %s\n",
      //                   rule._patternExpr, node, _patternsToDebug);
      if (_patternsToDebug.contains(pat.toString())) {
        System.out.format("%s Match.%s: pattern %s matched against expression %s and with new match data %s\n",
                          (result ? "SUCCEEDED" : "FAILED"), method, pat, expr, data);
      }
    }
  }

  public static void debugOn() { _debug = true; }
  public static void debugOff() { _debug = false; }
  public static void debug(String... patterns) { _patternsToDebug.addAll(Arrays.asList(patterns)); }
  public static void noDebug(String... patterns) { _patternsToDebug.removeAll(Arrays.asList(patterns)); }
}
