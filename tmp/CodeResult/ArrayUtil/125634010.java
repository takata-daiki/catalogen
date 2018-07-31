/**
 * S.L.'s utility classes. Copyright(c) 2009.
 */
package com.auxsms.utils.web;

/**
 * Type <code>{@link ArrayUtil}</code> represents utility methods for handling arrays .
 * 
 * @author S.L.
 */
public final class ArrayUtil {

    /**
     * Returns true if array is null or is empty (size = 0).
     * 
     * @param c collection
     * @return boolean flag
     */
    public static <T> boolean isEmpty(T... array) {
        return (null == array) || 0 == array.length;
    }

    /**
     * Checks whether the array contains a value.
     * 
     * @param <T> type
     * @param values array
     * @param value value
     * @return boolean flag
     */
    public static <T> boolean arrayContains(T[] values, T value) {
        for (T t : values) {
            if (value.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if array in not empty and has no null elements.
     * 
     * @param <T> element type
     * @param array array
     * @return boolean flag
     */
    public static <T> boolean isFilled(T... array) {
        if (isEmpty(array)) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (null == array[i]) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------
    // --- P R I V A T E ------------------------
    // ------------------------------------------

    /**
     * Constructor for <code>{@link ArrayUtil}</code> type.
     */
    private ArrayUtil() {
    }

}
