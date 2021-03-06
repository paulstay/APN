package com.estate.sc.utils;

public class Portfolio {
	double balance[] = new double[ScenarioConstants.MAX_TABLE];
	double growth[] = new double[ScenarioConstants.MAX_TABLE];
	double income[] = new double[ScenarioConstants.MAX_TABLE];
	double endBalance[] = new double[ScenarioConstants.MAX_TABLE];
	double excessCash[] = new double[ScenarioConstants.MAX_TABLE];
	double pctExcess;
	double growthRate;
	double incomeRate;
	String description;
	
	public void genYear(int i, double eCash) {
		double gr = Math.rint(balance[i] * growthRate);
		double bal = Math.rint(balance[i]);
		growth[i] = balance[i] * growthRate;
		income[i] = balance[i] * incomeRate;
		double e = gr + bal + Math.rint(eCash);
		endBalance[i] = e;
		if(i < ScenarioConstants.MAX_TABLE-1)
			balance[i+1] = endBalance[i];
		excessCash[i] = eCash;
	}
	
	public void setBeginingBalance(double v) {
		balance[0] = v;
	}
	
	public double getIncomeValue(int year) {
		if(year > ScenarioConstants.MAX_TABLE-1){
			return 0;
		}
		return income[year];
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getGrowthRate() {
		return growthRate;
	}
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	public double getIncomeRate() {
		return incomeRate;
	}
	public void setIncomeRate(double incomeRate) {
		this.incomeRate = incomeRate;
	}
	public double getPctExcess() {
		return pctExcess;
	}
	public void setPctExcess(double pctExcess) {
		this.pctExcess = pctExcess;
	}
	public double[] getBalance() {
		return balance;
	}
	public double[] getEndBalance() {
		return endBalance;
	}
	public double[] getExcessCash() {
		return excessCash;
	}
	public double[] getGrowth() {
		return growth;
	}
	public double[] getIncome() {
		return income;
	}
	
	
	
}
