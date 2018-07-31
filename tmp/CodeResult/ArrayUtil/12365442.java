/*
 * ArrayUtil.java
 */

package xq.util;

/**
 * 
 * @author ThorntonRP
 */
public class ArrayUtil {

	public static byte[] copyOf(byte[] original, int newLength) {
		byte[] result;
		if (newLength < original.length) {
			result = new byte[newLength];
			System.arraycopy(original, 0, result, 0, newLength);
		} else if (newLength > original.length) {
			result = new byte[newLength];
			System.arraycopy(original, 0, result, 0, original.length);
		} else {
			result = original;
		}
		return result;
	}

	public static char[] copyOf(char[] original, int newLength) {
		char[] result;
		if (newLength < original.length) {
			result = new char[newLength];
			System.arraycopy(original, 0, result, 0, newLength);
		} else if (newLength > original.length) {
			result = new char[newLength];
			System.arraycopy(original, 0, result, 0, original.length);
		} else {
			result = original;
		}
		return result;
	}

	private ArrayUtil() {
	}
}
