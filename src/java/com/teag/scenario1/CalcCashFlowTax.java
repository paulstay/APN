/*
 * Created on May 31, 2005
 *
 */
package com.teag.scenario1;

/**
 * @author Paul Stay
 * Description - Used to calculate Income tax for both state an dfederal which are then combined 
 * in the Cash flow projection sheets
 * Date - September 2006 
 *
 */

import com.zcalc.zCalc;

public class CalcCashFlowTax {
	public double stateAGI;
	public double totalCashInflows;
	public double crutCapGains;
	public double socialSecurity;
	public double bonds;
	public double charitableDed;
	public double stateTaxableIncome;
	public double stateIncomeTax;
	public double stateIncomeTaxRate;
	public double federalAGI;
	public double federalDeductions;
	public double federalTaxableIncome;
	public double federalOrdinaryTax;
	public double capTaxRate;
	public double totalTax;
	public double otherDed = 0;
	
	public void calculate(int year) {
		stateTaxableIncome = stateAGI - charitableDed - otherDed;
		stateIncomeTax = stateTaxableIncome * stateIncomeTaxRate;
		federalAGI = stateAGI + (socialSecurity * .85);
		federalDeductions = charitableDed + stateIncomeTax + otherDed;
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
