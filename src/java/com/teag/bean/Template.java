/*
 * Created on Mar 8, 2005
 *
 */
package com.teag.bean;

import java.util.HashMap;

/**
 * @author Paul Stay
 *
 */
public class Template extends SqlBean {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4116093331517372747L;
	private static String tableName ="";	// Name of the table
	private static String idFieldName ="";	
	private long id;	// The asset ID in the table
	private String uuid;
	private long ownerId;					// Id of the owner of the asset
	
	private HashMap<String,Object> account;
	
	public Template()
	{
		this.beanType = 0;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the ownerId.
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		account = null;

		String sql = "select * from " + tableName + " where " + idFieldName + "='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());

			// Add any additional fields
			
		}		
		

	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbObject.addField("OWNER_ID", "" + this.getOwnerId());
		// ####### Add additional fields 

		//##############
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + idFieldName + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(idFieldName);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();

	}

	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param ownerId The ownerId to set.
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		
//		dbObject.addField("DESCRIPTION", this.getName());
		// Add the necessary fields here
		dbObject.setWhere(idFieldName + "='" + id + "'");
		dbObject.update();

	}

}
