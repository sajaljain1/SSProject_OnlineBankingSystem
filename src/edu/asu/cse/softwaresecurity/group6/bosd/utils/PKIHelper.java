package edu.asu.cse.softwaresecurity.group6.bosd.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;

public class PKIHelper {
	private static byte[] encrypt(byte[] inpBytes, PublicKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(inpBytes);
	}
	private static byte[] decrypt(byte[] inpBytes, PrivateKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(inpBytes);
	}

	public static KeyPair generateKeys() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(512); // 512 is the keysize.
		KeyPair kp = kpg.generateKeyPair();
		return kp;
	}
	
	public static boolean verifyKeys(byte[] publicKey, byte[] privateKey) throws Exception{
		KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
		PublicKey pubk = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
		PrivateKey prvk = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		SecureRandom random = new SecureRandom();
		String randomString = new BigInteger(130, random).toString(32);
		byte[] dataBytes = randomString.getBytes();

		byte[] encBytes = encrypt(dataBytes, pubk);
		byte[] decBytes = decrypt(encBytes, prvk);

		boolean expected = java.util.Arrays.equals(dataBytes, decBytes);
		return expected;
	}
	
	public static void main(String[] args){
		try {
			KeyPair pair = generateKeys();
//			System.out.println(new String(pair.getPublic().getEncoded()));
//			System.out.println(new String(pair.getPrivate().getEncoded()));
			String pu = Base64.encodeBase64String(pair.getPublic().getEncoded());
			String pv =  Base64.encodeBase64String(pair.getPrivate().getEncoded());
			System.out.println("public :"+pu);
			System.out.println("private :"+pv);
			System.out.println(java.util.Arrays.equals(pair.getPrivate().getEncoded(), Base64.decodeBase64(pv)));
			boolean match = verifyKeys(Base64.decodeBase64(pu), pair.getPrivate().getEncoded());
			System.out.println(match);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
