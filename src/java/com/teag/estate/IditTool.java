/*
 * Created on May 3, 2005
 *
 */
package com.teag.estate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.estate.constants.ToolTableTypes;
import com.teag.webapp.EstatePlanningGlobals;
import com.zcalc.zCalc;

/**
 * @author Paul Stay
 *
 */
public class IditTool extends EstatePlanningTool {
	long id;
	String uuid;
	double gratTerm;
	int gratTrusts;
	double afrRate = 0;
	String afrDate;
	double annuity = 0;
	double annuityFreq = 0;
	double annuityIncrease = 0;
	double finalDeath = 0;
	boolean useInsurance = false;
	double lifePremium = 0;
	double lifeCashValue = 0;
	double lifeDeathBenefit = 0 ;
	double securitiesValue = 0;
	double stateIncomeTax = 0;
	double optimalPaymentRate = 0;
	double noteFlpGPInterest = 0;
	double noteFlpLPInterest = 0;
	double noteFlpDiscount = 0;
	double noteFlpToolId = 0;

	double rValue = 0;
	double rGrowth = 0;
	double rIncome = 0;
	double sValue = 0;
	double sGrowth = 0 ;
	double sIncome = 0;
	double noteValue = 0;
	double noteInterest = 0;
	double noteLength = 0;
	
	EstatePlanningGlobals estate;
	
	IditTrustTool trustTool;

	double iditTable[][];
	// Federal and Cap Gains tax rates 
	double capGainsPre = .15;
	double capGainsPost = .20;

	double fedTaxPre = .35;
	double fedTaxPost = .396;
	double turnover = .75;
	
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	Object assetData[];
	
	String assetNames;
	
	double[][] stdTable;
	
	boolean useLLC = false;
	
	public void buildBeforeTable() {

		IditAssets idAssets = new IditAssets();
		idAssets.setToolId(id);
		idAssets.setToolTableId(EstatePlanningTool.IDIT_TYPE);
		idAssets.setEstate(estate);
		idAssets.getOrig();
		ArrayList<AssetData> origData = idAssets.getOrigData();
		
		double reValue = 0;
		double reGrowth = 0;
		double reIncome = 0;
		double seValue = 0 ;
		double seGrowth = 0;
		double seIncome = 0;
		
		Iterator<AssetData> itr = origData.iterator();
		while(itr.hasNext()) {
			AssetData asset = itr.next();
			if( asset.getAssetType() == AssetData.OTHER) {
				reValue += asset.getValue();
				reIncome = asset.getIncome() * asset.getValue();
				reGrowth = asset.getGrowth() * asset.getValue();
			} else {
				seValue += asset.getValue();
				seGrowth += asset.getGrowth() * asset.getValue();
				seIncome += asset.getIncome() * asset.getValue();
			}
		}
		
		if( seValue != 0) {
			seGrowth = seGrowth / seValue;
			seIncome = seIncome / seValue;
			
		} else {
			seGrowth = 0;
			seIncome = 0;
			
		}
		if( reValue != 0) {
			reGrowth = reGrowth / reValue;
			reIncome = reIncome/ reValue;
		} else {
			reGrowth = 0;
			reIncome = 0;
		}
		
		int tableLength = 60;
		stdTable = new double[tableLength][11];
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		
		int year = cal.get(Calendar.YEAR);
		
		stdTable[0][0] = 1;
		stdTable[0][1] = year;
		stdTable[0][2] = reValue;
		stdTable[0][3] = seValue;
		stdTable[0][4] = reValue *(reIncome)* getIncomeTaxRate(year);
		stdTable[0][5] = seValue * seIncome * getIncomeTaxRate(year);
		stdTable[0][6] = seValue * getSecIncomeTaxRate(year,seGrowth);
		stdTable[0][7] = stdTable[0][2] + stdTable[0][3];
		// Calculate federal estate tax before array [9] so we don't need to use zcalc twice
		stdTable[0][9] = getFetTax(year);
		stdTable[0][8] = stdTable[0][9] * stdTable[0][7];
		stdTable[0][10] = stdTable[0][7]- stdTable[0][8];
		year++;

		for( int i = 1; i < stdTable.length; i++) {
			stdTable[i][0] = i+1;
			stdTable[i][1] = year;
			stdTable[i][2] = stdTable[i-1][2] * (1 + reGrowth);
			stdTable[i][3] = stdTable[i-1][3] + stdTable[i-1][4] + stdTable[i-1][5] + stdTable[i-1][6];
			stdTable[i][4] = stdTable[i][2] * (reIncome) * getIncomeTaxRate(year);
			stdTable[i][5] = stdTable[i][3] * seIncome * getIncomeTaxRate(year);
			stdTable[i][6] = stdTable[i][3] * getSecIncomeTaxRate(year,seGrowth);
			stdTable[i][7] = stdTable[i][2] + stdTable[i-1][3];
			// Calculate federal estate tax before array [9] so we don't need to use zcalc twice
			stdTable[i][9] = getFetTax(year);
			stdTable[i][8] = stdTable[i][9] * stdTable[i][7];
			stdTable[i][10] = stdTable[i][7]- stdTable[i][8];
			year++;
		}
	}

