package com.teag.estate;

/**
 * @author stay
 * Created on Apr 16, 2005
 * Description - Create the Retirement Plan tables with minimum required distributions
 * table consists of the following fields:
 * 		year, age, beg balance, plan growth, plan dist, yr end bal, Retirement Plan taxes at death, % to taxes, plan & trust money, total taxes, net to family
 * The above table is computed and used to generate the PDF docment.
 */

import java.util.Calendar;

import com.teag.util.Function;
import com.zcalc.zCalc;

public class RpmTable {
	
	// Assumptions for Calculations (May need to adjust later based on how laws change.
	public final static double CAPITAL_GAINS_PRE = .15;	 // for years <= 2010
	public final static double CAPITAL_GAINS_POST = .20; // for years > 2010
	private int term;		// Term that we will be looking at 
	
	private int clientAge;		// Client Age required for Min Distribution Calculations
	private int spouseAge;		// Spouse Age required for Min Distribution Calculations (dramaticaly different if difference in age is > 15 years
	
	private double lifeInsPremium;			// Life Insurance Premium each year		
	private double lifeInsDeathBenefit;		// Life Insurance available at second death to Family.
	// Securities for transfer out of Retirement Plan
	// As we draw money out of the retirement plan we assume that the money will be put into a trust, for
	// calculation purposes.
	private double secTurnoverRate;
	
	private double secGrowthRate;
	private double secDivRate;
	
	// Retirement Plan
	private double planValue;		// Total dollars from all of the added qualified plans
	
	private double planGrowth;		// weighted growth from all qualified plans
	// State Income Tax rate
	private double stateIncomeTaxRate;
	
	// Retirement Plan Table 
	double[][] rpTable;
	double[][] rpmTable;

	zCalc zc = new zCalc();
	
	boolean isSingle = false;
	
	public RpmTable() {
	
	}
	
	public void calculate() {
		rpTable = new double[60][12];
		int cAge = clientAge;
		int sAge = spouseAge;
	
		if(sAge == 0) 
			isSingle = true;
		
		double trustValue = 0;
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		for( int i = 0; i < 60; i++) {
			double pastBalance = 0;
			if( i == 0) {
				pastBalance = planValue;
			} else {
				pastBalance = rpTable[i-1][5];
			}
			// The following calculates the Retirement Plan Table
			rpTable[i][0] = i+1;
			rpTable[i][1] = year;
			rpTable[i][2] = pastBalance;
			rpTable[i][3] = rpTable[i][2] * planGrowth;
			rpTable[i][4] = getMinReqDist(rpTable[i][2],cAge, sAge);
			rpTable[i][5] = pastBalance + rpTable[i][3] - rpTable[i][4];
			
			// The following calculates the Retirement plan taxes atfter both deaths and the percentage to the Gov.
			rpTable[i][6] = getRetirementTaxes(rpTable[i][5], year);
			rpTable[i][7] = rpTable[i][6]/rpTable[i][5];
			
			// The following calculates the distribution from the plan going into the estate.
			if( rpTable[i][4] > 0) {
				// Calculate taxes from withdraw
				double amountToTrust = trustValue + rpTable[i][4] * (1 - getCombinedTaxRate(year));
				trustValue = amountToTrust + getNetDividend(amountToTrust, year) + getNetGrowth(amountToTrust, year);
			}
			

			rpTable[i][8] = trustValue; 						// Accumulated in Turst
			rpTable[i][9] = trustValue + rpTable[i][5];			// Total Plan and Trust			
			rpTable[i][10] = rpTable[i][6] + 					// Total taxes
				trustValue * getEstateTaxRate(year);			
			rpTable[i][11] = rpTable[i][9] - rpTable[i][10];	// NetTo Family
			
			// Update values
			cAge++;
			if( !isSingle)
				sAge++;
			year++;
		}
	}
	
	public void calculateRpm() {
		double planMaxValue = Function.PMT(planValue, planGrowth,term);
		
		rpmTable = new double[60][12];
		double trustValue = 0;
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		for( int i = 0; i < 60; i++) {
			double pastBalance = 0;
			if( i == 0) {
				pastBalance = planValue;
			} else {
				pastBalance = rpmTable[i-1][5];
			}
			
			// The following calculates the Retirement Plan Table
			rpmTable[i][0] = i+1;
			rpmTable[i][1] = year;
			rpmTable[i][2] = pastBalance;
			rpmTable[i][3] = rpmTable[i][2] * planGrowth;

			if( i < term ) 
				rpmTable[i][4] = planMaxValue;
			else
				rpmTable[i][4] = 0;
			
			
			rpmTable[i][5] = pastBalance + rpmTable[i][3] - rpmTable[i][4];
			
			if( rpmTable[i][5] < 0)
				rpmTable[i][5] = 0;
			
			// The following calculates the Retirement plan taxes atfter both deaths and the percentage to the Gov.
			rpmTable[i][7] = rpmTable[i][4] * ( 1 - this.getCombinedTaxRate(year));
			if( i < term) 
				rpmTable[i][8] = lifeInsPremium;
			else
				rpmTable[i][8] = 0;
			
			rpmTable[i][10] = lifeInsDeathBenefit;
			
			double value = rpmTable[i][5] + rpmTable[i][7];
			double dist = rpmTable[i][7];
			
			double fet = value * this.getFederalEstateTaxRate(year);
			double set = value * this.getStateEstateTaxRate(year);
			double sit = this.getStateIncomeTaxRate() * (value - dist);
			double fit = (value - dist - fet - sit)*this.getFederalTaxRate(year);
			
			if( fit < 0)
				fit = 0;
			
			rpmTable[i][6] = fet + set + fit + sit; 
			
			// The following calculates the distribution from the plan going into the estate.
			if( rpmTable[i][4] > 1) {
				// Calculate taxes from withdraw
				double amountToTrust = trustValue + rpmTable[i][7] - rpmTable[i][8];
				trustValue = amountToTrust + getNetDividend(amountToTrust, year) + getNetGrowth(amountToTrust, year);
			} else {
				trustValue = trustValue + getNetDividend(trustValue, year) + getNetGrowth(trustValue, year);
			}

			rpmTable[i][9] = trustValue;
			rpmTable[i][11] = rpmTable[i][10] + rpmTable[i][9] + rpmTable[i][5] - rpmTable[i][6];
			
			// Update values
			year++;
		}
	}

