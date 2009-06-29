package com.teag.estate;
import java.util.ArrayList;
import java.util.HashMap;

import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.EPTAssetDistBean;
import com.teag.bean.FlpLPBean;
import com.teag.bean.LoadAssetBean;
import com.teag.bean.SecuritiesBean;

public class ToolAssetDistribution extends EstatePlanningTool {
	
	Object[] distributions;
	AssetSqlBean[] assetData;
	long toolId;
	long toolTableId;
	double totalValue;
	double weightedGrowth;
	double averageGrowth;
	double weightedIncome;
	double averageIncome;
	double discountValue;
	double basis;
	double liability;
	
	String assetNames;
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	ToolAssetData toolAssetData[];
	
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#calculate()
	 */
	@Override
	public void calculate() {

		distributions = getDistributions();
		assetData = new AssetSqlBean[distributions.length];
		toolAssetData = new ToolAssetData[distributions.length];
		
		totalValue = 0.0;
		discountValue = 0.0;
		weightedGrowth = 0.0;
		weightedIncome = 0;
		basis = 0;
		liability = 0;

		StringBuffer sb = new StringBuffer("");
		
		for(int i = 0; i < distributions.length; i++) {
			EPTAssetBean asset = new EPTAssetBean();
			EPTAssetDistBean dist = (EPTAssetDistBean)distributions[i];
			asset.setId(dist.getEptassetId());
			asset.setDbObject();
			asset.initialize();
			LoadAssetBean lab = new LoadAssetBean();
			assetData[i] = lab.loadAssetBean(asset);
			liability += assetData[i].getAssetLiability();
			
			lab = null;
			ToolAssetData tad = new ToolAssetData();

			// We need to normalize the basis data
			double tValue = assetData[i].getAssetValue();
			double dValue = dist.getValue();
			double nBasis = assetData[i].getAssetBasis() * dValue/tValue;
			basis += nBasis;
			
			tad.setName(assetData[i].getAssetName());
			tad.setValue(dist.getValue());
			tad.setGrowth(assetData[i].getAssetGrowth());
			tad.setIncome(assetData[i].getAssetIncome());
			tad.setLiability(assetData[i].getAssetLiability());
			tad.setBasis(nBasis);
			tad.setAssetType(assetData[i].getBeanType());
			
			if( assetData[i] instanceof FlpLPBean) {
				FlpLPBean lpFlp = (FlpLPBean) assetData[i];
				tad.setDiscount(lpFlp.getDiscountValue());
			}
			
			if( assetData[i] instanceof SecuritiesBean) {
				SecuritiesBean secBean = (SecuritiesBean) assetData[i];
				tad.setTurnoverRate(secBean.getCgTurnover());
			}

			toolAssetData[i] = tad;

			totalValue += ((EPTAssetDistBean)distributions[i]).getValue();
			discountValue += tad.getDiscount();
			weightedGrowth += assetData[i].getAssetGrowth() * ((EPTAssetDistBean)distributions[i]).getValue();
			weightedIncome += assetData[i].getAssetIncome() * ((EPTAssetDistBean)distributions[i]).getValue();
			sb.append(assetData[i].getAssetName() + ", ");
			asset = null;
		}
		
		weightedGrowth = weightedGrowth / totalValue;
		weightedIncome = weightedIncome / totalValue;

		assetNames = sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {

    }

	/**
	 * @return Returns the assetData.
	 */
	public AssetSqlBean[] getAssetData() {
		return assetData;
	}

	/**
	 * @return Returns the assetNames.
	 */
	public String getAssetNames() {
		return assetNames;
	}

	/**
	 * @return Returns the averageGrowth.
	 */
	public double getAverageGrowth() {
		return averageGrowth;
	}

	/**
	 * @return Returns the averageIncome.
	 */
	public double getAverageIncome() {
		return averageIncome;
	}

	public double getBasis() {
		return basis;
	}

	public double getDiscountValue() {
		if( discountValue != 0)
			return discountValue;
		return totalValue;
	}

	private Object[] getDistributions() {
		Object[] assets;
		ArrayList<EPTAssetDistBean> aList = new ArrayList<EPTAssetDistBean>();
		HashMap<String,Object> rec;
			
		dbObj.start();
		dbObj.setTable("EPTASSET_DIST");
		String sql = "select EPTASSET_DIST_ID from EPTASSET_DIST where TOOL_ID='" + toolId + "' and TOOL_TYPE_ID='"
			+ toolTableId + "'";
		
		rec = dbObj.execute(sql);
		
		while( rec != null) {
			EPTAssetDistBean edb = new EPTAssetDistBean();
			edb.setDbObject();
			long id = getInteger(rec,"EPTASSET_DIST_ID");
			edb.setId(id);
			edb.initialize();
			aList.add(edb);
			rec = dbObj.next();
		}
		dbObj.stop();
		assets = aList.toArray();
		return assets;
	}

	public double getLiability() {
		return liability;
	}
	public ToolAssetData[] getToolAssetData() {
		return toolAssetData;
	}
	/**
	 * @return Returns the toolId.
	 */
	public long getToolId() {
		return toolId;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#getToolTableId()
	 */
	@Override
	public long getToolTableId() {
		return 0;
	}
	/**
	 * @return Returns the totalValue.
	 */
	public double getTotalValue() {
		return totalValue;
	}
	/**
	 * @return Returns the weightedGrowth.
	 */
	public double getWeightedGrowth() {
		return weightedGrowth;
	}
	/**
	 * @return Returns the weightedIncome.
	 */
	public double getWeightedIncome() {
		return weightedIncome;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {

	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#read()
	 */
	@Override
	public void read() {

	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#report()
	 */
	@Override
	public void report() {

	}
	/**
	 * @param assetData The assetData to set.
	 */
	public void setAssetData(AssetSqlBean[] assetData) {
		this.assetData = assetData;
	}
	/**
	 * @param assetNames The assetNames to set.
	 */
	public void setAssetNames(String assetNames) {
		this.assetNames = assetNames;
	}
	/**
	 * @param averageGrowth The averageGrowth to set.
	 */
	public void setAverageGrowth(double averageGrowth) {
		this.averageGrowth = averageGrowth;
	}
	/**
	 * @param averageIncome The averageIncome to set.
	 */
	public void setAverageIncome(double averageIncome) {
		this.averageIncome = averageIncome;
	}
	public void setBasis(double basis) {
		this.basis = basis;
	}
	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}
	public void setLiability(double liability) {
		this.liability = liability;
	}
	
	/**
	 * @param toolId The toolId to set.
	 */
	public void setToolId(long toolId) {
		this.toolId = toolId;
	}
	/**
	 * @param toolTableId The toolTableId to set.
	 */
	public void setToolTableId(long toolTableId) {
		this.toolTableId = toolTableId;
	}
	/**
	 * @param totalValue The totalValue to set.
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	/**
	 * @param weightedGrowth The weightedGrowth to set.
	 */
	public void setWeightedGrowth(double weightedGrowth) {
		this.weightedGrowth = weightedGrowth;
	}
	/**
	 * @param weightedIncome The weightedIncome to set.
	 */
	public void setWeightedIncome(double weightedIncome) {
		this.weightedIncome = weightedIncome;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {

	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#writeupText()
	 */
	@Override
	public String writeupText() {
		return null;
	}
}
