/*
 * Column.java
 *
 * Created on 22 de Julho de 2008, 23:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.celiosilva.swingDK.dataTable;

import java.lang.reflect.Method;

/**
 *
 * @author celio@celiosilva.com
 */
public class ColumnData {
    
    private                 Object                      columnValue;
    private                 int                         columnLength;
    private                 String                      columnName;
    private                 boolean                     resizable;
    /** Creates a new instance of Column */
    ColumnData() {
        this.setResizable(true);
    }       

    public Object getColumnValue() {
        return columnValue;
    }

    public int getColumnLength() {
        return columnLength;
    }

    void setColumnLength(int columnLength) {
        this.columnLength = columnLength;
    }

    void setColumnValue(Object columnValue) {
        this.columnValue = columnValue;
    }

    public String getColumnName() {
        return columnName;
    }

    void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }
    

         
}
