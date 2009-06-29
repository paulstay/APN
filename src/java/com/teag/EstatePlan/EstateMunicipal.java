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
public class EstateMunicipal extends EstatePlanSqlBean {
	double beginBalance[];
	double endBalance[];
	double excessCashFlow[];
	
	double weightedGrowth = 0;
	double weightedInterest = 0;
	
	public void addCashFlow(int year, double value) {
		if( year < finalDeath) {
			excessCashFlow[year] = value;
			endBalance[year] = (beginBalance[year] + excessCashFlow[year]) * (1.0 + weightedGrowth);
			beginBalance[year+1] = endBalance[year];
		}
	}
	
	public double getBeginBalance(int year) {
		return beginBalance[year];
	}
	
	public double getEndBalance(int year) {
		return endBalance[year];
	}
	
	public double getGrowth() {
		return weightedGrowth;
	}
	
	public double getInterestRate() {
		return weightedInterest;
	}
	
	public void init() {
		beginBalance = new double[finalDeath + 1];
		endBalance = new double[finalDeath+ 1];
		excessCashFlow = new double[finalDeath +1];
		LoadAssetBean lab = new LoadAssetBean();

		EstatePlanAssets epa = new EstatePlanAssets(scenarioId);
		ArrayList<EPTAssetBean> cashList = epa.getAssetTypes(1);	// 1 = Municipal Bonds
		
		Iterator<EPTAssetBean> i = cashList.iterator();
		
		double totalValue = 0;
		weightedGrowth = 0;
		
		while( i.hasNext()) {
			EPTAssetBean eab = i.next();
			eab.setDbObject();
			double rValue = eab.getRemainingValue();
			if( rValue > 0) {
				AssetSqlBean asb = lab.loadAssetBean(eab);
				double  iRate = asb.getAssetIncome();
				weightedGrowth += (rValue * asb.getAssetGrowth());
				weightedInterest += (rValue * iRate);
				totalValue += rValue;
			}
		}
		if( totalValue == 0) {
			weightedGrowth = .05;
			weightedInterest = .025;
		} else {
			weightedGrowth = weightedGrowth/totalValue;
			weightedInterest = weightedInterest/ totalValue;
		}
		
		setInitialBalance(totalValue);
	}
	
	public void setInitialBalance(double value) {
		beginBalance[0] = value;
		endBalance[0] = 0.0;
		excessCashFlow[0] = 0;
	}
}
