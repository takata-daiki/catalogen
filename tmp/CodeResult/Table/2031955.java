/*
 * Table.java
 *
 * Created on 12 de Julho de 2008, 18:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.celiosilva.swingDK.dataTable;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author celio@celiosilva.com
 */
public class Table extends JTable{
    
    private                 FontMetrics                     fm;
    private                 List<TableColumn>               columns;
    private                 Class<? extends ColumnFormat>   columnFormatClass;
    private                 ColumnFormat                    emptyColumnFormat;
    private                 Collection<? extends ColumnFormat>      dataReceived;
    private                 JLabel                          counter;
    private                 boolean                         hasData;
    private                 JButton                         actionToPerform;
    
    
    
    /** Creates a new instance of Table */
    public Table() {
        this.setAutoCreateColumnsFromModel(false);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.setAutoCreateRowSorter(true);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        columns = new ArrayList<TableColumn>();
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && actionToPerform != null){
                    actionToPerform.doClick();
                }
            }
        });
    }

    public JButton getActionToPerform() {
        return actionToPerform;
    }

    public void setActionToPerform(JButton actionToPerform) {
        this.actionToPerform = actionToPerform;
    }    
    
    public void setData(Collection<? extends ColumnFormat> data){
        //this.cleanTable();
        dataReceived = data;
        this.cleanData();
        Columns columns = null;
        int index = 0;
        if (data != null && data.size() != 0){
            TableModel tableModel = new TableModel(data);
            super.setModel(tableModel);
            columns = data.iterator().next().returnColumns();
            this.setEnabled(true);
            this.hasData = true;
        } else {
            columns = this.createEmptyColumnFormat().returnColumns();
            this.setEnabled(false);
            this.hasData = false;
        }
        fm = this.getFontMetrics(this.getFont());
        
        for (Iterator<ColumnData> it = columns.columnDatas().iterator(); it.hasNext();){
            ColumnData columnData = it.next();
            String titulo = columnData.getColumnName();
            int largura = larguraEmLetras(columnData.getColumnLength());
            adicionaColuna(index, largura, titulo, columnData.isResizable());
            index++;
        }
        if (counter != null){
            counter.setText(this.getModel().getRowCount() + "");
        }
        
    }
    
    private void cleanData(){
        for (TableColumn tc:columns){
            this.removeColumn(tc);
        }
        this.columns = new ArrayList<TableColumn>();
        this.hasData = false;
    }
    
    public void setData(ColumnFormat data){
        Collection<ColumnFormat> col = new ArrayList<ColumnFormat>();
        col.add(data);
        this.setData(col);
    }
    
    public void setFont(Font font) {
        super.setFont(font);
    }
    
    public Object getSelectedObject(){
        if (this.getSelectedRow() == -1){
            return null;
        }
        int selectedIndex = this.convertRowIndexToModel(this.getSelectedRow());
        Object obj = ((TableModel)this.getModel()).getValueAt(selectedIndex);
        return obj;
    }
    
    
    
    private void adicionaColuna(int indice, int largura, String titulo, boolean resizable){
        int larguraTitulo = this.larguraEmLetras(titulo.toString().length());
        if (largura < larguraTitulo){
            largura = larguraTitulo;
        }
        TableColumn column = new TableColumn(indice, largura, null, null);
        column.setHeaderValue(" " + titulo);
        column.setResizable(resizable);
        columns.add(column);
        this.addColumn(column);
        
    }
    
    private int larguraEmLetras(int largura){
        int letra = fm.stringWidth("M");
        return letra * largura;
    }
    
    private int larguraEmNumero(int largura){
        int numero = fm.stringWidth("0");
        return numero * largura;
    }
    
    public void cleanTable(){
        this.cleanData();
        this.setData(this.createEmptyColumnFormat());
        this.hasData = false;
    }
    
    public Class<? extends ColumnFormat> getColumnFormatClass() {
        return columnFormatClass;
    }
    
    public void setColumnFormatClass(Class<? extends ColumnFormat> columnFormatClass) {
        this.columnFormatClass = columnFormatClass;
        this.setData(this.createEmptyColumnFormat());
        this.hasData = false;
    }
    
    private ColumnFormat createEmptyColumnFormat(){
        if (this.emptyColumnFormat == null){
            try {
                this.emptyColumnFormat = this.getColumnFormatClass().newInstance();
            } catch (Exception ex) {
                String message = "This class cannot be used to generate the table." +
                        "\n" + ex.getMessage() + "\n\nPlease set the property ColumnFormatClass\nThe system will resume operation";
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        return emptyColumnFormat;
    }
    
    public Collection<? extends ColumnFormat> getData(){
        if (this.hasData)
            return dataReceived;
        else
            return null;
    }
    
    public void setCounter(JLabel counter){
        this.counter = counter;
        this.counter.setText("0");
    }
    
    public JLabel getCounter(){
        return this.counter;
    }
    
    public boolean isHasData() {
        return hasData;
    }
    
}
