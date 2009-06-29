package com.teag.estate;

/**
 * @author stay
 * Description this class is used to build a spreadsheet.
 */
public class AssetData {
	public static final int SECURITY=1;
	public static final int OTHER = 2;
	private String name;
	private double value = 0;
	private double income = 0;
	private double growth= 0;
	private double discountRate;
	private int assetType;
	public boolean isPartOfFlp = false;
	
	public AssetData(int t) {
		assetType = t;
		value = 0;
		income = 0;
		growth = 0;
	}
	
	public void addIncome(double income) {
		value += income;
	}

	/**
	 * @return Returns the assetType.
	 */
	public int getAssetType() {
		return assetType;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}
	
	/**
	 * @return Returns the growth.
	 */
	public double getGrowth() {
		return growth;
	}
	
	public double getGrowthValue() {
		return value * growth;
	}
	/**
	 * @return Returns the income.
	 */
	public double getIncome() {
		return income;
	}
	public double getIncomeValue() {
		return value * income;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}
	public boolean isPartOfFlp() {
		return isPartOfFlp;
	}
	/**
	 * @param assetType The assetType to set.
	 */
	public void setAssetType(int assetType) {
		this.assetType = assetType;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	/**
	 * @param growth The growth to set.
	 */
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	/**
	 * @param income The income to set.
	 */
	public void setIncome(double income) {
		this.income = income;
	}
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPartOfFlp(boolean isPartOfFlp) {
		this.isPartOfFlp = isPartOfFlp;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		sb.append("Name : " + getName());
		sb.append("\tValue : " + getValue());
		sb.append("\tGrowth + " + getGrowth());
		sb.append("\tIncome : " + getIncome());
		sb.append("\tDiscountRate : " + getDiscountRate());
		sb.append("\tIs FLP Asset : " + isPartOfFlp());

		return sb.toString();
	}
	public void update() {
		value = value * ( 1.0 + growth);
	}
}
