/*
 * Created on Mar 11, 2005
 *
 */
package com.teag.client;

import java.util.HashMap;

import com.estate.constants.EptAssetTypes;
import com.teag.bean.EPTAssetBean;
import com.teag.bean.EPTAssetDistBean;

/**
 * @author Paul Stay
 *
  */
public class EPTAssets extends ClientSql {

	private static final long serialVersionUID = 8832231327991768367L;
	private int count;
	private EPTAssetBean EPTAssetList[];
	
	public EPTAssets()
	{
		
	}
	
	private void buildList(long scenarioId)
	{
		// Ok we have added all the assets to the EPTASSET table
		// now we want to create a list of the assets
		String sqlCount = "select COUNT(*) as EPTASSETCOUNT from EPTASSET where SCENARIO_ID='" + scenarioId + "'";
		String sql = "select EPTASSET_ID from EPTASSET where SCENARIO_ID='" + scenarioId + "'";
		HashMap<String,Object> eptAssetRec;
		
		dbobj.start();
		eptAssetRec = dbobj.execute(sqlCount);
		count = 0;
		if(null != eptAssetRec)
		{	
			count = ((Number)(eptAssetRec.get("EPTASSETCOUNT"))).intValue();
		}
		dbobj.stop();

		if( count > 0)
		{
	
			int i = 0;
			EPTAssetList = new EPTAssetBean[count];	
			dbobj.start();
			eptAssetRec = dbobj.execute(sql);
			while(null != eptAssetRec)
			{
				long assetId = ((Number)(eptAssetRec.get("EPTASSET_ID"))).longValue();
				EPTAssetList[i]= new EPTAssetBean();
				EPTAssetList[i].setId(assetId);
				//EPTAssetList[i].setDbObject(dbobj);
				i++;
				eptAssetRec = dbobj.next();
			}
			dbobj.stop();

			for( i = 0; i < EPTAssetList.length; i++) {
				EPTAssetList[i].initialize();
			}
		}
		
	}
	
	public EPTAssetBean[] getEPTAssets()
	{
		return(EPTAssetList);
	}
	
	private long getOwnerIdFromScenario(long scenarioId)
	{
		long ownerId = 0;
		HashMap<String,Object> scenario;
		
		dbobj.start();
		
		scenario = dbobj.execute("select OWNER_ID from SCENARIO_TABLE where SCENARIO_ID ='" + scenarioId + "'");
		
		
		dbobj.stop();
		
		if(null != scenario)
		{
			ownerId = ((Number)(scenario.get("OWNER_ID"))).longValue();
		}
		return ownerId;
	}
	
	public EPTAssetDistBean[] getToolDistributions(long toolId, long toolTypeId)
	{
		EPTAssetDistBean eptAD[] = {};
		EPTAssetDistBean eptADTemp[] = {};
		int totalDist = 0;
		
		for(int i = 0; i < this.count; i++)
		{
			totalDist += EPTAssetList[i].getToolDistCount(toolId, toolTypeId);
		}
		
		if(totalDist > 0)
		{
			eptAD = new EPTAssetDistBean[totalDist];
			int idx = 0;
			for(int i = 0; i < this.count; i++)
			{
				eptADTemp = EPTAssetList[i].getToolDistributions(toolId, toolTypeId);
				for(int j = 0; j < eptADTemp.length; j++)
				{
					eptAD[idx++] = eptADTemp[j];
				}
				
			}
			
		}
		return(eptAD);
	}
	
	private void loadAllAssetsFrom(String tableName, String idName, long assetType, long scenarioId, long ownerId)
	{
		String sqlCount = "select count(*) as COUNT from " + tableName + " where OWNER_ID='" + ownerId + "'";
		HashMap<String,Object> assets;
		long assetId;
		int count = 0;
		long assetIds[] = {};
		
		dbobj.start();
		assets = dbobj.execute(sqlCount);
		if(null != assets)
		{
			count = ((Number)(assets.get("COUNT"))).intValue();
			assetIds =  new long[count];
			int i = 0;
			String idSql = "select " + idName + " from " + tableName + " where OWNER_ID='" + ownerId + "'";
			assets = dbobj.execute(idSql);
			while(null != assets)
			{
				assetIds[i++] = ((Number)(assets.get(idName))).longValue();
				assets = dbobj.next();
			}
		}
		dbobj.stop();
		
		dbobj.start();
		for(int i = 0; i < count; i++)
		{
			HashMap<String,Object> eptAssetRec;
			assetId = assetIds[i]; 
			String eptSql = "select * from EPTASSET where ASSET_ID='" + assetId + "' and ASSET_TYPE_ID='" + assetType + "'"
			+ " and SCENARIO_ID='" + scenarioId + "'";
			
			eptAssetRec = dbobj.execute(eptSql);
			//  is there an EPTAsset already for this asset?
			if(null == eptAssetRec)
			{	// No EPTAsset for this asset so create one
				EPTAssetBean ept = new EPTAssetBean();
				//pt.setDbObject(dbobj);
				ept.setAssetId(assetId);
				ept.setAssetType(assetType);
				ept.setScenarioId(scenarioId);
				ept.insert();
			}
			assets = dbobj.next();
		}
		dbobj.stop();
	}
	
	public void loadAssetList(long scenarioId)
	{
		//EPTAssetTypes eptAssetTypes = new EPTAssetTypes(dbobj);
		long ownerId = this.getOwnerIdFromScenario(scenarioId);
		
		/*
		for(long i = 1; i <= eptAssetTypes.getCount(); i++)
		{
			// This will create entries for any assets that don't exist in the EPTASSET table
			loadAllAssetsFrom(eptAssetTypes.getTableName(i), eptAssetTypes.getIdFieldName(i), i, scenarioId, ownerId);
		}
		*/
		for( EptAssetTypes t : EptAssetTypes.values()) {
			loadAllAssetsFrom(t.tableName(), t.tableId(), t.id(), scenarioId, ownerId);
		}
		
		this.buildList(scenarioId);

	}
	
	public void newAssetList(long scenarioId)
	{
		// Load up the existing EPTAssets so we can delete them and any distributions
		this.buildList(scenarioId);
		for(int i = 0; i < count; i++)
		{
			EPTAssetList[i].deleteFromDB();
			EPTAssetList[i] = null;
		}
		
		this.loadAssetList(scenarioId);
		
		
	}
}
