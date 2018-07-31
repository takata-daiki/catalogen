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

package org.adroitlogic.as2.message;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.adroitlogic.as2.api.AttachmentDetails;
import org.adroitlogic.as2.api.EncryptionInfo;
import org.adroitlogic.as2.api.SignatureInfo;
import org.adroitlogic.as2.util.KeystoreManager;
import org.adroitlogic.as2.util.MimeHelpUtils;
import org.adroitlogic.metrics.api.MetricsEngine;
import org.adroitlogic.metrics.core.MetricsEngineImpl;
import org.adroitlogic.ultraesb.core.ConfigurationImpl;
import org.adroitlogic.ultraesb.core.MessageImpl;
import org.adroitlogic.ultraesb.core.PooledMessageFileCache;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

/**
 * @author asankha
 */
public class SMIMECreateAndParseTest extends TestCase {

    static KeystoreManager ksManager = null;
    static SMIMEMessageParser p = null;
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String HELLO_WORLD_1 = "Hello World 1";
    private static final String HELLO_WORLD_2 = "Hello World 2";
    private static final String TMP_FILE1_TXT = TMP_DIR + File.separator + "file1.txt";
    private static final String TMP_FILE2_TXT = TMP_DIR + File.separator + "file2.txt";
    private static final String IMAGE_FILENAME = "image.png";
    private static final String TMP_FILE_IMG = TMP_DIR + File.separator + IMAGE_FILENAME;
    private static final String HELLO_WORLD = "Hello World";
    private static final String TMP_MESSAGE_FILENAME = TMP_DIR + File.separator + "test.message";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String IMAGE_PNG = "image/png";

    public static Test suite() {
        return new TestSetup(new TestSuite(SMIMECreateAndParseTest.class)) {

            protected void setUp() throws Exception {

                BasicConfigurator.configure();
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

                ksManager = new KeystoreManager(
                    "samples/conf/keys/server1.jks", "JKS", "password",
                    "samples/conf/keys/trust.jks", "JKS", "password");
                ksManager.setIdentityAlias("server1");
                ksManager.setIdentityKeypass("password");
                ksManager.initializeForSigning();

                final PooledMessageFileCache fileCache = new PooledMessageFileCache(10);
                ConfigurationImpl config = new ConfigurationImpl();
                MetricsEngine metricsEngine = new MetricsEngineImpl();
                metricsEngine.init("conf");
                fileCache.setMetricsEngine(metricsEngine);
                fileCache.setId("fileCache");
                fileCache.setConfig(config);
                fileCache.start();
                p = new SMIMEMessageParser(ksManager, fileCache);

                BufferedWriter out = new BufferedWriter(new FileWriter(TMP_FILE1_TXT));
                out.write(HELLO_WORLD_1);
                out.close();

                out = new BufferedWriter(new FileWriter(TMP_FILE2_TXT));
                out.write(HELLO_WORLD_2);
                out.close();

                MimeHelpUtils.createImage(TMP_FILE_IMG);
            }

            protected void tearDown() throws Exception {
                super.tearDown();
            }
        };
    }


    //----------- plain -------------
    private void testSingleTextAttachment(SignatureInfo sign, EncryptionInfo encr, boolean cBefore, boolean cAfter) throws Exception {
        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
        attachments.add(new AttachmentDetails(HELLO_WORLD));
        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream(TMP_MESSAGE_FILENAME), sign, encr, "server1", cBefore, cAfter);

        if (!sign.isSignMessage() && !encr.isEncryptMessage() && cBefore && !cAfter) {
            System.out.println("");
        }

