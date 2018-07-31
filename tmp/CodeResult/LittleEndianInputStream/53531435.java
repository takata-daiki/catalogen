/*
 * LittleEndianInputStreamTest.java
 *
 * David Finlayson, Ph.D.
 * U.S. Geological Survey
 *
 * Copyright (c) 2009 David Finlayson
 *
 */
package gov.usgs.wr.io;

import java.io.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for the LittleEndianInputStream.
 * @author David Finlayson
 * @version 1.0
 */
public class LittleEndianInputStreamTest {

    /**
     * Constructor
     */
    public LittleEndianInputStreamTest() {
    }

    /**
     * Test of isBigEndian method, of class LittleEndianInputStream.
     */
    @Test
    public void testIsBigEndian()
    {
        System.out.println("isBigEndian");
        byte[] bytes = new byte[1];
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(false, in.isBigEndian());
    }

    /**
     * Test of readUInt8 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadUInt8() throws Exception
    {
        System.out.println("readUInt8");
        byte[] bytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte)i;
        }

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        for (int i = 0; i < 256; i++) {
            assertEquals(i, in.readUInt8());
        }
    }

    /**
     * Test of readUInt16 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadUInt16() throws Exception
    {
        System.out.println("readUInt16");
        byte[] bytes = new byte[2];
        bytes[0] = 0;
        bytes[1] = 0;

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0, in.readUInt16());

        bytes[0] = (byte)255;
        bytes[1] = (byte)255;

        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(65536 - 1, in.readUInt16());
    }

    /**
     * Test of readUInt24 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadUInt24() throws Exception
    {
        System.out.println("readUInt24");
        byte[] bytes = new byte[3];
        bytes[0] = 0;
        bytes[1] = 0;
        bytes[2] = 0;

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0, in.readUInt24());

        bytes[0] = (byte)255;
        bytes[1] = (byte)255;
        bytes[2] = (byte)255;

        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals((int)Math.pow(2, 24) - 1, in.readUInt24());
    }

    /**
     * Test of readUInt32 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadUInt32() throws Exception
    {
        System.out.println("readUInt32");
        byte[] bytes = new byte[4];
        bytes[0] = 0;
        bytes[1] = 0;
        bytes[2] = 0;
        bytes[3] = 0;

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0L, in.readUInt32());

        bytes[0] = (byte)255;
        bytes[1] = (byte)255;
        bytes[2] = (byte)255;
        bytes[3] = (byte)255;

        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals((long)Math.pow(2, 32) - 1L, in.readUInt32());
    }

    /**
     * Test of readString method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadString() throws Exception
    {
        System.out.println("readString");
        byte[] bytes = new byte[26];
        for(int i = 0; i < 26; i++) {
            bytes[i] = (byte)'\0';
        }

        bytes[0] = (byte)'a';
        bytes[1] = (byte)'b';
        bytes[2] = (byte)'c';

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        
        // Test that we get the proper string back
        in.mark(10);
        assertEquals("abc", in.readString(3));

    }

    /**
     * Test of readInt8 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadInt8() throws Exception
    {
        System.out.println("readInt8");
        byte[] bytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte)i;
        }

        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        for (int i = 0; i < 256; i++) {
            if (i < 128) {
                assertEquals(i, in.readInt8());
            } else {
                assertEquals(i - 256, in.readInt8());
            }
        }
    }

    /**
     * Test of readInt16 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadInt16() throws Exception
    {
        System.out.println("readInt16");
        byte[] bytes = new byte[2];
        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0, in.readInt16());

        bytes[0] = (byte)1;
        bytes[1] = (byte)0;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(1, in.readInt16());

        bytes[0] = (byte)255;
        bytes[1] = (byte)255;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(-1, in.readInt16());

        bytes[0] = (byte)255;
        bytes[1] = (byte)127;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(32767, in.readInt16());

        bytes[0] = (byte)0;
        bytes[1] = (byte)128;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(-32768, in.readInt16());
}

    /**
     * Test of readInt24 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadInt24() throws Exception
    {
        System.out.println("readInt24");
        byte[] bytes = new byte[3];
        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0, in.readInt24());

        bytes[0] = (byte)1;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(1, in.readInt24());

        bytes[0] = (byte)255;
        bytes[1] = (byte)255;
        bytes[2] = (byte)255;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(-1, in.readInt24());
    }

    /**
     * Test of readInt32 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadInt32() throws Exception
    {
        System.out.println("readInt32");
        byte[] bytes = new byte[4];
        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0, in.readInt32());

        bytes[0] = (byte)1;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(1, in.readInt32());

        bytes[0] = (byte)255;
        bytes[1] = (byte)255;
        bytes[2] = (byte)255;
        bytes[3] = (byte)255;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(-1, in.readInt32());
    }

    /**
     * Test of readInt64 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadInt64() throws Exception
    {
        System.out.println("readInt64");
        byte[] bytes = new byte[8];
        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        bytes[4] = (byte)0;
        bytes[5] = (byte)0;
        bytes[6] = (byte)0;
        bytes[7] = (byte)0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0, in.readInt64());

        bytes[0] = (byte)1;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        bytes[4] = (byte)0;
        bytes[5] = (byte)0;
        bytes[6] = (byte)0;
        bytes[7] = (byte)0;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(1, in.readInt64());

        bytes[0] = (byte)255;
        bytes[1] = (byte)255;
        bytes[2] = (byte)255;
        bytes[3] = (byte)255;
        bytes[4] = (byte)255;
        bytes[5] = (byte)255;
        bytes[6] = (byte)255;
        bytes[7] = (byte)255;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(-1, in.readInt64());
    }

    /**
     * Test of readReal32 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadReal32() throws Exception
    {
        System.out.println("readReal32");
        byte[] bytes = new byte[8];
        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0.0, in.readReal32(), 0.01);

        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)128;
        bytes[3] = (byte)63;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(1.0, in.readReal32(), 0.01);

        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)128;
        bytes[3] = (byte)191;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(-1.0, in.readReal32(), 0.01);
    }

    /**
     * Test of readReal64 method, of class LittleEndianInputStream.
     */
    @Test
    public void testReadReal64() throws Exception
    {
        System.out.println("readReal64");
        byte[] bytes = new byte[8];
        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        bytes[4] = (byte)0;
        bytes[5] = (byte)0;
        bytes[6] = (byte)0;
        bytes[7] = (byte)0;
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        LittleEndianInputStream in = new LittleEndianInputStream(byteStream);
        assertEquals(0.0, in.readReal64(), 0.01);

        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        bytes[4] = (byte)0;
        bytes[5] = (byte)0;
        bytes[6] = (byte)240;
        bytes[7] = (byte)63;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(1.0, in.readReal64(), 0.01);

        bytes[0] = (byte)0;
        bytes[1] = (byte)0;
        bytes[2] = (byte)0;
        bytes[3] = (byte)0;
        bytes[4] = (byte)0;
        bytes[5] = (byte)0;
        bytes[6] = (byte)240;
        bytes[7] = (byte)191;
        byteStream = new ByteArrayInputStream(bytes);
        in = new LittleEndianInputStream(byteStream);
        assertEquals(-1.0, in.readReal64(), 0.01);
    }
}