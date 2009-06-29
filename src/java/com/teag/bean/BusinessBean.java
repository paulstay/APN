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
public class BusinessBean extends AssetSqlBean {
	
	public final static String ID = "BUSINESS_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String NOTES = "NOTES";
	public final static String BASIS = "BASIS";
	public final static String VALUE = "VALUE";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String PERCENT_OWNED = "PERCENT_OWNED";
	public final static String BUSINESS_TYPE_ID = "BUSINESS_TYPE_ID";
	public final static String ALD = "ALD";
	public final static String ANNUAL_INCOME = "ANNUAL_INCOME";
	public final static String INCOME_GROWTH = "INCOME_GROWTH";
	private static String tableName ="BUSINESS";	// Name of the table
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7291098326888870312L;
	private long id;	// The asset ID in the table
	private String uuid;	
	private long ownerId;					// Id of the owner of the asset
	private String description;
	private long ownershipId;
	private long titleId;
	private String notes;
	private double basis;
	private double value;
	private double growthRate;
	private double percentOwned;
	private long businessTypeId;
	private String ald;
	private double annualIncome;
	private double incomeGrowth;
	private SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");

	
	private HashMap<String,Object> account;
	
	public BusinessBean()
	{
		this.beanType = SqlBean.BUSINESS;
	}
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	/**
	 * @return Returns the ald.
	 */
	public String getAld() {
		if(ald != null && ald.length()>0)
			return ald;
		return null;
	}
	/**
	 * @return Returns the annualIncome.
	 */
	public double getAnnualIncome() {
		return annualIncome;
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
		return annualIncome/value;			// This needs to be a percentage.
	}
	// There is currently no liablility associated with Businesses, WHY?
	@Override
	public double getAssetLiability() {
		return 0;
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
	public ArrayList<BusinessBean> getBeans(String whereClause) {
		ArrayList<BusinessBean> bList = new ArrayList<BusinessBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			BusinessBean nb = new BusinessBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	/**
	 * @return Returns the businessTypeId.
	 */
	public long getBusinessTypeId() {
		return businessTypeId;
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
	 * @return Returns the incomeGrowth.
	 */
	public double getIncomeGrowth() {
		return incomeGrowth;
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
	 * @return Returns the percentOwned.
	 */
	public double getPercentOwned() {
		return percentOwned;
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

		String sql = "select * from " + tableName + " where " + ID+ "='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());
			this.setDescription((String)account.get("DESCRIPTION"));
			this.setOwnershipId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitleId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setNotes((String)account.get("NOTES"));
			this.setBasis(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("BASIS"))).doubleValue());
			this.setValue(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("VALUE"))).doubleValue());
			this.setGrowthRate(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("GROWTH_RATE"))).doubleValue());
			this.setPercentOwned(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("PERCENT_OWNED"))).doubleValue());
			this.setBusinessTypeId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("BUSINESS_TYPE_ID"))).longValue());
			String sALD = account.get("ALD") == null ? null : this.dateformat.format(account.get("ALD"));			
			this.setAld(sALD);
			this.setAnnualIncome(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("ANNUAL_INCOME"))).doubleValue());
			this.setIncomeGrowth(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("INCOME_GROWTH"))).doubleValue());
			// Add any additional fields
		}		
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		Date dALD;
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbAddField(OWNER_ID, "" + this.getOwnerId());
		dbAddField(DESCRIPTION, this.getDescription());
		dbAddField(OWNERSHIP_ID, "" + this.ownershipId);
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(PERCENT_OWNED, "" + this.getPercentOwned());
		dbAddField(BUSINESS_TYPE_ID, "" + this.getBusinessTypeId());
		if(getAld() !=null)
		{
			dALD = dateformat.parse(this.getAld(), new ParsePosition(0));
			dbAddField(ALD, dbObject.dbDate(dALD));
		}
		dbAddField(ANNUAL_INCOME, "" + this.getAnnualIncome());
		dbAddField(INCOME_GROWTH, "" + this.getIncomeGrowth());
		
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();

	}
	/**
	 * @param ald The ald to set.
	 */
	public void setAld(String ald) {
		this.ald = ald;
	}
	/**
	 * @param annualIncome The annualIncome to set.
	 */
	public void setAnnualIncome(double annualIncome) {
		this.annualIncome = annualIncome;
	}
	/**
	 * @param basis The basis to set.
	 */
	public void setBasis(double basis) {
		this.basis = basis;
	}
	/**
	 * @param businessTypeId The businessTypeId to set.
	 */
	public void setBusinessTypeId(long businessTypeId) {
		this.businessTypeId = businessTypeId;
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
	 * @param incomeGrowth The incomeGrowth to set.
	 */
	public void setIncomeGrowth(double incomeGrowth) {
		this.incomeGrowth = incomeGrowth;
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
	 * @param percentOwned The percentOwned to set.
	 */
	public void setPercentOwned(double percentOwned) {
		this.percentOwned = percentOwned;
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

		dbAddField(DESCRIPTION, this.getDescription());
		dbAddField(OWNERSHIP_ID, "" + this.ownershipId);
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(PERCENT_OWNED, "" + this.getPercentOwned());
		dbAddField(BUSINESS_TYPE_ID, "" + this.getBusinessTypeId());
		if(getAld() != null)
		{
			dbAddDate(ALD, getAld());
		}
		dbAddField(ANNUAL_INCOME, "" + this.getAnnualIncome());
		dbAddField(INCOME_GROWTH, "" + this.getIncomeGrowth());
		
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();

	}
}
