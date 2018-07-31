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

import java.io.Closeable;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.extentech.biffviewer.model.WorkBook;

public class WorkBookPanel
extends JPanel
implements Closeable {
	private static final long serialVersionUID = -3918013042174649909L;

	private final WorkBook book;
	
	private final JTable table;
	private final JScrollPane tableScroll;
	private final BinaryViewer view;
	
	public WorkBookPanel( WorkBook workbook ) {
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		
		book = workbook;
		
		RecordTableModel model = new RecordTableModel( book.getRecords() );
		table = new JTable( model );
		table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
//		table.setShowVerticalLines( false );
		table.setShowGrid( false );
		table.setAutoResizeMode( JTable.AUTO_RESIZE_NEXT_COLUMN );
		table.setColumnSelectionAllowed( false );
		
		tableScroll = new JScrollPane( table );
		
		view = new BinaryViewer();
		
		JSplitPane split = new JSplitPane( 
				JSplitPane.HORIZONTAL_SPLIT, tableScroll, view );
		
		this.add( split );
		
		table.getSelectionModel().addListSelectionListener(
				recordSelectionListener );
	}
	
	private ListSelectionListener recordSelectionListener =
		new ListSelectionListener() {
			public void valueChanged (ListSelectionEvent event) {
				if (event.getValueIsAdjusting()) return;
				int row = table.getSelectedRow();
				if (row == -1) return;
				view.setData( book.getRecords().get( row ).getContents() );
			}
		};
		
	/** Releases system resources associated with this workbook. */
	public void close()
	throws IOException {
		book.close();
	}
}
