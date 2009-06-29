package com.teag.EstatePlan;

import java.util.ArrayList;
import java.util.Iterator;

import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.LoadAssetBean;

public class LPAssets extends EstatePlanSqlBean {
	double lpValTable[] = new double[EstatePlanTable.MAX_TABLE];
	double lpIntTable[] = new double[EstatePlanTable.MAX_TABLE];
	
	public void calculate() {
		LoadAssetBean lab = new LoadAssetBean();

		EstatePlanAssets epa = new EstatePlanAssets(scenarioId);
		epa.setScenarioId(scenarioId);
		ArrayList<EPTAssetBean> lpList = epa.getAssetTypes(13);
		
		Iterator<EPTAssetBean> i = lpList.iterator();
		while( i.hasNext()) {
			EPTAssetBean eab = i.next();
			eab.setDbObject();
			double rValue = eab.getRemainingValue();
			if( rValue > 0) {
				AssetSqlBean asb = lab.loadAssetBean(eab);
				double iRate = asb.getAssetIncome();
				double iGrowth = asb.getAssetGrowth();
				double v = rValue;
				for( int k = 0; k < EstatePlanTable.MAX_TABLE; k++) {
					lpValTable[k] += v;
					lpIntTable[k] += v * iRate;
					v += v * iGrowth;
				}
			}
		}
	}
	
	public double getInterestOnCash(int year) {
		return lpIntTable[year];
	}
	
	public double getLpValue(int year) {
		return lpValTable[year];
	}
}
