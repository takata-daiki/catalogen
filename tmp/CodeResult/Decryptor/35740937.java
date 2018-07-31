package cryptoppm;

import java.security.*;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.util.*;

public class Decryptor {
  // name of the character set to use for converting between characters and bytes
  public static final String CHARSET_NAME = Encryptor.CHARSET_NAME;

  // cipher algorithm (must be compatible with KEY_ALGORITHM)
  public static final String CIPHER_ALGORITHM = Encryptor.CIPHER_ALGORITHM;
  
  public Decryptor(SecretKey secretKey, byte[] iv) {
    mSecretKey = secretKey;
    mIV = iv;
    try {
      mCipher = Cipher.getInstance(CIPHER_ALGORITHM);
      mCipher.init(Cipher.DECRYPT_MODE, mSecretKey,  new IvParameterSpec(mIV));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // The same IV from Encryptor must come here :-)
  public byte[] getIV() {
    return mIV;
  }

  public String getDecryptedData(byte[] cipherText) {
    String plaintext = null;
    try {
      plaintext = new String(mCipher.doFinal(cipherText), CHARSET_NAME);
    } catch (Exception e) {
      e.printStackTrace();
       ui.throwAndDie("ERROR: Wrong Password!");
    }
    return plaintext;
  }

  private byte[] mIV;
  private Cipher mCipher;
  private SecretKey mSecretKey;
}