	@Override
	public void calculate() {
		
		IditAssets idAssets = new IditAssets();
		idAssets.setToolId(id);
		idAssets.setToolTableId(EstatePlanningTool.IDIT_TYPE);
		idAssets.setEstate(estate);
		idAssets.calculate();
		if( idAssets.isUseLLC()){
			useLLC = true;
		}
		
		if( idAssets.isUseFLP()) {
			useLLC = false;
		}
		
		ArrayList<AssetData> al = idAssets.getAssetData();
		
		Iterator<AssetData> itr = al.iterator();
		
		rValue = 0;
		rIncome = 0;
		rGrowth = 0;
		sValue = 0;
		sIncome = 0;
		sGrowth = 0;
		noteValue = 0;
		boolean addSec = false;
		StringBuffer sb = new StringBuffer("");
		
		while(itr.hasNext()) {
			AssetData asset = itr.next();
			if( asset.getAssetType() == AssetData.OTHER) {
				rValue += asset.getValue();
				rIncome = asset.getIncome() * asset.getValue();
				rGrowth = asset.getGrowth() * asset.getValue();
				noteValue += asset.getValue() * ( 1 - asset.getDiscountRate());
				if( sb.length() > 0)
					sb.append(", " +asset.getName());
				else 
					sb.append(asset.getName());
			} else {
				sValue += asset.getValue();
				sGrowth += asset.getGrowth() * asset.getValue();
				sIncome += asset.getIncome() * asset.getValue();
				addSec = true;
			}
		}
		
		if( addSec)
				assetNames = sb.toString() + " Securities, ";
		
		if( rValue != 0) {
			rIncome = rIncome/rValue;
			rGrowth = rGrowth/rValue;			
		} else {
			rIncome = 0;
			rGrowth = 0;
		}

		if( sValue != 0) {
			sGrowth = sGrowth/sValue;
			sIncome = sIncome/sValue;
		} else {
			sGrowth = 0;
			sIncome = 0;
		}
		
		// Create trust tool
		trustTool = new IditTrustTool();
		trustTool.setId(id);
		
		trustTool.setSecGrowth(sGrowth);
		trustTool.setSecInterest(sIncome);
		
		trustTool.setNoteInterest(noteInterest);
		trustTool.setNoteValue(noteValue);
		trustTool.setOptimalGratRate(getOptimalPaymentRate());
		
		trustTool.setNoteFlpDiscount(this.getNoteFlpDiscount());
		trustTool.setNoteFlpGPInterest(this.getNoteFlpGPInterest());
		trustTool.setNoteFlpLPInterest(this.getNoteFlpLPInterest());
		
		trustTool.setAfrDate(getAfrDate());
		trustTool.setAfrRate(getAfrRate());
		trustTool.setAnnuity(getAnnuity());
		trustTool.setAnnuityFreq((int)getAnnuityFreq());
		trustTool.setTerm((int)getGratTerm());
		trustTool.setNumTrusts(getGratTrusts());
		trustTool.setStateIncomeTax(getStateIncomeTax());
		trustTool.setAssetOrigGrowth(rGrowth);
		trustTool.setAssetOrigIncome(rIncome);
		trustTool.setAssetOrigValue(rValue);
		trustTool.setSecOrigValue(sValue);
		
		trustTool.calculate();
		
		iditTable = trustTool.getIditTable();

	}
	
