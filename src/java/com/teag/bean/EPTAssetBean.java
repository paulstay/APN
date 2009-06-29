/*
 * Created on Mar 8, 2005
 *
 */
package com.teag.bean;

import java.util.HashMap;

import com.estate.constants.EptAssetTypes;

/**
 * @author Paul Stay
 * 
 */
public class EPTAssetBean extends SqlBean {
	private static final long serialVersionUID = -1906076824440645483L;

	private static String tableName = "EPTASSET"; // Name of the table

	private static String idFieldName = "EPTASSET_ID";

	public static String getTableName() {
		return tableName;
	}

	public static void setTableName(String tableName) {
		EPTAssetBean.tableName = tableName;
	}

	private long id; // The asset ID in the table

	private String uuid;

	private long assetId; // Id of the asset in it's table

	private long assetType; // The type of asset

	private long scenarioId; // The Scenario this EPTAsset is associated with

	private String description; // Description of the asset

	private long tclatId = 0; // If this is used for the Testementary CLAT,
								// than add the ID field.

	private HashMap<String,Object> account;

	public EPTAssetBean() {
		this.beanType = 0;
	}

	// Remove this item form the Data Base
	public void deleteFromDB() {
		dbObject.start();
		// clear all distributions associated with this asset
		dbObject.delete("EPTASSET_DIST", "EPTASSET_ID='" + this.getId() + "'");
		// clear the asset
		dbObject.delete("EPTASSET", "EPTASSET_ID='" + this.getId() + "'");
		dbObject.stop();

	}

	// Create a distribution of this asset
	public long distribute(double amount, long toolId, long toolTypeId) {
		double remaining = this.getRemainingValue();
		long id = 0;

		if (amount <= remaining) {
			EPTAssetDistBean eptDist = new EPTAssetDistBean();

			eptDist.setDbObject();
			eptDist.setEptassetId(this.getId());

			eptDist.setToolId(toolId);
			eptDist.setToolTypeId(toolTypeId);

			eptDist.setValue(amount);
			eptDist.insert();
			id = eptDist.getId();
		}

		return (id);
	}
	
	public long distribute(double amount, long toolId, long toolTypeId, boolean gift){
		double remaining = this.getRemainingValue();
		long id = 0;

		if (amount <= remaining) {
			EPTAssetDistBean eptDist = new EPTAssetDistBean();
			eptDist.setEptassetId(this.getId());
			eptDist.setToolId(toolId);
			eptDist.setToolTypeId(toolTypeId);
			eptDist.setValue(amount);
			eptDist.setGift(gift);			// Use this currently for IDIT
			eptDist.insert();
			id = eptDist.getId();
		}

		return (id);
	}

	public EPTAssetDistBean[] getAllDistributions() {
		EPTAssetDistBean eptADB[] = {};
		String sqlDist = "select EPTASSET_DIST_ID from EPTASSET_DIST where EPTASSET_ID='"
				+ this.getId() + "'";
		String sqlDistCount = "select COUNT(*) as DISTCOUNT from EPTASSET_DIST where EPTASSET_ID='"
				+ this.getId() + "'";
		HashMap<String,Object> dist;
		int count;

		dbObject.start();
		dist = dbObject.execute(sqlDistCount);
		if (null != dist) {
			count = ((Number) (dist.get("DISTCOUNT"))).intValue();

			eptADB = new EPTAssetDistBean[count];

			dist = dbObject.execute(sqlDist);
			int i = 0;
			while (null != dist) {
				long id = ((Number) (dist.get("EPTASSET_DIST_ID"))).longValue();
				eptADB[i].setDbObject();
				eptADB[i].setId(id);
				eptADB[i++].initialize();
				dist = dbObject.next();

			}
		}
		dbObject.stop();

		return (eptADB);

	}

	/**
	 * @return Returns the assetId.
	 */
	public long getAssetId() {
		return assetId;
	}

