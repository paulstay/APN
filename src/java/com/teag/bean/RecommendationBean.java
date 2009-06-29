package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;

public class RecommendationBean extends SqlBean {

	private static final long serialVersionUID = 8281494748071974543L;

	public static final String ID = "RECOMMENDATION_ID";
	public static final String OWNER_ID = "OWNER_ID";
	public static final String RECOMMENDATION = "RECOMMENDATION";
	
	long id;
	long ownerId;
	String recommendation;
	String tableName = "RECOMMENDATIONS";
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public ArrayList<RecommendationBean> getBeans(String whereClause) {
		ArrayList<RecommendationBean> bList = new ArrayList<RecommendationBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			RecommendationBean nb = new RecommendationBean();
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
	public long getOwnerId() {
		return ownerId;
	}
	
	public String getRecommendation() {
		return recommendation;
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
			setRecommendation(getString(res,RECOMMENDATION));
		}
		dbObject.stop();

	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		
		dbObject.addField(OWNER_ID, Long.toString(ownerId));
		dbObject.addField(RECOMMENDATION, DBObject.dbString(recommendation));
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

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbObject.addField(ID, Long.toString(getId()));
		dbObject.addField(OWNER_ID, Long.toString(getOwnerId()));
		dbObject.addField(RECOMMENDATION,  DBObject.dbString(getRecommendation()));
		dbObject.setWhere(ID + "='" + Long.toString(id) + "'");
		dbObject.update();
		dbObject.stop();

	}

}
