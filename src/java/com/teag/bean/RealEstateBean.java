/*
 * Created on Mar 8, 2005
 *
 */
package com.teag.bean;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Paul Stay
 *
 */
public class RealEstateBean extends AssetSqlBean  {
	public final static String ID = "REAL_ESTATE_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String REALESTATE_OWNER = "REALESTATE_OWNER";
	public final static String YEAR_ACQUIRED = "YEAR_ACQUIRED";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String NOTES = "NOTES";
	public final static String LOCATION_CITY = "LOCATION_CITY";
	public final static String LOCATION_STATE = "LOCATION_STATE";
	public final static String BASIS = "BASIS";
	public final static String VALUE = "VALUE";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String PERCENT_OWNED = "PERCENT_OWNED";
	public final static String LOAN_BALANCE = "LOAN_BALANCE";
	public final static String LOAN_INTEREST = "LOAN_INTEREST";
	public final static String LOAN_FREQ = "LOAN_FREQ";
	public final static String LOAN_PAYMENT = "LOAN_PAYMENT";
	public final static String LOAN_TERM = "LOAN_TERM";
	public final static String GROSS_RENTS = "GROSS_RENTS";
	public final static String GROSS_RENTS_GROWTH = "GROSS_RENTS_GROWTH";
	public final static String OPERATING_EXPENSES = "OPERATING_EXPENSES";
	public final static String GROWTH_EXPENSES = "GROWTH_EXPENSES";
	public final static String DEPRECIATION = "DEPRECIATION";
	public final static String DEPRECIATION_YEARS = "DEPRECIATION_YEARS";
	public final static String DEPRECIATION_VALUE = "DEPRECIATION_VALUE";
	public final static String DEPRECIATION_METHOD = "DEPRECIATION_METHOD";
	public final static String SALVAGE_VALUE = "SALVAGE_VALUE";
	/**
	 * 
	 */
	private static final long serialVersionUID = 7835182460386612174L;
	private static String tableName ="REAL_ESTATE";	// Name of the table
	private long id;	// The asset ID in the table	
	private String uuid;
	private long ownerId;					// Id of the owner of the asset
	
	private String description;
	private long realestateOwner;
	private String yearAquired;
	private long ownershipId;
	private long titleId;
	private String notes;
	private String locationCity;
	private String locationState;
	private double basis;
	private double value;
	private double growthRate;
	private double percentOwned;
	private double loanBalance;
	private double loanInterest;
	private long loanFreq;
	private double loanPayment;
	private long loanTerm;
	private double grossRents;
	private double grossRentsGrowth;
	private double operatingExpenses;
	private double growthExpenses;
	private double depreciation;
	private long depreciationYears;
	private double depreciationValue;
	private String depreciationMethod;
	private double salvageValue;
	
	private SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
	
	
	private HashMap<String,Object> account;
	
	public RealEstateBean()
	{
		this.beanType = SqlBean.REALESTATE;
	}
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	public double getAssetBasis(){
		return this.getBasis();
	}
	public double getAssetGrowth(){
		return this.getGrowthRate();
	}
	public double getAssetIncome() {
		if( this.getValue() != 0) {
			return (this.getGrossRents() - this.operatingExpenses)/this.getValue();
		}
		return .06;
	}

	// There is currently no liablility associated in the database!;
	public double getAssetLiability() {
		return this.getLoanBalance();
	}

	public String getAssetName() {
		return this.getDescription();
	}

	public double getAssetValue() {
		return this.getValue();
	}
	
	/**
	 * @return Returns the basis.
	 */
	public double getBasis() {
		return basis;
	}
	