	/*
	<Table name='IDIT_TOOL'>
		<Field name='ID' size='19' type='long' autoincrement='true' valid='true'/>
		<Field name='NOTE_INTEREST' type='number' size='12,2' null='false'/>
		<Field name='NOTE_LENGTH' type='number' size='2' null='false'/>
		<Field name='GRAT_TERM' type='number' size='2' null='false'/>
		<Field name='AFR_RATE' type='number' size='2,6' null='false'/>
		<Field name='AFR_DATE' type='date' null='false'/>
		<Field name='ANNUITY' type='number' size='12,2' null='false' />
		<Field name='ANNUITY_FREQ' type='number' size='12,2' null='false' />
		<Field name='ANNUITY_INCREASE' type='number' size='12,2' null='false' />
		<Field name='FINAL_DEATH' type='number' size='2' null='true'/>
		<Field name='USE_INSURANCE' type='string' size='1' null='true'/>
		<Field name='LIFE_INSURANCE_PREMIUM' type='number' size='12,2' null='true'/>
		<Field name='LIFE_DEATH_BENEFIT' type='number' size='14,2' null='true'/>
		<Field name='LIFE_CASH_VALUE' type='number' size='14,2' null='true'/>
		<Field name='SECURITIES_VALUE' type='number' size='12,2' null='false'/>
		<Field name='STATE_INCOME_TAX' type='number' size='2,4' null='false'/>
		<Field name='OPTIMAL_PAYMENT_RATE' type='number' size='2,6' null='false'/>
		<Field name='NOTE_FLP_ID' type='long' size='19'/>
		<PrimaryKey field='ID'/>
	</Table>
	*/
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("IDIT_TOOL", "ID='" + id + "'");
		dbObj.stop();
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
	public double getAnnuityFreq() {
		return annuityFreq;
	}
	public double getAnnuityIncrease() {
		return annuityIncrease;
	}
	public Object[] getAssetData() {
		return assetData;
	}
	public ArrayList<AssetData> getAssetList() {
		return assetList;
	}
	public String getAssetNames() {
		return assetNames;
	}
	public double getCapGainsPost() {
		return capGainsPost;
	}
	public double getCapGainsPre() {
		return capGainsPre;
	}
	public EstatePlanningGlobals getEstate() {
		return estate;
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
		double fet = zc.zFETMAXRATE(year, 0);
		zc.ShutDown();
		return fet;
	}
	public double getFinalDeath() {
		return finalDeath;
	}
	public double getGratTerm() {
		return gratTerm;
	}
	public int getGratTrusts() {
		return gratTrusts;
	}
	public long getId() {
		return id;
	}
	public double[][] getIditTable() {
		return iditTable;
	}
	public double getIncomeTaxRate(int year) {
		double rate = 0;
		if( year < 2011) {
			rate = 1 - ((( 1 - fedTaxPre) * stateIncomeTax) + fedTaxPre);
		}else {
			rate = 1 - ((( 1 - fedTaxPost) * stateIncomeTax)+ fedTaxPost);
		}
		return rate;
	}
	public double getLifeCashValue() {
		return lifeCashValue;
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
	public double getNoteInterest() {
		return noteInterest;
	}
	public double getNoteLength() {
		return noteLength;
	}
	public double getNoteValue() {
		return noteValue;
	}
	public double getOptimalPaymentRate() {
		return optimalPaymentRate;
	}
	public double getRGrowth() {
		return rGrowth;
	}
	public double getRIncome() {
		return rIncome;
	}
	public double getRValue() {
		return rValue;
	}
	public double getSecIncomeTaxRate(int year, double growth) {
		double rate = 0;
		rate = growth * turnover * getIncomeTaxRate(year);
		if( year < 2011)
			rate += growth * (1 - turnover) * getIncomeTaxRate(year) * ( 1- capGainsPre);
		else
			rate += growth * (1 - turnover) * getIncomeTaxRate(year) * ( 1 - capGainsPost);
		return rate;
	}
	public double getSecuritiesValue() {
		return securitiesValue;
	}
	public double getSGrowth() {
		return sGrowth;
	}
	public double getSIncome() {
		return sIncome;
	}
	public double getStateIncomeTax() {
		return stateIncomeTax;
	}
	public double[][] getStdTable() {
		return stdTable;
	}
	public double getSValue() {
		return sValue;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#getToolTableId()
	 */
	@Override
	public long getToolTableId() {
		return ToolTableTypes.IDIT.id();
	}
	public IditTrustTool getTrustTool() {
		return trustTool;
	}

	public double getTurnover() {
		return turnover;
	}

	public String getUuid() {
		return uuid;
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable("IDIT_TOOL");
		dbObj.clearFields();
		// First create the note flp!
		FlpTool noteFlp = new FlpTool();
		noteFlp.setDbObject();
		noteFlp.setGeneralPartnerShares(noteFlpGPInterest);
		noteFlp.setLimitedPartnerShares(noteFlpLPInterest);
		noteFlp.setDiscountRate(noteFlpDiscount);
		noteFlp.setValue(0); // Implied note
		noteFlp.insert();
		
		dbAddField("NOTE_INTEREST", this.getNoteInterest());
		dbAddField("NOTE_LENGTH", this.getNoteLength());
        dbAddField("GRAT_TERM", this.getGratTerm());
        dbAddField("GRAT_TRUSTS", this.getGratTrusts());
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("ANNUITY", getAnnuity());
		dbAddField("ANNUITY_FREQ", getAnnuityFreq());
		dbAddField("ANNUITY_INCREASE", getAnnuityIncrease());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("USE_INSURANCE", isUseInsurance());
		dbAddField("LIFE_INSURANCE_PREMIUM", getLifePremium());
		dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
		dbAddField("LIFE_CASH_VALUE", getLifeCashValue());
		dbAddField("SECURITIES_VALUE", getSecuritiesValue());
		dbAddField("OPTIMAL_PAYMENT_RATE", getOptimalPaymentRate());
		dbAddField("NOTE_FLP_ID", noteFlp.getId());
        dbAddField("STATE_INCOME_TAX", getStateIncomeTax());
		
		int error = dbObj.insert();
		if( error == 0 ) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from IDIT_TOOL where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			if( o != null) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();
	}

	public boolean isUseInsurance() {
		return useInsurance;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#read()
	 */
	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("IDIT_TOOL");
			dbObj.clearFields();
			HashMap<String,Object> rec = null;
			String sql = "select * from IDIT_TOOL where ID='" + id + "'";
			rec = dbObj.execute(sql);
			dbObj.stop();
			
			if( rec != null ) {
				setId(getLong(rec,"ID"));
				setNoteInterest(getDouble(rec,"NOTE_INTEREST"));
				setNoteLength(getInteger(rec,"NOTE_LENGTH"));
				setGratTerm(getDouble(rec, "GRAT_TERM"));
                setGratTrusts(getInteger(rec,"GRAT_TRUSTS"));
				setAfrRate(getDouble(rec,"AFR_RATE"));
				setAfrDate(getDate(rec,"AFR_DATE"));
				setAnnuity(getDouble(rec,"ANNUITY"));
				this.setAnnuitFreq(getDouble(rec,"ANNUITY_FREQ"));
				setAnnuityIncrease(getDouble(rec,"ANNUITY_INCREASE"));
				setFinalDeath(getInteger(rec,"FINAL_DEATH"));
				setUseInsurance(getBoolean(rec,"USE_INSURANCE"));
				setLifePremium(getDouble(rec,"LIFE_INSURANCE_PREMIUM"));
				setLifeDeathBenefit(getDouble(rec,"LIFE_DEATH_BENEFIT"));
				setLifeCashValue(getDouble(rec,"LIFE_CASH_VALUE"));
				setSecuritiesValue(getDouble(rec,"SECURITIES_VALUE"));
				setOptimalPaymentRate(getDouble(rec,"OPTIMAL_PAYMENT_RATE"));
				setNoteFlpToolId(getLong(rec,"NOTE_FLP_ID"));
                setStateIncomeTax(getDouble(rec,"STATE_INCOME_TAX"));
			}
		}
		if( noteFlpToolId > 0) {
			FlpTool noteFlp = new FlpTool();
			noteFlp.setDbObject();
			noteFlp.setId((long)noteFlpToolId);
			noteFlp.read();
			noteFlpGPInterest = noteFlp.getGeneralPartnerShares();
			noteFlpLPInterest = noteFlp.getLimitedPartnerShares();
			noteFlpDiscount = noteFlp.getDiscountRate();
		}
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
	public void setAnnuitFreq(double annuityFreq) {
		this.annuityFreq = annuityFreq;
	}
	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}
	public void setAnnuityFreq(double annuityFreq) {
		this.annuityFreq = annuityFreq;
	}
	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}
	public void setAssetData(Object[] assetData) {
		this.assetData = assetData;
	}
	public void setAssetList(ArrayList<AssetData> assetList) {
		this.assetList = assetList;
	}
	public void setAssetNames(String assetNames) {
		this.assetNames = assetNames;
	}
	public void setCapGainsPost(double capGainsPost) {
		this.capGainsPost = capGainsPost;
	}
	public void setCapGainsPre(double capGainsPre) {
		this.capGainsPre = capGainsPre;
	}
	public void setEstate(EstatePlanningGlobals estate) {
		this.estate = estate;
	}	public void setFedTaxPost(double fedTaxPost) {
		this.fedTaxPost = fedTaxPost;
	}
	public void setFedTaxPre(double fedTaxPre) {
		this.fedTaxPre = fedTaxPre;
	}
	public void setFinalDeath(double finalDeath) {
		this.finalDeath = finalDeath;
	}
	public void setGratTerm(double gratTerm) {
		this.gratTerm = gratTerm;
	}
	public void setGratTrusts(int gratTrusts) {
		this.gratTrusts = gratTrusts;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setIditTable(double[][] iditTable) {
		this.iditTable = iditTable;
	}
	public void setLifeCashValue(double lifeCashValue) {
		this.lifeCashValue = lifeCashValue;
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
	public void setNoteInterest(double noteInterest) {
		this.noteInterest = noteInterest;
	}
	public void setNoteLength(double noteLength) {
		this.noteLength = noteLength;
	}
	public void setNoteValue(double noteValue) {
		this.noteValue = noteValue;
	}
	public void setOptimalPaymentRate(double optimalPaymentRate) {
		this.optimalPaymentRate = optimalPaymentRate;
	}
	public void setRGrowth(double growth) {
		rGrowth = growth;
	}
	public void setRIncome(double income) {
		rIncome = income;
	}
	public void setRValue(double value) {
		rValue = value;
	}
	public void setSecuritiesValue(double securitiesValue) {
		this.securitiesValue = securitiesValue;
	}
	public void setSGrowth(double growth) {
		sGrowth = growth;
	}
	public void setSIncome(double income) {
		sIncome = income;
	}
	public void setStateIncomeTax(double stateIncomeTax) {
		this.stateIncomeTax = stateIncomeTax;
	}
	public void setStdTable(double[][] stdTable) {
		this.stdTable = stdTable;
	}
	public void setSValue(double value) {
		sValue = value;
	}
	public void setTrustTool(IditTrustTool trustTool) {
		this.trustTool = trustTool;
	}
	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}
	public void setUseInsurance(boolean useInsurance) {
		this.useInsurance = useInsurance;
	}
	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("IDIT_TOOL");
		dbObj.clearFields();
		
		//	 First create the note flp!
		FlpTool noteFlp = new FlpTool();
		noteFlp.setDbObject();
		noteFlp.setGeneralPartnerShares(noteFlpGPInterest);
		noteFlp.setLimitedPartnerShares(noteFlpLPInterest);
		noteFlp.setDiscountRate(noteFlpDiscount);
		noteFlp.setValue(0); // Implied note
		noteFlp.setId((long)getNoteFlpToolId());
		noteFlp.update();

		dbAddField("NOTE_INTEREST", this.getNoteInterest());
		dbAddField("NOTE_LENGTH", this.getNoteLength());
		dbAddField("GRAT_TERM", this.getGratTerm());
        dbAddField("GRAT_TRUSTS", this.getGratTrusts());
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("ANNUITY", getAnnuity());
		dbAddField("ANNUITY_FREQ", getAnnuityFreq());
		dbAddField("ANNUITY_INCREASE", getAnnuityIncrease());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("USE_INSURANCE", isUseInsurance());
		dbAddField("LIFE_INSURANCE_PREMIUM", getLifePremium());
		dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
		dbAddField("LIFE_CASH_VALUE", getLifeCashValue());
		dbAddField("SECURITIES_VALUE", getSecuritiesValue());
		dbAddField("OPTIMAL_PAYMENT_RATE", getOptimalPaymentRate());
		dbAddField("NOTE_FLP_ID", getNoteFlpToolId());
        dbAddField("STATE_INCOME_TAX", getStateIncomeTax());


		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();

		dbObj.stop();

	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#writeupText()
	 */
	@Override
	public String writeupText() {
		return null;
	}
}
