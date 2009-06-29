package com.teag.tbox;

/**
 * @author Paul Stay
 *
 */

import java.util.Date;

import com.zcalc.zCalc;

public class Clut {

	double fmv;
	double discount;
	double discountValue;
	double irsRate;
	Date irsDate;
	double growth;
	double remainderInterest;
	double charitableDed;
	int term;
	double payout;
	double freq;
	double uniLag;

	public Clut() {
		setFmv(1000000);
		setDiscount(.35);
		setDiscountValue(getFmv()* (1-getDiscountValue()));
		setRemainderInterest(0);
		setIrsRate(.056);
		setTerm(10);
		Date now = new Date();
		setIrsDate(now);
		setGrowth(.06);
		setFreq(1);
		setUniLag(1);
		calculate();
	}
	
	public double benefit(int currentYear) {
		zCalc zc = new zCalc();
		zc.StartUp();
		double ben = zc.zUNIBEN(discount, payout, currentYear, growth, (long)freq, (long)uniLag, 0);
		zc.ShutDown();
		return ben;
	}
	
	public void calculate() {
		zCalc zc = new zCalc();
		zc.StartUp();
		payout = zc.zUNIPAYOUTTARGET(discountValue, remainderInterest, term, 0, 0, 0, 0, 0, irsRate, (long)freq, (long)uniLag, 0, 0,0);
		zc.ShutDown();
		
	}
	
	public double getCharitableDed() {
		return charitableDed;
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

	public double getFreq() {
		return freq;
	}

	public double getGrowth() {
		return growth;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getPayout() {
		return payout;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public int getTerm() {
		return term;
	}

	public double getUniLag() {
		return uniLag;
	}

	public void optimize() {
		zCalc zc = new zCalc();
		zc.StartUp();
		payout = zc.zUNIPAYOUTTARGET(discountValue, remainderInterest, term, 0, 0, 0, 0, 0, irsRate, (long)freq, (long)uniLag, 0, 0,0);
		zc.ShutDown();
	}

	public void setCharitableDed(double charitableDed) {
		this.charitableDed = charitableDed;
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

	public void setFreq(double freq) {
		this.freq = freq;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setPayout(double payout) {
		this.payout = payout;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setUniLag(double uniLag) {
		this.uniLag = uniLag;
	}
}
