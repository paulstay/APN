package com.teag.toolbox;

/**
 * @author Paul Stay Description QprtTool.java Qualified Personal Residence
 *         Trust - Estate Planning Tool
 * 
 * Copyright
 * @2005 The Estate Advisory Group and NoboundsIt
 */

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zcalc.zCalc;

public class QprtTool {
    public static int calculateAge(String d) {
        SimpleDateFormat df = new SimpleDateFormat("M/d/y");
        Date birthday = df.parse(d, new ParsePosition(0));
        Calendar calendarA = Calendar.getInstance();
        Calendar calendarB = Calendar.getInstance(); // right now!

        int multiplier = -1;
        calendarA.setTime(birthday);

        int years = calendarA.get(Calendar.YEAR) - calendarB.get(Calendar.YEAR);
        int months = calendarA.get(Calendar.MONTH)
                - calendarB.get(Calendar.MONTH);
        int days = calendarA.get(Calendar.DAY_OF_MONTH)
                - calendarB.get(Calendar.DAY_OF_MONTH);
        if (years > 0 && (months < 0 || (months == 0 && days < 0))) {
            years -= 1;
        }
        return years * multiplier;

    }

    private String firstName;

    private String residenceName;

    private String residenceType;

    private double value;

    private double basis;

    private double growthRate;

    private String birthDate;

    private double priorGifts;

    private double afrRate;

    private String afrDate;

    private double discountValue;

    private boolean reversionRetained;

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

    private double incomeIntValue;

    private double reversionaryIntValue;

    private double remainderIntValue;

    private double totalIntValue;

    private double retainedIntValue;

    private double lifeExp;

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
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
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
     * @return Returns the residenceName.
     */
    public String getResidenceName() {
        return residenceName;
    }

    /**
     * @return Returns the residenceType.
     */
    public String getResidenceType() {
        return residenceType;
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
     * Initialize all of the variable and fields for the QPRT tool
     *  
     */
    public void init() {
        initZCalc();
        discountValue = value * fractionalDiscount;
        incomeInterest = 1 - (reversionaryInterest + remainderInterest);
        incomeIntValue = incomeInterest * discountValue;
        reversionaryIntValue = discountValue * reversionaryInterest;
        remainderIntValue = remainderInterest * discountValue;
        totalIntValue = discountValue;
        retainedIntValue = totalIntValue - taxableGiftValue;
        endResidenceValue = value
                * Math.pow((1. + this.growthRate), this.termLength);
        builtInGain = endResidenceValue - basis;
        estateTaxSavings = ToolboxDefines.ESTATE_TAX_RATE
                * (endResidenceValue - taxableGiftValue);
        taxableGift = taxableGiftValue / discountValue;
        valueGiftToHeirs = taxableGift * totalIntValue;
        afrDate.concat("");
    }

    /**
     * Initialize all of the zCalc calculations in one place.
     *  
     */

    public void initZCalc() {

        clientAge = calculateAge(birthDate);

        zCalc zc = new zCalc();
        zc.StartUp();
        int ageLen = (int) clientAge;

        if (this.startTerm > 1)
            ageLen = (int) ((startTerm - 1) + clientAge);

        lifeExp = zc.zLE(clientAge, 0, 0, 0, 0, 0, 11);
        this.reversionaryInterest = zc.zTERMLIFE(3, afrRate, termLength,
                ageLen, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
        this.remainderInterest = zc.zTERMLIFE(2, afrRate, termLength, ageLen,
                0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
        this.taxableGiftValue = zc.zQPRTGIFT(afrRate, termLength,
                ageLen, 0l, 0l, this.discountValue, 0l);

        zc.ShutDown();
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
     * @param firstName
     *            The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * @param priorGifts
     *            The priorGifts to set.
     */
    public void setPriorGifts(double priorGifts) {
        this.priorGifts = priorGifts;
    }

    /**
     * @param residenceName
     *            The residenceName to set.
     */
    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }

    /**
     * @param residenceType
     *            The residenceType to set.
     */
    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
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
     * @param termLength
     *            The termLength to set.
     */
    public void setTermLength(long termLength) {
        this.termLength = termLength;
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(double value) {
        this.value = value;
    }
}