        ParseResult res = p.parseSMIMEMessage(new MessageImpl(true, null, null), new File(TMP_MESSAGE_FILENAME), cr.getHeaders());
        attachments = res.getAttachments();
        assertEquals(attachments.size(), 1);
        assertTrue(attachments.get(0).isText());
        assertEquals(attachments.get(0).getTextContent(), HELLO_WORLD);
    }

    private void testSingleTextFileAttachment(SignatureInfo sign, EncryptionInfo encr, boolean cBefore, boolean cAfter) throws Exception {
        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
        attachments.add(new AttachmentDetails(TMP_FILE1_TXT, TEXT_PLAIN));
        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream(TMP_MESSAGE_FILENAME), sign, encr, "server1", cBefore, cAfter);

        ParseResult res = p.parseSMIMEMessage(new MessageImpl(true, null, null), new File(TMP_MESSAGE_FILENAME), cr.getHeaders());
        attachments = res.getAttachments();
        assertEquals(attachments.size(), 1);
        assertTrue(attachments.get(0).isText());
        assertEquals(attachments.get(0).getTextContent(), HELLO_WORLD_1);
    }

    private void testSingleImageFileAttachment(SignatureInfo sign, EncryptionInfo encr, boolean cBefore, boolean cAfter) throws Exception {
        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
        attachments.add(new AttachmentDetails(TMP_FILE_IMG, IMAGE_PNG));
        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream(TMP_MESSAGE_FILENAME), sign, encr, "server1", cBefore, cAfter);

        ParseResult res = p.parseSMIMEMessage(new MessageImpl(true, null, null), new File(TMP_MESSAGE_FILENAME), cr.getHeaders());
        attachments = res.getAttachments();
        assertEquals(attachments.size(), 1);
        assertTrue(!attachments.get(0).isText());
        assertTrue(IMAGE_FILENAME.equals(attachments.get(0).getOriginalFileName()));
    }

    private void testMultipleStringOnlyAttachments(SignatureInfo sign, EncryptionInfo encr, boolean cBefore, boolean cAfter) throws Exception {
        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
        attachments.add(new AttachmentDetails(HELLO_WORLD_1));
        attachments.add(new AttachmentDetails(HELLO_WORLD_2));
        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream(TMP_MESSAGE_FILENAME), sign, encr, "server1", cBefore, cAfter);

        ParseResult res = p.parseSMIMEMessage(new MessageImpl(true, null, null), new File(TMP_MESSAGE_FILENAME), cr.getHeaders());
        attachments = res.getAttachments();
        assertEquals(attachments.size(), 2);
        assertTrue(attachments.get(0).isText());
        assertEquals(attachments.get(0).getTextContent(), HELLO_WORLD_1);
        assertTrue(attachments.get(1).isText());
        assertEquals(attachments.get(1).getTextContent(), HELLO_WORLD_2);
    }

    private void testMultipleTextFileOnlyAttachment(SignatureInfo sign, EncryptionInfo encr, boolean cBefore, boolean cAfter) throws Exception {
        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
        attachments.add(new AttachmentDetails(TMP_FILE1_TXT, TEXT_PLAIN));
        attachments.add(new AttachmentDetails(TMP_FILE2_TXT, TEXT_PLAIN));
        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream(TMP_MESSAGE_FILENAME), sign, encr, "server1", cBefore, cAfter);

        ParseResult res = p.parseSMIMEMessage(new MessageImpl(true, null, null), new File(TMP_MESSAGE_FILENAME), cr.getHeaders());
        attachments = res.getAttachments();
        assertEquals(attachments.size(), 2);
        assertTrue(attachments.get(0).isText());
        assertEquals(attachments.get(0).getTextContent(), HELLO_WORLD_1);
        assertTrue(attachments.get(1).isText());
        assertEquals(attachments.get(1).getTextContent(), HELLO_WORLD_2);
    }

    private void testMultipleTextAndImageFileAttachment(SignatureInfo sign, EncryptionInfo encr, boolean cBefore, boolean cAfter) throws Exception {
        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
        attachments.add(new AttachmentDetails(TMP_FILE1_TXT, TEXT_PLAIN));
        attachments.add(new AttachmentDetails(TMP_FILE_IMG, IMAGE_PNG));
        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream(TMP_MESSAGE_FILENAME), sign, encr, "server1", cBefore, cAfter);

        ParseResult res = p.parseSMIMEMessage(new MessageImpl(true, null, null), new File(TMP_MESSAGE_FILENAME), cr.getHeaders());
        attachments = res.getAttachments();
        assertEquals(attachments.size(), 2);
        assertTrue(attachments.get(0).isText());
        assertEquals(attachments.get(0).getTextContent(), HELLO_WORLD_1);
        assertTrue(!attachments.get(1).isText());
        assertTrue(IMAGE_FILENAME.equals(attachments.get(1).getOriginalFileName()));
    }

