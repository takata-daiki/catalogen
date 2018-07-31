package tr.com.cs.banking.infrastructure.utility.sqlloader.utility;

public interface Encryptor {
	byte [] encrypt		(byte [] data, String password);
	byte [] decrypt 		(byte [] data, String password);
}
