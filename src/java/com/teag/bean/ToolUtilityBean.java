package com.teag.bean;

import com.estate.constants.ToolTableTypes;
import com.estate.db.DBObject;
/**
 * @author stay
 * Created on May 19, 2005
 *
 */
public class ToolUtilityBean {
	DBObject dbObject = new DBObject();
	
	public void deleteTool(long toolId, long tableId, long planToolId) {
		ToolTableTypes tType = ToolTableTypes.getToolTableType((int)tableId);
		// First delete all of the assets that may have been distributed
		dbObject.start();
		dbObject.delete("EPTASSET_DIST", "TOOL_ID='" + toolId + "' and TOOL_TYPE_ID='" + tableId + "'");
		String tableName = tType.toolName();
		String idName = tType.idName();
		// If this is a FLP or LLC then delete the GP and LP assets
		if( tableId == 5) {
			dbObject.delete("FLP_GP_TABLE", "FLP_TOOL_ID='" + toolId + "'" );
			dbObject.delete("FLP_LP_TABLE", "FLP_TOOL_ID='" + toolId + "'" );
		}
		if( tableId == 6) {
			dbObject.delete("LLC_GP_TABLE", "LLC_TOOL_ID='" + toolId + "'" );
			dbObject.delete("LLC_LP_TABLE", "LLC_TOOL_ID='" + toolId + "'" );
		}
		dbObject.delete(tableName, idName +"='" + toolId + "'");
		dbObject.delete("ESTATE_PLANNING_TOOLS", "ID='" + planToolId + "'");
		dbObject.stop();
	}
	
	public DBObject getDbObject(){
		return dbObject;
	}
}
