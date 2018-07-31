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
package com.extentech.biffviewer.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Maps from record opcodes to the human-readable names from the spec.
 */
public final class OpcodeLookup {
	
	// This class is purely static; prohibit construction.
	private OpcodeLookup() {
		throw new UnsupportedOperationException();
	}
	
	private static final Map<Short,String> recordNames;
	
	static {
		HashMap<Short,String> names = new HashMap<Short,String>();
		
		try {
			BufferedReader reader = new BufferedReader( new InputStreamReader(
					OpcodeLookup.class.getResourceAsStream( "opcodes.txt" ) ) );
			
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				
				int equals = line.indexOf( '=' );
				if (equals == -1) continue;
				
				try {
					names.put(
							Short.valueOf( line.substring( 0, equals ) ),
							line.substring( equals + 1 ) );
				} catch (NumberFormatException e) {}
			}
			
			reader.close();
		} catch (Exception e) {}
		
		recordNames = Collections.unmodifiableMap( names );
	}
	
	public static String getRecordName (short opcode) {
		return recordNames.get( opcode );
	}
}
