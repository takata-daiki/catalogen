package be.vezel.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class Crypto
{

	public final static String SYMM_CIPHER_ALGO = "AES/CBC/PKCS7Padding";
	public final static String SYMM_KEY_ALGO = "AES";
	public final static int SYMM_KEY_LENGTH = 128;
	
	public final static String ASYMM_ALGO = "RSA";
	public final static int ASYMM_KEY_LENGTH = 2048;
	
	private final static Provider CRYPTO_PROVIDER = new BouncyCastleProvider();
	
	
	public static KeyStore loadKeyStoreFromFile(File f, String storeFormat, char[] password) throws Exception
	{
		
		
		if (f.exists() && f.isFile()) {
			
			
			KeyStore store = null;
			FileInputStream storeInputstream = new FileInputStream(f);
			System.out.println("Loading keystore: " + f.getName());
			store = KeyStore.getInstance(storeFormat, CRYPTO_PROVIDER);
			store.load(storeInputstream, password);
			storeInputstream.close();

			return store;
			
		} else {
			throw new IllegalArgumentException("Provided keystore file is not valid.");
		}
	}
	
	public static Cipher createAsymmetricCipher(int operationMode, Key key)
	{
		try {
			Cipher c = Cipher.getInstance(ASYMM_ALGO);
			c.init(operationMode, key);
			return c;
			
        } catch (Exception e) {
        	
        	e.printStackTrace();
        	throw new IllegalStateException("Could not create the asymmetric cipher.", e);
        }
	}
	
	public static Cipher createSymmetricCipher(int operationMode, Key key, byte[] iv)
	{
		try {
			
			Cipher c = Cipher.getInstance(SYMM_CIPHER_ALGO, CRYPTO_PROVIDER);
			
			if (iv != null && iv.length > 0) {
				c.init(operationMode, key, new IvParameterSpec(iv));
			} else {
				c.init(operationMode, key);
			}
			
			return c;
			
        } catch (Exception e) {
        	
        	e.printStackTrace();
        	throw new IllegalStateException("Could not create the symmetric cipher.", e);
        }
	}
	
	public static Cipher createSymmetricCipher(int operationMode, SecretKey key)
	{
		return createSymmetricCipher(operationMode, key, null);
	}
	
	public static SecretKey generateSymmetricKey()
	{
		try {
			
		    KeyGenerator keyGenerator = KeyGenerator.getInstance(SYMM_KEY_ALGO, CRYPTO_PROVIDER);
		    keyGenerator.init(SYMM_KEY_LENGTH, new SecureRandom(new SecureRandom().generateSeed(64)));
		    return keyGenerator.generateKey();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Wrong algorithm for key generation was provided.", e);
		}
	}
	
	public static KeyPair generateKeypair()
	{
		try {
			
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ASYMM_ALGO, CRYPTO_PROVIDER);
			keyPairGenerator.initialize(ASYMM_KEY_LENGTH);
			return keyPairGenerator.generateKeyPair();
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Wrong algorithm for keypair generation was provided.", e);
		}
	}
	
	
	public static void main(String[] args) throws Exception
    {
		
		Security.addProvider(CRYPTO_PROVIDER);
		
		//Generate symmetric key
		SecretKey symmKey = generateSymmetricKey();
	    System.out.println(Arrays.toString(symmKey.getEncoded()));
		System.out.println((symmKey.getEncoded().length * 8) + "bits");
		System.out.println();
	    
		//Encryption & decryption without unwrapped key
	    Cipher encryptor = createSymmetricCipher(Cipher.ENCRYPT_MODE, symmKey);
	    byte[] cipherText = encryptor.doFinal("TESTING 1 2 3".getBytes());
	    System.out.println(Arrays.toString(cipherText));
		System.out.println((cipherText.length * 8) + "bits");
		
		Cipher decryptor = createSymmetricCipher(Cipher.DECRYPT_MODE, symmKey, encryptor.getIV());
	    byte[] decrypted = decryptor.doFinal(cipherText);
	    System.out.println(new String(decrypted));
	    System.out.println();
	    
		//Generate keypair
		KeyPair keyPair = generateKeypair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		//Encryption & decryption with unwrapped key
		Cipher keyWrapper = createAsymmetricCipher(Cipher.WRAP_MODE, publicKey);
		byte[] wrappedKey = keyWrapper.wrap(symmKey);
	    Cipher unwrapper = createAsymmetricCipher(Cipher.UNWRAP_MODE, privateKey);
	    symmKey = (SecretKey) unwrapper.unwrap(wrappedKey, SYMM_KEY_ALGO, Cipher.SECRET_KEY);
	    
	    encryptor = createSymmetricCipher(Cipher.ENCRYPT_MODE, symmKey);
	    cipherText = encryptor.doFinal("TESTING 1 2 3".getBytes());
	    System.out.println(Arrays.toString(cipherText));
		System.out.println((cipherText.length * 8) + "bits");
		
		decryptor = createSymmetricCipher(Cipher.DECRYPT_MODE, symmKey, encryptor.getIV());
	    decrypted = decryptor.doFinal(cipherText);
	    System.out.println(new String(decrypted));
    }
	
}
