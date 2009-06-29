package com.teag.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

public class SpouseView  extends SQLView {

	public final static String FIRST_NAME = "FIRST_NAME";
	public final static String LAST_NAME = "LAST_NAME";
	public final static String BIRTH_DATE = "BIRTH_DATE";
	public final static String MARRIAGE_DATE = "MARRIAGE_DATE";
	public final static String STATUS = "STATUS";
	public final static String GENDER = "GENDER";
	public final static String ID = "PERSON_ID";
	public final static String MARRIAGE_ID = "MARRIAGE_ID";
	
	long id;
	long marriageId;
	Date marriageDate;
	Date birthDate;
	String firstName;
	String lastName;
	String gender;
	String status;

	public Date getBirthDate() {
		return birthDate;
	}
	
	@Override
	public DBObject getDbObject(){
		return dbObject;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getGender() {
		return gender;
	}

	public long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getMarriageDate() {
		return marriageDate;
	}

	public long getMarriageId() {
		return marriageId;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public ArrayList<SpouseView> getView(long id) {
		ArrayList<SpouseView> sv = new ArrayList<SpouseView>();
		String sql = "select * from MARRIAGE a, PERSON c where a.PRIMARY_ID='" + id + 
			"' and a.spouse_id = c.person_id order by a.STATUS";
		dbObject.start();
		HashMap<String,Object> res = dbObject.execute(sql);
		while( res != null) {
				next(res); // Update the fields from the HashMap<String,Object>.
				SpouseView cv = new SpouseView();
				cv.setBirthDate(getBirthDate());
				cv.setMarriageDate(getMarriageDate());
				cv.setId(getId());
				cv.setMarriageId(getMarriageId());
				cv.setFirstName(getFirstName());
				cv.setLastName(getLastName());
				cv.setGender(getGender());
				cv.setStatus(status);
				sv.add(cv);
				res = dbObject.next();
		}
		dbObject.stop();
		return sv;
	}

	@Override
	public void next(HashMap<String,Object> res) {
		setBirthDate(getDate(res, BIRTH_DATE) );
		setMarriageDate(getDate(res, MARRIAGE_DATE));
		setId(getLong(res, ID));
		setMarriageId(getLong(res, MARRIAGE_ID));
		setFirstName(getString(res, FIRST_NAME));
		setLastName(getString(res,LAST_NAME));
		setGender(getString(res, GENDER));
		setStatus(getString(res, STATUS));
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMarriageDate(Date marriageDate) {
		this.marriageDate = marriageDate;
	}

	public void setMarriageId(long marriageId) {
		this.marriageId = marriageId;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
