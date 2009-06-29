/*
 * Created on Apr 22, 2005
 *
 */
package com.teag.estate;

import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
/**
 * @author Paul Stay
 *
 */
public class CrummeyTool extends EstatePlanningTool {

	private long id;
	private String uuid;
	private double term;
	private double annualGift;
	private double lifePremium;
	private double lifeDeathBenefit;
	private boolean withMgt;
	
	private HashMap<String,Object> account;

	@Override
	public void calculate() {

	}

	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("CRUM_TOOL", "ID='" + id + "'");
		dbObj.stop();

	}

	public double getAnnualGift() {
		return annualGift;
	}

	public long getId() {
		return id;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public double getLifePremium() {
		return lifePremium;
	}

	public double getTerm() {
		return term;
	}

	@Override
	public long getToolTableId() {
		return ToolTableTypes.CRUM.id();
	}

	@Override
	public void insert() {
		dbObj.start();
		dbObj.clearFields();
		dbObj.setTable("CRUM_TOOL");
		dbAddField("TERM", getTerm());
		dbAddField("ANNUAL_GIFT", getAnnualGift());
		dbAddField("LIFE_INSURANCE_PREMIUM", lifePremium);
		dbAddField("LIFE_DEATH_BENEFIT", lifeDeathBenefit);
		dbAddField("WITH_MGT", isWithMgt() ? "T" : "F");
		int error = dbObj.insert();

		if( error == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from CRUM_TOOL where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			id = Integer.parseInt(o.toString());
		}
		dbObj.stop();

	}
	/**
	 * @return Returns the withMgt.
	 */
	public boolean isWithMgt() {
		return withMgt;
	}
	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("CRUM_TOOL");
			account = null;
			String sql = "select * from CRUM_TOOL where ID='"+ id +"'";
			account = dbObj.execute(sql);
			dbObj.stop();
			
			if(account != null) {
				this.setTerm(getDouble(account,"TERM"));
				this.setAnnualGift(getDouble(account,"ANNUAL_GIFT"));
				this.setLifePremium(getDouble(account,"LIFE_INSURANCE_PREMIUM"));
				this.setLifeDeathBenefit(getDouble(account,"LIFE_DEATH_BENEFIT"));
				this.setWithMgt(getBoolean(account,"WITH_MGT"));
			}
		}
	}
	@Override
	public void report() {

	}
	public void setAnnualGift(double annualGift) {
		this.annualGift = annualGift;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}
	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
	}
	public void setTerm(double term) {
		this.term = term;
	}
	/**
	 * @param withMgt The withMgt to set.
	 */
	public void setWithMgt(boolean withMgt) {
		this.withMgt = withMgt;
	}
	@Override
	public void update() {
		
		dbObj.start();
		dbObj.clearFields();
		dbObj.setTable("CRUM_TOOL");
		dbAddField("TERM", getTerm());
		dbAddField("ANNUAL_GIFT", getAnnualGift());
		dbAddField("LIFE_INSURANCE_PREMIUM", lifePremium);
		dbAddField("LIFE_DEATH_BENEFIT", lifeDeathBenefit);
		dbAddField("WITH_MGT", isWithMgt() ? "T" : "F");
		
		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();

	}
	@Override
	public String writeupText() {
		return null;
	}
}
