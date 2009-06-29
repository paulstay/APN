package com.teag.EstatePlan;

import java.util.ArrayList;
import com.teag.estate.AssetData;
import com.teag.estate.GratTool;
import com.teag.estate.GratTrust;
import com.teag.sc.utils.LineObject;
import com.teag.sc.utils.ScenarioConstants;
import com.zcalc.zCalc;

/**
 * @author Paul Stay Description - Create the cash flow, estate distribution,
 *         and networth categories for the estate plan Date April 7, 2008
 *         copyright @ 2008
 * 
 */

public class GratCashFlow2 extends EstatePlanSqlBean {

	ArrayList<LineObject> assetCashFlow = new ArrayList<LineObject>();
	ArrayList<LineObject> assetNetWorth = new ArrayList<LineObject>();

	LineObject gratPayments;
	LineObject gratToFamily;
	LineObject lifeInsurance;
	LineObject estateDist;
	LineObject cashFlow = null;
	LineObject netWorth = null;

	int nTrusts = 1;
	boolean grantor = true;

	double taxableGift;

	public GratCashFlow2() {
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

		grat.buildAssetList(); // We need to do this each time, as it
		// reinitializes the data.

		double assetValue = 0;
		double secValue = 0;
		double aGrowth = 0;
		double aIncome = 0;
		double sGrowth = .05;
		double sIncome = .02;

		ArrayList<AssetData> assetList = grat.getAssetList();

		// Initialize the values
		for (AssetData a : assetList) {
			if (a.getAssetType() == AssetData.SECURITY) {
				secValue += a.getValue();
				sGrowth = Math.max(sGrowth, a.getGrowth());
				sIncome = Math.max(sIncome, a.getIncome());
			} else {
				assetValue += a.getValue();
				aIncome = Math.max(aIncome, a.getIncome());
				aGrowth = Math.max(aGrowth, a.getGrowth());
			}
		}

		// If we have more than one grat trust for this tool, we divide the
		// the asset values.
		if (grat.getNumTrusts() > 1) {
			assetValue = assetValue / 2.0;
			secValue = secValue / 2.0;
		}

		for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
			double sInc = secValue * sIncome;
			double sGrw = secValue * sGrowth;
			double aInc = assetValue * aIncome;
			double aGrw = assetValue * aGrowth;
			double grat1Payment = gratPayment(g, i + 1);
			double tax = 0;

			// Add the total paymet to the line object
			gratPayments.addValue(i, grat1Payment);

			// The tax calculation here is a bit difficult
			if (grat1Payment == 0) { // If we have a grat payment is zero we tax
					tax += (aInc + sInc) * grat.getIncomeTaxRate(); 
			}

			// Now we need to add to the line objects
			int state = calcGratState(grat, i);
			if (state < 0) {
				if (cashFlow == null) {
					cashFlow = new LineObject();
					cashFlow.setDescription("Before Grat Distribution");
				}
				cashFlow.setValue(i, aInc + sInc);
				aInc = 0; // We add this to the cash flow instead
				sInc = 0;

				if (netWorth == null) {
					netWorth = new LineObject();
					netWorth.setDescription("Before Grat Net Worth");
				}
				netWorth.addValue(i, assetValue);
			}

			secValue = secValue + sGrw + sInc + aInc - tax - grat1Payment;
			assetValue += aGrw;

			// If the securities are less than zero, then we need
			// to subtract the amount from the asset value as we need to dip
			// into the asset value for the payment.
			if (secValue < 0) {
				assetValue -= secValue;
				secValue = 0;
			}
			if (state == 0) {
				estateDist.addValue(i, assetValue);
				lifeInsurance.addValue(i, grat.getLifeDeathBenefit());
			}

			if (state == 1) {
				gratToFamily.addValue(i, secValue + assetValue);
			}
		}

		if (cashFlow != null)
			assetCashFlow.add(cashFlow);
		if (netWorth != null)
			assetNetWorth.add(netWorth);
	}

	/**
	 * calculate the grat payment for a client particular year adjusting for the
	 * start term, term length and if there are two trusts. From Excel
	 * Spreadshhet Grat - CF worksheet.
	 * 
	 * @param year
	 * @return gratPayment
	 */
	public double gratPayment(GratTrust g, int trustYear) {
		double gratPayment = 0;
		if (trustYear >= g.getStartTerm()) {
			if (trustYear < (g.getStartTerm() + g.getTerm())) {
				gratPayment = g.getAnnuityPayment();
			}
		}
		return gratPayment;
	}

	// Calculate the state of the grat, -1 is before, 0 is during, and 1 is
	// after
	public int calcGratState(GratTool grat, int year) {
		int termLength = (int) grat.getClientTermLength();
		int startTerm = (int) grat.getClientStartTerm() - 1;

		int state = -1;

		if (year >= startTerm) {
			if (year < (startTerm + termLength)) {
				state = 0;
			} else
				state = 1;
		}
		return state;
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
