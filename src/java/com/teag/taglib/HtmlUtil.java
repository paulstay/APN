package com.teag.taglib;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HtmlUtil {
	
	public String filterHtml(String text) {
		if(!hasSpecialChars(text))
			return text;
		
		StringBuffer filtered = new StringBuffer();
		for(int i=0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch(c) {
			case '<': filtered.append("&lt;"); break;
			case '>': filtered.append("&gt;"); break;
			case '"': filtered.append("&quot;"); break;
			case '&': filtered.append("&amp;"); break;
			default: filtered.append(c);
			}
		}
		return filtered.toString();
	}
	
	public String fmtDate(String type, String pattern, String value) {
		SimpleDateFormat parser = null;
		
		if(type.equalsIgnoreCase("date")){
			if( pattern != null && !pattern.equals("")){
				parser = new SimpleDateFormat(pattern);
			} else {
				parser = new SimpleDateFormat("M/d/yyyy");
			}
			
		} else {
			return "";
		}
		return parser.format(value);
	}
	
	public String fmtValue(String type, String pattern, String value) {
		NumberFormat parser = null;
		double input = 0;
		String retValue = null;
		
		// First convert string to a decimal format (assume that value is the string representation of a double.....
		try {
			input = parseValue(value);
		}catch (IOException e) {
			System.out.println(e.getMessage());
			return "";
		}
		
		if(type.equalsIgnoreCase("percent")){
			if( pattern != null && !pattern.equals("")){
				parser = new DecimalFormat(pattern);
			} else {
				parser = NumberFormat.getPercentInstance();
			}
		}
		
		if(type.equalsIgnoreCase("currency")) {
			if(pattern != null && !pattern.equals("")) {
				parser = new DecimalFormat(pattern);
			} else {
				parser = NumberFormat.getCurrencyInstance();
			}
		}
		
		if(type.equalsIgnoreCase("number")) {
			if(pattern!=null && !pattern.equals("")) {
				parser = new DecimalFormat(pattern);
			} else {
				parser = NumberFormat.getInstance();
			}
			
		}

		retValue = parser.format(input);
		
		return retValue;
	}
	
	public boolean hasSpecialChars(String input) {
		boolean flag = false;
		if(input != null && input.length()> 0) {
			char c;
			for(int i=0; i < input.length(); i++){
				c = input.charAt(i);
				switch(c) {
				case '<': flag = true; break;
				case '>': flag = true; break;
				case '"': flag = true; break;
				case '&': flag = true; break;
				}
			}
		}
		return flag;
	}

	public double parseValue(String value) throws IOException {
		NumberFormat parser = null;
		String input = null;
		Number retValue = 0;

		input = value;
			parser = NumberFormat.getInstance();
		
		try {
			retValue = parser.parse(input);
		} catch(ParseException pe) {
			throw new IOException(pe);
		}
		return retValue.doubleValue();
	}
	
}
