/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manticore.report;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellFill;

/**
 *
 * @author are
 */
class CellRenderer extends DefaultTableCellRenderer {
  private final FixFormatReportDesigner designer;
  CellRenderer(final FixFormatReportDesigner outer) {
    this.designer = outer;
  }

  private int getThickness(BorderStyle borderStyle) {
    int retval = 1;
    switch (borderStyle) {
      case THIN:
        retval = 2;
        break;
      case MEDIUM:
        retval = 3;
        break;
      case THICK:
        retval = 4;
        break;
      case DASHED:
        retval = 1;
        break;
      case DASH_DOT_DOT:
        retval = 1;
        break;
      case MEDIUM_DASH_DOT_DOT:
        retval = 3;
        break;
      case HAIR:
        retval = 1;
        break;
      default:
        retval = 1;
    }
    return retval;
  }

  /**
   * This method retrieves the AWT Color representation from the colour hash table
   *
   * @param index Description of the Parameter
   * @param deflt Description of the Parameter
   * @return The aWTColor value
   */
  public final static java.awt.Color awtColor(org.apache.poi.ss.usermodel.Color color) {
    java.awt.Color awtColor = null;

    if (color.equals(HSSFColor.AUTOMATIC.getInstance())) {
      awtColor = java.awt.Color.BLACK;
    } else if (color instanceof HSSFColor) {
      HSSFColor hssfColor = (HSSFColor) color;
      short[] rgb = hssfColor.getTriplet();
      int r = (rgb[0] < 0) ? (rgb[0] + 256) : rgb[0];
      int g = (rgb[1] < 0) ? (rgb[1] + 256) : rgb[1];
      int b = (rgb[2] < 0) ? (rgb[2] + 256) : rgb[2];
      awtColor = new java.awt.Color(r, g, b);
    } else if (color instanceof XSSFColor) {
      XSSFColor xssfColor = (XSSFColor) color;
      byte[] rgb = xssfColor.getRGB();
      int r = (rgb[0] < 0) ? (rgb[0] + 256) : rgb[0];
      int g = (rgb[1] < 0) ? (rgb[1] + 256) : rgb[1];
      int b = (rgb[2] < 0) ? (rgb[2] + 256) : rgb[2];
      awtColor = new java.awt.Color(r, g, b);
    }
    return awtColor;
  }

  public final static java.awt.Color awtColor(org.apache.poi.ss.usermodel.Color color, java.awt.Color defaultColor) {
    java.awt.Color awtColor = null;

    if (color == null || color.equals(HSSFColor.AUTOMATIC.getInstance())) {
      awtColor = defaultColor;
    } else if (color instanceof HSSFColor) {
      HSSFColor hssfColor = (HSSFColor) color;
      short[] rgb = hssfColor.getTriplet();
      int r = (rgb[0] < 0) ? (rgb[0] + 256) : rgb[0];
      int g = (rgb[1] < 0) ? (rgb[1] + 256) : rgb[1];
      int b = (rgb[2] < 0) ? (rgb[2] + 256) : rgb[2];
      awtColor = new java.awt.Color(r, g, b);
    } else if (color instanceof XSSFColor) {
      XSSFColor xssfColor = (XSSFColor) color;
      byte[] rgb = xssfColor.getARGB();
      int a = (rgb[0] < 0) ? (rgb[0] + 256) : rgb[0];
      int r = (rgb[1] < 0) ? (rgb[1] + 256) : rgb[1];
      int g = (rgb[2] < 0) ? (rgb[2] + 256) : rgb[2];
      int b = (rgb[3] < 0) ? (rgb[3] + 256) : rgb[3];
      awtColor = new java.awt.Color(r, g, b, a);
    }
    return awtColor;
  }

