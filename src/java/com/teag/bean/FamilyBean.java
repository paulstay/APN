package com.teag.bean;

import java.util.HashMap;

public class FamilyBean extends SqlBean {

	/**
	 * <Table name='FAMILY' comment='Family Information'>
		<Field name='PERSON_ID' type='long' size='19' null='false' />
		<Field name='MARRIAGE_ID' type='long' size='19' null='true' />
		<PrimaryKey field='PERSON_ID' />
		<ForeignKey field='PERSON_ID' reftable='PERSON' reffield='PERSON_ID' />
		<ForeignKey field='MARRIAGE_ID' reftable='MARRIAGE' reffield='MARRIAGE_ID' />
	</Table>
	 */
	private static final long serialVersionUID = -7436751448528991962L;
	public final static String ID = "PERSON_ID";
	public final static String MARRIAGE_ID = "MARRIAGE_ID";
	
	String tableName = "FAMILY";
	long id;
	long mId;
	
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public long getId() {
		return id;
	}

	public long getMId() {
		return mId;
	}

	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		HashMap<String,Object> ret = dbObject.execute("select * from " + tableName + 
				" where " + ID + "='" + getId() + "'");
		if(ret != null) {
			this.setMId(this.getLong(ret,MARRIAGE_ID));
		}
		dbObject.stop();

	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.clearFields();
		dbObject.setTable(tableName);
		dbObject.addField(ID,Long.toString(getId()));
		dbObject.addField(MARRIAGE_ID,Long.toString(mId));
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

	public void setMId(long id) {
		mId = id;
	}
	
	@Override
	public void update() {
		dbObject.start();
		dbObject.clearFields();
		dbObject.setTable("LOCATION");
		
		dbObject.addField(ID, Long.toString(id));
		dbObject.addField(MARRIAGE_ID, Long.toString(mId));

		dbObject.setWhere(ID + "='" + getId() + "'");
		dbObject.update();
		dbObject.stop();
	}
}
