package com.teag.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.teag.toolbox.FLPTool;

/**
 * @author Paul Stay
 * Description GratBean.java
 * 
 * Copyright @2005 The Estate Advisory Group and NoboundsIt
 */
public class ClatBean implements Serializable {
    class Asset {
        String name;
        double value;
        double growth;
        double income;
        
        /**
         * @return Returns the growth.
         */
        public double getGrowth() {
            return growth;
        }
        /**
         * @return Returns the income.
         */
        public double getIncome() {
            return income;
        }
        /**
         * @return Returns the name.
         */
        public String getName() {
            return name;
        }
        /**
         * @return Returns the value.
         */
        public double getValue() {
            return value;
        }
        /**
         * @param growth The growth to set.
         */
        public void setGrowth(double growth) {
            this.growth = growth;
        }
        /**
         * @param income The income to set.
         */
        public void setIncome(double income) {
            this.income = income;
        }
        /**
         * @param name The name to set.
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * @param value The value to set.
         */
        public void setValue(double value) {
            this.value = value;
        }
    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 4925437514020531064L;
    FLPTool flpTool = new FLPTool();
    Asset currentAsset;
    
    ArrayList<Asset> assets = new ArrayList<Asset>();
    
    public void addAsset(String name, double value, double growth, double income) {
        Asset a = new Asset();
        a.setName(name);
        a.setValue(value);
        a.setGrowth(growth);
        a.setIncome(income);
        assets.add(a);
    }
    
    public void clearAssets() {
        assets.clear();
    }
    
    public double getAverageGrowth() {
        double total = getTotalValue();
        Iterator<Asset> i = assets.iterator();
        double averageGrowth = 0.0;
        while( i.hasNext()) {
            Asset a = i.next();
            averageGrowth += (a.getGrowth()* (a.getValue()/total));
        }
        return averageGrowth;
    }
    
    public double getAverageIncome() {
        double total = getTotalValue();
        double averageIncome = 0.0;
        for(Asset i : assets){
            Asset a = i;
            averageIncome += (a.getIncome()* (a.getValue()/total));
        }
        return averageIncome;
    }
    
    public double getCurrentGrowth() {
        return currentAsset.getGrowth();
    }
    
    public double getCurrentIncome() {
        return currentAsset.getIncome();
    }
    
    public String getCurrentName() {
        return currentAsset.getName();
    }
    
    public double getCurrentValue() {
        return currentAsset.getValue();
    }
    
    /**
     * @return Returns the flpTool.
     */
    public FLPTool getFlpTool() {
        return flpTool;
    }
    
    public Iterator<Asset> getListIterator() {
        return assets.iterator();
    }
    
    public double getLpDiscountValue() {
        double lpValue = flpTool.getDiscountRate() * (getTotalValue()*flpTool.getLpPercentSlice());
        return lpValue;
    }
    
    public double getTotalValue() {
        double total = 0.0;
    	for(Asset i : assets){
            Asset a = i;
            total += a.getValue();
        }
        return total;
    }
    public boolean loadAsset(Iterator<Asset> i) {
        if( i.hasNext()) {
            currentAsset = i.next();
            return true;
        }
		return false;
    }
    /**
     * @param flpTool The flpTool to set.
     */
    public void setFlpTool(FLPTool flpTool) {
        this.flpTool = flpTool;
    }
}
