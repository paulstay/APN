package com.teag.tbox;

import java.util.Date;

import com.zcalc.zCalc;

public class Clat {
	double fmv;
	double discount;
	double discountValue;
	double remainderInterest;
	double irsRate;		// 7520 rate
	int term;
	Date irsDate;
	double growth;
	double income;
	
	double payoutRate;
	double annuityPayment;
	double annuityFactor;
	double charitableDeduction;
	
	double npvFamily; 
	double npvCharity; 
	double netToFamily;
	double netToCharity;
	
	// Initialize values
	public Clat() {
		setFmv(1000000);
		setDiscount(.35);
		setDiscountValue(getFmv() * (1-getDiscount()));
		setRemainderInterest(10000);
		setIrsRate(.056);
		setTerm(8);
		Date now = new Date();
		setIrsDate(now);
		setGrowth(.07);
		setIncome(.02);
		calculate();
	}
	
	public void calculate() {
		zCalc zc = new zCalc();
		zc.StartUp();
			payoutRate = zc.zANNRATETARGET(discountValue, remainderInterest, irsRate, term, 0,0, 0, 0, 0, 0, 0,0, 0, 0, 0);
			annuityPayment = payoutRate * discountValue;
			annuityFactor = zc.zTERM(0, irsRate,term,0,0,0,0);
			charitableDeduction = zc.zANNTERM(discountValue,annuityPayment,irsRate, term, 0,0,0,0,0); 
		zc.ShutDown();
		double bal = fmv;
		for(int i = 0; i < term; i++) {
			bal = bal + (bal * growth) + (bal * income) - annuityPayment;
		}
		netToFamily = bal;
		netToCharity = annuityPayment*term;
		npvFamily = calculateNPV(bal, term, growth+income);
		npvCharity = calculateNPV(annuityPayment*term, term, payoutRate);
	}
	
	public double calculateNPV(double fv, int term, double i) {
			double npv = fv / Math.pow((1 + i),term);
			return npv;
	}
	public double getAnnuityFactor() {
		return annuityFactor;
	}
	public double getAnnuityPayment() {
		return annuityPayment;
	}
	public double getCharitableDeduction() {
		return charitableDeduction;
	}
	public double getDiscount() {
		return discount;
	}
	public double getDiscountValue() {
		return discountValue;
	}
	public double getFmv() {
		return fmv;
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
	public double getPayoutRate() {
		return payoutRate;
	}
	public double getRemainderInterest() {
		return remainderInterest;
	}
	public int getTerm() {
		return term;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityPayment(double annuityPayment) {
		this.annuityPayment = annuityPayment;
	}

	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
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

	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setTerm(int term) {
		this.term = term;
	}
}
