package com.teag.bean;

/**
 * VCFBean Allows Additional Line items to be inserted into the cash flow tables.
 * @author Paul Stay
 * Date September 2005
 * Copyright The Estate Advisory Group @2005
 */

import java.util.ArrayList;
import java.util.HashMap;

public class VCFBean extends SqlBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3004249067450876687L;
	public final static String ID = "ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String VALUE = "VALUE";
	public final static String START_YEAR = "START_YEAR";
	public final static String END_YEAR = "END_YEAR";
	public final static String PRCT_TAX = "PRCT_TAX";
	public final static String GROWTH_PRCT = "GROWTH_PRCT";
	public final static String CF_FLAG = "CF_FLAG";
	public final static String TAX_DEDUCTION = "TAX_DEDUCTION";
	public final static String CHARITABLE_DEDUCTION = "CHARITABLE_DEDUCTION";
	public final static String USE_FLAG = "USE_FLAG";

	private String tablename = "VCF_TABLE";
	private long id = 0L;
	private String uuid;
	private long owner_id;
	
	private String description;		// Description of the cash flow variable
	private double value;			// Starting value of the cash reciept, or dispbursement
	private double prctTax;			// The percent taxable
	private double growth;			// growth percentage
	private int startYear;
	private int endYear;
	private String cfFlag = "C";	// C = Cash Recipts (Default), D = Disbursements
	private String taxDeduction = "N";	// N = No tax deduction, Y= tax deduction (disbursements)
	private String charitableDed = "N"; // N = non charitable deduction, y= charitable deduction
	private String useFlag = "B"; 		//B = Both, 1 = Scenario 1, 2 = scenario 2
	
	public void clearFields() {
		setId(0);
		owner_id = 0L;
		uuid = "";
		setDescription("");
		setStartYear(0);
		setEndYear(0);
		setValue(0);
		setPrctTax(0);
		setGrowth(0);
		setUseFlag("B");
	}
	
	public void delete() {
		if( id > 0L) {
			dbObject.start();
			dbObject.delete(tablename, ID + "='" + getId() + "'");
			dbObject.stop();
		}
	}

	public ArrayList<VCFBean> getBeans(String whereClause) {
		ArrayList<VCFBean> bList = new ArrayList<VCFBean>();
		String sql = "select " + ID + " from " + tablename + " where "
				+ whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while (ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			VCFBean nb = new VCFBean();
			nb.setId(id);
			nb.setDbObject();
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}
	
	public String getCfFlag() {
		return cfFlag;
	}
	
	public String getCharitableDed() {
		if( charitableDed != null)
			return charitableDed;
		return "N";
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getEndYear() {
		return endYear;
	}
	
	public double getGrowth() {
		return growth;
	}
	
	public long getId() {
		return id;
	}
	
	public long getOwner_id() {
		return owner_id;
	}

	public double getPrctTax() {
		return prctTax;
	}

	public int getStartYear() {
		return startYear;
	}

	public String getTaxDeduction() {
		if( taxDeduction != null)
			return taxDeduction;
		return "N";
	}

	public String getUseFlag() {
		return useFlag;
	}

	public String getUuid() {
		return uuid;
	}

	public double getValue() {
		return value;
	}

	@Override
	public void initialize() {
		if( id != 0L){
			dbObject.start();
			String sqlStatement = "select * from " + tablename + " where " + ID + "='" + id + "'";
			HashMap<String,Object> rec = dbObject.execute(sqlStatement);
			dbObject.stop();
			if( rec != null) {
				setId(getLong(rec, ID));
				setDescription(getString(rec,DESCRIPTION));
				setValue(getDouble(rec,VALUE));
				setPrctTax(getDouble(rec,PRCT_TAX));
				setGrowth(getDouble(rec,GROWTH_PRCT));
				setStartYear(getInteger(rec,START_YEAR));
				setEndYear(getInteger(rec,END_YEAR));
				setCfFlag(getString(rec,CF_FLAG));
				setTaxDeduction(getString(rec, TAX_DEDUCTION));
				setCharitableDed(getString(rec, CHARITABLE_DEDUCTION));
				setUseFlag(getString(rec, USE_FLAG));
			}
		}
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.clearFields();
		dbObject.setTable(tablename);
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(VALUE, getValue());
		dbAddField(START_YEAR, getStartYear());
		dbAddField(END_YEAR, getEndYear());
		dbAddField(PRCT_TAX, getPrctTax());
		dbAddField(GROWTH_PRCT, getGrowth());
		dbAddField(CF_FLAG, getCfFlag());
		dbAddField(OWNER_ID, getOwner_id());
		dbAddField(TAX_DEDUCTION, getTaxDeduction());
		dbAddField(CHARITABLE_DEDUCTION, getCharitableDed());
		dbAddField(USE_FLAG, getUseFlag());
		
		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tablename + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		setId(Integer.parseInt(o.toString()));
		dbObject.stop();
	}

	public void prep() {
		dbObject.clearFields();
		dbObject.setTable(tablename);
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(VALUE, getValue());
		dbAddField(START_YEAR, getStartYear());
		dbAddField(END_YEAR, getEndYear());
		dbAddField(PRCT_TAX, getPrctTax());
		dbAddField(GROWTH_PRCT, getGrowth());
		dbAddField(CF_FLAG, getCfFlag());
		dbAddField(TAX_DEDUCTION, getTaxDeduction());
		dbAddField(CHARITABLE_DEDUCTION, getCharitableDed());
		dbAddField(USE_FLAG, getUseFlag());
	}

	public VCFBean[] query(String field, String value) {
		VCFBean[] records = null;
		HashMap<String,Object> rec;
		dbObject.start();
		String sqlCount = "select count(*) as cnt from " + tablename + " where " + field + "='" + value + "'";
		rec = dbObject.execute(sqlCount);
		if( rec == null )
			return null;

		long count = 0;
		count = ((Number) rec.get("cnt")).longValue();
		
		
		if( count > 0) {
			records = new VCFBean[(int)count];
			String sqlStmt = "select " + ID + " from " + tablename + " where " + field + "='" + value + "'";
			int i = 0;
			rec = dbObject.execute(sqlStmt);
			while( rec != null && (i < count)){
				records[i] = new VCFBean();
				records[i].setDbObject();
				records[i].setId(getLong(rec, ID));
				records[i].initialize();
				rec = dbObject.next();
				i++;
			}
		}
		
		dbObject.stop();
		return records;
	}

	public void setCfFlag(String cfFlag) {
		this.cfFlag = cfFlag;
	}

	public void setCharitableDed(String charitableDed) {
		this.charitableDed = charitableDed;
	}

	public void setDefaults() {
		setCfFlag("C");
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}

	public void setPrctTax(double prctTax) {
		this.prctTax = prctTax;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	
	public void setTaxDeduction(String c) {
		taxDeduction = c;
	}
	
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public void update() {
		dbObject.start();
		prep();
		dbObject.setWhere(whereString());
		dbObject.update();
		dbObject.stop();
	}

	public String whereString() {
		return ID + "='" + getId() + "'";
	}
}
