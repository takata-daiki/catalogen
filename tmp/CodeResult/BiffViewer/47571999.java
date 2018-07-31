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

import java.util.Arrays;

import com.extentech.formats.LEO.Block;
import com.extentech.formats.LEO.BlockByteConsumer;
import com.extentech.formats.LEO.BlockByteReader;

/** Represents an individual BIFF record.
 */
public class Record
implements BlockByteConsumer {
	private final short opcode, length;
	private int offset, lastBlock, firstBlock;
	private Block[] blocks;
	private BlockByteReader reader;
	
	public Record (short opcode, short length) {
		this.opcode = opcode;
		this.length = length;
	}
	
	public short getOpcode() {
		return opcode;
	}
	
	public String getName() {
		return OpcodeLookup.getRecordName( opcode );
	}
	
	public byte[] getContents() {
		return reader.get( this, 0, length );
	}
	
	public boolean equals (Object obj) {
		if (obj == this) return true;
		if (obj instanceof Record) {
			Record that = (Record) obj;
			return that.opcode == this.opcode
					&& that.length == this.length 
					&& Arrays.equals( that.getContents(), this.getContents() );
		}
		return false;
	}
	
	public int hashCode() {
		int hashCode = 1;
		hashCode = 31 * hashCode + opcode;
		hashCode = 31 * hashCode + Arrays.hashCode( getContents() );
		return hashCode;
	}

	public void setOffset(int pos) {
		offset = pos;
	}

	public int getOffset() {
		return offset;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[] myblocks) {
		blocks = myblocks;
	}

	public void setFirstBlock(int i) {
		firstBlock = i;
	}

	public void setLastBlock(int i) {
		lastBlock = i;
	}

	public int getFirstBlock() {
		return firstBlock;
	}

	public int getLastBlock() {
		return lastBlock;
	}

	public int getLength() {
		return length;
	}

	public void setByteReader(BlockByteReader db) {
		reader = db;
	}

	public BlockByteReader getByteReader() {
		return reader;
	}
}
