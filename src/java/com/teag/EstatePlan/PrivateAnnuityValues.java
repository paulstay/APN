package com.teag.EstatePlan;

import com.teag.estate.PrivateAnnuityTool;

public class PrivateAnnuityValues {

	double annuity = 0;
	double amount = 0;
	double taxFree[] = new double[EstatePlanTable.MAX_TABLE];
	double capGains[] = new double[EstatePlanTable.MAX_TABLE];
	double growth[] = new double[EstatePlanTable.MAX_TABLE];
	int finalDeath = 25;
	int cAge;
	int sAge;
	
	public void calculate(long toolId) {
		PrivateAnnuityTool pat = new PrivateAnnuityTool();
		pat.setId(toolId);
		pat.read();
		pat.setCAge(cAge);
		pat.setSAge(sAge);
		pat.calculate();

		double tax[] = pat.getTaxFreeTable();
		double cap[] = pat.getCapGainsTable();
		annuity += pat.getAnnuityPayment();
		amount += pat.getAmount();
		
		double tAmount = amount;
		for( int i = 0; i < EstatePlanTable.MAX_TABLE; i++) {
			taxFree[i] += tax[i];
			capGains[i] += cap[i] * pat.getCapitalGainsRate();
			growth[i] += tAmount * (1 + pat.getGrowth());
			tAmount = tAmount * ( 1 + pat.getGrowth());
		}
	}
}
