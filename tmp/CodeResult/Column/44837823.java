package co.recloud.ariadne.model.logical;

import java.io.Serializable;
import java.util.Map;

public class Column implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1634134394545529756L;
	private Table table;
	private String field;
	
	public Column() {
		this.table = null;
		this.field = null;
	}
	public Column(Table table, String field) {
		this.table = table;
		this.field = field;
	}
	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}
	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table = table;
	}
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	public String toString() {
		if(table != null) {
			return table + "." + field;
		} else {
			return field;
		}
	}
	
	public boolean equals(Object thatObject) {
		if(!(thatObject instanceof Column)) {
			return false;
		}
		Column that = (Column) thatObject;
		return (this.table.equals(that.getTable()) && this.field.equals(that.getField()));
	}
	
	public int hashCode() {
		return (this.toString()).hashCode();
	}
	
	public static Column parseColumn(String columnString, Table baseTable, Map<String, Table> aliasMap) {
		Column column = new Column();
		if(columnString.contains(".")) {
			String[] tokens = columnString.split("\\.");
			Table table = null;
			if(aliasMap != null && aliasMap.containsKey(tokens[0])) {
				table = aliasMap.get(tokens[0]);
			} else {
				table = Table.parseTable(tokens[0], null);
			}
			column.setTable(table);
			column.setField(tokens[1]);
		} else {
			column.setTable(baseTable);
			column.setField(columnString);
		}
		return column;
	}
}
