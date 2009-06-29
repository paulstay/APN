package com.teag.client;

import com.teag.bean.MarriageBean;
import com.teag.bean.PersonBean;

/**
 * @author Paul Stay Add a dummy spouse record for each primary client this
 *         spouse is used to support the "None" or single spouse issues with
 *         family and children.
 */
public class AddDummySpouse {
	private long personId; // The primary user id

	public boolean addSpouse() {
		if (personId > 0) {
			PersonBean s = new PersonBean();
			s.setFirstName("None");
			s.setMiddleName("None");
			s.setLastName("None");
			s.setBirthDate("1/1/1980");
			s.setCitizenship("US");
			s.setCity("Belcamp");
			s.setState("MD");
			s.setGender("F");
			s.setHealthId(1);
			s.setLifeExpectancy(0);
			s.setOccupation("NONE");
			s.setSsn("zzz-zz-zzzz");
			s.insert();
			long id = s.getId();

			MarriageBean m = new MarriageBean();
			m.setCity("Crab Apple Cove");
			m.setState("MD");
			m.setDate("1/1/1995");
			m.setPersonId(personId);
			m.setSpouseId(id);
			m.setStatus("S"); // Single status
			m.insert();

			return true;
		}
		return false;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}
}
