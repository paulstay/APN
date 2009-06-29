/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author stay
 *
 */
import com.teag.estate.CrummeyTool;
public class CrummeyValues extends EstatePlanSqlBean {
		double annualGift;
		double lifeDeathBenefit;
		double lifePremium;
		int term;
		boolean useMgt;
	
		public void calculate(long toolId) {
			CrummeyTool cr = new CrummeyTool();
			cr.setDbObject();
			cr.setId(toolId);
			cr.read();
			cr.calculate();
			annualGift = cr.getAnnualGift();
			lifeDeathBenefit = cr.getLifeDeathBenefit();
			lifePremium = cr.getLifePremium();
			term = (int)cr.getTerm();
			useMgt = cr.isWithMgt();
		}
		
		public double getAnnualGift() {
			return annualGift;
		}
		
		public double getDeathBenefit(){
			return lifeDeathBenefit;
		}
		
		public double getLifePremium() {
			return lifePremium;
		}
		
		public int getTerm() {
			return term;
		}
		
		public void initialize(){
			
		}
}

