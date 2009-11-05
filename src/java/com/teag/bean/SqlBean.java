package com.teag.bean;

/**
 * <p>Title: SqlBean</p>
 * <p>Description: Interface definition for beans for users, clients and peopel
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: NoBoundsIT</p>
 * @author Paul R. Stay
 * @version 1.0
 */

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

public abstract class SqlBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3168149910940885059L;

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
	public static final int NOTES = 12;
	public static final int ECORP = 13;
        public static final int BIZCONT = 14;
        
	public static SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyy-M-d");
	public static DecimalFormat weightFormat = new DecimalFormat("0.000");
	public static DecimalFormat integerFormat = new DecimalFormat("0");
	DBObject dbObject = new DBObject();
	protected int beanType;
	
	public void dbAddDate(String field, Date date) {
		dbAddField(field, dbDateFormat.format(date));
	}
	public void dbAddDate(String field, String date) {
		try {
			Date nDate = dbDateFormat.parse(date);
			dbAddField(field, dbDateFormat.format(nDate), false);
		} catch (Exception e) {
			
		}
	}
	public void dbAddField(String field, double value) {
		String str = weightFormat.format(value);
		dbAddField(field, str, false);
	}
	
	public void dbAddField(String field, float value) {
		String str = weightFormat.format(value);
		dbAddField(field, str, false);
	}

	public void dbAddField(String field, long value) {
		String str = integerFormat.format((int) value);
		dbAddField(field, str, false);
	}
	
	public void dbAddField(String field, int value) {
		String str = integerFormat.format(value);
		dbAddField(field, str, false);
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
	
	public Date getDate(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if( obj != null)
			return (Date) obj;
		return null;
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

	public long getIdFromUUID(String tablename, String idName, String uuid) {
		long id;
		dbObject.start();
		HashMap<String,Object> rec = dbObject.execute("select " + idName + " from " + tablename + " where UUID='" + uuid + "'");
		Object o = rec.get(idName);
		id = Integer.parseInt(o.toString());
		return id;
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
	
	
	public void setDbObject() {
		//dbObject.copy(this.dbObject);
	}
	public abstract void update();
}