//    public void testSingleTextAttachment() throws Exception {
//         testSingleTextAttachment(true, true, true, true);
//    }

    //---------- real unit test invocations ----------------

    public void testSingleTextAttachment() throws Exception {
        for (int a=0; a<2; a++) {
            for (int b=0; b<2; b++) {
                for (int c=0; c<2; c++) {
                    for (int d=0; d<2; d++) {
                        testSingleTextAttachment(toSign(a), toEncrypt(b), (d==1 && c==0), (c==1 && d==0));
                    }
                }
            }
        }
    }

    public void testSingleTextFileAttachment() throws Exception {
        for (int a=0; a<2; a++) {
            for (int b=0; b<2; b++) {
                for (int c=0; c<2; c++) {
                    for (int d=0; d<2; d++) {
                        testSingleTextFileAttachment(toSign(a), toEncrypt(b), (d==1 && c==0), (c==1 && d==0));
                    }
                }
            }
        }
    }

    public void testMultipleStringOnlyAttachments() throws Exception {
        for (int a=0; a<2; a++) {
            for (int b=0; b<2; b++) {
                for (int c=0; c<2; c++) {
                    for (int d=0; d<2; d++) {
                        testMultipleStringOnlyAttachments(toSign(a), toEncrypt(b), (d==1 && c==0), (c==1 && d==0));
                    }
                }
            }
        }
    }

    public void testMultipleTextFileOnlyAttachment() throws Exception {
        for (int a=0; a<2; a++) {
            for (int b=0; b<2; b++) {
                for (int c=0; c<2; c++) {
                    for (int d=0; d<2; d++) {
                        testMultipleTextFileOnlyAttachment(toSign(a), toEncrypt(b), (d==1 && c==0), (c==1 && d==0));
                    }
                }
            }
        }
    }

    public void testSingleImageFileAttachment() throws Exception {
        for (int a=0; a<2; a++) {
            for (int b=0; b<2; b++) {
                for (int c=0; c<2; c++) {
                    for (int d=0; d<2; d++) {
                        testSingleImageFileAttachment(toSign(a), toEncrypt(b), (d==1 && c==0), (c==1 && d==0));
                    }
                }
            }
        }
    }

    public void testMultipleTextAndImageFileAttachment() throws Exception {
        for (int a=0; a<2; a++) {
            for (int b=0; b<2; b++) {
                for (int c=0; c<2; c++) {
                    for (int d=0; d<2; d++) {
                        testMultipleTextAndImageFileAttachment(toSign(a), toEncrypt(b), (d==1 && c==0), (c==1 && d==0));
                    }
                }
            }
        }
    }

    private SignatureInfo toSign(int a) {
        return a == 0 ? new SignatureInfo() : SignatureInfo.DISABLED;
    }

    private EncryptionInfo toEncrypt(int b) {
        return b == 0 ? new EncryptionInfo() : EncryptionInfo.DISABLED;
    }

    //----------------------------------------------------------------------
//    public void testCustom() throws Exception {
//        boolean sign = true;
//        boolean encr = false;
//        boolean cBefore = false;
//        boolean cAfter = false;
//
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Disposition-Notification-To", "someone@adroitlogic.com");
//        headers.put("Disposition-Notification-Options", "signed-receipt-protocol=optional, pkcs7-signature; signed-receipt-micalg=optional, sha1");
//        headers.put("AS2-Version", "1.2");
//        headers.put("AS2-To", "to-owner");
//        headers.put("AS2-From", "from-owner");
//        headers.put("Message-ID", "<1213131231231231@foo>");
//
//        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
//        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
//        attachments.add(new AttachmentDetails(HELLO_WORLD));
//        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream(TMP_MESSAGE_FILENAME), sign, encr, cBefore, cAfter);
//        attachments = null;
//
//        ParsingResult parsingResult = p.parseSMIMEMessage(new FileInputStream(TMP_MESSAGE_FILENAME), headers);
//        attachments = parsingResult.getAttachments();
//        assertEquals(attachments.size(), 1);
//        assertTrue(attachments.get(0).isText());
//        assertEquals(attachments.get(0).getTextContent(), HELLO_WORLD);
//
//        AS2Manager m = new AS2Manager("adroitlogic", "asankha", ksManager);
//        m.notifyNewAS2Message(headers, parsingResult);
//    }

//    public void testFirstLiveMessage() throws Exception {
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Disposition-Notification-To", "someone@adroitlogic.com");
//        headers.put("Disposition-Notification-Options", "signed-receipt-protocol=optional, pkcs7-signature; signed-receipt-micalg=optional, sha1");
//        headers.put("AS2-Version", "1.2");
//        headers.put("AS2-To", "to-owner");
//        headers.put("AS2-From", "from-asankha");
//        headers.put("Message-ID", "<1213131231231231@adroitlogic.com>");
//
//        SMIMEMessageCreator as2 = new SMIMEMessageCreator(ksManager);
//        List<AttachmentDetails> attachments = new ArrayList<AttachmentDetails>();
//        attachments.add(new AttachmentDetails("/home/asankha/UltraRuntime/sample/test_data_2.edi", "application/EDI-X12"));
//        CreationResult cr = as2.createSMIMEMessage(attachments, new FileOutputStream("/tmp/first.message"), true, true, false, false);
//    }
//
//    public void testFirstLiveMessage() throws Exception {
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/pkcs7-mime; name=\"smime.p7m\"; smime-type=enveloped-data");
////        headers.put("MIME-Version", "1.0");
////        headers.put("Content-Transfer-Encoding", "binary");
//        headers.put("Message-ID", "<963427735.5.1253038614853.JavaMail.asankha@asankha>");
//
//        SMIMEMessageParser as2 = new SMIMEMessageParser(ksManager);
//        ParsingResult pr = as2.parseSMIMEMessage(new File("/tmp/check.message"), headers);
//        System.out.println("pr " + pr.getMicResult());
//    }
}
