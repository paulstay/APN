package com.teag.estate;

import java.util.HashMap;

import com.estate.constants.ToolTableTypes;

/**
 * @author stay
 * Created on Apr 1, 2005
 * Description - Qualified Personal Residence Trust
 * Used to pass a residence to a trust and save lots of Estate Taxes.
 */
public class QprtTool extends EstatePlanningTool {
	
	public final static int RESIDENCE = 0;
	public final static int VACATION = 1;
	public final static int OTHER = 2;
	
	private long id;
	private String clientFirstName;
	private String clientDateOfBirth;
	private double clientAge;
	private double clientLifeExp;
	private String spouseFirstName;
	private String spouseDateOfBirth;
	private double spouseAge;
	private double spouseLifeExp;
	private int numberOfTrusts; // If 1 use only client information, otherwise craete two sub trusts
	private int clientTerm;
	private int clientStartTerm;
	private int spouseTerm;
	private int spouseStartTerm;
	private double fractionalInterestDiscount;
	private double afrRate;
	private String afrDate;
	private double estateTaxRate;
	private double rentAfterTerm;
	private double rentGrowthRateForHeirs;
	private double clientPriorGifts;
	private double spousePriorGifts;
	private int typeOfHome;
	private double value;
	private double basis;
	private double growth;
	private QprtTrustTool clientQprt;
	private QprtTrustTool spouseQprt;
	private int finalDeath = 0;
	private boolean revisionRetained = true;
	private String assetName;
	
	ToolAssetData assetData[];
	
	private HashMap<String,Object> account;
	private String uuid;
	@Override
	public void calculate() {
		ToolAssetDistribution tad = new ToolAssetDistribution();
		tad.setDbObject();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());
		tad.calculate();
		assetData = tad.getToolAssetData();

		value = tad.getTotalValue();
		basis = tad.getBasis();
		growth = tad.getWeightedGrowth();
		String tempName = tad.getAssetNames();
		int l = tempName.lastIndexOf(",");
		assetName = tempName.substring(0,l);

		clientQprt = null;
		clientQprt = new QprtTrustTool();

		// Setup client trust first.
		clientQprt = new QprtTrustTool();
		clientQprt.setAfrRate(afrRate);
		clientQprt.setAfrDate(afrDate);
		clientQprt.setBasis(basis);
		if( numberOfTrusts == 2) {
			clientQprt.setValue(value/2.0);
			clientQprt.setBasis(basis/2);
		} else {
			clientQprt.setValue(value);
			clientQprt.setBasis(basis);
		}
		clientQprt.setGrowthRate(growth);
		clientQprt.setClientAge((int) clientAge);
		clientQprt.setTermLength(clientTerm);
		clientQprt.setStartTerm(clientStartTerm);
		clientQprt.setEstateTaxSavings(estateTaxRate);
		clientQprt.setFractionalDiscount(fractionalInterestDiscount);
		clientQprt.setLifeExp(clientLifeExp);
		clientQprt.setReversionRetained(isRevisionRetained());
		clientQprt.calculate();

		spouseQprt = null;		

