/*
 * Created on Feb 24, 2005
 *
 */
package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Paul Stay
 *
 */
public class OrgBean extends SqlBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3058667973282988010L;
	
	public final static String ID = "ORGANIZATION_ID";
	private long parentId;
	private String orgName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String fax;
	private long id;
	private long orgLevel;
	private String uuid;
	private long subOrgIDs[];
	private long subOrgCount;
	private long plannerIDs[];
	private long plannerCount;
	
	String tableName = "ORGANIZATIONS";
	
	
	/**
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		return address2 == null ? "" : address2;
	}
	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return Returns the orgLevel.
	 */
	public long getOrgLevel() {
		return orgLevel;
	}
	/**
	 * @return Returns the orgName.
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @return Returns the parentId.
	 */
	public long getParentId() {
		return parentId;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @return Returns the plannerCount.
	 */
	public long getPlannerCount() {
		return plannerCount;
	}
	/**
	 * @return Returns the plannerIDs.
	 */
	public long getPlannerIDs(int idx ) {
		return plannerIDs[idx];
	}
	private void getPlanners()
	{
		dbObject.start();
		// Get the count 
		HashMap<String,Object> planners = dbObject.execute("select count(*) as COUNT from PLANNER where ORGANIZATION_ID='" + id + "'");
		dbObject.stop();
		
		plannerCount = ((Number)(planners.get("COUNT"))).longValue();
		
		plannerIDs = new long[(int)plannerCount];
		dbObject.start();
		planners = dbObject.execute("select PLANNER_ID from PLANNER where ORGANIZATION_ID='" + id + "'");
		
		
		for(int i = 0;  i < plannerCount; i ++)
		{
			plannerIDs[i] = ((Number)(planners.get("PLANNER_ID"))).longValue();
			planners = dbObject.next();
		}
		
		dbObject.stop();
		
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}
	/**
	 * @return Returns the subOrgCount.
	 */
	public long getSubOrgCount() {
		return subOrgCount;
	}
	/**
	 * @return Returns the subOrgIDs.
	 */
	public long getSubOrgIDs(int idx) {
		return subOrgIDs[idx];
	}
	
	public ArrayList<OrgBean> getOrgs(String whereClause) {
		ArrayList<OrgBean> aList = new ArrayList<OrgBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			OrgBean nb = new OrgBean();
			nb.setId(id);
			nb.setDbObject();
			nb.initialize();
			aList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		
		return aList;
	}
	
	public ArrayList<OrgBean> getSubOrgs() {
		ArrayList<OrgBean> aList = new ArrayList<OrgBean>();
		String  sql = "select * from organizations where PARENT_ORGANIZATION_ID='" + getId() + "'";
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while(ret != null) {
			Object o = ret.get(ID);
			long nid = Long.parseLong(o.toString());
			OrgBean ob = new OrgBean();
			ob.setId(nid);
			ob.initialize();
			aList.add(ob);
			ret = dbObject.next();
		}
		dbObject.stop();
		
		return aList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() {
		dbObject.start();
		HashMap<String,Object> account = dbObject.execute("select * from ORGANIZATIONS where ORGANIZATION_ID='" + id + "'");
		dbObject.stop();
		if( account != null) {
			setOrgName((String)(account.get("ORGANIZATION_NAME")));
			setParentId( ((Number)(account.get("PARENT_ORGANIZATION_ID"))).longValue());
			setAddress1((String)account.get("ADDRESS1"));
			setAddress2((String)account.get("ADDRESS2"));
			setCity((String)account.get("CITY"));
			setState((String)account.get("STATE_CODE"));
			setZip((String)account.get("ZIP"));
			setPhone((String)account.get("PHONE"));
			setFax((String)account.get("FAX"));
			setOrgLevel( ((Number)(account.get("ORGANIZATION_LEVEL"))).longValue());
			getSubOrgs();
			getPlanners();
		}
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable("ORGANIZATIONS");
		dbObject.clearFields();
		dbAddField("PARENT_ORGANIZATION_ID", "" + getParentId(), true);
		dbAddField("ORGANIZATION_LEVEL", "" + getOrgLevel(), true);
		dbAddField("ORGANIZATION_NAME", getOrgName(), true);
		dbAddField("ADDRESS1", getAddress1(), true);
		dbAddField("ADDRESS2", getAddress2(), true);
		dbAddField("CITY", getCity(), true);
		dbAddField("STATE_CODE", getState(), true);
		dbAddField("ZIP", getZip(), true);
		dbAddField("PHONE", getPhone(), true);
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select ORGANIZATION_ID from ORGANIZATIONS where UUID='" + uuid + "'");
		Object o = ret.get("ORGANIZATION_ID");
		id = Integer.parseInt(o.toString());
		dbObject.stop();

	}
	
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable("ORGANIZATIONS");
		dbObject.clearFields();
		dbAddField("ORGANIZATION_LEVEL", "" + getOrgLevel(), true);
		dbAddField("ORGANIZATION_NAME", getOrgName(), true);
		dbAddField("ADDRESS1", getAddress1(), true);
		dbAddField("ADDRESS2", getAddress2(), true);
		dbAddField("CITY", getCity(), true);
		dbAddField("STATE_CODE", getState(), true);
		dbAddField("ZIP", getZip(), true);
		dbAddField("PHONE", getPhone(), true);
		dbObject.setWhere("ORGANIZATION_ID='"+ id +"'");
		dbObject.update();
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
	 * @param orgLevel The orgLevel to set.
	 */
	public void setOrgLevel(long orgLevel) {
		this.orgLevel = orgLevel;
	}
	
	/**
	 * @param orgName The orgName to set.
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * @param parentId The parentId to set.
	 */
	public void setParentId(long parentId) {
		this.parentId = parentId;
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
	 * @param zip The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/**
	 * @return Returns the zip.
	 */
	public String getZip() {
		return zip;
	}
	
}
