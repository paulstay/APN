package com.estate.security;

/**
 * @author Paul Stay
 * Description; Generate a random password, specifying length, and using
 * the ascii character set, except l,0,1. We also will use combination of numbers
 * and letter (uppercase, lowercase). 
 * Use the SecureRandom function as it is a much better random number generator.
 * Date: Oct 2008
 */

import java.security.SecureRandom;

public class PasswordGenerator {

	int length = 6; // Default length of a password is 6 characters

	SecureRandom sr = new SecureRandom();
	
	/*
	 * Set of characters that is valid. Must be printable, memorable, and "won't
	 * break HTML" (i.e., not ' <', '>', '&', '=', ...). or break shell commands
	 * (i.e., not ' <', '>', '$', '!', ...). I, L and O are good to leave out,
	 * as are numeric zero and one.
	 */
	protected static char[] goodChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
			'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'2', '3', '4', '5', '6', '7', '8', '9', '$', '!', '@','*','$','?' };


	public String getPass() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i < length; i++) {
			sb.append(goodChar[sr.nextInt(goodChar.length)]);
		}
		return sb.toString();
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PasswordGenerator pg = new PasswordGenerator();
		pg.setLength(8);
		for(int i=0; i < 10; i++) {
			System.out.println(pg.getPass());
		}

	}

}
