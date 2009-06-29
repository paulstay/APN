package com.teag.estate;

/**
 * @author stay Created on Mar 22, 2005
 *  
 */

import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.zcalc.zCalc;

public class WptTool extends EstatePlanningTool {
	public static double AGI_LIMITATION = .30;
	public static double ESTATE_TAX_RATE = .55;
	//	 Database values
	long id;
	long scenarioId;
	String toolType;
	int term;
	int startTerm;
	double estateTaxRate;
	double capitalGainsRate;
	double afrRate;
	String afrDate;
	double payoutRate;
	double payoutIncome;
	double payoutGrowth;
	String payoutOption;
	
	double adjustedGrossIncome;
	
	double lifePolicy;
	double lifeDeathBenefit;

	double marginalTaxRate;
	double investmentReturn;
	// Values set by Tools
	int clientAge;
	int spouseAge;

	int clientLifeExp;
	int spouseLifeExp;
	int finalDeath;
	// If more than one asset than these are a weighted average.
	double assetValue;
	double assetBasis;

	double assetLiability;
	double assetGrowthRate;
	// Values calculated by tool!
	private double[][] wptTable;
	private double[][] outrightSale;
	private double charitableDeduction;
	private double charitableDeductionFactor;
	private double incomeTaxDeduction;
	private double netOutIncome;
	private double netWptIncome;
	private double netToFamilyWPT;
	private double netToFamilyOut;
	private double netToCharity;
	private double taxRate;

	private double totalFromOutrightSale;
	private double totalFromWpt;
	
	private HashMap<String,Object> account;
    private String uuid;
	
	ToolAssetData assetData[];
	
	/**
	 * Calcuate the benefit of the Wealth Preservation Tool, and create the information required for
	 * the different scenarios and reports!
	 */
	@Override
	public void calculate() {
		
		ToolAssetDistribution tad = new ToolAssetDistribution();
		tad.setDbObject();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());
		tad.calculate();
		assetData = tad.getToolAssetData();
		
		assetValue = tad.getTotalValue();
		assetGrowthRate = tad.getWeightedGrowth();
		assetLiability = tad.getLiability();
		assetBasis = tad.getBasis();
		
		initZCalc();

		charitableDeduction = (assetValue - assetLiability)
				* charitableDeductionFactor;
		taxRate = ((payoutIncome / payoutRate) * marginalTaxRate)
				+ ((payoutGrowth / payoutRate) * capitalGainsRate);

		// Extend the table out 5+ years after the longest life expectancy
		int tableLength = 100 - Math.max(clientAge, spouseAge);

		wptTable = new double[tableLength + 1][6];
		wptTable[0][5] = assetValue - assetLiability;

		double cDed = charitableDeduction;

		for (int i = 1; i <= tableLength; i++) {
			// Calculate crut values
			if (toolType.equals("CRUT")) {
				wptTable[i][0] = wptTable[i - 1][5] * payoutRate;
			} else  if( toolType.equals("NIMCRUT")) {
				if (i < startTerm) { // only start payment after so many years.
					wptTable[i][0] = 0;
				} else {
					if( investmentReturn < payoutRate) {
						wptTable[i][0] = wptTable[i - 1][5] * investmentReturn;
					} else {
						wptTable[i][0] = wptTable[i - 1][5] * payoutRate;
					}
				}
			} else { // FLIP Crut
				if( i < startTerm) {
					if( investmentReturn < payoutRate) {
						wptTable[i][0] = wptTable[i - 1][5] * investmentReturn;
					} else {
						wptTable[i][0] = wptTable[i - 1][5] * payoutRate;
					}
				} else {
					wptTable[i][0] = wptTable[i - 1][5] * payoutRate; // FLIP
				}
			}

			// Charitable Deduction value
			if (i <= 6) {
				double agi = wptTable[i][0] + adjustedGrossIncome;
				double charitableDed = Math.round(Math.min(agi
						* AGI_LIMITATION, cDed) / 1000) * 1000.00;
				cDed = Math.max(cDed - charitableDed, 0.0);
				wptTable[i][1] = charitableDed;
			} else {
				wptTable[i][1] = 0.0;
			}

			// Life Insurance Cost
			wptTable[i][2] = lifePolicy;

			// taxable income
			wptTable[i][3] = (wptTable[i][0] - wptTable[i][1]) * taxRate;

			// netSpendable = payout - (insurance + incometax)
			wptTable[i][4] = (wptTable[i][0] - (wptTable[i][2] + wptTable[i][3]));

			// Balance of Trust
			wptTable[i][5] = (wptTable[i - 1][5] * (1 + investmentReturn))
					- wptTable[i][0];

		} // End of Wealth Preservation Table

