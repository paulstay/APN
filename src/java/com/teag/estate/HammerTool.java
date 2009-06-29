/*
 * Created on Apr 28, 2005
 *
 */
package com.teag.estate;

import java.util.ArrayList;
import java.util.HashMap;

import com.teag.bean.LoadAssetBean;

public class HammerTool extends EstatePlanningTool {
	
	long id;
	String uuid;
	
	double count;
	double value;
	double growth;
	double income;
	String name;
	HashMap<String,Object> account;
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	ToolAssetData assetData[];
	
	public void buildAssetList() {
//		 Clear the list so we won't duplicate
		assetList.clear();
		// Get all of the tool asset ditributions
	
		for( int i = 0; i < assetData.length; i++) {
			AssetData ad;
				
			if( assetData[i].getAssetType() == LoadAssetBean.SECURITIES)
				ad = new AssetData(AssetData.SECURITY);
			else
				ad = new AssetData(AssetData.OTHER);
			
			ad.setName(assetData[i].getName());
			ad.setValue(assetData[i].getValue() * assetData[i].getDiscount());
			ad.setGrowth(assetData[i].getGrowth());
			ad.setIncome(assetData[i].getIncome());
			assetList.add(ad);
		}
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#calculate()
	 */
	@Override
	public void calculate() {
		ToolAssetDistribution tad = new ToolAssetDistribution();
		tad.setDbObject();
		tad.setToolId(id);
		tad.setToolTableId(this.getToolTableId());
		tad.calculate();
		assetData = tad.getToolAssetData();
		value = tad.getTotalValue();
		growth = tad.getWeightedGrowth();
		income = tad.getWeightedIncome();
		count = assetData.length;
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("HAMMER_TOOL");
			dbObj.delete("HAMMER_TOOL","ID='" + id + "'");
			dbObj.stop();
		}

	}

	public HashMap<String,Object> getAccount() {
		return account;
	}

	public ToolAssetData[] getAssetData() {
		return assetData;
	}

	public double getCount() {
		return count;
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

	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#getToolTableId()
	 */
	@Override
	public long getToolTableId() {

		return HAMMER_TYPE;
	}
	public double getValue() {
		return value;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable("HAMMER_TOOL");
		dbObj.clearFields();
		
		dbAddField("VALUE", value);
		dbAddField("DESCRIPTION", name);
		dbAddField("GROWTH", growth);
		dbAddField("INCOME", income);
		dbAddField("COUNT", count);
		
		int err = dbObj.insert();
		if( err == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> rec = dbObj.execute("select ID from HAMMER_TOOL where UUID='" + uuid + "'");
			Object o = rec.get("ID");
			if( o != null ) 
				id= Integer.parseInt(o.toString());
		}
		dbObj.stop();
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#read()
	 */
	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("FLP_TOOL");
			HashMap<String,Object> account;
			String sql = "Select * from FLP_TOOL where ID='" + id + "'";
			account = dbObj.execute(sql);
			dbObj.stop();
			
			if( account != null) {
				name = getString(account,"DESCRIPTION");
				value = getDouble(account, "VALUE");
				growth = getDouble(account,"GROWTH");
				income = getDouble(account,"INCOME");
				count = getDouble(account,"COUNT");
			}
		}

	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#report()
	 */
	@Override
	public void report() {

	}
	public void setAccount(HashMap<String,Object> account) {
		this.account = account;
	}
	public void setAssetData(ToolAssetData[] assetData) {
		this.assetData = assetData;
	}
	public void setCount(double count) {
		this.count = count;
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
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(double value) {
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("HAMMER_TOOL");
		dbObj.clearFields();
		
		dbAddField("VALUE", value);
		dbAddField("DESCRIPTION", name);
		dbAddField("GROWTH", growth);
		dbAddField("INCOME", income);
		dbAddField("COUNT", count);
		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#writeupText()
	 */
	@Override
	public String writeupText() {

		return null;
	}
}
