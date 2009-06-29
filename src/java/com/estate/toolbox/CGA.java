package com.estate.toolbox;

/*
 * Charitable Gift Annuity
 * Author Paul R. Stay
 * Date 16 April 2008
 * 
 * Build the details for a Charitable Gift Annuity.
 * 
 * Copyright @2008 The Estate Advisory Group
 */

import java.util.Date;

import com.estate.utils.AcgaValues;
import com.zcalc.zCalc;

public class CGA {

	double fmv;
	double basis;
	double charityFmvPortion;
	double irsRate;
	Date irsDate;
	double annuityRate;
	double endBegin;
	double frequency;
	double growth;
	double income;
	int clientAge;
	int spouseAge;
	boolean isSingle;
	double annuityFactor;
	double comparison; // Use starting value for comparison
	double compInc; // Increment for comparison;
	double annuityPayment;
	double totalInterest;
	double annuityInterest;
	double remainderInterest;
	double ordinaryIncome;
	double capitalGains;
	double taxFree;
	double lifeExpectancy; 	// Either based on single or both (second to die).
	double maxAnnuityRate;
	String calcType = "A";	// A = use ACGA rates, X = use maximum rate, M = Manual entry
	double charitableDed;
	
	public void calculate() {
		zCalc zc = new zCalc();
		zc.StartUp();
		// First check if we are calculating single or dual.
		AcgaValues av = new AcgaValues();
		
		annuityFactor = zc.zTERMLIFE(0, irsRate, 0, clientAge, spouseAge, 0, 0, 0, 0, (int)frequency, (int)endBegin, 0, 0, 0);
		maxAnnuityRate = .9 / annuityFactor;	// 90% rule
				
		if(calcType.equals("A")) {
			if(spouseAge > 0)
				annuityRate = av.doubleLife(clientAge, spouseAge)/100.0;
			else
				annuityRate = av.singleLife(clientAge)/100.0;
		}
		
		if(calcType.equals("X"))
			annuityRate = maxAnnuityRate;
		

		annuityPayment = zc.zGIFTANN(0, fmv, basis, annuityRate, irsRate, clientAge, spouseAge, 0, (int)frequency, (int)endBegin, 0, 0, 0, 0, 0, 0, 0);
		ordinaryIncome = zc.zGIFTANN(1, fmv, basis, annuityRate, irsRate, clientAge, spouseAge, 0, (int)frequency, (int)endBegin, 0, 0, 0, 0, 0, 0, 0);
		capitalGains = zc.zGIFTANN(2, fmv, basis, annuityRate, irsRate, clientAge, spouseAge, 0, (int)frequency, (int)endBegin, 0, 0, 0, 0, 0, 0, 0);
		taxFree = zc.zGIFTANN(3, fmv, basis, annuityRate, irsRate, clientAge, spouseAge, 0, (int)frequency, (int)endBegin, 0, 0, 0, 0, 0, 0, 0);
		totalInterest = fmv;
		annuityInterest = zc.zANNTERMLIFE(fmv, annuityRate, irsRate, 0, clientAge,spouseAge,0,0, 0, 0, (int)frequency, (int)endBegin, 0,0,0, 1);
		remainderInterest = totalInterest - annuityInterest;
		lifeExpectancy = zc.zERM(clientAge, spouseAge,0,20);		// We use Mortality table sec 73 annuity
		
		charitableDed = remainderInterest;
		
		zc.ShutDown();
		
		
	}

	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public double getAnnuityInterest() {
		return annuityInterest;
	}

	public double getAnnuityPayment() {
		return annuityPayment;
	}

	public double getAnnuityRate() {
		return annuityRate;
	}

	public double getBasis() {
		return basis;
	}

	public String getCalcType() {
		return calcType;
	}

	public double getCapitalGains() {
		return capitalGains;
	}

	public double getCharitableDed() {
		return charitableDed;
	}

	public double getCharityFmvPortion() {
		return charityFmvPortion;
	}

	public int getClientAge() {
		return clientAge;
	}

	public double getComparison() {
		return comparison;
	}

	public double getCompInc() {
		return compInc;
	}

	public double getEndBegin() {
		return endBegin;
	}

	public double getFmv() {
		return fmv;
	}

	public double getFrequency() {
		return frequency;
	}

	public double getGrowth() {
		return growth;
	}

	public double getIncome() {
		return income;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getLifeExpectancy() {
		return lifeExpectancy;
	}

	public double getMaxAnnuityRate() {
		return maxAnnuityRate;
	}

	public double getOrdinaryIncome() {
		return ordinaryIncome;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public double getTaxFree() {
		return taxFree;
	}

	public double getTotalInteres() {
		return totalInterest;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityInterest(double annuityInterest) {
		this.annuityInterest = annuityInterest;
	}

	public void setAnnuityPayment(double annuityPayment) {
		this.annuityPayment = annuityPayment;
	}

	public void setAnnuityRate(double annuityRate) {
		this.annuityRate = annuityRate;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public void setCapitalGains(double capitalGains) {
		this.capitalGains = capitalGains;
	}

	public void setCharitableDed(double charitableDed) {
		this.charitableDed = charitableDed;
	}

	public void setCharityFmvPortion(double charityFmvPortion) {
		this.charityFmvPortion = charityFmvPortion;
	}

	public void setClientAge( int age) {
		clientAge = age;
	}

	public void setComparison(double comparison) {
		this.comparison = comparison;
	}

	public void setCompInc(double compInc) {
		this.compInc = compInc;
	}

	public void setEndBegin(double endBegin) {
		this.endBegin = endBegin;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setLifeExpectancy(double lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public void setMaxAnnuityRate(double maxAnnuityRate) {
		this.maxAnnuityRate = maxAnnuityRate;
	}

	public void setOrdinaryIncome(double ordinaryIncome) {
		this.ordinaryIncome = ordinaryIncome;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public void setSpouseAge(int age) {
		spouseAge = age;
	}

	public void setTaxFree(double taxFree) {
		this.taxFree = taxFree;
	}

	public void setTotalInteres(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	
	
}