		if( numberOfTrusts > 1) {
			spouseQprt = new QprtTrustTool();		
			spouseQprt.setAfrDate(afrDate);
			spouseQprt.setAfrRate(afrRate);
			spouseQprt.setBasis(basis/2);
			spouseQprt.setGrowthRate(growth);
			spouseQprt.setValue(value/2.0);	// Always will be half
			spouseQprt.setClientAge((int)spouseAge);
			spouseQprt.setTermLength(spouseTerm);
			spouseQprt.setStartTerm(spouseStartTerm);
			spouseQprt.setEstateTaxSavings(estateTaxRate);
			spouseQprt.setFractionalDiscount(fractionalInterestDiscount);
			spouseQprt.setLifeExp(spouseLifeExp);
			spouseQprt.setReversionRetained(isRevisionRetained());
			spouseQprt.calculate();
		}
	}
	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("QPRT_TOOL", "ID='" + id + "'");
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

	public String getAssetName() {
		return assetName;
	}
	
	/**
	 * @return Returns the basis.
	 */
	public double getBasis() {
		return basis;
	}

	/**
	 * @return Returns the clientAge.
	 */
	public double getClientAge() {
		return clientAge;
	}


	/**
	 * @return Returns the clientDateOfBirth.
	 */
	public String getClientDateOfBirth() {
		return clientDateOfBirth;
	}

	/**
	 * @return Returns the clientFirstName.
	 */
	public String getClientFirstName() {
		return clientFirstName;
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
	 * @return Returns the clientQprt.
	 */
	public QprtTrustTool getClientQprt() {
		return clientQprt;
	}
	/**
	 * @return Returns the clientStartTerm.
	 */
	public int getClientStartTerm() {
		return clientStartTerm;
	}
	/**
	 * @return Returns the clientTerm.
	 */
	public int getClientTerm() {
		return clientTerm;
	}
	/**
	 * @return Returns the estateTaxRate.
	 */
	public double getEstateTaxRate() {
		return estateTaxRate;
	}
	/**
	 * @return Returns the finalDeath.
	 */
	public int getFinalDeath() {
		return finalDeath;
	}
	/**
	 * @return Returns the fractionalInterestDisccount.
	 */
	public double getFractionalInterestDiscount() {
		return fractionalInterestDiscount;
	}
	/**
	 * @return Returns the growth.
	 */
	public double getGrowth() {
		return growth;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the numberOfTrusts.
	 */
	public int getNumberOfTrusts() {
		return numberOfTrusts;
	}
	/**
	 * @return Returns the rentAfterTerm.
	 */
	public double getRentAfterTerm() {
		return rentAfterTerm;
	}
	/**
	 * @return Returns the rentGrowthRateForHeirs.
	 */
	public double getRentGrowthRateForHeirs() {
		return rentGrowthRateForHeirs;
	}
	/**
	 * @return Returns the spouseAge.
	 */
	public double getSpouseAge() {
		return spouseAge;
	}
	/**
	 * @return Returns the spouseDateOfBirth.
	 */
	public String getSpouseDateOfBirth() {
		return spouseDateOfBirth;
	}
	/**
	 * @return Returns the spouseFirstName.
	 */
	public String getSpouseFirstName() {
		return spouseFirstName;
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
	 * @return Returns the spouseQprt.
	 */
	public QprtTrustTool getSpouseQprt() {
		return spouseQprt;
	}
	/**
	 * @return Returns the spouseStartTerm.
	 */
	public int getSpouseStartTerm() {
		return spouseStartTerm;
	}
	/**
	 * @return Returns the spouseTerm.
	 */
	public int getSpouseTerm() {
		return spouseTerm;
	}
	@Override
	public long getToolTableId() {
		return ToolTableTypes.QPRT.id();
	}
	/**
	 * @return Returns the typeOfHome.
	 */
	public int getTypeOfHome() {
		return typeOfHome;
	}
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}
	@Override
	public void insert(){
		dbObj.start();
		dbObj.setTable("QPRT_TOOL");
		account = null;
		dbObj.clearFields();
		
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("TRUSTS", getNumberOfTrusts());
		dbAddField("CLIENT_TERM", getClientTerm());
		dbAddField("CLIENT_START_TERM", getClientStartTerm());
		dbAddField("SPOUSE_TERM", getSpouseTerm());
		dbAddField("SPOUSE_START_TERM", getSpouseStartTerm());
		dbAddField("FRACTIONAL_DISCOUNT", getFractionalInterestDiscount());
		dbAddField("ESTATE_TAX_RATE", getEstateTaxRate());
		dbAddField("RENT_GROWTH_RATE", getRentGrowthRateForHeirs());
		dbAddField("RENT_AFTER_TERM", getRentAfterTerm());
		dbAddField("CLIENT_PRIOR_GIFTS", getClientPriorGifts());
		dbAddField("SPOUSE_PRIOR_GIFTS", getSpousePriorGifts());
		dbAddField("REVISION_RETAINED", isRevisionRetained() ? "Y" : "F");
		dbAddField("FINAL_DEATH", getFinalDeath());

		int error = dbObj.insert();
		if( error == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from QPRT_TOOL where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			if ( o != null)
				id = Integer.parseInt(o.toString());
		}
		dbObj.stop();
	}
	/**
	 * @return Returns the revisionRetained.
	 */
	public boolean isRevisionRetained() {
		return revisionRetained;
	}
	@Override
	public void read(){
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("QPRT_TOOL");
			account = null;
			String sql = "select * from QPRT_TOOL where ID='"+ id +"'";
			account = dbObj.execute(sql);
			dbObj.stop();
			
			if(account != null) {
				setAfrDate(getDate(account,"AFR_DATE"));
				setAfrRate(getDouble(account,"AFR_RATE"));
				setId(getLong(account,"ID"));
				setNumberOfTrusts(getInteger(account,"TRUSTS"));
				setClientTerm(getInteger(account,"CLIENT_TERM"));
				setClientStartTerm(getInteger(account,"CLIENT_START_TERM"));
				setSpouseTerm(getInteger(account,"SPOUSE_TERM"));
				setSpouseStartTerm(getInteger(account,"SPOUSE_START_TERM"));
				setFractionalInterestDiscount(getDouble(account, "FRACTIONAL_DISCOUNT"));
				setEstateTaxRate(getDouble(account,"ESTATE_TAX_RATE"));
				setRentGrowthRateForHeirs(getDouble(account,"RENT_GROWTH_RATE"));
				setRentAfterTerm(getDouble(account,"RENT_AFTER_TERM"));
				setClientPriorGifts(getDouble(account,"CLIENT_PRIOR_GIFTS"));
				setSpousePriorGifts(getDouble(account,"SPOUSE_PRIOR_GIFTS"));
				setRevisionRetained(((String)account.get("REVISION_RETAINED")).equalsIgnoreCase("y"));
				setFinalDeath(getInteger(account,"FINAL_DEATH"));
			}
		}
	}
	@Override
	public void report() {
		
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
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	/**
	 * @param basis The basis to set.
	 */
	public void setBasis(double basis) {
		this.basis = basis;
	}
	/**
	 * @param clientAge The clientAge to set.
	 */
	public void setClientAge(double clientAge) {
		this.clientAge = clientAge;
	}
	/**
	 * @param clientDateOfBirth The clientDateOfBirth to set.
	 */
	public void setClientDateOfBirth(String clientDateOfBirth) {
		this.clientDateOfBirth = clientDateOfBirth;
	}
	/**
	 * @param clientFirstName The clientFirstName to set.
	 */
	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}
	/**
	 * @param clientLifeExp The clientLifeExp to set.
	 */
	public void setClientLifeExp(double clientLifeExp) {
		this.clientLifeExp = clientLifeExp;
	}
	/**
	 * @param clientPriorGifts The clientPriorGifts to set.
	 */
	public void setClientPriorGifts(double clientPriorGifts) {
		this.clientPriorGifts = clientPriorGifts;
	}
	/**
	 * @param clientQprt The clientQprt to set.
	 */
	public void setClientQprt(QprtTrustTool clientQprt) {
		this.clientQprt = clientQprt;
	}
	/**
	 * @param clientStartTerm The clientStartTerm to set.
	 */
	public void setClientStartTerm(int clientStartTerm) {
		this.clientStartTerm = clientStartTerm;
	}
	/**
	 * @param clientTerm The clientTerm to set.
	 */
	public void setClientTerm(int clientTerm) {
		this.clientTerm = clientTerm;
	}

	/**
	 * @param estateTaxRate The estateTaxRate to set.
	 */
	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}
	/**
	 * @param finalDeath The finalDeath to set.
	 */
	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}
	/**
	 * @param fractionalInterestDisccount The fractionalInterestDisccount to set.
	 */
	public void setFractionalInterestDiscount(
			double fractionalInterestDiscount) {
		this.fractionalInterestDiscount = fractionalInterestDiscount;
	}
	/**
	 * @param growth The growth to set.
	 */
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param numberOfTrusts The numberOfTrusts to set.
	 */
	public void setNumberOfTrusts(int numberOfTrusts) {
		this.numberOfTrusts = numberOfTrusts;
	}
	/**
	 * @param rentAfterTerm The rentAfterTerm to set.
	 */
	public void setRentAfterTerm(double rentAfterTerm) {
		this.rentAfterTerm = rentAfterTerm;
	}
	/**
	 * @param rentGrowthRateForHeirs The rentGrowthRateForHeirs to set.
	 */
	public void setRentGrowthRateForHeirs(double rentGrowthRateForHeirs) {
		this.rentGrowthRateForHeirs = rentGrowthRateForHeirs;
	}
	/**
	 * @param revisionRetained The revisionRetained to set.
	 */
	public void setRevisionRetained(boolean revisionRetained) {
		this.revisionRetained = revisionRetained;
	}
	/**
	 * @param spouseAge The spouseAge to set.
	 */
	public void setSpouseAge(double spouseAge) {
		this.spouseAge = spouseAge;
	}
	/**
	 * @param spouseDateOfBirth The spouseDateOfBirth to set.
	 */
	public void setSpouseDateOfBirth(String spouseDateOfBirth) {
		this.spouseDateOfBirth = spouseDateOfBirth;
	}
	/**
	 * @param spouseFirstName The spouseFirstName to set.
	 */
	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}
	/**
	 * @param spouseLifeExp The spouseLifeExp to set.
	 */
	public void setSpouseLifeExp(double spouseLifeExp) {
		this.spouseLifeExp = spouseLifeExp;
	}
	/**
	 * @param spousePriorGifts The spousePriorGifts to set.
	 */
	public void setSpousePriorGifts(double spousePriorGifts) {
		this.spousePriorGifts = spousePriorGifts;
	}
	/**
	 * @param spouseQprt The spouseQprt to set.
	 */
	public void setSpouseQprt(QprtTrustTool spouseQprt) {
		this.spouseQprt = spouseQprt;
	}
	/**
	 * @param spouseStartTerm The spouseStartTerm to set.
	 */
	public void setSpouseStartTerm(int spouseStartTerm) {
		this.spouseStartTerm = spouseStartTerm;
	}
	/**
	 * @param spouseTerm The spouseTerm to set.
	 */
	public void setSpouseTerm(int spouseTerm) {
		this.spouseTerm = spouseTerm;
	}
	/**
	 * @param typeOfHome The typeOfHome to set.
	 */
	public void setTypeOfHome(int typeOfHome) {
		this.typeOfHome = typeOfHome;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("QPRT_TOOL");
		dbObj.clearFields();
		
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("TRUSTS", getNumberOfTrusts());
		dbAddField("CLIENT_TERM", getClientTerm());
		dbAddField("CLIENT_START_TERM", getClientStartTerm());
		dbAddField("SPOUSE_TERM", getSpouseTerm());
		dbAddField("SPOUSE_START_TERM", getSpouseStartTerm());
		dbAddField("FRACTIONAL_DISCOUNT", getFractionalInterestDiscount());
		dbAddField("ESTATE_TAX_RATE", getEstateTaxRate());
		dbAddField("RENT_GROWTH_RATE", getRentGrowthRateForHeirs());
		dbAddField("RENT_AFTER_TERM", getRentAfterTerm());
		dbAddField("CLIENT_PRIOR_GIFTS", getClientPriorGifts());
		dbAddField("SPOUSE_PRIOR_GIFTS", getSpousePriorGifts());
		dbAddField("REVISION_RETAINED", isRevisionRetained() ? "Y" : "F");
		dbAddField("FINAL_DEATH", getFinalDeath());

		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();
	}
	@Override
	public String writeupText() {
		return null;
	}
}
