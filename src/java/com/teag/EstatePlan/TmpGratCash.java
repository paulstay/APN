package com.teag.EstatePlan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.teag.estate.AssetData;
import com.teag.estate.GratTool;
import com.teag.estate.GratTrust;
import com.teag.sc.utils.LineObject;
import com.teag.sc.utils.ScenarioConstants;
import com.zcalc.zCalc;

/**
 * @author Paul Stay Description - Create the cash flow, estate distribution,
 *         and networth categories for the estate plan Date April 7, 2008
 * copyright @ 2008
 * 
 */

public class TmpGratCash extends EstatePlanSqlBean {

	ArrayList<LineObject> assetCashFlow = new ArrayList<LineObject>();
	ArrayList<LineObject> assetNetWorth = new ArrayList<LineObject>();

	LineObject gratPayments;
	LineObject gratToFamily;
	LineObject lifeInsurance;
	LineObject estateDist;

	int nTrusts = 1;
	boolean grantor = true;
	
	double taxableGift;

	public TmpGratCash() {
		// Initialize the line objects that we will use multiple times.
		gratPayments = new LineObject();
		gratPayments.setDescription("Grat Payments");

		gratToFamily = new LineObject();
		gratToFamily.setDescription("Grat Assets to Family");

		lifeInsurance = new LineObject();
		lifeInsurance.setDescription("Life Insurance from Grat");

		estateDist = new LineObject();
		estateDist.setDescription("Grator Retained Annuity Trust");
		
		taxableGift = 0;
	}

	public ArrayList<LineObject> getAssetCashFlow() {
		return assetCashFlow;
	}

	public ArrayList<LineObject> getAssetNetWorth() {
		return assetNetWorth;
	}

	public LineObject getEstateDist() {
		return estateDist;
	}

	public double getFet(int year) {
		double fet;
		zCalc zc = new zCalc();
		zc.StartUp();
		fet = zc.zFETMAXRATE(year, 0);
		zc.ShutDown();
		return fet;
	}

	public LineObject getGratPayments() {
		return gratPayments;
	}

	public LineObject getGratToFamily() {
		return gratToFamily;
	}

	public LineObject getLifeInsurance() {
		return lifeInsurance;
	}

	public int getNTrusts() {
		return nTrusts;
	}

	public double getTaxableGift() {
		return taxableGift;
	}

	public boolean isGrantor() {
		return grantor;
	}

