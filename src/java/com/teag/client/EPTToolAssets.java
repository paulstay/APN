package com.teag.client;

import java.util.HashMap;

public class EPTToolAssets extends ClientSql {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4017680717846837703L;

	public void getDistributions(long toolId, long toolTypeId) {
		HashMap<String,Object> rec;
		
		dbobj.start();
		dbobj.setTable("EPTASSET_DIST");
		String sql = "select * from EPTASSET_DIST where TOOLID='" + toolId + "' and TOOL_TYPE_ID='" + toolTypeId + "'";
		
		rec = dbobj.execute(sql);
		
		while( rec != null) {
		}
	}

}
