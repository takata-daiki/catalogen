/**
 * LittleEndianOutputStream
 *
 * David Finlayson, Ph.D.
 * Operational Geologist
 * Western Coastal and Marine Geology
 * U.S. Geological Survey
 * Pacific Science Center
 * 400 Natural Bridges Drive
 * Santa Cruz, CA 95060
 *
 * Email: dfinlayson@usgs.gov
 * Phone: (831) 427-4757
 *
 * Copyright (C) 2009 David Finlayson
 */
package gov.usgs.wr.io;
import java.io.*;
import java.lang.Math.*;

/**
 * A LittleEndianOutputStream writes 8-, 16-, 24-, 32-, and 64-bit
 * twos-compliment integers; IEEE 734, 32-bit and 64-bit floating point numbers;
 * and standard C/C++ style null-terminated character arrays, from the underlying
 * InputStream.
 *
 * @author David Finlayson
 */
public class LittleEndianOutputStream extends BinaryOutputStream {
    
    private boolean isBigEndian = false;
    private byte[] b1; // 1-byte storage buffer
    private byte[] b2; // 2-byte storage buffer
    private byte[] b3; // 3-byte storage buffer
    private byte[] b4; // 4-byte storage buffer
    private byte[] b8; // 8-byte storage buffer
    
    /**
     * Creates a new <code>LittleEndianInputStream</code> and attaches it to the
     * <code>InputStream</code>.
     *  
     * @param in <code>InputStream</code> to read binary data from.
     */
    public LittleEndianOutputStream(OutputStream out) {
        super(out);
        
        b1 = new byte[1];
        b2 = new byte[2];
        b3 = new byte[3];
        b4 = new byte[4];
        b8 = new byte[8];
    }
    
    /**
     * Returns true if the underlying stream will be interpreted in big-endian 
     * byte order.
     * 
     * @return <code>true</code> if the InputStream is being interpreted in big-
     * endian format.
     */
    public boolean isBigEndian() {
        return isBigEndian;
    }
    
    /**
     * Write an unsigned, 8-bit integer to the <code>OutputStream</code>
     * 
     * @param n    a value between 0 and 255 to be written to the 
     *             <code>OutputStream</code>.
     * @exception IllegalArgumentException if <code>n</code> is not a valid uint8
     *            integer value
     * @exception  IOException  if the underlying stream throws an IOException.
     */
    public void writeUInt8(int n) throws IOException {
        if (n < 0 || 256 <= n) {
            throw new IllegalArgumentException(
                    String.format("writeUInt8: n (%d) must be beween 0 and 256", n));
        }
        out.write((byte)n);
    }
    
    /**
     * Write an unsigned, 16-bit integer to the <code>OutputStream</code>.
     * 
     * @param n    a value between 0 and 65536 to be written to the 
     *             <code>OutputStream</code>.
     * @exception IllegalArgumentException if <code>n</code> is not a valid uint16
     *            integer value.
     * @exception IOException if the underlying stream throws an IOException.
     */
    public void writeUInt16(int n) throws IOException {
        if (n < 0 || 65536 <= n) {
            throw new IllegalArgumentException(
                    String.format("writeUInt16: n (%d) must be between 0 and 65536", n));
        }
        out.write(n & 0xFF);
        out.write((n >>> 8) & 0xFF);
    }
    
    /**
     * Write an unsigned, 24-bit integer to the <code>OutputStream</code>.
     * 
     * @param n    a value between 0 and 16777216 to be written to the 
     *             <code>OutputStream</code>.
     * @exception IllegalArgumentException if <code>n</code> is not a valid 
     *            uint24 integer value.
     * @exception IOException if the underlying stream throws an IOException.
     */
    public void writeUInt24(int n) throws IOException {
        if (n < 0 || 16777216 <= n) {
            throw new IllegalArgumentException(
                    String.format("writeUInt24: n (%d) must be between 0 and 16777216", n));
        }
        out.write((n & 0xFF));
        out.write((n >>>  8) & 0xFF);
        out.write((n >>> 16) & 0xFF);
    }
    
    /**
     * Write an unsigned, 32-bit integer to the <code>OutputStream</code>.
     * 
     * @param n    a value between 0 and 4294967296 to be written to the 
     *             <code>OutputStream</code>.
     * @exception IllegalArgumentException if <code>n</code> is not a valid 
     *            uint24 integer value.
     * @exception IOException if the underlying stream throws an IOException.
     */
    public void writeUInt32(long n) throws IOException {
        if (n < 0 || 4294967296L <= n) {
            throw new IllegalArgumentException(
                    String.format("writeUInt32: n (%d) must be between 0 and 4294967296", n));
        }
        out.write((int)(n & 0xFF));
        out.write((int)(n >>>  8) & 0xFF);
        out.write((int)(n >>> 16) & 0xFF);
        out.write((int)(n >>> 24) & 0xFF);
    }
    
