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
public class SecuritiesBean extends AssetSqlBean {
	public final static String ID = "SECURITIES_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String BANK = "BANK";
	public final static String ACCT = "ACCT";
	public final static String BASIS = "BASIS";
	public final static String VALUE = "VALUE";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String DIV_INT = "DIV_INT";
	public final static String CG_TURNOVER = "CG_TURNOVER";
	public final static String NOTES = "NOTES";
	public final static String tableName = "SECURITIES";

	/**
	 * 
	 */
	private static final long serialVersionUID = -6760848441661233961L;

	private static String idFieldName = "SECURITIES_ID";

	private long id; // The asset ID in the table

	private String uuid;

	private long ownerId; // Id of the owner of the asset

	private String description;

	private String bank;

	private double acct;

	private long ownershipId;

	private long titleId;

	private String notes;

	private double basis;

	private double value;

	private double growthRate;

	private double divInt;

	private double cgTurnover;

	private HashMap<String,Object> account;

	public SecuritiesBean() {
		this.beanType = SqlBean.SECURITIES;
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
	public double getAcct() {
		return acct;
	}

	@Override
	public double getAssetBasis() {
		return this.getBasis();
	}

	@Override
	public double getAssetGrowth() {
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

	public ArrayList<SecuritiesBean> getBeans(String whereClause) {
		ArrayList<SecuritiesBean> bList = new ArrayList<SecuritiesBean>();
		String sql = "select " + ID + " from " + tableName + " where "
				+ whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while (ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			SecuritiesBean nb = new SecuritiesBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}

	/**
	 * @return Returns the cgTurnover.
	 */
	public double getCgTurnover() {
		return cgTurnover;
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
		if(notes !=null && notes.length() > 0)
			return notes;
		return "";
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		account = null;

		String sql = "select * from " + tableName + " where " + ID + "='" + id
				+ "'";

		account = dbObject.execute(sql);

		dbObject.stop();

		if (account != null) {

			this.setOwnerId(account.get("OWNER_ID") == null ? 0
					: ((Number) (account.get("OWNER_ID"))).longValue());

			// Add any additional fields
			this.setDescription((String) account.get("DESCRIPTION"));
			this.setBank((String) account.get("BANK"));
			this.setAcct(account.get("ACCT") == null ? 0 : ((Number) (account
					.get("ACCT"))).longValue());
			this.setOwnershipId(account.get("OWNERSHIP_ID") == null ? 0
					: ((Number) (account.get("OWNERSHIP_ID"))).longValue());
			this.setTitleId(account.get("TITLE_ID") == null ? 0
					: ((Number) (account.get("TITLE_ID"))).longValue());
			this.setNotes((String) account.get("NOTES"));
			this.setBasis(account.get("BASIS") == null ? 0 : ((Number) (account
					.get("BASIS"))).doubleValue());
			this.setValue(account.get("VALUE") == null ? 0 : ((Number) (account
					.get("VALUE"))).doubleValue());
			this.setGrowthRate(account.get("GROWTH_RATE") == null ? 0
					: ((Number) (account.get("GROWTH_RATE"))).doubleValue());
			this.setDivInt(account.get("DIV_INT") == null ? 0
					: ((Number) (account.get("DIV_INT"))).doubleValue());
			this.setCgTurnover(account.get("CG_TURNOVER") == null ? 0
					: ((Number) (account.get("CG_TURNOVER"))).doubleValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(OWNER_ID, "" + this.getOwnerId());
		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(BANK, "" + this.getBank());
		dbAddField(ACCT, "" + this.getAcct());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(DIV_INT, "" + this.getDivInt());
		dbAddField(CG_TURNOVER, "" + this.getCgTurnover());

		// ##############
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName
				+ " where UUID='" + uuid + "'");
		Object o = ret.get(idFieldName);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();

	}

	/**
	 * @param acct
	 *            The acct to set.
	 */
	public void setAcct(double acct) {
		this.acct = acct;
	}

	/**
	 * @param bank
	 *            The bank to set.
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @param basis
	 *            The basis to set.
	 */
	public void setBasis(double basis) {
		this.basis = basis;
	}

	/**
	 * @param cgTurnover
	 *            The cgTurnover to set.
	 */
	public void setCgTurnover(double cgTurnover) {
		this.cgTurnover = cgTurnover;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param divInt
	 *            The divInt to set.
	 */
	public void setDivInt(double divInt) {
		this.divInt = divInt;
	}

	/**
	 * @param growthRate
	 *            The growthRate to set.
	 */
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @param notes
	 *            The notes to set.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @param ownerId
	 *            The ownerId to set.
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @param ownershipId
	 *            The ownershipId to set.
	 */
	public void setOwnershipId(long ownershipId) {
		this.ownershipId = ownershipId;
	}

	/**
	 * @param titleId
	 *            The titleId to set.
	 */
	public void setTitleId(long titleId) {
		this.titleId = titleId;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(BANK, "" + this.getBank());
		dbAddField(ACCT, "" + this.getAcct());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(BASIS, "" + this.getBasis());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		dbAddField(DIV_INT, "" + this.getDivInt());
		dbAddField(CG_TURNOVER, "" + this.getCgTurnover());

		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
	}
}
