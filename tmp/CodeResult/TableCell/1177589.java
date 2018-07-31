/*
 * Created on Sep 12, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JTable;

import org.fest.swing.driver.JTableCell;

/**
 * Understands a cell in a <code>{@link JTable}</code>.
 * <p>
 * Example:
 * <pre>
 * // import static org.fest.swing.fixture.TableCell.row;
 * {@link JTableCellFixture} cell = dialog.{@link JTableFixture table}("records").cell({@link TableCell#row(int) row}(3).column(0));
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 */
public class TableCell extends JTableCell {

  /**
   * Starting point for the creation of a <code>{@link TableCell}</code>.
   * <p>
   * Example:
   * <pre>
   * // import static org.fest.swing.fixture.TableCell.row;
   * TableCell cell = row(5).column(3);
   * </pre>
   * </p>
   * @param row the row index of the table cell to create.
   * @return the created builder.
   */
  public static TableCellBuilder row(int row) { return new TableCellBuilder(row); }

  /**
   * Understands creation of <code>{@link TableCell}</code>s.
   *
   * @author Alex Ruiz
   */
  public static class TableCellBuilder {
    private final int row;
    
    TableCellBuilder(int row) { this.row = row; }
    
    /**
     * Creates a new table cell using the row index specified in <code>{@link TableCellBuilder#row(int)}</code> and the 
     * column index specified as the argument in this method. 
     * @param column the column index of the table cell to create.
     * @return the created table cell.
     */
    public TableCell column(int column) { return new TableCell(row, column); }
  }
  
  TableCell(int row, int column) {
    super(row, column);
  }
}