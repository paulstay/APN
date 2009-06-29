package com.estate.toolbox;

import com.zcalc.zCalc;

public class Grat {

	double fmv;
	double discount;
	double discountValue;
	double irsRate;
	int term;
	int endBegin;
	int freq;
	int age;
	double lifeExpectancy;
	
	boolean useInsurance;
	double lifeDeathBenefit;
	double lifePreimum;
	double lifeCashValue;

	int typeId;
	
	double annuityFactor;
	double annuityPayment;
	double annuityIncrease;
	double remainderInterest;
	double annuityInterest;
	double retainedAnnuityInterest;
	double optimalPaymentRate;
	double payoutRate;
	double totalInterest;
	double taxableGift;
	double leverage;

	public Grat() {
		typeId = 0;
		endBegin = 0;
		freq = 1;
	}
	
	// Calcualte using the optimal or zero out GRAT
	public void calcOpt() {
		discountValue = fmv * (1 - discount);
		zCalc zc = new zCalc();
		zc.StartUp();
			optimalPaymentRate = zc.zANNRATETARGET(discountValue, 0, irsRate, term, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
			annuityPayment = optimalPaymentRate * discountValue;
			annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease, freq, endBegin, 0);
			lifeExpectancy = zc.zLE(age,0,0, 0, 0, 0,0);
			annuityInterest = discountValue;
			taxableGift = 0;
			totalInterest = discountValue;
			payoutRate = optimalPaymentRate;
		zc.ShutDown();
	}
	
	// Calculate using a specified annuity payment.
	public void calcPayment() {
		discountValue = fmv * (1 - discount);
		zCalc zc = new zCalc();
		zc.StartUp();
		retainedAnnuityInterest = zc.zANNTERMLIFE(discountValue, annuityPayment, irsRate, term, 0, 0, 0, 0, 0, annuityIncrease, freq, endBegin, 0, 0, 0, 0);
			remainderInterest = discountValue - retainedAnnuityInterest;
			annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease, freq, endBegin, 0);
			annuityInterest = discountValue;
			taxableGift = remainderInterest;
			payoutRate = annuityPayment / discountValue;
			optimalPaymentRate =  zc.zANNRATETARGET(discountValue, 0, irsRate, term, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
			lifeExpectancy = zc.zLE(age,0,0, 0, 0, 0,0);
			leverage = fmv/taxableGift;
		zc.ShutDown();
	}
	
	// calculate in the std way, using the remainder interest to calcualte the 
	// payment.
	public void calcStd() {
		discountValue = fmv * (1 -discount);
		zCalc zc = new zCalc();
		zc.StartUp();
			optimalPaymentRate = zc.zGRATZO(irsRate, term, 0, 1, 0);
			payoutRate = 		 zc.zANNRATETARGET(discountValue, remainderInterest, irsRate, term, 0, 0, 0, 0, 0, annuityIncrease, freq, endBegin, 0, 0, 0);
			//annuityFactor = zc.zTERMLIFE(0, irsRate, term,0, 0, 0, 0, 0, annuityIncrease, freq, endBegin, 0, 0, 0);
			annuityFactor = zc.zTERM(1, irsRate, term, annuityIncrease, freq, endBegin, 0);
			annuityPayment = payoutRate * discountValue;
			annuityFactor = zc.zTERM(0,irsRate, term, annuityIncrease, freq, endBegin, 0);
			annuityInterest = discountValue - remainderInterest;
			retainedAnnuityInterest = discountValue - remainderInterest;
			taxableGift = remainderInterest;
			totalInterest = discountValue;
			lifeExpectancy = zc.zLE(age,0,0, 0, 0, 0,0);
			leverage = fmv/taxableGift;
		zc.ShutDown();
	}
	
	public void calculate() {
			switch(typeId) {
				// Normal calculation (Annuity Payment specified)
				case 0:
					calcPayment();
					break;
				// Remainder Interest
				case 1:
					calcStd();
					break;
				// Zero'd Out Grat
				case 2:
					calcOpt();
					break;
			}
	}

	public boolean checkTerm() {
		if(term <= lifeExpectancy) {
			return true;
		}
		return false;
	}
	
	
	public int getAge() {
		return age;
	}

	public double getAnnuityFactor() {
		return annuityFactor;
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

	public double getDiscount() {
		return discount;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public int getEndBegin() {
		return endBegin;
	}

	public double getFmv() {
		return fmv;
	}

	public int getFreq() {
		return freq;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getLeverage() {
		return leverage;
	}

	public double getLifeCashValue() {
		return lifeCashValue;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public double getLifeExpectancy() {
		return lifeExpectancy;
	}

	public double getLifePreimum() {
		return lifePreimum;
	}

	public double getOptimalPaymentRate() {
		return optimalPaymentRate;
	}

	public double getPayoutRate() {
		return payoutRate;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getRetainedAnnuityInterest() {
		return retainedAnnuityInterest;
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

	public int getTypeId() {
		return typeId;
	}

	public boolean isUseInsurance() {
		return useInsurance;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
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

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public void setEndBegin(int endBegin) {
		this.endBegin = endBegin;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setLeverage(double leverage) {
		this.leverage = leverage;
	}

	public void setLifeCashValue(double lifeCashValue) {
		this.lifeCashValue = lifeCashValue;
	}

	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}

	public void setLifeExpectancy(double lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public void setLifePreimum(double lifePreimum) {
		this.lifePreimum = lifePreimum;
	}

	public void setOptimalPaymentRate(double optimalPaymentRate) {
		this.optimalPaymentRate = optimalPaymentRate;
	}

	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setRetainedAnnuityInterest(double retainedAnnuityInterest) {
		this.retainedAnnuityInterest = retainedAnnuityInterest;
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

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public void setUseInsurance(boolean useInsurance) {
		this.useInsurance = useInsurance;
	}
	
	
}
