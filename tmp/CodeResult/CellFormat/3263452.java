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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Styling of a {@link AbstractCell}
 */
public class CellFormat implements Serializable {

   private String pattern;

   private SerializableColor backgroundColor = SerializableColor.WHITE;
   private SerializableColor color = SerializableColor.BLACK;

   /**
    * Border type
    */
   public enum Border {
      ALL, TOP, BOTTOM, RIGHT, LEFT, NONE
   }

   /**
    * Border line style
    */
   public enum BorderType {
      SOLID, DOUBLE, DOTTED, NONE
   }

   private List<Border> borders = new ArrayList<Border>();
   private Map<Border, BorderType> borderTypes = new HashMap<Border, BorderType>();
   private Map<Border, Integer> borderWidths = new HashMap<Border, Integer>();

   /**
    * Cell font
    */
   public enum Font {
      ARIAL, TAHOMA, TIMES
   }

   private Font font = Font.ARIAL;
   private int fontSize = 16;
   private boolean bold;
   private boolean italic;
   private boolean underlined;

   /**
    * Horizontal alignment
    */
   public enum Alignment {
      LEFT, RIGHT, CENTER, GENERAL
   }

   /**
    * Vertical alignment
    */
   public enum VAlignment {
      TOP, CENTER, BOTTOM
   }

   private Alignment alignment = Alignment.LEFT;
   private VAlignment valignment = VAlignment.BOTTOM;

   /**
    * Creates a new default cell format
    */
   public CellFormat() {
      borderTypes.put(Border.ALL, BorderType.SOLID);
      borderTypes.put(Border.BOTTOM, BorderType.SOLID);
      borderTypes.put(Border.LEFT, BorderType.SOLID);
      borderTypes.put(Border.RIGHT, BorderType.SOLID);
      borderTypes.put(Border.TOP, BorderType.SOLID);

      borderWidths.put(Border.ALL, 1);
      borderWidths.put(Border.BOTTOM, 1);
      borderWidths.put(Border.LEFT, 1);
      borderWidths.put(Border.RIGHT, 1);
      borderWidths.put(Border.TOP, 1);
   }

   /**
    * Get border type for a specific border
    * @param border the border
    * @return the borders type
    */
   public BorderType getBorderType(Border border) {
      return borderTypes.get(border);
   }

   /**
    * Sets a border type for a specific border
    * @param border the border
    * @param borderType the border type
    */
   public void setBorderType(Border border, BorderType borderType) {
      borderTypes.put(border, borderType);
   }

   /**
    * Is text in the cell italic
    * @return is text in the cell italic
    */
   public boolean isItalic() {
      return italic;
   }

   /**
    * Sets if the text in the cell is italic
    * @param italic if the text in the cell is italic
    */
   public void setItalic(boolean italic) {
      this.italic = italic;
   }

   /**
    * Is text in the cell underlined
    * @return if text in this cell is underlined
    */
   public boolean isUnderlined() {
      return underlined;
   }

   /**
    * Sets if text in the cell is underlined
    * @param underlined if text in this cell is underlined
    */
   public void setUnderlined(boolean underlined) {
      this.underlined = underlined;
   }

   /**
    * Gets the color on the text on the cell
    * @return the color
    */
   public SerializableColor getColor() {
      return color;
   }

   /**
    * Sets the color of the text in the cell
    * @param color the color
    */
   public void setColor(SerializableColor color) {
      this.color = color;
   }

   /**
    * Gets the vertical alignment in the cell
    * @return the vertical alignment
    */
   public VAlignment getValignment() {
      return valignment;
   }

   /**
    * Sets the vertical alignment in the cell
    * @param valignment  the vertical alignment
    */
   public void setValignment(VAlignment valignment) {
      this.valignment = valignment;
   }

   /**
    * Gets the background color of the cell
    * @return  the background color
    */
   public SerializableColor getBackgroundColor() {
      return backgroundColor;
   }

   /**
    * Sets the background color of the cell
    * @param backgroundColor  the background color
    */
   public void setBackgroundColor(SerializableColor backgroundColor) {
      this.backgroundColor = backgroundColor;
   }

   /**
    * Gets borders applied to the cell
    * @return this cells borders
    */
   public List<Border> getBorders() {
      return borders;
   }

   /**
    * Add a border to the cell
    * @param border the border
    */
   public void addBorder(Border border) {
      if (!borders.contains(border)) {
         borders.add(border);
      }
   }

   /**
    * Set all borders at once on the cell
    * @param borders the borders
    */
   public void setBorders(List<Border> borders) {
      this.borders = borders;
   }

   /**
    * Get font to use on the cell
    * @return the font
    */
   public Font getFont() {
      return font;
   }

   /**
    * Sets font to use on the cell
    * @param font the font
    */
   public void setFont(Font font) {
      this.font = font;
   }

   /**
    * Is text in the cell bold
    * @return whether text in cell is bold
    */
   public boolean isBold() {
      return bold;
   }

   /**
    * Sets if text in the cell is bold
    * @param bold  if text in the cell is bold
    */
   public void setBold(boolean bold) {
      this.bold = bold;
   }

   /**
    * Gets font size in the cell
    * @return the font size
    */
   public int getFontSize() {
      return fontSize;
   }

   /**
    * Sets font size in the cell
    * @param fontSize the font size
    */
   public void setFontSize(int fontSize) {
      this.fontSize = fontSize;
   }

   public String getPattern() {
      return pattern;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   /**
    * Gets width of a specific border
    * @param border the border
    * @return the border width
    */
   public int getBorderWidth(Border border) {
      return borderWidths.get(border);
   }

   /**
    * Sets width of a specific border
    * @param border the border
    * @param borderWidth the width
    */
   public void setBorderWidth(Border border, int borderWidth) {
      borderWidths.put(border, borderWidth);
   }

   /**
    * Gets horizontal alignment in the cell
    * @return the horizontal alignment
    */
   public Alignment getAlignment() {
      return alignment;
   }

   /**
    * Sets horizontal alignment on the cell
    * @param alignment the horizontal alignment
    */
   public void setAlignment(Alignment alignment) {
      this.alignment = alignment;
   }

   /**
    * Copies this cell format to a new instance
    * @return a cell format
    */
   public CellFormat copy() {
      CellFormat result = new CellFormat();
      result.setFontSize(fontSize);
      result.setBold(bold);
      result.setItalic(italic);
      result.setUnderlined(underlined);
      result.setPattern(pattern);
      result.setColor(color);
      result.setBackgroundColor(backgroundColor);
      result.setFont(font);
      result.setAlignment(alignment);
      result.setValignment(valignment);
      result.setBorders(new ArrayList<Border>(borders));
      for (Border border : borders) {
         result.setBorderType(border, borderTypes.get(border));
         result.setBorderWidth(border, borderWidths.get(border));
      }
      return result;
   }

   @Override
   public String toString() {
      return "CellFormat{" +
            "alignment=" + alignment +
            ", valignment=" + valignment +
            ", font=" + font +
            ", fontSize=" + fontSize +
            ", italic=" + italic +
            ", bold=" + bold +
            ", underlined=" + underlined +
            ", pattern='" + pattern + '\'' +
            ", backgroundColor=" + backgroundColor +
            ", color=" + color +
            ", borders=" + borders +
            ", borderTypes=" + borderTypes +
            ", borderWidths=" + borderWidths +
            '}';
   }
}
