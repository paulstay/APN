package com.estate.sc.utils;


/**
 * CalculateTax - use this module to calculate State tax and Federal tax
 * for the cash flow.
 * @author Paul Stay
 *
 */

import com.zcalc.*;

public class CalculateTax {
	public double stateAgi;
	public double cashInflows;
	public double socialSecurity;
	public double charitableDeduction;
	public double stateTaxableIncome;
	public double stateIncomeTax;
	public double stateIncomeTaxRate;
	public double federalAgi;
	public double federalDeduction;
	public double federalTaxableIncome;
	public double federalOrdinaryTax;
	public double totalTax;
	
	public void calculate(int year) {
		stateTaxableIncome = stateAgi - charitableDeduction;
		stateIncomeTax = stateTaxableIncome * stateIncomeTaxRate;
		federalAgi = stateAgi + (socialSecurity * .85);
		federalDeduction = charitableDeduction + stateIncomeTax;
		
		double test80 = federalDeduction * .8;
		double agiLess = federalAgi - 121200;
		double test03;
		
		if( test80 < 0)
			test03 = 0;
		else
			test03 = agiLess * .03;
		
		double allowDed = Math.min(test80,test03);
		federalTaxableIncome = federalAgi - (federalDeduction - allowDed);
		
		zCalc zc = new zCalc();
		zc.StartUp();
		federalOrdinaryTax = zc.zFIT(federalTaxableIncome, 2,0,0,0, year, 0, 0,0);
		zc.ShutDown();
		totalTax = federalOrdinaryTax + stateIncomeTax;
	}

	public double getCashInflows() {
		return cashInflows;
	}

	public void setCashInflows(double cashInflows) {
		this.cashInflows = cashInflows;
	}

	public double getCharitableDeduction() {
		return charitableDeduction;
	}

	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}

	public double getFederalAgi() {
		return federalAgi;
	}

	public void setFederalAgi(double federalAgi) {
		this.federalAgi = federalAgi;
	}

	public double getFederalDeduction() {
		return federalDeduction;
	}

	public void setFederalDeduction(double federalDeduction) {
		this.federalDeduction = federalDeduction;
	}

	public double getFederalOrdinaryTax() {
		return federalOrdinaryTax;
	}

	public void setFederalOrdinaryTax(double federalOrdinaryTax) {
		this.federalOrdinaryTax = federalOrdinaryTax;
	}

	public double getFederalTaxableIncome() {
		return federalTaxableIncome;
	}

	public void setFederalTaxableIncome(double federalTaxableIncome) {
		this.federalTaxableIncome = federalTaxableIncome;
	}

	public double getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public double getStateAgi() {
		return stateAgi;
	}

	public void setStateAgi(double stateAgi) {
		this.stateAgi = stateAgi;
	}

	public double getStateIncomeTax() {
		return stateIncomeTax;
	}

	public void setStateIncomeTax(double stateIncomeTax) {
		this.stateIncomeTax = stateIncomeTax;
	}

	public double getStateIncomeTaxRate() {
		return stateIncomeTaxRate;
	}

	public void setStateIncomeTaxRate(double stateIncomeTaxRate) {
		this.stateIncomeTaxRate = stateIncomeTaxRate;
	}

	public double getStateTaxableIncome() {
		return stateTaxableIncome;
	}

	public void setStateTaxableIncome(double stateTaxableIncome) {
		this.stateTaxableIncome = stateTaxableIncome;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}
	
	
	
}
