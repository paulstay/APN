/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teag.EstatePlan;

import com.estate.sc.utils.LineObject;
import com.teag.estate.*;
import java.util.ArrayList;

/**
 *
 * @author Paul Stay
 * Specific Split Dollar tool for Marc Sheridan
 * Set up the cash flow for the tool!
 * We have the specifics here, instead in the tool.....
 *
 * The process is that 1) we liquidate $4m in muni bonds
 * We add a gift, and then loan money at the apr interest rate of 3.4%
 * until death. We purchase a $20M life insurance policy, at death
 * the loan is paid back to the estate, and the family gets the rest of the life insurance
 *
 * Premiums
 *    Year 1  $1.4M
 *    Year 2  $1.4M
 *    Year 3  $800K
 *
 * Gift
 *    Year 1  $400K
 */
public class SplitDollarCashFlow extends EstatePlanSqlBean {

    SplitDollarTool splitDollarTool;
    ArrayList<LineObject> assetCashFlow = new ArrayList<LineObject>();
    ArrayList<LineObject> assetNetWorth = new ArrayList<LineObject>();
    ArrayList<LineObject> gratPayments = new ArrayList<LineObject>();
    ArrayList<LineObject> gratEstateValue = new ArrayList<LineObject>();
    ArrayList<LineObject> gratToFamily = new ArrayList<LineObject>();
    ArrayList<LineObject> lifeInsurance = new ArrayList<LineObject>();
    ArrayList<AssetData> assetList = new ArrayList<AssetData>();


    public void processSplitDollar() {

    }

    public ArrayList<LineObject> getAssetCashFlow() {
        return assetCashFlow;
    }

    public void setAssetCashFlow(ArrayList<LineObject> assetCashFlow) {
        this.assetCashFlow = assetCashFlow;
    }

    public ArrayList<LineObject> getAssetNetWorth() {
        return assetNetWorth;
    }

    public void setAssetNetWorth(ArrayList<LineObject> assetNetWorth) {
        this.assetNetWorth = assetNetWorth;
    }

    public ArrayList<LineObject> getGratEstateValue() {
        return gratEstateValue;
    }

    public void setGratEstateValue(ArrayList<LineObject> gratEstateValue) {
        this.gratEstateValue = gratEstateValue;
    }

    public ArrayList<LineObject> getGratPayments() {
        return gratPayments;
    }

    public void setGratPayments(ArrayList<LineObject> gratPayments) {
        this.gratPayments = gratPayments;
    }

    public ArrayList<LineObject> getGratToFamily() {
        return gratToFamily;
    }

    public void setGratToFamily(ArrayList<LineObject> gratToFamily) {
        this.gratToFamily = gratToFamily;
    }

    public ArrayList<LineObject> getLifeInsurance() {
        return lifeInsurance;
    }

    public void setLifeInsurance(ArrayList<LineObject> lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public SplitDollarTool getSplitDollarTool() {
        return splitDollarTool;
    }

    public void setSplitDollarTool(SplitDollarTool splitDollarTool) {
        this.splitDollarTool = splitDollarTool;
    }
}
