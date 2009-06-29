package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;

/**
 * @author Paul Stay
 * @version 2.0 Date : 7/26/07 com.teag.beans.PortfolioBean
 */

public class PortfolioBean extends SqlBean {
	public static final long serialVersionUID = 1;
	public final static String ID = "ID";
	public final static String DESCRIPTION = "DESCRIPTION";

	public final static String OWNER_ID = "OWNER_ID";
	public final static String VALUE = "VALUE";
	public final static String GROWTH_RATE = "GROWTH_RATE";
	public final static String INCOME = "INCOME";
	public final static String TAXABLE = "TAXABLE";
	public final static String EXCESS_CASH_FLOW = "EXCESS_CASH_FLOW";
	public String tableName = "PORTFOLIO";
	DBObject dbObject = new DBObject();

	private long id;
	private String description;
	private long ownerId;
	private double value;
	private double growth;
	private double income;
	private double taxable;
	private double excessCashFlow;

	public void delete() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.delete(tableName, ID + "=" + getId());
		dbObject.stop();
	}

	public String getDescription() {
		return description;
	}

	public double getExcessCashFlow() {
		return excessCashFlow;
	}

	public double getGrowth() {
		return growth;
	}

	public long getId() {
		return id;
	}

	public double getIncome() {
		return income;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public double getTaxable() {
		return taxable;
	}

	public double getValue() {
		return value;
	}

	@Override
	public void initialize() {
		if (getId() > 0l) {
			dbObject.start();
			dbObject.setTable(tableName);
			dbObject.clearFields();
			String sql = "select * from PORTFOLIO  where ID=" + getId();

			HashMap<String, Object> account = dbObject.execute(sql);

			if (account != null) {
				setId(getLong(account, ID));
				setDescription(getString(account, DESCRIPTION));
				setOwnerId(getLong(account, OWNER_ID));
				setValue(getDouble(account, VALUE));
				setGrowth(getDouble(account, GROWTH_RATE));
				setIncome(getDouble(account, INCOME));
				setExcessCashFlow(getDouble(account, EXCESS_CASH_FLOW));
			}
			dbObject.stop();
		}
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(VALUE, getValue());
		dbAddField(GROWTH_RATE, getGrowth());
		dbAddField(INCOME, getIncome());
		dbAddField(TAXABLE, getTaxable());
		dbAddField(EXCESS_CASH_FLOW, getExcessCashFlow());
		dbObject.insert();
		String uuid = dbObject.getUUID();
		HashMap<String, Object> ret = dbObject.execute("select " + ID
				+ " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get("ID");
		setId(Integer.parseInt(o.toString()));
		dbObject.stop();
	}

	public boolean next() {
		HashMap<String, Object> acc = dbObject.next();

		if (acc != null) {
			setId(getLong(acc, ID));
			setDescription(getString(acc, DESCRIPTION));
			setOwnerId(getLong(acc, OWNER_ID));
			setValue(getDouble(acc, VALUE));
			setGrowth(getDouble(acc, GROWTH_RATE));
			setIncome(getDouble(acc, INCOME));
			setExcessCashFlow(getDouble(acc, EXCESS_CASH_FLOW));
			return true;
		}
		return false;
	}

	public ArrayList<PortfolioBean> query(String whereClause) {
		ArrayList<PortfolioBean> list = new ArrayList<PortfolioBean>();
		String sql = "select " + ID + " from " + tableName + " where "
				+ whereClause;
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.setWhere(whereClause);
		dbObject.execute(sql);
		while (next()) {
			PortfolioBean bean = new PortfolioBean();
			bean.setId(getId());
			bean.initialize();
			list.add(bean);
		}
		dbObject.stop();
		return list;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExcessCashFlow(double excessCashFlow) {
		this.excessCashFlow = excessCashFlow;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setTaxable(double taxable) {
		this.taxable = taxable;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public void update() {
		if (getId() > 0l) {
			dbObject.start();
			dbObject.setTable(tableName);
			dbObject.clearFields();
			dbAddField(ID, getId());
			dbAddField(DESCRIPTION, getDescription());
			dbAddField(OWNER_ID, getOwnerId());
			dbAddField(VALUE, getValue());
			dbAddField(GROWTH_RATE, getGrowth());
			dbAddField(INCOME, getIncome());
			dbAddField(TAXABLE, getTaxable());
			dbAddField(EXCESS_CASH_FLOW, getExcessCashFlow());
			dbObject.setWhere(ID + "='" + getId() + "'");
			dbObject.update();
			dbObject.stop();
		}
	}

}
