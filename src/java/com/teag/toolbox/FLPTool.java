package com.teag.toolbox;
/*
 * Created on Feb 16, 2005
 *
 */
/**
 * @author Paul Stay
 * Description FLPTool.java
 * 
 * Copyright @2005 The Estate Advisory Group and NoboundsIt
 */

public class FLPTool {

    // Input parameters
    String tool;
    double discountRate;
    double assetValue;
    double premiumGPValue;
    double gpGrowth;
    double lpGrowth;
    double gpPercentSlice;
    double lpPercentSlice;

    double gpShares;
    double lpShares;
    double existingDiscountRate;

    // Calculated parameters
    double lpDiscountValue;
    double gpPremiumValue;
    double perGpShareValue;
    double perLpShareValue;
    double totalShares;
    double gpValue;
    double lpValue;
    double estateLpTax;

    /**
     * @return Returns the assetValue.
     */
    public double getAssetValue() {
        return assetValue;
    }

    
    /**
     * @return Returns the discountRate.
     */
    public double getDiscountRate() {
        return discountRate;
    }
    /**
     * @return Returns the estateLpTax.
     */
    public double getEstateLpTax() {
        return estateLpTax;
    }
    /**
     * @return Returns the existingDiscountRate.
     */
    public double getExistingDiscountRate() {
        return existingDiscountRate;
    }
    /**
     * @return Returns the gpGrowth.
     */
    public double getGpGrowth() {
        return gpGrowth;
    }
    /**
     * @return Returns the gpPercentSlice.
     */
    public double getGpPercentSlice() {
        return gpPercentSlice;
    }
    /**
     * @return Returns the gpPremiumValue.
     */
    public double getGpPremiumValue() {
        return gpPremiumValue;
    }
    /**
     * @return Returns the gpShares.
     */
    public double getGpShares() {
        return gpShares;
    }
    /**
     * @return Returns the gpValue.
     */
    public double getGpValue() {
        return gpValue;
    }
    /**
     * @return Returns the lpDiscountValue.
     */
    public double getLpDiscountValue() {
        return lpDiscountValue;
    }
    /**
     * @return Returns the lpGrowth.
     */
    public double getLpGrowth() {
        return lpGrowth;
    }
    /**
     * @return Returns the lpPercentSlice.
     */
    public double getLpPercentSlice() {
        return lpPercentSlice;
    }
    /**
     * @return Returns the lpShares.
     */
    public double getLpShares() {
        return lpShares;
    }
    /**
     * @return Returns the lpValue.
     */
    public double getLpValue() {
        return lpValue;
    }
    /**
     * @return Returns the perGpShareValue.
     */
    public double getPerGpShareValue() {
        return perGpShareValue;
    }
    /**
     * @return Returns the perLpShareValue.
     */
    public double getPerLpShareValue() {
        return perLpShareValue;
    }
    /**
     * @return Returns the premiumGPValue.
     */
    public double getPremiumGPValue() {
        return premiumGPValue;
    }
    /**
     * @return Returns the tool.
     */
    public String getTool() {
        return tool;
    }
    /**
     * @return Returns the totalShares.
     */
    public double getTotalShares() {
        return totalShares;
    }
    public void init() {
        gpValue = (gpPercentSlice * assetValue)*(1.0 + gpPremiumValue);
        lpValue = (lpPercentSlice * assetValue) *(1.0 - discountRate);
        estateLpTax = lpValue * ToolboxDefines.ESTATE_TAX_RATE;
    }
    /**
     * @param assetValue The assetValue to set.
     */
    public void setAssetValue(double assetValue) {
        this.assetValue = assetValue;
    }
    /**
     * @param discountRate The discountRate to set.
     */
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
    /**
     * @param estateLpTax The estateLpTax to set.
     */
    public void setEstateLpTax(double estateLpTax) {
        this.estateLpTax = estateLpTax;
    }
    /**
     * @param existingDiscountRate The existingDiscountRate to set.
     */
    public void setExistingDiscountRate(double existingDiscountRate) {
        this.existingDiscountRate = existingDiscountRate;
    }
    /**
     * @param gpGrowth The gpGrowth to set.
     */
    public void setGpGrowth(double gpGrowth) {
        this.gpGrowth = gpGrowth;
    }
    /**
     * @param gpPercentSlice The gpPercentSlice to set.
     */
    public void setGpPercentSlice(double gpPercentSlice) {
        this.gpPercentSlice = gpPercentSlice;
    }
    /**
     * @param gpPremiumValue The gpPremiumValue to set.
     */
    public void setGpPremiumValue(double gpPremiumValue) {
        this.gpPremiumValue = gpPremiumValue;
    }
    /**
     * @param gpShares The gpShares to set.
     */
    public void setGpShares(double gpShares) {
        this.gpShares = gpShares;
    }
    /**
     * @param gpValue The gpValue to set.
     */
    public void setGpValue(double gpValue) {
        this.gpValue = gpValue;
    }
    /**
     * @param lpDiscountValue The lpDiscountValue to set.
     */
    public void setLpDiscountValue(double lpDiscountValue) {
        this.lpDiscountValue = lpDiscountValue;
    }
    /**
     * @param lpGrowth The lpGrowth to set.
     */
    public void setLpGrowth(double lpGrowth) {
        this.lpGrowth = lpGrowth;
    }
    /**
     * @param lpPercentSlice The lpPercentSlice to set.
     */
    public void setLpPercentSlice(double lpPercentSlice) {
        this.lpPercentSlice = lpPercentSlice;
    }
    /**
     * @param lpShares The lpShares to set.
     */
    public void setLpShares(double lpShares) {
        this.lpShares = lpShares;
    }
    /**
     * @param lpValue The lpValue to set.
     */
    public void setLpValue(double lpValue) {
        this.lpValue = lpValue;
    }
    /**
     * @param perGpShareValue The perGpShareValue to set.
     */
    public void setPerGpShareValue(double perGpShareValue) {
        this.perGpShareValue = perGpShareValue;
    }
    /**
     * @param perLpShareValue The perLpShareValue to set.
     */
    public void setPerLpShareValue(double perLpShareValue) {
        this.perLpShareValue = perLpShareValue;
    }
    /**
     * @param premiumGPValue The premiumGPValue to set.
     */
    public void setPremiumGPValue(double premiumGPValue) {
        this.premiumGPValue = premiumGPValue;
    }
    /**
     * @param tool The tool to set.
     */
    public void setTool(String tool) {
        this.tool = tool;
    }
    /**
     * @param totalShares The totalShares to set.
     */
    public void setTotalShares(double totalShares) {
        this.totalShares = totalShares;
    }
}
