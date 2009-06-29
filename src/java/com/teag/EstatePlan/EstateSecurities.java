package com.teag.EstatePlan;

import java.util.ArrayList;
import java.util.Iterator;

import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.LoadAssetBean;

/**
 * @author stay
 * Created on May 30, 2005
 *
 */
public class EstateSecurities extends EstatePlanSqlBean {
	double beginBalance[];
	double endBalance[];
	double excessCashFlow[];
	double growth[];
	
	double weightedGrowth = 0;
	double weightedInterest = 0;
	
	public EstateSecurities() {
		
	}
	
	public void addCashFlow(int year, double value) {
		if( year < finalDeath) {
			excessCashFlow[year] = value;
			endBalance[year] = (beginBalance[year] * (1.0 + weightedGrowth)) + excessCashFlow[year];
			beginBalance[year+1] = endBalance[year];
		}
	}
	
	public double getBeginBalance(int year) {
		return beginBalance[year];
	}
	
	public double getEndBalance(int year) {
		return endBalance[year];
	}
	
	public double getGrowthRate() {
		return weightedGrowth;
	}
	
	public double getInterestRate() {
		return weightedInterest;
	}
	
	public void init() {
		beginBalance = new double[EstatePlanTable.MAX_TABLE];
		endBalance = new double[EstatePlanTable.MAX_TABLE];
		excessCashFlow = new double[EstatePlanTable.MAX_TABLE];
		growth = new double[EstatePlanTable.MAX_TABLE];

		LoadAssetBean lab = new LoadAssetBean();

		EstatePlanAssets epa = new EstatePlanAssets(scenarioId);
		ArrayList<EPTAssetBean> cashList = epa.getAssetTypes(7);	// 7 = Securities
		
		Iterator<EPTAssetBean> i = cashList.iterator();
		
		double totalValue = 0;
		weightedGrowth = 0;
		
		while( i.hasNext()) {
			EPTAssetBean eab = i.next();
			eab.setDbObject();
			double rValue = eab.getRemainingValue();
			if( rValue > 0) {
				AssetSqlBean asb = lab.loadAssetBean(eab);
				weightedGrowth += (rValue * asb.getAssetGrowth());
				weightedInterest += (rValue * asb.getAssetIncome());
				totalValue += rValue;
			}
		}
		if( totalValue == 0 ) {
			weightedGrowth = .035;
			weightedInterest = .02;
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
