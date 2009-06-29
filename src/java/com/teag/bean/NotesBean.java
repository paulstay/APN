package com.teag.bean;

/**
 * @author Paul Stay
 *	
 */

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;

public class NotesBean extends AssetSqlBean {

	public static final String ID = "ID";
	public static final String OWNER_ID = "OWNER_ID";
	public static final String OWNERSHIP = "OWNERSHIP_ID";
	public static final String TITLE = "TITLE_ID";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String YEARS = "YEARS";
	public static final String PAYMENTS_PER_YEAR = "PAYMENTS_PER_YEAR";
	public static final String INTEREST_RATE = "INTEREST_RATE";
	public static final String LOAN_AMOUNT = "VALUE";
	public static final String NOTE_TYPE = "NOTE_TYPE";

	private static final long serialVersionUID = -6162736245238882221L;
	
	public String tableName = "NOTES";

	private String description;
	private int years;
	private int paymentsPerYear;
	private double interestRate;
	private double loanAmount;
	private String noteType;
	private long ownershipId;
	private long titleId;
	private long id;
	private long ownerId;
	
	private String uuid;
	
	public NotesBean() {
		super();
		beanType = SqlBean.NOTES;
	}

	public void delete() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.delete(tableName, "ID='" + Long.toString(getId()) + "'");
		dbObject.stop();
		
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
		return 0;
	}

	@Override
	public double getAssetLiability() {
		return 0;
	}

	@Override
	public String getAssetName() {
		return getDescription();
	}
	
	@Override
	public double getAssetValue() {
		return getLoanAmount();
	}
	
	public ArrayList<NotesBean> getBeans(String whereClause) {
		ArrayList<NotesBean> bList = new ArrayList<NotesBean>();
		String sql = "select ID from NOTES where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			NotesBean nb = new NotesBean();
			nb.setId(id);
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		
		for(NotesBean c : bList) {
			c.initialize();
		}
		
		return bList;
	}

	private double getDbValue(String field, HashMap<String,Object> a) {
		if( a.get(field) != null ) {
			return ((Number)(a.get(field))).doubleValue();
		}
		return 0;
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}

	public double getInterestRate() {
		return interestRate;
	}

	private int getIntValue(String field, HashMap<String,Object> a) {
		if( a.get(field) != null ) {
			return ((Number)(a.get(field))).intValue();
		}
		return 0;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public String getNoteType() {
		return noteType;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public long getOwnershipId() {
		return ownershipId;
	}

	public int getPaymentsPerYear() {
		return paymentsPerYear;
	}

	public long getTitleId() {
		return titleId;
	}

	public int getYears() {
		return years;
	}

	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		HashMap<String,Object> account = null;
		String sql = "select * from NOTES where ID='" + Long.toString(getId()) + "'";
		account = dbObject.execute(sql);
		if( account != null) {
			setDescription((String)account.get(DESCRIPTION));
			setOwnerId((Long)account.get(OWNER_ID));
			setYears(getIntValue(YEARS, account));
			setPaymentsPerYear(getIntValue(PAYMENTS_PER_YEAR, account));
			setInterestRate(getDbValue(INTEREST_RATE, account));
			setLoanAmount(getDbValue(LOAN_AMOUNT, account));
			setNoteType((String)account.get(NOTE_TYPE));
			setOwnershipId(getIntValue(OWNERSHIP, account));
			setTitleId(getIntValue(TITLE,account));
		}
		dbObject.stop();
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		dbObject.addField(DESCRIPTION, DBObject.dbString(getDescription()));
		dbObject.addField(OWNER_ID,Long.toString(getOwnerId()));
		dbObject.addField(YEARS,Integer.toString(getYears()));
		dbObject.addField(PAYMENTS_PER_YEAR, Integer.toString(getPaymentsPerYear()));
		dbObject.addField(INTEREST_RATE, Double.toString(getInterestRate()));
		dbObject.addField(LOAN_AMOUNT, Double.toString(getLoanAmount()));
		dbObject.addField(NOTE_TYPE, DBObject.dbString(getNoteType()));
		dbObject.addField(OWNERSHIP, Long.toString(getOwnershipId()));
		dbObject.addField(TITLE, Long.toString(getTitleId()));
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ID from NOTES where UUID='"
				+ uuid + "'");
		Object o = ret.get(ID);
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setOwnershipId(long ownership) {
		this.ownershipId = ownership;
	}

	public void setPaymentsPerYear(int paymentsPerYear) {
		this.paymentsPerYear = paymentsPerYear;
	}

	public void setTitleId(long title) {
		this.titleId = title;
	}

	public void setYears(int years) {
		this.years = years;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		dbObject.addField(DESCRIPTION, DBObject.dbString(getDescription()));
		dbObject.addField(OWNER_ID,Long.toString(getOwnerId()));
		dbObject.addField(YEARS,Integer.toString(getYears()));
		dbObject.addField(PAYMENTS_PER_YEAR, Integer.toString(getPaymentsPerYear()));
		dbObject.addField(INTEREST_RATE, Double.toString(getInterestRate()));
		dbObject.addField(LOAN_AMOUNT, Double.toString(getLoanAmount()));
		dbObject.addField(NOTE_TYPE, DBObject.dbString(getNoteType()));
		dbObject.addField(OWNERSHIP, Long.toString(getOwnershipId()));
		dbObject.addField(TITLE, Long.toString(getTitleId()));
		dbObject.setWhere("ID='" + Long.toString(getId()) + "'");
		dbObject.update();
		dbObject.stop();
	}

}