	/**
	 * @return Returns the assetType.
	 */
	public long getAssetType() {
		return assetType;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	public int getDistCount() {
		HashMap<String,Object> dist;
		String sqlDistCount = "select COUNT(*) as DISTCOUNT from EPTASSET_DIST where EPTASSET_ID='"
				+ this.getId() + "'";
		dbObject.start();
		dist = dbObject.execute(sqlDistCount);
		dbObject.stop();

		return (((Number) (dist.get("DISTCOUNT"))).intValue());

	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}

	// Calculate the the remining un distributed value of the asset
	public double getRemainingValue() {
		String sql = "select SUM(VALUE) as DISTRIBUTED from EPTASSET_DIST where EPTASSET_ID='"
				+ this.getId() + "'";
		HashMap<String,Object> rec;
		double remaining = 0;
		double value;
		double distributed = 0;

		// Need to get the value of the asset
		value = this.getValue();
		if (value >= 0) // Negative value denotes an error
		{
			dbObject.start();
			rec = dbObject.execute(sql);
			dbObject.stop();
			if (rec != null) {
				distributed = rec.get("DISTRIBUTED") == null ? 0
						: ((Number) (rec.get("DISTRIBUTED"))).floatValue();
				remaining = value - distributed;
			} else {
				remaining = -3;
			}
		} else {
			remaining = value; // Pass the
		}
		return (remaining);
	}

	/**
	 * @return Returns the scenarioId.
	 */
	public long getScenarioId() {
		return scenarioId;
	}

	public long getTclatId() {
		return tclatId;
	}

	public int getToolDistCount(long toolId, long toolTypeId) {
		String sqlDistCount = "select COUNT(*) as DISTCOUNT from EPTASSET_DIST where TOOL_ID='"
				+ toolId
				+ "' and TOOL_TYPE_ID='"
				+ toolTypeId
				+ "' and EPTASSET_ID = '" + this.id + "'";
		HashMap<String,Object> dist;

		dbObject.start();
		dist = dbObject.execute(sqlDistCount);
		dbObject.stop();

		if (dist != null)
			return (((Number) (dist.get("DISTCOUNT"))).intValue());
		return 0;

	}

	public EPTAssetDistBean[] getToolDistributions(long toolId, long toolTypeId) {
		EPTAssetDistBean eptADB[] = {};
		String sqlDist = "select EPTASSET_DIST_ID from EPTASSET_DIST where TOOL_ID='"
				+ toolId
				+ "'  and TOOL_TYPE_ID='"
				+ toolTypeId
				+ "' and EPTASSET_ID='" + this.id + "'";

		HashMap<String,Object> dist;

		int count = getToolDistCount(toolId, toolTypeId);

		dbObject.start();

		eptADB = new EPTAssetDistBean[count];

		dist = dbObject.execute(sqlDist);
		int i = 0;
		while (null != dist) {
			long id = ((Number) (dist.get("EPTASSET_DIST_ID"))).longValue();
			eptADB[i] = new EPTAssetDistBean();
			eptADB[i].setDbObject();
			eptADB[i].setId(id);
			eptADB[i++].initialize();
			dist = dbObject.next();
		}

		dbObject.stop();

		return (eptADB);
	}

	// Get the total value of the asset this represents
	public double getValue() {
		EptAssetTypes ept = EptAssetTypes.getEptAssetType((int) getAssetType());

		double value = -2;
		String sql = null;
		if(ept.id()==17) {
			sql = "select LOAN_AMOUNT as VALUE from " + ept.tableName() + " where " 
			+ ept.tableId() + "='" + getAssetId() + "'";
		} else if(ept.id() == 10) {
			sql = "select FACE_VALUE as VALUE from " + ept.tableName() + " where "
			+ ept.tableId() + "='" + getAssetId() + "'";
			
		} else {
			sql = "select VALUE from " + ept.tableName() + " where "
				+ ept.tableId() + "='" + getAssetId() + "'";
		}
		HashMap<String,Object> asset;
		dbObject.start();
		asset = dbObject.execute(sql);
		dbObject.stop();

		if (null != asset) {
			value = asset.get("VALUE") == null ? -1 : ((Number) (asset
					.get("VALUE"))).doubleValue();
		}

		return (value);

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

		if (account != null) {

			// this.setOwnerId(account.get("OWNER_ID") == null ? 0 :
			// ((Number)(account.get("OWNER_ID"))).longValue());

			// Add any additional fields
			this.setAssetId(account.get("ASSET_ID") == null ? 0
					: ((Number) (account.get("ASSET_ID"))).longValue());

			this.setAssetType(account.get("ASSET_TYPE_ID") == null ? 0
					: ((Number) (account.get("ASSET_TYPE_ID"))).longValue());
			this.setScenarioId(account.get("SCENARIO_ID") == null ? 0
					: ((Number) (account.get("SCENARIO_ID"))).longValue());
			this.setTclatId(account.get("TCLAT_ASSET") == null ? 0
					: ((Number) (account.get("TCLAT_ASSET"))).longValue());

		}
		EptAssetTypes ept = EptAssetTypes.getEptAssetType((int) getAssetType());
		// Ok, now grab the description for the asset this represents
		String descSql = "select DESCRIPTION from " + ept.tableName()
				+ " where " + ept.tableId() + " ='" + assetId + "'";

		account = dbObject.execute(descSql);

		if (account != null) {
			this.description = (String) account.get("DESCRIPTION");
		} else {
			this.description = "";
		}

		dbObject.stop();

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
		EptAssetTypes ept = EptAssetTypes.getEptAssetType((int) getAssetType());

		// dbObject.addField("OWNER_ID", "" + this.getOwnerId());
		// ####### Add additional fields
		dbObject.addField("ASSET_ID", "" + this.getAssetId());
		dbObject.addField("ASSET_TYPE_ID", "" + this.getAssetType());
		dbObject.addField("SCENARIO_ID", "" + this.getScenarioId());
		dbObject.addField("TCLAT_ASSET", "" + this.getTclatId());

		// ##############
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + idFieldName + " from "
				+ tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(idFieldName);
		this.id = Integer.parseInt(o.toString());

		// Ok, now grab the description for the asset this represents
		String descSql = "select DESCRIPTION from " + ept.tableName()
				+ " where " + ept.tableId() + " ='" + this.assetId + "'";

		account = dbObject.execute(descSql);

		if (account != null) {
			this.description = (String) account.get("DESCRIPTION");
		} else {
			this.description = "";
		}

		dbObject.stop();

	}

	/**
	 * @param assetId
	 *            The assetId to set.
	 */
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}

	/**
	 * @param assetType
	 *            The assetType to set.
	 */
	public void setAssetType(long assetType) {
		this.assetType = assetType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param scenarioId
	 *            The scenarioId to set.
	 */
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}

	public void setTclatId(long tclatId) {
		this.tclatId = tclatId;
	}

	// Remove a distribution
	public void undistribute(long distributionId) {
		EPTAssetDistBean eptDist = new EPTAssetDistBean();
		eptDist.setId(distributionId);
		eptDist.setDbObject();
		eptDist.deleteFromDB();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		dbObject.addField("ASSET_ID", "" + this.getAssetId());
		dbObject.addField("ASSET_TYPE_ID", "" + this.getAssetType());
		dbObject.addField("SCENARIO_ID", "" + this.getScenarioId());
		dbObject.addField("TCLAT_ASSET", "" + this.getTclatId());

		dbObject.setWhere(idFieldName + "='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}
}
