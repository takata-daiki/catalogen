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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import com.extentech.biffviewer.model.WorkBook;

public class BiffViewer {
	private JFrame frame;
	private JTabbedPane tabs;
	private JFileChooser fileChooser;
	
	private Action actFileOpen = new AbstractAction( "Open" ) {
		public void actionPerformed( ActionEvent event ) {
			int result = fileChooser.showOpenDialog( frame );
			if (result != JFileChooser.APPROVE_OPTION) return;
			fileOpen( fileChooser.getSelectedFile() );
		}
	};
	
	private Action actFileClose = new AbstractAction( "Close" ) {
		public void actionPerformed( ActionEvent event ) {
			fileClose();
		}
	};
	
	private Action actExit = new AbstractAction( "Exit" ) {
		public void actionPerformed( ActionEvent event ) {
			fileExit();
		}
	};
	
	/** Listens for events occurring on the application's main window. */
	private WindowListener windowListener = new WindowAdapter() {
		public void windowClosing (WindowEvent event) {
			fileExit();
		}
	};
	
	/** Listens for changes to the active file and updates the UI. */
	private ChangeListener tabSelectionListener = new ChangeListener() {
		public void stateChanged (ChangeEvent event) {
			Component comp = tabs.getSelectedComponent();
			if (comp == null || !( comp instanceof WorkBookPanel )) {
				actFileClose.setEnabled( false );
				return;
			}
			
			//WorkBookPanel book = (WorkBookPanel) comp;
			
			actFileClose.setEnabled( true );
		}
	};
	
	public BiffViewer() {
		frame = new JFrame( "ExtenXLS BIFF Viewer" );
		frame.addWindowListener( windowListener );
		frame.getContentPane().setLayout( 
				new BoxLayout( frame.getContentPane(), BoxLayout.X_AXIS ) );
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar( menuBar );
		
		JMenu menu = new JMenu( "File" );
		menuBar.add( menu );
		
		actFileClose.setEnabled( false );
		
		menu.add( actFileOpen );
		menu.add( actFileClose );
		menu.addSeparator();
		menu.add( actExit );
		
		tabs = new JTabbedPane();
		frame.add( tabs );
		tabs.addChangeListener( tabSelectionListener );
		
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter( new FileFilter() {
				public boolean accept (File file) {
					// Allow directory browsing
					if (file.isDirectory()) return true;
					
					// Filter files by extension
					String name = file.getName();
					String ext = name.substring( name.length() - 4 );
					return ext.equalsIgnoreCase( ".xls" ) 
						|| ext.equalsIgnoreCase( ".xlt" );
				}
		
				public String getDescription() {
					return "BIFF8 Excel Spreadsheets (*.xls, *.xlt)";
				}
			
			});
		
		frame.setSize( new Dimension( 500, 600 ) );
		frame.setVisible( true );
	}
	
	public void fileOpen (File file) {
		WorkBook book;
		
		try {
			book = new WorkBook( file );
			book.parse();
		} catch (Exception e) {
			showErrorMessage( e, "Error Opening File" );
			return;
		}
		
		WorkBookPanel bookPanel = new WorkBookPanel( book );
		tabs.add( file.getName(), bookPanel );
	}
	
	public void fileClose() {
		WorkBookPanel panel = (WorkBookPanel) tabs.getSelectedComponent();
		tabs.remove( panel );
		try {
			panel.close();
		} catch (IOException e) {}
	}
	
	/** Cleans up any open resources and exits the application. */
	public void fileExit() {
		frame.setVisible( false );
		
		for (Component tab : tabs.getComponents()) try {
			if (tab instanceof WorkBookPanel)
				((WorkBookPanel)tab).close();
		} catch (IOException e) {}
		
		frame.dispose();
		
		System.exit( 0 );
	}
	
	public static void main (String[] args) {
		new BiffViewer();
	}
	
	/** Formats and displays an error message dialog from the given Throwable.
	 */
	private void showErrorMessage (Throwable error, String title) {
		StackTraceElement[] trace = error.getStackTrace();
		StringBuilder message = new StringBuilder();
		
		message.append( error.toString() );
		message.append( "\nat:\n" );
		
		int lastFrame = trace.length - 1;
		while (lastFrame >= 0 && 
				!trace[ lastFrame ].getClassName()
				.startsWith( "com.extentech" ) ) lastFrame--;
		
		if (lastFrame < 0) lastFrame = trace.length - 1;
		
		for (int idx = 0; idx <= lastFrame; idx++) {
			message.append( trace[ idx ].toString() );
			message.append( "\n" );
		}
		
		JOptionPane.showMessageDialog( frame, message.toString(),
				title, JOptionPane.ERROR_MESSAGE );
	}
}
