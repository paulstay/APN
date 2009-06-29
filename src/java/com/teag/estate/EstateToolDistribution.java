package com.teag.estate;
/**
 * @author stay
 * Created on May 4, 2005
 *
 */
import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;
import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.EPTAssetDistBean;
import com.teag.bean.FlpLPBean;
import com.teag.bean.LlcLPBean;
import com.teag.bean.LoadAssetBean;
import com.teag.bean.SecuritiesBean;

public class EstateToolDistribution {
	DBObject dbObj;
	Object[] distributions;
	long toolId;
	long toolTableId;
	
	double totalValue;
	double nonDiscountValue;
	double growth;
	double income;
	double discountValue;
	double basis;
	double liability;
	String assetNames;
	StringBuffer nameBuffer = new StringBuffer("");
	boolean useLLC = false;
	
	Object[] assetToolData;
	
	public void buildToolAssetList() {
		
		distributions = getDistributions();
		ArrayList<ToolAssetData> toolAssetData = new ArrayList<ToolAssetData>();
		LoadAssetBean lab = new LoadAssetBean();
		
		totalValue = 0;
		growth = 0;
		income = 0;
		discountValue = 0;
		nonDiscountValue = 0;
		basis = 0;
		liability = 0;
		
		if( distributions == null) 
			return;
		
		for( int i = 0; i < distributions.length; i++) {
			EPTAssetBean assetBean = new EPTAssetBean();
			ToolAssetData toolAsset = null;
			EPTAssetDistBean distBean = (EPTAssetDistBean) distributions[i];
			assetBean.setId(distBean.getEptassetId());
			assetBean.setDbObject();
			assetBean.initialize();
			AssetSqlBean asb = lab.loadAssetBean(assetBean);
			basis += asb.getAssetBasis();
			liability += asb.getAssetLiability();
			if( asb instanceof FlpLPBean) {
				processFlp((FlpLPBean)asb, toolAssetData,distBean);
			} else if(asb instanceof LlcLPBean){
				processLlc((LlcLPBean)asb, toolAssetData, distBean);
				useLLC = true;
			} else {
				toolAsset = loadToolAssetData(asb, distBean);
				// Add the asset to the tool list
				// We also don't want to duplicate the LP asset
				toolAssetData.add(toolAsset);
				totalValue += toolAsset.getValue();
				growth += toolAsset.getGrowth() * toolAsset.getValue(); // Use weighted
				income += toolAsset.getIncome() * toolAsset.getValue(); // Use weighted
				if( toolAsset.getDiscount() > 0) {
					discountValue += toolAsset.getDiscount();
				} else {
					nonDiscountValue += toolAsset.getValue();
				}

				if( nameBuffer.length() == 0)
					nameBuffer.append(toolAsset.getName());
				else
					nameBuffer.append(", " + toolAsset.getName());
			}
			
		}
		growth = growth / totalValue;
		income = income / totalValue;
		
		assetNames = nameBuffer.toString();
		assetToolData = toolAssetData.toArray();
		
	}

	public String getAssetNames() {
		return assetNames;
	}

	public Object[] getAssetToolData() {
		return assetToolData;
	}
	
	public double getBasis() {
		return basis;
	}
	
	public DBObject getDbObj() {
		return dbObj;
	}
	
	public double getDiscountValue() {
		return discountValue;
	}
	
	
	public Object[] getDistributions() {
		Object[] assets = null;
		ArrayList<EPTAssetDistBean> oList = new ArrayList<EPTAssetDistBean>();
		
		EPTAssetDistBean ea = new EPTAssetDistBean();
		oList = ea.getBeans("TOOL_ID='" + toolId + "' and TOOL_TYPE_ID='" + toolTableId + "'");
		
		if(oList.size() > 0) {
			assets = oList.toArray();
		}
		
		return assets;
	}
	public double getGrowth() {
		return growth;
	}
	public double getIncome() {
		return income;
	}
	public int getInteger(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if( obj != null) {
			return ((Number) obj).intValue();
		}
		return 0;
	}
	public double getLiability() {
		return liability;
	}

	public double getNonDiscountValue() {
		return nonDiscountValue;
	}
	
	public long getToolId() {
		return toolId;
	}
	public long getToolTableId() {
		return toolTableId;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public boolean isUseLLC() {
		return useLLC;
	}
	public ToolAssetData loadToolAssetData(AssetSqlBean asb, EPTAssetDistBean dist) {
		ToolAssetData tad = new ToolAssetData();
		tad.setName(asb.getAssetName());
		tad.setValue(dist.getValue());
		tad.setGrowth(asb.getAssetGrowth());
		tad.setIncome(asb.getAssetIncome());
		tad.setLiability(asb.getAssetLiability());
		tad.setBasis(asb.getAssetBasis());
		tad.setAssetType(asb.getBeanType());
		if( asb instanceof SecuritiesBean) {
			tad.setTurnoverRate(((SecuritiesBean)(asb)).getCgTurnover());
		}
		if( asb instanceof FlpLPBean) {
			tad.setDiscount(((FlpLPBean)(asb)).getDiscountValue());
			tad.setFlpAsset(true);
		}

		return tad;
	}
	// the asset is an flp so we need to get the sub distributions
	public void processFlp(FlpLPBean lpBean, ArrayList<ToolAssetData> toolAssets, EPTAssetDistBean dist) {
		double dValue = dist.getValue()/lpBean.getValue();
		ToolAssetData[] lpAssets = lpBean.getLpAssets();
		
		for(int i = 0; i < lpAssets.length; i++) {
			totalValue += lpAssets[i].getValue() * dValue;
			discountValue += lpAssets[i].getDiscount() * dValue;
			growth += lpAssets[i].getGrowth() * (lpAssets[i].getValue()* dValue);
			income += lpAssets[i].getIncome() * (lpAssets[i].getValue()* dValue);
			lpAssets[i].setValue(dValue * lpAssets[i].getValue());
			
			if( nameBuffer.length() == 0)
				nameBuffer.append(lpAssets[i].getName());
			else
				nameBuffer.append(", " + lpAssets[i].getName());
			toolAssets.add(lpAssets[i]);
		}
	}
	public void processLlc(LlcLPBean llcBean, ArrayList<ToolAssetData> toolAssets, EPTAssetDistBean dist) {
		double dValue = dist.getValue()/llcBean.getValue();
		ToolAssetData[] lpAssets = llcBean.getLpAssets();
		for(int i = 0; i < lpAssets.length; i++) {
			totalValue += lpAssets[i].getValue()*dValue;
			discountValue += lpAssets[i].getDiscount()*dValue;
			growth += lpAssets[i].getGrowth() * lpAssets[i].getValue()*dValue;
			income += lpAssets[i].getIncome() * lpAssets[i].getValue()*dValue;
			
			if( nameBuffer.length() == 0)
				nameBuffer.append(lpAssets[i].getName());
			else
				nameBuffer.append(", " + lpAssets[i].getName());
			toolAssets.add(lpAssets[i]);
		}
	}
	public void setAssetNames(String assetNames) {
		this.assetNames = assetNames;
	}
	public void setAssetToolData(Object[] assetToolData) {
		this.assetToolData = assetToolData;
	}
	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}
	public void setDistributions(Object[] distributions) {
		this.distributions = distributions;
	}
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public void setLiability(double liability) {
		this.liability = liability;
	}
	public void setNonDiscountValue(double nonDiscountValue) {
		this.nonDiscountValue = nonDiscountValue;
	}
	public void setToolId(long toolId) {
		this.toolId = toolId;
	}
	public void setToolTableId(long toolTableId) {
		this.toolTableId = toolTableId;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}
}
