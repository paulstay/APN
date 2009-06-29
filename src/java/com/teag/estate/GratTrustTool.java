package com.teag.estate;

/**
 * @author stay
 * Created on Apr 12, 2005
 * Description, The GratTrustTool calculates the GRAT trust benefits, and estate tax savings.
 * Source - GRAT excel spread sheet Sean and Henry Whiffen
 */

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.zcalc.zCalc;

public class GratTrustTool {
	double value;
	double discountValue;
	double growthRate;
	double incomeRate;
	double afrRate;
	String transferDate;
	double priorGifts;
	double spousePriorGifts;
	double annuity;
	int annuityFreq;
	double annuityIncrease;
	double estateTaxRate;
	double age;
	double termLength;
	double startTerm;
	double lifeExp;

	double annuityFactor;
	double annuityPaymentRate;
	double annuityRate;
	double optimalPaymentRate;
	double actualPaymentRate;
	double annuityInterest;
	double remainderInterest;
	double subjectToAfrregs;
	double totalInterest;
	double retainedAnnuityInterest;
	double taxableGift;
	double totalGiftTaxPayable;
	double gratBenefit;
	double estateTaxSavings;
	double leverage;						// On day of transfer
	boolean federalRev;
	
	// This method uses zcalc heavily.
	public void calculate() {
		zCalc zcalc = new zCalc();
		zcalc.StartUp();
		
		/*
		int yr = (int) age;
		
		if( startTerm != 1) {
			yr = (int)((startTerm -1 ) + age);
		}
		*/
		optimalPaymentRate = zcalc.zGRATZO(afrRate, (int)termLength, annuityIncrease, annuityFreq, 0);
		
		if( annuity == 0) {
			if( discountValue == 0)
				annuity = optimalPaymentRate * value;
			else
				annuity = optimalPaymentRate * discountValue;
		}
		
		lifeExp = zcalc.zLE((int)age,0,0,0,0,0,11);
		annuityPaymentRate = annuity / discountValue;
		annuityFactor = zcalc.zGRATAF(afrRate, (int)termLength, 0, 0, annuityIncrease, annuityFreq, 0);
		actualPaymentRate = annuityPaymentRate;
		taxableGift = zcalc.zGRATGIFT(afrRate, (int)termLength, 0, 0, 
				annuityIncrease, annuityFreq, discountValue, annuityPaymentRate, 0);
		remainderInterest = taxableGift;
		annuityInterest = discountValue - remainderInterest;
		
		if( actualPaymentRate > optimalPaymentRate) {
			federalRev = true;
		} else {
			federalRev = false;
		}
		
		totalInterest = discountValue;
		retainedAnnuityInterest = totalInterest  - taxableGift;
		
		Date now = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(now);
		int year = cal.get(Calendar.YEAR);
		if( spousePriorGifts > 0) {
			totalGiftTaxPayable = zcalc.zFGT(taxableGift, priorGifts + spousePriorGifts, 0,0,year,0,0);
		} else {
			totalGiftTaxPayable = zcalc.zFGT(taxableGift, priorGifts, 0,0,year,0,0);
		}
		
		gratBenefit = zcalc.zGRATBEN(afrRate, (int)termLength, growthRate, annuityIncrease, annuityFreq, 
					value, annuityPaymentRate, 0);
		
		estateTaxSavings = estateTaxRate * (gratBenefit - taxableGift);
		
		if (taxableGift > 0)
			leverage = value / taxableGift;
		
		zcalc.ShutDown();
	}
	
