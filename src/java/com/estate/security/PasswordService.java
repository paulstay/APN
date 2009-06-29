package com.estate.security;

/**
 * Password Service class takes a clear text pasword
 * and encrypts it with a SHA message digest algorithm
 * which can then be used to compare the password found in the 
 * database.
 * @author Paul Stay
 * copyright @2009 APN
 * May 2009
 *
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordService {

	private static PasswordService instance;

	private PasswordService() {
	}

	public synchronized String encrypt(String plaintext) throws Exception {
		MessageDigest md = null;
		try {
            // For now we will use the MD5, we might change this to SHA later.
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e.getMessage());
		}
		try {
			// Change the plaintext to bytes, and pass to teh message digest
			md.update(plaintext.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}

		byte raw[] = md.digest();
		//String hash = (new BASE64Encoder()).encode(raw);
        // According to one text, the Glassfish server expects the result to be hex encoded.
        String hash;
        StringBuffer buffer = new StringBuffer("");
        for(int i=0; i < raw.length; i++){
            String hexValue = Integer.toHexString(0xff & raw[i]);
            if(hexValue.length() == 1){
                buffer.append("0");
            }
            buffer.append(hexValue);
        }

        hash = buffer.toString();
        return hash;
	}

	public static synchronized PasswordService getInstance() // step 1
	{
		if (instance == null) {
			instance = new PasswordService();
		}
		return instance;
	}

	public static void main(String args[]) {
		String clearText = "cadd1sfly";
		try {
			String enc = PasswordService.getInstance().encrypt(clearText);
			System.out.println(enc + " : " + enc.length());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
