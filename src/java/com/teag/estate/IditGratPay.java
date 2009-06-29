package com.teag.estate;

/**
 * @author stay
 * Created on May 6, 2005
 */

import com.teag.util.Function;
import com.zcalc.zCalc;

public class IditGratPay {
	double note;
	double noteRate;
	double optimalRate;
	double securityValue;
	double flpDiscount;
	double flpLpInterests;
	double securitiesGrowth;
	double afrRate;
	int freq;
	int term;
	double guess = .7;
	
	public void calculate() {
		zCalc zc = new zCalc();
		zc.StartUp();
		optimalRate = zc.zGRATZO(afrRate, term, 0,freq,0);
		zc.ShutDown();
		double ratio;
		securityValue = note * guess;	// initial guess
		int itr = 0;

		while(true && itr++ < 25) {
			double dValue = (note + securityValue) * (1 - flpDiscount) * flpLpInterests;
			double zeroEstate = optimalRate * dValue;
			double notePayout = note * flpLpInterests * noteRate;
			double estSecPay = zeroEstate - notePayout;
			double secPay = Function.PMT(securityValue, securitiesGrowth, term);
			ratio = estSecPay / secPay;
			
			double fix = Math.abs(1 - ratio);
			
			if( fix < .0000001) {
				break;
			}
			securityValue = securityValue * ratio;
		}
	}
	
	public double getAfrRate() {
		return afrRate;
	}
	public double getFlpDiscount() {
		return flpDiscount;
	}
	public double getFlpLpInterests() {
		return flpLpInterests;
	}
	public int getFreq() {
		return freq;
	}
	public double getNote() {
		return note;
	}
	public double getNoteRate() {
		return noteRate;
	}
	public double getOptimalRate() {
		return optimalRate;
	}
	public double getSecuritiesGrowth() {
		return securitiesGrowth;
	}
	public double getSecurityValue() {
		return securityValue;
	}
	public int getTerm() {
		return term;
	}
	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}
	public void setFlpDiscount(double flpDiscount) {
		this.flpDiscount = flpDiscount;
	}
	public void setFlpLpInterests(double flpLpInterests) {
		this.flpLpInterests = flpLpInterests;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	public void setNote(double note) {
		this.note = note;
	}
	public void setNoteRate(double noteRate) {
		this.noteRate = noteRate;
	}
	public void setOptimalRate(double optimalRate) {
		this.optimalRate = optimalRate;
	}
	public void setSecuritiesGrowth(double securitiesGrowth) {
		this.securitiesGrowth = securitiesGrowth;
	}
	public void setSecurityValue(double securityValue) {
		this.securityValue = securityValue;
	}
	public void setTerm(int term) {
		this.term = term;
	}
}
