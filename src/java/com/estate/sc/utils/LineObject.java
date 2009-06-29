package com.estate.sc.utils;

import com.estate.constants.TeagFont;
import com.estate.constants.TaxableIncome;

public class LineObject {
	TeagFont f;
	String description;
	TaxableIncome taxableIncome = TaxableIncome.O;

	double taxablePercent = 1.0;
	
	double prctTaxable;		// 100% taxable means income is taxed fully
	
	double flow[] = new double[60];
	
	public void setValue(int year, double value) {
		if(year <60 && year >= 0)
			flow[year] =  rnd(value);
	}
	
	public void addValue(int year, double value) {
		if( year < 60 && year >=0) {
			flow[year] += rnd(value);
		}
	}
	
	public double getValue(int year) {
		if( year < 60 && year >= 0) {
			return flow[year];
		}
			return 0;
	}
	
	public double getTaxableValue(int year) {
		if( year < 60 && year >= 0) {
			return flow[year] * prctTaxable;
		} 
		return 0;
	}
	
	public double getTaxableIncome(int year) {
		double income = flow[year];
		double taxable = income;
		// Ordinary Income Tax
		if(taxableIncome.equals(TaxableIncome.O)) {
			taxable = income * taxablePercent;
		}
		// Income is taxed at Divident Rate + state tax so we return zero
		if(taxableIncome.equals(TaxableIncome.D) || taxableIncome.equals(TaxableIncome.N)){
			taxable = 0;
		}
		// Income is partially taxed as Ordinary Income
		if(taxableIncome.equals(TaxableIncome.B)) {
			taxable = income * taxablePercent;
		}
		return taxable;
	}
	
	public double getTaxableDividend(int year) {
		double income = flow[year];
		if(taxableIncome.equals(TaxableIncome.D)) {
			return income;
		}
		if(taxableIncome.equals(TaxableIncome.B)) {
			return income * (1 - taxablePercent);
		}
		return 0;
	}
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrctTaxable() {
		return prctTaxable;
	}

	public void setPrctTaxable(double prctTaxable) {
		if( prctTaxable > 1.0)
			prctTaxable = 1.0; //100%
		if( prctTaxable < 0) 
			prctTaxable = 0;
		this.prctTaxable = prctTaxable;
	}
	
	private double rnd(double v) {
		return Math.rint(v/1000.0);
	}

	public TeagFont getF() {
		return f;
	}

	public void setF(TeagFont f) {
		this.f = f;
	}
	
	public void setTaxableIncome(String t) {
		taxableIncome = TaxableIncome.getType(Integer.parseInt(t));
	}
	
	public void setTaxablePercent(double pct) {
		this.taxablePercent = pct;
		this.prctTaxable = pct;
	}
}
