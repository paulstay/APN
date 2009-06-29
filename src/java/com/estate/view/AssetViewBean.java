
package com.estate.view;

/**
 * @author Paul Stay
 * Description: Store the Asset data for view in the Planning Tools
 * Date: September 2008
 * Copyright @2008 Advance Estate Strategies (AES) and Grow Charity
 *
 */
public class AssetViewBean {
	int id;
	String name;
	double value;
	double basis;
	double growth;
	double income;
	int assetId;
	int assetType;
	boolean gift = false;	// Used for SIDIT
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getBasis() {
		return basis;
	}
	public void setBasis(double basis) {
		this.basis = basis;
	}
	public double getGrowth() {
		return growth;
	}
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public int getAssetType() {
		return assetType;
	}
	public void setAssetType(int assetType) {
		this.assetType = assetType;
	}
	public boolean isGift() {
		return gift;
	}
	public void setGift(boolean gift) {
		this.gift = gift;
	}

}
