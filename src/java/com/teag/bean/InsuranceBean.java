/*
 * Created on Mar 8, 2005
 *
 */
package com.teag.bean;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Description Life Insurace Bean, 
 * @author Paul Stay
 *
 */
public class InsuranceBean extends AssetSqlBean {
	
	private static final long serialVersionUID = -3776608420935107445L;
	public static final String ID = "LIFE_ID";
	public static final String LIFE_ID = "LIFE_ID";
	public static final String OWNER_ID = "OWNER_ID";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String VALUE = "VALUE";
	public static final String INSURED = "INSURED";
	public static final String OWNER = "OWNER";
	public static final String BENEFICIARY = "BENEFICIARY";
	public static final String DATE_ACQUIRED = "DATE_ACQUIRED";
	public static final String FACE_VALUE = "FACE_VALUE";
	public static final String FUTURE_CASH_VALUE = "FUTURE_CASH_VALUE";
	public static final String POLICY_TYPE_ID = "POLICY_TYPE_ID";
	public static final String PREMIUMS = "PREMIUMS";
	public static final String PREMIUM_FREQ_ID = "PREMIUM_FREQ_ID";
	public static final String INSURANCE_COMPANY = "INSURANCE_COMPANY";
	public static final String POLICY_NUMBER = "POLICY_NUMBER";
	public static final String LOANS = "LOANS";
	public static final String NOTES = "NOTES";
	public static final String TAXABLE_LIFE_INCL = "TAXABLE_LIFE_INCL";
	
	private static String tableName ="LIFE_INSURANCE";	// Name of the table
	private static String idFieldName ="LIFE_ID";	
	private long id;	// The asset ID in the table
	private String uuid;
	
	private long ownerId;					// Id of the owner of the asset
	private String description;
	private double value;			// Current cash value
	private String insured;
	private String owner;
	private String beneficiary;
	private String dateAcquired;
	private double faceValue;		// Life Death Benefit
	private double futureCashValue;		//In 10 years 
	private long policyTypeId;
	private double premiums;
	private long premiumFreqId;
	private String insuranceCompany;
	private String policyNumber;
	private double loans;
	private String taxableLifeInc;
	private String notes;
	
	private HashMap<String,Object> account;
	
