package com.teag.EstatePlan;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.estate.db.DBObject;
import com.teag.analysis.CFRow;
import com.teag.bean.CashFlowBean;
import com.teag.bean.ClientBean;
import com.teag.webapp.EstatePlanningGlobals;


/**
 * @author stay
 * Created on May 30, 2005
 * Description - Basic functionality for getting data from the database, and rows as well
 * as formatting routines for all of the scenario 2 routines.
 */
public class EstatePlanSqlBean {
	//	 General formating.
	public static DecimalFormat number = new DecimalFormat("###,###,###");
	public static DecimalFormat percentformat = new DecimalFormat("0.00%");
	
	public static DecimalFormat taxformat = new DecimalFormat("0.0000%");
	public static DecimalFormat weight = new DecimalFormat("0.0000");
	public static DecimalFormat dollarformat = new DecimalFormat("0.00");
	
	public static DecimalFormat moneyformat = new DecimalFormat("$0.00");

	public static SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
	
	
	
	ArrayList<CFRow> rows = new ArrayList<CFRow>();
	Iterator<CFRow> itr;
	long scenarioId;
	ClientBean clientBean;
	CashFlowBean cashFlowVars;
	int finalDeath;
	EstatePlanningGlobals estate;
	
	DBObject dbobj = new DBObject();

    public String formatNumber(double value) {
		DecimalFormat df = new DecimalFormat("###,###,###");
		return df.format(rnd(value));
	}

    public boolean getBoolean(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if (obj != null
				&& (((String) (obj)).equals("T") || ((String) (obj))
						.equals("t"))) {
			return true;
		}
		return false;
	}

	/**
	 * @return Returns the client.
	 */
	public ClientBean getClient() {
		return clientBean;
	}

	public String getDate(HashMap<String,Object> rec, String field) {
		Date rDate = (Date) rec.get(field);
		if (rDate != null) {
			try {
				return dateformat.format(rDate);
			} catch (Exception e) {
			}
		}
		return "";
	}
	
	public double getDouble(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if (obj != null) {
			return ((Number) obj).doubleValue();
		}
		return 0.0;
	}

	/**
	 * @return Returns the estate.
	 */
	public EstatePlanningGlobals getEstate() {
		return estate;
	}
	
	public int getInteger(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if( obj != null) {
			return ((Number) obj).intValue();
		}
		return 0;
	}

	public long getLong(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if( obj != null) {
			return ((Number)obj).longValue();
		}
		return 0l;
	}
	
	public CFRow getRow() {
		CFRow row = null;
		if (itr.hasNext()) {
			row = itr.next();
		}
		return row;
	}
	
	/**
	 * @return Returns the scenarioId.
	 */
	public long getScenarioId() {
		return scenarioId;
	}

	public String getString(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if (obj != null) {
			return ((String) obj);
		}
		return null;
	}
	
	public void resetRows() {
		itr = rows.iterator();
	}
	
	public double rnd(double v) {
		//return (Math.floor(v / 1000.0));
		return (Math.rint(v / 1000.0));
	}
	/**
	 * @param client The client to set.
	 */
	public void setClient(ClientBean client) {
		this.clientBean = client;
		long ownerId = client.getPrimaryId();
		cashFlowVars = new CashFlowBean();
		cashFlowVars.setOwnerId(ownerId);
		cashFlowVars.initialize();
		finalDeath = cashFlowVars.getFinalDeath();
	}

	/**
	 * @param estate The estate to set.
	 */
	public void setEstate(EstatePlanningGlobals estate) {
		this.estate = estate;
	}
	
	/**
	 * @param scenarioId The scenarioId to set.
	 */
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}
	public String totalFormat(double value) {
		DecimalFormat df = new DecimalFormat("###,###,###");
		return df.format(value);
	}
}
