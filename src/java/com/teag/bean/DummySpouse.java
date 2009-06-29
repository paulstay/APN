package com.teag.bean;

import com.estate.db.DBObject;

public class DummySpouse  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3860734638250225028L;
	
	DBObject dbObject = new DBObject();
	
	long marriageId = 0;
	
	public DummySpouse() {
		super(); 
	}
	
	public void addSpouseRecord(long personId) {
		PersonBean spouse = new PersonBean();
		spouse.setDbObject();
		spouse.setBirthDate("1/1/1980");
		spouse.setCitizenship("us");
		spouse.setCity("Cobble Creek");
		spouse.setState("UT");
		spouse.setFirstName("None");
		spouse.setMiddleName("None");
		spouse.setLastName("None");
		spouse.setSsn("sss-ss-ssss");
		spouse.setGender("F");
		spouse.setHealthId(1);
		spouse.setLifeExpectancy(1);
		spouse.setOccupation("NONE");
		spouse.insert();
		long id = spouse.getId();
		
		MarriageBean m = new MarriageBean();
		m.setCity("Belcamp");
		m.setState("MD");
		m.setDate("1/1/1990");
		m.setHusband("None");
		m.setPersonId(personId);
		m.setSpouseId(id);
		m.setStatus("S");
		m.setWife("None");
		m.insert();
		marriageId = m.getId();
	}

	public DBObject getDbObject(){
		return dbObject;
	}
	
	public long getMarriageId() {
		return marriageId;
	}

	public void setMarriageId(long marriageId) {
		this.marriageId = marriageId;
	}
	
	
}