	/**
	 * @return Returns the actualPaymentRate.
	 */
	public double getActualPaymentRate() {
		return actualPaymentRate;
	}
	/**
	 * @return Returns the afrRate.
	 */
	public double getAfrRate() {
		return afrRate;
	}
	/**
	 * @return Returns the age.
	 */
	public double getAge() {
		return age;
	}
	/**
	 * @return Returns the annuity.
	 */
	public double getAnnuity() {
		return annuity;
	}
	/**
	 * @return Returns the annuityFactor.
	 */
	public double getAnnuityFactor() {
		return annuityFactor;
	}
	/**
	 * @return Returns the annuityFreq.
	 */
	public int getAnnuityFreq() {
		return annuityFreq;
	}
	/**
	 * @return Returns the annuityIncrease.
	 */
	public double getAnnuityIncrease() {
		return annuityIncrease;
	}
	/**
	 * @return Returns the annuityInterest.
	 */
	public double getAnnuityInterest() {
		return annuityInterest;
	}
	/**
	 * @return Returns the annuityPaymentRate.
	 */
	public double getAnnuityPaymentRate() {
		return annuityPaymentRate;
	}
	/**
	 * @return Returns the annuityRate.
	 */
	public double getAnnuityRate() {
		return annuityRate;
	}
	/**
	 * @return Returns the discountValue.
	 */
	public double getDiscountValue() {
		return discountValue;
	}
	/**
	 * @return Returns the estateTaxRate.
	 */
	public double getEstateTaxRate() {
		return estateTaxRate;
	}
	/**
	 * @return Returns the estateTaxSavings.
	 */
	public double getEstateTaxSavings() {
		return estateTaxSavings;
	}
	/**
	 * @return Returns the gratBenefit.
	 */
	public double getGratBenefit() {
		return gratBenefit;
	}
	/**
	 * @return Returns the growthRate.
	 */
	public double getGrowthRate() {
		return growthRate;
	}
	/**
	 * @return Returns the incomeRate.
	 */
	public double getIncomeRate() {
		return incomeRate;
	}
	/**
	 * @return Returns the leverage.
	 */
	public double getLeverage() {
		return leverage;
	}
	/**
	 * @return Returns the lifeExp.
	 */
	public double getLifeExp() {
		return lifeExp;
	}
	/**
	 * @return Returns the optimalPaymentRate.
	 */
	public double getOptimalPaymentRate() {
		return optimalPaymentRate;
	}
	/**
	 * @return Returns the priorGifts.
	 */
	public double getPriorGifts() {
		return priorGifts;
	}
	/**
	 * @return Returns the remainderInterest.
	 */
	public double getRemainderInterest() {
		return remainderInterest;
	}
	/**
	 * @return Returns the retainedAnnuityInterest.
	 */
	public double getRetainedAnnuityInterest() {
		return retainedAnnuityInterest;
	}
	/**
	 * @return Returns the spousePriorGifts.
	 */
	public double getSpousePriorGifts() {
		return spousePriorGifts;
	}
	/**
	 * @return Returns the startTerm.
	 */
	public double getStartTerm() {
		return startTerm;
	}
	/**
	 * @return Returns the subjectToAfrregs.
	 */
	public double getSubjectToAfrregs() {
		return subjectToAfrregs;
	}
	/**
	 * @return Returns the taxableGift.
	 */
	public double getTaxableGift() {
		return taxableGift;
	}
	/**
	 * @return Returns the termLength.
	 */
	public double getTermLength() {
		return termLength;
	}
	/**
	 * @return Returns the totalGiftTaxPayable.
	 */
	public double getTotalGiftTaxPayable() {
		return totalGiftTaxPayable;
	}
	/**
	 * @return Returns the totalInterest.
	 */
	public double getTotalInterest() {
		return totalInterest;
	}
	/**
	 * @return Returns the transferDate.
	 */
	public String getTransferDate() {
		return transferDate;
	}
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @return Returns the federalRev.
	 */
	public boolean isFederalRev() {
		return federalRev;
	}
	/**
	 * @param actualPaymentRate The actualPaymentRate to set.
	 */
	public void setActualPaymentRate(double actualPaymentRate) {
		this.actualPaymentRate = actualPaymentRate;
	}
	/**
	 * @param afrRate The afrRate to set.
	 */
	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}
	/**
	 * @param age The age to set.
	 */
	public void setAge(double age) {
		this.age = age;
	}
	/**
	 * @param annuity The annuity to set.
	 */
	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}
	/**
	 * @param annuityFactor The annuityFactor to set.
	 */
	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}
	/**
	 * @param annuityFreq The annuityFreq to set.
	 */
	public void setAnnuityFreq(int annuityFreq) {
		this.annuityFreq = annuityFreq;
	}
	/**
	 * @param annuityIncrease The annuityIncrease to set.
	 */
	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}
	/**
	 * @param annuityInterest The annuityInterest to set.
	 */
	public void setAnnuityInterest(double annuityInterest) {
		this.annuityInterest = annuityInterest;
	}
	/**
	 * @param annuityPaymentRate The annuityPaymentRate to set.
	 */
	public void setAnnuityPaymentRate(double annuityPaymentRate) {
		this.annuityPaymentRate = annuityPaymentRate;
	}
	/**
	 * @param annuityRate The annuityRate to set.
	 */
	public void setAnnuityRate(double annuityRate) {
		this.annuityRate = annuityRate;
	}
	/**
	 * @param discountValue The discountValue to set.
	 */
	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}
	/**
	 * @param estateTaxRate The estateTaxRate to set.
	 */
	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}
	/**
	 * @param estateTaxSavings The estateTaxSavings to set.
	 */
	public void setEstateTaxSavings(double estateTaxSavings) {
		this.estateTaxSavings = estateTaxSavings;
	}
	/**
	 * @param federalRev The federalRev to set.
	 */
	public void setFederalRev(boolean federalRev) {
		this.federalRev = federalRev;
	}
	/**
	 * @param gratBenefit The gratBenefit to set.
	 */
	public void setGratBenefit(double gratBenefit) {
		this.gratBenefit = gratBenefit;
	}
	/**
	 * @param growthRate The growthRate to set.
	 */
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	/**
	 * @param incomeRate The incomeRate to set.
	 */
	public void setIncomeRate(double incomeRate) {
		this.incomeRate = incomeRate;
	}
	/**
	 * @param leverage The leverage to set.
	 */
	public void setLeverage(double leverage) {
		this.leverage = leverage;
	}
	/**
	 * @param lifeExp The lifeExp to set.
	 */
	public void setLifeExp(double lifeExp) {
		this.lifeExp = lifeExp;
	}
	/**
	 * @param optimalPaymentRate The optimalPaymentRate to set.
	 */
	public void setOptimalPaymentRate(double optimalPaymentRate) {
		this.optimalPaymentRate = optimalPaymentRate;
	}
	/**
	 * @param priorGifts The priorGifts to set.
	 */
	public void setPriorGifts(double priorGifts) {
		this.priorGifts = priorGifts;
	}
	/**
	 * @param remainderInterest The remainderInterest to set.
	 */
	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}
	/**
	 * @param retainedAnnuityInterest The retainedAnnuityInterest to set.
	 */
	public void setRetainedAnnuityInterest(double retainedAnnuityInterest) {
		this.retainedAnnuityInterest = retainedAnnuityInterest;
	}
	/**
	 * @param spousePriorGifts The spousePriorGifts to set.
	 */
	public void setSpousePriorGifts(double spousePriorGifts) {
		this.spousePriorGifts = spousePriorGifts;
	}
	/**
	 * @param startTerm The startTerm to set.
	 */
	public void setStartTerm(double startTerm) {
		this.startTerm = startTerm;
	}
	/**
	 * @param subjectToAfrregs The subjectToAfrregs to set.
	 */
	public void setSubjectToAfrregs(double subjectToAfrregs) {
		this.subjectToAfrregs = subjectToAfrregs;
	}
	/**
	 * @param taxableGift The taxableGift to set.
	 */
	public void setTaxableGift(double taxableGift) {
		this.taxableGift = taxableGift;
	}
	/**
	 * @param termLength The termLength to set.
	 */
	public void setTermLength(double termLength) {
		this.termLength = termLength;
	}
	/**
	 * @param totalGiftTaxPayable The totalGiftTaxPayable to set.
	 */
	public void setTotalGiftTaxPayable(double totalGiftTaxPayable) {
		this.totalGiftTaxPayable = totalGiftTaxPayable;
	}
	/**
	 * @param totalInterest The totalInterest to set.
	 */
	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}
	/**
	 * @param transferDate The transferDate to set.
	 */
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}
}
