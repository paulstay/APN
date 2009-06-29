package com.teag.bean;

/**
 * <p>Title: PersonBean
 * <p>Description: Store information for a person defined in the client code. 
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: NoBoundsIT</p>
 * @author Paul Stay
 * @version 1.0
 */

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.estate.constants.HealthTypes;
import com.estate.db.DBObject;

public class PersonBean  extends SqlBean implements Serializable {
	
	/**
	 * <Field name='PERSON_ID' type='long' size='19' null='false' autoincrement='true' valid='true' />
		<Group name='Name' />
		<Field name='GENDER' type='string' size='1' null='false' options='M=Male;F=Female' />
		<Field name='SSAN' type='string' size='11' null='true' />
		<Field name='OCCUPATION' type='string' size='35' null='true' />
		<Field name='BIRTH_DATE' type='date' null='false' />
		<Field name='BIRTH_CITY' type='string' size='120' null='true' />
		<Field name='BIRTH_STATE' type='string' size='2' null='true' />
		<Field name='CITIZENSHIP_CODE' type='string' size='2' null='true' />
		<Field name='LIFE_EXP' type ='long' size='19' null='true' />		
		<Field name='HEALTH_ID' type='long' size='19' null='true' />
		<ForeignKey field='BIRTH_STATE' reftable='STATE' reffield='STATE_CODE' />
		<ForeignKey field='CITIZENSHIP_CODE' reftable='COUNTRY' reffield='COUNTRY_CODE' />
		<ForeignKey field='HEALTH_ID' reftable='HEALTH' reffield='HEALTH_ID' />
		<PrimaryKey field='PERSON_ID' />
	 */
	public final static String ID = "PERSON_ID";
	public final static String PERSON_ID = "PERSON_ID";
	public final static String tableName = "PERSON";

	private static final long serialVersionUID = -7957544669395088607L;
	private String firstName;
	private String lastName;
	private String middleName;
	private String birthDate;
	private String city;
	private String state;
	private String gender;
	private String ssn;
	private String occupation;
	private String citizenship;
	private int lifeExpectancy; 
	private long healthId;
	private long id;
	private String uuid;
	
