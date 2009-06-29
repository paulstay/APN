package com.teag.util;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zcalc.zCalc;

/**
 * @author Paul Stay
 * Description Utilities.java
 * 
 * Copyright @2005 The Estate Advisory Group and NoboundsIt
 */

public class Utilities {

	public static int CalcAge(String date) {
	    SimpleDateFormat df = new SimpleDateFormat("M/d/y");
	    Date birthday = df.parse(date, new ParsePosition(0));
	    Calendar calendarA = Calendar.getInstance();
	    Calendar calendarB = Calendar.getInstance();  // right now!

        int multiplier = -1;
        calendarA.setTime( birthday);
	    
	    int years = calendarA.get(Calendar.YEAR) - calendarB.get(Calendar.YEAR);
	    int months = calendarA.get(Calendar.MONTH) - calendarB.get(Calendar.MONTH);
	    int days = calendarA.get(Calendar.DAY_OF_MONTH) - calendarB.get(Calendar.DAY_OF_MONTH);
	    if (years > 0 && (months < 0 || (months == 0 && days < 0))) {
	        years -= 1;
	    }
	    return years * multiplier;
	}
	
	public static String cashFormat(double a) {
	    DecimalFormat number = new DecimalFormat("###,###");
	    return number.format(Math.floor(a/1000.0));
	}
	
	public static String cFormat(double a) {
	    DecimalFormat number = new DecimalFormat("###,###,###");
	    return number.format(a);
	}
	
	public static double getDouble(String p) {
		DecimalFormat df = new DecimalFormat("#########.##");
		try {
			return df.parse(p).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static double getFetMaxRate(int year) {
		double fet = 0;
		zCalc zc = new zCalc();
		zc.StartUp();
		fet = zc.zFETMAXRATE(year,0);
		zc.ShutDown();
		return fet;
	}
	
	public static double getLifeExp(int age, String gender) {
		int nAge = age;
		// Differences between female and male.
		if( gender.equalsIgnoreCase("M")) {
			nAge = nAge +2;
		} else {
			nAge = nAge - 4;
		}
		double le = 0;
		zCalc zc = new zCalc();
		zc.StartUp();
		le = zc.zLE(nAge,0,0,0,0,0,0);
		zc.ShutDown();
		return le;
	}
	
	public static double getPercent(String p) {
		DecimalFormat percentFormat = new DecimalFormat("0.00%");
	    double v = 0.0;
		if( !p.endsWith("%")){
		    p += "%";
		}

		try {
		    v = percentFormat.parse(p).doubleValue();
		} catch (Exception e) {
		    System.out.println("Error parsing percent format");
		}
		
		return v;
	}
	
	public static String moneyFormat(double a) {
		DecimalFormat number = new DecimalFormat("$###,###,###");
		return number.format(a);
		
	}
	
	public static String percentFormat(double a) {
	    DecimalFormat number = new DecimalFormat("##.##%");
	    return number.format(a);
	}
}
