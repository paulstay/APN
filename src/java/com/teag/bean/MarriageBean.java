/*
 * Created on Nov 5, 2004
 *
 */
package com.teag.bean;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

/**
 * @author Paul Stay
 *
 */
public class MarriageBean extends SqlBean implements Serializable {
	/**
	 * 
	 */
	
	public static final String ID = "MARRIAGE_ID";
	public static final String OWNER_ID = "OWNER_ID";
	public static final String PRIMARY_ID = "PRIMARY_ID";
	public static final String SPOUSE_ID = "SPOUSE_ID";
	public static final String tableName = "MARRIAGE";
	
	
	private static final long serialVersionUID = -4939409068698542440L;
	private String date;
	private String city;
	private String state;
	private String status;
	private HashMap<String,Object> account;
	private String husband;
	private String wife;
	private String uuid;
	private long spouseId;
	private long personId;
	private PersonBean spouse;
	private PersonBean person;
	//private String name;
	long id;
	
	/**
	 * 
	 */
	public MarriageBean() {
		super();
	}
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	public void empty()
	{
		setDate("");
		setCity("");
		setState("");
		setStatus("");
		setSpouseId(0);
	}
	public boolean find(long primaryId, long spouseId)
	{
		HashMap<String,Object> account;
		String sqlStatement = "select MARRIAGE_ID from MARRIAGE where PRIMARY_ID='" + primaryId + 
			"' and SPOUSE_ID='" + spouseId + "'";
		
		dbObject.start();
		account = dbObject.execute(sqlStatement);
		if(null != account)
		{
			setId(((Number)(account.get("MARRIAGE_ID"))).longValue());
			dbObject.stop();
			return(true);
		}
		dbObject.stop();
		return(false);
	}
	
	public ArrayList<MarriageBean> getBeans(String whereClause) {
		ArrayList<MarriageBean> bList = new ArrayList<MarriageBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			MarriageBean nb = new MarriageBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @return Returns the husband.
	 */
	public String getHusband() {
		return husband;
	}
	
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	public PersonBean getPerson() {
		return person;
	}
	public long getPersonId() {
		return personId;
	}
	public String getPersonName()
	{
		String name = person.getFirstName() + " " + person.getLastName();
		return(name);
	}
	/**
	 * @return Returns the spouse.
	 */
	public PersonBean getSpouse() {
		return spouse;
	}
	/**
	 * @return Returns the spouseId.
	 */
	public long getSpouseId() {
		return spouseId;
	}
	public String getSpouseName()
	{
		String name = spouse.getFirstName() + " " + spouse.getLastName();
		return(name);
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return Returns the wife.
	 */
	public String getWife() {
		return wife;
	}

	@Override
	public void initialize()
	{
		dbObject.start();
		dbObject.setTable("MARRIAGE");
		account = null;
		
		String sql = "select * from MARRIAGE where MARRIAGE_ID='"+ id +"'";

		account = dbObject.execute(sql);

		dbObject.stop();

		if( account != null) {
			SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
			setDate((df.format(account.get("MARRIAGE_DATE"))));
			setCity((String)(account.get("MARRIAGE_CITY")));
			setState((String)(account.get("MARRIAGE_STATE")));
			setStatus((String)(account.get("STATUS")));
			setSpouseId(((Number)(account.get("SPOUSE_ID"))).longValue());
			setPersonId(((Number)(account.get("PRIMARY_ID"))).longValue());
			spouse = new PersonBean();
			spouse.setDbObject();
			spouse.setId(getSpouseId());
			spouse.initialize();
			person = new PersonBean();
			person.setDbObject();
			person.setId(getPersonId());
			person.initialize();
		}
	}
	
	@Override
	public void insert()
	{
		dbObject.start();
		dbObject.setTable("MARRIAGE");
		dbObject.clearFields();
		dbObject.addField("MARRIAGE_CITY", DBObject.dbString(getCity())); 
		dbObject.addField("MARRIAGE_STATE", DBObject.dbString(getState()));
		SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
		Date nDate = dateformat.parse(date, new ParsePosition(0));
		dbObject.addField("MARRIAGE_DATE", dbObject.dbDate( nDate));
		dbObject.addField("STATUS", DBObject.dbString(status));
		dbObject.addField("SPOUSE_ID", DBObject.dbString(""+getSpouseId()));
		dbObject.addField("PRIMARY_ID",DBObject.dbString("" + getPersonId()));
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select MARRIAGE_ID from MARRIAGE where UUID='" + uuid + "'");
		Object o = ret.get("MARRIAGE_ID");
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * @param husband The husband to set.
	 */
	public void setHusband(String husband) {
		this.husband = husband;
	}
	
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	
	/**
	 * @param spouseId The spouseId to set.
	 */
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	
	/**
	 * @param spouseId The spouseId to set.
	 */
	public void setSpouseId(long spouseId) {
		this.spouseId = spouseId;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param wife The wife to set.
	 */
	public void setWife(String wife) {
		this.wife = wife;
	}
	@Override
	public void update()
	{
		dbObject.start();
		dbObject.setTable("MARRIAGE");
		dbObject.clearFields();
		dbObject.addField("MARRIAGE_CITY", DBObject.dbString(getCity())); 
		dbObject.addField("MARRIAGE_STATE", DBObject.dbString(getState()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date nDate = dateFormat.parse(date, new ParsePosition(0));
		dbObject.addField("MARRIAGE_DATE", dbObject.dbDate( nDate));
		dbObject.addField("STATUS", DBObject.dbString(status));
		dbObject.setWhere("MARRIAGE_ID='"+ id + "'");
		dbObject.update();
		dbObject.stop();
	}
}