	public InsuranceBean()
	{
		this.beanType = SqlBean.INSURANCE;
	}
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	/**
	 * getAssetBasis()
	 * @return
	 */
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
		return value;
	}
	
	public ArrayList<InsuranceBean> getBeans(String whereClause) {
		ArrayList<InsuranceBean> bList = new ArrayList<InsuranceBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			InsuranceBean nb = new InsuranceBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	
	public String getBeneficiary() {
		return beneficiary;
	}
	
	public String getDateAcquired() {
		Date now = new Date();
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("M/dd/yyyy");
		if(dateAcquired != null)
			return dateAcquired;
		return df.format(now);
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getFaceValue() {
		return faceValue;
	}
	
	public double getFutureCashValue() {
		return futureCashValue;
	}
	
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	
	public String getInsured() {
		return insured;
	}
	
	public double getLoans() {
		return loans;
	}

	public String getNotes() {
		return notes;
	}

	public String getOwner() {
		return owner;
	}

	/**
	 * @return Returns the ownerId.
	 */
	public long getOwnerId() {
		return ownerId;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public long getPolicyTypeId() {
		return policyTypeId;
	}

	public long getPremiumFreqId() {
		if(premiumFreqId == 0)
			return 1;
		return premiumFreqId;
	}

	public double getPremiums() {
		return premiums;
	}

	public String getTaxableLifeInc() {
		return taxableLifeInc;
	}

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
			
			setOwnerId(getLong(account, OWNER_ID));
			setId(getLong(account,LIFE_ID));
			setDescription(getString(account,DESCRIPTION));
			setValue(getDouble(account, VALUE));
			setInsured(getString(account,INSURED));
			setOwner(getString(account, OWNER));
			setBeneficiary(getString(account,BENEFICIARY));
			setDateAcquired(getDate(account, DATE_ACQUIRED));
			setFaceValue(getDouble(account,FACE_VALUE));
			setFutureCashValue(getDouble(account,FUTURE_CASH_VALUE));
			setPremiumFreqId(getInteger(account,PREMIUM_FREQ_ID));
			setPolicyTypeId(getLong(account, POLICY_TYPE_ID));
			setPremiums(getDouble(account,PREMIUMS));
			setInsuranceCompany(getString(account,INSURANCE_COMPANY));
			setPolicyNumber(getString(account, POLICY_NUMBER));
			setLoans(getDouble(account, LOANS));
			setNotes(getString(account, NOTES));
			setTaxableLifeInc(getString(account, TAXABLE_LIFE_INCL));
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
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(OWNER_ID, (int)getOwnerId());
		dbAddField(VALUE, getValue());
		dbAddField(INSURED, getInsured());
		dbAddField(OWNER, getOwner());
		dbAddField(BENEFICIARY, getBeneficiary());
		dbAddField(PREMIUM_FREQ_ID, getPremiumFreqId());
		dbAddDate(DATE_ACQUIRED, getDateAcquired());
		dbAddField(FACE_VALUE, getFaceValue());
		dbAddField(FUTURE_CASH_VALUE, getFutureCashValue());
		dbAddField(POLICY_TYPE_ID, getPolicyTypeId());
		dbAddField(PREMIUMS, getPremiums());
		dbAddField(INSURANCE_COMPANY, getInsuranceCompany());
		dbAddField(POLICY_NUMBER, getPolicyNumber());
		dbAddField(LOANS, getLoans());
		dbAddField(NOTES, getNotes());
		dbAddField(TAXABLE_LIFE_INCL, getTaxableLifeInc());
		
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + idFieldName + " from " + tableName + 
				" where UUID='" + uuid + "'");
		Object o = ret.get(idFieldName);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	/**
	 * Query the database records and return a list of beans for life insurance.
	 * @param field
	 * @param value
	 * @return Array of InsuranceBean
	 */
	public InsuranceBean[] query(String field, String value) {
		InsuranceBean[] records = null;
		HashMap<String,Object> rec;
		dbObject.start();
		String sqlCount = "select count(*) as cnt from " + tableName + " where " + field + "='" + value + "'";
		rec = dbObject.execute(sqlCount);
		if( rec == null )
			return null;

		long count = 0;
		count = ((Number) rec.get("cnt")).longValue();
		
		if( count > 0) {
			records = new InsuranceBean[(int)count];
			String sqlStmt = "select " + LIFE_ID + " from " + tableName + " where " + field + "='" + value + "'";
			int i = 0;
			rec = dbObject.execute(sqlStmt);
			while( rec != null && (i < count)){
				records[i] = new InsuranceBean();
				records[i].setId(getLong(rec, LIFE_ID));
				records[i].initialize();
				rec = dbObject.next();
				i++;
			}
		}
		
		dbObject.stop();
		return records;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public void setDateAcquired(String dateAcquired) {
		this.dateAcquired = dateAcquired;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}

	public void setFutureCashValue(double futureCashValue) {
		this.futureCashValue = futureCashValue;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public void setInsured(String insured) {
		this.insured = insured;
	}

	public void setLoans(double loans) {
		this.loans = loans;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @param ownerId The ownerId to set.
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public void setPolicyTypeId(long policyTypeId) {
		this.policyTypeId = policyTypeId;
	}

	public void setPremiumFreqId(long premiumFreqId) {
		this.premiumFreqId = premiumFreqId;
	}

	public void setPremiums(double premiums) {
		this.premiums = premiums;
	}

	public void setTaxableLifeInc(String taxableLifeInc) {
		if(taxableLifeInc.equals("Y") || taxableLifeInc.equals("N"))
			this.taxableLifeInc = taxableLifeInc;
		else
			this.taxableLifeInc = "Y";	// Default
	}

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
		
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(VALUE, getValue());
		dbAddField(INSURED, getInsured());
		dbAddField(OWNER, getOwner());
		dbAddField(BENEFICIARY, getBeneficiary());
		dbAddDate(DATE_ACQUIRED, getDateAcquired());
		dbAddField(FACE_VALUE, getFaceValue());
		dbAddField(FUTURE_CASH_VALUE, getFutureCashValue());
		dbAddField(POLICY_TYPE_ID, getPolicyTypeId());
		dbAddField(PREMIUMS, getPremiums());
		dbAddField(PREMIUM_FREQ_ID, getPremiumFreqId());
		dbAddField(INSURANCE_COMPANY, getInsuranceCompany());
		dbAddField(POLICY_NUMBER, getPolicyNumber());
		dbAddField(LOANS, getLoans());
		dbAddField(NOTES, getNotes());
		dbAddField(TAXABLE_LIFE_INCL, getTaxableLifeInc());
		
		dbObject.setWhere(idFieldName + "='" + id + "'");
		dbObject.update();

	}
}
