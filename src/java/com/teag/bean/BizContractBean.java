/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Paul Stay
 * Date October 2009
 */
public class BizContractBean extends AssetSqlBean {

    public final static String ID = "ID";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String VALUE = "VALUE";
    public final static String START_YEAR = "START_YEAR";
    public final static String END_YEAR = "END_YEAR";
    public final static String SALARY = "SALARY";
    public final static String OWNER_ID = "OWNER_ID";

    public final static String tableName = "BIZ_CONTRACT";

    private long id;
    private String uuid;
    private HashMap<String,Object> account;

    private String description;
    private double value;
    private int startYear;
    private int endYear;
    private double salary;
    private long ownerId;
    private String notes;


    public BizContractBean() {
        this.beanType = SqlBean.BIZCONT;
    }

    public ArrayList<BizContractBean> getBeans(String whereClause) {
        ArrayList<BizContractBean> bList = new ArrayList<BizContractBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			BizContractBean nb = new BizContractBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();

		return bList;
    }

    /**
     * delete the current object
     */
    public void delete() {
        String whereClause = ID + "='" + getId() + "'";
        dbObject.start();
        dbObject.delete(tableName, whereClause);
        dbObject.stop();
    }

    /**
     * Insert the current bean object into the database
     *
     */
    public void insert() {

        dbObject.start();
        dbObject.setTable(tableName);
        dbObject.clearFields();
        dbAddField(OWNER_ID, this.getOwnerId());
        dbAddField(DESCRIPTION, getDescription());
        dbAddField(VALUE, getValue());
        dbAddField(START_YEAR, getStartYear());
        dbAddField(END_YEAR, getEndYear());
        dbAddField(SALARY, getSalary());

        dbObject.insert();
        uuid = dbObject.getUUID();
        HashMap<String,Object> ret = dbObject.execute("select ID from " + tableName + " where UUID='" + uuid + "'");
        Object o = ret.get(ID);
        this.id = Integer.parseInt(o.toString());
        dbObject.stop();
    }

    @Override
    public void update() {
        dbObject.start();
        dbObject.setTable(tableName);
        dbObject.clearFields();
        dbAddField(OWNER_ID, this.getOwnerId());
        dbAddField(DESCRIPTION, getDescription());
        dbAddField(VALUE, getValue());
        dbAddField(START_YEAR, getStartYear());
        dbAddField(END_YEAR, getEndYear());
        dbAddField(SALARY, getSalary());
        dbObject.setWhere(ID + "='" + getId() + "'");
        dbObject.update();
        dbObject.stop();


    }

    public void initialize() {
        dbObject.start();
        dbObject.setTable(tableName);
        account = null;

        String sql = "select * from BIZ_CONTRACT where ID='" + getId() + "'";
        account = dbObject.execute(sql);
        dbObject.stop();

        if(account != null) {
            setDescription( (String) (account.get(DESCRIPTION)));
            setValue(((Number)(account.get(VALUE))).doubleValue());
            setStartYear(((Number)(account.get(START_YEAR))).intValue());
            setEndYear(((Number)(account.get(END_YEAR))).intValue());
            setSalary(((Number)(account.get(SALARY))).doubleValue());
            setOwnerId(((Number)(account.get(OWNER_ID))).longValue());
        }
    }


    public double getAssetBasis() {
        return 0;
    }


    public double getAssetGrowth() {
        return 0;

    }

    public double getAssetIncome() {
        return 0;
    }

    public double getAssetLiability() {
        return 0;
    }


    public String getAssetName() {
        return description;

    }


    public double getAssetValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
