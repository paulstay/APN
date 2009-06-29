package com.teag.EstatePlan;

/**
 * @author Paul Stay
 * Description - Generate the Grat Cash Flow, if GRAT Term starts after 1 year, we need to provide the 
 * asset line descriptions and values for the years up to the start of the Grat Term.
 * copyright TEAG Software LLC, @2008
 * Date Feb. 19 2008
 *
 */

import java.util.ArrayList;

import com.teag.estate.AssetData;
import com.teag.estate.GratTool;
import com.teag.estate.GratTrust;
import com.teag.sc.utils.LineObject;

public class GratCashFlow extends EstatePlanSqlBean  {

	boolean needAssets;
	GratTool grat;
	ArrayList<LineObject> assetCashFlow = new ArrayList<LineObject>();
	ArrayList<LineObject> assetNetWorth = new ArrayList<LineObject>();
	ArrayList<LineObject> gratPayments = new ArrayList<LineObject>();
	ArrayList<LineObject> gratEstateValue = new ArrayList<LineObject>();
	ArrayList<LineObject> gratToFamily = new ArrayList<LineObject>();
	ArrayList<LineObject> lifeInsurance = new ArrayList<LineObject>();

	int trusts = 1;
	boolean grantor = true;
	int finalDeath;
	
	double lifeDeathBenefit;
	double lifeCashValue;
	
	GratTrust clientGrat;
	GratTrust spouseGrat;
	
	double clientGiftTax = 0;
	double spouseGiftTax = 0;
	
	double discount = 1.0;
	
	int maxTerm;
	
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();

	public ArrayList<LineObject> getAssetCashFlow() {
		return assetCashFlow;
	}
	
	public ArrayList<LineObject> getAssetNetWorth() {
		return assetNetWorth;
	}
	
