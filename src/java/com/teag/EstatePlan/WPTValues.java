/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author stay
 *
 */
import com.teag.estate.CrtTool;

public class WPTValues extends EstatePlanSqlBean {
	double wptPayments[] = new double[EstatePlanTable.MAX_TABLE];
	double wptTotalValue[] = new double[EstatePlanTable.MAX_TABLE];
	int cAge;
	int sAge;
	int finalDeath;
	double charitableDedFactor = 0;
	double payOutRate;
	double interestRate;
	double deathBenefit = 0;
	double charitableDeduction = 0;

	public void calculate(long toolId) {
		CrtTool wpt = new CrtTool();
		wpt.setId(toolId);
		wpt.setClientAge(cAge);
		wpt.setSpouseAge(sAge);
		wpt.read();
		wpt.setFinalDeath(finalDeath+1);
		wpt.calculate();
		charitableDeduction = wpt.getCharitableDeduction();
		deathBenefit = wpt.getLifeDeathBenefit();
		charitableDedFactor = wpt.getCharitableDeductionFactor();
		payOutRate = wpt.getPayoutRate();
		interestRate = wpt.getPayoutIncome();
		
		double wptTable[][] = wpt.getCrtTable();
		
		for(int i = 0; i < finalDeath; i++){
			wptPayments[i] += wptTable[i+1][0];
			wptTotalValue[i] = wptTable[i+1][5];
		}
	}
	
	public double getCharitableDedFactor() {
		return charitableDedFactor;
	}
	
	public double getDeathBenefit() {
		return deathBenefit;
	}
	
	public double getPayment(int year) {
		return wptPayments[year];
	}
	
	public double getValue(int year) {
		return wptTotalValue[year];
	}
	
	public void initialize() {
		for( int i = 0; i < EstatePlanTable.MAX_TABLE; i++) {
			wptPayments[i] = 0.;
			wptTotalValue[i] = 0;
		}
		cAge = estate.getClientAge();
		sAge = estate.getSpouseAge();
	}

	public double getCharitableDeduction() {
		return charitableDeduction;
	}

	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}
}
