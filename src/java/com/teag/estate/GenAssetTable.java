package com.teag.estate;

import java.util.ArrayList;

import com.teag.bean.AssetSqlBean;

public class GenAssetTable {

	ArrayList<AssetData> assets = new ArrayList<AssetData>();
	
	public void addAsset(ToolAssetData a) {
		AssetData ad;
		
		if (a.getAssetType() == AssetSqlBean.SECURITIES) {
			ad = new AssetData(AssetData.SECURITY);
		} else {
			ad = new AssetData(AssetData.OTHER);
		}
		
		ad.setName(a.getName());
		ad.setValue(a.getValue());
		ad.setGrowth(a.getGrowth());
		ad.setIncome(a.getIncome());
		assets.add(ad);
	}
	
	public void clearAssets() {
		assets.clear();
	}
	
	public void genData(String description, double value, double income, double growth, int type){
		AssetData ad = new AssetData(type);
		ad.setName(description);
		ad.setValue(value);
		ad.setIncome(income);
		ad.setGrowth(growth);
		assets.add(ad);
	}
	
	public void genTable(int maxTable) {
		
		for(int i=0; i < maxTable; i++) {
			
		}
		
	}
	
}
