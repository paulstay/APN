/*
 * Created on May 24, 2005
 *
*/
package com.teag.scenario1;

/**
 * @author Paul Stay
 *
 */
import com.zcalc.zCalc;

public class RetirementCashFlow {


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
	
	public int getClientAge() {
		return clientAge;
	}
	public double[] getDispersements() {
		return dispersements;
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
	public int getSpouseAge() {
		return spouseAge;
	}
	public double[] getTotalValue() {
		return totalValue;
	}
	public boolean isSingle() {
		return isSingle;
	}
	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}
	public void setDispersements(double[] dispersements) {
		this.dispersements = dispersements;
	}
	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}

	public void setTotalValue(double[] totalValue) {
		this.totalValue = totalValue;
	}
}
