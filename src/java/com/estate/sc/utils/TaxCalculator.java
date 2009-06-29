package com.estate.sc.utils;

import com.zcalc.*;

public class TaxCalculator {
	
	double ordinaryIncome;
	double dividendIncome;
	double capGains15;
	double capGains25;
	double capGains28;
	int year;
	double adjustmentsToGross;
	double itemizedDeductions;
	int filingStatus;
	int stdDeductions;
	int personalExemptions;
	double socialSecurity;
	double totalTax;
	double stateTax;
	double federalTax;
	double stateTaxRate = 0;
	
	public void calculate() {
		// First calculate State Tax
		if(stateTaxRate > 0) {
			double agi = 0;
			agi = ordinaryIncome + dividendIncome + capGains15 + capGains28 + capGains25 - adjustmentsToGross;
			agi -= itemizedDeductions;
			agi -= socialSecurity;			// We need to subtract the Social Security first as it is alread part of the Cash Receipts
			stateTax = agi * stateTaxRate;
		}
		
		// Calculate Federal Income Tax
		double federalAGI = ordinaryIncome + dividendIncome + capGains15 + capGains28 + capGains25 - adjustmentsToGross;
		federalAGI += socialSecurity * .85;

		double federalDeductions = stateTax + itemizedDeductions;
		
		zCalc zc = new zCalc();
		zc.StartUp();
	
		double taxableIncome = zc.zTI(federalAGI, federalDeductions, filingStatus, personalExemptions, 0, year, 0, 0);
		federalTax = zc.zFIT(taxableIncome, filingStatus, capGains15, capGains28, capGains25, year, 0, 0, dividendIncome);
		totalTax = federalTax + stateTax;
		
		zc.ShutDown();
		
	}
	
	public static void main(String args[]) {
		TaxCalculator tc = new TaxCalculator();
		tc.setOrdinaryIncome(78000);
		tc.setDividendIncome(10000);
		tc.setCapGains15(15000);
		tc.setCapGains25(8000);
		tc.setCapGains28(25000);
		tc.setAdjustmentsToGross(2000);
		tc.setFilingStatus(2);
		tc.setItemizedDeductions(25000);
		tc.setPersonalExemptions(5);
		tc.setYear(2007);
		tc.calculate();
		
		System.out.println("Federal Tax  is " + tc.getFederalTax() );
		System.out.println("State Tax is " + tc.getStateTax());
		
	}

	public double getAdjustmentsToGross() {
		return adjustmentsToGross;
	}

	public void setAdjustmentsToGross(double adjustmentsToGross) {
		this.adjustmentsToGross = adjustmentsToGross;
	}

	public double getCapGains15() {
		return capGains15;
	}

	public void setCapGains15(double capGains15) {
		this.capGains15 = capGains15;
	}

	public double getCapGains25() {
		return capGains25;
	}

	public void setCapGains25(double capGains25) {
		this.capGains25 = capGains25;
	}

	public double getCapGains28() {
		return capGains28;
	}

	public void setCapGains28(double capGains28) {
		this.capGains28 = capGains28;
	}

	public double getDividendIncome() {
		return dividendIncome;
	}

	public void setDividendIncome(double dividendIncome) {
		this.dividendIncome = dividendIncome;
	}

	public double getFederalTax() {
		return federalTax;
	}

	public void setFederalTax(double federalTax) {
		this.federalTax = federalTax;
	}

	public int getFilingStatus() {
		return filingStatus;
	}

	public void setFilingStatus(int filingStatus) {
		this.filingStatus = filingStatus;
	}

	public double getItemizedDeductions() {
		return itemizedDeductions;
	}

	public void setItemizedDeductions(double itemizedDeductions) {
		this.itemizedDeductions = itemizedDeductions;
	}

	public double getOrdinaryIncome() {
		return ordinaryIncome;
	}

	public void setOrdinaryIncome(double ordinaryIncome) {
		this.ordinaryIncome = ordinaryIncome;
	}

	public int getPersonalExemptions() {
		return personalExemptions;
	}

	public void setPersonalExemptions(int personalExemptions) {
		this.personalExemptions = personalExemptions;
	}

	public double getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public double getStateTax() {
		return stateTax;
	}

	public void setStateTax(double stateTax) {
		this.stateTax = stateTax;
	}

	public double getStateTaxRate() {
		return stateTaxRate;
	}

	public void setStateTaxRate(double stateTaxRate) {
		this.stateTaxRate = stateTaxRate;
	}

	public int getStdDeductions() {
		return stdDeductions;
	}

	public void setStdDeductions(int stdDeductions) {
		this.stdDeductions = stdDeductions;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
