package com.teag.estate;

public class TableAsset {

	double[] balance = new double[60];
	double[] growth = new double[60];
	double[] income = new double[60];
	
	double fmv = 0;
	double growthRate = .05;
	double incomeRate = .03;
	String description;
	
	public void genTable() {
		balance[0] = fmv;
		growth[0] = fmv * growthRate;
		income[0] = fmv * incomeRate;
		for(int i=1; i < 60; i++ ) {
			balance[i] = balance[i-1] + growth[i-1];
			growth[i] = balance[i] * growthRate;
			income[i] = balance[i] * incomeRate;
		}
	}

	public void genTable(int i, double addToBalance){
		if(i==0) {
			balance[0] = fmv + addToBalance;
			growth[0] = fmv * growthRate;
			income[0] = fmv * incomeRate;
		} else {
			balance[i] = balance[i-1] + growth[i-1] + addToBalance;
			growth[i] = balance[i] * growthRate;
			income[i] = balance[i] * incomeRate;
		}
	}
	
}
