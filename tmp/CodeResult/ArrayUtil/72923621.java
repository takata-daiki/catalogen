package de.dennis_boldt.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

/**
 * Utils for arrays
 *
 * @author Dennis Boldt
 *
 */
public class ArrayUtil {

	/**
	 * Concatenates two arrays
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> T[] concat(T[] first, T[] second) {
		// @see: http://stackoverflow.com/a/784842/605890
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * Concatenates two arrays
	 *
	 * @param byteArray1
	 * @param byteArray2
	 * @return
	 */
	public static byte[] concat(final byte[] byteArray1, final byte[] byteArray2) {
		ByteBuffer b = ByteBuffer.allocate(byteArray1.length + byteArray2.length);
		b.put(byteArray1);
		b.put(byteArray2);
		return b.array();
	}

	/**
	 * Concatenates two arrays
	 *
	 * @param byteArray1
	 * @param byteArray2
	 * @return
	 */
	public static int[] concat(final int[] byteArray1, final int[] byteArray2) {
		Integer[] result = ArrayUtil.concat(ArrayUtils.toObject(byteArray1), ArrayUtils.toObject(byteArray2));
		return ArrayUtils.toPrimitive(result);
	}

	/**
	 * Concatenates two arrays
	 *
	 * @param byteArray1
	 * @param byteArray2
	 * @return
	 */
	public static short[] concat(final short[] byteArray1, final short[] byteArray2) {
		Short[] result = ArrayUtil.concat(ArrayUtils.toObject(byteArray1), ArrayUtils.toObject(byteArray2));
		return ArrayUtils.toPrimitive(result);
	}

	/**
	 * Concatenates two arrays
	 *
	 * @param byteArray1
	 * @param byteArray2
	 * @return
	 */
	public static boolean[] concat(final boolean[] byteArray1, final boolean[] byteArray2) {
		Boolean[] result = ArrayUtil.concat(ArrayUtils.toObject(byteArray1), ArrayUtils.toObject(byteArray2));
		return ArrayUtils.toPrimitive(result);
	}

	/**
	 * Gets the subarray of an array
	 *
	 * @param byteArray
	 * @param startIndexInclusive
	 * @param endIndexExclusive
	 * @return
	 */
	public static byte[] subarray(final byte[] byteArray, int startIndexInclusive, int endIndexExclusive) {
		return ArrayUtils.subarray(byteArray, startIndexInclusive, endIndexExclusive);
		//byte [] subArray = Arrays.copyOfRange(a, 4, 6);
	}

	// TODO: subarray for T[] and other primitive types

	/**
	 * Does:
	 * {"a", "b", "c"} -> "a,b,c" or
	 * {1, 2, 3} -> "1,2,3"
	 *
	 * @param array
	 * @param separator
	 * @return
	 */
	public static <T> String join(T[] array, String separator) {
	    String out = "";
	    for(int i=0; i<array.length; i++) {
	        if(i!=0) { out += separator; }
	        out += array[i].toString();
	    }
	    return out;
	}

	/**
	 * Checks, if an element is in an array
	 *
	 * @param array
	 * @param needle
	 * @return
	 */
	public static <T> boolean contains(T[] array, T needle) {
	    for(int i=0;i<array.length;i++) {
	        if(array[i].equals(needle)) {
	            return true;
	        }
	    }
	    return false;
	}

	/**
	 * Converts an array to a list
	 *
	 * @param array an array
	 * @return the array as a list
	 */
	public static <T> List<T> toList(T[] array) {
		return Arrays.asList(array);
	}

}
