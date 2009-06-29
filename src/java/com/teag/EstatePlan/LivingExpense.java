package com.teag.EstatePlan;

/**
 * @author stay
 * Created on May 30, 2005
 */
public class LivingExpense {
	double livingExpense;
	double growth;
	
	double livingTable[];
	
	public void calculate() {
		livingTable = new double[EstatePlanTable.MAX_TABLE];
		double l = livingExpense;
        
        livingTable = new double[EstatePlanTable.MAX_TABLE];
        for(int i= 0; i < 60; i++) {
               livingTable[i] = l;
               l += (l * growth);
        }
	}
	
	/**
	 * @return Returns the growth.
	 */
	public double getGrowth() {
		return growth;
	}
	
	public double getLiving(int year) {
		return livingTable[year];
	}
	/**
	 * @return Returns the livingExpense.
	 */
	public double getLivingExpense() {
		return livingExpense;
	}
	/**
	 * @param growth The growth to set.
	 */
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	/**
	 * @param livingExpense The livingExpense to set.
	 */
	public void setLivingExpense(double livingExpense) {
		this.livingExpense = livingExpense;
	}
}
