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

import org.adroitlogic.ultraesb.api.ErrorInfo;

import java.util.UUID;

/**
 * Holds processing result information for an AS2 message send operation
 *
 * @author asankha
 */
public class SendInfo {

    /** the UltraESB message UUID */
    private final UUID messageUUID;
    /** AS2 version */
    private String version;
    /** AS2 to */
    private String to;
    /** AS2 from */
    private String from;
    /** AS2 message subject */
    private String subject;
    /** message ID */
    private String msgID;
    /** original message ID for an MDN for which it applies */
    private String originalMessageID;
    /** error info */
    private ErrorInfo errorInfo;
    /** MIC of the prepared message */
    private String mic;
    /** the signature info of the message */
    private SignatureInfo signatureInfo;
    /** the encryption info of the message */
    private EncryptionInfo encryptionInfo;
    /** is the message compressed */
    private boolean compressed;
    /** is an MDN requested */
    private boolean mdnRequested;

    /** Partner URL to be sent to */
    private String partnerURL = null;

    public SendInfo(UUID messageUUID) {
        this.messageUUID = messageUUID;
    }

    public UUID getMessageUUID() {
        return messageUUID;
    }

    public String getOriginalMessageID() {
        return originalMessageID;
    }

    public void setOriginalMessageID(String originalMessageID) {
        this.originalMessageID = originalMessageID;
    }

    public String getPartnerURL() {
        return partnerURL;
    }

    public void setPartnerURL(String partnerURL) {
        this.partnerURL = partnerURL;
    }

    public String getMic() {
        return mic;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
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

    public boolean isMdnRequested() {
        return mdnRequested;
    }

    public void setMdnRequested(boolean mdnRequested) {
        this.mdnRequested = mdnRequested;
    }

    @Override
    public String toString() {
        return "SendInfo{" +
            "messageUUID=" + messageUUID +
            ", version='" + version + '\'' +
            ", to='" + to + '\'' +
            ", from='" + from + '\'' +
            ", msgID='" + msgID + '\'' +
            ", originalMessageID='" + originalMessageID + '\'' +
            ", errorInfo=" + errorInfo +
            ", mic='" + mic + '\'' +
            ", signature=" + signatureInfo +
            ", encryption=" + encryptionInfo +
            ", compressed=" + compressed +
            ", mdnRequested=" + mdnRequested +
            ", partnerURL='" + partnerURL + '\'' +
            '}';
    }
}
