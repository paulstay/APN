package com.teag.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

public class ChildrenView extends SQLView {
	
	public final static String ID = "id";
	public final static String FIRST_NAME = "fn";
	public final static String LAST_NAME = "ln";
	public final static String BIRTH_DATE = "bd";
	public final static String OCCUPATION = "oc";
	public final static String SPOUSE_ID = "sid";
	public final static String SPOUSE_FIRST_NAME = "sfn";
	public final static String SPOUSE_LAST_NAME = "sln";
	public final static String MARRIAGE_ID = "marriageId";
	
	long id;
	long spouseId;
	String firstName;
	String lastName;
	String occupation;
	Date birthDate;
	String spouseFirstName;
	String spouseLastName;
	long marriageId;
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	@Override
	public DBObject getDbObject() {
		return dbObject;
	}

	public String getFirstName() {
		return firstName;
	}

	public long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public long getMarriageId() {
		return marriageId;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getSpouseFirstName() {
		return spouseFirstName;
	}

	public long getSpouseId() {
		return spouseId;
	}

	public String getSpouseLastName() {
		return spouseLastName;
	}

	@Override
	public ArrayList<ChildrenView> getView(long id) {
		ArrayList<ChildrenView> children = new ArrayList<ChildrenView>();
		dbObject.start();
		String sql = "select p.person_id as id, p.first_name as fn, p.last_name as ln, p.birth_date as bd, p.occupation as oc, s.person_id as sid, s.FIRST_NAME as sfn, s.LAST_NAME as sln, m.marriage_id as marriageId " +
		"from Person p, marriage m, family f " + 
		"left outer join marriage mc on mc.primary_id=p.person_id " + 
		"left outer join Person s on s.person_id=mc.spouse_id " +
		"where m.primary_id='" + id + "' and f.marriage_id=m.marriage_id and p.person_id=f.person_id";

		HashMap<String,Object> rs = dbObject.execute(sql);
		while(rs != null) {
			next(rs);
			ChildrenView c = new ChildrenView();
			c.setId(getId());
			c.setBirthDate(birthDate);
			c.setFirstName(firstName);
			c.setLastName(lastName);
			c.setOccupation(occupation);
			c.setBirthDate(birthDate);
			c.setSpouseFirstName(spouseFirstName);
			c.setSpouseId(spouseId);
			c.setSpouseLastName(spouseLastName);
			c.setMarriageId(marriageId);
			children.add(c);
			rs = dbObject.next();
		}
		dbObject.stop();
		
		return children;
	}

	@Override
	public void next(HashMap<String,Object> res) {
			setId(getLong(res, ID));
			setFirstName(getString(res, FIRST_NAME));
			setLastName(getString(res, LAST_NAME));
			setOccupation(getString(res, OCCUPATION));
			setBirthDate(getDate(res, BIRTH_DATE));
			setSpouseFirstName(getString(res, SPOUSE_FIRST_NAME));
			setSpouseLastName(getString(res, SPOUSE_LAST_NAME));
			setSpouseId(getLong(res, SPOUSE_ID));
			setMarriageId(getLong(res,MARRIAGE_ID));
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMarriageId(long marriageId) {
		this.marriageId = marriageId;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}

	public void setSpouseId(long spouseId) {
		this.spouseId = spouseId;
	}

	public void setSpouseLastName(String spouseLastName) {
		this.spouseLastName = spouseLastName;
	}
	
	
}
