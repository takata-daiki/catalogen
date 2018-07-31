/*
 * Copyright 2011 Kim Lindhardt Madsen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package dk.lindhardt.gwt.geie.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: AnAmuser
 * Date: 29-07-11
 * <p/>
 * A formula
 * <p>
 *    Simple Example
 * </p>
 * <pre>
 *    TableLayout layout = new TableLayout(..);
 *    Position position = new Position(0, 0);
 *    layout.addCell(new NumberCell(1.0, position, "default"));
 *
 *    Formula sinFormula = new Formula("Sin[x]");
 *    sinFormula.putVariable("x", position);
 * </pre>
 */
public class Formula implements Serializable {

   private String formula;
   private Map<String, Position> cellVariables = new HashMap<String, Position>();

   /**
    * For serialization
    */
   Formula() {
   }

   /**
    * Constructs a new formula
    * @param formula a formula
    */
   public Formula(String formula) {
      this.formula = formula;
   }

   /**
    * @return gets the formula string
    */
   public String getFormula() {
      return formula;
   }

   /**
    * Returns the variables used in the formula as a map with the variable name as the key and the position of the
    * referenced cell (the variable) as the value.
    * @return variables used in the formula
    */
   public Map<String, Position> getCellVariables() {
      return cellVariables;
   }

   /**
    * Sets the variables used in the formula as a map with the variable name as the key and the position of the
    * referenced cell (the variable) as the value.
    * @param cellVariables variables used in the formula
    */
   public void setCellVariables(Map<String, Position> cellVariables) {
      this.cellVariables = new HashMap<String, Position>(cellVariables);
   }

   /**
    * Adds a new variable to the formula with the variable name as the key and the referenced cell (the variable) as the value.
    * @param variableName the variable name
    * @param reference the position of the referenced cell (the variable)
    */
   public void putVariable(String variableName, Position reference) {
      cellVariables.put(variableName, reference);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      Formula formula1 = (Formula) o;

      if (!cellVariables.equals(formula1.cellVariables)) {
         return false;
      }
      if (!formula.equals(formula1.formula)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode() {
      int result = formula.hashCode();
      result = 31 * result + cellVariables.hashCode();
      return result;
   }

   @Override
   public String toString() {
      return "Formula{" +
            "formula='" + formula + '\'' +
            ", cellVariables=" + cellVariables +
            '}';
   }
}
