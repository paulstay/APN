package com.teag.scenario1;

import com.teag.bean.RealEstateBean;

public class RealEstateCashFlow {
	static int MAX_TABLE = 60;
	double[] income;
	double[] mortgage;
	
	double[] depreciation;
	
	int finalDeath;
	
	RealEstateCashFlow() {
		income = new double[MAX_TABLE];
		mortgage = new double[MAX_TABLE];
		depreciation = new double[MAX_TABLE];
	}

	public void addRealEstate(RealEstateBean r) {
		// Income generated
		double rents = r.getGrossRents();
		double rentGrowth = r.getGrossRentsGrowth();
		double expenses = r.getOperatingExpenses();
		double expenseGrowth = r.getGrowthExpenses();
		
		double realEstateIncome = rents - expenses;
		for( int i = 0; i < MAX_TABLE; i++) {
			income[i] = realEstateIncome;
			rents += rents * rentGrowth;
			expenses += expenses * expenseGrowth;
			realEstateIncome = rents - expenses;
		}

		// Calcualte Mortgage information
		double loanTerm = r.getLoanTerm();
		double loanFreq = r.getLoanFreq();
		
		double loanPayment = r.getLoanPayment() * loanFreq; // use yearly
		if( loanPayment > 0) {
			double years = Math.floor(loanTerm / loanFreq) + 1;
			for( int i = 0; i < years; i++) {
				mortgage[i] += loanPayment;
			}
		}
		
		
		// Depreciation using Straight - Line method
		double dValue = r.getDepreciationValue();
		double sValue = r.getSalvageValue();
		double dYears = r.getDepreciationYears();
		double dPayment = 0;
		
		if( dYears > 0) {
			dPayment = (dValue - sValue)/dYears;
			for(int i = 0; i < dYears; i++) {
				depreciation[i] += dPayment;
			}
		}
	}
}
