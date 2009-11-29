/*
 * Created on Jan 25, 2005
 *
 */
package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Paul Stay
 * CashBean - used to access CASH records from the database.
 *
 */
public class CashBean extends AssetSqlBean {
	public final static String ID = "CASH_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String BANK = "BANK";
	public final static String ACCT = "ACCT";
	public final static String BASIS = "BASIS";
	public final static String VALUE = "VALUE";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String INTEREST_RATE = "INTEREST_RATE";
	public final static String NOTES = "NOTES";
	public final static String tableName = "CASH";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1154213509199871457L;
	private String name;
	private long ownership;
	private long title;
	private double basis;
	private double currentValue;
	private double interest;
	private double growth;
	private String bank;
	private long acct;
	private String notes;
	private long id;
	private String uuid;
	private long ownerId;
	
	private HashMap<String,Object> account;
	
	public CashBean()
	{
		this.beanType = SqlBean.CASH;
	}
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	/**
	 * @return Returns the acct.
	 */
	public long getAcct() {
		return acct;
	}

	@Override
	public double getAssetBasis(){
		return this.getBasis();
	}
	
	@Override
	public double getAssetGrowth(){
		return this.getGrowth();
	}
	
	@Override
	public double getAssetIncome() {
		return this.getInterest();
	}

	// There should be no liability for Cash and equivalents!
	@Override
	public double getAssetLiability() {
		return 0;
	}
	@Override
	public String getAssetName() {
		return getName();
	}
	@Override
	public double getAssetValue() {
		return this.getCurrentValue();
	}
	/**
	 * @return Returns the bank.
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * @return Returns the basis.
	 */
	public double getBasis() {
		return basis;
	}
	public ArrayList<CashBean> getBeans(String whereClause) {
		ArrayList<CashBean> bList = new ArrayList<CashBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			CashBean nb = new CashBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		
		return bList;
	}
	/**
	 * @return Returns the currentValue.
	 */
	public double getCurrentValue() {
		return currentValue;
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
	 * @return Returns the interest.
	 */
	public double getInterest() {
		return interest;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
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
	 * @return Returns the ownership.
	 */
	public long getOwnership() {
		return ownership;
	}
	/**
	 * @return Returns the title.
	 */
	public long getTitle() {
		return title;
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() 
	{

		dbObject.start();
		dbObject.setTable("CASH");
		account = null;

		String sql = "select * from CASH where CASH_ID='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());
			this.setName((String)(account.get("DESCRIPTION")));
			this.setAcct(account.get("ACCT") == null ? 0 : ((Number)(account.get("ACCT"))).longValue());
			this.setBasis(account.get("BASIS") == null ? 0 : ((Number)(account.get("BASIS"))).floatValue());
			this.setCurrentValue(account.get("VALUE") == null ? 0 : ((Number)(account.get("VALUE"))).floatValue());
			this.setGrowth(account.get("GROWTH_RATE") == null ? 0 : ((Number)(account.get("GROWTH_RATE"))).floatValue());
			this.setInterest(account.get("INTEREST_RATE") == null ? 0 : ((Number)(account.get("INTEREST_RATE"))).floatValue());
			this.setOwnership(account.get("OWNERSHIP_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitle(account.get("TITLE_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setBank((String)(account.get("BANK")));
			this.setNotes((String)(account.get("NOTES")));
		}		
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() 
	{
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbAddField(DESCRIPTION, getName());
		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(BANK, getBank());
		if(getNotes() != null && getNotes().length() > 0)
			dbAddField(NOTES, getNotes());
		dbAddField(ACCT, getAcct());
		dbAddField(BASIS, getBasis());
		dbAddField(VALUE, getCurrentValue());
		dbAddField(GROWTH_RATE, getGrowth());
		dbAddField(INTEREST_RATE, getInterest());
		dbAddField(OWNERSHIP_ID, getOwnership());
		dbAddField(TITLE_ID, getTitle());
		
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select CASH_ID from CASH where UUID='" + uuid + "'");
		Object o = ret.get("CASH_ID");
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	/**
	 * @param acct The acct to set.
	 */
	public void setAcct(long acct) {
		this.acct = acct;
	}
	/**
	 * @param bank The bank to set.
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * @param basis The basis to set.
	 */
	public void setBasis(double basis) {
		this.basis = basis;
	}
	/**
	 * @param currentValue The currentValue to set.
	 */
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
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
	 * @param interest The interest to set.
	 */
	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param ownership The ownership to set.
	 */
	public void setOwnership(long ownership) {
		this.ownership = ownership;
	}
	
	/**
	 * @param title The title to set.
	 */
	public void setTitle(long title) {
		this.title = title;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() 
	{
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbAddField(DESCRIPTION, getName());
		
		dbAddField(DESCRIPTION, getName());
		dbAddField(BANK, getBank());
		dbAddField(NOTES, getNotes());
		dbAddField(ACCT, getAcct());
		dbAddField(BASIS, Double.toString(getBasis()));
		dbAddField(VALUE, Double.toString(getCurrentValue()));
		dbAddField(GROWTH_RATE, Double.toString(getGrowth()));
		dbAddField(INTEREST_RATE, Double.toString(getInterest()));
		dbAddField(OWNERSHIP_ID, Double.toString(getOwnership()));
		dbAddField(TITLE_ID, Double.toString(getTitle()));
		
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}
}
