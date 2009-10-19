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
 * @author Paul R. Stay
 * 
 *
 */
public class IlliquidBean extends AssetSqlBean {

	public static final String ID = "ILLIQUID_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String NOTES = "NOTES";
	public final static String BASIS = "BASIS";
	public final static String VALUE = "VALUE";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String DIV_INT = "DIV_INT";
	public final static String ALD = "ALD";
	public final static String tableName = "ILLIQUID";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 442342026390837691L;
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
	private double divInt;
	private String ald;
	
	private SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
	
	private HashMap<String,Object> account;
	public IlliquidBean()
	{
		this.beanType = SqlBean.ILLIQUID;
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
		return ald;
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
		return this.getDivInt();
	}
	// There is currently no liablility associated in the database!;
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
	public ArrayList<IlliquidBean> getBeans(String whereClause) {
		ArrayList<IlliquidBean> bList = new ArrayList<IlliquidBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			IlliquidBean nb = new IlliquidBean();
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
	 * @return Returns the divInt.
	 */
	public double getDivInt() {
		return divInt;
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
			String sALD = account.get("ALD") == null ? null : this.dateformat.format(account.get("ALD"));
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());

			// Add any additional fields
			this.setDescription((String)account.get("DESCRIPTION"));
			this.setOwnershipId(account.get("OWNERSHIP_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitleId(account.get("TITLE_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setNotes((String)account.get("NOTES"));
			this.setBasis(account.get("BASIS") == null ? 0 : ((Number)(account.get("BASIS"))).doubleValue());
			this.setValue(account.get("VALUE") == null ? 0 : ((Number)(account.get("VALUE"))).doubleValue());
			this.setGrowthRate(account.get("GROWTH_RATE") == null ? 0 : ((Number)(account.get("GROWTH_RATE"))).doubleValue());
			this.setDivInt(account.get("DIV_INT") == null ? 0 : ((Number)(account.get("DIV_INT"))).doubleValue());
			this.setAld(sALD);
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
		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(DIV_INT, "" + this.getDivInt());
		if(null != this.getAld())
		{
			dALD = dateformat.parse(this.getAld(), new ParsePosition(0));
			dbAddDate(ALD, this.getAld());
		}		
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
	 * @param divInt The divInt to set.
	 */
	public void setDivInt(double divInt) {
		this.divInt = divInt;
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
		Date dALD;
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbAddField(OWNER_ID, "" + this.getOwnerId());
		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(DIV_INT, "" + this.getDivInt());
		if(null != this.getAld())
		{
			dALD = dateformat.parse(this.getAld(), new ParsePosition(0));
			dbAddDate(ALD, getAld());
		}
		
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();

	}
}
