/*
 * Created on Jan 13, 2005
 *
 */
package com.teag.bean;

import java.io.Serializable;
import java.util.HashMap;



/**
 * @author Paul Stay
 *
 */
public class HealthTypeBean extends SqlBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -164439187341805647L;
	private long healthId[];
	private String healthDescr[];
	private long count;

	// Empty constructor
	public HealthTypeBean() {
		super();
	}

	/**
	 * @return Returns the count.
	 */
	public long getCount() {
		return count;
	}
	
	public String getHealthDescr(long healthId)
	{
		int i;
		
		for(i = 0; i < count; i++)
		{
			if(healthId == this.healthId[i])
			{
				return(healthDescr[i]);
			}
		}
		return(null);
	}
	
	public long getHealthId(int index)
	{
		return(healthId[index]);
		
	}
	@Override
	public void initialize()
	{
		int i;
		String sqlStatement = "select HEALTH_ID, HEALTH_DESCR from HEALTH";
		String sqlCount = "select count(*) as COUNT from HEALTH";
		dbObject.start();
		HashMap<String,Object> rec = dbObject.execute(sqlCount);
		HashMap<String,Object> rec2;
		count = ((Number)(rec.get("COUNT"))).longValue();
		dbObject.stop();
		
		healthId = new long[(int)count];
		healthDescr = new String[(int)count];
		dbObject.start();
		rec2 = dbObject.execute(sqlStatement);
		
		for(i = 0; i < count; i++)
		{
			healthId[i] = ((Number)(rec2.get("HEALTH_ID"))).longValue();
			healthDescr[i] = (String)rec2.get("HEALTH_DESCR");
			rec2 = dbObject.next();
		}
		
		dbObject.stop();
		
	}
	
	@Override
	public void insert()
	{
		return;
	}
	@Override
	public void update()
	{
		return;
	}
}
