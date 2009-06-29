/*
 * Created on May 27, 2005
 *
 */
package com.teag.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author stay
 *
 */
public class ChildrenBean extends SqlBean implements Serializable {
	/**
	 * 
	 */
	
	public static final String OWNER_ID = "OWNER_ID";
	public static final String ID = "ID";
	public static final String NUMBER_OF_CHILDREN = "NUMBER_OF_CHILDREN";
	public static final String tableName = "CHILDREN";
	private static final long serialVersionUID = -1734560926315754181L;
	
	long id;
	String uuid;
	long ownerId;
	int numChildren;
	String notes;
	
	public void delete() {
		dbObject.start();
		String whereClause = "ID" + "='" + getId() + "'";
		dbObject.delete("CHILDREN", whereClause);
		dbObject.stop();
	}

	public ArrayList<ChildrenBean> getBeans(String whereClause) {
		ArrayList<ChildrenBean> bList = new ArrayList<ChildrenBean>();
		String sql = "select " + OWNER_ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(OWNER_ID);
			long id = Long.parseLong(o.toString());
			ChildrenBean nb = new ChildrenBean();
			nb.setOwnerId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Returns the notes.
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @return Returns the numChildren.
	 */
	public int getNumChildren() {
		return numChildren;
	}
	

	/**
	 * @return Returns the ownerId.
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @return Returns the uuid.
	 */
	public String getUuid() {
		return uuid;
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable("CHILDREN");
		HashMap<String,Object> account = null;
		String sql = "select * from CHILDREN where OWNER_ID='"+ getOwnerId() +"'";
		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			setId(getLong(account,"ID"));
			setOwnerId(getLong(account,"OWNER_ID"));
			setNumChildren(getInteger(account,"NUMBER_OF_CHILDREN"));
			setNotes(getString(account,"NOTES"));
		}			
		dbObject.stop();
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("CHILDREN");
		dbObject.clearFields();
		dbAddField("NOTES", getNotes());
		dbAddField("OWNER_ID", Long.toString(getOwnerId()));
		dbAddField("NUMBER_OF_CHILDREN", Integer.toString(getNumChildren()));

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ID from CHILDREN where UUID='" + uuid + "'");
		Object o = ret.get("ID");
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param notes The notes to set.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @param numChildren The numChildren to set.
	 */
	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}
	/**
	 * @param ownerId The ownerId to set.
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	/**
	 * @param uuid The uuid to set.
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable("CHILDREN");
		dbObject.clearFields();
		dbAddField("NOTES", getNotes());
		dbAddField("NUMBER_OF_CHILDREN", Integer.toString(getNumChildren()));
		dbObject.setWhere("OWNER_ID='" + getOwnerId() + "'");
		dbObject.update();
		dbObject.stop();
	}
}
