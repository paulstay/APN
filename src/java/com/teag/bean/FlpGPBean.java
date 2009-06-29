package com.teag.bean;

import java.util.HashMap;

import com.teag.estate.FlpTool;

/**
 * @author stay
 * Description - Flp GP Interest Bean for ept asset creation.
 * Created on Apr 29, 2005
 */
public class FlpGPBean extends AssetSqlBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5732703386334463087L;
	long id;
	String uuid;
	long flpToolId;
	long ownerId; 	// Need this to build assets
	double value;
	
	String assetName;
	double assetValue;
	double premiumValue;
	double assetGrowth;
	double assetIncome;

	
	public void delete(long flpId) {
		dbObject.start();
		dbObject.delete("FLP_GP_TABLE", "FLP_TOOL_ID='" + flpId + "'");
		dbObject.stop();
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#getAssetBasis()
	 */
	@Override
	public double getAssetBasis() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#getAssetGrowth()
	 */
	@Override
	public double getAssetGrowth() {
		return assetGrowth;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#getAssetIncome()
	 */
	@Override
	public double getAssetIncome() {
		return assetIncome;
	}
	

	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#getAssetLiability()
	 */
	@Override
	public double getAssetLiability() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#getAssetName()
	 */
	@Override
	public String getAssetName() {
		return assetName; 
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#getAssetValue()
	 */
	@Override
	public double getAssetValue() {
		return value;
	}

	public long getFlpToolId() {
		return flpToolId;
	}

	public long getId() {
		return id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public double getPremiumValue() {
		return premiumValue;
	}

	public String getUuid() {
		return uuid;
	}
	
	public double getValue() {
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#initialize()
	 */
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable("FLP_GP_TABLE");
		HashMap<String,Object> account = null;

		String sql = "select * from FLP_GP_TABLE where ID='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			setValue(getDouble(account,"VALUE"));
			setFlpToolId(getLong(account,"FLP_TOOL_ID"));
			setId(getLong(account,"ID"));
			setOwnerId(getLong(account,"OWNER_ID"));
			setAssetName(getString(account,"DESCRIPTION"));
		}

		FlpTool flp = new FlpTool();
		flp.setDbObject();
		flp.setId(getFlpToolId());
		flp.calculate();
		
		assetValue = value;
		assetName = flp.getName();
		assetIncome = flp.getIncome();
		assetGrowth = flp.getGrowth();
		
		premiumValue = flp.getPremiumGpValue();
		
		flp = null;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("FLP_GP_TABLE");
		dbObject.clearFields();
		dbAddField("VALUE", value);
		dbAddField("FLP_TOOL_ID", flpToolId);
		dbAddField("OWNER_ID", ownerId);
		dbAddField("DESCRIPTION",getAssetName());
		dbObject.setWhere("ID='" + id + "'");

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ID from FLP_GP_TABLE where UUID='" + uuid + "'");
		Object o = ret.get("ID");
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();
	}
	
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}
	
	public void setFlpToolId(long flpToolId) {
		this.flpToolId = flpToolId;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public void setPremiumValue(double premiumValue) {
		this.premiumValue = premiumValue;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setValue(double value) {
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable("FLP_GP_TABLE");
		dbObject.clearFields();
		dbAddField("VALUE", value);
		dbAddField("FLP_TOOL_ID", flpToolId);
		dbAddField("OWNER_ID", ownerId);
		dbAddField("DESCRIPTION",getAssetName());
		dbObject.setWhere("ID='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}
}
