package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;

public class ObjectivesBean extends SqlBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2793604134758556988L;
	
	public final static String ID = "OBJECTIVE_ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OBJECTIVE = "OBJECTIVE";
	
	String tableName = "OBJECTIVES";

	long id;
	long ownerId;
	String objective;
	
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public ArrayList<ObjectivesBean> getBeans(String whereClause) {
		ArrayList<ObjectivesBean> bList = new ArrayList<ObjectivesBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			ObjectivesBean nb = new ObjectivesBean();
			nb.setId(id);
			nb.setDbObject();
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}

	public long getId() {
		return id;
	}
	
	public String getObjective() {
		return objective;
	}
	
	public long getOwnerId() {
		return ownerId;
	}

	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		
		String sql = "select * from " + tableName + " where " + ID + "='" + Long.toString(id) + "'";
		HashMap<String,Object> res = dbObject.execute(sql);
		if(res != null) {
			setId(getLong(res,ID));
			setOwnerId(getLong(res,OWNER_ID));
			setObjective(getString(res,OBJECTIVE));
		}
		dbObject.stop();
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		
		dbObject.addField(OWNER_ID, Long.toString(ownerId));
		dbObject.addField(OBJECTIVE, DBObject.dbString(objective));
		dbObject.insert();
		String uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbObject.addField(ID, Long.toString(getId()));
		dbObject.addField(OWNER_ID, Long.toString(getOwnerId()));
		dbObject.addField(OBJECTIVE, DBObject.dbString(getObjective()));
		dbObject.setWhere(ID + "='" + Long.toString(id) + "'");
		dbObject.update();
		dbObject.stop();
	}

}
