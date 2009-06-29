package com.teag.estate;

/**
 * @author paul stay
 * Created on Apr 29, 2005
 * Description - This class is used to store data for a tool, as it processes and calcualtes the results.
  */
public class ToolAssetData {
	String name;
	double value = 0;
	double discount = 0.0;	// If 1 there is no discount
	double growth = 0;
	double income = 0;
	double liability = 0;
	double turnoverRate = 0;
	double basis = 0;
	double incomeGrowth = 0;
	boolean flpAsset = false;
	int assetType;
	
	public int getAssetType() {
		return assetType;
	}
	public double getBasis() {
		return basis;
	}
	public double getDiscount() {
		return discount;
	}
	public double getGrowth() {
		return growth;
	}
	public double getIncome() {
		return income;
	}
	public double getIncomeGrowth() {
		return incomeGrowth;
	}
	public double getLiability() {
		return liability;
	}
	public String getName() {
		return name;
	}
	public double getTurnoverRate() {
		return turnoverRate;
	}
	public double getValue() {
		return value;
	}
	public boolean isFlpAsset() {
		return flpAsset;
	}
	public void setAssetType(int assetType) {
		this.assetType = assetType;
	}
	public void setBasis(double basis) {
		this.basis = basis;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public void setFlpAsset(boolean flpAsset) {
		this.flpAsset = flpAsset;
	}
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public void setIncomeGrowth(double incomeGrowth) {
		this.incomeGrowth = incomeGrowth;
	}
	public void setLiability(double liability) {
		this.liability = liability;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTurnoverRate(double turnoverRate) {
		this.turnoverRate = turnoverRate;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
