package com.teag.EstatePlan;

import java.util.ArrayList;
import java.util.Iterator;

import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.LoadAssetBean;
import com.teag.estate.TClatTool;
import com.zcalc.zCalc;


public class TClatValues extends EstatePlanSqlBean {
	double tClatValue[] = new double[EstatePlanTable.MAX_TABLE];
	double remainderInterest[] = new double[EstatePlanTable.MAX_TABLE];
	long toolId;
	double annuity;
	double afrRate;
	double payoutRate;
	int term;
	String inclusive;
	
	
	public void calculate() {

		if( !inclusive.equals("Y")) {
			EstatePlanAssets epa = new EstatePlanAssets(scenarioId);
			epa.finalDeath = finalDeath;
			ArrayList<EPTAssetBean> assets = epa.getAllAssets();

			LoadAssetBean lab = new LoadAssetBean();
			Iterator<EPTAssetBean> i = assets.iterator();
			while(i.hasNext()) {
				EPTAssetBean eab = i.next();
				if( eab.getTclatId() > 0) {
					AssetSqlBean asb = lab.loadAssetBean(eab);
					double rValue = eab.getRemainingValue();
					double rGrowth = asb.getAssetGrowth();
					double pValue = rValue;
					for( int j = 0; j < finalDeath; j++ ) {
						pValue = rValue * Math.pow((1 + rGrowth), j);
						tClatValue[j] += pValue;
					}
				}
			}
		}
		// Calculate Remainder Interest
		zCalc zc = new zCalc();
		zc.StartUp();
			for( int j = 0; j < finalDeath; j++) {
				double ann = zc.zANNTERMLIFE(tClatValue[j]* ( .99), payoutRate, afrRate, term, 0,0, 0,0,0,0,4,0,0,0,0,0);
				double p = ann/tClatValue[j];
				double rInt = tClatValue[j] * ( 1 - p);
				remainderInterest[j] = rInt;
			}
		zc.ShutDown();
		
		
	}
	
	public void calculateTool(long toolId) {
		this.toolId = toolId;
		TClatTool tool = new TClatTool();
		tool.setDbObject();
		tool.setId(toolId);
		tool.read();
		tool.setScenarioId(scenarioId);
		tool.calculate();
		annuity = tool.getAnnuity();
		afrRate = tool.getAfrRate();
		payoutRate = tool.getPaymentRate();
		term = tool.getTerm();
		inclusive = tool.getInclusive();
		
	}

	public double getTClatValue(int year) {
		return tClatValue[year];
	}
	
	public void setTClatValue(double value, int year) {
		tClatValue[year] = value;
	}
	
}
