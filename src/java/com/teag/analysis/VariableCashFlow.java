package com.teag.analysis;

import java.util.Calendar;
import java.util.Date;

import com.teag.bean.VCFBean;


public class VariableCashFlow {
	String description;
	double value;
	double growth;
	double startingYear;
	double endingYear;
	double percentTaxable;
	String cfType; // Chash Flow type
	String cdType; // Charitable deduction
	boolean taxDed = false;
	
	double vTable[] = new double[CashFlow.MAX_TABLE];
	
	public void initialize() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        int currentYear = cal.get(Calendar.YEAR);
        double tValue = value;
        if( endingYear == 0)
        	endingYear = currentYear + CashFlow.MAX_TABLE + 5;
		for(int j = 0; j < CashFlow.MAX_TABLE; j++, currentYear++) {
			if( currentYear < startingYear || currentYear > endingYear) {
				vTable[j] = 0;
			} else {
				vTable[j] = tValue;
				tValue += tValue * growth; 
			}
		}
	}
	
	public void setValues(VCFBean vcf) {
		setDescription(vcf.getDescription());
		setValue(vcf.getValue());
		setGrowth(vcf.getGrowth());
		setPercentTaxable(vcf.getPrctTax());
		setStartingYear(vcf.getStartYear());
		setEndingYear(vcf.getEndYear());
		setCfType(vcf.getCfFlag());
		setCdType(vcf.getCharitableDed());
		
		if( vcf.getTaxDeduction().equals("Y"))
			setTaxDed(true);
		else
			setTaxDed(false);
		initialize();
	}
	
	// What we really want here is the amount that is taxable, so we subtract the percent taxable
	// from 1 and then multiply it by the value for the year.
	// Thus if 10000 is 100 % 100 than return 0 (this value is subtracted from the cash flow).
	public double getTaxableValue(int year) {
		if( taxDed) {
			return vTable[year];
		} 
		if( getCfType().equals("C") )
			return vTable[year] * (1. - getPercentTaxable());
		return 0.0;
	}
	
	public String getCfType() {
		return cfType;
	}

	public void setCfType(String cfType) {
		this.cfType = cfType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getEndingYear() {
		return endingYear;
	}

	public void setEndingYear(double endingYear) {
		this.endingYear = endingYear;
	}

	public double getGrowth() {
		return growth;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public double getPercentTaxable() {
		return percentTaxable;
	}

	public void setPercentTaxable(double percentTaxable) {
		this.percentTaxable = percentTaxable;
	}

	public double getStartingYear() {
		return startingYear;
	}

	public void setStartingYear(double startingYear) {
		this.startingYear = startingYear;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double[] getVTable() {
		return vTable;
	}

	public boolean isTaxDed() {
		return taxDed;
	}

	public void setTaxDed(boolean taxDed) {
		this.taxDed = taxDed;
	}

	public String getCdType() {
		return cdType;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}
}