  public final java.awt.Color awtColor(Short c, java.awt.Color defaultColor) {
    java.awt.Color awtColor = defaultColor;

    if (designer.workbook instanceof HSSFWorkbook) {
      HSSFPalette customPalette = ((HSSFWorkbook) designer.workbook).getCustomPalette();
      HSSFColor hssfColor = customPalette.getColor(c);
      if (hssfColor == null || hssfColor.equals(HSSFColor.AUTOMATIC.getInstance())) {
        awtColor = defaultColor;
      } else {
        short[] rgb = hssfColor.getTriplet();
        int r = (rgb[0] < 0)
                ? (rgb[0] + 256)
                : rgb[0];
        int g = (rgb[1] < 0)
                ? (rgb[1] + 256)
                : rgb[1];
        int b = (rgb[2] < 0)
                ? (rgb[2] + 256)
                : rgb[2];
        awtColor = new java.awt.Color(r, g, b);
      }
    }
    return awtColor;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
          int row, int column) {
    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    String sheetname = designer.tabbedPane.getTitleAt(designer.tabbedPane.getSelectedIndex());
    String reference = designer.getExcelColumnName(column) + (row + 1);

    label.setBackground(java.awt.Color.WHITE);
    if (value instanceof Cell) {
      Cell cell = (Cell) value;
      Comment comment = cell.getCellComment();
      if (comment != null) {
        label.setToolTipText(cell.getCellComment().getString().getString());
      } else {
        label.setToolTipText(null);
      }
      

      CellStyle cellStyle1 = cell.getCellStyle();
      label.setText(new DataFormatter().formatCellValue(cell));
      
      org.apache.poi.ss.usermodel.Font f;
      if (cellStyle1 instanceof XSSFCellStyle) {
        XSSFCell xssfCell = (XSSFCell) cell;
        XSSFCellStyle xssfCellStyle = xssfCell.getCellStyle();

        XSSFColor fillForegroundColorColor = xssfCellStyle.getFillForegroundColorColor();
        if (fillForegroundColorColor == null) {
          short fillId = (short) xssfCellStyle.getCoreXf().getFillId();
          XSSFCellFill fill = ((XSSFWorkbook) designer.workbook).getStylesSource().getFillAt(fillId);

          fillForegroundColorColor = fill.getFillForegroundColor();
        }
        label.setBackground(awtColor(fillForegroundColorColor, java.awt.Color.WHITE));
        f = designer.workbook.getFontAt(xssfCellStyle.getFontIndex());
      } else {
        label.setBackground(awtColor(cellStyle1.getFillBackgroundColorColor(), java.awt.Color.WHITE));
        label.setForeground(awtColor(cellStyle1.getFillForegroundColorColor(), java.awt.Color.BLACK));
        f = designer.workbook.getFontAt(cellStyle1.getFontIndex());
      }

      String fontName = f.getFontName();
      short fontHeightInPoints = f.getFontHeightInPoints();
      boolean italic = f.getItalic();
      short boldweight = f.getBoldweight();
      byte underline = f.getUnderline();

      java.awt.Font font = new java.awt.Font(fontName, java.awt.Font.PLAIN, fontHeightInPoints);
      final Map attributes = font.getAttributes();
      if (underline > 0) {
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
      }
      if (boldweight > 0) {
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
      }
      if (italic) {
        attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
      } else {
        attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
      }
      font = font.deriveFont(attributes);
      label.setFont(font);
      

      if (f instanceof XSSFFont) {
        XSSFFont xssfFont = (XSSFFont) f;
        label.setForeground(awtColor(xssfFont.getXSSFColor(), java.awt.Color.BLACK));
      } else {
        label.setForeground(awtColor(f.getColor(), java.awt.Color.BLACK));
      }

      MatteBorder topBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, java.awt.Color.WHITE);
      MatteBorder bottomBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, java.awt.Color.WHITE);
      MatteBorder leftBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, java.awt.Color.WHITE);
      MatteBorder rightBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, java.awt.Color.WHITE);

      if (cellStyle1 instanceof XSSFCellStyle) {
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle1;

        if (xssfCellStyle.getBorderTop()!=BorderStyle.NONE) {
          topBorder = BorderFactory.createMatteBorder(getThickness(cellStyle1.getBorderTop()), 0, 0, 0, awtColor(xssfCellStyle.getTopBorderXSSFColor(), java.awt.Color.BLACK));
        }
        if (cellStyle1.getBorderBottom()!=BorderStyle.NONE) {
          bottomBorder = BorderFactory.createMatteBorder(0, 0, getThickness(cellStyle1.getBorderBottom()), 0, awtColor(xssfCellStyle.getBottomBorderXSSFColor(), java.awt.Color.BLACK));
        }
        CompoundBorder compoundBorder1 = new CompoundBorder(topBorder, bottomBorder);
        if (cellStyle1.getBorderLeft()!=BorderStyle.NONE) {
          leftBorder = BorderFactory.createMatteBorder(0, getThickness(cellStyle1.getBorderLeft()), 0, 0, awtColor(xssfCellStyle.getLeftBorderXSSFColor(), java.awt.Color.BLACK));
        }
        if (cellStyle1.getBorderRight()!=BorderStyle.NONE) {
          rightBorder = BorderFactory.createMatteBorder(0, 0, 0, getThickness(cellStyle1.getBorderRight()), awtColor(xssfCellStyle.getRightBorderXSSFColor(), java.awt.Color.BLACK));
        }
        CompoundBorder compoundBorder2 = new CompoundBorder(leftBorder, rightBorder);
        if (!isSelected) {
          label.setBorder(new CompoundBorder(compoundBorder1, compoundBorder2));
        }
      } else {
        if (cellStyle1.getBorderTop()!=BorderStyle.NONE) {
          topBorder = BorderFactory.createMatteBorder(getThickness(cellStyle1.getBorderTop()), 0, 0, 0, awtColor(cellStyle1.getTopBorderColor(), java.awt.Color.BLACK));
        }
        if (cellStyle1.getBorderBottom()!=BorderStyle.NONE) {
          bottomBorder = BorderFactory.createMatteBorder(0, 0, getThickness(cellStyle1.getBorderBottom()), 0, awtColor(cellStyle1.getBottomBorderColor(), java.awt.Color.BLACK));
        }
        CompoundBorder compoundBorder1 = new CompoundBorder(topBorder, bottomBorder);
        if (cellStyle1.getBorderLeft()!=BorderStyle.NONE) {
          leftBorder = BorderFactory.createMatteBorder(0, getThickness(cellStyle1.getBorderLeft()), 0, 0, awtColor(cellStyle1.getLeftBorderColor(), java.awt.Color.BLACK));
        }
        if (cellStyle1.getBorderRight()!=BorderStyle.NONE) {
          rightBorder = BorderFactory.createMatteBorder(0, 0, 0, getThickness(cellStyle1.getBorderRight()), awtColor(cellStyle1.getRightBorderColor(), java.awt.Color.BLACK));
        }
        CompoundBorder compoundBorder2 = new CompoundBorder(leftBorder, rightBorder);
        if (!isSelected) {
          label.setBorder(new CompoundBorder(compoundBorder1, compoundBorder2));
        }
      }

      switch (cellStyle1.getAlignment()) {
        case 1:
          label.setHorizontalAlignment(JLabel.LEFT);
          break;
        case 2:
          label.setHorizontalAlignment(JLabel.CENTER);
          break;
        case 3:
          label.setHorizontalAlignment(JLabel.RIGHT);
          break;
        default:
          label.setHorizontalAlignment(JLabel.LEFT);
      }
      switch (cellStyle1.getVerticalAlignment()) {
        case 1:
          label.setVerticalAlignment(JLabel.TOP);
          break;
        case 2:
          label.setVerticalAlignment(JLabel.CENTER);
          break;
        case 3:
          label.setVerticalAlignment(JLabel.BOTTOM);
          break;
        default:
          label.setVerticalAlignment(JLabel.TOP);
      }
    }
    

    for (RecordSet r : designer.fixFormatReport.recordSets.values()) {
      for (FieldMapping m : r.fieldMappings) {
        if (m.sheetName.equalsIgnoreCase(sheetname)
                && m.reference.equalsIgnoreCase(reference)) {
          StringBuilder builder = new StringBuilder();
          if (!designer.previewMode) {
            builder.append("<html>");
            builder.append("<font size='0' color='blue'>").append(m.fieldName).append("</font>");
            builder.append("</html>");
            label.setText(builder.toString());
          } else {
              //label.setText(cell.);
          }
          
          builder.setLength(0);
          builder.append("<html>");
          builder.append("<font size='0' color='blue'>").append(m.recordSetId).append("</font>!");
          builder.append("<font size='0' color='blue'>").append(m.fieldName).append("</font>");
          builder.append("</html>");
          label.setToolTipText(builder.toString());
          break;
        }
      }
    }
    return label;
  }

}
