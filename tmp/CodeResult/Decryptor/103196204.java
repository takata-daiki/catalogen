package com.recoverweblogicpassword.server;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.recoverweblogicpassword.client.DecryptResult;

public class Decryptor {
	private static final Logger log = Logger.getLogger(Decryptor.class.getName());
	
	public static DecryptResult decrypt(byte[] serializedSystemIni, String text) throws Exception {
        MySerializedSystemIni ssi = new MySerializedSystemIni(serializedSystemIni);

        DecryptResult dr = new DecryptResult();
        
        LineNumberReader lnr = new LineNumberReader(new StringReader(text));
        
        String prev = null;
        String curr = null;
        List<String> block = new ArrayList<String>();
        
        while(true) {
        	curr = lnr.readLine();
        	if( curr == null ) break;

        	int ln = lnr.getLineNumber();

        	String decryped = tryDecrypt(ssi,curr);
        	if( decryped == null ) {
        		// end of block? add to result
        		if( block.size() > 0 ) {
        			block.add(lineNo(ln)+curr);
        			dr.addDecryptedBlock(block);
        			
        			block = new ArrayList<String>();
        		}
        	} else {
        		if( block.size() == 0 ) {
        			if( prev != null ) block.add(lineNo(ln-1)+prev);
        		}
        		block.add(lineNo(ln)+decryped);
        	}
        	
        	prev = curr;
        }
        lnr.close();
        
        if( block.size() != 0 ) {
        	dr.addDecryptedBlock(block);
        }
        
		return dr;
	}

	private static String lineNo(int i) {
		return "line "+i+": ";		
	}

	private static String tryDecrypt(MySerializedSystemIni ssi, String s) throws Exception {
    	boolean wasDecrypted = false;

    	StringBuilder sb = new StringBuilder();
    	while(true) {
        	int des = s.indexOf("{3DES}");
        	int aes = s.indexOf("{AES}");
    		if( des < 0 && aes < 0 ) {
    			sb.append(s);
    			break;
    		}

    		String prefix = "{3DES}";
    		int pos = des;
    		if( des < 0 ) {
    			prefix = "{AES}";
    			pos = aes;
    		}
    		
    		wasDecrypted = true;
    		
    		sb.append(s.substring(0,pos));
    		
        	int beg = pos+prefix.length();
        	int end = beg;
        	while( end < s.length() ) {
        		char c = s.charAt(end);
        		if( (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') ||
        				(c=='+') || (c=='/') || (c=='=') || (c=='\\') ) {
        			end++;
        			continue;
        		}
        		break;
        	}
        	
        	String encrypted = s.substring(beg,end);
        	String decrypted = null;
        	
        	try {
        		decrypted = decryptBlock(prefix,ssi,encrypted);
        	} catch( Exception ex ) {
        		decrypted = ex.getMessage();
        		if( decrypted == null ) decrypted = "(Decryption error)";
        	}
        	sb.append(decrypted);

        	s = s.substring(end);
    	}
    	
    	if( !wasDecrypted ) return null;
    	return sb.toString();
	}

	private static String decryptBlock(String prefix,MySerializedSystemIni ssi, String encrypted) throws Exception {
		byte[] debase64d = Base64Coder.decode(encrypted);
		byte[] decrypted = ssi.decryptBytes(prefix,debase64d);
		String pwd = new String(decrypted,"UTF-8");
		log.info("decryped: "+pwd);
		return pwd;
	}
}
