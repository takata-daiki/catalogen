/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// $URL$
// $Rev$
// $Date$
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  Copyright(C) 2007 - 2009 MayaStudios
//  All rights reserved. Use is subject to license terms.
//
//  Part of the IXML library (Interface XML) for Java
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  This library is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this library. If not, see <http://www.gnu.org/licenses/>.
//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.mayastudios.ixml.documents.components.base;

import java.io.Serializable;


/**
 * Describes a margin around a rectangle (for example an IXML component). The margin consist of a top, left,
 * bottom, and right margin. Instances of this class are immutable.<p>
 *
 * A margin is often also called "padding" or "insets".<p>
 *
 * @author Sebastian Krysmanski
 */
public class Margin implements Serializable {

  /**
   * The top margin.
   */
  public final int top;

  /**
   * The left margin.
   */
  public final int left;

  /**
   * The bottom margin.
   */
  public final int bottom;

  /**
   * The right margin.
   */
  public final int right;

  /** Serial Version UID */
  private static final long serialVersionUID = -7010593305477301962L;

  /**
   * Creates a margin where all four margins have the same size.
   *
   * @param p_margin   the size of each margin in pixels.
   */
  public Margin(int p_margin) {
    this(p_margin, p_margin, p_margin, p_margin);
  }

  /**
   * Creates a margin where the top and the bottom as well as the left and the right margin have the same
   * size respectively.<p>
   *
   * <i>Note:</i> The order of the parameters correspond to the order of the CSS property "margin".
   *
   * @param p_vertically   the margin (in pixels) to be assigned to the top and the bottom margin
   * @param p_horizontally  the margin (in pixels) to be assigned to the left and the right margin.
   */
  public Margin(int p_vertically, int p_horizontally) {
    this(p_vertically, p_horizontally, p_vertically, p_horizontally);
  }

  /**
   * Creates a margin.<p>
   *
   * <i>Note:</i> The order of the parameters correspond to the order of the CSS property "margin".
   *
   * @param p_top   the top margin in pixels
   * @param p_right   the right margin in pixels
   * @param p_bottom   the bottom margin in pixels
   * @param p_left   the left margin in pixels
   */
  public Margin(int p_top, int p_right, int p_bottom, int p_left) {
    this.top = p_top;
    this.left = p_left;
    this.bottom = p_bottom;
    this.right = p_right;
  }

  /**
   * The top margin.
   */
  public int getTop() {
    return this.top;
  }

  /**
   * The left margin.
   */
  public int getLeft() {
    return this.left;
  }

  /**
   * The bottom margin.
   */
  public int getBottom() {
    return this.bottom;
  }

  /**
   * The right margin.
   */
  public int getRight() {
    return this.right;
  }

  /**
   * Returns the sum of the left and the right margin.
   */
  public int getHorizontalMargin() {
    return this.left + this.right;
  }

  /**
   * Returns the sum of the top and the bottom margin.
   */
  public int getVerticalMargin() {
    return this.top + this.bottom;
  }

  /**
   * Checks whether two margin objects are equal. Two instances of {@code Margin} are equal if the four
   * integer values of the fields {@code top}, {@code right}, {@code bottom}, and {@code left} are all equal.
   */
  @Override
  public boolean equals(Object p_obj) {
    if (p_obj instanceof Margin) {
      Margin insets = (Margin) p_obj;
      return (   this.top == insets.top && this.left == insets.left
              && this.bottom == insets.bottom && this.right == insets.right);
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int sum1 = this.left + this.bottom;
    int sum2 = this.right + this.top;
    int val1 = sum1 * (sum1 + 1) / 2 + this.left;
    int val2 = sum2 * (sum2 + 1) / 2 + this.top;
    int sum3 = val1 + val2;
    return sum3 * (sum3 + 1) / 2 + val2;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getClass().getName() + "[top=" + this.top + ",right=" + this.right
                                + ",bottom=" + this.bottom + ",left=" + this.left + "]";
  }
}
