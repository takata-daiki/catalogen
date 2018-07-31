package repast.simphony.data.gui.formula;

import repast.simphony.data.gui.DataSetWizardModel;
import repast.simphony.data.gui.MappingSourceRepresentation;
import repast.simphony.data.logging.formula.FormulaEvaluator;
import repast.simphony.data.logging.formula.FormulaMapping;
import repast.simphony.data.logging.gather.DataMapping;

/**
 * @author Nick Collier
 */
public class FormulaScriptRepresentation implements MappingSourceRepresentation {

  private FormulaEvaluator eval;
  private boolean isAggregate;
  private String str;


  public FormulaScriptRepresentation(FormulaEvaluator eval, boolean isAggregate) {
    this.eval = eval;
    this.isAggregate = isAggregate;
    str = (isAggregate ? "Aggregate Formula: " : "Formula: ") + eval.getScript();
  }

  /**
   * Adds this mapping represented by this to the specified model.
   *
   * @param model      the model to add the mapping to
   * @param columnName the name of the column the mapping maps to
   */
  public void addMapping(String columnName, DataSetWizardModel model) {
    if (isAggregate) model.getDescriptor().addPrimaryAggregateMapping(columnName, new FormulaMapping(eval, str));
    else model.getDescriptor().addMapping(columnName, new FormulaMapping(eval, str));
  }

  /**
   * Returns true if the mapping source of this representation
   * is equal to that in the specified DataMapping. Otherwise false.
   *
   * @param mapping the DataMapping to compare to
   * @return true if the mapping source of this representation
   *         is equal to that in the specified DataMapping. Otherwise false.
   */
  public boolean equalsMappingSource(DataMapping mapping) {
    return false;
  }

  /**
   * Gets whether or not this mapping representation is editable.
   *
   * @return true if editable otherwise false.
   */
  public boolean isMappingEditable() {
    return false;
  }

  public String toString() {
    return str;
  }
}
