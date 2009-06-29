package com.teag.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author stay
 *
 */
public class LocationBean extends SqlBean implements Serializable {
	/**
	 * 
	 */
	
	public final static String ID = "LOCATION_ID";
	public final static String OWNER_ID = "OWNER_ID";
	
	private static final long serialVersionUID = 8841883426654151853L;
	private long id;
	private String type = "";	// P-Primary, S-Secondary, B-Business, V-Vacation
	private String name = "";
	private String address1 = "";
	private String address2 = "";
	private String city = "";
	private String state = "";
	private String zip = "";
	private String phone = "";
	private String fax = "";
	private long ownerId = 0;
	private String uuid = "";

	private HashMap<String,Object> account;
	
	String tableName = "LOCATION";
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
		if( address1 == null)
			return "";
		return address1;
	}
	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		if( address2 == null)	
			return "";
		return address2;
		
	}
	public ArrayList<LocationBean> getBeans(String whereClause) {
		ArrayList<LocationBean> bList = new ArrayList<LocationBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			LocationBean nb = new LocationBean();
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
		if( city == null)
			return "";
		return city;
	}
	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		if( fax == null)
			return "";
		return fax;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		if( name == null)
			return "";
		return name;
	}
	/**
	 * @return Returns the ownerID.
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		if( phone == null )
			return "";
		return phone;
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		if(state == null)
			return "";
		return state;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		if( type == null)
			return "";
		return type;
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
		if( zip == null)
			return "";
		return zip;
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() {
		dbObject.start();
		account = dbObject.execute("select * from LOCATION where LOCATION_ID=" + getId() + "");
		dbObject.stop();
		if( account != null ) {
			setType((String) account.get("LOCATION_TYPE"));
			setName((String)account.get("LOCATION_NAME"));
			setAddress1((String)account.get("ADDRESS1"));
			setAddress2((String)account.get("ADDRESS2"));
			setCity((String)account.get("CITY"));
			setState((String)account.get("STATE_CODE"));
			setZip((String)account.get("ZIP"));
			setPhone((String)account.get("PHONE"));
			setFax((String)account.get("FAX"));
			setUuid((String)account.get("UUID"));
			Object o = account.get("OWNER_ID");
			ownerId = Integer.parseInt( o.toString());
		}
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.clearFields();
		dbObject.setTable("LOCATION");

		dbAddField("LOCATION_NAME", getName(), true);
		dbAddField("LOCATION_TYPE", getType(), true);
		dbAddField("ADDRESS1", getAddress1(), true);
		dbAddField("ADDRESS2", getAddress2(), true);
		dbAddField("CITY", getCity(), true);
		dbAddField("STATE_CODE", getState(), true);
		dbAddField("ZIP", getZip(), true);
		dbAddField("PHONE", getPhone(), true);
		dbAddField("FAX", getFax(), true);
		dbAddField("OWNER_ID", "" + ownerId + "", true);
		dbObject.insert();

		String uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select LOCATION_ID from LOCATION where UUID='" + uuid + "'");
		Object o = ret.get("LOCATION_ID");
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}
	/**
	 * @param address1 The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @param address2 The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @param fax The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param ownerID The ownerID to set.
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param uuid The uuid to set.
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * @param zip The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.clearFields();
		dbObject.setTable("LOCATION");
		
		dbAddField("LOCATION_NAME", getName());
		dbAddField("LOCATION_TYPE", getType());
		dbAddField("ADDRESS1", getAddress1());
		dbAddField("ADDRESS2", getAddress2());
		dbAddField("CITY", getCity());
		dbAddField("STATE_CODE", getState());
		dbAddField("ZIP", getZip());
		dbAddField("PHONE", getPhone());
		dbAddField("FAX", getFax());
		dbAddField("OWNER_ID", "" + ownerId + "");

		dbObject.setWhere("LOCATION_ID=" + getId() + "");
		dbObject.update();
		dbObject.stop();
	}
}
