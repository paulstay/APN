/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

import java.util.ArrayList;

import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.LoadAssetBean;

/**
 * @author Paul Stay
 *
 */
public class Cash extends EstatePlanSqlBean {
	
	double cashTable[] = new double[EstatePlanTable.MAX_TABLE];
	double cashValue[] = new double[EstatePlanTable.MAX_TABLE];
	
	public void calculate() {
		LoadAssetBean lab = new LoadAssetBean();

		EstatePlanAssets epa = new EstatePlanAssets(scenarioId);
		ArrayList<EPTAssetBean> cashList = epa.getAssetTypes(3);	// 3 = Asset Type cash
		
		
		for(EPTAssetBean i : cashList) {
			EPTAssetBean eab = i;
			eab.setDbObject();
			double rValue = eab.getRemainingValue();
			if( rValue > 0) {
				AssetSqlBean asb = lab.loadAssetBean(eab);
				double  iRate = asb.getAssetIncome();
				double  gRate = asb.getAssetGrowth();
				for( int j=0; j < finalDeath; j++) {
					cashTable[j] += rValue * iRate;
					cashValue[j] += rValue;
					rValue += gRate * rValue;
				}
			}
		}
	}
	
	public double getCashValue(int year) {
		return cashValue[year];
	}
	
	public double getInterestOnCash(int year) {
		return cashTable[year];
	}
}
