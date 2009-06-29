package com.estate.sc.utils;

import com.zcalc.zCalc;

public class CalcEstateTaxes {
	double taxableEstate = 0;
	double clientTaxableGifts = 0;
	double spouseTaxableGifts = 0;
	double stateTaxRate = 0.0;
	double netIncTax = 0.0;
	double rpBalance = 0;
	double estateTaxDeduction = 0;
	double stateTax;
	double federalTaxable = 0;
	double totalIncomeTax = 0;
	double estateTax;
	double federalIncomeTax = 0;
	int year;
	
	public void calc() {
		zCalc zc = new zCalc();
		zc.StartUp();
		estateTax = zc.zET(taxableEstate,spouseTaxableGifts,0.0,0.0,year,clientTaxableGifts,0.0,0.0,0L,0.0,0L);
		netIncTax = zc.zFET(taxableEstate,spouseTaxableGifts,0.0,0.0,year,clientTaxableGifts,0.0,0.0,0l,0.0,0L)/(taxableEstate);
		estateTaxDeduction = rpBalance * netIncTax;
		stateTax = rpBalance * stateTaxRate;
		federalTaxable = rpBalance - estateTaxDeduction - stateTax;
		federalIncomeTax = zc.zFITET(federalTaxable,0.0, 0.0, 0.0, year,0.0, 0, 0.0);
		totalIncomeTax = federalIncomeTax + stateTax + estateTax;
		zc.ShutDown();
	}

	public double getClientTaxableGifts() {
		return clientTaxableGifts;
	}

	public void setClientTaxableGifts(double clientTaxableGifts) {
		this.clientTaxableGifts = clientTaxableGifts;
	}

	public double getEstateTax() {
		return estateTax;
	}

	public void setEstateTax(double estateTax) {
		this.estateTax = estateTax;
	}

	public double getEstateTaxDeduction() {
		return estateTaxDeduction;
	}

	public void setEstateTaxDeduction(double estateTaxDeduction) {
		this.estateTaxDeduction = estateTaxDeduction;
	}

	public double getFederalIncomeTax() {
		return federalIncomeTax;
	}

	public void setFederalIncomeTax(double federalIncomeTax) {
		this.federalIncomeTax = federalIncomeTax;
	}

	public double getFederalTaxable() {
		return federalTaxable;
	}

	public void setFederalTaxable(double federalTaxable) {
		this.federalTaxable = federalTaxable;
	}

	public double getNetIncTax() {
		return netIncTax;
	}

	public void setNetIncTax(double netIncTax) {
		this.netIncTax = netIncTax;
	}

	public double getRpBalance() {
		return rpBalance;
	}

	public void setRpBalance(double rpBalance) {
		this.rpBalance = rpBalance;
	}

	public double getSpouseTaxableGifts() {
		return spouseTaxableGifts;
	}

	public void setSpouseTaxableGifts(double spouseTaxableGifts) {
		this.spouseTaxableGifts = spouseTaxableGifts;
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

	public double getTaxableEstate() {
		return taxableEstate;
	}

	public void setTaxableEstate(double taxableEstate) {
		this.taxableEstate = taxableEstate;
	}

	public double getTotalIncomeTax() {
		return totalIncomeTax;
	}

	public void setTotalIncomeTax(double totalIncomeTax) {
		this.totalIncomeTax = totalIncomeTax;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}
