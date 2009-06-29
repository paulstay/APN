package com.estate.beans;

import java.util.ArrayList;

public class AssetList {

	private ArrayList<UserAssets> assetList = new ArrayList<UserAssets>();
	int nextId;
	
	public AssetList() {
		nextId = 1;
	}
	
	public UserAssets[] getAssetList() {
		UserAssets assets[] = null;
		if(!assetList.isEmpty()) {
			assets = new UserAssets[assetList.size()];
			assetList.toArray(assets);
		}
		
		return assets;
	}

	public double getTotal(){
		double x = 0;
		for(UserAssets a : assetList) {
			x += a.getFmv();
		}
		return x;
	}
	
	public double getIncome() {
		double x = 0;
		double total = getTotal();
		for(UserAssets a : assetList) {
			x += a.getIncome() * a.getFmv()/total;
		}
		return x;
	}
	
	public double getGrowth() {
		double x = 0;
		double total = getTotal();
		for(UserAssets a : assetList) {
			x += a.getGrowth() * a.getFmv()/total;
		}
		return x;
	}
	
	public void setAsset(UserAssets a) {
		if(a != null && assetList.indexOf(a) == -1) {
			synchronized(a) {
				a.setId(nextId++);
			}
			assetList.add(a);
		}
	}
	
	public void setDelete(int assetId) {
		UserAssets asset = null;
		if(assetList.isEmpty())
			return;
		for(UserAssets a: assetList) {
			if(a.getId() == assetId) {
				asset = a;
				break;
			}
		}
		assetList.remove(asset);
	}
}
