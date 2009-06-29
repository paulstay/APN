package com.teag.tbox;


import java.util.Date;

import com.zcalc.zCalc;

public class CLAT3 {

	String method;
	String grantor;
	double fmv;
	double discount;
	double basis;
	double income;
	double growth;
	double liability;
	double irsRate;
	Date irsDate;
	double remainderInterest;
	double payoutRate;
	double annuityPayment;
	double annuityFactor;
	double annuityInterest;
	double annuityFreq = 1;
	double term;
	double incomeTaxRate;
	double estateTaxRate;
	double finalDeath;
	double discountValue;

	public void calculate(){
		discountValue = (1-discount) * fmv;
		zCalc zc = new zCalc();
		zc.StartUp();
			annuityFactor = zc.zTERM(0, irsRate,(long) term,0,0,0,0);
			if(method.equalsIgnoreCase("a")) {		// Calcualte remainder interest....
				annuityInterest = zc.zANNTERMLIFE(discountValue, annuityPayment, irsRate, (long)term, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
				remainderInterest = discountValue - annuityInterest;
				payoutRate = annuityPayment/discountValue;
			} else {								// Calculate annuity payment
				payoutRate = zc.zANNRATETARGET(discountValue, remainderInterest, irsRate, (long)term, 0,0, 0, 0, 0, 0, 0,0, 0, 0, 0);
				annuityPayment = payoutRate * discountValue;
				annuityInterest = zc.zANNTERMLIFE(discountValue, annuityPayment, irsRate, (long)term, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
			}
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

	public double getBasis() {
		return basis;
	}

	public double getDiscount() {
		return discount;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public String getGrantor() {
		return grantor;
	}

	public double getGrowth() {
		return growth;
	}

	public double getIncome() {
		return income;
	}

	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getLiability() {
		return liability;
	}

	public String getMethod() {
		return method;
	}

	public double getPayoutRate() {
		return payoutRate;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getTerm() {
		return term;
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

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setFinalDeath(double finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setGrantor(String grantor) {
		this.grantor = grantor;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setLiability(double liability) {
		this.liability = liability;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setTerm(double term) {
		this.term = term;
	}
	
}
