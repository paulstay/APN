package com.teag.bean;

import com.estate.db.DBObject;

/**
 * @author Paul Stay
 * Created on Apr 1, 2005
 *
 */
public abstract class ToolSqlBean {
	DBObject dbObject = new DBObject();
	
	protected int beanType;
	public void dbAddField(String field, long value) {
		dbAddField(field, value + "", false);
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

	public DBObject getDbObject(){
		return dbObject;
	}
	
	public abstract void initialize();
	
	public abstract void insert();
	
	public void setDbObject() {
	}
	
	public abstract void update();
}
