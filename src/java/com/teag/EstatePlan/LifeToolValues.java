package com.teag.EstatePlan;

import com.teag.estate.LifeTool;

public class LifeToolValues extends EstatePlanSqlBean {
	double assetValue;
	double lifeTrustValue[] = new double[EstatePlanTable.MAX_TABLE];
	
	public void calculate(long toolId) {
		LifeTool life = new LifeTool();
		life.setDbObject();
		life.setId(toolId);
		life.read();
		life.calculate();
		assetValue = life.getFaceValue();

		for(int i = 0; i < finalDeath; i++) {
			lifeTrustValue[i] += assetValue;
		}
	}
	
	public double getFaceValue(int year) {
		return lifeTrustValue[year];
	}
	
	public void intialize() {
		assetValue = 0;
	}
	
}
