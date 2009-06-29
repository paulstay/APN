package com.estate.toolbox;

import java.util.Date;

import com.zcalc.zCalc;

public class SCIN {
	int noteType;			// 0 = Interest Only, 1 = Level Prin + Interest, 2 = Self Amoritizing
	double irsRate;
	Date irsDate;
	int term;
	double noteRate;
	int endBegin = 0;		// End
	int xTerm = 0;
	int yrsDeffered = 0;
	int paymentType = 0;	// 0 = Principal Risk Premium, 1= Interest Risk Premium
	int clientAge;
	int spouseAge;
	double cLifeExpectancy;
	double sLifeExpectancy;
	
	double fmv;
	double basis;
	double growth;
	
	double principalRiskPremium;
	double interestRiskPremium;
	double adjustedPremium;
	double adjustedInterest;
	int finalDeath;
	
	double note[];
	double capGains[];
	double taxFree[];
	double payment[];
	double interestPayment[];
	
	public void calculate() {
		int n = term + yrsDeffered;
		
		finalDeath = n;
		
		payment = new double[finalDeath];
		capGains = new double[finalDeath];
		taxFree = new double[finalDeath];
		payment = new double[finalDeath];
		interestPayment = new double[finalDeath];
		note = new double[finalDeath];

		zCalc zc = new zCalc();
		zc.StartUp();
			cLifeExpectancy = zc.zLE(clientAge, 0, 0, 0, 0, 0, 0);
			sLifeExpectancy = zc.zLE(spouseAge, 0, 0, 0, 0, 0, 0);
			principalRiskPremium = zc.zSCIN(0, fmv, basis, irsRate, clientAge, noteRate, term, noteType, xTerm, endBegin, yrsDeffered,0, 0, spouseAge, 0);
			interestRiskPremium = zc.zSCIN(1, fmv, basis, irsRate, clientAge, noteRate, term, noteType, xTerm, endBegin, yrsDeffered,0, 0, spouseAge, 0);
			adjustedPremium = fmv + principalRiskPremium;
			adjustedInterest = interestRiskPremium + noteRate;
			double useRate = noteRate;
			double usePremium = adjustedPremium;
			if(paymentType == 1) {
				useRate = adjustedInterest;
				usePremium = fmv;
				
			}
			for(int i = 0; i < term+yrsDeffered; i++){
				payment[i] = zc.zINSTALL(0, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeffered);
				interestPayment[i] = zc.zINSTALL(1, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeffered);
				capGains[i] = zc.zINSTALL(2, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeffered);
				taxFree[i] = zc.zINSTALL(3, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeffered);
				note[i] = zc.zINSTALL(4, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeffered);
				
			}
		zc.ShutDown();
	}

	public double getAdjustedInterest() {
		return adjustedInterest;
	}

	public double getAdjustedPremium() {
		return adjustedPremium;
	}

	public double getBasis() {
		return basis;
	}

	public double[] getCapGains() {
		return capGains;
	}

	public int getClientAge() {
		return clientAge;
	}

	public double getCLifeExpectancy() {
		return cLifeExpectancy;
	}

	public int getEndBegin() {
		return endBegin;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public double getGrowth() {
		return growth;
	}

	public double[] getInterestPayment() {
		return interestPayment;
	}

	public double getInterestRiskPremium() {
		return interestRiskPremium;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double[] getNote() {
		return note;
	}

	public double getNoteRate() {
		return noteRate;
	}

	public int getNoteType() {
		return noteType;
	}

	public double[] getPayment() {
		return payment;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public double getPrincipalRiskPremium() {
		return principalRiskPremium;
	}

	public double getSLifeExpectancy() {
		return sLifeExpectancy;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public double[] getTaxFree() {
		return taxFree;
	}

	public int getTerm() {
		return term;
	}

	public int getXTerm() {
		return xTerm;
	}

	public int getYrsDeffered() {
		return yrsDeffered;
	}

	public void setAdjustedInterest(double adjustedInterest) {
		this.adjustedInterest = adjustedInterest;
	}

	public void setAdjustedPremium(double adjustedPremium) {
		this.adjustedPremium = adjustedPremium;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setCapGains(double[] capGains) {
		this.capGains = capGains;
	}

	public void setClientAge(int age) {
		clientAge = age;
	}

	public void setCLifeExpectancy(double lifeExpectancy) {
		cLifeExpectancy = lifeExpectancy;
	}

	public void setEndBegin(int endBegin) {
		this.endBegin = endBegin;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setInterestPayment(double[] interestPayment) {
		this.interestPayment = interestPayment;
	}

	public void setInterestRiskPremium(double interestRiskPremium) {
		this.interestRiskPremium = interestRiskPremium;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setNote(double[] note) {
		this.note = note;
	}

	public void setNoteRate(double noteRate) {
		this.noteRate = noteRate;
	}

	public void setNoteType(int noteType) {
		this.noteType = noteType;
	}

	public void setPayment(double[] payment) {
		this.payment = payment;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public void setPrincipalRiskPremium(double principalRiskPremium) {
		this.principalRiskPremium = principalRiskPremium;
	}

	public void setSLifeExpectancy(double lifeExpectancy) {
		sLifeExpectancy = lifeExpectancy;
	}

	public void setSpouseAge(int age) {
		spouseAge = age;
	}

	public void setTaxFree(double[] taxFree) {
		this.taxFree = taxFree;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setXTerm(int term) {
		xTerm = term;
	}

	public void setYrsDeffered(int yrsDeffered) {
		this.yrsDeffered = yrsDeffered;
	}
}
