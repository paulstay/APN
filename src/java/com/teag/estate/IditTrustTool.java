package com.teag.estate;

/**
 * @author stay
 * Created on May 9, 2005
 * Description keep the grats, and securitiy calculations here so we can distinguish the 
 * difference between the IDIT flp and the note flip
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.zcalc.zCalc;

public class IditTrustTool extends EstatePlanningTool {
	long id;
	String uuid;
	
	double  assetOrigValue;
	double 	assetOrigGrowth;
	double 	assetOrigIncome;
	double 	secOrigValue;
	double 	noteValue;
	double 	noteInterest;
	double 	noteGrowth;
	double 	secValue;
	double 	secInterest;
	double 	secGrowth;
	int 	term;
	int 	numTrusts;
	double 	lifePremium;
	double 	lifeDeathBenefit;
	double	afrRate;
	String	afrDate;
	double 	gratPayment;
	double	optimalGratRate;
	double  remainderPaymentRate;
	double  remainderInterest;
	double 	annuity;
	int 	annuityFreq;

	// we may not need this.....
	double noteFlpGPInterest;
	double noteFlpLPInterest;
	double noteFlpDiscount;
	double noteFlpToolId;
	
	//	 Federal and Cap Gains tax rates 
	double 	capGainsPre = .15;
	double 	capGainsPost = .20;
	double 	fedTaxPre = .35;
	double 	fedTaxPost = .396;	

	double stateIncomeTax;
	
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	Object assetData[];
	
	GratTrustTool clientGratTrust;
	GratTrustTool spouseGratTrust;
	
	int finalDeath = 25;
	
	double iditTable[][];
	
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#calculate()
	 */
	@Override
	public void calculate() {
		
		int tableLength = 60;
		iditTable = new double[tableLength][11];
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		
		double gratValue = (noteValue + secOrigValue) * (1 - noteFlpDiscount) * noteFlpLPInterest;

		zCalc zc = new zCalc();
		zc.StartUp();
		optimalGratRate = zc.zGRATZO(afrRate, term, 0,4,0);
		remainderInterest = 45000;
		remainderPaymentRate = zc.zANNRATETARGET(gratValue, remainderInterest, afrRate, term, 0, 0,0,
				0,0,0,1,0,0,0,0); 
		zc.ShutDown();
		
		gratPayment = gratValue * remainderPaymentRate;
		double assetPay = noteValue * noteFlpLPInterest * noteInterest;
		double secPay = gratPayment - assetPay;
		double taxableEstate = gratValue - secPay;
		
		int year = cal.get(Calendar.YEAR);
		
		
		double fet = getFetTax(year);
		
		iditTable[0][0] = 1;
		iditTable[0][1] = year++;
		iditTable[0][2] = assetOrigValue;		// Original Asset (real estate value)
		iditTable[0][3] = assetPay;			// Calculated GratPayment of real estate;
		iditTable[0][4] = secOrigValue;			// Original Securities value we will be zeroint it out
		iditTable[0][5] = secOrigValue * secGrowth;
		iditTable[0][6] = secPay;
		iditTable[0][7] = taxableEstate;
		iditTable[0][8] = taxableEstate * fet;
		iditTable[0][9] = fet;
		iditTable[0][10] = iditTable[0][2] + iditTable[0][4] + iditTable[0][5] 
										- iditTable[0][6] - iditTable[0][8];
		
		for( int i = 1; i < tableLength; i++) {
			
			
			fet = getFetTax(year);
			iditTable[i][0] = i+1;
			iditTable[i][1] = year++;
			iditTable[i][2] = iditTable[i-1][2] * (1 + (assetOrigGrowth + assetOrigIncome)) - assetPay;
			double sTmp =  iditTable[i-1][4] + iditTable[i-1][5] - iditTable[i-1][6];
			if( sTmp < 0)
				sTmp = 0;
			iditTable[i][4] = sTmp;
			iditTable[i][5] = iditTable[i][4] * secGrowth;
			if( i > getTerm()-1){
				iditTable[i][3] = 0;
				iditTable[i][6] = 0;
				iditTable[i][7] = 0;
			} else {
				iditTable[i][3] = assetPay;
				iditTable[i][6] = secPay;
				iditTable[i][7] = iditTable[i-1][7] - secPay;
			}
			iditTable[i][8] = iditTable[i][7] * fet;
			iditTable[i][9] = fet;
			iditTable[i][10] = iditTable[i][2] + iditTable[i][4] + iditTable[i][5] 
									- iditTable[i][6] - iditTable[i][8];
			if( i > getTerm()-1) {
				assetPay = 0;
				secPay = 0;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {

	}

	public String getAfrDate() {
		return afrDate;
	}

	public double getAfrRate() {
		return afrRate;
	}

	public double getAnnuity() {
		return annuity;
	}

	public int getAnnuityFreq() {
		return annuityFreq;
	}

	public Object[] getAssetData() {
		return assetData;
	}

	public ArrayList<AssetData> getAssetList() {
		return assetList;
	}

	public double getAssetOrigGrowth() {
		return assetOrigGrowth;
	}

	public double getAssetOrigIncome() {
		return assetOrigIncome;
	}
	public double getAssetOrigValue() {
		return assetOrigValue;
	}
	public double getCapGainsPost() {
		return capGainsPost;
	}
	public double getCapGainsPre() {
		return capGainsPre;
	}
	public GratTrustTool getClientGratTrust() {
		return clientGratTrust;
	}
	public double getFedTaxPost() {
		return fedTaxPost;
	}
	public double getFedTaxPre() {
		return fedTaxPre;
	}
	public double getFetTax(int year) {
		zCalc zc = new zCalc();
		zc.StartUp();
		double rate = zc.zFETMAXRATE(year,0);
		zc.ShutDown();
		return rate;
	}
	public int getFinalDeath() {
		return finalDeath;
	}
	public double getGratPayment() {
		return gratPayment;
	}
	public long getId() {
		return id;
	}
	public double[][] getIditTable() {
		return iditTable;
	}
	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}
	public double getLifePremium() {
		return lifePremium;
	}
	public double getNoteFlpDiscount() {
		return noteFlpDiscount;
	}
	public double getNoteFlpGPInterest() {
		return noteFlpGPInterest;
	}
	public double getNoteFlpLPInterest() {
		return noteFlpLPInterest;
	}
	public double getNoteFlpToolId() {
		return noteFlpToolId;
	}
	public double getNoteGrowth() {
		return noteGrowth;
	}
	public double getNoteInterest() {
		return noteInterest;
	}
	public double getNoteValue() {
		return noteValue;
	}
	public int getNumTrusts() {
		return numTrusts;
	}
	public double getOptimalGratRate() {
		return optimalGratRate;
	}
	public double getSecGrowth() {
		return secGrowth;
	}
	public double getSecInterest() {
		return secInterest;
	}
	public double getSecOrigValue() {
		return secOrigValue;
	}
	public double getSecValue() {
		return secValue;
	}
	public GratTrustTool getSpouseGratTrust() {
		return spouseGratTrust;
	}
	public double getStateIncomeTax() {
		return stateIncomeTax;
	}
	public int getTerm() {
		return term;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#getToolTableId()
	 */
	@Override
	public long getToolTableId() {
		return EstatePlanningTool.IDIT_TRUST_TYPE;
	}
	public String getUuid() {
		return uuid;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {

	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#read()
	 */
	@Override
	public void read() {

	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#report()
	 */
	@Override
	public void report() {

	}
	public void setAfrDate(String afrDate) {
		this.afrDate = afrDate;
	}
	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}
	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}
	public void setAnnuityFreq(int annuityFreq) {
		this.annuityFreq = annuityFreq;
	}
	public void setAssetData(Object[] assetData) {
		this.assetData = assetData;
	}
	public void setAssetList(ArrayList<AssetData> assetList) {
		this.assetList = assetList;
	}
	public void setAssetOrigGrowth(double assetOrigGrowth) {
		this.assetOrigGrowth = assetOrigGrowth;
	}
	public void setAssetOrigIncome(double assetOrigIncome) {
		this.assetOrigIncome = assetOrigIncome;
	}
	public void setAssetOrigValue(double assetOrigValue) {
		this.assetOrigValue = assetOrigValue;
	}
	public void setCapGainsPost(double capGainsPost) {
		this.capGainsPost = capGainsPost;
	}
	public void setCapGainsPre(double capGainsPre) {
		this.capGainsPre = capGainsPre;
	}
	public void setClientGratTrust(GratTrustTool clientGratTrust) {
		this.clientGratTrust = clientGratTrust;
	}
	public void setFedTaxPost(double fedTaxPost) {
		this.fedTaxPost = fedTaxPost;
	}
	public void setFedTaxPre(double fedTaxPre) {
		this.fedTaxPre = fedTaxPre;
	}
	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}
	public void setGratPayment(double gratPayment) {
		this.gratPayment = gratPayment;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setIditTable(double[][] iditTable) {
		this.iditTable = iditTable;
	}
	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}
	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
	}
	public void setNoteFlpDiscount(double noteFlpDiscount) {
		this.noteFlpDiscount = noteFlpDiscount;
	}
	public void setNoteFlpGPInterest(double noteFlpGPInterest) {
		this.noteFlpGPInterest = noteFlpGPInterest;
	}
	public void setNoteFlpLPInterest(double noteFlpLPInterest) {
		this.noteFlpLPInterest = noteFlpLPInterest;
	}
	public void setNoteFlpToolId(double noteFlpToolId) {
		this.noteFlpToolId = noteFlpToolId;
	}
	public void setNoteGrowth(double noteGrowth) {
		this.noteGrowth = noteGrowth;
	}
	public void setNoteInterest(double noteInterest) {
		this.noteInterest = noteInterest;
	}
	public void setNoteValue(double noteValue) {
		this.noteValue = noteValue;
	}

	public void setNumTrusts(int numTrusts) {
		this.numTrusts = numTrusts;
	}
	public void setOptimalGratRate(double optimalGratRate) {
		this.optimalGratRate = optimalGratRate;
	}
	public void setSecGrowth(double secGrowth) {
		this.secGrowth = secGrowth;
	}
	public void setSecInterest(double secInterest) {
		this.secInterest = secInterest;
	}
	public void setSecOrigValue(double secOrigValue) {
		this.secOrigValue = secOrigValue;
	}
	public void setSecValue(double secValue) {
		this.secValue = secValue;
	}
	public void setSpouseGratTrust(GratTrustTool spouseGratTrust) {
		this.spouseGratTrust = spouseGratTrust;
	}
	public void setStateIncomeTax(double stateIncomeTax) {
		this.stateIncomeTax = stateIncomeTax;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {

	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#writeupText()
	 */
	@Override
	public String writeupText() {
		return null;
	}
}
