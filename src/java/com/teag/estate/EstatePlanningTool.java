package com.teag.estate;

/**
 * @author stay 
 * Created on Mar 21, 2005
 *  
 */

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

public abstract class EstatePlanningTool {
	public static final int CRUT_TYPE = 1;
	public static final int QPRT_TYPE = 2;
	public static final int GRAT_TYPE = 3;
	public static final int CLAT_TYPE = 4;
	public static final int FLP_TYPE = 5;
	public static final int LLC_TYPE = 6;
	public static final int CRUM_TYPE = 7;
	public static final int MULTI_TYPE = 8;
	public static final int RPM_TYPE = 9;
	public static final int TCLAT_TYPE = 10;
	public static final int IDIT_TYPE = 11;
	public static final int WILL_TYPE = 12;
	public static final int BUY_TYPE = 13;
	public static final int HAMMER_TYPE = 14;
	public static final int IDIT_TRUST_TYPE = 15;
	public static final int CRUM_LIFE_TYPE = 16;
	public static final int CHARLIE_PLAN = 17;
	public static final int PRIVATE_ANNUITY_TYPE = 18;
	public static final int SCIN_TYPE = 19;
	
	// General formating.
	public static DecimalFormat number = new DecimalFormat("0.00");
	public static DecimalFormat percentformat = new DecimalFormat("0.00%");
	public static DecimalFormat taxformat = new DecimalFormat("0.0000%");
	public static DecimalFormat weight = new DecimalFormat("0.0000");
	public static DecimalFormat dollarformat = new DecimalFormat("0.00");
	public static DecimalFormat moneyformat = new DecimalFormat("$0.00");
	public static SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");

	DBObject dbObj = new DBObject();
	long toolTableId;

	public abstract void calculate();

	public void dbAddDate(String field, String date) {
		Date nDate = dateformat.parse(date, new ParsePosition(0));
		dbObj.addField(field,dbObj.dbDate(nDate));
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
		String str = Integer.toString(value);
		dbAddField(field, str, false);
	}
	
	public void dbAddField(String field, long value) {
		String str = Long.toString(value);
		dbAddField(field, str, false);
	}

	public void dbAddField(String field, String value) {
		dbAddField(field, value, false);
	}

	public void dbAddField(String field, String value, boolean nFlag) {
		if (value != null && value.length() > 0) {
			dbObj.addField(field, DBObject.dbString(value));
		} else if (nFlag) {
			dbObj.addField(field, DBObject.dbString(""));
		} else {
			dbObj.addField(field, DBObject.dbString("NULL"));
		}
	}

	public abstract void delete();

	public boolean getBoolean(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if (obj != null
				&& (((String) (obj)).equals("T") || ((String) (obj))
						.equals("t"))) {
			return true;
		}
		return false;
	}
	
	public Date getRealDate(HashMap<String,Object>rec, String field) {
		Date rDate = (Date) rec.get(field);
		if(rDate != null)
			return rDate;
		return new Date();
	}

	public String getDate(HashMap<String,Object> rec, String field) {
		Date rDate = (Date) rec.get(field);
		if (rDate != null) {
			try {
				return dateformat.format(rDate);
			} catch (Exception e) {
			}
		}
		return "";
	}

	public DBObject getDbObject() {
		return dbObj;
	}

	public double getDouble(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if (obj != null) {
			return ((Number) obj).doubleValue();
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
		if (obj != null) {
			return ((String) obj);
		}
		return null;
	}

	public String convertDateToString(Date date) {
		return dateformat.format(date);
	}
	
	public abstract long getToolTableId();
	
	public abstract void insert();

	public abstract void read();

	public void setDbObject() {

	}

	public abstract void update();

	public abstract void report();
	public abstract String writeupText();
}