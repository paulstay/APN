/*
 * Created on May 9, 2005
 *
 */
package com.teag.estate;

/**
 * @author Paul Stay
 * 
 */

import java.util.ArrayList;

import com.estate.db.DBObject;
import com.teag.bean.AssetSqlBean;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.EPTAssetDistBean;
import com.teag.bean.FlpLPBean;
import com.teag.bean.LlcLPBean;
import com.teag.bean.LoadAssetBean;
import com.teag.client.EPTAssets;
import com.teag.webapp.EstatePlanningGlobals;

public class IditAssets {
	EstatePlanningGlobals estate;

	EPTAssets ept;

	EPTAssetBean[] assets;

	long toolId;

	long toolTableId;

	DBObject dbObject = new DBObject();

	ArrayList<AssetData> assetData = new ArrayList<AssetData>();

	ArrayList<AssetData> origData = new ArrayList<AssetData>();

	boolean useLLC = false;

	boolean useFLP = false;

	public void calculate() {
		ept = estate.getEptassets();
		assets = ept.getEPTAssets();

		LoadAssetBean lab = new LoadAssetBean();

		EPTAssetDistBean[] distributions = ept.getToolDistributions(toolId,
				toolTableId);

		for (int i = 0; i < distributions.length; i++) {
			EPTAssetBean toolAsset = new EPTAssetBean();
			toolAsset.setId(distributions[i].getEptassetId());
			toolAsset.setDbObject();
			toolAsset.initialize();
			AssetSqlBean asb = lab.loadAssetBean(toolAsset);
			AssetData asset;
			if (asb.getBeanType() == AssetSqlBean.SECURITIES)
				asset = new AssetData(AssetData.SECURITY);
			else
				asset = new AssetData(AssetData.OTHER);

			asset.setValue(distributions[i].getValue());
			asset.setGrowth(asb.getAssetGrowth());
			asset.setIncome(asb.getAssetIncome());
			if (asb instanceof FlpLPBean) {
				FlpLPBean lpBean = (FlpLPBean) asb;
				asset.setDiscountRate(lpBean.getDiscount());
				ToolAssetData tad[] = lpBean.getLpAssets();
				StringBuffer lsp = new StringBuffer("");
				for (int j = 0; j < tad.length; j++) {
					lsp.append(tad[j].getName() + ", ");
				}
				asset.setName(lsp.toString());
				useFLP = true;
			} else	if (asb instanceof LlcLPBean) {
				LlcLPBean lcbean = (LlcLPBean) asb;
				asset.setDiscountRate(lcbean.getDiscount());
				ToolAssetData tad[] = lcbean.getLpAssets();
				StringBuffer lsp = new StringBuffer("");
				for (int j = 0; j < tad.length; j++) {
					lsp.append(tad[j].getName() + ", ");
				}
				asset.setName(lsp.toString());
				useLLC = true;
			} else {
				asset.setDiscountRate(1.0);
				asset.setName(asb.getAssetName());
			}
			assetData.add(asset);
		}
	}

	public ArrayList<AssetData> getAssetData() {
		return assetData;
	}

	public EPTAssetBean[] getAssets() {
		return assets;
	}

	public DBObject getDbObject() {
		return dbObject;
	}

	public EPTAssets getEpt() {
		return ept;
	}

	public EstatePlanningGlobals getEstate() {
		return estate;
	}

	public void getOrig() {
		ept = estate.getEptassets();
		assets = ept.getEPTAssets();

		LoadAssetBean lab = new LoadAssetBean();

		EPTAssetDistBean[] distributions = ept.getToolDistributions(toolId,
				toolTableId);

		for (int i = 0; i < distributions.length; i++) {
			EPTAssetBean toolAsset = new EPTAssetBean();
			toolAsset.setId(distributions[i].getEptassetId());
			toolAsset.setDbObject();
			toolAsset.initialize();
			AssetSqlBean asb = lab.loadAssetBean(toolAsset);
			AssetData asset;
			if (asb.getBeanType() == AssetSqlBean.SECURITIES)
				asset = new AssetData(AssetData.SECURITY);
			else
				asset = new AssetData(AssetData.OTHER);

			asset.setGrowth(asb.getAssetGrowth());
			asset.setIncome(asb.getAssetIncome());
			if (asb instanceof FlpLPBean) {
				FlpLPBean lpBean = (FlpLPBean) asb;
				asset.setValue(asb.getAssetValue() / .98);
				asset.setDiscountRate(lpBean.getDiscount());
				ToolAssetData tad[] = lpBean.getLpAssets();
				StringBuffer lsp = new StringBuffer("");
				for (int j = 0; j < tad.length; j++) {
					lsp.append(tad[j].getName() + ", ");
				}
				asset.setName(lsp.toString());
			} else {
				asset.setValue(distributions[i].getValue());
				asset.setDiscountRate(1.0);
				// asset.setName(asb.getAssetName());
			}
			origData.add(asset);
		}
	}

	public ArrayList<AssetData> getOrigData() {
		return origData;
	}

	public long getToolId() {
		return toolId;
	}

	public long getToolTableId() {
		return toolTableId;
	}

	public boolean isUseFLP() {
		return useFLP;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	public void setAssetData(ArrayList<AssetData> assetData) {
		this.assetData = assetData;
	}

	public void setAssets(EPTAssetBean[] assets) {
		this.assets = assets;
	}

	public void setEpt(EPTAssets ept) {
		this.ept = ept;
	}

	public void setEstate(EstatePlanningGlobals estate) {
		this.estate = estate;
	}

	public void setOrigData(ArrayList<AssetData> origData) {
		this.origData = origData;
	}

	public void setToolId(long toolId) {
		this.toolId = toolId;
	}

	public void setToolTableId(long toolTableId) {
		this.toolTableId = toolTableId;
	}

	public void setUseFLP(boolean useFLP) {
		this.useFLP = useFLP;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}
}