	public void processGrat(GratTool grat, GratTrust g) {
		int year = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		year = cal.get(Calendar.YEAR);
		grat.buildAssetList(); // We need to do this each time, as it
								// reinitializes the data.
		
		double divisor = 1;
		if (grat.getNumTrusts() > 1) {
			divisor = 2;
		}

		// Get assetdata so we can calculate the value of the grat as it grows
		// and make payments.
		// The divisor will be used if there are multiple grats, and we need to
		// divide the asset value in half.
		ArrayList<AssetData> aList = grat.getAssetList();
		for (AssetData a : aList) {
			a.setValue(a.getValue() / divisor); // If we split the grat, then
												// split the asset value
		}
		
		if (g.getStartTerm() <= 1) { // Start grat now.....
			// Get Grat Payment
			double payment = g.getAnnuityPayment();
			for (int i = 0; i < g.getTerm(); i++) {
				gratPayments.addValue(i, payment);
			}

			taxableGift += g.getTaxableGift();
			
			double totalValue[] = new double[EstatePlanTable.MAX_TABLE];
			
			double tValue = grat.getTotalValue()/divisor;
			double tGrowth = grat.getWeightedGrowth();
			double tIncome = grat.getWeightedIncome();

			for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				double yearlyIncome = 0;
				
				tValue += (tValue * tGrowth);			// Yearly Growth
				yearlyIncome = tValue * (tIncome);		// Yearly Income
				double remainingPayment = payment;
				if(i < g.getTerm()) {
					if(yearlyIncome > remainingPayment){
						yearlyIncome -= remainingPayment;		// Subtract out the payment from the income
					} else {
						remainingPayment -= yearlyIncome;
						yearlyIncome = 0;
						tValue -= remainingPayment;				// We need to ubtract some of thepayment from the assets as we
																// Don't have enough income to make the payment.
						remainingPayment = 0;
					}
				}
				
				double tax = 0;

				if (grantor) {
					if (i < g.getTerm()) {
						tax = 0;
					} else {
						tax = tIncome * grat.getIncomeTaxRate();
					}
				}

				if (tax < 0)
					tax = 0;

				tValue += yearlyIncome - tax;
				
				totalValue[i] = tValue - (grat.getRemainderInterest() / divisor * getFet(year));

				if (i < g.getTerm()) {
					estateDist.addValue(i, totalValue[i]);
					lifeInsurance.addValue(i, grat.getLifeDeathBenefit()/2);
				} else {
					gratToFamily.addValue(i, totalValue[i]);
				}
			}
		} else { // Grat needs to calculate the income and growth first, and
					// then add the networth at this point.
			int startTerm = g.getStartTerm();
			double income[] = new double[EstatePlanTable.MAX_TABLE];
			double totalValue[] = new double[EstatePlanTable.MAX_TABLE];
			double tax = 0;

			double aValue = grat.getTotalValue()/2;
			double wGrowth = grat.getWeightedGrowth();
			double wIncome = grat.getWeightedIncome();
			
			for(int i=0; i < startTerm; i++) {
				
				aValue += aValue * wGrowth;			// Add in weighted growth
				double tIncome = aValue * wIncome;


				tax = tIncome * grat.getIncomeTaxRate();
				if(tax < 0)
					tax = 0;
				
				totalValue[i] = aValue + tIncome -tax;
				year++;
				
			}
			// We now have the balance to re-initialize the grat calculation.
			g.setFmv(totalValue[startTerm-2]);
			double dValue = grat.getDiscountedValue()/grat.getTotalValue();
			g.setDiscount(dValue * g.getFmv() );
			g.calculate();

			taxableGift += g.getTaxableGift();
			
			// Add in the grat payment
			double payment = g.getAnnuityPayment();
			for(int i=startTerm-1; i < (startTerm-1) + g.getTerm(); i++) {
				gratPayments.addValue(i,payment);
			}
			
			// Continue the asset and securities values so we can have it grow in the grat and then to the family.
			
			for(int i=startTerm-1; i < ScenarioConstants.MAX_TABLE; i++) {
				double remainingPayment = payment;
				aValue += aValue * wGrowth;			// Add in weighted growth
				double yearlyIncome = aValue * wIncome;

				if (i < (startTerm-1) + g.getTerm()) {
					if(yearlyIncome > remainingPayment){
						yearlyIncome -= remainingPayment;		// Subtract out the payment from the income
					} else {
						remainingPayment -= yearlyIncome;
						yearlyIncome = 0;
						aValue -= remainingPayment;				// We need to ubtract some of thepayment from the assets as we
																// Don't have enough income to make the payment.
						remainingPayment = 0;
					}
				}

				tax = 0;

				if (grantor) {
					if (i < g.getTerm()) {
						tax = 0;
					} else {
						tax = yearlyIncome * grat.getIncomeTaxRate();
					}
				}

				if (tax < 0)
					tax = 0;

				aValue += yearlyIncome - tax;
				
				totalValue[i] = aValue
						- (grat.getRemainderInterest() / divisor * getFet(year));

				if (i < (g.getTerm()-1)+startTerm) {
					estateDist.addValue(i, totalValue[i]);
					lifeInsurance.addValue(i, grat.getLifeDeathBenefit()/2);
				} else {
					gratToFamily.addValue(i, totalValue[i]);
				}
				
				year++;
			}
			// We now need to setup the income for cash flow, and networth.
			LineObject cf = new LineObject();
			cf.setDescription("Before Grat Asset Income");
			LineObject nw = new LineObject();
			nw.setDescription("Before Grat Asset Net worth");
			
			for(int i=0; i < g.getStartTerm()-1; i++){
				cf.setValue(i,income[i]);
				nw.setValue(i,totalValue[i]);
			}
			assetCashFlow.add(cf);
			assetNetWorth.add(nw);
		}

	}

	public void setAssetCashFlow(ArrayList<LineObject> assetCashFlow) {
		this.assetCashFlow = assetCashFlow;
	}

	public void setAssetNetWorth(ArrayList<LineObject> assetNetWorth) {
		this.assetNetWorth = assetNetWorth;
	}

	public void setEstateDist(LineObject estateDist) {
		this.estateDist = estateDist;
	}

	public void setGrantor(boolean grantor) {
		this.grantor = grantor;
	}

	public void setGrat(long toolId) {

		GratTool grat;

		grat = new GratTool();
		grat.setId(toolId);
		grat.setDbObject();
		grat.read();
		grat.calculate();

		ArrayList<GratTrust> gList = new ArrayList<GratTrust>();
		gList.add(grat.getClientTrust());
		if (grat.getSpouseTrust() != null) {
			gList.add(grat.getSpouseTrust());
			nTrusts = grat.getNumTrusts();
		}

		for (GratTrust g : gList) {
			processGrat(grat, g);
		}

	}

	public void setGratPayments(LineObject gratPayments) {
		this.gratPayments = gratPayments;
	}

	public void setGratToFamily(LineObject gratToFamily) {
		this.gratToFamily = gratToFamily;
	}

	public void setLifeInsurance(LineObject lifeInsurance) {
		this.lifeInsurance = lifeInsurance;
	}

	public void setNTrusts(int trusts) {
		nTrusts = trusts;
	}

	public void setTaxableGift(double taxableGift) {
		this.taxableGift = taxableGift;
	}

}
