package com.estate.servlet;

/**
 * 
 * @author Paul Stay
 * date: September 2008
 * Description: Utilities to convert parameters to appropriate 
 * String, Float, Double, Integer, Long, Date, etc. from a request parameter, and
 * to provide a mechanism for a default.
 *
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	// Not really much processing here, as the request usually already is a string parameter.
	public static String getStringParameter(HttpServletRequest request, String param, String defStr){
		String str = request.getParameter(param);
		if(str != null) {
			return str;
		} 
		return defStr;
	}
	
	public static double getPercentParameter(HttpServletRequest request, String param, double defValue) {
		double p = defValue;
		String input = request.getParameter(param);
		String pattern = "##.####%";
		
		if((input == null) || (input.equals(""))) {
			return p;
		}
		
		// Make sure we have a % at the end of the string, if not add one.
		if(input.indexOf('%') < 0) {
			input = input + "%";
		}
		
		NumberFormat parser = null;
		if(!pattern.equals("")) {
			parser = new DecimalFormat(pattern);
		} else {
			parser = NumberFormat.getPercentInstance();
		}
		
		Number parsed = null;
		try {
			parsed = parser.parse(input);
		} catch(ParseException pe) {
			return defValue;
		}
	
		return parsed.doubleValue();

		
	}
	
	public static double getDoubleParameter(HttpServletRequest request, String param, double defValue) {
		// Need to change this to parse text with commas.
		String sValue = request.getParameter(param);
		if(sValue == null || sValue == "")
			return defValue;
		NumberFormat parser = null;
		String input = null;
		Number retValue = 0;

		input = sValue;
		parser = NumberFormat.getInstance();
		
		try {
			retValue = parser.parse(input);
		} catch(ParseException pe) {
			return defValue;
		}
		return retValue.doubleValue();
		

	}
	
	public static int getIntegerParameter(HttpServletRequest request, String param, int defValue) {
		String sValue = request.getParameter(param);
		if(sValue == null || sValue == "")
			return defValue;
		NumberFormat parser = null;
		String input = null;
		Number retValue = 0;

		input = sValue;
		parser = NumberFormat.getInstance();
		
		try {
			retValue = parser.parse(input);
		} catch(ParseException pe) {
			return defValue;
		}
		return retValue.intValue();
	}
	
	public static float getFloatParameter(HttpServletRequest request, String param, float defValue) {
		String sValue = request.getParameter(param);
		if(sValue == null || sValue == "")
			return defValue;
		NumberFormat parser = null;
		String input = null;
		Number retValue = 0;

		input = sValue;
		parser = NumberFormat.getInstance();
		
		try {
			retValue = parser.parse(input);
		} catch(ParseException pe) {
			return defValue;
		}
		return retValue.floatValue();	
	}
	
	public static long getLongParameter(HttpServletRequest request, String param, long defValue) {
		String sValue = request.getParameter(param);
		if(sValue == null || sValue == "")
			return defValue;
		NumberFormat parser = null;
		String input = null;
		Number retValue = 0;

		input = sValue;
		parser = NumberFormat.getInstance();
		
		try {
			retValue = parser.parse(input);
		} catch(ParseException pe) {
			return defValue;
		}
		return retValue.longValue();
	}
	
	public static Date getDateParameter(HttpServletRequest request, String param, Date defValue) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("M/d/yyyy");
		String sValue = request.getParameter(param);
		if(sValue == null || sValue == "")
			return defValue;
		Date now = new Date();
		
		try{
			return df.parse(sValue);
		} catch(ParseException e) {
			if(defValue != null)
				return defValue;
		}
		return now;
	}
	
	public static boolean getBooleanParameter(HttpServletRequest request, String param, boolean defValue){
		String sValue = request.getParameter(param);
		if(sValue == null || sValue == "")
			return defValue;
		if((sValue.equalsIgnoreCase("true") || sValue.equalsIgnoreCase("T"))) {
			return true;
		} else if((sValue.equalsIgnoreCase("false") || sValue.equalsIgnoreCase("F"))) {
			return false;
		}
		return defValue;
	}
}
