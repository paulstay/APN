package com.teag.tbox;

import java.util.Date;

import com.zcalc.zCalc;

public class Qprt {
	double value;
	double basis;
	double endValue;
	double discountValue = 0;
	double growth;
	double fractionalDiscount;
	boolean reversionRetained = true;
	double startTerm;
	double reversionaryInterest;
	double remainderInterest;
	double taxableGift;
	double retainedInterest;
	double estateTaxSavings;
	double estateTaxRate;
	double builtInGain;
	double irsRate;
	double valueGiftToHeirs;
	Date irsDate;
	int pmtPeriod = 1;	// Default yearly
	int endBegin = 0;	// At end of pmtPeriod
	int clientAge = 0;
	
	int term;
	
	double remainder;
	double reversion;
	double incomeInterest;
	double giftLeverage;
	
	
	public Qprt() {
		term = 0;
		value = 0;
		basis = 0;
		growth=.0;
		fractionalDiscount = 0;
		irsRate = 0.0;
		irsDate = new Date();
		estateTaxRate = 0;
		//calculate();
	}
	public void calculate() {
		// We need to do this before zcalc.....
		if(fractionalDiscount > 0){
			discountValue = value * (1-fractionalDiscount);
		} else {
			discountValue = value;
		}

		initZCalc();

		reversion = discountValue * reversionaryInterest;
		remainder = discountValue * remainderInterest;
		double i = 1 - (reversionaryInterest + remainderInterest);
        incomeInterest = i * discountValue;
		endValue = value * Math.pow(1 + growth,term);
		builtInGain = endValue - basis;
		estateTaxSavings = estateTaxRate * (endValue - taxableGift);
		valueGiftToHeirs = (taxableGift / discountValue)*incomeInterest;
		retainedInterest = discountValue - taxableGift;
        giftLeverage = value/valueGiftToHeirs;
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

	public double getDiscountValue() {
		return discountValue;
	}

	public int getEndBegin() {
		return endBegin;
	}

	public double getEndValue() {
		return endValue;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getEstateTaxSavings() {
		return estateTaxSavings;
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

	public double getIncomeInterest() {
		return incomeInterest;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public int getPmtPeriod() {
		return pmtPeriod;
	}

	public double getRemainder() {
		return remainder;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getRetainedInterest() {
		return retainedInterest;
	}

	public double getReversion() {
		return reversion;
	}

	public double getReversionaryInterest() {
		return reversionaryInterest;
	}

	public double getStartTerm() {
		return startTerm;
	}

	public double getTaxableGift() {
		return taxableGift;
	}

	public int getTerm() {
		return term;
	}

	public double getValue() {
		return value;
	}

	public double getValueGiftToHeirs() {
		return valueGiftToHeirs;
	}

	public void initZCalc() {
		zCalc zc = new zCalc();
		zc.StartUp();
		if( reversionRetained){
			reversionaryInterest = zc.zTERMLIFE(3, irsRate, term, clientAge, 0l, 0l, 0l, 0l, 0l, pmtPeriod, endBegin, 0, 0, 0);
			remainderInterest = zc.zTERMLIFE(2, irsRate, term, clientAge, 0l, 0l, 0l, 0l, 0l, pmtPeriod, endBegin, 0, 0, 0);
			taxableGift = zc.zQPRTGIFT(irsRate, term, clientAge, 0, 0, discountValue, 0);
		}
		else {
			reversionaryInterest = 0;
			remainderInterest = zc.zTERMLIFE(2, irsRate, term, 0l, 0l, 0l, 0l, 0l, 0l, pmtPeriod, endBegin, 0, 0, 0);
			taxableGift = zc.zQPRTGIFT(irsRate, term, 0, 0, 1, discountValue, 0);
		}
		zc.ShutDown();
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

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public void setEndBegin(int endBegin) {
		this.endBegin = endBegin;
	}

	public void setEndValue(double endValue) {
		this.endValue = endValue;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setEstateTaxSavings(double estateTaxSavings) {
		this.estateTaxSavings = estateTaxSavings;
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

	public void setIncomeInterest(double incomeInterest) {
		this.incomeInterest = incomeInterest;
	}
	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}
	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}
	public void setPmtPeriod(int pmtPeriod) {
		this.pmtPeriod = pmtPeriod;
	}
	public void setRemainder(double remainder) {
		this.remainder = remainder;
	}
	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}
	public void setRetainedInterest(double retainedInterest) {
		this.retainedInterest = retainedInterest;
	}
	public void setReversion(double reversion) {
		this.reversion = reversion;
	}
	public void setReversionaryInterest(double reversionaryInterest) {
		this.reversionaryInterest = reversionaryInterest;
	}
	public void setReversionRetained(boolean reversionRetained) {
		this.reversionRetained = reversionRetained;
	}
	public void setStartTerm(double startTerm) {
		this.startTerm = startTerm;
	}
	public void setTaxableGift(double taxableGift) {
		this.taxableGift = taxableGift;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void setValueGiftToHeirs(double valueGiftToHeirs) {
		this.valueGiftToHeirs = valueGiftToHeirs;
	}

	
}
