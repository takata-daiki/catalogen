/*
 * Copyright (C) 2012, The MINT Consortium (See COPYRIGHTS file for a list of copyright holders).
 * All rights reserved.
 *
 * This source code contains the intellectual property of its copyright holders, and is made
 * available under a license. If you do not know the terms of the license, please review it before
 * you read further.
 *
 * You can read LICENSES for detailed information about the license terms this source code file is
 * available under.
 *
 * Questions should be directed to legal@peakhealthcare.com
 *
 */

package org.oht.miami.msdtk.crypto;

import org.oht.miami.msdtk.studymodel.Study;
import org.oht.miami.msdtk.util.DicomUID;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Random;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.Mac;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BCStudyCipher extends StudyCipher {

    private static final Logger LOG = LoggerFactory.getLogger(BCStudyCipher.class);

    private static final byte[] GLOBAL_SECRET = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                    0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14,
                    0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F };

    private static final int BLOCK_SIZE = 16;

    // 1MB
    private static final int CHUNK_SIZE = 1 << 20;

    private GCMBlockCipher gcmCipher;

    private Mac hmac;

    private Random random;

    private Deflater deflater;

    private Inflater inflater;

    private byte[] keyBytes;

    private byte[] ivBytes;

    private byte[] aadBytes;

    private ByteBuf aad;

    private byte[] tailBytes;

    private byte[] decryptedChunkBytes;

    static {

        Security.addProvider(new BouncyCastleProvider());
    }

    public BCStudyCipher() {

        gcmCipher = new GCMBlockCipher(new AESEngine());
        try {
            hmac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HmacSHA256 algorithm is not supported");
        }
        random = new SecureRandom();
        deflater = new Deflater();
        inflater = new Inflater();
        // 32 bytes = 256 bits
        keyBytes = new byte[32];
        ivBytes = new byte[StudyCipher.IV_SIZE];
        aadBytes = new byte[StudyCipher.HEADER_SIZE + StudyCipher.IV_SIZE];
        aad = Unpooled.wrappedBuffer(aadBytes);
        tailBytes = new byte[BLOCK_SIZE - 1 + StudyCipher.MAC_SIZE];
        decryptedChunkBytes = new byte[CHUNK_SIZE];
    }

    @Override
    protected ByteBuf allocateBuffer(int capacity) {

        return Unpooled.buffer(capacity);
    }

    private void prepareKey(DicomUID studyInstanceUID) throws StudyCryptoException {

        String keyMessage = Study.IMPLEMENTATION_VERSION_NAME + studyInstanceUID;
        byte[] keyMessageBytes = null;
        try {
            keyMessageBytes = keyMessage.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new StudyCryptoException(e);
        }
        try {
            hmac.init(new SecretKeySpec(GLOBAL_SECRET, ""));
        } catch (InvalidKeyException e) {
            throw new StudyCryptoException(e);
        }
        hmac.update(keyMessageBytes);
        try {
            hmac.doFinal(keyBytes, 0);
        } catch (ShortBufferException e) {
            throw new StudyCryptoException(e);
        }
    }

    private void prepareIV() {

        random.nextBytes(ivBytes);
        System.arraycopy(ivBytes, 0, aadBytes, StudyCipher.HEADER_SIZE, StudyCipher.IV_SIZE);
    }

    private void prepareHeader() throws StudyCryptoException {

        ByteBuf header = Unpooled.wrappedBuffer(aadBytes, 0, StudyCipher.HEADER_SIZE);
        header.writerIndex(0);
        try {
            this.encryptionInfo.save(header);
        } catch (IOException e) {
            throw new StudyCryptoException(e);
        }
    }

    private AEADParameters createAEADParameters() {

        KeyParameter keyParam = new KeyParameter(keyBytes, 0, StudyCipher.KEY_SIZE);
        return new AEADParameters(keyParam, StudyCipher.MAC_SIZE * Byte.SIZE, ivBytes, aadBytes);
    }

    @Override
    protected void init(OperationMode mode, DicomUID studyInstanceUID) throws StudyCryptoException {

        super.init(mode, studyInstanceUID);
        long keyPrepStart = System.nanoTime();
        prepareKey(studyInstanceUID);
        long keyPrepEnd = System.nanoTime();
        LOG.trace("{}\t", keyPrepEnd - keyPrepStart);
    }

    @Override
    public void initEncrypt(DicomUID studyInstanceUID, boolean compress, UUID uuid, long length)
            throws StudyCryptoException {

        super.initEncrypt(studyInstanceUID, compress, uuid, length);
        long ivPrepStart = System.nanoTime();
        prepareIV();
        long headerPrepStart = System.nanoTime();
        prepareHeader();
        long gcmInitStart = System.nanoTime();
        gcmCipher.init(true, createAEADParameters());
        long gcmInitEnd = System.nanoTime();
        LOG.trace("{}\t{}\t{}\t", new Object[] { headerPrepStart - ivPrepStart,
                        gcmInitStart - headerPrepStart, gcmInitEnd - gcmInitStart });
    }

    @Override
    public ByteBuf getKey() {

        return Unpooled.wrappedBuffer(keyBytes, 0, StudyCipher.KEY_SIZE);
    }

    private void processAAD(ByteBuf input) throws StudyCryptoException {

        long aadParsingStart = System.nanoTime();
        input.getBytes(input.readerIndex(), aadBytes);
        input.getBytes(input.readerIndex() + StudyCipher.HEADER_SIZE, ivBytes);
        ByteBuf aad = input.readSlice(StudyCipher.AAD_SIZE);
        try {
            this.encryptionInfo.load(aad);
        } catch (IOException e) {
            throw new StudyCryptoException(e);
        }
        long gcmInitStart = System.nanoTime();
        gcmCipher.init(false, createAEADParameters());
        long gcmInitEnd = System.nanoTime();
        LOG.trace("{}\t{}\t", gcmInitStart - aadParsingStart, gcmInitEnd - gcmInitStart);
    }

    @Override
    protected ByteBuf updateEncrypt(ByteBuf input) throws StudyCryptoException {

        if (!input.hasArray()) {
            throw new StudyCryptoException("Input buffer must be backed by a byte array");
        }
        long compressionStart = System.nanoTime();
        byte[] inputBytes = input.array();
        int offset = input.arrayOffset() + input.readerIndex();
        int uncompressedSize = input.readableBytes();
        input.skipBytes(uncompressedSize);
        int compressedSize = uncompressedSize;
        if (this.encryptionInfo.isCompressed()) {
            deflater.reset();
            deflater.setLevel(this.compressionLevel);
            deflater.setInput(inputBytes, offset + StudyCipher.ZLIB_HEADER_LEN, uncompressedSize
                    - StudyCipher.ZLIB_HEADER_LEN);
            deflater.finish();
            compressedSize = 0;
            while (!deflater.finished()) {
                compressedSize += deflater.deflate(inputBytes, offset + compressedSize,
                        uncompressedSize - compressedSize);
            }
        }
        long encryptionStart = System.nanoTime();
        int encryptedSize = gcmCipher.processBytes(inputBytes, offset, compressedSize, inputBytes,
                offset);
        assert encryptedSize <= compressedSize;
        ByteBuf encrypted = Unpooled.wrappedBuffer(aad,
                Unpooled.wrappedBuffer(inputBytes, offset, encryptedSize));
        long encryptionEnd = System.nanoTime();
        LOG.trace("{}\t{}\t{}\t", new Object[] { encryptionStart - compressionStart,
                        compressedSize, encryptionEnd - encryptionStart });
        return encrypted;
    }

    @Override
    protected ByteBuf updateDecrypt(ByteBuf input) throws StudyCryptoException {

        if (!input.hasArray()) {
            throw new StudyCryptoException("Input buffer must be backed by a byte array");
        }
        processAAD(input);
        long decryptionStart = System.nanoTime();
        byte[] inputBytes = input.array();
        int offset = input.arrayOffset() + input.readerIndex();
        int remainingSize = input.readableBytes();
        int encryptedSize = remainingSize - StudyCipher.MAC_SIZE;
        input.skipBytes(remainingSize);
        int decryptedSize = gcmCipher.processBytes(inputBytes, offset, remainingSize, inputBytes,
                offset);
        assert decryptedSize <= encryptedSize;
        long decompressionStart = System.nanoTime();
        ByteBuf decrypted = null;
        if (!this.encryptionInfo.isCompressed()) {
            decrypted = Unpooled.wrappedBuffer(inputBytes, offset, decryptedSize);
        } else {
            int uncompressedSize = (int) this.encryptionInfo.getLength();
            ByteBuf decompressed = Unpooled.buffer(uncompressedSize);
            byte[] decompressedBytes = decompressed.array();
            inflater.reset();
            inflater.setInput(inputBytes, offset, decryptedSize);
            try {
                int decompressedSize = inflater.inflate(decompressedBytes,
                        decompressed.arrayOffset(), uncompressedSize);
                decompressed.writerIndex(decompressedSize);
            } catch (DataFormatException e) {
                throw new StudyCryptoException(e);
            }
            decrypted = decompressed;
        }
        long decompressionEnd = System.nanoTime();
        LOG.trace("{}\t{}\t", decompressionStart - decryptionStart, decompressionEnd
                - decompressionStart);
        return decrypted;
    }

    @Override
    protected ByteBuf updateAuthenticate(ByteBuf input) throws StudyCryptoException {

        if (!input.hasArray()) {
            throw new StudyCryptoException("Input buffer must be backed by a byte array");
        }
        ByteBuf authenticated = input.duplicate();
        processAAD(input);
        long authenticationStart = System.nanoTime();
        byte[] inputBytes = input.array();
        int offset = input.arrayOffset() + input.readerIndex();
        int remainingSize = input.readableBytes();
        input.skipBytes(remainingSize);
        while (remainingSize > 0) {
            int bytesToAuthenticate = Math.min(CHUNK_SIZE, remainingSize);
            int bytesAuthenticated = gcmCipher.processBytes(inputBytes, offset,
                    bytesToAuthenticate, decryptedChunkBytes, 0);
            assert bytesAuthenticated <= bytesToAuthenticate;
            offset += bytesToAuthenticate;
            remainingSize -= bytesToAuthenticate;
        }
        long authenticationEnd = System.nanoTime();
        LOG.trace("{}\t", authenticationEnd - authenticationStart);
        return authenticated;
    }

    @Override
    protected ByteBuf finish() throws BadMACException, StudyCryptoException {

        long macProcessingStart = System.nanoTime();
        int tailSize = 0;
        try {
            tailSize = gcmCipher.doFinal(tailBytes, 0);
        } catch (InvalidCipherTextException e) {
            throw new BadMACException(e);
        }
        long macProcessingEnd = System.nanoTime();
        LOG.trace("{}\t", macProcessingEnd - macProcessingStart);
        if (this.mode == OperationMode.DECRYPT && this.encryptionInfo.isCompressed()) {
            long finalDecompressionStart = System.nanoTime();
            ByteBuf decompressedTail = null;
            if (tailSize > 0) {
                int remainingSize = (int) (this.encryptionInfo.getLength() - inflater
                        .getBytesWritten());
                decompressedTail = Unpooled.buffer(remainingSize);
                byte[] decompressedTailBytes = decompressedTail.array();
                int offset = decompressedTail.arrayOffset();
                inflater.setInput(tailBytes, 0, tailSize);
                try {
                    int decompressedTailSize = inflater.inflate(decompressedTailBytes, offset,
                            remainingSize);
                    assert decompressedTailSize == remainingSize;
                    decompressedTail.writerIndex(decompressedTailSize);
                } catch (DataFormatException e) {
                    throw new StudyCryptoException(e);
                }
            }
            long finalDecompressionEnd = System.nanoTime();
            LOG.trace("{}\t", finalDecompressionEnd - finalDecompressionStart);
            return decompressedTail;
        } else if (this.mode == OperationMode.AUTHENTICATE) {
            return null;
        } else {
            return Unpooled.wrappedBuffer(tailBytes, 0, tailSize);
        }
    }
}
