package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;

public class ObservationBean extends SqlBean {
/**
	 * 
	 */
	private static final long serialVersionUID = 6882712962227260770L;
	
	public static final String ID = "OBSERVATION_ID";
	public static final String OWNER_ID = "OWNER_ID";
	public static final String OBSERVATION = "OBSERVATION";
	
	String tableName = "OBSERVATIONS";
	long id;
	long ownerId;
	String observation;
	
	
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public ArrayList<ObservationBean> getBeans(String whereClause) {
		ArrayList<ObservationBean> bList = new ArrayList<ObservationBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			ObservationBean nb = new ObservationBean();
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
	
	public String getObservation() {
		return observation;
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
			setObservation(getString(res,OBSERVATION));
		}
		dbObject.stop();

	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		
		dbObject.addField(OWNER_ID, Long.toString(ownerId));
		dbObject.addField(OBSERVATION, DBObject.dbString(observation));
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

	public void setObservation(String observation) {
		this.observation = observation;
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
		dbObject.addField(OBSERVATION, DBObject.dbString(getObservation()));
		dbObject.setWhere(ID + "='" + Long.toString(id) + "'");
		dbObject.update();
		dbObject.stop();
	}

}
