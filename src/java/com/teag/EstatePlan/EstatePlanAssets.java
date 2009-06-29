package com.teag.EstatePlan;

/**
 * @author stay
 * Created on May 30, 2005
 * Description - Utilities for getting EPTasset data from the database for Scenario 2
 */
import java.util.ArrayList;
import java.util.HashMap;

import com.teag.bean.EPTAssetBean;

public class EstatePlanAssets extends EstatePlanSqlBean {
	long scenarioId;
	
	public EstatePlanAssets(long scenarioId) {
		this.scenarioId = scenarioId;
	}
	
	public ArrayList<EPTAssetBean> getAllAssets(){
		ArrayList<EPTAssetBean> assetList = new ArrayList<EPTAssetBean>();
		
		String sql = "select EPTASSET_ID from EPTASSET where SCENARIO_ID='" + scenarioId + "'";
		dbobj.start();
		HashMap<String,Object> rec = dbobj.execute(sql);
		while( rec != null ) {
			long id = getLong(rec, "EPTASSET_ID");
			EPTAssetBean eag = new EPTAssetBean();
			eag.setId(id);
			eag.setDbObject();
			eag.initialize();
			assetList.add(eag);
			rec = dbobj.next();
		}
		dbobj.stop();

		return assetList;
	}
	
	public ArrayList<EPTAssetBean> getAssetTypes(long assetType) {
		ArrayList<EPTAssetBean> assetList = new ArrayList<EPTAssetBean>();
		String sql = "select EPTASSET_ID from EPTAsset where ASSET_TYPE_ID='" + assetType + "' and SCENARIO_ID='"
			+ scenarioId + "'";
		dbobj.start();
		HashMap<String,Object> rec = dbobj.execute(sql);
		while( rec != null) {
			long id = getLong(rec,"EPTASSET_ID");
			EPTAssetBean eag = new EPTAssetBean();
			eag.setId(id);
			eag.setDbObject();
			eag.initialize();
			assetList.add(eag);
			rec = dbobj.next();
		}
		dbobj.stop();
		
		return assetList;
	}
	
}
