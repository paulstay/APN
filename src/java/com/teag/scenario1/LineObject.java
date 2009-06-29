package com.teag.scenario1;

import com.estate.constants.AssetDatabase;

public class LineObject {
	AssetDatabase assetType;
	String description;
	double row[];
	int finalDeath;
	double percentTaxable;
	double growth;
	double iRate;
	double beginBalance;
	boolean charitableDeduction = false;
	boolean taxDeduction = false;
	
	Sections section;
	
	public LineObject(int finalDeath) {
		percentTaxable = 1.0;
		this.finalDeath = finalDeath;
		if( finalDeath == 0)
			finalDeath = 60;
		row = new double[finalDeath];
	}

	public void addValue(int year, double v) {
		row[year] += v;
	}
	
	public AssetDatabase getAssetType() {
		return assetType;
	}
	
	public double getBeginBalance() {
		return beginBalance;
	}
	
	public String getDescription() {
		return description;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public double getGrowth() {
		return growth;
	}

	public double getIRate() {
		return iRate;
	}

	public double getPercentTaxable() {
		return percentTaxable;
	}

	public double[] getRow() {
		return row;
	}

	public Sections getSection() {
		return section;
	}

	public double getValue(int year) {
		return row[year];
	}

	public boolean isCharitableDeduction() {
		return charitableDeduction;
	}

	public boolean isTaxDeduction() {
		return taxDeduction;
	}

	public void setAssetType(AssetDatabase assetType) {
		this.assetType = assetType;
	}

	public void setBeginBalance(double beginBalance) {
		this.beginBalance = beginBalance;
	}

	public void setCharitableDeduction(boolean charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setIRate(double rate) {
		iRate = rate;
	}

	public void setPercentTaxable(double percentTaxable) {
		this.percentTaxable = percentTaxable;
	}

	public void setRow(double[] row) {
		this.row = row;
	}

	public void setSection(Sections section) {
		this.section = section;
	}

	public void setTaxDeduction(boolean taxDeduction) {
		this.taxDeduction = taxDeduction;
	}

	public void setValue(int year, double v) {
		row[year] = v;
	}
}