	public double getClientGiftTax() {
		return clientGiftTax;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public ArrayList<LineObject> getGratEstateValue() {
		return gratEstateValue;
	}

	public ArrayList<LineObject> getGratPayments() {
		return gratPayments;
	}

	public ArrayList<LineObject> getGratToFamily() {
		return gratToFamily;
	}

	public double getLifeCashValue() {
		return lifeCashValue;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public ArrayList<LineObject> getLifeInsurance() {
		return lifeInsurance;
	}

	public int getMaxTerm() {
		return maxTerm;
	}

	public double getSpouseGiftTax() {
		return spouseGiftTax;
	}

	public boolean isNeedAssets() {
		return needAssets;
	}

	public void processAsset(AssetData ad, LineObject nw, LineObject cf, double factor, GratTrust grat){
		nw.setDescription(ad.getName());
		cf.setDescription(ad.getName() + " income");
		double value = ad.getValue()* factor;
		double income = ad.getIncome();
		double growth = ad.getGrowth();
		for(int i=0; i < grat.getStartTerm(); i++) {
			cf.addValue(i,value * income);
			value += value * growth;
			nw.addValue(i, value);
		}
	}

	public void processLife(GratTrust grat, double lifeDeathBenefit, double lifeCashValue) {
		LineObject life = new LineObject();
		for(int i=grat.getStartTerm(); i < (grat.getTerm()+ grat.getStartTerm()-1); i++) {
			life.addValue(i,lifeDeathBenefit);
		}
		life.addValue((grat.getTerm() + grat.getStartTerm()-1), lifeCashValue + lifeDeathBenefit);

		// Add Life insurance!
		lifeInsurance.add(life);
	}

	public void setAssetCashFlow(ArrayList<LineObject> assetCashFlow) {
		this.assetCashFlow = assetCashFlow;
	}

	public void setAssetNetWorth(ArrayList<LineObject> assetNetWorth) {
		this.assetNetWorth = assetNetWorth;
	}

	public void setClientGiftTax(double clientGiftTax) {
		this.clientGiftTax = clientGiftTax;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	/**
	 * Create the GRAT, and read the setup data from the database
	 * @param toolId
	 */
	public void setGrat(int toolId) {
		
		grat = new GratTool();
		grat.setId(toolId);
		grat.setDbObject();
		grat.read();
		// we call calculate here, but based on the start of the term we may need to adjust this.
		grat.calculate();
		grat.buildAssetList();
		
		discount = grat.getDiscountedValue() /grat.getTotalValue();
		
		assetList = grat.getAssetList();
		trusts = grat.getNumTrusts();
		
		clientGrat = grat.getClientTrust();
		spouseGrat = grat.getSpouseTrust();
		
		boolean cAsset = false;				// Check this grat, 
		if(grat.getClientStartTerm()>1) {
			cAsset = true;
			needAssets = true;
		}
		
		if(trusts > 1 & grat.getSpouseStartTerm()>1) {
			cAsset = true;
			needAssets = true;
		}
		
		if(trusts > 1)
			maxTerm = Math.max(clientGrat.getTerm(),spouseGrat.getTerm());
		else
			maxTerm = clientGrat.getTerm();
		
		// If we need to use a different startTerm we need to calculate this
		// differently

		if(!cAsset){				
			grat.calculate();
			grat.buildAssetList();
			grat.buildCFTable();
			grat.buildSCTable();
			clientGrat = grat.getClientTrust();
			spouseGrat = grat.getSpouseTrust();
			
			LineObject lineObject = new LineObject();
			lineObject.setDescription("Client GRAT");
			lineObject.setPrctTaxable(0);
			
			for(int i=0; i < maxTerm; i++){
				lineObject.setValue(i, grat.getCFTotalValue(i+1));
			}

			
			LineObject family = new LineObject();
			
			for(int i=maxTerm; i < finalDeath; i++) {
				family.setValue(i, grat.getCFTotalValue(i+1));
			}
			gratToFamily.add(family);
			
			
			gratEstateValue.add(lineObject);
			LineObject lObj = new LineObject();
			lObj.setDescription("Grat Payment");
			double annuityPayment = clientGrat.getAnnuityPayment();
			
			LineObject life = new LineObject();
			
			for(int i=0; i < clientGrat.getTerm(); i++) { 
				lObj.setValue(i,annuityPayment);
				life.addValue(i,grat.getLifeDeathBenefit());
			}
			
			// Add the cash value for the life insurance
			life.addValue(clientGrat.getTerm()-1, grat.getLifeCashValue());
			lifeInsurance.add(life);
			
			clientGiftTax += clientGrat.getTaxableGift();
			
			if(spouseGrat != null) {
				spouseGiftTax += spouseGrat.getTaxableGift();
				annuityPayment = spouseGrat.getAnnuityPayment();
				for(int i=0; i < spouseGrat.getTerm(); i++) {
					lObj.addValue(i,annuityPayment);		// We add the value here to use the same 
															// Line Object
				}
			}
			gratPayments.add(lObj);
			
		} else {				// Need to process assets before term starts
			// First process life insurance
			processLife(clientGrat, grat.getLifeDeathBenefit(), grat.getLifeCashValue());
			for(AssetData ad : assetList) {
				LineObject nw = new LineObject();
				LineObject cf = new LineObject();
				if(trusts == 1){
					processAsset(ad, nw, cf, 1.0, clientGrat);
				} else {
					processAsset(ad, nw, cf, .5, clientGrat);
					processAsset(ad, nw, cf, .5, spouseGrat);
				}
				assetCashFlow.add(cf);
				assetNetWorth.add(nw);
			}
		}
	}

	public void setGratEstateValue(ArrayList<LineObject> gratEstateValue) {
		this.gratEstateValue = gratEstateValue;
	}

	public void setGratPayments(ArrayList<LineObject> gratPayments) {
		this.gratPayments = gratPayments;
	}

	public void setGratToFamily(ArrayList<LineObject> gratToFamily) {
		this.gratToFamily = gratToFamily;
	}

	public void setLifeCashValue(double lifeCashValue) {
		this.lifeCashValue = lifeCashValue;
	}

	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}

	public void setLifeInsurance(ArrayList<LineObject> lifeInsurance) {
		this.lifeInsurance = lifeInsurance;
	}

	public void setMaxTerm(int maxTerm) {
		this.maxTerm = maxTerm;
	}

	public void setNeedAssets(boolean needAssets) {
		this.needAssets = needAssets;
	}

	public void setSpouseGiftTax(double spouseGiftTax) {
		this.spouseGiftTax = spouseGiftTax;
	}
		
}
