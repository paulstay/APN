/*
 * Created on Jan 10, 2005
 *
 */
package com.teag.client;

import java.util.HashMap;

import com.teag.bean.MarriageBean;

/**
 * @author Paul Stay
 *
 */
public class Marriages extends ClientSql {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8620898956548807328L;
	private long primaryId;
	private int marriageIdx;
	
	private MarriageBean marriage[];
	private long marriageCount; 
	private MarriageBean currentMarriage;
	

	/**
	 * @return Returns the currentMarriage.
	 */
	public MarriageBean getCurrentMarriage() {
		return currentMarriage;
	}
	public MarriageBean getFirstMarriage( )
	{
		marriageIdx = 0;
		return(marriage[marriageIdx]);
	}
	public MarriageBean getNextMarriage( )
	{
		marriageIdx++;
		return(marriageIdx < marriageCount ? marriage[marriageIdx] : null); 
	}
	
	/**
	 * @return Returns the primaryId.
	 */
	public long getPrimaryId() {
		return primaryId;
	}
	
	public void initialize()
	{
		int i;
		HashMap<String,Object> marriages;
		HashMap<String,Object> count;
		String sqlStatement = "select * from MARRIAGE where PRIMARY_ID='" + primaryId + "'";
		String sqlCount = "select count(*) as TOTAL_MARRIAGES from MARRIAGE where PRIMARY_ID='" + primaryId + "'";
		
		currentMarriage = null;
		// First get the number of marriages for the person.
		dbobj.start();
		count = dbobj.execute(sqlCount);
		marriageCount = ((Number)(count.get("TOTAL_MARRIAGES"))).intValue();
		dbobj.stop();
		
		dbobj.start();
		marriages = dbobj.execute(sqlStatement);

		if(null == marriages)
		{
			dbobj.stop();
			return;
		}
		
		marriage = new MarriageBean[(int)marriageCount];
		
		for(i = 0; i < marriageCount; i++)
		{
			marriage[i] = new MarriageBean();
			marriage[i].setDbObject();
			marriage[i].setId(((Number)(marriages.get("MARRIAGE_ID"))).longValue());
			marriages = dbobj.next();
		}
		dbobj.stop();

		for( int j = 0; j < marriageCount; j++) {
			marriage[j].initialize();
			if(marriage[j].getStatus().equalsIgnoreCase("c"))
			{
				currentMarriage = marriage[j];
			}		}
	}
	/**
	 * @param primaryId The primaryId to set.
	 */
	public void setPrimaryId(long primaryId) {
		this.primaryId = primaryId;
	}
}
