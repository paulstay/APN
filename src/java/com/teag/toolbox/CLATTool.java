package com.teag.toolbox;

/**
 * @author Paul Stay Description CLATTool.java Charitable Lead Annuity Trust
 *         Tool
 * 
 * Copyright
 * @2005 The Estate Advisory Group and NoboundsIt
 */

import com.teag.util.Utilities;
import com.zcalc.zCalc;

public class CLATTool {
    // Variables for CLAT Tool
    double totalValue;
    double discountValue;
    double averageGrowth;
    double averageIncome;
    double discountRate;
    double annuityPayment;
    double annuityRate;
    int annuityFreq;
    double afrRate;
    String transferDate;
    int trustTerm;
    double taxRate;
    double estateTaxRate;
    double priorGifts;
    double lifeExpectancy;
    double age1;
    double age2;
    String clientDateOfBirth;
    String spouseDateOfBirth;
    double annuityFactor;
    double paymentRate;
    double actualDiscRate;
    double annuityInterest;
    double totalInterest;
    double remainderInterest;
    double nonCharitableRemainderInterest;
    double clatDeduction;
    String leverage;

    /**
     * @return Returns the actualDiscRate.
     */
    public double getActualDiscRate() {
        return actualDiscRate;
    }

    /**
     * @return Returns the afrRate.
     */
    public double getAfrRate() {
        return afrRate;
    }

    /**
     * @return Returns the age1.
     */
    public double getAge1() {
        return age1;
    }

