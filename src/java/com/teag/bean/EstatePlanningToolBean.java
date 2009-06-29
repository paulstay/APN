/*
 * Created on Apr 1, 2005
 *
 */
package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;

/**
 * @author Paul Stay
 *
 */
public class EstatePlanningToolBean extends ToolSqlBean {
	
	public final static String OWNER_ID = "OWNER_ID";

	private long id;
	private long scenarioId;
	private long toolId;
	private long toolTableId;
	private String description;
	private String idFieldName = "ID";
	private String tableName = "ESTATE_PLANNING_TOOLS";
	private HashMap<String,Object> account;
	
	private String uuid;
	
	public void deleteTools(long sId) {
		Object[] tools = getEstateToolList(sId);
		dbObject.start();
		
		for( int i = 0; i < tools.length; i++) {
			EstatePlanningToolBean eb = (EstatePlanningToolBean) tools[i];
			long toolId = eb.getToolId();
			long tooltableid = eb.getToolTableId();
			
			ToolTableTypes toolType = ToolTableTypes.getToolTableType((int)tooltableid);
			String toolFieldId = toolType.idName();
			String toolTableName = toolType.toolName();
			String whereClause = " " + toolFieldId + "='" + toolId + "'";

			dbObject.delete(toolTableName, whereClause  );
			
			if( toolType == ToolTableTypes.FLP ){
				String w2 = " FLP_TOOL_ID='" + toolId + "'";
				dbObject.delete("FLP_GP_TABLE", w2);
				dbObject.delete("FLP_LP_TABLE", w2);
			}
			
			if( toolType == ToolTableTypes.LLC ) {
				String w2 = " LLC_TOOL_ID='" + toolId + "'";
				dbObject.delete("LLC_GP_TABLE", w2);
				dbObject.delete("LLC_LP_TABLE", w2);
			}
			
			dbObject.delete("ESTATE_PLANNING_TOOLS", "ID='" + eb.getId() + "'");
		}
		dbObject.stop();
	}
	/**
	 * @return Returns the account.
	 */
	public HashMap<String,Object> getAccount() {
		return account;
	}
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	
	public Object[] getEstateToolList(long sId) {
		Object[] tools;
		ArrayList<EstatePlanningToolBean> aList = new ArrayList<EstatePlanningToolBean>();
		dbObject.start();
		dbObject.setTable(tableName);
		HashMap<String,Object> list = null;
		String sql = "select " + idFieldName + " from " + tableName + " where SCENARIO_ID='" + sId +"'";
		list = dbObject.execute(sql);

		while( list != null) {
			long id = ((Number)list.get(idFieldName)).longValue();
			EstatePlanningToolBean e = new EstatePlanningToolBean();
			e.setId(id);
			e.setDbObject();
			e.initialize();
			aList.add(e);
			list = dbObject.next();
		}
		
		tools =  aList.toArray();
		dbObject.stop();
		
		return tools;
	}
	
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @return Returns the scenarioId.
	 */
	public long getScenarioId() {
		return scenarioId;
	}
	/**
	 * @return Returns the toolid.
	 */
	public long getToolId() {
		return toolId;
	}
	/**
	 * @return Returns the toolTableId.
	 */
	public long getToolTableId() {
		return toolTableId;
	}
	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		account = null;
		String sql = "select * from " + tableName + " where id='" + id + "'";

		account = dbObject.execute(sql);
		
		dbObject.stop();

		if( account != null) {
			setId(((Number)account.get("ID")).longValue());
			setScenarioId(((Number)account.get("SCENARIO_ID")).longValue());
			setToolId(((Number)account.get("TOOL_ID")).longValue());
			setToolTableId(((Number)account.get("TOOL_TABLE_ID")).longValue());
			setDescription((String)account.get("DESCRIPTION"));
		}
	}
	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField("ID", getId());
		dbAddField("SCENARIO_ID", getScenarioId());
		dbAddField("TOOL_ID", getToolId());
		dbAddField("TOOL_TABLE_ID", getToolTableId());
		dbAddField("DESCRIPTION", getDescription());

		dbObject.insert();
		
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + idFieldName + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(idFieldName);
		this.id = Integer.parseInt(o.toString());
		
		dbObject.stop();

	}
	/**
	 * @param account The account to set.
	 */
	public void setAccount(HashMap<String,Object> account) {
		this.account = account;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param scenarioId The scenarioId to set.
	 */
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}
	/**
	 * @param toolid The toolid to set.
	 */
	public void setToolId(long toolid) {
		this.toolId = toolid;
	}
	/**
	 * @param toolTableId The toolTableId to set.
	 */
	public void setToolTableId(long toolTableId) {
		this.toolTableId = toolTableId;
	}
	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField("ID", getId());
		dbAddField("SCENARIO_ID", getScenarioId());
		dbAddField("TOOL_ID", getToolId());
		dbAddField("TOOL_TABLE_ID", getToolTableId());
		dbAddField("DESCRIPTION", getDescription());
		dbObject.setWhere("ID='"+ id +"'");
		dbObject.update();
		dbObject.stop();
	}
}
