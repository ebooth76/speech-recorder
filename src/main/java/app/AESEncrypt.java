package app;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESEncrypt {

	public static void encrypt(String key, File inputFile, File outputFile) {
		doCrypt(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
	}

	public static void decrypt(String key, File inputFile, File outputFile) {
		doCrypt(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
	}

	private static void doCrypt(int cipherMode, String key, File inputFile, File outputFile) {
		try {

			// generate the key and cipher
			Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(cipherMode, secretKey);

			// get the bytes from the provided input file
			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			// encrypt or decrypt the bytes
			byte[] outputBytes = cipher.doFinal(inputBytes);

			// write bytes to file
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

		} catch (NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidKeyException | BadPaddingException
				| IllegalBlockSizeException | IOException e) {
			if (cipherMode == Cipher.ENCRYPT_MODE) {
				System.out.println("Error encrypting file " + Arrays.toString(e.getStackTrace()));
			} else {
				System.out.println("Error decrypting file " + Arrays.toString(e.getStackTrace()));
			}
		}
	}

	public static void test() {

		String key = "This is a secret"; // must be 16 bytes
		File inputFile = new File("10001-90210-01803.wav");
		File encryptedFile = new File("10001-90210-01803-encrypt.wav");
		File decryptedFile = new File("10001-90210-01803-decrypt.wav");

		try {
			AESEncrypt.doCrypt(Cipher.ENCRYPT_MODE, key, inputFile, encryptedFile);
			AESEncrypt.doCrypt(Cipher.DECRYPT_MODE, key, encryptedFile, decryptedFile);
			System.out.println("Encryption success");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
}

