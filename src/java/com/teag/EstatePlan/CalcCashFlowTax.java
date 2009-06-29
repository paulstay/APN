/*
 * Created on May 31, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author Paul Stay
 *
 */
import com.zcalc.zCalc;
public class CalcCashFlowTax {
	double stateAGI;
	double totalCashInflows;
	double crutCapGains;
	double socialSecurity;
	double bonds;
	double charitableDed;
	double stateTaxableIncome;
	double stateIncomeTax;
	double stateIncomeTaxRate;
	double federalAGI;
	double federalDeductions;
	double federalTaxableIncome;
	double federalOrdinaryTax;
	double capTaxRate;
	double totalTax;
	
	public void calculate(int year) {
		stateTaxableIncome = stateAGI - charitableDed;
		stateIncomeTax = stateTaxableIncome * stateIncomeTaxRate;
		federalAGI = stateAGI + (socialSecurity * .85);
		federalDeductions = charitableDed + stateIncomeTax;
		double test80 = federalDeductions * .80;
		double agiLess = federalAGI - 121200;
		double test03;
		if( test80 < 0)
			test03 = 0;
		else 
			test03 = agiLess * .03;
		double allowDed = Math.min(test80, test03);
		federalTaxableIncome = federalAGI - (federalDeductions - allowDed);
		
		zCalc zc = new zCalc();
		zc.StartUp();
		federalOrdinaryTax = zc.zFIT(federalTaxableIncome,2,0,0,0,year,0,0,0);
		zc.ShutDown();
		double capGains = crutCapGains * capTaxRate;
		totalTax = federalOrdinaryTax + stateIncomeTax + capGains;
	}
}
