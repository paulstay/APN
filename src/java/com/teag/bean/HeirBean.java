package com.teag.bean;

import java.util.HashMap;

public class HeirBean extends SqlBean {

	/**
	 * <Table name='HEIRS' comment='Number of heirs for client'>
	    <Field name='HEIRS_ID' type='long' size='19' null='false' autoincrement='true' valid='true' />
		<Field name='OWNER_ID' type='long' size='19' null='false' />
		<Field name='NUMBER_OF_HEIRS' type='number' size='2' null='false' />
		<PrimaryKey field='HEIRS_ID' />
		<ForeignKey field='OWNER_ID' reftable='PERSON' reffield='PERSON_ID'  />
	</Table>
	 */
	
	private static final long serialVersionUID = -5680028829517844314L;

	public final static String ID = "HEIRS_ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String NUMBER_OF_HEIRS = "NUMBER_OF_HEIRS";
	
	long id;
	long ownerId;
	int numberOfHeirs;
	
	String tableName = "HEIRS";
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public long getId() {
		return id;
	}


	public int getNumberOfHeirs() {
		return numberOfHeirs;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		HashMap<String,Object> ret = dbObject.execute("select * from " + tableName + 
				" where " + ID + "='" + getId() + "'");
		if(ret != null) {
			this.setOwnerId(this.getLong(ret,OWNER_ID));
			this.setNumberOfHeirs(getInteger(ret, NUMBER_OF_HEIRS));
		}
		dbObject.stop();
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.clearFields();
		dbObject.setTable(tableName);
		dbObject.addField(OWNER_ID,Long.toString(ownerId));
		dbObject.addField(NUMBER_OF_HEIRS,Integer.toString(numberOfHeirs));
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

	public void setNumberOfHeirs(int numberOfHeirs) {
		this.numberOfHeirs = numberOfHeirs;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.clearFields();
		dbObject.setTable(tableName);
		
		dbObject.addField(OWNER_ID,Long.toString(ownerId));
		dbObject.addField(NUMBER_OF_HEIRS,Integer.toString(numberOfHeirs));
		dbObject.setWhere(ID + "='" + getId() + "'");
		dbObject.update();
		dbObject.stop();
	}
}
