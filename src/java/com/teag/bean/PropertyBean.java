/*
 * Created on Mar 8, 2005
 *
 */
package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Paul Stay
 *
 */
public class PropertyBean extends AssetSqlBean {
	public final static String ID = "PROPERTY_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String LOCATION_CITY = "LOCATION_CITY";
	public final static String LOCATION_STATE = "LOCATION_STATE";
	public final static String BASIS = "BASIS";
	public final static String VALUE = "VALUE";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String LOAN_BALANCE = "LOAN_BALANCE";
	public final static String LOAN_INTEREST = "LOAN_INTEREST";
	public final static String LOAN_FREQ = "LOAN_FREQ";
	public final static String LOAN_PAYMENT = "LOAN_PAYMENT";
	public final static String LOAN_TERM = "LOAN_TERM";
	public final static String INT_DED = "INT_DED";
	public final static String NOTES = "NOTES";
	public final static String tableName = "PROPERTY";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1908601738275669610L;
	private long id;	// The asset ID in the table
	private String uuid;	
	private long ownerId;					// Id of the owner of the asset
	
	private String description;
	private long ownershipId;
	private long titleId;
	private String locationCity;
	private String locationState;
	private String notes;
	private double basis;
	private double value;
	private double growthRate;
	private double loanBalance;
	private double loanInterest;
	private long loanFreq; 
	private double loanPayment;
	private long loanTerm;
	private String intDed;
	
	private HashMap<String,Object> account;
	
	public PropertyBean()
	{
		this.beanType = SqlBean.PROPERTY;
	}
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	@Override
	public double getAssetBasis(){
		return this.getBasis();
	}
	@Override
	public double getAssetGrowth(){
		return this.getGrowthRate();
	}
	@Override
	public double getAssetIncome() {
		return 0.0;
	}

	// There is currently no liablility associated in the database!;
	@Override
	public double getAssetLiability() {
		return this.getLoanBalance();
	}

	@Override
	public String getAssetName() {
		return this.getDescription();
	}

	@Override
	public double getAssetValue() {
		return this.getValue();
	}

	/**
	 * @return Returns the basis.
	 */
	public double getBasis() {
		return basis;
	}
	
	public ArrayList<PropertyBean> getBeans(String whereClause) {
		ArrayList<PropertyBean> bList = new ArrayList<PropertyBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			PropertyBean nb = new PropertyBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
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
	 * @return Returns the intDed.
	 */
	public String getIntDed() {
		return intDed;
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
		return notes;
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
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
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
			this.setOwnershipId(account.get("OWNERSHIP_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitleId(account.get("TITLE_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setLocationCity((String)account.get("LOCATION_CITY"));
			this.setLocationState((String)account.get("LOCATION_STATE"));
			this.setNotes((String)account.get("NOTES"));
			this.setBasis(account.get("BASIS") == null ? 0 : ((Number)(account.get("BASIS"))).doubleValue());
			this.setValue(account.get("VALUE") == null ? 0 : ((Number)(account.get("VALUE"))).doubleValue());
			this.setGrowthRate(account.get("GROWTH_RATE") == null ? 0 : ((Number)(account.get("GROWTH_RATE"))).doubleValue());
			this.setLoanBalance(account.get("LOAN_BALANCE") == null ? 0 : ((Number)(account.get("LOAN_BALANCE"))).doubleValue());
			this.setLoanInterest(account.get("LOAN_INTEREST") == null ? 0 : ((Number)(account.get("LOAN_INTEREST"))).doubleValue());
			this.setLoanFreq(account.get("LOAN_FREQ") == null ? 0 : ((Number)(account.get("LOAN_FREQ"))).longValue()); 
			this.setLoanPayment(account.get("LOAN_PAYMENT") == null ? 0 : ((Number)(account.get("LOAN_PAYMENT"))).doubleValue());
			this.setLoanTerm(account.get("LOAN_TERM") == null ? 0 : ((Number)(account.get("LOAN_TERM"))).longValue());
			this.setIntDed(account.get("INT_DED") == null ? "" : (String)account.get("INT_DED"));
		}		
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbAddField(OWNER_ID, "" + this.getOwnerId());
		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(LOCATION_CITY, "" + this.getLocationCity());
		dbAddField(LOCATION_STATE, "" + this.getLocationState());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(LOAN_BALANCE, "" + this.getLoanBalance());
		dbAddField(LOAN_INTEREST, "" + this.getLoanInterest());
		dbAddField(LOAN_FREQ, "" + this.getLoanFreq());
		dbAddField(LOAN_PAYMENT, "" + this.getLoanPayment());
		dbAddField(LOAN_TERM, "" + this.getLoanTerm());
		dbAddField(INT_DED, "" + this.getIntDed());

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
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @param intDed The intDed to set.
	 */
	public void setIntDed(String intDed) {
		this.intDed = intDed;
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
	
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(LOCATION_CITY, "" + this.getLocationCity());
		dbAddField(LOCATION_STATE, "" + this.getLocationState());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(LOAN_BALANCE, "" + this.getLoanBalance());
		dbAddField(LOAN_INTEREST, "" + this.getLoanInterest());
		dbAddField(LOAN_FREQ, "" + this.getLoanFreq());
		dbAddField(LOAN_PAYMENT, "" + this.getLoanPayment());
		dbAddField(LOAN_TERM, "" + this.getLoanTerm());
		dbAddField(INT_DED, "" + this.getIntDed());		
		
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
	}
}
