package com.teag.tbox;

import java.util.Date;

import com.zcalc.zCalc;

public class Grat {
	public static void main(String args[]) {
		Grat grat = new Grat();
		grat.setTerm(10);
		grat.setEndBegin(0);
		grat.setFreq(1);
		grat.setAge(0);
		grat.setIrsRate(.06);
		grat.setIrsDate(new Date());
		grat.setFmv(1000000);
		grat.setDiscount(.25);
		grat.setGrowth(.05);
		grat.setIncome(.02);
		grat.setRemainderInterest(50000);
		grat.setAnnuityPayment(95000);
		grat.calcStd();
		System.out.println("Payout Rate " + grat.getPayoutRate());
		System.out.println("Annuity Factor " + grat.getAnnuityFactor());
		System.out.println("Annuity Payment " + grat.getAnnuityPayment());
		System.out.println("Optimal Payment Rate " + grat.getOptimalPaymentRate());
		System.out.println("Retained Interest " + grat.getAnnuityInterest());
		System.out.println("Taxable Gift " + grat.getTaxableGift());
	}
	double fmv;
	double discount;
	double discountValue;
	double growth;
	double income;
	double irsRate;
	Date irsDate;
	int term;
	int endBegin;
	int freq;
	
	int age;
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
	double gratBenefit;
	double estateTaxRate;
	double estateTaxSavings;
	
	double leverage;
	
	boolean annuityCalc = false;
	
	public Grat() {
	}

	// Calcualte using the optimal or zero out GRAT
	public void calcOpt() {
		zCalc zc = new zCalc();
		zc.StartUp();
			optimalPaymentRate = zc.zANNRATETARGET(discountValue, 0, irsRate, term, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
			annuityPayment = optimalPaymentRate * discountValue;
			annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease, freq, endBegin, 0);
			annuityInterest = discountValue;
			taxableGift = 0;
			totalInterest = discountValue;
			payoutRate = optimalPaymentRate;
		zc.ShutDown();
	}
	
	// Calculate using a specified annuity payment.
	public void calcPayment() {
		zCalc zc = new zCalc();
		zc.StartUp();
		retainedAnnuityInterest = zc.zANNTERMLIFE(discountValue, annuityPayment, irsRate, term, 0, 0, 0, 0, 0, annuityIncrease, freq, endBegin, 0, 0, 0, 0);
			remainderInterest = discountValue - retainedAnnuityInterest;
			annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease, freq, endBegin, 0);
			annuityInterest = discountValue;
			taxableGift = remainderInterest;
			payoutRate = annuityPayment / discountValue;
			optimalPaymentRate =  zc.zANNRATETARGET(discountValue, 0, irsRate, term, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
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
			leverage = fmv/taxableGift;
		zc.ShutDown();
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

	public double getLeverage() {
		return leverage;
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

	public boolean isAnnuityCalc() {
		return annuityCalc;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAnnuityCalc(boolean annuityCalc) {
		this.annuityCalc = annuityCalc;
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

	public void setLeverage(double leverage) {
		this.leverage = leverage;
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
}
