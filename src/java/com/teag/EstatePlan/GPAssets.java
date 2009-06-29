/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

import java.util.ArrayList;
import java.util.Iterator;

import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.LoadAssetBean;

/**
 * @author Paul Stay
 *
 */
public class GPAssets extends EstatePlanSqlBean {
	double gpValTable[] = new double[EstatePlanTable.MAX_TABLE];
	double gpIntTable[] = new double[EstatePlanTable.MAX_TABLE];
	
	public void calculate() {
		LoadAssetBean lab = new LoadAssetBean();

		EstatePlanAssets epa = new EstatePlanAssets(scenarioId);
		epa.setScenarioId(scenarioId);
		ArrayList<EPTAssetBean> gpList = epa.getAssetTypes(12);	// 3 = Asset Type cash
		
		Iterator<EPTAssetBean> i = gpList.iterator();
		while( i.hasNext()) {
			EPTAssetBean eab = i.next();
			eab.setDbObject();
			double rValue = eab.getRemainingValue();
			if( rValue > 0) {
				AssetSqlBean asb = lab.loadAssetBean(eab);
				double iRate = asb.getAssetIncome();
				double iGrowth = asb.getAssetGrowth();
				double v = rValue;
				for( int k = 0; k < 30; k++) {
					gpValTable[k] += v;
					gpIntTable[k] += v * iRate;
					v += v * iGrowth;
				}
			}
		}
	}
	
	public double getGpValue(int year) {
		return gpValTable[year];
	}
	
	public double getInterestOnCash(int year) {
		return gpIntTable[year];
	}
}
