package com.estate.controller;

import java.util.Date;

import com.estate.toolbox.Grat;

public class GratController {
	double fmv;
	double discount;
	double discountValue;
	double irsRate;
	Date irsDate;
	double assetGrowth;
	double assetIncome;
	int term;
	int endBegin;
	int freq;
	int age;
	double lifeExpectancy;
	double estateTaxRate;
	
	boolean useInsurance = false;
	double lifeDeathBenefit;
	double lifePremium;
	double lifeCashValue;
	boolean useLLC = false;

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
	
	double balance[];
	double income[];
	double growth[];
	
	public void calculate() {
		Grat grat = new Grat();
		grat.setAge(age);
		grat.setFmv(fmv);
		grat.setDiscount(discount);
		grat.setDiscountValue(fmv * (1 - discount));
		grat.setIrsRate(irsRate);
		grat.setEndBegin(endBegin);
		grat.setFreq(freq);
		grat.setTypeId(typeId);
		grat.setAnnuityInterest(annuityInterest);
		grat.setTerm(term);
		
		if(typeId == 0) {
			grat.setAnnuityPayment(annuityPayment);
		}
		
		if(typeId == 1) {
			grat.setRemainderInterest(remainderInterest);
		}

		if(typeId == 3) {
			grat.setRemainderInterest(0);
		}
		
		grat.calculate();
		
		annuityFactor = grat.getAnnuityFactor();
		annuityPayment = grat.getAnnuityPayment();
		remainderInterest = grat.getRemainderInterest();
		annuityInterest = grat.getAnnuityInterest();
		retainedAnnuityInterest = grat.getRetainedAnnuityInterest();
		optimalPaymentRate = grat.getOptimalPaymentRate();
		payoutRate = grat.getPayoutRate();
		totalInterest = grat.getTotalInterest();
		taxableGift = grat.getTaxableGift();
		leverage = grat.getLeverage();
		lifeExpectancy = grat.getLifeExpectancy();
		discountValue = grat.getDiscountValue();

		balance = new double[term];
		income = new double[term];
		growth = new double[term];
		
		double b = fmv;
		for(int i=0; i < term; i++){
			balance[i] = b;
			income[i] = b * assetIncome;
			growth[i] = b * assetGrowth;
			b = b + income[i] + growth[i] - annuityPayment;
		}
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

	public double getAssetGrowth() {
		return assetGrowth;
	}

	public double getAssetIncome() {
		return assetIncome;
	}

	public double[] getBalance() {
		return balance;
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

	public double getFmv() {
		return fmv;
	}

	public int getFreq() {
		return freq;
	}

	public double[] getGrowth() {
		return growth;
	}

	public double[] getIncome() {
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

	public double getLifeCashValue() {
		return lifeCashValue;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public double getLifeExpectancy() {
		return lifeExpectancy;
	}

	public double getLifePremium() {
		return lifePremium;
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

	public boolean isUseLLC() {
		return useLLC;
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

	public void setAssetGrowth(double assetGrowth) {
		this.assetGrowth = assetGrowth;
	}

	public void setAssetIncome(double assetIncome) {
		this.assetIncome = assetIncome;
	}

	public void setBalance(double[] balance) {
		this.balance = balance;
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

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public void setGrowth(double[] growth) {
		this.growth = growth;
	}

	public void setIncome(double[] income) {
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

	public void setLifeCashValue(double lifeCashValue) {
		this.lifeCashValue = lifeCashValue;
	}

	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}

	public void setLifeExpectancy(double lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
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

	public void setTypeId(int id) {
		typeId = id;
	}

	public void setUseInsurance(boolean useInsurance) {
		this.useInsurance = useInsurance;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}
	
	
	
}
