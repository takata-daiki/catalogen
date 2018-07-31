/*
 * Copyright (c) 1999-2004 Sourceforge JACOB Project.
 * All rights reserved. Originator: Dan Adler (http://danadler.com).
 * Get more information about JACOB at http://sourceforge.net/projects/jacob-project
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.racob.com;

import java.math.BigDecimal;
//import java.math.BigInteger;
import java.util.Date;

/**
 * The multi-format data type used for all call backs and most communications
 * between Java and COM. It provides a single class that can handle all data
 * types.
 * <p>
 * Just loading this class creates 3 variants that get added to the ROT
 * <p>
 * PROPVARIANT introduces new types so eventually Variant will need to be
 * upgraded to support PropVariant types.
 * http://blogs.msdn.com/benkaras/archive/2006/09/13/749962.aspx
 * <p>
 * This object no longer implements Serializable because serialization is broken
 * (and has been since 2000/xp). The underlying marshalling/unmarshalling code
 * is broken in the JNI layer.
 */
public class Variant {    
    /** Use this constant for optional parameters */
    public final static Variant DEFAULT;
    /** Same than {@link #DEFAULT} */
    public final static Variant VT_MISSING;
    /** Use for true/false variant parameters */
    public final static Variant VT_TRUE = new Variant(true, false);
    /** Use for true/false variant parameters */
    public final static Variant VT_FALSE = new Variant(false, false);
    /** variant's type is empty : equivalent to VB Nothing and VT_EMPTY */
    public final static short VariantEmpty = 0;
    /** variant's type is null : equivalent to VB Null and VT_NULL */
    public final static short VariantNull = 1;
    /** variant's type is short VT_I2 */
    public final static short VariantShort = 2;
    /** variant's type is int VT_I4, a Long in VC */
    public final static short VariantInt = 3;
    /** variant's type is float VT_R4 */
    public final static short VariantFloat = 4;
    /** variant's type is double VT_R8 */
    public final static short VariantDouble = 5;
    /** variant's type is currency VT_CY */
    public final static short VariantCurrency = 6;
    /** variant's type is date VT_DATE */
    public final static short VariantDate = 7;
    /** variant's type is string also known as VT_BSTR */
    public final static short VariantString = 8;
    /** variant's type is dispatch VT_DISPATCH */
    public final static short VariantDispatch = 9;
    /** variant's type is error VT_ERROR */
    public final static short VariantError = 10;
    /** variant's type is boolean VT_BOOL */
    public final static short VariantBoolean = 11;
    /** variant's type is variant it encapsulate another variant VT_VARIANT */
    public final static short VariantVariant = 12;
    /** variant's type is object VT_UNKNOWN */
    public final static short VariantObject = 13;
    /** variant's type is object VT_DECIMAL */
    public final static short VariantDecimal = 14;
    // VT_I1 = 16
    /** variant's type is byte VT_UI1 */
    public final static short VariantByte = 17;
    // VT_UI2 = 18
    public final static short VariantUnsignedShort = 18;
    // VT_UI4 = 19;
    public final static short VariantUnsignedLong = 19;

    /**
     * variant's type is 64 bit long integer VT_I8 - not yet implemented in
     * Jacob because we have to decide what to do with Currency and because its
     * only supported on XP and later. No win2k, NT or 2003 server.
     */
    public final static short VariantLongInt = 20;
    // VT_UI8 = 21
    public final static short VariantUnsignedInt = 21;
    // VT_INT = 22
    // VT_UNIT = 23
    // VT_VOID = 24
    // VT_HRESULT = 25
    /**
     * This value is for reference only and is not to be used by any callers
     */
    public final static short VariantPointer = 26;
    // VT_SAFEARRAY = 27
    // VT_CARRARY = 28
    // VT_USERDEFINED = 29
    /** what is this? VT_TYPEMASK && VT_BSTR_BLOB */
    public final static short VariantTypeMask = 4095;
    /** variant's type is array VT_ARRAY */
    public final static short VariantArray = 8192;
    /** variant's type is a reference (to IDispatch?) VT_BYREF */
    public final static short VariantByref = 16384;


    public static final int DISP_E_PARAMNOTFOUND = new Integer(0x80020004);

    private static boolean initialized;

    /*
     * Do the run time definition of DEFAULT and MISSING. Have to use static
     * block because of the way the initialization is done via two calls instead
     * of just a constructor for this type.
     */
    static {
        initialize();
        Variant vtMissing = new Variant(DISP_E_PARAMNOTFOUND, VariantError, false);
        DEFAULT = vtMissing;
        VT_MISSING = vtMissing;
    }

