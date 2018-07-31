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
 * Defines the encryption behavior whether to encrypt or not and if encrypt then the encryption algorithm to be used by
 * AS2 SMIME message sending and processing
 *
 * @author Ruwan
 * @since 2.1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class EncryptionInfo {

    public static final EncryptionInfo DISABLED = new EncryptionInfo(false, null);

    /** should messages sent to the relevant partner be encrypted? */
    private final boolean encryptMessage;
    /** encryption algorithm to be used */
    private final Algorithm algorithm;

    public EncryptionInfo() {
        this(true, Algorithm.DES_EDE3_CBC);
    }

    public EncryptionInfo(Algorithm algorithm) {
        this(true, algorithm);
    }

    public EncryptionInfo(String algorithm) {
        this(true, algorithm != null ? Algorithm.valueOf(algorithm.toUpperCase()) : Algorithm.DES_EDE3_CBC);
    }

    private EncryptionInfo(boolean encryptMessage, Algorithm algorithm) {
        this.encryptMessage = encryptMessage;
        this.algorithm = algorithm;
    }

    public boolean isEncryptMessage() {
        return encryptMessage;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Defines the constants for the supported encryption algorithms to be used by AS2 SMIME message sending
     * and processing
     */
    public static enum Algorithm {

        DES_EDE3_CBC, RC2_CBC, IDEA_CBC, CAST5_CBC,

        AES128_CBC, AES192_CBC, AES256_CBC,

        CAMELLIA128_CBC, CAMELLIA192_CBC, CAMELLIA256_CBC,

        SEED_CBC,

        DES_EDE3_WRAP,

        AES128_WRAP, AES256_WRAP,

        CAMELLIA128_WRAP, CAMELLIA192_WRAP, CAMELLIA256_WRAP,

        SEED_WRAP,

        ECDH_SHA1KDF
    }

    @Override
    public String toString() {
        return "EncryptionInfo{" +
                "encryptMessage=" + encryptMessage +
                ", algorithm=" + algorithm +
                '}';
    }
}