    /**
     * @return Returns the age2.
     */
    public double getAge2() {
        return age2;
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
     * @return Returns the annuityInterest.
     */
    public double getAnnuityInterest() {
        return annuityInterest;
    }

    /**
     * @return Returns the annuityPayment.
     */
    public double getAnnuityPayment() {
        return annuityPayment;
    }

    /**
     * @return Returns the annuityRate.
     */
    public double getAnnuityRate() {
        return annuityRate;
    }

    /**
     * @return Returns the averageGrowth.
     */
    public double getAverageGrowth() {
        return averageGrowth;
    }

    /**
     * @return Returns the averageIncome.
     */
    public double getAverageIncome() {
        return averageIncome;
    }

    /**
     * @return Returns the clatDeduction.
     */
    public double getClatDeduction() {
        return clatDeduction;
    }

    /**
     * @return Returns the clientDateOfBirth.
     */
    public String getClientDateOfBirth() {
        return clientDateOfBirth;
    }

    /**
     * @return Returns the discountRate.
     */
    public double getDiscountRate() {
        return discountRate;
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
     * @return Returns the leverage.
     */
    public String getLeverage() {
        return leverage;
    }

    /**
     * @return Returns the lifeExpectancy.
     */
    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    /**
     * @return Returns the nonCharitableRemainderInterest.
     */
    public double getNonCharitableRemainderInterest() {
        return nonCharitableRemainderInterest;
    }

    /**
     * @return Returns the paymentRate.
     */
    public double getPaymentRate() {
        return paymentRate;
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
     * @return Returns the spouseDateOfBirth.
     */
    public String getSpouseDateOfBirth() {
        return spouseDateOfBirth;
    }

    /**
     * @return Returns the taxRate.
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * @return Returns the totalInterest.
     */
    public double getTotalInterest() {
        return totalInterest;
    }

    /**
     * @return Returns the totalValue.
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * @return Returns the transferDate.
     */
    public String getTransferDate() {
        return transferDate;
    }

    /**
     * @return Returns the trustTerm.
     */
    public int getTrustTerm() {
        return trustTerm;
    }

    public void init() {
        zCalc zc = new zCalc();
        zc.StartUp();

        age1 = Utilities.CalcAge(clientDateOfBirth);
        age2 = Utilities.CalcAge(spouseDateOfBirth);

        int age = (int) Math.max(age1, age2);

        lifeExpectancy = zc.zLE(Math.round(age), 0, 0, 0, 0, 0, 11);

        if (annuityPayment == 0) {
            annuityPayment = discountValue * annuityRate;
        } else {
            annuityRate = annuityPayment / discountValue;
        }

        annuityFactor = zc.zTERM(0l, afrRate, trustTerm, 0.0,
                annuityFreq, 0l, 0l);
        paymentRate = annuityPayment / totalValue;
        clatDeduction = zc.zANNTERM(discountValue, annuityRate, afrRate,
                trustTerm, 0.0, annuityFreq, 0, 0, 0);

        annuityInterest = clatDeduction;
        totalInterest = discountValue;
        nonCharitableRemainderInterest = totalInterest - clatDeduction;
        remainderInterest = nonCharitableRemainderInterest;
        actualDiscRate = annuityRate;
        
        if (nonCharitableRemainderInterest != 0.0) {
            leverage = Math
                    .round(10.0 * (totalValue / nonCharitableRemainderInterest))
                    / 10.0 + ":1";
        } else {
            leverage = "??:??";
        }
    }

    /**
     * @param actualDiscRate
     *            The actualDiscRate to set.
     */
    public void setActualDiscRate(double actualDiscRate) {
        this.actualDiscRate = actualDiscRate;
    }

    /**
     * @param afrRate
     *            The afrRate to set.
     */
    public void setAfrRate(double afrRate) {
        this.afrRate = afrRate;
    }

    /**
     * @param age1 The age1 to set.
     */
    public void setAge1(double age1) {
        this.age1 = age1;
    }

    /**
     * @param age2 The age2 to set.
     */
    public void setAge2(double age2) {
        this.age2 = age2;
    }

    /**
     * @param annuityFactor
     *            The annuityFactor to set.
     */
    public void setAnnuityFactor(double annuityFactor) {
        this.annuityFactor = annuityFactor;
    }

    /**
     * @param annuityFreq
     *            The annuityFreq to set.
     */
    public void setAnnuityFreq(int annuityFreq) {
        this.annuityFreq = annuityFreq;
    }

    /**
     * @param annuityInterest
     *            The annuityInterest to set.
     */
    public void setAnnuityInterest(double annuityInterest) {
        this.annuityInterest = annuityInterest;
    }

    /**
     * @param annuityPayment
     *            The annuityPayment to set.
     */
    public void setAnnuityPayment(double annuityPayment) {
        this.annuityPayment = annuityPayment;
    }

    /**
     * @param annuityRate
     *            The annuityRate to set.
     */
    public void setAnnuityRate(double annuityRate) {
        this.annuityRate = annuityRate;
    }

    /**
     * @param averageGrowth
     *            The averageGrowth to set.
     */
    public void setAverageGrowth(double averageGrowth) {
        this.averageGrowth = averageGrowth;
    }

    /**
     * @param averageIncome
     *            The averageIncome to set.
     */
    public void setAverageIncome(double averageIncome) {
        this.averageIncome = averageIncome;
    }

    /**
     * @param clatDeduction
     *            The clatDeduction to set.
     */
    public void setClatDeduction(double clatDeduction) {
        this.clatDeduction = clatDeduction;
    }

    /**
     * @param clientDateOfBirth The clientDateOfBirth to set.
     */
    public void setClientDateOfBirth(String clientDateOfBirth) {
        this.clientDateOfBirth = clientDateOfBirth;
    }

    /**
     * @param discountRate
     *            The discountRate to set.
     */
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * @param discountValue
     *            The discountValue to set.
     */
    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    /**
     * @param estateTaxRate
     *            The estateTaxRate to set.
     */
    public void setEstateTaxRate(double estateTaxRate) {
        this.estateTaxRate = estateTaxRate;
    }

    /**
     * @param leverage
     *            The leverage to set.
     */
    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    /**
     * @param lifeExpectancy
     *            The lifeExpectancy to set.
     */
    public void setLifeExpectancy(double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    /**
     * @param nonCharitableRemainderInterest
     *            The nonCharitableRemainderInterest to set.
     */
    public void setNonCharitableRemainderInterest(
            double nonCharitableRemainderInterest) {
        this.nonCharitableRemainderInterest = nonCharitableRemainderInterest;
    }

    /**
     * @param paymentRate
     *            The paymentRate to set.
     */
    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
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
     * @param spouseDateOfBirth The spouseDateOfBirth to set.
     */
    public void setSpouseDateOfBirth(String spouseDateOfBirth) {
        this.spouseDateOfBirth = spouseDateOfBirth;
    }
    /**
     * @param taxRate
     *            The taxRate to set.
     */
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
    /**
     * @param totalInterest
     *            The totalInterest to set.
     */
    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }
    /**
     * @param totalValue
     *            The totalValue to set.
     */
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
    /**
     * @param transferDate
     *            The transferDate to set.
     */
    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
    /**
     * @param trustTerm
     *            The trustTerm to set.
     */
    public void setTrustTerm(int trustTerm) {
        this.trustTerm = trustTerm;
    }
}