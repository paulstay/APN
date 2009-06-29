/*
 * Created on Apr 5, 2005
 *
 */
package com.teag.estate;

import com.zcalc.zCalc;

/**
 * @author paul stay
 * 
 */
public class QprtTrustTool {
	public static double AGI_LIMITATION = .30;
	public static double ESTATE_TAX_RATE = .55;
	private double value;
	private double basis;
	private double growthRate;
	private String birthDate;
	private double priorGifts;
	private double afrRate;
	private String afrDate;
	private double discountValue;
	private boolean reversionRetained = true;
	private double fractionalDiscount;
	private long termLength;
	private long startTerm;
	private double incomeInterest;
	private double reversionaryInterest;
	private double remainderInterest;
	private double totalInterest;
	private double retainedInterest;
	private double taxableGift;
	private double taxableGiftValue;
	private double endResidenceValue;
	private double builtInGain;
	private double estateTaxSavings;

	private double valueGiftToHeirs;
	private double giftLeverage;
	private long clientAge;
	private double lifeExp;
	private double incomeIntValue;
	private double reversionaryIntValue;
	private double remainderIntValue;

	private double totalIntValue;
	private double retainedIntValue;

	public void calculate() {

		// Need to calculate this before the zCalc functions.
		discountValue = value * (1 - fractionalDiscount);

		initZCalc();
		incomeInterest = 1 - (reversionaryInterest + remainderInterest);
		incomeIntValue = incomeInterest * discountValue;
		reversionaryIntValue = discountValue * reversionaryInterest;
		remainderIntValue = remainderInterest * discountValue;
		totalIntValue = discountValue;
		retainedIntValue = totalIntValue - taxableGiftValue;
		endResidenceValue = value
				* Math.pow((1. + this.growthRate), this.termLength);
		builtInGain = endResidenceValue - basis;
		estateTaxSavings = ESTATE_TAX_RATE
				* (endResidenceValue - taxableGiftValue);
		taxableGift = taxableGiftValue / discountValue;
		valueGiftToHeirs = taxableGift * totalIntValue;
		giftLeverage = value / valueGiftToHeirs;
	}
	/**
	 * Initialize all of the zCalc calculations in one place.
	 * 
	 */

