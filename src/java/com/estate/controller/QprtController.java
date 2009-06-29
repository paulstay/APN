package com.estate.controller;

import java.util.Date;

import com.estate.toolbox.QPRT;

public class QprtController {

	QPRT q1;
	QPRT q2;

	int trusts;
	int clientTerm;
	int spouseTerm;
	int clientAge;
	int spouseAge;
	double clientPriorGifts;
	double spousePriorGifts;
	String homeType; // h= primary home, v = vacation home
	double fractionalDiscount;
	double estateTaxRate;
	double irsRate;
	Date irsDate;
	int finalDeath;
	boolean reversionRetained = true;
	double fmv;
	double basis;
	double growth;
	
	public void calculate() {
		double split = 1;

		q1 = new QPRT();

		if (trusts > 1) {
			q2 = new QPRT(); // Spouse QPRT
			split = 2;
		}

		q1.setFmv(fmv/split);
		q1.setBasis(basis/split);
		q1.setTerm(clientTerm);
		q1.setClientAge(clientAge);
		q1.setFractionalDiscount(fractionalDiscount);
		q1.setEstateTaxRate(estateTaxRate);
		q1.setIrsRate(irsRate);
		q1.setFinalDeath(finalDeath);
		q1.setGrowth(growth);
		
		q1.calculate();
		
		if(trusts > 1) {
			q2.setFmv(fmv/split);
			q2.setBasis(basis/split);
			q2.setTerm(spouseTerm);
			q2.setClientAge(spouseAge);
			q2.setFractionalDiscount(fractionalDiscount);
			q2.setEstateTaxRate(estateTaxRate);
			q2.setIrsRate(irsRate);
			q2.setFinalDeath(finalDeath);
			q2.setGrowth(growth);
			q2.calculate();
		}
	}

	public double getBasis() {
		return basis;
	}

	public int getClientAge() {
		return clientAge;
	}

	public double getClientPriorGifts() {
		return clientPriorGifts;
	}

	public int getClientTerm() {
		return clientTerm;
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

	public double getFractionalDiscount() {
		return fractionalDiscount;
	}

	public double getGrowth() {
		return growth;
	}

	public String getHomeType() {
		return homeType;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public QPRT getQ1() {
		return q1;
	}

	public QPRT getQ2() {
		return q2;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public double getSpousePriorGifts() {
		return spousePriorGifts;
	}

	public int getSpouseTerm() {
		return spouseTerm;
	}

	public int getTrusts() {
		return trusts;
	}

	public boolean isReversionRetained() {
		return reversionRetained;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public void setClientPriorGifts(double clientPriorGifts) {
		this.clientPriorGifts = clientPriorGifts;
	}

	public void setClientTerm(int clientTerm) {
		this.clientTerm = clientTerm;
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

	public void setFractionalDiscount(double fractionalDiscount) {
		this.fractionalDiscount = fractionalDiscount;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setQ1(QPRT q1) {
		this.q1 = q1;
	}

	public void setQ2(QPRT q2) {
		this.q2 = q2;
	}

	public void setReversionRetained(boolean reversionRetained) {
		this.reversionRetained = reversionRetained;
	}

	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}

	public void setSpousePriorGifts(double spousePriorGifts) {
		this.spousePriorGifts = spousePriorGifts;
	}

	public void setSpouseTerm(int spouseTerm) {
		this.spouseTerm = spouseTerm;
	}

	public void setTrusts(int trusts) {
		this.trusts = trusts;
	}
}
