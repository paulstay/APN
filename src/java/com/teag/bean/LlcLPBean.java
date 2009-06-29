package com.teag.bean;

/**
 * @author Paul Stay
 * Created on May 26, 2005
 *
 */


import java.util.HashMap;

import com.teag.estate.LlcTool;
import com.teag.estate.ToolAssetData;

public class LlcLPBean extends AssetSqlBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5647218225871723464L;
	long id;
	String uuid;
	long llcToolId;
	double value;
	long ownerId;
	
	String assetName;
	double assetValue;
	double discount;
	double discountValue;
	double growth;
	double income;
	
	ToolAssetData lpAssets[];
	
	public void delete(long flpId) {
		dbObject.start();
		dbObject.delete("LLC_LP_TABLE", "LLC_TOOL_ID='" + flpId + "'");
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
		return growth;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#getAssetIncome()
	 */
	@Override
	public double getAssetIncome() {
		return income;
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

	public double getDiscount() {
		return discount;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public double getGrowth() {
		return growth;
	}

	public long getId() {
		return id;
	}

	public double getIncome() {
		return income;
	}

	public long getLlcToolId() {
		return llcToolId;
	}

	public ToolAssetData[] getLpAssets() {
		return lpAssets;
	}

	public long getOwnerId() {
		return ownerId;
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
		dbObject.setTable("LLC_LP_TABLE");
		HashMap<String,Object> account = null;

		String sql = "select * from LLC_LP_TABLE where ID='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			setValue(getDouble(account,"VALUE"));
			setLlcToolId(getLong(account,"LLC_TOOL_ID"));
			setId(getLong(account,"ID"));
			setOwnerId(getLong(account,"OWNER_ID"));
			setAssetName(getString(account,"DESCRIPTION"));
		}

		LlcTool llc = new LlcTool();
		llc.setDbObject();
		llc.setId(getLlcToolId());
		llc.read();
		llc.calculate();
			
		assetValue = value;
		discount = llc.getDiscountRate();
		discountValue = value * (1 - discount);
		growth = llc.getGrowth();
		income = llc.getIncome();

		lpAssets = llc.getLlcAssets();
		// add in the discount for the shares.
		for(int i = 0; i < lpAssets.length; i++) {
			lpAssets[i].setValue(lpAssets[i].getValue() * llc.getMemberShares());
			lpAssets[i].setDiscount(lpAssets[i].getValue() * (1-discount));
			lpAssets[i].setFlpAsset(true);
		}
		llc = null;
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.AssetSqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("LLC_LP_TABLE");
		dbObject.clearFields();
		dbAddField("VALUE", value);
		dbAddField("LLC_TOOL_ID", llcToolId);
		dbAddField("OWNER_ID", ownerId);
		dbAddField("DESCRIPTION",getAssetName());
		dbObject.setWhere("ID='" + id + "'");

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ID from LLC_LP_TABLE where UUID='" + uuid + "'");
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

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}
	
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public void setLlcToolId(long llcToolId) {
		this.llcToolId = llcToolId;
	}
	public void setLpAssets(ToolAssetData[] lpAssets) {
		this.lpAssets = lpAssets;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
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
		dbObject.setTable("LLC_LP_TABLE");
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
