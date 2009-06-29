/*
 * Created on Jan 7, 2005
 *
 */
package com.teag.client;


import java.util.HashMap;

import com.teag.bean.PersonBean;
/**
 * @author tjensen
 *
 */
public class Children extends ClientSql {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8273418538475003994L;
	private long primaryId;
	private long parentsId[];
	private long spouseId[];
	private long childId[];
	
	private int idx;
	
	HashMap<String,Object> children;
	/**
	 * 
	 */
	public Children() {
		
	}
	public PersonBean getFirstChild()
	{
		PersonBean Child = new PersonBean();
		HashMap<String,Object> count;
		int numChildren;
		
		String CountQuery = "select count(*) as TOTAL_CHILDREN " + 
		"from PERSON p, MARRIAGE m, FAMILY f " + 
		"left outer join MARRIAGE mc on mc.PRIMARY_ID=p.PERSON_ID and mc.STATUS='c' " + 
		"left outer join PERSON s on s.PERSON_ID=mc.SPOUSE_ID" + 
		" where m.PRIMARY_ID='" + primaryId +  
		"' and f.MARRIAGE_ID=m.MARRIAGE_ID and p.PERSON_ID=f.PERSON_ID";
		String Query = "select s.PERSON_ID as CHILD_SPOUSE_ID, p.PERSON_ID as CHILD_PERSON_ID, m.MARRIAGE_ID as CHILD_PARENTS_MARRIAGE_ID " + 
		"from PERSON p, MARRIAGE m, FAMILY f " + 
		"left outer join MARRIAGE mc on mc.PRIMARY_ID=p.PERSON_ID and mc.STATUS='c' " + 
		"left outer join PERSON s on s.PERSON_ID=mc.SPOUSE_ID" + 
		" where m.PRIMARY_ID='" + primaryId +  
		"' and f.MARRIAGE_ID=m.MARRIAGE_ID and p.PERSON_ID=f.PERSON_ID";
		// Execute the query
		dbobj.start();
		count = dbobj.execute(CountQuery);
		numChildren = ((Number)(count.get("TOTAL_CHILDREN"))).intValue();
		dbobj.stop();
		
		dbobj.start();
		children = dbobj.execute(Query);
		
		if(null == children)
		{
			dbobj.stop();
			return(null);
		}
		
		childId = new long[numChildren + 1];
		spouseId = new long[numChildren + 1];
		parentsId = new long[numChildren + 1];
		
		for(idx = 0; idx < numChildren; idx++)
		{
			childId[idx] = ((Number)(children.get("CHILD_PERSON_ID"))).intValue();
			spouseId[idx] = children.get("CHILD_SPOUSE_ID") == null ? 0 : ((Number)(children.get("CHILD_SPOUSE_ID"))).intValue();
			parentsId[idx] = ((Number)(children.get("CHILD_PARENTS_MARRIAGE_ID"))).intValue();
			children = dbobj.next();
		}
		dbobj.stop();
		childId[idx] = 0;	// Mark the end of the list
		idx = 0;
		Child.setDbObject();
		Child.setId(childId[idx]);
		Child.initialize();
		return(Child);
	}
	public PersonBean getNextChild()
	{
		PersonBean Child = new PersonBean();
		 
		
		
		if(childId[++idx] == 0)
		{
			return(null);
		}
			
		
		Child.setDbObject();
		Child.setId(childId[idx]);
		Child.initialize();
		
		return(Child);
	}
	
	// This returns the marraige id for the current child.
	public long getParentsId()
	{
		return(parentsId[idx]);
	}
	
	/**
	 * @return Returns the primaryId.
	 */
	public long getPrimaryId() {
		return primaryId;
	}
	
	public long getSpouseId()
	{
		return(spouseId[idx]);
	}
	
	/**
	 * @param primaryId The primaryId to set.
	 */
	public void setPrimaryId(long primaryId) {
		this.primaryId = primaryId;
	}
	
}