		// Outright Sale table
		outrightSale = new double[tableLength + 1][4];
		outrightSale[0][3] = assetValue - assetLiability
				- ((assetValue - assetBasis) * capitalGainsRate);

		/*
		 * Use the percentage of this to show how much should be taxes as capital gains, and how much as income
		 * The original code used just the payoutgrowth, but it does not really reflect the income payout. 
		 */
		double percentGrowth = payoutGrowth / payoutRate * investmentReturn;
		double percentIncome = payoutIncome/ payoutRate * investmentReturn;
		
		for (int i = 1; i <= tableLength; i++) {
			outrightSale[i][0] = outrightSale[i - 1][3] * investmentReturn;
			outrightSale[i][1] = (outrightSale[i - 1][3] * (percentIncome * marginalTaxRate)) + 
				(outrightSale[i-1][3] * percentGrowth * capitalGainsRate);
			if (payoutOption.equals("P")) {
				outrightSale[i][2] = outrightSale[i - 1][3]
						* (payoutRate * (1 - taxRate));
			} else if (payoutOption.equals("M")) {
				outrightSale[i][2] = wptTable[i][4];
			} else { // Level payout
				outrightSale[i][2] = outrightSale[i][0] - outrightSale[i][1];
			}

			double balance = (outrightSale[i-1][3] * (1 + investmentReturn))
					- (outrightSale[i][1] + outrightSale[i][2]);
			if (balance < 0.0) {
				outrightSale[i][3] = 0.0;
			} else {
				outrightSale[i][3] = balance;
			}
		} // End of outright Sale table

		// Calculate final values
		incomeTaxDeduction = 0;
		netOutIncome = 0;
		netWptIncome = 0;

		for (int i = 0; i <= tableLength; i++) {
			incomeTaxDeduction += wptTable[i][1];
			netOutIncome += outrightSale[i][2];
			netWptIncome += wptTable[i][4];
		}

		netToFamilyOut = outrightSale[tableLength][3] * (1 - estateTaxRate);
		netToFamilyWPT = lifeDeathBenefit;
		netToCharity = wptTable[tableLength - 5][5]; // remember we extend out
													 // to 5+ years above.

