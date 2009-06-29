package com.estate.toolbox;

import java.util.Date;

import com.zcalc.zCalc;

public class QPRT {

	double fmv;
	double basis;
	double growth;
	double fractionalDiscount;
	double dValue;
	double irsRate;
	Date irsDate;
	int clientAge;
	int term;
	String homeType;		// "V" vacation home, "H" primary Home
	double estateTax;
	double rentAfterTerm;
	double finalDeath;
	double lifeExpectancy;
	boolean reversionRetained = true;
	
	double reversionaryInterest;
	double reversionaryIntValue;
	double remainderInterest;
	double remainderIntValue;
	double retainedIntValue;
	double taxableGift;
	double incomeInterest;
	double incomeIntValue;
	double taxableGiftValue;
	double endResidenceValue;
	double builtInGain;
	double estateTaxSavings;
	double estateTaxRate;
	double valueGiftToHeirs;
	double giftLeverage;
	
	public void calculate() {

		if(fractionalDiscount > 0) {
			dValue = fmv * (1 - fractionalDiscount);
		} else {
			dValue = fmv;
		}
		
		zCalc zc = new zCalc();
		zc.StartUp();
		reversionaryInterest = zc.zTERMLIFE(3, irsRate, term, clientAge, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		remainderInterest = zc.zTERMLIFE(2, irsRate, term, clientAge, 0, 0, 0, 0, 0, 0,0, 0, 0, 0);
		if(reversionRetained)
			taxableGiftValue = zc.zQPRTGIFT(irsRate, term, clientAge, 0, 0, dValue, 0);
		else
			taxableGiftValue = zc.zQPRTGIFT(irsRate, term, clientAge, 0, 1, fmv, 0);
		lifeExpectancy = zc.zLE(clientAge, 0, 0, 0, 0, 0, 0);
		zc.ShutDown();
		
		// Generate the data.....
		incomeInterest = 1 - (reversionaryInterest + remainderInterest);
		incomeIntValue = dValue * incomeInterest;
		reversionaryIntValue = dValue * reversionaryInterest;
		remainderIntValue = dValue * remainderInterest;
		retainedIntValue = dValue - taxableGiftValue;
		endResidenceValue = fmv * Math.pow(1 + growth, term);
		builtInGain = endResidenceValue - basis;
		estateTaxSavings = estateTaxRate * (endResidenceValue - taxableGiftValue);
		taxableGift = taxableGiftValue / dValue;
		valueGiftToHeirs = taxableGift * dValue;
		giftLeverage = fmv / valueGiftToHeirs;
	}

	public double getBasis() {
		return basis;
	}

	public double getBuiltInGain() {
		return builtInGain;
	}

	public int getClientAge() {
		return clientAge;
	}

	public double getDValue() {
		return dValue;
	}

	public double getEndResidenceValue() {
		return endResidenceValue;
	}

	public double getEstateTax() {
		return estateTax;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getEstateTaxSavings() {
		return estateTaxSavings;
	}

	public double getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public double getFractionalDiscount() {
		return fractionalDiscount;
	}

	public double getGiftLeverage() {
		return giftLeverage;
	}

	public double getGrowth() {
		return growth;
	}

	public String getHomeType() {
		return homeType;
	}

	public double getIncomeInterest() {
		return incomeInterest;
	}

	public double getIncomeIntValue() {
		return incomeIntValue;
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

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getRemainderIntValue() {
		return remainderIntValue;
	}

	public double getRentAfterTerm() {
		return rentAfterTerm;
	}

	public double getRetainedIntValue() {
		return retainedIntValue;
	}

	public double getReversionaryInterest() {
		return reversionaryInterest;
	}

	public double getReversionaryIntValue() {
		return reversionaryIntValue;
	}

	public double getTaxableGift() {
		return taxableGift;
	}

	public double getTaxableGiftValue() {
		return taxableGiftValue;
	}

	public int getTerm() {
		return term;
	}

	public double getValueGiftToHeirs() {
		return valueGiftToHeirs;
	}

	public boolean isReversionRetained() {
		return reversionRetained;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setBuiltInGain(double builtInGain) {
		this.builtInGain = builtInGain;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public void setDValue(double value) {
		dValue = value;
	}

	public void setEndResidenceValue(double endResidenceValue) {
		this.endResidenceValue = endResidenceValue;
	}

	public void setEstateTax(double estateTax) {
		this.estateTax = estateTax;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setEstateTaxSavings(double estateTaxSavings) {
		this.estateTaxSavings = estateTaxSavings;
	}

	public void setFinalDeath(double finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setFractionalDiscount(double fractionalDiscount) {
		this.fractionalDiscount = fractionalDiscount;
	}

	public void setGiftLeverage(double giftLeverage) {
		this.giftLeverage = giftLeverage;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}

	public void setIncomeInterest(double incomeInterest) {
		this.incomeInterest = incomeInterest;
	}

	public void setIncomeIntValue(double incomeIntValue) {
		this.incomeIntValue = incomeIntValue;
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

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setRemainderIntValue(double remainderIntValue) {
		this.remainderIntValue = remainderIntValue;
	}

	public void setRentAfterTerm(double rentAfterTerm) {
		this.rentAfterTerm = rentAfterTerm;
	}

	public void setRetainedIntValue(double retainedIntValue) {
		this.retainedIntValue = retainedIntValue;
	}

	public void setReversionaryInterest(double reversionaryInterest) {
		this.reversionaryInterest = reversionaryInterest;
	}

	public void setReversionaryIntValue(double reversionaryIntValue) {
		this.reversionaryIntValue = reversionaryIntValue;
	}

	public void setReversionRetained(boolean reversionRetained) {
		this.reversionRetained = reversionRetained;
	}

	public void setTaxableGift(double taxableGift) {
		this.taxableGift = taxableGift;
	}

	public void setTaxableGiftValue(double taxableGiftValue) {
		this.taxableGiftValue = taxableGiftValue;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setValueGiftToHeirs(double valueGiftToHeirs) {
		this.valueGiftToHeirs = valueGiftToHeirs;
	}
}
