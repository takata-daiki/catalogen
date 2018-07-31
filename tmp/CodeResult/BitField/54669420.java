/**
 *
 * @author Thade O'Connor <thade.oconnor@gmail.com>
 * Copyright (c) 2010, Thaddeus O'Connor
 *	All rights reserved.
 *
 *	Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following
 *  conditions are met:
 *
 *	 - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 *	 - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *	 - Neither the name of the organisation nor the names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *	THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 *  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 *  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */


package rush.util;

import java.util.ArrayList;
import java.util.Arrays;


public class Bitfield {

	//bit masks to pick out each bit bit from a byte- [0] for left-most bit (msb), [7] for right-most bit (lsb).
	private static byte[] mask =
	{
		(byte)0x80,
		(byte)0x40,
		(byte)0x20,
		(byte)0x10,
		(byte)0x08,
		(byte)0x04,
		(byte)0x02,
		(byte)0x01
	};

	private byte[] data;
	private int size;
	public Bitfield(int size)
	{
		this.size = size;
		int numbytes = (int) Math.ceil( (((double)size) / 8.0) );
		data = new byte[numbytes];
	}

	private Bitfield(byte[] array, int size)
	{
		this.size = size;
		this.data = array;
	}

	public int size()
	{
		return size;
	}

	public byte[] getBytes()
	{
		return data;
	}

	public synchronized boolean get(int index)
	{
		if(index >= size)
			return false;

		int byte_index = index / 8;
		int bit_index = index % 8;
		return ((data[byte_index] & mask[bit_index]) != 0);
	}

	public void set(int index, boolean value)
	{
		if(index >= size)
			return;

		int byte_index = index / 8;
		int bit_index = index % 8;
		if(value)
		{
			data[byte_index] |= mask[bit_index];
		}
		else
		{
			data[byte_index] ^= mask[bit_index];
		}
	}

	public int numSet()
	{
		int num = 0;
		for(int i = 0; i < this.size; i++)
		{
			if(this.get(i))
				num++;
		}
		return num;
	}

	public ArrayList<Integer> getInterested(Bitfield other)
	{
		ArrayList<Integer> ints = new ArrayList<Integer>();
		if(this.size != other.size())
			return ints;

		for(int i = 0; i < this.size; i++)
		{
			if(!this.get(i))
			{
				if(other.get(i))
				{
					ints.add(i);
				}
			}
		}

		return ints;
	}

	public static Bitfield fromByteArray(byte[] array, int size)
	{
		//creates a Bitfield from an existing byte array and size.
		//returns null if the size doesn't match, or any of the
		//extra bits are set.

		int numbytes = (int) Math.ceil( (((double)size) / 8.0) );
		if(numbytes != array.length)
		{
			return null;
		}

		int bit_index = size % 8;
		while( ( bit_index > 0 ) && ( bit_index < 8 ) )
		{
			if( ( array[numbytes-1] & mask[bit_index] ) != 0 )
			{
				return null;
			}
			bit_index++;
		}

		return new Bitfield(array, size);
	}

	@Override
	public Bitfield clone()
	{
		return new Bitfield(Arrays.copyOf(this.data, this.data.length), this.size);
	}

}
