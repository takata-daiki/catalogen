package cryptoppm;

import java.security.*;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.util.*;

public class Encryptor {
  // name of the character set to use for converting between characters and bytes
  public static final String CHARSET_NAME = "UTF-8";

  // cipher algorithm (must be compatible with KEY_ALGORITHM)
  public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

  // Cipher.getBlockSize() is same as IV length, in bytes
  public static final int IV_LENGTH = 16;

  public Encryptor(SecretKey secretKey) {
    mSecretKey = secretKey;
    try {
      mCipher = Cipher.getInstance(CIPHER_ALGORITHM);
      generateIV();
      mCipher.init(Cipher.ENCRYPT_MODE, mSecretKey, new IvParameterSpec(mIV));
      AlgorithmParameters params = mCipher.getParameters();
      // mIV = params.getParameterSpec(IvParameterSpec.class).getIV();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // The same IV needs to go for Decryptor
  public byte[] getIV() {
    return mIV;
  }

  public byte[] getEncryptedData(String plaintext) {
    byte[] cipherText = null;
    try {
      cipherText = mCipher.doFinal(plaintext.getBytes(CHARSET_NAME));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }

  private void generateIV() {
    mIV = new byte[IV_LENGTH];
    Random r = new SecureRandom();
    r.nextBytes(mIV);
  }

  private byte[] mIV;
  private Cipher mCipher;
  private SecretKey mSecretKey;
}