	private double getCapitalGainsRate(int year) {
		if( year <= 2010)
			return CAPITAL_GAINS_PRE;
		return CAPITAL_GAINS_POST;
	}
	
	// Getters and Setters for tables.....
	public int getClientAge() {
		return clientAge;
	}
	
	private double getCombinedTaxRate(int year) {
		if( year <= 2010) {
			return getStateIncomeTaxRate() * .65 + getFederalTaxRate(year);
		}
		return getStateIncomeTaxRate() * .604 + getFederalTaxRate(year);
	}

	
	private double getEstateTaxRate(int year) {
		return getFederalEstateTaxRate(year) + getStateEstateTaxRate(year);
	}
	
	private double getFederalEstateTaxRate(int year) {
		double f;
        /*
		zc.StartUp();
		f = zc.zFETMAXRATE(year,0);
		zc.ShutDown();
        */
        f = FederalEstateTax.getFET(year);
		if(year < 2011) {
			return f;
		}
		return f - .16;
	}
	
	private double getFederalTaxRate(int year) {
		if( year <= 2010) {
			return .35;
		}
		return .396;
	}
	
	public double getLifeInsDeathBenefit() {
		return lifeInsDeathBenefit;
	}
	
	public double getLifeInsPremium() {
		return lifeInsPremium;
	}

	private synchronized double getMinReqDist(double value, int cAge, int sAge) {
		if( cAge < 70) {
			return 0.0;
		}
		zc.StartUp();
		double zFactor = zc.zMINDIS(cAge,sAge,0,0,0,0,0,0,0,0);
		zc.ShutDown();

		if( zFactor > 0) {
			return value/zFactor;
		}
		return 0.0;
	}
	
	public double getNetDividend(double value, int year) {
		return (value * getSecDivRate()) * ( 1- getCombinedTaxRate(year));
	}
	
	public double getNetGrowth(double value, int year) {
		return (value * getSecGrowthRate()) * ( 1 - (secTurnoverRate * 
				getCombinedTaxRate(year) + (1 - secTurnoverRate) * getCapitalGainsRate(year)));
	}
	
	public double getPlanGrowth() {
		return planGrowth;
	}
	public double getPlanValue() {
		return planValue;
	}
	private double getRetirementTaxes(double value, int year) {
		double fet = getFederalEstateTaxRate(year);
		double set = getStateEstateTaxRate(year);
		double fitr = getFederalTaxRate(year);
		double sitr = getStateIncomeTaxRate();

		double sValue = value * (fet + set) + (value * (1 - fet) - value * sitr)*fitr + sitr*value; 
		return sValue;
	}
	public double[][] getRpmTable() {
		return rpmTable;
	}
	public double[][] getRpTable() {
		return rpTable;
	}
	public double getSecDivRate() {
		return secDivRate;
	}
	public double getSecGrowthRate() {
		return secGrowthRate;
	}
	public double getSecTurnoverRate() {
		return secTurnoverRate;
	}
	public int getSpouseAge() {
		return spouseAge;
	}
	private double getStateEstateTaxRate(int year) {
		if( year < 2011)
			return 0;
		return .16;
	}
	public double getStateIncomeTaxRate() {
		return stateIncomeTaxRate;
	}
	public int getTerm() {
		return term;
	}
	public void setClientAge(int age) {
		clientAge = age;
	}
	public void setLifeInsDeathBenefit(double lifeInsDeathBenefit) {
		this.lifeInsDeathBenefit = lifeInsDeathBenefit;
	}
	public void setLifeInsPremium(double lifeInsPremium) {
		this.lifeInsPremium = lifeInsPremium;
	}
	public void setPlanGrowth(double planGrowth) {
		this.planGrowth = planGrowth;
	}
	public void setPlanValue(double planValue) {
		this.planValue = planValue;
	}
	public void setSecDivRate(double secDivRate) {
		this.secDivRate = secDivRate;
	}
	public void setSecGrowthRate(double secGrowthRate) {
		this.secGrowthRate = secGrowthRate;
	}
	public void setSecTurnoverRate(double secTurnoverRate) {
		this.secTurnoverRate = secTurnoverRate;
	}
	public void setSpouseAge(int age) {
		spouseAge = age;
	}
	public void setStateIncomeTaxRate(double stateIncomeTaxRate) {
		this.stateIncomeTaxRate = stateIncomeTaxRate;
	}
	public void setTerm(int term) {
		this.term = term;
	}
}
