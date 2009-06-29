/*
 * Created on Mar 8, 2005
 *
 */
package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Paul Stay
 * 
 */
public class EPTAssetDistBean extends SqlBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5074255480721543950L;
	private static String tableName = "EPTASSET_DIST"; // Name of the table
	// private long ownerId; // Id of the owner of the asset
	private static String idFieldName = "EPTASSET_DIST_ID";
	private long id; // The asset ID in the table
	private String uuid;

	private long eptAssetId;
	private long toolId;
	private long toolTypeId;
	private double value;
	private boolean gift; // Mark this as a gift for the IDIT

	/**
	 * @return Returns the ownerId.
	 */
	private HashMap<String, Object> account;

	public EPTAssetDistBean() {
		this.beanType = 0;
	}

	public void deleteFromDB() {
		dbObject.start();
		dbObject.delete(tableName, "EPTASSET_DIST_ID='" + this.getId() + "'");
		dbObject.stop();
	}

	public ArrayList<EPTAssetDistBean> getBeans(String whereClause) {
		ArrayList<EPTAssetDistBean> bList = new ArrayList<EPTAssetDistBean>();
		String sql = "select " + idFieldName + " from " + tableName + " where "
				+ whereClause;
		dbObject.start();
		HashMap<String, Object> ret = dbObject.execute(sql);
		while (ret != null) {
			Object o = ret.get(idFieldName);
			long id = Long.parseLong(o.toString());
			EPTAssetDistBean nb = new EPTAssetDistBean();
			nb.setId(id);
			nb.setDbObject();
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}

	/**
	 * @return Returns the eptAssetId.
	 */
	public long getEptassetId() {
		return eptAssetId;
	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Returns the toolId.
	 */
	public long getToolId() {
		return toolId;
	}

	/**
	 * @return Returns the tooTypeId.
	 */
	public long getToolTypeId() {
		return toolTypeId;
	}

	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		account = null;

		String sql = "select * from " + tableName + " where " + idFieldName
				+ "='" + id + "'";

		account = dbObject.execute(sql);

		dbObject.stop();

		if (account != null) {

			this.setEptassetId(account.get("EPTASSET_ID") == null ? 0
					: ((Number) (account.get("EPTASSET_ID"))).longValue());
			this.setToolId(account.get("TOOL_ID") == null ? 0
					: ((Number) (account.get("TOOL_ID"))).longValue());
			this.setToolTypeId(account.get("TOOL_TYPE_ID") == null ? 0
					: ((Number) (account.get("TOOL_TYPE_ID"))).longValue());
			this.setValue(account.get("VALUE") == null ? 0 : ((Number) (account
					.get("VALUE"))).doubleValue());
			String g = (String) account.get("GIFT");
			if (g.equalsIgnoreCase("T"))
				setGift(true);
			else
				setGift(false);
		}
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbObject.addField("EPTASSET_ID", "" + this.getEptassetId());
		dbObject.addField("TOOL_ID", "" + this.getToolId());
		dbObject.addField("TOOL_TYPE_ID", "" + this.getToolTypeId());
		dbObject.addField("VALUE", "" + this.getValue());
		if (gift)
			dbObject.addField("GIFT", "T");
		else
			dbObject.addField("GIFT", "F");

		dbObject.setWhere(idFieldName + "='" + id + "'");
		dbObject.update();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.bean.SqlBean#insert()
	 */

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbObject.addField("EPTASSET_ID", "" + this.getEptassetId());
		dbObject.addField("TOOL_ID", "" + this.getToolId());
		dbObject.addField("TOOL_TYPE_ID", "" + this.getToolTypeId());
		dbObject.addField("VALUE", "" + this.getValue());
		if (gift)
			dbObject.addField("GIFT", "'T'");
		else
			dbObject.addField("GIFT", "'F'");

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String, Object> ret = dbObject.execute("select " + idFieldName
				+ " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(idFieldName);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();

	}

	/**
	 * @param eptAssetId
	 *            The eptAssetId to set.
	 */
	public void setEptassetId(long eptAssetId) {
		this.eptAssetId = eptAssetId;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param toolId
	 *            The toolId to set.
	 */
	public void setToolId(long toolId) {
		this.toolId = toolId;
	}

	/**
	 * @param tooTypeId
	 *            The tooTypeId to set.
	 */
	public void setToolTypeId(long toolTypeId) {
		this.toolTypeId = toolTypeId;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	public boolean isGift() {
		return gift;
	}

	public void setGift(boolean gift) {
		this.gift = gift;
	}

}
