package com.estate.toolbox;

/**
 *
 * @author Paul Stay
 * Description - Calculate the Estate and Gift taxes based on the inputs
 * Date May 2009
 * copyright The Estate Advisory Group @ 2009
 */

import com.zcalc.*;

public class EstateClass {
    double grossEstate = 0;
    double maritalDeduction = 0;
    double adminCosts = 0;
    double charitableDeduction = 0;
    double postGifts = 0;
    double unifiedCredit = 0;
    double fobiDeduction = 0;
    int taxLaw = 0;                  // For now we will use the default as the current law
    double taxableEstate = 0;        // Calculated from above
    double federalEstateTax = 0;     // Calculated using zCalc
    double giftTax = 0;              // Calculated using post 76 gifts
    double netEstate = 0;            // Final value of estate
    double yearsComparison = 0;      // If we want to compare the out years
    double growthRate = 0;           // Amount to grow the grossEstate


    /**
     * We will use this for standalone testing. And compare the results with ZCalc, and or Number Cruncher
     * @param args
     */
    public static void main(String args[]) {

    }

    public EstateClass() {

    }


    public void comparison() {

    }



    public double getAdminCosts() {
        return adminCosts;
    }

    public void setAdminCosts(double adminCosts) {
        this.adminCosts = adminCosts;
    }

    public double getCharitableDeduction() {
        return charitableDeduction;
    }

    public void setCharitableDeduction(double charitableDeduction) {
        this.charitableDeduction = charitableDeduction;
    }

    public double getFederalEstateTax() {
        return federalEstateTax;
    }

    public void setFederalEstateTax(double federalEstateTax) {
        this.federalEstateTax = federalEstateTax;
    }

    public double getFobiDeduction() {
        return fobiDeduction;
    }

    public void setFobiDeduction(double fobiDeduction) {
        this.fobiDeduction = fobiDeduction;
    }

    public double getGiftTax() {
        return giftTax;
    }

    public void setGiftTax(double giftTax) {
        this.giftTax = giftTax;
    }

    public double getGrossEstate() {
        return grossEstate;
    }

    public void setGrossEstate(double grossEstate) {
        this.grossEstate = grossEstate;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public double getMaritalDeduction() {
        return maritalDeduction;
    }

    public void setMaritalDeduction(double maritalDeduction) {
        this.maritalDeduction = maritalDeduction;
    }

    public double getNetEstate() {
        return netEstate;
    }

    public void setNetEstate(double netEstate) {
        this.netEstate = netEstate;
    }

    public double getPostGifts() {
        return postGifts;
    }

    public void setPostGifts(double postGifts) {
        this.postGifts = postGifts;
    }

    public int getTaxLaw() {
        return taxLaw;
    }

    public void setTaxLaw(int taxLaw) {
        this.taxLaw = taxLaw;
    }

    public double getTaxableEstate() {
        return taxableEstate;
    }

    public void setTaxableEstate(double taxableEstate) {
        this.taxableEstate = taxableEstate;
    }

    public double getUnifiedCredit() {
        return unifiedCredit;
    }

    public void setUnifiedCredit(double unifiedCredit) {
        this.unifiedCredit = unifiedCredit;
    }

    public double getYearsComparison() {
        return yearsComparison;
    }

    public void setYearsComparison(double yearsComparison) {
        this.yearsComparison = yearsComparison;
    }


}
