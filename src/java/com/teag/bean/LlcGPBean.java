package com.teag.bean;

/**
 * @author stay
 * Created on May 26, 2005
 *
 */

import java.util.HashMap;

import com.teag.estate.LlcTool;


public class LlcGPBean  extends AssetSqlBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1969624920586869445L;
	long id;
	String uuid;
	long llcToolId;
	long ownerId; 	// Need this to build assets
	double value;
	
	String assetName;
	double assetValue;
	double premiumValue;
	double assetGrowth;
	double assetIncome;

	
	public void delete(long flpId) {
		dbObject.start();
		dbObject.delete("LLC_GP_TABLE", "LLC_TOOL_ID='" + flpId + "'");
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

	public long getId() {
		return id;
	}

	public long getLlcToolId() {
		return llcToolId;
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
		dbObject.setTable("LLC_GP_TABLE");
		HashMap<String,Object> account = null;

		String sql = "select * from LLC_GP_TABLE where ID='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			setValue(getDouble(account,"VALUE"));
			setLlcToolId(getLong(account,"LLC_TOOL_ID"));
			setId(getLong(account,"ID"));
			setOwnerId(getLong(account,"ID"));
			setAssetName(getString(account,"DESCRIPTION"));
		}

		LlcTool llc = new LlcTool();
		llc.setDbObject();
		llc.setId(getLlcToolId());
		llc.calculate();
		
		assetValue = value;
		premiumValue = llc.getPremiumGpValue();
		assetName = llc.getName();
		assetIncome = llc.getIncome();
		assetGrowth = llc.getGrowth();
		
		llc = null;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("LLC_GP_TABLE");
		dbObject.clearFields();
		dbAddField("VALUE", value);
		dbAddField("LLC_TOOL_ID", llcToolId);
		dbAddField("OWNER_ID", ownerId);
		dbAddField("DESCRIPTION",getAssetName());
		dbObject.setWhere("ID='" + id + "'");

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ID from LLC_GP_TABLE where UUID='" + uuid + "'");
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
	
	public void setId(long id) {
		this.id = id;
	}
	public void setLlcToolId(long llcToolId) {
		this.llcToolId = llcToolId;
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
		dbObject.setTable("LLC_GP_TABLE");
		dbObject.clearFields();
		dbAddField("VALUE", value);
		dbAddField("LLC_TOOL_ID", llcToolId);
		dbAddField("OWNER_ID", ownerId);
		dbAddField("DESCRIPTION",getAssetName());
		dbObject.setWhere("ID='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}
}
