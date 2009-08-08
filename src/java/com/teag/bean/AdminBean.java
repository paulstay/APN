package com.teag.bean;

/** 
 * <p>Title: PersonBean
 * <p>Description: Store information for a person defined in the client code. 
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: APN</p>
 * @author Paul Stay
 * @version 1.0
 */
import com.estate.db.NBConnection;
import java.sql.PreparedStatement;
import java.io.Serializable;
import java.util.HashMap;

public class AdminBean extends SqlBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7785256927297832646L;
    private String userName;
    private String password;
    private String ePass;
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
    private String status;
    long id;
    long orgId;
    boolean hash = false;
    HashMap<String, Object> account;
    boolean authorized;

    @Override
    public void insert() {
        this.setUserName(this.getFirstName().toLowerCase() + this.getLastName().toLowerCase());
        this.setPassword("letmein");
        dbObject.start();
        dbObject.setTable("PLANNER");
        dbObject.clearFields();
        dbAddField("PASSWORD", this.getPassword(), true);
        dbAddField("EPASS", getEPass());
        dbAddField("USER_NAME", this.getUserName(), true);
        dbAddField("MAX_CLIENTS", "25", true);
        dbAddField("FIRST_NAME", this.getFirstName(), true);
        dbAddField("MIDDLE_NAME", this.getMiddleName(), true);
        dbAddField("LAST_NAME", this.getLastName(), true);
        dbAddField("SUFFIX", this.getSuffix(), true);
        dbAddField("ADDRESS1", this.getAddress1(), true);
        dbAddField("ADDRESS2", this.getAddress2(), true);
        dbAddField("CITY", this.getCity(), true);
        dbAddField("STATE_CODE", this.getState(), true);
        dbAddField("ZIP", this.getZipcode(), true);
        dbAddField("PHONE", this.getPhone(), true);
        dbAddField("FAX", this.getFax(), true);
        dbAddField("EMAIL", this.getEmail(), true);
        dbAddField("PRIVILAGES", "VE", true);
        dbAddField("ORGANIZATION_ID", "" + this.getOrgId(), true);
        dbAddField("STATUS", getStatus());
        dbObject.insert();
        uuid = dbObject.getUUID();
        HashMap<String, Object> ret = dbObject.execute("select PLANNER_ID from PLANNER where UUID='" + uuid + "'");
        Object o = ret.get("PLANNER_ID");
        id = Integer.parseInt(o.toString());
        dbObject.stop();

    }

    @Override
    public void update() {
        dbObject.start();
        dbObject.setTable("PLANNER");
        dbObject.clearFields();
        dbAddField("PASSWORD", this.getPassword(), true);
        dbAddField("EPASS", this.getEPass(), true);
        dbAddField("PRIVILAGES",this.getPrivilages());
        dbAddField("USER_NAME", this.getUserName(), true);
        dbAddField("MAX_CLIENTS", "25", true);
        dbAddField("FIRST_NAME", this.getFirstName(), true);
        dbAddField("MIDDLE_NAME", this.getMiddleName(), true);
        dbAddField("LAST_NAME", this.getLastName(), true);
        dbAddField("SUFFIX", this.getSuffix(), true);
        dbAddField("ADDRESS1", this.getAddress1(), true);
        dbAddField("ADDRESS2", this.getAddress2(), true);
        dbAddField("CITY", this.getCity(), true);
        dbAddField("STATE_CODE", this.getState(), true);
        dbAddField("ZIP", this.getZipcode(), true);
        dbAddField("PHONE", this.getPhone(), true);
        dbAddField("FAX", this.getFax(), true);
        dbAddField("EMAIL", this.getEmail(), true);
        dbAddField("PRIVILAGES", "VE", true);
        dbAddField("ORGANIZATION_ID", "" + this.getOrgId(), true);
        dbAddField("STATUS", getStatus());
        dbObject.setWhere("PLANNER_ID=" + id);
        dbObject.update();
        dbObject.stop();
    }

    public void initialize() {
        authorized = false;
        dbObject.start();
        dbObject.setTable("PLANNER");
        dbObject.clearFields();
        String passCheck = null;
        if (!hash) {
            try {
                passCheck = com.estate.security.PasswordService.getInstance().encrypt(password);
                System.out.printf("Login %10s %s\n", userName, passCheck);
            } catch (Exception e) {
            }
        } else {
            passCheck = password;
        }
        account = dbObject.execute("select * from PLANNER where USER_NAME='" + userName.trim() +
                "' and EPASS='" + passCheck + "'");

        if (account != null) {
            authorized = true;
            setFirstName((String) (account.get("FIRST_NAME")));
            setLastName((String) (account.get("LAST_NAME")));
            setMiddleName((String) (account.get("MIDDLE_NAME")));
            setEPass((String) (account.get("EPASS")));
            setAddress1((String) account.get("ADDRESS1"));
            setAddress2((String) account.get("ADDRESS2"));
            setCity((String) account.get("CITY"));
            setState((String) account.get("STATE_CODE"));
            setZipcode((String) account.get("ZIP"));
            setPhone((String) account.get("PHONE"));
            setFax((String) account.get("FAX"));
            setEmail((String) account.get("EMAIL"));
            setRevReq((String) account.get("REVIEW_REQ"));
            setStatus((String) account.get("STATUS"));
            privilages = (String) account.get("PRIVILAGES");
            Object o = account.get("PLANNER_ID");
            id = Integer.parseInt(o.toString());
            orgId = ((Number) (account.get("ORGANIZATION_ID"))).longValue();
            org = new OrgBean();
            org.setId(orgId);
            org.initialize();
        }
        dbObject.stop();
    }

    public void voidUser(String userName){
        dbObject.start();
        dbObject.setTable("PLANNER");
        String sql = "select * from PLANNER where USER_NAME='" + userName + "'";
        account = dbObject.execute(sql);
        if (account != null) {
                dbObject.setTable("PLANNER");
                dbObject.clearFields();
                dbAddField("STATUS", "N");
                Object o = account.get("PLANNER_ID");
                id = Integer.parseInt(o.toString());
                dbObject.setWhere("PLANNER_ID=" + id);
                dbObject.update();
                System.out.println(userName + ": user status set to N");
        } else {
            System.out.println(userName + ": user status not changed");
        }
        dbObject.stop();
    }

    public void delete() {
        dbObject.start();
        String whereClause = "PLANNER_ID='" + getId() + "'";
        dbObject.delete("PLANNER", whereClause);
        dbObject.stop();
    }

    public boolean canCreateBranch() {
        boolean admin = false;

        if (privilages.indexOf("B") >= 0) {
            admin = true;
        }
        return admin;
    }

    public boolean canCreateClient() {
        boolean admin = false;

        if (privilages != null && privilages.indexOf("E") >= 0) {
            admin = true;
        }
        return admin;
    }

    public boolean canCreateCorp() {
        boolean admin = false;

        if (privilages.indexOf("C") >= 0) {
            admin = true;
        }
        return admin;
    }

    public boolean canCreateDivision() {
        boolean admin = false;

        if (privilages.indexOf("D") >= 0) {
            admin = true;
        }
        return admin;
    }

    public boolean canCreatePlanner() {
        boolean admin = false;

        if (privilages.indexOf("P") >= 0) {
            admin = true;
        }
        return admin;
    }

    public boolean canCreateRegion() {
        boolean admin = false;

        if (privilages.indexOf("R") >= 0) {
            admin = true;
        }
        return admin;
    }

    /**
     * @return Returns the account.
     */
    public HashMap<String, Object> getAccount() {
        return account;
    }

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
        return address2;
    }

    public boolean getAuthorized() {
        return authorized;
    }

    /**
     * @return Returns the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Returns the fax.
     */
    public String getFax() {
        return fax;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return Returns the middleName.
     */
    public String getMiddleName() {
        return middleName;
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
        return phone;
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
        return state;
    }

    /**
     * @return Returns the suffix.
     */
    public String getSuffix() {
        return suffix;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * @return Returns the zipcode.
     */
    public String getZipcode() {
        return zipcode;
    }

    public boolean isAdmin() {
        boolean admin = false;

        if (privilages.indexOf("A") >= 0) {
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
    public void setAccount(HashMap<String, Object> account) {
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

    public void setAuthorized(boolean auth) {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setPassword(String password) {
        String passCheck = null;
        String pass = com.teag.util.StringUtil.removeChar(password, '\'');
        try {
                passCheck = com.estate.security.PasswordService.getInstance().encrypt(pass);
                System.out.printf("Login %10s %s\n", userName, passCheck);
            } catch (Exception e) {
            }
        this.password = pass;
        this.ePass = passCheck;
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

    public String getEPass() {
        return this.ePass;
    }

    public void setEPass(String ePass) {
        this.ePass = ePass;
    }

    public void setHash(boolean hash) {
        this.hash = hash;
    }
}
