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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.extentech.formats.LEO.BlockByteReader;
import com.extentech.formats.LEO.LEOFile;
import com.extentech.formats.XLS.XLSConstants;
import com.extentech.toolkit.ByteTools;

/** Represents a BIFF8 file and handles stream-level parsing.
 */
public class WorkBook
implements Closeable {
	private final LEOFile file;
	private final BlockByteReader reader;
	private int position;
	private List<Record> records;
	
	/** Creates a new WorkBook parsing the file at the given path.
	 * @param path the file to parse
	 * @throws IOException if an unexpected IO error occurs while reading
	 */
	public WorkBook (File path) {
		file = new LEOFile( path.getAbsolutePath() );
		reader = file.getXLSBlockBytes();
		position = 0;
	}
	
	/** Gets the next record in the stream.
	 * @throws InvalidRecordException if the record header is corrupt
	 * @return the next Record in the stream
	 *         or null if the end of the stream has been reached
	 */
	public Record next() {
		if (position >= reader.getLength()) return null;
		
		byte[] header = reader.getHeaderBytes( position );
		short opcode = ByteTools.readShort( header[ 0 ], header[ 1 ] );
		short length = ByteTools.readShort( header[ 2 ], header[ 3 ]);
		
		if (length < 0)
			throw new InvalidRecordException( position, "negative length" );
		
		if (length > XLSConstants.MAXRECLEN)
			throw new InvalidRecordException( position, "record too long (was "
					+ length + ", max " + XLSConstants.MAXRECLEN + ")" );
		
		if (position + length > reader.getLength())
			throw new InvalidRecordException( position,
					"record runs past end of file" );
		
		Record record = new Record( opcode, length );
		record.setByteReader( reader );
		record.setOffset( position );
		
		position += length + 4;
		
		return record;
	}
	
	/** Parses the entire workbook into the record list.
	 * @throws IllegalStateException if the workbook is already parsed
	 * @throws InvalidRecordException if the workbook contains corrupt records
	 * @see #getRecords()
	 */
	public void parse() {
		if (records != null) throw new IllegalStateException(
				"workbook has already been parsed" );
		if (position != 0) throw new IllegalStateException(
				"parsing has already been started in pull mode" );
		
		records = new ArrayList<Record>();
		
		while (true) {
			Record record = next();
			if (record == null) break;
			records.add( record );
		}
		
		records = Collections.unmodifiableList( records );
	}
	
	/** Gets the list of records parsed by {@link #parse()}.
	 */
	public List<Record> getRecords() {
		return records;
	}
	
	/** Releases system resources associated with this workbook. */
	public void close()
	throws IOException {
		file.close();
	}
}