		totalFromOutrightSale = netToFamilyOut + netOutIncome;
		totalFromWpt = netToCharity + netToFamilyWPT + netWptIncome;
	}

	/*
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("CRT_TOOL", "ID='" + id + "'");
		dbObj.stop();
	}

	/**
	 * @return Returns the adjustedGrossIncome.
	 */
	public double getAdjustedGrossIncome() {
		return adjustedGrossIncome;
	}

	/**
	 * @return Returns the afrDate.
	 */
	public String getAfrDate() {
		return afrDate;
	}
	
	/**
	 * @return Returns the afrRate.
	 */
	public double getAfrRate() {
		return afrRate;
	}

	/**
	 * @return Returns the assetBasis.
	 */
	public double getAssetBasis() {
		return assetBasis;
	}

	/**
	 * @return Returns the assetGrowthRate.
	 */
	public double getAssetGrowthRate() {
		return assetGrowthRate;
	}

	/**
	 * @return Returns the assetLiability.
	 */
	public double getAssetLiability() {
		return assetLiability;
	}

	/**
	 * @return Returns the assetValue.
	 */
	public double getAssetValue() {
		return assetValue;
	}
	/**
	 * @return Returns the capitalGainsRate.
	 */
	public double getCapitalGainsRate() {
		return capitalGainsRate;
	}
	/**
	 * @return Returns the charitableDeduction.
	 */
	public double getCharitableDeduction() {
		return charitableDeduction;
	}
	/**
	 * @return Returns the charitableDeductionFactor.
	 */
	public double getCharitableDeductionFactor() {
		return charitableDeductionFactor;
	}
	/**
	 * @return Returns the clientAge.
	 */
	public int getClientAge() {
		return clientAge;
	}
	/**
	 * @return Returns the clientLifeExp.
	 */
	public int getClientLifeExp() {
		return clientLifeExp;
	}
	/**
	 * @return Returns the estateTaxRate.
	 */
	public double getEstateTaxRate() {
		return estateTaxRate;
	}
	
	public int getFinalDeath() {
		return finalDeath;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the incomeTaxDeduction.
	 */
	public double getIncomeTaxDeduction() {
		return incomeTaxDeduction;
	}
	/**
	 * @return Returns the investmentReturn.
	 */
	public double getInvestmentReturn() {
		return investmentReturn;
	}
	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}
	public double getLifePolicy() {
		return lifePolicy;
	}
	/**
	 * @return Returns the marginalTaxRate.
	 */
	public double getMarginalTaxRate() {
		return marginalTaxRate;
	}
	/**
	 * @return Returns the netOutIncome.
	 */
	public double getNetOutIncome() {
		return netOutIncome;
	}
	/**
	 * @return Returns the netToCharity.
	 */
	public double getNetToCharity() {
		return netToCharity;
	}
	/**
	 * @return Returns the netToFamilyOut.
	 */
	public double getNetToFamilyOut() {
		return netToFamilyOut;
	}
	/**
	 * @return Returns the netToFamilyWPT.
	 */
	public double getNetToFamilyWPT() {
		return netToFamilyWPT;
	}
	/**
	 * @return Returns the netWptIncome.
	 */
	public double getNetWptIncome() {
		return netWptIncome;
	}
	/**
	 * @return Returns the outrightSale.
	 */
	public double[][] getOutrightSale() {
		return outrightSale;
	}
	/**
	 * @return Returns the payoutGrowth.
	 */
	public double getPayoutGrowth() {
		return payoutGrowth;
	}
	/**
	 * @return Returns the payoutIncome.
	 */
	public double getPayoutIncome() {
		return payoutIncome;
	}
	/**
	 * @return Returns the payoutOption.
	 */
	public String getPayoutOption() {
		return payoutOption;
	}
	/**
	 * @return Returns the payoutRate.
	 */
	public double getPayoutRate() {
		return payoutRate;
	}
	/**
	 * @return Returns the scenarioId.
	 */
	public long getScenarioId() {
		return scenarioId;
	}
	/**
	 * @return Returns the spouseAge.
	 */
	public int getSpouseAge() {
		return spouseAge;
	}
	/**
	 * @return Returns the spouseLifeExp.
	 */
	public int getSpouseLifeExp() {
		return spouseLifeExp;
	}
	/**
	 * @return Returns the startTerm.
	 */
	public int getStartTerm() {
		return startTerm;
	}
	/**
	 * @return Returns the taxRate.
	 */
	public double getTaxRate() {
		return taxRate;
	}
	/**
	 * @return Returns the term.
	 */
	public int getTerm() {
		return term;
	}
	@Override
	public long getToolTableId() {
		return ToolTableTypes.CRT.id();
	}
	/**
	 * @return Returns the toolType.
	 */
	public String getToolType() {
		return toolType;
	}

	/**
	 * @return Returns the totalFromOutrightSale.
	 */
	public double getTotalFromOutrightSale() {
		return totalFromOutrightSale;
	}
	/**
	 * @return Returns the totalFromWpt.
	 */
	public double getTotalFromWpt() {
		return totalFromWpt;
	}
	/**
	 * @return Returns the wptTable.
	 */
	public double[][] getWptTable() {
		return wptTable;
	}
	public void initZCalc() {
		zCalc zc = new zCalc();
		zc.StartUp();

		// Used to calculate the Charitable Deduction Factor
		// Returns the income (lead) and remainder factors of a unitrust
		// involving
		// one to five lives

		// zUNILIFE(Factor, Payout, Age1, Age2, Age3, Age4, Age5, Rate,
		// PmtPeriod,
		// uni/lag, YsrsDef, Status, Table)
		if (toolType.equals("CRUT") || toolType.equals("NIMCRUT")) {
			charitableDeductionFactor = zc.zUNILIFE(2, payoutRate, clientAge,
					spouseAge, 0, 0, 0, afrRate, 4, 3, 0, 0, 0);
		} else {
			double c = zc.zANNLIFE(assetValue, payoutRate, afrRate, clientAge,
					spouseAge, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0);
			charitableDeductionFactor = (-c + assetValue) / assetValue;
		}

		zc.ShutDown();
		zc = null;

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable("CRT_TOOL");
		account = null;
		dbObj.clearFields();
		dbAddField("TOOL_TYPE", getToolType());
		dbAddField("TERM", getTerm());
		dbAddField("START_TERM", getStartTerm());
		dbAddField("TAX_RATE", this.getMarginalTaxRate());
		dbAddField("ESTATE_TAX_RATE", getEstateTaxRate());
		dbAddField("CAPITAL_GAINS_RATE", getCapitalGainsRate());
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("PAYOUT_RATE",getPayoutRate());
		dbAddField("PAYOUT_INCOME",getPayoutIncome());
		dbAddField("PAYOUT_GROWTH",getPayoutGrowth());
		dbAddField("PAYOUT_OPTION",getPayoutOption());
		dbAddField("ADJUSTED_GROSS_INCOME", getAdjustedGrossIncome());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("LIFE_PREMIUM", getLifePolicy());
		dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
		dbAddField("INVESTMENT_RETURN", getInvestmentReturn());

		int error = dbObj.insert();
		if( error == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from CRT_TOOL where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			id = Integer.parseInt(o.toString());
		}
		dbObj.stop();
	}
	/*
	 * Read in the record from the database!
	 * 
	 */
	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("CRT_TOOL");
			account = null;
			String sql = "select * from CRT_TOOL where ID='"+ id +"'";
			account = dbObj.execute(sql);
			dbObj.stop();
			
			if(account != null) {
				this.setToolType(getString(account,"TOOL_TYPE"));
				this.setAdjustedGrossIncome(getDouble(account, "ADJUSTED_GROSS_INCOME"));
				this.setAfrDate(getDate(account,"AFR_DATE"));
				this.setAfrRate(getDouble(account,"AFR_RATE"));
				this.setCapitalGainsRate(getDouble(account, "CAPITAL_GAINS_RATE"));
				this.setTerm((int)getDouble(account, "TERM"));
				this.setStartTerm((int)getDouble(account, "START_TERM"));
				this.setEstateTaxRate(getDouble(account,"ESTATE_TAX_RATE"));
				this.setPayoutRate(getDouble(account,"PAYOUT_RATE"));
				this.setPayoutIncome(getDouble(account, "PAYOUT_INCOME"));
				this.setPayoutGrowth(getDouble(account,"PAYOUT_GROWTH"));
				this.setPayoutOption(getString(account, "PAYOUT_OPTION"));
				this.setFinalDeath(getInteger(account,"FINAL_DEATH"));
				this.setLifeDeathBenefit(getDouble(account,"LIFE_DEATH_BENEFIT"));
				this.setLifePolicy(getDouble(account,"LIFE_PREMIUM"));
				this.setInvestmentReturn(getDouble(account,"INVESTMENT_RETURN"));
				this.setMarginalTaxRate(getDouble(account,"TAX_RATE"));
				this.setAdjustedGrossIncome(getDouble(account,"ADJUSTED_GROSS_INCOME"));
			}
		}
	}

	/**
	 * @param adjustedGrossIncome The adjustedGrossIncome to set.
	 */
	public void setAdjustedGrossIncome(double adjustedGrossIncome) {
		this.adjustedGrossIncome = adjustedGrossIncome;
	}
	/**
	 * @param afrDate The afrDate to set.
	 */
	public void setAfrDate(String afrDate) {
		this.afrDate = afrDate;
	}
	/**
	 * @param afrRate The afrRate to set.
	 */
	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}
	/**
	 * @param assetBasis The assetBasis to set.
	 */
	public void setAssetBasis(double assetBasis) {
		this.assetBasis = assetBasis;
	}
	/**
	 * @param assetGrowthRate The assetGrowthRate to set.
	 */
	public void setAssetGrowthRate(double assetGrowthRate) {
		this.assetGrowthRate = assetGrowthRate;
	}
	/**
	 * @param assetLiability The assetLiability to set.
	 */
	public void setAssetLiability(double assetLiability) {
		this.assetLiability = assetLiability;
	}
	/**
	 * @param assetValue The assetValue to set.
	 */
	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}
	/**
	 * @param capitalGainsRate The capitalGainsRate to set.
	 */
	public void setCapitalGainsRate(double capitalGainsRate) {
		this.capitalGainsRate = capitalGainsRate;
	}
	/**
	 * @param charitableDeduction The charitableDeduction to set.
	 */
	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}
	/**
	 * @param charitableDeductionFactor The charitableDeductionFactor to set.
	 */
	public void setCharitableDeductionFactor(double charitableDeductionFactor) {
		this.charitableDeductionFactor = charitableDeductionFactor;
	}
	/**
	 * @param clientAge The clientAge to set.
	 */
	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}
	/**
	 * @param clientLifeExp The clientLifeExp to set.
	 */
	public void setClientLifeExp(int clientLifeExp) {
		this.clientLifeExp = clientLifeExp;
	}
	/**
	 * @param estateTaxRate The estateTaxRate to set.
	 */
	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}
	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param incomeTaxDeduction The incomeTaxDeduction to set.
	 */
	public void setIncomeTaxDeduction(double incomeTaxDeduction) {
		this.incomeTaxDeduction = incomeTaxDeduction;
	}
	/**
	 * @param investmentReturn The investmentReturn to set.
	 */
	public void setInvestmentReturn(double investmentReturn) {
		this.investmentReturn = investmentReturn;
	}
	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}
	public void setLifePolicy(double lifePolicy) {
		this.lifePolicy = lifePolicy;
	}
	/**
	 * @param marginalTaxRate The marginalTaxRate to set.
	 */
	public void setMarginalTaxRate(double marginalTaxRate) {
		this.marginalTaxRate = marginalTaxRate;
	}
	/**
	 * @param netOutIncome The netOutIncome to set.
	 */
	public void setNetOutIncome(double netOutIncome) {
		this.netOutIncome = netOutIncome;
	}
	/**
	 * @param netToCharity The netToCharity to set.
	 */
	public void setNetToCharity(double netToCharity) {
		this.netToCharity = netToCharity;
	}
	/**
	 * @param netToFamilyOut The netToFamilyOut to set.
	 */
	public void setNetToFamilyOut(double netToFamilyOut) {
		this.netToFamilyOut = netToFamilyOut;
	}
	/**
	 * @param netToFamilyWPT The netToFamilyWPT to set.
	 */
	public void setNetToFamilyWPT(double netToFamilyWPT) {
		this.netToFamilyWPT = netToFamilyWPT;
	}
	/**
	 * @param netWptIncome The netWptIncome to set.
	 */
	public void setNetWptIncome(double netWptIncome) {
		this.netWptIncome = netWptIncome;
	}
	/**
	 * @param outrightSale The outrightSale to set.
	 */
	public void setOutrightSale(double[][] outrightSale) {
		this.outrightSale = outrightSale;
	}
	/**
	 * @param payoutGrowth The payoutGrowth to set.
	 */
	public void setPayoutGrowth(double payoutGrowth) {
		this.payoutGrowth = payoutGrowth;
	}
	/**
	 * @param payoutIncome The payoutIncome to set.
	 */
	public void setPayoutIncome(double payoutIncome) {
		this.payoutIncome = payoutIncome;
	}
	/**
	 * @param payoutOption The payoutOption to set.
	 */
	public void setPayoutOption(String payoutOption) {
		this.payoutOption = payoutOption;
	}
	/**
	 * @param payoutRate The payoutRate to set.
	 */
	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}
	/**
	 * @param scenarioId The scenarioId to set.
	 */
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}
	/**
	 * @param spouseAge The spouseAge to set.
	 */
	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}
	/**
	 * @param spouseLifeExp The spouseLifeExp to set.
	 */
	public void setSpouseLifeExp(int spouseLifeExp) {
		this.spouseLifeExp = spouseLifeExp;
	}
	
	/**
	 * @param startTerm The startTerm to set.
	 */
	public void setStartTerm(int startTerm) {
		this.startTerm = startTerm;
	}
	/**
	 * @param taxRate The taxRate to set.
	 */
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	
	/**
	 * @param term The term to set.
	 */
	public void setTerm(int term) {
		this.term = term;
	}
	/**
	 * @param toolType The toolType to set.
	 */
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
	/**
	 * @param totalFromOutrightSale The totalFromOutrightSale to set.
	 */
	public void setTotalFromOutrightSale(double totalFromOutrightSale) {
		this.totalFromOutrightSale = totalFromOutrightSale;
	}
	/**
	 * @param totalFromWpt The totalFromWpt to set.
	 */
	public void setTotalFromWpt(double totalFromWpt) {
		this.totalFromWpt = totalFromWpt;
	}
	/**
	 * @param wptTable The wptTable to set.
	 */
	public void setWptTable(double[][] wptTable) {
		this.wptTable = wptTable;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("CRT_TOOL");
		dbObj.clearFields();
		
		dbAddField("TOOL_TYPE", getToolType());
		dbAddField("TERM", getTerm());
		dbAddField("START_TERM", getStartTerm());
		dbAddField("ESTATE_TAX_RATE", getEstateTaxRate());
		dbAddField("CAPITAL_GAINS_RATE", getCapitalGainsRate());
		dbAddField("TAX_RATE", this.getMarginalTaxRate());
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("PAYOUT_RATE",getPayoutRate());
		dbAddField("PAYOUT_INCOME",getPayoutIncome());
		dbAddField("PAYOUT_GROWTH",getPayoutGrowth());
		dbAddField("PAYOUT_OPTION",getPayoutOption());
		dbAddField("ADJUSTED_GROSS_INCOME", getAdjustedGrossIncome());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("LIFE_PREMIUM", getLifePolicy());
		dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
		dbAddField("INVESTMENT_RETURN", getInvestmentReturn());

		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();
	}

	@Override
	public void report() {
		
	}

	@Override
	public String writeupText() {
		return null;
	}
}
