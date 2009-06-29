package com.teag.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.estate.constants.AdvisorTypes;
/**
 * @author Paul Stay 
 * Description - Provide Bean actions for Adviso Date Nov 11,
 * Copywrite 2008 TEAG
 *  
 */
public class AdvisorBean extends SqlBean implements Serializable {

	public final static String ID = "ADVISOR_ID";
	public final static String OWNER_ID = "OWNER_ID";
	/**
	 * 
	 */
	private static final long serialVersionUID = -7913448014592522049L;
	private long id;
	private long ownerID;
	private long typeId;
	private String type;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String title;
	private String firmName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String fax;
	private String email;
	private String assistant;
	
	private String uuid;
	private HashMap<String,Object> account;

	String tableName = "ADVISOR";
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}
	/**
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		if (address1 != null)
			return address1;
		return "";
	}

	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		if (address2 != null)
			return address2;
		return "";
	}

	public ArrayList<AdvisorBean> getBeans(String whereClause) {
		ArrayList<AdvisorBean> bList = new ArrayList<AdvisorBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			AdvisorBean nb = new AdvisorBean();
			nb.setId(id);
			nb.setDbObject();
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
		if (city != null)
			return city;
		return "";
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		if (email != null)
			return email;
		return "";
	}

	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		if (fax != null)
			return fax;
		return "";
	}

	/**
	 * @return Returns the firmName.
	 */
	public String getFirmName() {
		if (firmName != null)
			return firmName;
		return "";
	}

	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		if (firstName != null)
			return firstName;
		return "";
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
		if (lastName != null)
			return lastName;
		return "";
	}

	/**
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		if (middleName != null)
			return middleName;
		return "";
	}

	/**
	 * @return Returns the ownerID.
	 */
	public long getOwnerID() {
		return ownerID;
	}

	/**
	 * @return Returns the phone.
	 */
public String getPhone() {
		if( phone != null)
		return phone;
		return "";
	}

	/**
	 * @return Returns the state.
	 */
	public String getState() {
		if (state != null)
			return state;
		return "";
	}

	/**
	 * @return Returns the suffix.
	 */
	public String getSuffix() {
		if (suffix != null)
			return suffix;
		return "";
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		if (title != null)
			return title;
		return "";
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return Returns the typeId.
	 */
	public long getTypeId() {
		return typeId;
	}

	/**
	 * @return Returns the uuid.
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @return Returns the zip.
	 */
	public String getZip() {
		if (zip != null)
			return zip;
		return "";
	}

	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable("ADVISOR");
		account = null;
		String sql = "select * from ADVISOR where ADVISOR_ID=" + id + "";
		account = dbObject.execute(sql);
		dbObject.stop();
		if( account != null) {
			long aType = ((Number)(account.get("ADVISOR_TYPE_ID"))).longValue();
			this.setTypeId(aType);
			AdvisorTypes at = AdvisorTypes.getType((int)aType);
			setType(at.description());
			setFirstName((String)(account.get("FIRST_NAME")));
			setMiddleName((String)(account.get("MIDDLE_NAME")));
			setLastName((String)(account.get("LAST_NAME")));
			setSuffix((String)account.get("SUFFIX"));
			setTitle((String)account.get("TITLE"));
			setFirmName((String)account.get("FIRM_NAME"));
			setAddress1((String)account.get("ADDRESS1"));
			setAddress2((String)account.get("ADDRESS2"));
			setCity((String)account.get("CITY"));
			setState((String)account.get("STATE_CODE"));
			setZip((String)account.get("ZIP"));
			setPhone((String)account.get("PHONE"));
			setFax((String)account.get("FAX"));
			setEmail((String)account.get("EMAIL"));
			setAssistant((String)account.get("ASSISTANT"));
			Object o = account.get("OWNER_ID");
			ownerID = Integer.parseInt(o.toString());
		}
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("ADVISOR");
		dbObject.clearFields();
		dbAddField("ADVISOR_TYPE_ID", "" + getTypeId(), true);
		dbAddField("FIRST_NAME", getFirstName(), true);
		dbAddField("MIDDLE_NAME", getMiddleName(), true);
		dbAddField("LAST_NAME", getLastName(), true);
		dbAddField("SUFFIX", getSuffix(), true);
		dbAddField("TITLE", getTitle(), true);
		dbAddField("FIRM_NAME", getFirmName(), true);
		dbAddField("ADDRESS1", getAddress1(), true);
		dbAddField("ADDRESS2", getAddress2(), true);
		dbAddField("CITY", getCity(), true);
		dbAddField("STATE_CODE", getState(), true);
		dbAddField("ZIP", getZip(), true);
		dbAddField("PHONE", getPhone(), true);
		dbAddField("FAX", getFax(), true);
		dbAddField("EMAIL", getEmail(), true);
		dbAddField("ASSISTANT", getAssistant(), true);
		dbAddField("OWNER_ID", "" + ownerID + "", true);
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ADVISOR_ID from ADVISOR where UUID='" + uuid + "'");
		Object o = ret.get("ADVISOR_ID");
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	/**
	 * @param address1
	 *            The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @param address2
	 *            The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @param city
	 *            The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param fax
	 *            The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @param firmName
	 *            The firmName to set.
	 */
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	/**
	 * @param firstName
	 *            The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param lastName
	 *            The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param middleName
	 *            The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @param ownerID
	 *            The ownerID to set.
	 */
	public void setOwnerID(long ownerID) {
		this.ownerID = ownerID;
	}

	/**
	 * @param phone
	 *            The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param state
	 *            The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param suffix
	 *            The suffix to set.
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @param title
	 *            The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		for(AdvisorTypes a : AdvisorTypes.values()){
			if( a.description().equalsIgnoreCase(type)) {
				typeId = a.id();
			}
		}
			
		this.type = type;
	}
	
	/**
	 * @param typeId The typeId to set.
	 */
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	/**
	 * @param uuid
	 *            The uuid to set.
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @param zip
	 *            The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAssistant() {
		return assistant;
	}
	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}
	
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable("ADVISOR");
		dbObject.clearFields();
		dbAddField("ADVISOR_TYPE_ID", "" + getTypeId());
		dbAddField("FIRST_NAME", getFirstName());
		dbAddField("MIDDLE_NAME", getMiddleName());
		dbAddField("LAST_NAME", getLastName());
		dbAddField("SUFFIX", getSuffix());
		dbAddField("TITLE", getTitle());
		dbAddField("FIRM_NAME", getFirmName());
		dbAddField("ADDRESS1", getAddress1());
		dbAddField("ADDRESS2", getAddress2());
		dbAddField("CITY", getCity());
		dbAddField("STATE_CODE", getState());
		dbAddField("ZIP", getZip());
		dbAddField("PHONE", getPhone());
		dbAddField("FAX", getFax());
		dbAddField("EMAIL", getEmail());
		dbAddField("ASSISTANT", getAssistant());
		dbAddField("OWNER_ID", "" + ownerID + "");
		dbObject.setWhere( "ADVISOR_ID=" + id);
		dbObject.update();
		dbObject.stop();
	}

}
		