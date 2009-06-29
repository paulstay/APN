package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;

public class PhilosophiesBean extends SqlBean {

	/**
	 * <Table name='PHILOSOPHIES' comment='Objectives for the client'>
		<Field name='PHILOSOPHY_ID' type='long' size='19' null='false' autoincrement='true' valid='true' />
		<Field name='OWNER_ID' type='long' size='19' null='false'/>
		<Field name='PHILOSOPHY' type='string' size='1000' null='false'/>
		<ForeignKey field='OWNER_ID' reftable='PERSON' reffield='PERSON_ID' />
		<PrimaryKey field='PHILOSOPHY_ID'/>
	</Table>
	 */
	private static final long serialVersionUID = 7816792668682789924L;
	public final static String ID = "PHILOSOPHY_ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String PHILOSOPHY = "PHILOSOPHY";
	
	long id;
	long ownerId;
	String philosophy;
	String tableName = "PHILOSOPHIES";

	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public ArrayList<PhilosophiesBean> getBeans(String whereClause) {
		ArrayList<PhilosophiesBean> bList = new ArrayList<PhilosophiesBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			PhilosophiesBean nb = new PhilosophiesBean();
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
	
	public String getPhilosophy() {
		return philosophy;
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
			setPhilosophy(getString(res,PHILOSOPHY));
		}
		dbObject.stop();


	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		
		dbObject.addField(OWNER_ID, Long.toString(ownerId));
		dbObject.addField(PHILOSOPHY, DBObject.dbString(philosophy));
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

	public void setPhilosophy(String philosophy) {
		this.philosophy = philosophy;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbObject.addField(ID, Long.toString(getId()));
		dbObject.addField(OWNER_ID, Long.toString(getOwnerId()));
		dbObject.addField(PHILOSOPHY, DBObject.dbString(getPhilosophy()));
		dbObject.setWhere(ID + "='" + Long.toString(id) + "'");
		dbObject.update();
		dbObject.stop();
	}

}
