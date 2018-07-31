package de.persosim.simulator.utils;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.persosim.simulator.exception.BitFieldOutOfBoundsException;

/**
 * This class implements a little endian bitfield providing several bitwise
 * logical operations.
 * 
 * @author mboonk
 * 
 */
@XmlRootElement
public class BitField {
	@XmlElement
	boolean[] storedBits;

	public BitField() {
	}

	/**
	 * Creates an empty (all zero bits) {@link BitField} of the given length.
	 * @param numberOfBits
	 */
	public BitField(int numberOfBits){
		storedBits = new boolean[numberOfBits];
	}
	
	/**
	 * Create a {@link BitField} from a big endian ordered byte array.
	 * @param numberOfBits
	 * @param bitsToStore
	 * @return
	 */
	public static BitField buildFromBigEndian(int numberOfBits, byte [] bitsToStore){
		BitField result = new BitField(numberOfBits);
		
		boolean [] sourceBits = new boolean [bitsToStore.length*8];
		
		for (int i = 0; i < sourceBits.length; i++){
			sourceBits[i] = ((bitsToStore[i/8] >>> 7-(i%8)) & 0b00000001) == 1;
		}

		int offset = bitsToStore.length*8 - numberOfBits;
		for (int i = offset; i < sourceBits.length; i++){
			result.setBit(numberOfBits - 1 - (i - offset), sourceBits[i]);
		}
		
		return result;
	}
	
	/**
	 * This constructor takes the given byte array and parses it beginning at
	 * element 0 and the LSB of this element up to the element in which the
	 * numberOfBits-1 bit is contained.
	 * 
	 * @param numberOfBits
	 *            to store in the field
	 * @param bitsToStore
	 *            source data
	 */
	public BitField(int numberOfBits, byte[] bitsToStore) {
		this(numberOfBits);
		for (int i = 0; i < numberOfBits; i++) {

			/*
			 * Access the next byte every 8 bits and mask away any potential
			 * sign. Then shift this byte such that the currently interesting
			 * bit is the LSB. Masking the other 7 bits away provides the value
			 * that is added to the internal representation of the bit field.
			 */
			setBit(i,
					(((bitsToStore[i / 8] & 0xFF) >>> i % 8) & 0b00000001) == 1);
		}
	}

	/**
	 * This constructor takes the given boolean array and parses it beginning at
	 * element 0 interpreted as LSB using the true/false values directly as bit
	 * values 1/0 respectively.
	 * 
	 * @param bitsToStore
	 *            source data
	 */
	public BitField(boolean[] bitsToStore) {
		storedBits = Arrays.copyOf(bitsToStore, bitsToStore.length);
	}

	/**
	 * This methods concatenates the given {@link BitField} with this object.
	 * 
	 * thisObject|field
	 * 
	 * @param field
	 *            to concatenate with
	 * @return the concatenation of this object with
	 */
	public BitField concatenate(BitField field) {
		boolean[] result = new boolean[field.getNumberOfBits()
				+ getNumberOfBits()];

		System.arraycopy(storedBits, 0, result, 0, getNumberOfBits());
		System.arraycopy(field.storedBits, 0, result, getNumberOfBits(), field.getNumberOfBits());

		return new BitField(result);
	}

	/**
	 * Calculate an bitwise or over this and the given {@link BitField}.
	 * 
	 * @param field
	 * @return a new {@link BitField} containing the result
	 */
	public BitField or(BitField field) {
		boolean[] result = new boolean[Math.max(getNumberOfBits(), field.getNumberOfBits())];

		for (int i = 0; i < result.length; i++) {
			result[i] = getZeroPaddedBit(i) | field.getZeroPaddedBit(i);
		}

		return new BitField(result);
	}

	/**
	 * Calculate an bitwise and over this and the given {@link BitField}.
	 * 
	 * @param field
	 * @return a new {@link BitField} containing the result
	 */
	public BitField and(BitField field) {
		boolean[] result = new boolean[Math.max(getNumberOfBits(), field.getNumberOfBits())];

		for (int i = 0; i < result.length; i++) {
			result[i] = getZeroPaddedBit(i) & field.getZeroPaddedBit(i);
		}

		return new BitField(result);
	}

	private boolean getZeroPaddedBit(int index) {
		if (index >= storedBits.length) {
			return false;
		}
		return storedBits[index];
	}

	public int getNumberOfBits() {
		return storedBits.length;
	}

	public boolean getBit(int index) {
		if (0 <= index && index < storedBits.length) {
			return storedBits[index];
		} else {
			throw new BitFieldOutOfBoundsException();
		}
	}
	
	/**
	 * This method creates a new instance with one flipped bit.
	 * @param index the bit to flip
	 * @return
	 */
	public BitField flipBit(int index){
		BitField result = new BitField(this.storedBits);
		result.setBit(index, !result.getBit(index));
		return result;
	}
	
	private void setBit(int index, boolean value) {
		if (0 <= index && index < storedBits.length) {
			storedBits[index] = value;
		} else {
			throw new BitFieldOutOfBoundsException();
		}
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(storedBits);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BitField other = (BitField) obj;
		if (!Arrays.equals(storedBits, other.storedBits))
			return false;
		return true;
	}

	/**
	 * This method creates a byte array representation of the bitfield. It
	 * contains the LSB of the field as the LSB of the element 0, bit 9 as LSB
	 * of element 1 etc.. After the internal bit field is exhausted, missing
	 * bits will be padded to 0.
	 * 
	 * @return
	 */
	public byte[] getAsZeroPaddedByteArray() {
		int length = getNumberOfBits() / 8;
		if (getNumberOfBits() % 8 > 0) {
			length++;
		}
		byte[] result = new byte[length];
		for (int i = 0; i < getNumberOfBits(); i++) {
			result[i / 8] |= (byte) (getBit(i) ? 1 : 0);

			if (!(i == getNumberOfBits() - 1)) {
				result[i / 8] = (byte) (result[i / 8] << 1);
			}

		}
		return result;
	}
}
