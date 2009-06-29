package com.teag.estate;

import com.zcalc.zCalc;

public class GratTrust {

	double fmv;
	double discount;
	double growthRate;
	double irsRate;
	int term;
	int startTerm;
	int endBegin;
	int freq;
	int age;
	double lifeExpectancy;

	String calcType;

	double estateTaxRate;
	double estateTaxSavings;
	double annuityFactor;
	double annuityPayment;
	double annuityIncrease;
	double remainderInterest;
	int annuityFreq; // 1 annual, 2 seminannual, .....
	double annuityInterest;
	double retainedAnnuityInterest;
	double optimalPaymentRate;
	double actualPaymentRate;
	double totalInterest;
	double taxableGift;
	double gratBenefit;

	double leverage;

	// If we already know the annuity payment as specified, calculate the other
	// variables or dials including taxableGift.
	public void annuityCalc() {
		zCalc zc = new zCalc();
		zc.StartUp();
		retainedAnnuityInterest = zc.zANNTERMLIFE(discount, annuityPayment,
				irsRate, term, 0, 0, 0, 0, 0, annuityIncrease, annuityFreq,
				endBegin, 0, 0, 0, 0);
		remainderInterest = discount - retainedAnnuityInterest;
		annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease,
				annuityFreq, endBegin, 0);
		annuityInterest = discount;
		taxableGift = remainderInterest;
		actualPaymentRate = annuityPayment / discount;
		optimalPaymentRate = zc.zANNRATETARGET(discount, 0, irsRate, term, 0,
				0, 0, 0, 0, annuityIncrease, annuityFreq, endBegin, 0, 0, 0);
		lifeExpectancy = zc.zLE(age, 0, 0, 0, 0, 0, 0);

		gratBenefit = zc.zGRATBEN(irsRate, term, growthRate, annuityIncrease,
				annuityFreq, fmv, actualPaymentRate, 0);
		estateTaxSavings = estateTaxRate * (gratBenefit - taxableGift);

		if (taxableGift > 0)
			leverage = fmv / taxableGift;
		else
			leverage = 0;
		zc.ShutDown();
	}

	// We already have a target remainder interest calculate the annuityPayment.
	public void remainderCalc() {
		zCalc zc = new zCalc();
		zc.StartUp();
		optimalPaymentRate = zc.zGRATZO(irsRate, term, annuityIncrease,
				annuityFreq, 0);
		actualPaymentRate = zc.zANNRATETARGET(discount, remainderInterest,
				irsRate, term, 0, 0, 0, 0, 0, annuityIncrease, annuityFreq,
				endBegin, 0, 0, 0);
		annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease,
				annuityFreq, endBegin, 0);
		annuityPayment = actualPaymentRate * discount;
		annuityInterest = discount - remainderInterest;
		retainedAnnuityInterest = discount - remainderInterest;
		taxableGift = remainderInterest;
		totalInterest = discount;
		lifeExpectancy = zc.zLE(age, 0, 0, 0, 0, 0, 0);
		gratBenefit = zc.zGRATBEN(irsRate, term, growthRate, annuityIncrease,
				annuityFreq, fmv, actualPaymentRate, 0);
		estateTaxSavings = estateTaxRate * (gratBenefit - taxableGift);

		if (taxableGift > 0)
			leverage = fmv / taxableGift;
		else
			leverage = 0;

		zc.ShutDown();
	}

	public void calculate() {
		if (calcType.equalsIgnoreCase("A")) { // Annuity Payment already
			// specified
			annuityCalc();
		} else if (calcType.equalsIgnoreCase("R")) { // Remainder Interest
			// specified, calculate
			// AnnuityPayment
			remainderCalc();
		} else {
			zeroCalc(); // We want a zero gift, so remainder interest equals
			// zero
		}
	}

	public double getActualPaymentRate() {
		return actualPaymentRate;
	}

	public int getAge() {
		return age;
	}

	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public int getAnnuityFreq() {
		return annuityFreq;
	}

	public double getAnnuityIncrease() {
		return annuityIncrease;
	}

	public double getAnnuityInterest() {
		return annuityInterest;
	}

	public double getAnnuityPayment() {
		return annuityPayment;
	}

	public String getCalcType() {
		return calcType;
	}

	public double getDiscount() {
		return discount;
	}

	public int getEndBegin() {
		return endBegin;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getEstateTaxSavings() {
		return estateTaxSavings;
	}

	public double getFmv() {
		return fmv;
	}

	public int getFreq() {
		return freq;
	}

	public double getGratBenefit() {
		return gratBenefit;
	}

	public double getGrowthRate() {
		return growthRate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getLeverage() {
		return leverage;
	}

	public double getLifeExpectancy() {
		return lifeExpectancy;
	}

	public double getOptimalPaymentRate() {
		return optimalPaymentRate;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getRetainedAnnuityInterest() {
		return retainedAnnuityInterest;
	}

	public int getStartTerm() {
		return startTerm;
	}

	public double getTaxableGift() {
		return taxableGift;
	}

	public int getTerm() {
		return term;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public void Grat() {
		calcType = "A"; // Default Annuity Payment
		endBegin = 0; // End of payment
		freq = 1; // Annual Payment
	}

	public void setActualPaymentRate(double actualPaymentRate) {
		this.actualPaymentRate = actualPaymentRate;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityFreq(int annuityFreq) {
		this.annuityFreq = annuityFreq;
	}

	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}

	public void setAnnuityInterest(double annuityInterest) {
		this.annuityInterest = annuityInterest;
	}

	public void setAnnuityPayment(double annuityPayment) {
		this.annuityPayment = annuityPayment;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setEndBegin(int endBegin) {
		this.endBegin = endBegin;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setEstateTaxSavings(double estateTaxSavings) {
		this.estateTaxSavings = estateTaxSavings;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public void setGratBenefit(double gratBenefit) {
		this.gratBenefit = gratBenefit;
	}

	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setLeverage(double leverage) {
		this.leverage = leverage;
	}

	public void setLifeExpectancy(double lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public void setOptimalPaymentRate(double optimalPaymentRate) {
		this.optimalPaymentRate = optimalPaymentRate;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setRetainedAnnuityInterest(double retainedAnnuityInterest) {
		this.retainedAnnuityInterest = retainedAnnuityInterest;
	}

	public void setStartTerm(int startTerm) {
		this.startTerm = startTerm;
	}

	public void setTaxableGift(double taxableGift) {
		this.taxableGift = taxableGift;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	// We want a zero'd out grat, no remainderInterest
	public void zeroCalc() {
		zCalc zc = new zCalc();
		zc.StartUp();
		optimalPaymentRate = zc.zANNRATETARGET(discount, 0, irsRate, term, 0,
				0, 0, 0, 0, annuityIncrease, annuityFreq, endBegin, 0, 0, 0);
		annuityPayment = optimalPaymentRate * discount;
		annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease,
				annuityFreq, endBegin, 0);
		lifeExpectancy = zc.zLE(age, 0, 0, 0, 0, 0, 0);
		annuityInterest = discount;
		totalInterest = discount;
		remainderInterest = 0;
		actualPaymentRate = optimalPaymentRate;

		gratBenefit = zc.zGRATBEN(irsRate, term, growthRate, annuityIncrease,
				annuityFreq, fmv, actualPaymentRate, 0);
		estateTaxSavings = estateTaxRate * (gratBenefit - taxableGift);

		zc.ShutDown();
	}

}
