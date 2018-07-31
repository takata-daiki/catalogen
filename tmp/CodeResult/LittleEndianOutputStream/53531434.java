/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.usgs.wr.io;

import java.io.ByteArrayOutputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Finlayson
 */
public class LittleEndianOutputStreamTest {

    public LittleEndianOutputStreamTest() {
    }

    /**
     * Test of isBigEndian method, of class LittleEndianOutputStream.
     */
    @Test
    public void testIsBigEndian()
    {
        System.out.println("isBigEndian");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        assertEquals(out.isBigEndian(), false);
    }

    /**
     * Test of writeUInt8 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteUInt8() throws Exception
    {
        System.out.println("writeUInt8");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(256);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);

        for (int i = 0; i < 256; i++) {
            out.writeUInt8(i);
        }

        byte[] bytes = byteStream.toByteArray();
        for (int i = 0; i < 256; i++) {
            assertEquals(bytes[i], (byte)i);
        }

        try {
            byteStream = new ByteArrayOutputStream(1);
            out = new LittleEndianOutputStream(byteStream);
            out.writeUInt8(-1);
            fail("Failed to catch a negative number inserted into a UInt8");
        } catch (IllegalArgumentException e) {
            // passed
        }
    }

    /**
     * Test of writeUInt16 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteUInt16() throws Exception
    {
        System.out.println("writeUInt16");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(2);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeUInt16(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);

        byteStream = new ByteArrayOutputStream(2);
        out = new LittleEndianOutputStream(byteStream);
        out.writeUInt16(65535);
        bytes = byteStream.toByteArray();
        assertEquals((byte)255, bytes[0]);
        assertEquals((byte)255, bytes[1]);

        try {
            byteStream = new ByteArrayOutputStream(2);
            out = new LittleEndianOutputStream(byteStream);
            out.writeUInt16(-1);
            fail("Failed to catch a negative number inserted into a UInt16");
        } catch (IllegalArgumentException e) {
            // passed
        }
    }

    /**
     * Test of writeUInt24 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteUInt24() throws Exception
    {
        System.out.println("writeUInt24");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(2);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeUInt24(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);

        byteStream = new ByteArrayOutputStream(3);
        out = new LittleEndianOutputStream(byteStream);
        out.writeUInt24((int)Math.pow(2, 24)-1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)255, bytes[0]);
        assertEquals((byte)255, bytes[1]);
        assertEquals((byte)255, bytes[2]);

        try {
            byteStream = new ByteArrayOutputStream(3);
            out = new LittleEndianOutputStream(byteStream);
            out.writeUInt24(-1);
            fail("Failed to catch a negative number inserted into a UInt24");
        } catch (IllegalArgumentException e) {
            // passed
        }
    }

    /**
     * Test of writeUInt32 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteUInt32() throws Exception
    {
        System.out.println("writeUInt32");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeUInt32(0L);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);

        byteStream = new ByteArrayOutputStream(4);
        out = new LittleEndianOutputStream(byteStream);
        out.writeUInt32((long)Math.pow(2, 32)-1L);
        bytes = byteStream.toByteArray();
        assertEquals((byte)255, bytes[0]);
        assertEquals((byte)255, bytes[1]);
        assertEquals((byte)255, bytes[2]);
        assertEquals((byte)255, bytes[3]);

        try {
            byteStream = new ByteArrayOutputStream(2);
            out = new LittleEndianOutputStream(byteStream);
            out.writeUInt32(-1);
            fail("Failed to catch a negative number inserted into a UInt24");
        } catch (IllegalArgumentException e) {
            // passed
        }
    }

    /**
     * Test of writeInt8 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteInt8() throws Exception
    {
        System.out.println("writeInt8");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(256);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);

        for (int i = 0; i < 256; i++) {
            out.writeInt8((byte)i);
        }

        byte[] bytes = byteStream.toByteArray();
        for (int i = 0; i < 256; i++) {
            if (i < 128) {
                assertEquals((byte)i, bytes[i]);
            } else {
                assertEquals(i - 256, bytes[i]);
            }
        }

        try {
            byteStream = new ByteArrayOutputStream(1);
            out = new LittleEndianOutputStream(byteStream);
            out.writeInt8(-200);
            fail("Failed to catch an out of bounds number inserted into a UInt8");
        } catch (IllegalArgumentException e) {
            // passed
        }

        try {
            byteStream = new ByteArrayOutputStream(1);
            out = new LittleEndianOutputStream(byteStream);
            out.writeInt8(200);
            fail("Failed to catch an out of bounds number inserted into a UInt8");
        } catch (IllegalArgumentException e) {
            // passed
        }
    }

    /**
     * Test of writeInt16 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteInt16() throws Exception
    {
        System.out.println("writeInt16");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(2);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeInt16(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);

        byteStream = new ByteArrayOutputStream(2);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt16(-1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)255, bytes[0]);
        assertEquals((byte)255, bytes[1]);

        byteStream = new ByteArrayOutputStream(2);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt16(1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)1, bytes[0]);
        assertEquals((byte)0, bytes[1]);

        try {
            byteStream = new ByteArrayOutputStream(2);
            out = new LittleEndianOutputStream(byteStream);
            out.writeInt16((int)-1e6);
            fail("Failed to catch an out of bounds number inserted into a writeInt16");
        } catch (IllegalArgumentException e) {
            // passed
        }

        try {
            byteStream = new ByteArrayOutputStream(2);
            out = new LittleEndianOutputStream(byteStream);
            out.writeInt16((int)1e6);
            fail("Failed to catch an out of bounds number inserted into a writeInt16");
        } catch (IllegalArgumentException e) {
            // passed
        }
    }

    /**
     * Test of writeInt24 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteInt24() throws Exception
    {
        System.out.println("writeInt24");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(3);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeInt24(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);

        byteStream = new ByteArrayOutputStream(3);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt24(-1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)255, bytes[0]);
        assertEquals((byte)255, bytes[1]);
        assertEquals((byte)255, bytes[2]);

        byteStream = new ByteArrayOutputStream(3);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt24(1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)1, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);

        try {
            byteStream = new ByteArrayOutputStream(3);
            out = new LittleEndianOutputStream(byteStream);
            out.writeInt24((int)-1e7);
            fail("Failed to catch an out of bounds number inserted into a writeInt24");
        } catch (IllegalArgumentException e) {
            // passed
        }

        try {
            byteStream = new ByteArrayOutputStream(3);
            out = new LittleEndianOutputStream(byteStream);
            out.writeInt24((int)1e7);
            fail("Failed to catch an out of bounds number inserted into a writeInt24");
        } catch (IllegalArgumentException e) {
            // passed
        }
    }

    /**
     * Test of writeInt32 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteInt32() throws Exception
    {
        System.out.println("writeInt32");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeInt32(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);

        byteStream = new ByteArrayOutputStream(4);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt32(-1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)255, bytes[0]);
        assertEquals((byte)255, bytes[1]);
        assertEquals((byte)255, bytes[2]);
        assertEquals((byte)255, bytes[3]);

        byteStream = new ByteArrayOutputStream(3);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt32(1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)1, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);

        // No need to test for out of bounds errors because the method
        // reads and writes native integers (Java gaurds bounds).
    }

    /**
     * Test of writeInt64 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteInt64() throws Exception
    {
        System.out.println("writeInt64");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(8);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeInt64(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);
        assertEquals((byte)0, bytes[4]);
        assertEquals((byte)0, bytes[5]);
        assertEquals((byte)0, bytes[6]);
        assertEquals((byte)0, bytes[7]);

        byteStream = new ByteArrayOutputStream(8);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt64(-1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)255, bytes[0]);
        assertEquals((byte)255, bytes[1]);
        assertEquals((byte)255, bytes[2]);
        assertEquals((byte)255, bytes[3]);
        assertEquals((byte)255, bytes[4]);
        assertEquals((byte)255, bytes[5]);
        assertEquals((byte)255, bytes[6]);
        assertEquals((byte)255, bytes[7]);

        byteStream = new ByteArrayOutputStream(8);
        out = new LittleEndianOutputStream(byteStream);
        out.writeInt64(1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)1, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);
        assertEquals((byte)0, bytes[4]);
        assertEquals((byte)0, bytes[5]);
        assertEquals((byte)0, bytes[6]);
        assertEquals((byte)0, bytes[7]);

        // No need to test for out of bounds errors because the method
        // reads and writes native long integers (Java gaurds bounds).
    }

    /**
     * Test of writeReal32 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteReal32() throws Exception
    {
        System.out.println("writeReal32");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeReal32(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);

        byteStream = new ByteArrayOutputStream(4);
        out = new LittleEndianOutputStream(byteStream);
        out.writeReal32(1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)128, bytes[2]);
        assertEquals((byte)63, bytes[3]);

        byteStream = new ByteArrayOutputStream(4);
        out = new LittleEndianOutputStream(byteStream);
        out.writeReal32(-1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)128, bytes[2]);
        assertEquals((byte)191, bytes[3]);
    }

    /**
     * Test of writeReal64 method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteReal64() throws Exception
    {
        System.out.println("writeReal64");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(8);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeReal64(0);
        byte[] bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);
        assertEquals((byte)0, bytes[4]);
        assertEquals((byte)0, bytes[5]);
        assertEquals((byte)0, bytes[6]);
        assertEquals((byte)0, bytes[7]);

        byteStream = new ByteArrayOutputStream(8);
        out = new LittleEndianOutputStream(byteStream);
        out.writeReal64(1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);
        assertEquals((byte)0, bytes[4]);
        assertEquals((byte)0, bytes[5]);
        assertEquals((byte)240, bytes[6]);
        assertEquals((byte)63, bytes[7]);

        byteStream = new ByteArrayOutputStream(8);
        out = new LittleEndianOutputStream(byteStream);
        out.writeReal64(-1);
        bytes = byteStream.toByteArray();
        assertEquals((byte)0, bytes[0]);
        assertEquals((byte)0, bytes[1]);
        assertEquals((byte)0, bytes[2]);
        assertEquals((byte)0, bytes[3]);
        assertEquals((byte)0, bytes[4]);
        assertEquals((byte)0, bytes[5]);
        assertEquals((byte)240, bytes[6]);
        assertEquals((byte)191, bytes[7]);
    }

    /**
     * Test of writeString method, of class LittleEndianOutputStream.
     */
    @Test
    public void testWriteString() throws Exception
    {
        System.out.println("writeString");

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(10);
        LittleEndianOutputStream out = new LittleEndianOutputStream(byteStream);
        out.writeString("Test", 6);
        byte[] bytes = byteStream.toByteArray();

        assertEquals((byte)84, bytes[0]);
        assertEquals((byte)101, bytes[1]);
        assertEquals((byte)115, bytes[2]);
        assertEquals((byte)116, bytes[3]);
        assertEquals((byte)0, bytes[4]);
        assertEquals((byte)0, bytes[5]);
    }
}