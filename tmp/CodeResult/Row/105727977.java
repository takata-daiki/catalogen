/*
 * Row.java
 *
 * Created on 25 May 2007, 19:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dataman.model;

/**
 *
 * @author Jacob Rhoden
 */
public class Row {
    
    /** Creates a new instance of Row */
    public Row() {
    }

    private String[] values;
    
    public void analyseLine(String separator,String line) {
        values=line.split(separator,-1); 
    }

    public String getValue(int i) {
        return values[i];
    }
    
    public int columns() {
        return values.length;
    }
}
