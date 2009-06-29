/*
 * Created on Jan 27, 2005
 *
 */
package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author tjensen
 *
 */
public class BondBean extends AssetSqlBean  {

	public final static String ID = "BOND_ID";
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
	public final static String TAX = "TAX";
	public final static String AMT = "AMT";
	public final static String NOTES = "NOTES";
	public final static String tableName = "BONDS";
	/**
	 * 
	 */
	private static final long serialVersionUID = -2081198486746107890L;
	private String name;
	private long ownership;
	private long title;
	private double basis;
	private double currentValue;
	private double interest;
	private double growth;
	private String tax;
	private String amt;
	private String notes;
	private long ownerId;
	
	private long id;
	private String uuid;
	
	private HashMap<String,Object> account;

	public BondBean()
	{
		this.beanType = SqlBean.BONDS;
	}
		public void delete() {
			dbObject.start();
			String whereClause = ID + "='" + getId() + "'";
			dbObject.delete(tableName, whereClause);
			dbObject.stop();
		}
	/**
 * @return Returns the amt.
 */
public String getAmt() {
	return amt;
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
	 * @return Returns the basis.
	 */
	public double getBasis() {
		return basis;
	}
	public ArrayList<BondBean> getBeans(String whereClause) {
		ArrayList<BondBean> bList = new ArrayList<BondBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			BondBean nb = new BondBean();
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
	 * @return Returns the tax.
	 */
	public String getTax() {
		return tax;
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
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		account = null;

		String sql = "select * from BONDS where BOND_ID='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			
			
			this.setName((String)(account.get("DESCRIPTION")));
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());
			this.setOwnership(account.get("OWNERSHIP_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitle(account.get("TITLE_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setBasis(account.get("BASIS") == null ? 0 : ((Number)(account.get("BASIS"))).doubleValue());
			this.setCurrentValue(account.get("VALUE") == null ? 0 : ((Number)(account.get("VALUE"))).doubleValue());
			this.setInterest(account.get("INTEREST_RATE") == null ? 0 : ((Number)(account.get("INTEREST_RATE"))).doubleValue());
			this.setGrowth(account.get("GROWTH_RATE") == null ? 0 : ((Number)(account.get("GROWTH_RATE"))).doubleValue());
			this.setTax(account.get("TAX") == null ? "" : (String)(account.get("TAX")));
			this.setAmt(account.get("AMT") == null ? "" : (String)(account.get("AMT")));
			this.setNotes((String)(account.get("NOTES")));

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
		dbAddField(DESCRIPTION, this.getName());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnership());
		dbAddField(TITLE_ID, "" + this.getTitle());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getCurrentValue());
		dbAddField(INTEREST_RATE, "" + this.getInterest());		
		dbAddField(GROWTH_RATE, "" + this.getGrowth());
		dbAddField(TAX, this.getTax());
		dbAddField(AMT, this.getAmt());
		dbAddField(NOTES, this.getNotes());
		
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select BOND_ID from BONDS where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();
	}
	
	/**
	 * @param amt The amt to set.
	 */
	public void setAmt(String amt) {
		this.amt = amt;
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
	 * @param tax The tax to set.
	 */
	public void setTax(String tax) {
		this.tax = tax;
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
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbAddField(DESCRIPTION,  getName());
		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(OWNERSHIP_ID, "" +  getOwnership());
		dbAddField(TITLE_ID, "" + getTitle());
		dbAddField(BASIS, "" + getBasis());
		dbAddField(VALUE, "" + getCurrentValue());
		dbAddField(INTEREST_RATE, "" + getInterest());		
		dbAddField(GROWTH_RATE, "" + getGrowth());
		dbAddField(TAX, getTax());
		dbAddField(AMT, getAmt());
		dbAddField(NOTES, getNotes());
		
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
	}
}
