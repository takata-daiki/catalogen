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

public class InvalidRecordException
extends RuntimeException {
	private static final long serialVersionUID = 6409352421750118321L;

	private final int position;
	
	public InvalidRecordException( int position, String message ) {
		super( message );
		this.position = position;
	}
	
	public String toString() {
		return "Invalid record at 0x" + Integer.toHexString( position )
				+ ": " + getMessage();
	}
}
