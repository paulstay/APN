package com.teag.util;

public class StringUtil {

	public static void main(String args[]) {
		String s = "a' or 'a'='a";
		
		System.out.println(StringUtil.removeChar(s,"'".charAt(0)));
	}
	
	// Used to replace strings that might cause problems with mysql.
	public static String removeChar(String s, char c) {
		   StringBuffer r = new StringBuffer("");
		   for (int i = 0; i < s.length(); i ++) {
		      if (s.charAt(i) != c) 
		    	  r.append(s.charAt(i));
		   }
		   return r.toString();
	}
}
