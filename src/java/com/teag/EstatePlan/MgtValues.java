package com.teag.EstatePlan;

/**
 * @author stay
 * Created on May 30, 2005
 *
 */

import com.teag.estate.MGTrustTool;
public class MgtValues extends EstatePlanSqlBean {
	double assetValue;
	double assetGrowth;
	double mgtTrustValue[] = new double[EstatePlanTable.MAX_TABLE];
	int term = 10;
	
	public void calculate(long toolId) {
		MGTrustTool mgt = new MGTrustTool();
		mgt.setDbObject();
		mgt.setId(toolId);
		mgt.read();
		mgt.calculate();
		term = mgt.getTerm();
		mgt.calcDelawareTrust();
		double mgtTable[][] = mgt.getDelawareTrust();
		
		assetValue = mgtTable[0][0];
		assetGrowth = mgt.getGrowth();

		for(int i = 0; i < finalDeath; i++) {
			mgtTrustValue[i] = assetValue * Math.pow((1.0 + assetGrowth), i);
		}

	}
	
	public double getAssetGrowth() {
		return assetGrowth;
	}
	
	public double getAssetValue() {
		return assetValue;
	}
	
	public double getMgtValue(int year) {
		return mgtTrustValue[year];
	}
	
	public int getTerm() {
		return term;
	}

	public void intialize() {
		assetValue = 0;
	}

	public void setTerm(int term) {
		this.term = term;
	}
}
