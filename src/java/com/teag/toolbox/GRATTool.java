package com.teag.toolbox;

/**
 * @author Paul Stay
 * Description GRATTool.java
 * 
 * Copyright @2005 The Estate Advisory Group and NoboundsIt
 */

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.zcalc.zCalc;


public class GRATTool {
    int age;
    double afrRate;
    double annuity;
    int annuityFreq;
    double annuityIncrease;
    double annuityPaymentRate;
    double avgEstateTaxRate;
    double discountedPrincipal;
    double incomeTaxRate;
    String firstName;
    String lastName;
    int lifeExpectancy;
    double priorGifts;
    double principalGrowthRate;
    double principal;
    int startTerm;
    int termLength;
    Date transferDate;
    
    double acctualPaymentRate;
    double annuityPayment;
    double annuityInterest;
    double annuityFactor;
    double estateTaxSavings;
    boolean federalRev;
    double gratBenefit;
    double leverage;
    double optimalPaymentrate;
    double remainderInterest;
    double reatainedAnnuityInterest;
    double taxableGift;
    double totalGiftTaxPayable;
    double totalInterest;

    /**
     * @return Returns the acctualPaymentRate.
     */
    public double getAcctualPaymentRate() {
        return acctualPaymentRate;
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
    public int getAge() {
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
     * @return Returns the annuityPayment.
     */
    public double getAnnuityPayment() {
        return annuityPayment;
    }
    /**
     * @return Returns the annuityPaymentRate.
     */
    public double getAnnuityPaymentRate() {
        return annuityPaymentRate;
    }
    /**
     * @return Returns the avgEstateTaxRate.
     */
    public double getAvgEstateTaxRate() {
        return avgEstateTaxRate;
    }
    /**
     * @return Returns the discountedPrincipal.
     */
    public double getDiscountedPrincipal() {
        return discountedPrincipal;
    }
    /**
     * @return Returns the estateTaxSavings.
     */
    public double getEstateTaxSavings() {
        return estateTaxSavings;
    }
    /**
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @return Returns the gratBenefit.
     */
    public double getGratBenefit() {
        return gratBenefit;
    }
    /**
     * @return Returns the incomeTaxRate.
     */
    public double getIncomeTaxRate() {
        return incomeTaxRate;
    }
    /**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @return Returns the leverage.
     */
    public double getLeverage() {
        return leverage;
    }
    /**
     * @return Returns the lifeExpectancy.
     */
    public int getLifeExpectancy() {
        return lifeExpectancy;
    }
    /**
     * @return Returns the optimalPaymentrate.
     */
    public double getOptimalPaymentrate() {
        return optimalPaymentrate;
    }
    /**
     * @return Returns the principal.
     */
    public double getPrincipal() {
        return principal;
    }
    /**
     * @return Returns the principalGrowthRate.
     */
    public double getPrincipalGrowthRate() {
        return principalGrowthRate;
    }
    /**
     * @return Returns the priorGifts.
     */
    public double getPriorGifts() {
        return priorGifts;
    }
    /**
     * @return Returns the reatainedAnnuityInterest.
     */
    public double getReatainedAnnuityInterest() {
        return reatainedAnnuityInterest;
    }
    /**
     * @return Returns the remainderInterest.
     */
    public double getRemainderInterest() {
        return remainderInterest;
    }
    /**
     * @return Returns the startTerm.
     */
    public int getStartTerm() {
        return startTerm;
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
    public int getTermLength() {
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
    public Date getTransferDate() {
        return transferDate;
    }
    public void init() {
        zCalc zc = new zCalc();
        zc.StartUp();
        int yr = age;
        
        if( startTerm != 1) {
            yr = (startTerm - 1) + age;
        }
        
        lifeExpectancy = (int) zc.zLE(age,0,0,0,0,0,11);
        
        annuityPaymentRate = annuity / discountedPrincipal;
        
        annuityFactor = zc.zGRATAF(afrRate, termLength, yr, 0, annuityIncrease, annuityFreq,0);
        optimalPaymentrate = zc.zGRATZO(afrRate, termLength, annuityIncrease, annuityFreq,0);
        acctualPaymentRate = annuityPaymentRate;
        annuityPayment = annuity;
        taxableGift = zc.zGRATGIFT(afrRate, termLength, 0, 0, annuityIncrease, annuityFreq, discountedPrincipal, annuityPaymentRate, 0);
        remainderInterest = taxableGift;
        annuityInterest = discountedPrincipal - remainderInterest;
        
        if( acctualPaymentRate > optimalPaymentrate) 
            federalRev = true;
        else
            federalRev = false;
        
        totalInterest = discountedPrincipal;
        reatainedAnnuityInterest = totalInterest - taxableGift;
        
        Date now = new Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(now);
        int year = cal.get(Calendar.YEAR);
        totalGiftTaxPayable = zc.zFGT(taxableGift, age, 0,0,year,0,0);
        gratBenefit = zc.zGRATBEN(afrRate, termLength, principalGrowthRate, annuityIncrease, 
                annuityFreq, principal, annuityPaymentRate,0);
        estateTaxSavings = avgEstateTaxRate * (gratBenefit - taxableGift);

        zc.ShutDown();
    }
    /**
     * @return Returns the federalRev.
     */
    public boolean isFederalRev() {
        return federalRev;
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
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * @param annuity The annuity to set.
     */
    public void setAnnuity(double annuity) {
        this.annuity = annuity;
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
     * @param annuityPayment The annuityPayment to set.
     */
    public void setAnnuityPayment(double annuityPayment) {
        this.annuityPayment = annuityPayment;
    }
    /**
     * @param avgEstateTaxRate The avgEstateTaxRate to set.
     */
    public void setAvgEstateTaxRate(double avgEstateTaxRate) {
        this.avgEstateTaxRate = avgEstateTaxRate;
    }
    /**
     * @param discountedPrincipal The discountedPrincipal to set.
     */
    public void setDiscountedPrincipal(double discountedPrincipal) {
        this.discountedPrincipal = discountedPrincipal;
    }
    /**
     * @param firstName The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @param incomeTaxRate The incomeTaxRate to set.
     */
    public void setIncomeTaxRate(double incomeTaxRate) {
        this.incomeTaxRate = incomeTaxRate;
    }
    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @param lifeExpectancy The lifeExpectancy to set.
     */
    public void setLifeExpectancy(int lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }
    /**
     * @param principal The principal to set.
     */
    public void setPrincipal(double principal) {
        this.principal = principal;
    }
    /**
     * @param principalGrowthRate The principalGrowthRate to set.
     */
    public void setPrincipalGrowthRate(double principalGrowthRate) {
        this.principalGrowthRate = principalGrowthRate;
    }
    /**
     * @param priorGifts The priorGifts to set.
     */
    public void setPriorGifts(double priorGifts) {
        this.priorGifts = priorGifts;
    }
    /**
     * @param startTerm The startTerm to set.
     */
    public void setStartTerm(int startTerm) {
        this.startTerm = startTerm;
    }
    /**
     * @param termLength The termLength to set.
     */
    public void setTermLength(int termLength) {
        this.termLength = termLength;
    }
    /**
     * @param transferDate The transferDate to set.
     */
    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }
}
