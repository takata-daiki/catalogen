package org.baseparadigm;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;

import org.baseparadigm.i.Bytes;

/**
 * A simple {@link Bytes} implementation that wraps a byte array.
 */
public class ByteChunk implements Bytes {

    protected final byte[] chunk;

    /**
     * zero length byte chunk
     */
    public static final ByteChunk EMPTY = new ByteChunk(new byte[0]);

    /**
     * Reads {@code nbrBytes} bytes from the {@link ByteBuffer}
     * 
     * @param bytes
     *            the buffer containing bytes to become this {@code ByteChunk}
     * @param nbrBytes
     *            the number of bytes to read from the buffer {@code bytes},
     *            which will become the size of this {@code ByteChunk}
     * @throws BufferUnderflowException
     *             if there are fewer than {@code nbrBytes} remaining in the
     *             buffer {@code bytes}
     */
    public ByteChunk(final ByteBuffer bytes, final int nbrBytes) throws BufferUnderflowException {
        chunk = new byte[nbrBytes];
        bytes.get(chunk);
    }

    /**
     * Copies the given byte array, so you can feel free to pass in a reused
     * buffer.
     * 
     * @param byteArray
     *            the data that composes this ByteChunk
     */
    public ByteChunk(final byte[]... byteArrays) {
        assert byteArrays != null;
        long totalLength = 0;
        for(final byte[] i : byteArrays) totalLength += i.length;
        assert totalLength <= Integer.MAX_VALUE;
        this.chunk = new byte[(int) totalLength];
        int off = 0;
        for (final byte[] onechunk : byteArrays) {
            System.arraycopy(onechunk, 0, chunk, off, onechunk.length);
            off += onechunk.length;
        }
        assert off == chunk.length;
    }
    public ByteChunk(final Bytes... bytes) {
        assert bytes != null;
        long totalLength = 0;
        for(final Bytes i : bytes) {
            totalLength += i.getSize().longValue();
            // to avoid unnecessary io and/or processing,
            // do this check in the loop in case getSize() is forcing evaluation
            if (totalLength > Integer.MAX_VALUE) {
                throw new IllegalArgumentException(
                        "current implementation tries to stuff the given bytes"+
                        " into a byte array, and "+totalLength+" bytes are too many.");
            }
        }
        this.chunk = new byte[(int) totalLength];
        int off = 0;
        for (final Bytes onechunk : bytes) {
            final byte[] bytea = onechunk.toByteArray();
            System.arraycopy(bytea, 0, chunk, off, bytea.length);
            off += bytea.length;
        }
        assert off == chunk.length;
    }

    @Override
    public Iterator<Byte> iterator() {
        return new ByteIterator(this);
    }

    @Override
    public byte[] toByteArray() {
        return Arrays.copyOf(chunk, chunk.length);
    }

    @Override
    public Number getSize() {
        return chunk.length;
    }

    @Override
    public Bytes getRange(final Number start, final Number length) {
        return new ByteChunk(Arrays.copyOfRange(chunk, start.intValue(),
                start.intValue() + length.intValue()));
    }

    @Override
    public Byte get(final Number i) {
        return chunk[i.intValue()];
    }

    /**
     * @return this (same instance)
     */
    @Override
    public Bytes getBytes() {
        return this;
    }

    @Override
    public String toString() {
        return Util.toHex(toByteArray()).toString();
    }

    @Override
    public int hashCode() {
        return 443 + Arrays.hashCode(chunk);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ByteChunk other = (ByteChunk) obj;
        if (!Arrays.equals(chunk, other.chunk))
            return false;
        return true;
    }

    @Override
    public byte[] get(final Number start, final byte[] buff) {
        return Arrays.copyOfRange(chunk, start.intValue(), start.intValue() +buff.length);
    }

    public static ByteChunk concat(byte[] first, byte[] second) {
      byte[] result = Arrays.copyOf(first, first.length + second.length);
      System.arraycopy(second, 0, result, first.length, second.length);
      return new ByteChunk(result);
    }

    /**
     * copies all your byte arrays into one new big ByteChunk
     * @return a ByteChunk containing all the bytes given
     */
    public static ByteChunk concat(byte[]... nChunks) {
        // find out how big of an array to allocate
        long totalLength = 0;
        for(byte[] b : nChunks)
            totalLength += b.length;
        if (totalLength > Integer.MAX_VALUE)
            throw new IllegalArgumentException(
                    "current implementation tries to stuff the given bytes"
                    + " into a byte array, and " + totalLength + " bytes are too many.");
        byte[] newchunk = new byte[(int) totalLength];
        int off = 0;
        for (final byte[] bytea : nChunks) {
            System.arraycopy(bytea, 0, newchunk, off, bytea.length);
            off += bytea.length;
        }
        assert off == newchunk.length;
        return new ByteChunk(newchunk);
    }
}
