package com.teag.EstatePlan;

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
	boolean taxDed = false;
	String cfType;
	String cdType; // Charitable deduction

	
	double vTable[] = new double[EstatePlanTable.MAX_TABLE];
	
	public String getCdType() {
		return cdType;
	}
	
	public String getCfType() {
		return cfType;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getEndingYear() {
		return endingYear;
	}

	public double getGrowth() {
		return growth;
	}

	public double getPercentTaxable() {
		return percentTaxable;
	}

	public double getStartingYear() {
		return startingYear;
	}

	// What we really want here is the amount that is taxable, so we subtract the percent taxable
	// from 1 and then multiply it by the value for the year.
	// Thus if 10000 is 100 % 100 than return 0 (this value is subtracted from the cash flow).
	public double getTaxableValue(int year) {
		if( taxDed) {
			return vTable[year];
		} else if( getCfType().equals("C"))
			return vTable[year] * (1. - getPercentTaxable());
		return 0;
	}

	public double getValue() {
		return value;
	}

	public double[] getVTable() {
		return vTable;
	}

	public void initialize() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        int currentYear = cal.get(Calendar.YEAR);
        double tValue = value;
        if( endingYear == 0)
        	endingYear = currentYear + EstatePlanTable.MAX_TABLE + 5;
		for(int j = 0; j < EstatePlanTable.MAX_TABLE; j++, currentYear++) {
			if( currentYear < startingYear || currentYear > endingYear) {
				vTable[j] = 0;
			} else {
				vTable[j] = tValue;
				tValue += tValue * growth; 
			}
		}
	}

	public boolean isTaxDed() {
		return taxDed;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	public void setCfType(String cfType) {
		this.cfType = cfType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndingYear(double endingYear) {
		this.endingYear = endingYear;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setPercentTaxable(double percentTaxable) {
		this.percentTaxable = percentTaxable;
	}

	public void setStartingYear(double startingYear) {
		this.startingYear = startingYear;
	}

	public void setTaxDed(boolean taxDed) {
		this.taxDed = taxDed;
	}
	public void setValue(double value) {
		this.value = value;
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
}
