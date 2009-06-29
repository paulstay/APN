/*
 * Created on May 24, 2005
 *
 */
package com.estate.sc.utils;

/**
 * @author Paul Stay
 *
 */
import com.zcalc.*;

public class RetirementCashFlow {

	private static final long serialVersionUID = 440505361769028249L;
	private final static int MAX_TABLE = 60;
	int clientAge;
	int spouseAge;
	boolean isSingle = false;
	double dispersements[] = new double[MAX_TABLE];
	double totalValue[] = new double[MAX_TABLE];
	zCalc zc = new zCalc();
	
	public RetirementCashFlow() {
		for(int i = 0; i < MAX_TABLE; i++) {
			dispersements[i] = 0;
			totalValue[i] = 0;
		}
	}
	
	public void addPlan(double value, double growth ) {
		double cPlanValue = value;

		for( int i = 0; i < MAX_TABLE; i++){
			double tmp = cPlanValue * growth;
			double dist = 0;
			if( isSingle) {
				dist = getMinReqDist(cPlanValue, clientAge + i, 0);
			} else {
				dist = getMinReqDist(cPlanValue, clientAge + i, spouseAge+i);
			}
			cPlanValue = cPlanValue + tmp - dist;
			dispersements[i] += dist;
			totalValue[i] += cPlanValue;
		}
	}
	
	public void addPlan(double value, double growth, double ac, double ay) {
		double cPlanValue = value;
		
		for(int i = 0; i < MAX_TABLE; i++) {
			double tmp = cPlanValue * growth;
			if( i < ay)
				tmp += ac;
			double dist = 0; 
			if( isSingle) {
				dist = getMinReqDist(cPlanValue, clientAge + i, 0);
			} else {
				dist = getMinReqDist(cPlanValue, clientAge + i, spouseAge+i);
			}
			cPlanValue = cPlanValue + tmp - dist;
			dispersements[i] += dist;
			totalValue[i] += cPlanValue;
		}
	}
	
	private double getMinReqDist(double value, int cAge, int sAge) {
		if( cAge < 70) {
			return 0.0;
		}
		zc.StartUp();
		double zFactor = zc.zMINDIS(cAge,sAge,0,0,0,0,0,0,0,0);
		zc.ShutDown();
		
		if( zFactor > 0) {
			return value/zFactor;
		}
		return 0.0;
	}
	public int getClientAge() {
		return clientAge;
	}
	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}
	public double[] getDispersements() {
		return dispersements;
	}
	public void setDispersements(double[] dispersements) {
		this.dispersements = dispersements;
	}
	public int getSpouseAge() {
		return spouseAge;
	}
	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}
	public double[] getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double[] totalValue) {
		this.totalValue = totalValue;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}
}
