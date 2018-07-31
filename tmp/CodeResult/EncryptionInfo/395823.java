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
 * A simple Java bean of information about an AS2 Partner with whom messages could be exchanged
 *
 * @author asankha
 */
public class Partner {

    /** The Partners AS2 identifier */
    private final String as2Identifier;
    /** Name of the partner */
    private String name;
    /** Description for the partner */
    private String description;
    /** URL for the Partner AS2 endpoint */
    private String url;
    /** signature information including whether to sign messages, digest algorithm and encryption algorithm used */
    private SignatureInfo signatureInfo;
    /** encryption information including whether to encrypt, the encryption algorithm */
    private EncryptionInfo encryptionInfo;
    /** should messages sent to this partner be compressed before sign/encrypt ? */
    private boolean compressBefore;
    /** should messages sent to this partner be compressed after sign/encrypt ? */
    private boolean compressAfter;
    /** Should we request for a MDN from this partner? */
    private boolean requestMDN;
    /** Should we request for a signed MDN from this partner? */
    private boolean requestSignedMDN;
    /** Should we request for an async MDN from this partner? */
    private boolean requestAsyncMDN;
    /** The Async receipt URL if an async MDN is requested. (This should be the externally visible URL) */
    private String asyncReceiptURL;
    /** Subject for messages sent to this partner */
    private String messageSubject;
    /** The alias of the certificate with which encryption should be performed when sending to this partner */
    private String encryptCertAlias;

    public Partner(String as2Identifier) {
        this.as2Identifier = as2Identifier;
        this.name = as2Identifier;
    }

    public String getAs2Identifier() {
        return as2Identifier;
    }

    public String getName() {
        return name == null ? as2Identifier : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SignatureInfo getSignatureInfo() {
        return signatureInfo;
    }

    public void setSignatureInfo(SignatureInfo signatureInfo) {
        this.signatureInfo = signatureInfo;
    }

    public EncryptionInfo getEncryptionInfo() {
        return encryptionInfo;
    }

    public void setEncryptionInfo(EncryptionInfo encryptionInfo) {
        this.encryptionInfo = encryptionInfo;
    }

    public String getEncryptCertAlias() {
        return encryptCertAlias;
    }

    public void setEncryptCertAlias(String encryptCertAlias) {
        this.encryptCertAlias = encryptCertAlias;
    }

    public boolean isCompressBefore() {
        return compressBefore;
    }

    public void setCompressBefore(boolean compressBefore) {
        this.compressBefore = compressBefore;
    }

    public boolean isCompressAfter() {
        return compressAfter;
    }

    public void setCompressAfter(boolean compressAfter) {
        this.compressAfter = compressAfter;
    }

    public boolean isRequestSignedMDN() {
        return requestSignedMDN;
    }

    public void setRequestSignedMDN(boolean requestSignedMDN) {
        this.requestSignedMDN = requestSignedMDN;
    }

    public String getAsyncReceiptURL() {
        return asyncReceiptURL;
    }

    public void setAsyncReceiptURL(String asyncReceiptURL) {
        this.asyncReceiptURL = asyncReceiptURL;
    }

    public String getMessageSubject() {
        return messageSubject == null ? "AS2 Request Message" : messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public boolean isRequestMDN() {
        return requestMDN;
    }

    public void setRequestMDN(boolean requestMDN) {
        this.requestMDN = requestMDN;
    }

    public boolean isRequestAsyncMDN() {
        return requestAsyncMDN;
    }

    public void setRequestAsyncMDN(boolean requestAsyncMDN) {
        this.requestAsyncMDN = requestAsyncMDN;
    }
}
