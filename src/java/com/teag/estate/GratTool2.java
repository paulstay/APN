package com.teag.estate;

/**
 * @author stay
 * Created on Apr 11, 2005
 * Description GRAT_TOOl used to get the data from the database as well as setup the calculations for the GRAT.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.AssetSqlBean;
import com.zcalc.zCalc;

public class GratTool2 extends EstatePlanningTool {

	long id;
	String uuid;
	HashMap<String,Object> rec;

	double clientAge;
	double spouseAge;
	double clientLifeExp;
	double spouseLifeExp;
	double afrRate;
	String afrDate;
	int numTrusts; // number of trusts to use.
	String calcType;		// A = annuity payment, R = remainder interest, Z = zero out (default)

	// If there are two trusts, the annuity will be split in two and equally
	// divided between the two trusts.
	// The same with the valuation of the assets.
	double annuity;
	int annuityFreq;
	double remainderInterest;
	double annuityIncrease;
	double annuityPaymentRate;
	double incomeTaxRate;
	double clientPriorGifts;
	double spousePriorGifts;
	double clientTermLength;
	double spouseTermLength;
	double clientStartTerm;
	double spouseStartTerm;
	double transferDate;

	boolean useLife = false;
	double lifeInsPremium = 0;
	double lifeDeathBenefit = 0;
	double lifeCashValue = 0;

	// Assets put into the trust, usually a FLP or LLC
	// The principal is the total value of the assets, the discountedValue is
	// the valuation discount (if any)
	// that the FLP provides, if this is not a FLP, than there will be no
	// discount.
	// The growthRate and the IncomeRate are weighted averages
	double totalValue;
	double weightedGrowth;
	double weightedIncome;
	double discountedValue;
	String assetNames;

	int finalDeath = 0;
	boolean securities = false;

	double estateTaxRate;

	GratTrust clientTrust = null;
	GratTrust spouseTrust = null;

	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	Object assetData[];

	// The following arrays are used for exporting the data for the scenario 2
	// issues, as well as the summary.
	double sc1[];
	double cfTotalValue[];
	double cfSecurities[];
	double cfAssetValue[];

	boolean grantor = true;

	public void buildAssetList() {
		securities = false;
		assetList.clear();
		double tValue = 0;
		
		for (int i = 0; i < assetData.length; i++) {
			AssetData ad;
			ToolAssetData asset = (ToolAssetData) assetData[i];
			if (asset.getAssetType() == AssetSqlBean.SECURITIES) {
				ad = new AssetData(AssetData.SECURITY);
				securities = true;
			} else {
				ad = new AssetData(AssetData.OTHER);
			}

			tValue += asset.getValue();
			
			ad.setName(asset.getName());
			ad.setValue(asset.getValue());
			ad.setGrowth(asset.getGrowth());
			ad.setIncome(asset.getIncome());
			assetList.add(ad);
		}
		// If there are no securities present, than the income will need to be
		// put into a securities account
		if (!securities) {
			AssetData ad = new AssetData(AssetData.SECURITY);
			ad.setValue(0);
			ad.setGrowth(.05);
			ad.setIncome(.02);
			ad.setName("Internal Securities");
			assetList.add(ad);
		}
		// Adjust the value because we might have a percentage of the asset.
		if( totalValue < tValue) {
			double discount = totalValue/tValue;
			for(AssetData a : assetList) {
				a.setValue(a.getValue() * discount);
			}
		}
	}

	public void buildCFTable() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		int year = cal.get(Calendar.YEAR);

		// Need to start with new asset list.
		buildAssetList();
		if (finalDeath <= 0)
			finalDeath = 25;

		cfSecurities = new double[60];
		cfTotalValue = new double[60];
		cfAssetValue = new double[60];

		double estateTaxOnGrat = clientTrust.getTaxableGift();

		if (spouseTrust != null) {
			estateTaxOnGrat += spouseTrust.getTaxableGift();
		}

		for (int i = 0; i < finalDeath; i++) {
			// Since we are looking at the start year as index 0 we need to add
			// 1 to make this work.
			double gratPayment = gratClientPayment(i + 1)
					+ gratSpousePayment(i + 1);
			double securitiesValue = 0;
			double assetsValue = 0;
			double totalIncome = 0;

			Iterator<AssetData> iter = assetList.iterator();
			// Walk through the asset list and calculate income,
			// and ending value, don't update yet.
			while (iter.hasNext()) {
				AssetData ad = iter.next();
				if (ad.getAssetType() == AssetData.SECURITY) {
					securitiesValue += ad.getValue() * (1 + ad.getGrowth());
				} else {
					assetsValue += ad.getValue() * (1 + ad.getGrowth());
				}
				totalIncome += ad.getValue() * ad.getIncome();
			}

			double netIncome = totalIncome + gratPayment;

			double incomeTax = 0;
			if (grantor) {
				if (i <= Math.min(clientTermLength, spouseTermLength)) {
					incomeTax = 0;
				} else if (i > Math.min(clientTermLength, spouseTermLength)
						&& i <= Math.max(clientTermLength, spouseTermLength)) {
					incomeTax = -(totalIncome * getIncomeTaxRate() * .5);
				} else {
					incomeTax = -(totalIncome * getIncomeTaxRate());
				}
			} else {
				incomeTax = -(totalIncome * getIncomeTaxRate());
			}

			// Fill out the three tables
			cfSecurities[i] = securitiesValue + netIncome + incomeTax;
			cfAssetValue[i] = assetsValue;
			cfTotalValue[i] = cfSecurities[i] + cfAssetValue[i]
					- (estateTaxOnGrat * getFet(year));

			// Update all of the assets, making sure we put in the netIncome -
			// taxes back into the securities
			iter = assetList.iterator();
			while (iter.hasNext()) {
				boolean found = false;
				AssetData item = iter.next();
				item.update();
				if (item.getAssetType() == AssetData.SECURITY && !found) {
					item.addIncome(netIncome + incomeTax);
					found = true;
				}
			}
			year++;
		}
		assetList.clear();
	}

	public void buildSCTable() {
		// Need to start with new asset list.
		buildAssetList();
		if (finalDeath <= 0)
			finalDeath = 25;

		sc1 = new double[60];

		for (int i = 0; i < finalDeath; i++) {
			Iterator<AssetData> ai = assetList.iterator();
			double totalValue = 0;
			double totalIncome = 0;
			while (ai.hasNext()) {
				AssetData item = ai.next();
				totalValue += (item.getGrowthValue() + item.getValue());
				totalIncome += item.getIncomeValue();
			}

			double incomeTax = -(totalIncome * getIncomeTaxRate());

			ai = assetList.iterator();
			while (ai.hasNext()) {
				boolean found = false;
				AssetData item = ai.next();
				item.update();
				if (item.getAssetType() == AssetData.SECURITY && !found) {
					item.addIncome(totalIncome + incomeTax);
					found = true;
				}
			}
			sc1[i] = totalValue + totalIncome + incomeTax;
		}
		assetList.clear();
	}

	/*
	 * (non-Javadoc)p
	 * 
	 * @see com.teag.estate.EstatePlanningTool#calculate()
	 */
	@Override
	public void calculate() {
		// ToolAssetDistribution tad = new ToolAssetDistribution();
		EstateToolDistribution tad = new EstateToolDistribution();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());

		tad.buildToolAssetList();
		assetData = tad.getAssetToolData();
		assetNames = tad.getAssetNames();
		totalValue = tad.getTotalValue();
		weightedGrowth = tad.getGrowth();
		weightedIncome = tad.getIncome();
		discountedValue = tad.getDiscountValue();

		clientTrust = null;
		clientTrust = new GratTrust();
		clientTrust.setIrsRate(afrRate);
		clientTrust.setAge((int)clientAge);
		clientTrust.setTerm((int)clientTermLength);
		clientTrust.setGrowthRate(weightedGrowth);
		// clientTrust.setAnnuityPaymentRate(annuityPaymentRate);
		clientTrust.setAnnuityFreq(annuityFreq);
		clientTrust.setAnnuityIncrease(annuityIncrease);
		clientTrust.setEstateTaxRate(estateTaxRate);
		clientTrust.setStartTerm((int)clientStartTerm);
		double factor = 1;
		
		if(numTrusts == 2)
			factor = 2;

		clientTrust.setFmv(totalValue/factor);
		clientTrust.setDiscount(discountedValue/factor);
		
		clientTrust.setCalcType(calcType);
		if(calcType.equalsIgnoreCase("A")){
			clientTrust.setAnnuityPayment(annuity/factor);
			clientTrust.setRemainderInterest(0);				// need to calculate this
		} else if(calcType.equalsIgnoreCase("R")){
			clientTrust.setRemainderInterest(remainderInterest/factor);
			clientTrust.setAnnuityPayment(0);					// Will calculate this
		} else {
			clientTrust.setRemainderInterest(0);
			clientTrust.setAnnuityPayment(0);
		}

		// Setup spouse trusts if required
		if (numTrusts == 2) {
			spouseTrust = null;
			spouseTrust = new GratTrust();
			spouseTrust.setIrsRate(afrRate);
			spouseTrust.setAge((int)spouseAge);
			spouseTrust.setTerm((int)spouseTermLength);
			spouseTrust.setAnnuityPayment(annuity / factor);
			spouseTrust.setGrowthRate(weightedGrowth);
			// spouseTrust.setAnnuityPaymentRate(annuityPaymentRate);
			spouseTrust.setAnnuityFreq(annuityFreq);
			spouseTrust.setAnnuityIncrease(annuityIncrease);
			spouseTrust.setEstateTaxRate(estateTaxRate);
			spouseTrust.setFmv(totalValue / factor);
			spouseTrust.setDiscount(discountedValue / factor);
			spouseTrust.setStartTerm((int)spouseStartTerm);
			spouseTrust.setCalcType(calcType);
			if(calcType.equalsIgnoreCase("A")){
				spouseTrust.setAnnuityPayment(annuity/factor);
				spouseTrust.setRemainderInterest(0);				// need to calculate this
			} else if(calcType.equalsIgnoreCase("R")){
				spouseTrust.setRemainderInterest(remainderInterest/factor);
				spouseTrust.setAnnuityPayment(0);					// Will calculate this
			} else {
				spouseTrust.setRemainderInterest(0);
				spouseTrust.setAnnuityPayment(0);
			}
		}

		// Calculate the trust values;
		clientTrust.calculate();
		if (numTrusts == 2) {
			spouseTrust.calculate();
		}
		
		remainderInterest = clientTrust.getRemainderInterest();
		if(numTrusts == 2)
			remainderInterest += spouseTrust.getRemainderInterest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("GRAT_TOOL", "ID='" + id + "'");
		dbObj.stop();
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
	 * @return Returns the annuity.
	 */
	public double getAnnuity() {
		return annuity;
	}

	/**
	 * @return Returns the annuityFreq.
	 */
	public int getAnnuityFreq() {
		return annuityFreq;
	}

	/**
	 * @return Returns the annuityIncrease.
	 */
	public double getAnnuityIncrease() {
		return annuityIncrease;
	}

	/**
	 * @return Returns the annuityPaymentRate.
	 */
	public double getAnnuityPaymentRate() {
		return annuityPaymentRate;
	}

	public ArrayList<AssetData> getAssetList() {
		return assetList;
	}

	/**
	 * @return Returns the assetNames.
	 */
	public String getAssetNames() {
		return assetNames;
	}

	public String getCalcType() {
		return calcType;
	}

	public double[] getCfAssetValue() {
		return cfAssetValue;
	}

	public double getCFAssetValue(int i) {
		return cfAssetValue[i - 1];
	}

	public double getCfSecurities(int i) {
		return cfSecurities[i - 1];
	}

	public double[] getCfTotalValue() {
		return cfTotalValue;
	}

	public double getCFTotalValue(int i) {
		return cfTotalValue[i - 1];
	}

	/**
	 * @return Returns the clientAge.
	 */
	public double getClientAge() {
		return clientAge;
	}

	/**
	 * @return Returns the clientLifeExp.
	 */
	public double getClientLifeExp() {
		return clientLifeExp;
	}

	/**
	 * @return Returns the clientPriorGifts.
	 */
	public double getClientPriorGifts() {
		return clientPriorGifts;
	}

	/**
	 * @return Returns the clientStartTerm.
	 */
	public double getClientStartTerm() {
		return clientStartTerm;
	}

	/**
	 * @return Returns the clientTermLength.
	 */
	public double getClientTermLength() {
		return clientTermLength;
	}

	/**
	 * @return Returns the clientTrust.
	 */
	public GratTrust getClientTrust() {
		return clientTrust;
	}

	/**
	 * @return Returns the discountedValue.
	 */
	public double getDiscountedValue() {
		return discountedValue;
	}

	/**
	 * @return Returns the estateTaxRate.
	 */
	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getFet(int year) {
		double fet;
		zCalc zc = new zCalc();
		zc.StartUp();
		fet = zc.zFETMAXRATE(year, 0);
		zc.ShutDown();
		return fet;
	}

	/**
	 * @return Returns the finalDeath.
	 */
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
	 * @return Returns the incomeTaxRate.
	 */
	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}

	public double getLifeCashValue() {
		return lifeCashValue;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public double getLifeInsPremium() {
		return lifeInsPremium;
	}

	/**
	 * @return Returns the numTrusts.
	 */
	public int getNumTrusts() {
		return numTrusts;
	}

	/**
	 * @return Returns the rec.
	 */
	public HashMap<String,Object> getRec() {
		return rec;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double[] getSc1() {
		return sc1;
	}

	public double getSCTable(int i) {
		return sc1[i - 1];
	}

	/**
	 * @return Returns the spouseAge.
	 */
	public double getSpouseAge() {
		return spouseAge;
	}

	/**
	 * @return Returns the spouseLifeExp.
	 */
	public double getSpouseLifeExp() {
		return spouseLifeExp;
	}

	/**
	 * @return Returns the spousePriorGifts.
	 */
	public double getSpousePriorGifts() {
		return spousePriorGifts;
	}

	/**
	 * @return Returns the spouseStartTerm.
	 */
	public double getSpouseStartTerm() {
		return spouseStartTerm;
	}

	/**
	 * @return Returns the spouseTermLength.
	 */
	public double getSpouseTermLength() {
		return spouseTermLength;
	}

	/**
	 * @return Returns the spouseTrust.
	 */
	public GratTrust getSpouseTrust() {
		return spouseTrust;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#getToolTableId()
	 */
	@Override
	public long getToolTableId() {
		return ToolTableTypes.GRAT.id();
	}

	/**
	 * @return Returns the totalValue.
	 */
	public double getTotalValue() {
		return totalValue;
	}

	/**
	 * @return Returns the transferDate.
	 */
	public double getTransferDate() {
		return transferDate;
	}

	/**
	 * @return Returns the uuid.
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @return Returns the weightedGrowth.
	 */
	public double getWeightedGrowth() {
		return weightedGrowth;
	}

	/**
	 * @return Returns the weightedIncome.
	 */
	public double getWeightedIncome() {
		return weightedIncome;
	}

	/**
	 * calculate the grat payment for a client particular year adjusting for the
	 * start term, term length and if there are two trusts. From Excel
	 * Spreadshhet Grat - CF worksheet.
	 * 
	 * @param year
	 * @return gratPayment
	 */
	public double gratClientPayment(int trustYear) {
		double gratPayment = 0;
		if (trustYear >= clientStartTerm) {
			if (trustYear < (clientStartTerm + clientTermLength)) {
				gratPayment = -annuity;
			}
		}
		if (numTrusts == 1)
			return gratPayment;
		return gratPayment / 2;
	}

	/**
	 * calculate the grat payment for a particular year adjusting for a spouse
	 * trust given the the start term, term length and if there are two trusts.
	 * From Excel Spreadshhet Grat - CF worksheet.
	 * 
	 * @param year
	 * @return gratPayment
	 */
	public double gratSpousePayment(int trustYear) {
		double gratPayment = 0;

		if (numTrusts == 1)
			return 0;

		if (trustYear >= spouseStartTerm) {
			if (trustYear < (spouseStartTerm + spouseTermLength)) {
				gratPayment = -annuity;
			}
		}
		return gratPayment / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable("GRAT_TOOL");
		dbObj.clearFields();
		rec = null;
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("TRUSTS", getNumTrusts());
		dbAddField("CLIENT_TERM", getClientTermLength());
		dbAddField("CLIENT_START_TERM", getClientStartTerm());
		dbAddField("SPOUSE_TERM", getSpouseTermLength());
		dbAddField("SPOUSE_START_TERM", getSpouseStartTerm());
		dbAddField("CLIENT_PRIOR_GIFTS", getClientPriorGifts());
		dbAddField("SPOUSE_PRIOR_GIFTS", getClientPriorGifts());
		dbAddField("ANNUITY", getAnnuity());
		dbAddField("ANNUITY_FREQ", getAnnuityFreq());
		dbAddField("ANNUITY_INCREASE", getAnnuityIncrease());
		dbAddField("ESTATE_TAX_RATE", getEstateTaxRate());
		dbAddField("INCOME_TAX_RATE", getIncomeTaxRate());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("CALC_TYPE", getCalcType());
		dbAddField("REMAINDER_INTEREST", getRemainderInterest());
		if (isUseLife()) {
			dbAddField("USE_INSURANCE", "T");
			dbAddField("LIFE_INSURANCE_PREMIUM", getLifeInsPremium());
			dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
			dbAddField("LIFE_CASH_VALUE", getLifeCashValue());
		} else {
			dbAddField("USE_INSURANCE", "F");
			dbAddField("LIFE_INSURANCE_PREMIUM", 0.0);
			dbAddField("LIFE_DEATH_BENEFIT", 0.0);
			dbAddField("LIFE_CASH_VALUE", 0.0);
		}

		int error = dbObj.insert();
		if (error == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from GRAT_TOOL where UUID='"
					+ uuid + "'");
			Object o = ret.get("ID");
			if (o != null) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();
	}

	public boolean isUseLife() {
		return useLife;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#read()
	 */
	@Override
	public void read() {
		if (id > 0L) {
			dbObj.start();
			dbObj.setTable("GRAT_TOOL");
			dbObj.clearFields();
			rec = null;
			String sql = "select * from GRAT_TOOL where ID='" + id + "'";
			rec = dbObj.execute(sql);
			dbObj.stop();

			if (rec != null) {
				setAfrRate(getDouble(rec, "AFR_RATE"));
				setAfrDate(getDate(rec, "AFR_DATE"));
				setId(getLong(rec, "ID"));
				setNumTrusts(getInteger(rec, "TRUSTS"));
				setClientTermLength(getInteger(rec, "CLIENT_TERM"));
				setClientStartTerm(getInteger(rec, "CLIENT_START_TERM"));
				setSpouseTermLength(getInteger(rec, "SPOUSE_TERM"));
				setSpouseStartTerm(getInteger(rec, "SPOUSE_START_TERM"));
				setClientPriorGifts(getDouble(rec, "CLIENT_PRIOR_GIFTS"));
				setSpousePriorGifts(getDouble(rec, "SPOUSE_PRIOR_GIFTS"));
				setAnnuity(getDouble(rec, "ANNUITY"));
				setAnnuityFreq(getInteger(rec, "ANNUITY_FREQ"));
				setAnnuityIncrease(getDouble(rec, "ANNUITY_INCREASE"));
				setEstateTaxRate(getDouble(rec, "ESTATE_TAX_RATE"));
				setIncomeTaxRate(getDouble(rec, "INCOME_TAX_RATE"));
				setFinalDeath(getInteger(rec, "FINAL_DEATH"));
				setCalcType(getString(rec, "CALC_TYPE"));
				setRemainderInterest(getDouble(rec, "REMAINDER_INTEREST"));
				this.setUseLife(getBoolean(rec, "USE_INSURANCE"));
				if (isUseLife()) {
					this.setLifeCashValue(getDouble(rec, "LIFE_CASH_VALUE"));
					this.setLifeDeathBenefit(getDouble(rec,
							"LIFE_DEATH_BENEFIT"));
					this.setLifeInsPremium(getDouble(rec,
							"LIFE_INSURANCE_PREMIUM"));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#report()
	 */
	@Override
	public void report() {

	}

	/**
	 * @param afrDate
	 *            The afrDate to set.
	 */
	public void setAfrDate(String afrDate) {
		this.afrDate = afrDate;
	}

	/**
	 * @param afrRate
	 *            The afrRate to set.
	 */
	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}

	/**
	 * @param annuity
	 *            The annuity to set.
	 */
	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}

	/**
	 * @param annuityFreq
	 *            The annuityFreq to set.
	 */
	public void setAnnuityFreq(int annuityFreq) {
		this.annuityFreq = annuityFreq;
	}

	/**
	 * @param annuityIncrease
	 *            The annuityIncrease to set.
	 */
	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}

	/**
	 * @param annuityPaymentRate
	 *            The annuityPaymentRate to set.
	 */
	public void setAnnuityPaymentRate(double annuityPaymentRate) {
		this.annuityPaymentRate = annuityPaymentRate;
	}

	/**
	 * @param assetNames
	 *            The assetNames to set.
	 */
	public void setAssetNames(String assetNames) {
		this.assetNames = assetNames;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	/**
	 * @param clientAge
	 *            The clientAge to set.
	 */
	public void setClientAge(double clientAge) {
		this.clientAge = clientAge;
	}

	/**
	 * @param clientLifeExp
	 *            The clientLifeExp to set.
	 */
	public void setClientLifeExp(double clientLifeExp) {
		this.clientLifeExp = clientLifeExp;
	}

	/**
	 * @param clientPriorGifts
	 *            The clientPriorGifts to set.
	 */
	public void setClientPriorGifts(double clientPriorGifts) {
		this.clientPriorGifts = clientPriorGifts;
	}

	/**
	 * @param clientStartTerm
	 *            The clientStartTerm to set.
	 */
	public void setClientStartTerm(double clientStartTerm) {
		this.clientStartTerm = clientStartTerm;
	}

	/**
	 * @param clientTermLength
	 *            The clientTermLength to set.
	 */
	public void setClientTermLength(double clientTermLength) {
		this.clientTermLength = clientTermLength;
	}

	/**
	 * @param clientTrust
	 *            The clientTrust to set.
	 */
	public void setClientTrust(GratTrust clientTrust) {
		this.clientTrust = clientTrust;
	}

	/**
	 * @param discountedValue
	 *            The discountedValue to set.
	 */
	public void setDiscountedValue(double discountedValue) {
		this.discountedValue = discountedValue;
	}

	/**
	 * @param estateTaxRate
	 *            The estateTaxRate to set.
	 */
	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	/**
	 * @param finalDeath
	 *            The finalDeath to set.
	 */
	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param incomeTaxRate
	 *            The incomeTaxRate to set.
	 */
	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}

	public void setLifeCashValue(double lifeCashValue) {
		this.lifeCashValue = lifeCashValue;
	}

	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}

	public void setLifeInsPremium(double lifeInsPremium) {
		this.lifeInsPremium = lifeInsPremium;
	}

	/**
	 * @param numTrusts
	 *            The numTrusts to set.
	 */
	public void setNumTrusts(int numTrusts) {
		this.numTrusts = numTrusts;
	}

	/**
	 * @param rec
	 *            The rec to set.
	 */
	public void setRec(HashMap<String,Object> rec) {
		this.rec = rec;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	/**
	 * @param spouseAge
	 *            The spouseAge to set.
	 */
	public void setSpouseAge(double spouseAge) {
		this.spouseAge = spouseAge;
	}

	/**
	 * @param spouseLifeExp
	 *            The spouseLifeExp to set.
	 */
	public void setSpouseLifeExp(double spouseLifeExp) {
		this.spouseLifeExp = spouseLifeExp;
	}

	/**
	 * @param spousePriorGifts
	 *            The spousePriorGifts to set.
	 */
	public void setSpousePriorGifts(double spousePriorGifts) {
		this.spousePriorGifts = spousePriorGifts;
	}

	/**
	 * @param spouseStartTerm
	 *            The spouseStartTerm to set.
	 */
	public void setSpouseStartTerm(double spouseStartTerm) {
		this.spouseStartTerm = spouseStartTerm;
	}

	/**
	 * @param spouseTermLength
	 *            The spouseTermLength to set.
	 */
	public void setSpouseTermLength(double spouseTermLength) {
		this.spouseTermLength = spouseTermLength;
	}

	/**
	 * @param spouseTrust
	 *            The spouseTrust to set.
	 */
	public void setSpouseTrust(GratTrust spouseTrust) {
		this.spouseTrust = spouseTrust;
	}

	/**
	 * @param totalValue
	 *            The totalValue to set.
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	/**
	 * @param transferDate
	 *            The transferDate to set.
	 */
	public void setTransferDate(double transferDate) {
		this.transferDate = transferDate;
	}

	public void setUseLife(boolean useLife) {
		this.useLife = useLife;
	}

	/**
	 * @param uuid
	 *            The uuid to set.
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @param weightedGrowth
	 *            The weightedGrowth to set.
	 */
	public void setWeightedGrowth(double weightedGrowth) {
		this.weightedGrowth = weightedGrowth;
	}

	/**
	 * @param weightedIncome
	 *            The weightedIncome to set.
	 */
	public void setWeightedIncome(double weightedIncome) {
		this.weightedIncome = weightedIncome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("GRAT_TOOL");
		dbObj.clearFields();
		rec = null;
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("TRUSTS", getNumTrusts());
		dbAddField("CLIENT_TERM", getClientTermLength());
		dbAddField("CLIENT_START_TERM", getClientStartTerm());
		dbAddField("SPOUSE_TERM", getSpouseTermLength());
		dbAddField("SPOUSE_START_TERM", getSpouseStartTerm());
		dbAddField("CLIENT_PRIOR_GIFTS", getClientPriorGifts());
		dbAddField("SPOUSE_PRIOR_GIFTS", getClientPriorGifts());
		dbAddField("ANNUITY", getAnnuity());
		dbAddField("ANNUITY_FREQ", getAnnuityFreq());
		dbAddField("ANNUITY_INCREASE", getAnnuityIncrease());
		dbAddField("ESTATE_TAX_RATE", getEstateTaxRate());
		dbAddField("INCOME_TAX_RATE", getIncomeTaxRate());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("CALC_TYPE", getCalcType());
		dbAddField("REMAINDER_INTEREST", getRemainderInterest());
		if (isUseLife()) {
			dbAddField("USE_INSURANCE", "T");
			dbAddField("LIFE_INSURANCE_PREMIUM", getLifeInsPremium());
			dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
			dbAddField("LIFE_CASH_VALUE", getLifeCashValue());
		}
		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();

		dbObj.stop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.estate.EstatePlanningTool#writeupText()
	 */
	@Override
	public String writeupText() {
		return null;
	}
}