	private HashMap<String,Object> account;
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	public void empty(){
		setFirstName("");
		setMiddleName("");
		setLastName("");
		setOccupation("");
		setGender("");
		setBirthDate("");
		setCity("");
		setState("");
		this.setSsn("");
		this.setCitizenship("US");
		
	}
	/**
	 * @return Returns the birthDate.
	 */
	public String getBirthDate() {
		return birthDate;
	}
	/**
	 * @return Returns the citizenship.
	 */
	public String getCitizenship() {
		if(citizenship != null)
			return citizenship.toUpperCase();
		return "US";
	}
	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return Returns the dbObject.
	 */
	@Override
	public DBObject getDbObject() {
		return dbObject;
	}
	/**
	 * @param dbObject The dbObject to set.
	 */
	//public void setDbObject(DBObject dbObject) {
//		this.dbObject = dbObject;
//	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		if( firstName != null)
			return firstName;
		return "";
	}
	/**
	 * @return Returns the gender.
	 */
	public String getGender() {
		if(gender !=null)
			return gender.toUpperCase();
		return "";
	}
	public String getHealth() {
		return HealthTypes.getType((int)healthId).description();
	}
	/**
	 * @return Returns the healthId.
	 */
	public long getHealthId() {
		return healthId;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		if( lastName!= null)
			return lastName;
		return "";
	}
	/**
	 * @return Returns the lifeExpectancy.
	 */
	public int getLifeExpectancy() {
		return lifeExpectancy;
	}
	/**
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		if( middleName != null)
			return middleName;
		return "";
	}
	/**
	 * @return Returns the occupation.
	 */
	public String getOccupation() {
		if(occupation != null)
			return occupation;
		return "";
	}
	/**
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}
	/**
	 * @return Returns the uuid.
	 */
	public String getUuid() {
		return uuid;
	}
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable("PERSON");
		account = null;
		String sql = "select * from PERSON where PERSON_ID='"+ id +"'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			setFirstName((String)(account.get("FIRST_NAME")));
			setLastName((String)(account.get("LAST_NAME")));
			setMiddleName((String)(account.get("MIDDLE_NAME")));
			Date d = (Date)account.get("BIRTH_DATE");
			if( d != null) {
			    SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
			    setBirthDate(df.format(d));
			} else {
			    setBirthDate("00/00/0000");
			}
			setCity((String)(account.get("BIRTH_CITY")));
			setState((String)(account.get("BIRTH_STATE")));
			setGender((String)(account.get("GENDER")));
			setOccupation((String)(account.get("OCCUPATION")));
			this.setCitizenship((String)(account.get("CITIZENSHIP_CODE")));
			if(account.get("HEALTH_ID") != null)
			{
				this.setHealthId(((Number)(account.get("HEALTH_ID"))).longValue());
			}
			this.setSsn((String)(account.get("SSAN")));
			this.setLifeExpectancy(account.get("LIFE_EXP") == null ? 0 : ((Number)(account.get("LIFE_EXP"))).intValue());
			
		}
	}
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("PERSON");
		dbObject.clearFields();

		dbAddField("FIRST_NAME", getFirstName(), true);
		dbAddField("MIDDLE_NAME", getMiddleName(), true);
		dbAddField("LAST_NAME", getLastName(), true);
		dbAddField("GENDER", getGender(), true);
		dbAddField("BIRTH_CITY", getCity(), true);
		dbAddField("BIRTH_STATE", getState(), true);
		dbAddField("SSAN", getSsn(), true);
		dbAddField("OCCUPATION", getOccupation(), true);

		if(getCitizenship() == null)
		{
			this.setCitizenship("us");
		}
		dbAddField("CITIZENSHIP_CODE", getCitizenship(), true);
		if(getHealthId() != 0)
		{
			dbAddField("HEALTH_ID", "" + getHealthId(), true);
		}
		
		if(getLifeExpectancy() != 0)
		{
			dbAddField("LIFE_EXP", "" + getLifeExpectancy(), true);
		}

		if( birthDate != null) {
			SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
			Date nDate = dateformat.parse(birthDate, new ParsePosition(0));
			dbObject.addField("BIRTH_DATE", dbObject.dbDate(nDate));
		} else {
			Date nDate = new Date();
			dbObject.addField("BIRTH_DATE", dbObject.dbDate(nDate));
		}
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select PERSON_ID from PERSON where UUID='" + uuid + "'");
		Object o = ret.get("PERSON_ID");
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}
	/**
	 * @param birthDate The birthDate to set.
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @param citizenship The citizenship to set.
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @param gender The gender to set.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @param healthId The healthId to set.
	 */
	public void setHealthId(long healthId) {
		this.healthId = healthId;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @param lifeExpectancy The lifeExpectancy to set.
	 */
	public void setLifeExpectancy(int lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}
	
	/**
	 * @param middleName The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	/**
	 * @param occupation The occupation to set.
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable("PERSON");
		dbObject.clearFields();

		dbAddField("FIRST_NAME", getFirstName());
		dbAddField("MIDDLE_NAME", getMiddleName());
		dbAddField("LAST_NAME", getLastName());
		dbAddField("GENDER", getGender());
		dbAddField("BIRTH_CITY", getCity());
		dbAddField("BIRTH_STATE", getState());
		dbAddField("SSAN", getSsn());
		dbAddField("OCCUPATION", getOccupation());
		dbAddField("CITIZENSHIP_CODE", getCitizenship());
		dbAddField("HEALTH_ID", "" + getHealthId());
		if(getLifeExpectancy() != 0)
		{
			dbAddField("LIFE_EXP", "" + getLifeExpectancy());
		}
		else
		{
			dbAddField("LIFE_EXP", "NULL" );
		}

		
		if( birthDate != null) {
			SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
			Date nDate = dateformat.parse(birthDate, new ParsePosition(0));
			dbObject.addField("BIRTH_DATE", dbObject.dbDate(nDate));
		}

		dbObject.setWhere("PERSON_ID='"+ id +"'");
		dbObject.update();
		dbObject.stop();
	}
}