	public ArrayList<RealEstateBean> getBeans(String whereClause) {
		ArrayList<RealEstateBean> bList = new ArrayList<RealEstateBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			RealEstateBean nb = new RealEstateBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	
	
	/**
	 * @return Returns the depreciation.
	 */
	public double getDepreciation() {
		return depreciation;
	}
	/**
	 * @return Returns the depreciationMethod.
	 */
	public String getDepreciationMethod() {
		return depreciationMethod;
	}
	/**
	 * @return Returns the depreciationValue.
	 */
	public double getDepreciationValue() {
		return depreciationValue;
	}
	/**
	 * @return Returns the depreciationYears.
	 */
	public long getDepreciationYears() {
		return depreciationYears;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return Returns the grossRents.
	 */
	public double getGrossRents() {
		return grossRents;
	}
	/**
	 * @return Returns the grossRentsGrowth.
	 */
	public double getGrossRentsGrowth() {
		return grossRentsGrowth;
	}
	/**
	 * @return Returns the growthExpenses.
	 */
	public double getGrowthExpenses() {
		return growthExpenses;
	}
	/**
	 * @return Returns the growthRate.
	 */
	public double getGrowthRate() {
		return growthRate;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the loanBalance.
	 */
	public double getLoanBalance() {
		return loanBalance;
	}
	/**
	 * @return Returns the loanFreq.
	 */
	public long getLoanFreq() {
		return loanFreq;
	}
	/**
	 * @return Returns the loanInterest.
	 */
	public double getLoanInterest() {
		return loanInterest;
	}
	/**
	 * @return Returns the loanPayment.
	 */
	public double getLoanPayment() {
		return loanPayment;
	}
	/**
	 * @return Returns the loanTerm.
	 */
	public long getLoanTerm() {
		return loanTerm;
	}
	/**
	 * @return Returns the locationCity.
	 */
	public String getLocationCity() {
		return locationCity;
	}
	/**
	 * @return Returns the locationState.
	 */
	public String getLocationState() {
		return locationState;
	}
	/**
	 * @return Returns the notes.
	 */
	public String getNotes() {
		if(notes != null)
			return notes;
		return "";
	}
	/**
	 * @return Returns the operatingExpenses.
	 */
	public double getOperatingExpenses() {
		return operatingExpenses;
	}
	/**
	 * @return Returns the ownerId.
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @return Returns the ownershipId.
	 */
	public long getOwnershipId() {
		return ownershipId;
	}
	/**
	 * @return Returns the percentOwned.
	 */
	public double getPercentOwned() {
		return percentOwned;
	}
	/**
	 * @return Returns the realestateOwner.
	 */
	public long getRealestateOwner() {
		return realestateOwner;
	}
	/**
	 * @return Returns the salvageValue.
	 */
	public double getSalvageValue() {
		return salvageValue;
	}
	/**
	 * @return Returns the titleId.
	 */
	public long getTitleId() {
		return titleId;
	}
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @return Returns the yearAquired.
	 */
	public String getYearAquired() {
		return yearAquired;
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		account = null;

		String sql = "select * from " + tableName + " where " + ID + "='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());

			// Add any additional fields
			this.setDescription((String)account.get("DESCRIPTION"));
			this.setRealestateOwner(account.get("REALESTATE_OWNER") == null ? 0 : ((Number)(account.get("REALESTATE_OWNER"))).longValue());
			this.setYearAquired((String)account.get("YEAR_AQUIRED"));
			this.setOwnershipId(account.get("OWNERSHIP_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitleId(account.get("TITLE_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setNotes((String)account.get("NOTES"));
			this.setLocationCity((String)account.get("LOCATION_CITY"));
			this.setLocationState((String)account.get("LOCATION_STATE"));
			this.setBasis(account.get("BASIS") == null ? 0 : ((Number)(account.get("BASIS"))).doubleValue());
			this.setValue(account.get("VALUE") == null ? 0 : ((Number)(account.get("VALUE"))).doubleValue());
			this.setGrowthRate(account.get("GROWTH_RATE") == null ? 0 : ((Number)(account.get("GROWTH_RATE"))).doubleValue());
			this.setPercentOwned(account.get("PERCENT_OWNED") == null ? 0 : ((Number)(account.get("PERCENT_OWNED"))).doubleValue());
			this.setLoanBalance(account.get("LOAN_BALANCE") == null ? 0 : ((Number)(account.get("LOAN_BALANCE"))).doubleValue());
			this.setLoanInterest(account.get("LOAN_INTEREST") == null ? 0 : ((Number)(account.get("LOAN_INTEREST"))).doubleValue());
			this.setLoanFreq(account.get("LOAN_FREQ") == null ? 0 : ((Number)(account.get("LOAN_FREQ"))).longValue());
			this.setLoanPayment(account.get("LOAN_PAYMENT") == null ? 0 : ((Number)(account.get("LOAN_PAYMENT"))).doubleValue());
			this.setLoanTerm(account.get("LOAN_TERM") == null ? 0 : ((Number)(account.get("LOAN_TERM"))).longValue());
			this.setGrossRents(account.get("GROSS_RENTS") == null ? 0 : ((Number)(account.get("GROSS_RENTS"))).doubleValue());
			this.setGrossRentsGrowth(account.get("GROSS_RENTS_GROWTH") == null ? 0 : ((Number)(account.get("GROSS_RENTS_GROWTH"))).doubleValue());
			this.setOperatingExpenses(account.get("OPERATING_EXPENSES") == null ? 0 : ((Number)(account.get("OPERATING_EXPENSES"))).doubleValue());
			this.setGrowthExpenses(account.get("GROWTH_EXPENSES") == null ? 0 : ((Number)(account.get("GROWTH_EXPENSES"))).doubleValue());
			this.setDepreciation(account.get("DEPRECIATION") == null ? 0 : ((Number)(account.get("DEPRECIATION"))).doubleValue());
			this.setDepreciationYears(account.get("DEPRECIATION_YEARS") == null ? 0 : ((Number)(account.get("DEPRECIATION_YEARS"))).longValue());
			this.setDepreciationValue(account.get("DEPRECIATION_VALUE") == null ? 0 : ((Number)(account.get("DEPRECIATION_VALUE"))).doubleValue());
			this.setDepreciationMethod((String)account.get("DEPRECIATION_METHOD"));
			this.setSalvageValue(account.get("SALVAGE_VALUE") == null ? 0 : ((Number)(account.get("SALVAGE_VALUE"))).doubleValue());			
		}		
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(OWNER_ID, "" + this.getOwnerId());
		Date YearAquired;
		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(REALESTATE_OWNER, "" + this.getRealestateOwner());
		if(null != this.getYearAquired())
		{
			YearAquired = dateformat.parse(this.getYearAquired(), new ParsePosition(0));
			dbAddField(YEAR_ACQUIRED, dbObject.dbDate(YearAquired));
		}
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(LOCATION_CITY, "" + this.getLocationCity());
		dbAddField(LOCATION_STATE, "" + this.getLocationState());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(PERCENT_OWNED, "" + this.getPercentOwned());
		dbAddField(LOAN_BALANCE, "" + this.getLoanBalance());
		dbAddField(LOAN_INTEREST, "" + this.getLoanInterest());
		dbAddField(LOAN_FREQ, "" + this.getLoanFreq());
		dbAddField(LOAN_PAYMENT, "" + this.getLoanPayment());
		dbAddField(LOAN_TERM, "" + this.getLoanTerm());
		dbAddField(GROSS_RENTS, "" + this.getGrossRents());
		dbAddField(GROSS_RENTS_GROWTH, "" + this.getGrossRentsGrowth());
		dbAddField(OPERATING_EXPENSES, "" + this.getOperatingExpenses());
		dbAddField(GROWTH_EXPENSES, "" + this.getGrowthExpenses());
		dbAddField(DEPRECIATION, "" + this.getDepreciation());
		dbAddField(DEPRECIATION_YEARS, "" + this.getDepreciationYears());
		dbAddField(DEPRECIATION_VALUE, "" + this.getDepreciationValue());
		dbAddField(DEPRECIATION_METHOD, "" + this.getDepreciationMethod());
		dbAddField(SALVAGE_VALUE, "" + this.getSalvageValue());

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();

	}
	/**
	 * @param basis The basis to set.
	 */
	public void setBasis(double basis) {
		this.basis = basis;
	}
	/**
	 * @param depreciation The depreciation to set.
	 */
	public void setDepreciation(double depreciation) {
		this.depreciation = depreciation;
	}
	/**
	 * @param depreciationMethod The depreciationMethod to set.
	 */
	public void setDepreciationMethod(String depreciationMethod) {
		this.depreciationMethod = depreciationMethod;
	}
	/**
	 * @param depreciationValue The depreciationValue to set.
	 */
	public void setDepreciationValue(double depreciationValue) {
		this.depreciationValue = depreciationValue;
	}
	/**
	 * @param depreciationYears The depreciationYears to set.
	 */
	public void setDepreciationYears(long depreciationYears) {
		this.depreciationYears = depreciationYears;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param grossRents The grossRents to set.
	 */
	public void setGrossRents(double grossRents) {
		this.grossRents = grossRents;
	}
	/**
	 * @param grossRentsGrowth The grossRentsGrowth to set.
	 */
	public void setGrossRentsGrowth(double grossRentsGrowth) {
		this.grossRentsGrowth = grossRentsGrowth;
	}
	/**
	 * @param growthExpenses The growthExpenses to set.
	 */
	public void setGrowthExpenses(double growthExpenses) {
		this.growthExpenses = growthExpenses;
	}
	/**
	 * @param growthRate The growthRate to set.
	 */
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param loanBalance The loanBalance to set.
	 */
	public void setLoanBalance(double loanBalance) {
		this.loanBalance = loanBalance;
	}
	/**
	 * @param loanFreq The loanFreq to set.
	 */
	public void setLoanFreq(long loanFreq) {
		this.loanFreq = loanFreq;
	}
	/**
	 * @param loanInterest The loanInterest to set.
	 */
	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}
	/**
	 * @param loanPayment The loanPayment to set.
	 */
	public void setLoanPayment(double loanPayment) {
		this.loanPayment = loanPayment;
	}
	/**
	 * @param loanTerm The loanTerm to set.
	 */
	public void setLoanTerm(long loanTerm) {
		this.loanTerm = loanTerm;
	}
	/**
	 * @param locationCity The locationCity to set.
	 */
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	/**
	 * @param locationState The locationState to set.
	 */
	public void setLocationState(String locationState) {
		this.locationState = locationState;
	}
	/**
	 * @param notes The notes to set.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @param operatingExpenses The operatingExpenses to set.
	 */
	public void setOperatingExpenses(double operatingExpenses) {
		this.operatingExpenses = operatingExpenses;
	}
	/**
	 * @param ownerId The ownerId to set.
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	
	/**
	 * @param ownershipId The ownershipId to set.
	 */
	public void setOwnershipId(long ownershipId) {
		this.ownershipId = ownershipId;
	}
	/**
	 * @param percentOwned The percentOwned to set.
	 */
	public void setPercentOwned(double percentOwned) {
		this.percentOwned = percentOwned;
	}
	
	/**
	 * @param realestateOwner The realestateOwner to set.
	 */
	public void setRealestateOwner(long realestateOwner) {
		this.realestateOwner = realestateOwner;
	}
	
	/**
	 * @param salvageValue The salvageValue to set.
	 */
	public void setSalvageValue(double salvageValue) {
		this.salvageValue = salvageValue;
	}
	/**
	 * @param titleId The titleId to set.
	 */
	public void setTitleId(long titleId) {
		this.titleId = titleId;
	}
	
	/**
	 * @param value The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}
	
	/**
	 * @param yearAquired The yearAquired to set.
	 */
	public void setYearAquired(String yearAquired) {
		this.yearAquired = yearAquired;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		
//		dbAddField("DESCRIPTION", this.getName());
		// Add the necessary fields here
		Date YearAquired;
		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(REALESTATE_OWNER, "" + this.getRealestateOwner());
		if(null != this.getYearAquired())
		{
			YearAquired = dateformat.parse(this.getYearAquired(), new ParsePosition(0));
			dbAddField(YEAR_ACQUIRED, dbObject.dbDate(YearAquired));
		}
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(LOCATION_CITY, "" + this.getLocationCity());
		dbAddField(LOCATION_STATE, "" + this.getLocationState());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(PERCENT_OWNED, "" + this.getPercentOwned());
		dbAddField(LOAN_BALANCE, "" + this.getLoanBalance());
		dbAddField(LOAN_INTEREST, "" + this.getLoanInterest());
		dbAddField(LOAN_FREQ, "" + this.getLoanFreq());
		dbAddField(LOAN_PAYMENT, "" + this.getLoanPayment());
		dbAddField(LOAN_TERM, "" + this.getLoanTerm());
		dbAddField(GROSS_RENTS, "" + this.getGrossRents());
		dbAddField(GROSS_RENTS_GROWTH, "" + this.getGrossRentsGrowth());
		dbAddField(OPERATING_EXPENSES, "" + this.getOperatingExpenses());
		dbAddField(GROWTH_EXPENSES, "" + this.getGrowthExpenses());
		dbAddField(DEPRECIATION, "" + this.getDepreciation());
		dbAddField(DEPRECIATION_YEARS, "" + this.getDepreciationYears());
		dbAddField(DEPRECIATION_VALUE, "" + this.getDepreciationValue());
		dbAddField(DEPRECIATION_METHOD, "" + this.getDepreciationMethod());
		dbAddField(SALVAGE_VALUE, "" + this.getSalvageValue());
		
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
	}

}
