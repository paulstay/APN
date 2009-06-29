package com.teag.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LogonBean extends SqlBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8158220020271338378L;
	
	public final static String ID = "ID";
	public final static String PLANNER_ID = "PLANNER_ID";
	public final static String IP_ADDRESS = "IP_ADDRESS";
	public final static String LOG_TIME = "CREATE_DATE";
	
	public final static String tableName = "UserLog";
	
	long id;
	long plannerId;
	String ipAddress;
	Date logTime;
	
	HashMap<String,Object> record;
	
	public long getId() {
		return id;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public Date getLogTime() {
		return logTime;
	}


	public long getPlannerId() {
		return plannerId;
	}

	public HashMap<String,Object> getRecord() {
		return record;
	}

	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		HashMap<String,Object> account = null;
		String sql = "select * from " + tableName + " where ID='"+ id +"'";
		account = dbObject.execute(sql);
		dbObject.stop();

		if( account != null) {
			setPlannerId(getLong(account, PLANNER_ID));
			setIpAddress(getString(account, IP_ADDRESS));
		}
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		dbAddField(PLANNER_ID, Long.toString(getPlannerId()), true);
		dbAddField(IP_ADDRESS, getIpAddress(), true);
		
		dbObject.insert();
		String uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ID from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get("ID");
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	public ArrayList<LogonBean> query(String whereClause) {
		ArrayList<LogonBean> list = new ArrayList<LogonBean>();
		dbObject.start();
		dbObject.setTable("UserLog");
		dbObject.clearFields();

		String sql = "select * from " + tableName + " where " + whereClause;
		record = dbObject.execute(sql);
		HashMap<String,Object> ret = dbObject.execute(sql);

		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			LogonBean nb = new LogonBean();
			nb.setId(id);
			list.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();

		return list;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public void setPlannerId(long plannerId) {
		this.plannerId = plannerId;
	}

	public void setRecord(HashMap<String,Object> record) {
		this.record = record;
	}

	@Override
	public void update() {

	}

}
