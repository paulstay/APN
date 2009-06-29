/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author Paul Stay
 * Description: This is used to store Idit with Grat values
 */

import com.teag.estate.IditTool;

public class IditValues extends EstatePlanSqlBean {
	double notePayment[];
	double gratPayment[];
	double toFamily[];
	double gratValue[];
	int finalDeath;
	double lifeDeathBenefit;
	double lifeCashValue;
	int term;
	
	public void calculate(long toolId){
		IditTool idit = new IditTool();
		idit.setDbObject();
		idit.setId(toolId);
		idit.read();
		idit.setEstate(estate);
		idit.calculate();
		
		term = (int) idit.getGratTerm();
		
		lifeDeathBenefit += idit.getLifeDeathBenefit();
		lifeCashValue += idit.getLifeCashValue();
		
		double iditTable[][] = idit.getIditTable();
		
		for( int i = 0; i < idit.getGratTerm(); i++) {
			notePayment[i] += iditTable[i][3];
			gratPayment[i] += iditTable[i][6];
			gratValue[i] += iditTable[i][2];
		}
		
		for( int i= 0; i < finalDeath; i++) {
			toFamily[i] += iditTable[i][10];
		}
	}
	
	public double getGratPayment(int year) {
		return gratPayment[year];
	}
	
	public double getGratValue(int year) {
		return gratValue[year];
	}
	
	public double getLifeCashValue() {
		return lifeCashValue;
	}
	
	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}
	
	public double getNotePayment(int year) {
		return notePayment[year];
	}
	
	public int getTerm() {
		return term;
	}
	
	public double getToFamily(int year) {
		return toFamily[year];
	}
	
	public void initialize() {
		notePayment = new double[EstatePlanTable.MAX_TABLE];
		gratPayment = new double[EstatePlanTable.MAX_TABLE];
		toFamily = new double[EstatePlanTable.MAX_TABLE];
		gratValue = new double[EstatePlanTable.MAX_TABLE];
		lifeDeathBenefit = 0;
		lifeCashValue = 0;
		for(int i = 0; i < 30; i++) {
			notePayment[i] = 0;
			gratPayment[i] = 0;
			toFamily[i] = 0;
			gratValue[i] = 0;
		}
	}
}
