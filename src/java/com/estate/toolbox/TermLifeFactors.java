package com.estate.toolbox;

/**
 * @author Paul Stay
 * Description Calculate the basic term life factors for GRAT, CLATS
 * Date Feb 17, 2009
 * Copyright Advance Practice Network
 *
 */

import com.zcalc.*;
//import java.text.*;

public class TermLifeFactors {

	// Input Variables
	long term;
	long age1;
	long age2;
	double irsRate;
	long freq = 1;			// Default Annual
	long annuityType;
	double annuityIncrease;
	long status = 0;
	boolean rounded = true;
	long yrsDef = 0;
	double lifeExpectancy = 0;
	
	double incomeFactor;
	double remainderFactor;
	double annuityFactor;
	double reversionFactor;

	public void calculate() {
		zCalc zc = new zCalc();
		int rnd = 0;
		
		if(!rounded)
			rnd = 20;
		
		
		zc.StartUp();
			// Calculate Income factor
			incomeFactor = zc.zTERMLIFE(rnd+1, irsRate, term, age1, age2, 0, 0, 0, annuityIncrease, freq, annuityType, yrsDef, status, 0);
			remainderFactor = zc.zTERMLIFE(rnd+2, irsRate, term, age1, age2, 0, 0, 0, annuityIncrease, freq, annuityType, yrsDef, status, 0);
			reversionFactor = zc.zTERMLIFE(rnd+3, irsRate, term, age1, age2, 0, 0, 0, annuityIncrease, freq, annuityType, yrsDef, status, 0);
			annuityFactor = zc.zTERMLIFE(rnd+0, irsRate, term, age1, age2, 0, 0, 0, annuityIncrease, freq, annuityType, yrsDef, status, 0);
			lifeExpectancy = zc.zLE(age1, age2, 0, 0, 0, status, 0);
		zc.ShutDown();
		//System.out.printf("Annuity factor %f term %d Age1 %d Age2 %d Increase %f Freq %d End %d Status %d\n",annuityFactor, term, age1,age2, annuityIncrease, freq, annuityType, status);
	}

	public double calculatePayment(double fmv, double remainder){
		/*
		double x = fmv - remainder;
		double payment = x/annuityFactor;
		return payment;
		*/
		double payment = 0;
		zCalc zc = new zCalc();
		zc.StartUp();
		
		payment = fmv * zc.zANNRATETARGET(fmv, remainder, irsRate, term, age1, age1, 0, 0, 0, annuityIncrease, freq, annuityType, yrsDef, status, 0);
		
		zc.ShutDown();
		return payment;
	}
	
	public static void main(String args[]) {
		//DecimalFormat df = new DecimalFormat("##.######");
		//DecimalFormat df2 = new DecimalFormat("###,###.###");
		TermLifeFactors factors = new TermLifeFactors();
		factors.setAge1(60);
		factors.setAge2(56);
		factors.setTerm(15);
		factors.setStatus(2);
		factors.setIrsRate(.05);
		factors.setFreq(1);
		factors.setAnnuityIncrease(0);
		factors.calculate();
		
		//System.out.println("The Remainder factor is " + df.format(factors.getRemainderFactor()));
		//System.out.println("The Income factor is " + df.format(factors.getIncomeFactor()));
		//System.out.println("The Annuity factor is " + df.format(factors.getAnnuityFactor()));
		
		//System.out.println("The payout for a remainder interest of 40,000 on 1,000,000 is " + df2.format(factors.calculatePayment(1000000, 40000)));
		//System.out.println("The max payout for zero remainder is " + df.format(1.0/factors.getAnnuityFactor()));
	}
	
	
	
	public long getTerm() {
		return term;
	}

	public void setTerm(long term) {
		this.term = term;
	}

	public long getAge1() {
		return age1;
	}

	public void setAge1(long age1) {
		this.age1 = age1;
	}

	public long getAge2() {
		return age2;
	}

	public void setAge2(long age2) {
		this.age2 = age2;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public long getFreq() {
		return freq;
	}

	public void setFreq(long freq) {
		this.freq = freq;
	}

	public long getAnnuityType() {
		return annuityType;
	}

	public void setAnnuityType(long annuityType) {
		this.annuityType = annuityType;
	}

	public double getAnnuityIncrease() {
		return annuityIncrease;
	}

	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public boolean isRounded() {
		return rounded;
	}

	public void setRounded(boolean rounded) {
		this.rounded = rounded;
	}

	public long getYrsDef() {
		return yrsDef;
	}

	public void setYrsDef(long yrsDef) {
		this.yrsDef = yrsDef;
	}

	public double getIncomeFactor() {
		return incomeFactor;
	}

	public void setIncomeFactor(double incomeFactor) {
		this.incomeFactor = incomeFactor;
	}

	public double getRemainderFactor() {
		return remainderFactor;
	}

	public void setRemainderFactor(double remainderFactor) {
		this.remainderFactor = remainderFactor;
	}

	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public double getReversionFactor() {
		return reversionFactor;
	}

	public void setReversionFactor(double reversionFactor) {
		this.reversionFactor = reversionFactor;
	}

	public double getLifeExpectancy() {
		return lifeExpectancy;
	}

	public void setLifeExpectancy(double lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}
}
