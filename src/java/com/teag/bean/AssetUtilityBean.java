package com.teag.bean;

/**
 * @author Paul Stay
 * Created on Apr 28, 2005
 *
 */

import com.estate.db.DBObject;
import com.teag.client.EPTAssets;

public class AssetUtilityBean {
	DBObject dbObject = new DBObject();
	
	// Clean out any tools that might have been left over, but not committed
	public void cleanDB(long sId) {
		dbObject.start();
		dbObject.delete("EPTASSET_DIST", "TOOL_ID='"+ sId + "'");
		dbObject.stop();
	}

	public void deleteDistribution(long id) {
		dbObject.start();
		dbObject.delete("EPTASSET_DIST", "EPTASSET_DIST_ID='" + id + "'");
		dbObject.stop();
	}
	
	public void deleteTClatAssts(long scenarioId) {
		EPTAssets assets = new EPTAssets();
		assets.loadAssetList(scenarioId);
		
		EPTAssetBean eab[] = assets.getEPTAssets();
		for(int i = 0; i < eab.length; i++) {
		EPTAssetBean toolAsset = eab[i];
			if( toolAsset.getTclatId() == scenarioId ) {
				toolAsset.initialize();
				toolAsset.setTclatId(0L);
				toolAsset.update();
			}
		}
	}
	
	public DBObject getDbObject(){
		return dbObject;
	}
	
	public void setDbObject() {
		//dbObject.copy(this.dbObject);
	}
	
	// Committ tools when writting out Tool.
	public void updateTools(long tool_id, long sId) {
		dbObject.start();
		String stmt = "update EPTASSET_DIST set tool_id='" + tool_id + "' where tool_id='"+ sId+"'";
		dbObject.executeStatement(stmt);
		dbObject.stop();
	}
}
