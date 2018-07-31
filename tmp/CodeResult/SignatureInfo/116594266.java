/*
 * AdroitLogic UltraESB Enterprise Service Bus
 *
 * Copyright (c) 2010-2015 AdroitLogic Private Ltd. (http://adroitlogic.org). All Rights Reserved.
 *
 * GNU Affero General Public License Usage
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program (See LICENSE-AGPL.TXT).
 * If not, see http://www.gnu.org/licenses/agpl-3.0.html
 *
 * Commercial Usage
 *
 * Licensees holding valid UltraESB Commercial licenses may use this file in accordance with the UltraESB Commercial
 * License Agreement provided with the Software or, alternatively, in accordance with the terms contained in a written
 * agreement between you and AdroitLogic.
 *
 * If you are unsure which license is appropriate for your use, or have questions regarding the use of this file,
 * please contact AdroitLogic at info@adroitlogic.com
 */

package org.adroitlogic.as2.api;

/**
 * Defines the signature behavior whether to sign or not and if sign then the digest and encryption algorithms to be
 * used by AS2 SMIME message sending and processing
 *
 * @author Ruwan
 * @since 2.1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class SignatureInfo {

    public static final SignatureInfo DISABLED = new SignatureInfo(false, null, null);

    /** should messages sent to the assigned partner be signed? */
    private final boolean signMessage;
    /** digest algorithm to be used in signature generation */
    private final DigestAlgorithm digestAlgorithm;
    /** encryption algorithm to be used in signature generation */
    private final EncryptionAlgorithm encryptionAlgorithm;

    /**
     * Creates a signature info object with the default digest algorithm (which is SHA1) and default encryption (which
     * is extracted from the specified private key for the message sending) if message signing is set to true
     */
    public SignatureInfo() {
        this(true, DigestAlgorithm.SHA1, null);
    }

    public SignatureInfo(DigestAlgorithm digestAlgorithm, EncryptionAlgorithm encryptionAlgorithm) {
        this(true, digestAlgorithm, encryptionAlgorithm);
    }

    public SignatureInfo(String digestAlgorithm, String encryptionAlgorithm) {
        this(true, digestAlgorithm != null ? DigestAlgorithm.valueOf(digestAlgorithm.toUpperCase()) :
                DigestAlgorithm.SHA1, encryptionAlgorithm != null ?
                EncryptionAlgorithm.valueOf(encryptionAlgorithm.toUpperCase()) : null);
    }

    private SignatureInfo(boolean signMessage, DigestAlgorithm digestAlgorithm, EncryptionAlgorithm encryptionAlgorithm) {
        this.signMessage = signMessage;
        this.digestAlgorithm = digestAlgorithm;
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public boolean isSignMessage() {
        return signMessage;
    }

    public DigestAlgorithm getDigestAlgorithm() {
        return digestAlgorithm;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    /**
     * Signature encryption algorithms
     */
    public static enum EncryptionAlgorithm {
        RSA, DSA, ECDSA, RSA_PSS, GOST3410, ECGOST3410
    }

    /**
     * Signature digest algorithms
     */
    public static enum DigestAlgorithm {
        SHA1, MD5, SHA224, SHA256, SHA384, SHA512, GOST3411, RIPEMD128, RIPEMD160, RIPEMD256
    }

    @Override
    public String toString() {
        return "SignatureInfo{" +
                "signMessage=" + signMessage +
                ", digestAlgorithm=" + digestAlgorithm +
                ", encryptionAlgorithm=" + encryptionAlgorithm +
                '}';
    }
}
