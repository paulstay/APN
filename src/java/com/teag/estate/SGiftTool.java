package com.teag.estate;

import java.util.ArrayList;
import java.util.HashMap;


public class SGiftTool extends EstatePlanningTool {

	public static final String ID = "ID";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String PAY_TAX = "PAY_TAX";
	public static final String GIVE_AT_DEATH = "GIVE_AT_DEATH";
	public static final String YEAR_OF_GIFT = "YEAR_OF_GIFT";
	
	final static String tableName = "SGIFT_TOOL";
	long id;
	String description;
	boolean payTax;
	boolean giveAtDeath;

	int yearOfGift;
	HashMap<String,Object> rec;
	
	String uuid;
	
	String assetNames;
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	
	
	Object assetData[];
	
	
	
	@Override
	public void calculate() {
		// ToolAssetDistribution tad = new ToolAssetDistribution();
		EstateToolDistribution tad = new EstateToolDistribution();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());

		tad.buildToolAssetList();
		assetData = tad.getAssetToolData();
		assetNames = tad.getAssetNames();
	}

	@Override
	public void delete() {
		
	}

	public Object[] getAssetData() {
		return assetData;
	}


	public ArrayList<AssetData> getAssetList() {
		return assetList;
	}


	public String getAssetNames() {
		return assetNames;
	}


	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}

	@Override
	public long getToolTableId() {
		return com.estate.constants.ToolTableTypes.SGIFT.id();
	}

	public int getYearOfGift() {
		return yearOfGift;
	}

	@Override
	public void insert() {

		
	}

	public boolean isGiveAtDeath() {
		return giveAtDeath;
	}

	public boolean isPayTax() {
		return payTax;
	}

	@Override
	public void read() {

		
	}

	@Override
	public void report() {
		
	}

	public void setAssetData(Object[] assetData) {
		this.assetData = assetData;
	}

	public void setAssetList(ArrayList<AssetData> assetList) {
		this.assetList = assetList;
	}

	public void setAssetNames(String assetNames) {
		this.assetNames = assetNames;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGiveAtDeath(boolean giveAtDeath) {
		this.giveAtDeath = giveAtDeath;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPayTax(boolean payTax) {
		this.payTax = payTax;
	}

	public void setYearOfGift(int yearOfGift) {
		this.yearOfGift = yearOfGift;
	}

	@Override
	public void update() {
		
	}

	@Override
	public String writeupText() {
		return null;
	}
	
	
}
