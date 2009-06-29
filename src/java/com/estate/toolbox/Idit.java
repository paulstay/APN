package com.estate.toolbox;

/**
 * @author Paul Stay
 * Date December 2007
 * Copyright @ TEAG Software LLC.
 *
 */

import com.zcalc.zCalc;

public class Idit {

	public static void main(String args[]) {
		Idit idit = new Idit();
		idit.setFinalDeath(25);
		idit.setFmv(1000000);
		idit.setAssetGrowth(.06);
		idit.setAssetIncome(.04);
		idit.setDiscount(.35);
		idit.setNoteTerm(10);
		idit.setNoteType(0);
		idit.setNoteRate(.0478);
		idit.calculate();
		double np[] = idit.getNotePayment();
		double nb[] = idit.getNoteBalance();
		for (int i = 0; i < idit.getNoteTerm(); i++) {
			System.out.println("year: " + Integer.toString(i + 1)
					+ " Note Payment " + Double.toString(np[i])
					+ " Note Balance " + Double.toString(nb[i]));
		}

		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,###");
		double balance = idit.getFmv();

		for (int i = 0; i < idit.getNoteTerm(); i++) {
			double growth = balance * idit.getAssetGrowth();
			double income = balance * idit.getAssetIncome();
			double pay = np[i];

			System.out.println("Year " + Integer.toString(i + 1) + "\tBalance "
					+ df.format(balance) + "\tGrowth " + df.format(growth)
					+ "\tIncome " + df.format(income) + "\tPayment "
					+ df.format(-pay) + "\tEnd Balance "
					+ df.format(balance + growth + income - pay));
			balance = balance + growth + income - pay;
		}
	}

	double noteRate;
	int noteTerm;

	int noteType = 2; // 0 = Level Payment + Int. 1 = Self Amoritizing, 2 =
						// INterest only w Balloon
	double taxRate; // Combined federal and state for summary purposes.
	int finalDeath;
	double lifeDeathBenefit;
	double lifePremium;

	int lifePremiumYears;
	// For calculations.....
	double fmv; // Fair Market Value
	double gift; // Initial Gift
	double giftAmount = 0;
	String giftType;

	double dmv; // Discounted market value
	double discount;
	double payment;
	double assetGrowth;

	double assetIncome;

	double estateTaxRate;
	double finalBalance = 0;

	boolean useLLC = false;

	double[] notePayment;
	double[] noteBalance;
	double[] balance;
	double[] growth;
	double[] income;
	double[] lifePayment;
	double[] endBalance;

	public void calculate() {

		if (finalDeath < noteTerm) {
			finalDeath = noteTerm;
		}

		notePayment = new double[finalDeath];
		noteBalance = new double[finalDeath];
		balance = new double[finalDeath];
		growth = new double[finalDeath];
		income = new double[finalDeath];
		lifePayment = new double[finalDeath];
		endBalance = new double[finalDeath];

		dmv = fmv * (1 - discount);

		double bal = fmv;
		for (int i = 0; i < finalDeath; i++) {
			double g = bal * assetGrowth;
			double d = bal * assetIncome;
			bal += g + d;
		}

		finalBalance = bal; // Final Balance for comparison!

		zCalc zc = new zCalc();
		zc.StartUp();
		bal = fmv + giftAmount;
		for (int year = 1; year <= finalDeath; year++) {
			balance[year - 1] = bal;
			growth[year - 1] = bal * assetGrowth;
			income[year - 1] = bal * assetIncome;
			notePayment[year - 1] = zc.zINSTALL(0, dmv, 0, noteRate, noteTerm,
					year, noteType, 0, 0, 0);
			if (year <= lifePremiumYears)
				lifePayment[year - 1] = lifePremium;
			noteBalance[year - 1] = zc.zINSTALL(4, dmv, 0, noteRate, noteTerm,
					year, noteType, 0, 0, 0);
			bal = bal + growth[year - 1] + income[year - 1]
					- lifePayment[year - 1] - notePayment[year - 1];
			endBalance[year - 1] = bal;
		}
		zc.ShutDown();
	}

	public double getAssetGrowth() {
		return assetGrowth;
	}

	public double getAssetIncome() {
		return assetIncome;
	}

	public double getDiscount() {
		return discount;
	}

	public double getDmv() {
		return dmv;
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

	public double getGift() {
		return gift;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public double getLifePremium() {
		return lifePremium;
	}

	public int getLifePremiumYears() {
		return lifePremiumYears;
	}

	public double[] getNoteBalance() {
		return noteBalance;
	}

	public double[] getNotePayment() {
		return notePayment;
	}

	public double getNoteRate() {
		return noteRate;
	}

	public int getNoteTerm() {
		return noteTerm;
	}

	public int getNoteType() {
		return noteType;
	}

	public double getPayment() {
		return payment;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	public void setAssetGrowth(double assetGrowth) {
		this.assetGrowth = assetGrowth;
	}

	public void setAssetIncome(double assetIncome) {
		this.assetIncome = assetIncome;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setDmv(double dmv) {
		this.dmv = dmv;
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

	public void setGift(double gift) {
		this.gift = gift;
	}

	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}

	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
	}

	public void setLifePremiumYears(int lifePremiumYears) {
		this.lifePremiumYears = lifePremiumYears;
	}

	public void setNoteBalance(double[] noteBalance) {
		this.noteBalance = noteBalance;
	}

	public void setNotePayment(double[] notePayment) {
		this.notePayment = notePayment;
	}

	public void setNoteRate(double noteRate) {
		this.noteRate = noteRate;
	}

	public void setNoteTerm(int noteTerm) {
		this.noteTerm = noteTerm;
	}

	public void setNoteType(int noteType) {
		this.noteType = noteType;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}

	public double getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(double giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public double getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(double finalBalance) {
		this.finalBalance = finalBalance;
	}

	public double[] getBalance() {
		return balance;
	}

	public void setBalance(double[] balance) {
		this.balance = balance;
	}

	public double[] getGrowth() {
		return growth;
	}

	public void setGrowth(double[] growth) {
		this.growth = growth;
	}

	public double[] getIncome() {
		return income;
	}

	public void setIncome(double[] income) {
		this.income = income;
	}

	public double[] getLifePayment() {
		return lifePayment;
	}

	public void setLifePayment(double[] lifePayment) {
		this.lifePayment = lifePayment;
	}

	public double[] getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(double[] endBalance) {
		this.endBalance = endBalance;
	}

}
