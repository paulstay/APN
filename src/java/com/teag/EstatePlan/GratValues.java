/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author Paul Stay
 *
 */
import com.teag.estate.GratTool;
import com.teag.estate.GratTrust;

public class GratValues extends EstatePlanSqlBean {
		
	double gratPayments[] = new double[EstatePlanTable.MAX_TABLE];
	double gratValue[] = new double[EstatePlanTable.MAX_TABLE];
	double clientGiftTax = 0;
	double spouseGiftTax = 0;
	int maxTerm = 0;
	int gratTerm;
	double lifeDeathBenefit;
	double lifeCashValue;

	public void calculateTool(long gratToolId) {
		GratTrust cTrust = null;
		GratTrust sTrust = null;
		
		GratTool gratTool = new GratTool();
		gratTool.setDbObject();
		gratTool.setId( gratToolId);
		gratTool.read();
		gratTool.calculate();
		gratTool.buildAssetList();
		gratTool.buildCFTable();
		gratTool.buildSCTable();
		cTrust = gratTool.getClientTrust();
		sTrust = gratTool.getSpouseTrust();
		
		lifeDeathBenefit += gratTool.getLifeDeathBenefit();
		lifeCashValue += gratTool.getLifeCashValue();
		
		// First add total vaule
		if( gratTool.getNumTrusts() > 1) {
			maxTerm = (int)Math.max(gratTool.getClientTermLength(), gratTool.getSpouseTermLength());
		} else {
			maxTerm = (int)gratTool.getClientTermLength();
		}
		
		int clientStart = 0;
		int spouseStart = 0;
		
		if(gratTool.getClientStartTerm()>1) {
			clientStart = (int)gratTool.getClientStartTerm()-1;
		}
		
		if(gratTool.getSpouseStartTerm()>1) {
			clientStart = (int)gratTool.getSpouseStartTerm()-1;
		}
		
		for( int i = clientStart; i < finalDeath; i++) {
			gratValue[i] += gratTool.getCFTotalValue(i+1);
		}
		
		double annuityPayment = cTrust.getAnnuityPayment();
		for( int i = clientStart; i < gratTool.getClientTermLength() + clientStart; i++) {
			gratPayments[i] += annuityPayment;
		}
		
		clientGiftTax = cTrust.getTaxableGift();
		
		if( sTrust != null) {
			spouseGiftTax = sTrust.getTaxableGift();
			annuityPayment = sTrust.getAnnuityPayment();
			for( int i = spouseStart; i < gratTool.getSpouseTermLength() + spouseStart; i++) { 
				gratPayments[i] += annuityPayment;
			}
		}
	}
	
	public double getGratmaxTerm() {
		return maxTerm;
	}
	
	public double getGratPayment(int year) {
		return gratPayments[year];
	}
	
	public double getGratValue(int year) {
		return gratValue[year];
	}
	
	public double getLifeCashValue() {
		return lifeCashValue;
	}
	
	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}
	
	public void initialize() {
		lifeDeathBenefit = 0;
		lifeCashValue = 0;
		for( int i = 0; i < 30; i++) {
			gratPayments[i] = 0.;
			gratValue[i] = 0;
		}
	}
}
