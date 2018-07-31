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

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.extentech.toolkit.ByteTools;

public class BinaryViewer
extends JPanel {
	private static final long serialVersionUID = -8463013614773117415L;

	private JTextArea text;
	
	public BinaryViewer() {
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		
		text = new JTextArea();
		text.setFont( Font.decode( "Monospaced" ) );
		text.setColumns( 62 );
		add( text );
	}
	
	public void setData( byte[] data ) {
		text.setText( ByteTools.getByteDump( data, 0 ) );
	}
}
