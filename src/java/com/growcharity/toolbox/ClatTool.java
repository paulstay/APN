package com.growcharity.toolbox;

/**
 * ClatTool	- 	implement the Charitable Lead Annuity Trust (Grantor and non-Grantor)
 * @author Paul Stay
 * Date: June 4, 2008
 * Copywrite @ 2008 GrowCharity LLC
 */

import java.util.Date;

import com.zcalc.zCalc;

public class ClatTool {

	/**
	 * irsRate - The IRS 7520 rate which is 120 % of the mid term ARF rate. Use
	 * the lowest from the past 3 months.
	 */
	double irsRate; // IRS 7520 Rate
	/**
	 * irsDate - The date of transfer to the trust. We use this to help pick the
	 * best 7520 rate
	 */
	Date irsDate;

	double fmv; // Fair Market Value
	double discount; // Discount in percent
	double discountValue; // Discount value = fmv * (1 - discount)
	double assetGrowth; // Asset Growth rate
	double assetIncome; // Asset Income generated per year
	double discountAssumptionRate; // used to help with Present Value
									// calculations
	double taxRate; // State Tax rate to apply with the appropriate Fed rate as
					// found in zCalc
	String calculationType; // A = Specific Annuity Payment, R = Specify
							// remainder interest, Z = zero remainder Interest
	double remainderInterest;
	double annuityPayment;
	double annuityRate;
	double annuityIncrease;
	double optimalRate;
	double annuityFactor;
	double charitableDeduction;
	int term;
	int recalculate = 0;

	boolean isGrantor;
	boolean useLLC;

	int freq;
	int endBegin;
	int estateTaxRate;
	int finalDeath;

	double npvFamily;
	double npvCharity;
	double netToCharity;
	double netToFamily;
	double taxesPaid;

	public void calculate() {
		discountValue = fmv * (1 - discount);
		if (calculationType.equalsIgnoreCase("A")) {
			calculateAnnuity();
		} else if (calculationType.equalsIgnoreCase("R")) {
			calculateRemainder();
		} else {
			remainderInterest = 0;
			calculateRemainder();
		}
	}

	public void calculateAnnuity() {
		zCalc zc = new zCalc();
		zc.StartUp();
		charitableDeduction = zc.zANNTERMLIFE(discountValue, annuityPayment,
				irsRate, term, 0, 0, 0, 0, 0, annuityIncrease, freq,
				endBegin, 0, 0, 0, recalculate);
		optimalRate = zc.zANNRATETARGET(discountValue, 0, irsRate, term,
				0, 0, 0, 0, 0, annuityIncrease, freq, endBegin, 0, 0, 0);
		remainderInterest = discountValue - charitableDeduction;
		annuityRate = annuityPayment / discountValue;
		zc.ShutDown();

		double balance = fmv;
		double ap = annuityPayment; // We need to keep this separate so we can
									// pass the annuityPayment for the beginning
									// of the term
		double sumAnnuityPayment = 0;
		for (int i = 0; i < term; i++) {
			double growth = balance * assetGrowth;
			double income = balance * assetIncome;
			double taxes = balance * taxRate;

			balance = balance + growth + income - taxes - ap;
			sumAnnuityPayment += ap;
			ap *= (1 + annuityIncrease);
		}

		netToFamily = balance;
		netToCharity = sumAnnuityPayment;

		if (discountAssumptionRate > 0) {
			npvFamily = calculateNPV(balance, term, discountAssumptionRate);
		} else {
			npvFamily = calculateNPV(balance, term, assetGrowth + assetIncome);
		}

		npvCharity = calculateNPV(sumAnnuityPayment, term, annuityRate);
	}

	/**
	 * Caluclate the Net Present Value of a gift for the CLAT, this an be either
	 * Charitable, or Family
	 * 
	 * @param value
	 * @param term
	 * @param interest
	 * @return netPresentValue;
	 */
	public double calculateNPV(double value, int term, double interest) {
		double npv = value / Math.pow((1 + interest), term);
		return npv;
	}