    private static native void initializeNative();

    public static void initialize() {
        if (!initialized) {
            initialized = true;
            initializeNative();
        }
    }

    // Is V_VT(v) in C or manually passed if going from Java to VARIANT
    private short type;
    private Object value;

    public Variant(Object value, short vt) {
        this.value = value;
        this.type = vt;
    }

    /** Generic constructor */
    public Variant(Object value, short type, boolean byRef) {
        this(value, (short) (type | (byRef ? VariantByref : 0)));
    }

    /** Constructor that sets type to VariantEmpty */
    public Variant() {
        this(null, VariantEmpty, false);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(boolean in, boolean byRef) {
        this(new Boolean(in), VariantBoolean, byRef);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(boolean in) {
        this(new Boolean(in), VariantBoolean, false);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(byte in, boolean byRef) {
        this(new Byte(in), VariantByte, byRef);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(byte in) {
        this(new Byte(in), VariantByte, false);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(double in, boolean byRef) {
        this(new Double(in), VariantDouble, byRef);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(double in) {
        this(new Double(in), VariantDouble, false);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(float in, boolean byRef) {
        this(new Float(in), VariantFloat, byRef);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(float in) {
        this(new Float(in), VariantFloat, false);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(short in, boolean byRef) {
        this(new Short(in), VariantShort, byRef);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(short in) {
        this(new Short(in), VariantShort, false);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(int in, boolean byRef) {
        this(new Integer(in), VariantInt, byRef);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(int in) {
        this(new Integer(in), VariantInt, false);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(long in, boolean byRef) {
        this(new Long(in), VariantLongInt, byRef);
    }

    /** Constructor that accepts a primitive rather than an object */
    public Variant(long in) {
        this(new Long(in), VariantLongInt, false);
    }

    public Variant(BigDecimal decimal, boolean byRef) {
        this(decimal, VariantDecimal, byRef);
    }

   public Variant(BigDecimal decimal) {
        this(decimal, VariantDecimal, false);
    }

    public Variant(Currency currency, boolean byRef) {
        this(currency, VariantLongInt, byRef);
    }

    public Variant(Currency currency) {
        this(currency, VariantLongInt, false);
    }

    public Variant(Dispatch dispatch, boolean byRef) {
        this(dispatch, VariantDispatch, byRef);
    }

    public Variant(Dispatch dispatch) {
        this(dispatch, VariantDispatch, false);
    }

    public Variant(Date date, boolean byRef) {
        this(date, VariantDate, byRef);
    }

    public Variant(Date date) {
        this(date, VariantDate, false);
    }

    public Variant(SafeArray array, boolean byRef) {
        this(array, VariantArray, byRef);
    }

    public Variant(SafeArray array) {
        this(array, VariantArray, false);
    }

    public Variant(String string, boolean byRef) {
        this(string, VariantString, byRef);
    }

    public Variant(String string) {
        this(string, VariantString, false);
    }

    public Variant(Variant variant, boolean byRef) {
        this(variant, VariantVariant, byRef);
    }

    public Variant(Variant variant) {
        this(variant, VariantVariant, false);
    }

    private void illegal(String methodName, String variantName) {
        throw new IllegalStateException(methodName +
                 "() only legal on Variants of type " + variantName + " not " + 
                 getvt());
    }

    public boolean isA(short testType) {
        return type == testType || type == (testType | VariantByref);
    }

    public boolean isArray() {
        return (type & VariantArray) != 0;
    }

    public boolean isByref() {
        return (type & VariantByref) != 0;
    }

    public short getvt() {
        return type;
    }

    public short getType() {
        return (short) (type & ~VariantByref);
    }
    
    public Object getValue() {
        return value;
    }

    public static Variant createDispatchVariant(int pointer) {
        // No point making a variant that has an invalid pointer
        if (pointer == 0) return null;

        return new Variant(new Dispatch(pointer));
    }

    public static Variant createDateVariant(double comDateValue) {
        return new Variant(DateUtilities.convertWindowsTimeToDate(comDateValue));
    }

    public static Variant createIntVariant(int value) {
        return new Variant(new Integer(value));
    }

    /**
     * Cover for native method so we can cover it.
     * <p>
     * This cannot convert an object to a byRef. It can convert from byref to
     * not byref
     *
     * @param in type to convert this variant too
     * @return Variant returns this same object so folks can change when
     *         replacing calls toXXX() with changeType().getXXX()
     */
    public Variant changeType(short in) {
        this.type = in;
        // FIXME:  This needs some round tripping to make sure it is valid
        return this;
    }

    public SafeArray getArray() {
        if (!isArray()) illegal("getArray", "VariantArray");
        return (SafeArray) value;
    }

    /**
     * @return returns the value as a boolean, throws an exception if its not.
     * @throws IllegalStateException if variant is not of the requested type
     */
    public boolean getBoolean() {
        if (!isA(VariantBoolean)) illegal("getBoolean", "VariantBoolean");
        return ((Boolean) value).booleanValue();
    }

    /**
     * @return returns the value as a boolean, throws an exception if its not.
     * @throws IllegalStateException if variant is not of the requested type
     */
    public byte getByte() {
        if (!isA(VariantByte)) illegal("getByte", "VariantByte");
        return ((Byte) value).byteValue();
    }

    /**
     * MS Currency objects are 64 bit fixed point numbers with 15 digits to the
     * left and 4 to the right of the decimal place.
     *
     * @return returns the currency value
     * @throws IllegalStateException if variant is not of the requested type
     */
    public Currency getCurrency() {
        if (!isA(VariantCurrency)) illegal("getCurrency", "VariantCurrency");
        return (Currency) value;
    }

    /**
     * @return the noughgat that JNI side wants versus full blown object.
     */
    public long getCurrencyAsLong() {
        return getCurrency().longValue();
    }

    /**
     * @return the date
     * @throws IllegalStateException if variant is not of the requested type
     */
    public Date getDate() {
        if (!isA(VariantDate)) illegal("getDate", "VariantDate");
        return (Date) value;
    }

     /**
     * @return the noughgat that JNI side wants versus full blown object.
     */
    public double getDateAsDouble() {
       return DateUtilities.convertDateToWindowsTime(getDate());
    }

    /**
     * @return the BigDecimal for this Decimal
     * @throws IllegalStateException if variant is not of the requested type
     */
    public BigDecimal getDecimal() {
        if (!isA(VariantDecimal)) illegal("getDecimal", "VariantDecimal");
        return (BigDecimal) value;
    }

    /**
     * @return this object as a dispatch
     * @throws IllegalStateException if wrong variant type
     */
    public Dispatch getDispatch() {
        if (!isA(VariantDispatch)) illegal("getDispatch", "VariantDispatch");
        return (Dispatch) value;
    }

    public int getDispatchPointer() {
        return getDispatch().pointer.get();
    }

    /**
     * @return the double value
     * @throws IllegalStateException if variant is not of the requested type
     */
    public double getDouble() {
        if (!isA(VariantDouble)) illegal("getDouble", "VariantDouble");
        return ((Double) value).doubleValue();
    }

    /**
     * @return the error value 
     * @throws IllegalStateException if variant is not of the requested type
     */
    public int getError() {
        if (!isA(VariantError)) illegal("getError", "VariantError");
        return ((Integer) value).intValue();
    }

    /**
     * @return returns the value as a float if the type is of type float
     * @throws IllegalStateException if variant is not of the requested type
     */
    public float getFloat() {
        if (!isA(VariantFloat)) illegal("getFloat", "VariantFloat");
        return ((Float) value).floatValue();
    }

    /**
     * return the int value held in this variant if it is an int or a short.
     * Throws for other types.
     *
     * @return int contents of the windows memory
     * @throws IllegalStateException
     *             if variant is not of the requested type
     */
    public int getInt() {
        if (isA(VariantUnsignedInt)) return ((Integer) value).intValue();
        if (isA(VariantInt)) return ((Integer) value).intValue();
        if (isA(VariantShort)) return ((Short) value).shortValue();
        illegal("getInt", "VariantInt");
        return -1; // not reached
    }

    /**
     * returns the windows time contained in this Variant to a Java Date. should
     * return null if this is not a date Variant SF 959382
     *
     * @return java.util.Date returns the date if this is a VariantDate != 0,
     *         null if it is a VariantDate == 0 and throws an
     *         IllegalStateException if this isn't a date.
     * @throws IllegalStateException
     *             if variant is not of the requested type
     */
    @Deprecated
    public Date getJavaDate() {
        // returnDate = DateUtilities.convertWindowsTimeToDate(getDate());
        return getDate();
    }

    /**
     * 64 bit Longs only available on x64. 64 bit long support added 1.14
     *
     * @return returns the value as a long
     * @throws IllegalStateException if variant is not of the requested type
     */
    public long getLong() {
        if (!isA(VariantLongInt) && !(isA(VariantUnsignedLong))) illegal("getLong", "VariantLongInt");
        return ((Long) value).longValue();
    }

    public SafeArray getSafeArray() {
        return (SafeArray) value;
    }

    // FIXME: Figure out what boolean field is for.
    @Deprecated
    public SafeArray toSafeArray(boolean something) {
        return getSafeArray();
    }

    /**
     * @return the short value
     * @throws IllegalStateException if variant is not of the requested type
     */
    public short getShort() {
        if (!isA(VariantShort) && !isA(VariantUnsignedShort)) illegal("getShort", "VariantShort");
        return ((Short) value).shortValue();
    }

    /**
     * @return string contents of the variant.
     * @throws IllegalStateException if this variant is not of type String
     */
    public String getString() {
        if (!isA(VariantString)) illegal("getString", "VariantString");
        return (String) value;
    }

    /**
     * Used to get the value from a windows type of VT_VARIANT or a jacob
     * Variant type of VariantVariant. Added 1.12 pre 6 - VT_VARIANT support is
     * at an alpha level
     *
     * @return Object a java Object that represents the content of the enclosed
     *         Variant
     */
    public Variant getVariant() {
        if (!isA(VariantVariant) || !isByref()) illegal("getVariant", "VariantVariant");
        return (Variant) value;
    }

    /**
     * @return returns true if the variant is considered null
     */
    public boolean isNull() {
        // ENEBO: This is radically simpler than Jacob's old way and may
        // include more values.
        return value == null;
    }

    /**
     * Convert a Racob Variant value to a Java object.
     *
     * @return Java equivalent version of Variant value.
     */
    public Object toJavaObject() {
        return VariantUtilities.variantToObject(this);
    }

    public String toDebugString() {
        StringBuilder buf = new StringBuilder("Variant: ");

        switch (getType()) {
            case VariantEmpty:
                buf.append("Empty"); break;
            case VariantNull:
                buf.append("Null"); break;
            case VariantShort:
                buf.append("Short [" + value + "]"); break;
            case VariantInt:
                buf.append("Int [" + value + "]"); break;
            case VariantFloat:
                buf.append("Float [" + value + "]"); break;
            case VariantDouble:
                buf.append("Double [" + value + "]"); break;
            case VariantCurrency:
                buf.append("Currency [" + value + "]"); break;
            case VariantDate:
                buf.append("Date [" + value + "]"); break;
            case VariantString:
                buf.append("String [" + value + "]"); break;
            case VariantDispatch:
                buf.append("Dispatch [" + value + "]"); break;
            case VariantError:
                buf.append("Error [" + value + "]"); break;
            case VariantBoolean:
                buf.append("Boolean [" + value + "]"); break;
            case VariantVariant:
                buf.append("Variant [" + value + "]"); break;
            case VariantObject:
                buf.append("Object [" + value + "]"); break;
            case VariantDecimal:
                buf.append("Decimal [" + value + "]"); break;
            case VariantByte:
                buf.append("Byte [" + value + "]"); break;
            case VariantLongInt:
                buf.append("LongInt [" + value + "]"); break;
            case VariantPointer:
                buf.append("Pointer [" + value + "]"); break;
            case VariantTypeMask:
                buf.append("TypeMask [" + value + "]"); break;
            case VariantArray:
                buf.append("Array [" + value + "]"); break;
            case VariantByref:
                buf.append("ByRef???"); break;
            default:
                buf.append("Uknown type: ").append(type);
                break;
        }

        if (isByref()) buf.append("<BYREF>");

        return buf.toString();
    }

    /**
     * A String representation of the variant if possible.  It will return
     * "null" if VariantEmpty, VariantError, or VariantNull.
     *
     * @return a reasonable string representation
     */
    @Override
    public String toString() {
        int vt = getvt();

        if (vt == VariantEmpty || vt == VariantError || vt == VariantNull) return "null";
        if (vt == VariantString) return getString();

        try {
            Object foo = toJavaObject();
            // rely on java objects to do the right thing
            if (foo == null) return "{Java null}";

            return foo.toString();
        } catch (RuntimeException e) {
            // some types do not generate a good description yet
            return "Description not available for type: " + getvt();
        }
    }
}
