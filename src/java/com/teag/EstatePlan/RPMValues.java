/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author Paul Stay
 *
 * 
 */
import com.teag.estate.RpmTable;
import com.teag.estate.RpmTool;

public class RPMValues extends EstatePlanSqlBean {
	double rpmPayments[] = new double[EstatePlanTable.MAX_TABLE];
	double rpmTax[] = new double[EstatePlanTable.MAX_TABLE];
	double rpmToTrust[] = new double[EstatePlanTable.MAX_TABLE];
	double rpmToFamily[] = new double[EstatePlanTable.MAX_TABLE];
	double rpmValue[] = new double[EstatePlanTable.MAX_TABLE];
	int rpmTerm;
	int finalDeath;
	
	public void calculate(long toolId) {
		RpmTool rpm = new RpmTool();
		rpm.setDbObject();
		rpm.setId(toolId);
		rpm.read();
		rpm.calculate();
		RpmTable  rpTable = rpm.getRpTable();
		rpTable.calculateRpm();
		double rTable[][] = rpTable.getRpmTable();
		
		for( int i = 0; i < rpm.getTerm(); i++) {
			rpmPayments[i] += rTable[i][4];
			rpmTax[i] += ((rTable[i][7]/rTable[i][4] -1) * -1) * rTable[i][4];
			rpmToTrust[i] += rTable[i][7];
			rpmValue[i] = rTable[i][2];
		}
		
		for( int i = 0; i < finalDeath; i++) {
			rpmToFamily[i] += rTable[i][11];
		}
	}
	
	public double getDistribution(int year) {
		return rpmPayments[year];
	}
		
	
	public double getTax(int year) {
		return rpmTax[year];
	}
	
	public double getToFamily(int year){
		return rpmToFamily[year];
	}
	
	public double getToTrust(int year) {
		return rpmToTrust[year];
	}
	
	public double getValue(int year) {
		return rpmValue[year];
	}
	
	public void initialize() {
		for(int i = 0; i < finalDeath; i++) {
			rpmPayments[i] = 0;
			rpmTax[i] = 0;
			rpmToFamily[i] = 0;
			rpmValue[i] = 0;
		}
	}
}
