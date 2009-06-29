package com.teag.analysis;

/**
 * @author stay
 * Created on May 26, 2005
 * Description - Calcualte the Estate tax upon 2nd death with retirement income and zcalc functions.
 */

import com.zcalc.zCalc;

public class CalcEstateTax {
	double taxableEstate = 0;
	double clientTaxableGifts = 0;
	double spouseTaxableGifts = 0;
	double byPassTrust = 1000;
	double taxableLessByPass = 0;
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
	
	public void calculateTax() {
		taxableLessByPass = taxableEstate;// - byPassTrust;
		
		zCalc zc = new zCalc();
		zc.StartUp();
        
        double gifts = clientTaxableGifts + spouseTaxableGifts;

		estateTax = zc.zET(taxableLessByPass,gifts,0.0,0.0,year,0,0.0,0.0,0L,0.0,0L);

		/*
		double maxrate = zc.zFETMAXRATE(year,0);
		estateTax = maxrate * taxableLessByPass;
		*/
		
		netIncTax = zc.zFET(taxableLessByPass,spouseTaxableGifts,0.0,0.0,year,clientTaxableGifts,0.0,0.0,0l,0.0,0L)/(taxableEstate);
		estateTaxDeduction = rpBalance * netIncTax;
		stateTax = rpBalance * stateTaxRate;
		federalTaxable = rpBalance - estateTaxDeduction - stateTax;
		federalIncomeTax = zc.zFITET(federalTaxable,0.0, 0.0, 0.0, year,0.0, 0, 0.0);
		totalIncomeTax = federalIncomeTax + stateTax + estateTax;
		zc.ShutDown();
	}
	
	/**
	 * @return Returns the byPassTrust.
	 */
	public double getByPassTrust() {
		return byPassTrust;
	}
	/**
	 * @param byPassTrust The byPassTrust to set.
	 */
	public void setByPassTrust(double byPassTrust) {
		this.byPassTrust = byPassTrust;
	}
	/**
	 * @return Returns the estateTax.
	 */
	public double getEstateTax() {
		return estateTax;
	}
	/**
	 * @param estateTax The estateTax to set.
	 */
	public void setEstateTax(double estateTax) {
		this.estateTax = estateTax;
	}
	/**
	 * @return Returns the estateTaxDeduction.
	 */
	public double getEstateTaxDeduction() {
		return estateTaxDeduction;
	}
	/**
	 * @param estateTaxDeduction The estateTaxDeduction to set.
	 */
	public void setEstateTaxDeduction(double estateTaxDeduction) {
		this.estateTaxDeduction = estateTaxDeduction;
	}
	
	/**
	 * @return Returns the federalTaxable.
	 */
	public double getFederalTaxable() {
		return federalTaxable;
	}
	/**
	 * @param federalTaxable The federalTaxable to set.
	 */
	public void setFederalTaxable(double federalTaxable) {
		this.federalTaxable = federalTaxable;
	}
	/**
	 * @return Returns the netIncTax.
	 */
	public double getNetIncTax() {
		return netIncTax;
	}
	/**
	 * @param netIncTax The netIncTax to set.
	 */
	public void setNetIncTax(double netIncTax) {
		this.netIncTax = netIncTax;
	}
	/**
	 * @return Returns the rpBalance.
	 */
	public double getRpBalance() {
		return rpBalance;
	}
	/**
	 * @param rpBalance The rpBalance to set.
	 */
	public void setRpBalance(double rpBalance) {
		this.rpBalance = rpBalance;
	}
	/**
	 * @return Returns the stateTax.
	 */
	public double getStateTax() {
		return stateTax;
	}
	/**
	 * @param stateTax The stateTax to set.
	 */
	public void setStateTax(double stateTax) {
		this.stateTax = stateTax;
	}
	/**
	 * @return Returns the stateTaxRate.
	 */
	public double getStateTaxRate() {
		return stateTaxRate;
	}
	/**
	 * @param stateTaxRate The stateTaxRate to set.
	 */
	public void setStateTaxRate(double stateTaxRate) {
		this.stateTaxRate = stateTaxRate;
	}
	/**
	 * @return Returns the taxableEstate.
	 */
	public double getTaxableEstate() {
		return taxableEstate;
	}
	/**
	 * @param taxableEstate The taxableEstate to set.
	 */
	public void setTaxableEstate(double taxableEstate) {
		this.taxableEstate = taxableEstate;
	}

	/**
	 * @return Returns the clientTaxableGifts.
	 */
	public double getClientTaxableGifts() {
		return clientTaxableGifts;
	}
	/**
	 * @param clientTaxableGifts The clientTaxableGifts to set.
	 */
	public void setClientTaxableGifts(double clientTaxableGifts) {
		this.clientTaxableGifts = clientTaxableGifts;
	}
	/**
	 * @return Returns the spouseTaxableGifts.
	 */
	public double getSpouseTaxableGifts() {
		return spouseTaxableGifts;
	}
	/**
	 * @param spouseTaxableGifts The spouseTaxableGifts to set.
	 */
	public void setSpouseTaxableGifts(double spouseTaxableGifts) {
		this.spouseTaxableGifts = spouseTaxableGifts;
	}
	/**
	 * @return Returns the taxableLessByPass.
	 */
	public double getTaxableLessByPass() {
		return taxableLessByPass;
	}
	/**
	 * @param taxableLessByPass The taxableLessByPass to set.
	 */
	public void setTaxableLessByPass(double taxableLessByPass) {
		this.taxableLessByPass = taxableLessByPass;
	}
	/**
	 * @return Returns the totalIncomeTax.
	 */
	public double getTotalIncomeTax() {
		return totalIncomeTax;
	}
	/**
	 * @param totalIncomeTax The totalIncomeTax to set.
	 */
	public void setTotalIncomeTax(double totalIncomeTax) {
		this.totalIncomeTax = totalIncomeTax;
	}
	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	public double getFederalIncomeTax() {
		return federalIncomeTax;
	}

	public void setFederalIncomeTax(double federalIncomeTax) {
		this.federalIncomeTax = federalIncomeTax;
	}
	
}
