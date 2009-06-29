package com.estate.toolbox;

import java.util.Date;

public class Clat {

	double fmv;
	double discount;
	double discountValue;
	double assetGrowth;
	double assetIncome;
	double irsRate;
	Date irsDate;
	double discountAssumptionRate;
	double taxRate;
	String calculationType; // A = annuity payment, R = Remainder Interest, Z =
							// zero out

	String trustType;		// T = term, L = Life, S = Shorter of, G = Greater of
	boolean grantor = false; // Trust pays taxes.
	int lifeType =0; 		//Default is 0 = Last To Die, 1 = First To Die
	int age1;
	int age2;
	int clatTermLength;
	
	double remainderInterest;
	double annuityPayment;
	double optimalRate;
	int term;
	double payoutRate;
	double annuityFactor;
	double charitableDeduction;

	double npvFamily;
	double npvCharity;
	double netToFamily;
	double netToCharity;

	boolean useLLC = false;
	int finalDeath;

	double estateTaxRate;
	int endBegin;
	int freq;
	double annuityIncrease;
	int recalculate = 0;
	
	public void calculate() {
		int status = lifeType;
		discountValue = fmv * (1 - discount);
		
		if(trustType.equals("G")){
			status = 2 + lifeType;
		}
		TermLifeFactors factors = new TermLifeFactors();
		factors.setIrsRate(irsRate);
		factors.setTerm(term);
		factors.setAge1(age1);
		factors.setAge2(age2);
		factors.setAnnuityIncrease(annuityIncrease);
		factors.setFreq(freq);
		factors.setStatus(status);
		factors.calculate();
		
		annuityFactor = factors.getAnnuityFactor();
		double lifeExpectancy = factors.getLifeExpectancy();
		
		if(calculationType.equals("A")){
			payoutRate = annuityPayment/discountValue;
			optimalRate = factors.calculatePayment(discountValue, 0);
			if(payoutRate > optimalRate){
				// This is an error, don't let it happen!
				payoutRate = optimalRate;
			}
			charitableDeduction = annuityPayment * annuityFactor;
			remainderInterest = discountValue - charitableDeduction;
			
		} else {			// Used for both remainder interest and zero out CLAT
			double remainder = 0;
			if(calculationType.equals("R")){
				remainder = remainderInterest;
			}
			annuityPayment = factors.calculatePayment(discountValue, remainder);
			payoutRate = annuityPayment/discountValue;
			optimalRate = factors.calculatePayment(discountValue,0)/discountValue;
			charitableDeduction = annuityPayment * annuityFactor;
		}

		// Calculate the length of the CLAT Term
		if(trustType.equals("T")){
			clatTermLength = term;
		} else	if(trustType.equals("L")) {
			clatTermLength = (int) Math.floor(lifeExpectancy);
		} else 	if(trustType.equals("G")){
			clatTermLength = (int) Math.max(term, lifeExpectancy);
		} else 	if(trustType.equals("S")){
			clatTermLength = (int) Math.min(term, lifeExpectancy);
		} else {
			clatTermLength = term;		// Default
		}
		
		double balance = fmv;
		double ap = annuityPayment;
		double sumAp = 0;
		for(int i=0; i < clatTermLength; i++){
			double growth = balance * assetGrowth;
			double income = balance * assetIncome;
			double taxes = 0;
			if(!grantor){
				taxes = income * taxRate;
			}
			balance = balance + growth + income - taxes - ap;
			sumAp += ap;
			ap *= (1 + annuityIncrease);
		}
		
		netToFamily = balance;
		netToCharity = sumAp;
		
		if(discountAssumptionRate > 0){
			npvFamily = calculateNPV(balance, clatTermLength, discountAssumptionRate);
		} else {
			npvFamily = calculateNPV(balance, clatTermLength, assetGrowth + assetIncome);
		}
		
		npvCharity = calculateNPV(balance, clatTermLength, payoutRate);
	}
	
	public double calculateNPV(double fv, int term, double i) {
		double npv = fv / Math.pow((1 + i), term);
		return npv;
	}
	
	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public double getAnnuityIncrease() {
		return annuityIncrease;
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

	public int getCalculate() {
		int x = 0;
		if( discount > 0)
			discountValue = fmv * (1 - discount);
		else 
			discountValue = fmv;
			
		calculate();
		return x;
	}

	public String getCalculationType() {
		return calculationType;
	}

	public double getCharitableDeduction() {
		return charitableDeduction;
	}

	public double getDiscount() {
		return discount;
	}

	public double getDiscountAssumptionRate() {
		return discountAssumptionRate;
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

	public int getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public int getFreq() {
		return freq;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getNetToCharity() {
		return netToCharity;
	}

	public double getNetToFamily() {
		return netToFamily;
	}

	public double getNpvCharity() {
		return npvCharity;
	}

	public double getNpvFamily() {
		return npvFamily;
	}

	public double getOptimalRate() {
		return optimalRate;
	}

	public double[] getPayments() {
		double payments[] = new double[getTerm()];
		double cPay = annuityPayment;
		for(int i=0; i < term; i++) {
			payments[i] = cPay;
			cPay = cPay * (1 + annuityIncrease);
		}
		
		return payments;
	}

	public double getPayoutRate() {
		return payoutRate;
	}

	public int getRecalculate() {
		return recalculate;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public int getTerm() {
		return term;
	}

	public boolean isGrantor() {
		return grantor;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
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

	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setDiscountAssumptionRate(double discountAssumptionRate) {
		this.discountAssumptionRate = discountAssumptionRate;
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

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public void setGrantor(boolean grantor) {
		this.grantor = grantor;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setNetToCharity(double netToCharity) {
		this.netToCharity = netToCharity;
	}

	public void setNetToFamily(double netToFamily) {
		this.netToFamily = netToFamily;
	}

	public void setNpvCharity(double npvCharity) {
		this.npvCharity = npvCharity;
	}

	public void setNpvFamily(double npvFamily) {
		this.npvFamily = npvFamily;
	}

	public void setOptimalRate(double optimalRate) {
		this.optimalRate = optimalRate;
	}

	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public void setRecalculate(int recalculate) {
		this.recalculate = recalculate;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}

	public String getTrustType() {
		return trustType;
	}

	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}

	public int getAge1() {
		return age1;
	}

	public void setAge1(int age1) {
		this.age1 = age1;
	}

	public int getAge2() {
		return age2;
	}

	public void setAge2(int age2) {
		this.age2 = age2;
	}

	public int getLifeType() {
		return lifeType;
	}

	public void setLifeType(int lifeType) {
		this.lifeType = lifeType;
	}

	public int getClatTermLength() {
		return clatTermLength;
	}

	public void setClatTermLength(int clatTermLength) {
		this.clatTermLength = clatTermLength;
	}
	
}
