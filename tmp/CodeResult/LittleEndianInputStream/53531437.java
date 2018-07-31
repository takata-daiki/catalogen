/**
 * LittleEndianInputStream
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

/**
 * A <code>LittleEndianInputStream</code> object reads 8-, 16-, 24-, 32-, and 64-bit 
 * twos-compliment integers; IEEE 734, 32-bit and 64-bit floating point numbers;
 * and standard C/C++ style null-terminated character arrays, from the underlying
 * <code>InputStream</code>.
 * 
 * @author David Finlayson
 * @version 1.0.0, 27 September 2008
 */
public class LittleEndianInputStream extends BinaryInputStream {
    
    private boolean isBigEndian = false;
    private byte[] b1; // 1-byte storage array
    private byte[] b2; // 2-byte storage array
    private byte[] b3; // 3-byte storage array
    private byte[] b4; // 4-byte storage array
    private byte[] b8; // 8-byte storage array
    
    /**
     * Creates a new <code>LittleEndianInputStream</code> and attaches it to the
     * <code>InputStream</code>.
     *  
     * @param in <code>InputStream</code> to read binary data from.
     */
    public LittleEndianInputStream(InputStream in) {
        super(in);        
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
     * Reads a 1-byte unsigned, integer (0 to 255). 
     * 
     * @return     The 8-bit unsigned integer value.
     * @exception  IOException  if the underlying stream throws an IOException.
     */
    public int readUInt8() throws IOException {
        readFully(b1);
        return (b1[0] & 0xFF);
    }
    
    /**
     * Reads an unsigned 2-byte integer (0 to 65,536). 
     * 
     * @return     The 16-bit unsigned integer value.
     * @exception  IOException  if the underlying stream throws an IOException.
     */
    public int readUInt16() throws IOException {
        readFully(b2);
        return ((b2[1] & 0xFF) << 8 | 
                (b2[0] & 0xFF));
    }
    
    /**
     * Reads an unsigned, 3-byte integer (0 to 16777216). 
     * 
     * @return     The 24-bit unsigned integer value.
     * @exception  IOException  if the underlying stream throws an IOException.
     * */
    public int readUInt24() throws IOException {
        readFully(b3);
        return ((b3[2] & 0xFF) << 16 |
                (b3[1] & 0xFF) <<  8 |
                (b3[0] & 0xFF));
    }
    
    /**
     * Reads an unsigned, 4-byte integer (0 to 4,294,967,296).
     * 
     * @return     The 32-bit unsigned integer value.
     * @exception  IOException  if the underlying stream throws an IOException.
     * */
    public long readUInt32() throws IOException {
        readFully(b4);
        return ((long)(b4[3] & 0xFF) << 24 |
                (b4[2] & 0xFF) << 16 |
                (b4[1] & 0xFF) <<  8 |
                (b4[0] & 0xFF));
    }
    
    /**
     * Reads a <code>String</code>.
     *  
     * @param length The number of characters to read from the <code>InputStream</code>
     * @return a <code>string</code> containing the characters read.
     * @throws IOException if the underlying stream throws an exception.
     */
    public String readString(int length) throws IOException {
        byte[] b = new byte[length];
        readFully(b);
        return new String(b);
    }
    
    /**
     * Reads a 1-byte integer (-128 to 127)
     * 
     * @return an 8-bit integer value.
     * @throws java.io.IOException if the underlying stream throws an exception.
     */
    public int readInt8() throws IOException {
        int value = readUInt8();
        if (value < 128) {
            return value;
        } else {
            return value - 256;
        }
    }
    
    /**
     * Reads a 2-byte integer (-32,768 to 32,767)
     * 
     * @return a 16-bit integer value.
     * @throws java.io.IOException if the underlying stream throws an exception.
     */
    public int readInt16() throws IOException {
        int value = readUInt16();
        if (value < 32768) {
            return value;
        } else {
            return value - 65536;
        }
    }
    
    /**
     * Reads a 3-byte integer (-8,388,608 to 8388607)
     * 
     * @return a 24-bit integer value.
     * @throws java.io.IOException if the underlying stream throws an exception.
     */
    public int readInt24() throws IOException {
        int value = readUInt24();
        if (value < 8388608) {
            return value;
        } else {
            return value - 16777216;
        }
    }
    
    /**
     * Reads a 4-byte integer (-2,147,483,648 to 2,147,483,647)
     * 
     * @return an 32-bit integer value.
     * @throws IOException if the underlying stream throws an exception.
     */
    public int readInt32() throws IOException {
        readFully(b4);
        return ((b4[3] & 0xFF) << 24 |
                (b4[2] & 0xFF) << 16 |
                (b4[1] & 0xFF) <<  8 |
                (b4[0] & 0xFF));
    }
    
    /**
     * Reads a 8-byte integer (-9,223,372,036,854,775,808 to 
     *                          9,223,372,036,854,775,807)
     * 
     * @return a 64-bit integer value.
     * @throws java.io.IOException if the underlying stream throws an exception.
     */
    public long readInt64() throws IOException {
        readFully(b8);
        return (long) (b8[7] & 0xFF) << 56 |
               (long) (b8[6] & 0xFF) << 48 |
               (long) (b8[5] & 0xFF) << 40 |
               (long) (b8[4] & 0xFF) << 32 |
               (long) (b8[3] & 0xFF) << 24 |
               (long) (b8[2] & 0xFF) << 16 |
               (long) (b8[1] & 0xFF) <<  8 |
               (long) (b8[0] & 0xFF);
    }
    
    /**
     * Reads an IEEE 734, 32-bit floating point number (single-precision float) 
     * from the underlying stream.
     * 
     * @return a 32-bit java <code>float</code>.
     * @throws java.io.IOException
     */
    public float readReal32() throws IOException {
        return Float.intBitsToFloat(this.readInt32());
    }
    
    /**
     * Reads an IEEE 734, 64-bit floating point number (double-precision float)
     * from the underlying stream.
     * 
     * @return a 64-bit java <code>double</code>.
     * @throws java.io.IOException
     */
    public double readReal64() throws IOException {
        return Double.longBitsToDouble(this.readInt64());
    }
    
}