    /**
     * Write a twos-compliment, signed, 8-bit integer to the 
     * <code>OutputStream</code>.
     * 
     * @param n    a value between -128 and 128 to be written to the 
     *             <code>OutputStream</code>.
     * @exception IllegalArgumentException if <code>n</code> is not a valid 
     *            uint8 integer value.
     * @exception IOException if the underlying stream throws an IOException.
     */
    public void writeInt8(int n) throws IOException {
        if (n < -128 || 128 <= n) {
            throw new IllegalArgumentException(
                    String.format("writeInt8: n (%d) must be between -128 and 128", n));
        }
        out.write((byte)n);
    }
    
    /**
     * Write a twos-compliment, signed, 16-bit integer to the 
     * <code>OutputStream</code>.
     * 
     * @param n    a value between -32768 and 32768 to be written to the 
     *             <code>OutputStream</code>.
     * @exception IllegalArgumentException if <code>n</code> is not a valid 
     *            int16 integer value.
     * @exception IOException if the underlying stream throws an IOException.
     */
    public void writeInt16(int n) throws IOException {
        if (n < -32768 || 32768 <= n) {
            throw new IllegalArgumentException(
                    String.format("writeInt16: n (%d) must be between -32768 and 32768", n));
        }
        b2[0] = (byte)n;
        b2[1] = (byte)(n >> 8);
        out.write(b2, 0, 2);
    }
    
    /**
     * Write a twos-compliment, signed, 24-bit integer to the 
     * <code>OutputStream</code>.
     * 
     * @param n    a value between -8388608 and 8388608 to be written to the 
     *             <code>OutputStream</code>.
     * @exception IllegalArgumentException if <code>n</code> is not a valid 
     *            int24 integer value.
     * @exception IOException if the underlying stream throws an IOException.
     */
    public void writeInt24(int n) throws IOException {
        if (n < -8388608 || 8388608 <= n) {
            throw new IllegalArgumentException(
                    String.format("writeInt24: n (%d) must be between -8388608 and 8388608", n));
        }
        b3[0] = (byte)n;
        b3[1] = (byte)(n >>  8);
        b3[2] = (byte)(n >> 16);
        out.write(b3, 0, 3);
    }
    
    /**
     * Write a twos-compliment, signed, 32-bit integer to the 
     * <code>OutputStream</code>.
     * 
     * @param n   a integer between -2147483648L and 2147483648L to be 
     *            written to the <code>OutputStream</code>.
     * @exception IOException if the underlying stream throws an IOException.
     */
    public void writeInt32(int n) throws IOException {
        // bounds protected by native integer bounds
        b4[0] = (byte)n;
        b4[1] = (byte)(n >>  8);
        b4[2] = (byte)(n >> 16);
        b4[3] = (byte)(n >> 24);
        out.write(b4, 0, 4);
    }

    /**
     * Write a twos-compliment, signed, 64-bit integer to the OutputStream.
     * @param n
     * @throws java.io.IOException
     */
    public void writeInt64(long n) throws IOException {
        // bounds protected by native long integer bounds
        b8[0] = (byte)n;
        b8[1] = (byte)(n >> 8);
        b8[2] = (byte)(n >> 16);
        b8[3] = (byte)(n >> 24);
        b8[4] = (byte)(n >> 32);
        b8[5] = (byte)(n >> 40);
        b8[6] = (byte)(n >> 48);
        b8[7] = (byte)(n >> 56);
        out.write(b8, 0, 8);
    }
    
    
    /**
     * Writes a 4 byte Java float to the underlying output stream in
     * little endian order.
     *
     * @param      f   the <code>float</code> value to be written.
     * @exception  IOException  if an I/O error occurs.
     */
    public final void writeReal32(float f) throws IOException {
        this.writeInt32(Float.floatToIntBits(f));    
    }

    /**
     * Writes an 8 byte Java double to the underlying output stream in
     * little endian order.
     *
     * @param      d   the <code>double</code> value to be written.
     * @exception  IOException  if an I/O error occurs.
     */
     public final void writeReal64(double d) throws IOException {
        this.writeInt64(Double.doubleToLongBits(d));
     }
     
     /**
      * Writes a null-terminated string of <code>length</code> to the 
      * output stream. The string will be truncated at length
      * 
      * @param text the character array to write
      * @param length the length of the character array.
      * @exception IOException if an I/O error occurs.
      */
     public void writeString(String text, int length) throws IOException {
         if (length < 1) {
             throw new IllegalArgumentException(
                     String.format("writeString: length (%d) must be > 1", length));
         }
         
         int n = Math.min(length, text.length()+1);
         n -= 1; // subtract 1 byte to allow for the final null-terminator
         for (int i = 0; i < length; i++) {
             out.write((byte)((i < n) ? text.charAt(i) : '\0')); 
         }
     }
}
