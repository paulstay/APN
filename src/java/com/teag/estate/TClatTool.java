package com.teag.estate;

/**
 * @author stay
 * Created on Apr 27, 2005
 *
 */

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.LoadAssetBean;
import com.teag.client.EPTAssets;
import com.zcalc.zCalc;

public class TClatTool extends EstatePlanningTool {

	private long id;
	private String uuid;
	private HashMap<String,Object> record;
	private double afrRate;
	private String afrDate;
	private double annuityFreq;
	private int annuityType;
	private int term;
	private double incomeTaxRate;
	private double finalDeath;
	private String inclusive;
	
	double annuity = 0;
	double annuityFactor;
	double paymentRate;
	double annuityInterest;
	double remainderInterest;
	double totalInterest;
	double nonCharitableInterest;
	double clatDeduction;
	
	double toolValue = 0;
	double toolGrowth = 0;
	double toolIncome = 0;
	long scenarioId = 0;
	
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	
	public void buildAssetList() {
		/*
		assetList.clear();
		
		for(int i = 0; i < assetData.length; i++) {
			AssetData ad;
			if( assetData[i].getAssetType() == AssetSqlBean.SECURITIES) {
				ad = new AssetData(AssetData.SECURITY);
			} else {
				ad = new AssetData(AssetData.OTHER);
			}
			ad.setName(assetData[i].getName());
			ad.setValue(assetData[i].getValue());
			ad.setGrowth(assetData[i].getGrowth());
			ad.setIncome(assetData[i].getIncome());
			assetList.add(ad);
		}
		*/
		getAssets();
	}
	
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#calculate()
	 */
	@Override
	public void calculate() {
		zCalc zc = new zCalc();
		zc.StartUp();

		getAssets();
		
		paymentRate = zc.zANNRATEMAX(afrRate,term,0,0,0,0,0,0,(int)annuityFreq,annuityType,0,0,0);
		
		annuityFactor = zc.zTERM(0L,afrRate,term,0,(int)annuityFreq,annuityType,0);
		annuity = paymentRate * toolValue;
		
		clatDeduction = zc.zANNTERM(toolValue,paymentRate,afrRate,term,0,(int)annuityFreq,annuityType,0,0);
		
		annuityInterest = clatDeduction;
		totalInterest = toolValue;
		nonCharitableInterest = totalInterest - clatDeduction;
		
		zc.ShutDown();
	}
	
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("TCLAT_TOOL", "ID='" + id + "'");
		dbObj.stop();
	}
	
	public String getAfrDate() {
		return afrDate;
	}
	
	public double getAfrRate() {
		return afrRate;
	}

	public double getAnnuity() {
		return annuity;
	}

	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public double getAnnuityFreq() {
		return annuityFreq;
	}

	public double getAnnuityInterest() {
		return annuityInterest;
	}

	public int getAnnuityType() {
		return annuityType;
	}

	public ArrayList<AssetData> getAssetList() {
		return assetList;
	}

	public void getAssets() {
		assetList.clear();

		LoadAssetBean lab = new LoadAssetBean();
		toolValue = 0;
		
		EPTAssets assets = new EPTAssets();
		assets.loadAssetList(scenarioId);
		
		EPTAssetBean eab[] = assets.getEPTAssets();
		for(int i = 0; i < eab.length; i++) {
		EPTAssetBean toolAsset = eab[i];
			if( toolAsset.getTclatId() == scenarioId ) {
				toolAsset.initialize();
				AssetSqlBean sb = lab.loadAssetBean(toolAsset);
				AssetData ad; 
				if(toolAsset.getBeanType() == AssetSqlBean.SECURITIES) {
					ad = new AssetData(AssetData.SECURITY);
				} else {
					ad = new AssetData(AssetData.OTHER);
				}
				toolValue += toolAsset.getRemainingValue();
				ad.setName(toolAsset.getDescription());
				ad.setValue(toolAsset.getRemainingValue());
				ad.setGrowth(sb.getAssetGrowth());
				ad.setIncome(sb.getAssetIncome());
				assetList.add(ad);
			}
			
		}
	}

	public double getClatDeduction() {
		return clatDeduction;
	}
	public double getEstateValue() {
		return toolValue * Math.pow((1 + toolGrowth + toolIncome),term);
	}
	public double getFinalDeath() {
		return finalDeath;
	}
	public long getId() {
		return id;
	}
	public String getInclusive() {
		return inclusive;
	}
	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}
	public double getNonCharitableInterest() {
		return nonCharitableInterest;
	}
	public double getPaymentRate() {
		return paymentRate;
	}
	public HashMap<String,Object> getRecord() {
		return record;
	}
	public double getRemainderInterest() {
		return remainderInterest;
	}
	public long getScenarioId() {
		return scenarioId;
	}
	public int getTerm() {
		return term;
	}
	public double getToolGrowth() {
		return toolGrowth;
	}
	public double getToolIncome() {
		return toolIncome;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#getToolTableId()
	 */
	@Override
	public long getToolTableId() {
		return ToolTableTypes.TCLAT.id();
	}
	public double getToolValue() {
		return toolValue;
	}
	public double getTotalInterest() {
		return totalInterest;
	}
	public String getUuid() {
		return uuid;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable("TCLAT_TOOL");
		dbObj.clearFields();
		record = null;
		
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("TERM", getTerm());
		dbAddField("ANNUITY_FREQ", getAnnuityFreq());
		dbAddField("ANNUITY_TYPE", getAnnuityType());
		dbAddField("INCOME_TAX_RATE", getIncomeTaxRate());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("INCLUSIVE", getInclusive());
		int error = dbObj.insert();
		
		if( error == 0) {
			uuid = dbObj.getUUID();
			record = dbObj.execute("select ID from TCLAT_TOOL where UUID='" + uuid + "'");
			Object o = record.get("ID");
			if( o != null ) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#read()
	 */
	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("TCLAT_TOOL");
			dbObj.clearFields();
			record = null;
			
			String sql = "select * from TCLAT_TOOL where ID='" + id + "'";
			record = dbObj.execute(sql);
			dbObj.stop();
			
			if( record != null ) {
				setAfrRate(getDouble(record, "AFR_RATE"));
				setAfrDate(getDate(record,"AFR_DATE"));
				setId(getLong(record,"ID"));
				setTerm(getInteger(record,"TERM"));
				setAnnuityFreq(getDouble(record,"ANNUITY_FREQ"));
				setAnnuityType(getInteger(record,"ANNUITY_TYPE"));
				setIncomeTaxRate(getDouble(record,"INCOME_TAX_RATE"));
				setFinalDeath(getInteger(record,"FINAL_DEATH"));
				setInclusive(getString(record, "INCLUSIVE"));
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#report()
	 */
	@Override
	public void report() {

	}
	public void setAfrDate(String afrDate) {
		this.afrDate = afrDate;
	}
	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}
	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}
	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}
	public void setAnnuityFreq(double annuityFreq) {
		this.annuityFreq = annuityFreq;
	}
	public void setAnnuityInterest(double annuityInterest) {
		this.annuityInterest = annuityInterest;
	}
	public void setAnnuityType(int annuityType) {
		this.annuityType = annuityType;
	}
	public void setAssetList(ArrayList<AssetData> assetList) {
		this.assetList = assetList;
	}
	public void setClatDeduction(double clatDeduction) {
		this.clatDeduction = clatDeduction;
	}
	public void setFinalDeath(double finalDeath) {
		this.finalDeath = finalDeath;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setInclusive(String inclusive) {
		this.inclusive = inclusive;
	}
	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}
	public void setNonCharitableInterest(double nonCharitableInterest) {
		this.nonCharitableInterest = nonCharitableInterest;
	}
	public void setPaymentRate(double paymentRate) {
		this.paymentRate = paymentRate;
	}
	public void setRecord(HashMap<String,Object> record) {
		this.record = record;
	}
	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public void setToolGrowth(double toolGrowth) {
		this.toolGrowth = toolGrowth;
	}
	public void setToolIncomde(double toolIncome) {
		this.toolIncome = toolIncome;
	}
	public void setToolValue(double toolValue) {
		this.toolValue = toolValue;
	}
	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void stdCalc(double amount, double remainderInterest) {
		zCalc zc = new zCalc();
		zc.StartUp();
		if( remainderInterest > 0) {
			paymentRate = zc.zANNRATETARGET(amount, remainderInterest, afrRate, term, 
					0, 0, 0, 0, 0, 0, (int)annuityFreq, annuityType, 0,0, 0);
		} else {
			paymentRate = zc.zANNRATEMAX(afrRate, term, 0, 0, 0, 0, 0, 0, (int)annuityFreq, annuityType, 0, 0, 0);
		}
		annuity = paymentRate * amount;
		
		annuityFactor = zc.zTERM(0L,afrRate, term, 0, (int)annuityFreq, annuityType, 0);
		double actualPayout = annuity/amount;
		clatDeduction = zc.zANNTERM(amount, actualPayout, afrRate, term, 0, (int)annuityFreq, annuityType, 0, 0);

		this.remainderInterest = amount - clatDeduction;
		
		zc.ShutDown();
	}


	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("TCLAT_TOOL");
		dbObj.clearFields();
		record = null;
		
		dbAddField("AFR_RATE", getAfrRate());
		dbAddDate("AFR_DATE", getAfrDate());
		dbAddField("TERM", getTerm());
		dbAddField("ANNUITY_FREQ", getAnnuityFreq());
		dbAddField("ANNUITY_TYPE", getAnnuityType());
		dbAddField("INCOME_TAX_RATE", getIncomeTaxRate());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("INCLUSIVE", getInclusive());
		dbObj.setWhere("ID='" + id + "'");
		
		dbObj.update();
		dbObj.stop();
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#writeupText()
	 */
	@Override
	public String writeupText() {
		return null;
	}
}
