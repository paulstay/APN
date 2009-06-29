package com.teag.view;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientView extends SQLView {
	
	public final static String FIRST_NAME = "FIRST_NAME";
	public final static String LAST_NAME = "LAST_NAME";
	public final static String CLIENT_ID = "CLIENT_ID";
	public final static String PERSON_ID = "PERSON_ID";
	
	long clientId;
	long plannerId;
	long personId;
	String firstName;
	String lastName;
	
	public long getClientId() {
		return clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public long getPersonId() {
		return personId;
	}

	public long getPlannerId() {
		return plannerId;
	}

	@Override
	public ArrayList<ClientView> getView(long id) {
		ArrayList<ClientView> list = new ArrayList<ClientView> ();
		String sql = "Select * from CLIENT A, PERSON B where PLANNER_ID='"
				+ id +"'" + " and A.PRIMARY_ID=B.PERSON_ID;";
		dbObject.start();
		HashMap<String,Object> rs = dbObject.execute(sql);
		while(rs != null) {
			next(rs);
			ClientView cv = new ClientView();
			cv.setClientId(getClientId());
			cv.setFirstName(getFirstName());
			cv.setLastName(getLastName());
			cv.setPersonId(getPersonId());
			list.add(cv);
			rs = dbObject.next();
		}
		dbObject.stop();
		return list;
	}

	@Override
	public void next(HashMap<String,Object> res) {
		setClientId(getLong(res, CLIENT_ID));
		setFirstName(getString(res, FIRST_NAME));
		setLastName(getString(res, LAST_NAME));
		setPersonId(getLong(res, PERSON_ID));
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public void setPlannerId(long plannerId) {
		this.plannerId = plannerId;
	}

	
}
