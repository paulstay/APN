package com.teag.estate;

/**
 * @author Paul Stay
 * Copyright @  Advance Estate Strategies
 * October 2008
 * 
 * Charitable Remainder Unitrust Tool
 */

import java.util.Date;
import java.util.HashMap;

import com.zcalc.zCalc;

public class CrtTool extends EstatePlanningTool {

	public static final String ID = "ID";
	public static final String TOOL_TYPE = "TOOL_TYPE";
	public static final String TERM = "TERM";
	public static final String START_TERM = "START_TERM";
	public static final String ESTATE_TAX_RATE = "ESTATE_TAX_RATE";
	public static final String CAPITAL_GAINS_RATE = "CAPITAL_GAINS_RATE";
	public static final String TAX_RATE = "TAX_RATE";
	public static final String AFR_RATE = "AFR_RATE";
	public static final String AFR_DATE = "AFR_DATE";
	public static final String FREQUENCY = "FREQUECY";
	public static final String UNILAG = "UNILAG";
	public static final String PAYOUT_RATE  = "PAYOUT_RATE";
	public static final String PAYOUT_INCOME = "PAYOUT_INCOME";
	public static final String PAYOUT_GROWTH = "PAYOUT_GROWTH";
	public static final String PAYOUT_OPTION = "PAYOUT_OPTION";
	public static final String INVESTMENT_RETURN = "INVESTMENT_RETURN";
	public static final String ADJUSTED_GROSS_INCOME = "ADJUSTED_GROSS_INCOME";
	public static final String LIFE_PREMIUM = "LIFE_PREMIUM";
	public static final String LIFE_DEATH_BENEFIT = "LIFE_DEATH_BENEFIT";
	public static final String FINAL_DEATH = "FINAL_DEATH";
	public static final String UUID = "UUID";

	public static final String tableName = "CRT_TOOL";
	
	long id;
	String toolType;
	int term;
	int startTerm;
	double estateTaxRate;
	double capitalGainsRate;
	double taxRate;
	double irsRate;
	Date irsDate;
	int frequency = 1;		// Assume annual payments 12 = monthly, 4 quarterly, 2 semi-annual
	double payoutRate;
	double payoutIncome;
	double payoutGrowth;
	String payoutOption;
	double investmentReturn;
	double adjustedGrossIncome;
	double lifePremium;
	double lifeDeathBenefit;
	double finalDeath;
	String uuid;
	
	int clientAge;			// Needed for max crut calculations
	int spouseAge;			// Needed for max crut calculations (optional)
	int uniLag = 3;			// Number of months after evaluation to start term
	
	double lifeExpectancy;	// Calculated
	
	double assetValue;
	double assetBasis;
	
	double assetLiability;
	double assetGrowth;
	double assetIncome;
	
	double agiLimitation = .3;	// 30% allowable
	double maxPayout;
	double minPayout = .05;		// This should always be .05 or 5%
	
	double charitableDeduction;
	double charitableDeductionFactor;
	
	HashMap<String,Object> account;
	
	ToolAssetData assetData[];
	
	long scenarioiId;
	
	// Tables for calculations and trust
	double[][] crtTable;
	double[][] outRightSale;
	
	// The following are used for output
	double netSpendableFamily;
	double netOutIncome;
	double netCrtIncome;
	double totalOut;
	double totalCrt;
	double netToFamilyCrt;
	double netToFamilyOut;	// Outright Sale net to family after LE.
	double incomeTaxDeduction;
	double netToCharity;
	
	@Override
	public void calculate() {

		ToolAssetDistribution tad = new ToolAssetDistribution();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());
		tad.calculate();
		
		assetData = tad.getToolAssetData();
		assetValue = tad.getTotalValue();
		assetGrowth = tad.getAverageGrowth();
		assetIncome = tad.getAverageIncome();
		assetLiability = tad.getLiability();
		assetBasis = tad.getBasis();
	
		zCalc zc = new zCalc();
		zc.StartUp();
			charitableDeductionFactor = zc.zUNILIFE(2, payoutRate, clientAge, spouseAge, 0, 0, 0, irsRate, frequency, uniLag, 0, 0, 0);
			maxPayout = zc.zCRUTMAX(0, clientAge, spouseAge, 0, 0, 0, irsRate, frequency, uniLag, 0, 0, 0);
			lifeExpectancy = zc.zLE(clientAge, spouseAge, 0, 0, 0, 0, 0);
		zc.ShutDown();
		
