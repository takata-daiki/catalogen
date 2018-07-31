/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djbc.zkcomponent.fusion.model;

/**
 *
 * @author jote
 */
public class ChartData {

   private String label;
   private String value;
   private String tooltext;
   private String link;
   private String color;

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public ChartData(String label, String value, String tooltext, String link) {
      this.label = label;
      this.value = value;
      this.tooltext = tooltext;
      this.link = link;
   }

   public ChartData(String label, String value, String link) {
      this.label = label;
      this.value = value;
      this.tooltext = link;
   }

   public ChartData(String label, String value) {
      this.label = label;
      this.value = value;
   }

   public ChartData(String label, String value, String tooltext, String link, ChartColor cl) {
      // ChartColor cl = ChartColor.Blue;
      this.label = label;
      this.value = value;
      this.tooltext = tooltext;
      this.link = link;
      if (!(cl == null)) {
         this.color = cl.getColor();
      }
   }

   /**
    * @return the tooltext
    */
   public String getTooltext() {
      return tooltext;
   }

   /**
    * @param tooltext the tooltext to set
    */
   public void setTooltext(String tooltext) {
      this.tooltext = tooltext;
   }

   /**
    * @return the link
    */
   public String getLink() {
      return link;
   }

   /**
    * @param link the link to set
    */
   public void setLink(String link) {
      this.link = link;
   }

   /**
    * @return the color
    */
   public String getColor() {
      return color;
   }

   /**
    * @param color the color to set
    */
   public void setColor(String Color) {
      this.color = Color;
   }
}
