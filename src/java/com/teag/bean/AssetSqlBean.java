package com.teag.bean;
/**
 * <p>Title: AssetSqlBean</p>
 * <p>Description: Interface definition for beans for users, clients and peopel
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: NoBoundsIT</p>
 * @author Paul R. Stay
 * @version 1.0
 */

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

public abstract class AssetSqlBean implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -2645022957178094990L;
	
	//	 General formating.
	public static DecimalFormat number = new DecimalFormat("0.00");
	public static DecimalFormat percentformat = new DecimalFormat("0.00%");
	public static DecimalFormat taxformat = new DecimalFormat("0.0000%");
	public static DecimalFormat weight = new DecimalFormat("0.0000");
	public static DecimalFormat dollarformat = new DecimalFormat("0.00");
	public static DecimalFormat moneyformat = new DecimalFormat("$0.00");
	public static SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
	
	public static final int BONDS = 1;
	
	public static final int BUSINESS = 2;
	public static final int CASH = 3;
	public static final int DEBT = 4;
	public static final int ILLIQUID = 5;
	
	public static final int RETIREMENT = 6;
	public static final int SECURITIES = 7;
	public static final int REALESTATE = 8;
	public static final int PROPERTY = 9;
	public static final int INSURANCE = 10;
	public static final int GIFTING = 11;
	public static final int NOTES = 30;
	DBObject dbObject = new DBObject();
	protected int beanType;
	public void dbAddDate(String field, String date) {
		Date nDate = dateformat.parse(date, new ParsePosition(0));
		dbObject.addField(field, dbObject.dbDate(nDate));
	}
	public void dbAddField(String field, boolean value) {
		String str = value == true ? "true" : "false";
		dbAddField(field, str, false);
	}
	public void dbAddField(String field, double value) {
		String str = weight.format(value);
		dbAddField(field, str, false);
	}
	
	public void dbAddField(String field, float value) {
		String str = number.format(value);
		dbAddField(field, str, false);
	}
	public void dbAddField(String field, int value) {
		// This was a number.format, but since it is a integer, we don't need		
		// the floating point value!
		String str = Integer.toString(value);
		dbAddField(field, str, false);
	}
	
	public void dbAddField(String field, long value) {
		dbObject.addField(field, dbObject.dbID(value));
	}
	public void dbAddField(String field, String value) {
		dbAddField(field, value, false);
	}
	public void dbAddField(String field, String value, boolean nFlag ) {
		if( value != null && value.length()> 0) {
			dbObject.addField(field, DBObject.dbString(value));
		} else if (nFlag) {
			dbObject.addField(field, DBObject.dbString(""));
		}
	}
	public abstract double getAssetBasis();

	public abstract double getAssetGrowth();
	
	public abstract double getAssetIncome();
	
	public abstract double getAssetLiability();
	

	public abstract String getAssetName();

	public abstract double getAssetValue();

	/**
	 * @return Returns the beanType.
	 */
	public int getBeanType() {
		return beanType;
	}

	public boolean getBoolean(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if(obj!= null && (((String)(obj)).equals("T") || ((String)(obj)).equals("t"))) {
				return true;
		}
		return false;
	}
	
	public String getDate(HashMap<String,Object> rec, String field) {
		//SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-M-d");
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		String fDate = "";
		Date d = (Date) rec.get(field);
		try {
			fDate = df.format(d);
		} catch (Exception e) {
			
		}
		return fDate;
	}

	public DBObject getDbObject(){
		return dbObject;
	}
	
	public double getDouble(HashMap<String,Object> r, String field) {
    	Object obj = r.get(field);
    	if( obj != null) {
    		return ((Number)obj).doubleValue();
    	}
    	return 0.0;
    }

	public int getInteger(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if( obj != null) {
			return ((Number) obj).intValue();
		}
		return 0;
	}
	public long getLong(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if( obj != null) {
			return ((Number)obj).longValue();
		}
		return 0l;
	}

	public String getString(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if( obj != null) {
			return ((String) obj);
		}
		return null;
	}
	
	public abstract void initialize();
	
	public abstract void insert();
	
	public abstract void update();
}
