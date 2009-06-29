package com.estate.view;

public class AssetsAvailableBean {

	int id;
	int assetType;
	String description;
	double remainingValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRemainingValue() {
		return remainingValue;
	}
	public void setRemainingValue(double remainingValue) {
		this.remainingValue = remainingValue;
	}
	public int getAssetType() {
		return assetType;
	}
	public void setAssetType(int assetType) {
		this.assetType = assetType;
	}
}
