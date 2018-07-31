package com.example.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Neil Traft
 */
public final class ArrayUtil {

	////////////////////////////////////////////////////////////////////
	// Non-Instantiable

	private ArrayUtil() {}

	////////////////////////////////////////////////////////////////////
	// Public API

	public static final List<Float> listOf(float[] values) {
		List<Float> list = new ArrayList<Float>();
		for (float v : values) {
			list.add(v);
		}
		return list;
	}

	public static final float[] toArray(List<Float> sorted) {
		float[] values = new float[sorted.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = sorted.get(i);
		}
		return values;
	}

	public static final float[] concat(float[]... arrays) {
		int length = 0;
		for (float[] arr : arrays) {
			length += arr.length;
		}
		float[] all = new float[length];
		int pos = 0;
		for (float[] src : arrays) {
			System.arraycopy(src, 0, all, pos, src.length);
			pos += src.length;
		}
		return all;
	}
}
