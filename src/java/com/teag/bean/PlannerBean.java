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
import java.util.ArrayList;
import java.util.HashMap;


public class PlannerBean extends SqlBean implements Serializable {
	/**
	 * 	<Table name='PLANNER' comment='Financial Planner' >
		<Field name='PLANNER_ID' type='long' size='19' null='false' autoincrement='true' valid='true' />
		<Field name='PASSWORD' type='string' size='14' null='false' valid='true' />
		<Field name='USER_NAME' type='string' size='19' null='false' valid='true' />
		<Field name='ORGANIZATION_ID' type='long' size='19' null='false'/>
		<Field name='MAX_CLIENTS' type='long' size='19' null='false'/>
		<Group name='Name' />
		<Group name='Address' />
		<Field name='REVIEW_REQ' type='string' size='4' null='true' />
		<Field name='PRIVILAGES' type='string' size='9' null='false' />
		<Field name='SUPERVISOR_ID' type='long' size='19' null='false' />
		<ForeignKey field='ORGANIZATION_ID' reftable='ORGANIZATIONS' reffield='ORGANIZATION_ID'/>		
		<PrimaryKey field='PLANNER_ID' />
		</Table>
	 */
	private static final long serialVersionUID = -7312844274938743386L;
	
	public final static String ID = "PLANNER_ID";
	public final static String PASSWORD = "PASSWORD";
    public final static String EPASS = "EPASS";
	public final static String USER_NAME = "USER_NAME";
	public final static String ORGANIZATION_ID = "ORGANIZATION_ID";
	public final static String MAX_CLIENTS = "MAX_CLIENTS";
	public final static String FIRST_NAME = "FIRST_NAME";
	public final static String MIDDLE_NAME = "MIDDLE_NAME";
	public final static String LAST_NAME = "LAST_NAME";
	public final static String SUFFIX = "SUFFIX";
	public final static String ADDRESS1 = "ADDRESS1";
	public final static String ADDRESS2 = "ADDRESS2";
	public final static String CITY = "CITY";
	public final static String STATE = "STATE_CODE";
	public final static String ZIP = "ZIP";
	public final static String LONGITUDE = "LONGITUDE";
	public final static String LATITUDE = "LATITUDE";
	public final static String PHONE = "PHONE";
	public final static String FAX = "FAX";
	public final static String EMAIL = "EMAIL";
	public final static String REVIEW_REQ = "REVIEW_REQ";
	public final static String PRIVILAGES = "PRIVILAGES";
	public final static String SUPERVISOR_ID = "SUPERVISOR_ID";
	public final static String STATUS = "STATUS";
	
	private String userName;
	private String password;
    private String encryptPass;
	private String firstName;
	private String middleName;
	
	private String lastName;
	private String suffix;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private String phone;
	private String fax;
	private String email;
	private String revReq;
	private String uuid;
	private String privilages;
	private OrgBean org;
	private long maxClients;
	private String status;

	long id;
	
	long orgId;
	HashMap<String,Object> account;
	boolean authorized;
	/**
	 * @return Returns the maxClients.
	 */
	public PlannerBean()
	{
		maxClients = 25;
		privilages = "VE";
	}
	
	public boolean canCreateBranch()
	{
		boolean admin = false;
		
		if(privilages.indexOf("B") >= 0)
		{
			admin = true;
		}
		return admin;
	}
	public boolean canCreateClient()
	{
		boolean admin = false;
		
		if(privilages.indexOf("E") >= 0)
		{
			admin = true;
		}
		return admin;
	}
	
