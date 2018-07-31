package repast.simphony.data.logging.formula;

import repast.simphony.data.array.DoubleArray;
import repast.simphony.data.array.IllegalFormulaException;

import java.util.List;
import java.io.StringReader;

/**
 * Parses and evaluates formulas.
 * 
 * @author Nick Collier
 */
public class FormulaEvaluator {

  private String formula;
  private List<Class> agentClasses;
  private ASTstart start;

  /**
   * Creates a FormulaEvaulator.
   *
   * @param formula the formula to evaluate
   * @param agentClasses the types of agents
   *
   * @throws ParseException if there is a syntax error in the formula
   * @throws IllegalFormulaException if there is a semantic error in the formula
   */
  public FormulaEvaluator(String formula, List<Class> agentClasses) throws ParseException,
          IllegalFormulaException
  {
    this.formula = formula;
    if (!formula.endsWith(";")) {
      this.formula += ";";
    }
    this.agentClasses = agentClasses;
    init();
  }

  /**
   * Gets the script this formula evaluator will evaluate.
   *
   * @return the script this formula evaluator will evaluate.
   */
  public String getScript() {
    return formula;
  }

  /**
   * Initializes this FormulaEvaluator prior to evaluation.
   *
   * @throws ParseException if there is a syntax error in the formula
   * @throws IllegalFormulaException if there is a semantic error in the formula
   */
  private void init() throws ParseException, IllegalFormulaException {
    StringReader reader = new StringReader(formula);
    Parser parser = new Parser(reader);
    start = parser.start();
    start.init(agentClasses);
  }

  /**
   * Evaluates the formula using the specified sources.
   *
   * @param sources the data sources (agents) used in the formula
   *
   * @return the result of the evalution as a DoubleArray
   */
  public DoubleArray evaluate(Iterable sources) {
    if (start == null) {
      try {
        init();
      } catch (Exception e) {
        // this should never happen because the only time
        // start will be null is if we are loading this from
        // the xml serialization.
      }
    }
    return start.evaluate(sources);
  }

}
