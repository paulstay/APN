package com.estate.validate;

/**
 * Validate Percent
 * @author Paul Stay
 * Date September 2008
 * 
 * Check the string for a valid percent, and convert to double. If it does not convert return a zero
 *
 */

import java.text.DecimalFormat;

public class ValidatePercent {

	public static double checkPercent(String value) {
		double x = 0;
		
		DecimalFormat df = new DecimalFormat("##.####%");
		DecimalFormat pf = new DecimalFormat("##.#########");
		try{
			if( value.indexOf('%') > 0) {
				x = (df.parse(value)).doubleValue();
			} else {
				x = (pf.parse(value)).doubleValue()/100.0;
			}
		} catch (Exception e) {
			x = 0.0;
		}
		return x;
	}

	public static double checkPercent(String value, double defaultValue) {
		double x = defaultValue;
		
		DecimalFormat df = new DecimalFormat("##.####%");
		DecimalFormat pf = new DecimalFormat("##.#########");
		try{
			if( value.indexOf('%') > 0) {
				x = (df.parse(value)).doubleValue();
			} else {
				x = (pf.parse(value)).doubleValue()/100.0;
			}
		} catch (Exception e) {
			x = defaultValue;
		}
		return x;
	}
	
	
}
