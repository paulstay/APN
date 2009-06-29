package com.teag.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

public abstract class SQLView {
	DBObject dbObject = new DBObject();
	
	public boolean getBoolean(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if(obj!= null && (((String)(obj)).equals("T") || ((String)(obj)).equals("t"))) {
				return true;
		}
		return false;
	}
	
	public Date getDate(HashMap<String,Object> res, String field) {
		Date d = (Date) res.get(field);
		return d;
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
	
	public String getStringDate(HashMap<String,Object> rec, String field) {
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
	
	/**
	 * Return an array list of the view
	 * @param id
	 * @return
	 */
	public abstract ArrayList<?> getView(long id);
	
	/**
	 * read the database record and update the fields.
	 *
	 */
	
	public abstract void next(HashMap<String,Object> res);

}