	public void calculateRemainder() {

		zCalc zc = new zCalc();
		zc.StartUp();
		annuityRate = zc.zANNRATETARGET(discountValue, remainderInterest,
				irsRate, term, 0, 0, 0, 0, 0, annuityIncrease, freq, endBegin,
				0, 0, 0);
		optimalRate = zc.zANNRATETARGET(discountValue, 0, irsRate, term,
				0, 0, 0, 0, 0, annuityIncrease, freq, endBegin, 0, 0, 0);
		annuityPayment = annuityRate * discountValue;
		annuityFactor = zc.zTERM(0, irsRate, term, annuityIncrease, freq,
				endBegin, 0);
		charitableDeduction = zc.zANNTERM(discountValue, annuityPayment,
				irsRate, term, annuityIncrease, freq, endBegin, 0, recalculate);
		zc.ShutDown();

		double balance = fmv;
		double ap = annuityPayment; // We need to keep this separate so we can
									// pass the annuityPayment for the beginning
									// of the term
		double sumAnnuityPayment = 0;
		for (int i = 0; i < term; i++) {
			double growth = balance * assetGrowth;
			double income = balance * assetIncome;
			double taxes = balance * taxRate;

			balance = balance + growth + income - taxes - ap;
			sumAnnuityPayment += ap;
			ap *= (1 + annuityIncrease);
		}

		netToFamily = balance;
		netToCharity = sumAnnuityPayment;

		if (discountAssumptionRate > 0) {
			npvFamily = calculateNPV(balance, term, discountAssumptionRate);
		} else {
			npvFamily = calculateNPV(balance, term, assetGrowth + assetIncome);
		}

		npvCharity = calculateNPV(sumAnnuityPayment, term, annuityRate);
	}

	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public double getAnnuityIncrease() {
		return annuityIncrease;
	}

	public double getAnnuityPayment() {
		return annuityPayment;
	}

	public double getAnnuityRate() {
		return annuityRate;
	}

	public double getAssetGrowth() {
		return assetGrowth;
	}

	public double getAssetIncome() {
		return assetIncome;
	}

	public String getCalculationType() {
		return calculationType;
	}

	public double getCharitableDeduction() {
		return charitableDeduction;
	}

	public double getDiscount() {
		return discount;
	}

	public double getDiscountAssumptionRate() {
		return discountAssumptionRate;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public int getEndBegin() {
		return endBegin;
	}

	public int getEstateTaxRate() {
		return estateTaxRate;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public int getFreq() {
		return freq;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getNetToCharity() {
		return netToCharity;
	}

	public double getNetToFamily() {
		return netToFamily;
	}

	public double getNpvCharity() {
		return npvCharity;
	}

	public double getNpvFamily() {
		return npvFamily;
	}

	public double getOptimalRate() {
		return optimalRate;
	}

	public int getRecalculate() {
		return recalculate;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getTaxesPaid() {
		return taxesPaid;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public int getTerm() {
		return term;
	}

	public boolean isGrantor() {
		return isGrantor;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}

	public void setAnnuityPayment(double annuityPayment) {
		this.annuityPayment = annuityPayment;
	}

	public void setAnnuityRate(double annuityRate) {
		this.annuityRate = annuityRate;
	}

	public void setAssetGrowth(double assetGrowth) {
		this.assetGrowth = assetGrowth;
	}

	public void setAssetIncome(double assetIncome) {
		this.assetIncome = assetIncome;
	}

	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}

	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setDiscountAssumptionRate(double discountAssumptionRate) {
		this.discountAssumptionRate = discountAssumptionRate;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public void setEndBegin(int endBegin) {
		this.endBegin = endBegin;
	}

	public void setEstateTaxRate(int estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public void setGrantor(boolean isGrantor) {
		this.isGrantor = isGrantor;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setNetToCharity(double netToCharity) {
		this.netToCharity = netToCharity;
	}

	public void setNetToFamily(double netToFamily) {
		this.netToFamily = netToFamily;
	}

	public void setNpvCharity(double npvCharity) {
		this.npvCharity = npvCharity;
	}

	public void setNpvFamily(double npvFamily) {
		this.npvFamily = npvFamily;
	}

	public void setOptimalRate(double optimalRate) {
		this.optimalRate = optimalRate;
	}

	public void setRecalculate(int recalculate) {
		this.recalculate = recalculate;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setTaxesPaid(double taxesPaid) {
		this.taxesPaid = taxesPaid;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}

}
