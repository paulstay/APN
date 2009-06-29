/*
 * Created on Oct 19, 2004
 *
 */
package com.teag.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Paul Stay
 *
 */
public class ClientBean extends SqlBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1691692945858608250L;
	private long clientId;
	private long plannerId;
	private long primaryId;
	private long spouseId;
	private boolean isSingle = false;
	HashMap<String,Object> account;
	
	public void delete() {
		dbObject.start();
		String whereClause = "CLIENT_ID" + "='" + getClientId() + "'";
		dbObject.delete("CLIENT", whereClause);
		dbObject.stop();
	}
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId() {
		return clientId;
	}
	/**
	 * @return Returns the plannerId.
	 */
	public long getPlannerId() {
		return plannerId;
	}
	/**
	 * @return Returns the primaryId.
	 */
	public long getPrimaryId() {
		return primaryId;
	}
	/**
	 * @return Returns the spouseId.
	 */
	public long getSpouseId() {
		if( isSingle) {
			return 0;
		} 
		return spouseId;
	}
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable("Client");
		String sql = "select * from Client where CLIENT_ID='" + clientId + "'";
		account = dbObject.execute(sql);
		dbObject.stop();
		if( account != null){
			Object o = account.get("PLANNER_ID");
			plannerId = Integer.parseInt(o.toString());
			o = account.get("PRIMARY_ID");
			primaryId = Integer.parseInt(o.toString());
			
			sql = "select SPOUSE_ID from MARRIAGE where PRIMARY_ID='" + primaryId + "' and STATUS='C'";
			dbObject.start();
			account = dbObject.execute(sql);
			dbObject.stop();
			
			if(account != null)
			{
				o = account.get("SPOUSE_ID");
				if( o != null )
				    spouseId = Integer.parseInt(o.toString());
			} else {
				System.out.println("Single Spouse Issue : Client Bean");
				setSingle(true);
			}
		}
	}
	
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("CLIENT");
		dbObject.clearFields();
		if( plannerId > 0)
			dbObject.addField("PLANNER_ID", "'" + plannerId + "'");
		dbObject.addField("PRIMARY_ID", "'" + primaryId + "'");	
		dbObject.insert();
		String uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select CLIENT_ID from CLIENT where UUID='" + uuid + "'");
		Object o = ret.get("CLIENT_ID");
		clientId = Integer.parseInt(o.toString());
		dbObject.stop();
	}
	public boolean isSingle() {
		return isSingle;
	}

	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * @param plannerId The plannerId to set.
	 */
	public void setPlannerId(long plannerId) {
		this.plannerId = plannerId;
	}
	
	/**
	 * @param primaryId The primaryId to set.
	 */
	public void setPrimaryId(long primaryId) {
		this.primaryId = primaryId;
	}
	
	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}
	
	
	/**
	 * @param spouseId The spouseId to set.
	 */
	public void setSpouseId(long spouseId) {
		this.spouseId = spouseId;
	}
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable("CLIENT");
		dbObject.clearFields();
		if( plannerId > 0)
			dbObject.addField("PLANNER_ID", "'" + plannerId + "'");
		if( spouseId > 0)
			dbObject.addField("SPOUSE_ID", "'" + spouseId + "'");
		dbObject.addField("PRIMARY_ID", "'" + primaryId + "'");		
		dbObject.setWhere("CLIENT_ID='"+ clientId +"'");
		dbObject.update();
		dbObject.stop();
	}
}
