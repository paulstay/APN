package com.teag.EstatePlan;

/**
 * @author stay
 * Created on May 30, 2005
 *
 */

import java.util.ArrayList;

import com.teag.bean.EstatePlanningToolBean;

public class EstateTools extends EstatePlanSqlBean {
	
	long scenarioId;
	Object toolList[];
	
	
	/**
	 * @return Returns the scenarioId.
	 */
	@Override
	public long getScenarioId() {
		return scenarioId;
	}
	
	/**
	 * @return Returns the toolList.
	 */
	public Object[] getToolList() {
		return toolList;
	}
	
	
	public ArrayList<EstatePlanningToolBean> getToolsByType(int toolType) {
		ArrayList<EstatePlanningToolBean> retList = new ArrayList<EstatePlanningToolBean>();
		for( int i =0; i < toolList.length; i++) {
			EstatePlanningToolBean ept = (EstatePlanningToolBean) toolList[i];
			if( ept.getToolTableId() == toolType) {
				retList.add(ept);
			}
		}
		return retList;
	}
	public void initialize() {
		//	 Get tool list of existing tools!
		EstatePlanningToolBean estateTools = new EstatePlanningToolBean();
		if( dbobj != null)
			estateTools.setDbObject();
		if( getScenarioId() > 0)
			toolList = estateTools.getEstateToolList(getScenarioId());
	}
	/**
	 * @param scenarioId The scenarioId to set.
	 */
	@Override
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}
	/**
	 * @param toolList The toolList to set.
	 */
	public void setToolList(Object[] toolList) {
		this.toolList = toolList;
	}
}
