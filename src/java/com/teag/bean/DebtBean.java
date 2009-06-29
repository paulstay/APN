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
public class DebtBean extends AssetSqlBean {
	public final static String ID = "DEBT_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String DEBT_TYPE_ID = "DEBT_TYPE_ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String BANK = "BANK";
	public final static String ACCT = "ACCT";
	public final static String BASIS = "BASIS";
	public final static String VALUE = "VALUE";
	public final static String LOAN_TERM = "LOAN_TERM";
	public final static String INTEREST_RATE = "INTEREST_RATE";
	public final static String TAX = "TAX";
	public final static String AMT = "AMT";
	public final static String NOTES = "NOTES";
	public final static String tableName = "DEBT";

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5476638999444254095L;
	private static String idFieldName ="DEBT_ID";
	private long id;	// The asset ID in the table	
	private String uuid;
	private long ownerId;					// Id of the owner of the asset
	
	private String description;
	private long debtTypeId;
	private String bank;
	private long acct;
	private long ownershipId;
	private long titleId;
	private String notes;
	private double value;
	private long loanTerm;
	private double interestRate;
	
	private HashMap<String,Object> account;
	
	public DebtBean()
	{
		this.beanType = SqlBean.DEBT;
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
	public double getAssetBasis() {
		return 0;
	}
	@Override
	public double getAssetGrowth() {
		return 0;
	}
	

	@Override
	public double getAssetIncome() {
		return interestRate;
	}

	@Override
	public double getAssetLiability() {
		return value;
	}

	@Override
	public String getAssetName() {
		return description;
	}
	
	@Override
	public double getAssetValue() {
		return value;
	}
	
	/**
	 * @return Returns the bank.
	 */
	public String getBank() {
		return bank;
	}
	
	public ArrayList<DebtBean> getBeans(String whereClause) {
		ArrayList<DebtBean> bList = new ArrayList<DebtBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			DebtBean nb = new DebtBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	/**
	 * @return Returns the debtTypeId.
	 */
	public long getDebtTypeId() {
		return debtTypeId;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the interestRate.
	 */
	public double getInterestRate() {
		return interestRate;
	}
	/**
	 * @return Returns the loanTerm.
	 */
	public long getLoanTerm() {
		return loanTerm;
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

		String sql = "select * from " + tableName + " where " + idFieldName + "='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());
			this.setId(id);
			this.setDescription(account.get("OWNER_ID") == null ? "" : (String)(account.get("DESCRIPTION")));
			this.setBank(account.get("OWNER_ID") == null ? "" : (String)(account.get("BANK")));
			this.setAcct(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("ACCT"))).longValue());
			this.setAcct(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("ACCT"))).longValue());
			this.setDebtTypeId(((Number)(account.get("DEBT_TYPE_ID"))).longValue());
			this.setOwnershipId(account.get("OWNERSHIP_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitleId(account.get("TITLE_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setBank((String)(account.get("BANK")));
			this.setNotes((String)(account.get("NOTES")));
			this.setValue(((Number)(account.get("VALUE"))).doubleValue());
			if( account.get("LOAN_TERM") != null) {
				this.setLoanTerm(((Number)(account.get("LOAN_TERM"))).intValue());
			}
			if( account.get("INTEREST_RATE") != null) { 
					this.setInterestRate(((Number)(account.get("INTEREST_RATE"))).doubleValue());
			}
			//this.setLoanTerm(((Number)(account.get("LOAN_TERM"))).intValue());
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
		dbAddField(DESCRIPTION, this.getDescription());
		dbAddField(DEBT_TYPE_ID, "" + this.getDebtTypeId());
		dbAddField(BANK, this.getBank());
		dbAddField(ACCT, "" + this.getAcct());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, this.getNotes());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(LOAN_TERM, "" + this.getLoanTerm());
		dbAddField(INTEREST_RATE, "" + this.getInterestRate());
		
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
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
	 * @param debtTypeId The debtTypeId to set.
	 */
	public void setDebtTypeId(long debtTypeId) {
		this.debtTypeId = debtTypeId;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param interestRate The interestRate to set.
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	/**
	 * @param loanTerm The loanTerm to set.
	 */
	public void setLoanTerm(long loanTerm) {
		this.loanTerm = loanTerm;
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
		
		// Add the necessary fields here
		dbAddField(OWNER_ID, "" + this.getOwnerId());
		dbAddField(DESCRIPTION, this.getDescription());
		dbAddField(DEBT_TYPE_ID, "" + this.getDebtTypeId());
		dbAddField(BANK, this.getBank());
		dbAddField(ACCT, "" + this.getAcct());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, this.getNotes());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(LOAN_TERM, "" + this.getLoanTerm());
		dbAddField(INTEREST_RATE, "" + this.getInterestRate());

		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();

	}
}
