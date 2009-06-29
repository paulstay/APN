package com.teag.estate;

/**
 * @author stay
 * Created on Apr 16, 2005
 *
 */

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.AssetSqlBean;

public class RpmTool extends EstatePlanningTool {

	private long id;
	private String uuid;
	private HashMap<String,Object> account;

	// Both ages are used to calculate the minimum required distribution.
	private int clientAge;
	private int spouseAge;
	
	private int term;
	private double lifePremium;
	private double lifeDeathBenefit;

	private double stateIncomeTaxRate;

	private double securitiesGrowth;
	private double securitiesIncome;
	private double securitiesTurnover;
	
	private double planValue;			// Combined qualified plans in tool
	private double planGrowth;			// Weighted average for plans in tool.
	
	private ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	
	RpmTable rpTable = new RpmTable();
	
	// There should only be assets from the Retirement Plan, so we can keep this pretty small.
	public void buildAssetList() {
		// Clear the list so we won't duplicate
		assetList.clear();
		// Get all of the tool asset ditributions
		ToolAssetDistribution toolAssets = new ToolAssetDistribution();
		toolAssets.setDbObject();
		toolAssets.setToolId( getId());
		toolAssets.setToolTableId(getToolTableId());
		toolAssets.calculate();
		
		planValue = 0;
		planGrowth = 0;
		
		AssetSqlBean[] asb = toolAssets.getAssetData();

		for(int i = 0; i < asb.length; i++) {
				AssetData ad = null;
				ad = new AssetData(AssetData.SECURITY);

				ad.setValue(asb[i].getAssetValue());
				ad.setGrowth(asb[i].getAssetGrowth());
				ad.setIncome(asb[i].getAssetIncome());
				ad.setName(asb[i].getAssetName());
				assetList.add(ad);
				
				planValue += ad.getValue();
				planGrowth += ad.getValue() * ad.getGrowth(); // Use a weighted growth
		}
		
		planGrowth = planGrowth / planValue;
	}

	@Override
	public void calculate() {
		buildAssetList();
		rpTable.setTerm(term);
		rpTable.setClientAge(clientAge);
		rpTable.setSpouseAge(spouseAge);

		rpTable.setSecDivRate(securitiesIncome);
		rpTable.setSecGrowthRate(securitiesGrowth);
		rpTable.setSecTurnoverRate(securitiesTurnover);
		
		rpTable.setLifeInsPremium(lifePremium);
		rpTable.setLifeInsDeathBenefit(lifeDeathBenefit);
		
		rpTable.setStateIncomeTaxRate(stateIncomeTaxRate);
		rpTable.setPlanGrowth(planGrowth);
		rpTable.setPlanValue(planValue);
		rpTable.calculate();
		rpTable.calculateRpm();
	}

	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("RPM_TOOL", "ID='" + id + "'");
		dbObj.stop();
	}

	public HashMap<String,Object> getAccount() {
		return account;
	}

	public ArrayList<AssetData> getAssetList() {
		return assetList;
	}

	public int getClientAge() {
		return clientAge;
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

	public double getPlanGrowth() {
		return planGrowth;
	}
	public double getPlanValue() {
		return planValue;
	}
	public RpmTable getRpTable() {
		return rpTable;
	}
	public double getSecuritiesGrowth() {
		return securitiesGrowth;
	}
	public double getSecuritiesIncome() {
		return securitiesIncome;
	}
	public double getSecuritiesTurnover() {
		return securitiesTurnover;
	}
	public int getSpouseAge() {
		return spouseAge;
	}
	public double getStateIncomeTaxRate() {
		return stateIncomeTaxRate;
	}
	public int getTerm() {
		return term;
	}
	@Override
	public long getToolTableId() {
		return ToolTableTypes.RPM.id();
	}
	public String getUuid() {
		return uuid;
	}
	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable("RPM_TOOL");
		dbObj.clearFields();
		
		account = null;
		
		dbAddField("TERM", getTerm());
		dbAddField("STATE_INCOME_TAX_RATE", getStateIncomeTaxRate());
		dbAddField("LIFE_INSURANCE_PREMIUM", getLifePremium());
		dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
		dbAddField("SEC_GROWTH", getSecuritiesGrowth());
		dbAddField("SEC_DIVIDEND", getSecuritiesIncome());
		dbAddField("SEC_TURNOVER_RATE", getSecuritiesTurnover());
		
		int err = dbObj.insert();
		if( err == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from RPM_TOOL where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			if( o!= null) {
				id = (Integer.parseInt(o.toString()));
			}
		}
		dbObj.stop();
	}
	@Override
	public void read() {
		if( id >0L) {
			dbObj.start();
			dbObj.setTable("RPM_TOOL");
			dbObj.clearFields();
			account = null;
			
			String sql = "select * from RPM_TOOL where ID='" + id + "'";
			account = dbObj.execute(sql);
			
			if( account != null ) {
				setTerm(getInteger(account,"TERM"));
				setStateIncomeTaxRate(getDouble(account,"STATE_INCOME_TAX_RATE"));
				setLifePremium(getDouble(account,"LIFE_INSURANCE_PREMIUM"));
				setLifeDeathBenefit(getDouble(account,"LIFE_DEATH_BENEFIT"));
				setSecuritiesGrowth(getDouble(account,"SEC_GROWTH"));
				setSecuritiesIncome(getDouble(account,"SEC_DIVIDEND"));
				setSecuritiesTurnover(getDouble(account,"SEC_TURNOVER_RATE"));
			}
			dbObj.stop();
		}
	}
	@Override
	public void report() {


	}
	public void setAccount(HashMap<String,Object> account) {
		this.account = account;
	}
	public void setAssetList(ArrayList<AssetData> assetList) {
		this.assetList = assetList;
	}
	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
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
	public void setPlanGrowth(double planGrowth) {
		this.planGrowth = planGrowth;
	}
	public void setPlanValue(double planValue) {
		this.planValue = planValue;
	}
	public void setSecuritiesGrowth(double securitiesGrowth) {
		this.securitiesGrowth = securitiesGrowth;
	}
	public void setSecuritiesIncome(double securitiesIncome) {
		this.securitiesIncome = securitiesIncome;
	}
	public void setSecuritiesTurnover(double securitiesTurnover) {
		this.securitiesTurnover = securitiesTurnover;
	}
	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}
	public void setStateIncomeTaxRate(double stateIncomeTaxRate) {
		this.stateIncomeTaxRate = stateIncomeTaxRate;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("RPM_TOOL");
		dbObj.clearFields();
		
		account = null;
		dbAddField("TERM", getTerm());
		dbAddField("STATE_INCOME_TAX_RATE", getStateIncomeTaxRate());
		dbAddField("LIFE_INSURANCE_PREMIUM", getLifePremium());
		dbAddField("LIFE_DEATH_BENEFIT", getLifeDeathBenefit());
		dbAddField("SEC_GROWTH", getSecuritiesGrowth());
		dbAddField("SEC_DIVIDEND", getSecuritiesIncome());
		dbAddField("SEC_TURNOVER_RATE", getSecuritiesTurnover());
		
		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();
	}
	@Override
	public String writeupText() {

		return null;
	}
}