	public boolean canCreateCorp()
	{
		boolean admin = false;
		
		if(privilages.indexOf("C") >= 0)
		{
			admin = true;
		}
		return admin;
	}
	public boolean canCreateDivision()
	{
		boolean admin = false;
		
		if(privilages.indexOf("D") >= 0)
		{
			admin = true;
		}
		return admin;
	}
	public boolean canCreatePlanner()
	{
		boolean admin = false;
		
		if(privilages.indexOf("P") >= 0)
		{
			admin = true;
		}
		return admin;
	}
	public boolean canCreateRegion()
	{
		boolean admin = false;
		
		if(privilages.indexOf("R") >= 0)
		{
			admin = true;
		}
		return admin;
	}
	/**
	 * @return Returns the account.
	 */
	public HashMap<String,Object> getAccount() {
		return account;
	}
	/**
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		return address1 == null ? "" : address1;
	}
	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		return address2 == null ? "" : address2;
	}
	public boolean getAuthorized() {
		return authorized;
	}
	public ArrayList<PlannerBean> getBeans(String whereClause) {
		ArrayList<PlannerBean> bList = new ArrayList<PlannerBean>();
		String sql = "select " + ID + " from PLANNER where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			PlannerBean nb = new PlannerBean();
			nb.setDbObject();
			nb.setId(id);
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		
		for(PlannerBean c : bList) {
			c.initialize();
		}
		
		return bList;
	}
	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city == null ? "" : city;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email == null ? "" : email;
	}
	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		return fax == null ? "" : fax;
	}
	public String getFirstName(){
		return firstName == null ? "" : firstName;
	}
	public long getId() {
		return id;
	}
	public String getLastName(){
		return lastName == null ? "" : lastName;
	}
	public long getMaxClients() {
		return maxClients;
	}
	/**
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		return middleName == null ? "" : middleName;
	}
	/**
	 * @return Returns the org.
	 */
	public OrgBean getOrg() {
		return org;
	}
	/**
	 * @return Returns the orgId.
	 */
	public long getOrgId() {
		return orgId;
	}
	public String getPassword() {
		return password;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone == null ? "" : phone;
	}
	/**
	 * @return Returns the fax.
	 */
	/**
	 * @return Returns the email.
	 */
	public String getRevReq() {
		return revReq;
	}
	
		
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state == null ? "" : state;
	}
	
	/**
	 * @return Returns the suffix.
	 */
	public String getSuffix() {
		return suffix == null ? "" : suffix;
	}
	
	public String getUserName(){
		return userName;
	}
	
	/**
	 * @return Returns the zipcode.
	 */
	public String getZipcode() {
		return zipcode == null ? "" : zipcode;
	}

	@Override
	public void initialize() {
		authorized = false;
		dbObject.start();
		account = dbObject.execute("select * from PLANNER where PLANNER_ID='" + id + "'");
		dbObject.stop();
		if( account != null) {
			authorized = true;
			setFirstName((String)(account.get(FIRST_NAME)));
			setLastName((String)(account.get(LAST_NAME)));
			setMiddleName((String)(account.get(MIDDLE_NAME)));
			setSuffix((String) (account.get(SUFFIX)));
			setAddress1((String)account.get(ADDRESS1));
			setAddress2((String)account.get(ADDRESS2));
			setCity((String)account.get(CITY));
			setState((String)account.get(STATE));
			setZipcode((String)account.get(ZIP));
			setPhone((String)account.get(PHONE));
			setFax((String)account.get(FAX));
			setEmail((String)account.get(EMAIL));
			setRevReq((String)account.get(REVIEW_REQ));
			privilages = (String)account.get(PRIVILAGES);
			this.setMaxClients(((Number)(account.get(MAX_CLIENTS))).longValue());
			this.setPassword((String)account.get(PASSWORD));
            this.setEPass((String)account.get(EPASS));
			this.setUserName((String)account.get(USER_NAME));
			this.setStatus((String) account.get(STATUS));
			Object o = account.get("PLANNER_ID");
			id = Integer.parseInt(o.toString());
			orgId = ((Number)(account.get("ORGANIZATION_ID"))).longValue();
			org = new OrgBean();
			org.setDbObject();
			org.setId(orgId);
			org.initialize();
		}
	}
	
	@Override
	public void insert() 
	{
		//this.setUserName( this.getFirstName().toLowerCase() + this.getLastName().toLowerCase());
		//this.setPassword("letmein");
		dbObject.start();
		dbObject.setTable("PLANNER");
		dbObject.clearFields();
		dbAddField(PASSWORD,  this.getPassword(), true );
        dbAddField(EPASS, this.getEncryptPass(), true);
		dbAddField(USER_NAME,  this.getUserName(), true );
		dbAddField(MAX_CLIENTS,  "" + this.getMaxClients(), true );
		dbAddField(FIRST_NAME,  this.getFirstName(), true );
		dbAddField(MIDDLE_NAME,  this.getMiddleName(), true );
		dbAddField(LAST_NAME,  this.getLastName(), true );
		dbAddField(SUFFIX,  this.getSuffix(), true );
		dbAddField(ADDRESS1,  this.getAddress1(), true );
		dbAddField(ADDRESS2,  this.getAddress2(), true );
		dbAddField(CITY,  this.getCity(), true );
		dbAddField(STATE,  this.getState(), true );
		dbAddField(ZIP,  this.getZipcode(), true );
		dbAddField(PHONE,  this.getPhone(), true );
		dbAddField(FAX,  this.getFax(), true );
		dbAddField(EMAIL,  this.getEmail(), true );
		dbAddField(PRIVILAGES,  this.privilages, true );
		dbAddField(ORGANIZATION_ID,  "" + this.getOrgId(), true );
		dbAddField(STATUS, getStatus(), true);
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from PLANNER where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		id = Integer.parseInt(o.toString());
		dbObject.stop();
		
	}
	public boolean isAdmin()
	{
		boolean admin = false;
		
		if(privilages.indexOf("A") >= 0)
		{
			admin = true;
		}
		return admin;
	}
	
	public boolean isAuthorized() {
		return authorized;
	}
	/**
	 * @param account The account to set.
	 */
	public void setAccount(HashMap<String,Object> account) {
		this.account = account;
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
	
	public void setAuthorized(boolean auth){
		this.authorized = auth;
	}
	
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @param fax The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setId(long id ) {
		this.id = id;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	/**
	 * @param maxClients The maxClients to set.
	 */
	public void setMaxClients(long maxClients) {
		this.maxClients = maxClients;
	}
	
	/**
	 * @param middleName The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	/**
	 * @param org The org to set.
	 */
	public void setOrg(OrgBean org) {
		this.org = org;
	}
	
	/**
	 * @param orgId The orgId to set.
	 */
	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}
	
	public void setPassword( String password) {
		String pass;
		pass = com.teag.util.StringUtil.removeChar(password, '\'');
		this.password = pass;
	}
	
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @param email The email to set.
	 */
	public void setRevReq(String rev) {
		this.revReq = rev;
	}	
	
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	
	/**
	 * @param suffix The suffix to set.
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @param zipcode The zipcode to set.
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@Override
	public void update()
	{
		dbObject.start();
		dbObject.setTable("PLANNER");
		dbObject.clearFields();
		dbAddField(PASSWORD,  this.getPassword(), true );
        dbAddField(EPASS, this.getEncryptPass(), true);
		dbAddField(USER_NAME,  this.getUserName(), true );
		dbAddField(MAX_CLIENTS,  "" + this.getMaxClients(), true );
		dbAddField(FIRST_NAME,  this.getFirstName(), true );
		dbAddField(MIDDLE_NAME,  this.getMiddleName(), true );
		dbAddField(LAST_NAME,  this.getLastName(), true );
		dbAddField(SUFFIX,  this.getSuffix(), true );
		dbAddField(ADDRESS1,  this.getAddress1(), true );
		dbAddField(ADDRESS2,  this.getAddress2(), true );
		dbAddField(CITY,  this.getCity(), true );
		dbAddField(STATE,  this.getState(), true );
		dbAddField(ZIP,  this.getZipcode(), true );
		dbAddField(PHONE,  this.getPhone(), true );
		dbAddField(FAX,  this.getFax(), true );
		dbAddField(EMAIL,  this.getEmail(), true );
		dbAddField(PRIVILAGES,  privilages, true );
		dbAddField(STATUS, getStatus(), true);
		dbAddField(ORGANIZATION_ID,  "" + this.getOrgId(), true );
		dbObject.setWhere( ID + "='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}

	public String getPrivilages() {
		return privilages;
	}

	public void setPrivilages(String privilages) {
		this.privilages = privilages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getEncryptPass() {
        return encryptPass;
    }

    public void setEPass(String ePass) {
        this.encryptPass = ePass;
    }
}
