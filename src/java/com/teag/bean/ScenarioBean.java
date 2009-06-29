package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class ScenarioBean extends SqlBean {

	/**
	 * 	<Table name="SCENARIO_TABLE">
		<Field name='SCENARIO_ID' size='19' type='long' null='false' autoincrement='true' valid='true'/>
		<Field name='OWNER_ID'  size='19' type='long' null='true'/>
		<PrimaryKey field='SCENARIO_ID' />
		<ForeignKey field='OWNER_ID' reftable='PERSON' reffield='PERSON_ID'/>
	</Table>
	 */
	private static final long serialVersionUID = -8019380782298230107L;
	
	public static final String ID = "SCENARIO_ID";
	public static final String OWNER_ID = "OWNER_ID";
	
	long id;
	long ownerId;
	
	private String tableName = "SCENARIO_TABLE";

	public void delete() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.delete(tableName, ID + "='" + id + "'");
		dbObject.stop();
	}

	public ArrayList<ScenarioBean> getBeans(String whereClause) {
		ArrayList<ScenarioBean> bList = new ArrayList<ScenarioBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			ScenarioBean nb = new ScenarioBean();
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

	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		HashMap<String,Object> rec = null;

		String sql = "select * from " + tableName + " where " + ID + "='" + getId() + "'";

		if( id > 0) {
			rec = dbObject.execute(sql);
		}
		
		if( rec != null) {
			this.setId(getLong(rec,ID));
			this.setOwnerId(getLong(rec,OWNER_ID));
		}
		dbObject.stop();
	}
	
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbAddField(OWNER_ID, getOwnerId());
		dbObject.insert();
		String uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		id = Integer.parseInt(o.toString());
		
		dbObject.stop();
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbAddField(OWNER_ID, getOwnerId());
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}

}