		charitableDeduction = (assetValue - assetLiability) * charitableDeductionFactor;

		buildCrtTable();
		calcOutRightSale();
		calcFinal();
	}

	/**
	 * Calculate the initial maximum payout,and life expectancy depending on 
	 * the client age(s), the irsRate used.
	 */
	public void initialCalc(){
		
		zCalc zc = new zCalc();
		zc.StartUp();
		maxPayout = zc.zCRUTMAX(0, clientAge, spouseAge, 0, 0, 0, irsRate, frequency, uniLag, 0, 0, 0);
		lifeExpectancy = zc.zLE(clientAge, spouseAge, 0, 0, 0, 0, 0);
		zc.ShutDown();
	}

	/**
	 * Build CRT table with life insurance, taxes, charitable deduction, and annual payment.
	 */
	public void buildCrtTable() {
		// Extend the table to 5 years after the life expectancy.
		// [0] Payment
		// [1] Charitable Deduction based on AGI
		// [2] Insurance Premiums
		// [3] Income Tax
		// [4] Net Spendable
		// [5] Trust Balance
		int tableLength = (int)(lifeExpectancy + 6);
		
		if( tableLength < finalDeath){
			tableLength = (int)finalDeath + 1;
		}

		netSpendableFamily = 0;
		crtTable = new double[tableLength][6];
		
		double cDed = charitableDeduction;
		
		// Initialize the table at row 0, Only the Balance is necessary
		crtTable[0][5] = assetValue - assetLiability;
		
		double crtTaxRate = ((payoutGrowth/payoutRate)* capitalGainsRate) +
			((payoutIncome/payoutRate) * taxRate);
		
		/*
		 * Here we calculate the annual growth and income in the Charitable Trust, for now we are
		 * assuming annual payments, as it is easier to calculate. In the future we might want to 
		 * calculate it based on the frequency of the payments. 
		 * For instance calculate each month growth, income and payment at the appropriate time. 
		 * There might be a good algorithm for this and we should look at it. 
		 * ZCalc has one that we might use zUniBen();
		 */
		
		for(int i=1; i < tableLength; i++) {
			crtTable[i][0] = crtTable[i-1][5] * payoutRate;	// Payout from Charity
			// First calculate the charitable deduction we can take.
			if(i <=6 && cDed > 0) {
				double agi = crtTable[i][0] + adjustedGrossIncome;
				double deductionAllowed = Math.round(Math.min(agi* agiLimitation,cDed));
				cDed = Math.max(cDed - deductionAllowed, 0);
				crtTable[i][1] = deductionAllowed;
			} else {
				crtTable[i][1] = 0;
			}
			crtTable[i][2] = lifePremium;
			crtTable[i][3] = (crtTable[i][0] - crtTable[i][1])* crtTaxRate;	// Income Tax
			// Netspendable calculations
			// WEven if tax is negative, we add that in as it reflects the net spendable from the agi
			crtTable[i][4] = crtTable[i][0] - crtTable[i][3] - crtTable[i][2]; 

			netSpendableFamily += crtTable[i][4];
			
			// Subtract the payment from the trust, and increase the value by the investment return.
			crtTable[i][5] = ((crtTable[i-1][5]) * (1 + investmentReturn)) - crtTable[i][0];
		}
	}

	public void calcOutRightSale(){

		int tableLength = (int)(lifeExpectancy+6);
	
		if( tableLength < finalDeath){
			tableLength = (int)finalDeath + 1;
		}
		/*
		 * Use the mix of growth and income to calculate the effective tax rate.
		 * This is the percentage of growth * the capitalGainsRate +
		 * the percentage of Income * the income Tax rate. The key here is that the percentage
		 * of growth + percentage of INcome equals 100%, so it give a mixture of the two.
		 */
		double incomeTaxRate = (payoutGrowth/payoutRate) * capitalGainsRate +
			(payoutIncome/payoutRate) * taxRate; 

		outRightSale = new double[tableLength][4];
		
		// Subtract out the sale of the business, and capital gains tax
		// note that the second line subtracts the capital gains.
		outRightSale[0][3] = (assetValue - assetLiability);
		outRightSale[0][3] -= (assetValue - assetBasis) * capitalGainsRate;
		
		for(int i=1; i < tableLength; i++) {
			outRightSale[i][0] = outRightSale[i-1][3] * investmentReturn;
			//outRightSale[i][1] = outRightSale[i][0] * incomeTaxRate;
			outRightSale[i][1] = (outRightSale[i-1][3]* payoutGrowth * capitalGainsRate) +
				(outRightSale[i-1][3]* payoutIncome * taxRate); 
			
			outRightSale[i][2] = outRightSale[i-1][3] * (payoutRate * (1-incomeTaxRate));
			
			double balance = (outRightSale[i-1][3] * (1 + investmentReturn)) -
				outRightSale[i][1] - outRightSale[i][2];
			
			if(balance < 0.0) {
				outRightSale[i][3] = 0.0;
			} else {
				outRightSale[i][3] = balance;
			}
		}
	}
	
	public void calcFinal() {
		
		int tableLength = (int)(lifeExpectancy+6);
		
		if( tableLength < finalDeath){
			tableLength = (int)finalDeath + 1;
		}

		incomeTaxDeduction = 0;
		netToFamilyOut = 0;
		netToFamilyCrt = 0;
		netToCharity = 0;
		
		for(int i=0; i < tableLength -5; i++) {
			incomeTaxDeduction += crtTable[i][1];
			netOutIncome += outRightSale[i][2];
			netCrtIncome += crtTable[i][4];
		}
		
		netToFamilyOut = outRightSale[tableLength-1][3] * (1 - estateTaxRate);
		netToFamilyCrt = lifeDeathBenefit;
		netToCharity = crtTable[tableLength-5][4];
		
		totalOut = netToFamilyOut + netOutIncome;
		totalCrt = netToCharity + netToFamilyCrt + netCrtIncome;

	}
	
	
	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete(tableName, ID + "='"+ Long.toString(id) + "'");
		dbObj.stop();
	}

	@Override
	public long getToolTableId() {
		return com.estate.constants.ToolTableTypes.CRT.id();
	}

	
	
	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable(tableName);
		account = null;
		dbObj.clearFields();
		dbAddField(TOOL_TYPE, getToolType());
		dbAddField(TERM, getTerm());
		dbAddField(START_TERM, getStartTerm());
		dbAddField(TAX_RATE, getTaxRate());
		dbAddField(ESTATE_TAX_RATE, getEstateTaxRate());
		dbAddField(CAPITAL_GAINS_RATE, getCapitalGainsRate());
		dbAddField(AFR_RATE, getIrsRate());
		dbAddDate(AFR_DATE, convertDateToString(getIrsDate()));
		dbAddField(PAYOUT_RATE, getPayoutRate());
		dbAddField(PAYOUT_INCOME, getPayoutIncome());
		dbAddField(PAYOUT_GROWTH, getPayoutGrowth());
		dbAddField(PAYOUT_OPTION, getPayoutOption());
		dbAddField(ADJUSTED_GROSS_INCOME, getAdjustedGrossIncome());
		dbAddField(FINAL_DEATH, getFinalDeath());
		dbAddField(LIFE_PREMIUM, getLifePremium());
		dbAddField(LIFE_DEATH_BENEFIT, getLifeDeathBenefit());
		dbAddField(INVESTMENT_RETURN, getInvestmentReturn());
		dbAddField(UNILAG, getUniLag());
		
		int error = dbObj.insert();
		if(error == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object>ret = dbObj.execute("select "+ ID + " from " + tableName +
					" where UUID='" + uuid + "'");
			Object o = ret.get(ID);
			id = Integer.parseInt(o.toString());
		}

		dbObj.stop();
	}


	@Override
	public void read() {
		if(id > 0L){
			dbObj.start();
			dbObj.setTable(tableName);
			account = null;
			String sql = "select * from " + tableName + " where " + ID + "='"
				+ Long.toString(getId()) + "'";
			account = dbObj.execute(sql);
			dbObj.stop();
			if(account != null) {
				setToolType(getString(account,TOOL_TYPE));
				setAdjustedGrossIncome(getDouble(account,ADJUSTED_GROSS_INCOME));
				setIrsDate(getRealDate(account,AFR_DATE));
				setIrsRate(getDouble(account,AFR_RATE));
				setCapitalGainsRate(getDouble(account, CAPITAL_GAINS_RATE));
				setTerm((int)getDouble(account, TERM));
				setStartTerm((int)getDouble(account, START_TERM));
				setEstateTaxRate(getDouble(account,ESTATE_TAX_RATE));
				setPayoutRate(getDouble(account,PAYOUT_RATE));
				setPayoutIncome(getDouble(account, PAYOUT_INCOME));
				setPayoutGrowth(getDouble(account,PAYOUT_GROWTH));
				setPayoutOption(getString(account, PAYOUT_OPTION));
				setFinalDeath(getInteger(account,FINAL_DEATH));
				setLifeDeathBenefit(getDouble(account,LIFE_DEATH_BENEFIT));
				setLifePremium(getDouble(account,LIFE_PREMIUM));
				setInvestmentReturn(getDouble(account,INVESTMENT_RETURN));
				setTaxRate(getDouble(account,TAX_RATE));
				setFrequency(getInteger(account,FREQUENCY));
				setUniLag(getInteger(account,UNILAG));
			}
		}
	}


	@Override
	public void report() {

	}


	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		
		dbAddField(TOOL_TYPE, getToolType());
		dbAddField(TERM, getTerm());
		dbAddField(START_TERM, getStartTerm());
		dbAddField(TAX_RATE, getTaxRate());
		dbAddField(ESTATE_TAX_RATE, getEstateTaxRate());
		dbAddField(CAPITAL_GAINS_RATE, getCapitalGainsRate());
		dbAddField(AFR_RATE, getIrsRate());
		dbAddDate(AFR_DATE, convertDateToString(getIrsDate()));
		dbAddField(PAYOUT_RATE, getPayoutRate());
		dbAddField(PAYOUT_INCOME, getPayoutIncome());
		dbAddField(PAYOUT_GROWTH, getPayoutGrowth());
		dbAddField(PAYOUT_OPTION, getPayoutOption());
		dbAddField(ADJUSTED_GROSS_INCOME, getAdjustedGrossIncome());
		dbAddField(FINAL_DEATH, getFinalDeath());
		dbAddField(LIFE_PREMIUM, getLifePremium());
		dbAddField(LIFE_DEATH_BENEFIT, getLifeDeathBenefit());
		dbAddField(INVESTMENT_RETURN, getInvestmentReturn());
		dbAddField(UNILAG, getUniLag());
		
		dbObj.setWhere(ID +"='" + Long.toString(getId()) + "'");
		dbObj.update();
		
		dbObj.stop();
	}

	@Override
	public String writeupText() {
		return null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToolType() {
		return toolType;
	}

	public void setToolType(String toolType) {
		this.toolType = toolType;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public int getStartTerm() {
		return startTerm;
	}

	public void setStartTerm(int startTerm) {
		this.startTerm = startTerm;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public double getCapitalGainsRate() {
		return capitalGainsRate;
	}

	public void setCapitalGainsRate(double capitalGainsRate) {
		this.capitalGainsRate = capitalGainsRate;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public double getPayoutRate() {
		return payoutRate;
	}

	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public double getPayoutIncome() {
		return payoutIncome;
	}

	public void setPayoutIncome(double payoutIncome) {
		this.payoutIncome = payoutIncome;
	}

	public double getPayoutGrowth() {
		return payoutGrowth;
	}

	public void setPayoutGrowth(double payoutGrowth) {
		this.payoutGrowth = payoutGrowth;
	}

	public String getPayoutOption() {
		return payoutOption;
	}

	public void setPayoutOption(String payoutOption) {
		this.payoutOption = payoutOption;
	}

	public double getInvestmentReturn() {
		return investmentReturn;
	}

	public void setInvestmentReturn(double investmentReturn) {
		this.investmentReturn = investmentReturn;
	}

	public double getAdjustedGrossIncome() {
		return adjustedGrossIncome;
	}

	public void setAdjustedGrossIncome(double adjustedGrossIncome) {
		this.adjustedGrossIncome = adjustedGrossIncome;
	}

	public double getLifePremium() {
		return lifePremium;
	}

	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}

	public double getFinalDeath() {
		return finalDeath;
	}

	public void setFinalDeath(double finalDeath) {
		this.finalDeath = finalDeath;
	}

	public int getClientAge() {
		return clientAge;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}

	public int getUniLag() {
		return uniLag;
	}

	public void setUniLag(int uniLag) {
		this.uniLag = uniLag;
	}

	public double getLifeExpectancy() {
		return lifeExpectancy;
	}

	public void setLifeExpectancy(double lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public double getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}

	public double getAssetBasis() {
		return assetBasis;
	}

	public void setAssetBasis(double assetBasis) {
		this.assetBasis = assetBasis;
	}

	public double getAssetLiability() {
		return assetLiability;
	}

	public void setAssetLiability(double assetLiability) {
		this.assetLiability = assetLiability;
	}

	public double getAssetGrowth() {
		return assetGrowth;
	}

	public void setAssetGrowth(double assetGrowth) {
		this.assetGrowth = assetGrowth;
	}

	public double getAssetIncome() {
		return assetIncome;
	}

	public void setAssetIncome(double assetIncome) {
		this.assetIncome = assetIncome;
	}

	public double getAgiLimitation() {
		return agiLimitation;
	}

	public void setAgiLimitation(double agiLimitation) {
		this.agiLimitation = agiLimitation;
	}

	public double getMaxPayout() {
		return maxPayout;
	}

	public void setMaxPayout(double maxPayout) {
		this.maxPayout = maxPayout;
	}

	public double getMinPayout() {
		return minPayout;
	}

	public void setMinPayout(double minPayout) {
		this.minPayout = minPayout;
	}

	public double getCharitableDeduction() {
		return charitableDeduction;
	}

	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}

	public double getCharitableDeductionFactor() {
		return charitableDeductionFactor;
	}

	public void setCharitableDeductionFactor(double charitableDeductionFactor) {
		this.charitableDeductionFactor = charitableDeductionFactor;
	}

	public ToolAssetData[] getAssetData() {
		return assetData;
	}

	public void setAssetData(ToolAssetData[] assetData) {
		this.assetData = assetData;
	}

	public long getScenarioiId() {
		return scenarioiId;
	}

	public void setScenarioiId(long scenarioiId) {
		this.scenarioiId = scenarioiId;
	}

	public double[][] getCrtTable() {
		return crtTable;
	}

	public void setCrtTable(double[][] crtTable) {
		this.crtTable = crtTable;
	}

	public double[][] getOutRightSale() {
		return outRightSale;
	}

	public void setOutRightSale(double[][] outRightSale) {
		this.outRightSale = outRightSale;
	}

	public double getNetSpendableFamily() {
		return netSpendableFamily;
	}

	public void setNetSpendableFamily(double netSpendableFamily) {
		this.netSpendableFamily = netSpendableFamily;
	}

	public double getNetOutIncome() {
		return netOutIncome;
	}

	public void setNetOutIncome(double netOutIncome) {
		this.netOutIncome = netOutIncome;
	}

	public double getNetCrtIncome() {
		return netCrtIncome;
	}

	public void setNetCrtIncome(double netCrtIncome) {
		this.netCrtIncome = netCrtIncome;
	}

	public double getTotalOut() {
		return totalOut;
	}

	public void setTotalOut(double totalOut) {
		this.totalOut = totalOut;
	}

	public double getTotalCrt() {
		return totalCrt;
	}

	public void setTotalCrt(double totalCrt) {
		this.totalCrt = totalCrt;
	}

	public double getNetToFamilyCrt() {
		return netToFamilyCrt;
	}

	public void setNetToFamilyCrt(double netToFamilyCrt) {
		this.netToFamilyCrt = netToFamilyCrt;
	}

	public double getNetToFamilyOut() {
		return netToFamilyOut;
	}

	public void setNetToFamilyOut(double netToFamilyOut) {
		this.netToFamilyOut = netToFamilyOut;
	}

	public double getIncomeTaxDeduction() {
		return incomeTaxDeduction;
	}

	public void setIncomeTaxDeduction(double incomeTaxDeduction) {
		this.incomeTaxDeduction = incomeTaxDeduction;
	}

	public double getNetToCharity() {
		return netToCharity;
	}

	public void setNetToCharity(double netToCharity) {
		this.netToCharity = netToCharity;
	}
	
	
	
}
