/*
 *
 * Copyright (C) 201 Andreas Reichel <andreas@manticore-projects.com>
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.manticore.swingui;

import com.manticore.icon.Icon16;
import static com.manticore.swingui.MTableCellRenderer.dateFormat;
import static com.manticore.swingui.MTableCellRenderer.dateTimeFormat;
import static com.manticore.swingui.MTableCellRenderer.decimalFormat;
import static com.manticore.swingui.MTableCellRenderer.integerFormat;
import com.opencsv.CSVWriter;
import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestLine;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class MTable extends JTable {

  public final static Dimension viewPortSize = new Dimension(480, 120);
  public final TableColumnAdjuster tca;

  public Action copyAllAction = new AbstractAction("Copy All", Icon16.STOCK_COPY) {
    @Override
    public void actionPerformed(ActionEvent ae) {
      copyToClipBoard(-1, -1);
    }
  };

  public Action copyRowAction = new AbstractAction("Copy Row", Icon16.STOCK_COPY) {
    @Override
    public void actionPerformed(ActionEvent ae) {
      copyToClipBoard(getSelectedRow(), -1);
    }
  };

  public Action copyColAction = new AbstractAction("Copy Column", Icon16.STOCK_COPY) {
    @Override
    public void actionPerformed(ActionEvent ae) {
      copyToClipBoard(-1, getSelectedColumn());
    }
  };

  public Action copyCellAction = new AbstractAction("Copy Cells", Icon16.STOCK_COPY) {
    @Override
    public void actionPerformed(ActionEvent ae) {
      copyToClipBoard(getSelectedRow(), getSelectedColumn());
    }
  };

  public Action copySpreadSheetAction =
      new AbstractAction("Copy to Spreadsheet", Icon16.APPLICATION_VND_MS_EXCEL) {
        @Override
        public void actionPerformed(ActionEvent ae) {
          copyToSpreadSheet(-1, -1);
        }
      };

  public Action copyASCIIAction = new AbstractAction("Copy to ASCII", Icon16.APPLICATION_TEXT) {
    @Override
    public void actionPerformed(ActionEvent ae) {
      copyToASCII(-1, -1);
    }
  };

  public Action copyCSVAction = new AbstractAction("Copy to CSV", Icon16.GNOME_MIME_TEXT_PLAIN) {
    @Override
    public void actionPerformed(ActionEvent ae) {
      copyToCSV(-1, -1);
    }
  };

  public JPopupMenu popupMenu = new JPopupMenu("Table Actions");

  {
    popupMenu.add(copyAllAction);
    popupMenu.add(copyRowAction);
    popupMenu.add(copyColAction);
    popupMenu.add(copyCellAction);
    popupMenu.add(new JSeparator());
    popupMenu.add(copySpreadSheetAction);
    popupMenu.add(copyASCIIAction);
    popupMenu.add(copyCSVAction);
  }

  public MTable() {
    super();
    setDefaultRenderer(Object.class, new MTableCellRenderer());
    setDefaultRenderer(Integer.class, new MTableCellRenderer());
    setDefaultRenderer(Long.class, new MTableCellRenderer());
    setDefaultRenderer(Float.class, new MTableCellRenderer());
    setDefaultRenderer(Double.class, new MTableCellRenderer());
    setDefaultRenderer(BigDecimal.class, new MTableCellRenderer());
    setDefaultRenderer(Number.class, new MTableCellRenderer());
    setDefaultRenderer(Short.class, new MTableCellRenderer());
    setDefaultRenderer(String.class, new MTableCellRenderer());

    setFont(SwingUI.MONO_MANTICORE_FONT);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setColumnSelectionAllowed(false);
    setPreferredScrollableViewportSize(viewPortSize);
    setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    tca = new TableColumnAdjuster(this);
    tca.adjustColumns();

    setComponentPopupMenu(popupMenu);
    setInheritsPopupMenu(true);
  }

  public void adjustColumns() {
    tca.adjustColumns();
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void setData(String[] columnNames, ArrayList<Object[]> data) {
    if (data != null && columnNames != null) {
      DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columnNames);

      boolean enabled = super.isEnabled();
      super.setEnabled(false);

      boolean ignoreRepaint = super.getIgnoreRepaint();
      super.setIgnoreRepaint(true);

      setModel(model);
      adjustColumns();

      super.setIgnoreRepaint(ignoreRepaint);
      super.setEnabled(enabled);
    }
  }

  public void setData(String[] columnNames, Object[][] data) {
    if (data != null && columnNames != null) {
      DefaultTableModel model = new DefaultTableModel(data, columnNames);

      boolean enabled = super.isEnabled();
      super.setEnabled(false);

      boolean ignoreRepaint = super.getIgnoreRepaint();
      super.setIgnoreRepaint(true);

      setModel(model);
      adjustColumns();

      super.setIgnoreRepaint(ignoreRepaint);
      super.setEnabled(enabled);
    }
  }

  public void setData(Object[][] data) {
    if (data != null) {
      Object[] columnNames = data[0];
      Object[][] d = (Object[][]) data[1];

      DefaultTableModel model = new DefaultTableModel(d, columnNames);

      boolean enabled = super.isEnabled();
      super.setEnabled(false);

      boolean ignoreRepaint = super.getIgnoreRepaint();
      super.setIgnoreRepaint(true);

      setModel(model);
      adjustColumns();

      super.setIgnoreRepaint(ignoreRepaint);
      super.setEnabled(enabled);
    }
  }

  public void copyToClipBoard(int row, int col) {
    StringBuilder htmlBuilder = new StringBuilder("<html>");
    StringBuilder asciiBuilder = new StringBuilder();

    htmlBuilder.append("<table>").append("<thead><tr>");
    for (int c = 0; c < getColumnCount(); c++) {
      if (col < 0 || col == c) {
        htmlBuilder.append("<th alignt='center' bgcolor=")
            .append(SwingUI.getHexColor(SwingUI.MANTICORE_DARK_BLUE))
            .append("'><font color='WHITE'>").append(getColumnName(c)).append("</font></th>");
        asciiBuilder.append(getColumnName(c)).append("\t");
      }
    }
    htmlBuilder.append("</tr></thead>");
    htmlBuilder.append("<tbody>");
    asciiBuilder.append("\n");
    for (int r = 0; r < getRowCount(); r++) {
      if (row < 0 || row == r) {
        htmlBuilder.append("<tr>");
        for (int c = 0; c < getColumnCount(); c++) {
          if (col < 0 || col == c) {
            Object value = getValueAt(r, c);
            String s;
            String align = "left";
            Color fgColor = SwingUI.MANTICORE_DARK_BLUE;

            if (value == null) {
              s = "";
            } else if (value instanceof java.sql.Timestamp) {
              align = "right";
              s = dateTimeFormat.format((java.sql.Timestamp) value);
            } else if (value instanceof Date) {
              align = "right";
              GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
              cal.setTime((Date) value);
              if (cal.get(GregorianCalendar.HOUR_OF_DAY) != 0
                  || cal.get(GregorianCalendar.MINUTE) != 0
                  || cal.get(GregorianCalendar.SECOND) != 0
                  || cal.get(GregorianCalendar.MILLISECOND) != 0) {
                s = dateTimeFormat.format((Date) value);
              } else {
                s = dateFormat.format((Date) value);
              }
            } else if (value instanceof Long) {
              align = "right";
              s = integerFormat.format((Long) value);

              if (((Long) value) < 0) {
                fgColor = SwingUI.MANTICORE_ORANGE;
              }

            } else if (value instanceof Integer) {
              align = "right";
              s = integerFormat.format((Integer) value);

              if (((Integer) value) < 0) {
                fgColor = SwingUI.MANTICORE_ORANGE;
              }

            } else if (value instanceof Short) {
              align = "right";
              s = integerFormat.format((Short) value);

              if (((Short) value) < 0) {
                fgColor = SwingUI.MANTICORE_ORANGE;
              }

            } else if (value instanceof Double) {
              align = "right";
              s = decimalFormat.format((Double) value);

              if (((Double) value) < 0) {
                fgColor = SwingUI.MANTICORE_ORANGE;
              }

            } else if (value instanceof Float) {
              align = "right";
              s = decimalFormat.format((Float) value);

              if (((Float) value) < 0) {
                fgColor = SwingUI.MANTICORE_ORANGE;
              }

            } else if (value instanceof BigDecimal) {
              align = "right";
              s = decimalFormat.format((BigDecimal) value);

              if (((BigDecimal) value).doubleValue() < 0) {
                fgColor = SwingUI.MANTICORE_ORANGE;
              }

            } else if (value instanceof Number) {
              align = "right";
              s = decimalFormat.format((Number) value);

              if (((Number) value).doubleValue() < 0d) {
                fgColor = SwingUI.MANTICORE_ORANGE;
              }

            } else if (value instanceof String) {
              s = (String) value;
              s = s.replaceAll(" ", "&nbsp;");
            } else {
              s = value.toString();
              s = s.replaceAll(" ", "&nbsp;");
            }

            htmlBuilder.append("<td align='").append(align).append("' bgcolor='")
                .append(r % 2 == 0 ? "WHITE" : SwingUI.getHexColor(SwingUI.MANTICORE_LIGHT_GREY))
                .append("'><font color='").append(SwingUI.getHexColor(fgColor)).append("'>")
                .append(s).append("</font></td>");
            asciiBuilder.append(s).append("\t");
          }
        }
        htmlBuilder.append("</tr>");
        asciiBuilder.append("\n");
      }
    }
    htmlBuilder.append("</tbody></table>");
    htmlBuilder.append("</html>");

    Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable transferable = new BasicTransferable(
        asciiBuilder.toString().replaceAll("&nbsp;", " "), htmlBuilder.toString());
    systemClipboard.setContents(transferable, null);
  }

  public void copyToSpreadSheet(int row, int col) {
    try {
      Workbook wb = new HSSFWorkbook();

      DataFormat dataFormat = wb.createDataFormat();

      Font variableFont = wb.createFont();
      variableFont.setFontName("Monospaced"); // SwingUI.MEDIUM_MANTICORE_FONT.getFontName());
      variableFont.setFontHeightInPoints((short) 10);

      Font headerFont = wb.createFont();
      headerFont.setFontName(SwingUI.MEDIUM_MANTICORE_FONT.getFontName());
      headerFont.setFontHeightInPoints((short) 10);
      headerFont.setBold(true);
      headerFont.setColor(IndexedColors.WHITE.getIndex());

      Font fixedFont = wb.createFont();
      fixedFont.setFontName("Monospaced"); // SwingUI.MONO_MANTICORE_FONT.getFontName());
      fixedFont.setFontHeightInPoints((short) 10);

      CellStyle headerCellStyle = wb.createCellStyle();
      headerCellStyle.setFont(headerFont);
      headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
      headerCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
      headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

      CellStyle textCellStyle = wb.createCellStyle();
      textCellStyle.setFont(variableFont);

      CellStyle dateCellStyle = wb.createCellStyle();
      dateCellStyle.setFont(fixedFont);
      dateCellStyle.setDataFormat(dataFormat.getFormat("MM/DD/YYYY"));

      CellStyle timeStampCellStyle = wb.createCellStyle();
      timeStampCellStyle.setFont(fixedFont);
      timeStampCellStyle.setDataFormat(dataFormat.getFormat("MM/DD/YYYY HH:mm"));

      CellStyle integerStyle = wb.createCellStyle();
      integerStyle.setFont(fixedFont);
      integerStyle.setDataFormat(dataFormat.getFormat("#,##0;[RED]-#,##0"));

      CellStyle floatStyle = wb.createCellStyle();
      floatStyle.setFont(fixedFont);
      floatStyle.setDataFormat(dataFormat.getFormat("#,##0.00;[RED]-#,##0.00"));

      Sheet sheet = wb.createSheet("Table Data");
      int sheetRowCounter = 0;
      int sheetColCounter = 0;

      Row sheetRow = sheet.createRow(sheetRowCounter++);
      for (int c = 0; c < getColumnCount(); c++) {
        if (col < 0 || col == c) {
          Cell cell = sheetRow.createCell(sheetColCounter++);
          cell.setCellValue(getColumnName(c));
          cell.setCellStyle(headerCellStyle);
        }
      }


      for (int r = 0; r < getRowCount(); r++) {
        sheetColCounter = 0;

        if (row < 0 || row == r) {
          sheetRow = sheet.createRow(sheetRowCounter++);
          for (int c = 0; c < getColumnCount(); c++) {
            if (col < 0 || col == c) {
              Cell cell = sheetRow.createCell(sheetColCounter++);

              Object value = getValueAt(r, c);

              if (value == null) {
              } else if (value instanceof java.sql.Timestamp) {
                cell.setCellStyle(timeStampCellStyle);
                cell.setCellValue((java.sql.Timestamp) value);
              } else if (value instanceof Date) {
                GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
                cal.setTime((Date) value);
                if (cal.get(GregorianCalendar.HOUR_OF_DAY) != 0
                    || cal.get(GregorianCalendar.MINUTE) != 0
                    || cal.get(GregorianCalendar.SECOND) != 0
                    || cal.get(GregorianCalendar.MILLISECOND) != 0) {

                  cell.setCellStyle(timeStampCellStyle);
                  cell.setCellValue((java.util.Date) value);
                } else {
                  cell.setCellStyle(dateCellStyle);
                  cell.setCellValue((java.util.Date) value);
                }
              } else if (value instanceof Long) {
                cell.setCellStyle(integerStyle);
                cell.setCellValue(((Long) value).doubleValue());
              } else if (value instanceof Integer) {
                cell.setCellStyle(integerStyle);
                cell.setCellValue(((Integer) value).doubleValue());
              } else if (value instanceof Short) {
                cell.setCellStyle(integerStyle);
                cell.setCellValue(((Short) value).doubleValue());
              } else if (value instanceof Double) {
                cell.setCellStyle(floatStyle);
                cell.setCellValue((Double) value);
              } else if (value instanceof Float) {
                cell.setCellStyle(floatStyle);
                cell.setCellValue(((Float) value).doubleValue());
              } else if (value instanceof BigDecimal) {
                cell.setCellStyle(floatStyle);
                cell.setCellValue(((BigDecimal) value).doubleValue());
              } else if (value instanceof Number) {
                cell.setCellStyle(floatStyle);
                cell.setCellValue(((Number) value).doubleValue());
              } else if (value instanceof String) {
                cell.setCellStyle(textCellStyle);
                cell.setCellValue((String) value);
              } else {
                cell.setCellStyle(textCellStyle);
                cell.setCellValue(value.toString());
              }
            }
          }
        }
      }

      HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
      for (int j = 0; j < sheetColCounter; j++) {
        sheet.setDefaultColumnStyle(j, sheet.getRow(sheetRowCounter - 1).getCell(j).getCellStyle());
        sheet.autoSizeColumn(j);
      }

      File f = File.createTempFile("manticore", ".xls");
      FileOutputStream fileOutputStream = new FileOutputStream(f);
      wb.write(fileOutputStream);
      fileOutputStream.flush();
      fileOutputStream.close();

      if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.OPEN)) {
          desktop.open(f);
        }
      }
    } catch (IOException ex) {
      ErrorDialog.show(this, ex);
    }
  }

  public void copyToASCII(int row, int col) {
    V2_AsciiTable at = new V2_AsciiTable();
    V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
    rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
    rend.setWidth(new WidthLongestLine());

    at.addStrongRule();
    ArrayList<Object> colData = new ArrayList<>();
    for (int c = 0; c < getColumnCount(); c++) {
      if (col < 0 || col == c) {
        Object headerValue = getColumnModel().getColumn(c).getHeaderValue();
        colData.add(headerValue);
      }
    }
    at.addRow(colData.toArray());
    at.addStrongRule();


    for (int r = 0; r < getRowCount(); r++) {
      if (row < 0 || row == r) {
        ArrayList<Object> rowData = new ArrayList<>();
        ArrayList<Object> alignmentData = new ArrayList<>();

        for (int c = 0; c < getColumnCount(); c++) {
          if (col < 0 || col == c) {
            Object value = getValueAt(r, c);
            String s;
            char align = 'l';
            if (value == null) {
              s = "";
            } else if (value instanceof java.sql.Timestamp) {
              align = 'c';
              s = dateTimeFormat.format((java.sql.Timestamp) value);
            } else if (value instanceof Date) {
              align = 'c';
              GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
              cal.setTime((Date) value);
              if (cal.get(GregorianCalendar.HOUR_OF_DAY) != 0
                  || cal.get(GregorianCalendar.MINUTE) != 0
                  || cal.get(GregorianCalendar.SECOND) != 0
                  || cal.get(GregorianCalendar.MILLISECOND) != 0) {
                s = dateTimeFormat.format((Date) value);
              } else {
                s = dateFormat.format((Date) value);
              }
            } else if (value instanceof Long) {
              align = 'r';
              s = integerFormat.format((Long) value);
            } else if (value instanceof Integer) {
              align = 'r';
              s = integerFormat.format((Integer) value);
            } else if (value instanceof Short) {
              align = 'r';
              s = integerFormat.format((Short) value);
            } else if (value instanceof Double) {
              align = 'r';
              s = decimalFormat.format((Double) value);
            } else if (value instanceof Float) {
              align = 'r';
              s = decimalFormat.format((Float) value);
            } else if (value instanceof BigDecimal) {
              align = 'r';
              s = decimalFormat.format((BigDecimal) value);
            } else if (value instanceof Number) {
              align = 'r';
              s = decimalFormat.format((Number) value);
            } else if (value instanceof String) {
              s = (String) value;
            } else {
              s = value.toString();
            }
            rowData.add(s);
            alignmentData.add(align);
          }
        }

        char[] alignArr = new char[alignmentData.size()];
        for (int i = 0; i < alignmentData.size(); i++) {
          alignArr[i] = (char) alignmentData.get(i);
        }

        at.addRow(rowData.toArray()).setAlignment(alignArr);
        at.addRule();
      }
    }
    RenderedTable rt = rend.render(at);

    Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable transferable = new BasicTransferable(rt.toString(), null);
    systemClipboard.setContents(transferable, null);
  }

  public void copyToCSV(int row, int col) {
    try {
      File f = File.createTempFile("manticore", ".csv");
      FileWriter fileWriter = new FileWriter(f);
      CSVWriter writer = new CSVWriter(fileWriter);
      ArrayList<String> colData = new ArrayList<>();
      for (int c = 0; c < getColumnCount(); c++) {
        if (col < 0 || col == c) {
          Object headerValue = getColumnModel().getColumn(c).getHeaderValue();
          colData.add((String) headerValue);
        }
      }
      writer.writeNext(colData.toArray(new String[0]));
      for (int r = 0; r < getRowCount(); r++) {
        if (row < 0 || row == r) {
          ArrayList<String> rowData = new ArrayList<>();
          for (int c = 0; c < getColumnCount(); c++) {
            if (col < 0 || col == c) {
              Object value = getValueAt(r, c);
              String s;
              if (value == null) {
                s = "";
              } else if (value instanceof java.sql.Timestamp) {
                s = dateTimeFormat.format((java.sql.Timestamp) value);
              } else if (value instanceof Date) {
                GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
                cal.setTime((Date) value);
                if (cal.get(GregorianCalendar.HOUR_OF_DAY) != 0
                    || cal.get(GregorianCalendar.MINUTE) != 0
                    || cal.get(GregorianCalendar.SECOND) != 0
                    || cal.get(GregorianCalendar.MILLISECOND) != 0) {
                  s = dateTimeFormat.format((Date) value);
                } else {
                  s = dateFormat.format((Date) value);
                }
              } else if (value instanceof Long) {
                s = integerFormat.format((Long) value);
              } else if (value instanceof Integer) {
                s = integerFormat.format((Integer) value);
              } else if (value instanceof Short) {
                s = integerFormat.format((Short) value);
              } else if (value instanceof Double) {
                s = decimalFormat.format((Double) value);
              } else if (value instanceof Float) {
                s = decimalFormat.format((Float) value);
              } else if (value instanceof BigDecimal) {
                s = decimalFormat.format((BigDecimal) value);
              } else if (value instanceof Number) {
                s = decimalFormat.format((Number) value);
              } else if (value instanceof String) {
                s = (String) value;
              } else {
                s = value.toString();
              }
              rowData.add(s);
            }
          }
          writer.writeNext(rowData.toArray(new String[0]));
        }
      }
      writer.flush();
      fileWriter.flush();
      writer.close();
      fileWriter.close();


      if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.OPEN)) {
          desktop.open(f);
        }
      }
    } catch (IOException ex) {
      ErrorDialog.show(this, ex);
    }
  }

    @Override
    public void setPreferredScrollableViewportSize(Dimension size) {
        super.setPreferredScrollableViewportSize(size); //To change body of generated methods, choose Tools | Templates.
    }
}
