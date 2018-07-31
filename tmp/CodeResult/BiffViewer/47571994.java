/* --------- BEGIN COPYRIGHT NOTICE ----------
 * Copyright 2011-2012 Extentech Inc
 * Copyright 2013 Infoteria America Corp
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---------- END COPYRIGHT NOTICE ----------
 */
package com.extentech.biffviewer;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.extentech.biffviewer.model.Record;

public class RecordTableModel
implements TableModel {
	public static final int COL_TYPE = 0;
	public static final int COL_SIZE = 1;
	public static final int COL_OFFSET = 2;
	
	private final List<Record> records;
	
	public RecordTableModel (List<Record> records) {
		this.records = records;
	}

	public void addTableModelListener (TableModelListener listener) {
		// The model is static, we don't need listeners. Do nothing.
	}

	public void removeTableModelListener(TableModelListener arg0) {
		// The model is static, we don't need listeners. Do nothing.
	}

	public String getColumnName (int column) {
		switch (column) {
		case COL_TYPE:
			return "Type";
		case COL_SIZE:
			return "Size";
		case COL_OFFSET:
			return "Offset";
		
		default:
			return null;
		}
	}
	
	public Class<?> getColumnClass (int column) {
		switch (column) {
		case COL_TYPE:
			return String.class;
			
		case COL_SIZE:
		case COL_OFFSET:
			return Integer.class;
			
		default:
			return Object.class;
		}
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return records.size();
	}

	public Object getValueAt (int row, int col) {
		Record record = records.get( row );
		
		switch (col) {
		
		case COL_TYPE:
			return record.getName();
		
		case COL_SIZE:
			return record.getLength();
			
		case COL_OFFSET:
			return record.getOffset();
		
		default:
			return null;
		}
	}

	public boolean isCellEditable (int row, int col) {
		return false;
	}

	public void setValueAt (Object value, int row, int col) {
		throw new UnsupportedOperationException( "this model is immutable" );
	}

}
