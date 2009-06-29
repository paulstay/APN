package com.estate.sc.utils;

public class Asset {
	private String description;
	private double value;
	private double basis;
	private double growth;
	private double income;
	private double taxable;
	private double pctExcessCash;
	private boolean isReceipt = true;
	
	double cashFlow[] = new double[ScenarioConstants.MAX_TABLE];
	
	
	public void genIncomeFlow() {
		double cValue = value;
		for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
			cValue += cValue * growth; 
			cashFlow[i] = cValue * income;
		}
	}
	
	public void genNetWorth() {
		double cValue = value;
		for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
			cValue += cValue * growth;
			cashFlow[i] = cValue;
		}
	}

	public void setCashFlow(int year, double v) {
		cashFlow[year] = v;
	}
	
	public double getCashFlow(int year) {
		return cashFlow[year];
	}
	
	public double getBasis() {
		return basis;
	}
	public void setBasis(double basis) {
		this.basis = basis;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public double getTaxable() {
		return taxable;
	}
	public void setTaxable(double taxable) {
		this.taxable = taxable;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	public double getPctExcessCash() {
		return pctExcessCash;
	}

	public void setPctExcessCash(double pctExcessCash) {
		this.pctExcessCash = pctExcessCash;
	}

	public double[] getCashFlow() {
		return cashFlow;
	}

	public boolean isReceipt() {
		return isReceipt;
	}

	public void setReceipt(boolean isReceipt) {
		this.isReceipt = isReceipt;
	}


	
	
}