	public void initZCalc() {

		zCalc zc = new zCalc();
		zc.StartUp();
		int ageLen = (int) clientAge;

		if (this.startTerm > 1)
			ageLen = (int) ((startTerm - 1) + clientAge);

		this.reversionaryInterest = zc.zTERMLIFE(3, afrRate, termLength,
				ageLen, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
		this.remainderInterest = zc.zTERMLIFE(2, afrRate, termLength, ageLen,
				0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
		if (this.reversionRetained) {
			this.taxableGiftValue = zc.zQPRTGIFT(afrRate, termLength, ageLen,
					0l, 0l, this.discountValue, 0l);
		} else {
			this.taxableGiftValue = zc.zQPRTGIFT(afrRate, termLength, ageLen,
					0l, 1, discountValue, 0l);
		}
		lifeExp = zc.zLE(clientAge, 0, 0, 0, 0, 0, 0);

		zc.ShutDown();
	}
	/**
	 * @return Returns the afrDate.
	 */
	public String getAfrDate() {
		return afrDate;
	}

	/**
	 * @return Returns the afrRate.
	 */
	public double getAfrRate() {
		return afrRate;
	}

	/**
	 * @return Returns the basis.
	 */
	public double getBasis() {
		return basis;
	}

	/**
	 * @return Returns the birthDate.
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @return Returns the builtInGain.
	 */
	public double getBuiltInGain() {
		return builtInGain;
	}

	/**
	 * @return Returns the clientAge.
	 */
	public long getClientAge() {
		return clientAge;
	}

	/**
	 * @return Returns the discountValue.
	 */
	public double getDiscountValue() {
		return discountValue;
	}

	/**
	 * @return Returns the endResidenceValue.
	 */
	public double getEndResidenceValue() {
		return endResidenceValue;
	}

	/**
	 * @return Returns the estateTaxSavings.
	 */
	public double getEstateTaxSavings() {
		return estateTaxSavings;
	}

	/**
	 * @return Returns the fractionalDiscount.
	 */
	public double getFractionalDiscount() {
		return fractionalDiscount;
	}

	/**
	 * @return Returns the giftLeverage.
	 */
	public double getGiftLeverage() {
		return giftLeverage;
	}

	/**
	 * @return Returns the growthRate.
	 */
	public double getGrowthRate() {
		return growthRate;
	}

	/**
	 * @return Returns the incomeInterest.
	 */
	public double getIncomeInterest() {
		return incomeInterest;
	}

	/**
	 * @return Returns the incomeIntValue.
	 */
	public double getIncomeIntValue() {
		return incomeIntValue;
	}

	/**
	 * @return Returns the lifeExp.
	 */
	public double getLifeExp() {
		return lifeExp;
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
	 * @return Returns the remainderIntValue.
	 */
	public double getRemainderIntValue() {
		return remainderIntValue;
	}

	/**
	 * @return Returns the retainedInterest.
	 */
	public double getRetainedInterest() {
		return retainedInterest;
	}

	/**
	 * @return Returns the retainedIntValue.
	 */
	public double getRetainedIntValue() {
		return retainedIntValue;
	}

	/**
	 * @return Returns the reversionaryInterest.
	 */
	public double getReversionaryInterest() {
		return reversionaryInterest;
	}

	/**
	 * @return Returns the reversionaryIntValue.
	 */
	public double getReversionaryIntValue() {
		return reversionaryIntValue;
	}

	/**
	 * @return Returns the startTerm.
	 */
	public long getStartTerm() {
		return startTerm;
	}

	/**
	 * @return Returns the taxableGift.
	 */
	public double getTaxableGift() {
		return taxableGift;
	}

	/**
	 * @return Returns the taxableGiftValue.
	 */
	public double getTaxableGiftValue() {
		return taxableGiftValue;
	}

	/**
	 * @return Returns the termLength.
	 */
	public long getTermLength() {
		return termLength;
	}

	/**
	 * @return Returns the totalInterest.
	 */
	public double getTotalInterest() {
		return totalInterest;
	}

	/**
	 * @return Returns the totalIntValue.
	 */
	public double getTotalIntValue() {
		return totalIntValue;
	}

	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @return Returns the valueGiftToHeirs.
	 */
	public double getValueGiftToHeirs() {
		return valueGiftToHeirs;
	}

	

	/**
	 * @return Returns the reversionRetained.
	 */
	public boolean isReversionRetained() {
		return reversionRetained;
	}

	/**
	 * @param afrDate
	 *            The afrDate to set.
	 */
	public void setAfrDate(String afrDate) {
		this.afrDate = afrDate;
	}

	/**
	 * @param afrRate
	 *            The afrRate to set.
	 */
	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}

	/**
	 * @param basis
	 *            The basis to set.
	 */
	public void setBasis(double basis) {
		this.basis = basis;
	}

	/**
	 * @param birthDate
	 *            The birthDate to set.
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @param builtInGain
	 *            The builtInGain to set.
	 */
	public void setBuiltInGain(double builtInGain) {
		this.builtInGain = builtInGain;
	}

	/**
	 * @param clientAge
	 *            The clientAge to set.
	 */
	public void setClientAge(long clientAge) {
		this.clientAge = clientAge;
	}

	/**
	 * @param discountValue
	 *            The discountValue to set.
	 */
	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	/**
	 * @param endResidenceValue
	 *            The endResidenceValue to set.
	 */
	public void setEndResidenceValue(double endResidenceValue) {
		this.endResidenceValue = endResidenceValue;
	}

	/**
	 * @param estateTaxSavings
	 *            The estateTaxSavings to set.
	 */
	public void setEstateTaxSavings(double estateTaxSavings) {
		this.estateTaxSavings = estateTaxSavings;
	}

	/**
	 * @param fractionalDiscount
	 *            The fractionalDiscount to set.
	 */
	public void setFractionalDiscount(double fractionalDiscount) {
		this.fractionalDiscount = fractionalDiscount;
	}

	/**
	 * @param giftLeverage
	 *            The giftLeverage to set.
	 */
	public void setGiftLeverage(double giftLeverage) {
		this.giftLeverage = giftLeverage;
	}

	/**
	 * @param growthRate
	 *            The growthRate to set.
	 */
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}

	/**
	 * @param incomeInterest
	 *            The incomeInterest to set.
	 */
	public void setIncomeInterest(double incomeInterest) {
		this.incomeInterest = incomeInterest;
	}

	/**
	 * @param incomeIntValue
	 *            The incomeIntValue to set.
	 */
	public void setIncomeIntValue(double incomeIntValue) {
		this.incomeIntValue = incomeIntValue;
	}

	/**
	 * @param lifeExp
	 *            The lifeExp to set.
	 */
	public void setLifeExp(double lifeExp) {
		this.lifeExp = lifeExp;
	}

	/**
	 * @param priorGifts
	 *            The priorGifts to set.
	 */
	public void setPriorGifts(double priorGifts) {
		this.priorGifts = priorGifts;
	}

	/**
	 * @param remainderInterest
	 *            The remainderInterest to set.
	 */
	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	/**
	 * @param remainderIntValue
	 *            The remainderIntValue to set.
	 */
	public void setRemainderIntValue(double remainderIntValue) {
		this.remainderIntValue = remainderIntValue;
	}

	/**
	 * @param retainedInterest
	 *            The retainedInterest to set.
	 */
	public void setRetainedInterest(double retainedInterest) {
		this.retainedInterest = retainedInterest;
	}

	/**
	 * @param retainedIntValue
	 *            The retainedIntValue to set.
	 */
	public void setRetainedIntValue(double retainedIntValue) {
		this.retainedIntValue = retainedIntValue;
	}

	/**
	 * @param reversionaryInterest
	 *            The reversionaryInterest to set.
	 */
	public void setReversionaryInterest(double reversionaryInterest) {
		this.reversionaryInterest = reversionaryInterest;
	}

	/**
	 * @param reversionaryIntValue
	 *            The reversionaryIntValue to set.
	 */
	public void setReversionaryIntValue(double reversionaryIntValue) {
		this.reversionaryIntValue = reversionaryIntValue;
	}

	/**
	 * @param reversionRetained
	 *            The reversionRetained to set.
	 */
	public void setReversionRetained(boolean reversionRetained) {
		this.reversionRetained = reversionRetained;
	}

	/**
	 * @param startTerm
	 *            The startTerm to set.
	 */
	public void setStartTerm(long startTerm) {
		this.startTerm = startTerm;
	}

	/**
	 * @param taxableGift
	 *            The taxableGift to set.
	 */
	public void setTaxableGift(double taxableGift) {
		this.taxableGift = taxableGift;
	}

	/**
	 * @param taxableGiftValue
	 *            The taxableGiftValue to set.
	 */
	public void setTaxableGiftValue(double taxableGiftValue) {
		this.taxableGiftValue = taxableGiftValue;
	}

	/**
	 * @param termLength
	 *            The termLength to set.
	 */
	public void setTermLength(long termLength) {
		this.termLength = termLength;
	}

	/**
	 * @param totalInterest
	 *            The totalInterest to set.
	 */
	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	/**
	 * @param totalIntValue
	 *            The totalIntValue to set.
	 */
	public void setTotalIntValue(double totalIntValue) {
		this.totalIntValue = totalIntValue;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @param valueGiftToHeirs
	 *            The valueGiftToHeirs to set.
	 */
	public void setValueGiftToHeirs(double valueGiftToHeirs) {
		this.valueGiftToHeirs = valueGiftToHeirs;
	}
}
