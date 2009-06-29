package com.teag.asset;

import com.estate.constants.AssetDatabase;

public class Asset {
	public String name;
	public double value;
	public long id;
	public AssetDatabase asset;
	public boolean addToEcorp = false;
	
	public AssetDatabase getAsset() {
		return asset;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return asset.name();
	}
	public double getValue() {
		return value;
	}
	public boolean isAddToEcorp() {
		return addToEcorp;
	}
	public void setAddToEcorp(boolean addToEcorp) {
		this.addToEcorp = addToEcorp;
	}
	public void setAsset(AssetDatabase asset) {
		this.asset = asset;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
