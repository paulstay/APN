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
public class RetirementBean extends AssetSqlBean {
	public final static String ID = "RETIREMENT_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String OWNERSHIP_ID = "OWNERSHIP_ID";
	public final static String TITLE_ID = "TITLE_ID";
	public final static String BANK = "BANK";
	public final static String ACCT = "ACCT";
	public final static String ANNUAL_CONT = "ANNUAL_CONT";
	public final static String VALUE = "VALUE";
	public final static String EMPLOYEE_CONTRIB = "EMPLOYEE_CONTRIB";
	public final static String EMPLOYER_CONTRIB = "EMPLOYER_CONTRIB";
	public final static String NAMED_BENEFICIARY = "NAMED_BENEFICIARY";
	public final static String BENEFICIARY_TYPE = "BENEFICIARY_TYPE";
	public final static String BIRTH_DATE = "BIRTH_DATE";
	public final static String LIFE_EXP_CALC = "LIFE_EXP_CALC";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String NOTES = "NOTES";
	public final static String TOCHARITY = "TOCHARITY";
	public final static String tableName = "RETIREMENT";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7750991949978228484L;
	private static String idFieldName ="RETIREMENT_ID";
	private long id;	// The asset ID in the table	
	private String uuid;
	private long ownerId;					// Id of the owner of the asset
	private String description;
	private long ownershipId;
	private long titleId;
	private String notes;
	private double annualContrib;
	private double value;
	private double employeeContrib;
	private double employerContrib;
	private String namedBeneficiary;
	private String beneficiaryType;
	private String birthDate;
	private String lifeExpCalc;
	private double growthRate;
	private SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
	private String toCharity = "N";
	
	
	private HashMap<String,Object> account;
	public RetirementBean()
	{
		this.beanType = SqlBean.RETIREMENT;
	}
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	/**
	 * @return Returns the annualContrib.
	 */
	public double getAnnualContrib() {
		return annualContrib;
	}
	@Override
	public double getAssetBasis(){
		return 0.0;
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
	public ArrayList<RetirementBean> getBeans(String whereClause) {
		ArrayList<RetirementBean> bList = new ArrayList<RetirementBean>();
		String sql = "select " + ID + " from " + tableName + " where "
				+ whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while (ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			RetirementBean nb = new RetirementBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	/**
	 * @return Returns the beneficiaryType.
	 */
	public String getBeneficiaryType() {
		return beneficiaryType;
	}
	/**
	 * @return Returns the birthDate.
	 */
	public String getBirthDate() {
		SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy");
		try {
			df.parse(birthDate);
		} catch (Exception e) {
			birthDate = "01/01/1970";
		}
		return birthDate;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return Returns the employeeContrib.
	 */
	public double getEmployeeContrib() {
		return employeeContrib;
	}
	/**
	 * @return Returns the employerContrib.
	 */
	public double getEmployerContrib() {
		return employerContrib;
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
	 * @return Returns the lifeExpCalc.
	 */
	public String getLifeExpCalc() {
		return lifeExpCalc;
	}
	/**
	 * @return Returns the namedBeneficiary.
	 */
	public String getNamedBeneficiary() {
		return namedBeneficiary;
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
	public String getToCharity() {
		return toCharity;
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
			String bDay = account.get("BIRTH_DATE") == null ? null : this.dateformat.format( account.get("BIRTH_DATE"));
			
			this.setOwnerId(account.get("OWNER_ID") == null ? 0 : ((Number)(account.get("OWNER_ID"))).longValue());

			// Add any additional fields
			this.setDescription((String)account.get("DESCRIPTION"));
			this.setOwnershipId(account.get("OWNERSHIP_ID") == null ? 0 : ((Number)(account.get("OWNERSHIP_ID"))).longValue());
			this.setTitleId(account.get("TITLE_ID") == null ? 0 : ((Number)(account.get("TITLE_ID"))).longValue());
			this.setNotes((String)account.get("NOTES"));
			this.setAnnualContrib(account.get("ANNUAL_CONTRIB") == null ? 0 : ((Number)(account.get("ANNUAL_CONTRIB"))).doubleValue());
			this.setValue(account.get("VALUE") == null ? 0 : ((Number)(account.get("VALUE"))).doubleValue());
			this.setEmployeeContrib(account.get("EMPLOYEE_CONTRIB") == null ? 0 : ((Number)(account.get("EMPLOYEE_CONTRIB"))).doubleValue());
			this.setEmployerContrib(account.get("EMPLOYER_CONTRIB") == null ? 0 : ((Number)(account.get("EMPLOYER_CONTRIB"))).doubleValue());
			this.setNamedBeneficiary((String)account.get("NAMED_BENEFICIARY"));
			this.setBeneficiaryType((String)account.get("BENEFICIARY_TYPE"));
			this.setBirthDate(bDay);
			this.setLifeExpCalc((String)account.get("LIFE_EXP_CALC"));
			this.setGrowthRate(account.get("GROWTH_RATE") == null ? 0 : ((Number)(account.get("GROWTH_RATE"))).doubleValue());
			this.setToCharity((String)account.get(TOCHARITY));
		}		
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		Date BDay;
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		
		dbAddField(OWNER_ID, "" + this.getOwnerId());
		// ####### Add additional fields 
		dbAddField(DESCRIPTION, "" + this.getDescription());
		dbAddField(OWNERSHIP_ID, "" + this.getOwnershipId());
		dbAddField(TITLE_ID, "" + this.getTitleId());
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(ANNUAL_CONT, "" + this.getAnnualContrib());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(EMPLOYEE_CONTRIB, "" + this.getEmployeeContrib());
		dbAddField(EMPLOYER_CONTRIB, "" + this.getEmployerContrib());
		dbAddField(NAMED_BENEFICIARY, "" + this.getNamedBeneficiary());
		dbAddField(BENEFICIARY_TYPE, "" + this.getBeneficiaryType());
		dbAddField(TOCHARITY, "" + this.getToCharity());
		if(null != this.getBirthDate())
		{
			BDay = dateformat.parse(this.getBirthDate(), new ParsePosition(0));
			dbAddField(BIRTH_DATE, dbObject.dbDate(BDay));
		}
		dbAddField(LIFE_EXP_CALC, "" + this.getLifeExpCalc());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		
		//##############
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(idFieldName);
		this.id = Integer.parseInt(o.toString());
		dbObject.stop();

	}
	/**
	 * @param annualContrib The annualContrib to set.
	 */
	public void setAnnualContrib(double annualContrib) {
		this.annualContrib = annualContrib;
	}
	/**
	 * @param beneficiaryType The beneficiaryType to set.
	 */
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}
	/**
	 * @param birthDate The birthDate to set.
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param employeeContrib The employeeContrib to set.
	 */
	public void setEmployeeContrib(double employeeContrib) {
		this.employeeContrib = employeeContrib;
	}

	/**
	 * @param employerContrib The employerContrib to set.
	 */
	public void setEmployerContrib(double employerContrib) {
		this.employerContrib = employerContrib;
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
	 * @param lifeExpCalc The lifeExpCalc to set.
	 */
	public void setLifeExpCalc(String lifeExpCalc) {
		this.lifeExpCalc = lifeExpCalc;
	}
	
	/**
	 * @param namedBeneficiary The namedBeneficiary to set.
	 */
	public void setNamedBeneficiary(String namedBeneficiary) {
		this.namedBeneficiary = namedBeneficiary;
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
	
	public void setToCharity(String toCharity) {
		this.toCharity = toCharity;
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
		dbAddField(NOTES, "" + this.getNotes());
		dbAddField(ANNUAL_CONT, "" + this.getAnnualContrib());
		dbAddField(VALUE, "" + this.getValue());
		dbAddField(EMPLOYEE_CONTRIB, "" + this.getEmployeeContrib());
		dbAddField(EMPLOYER_CONTRIB, "" + this.getEmployerContrib());
		dbAddField(NAMED_BENEFICIARY, "" + this.getNamedBeneficiary());
		dbAddField(BENEFICIARY_TYPE, "" + this.getBeneficiaryType());
		dbAddField(TOCHARITY, "" + this.getToCharity());
		if(null != this.getBirthDate())
		{
			dbAddDate(BIRTH_DATE, getBirthDate());
		} else {
			dbAddDate(BIRTH_DATE, "6/1/1960");
		}

		dbAddField(LIFE_EXP_CALC, "" + this.getLifeExpCalc());
		dbAddField(GROWTH_RATE, "" + this.getGrowthRate());
		
		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();

	}
}
