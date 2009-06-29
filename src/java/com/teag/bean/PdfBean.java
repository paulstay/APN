package com.teag.bean;

import com.teag.util.Utilities;

public class PdfBean {
	private String clientFirstName;
	private String clientLastName;
	private boolean single = false; // assume married
	private String spouseFirstName;
	private String spouseLastName;
	private String plannerFirstName;
	private String plannerLastName;
	private long scenarioId;
	private long ownerId;
	private int clientAge;
	private int spouseAge;
	private String imgLocation; // Location of graphics
	private String fontLocation; // Locaiton of fonts;
	private int finalDeath;
	private String clientGender;
	private String spouseGender;
	private int clientLifeExpectancy;
	private int spouseLifeExpectancy;
	private String clientHeading;
	String firstName;
	String middleName;
	String lastName;
	int age;
	String address;
	String city;
	String state;
	String zipCode;
	String phone;
	String gender;
	String advisor1;
	String advisor2;
	String advisor3;
	String advisor4;

	// Put specific details for pdf here such as borders, logos, etc.....

	public PdfBean() {
		super();
	}

	public void genLife() {
		setClientLifeExpectancy((int)Utilities.getLifeExp(getClientAge(),getClientGender()));
		if( !isSingle()) {
			setSpouseLifeExpectancy((int)Utilities.getLifeExp(getSpouseAge(),getSpouseGender()));
		}
	}

	public int getClientAge() {
		return clientAge;
	}
	
	public String getClientFirstName() {
		return clientFirstName;
	}

	public String getClientGender() {
		return clientGender;
	}

	public String getClientHeading() {
		return clientHeading;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	public int getClientLifeExpectancy() {
		return clientLifeExpectancy;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public String getFontLocation() {
		return fontLocation;
	}

	public String getImgLocation() {
		return imgLocation;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public String getPlannerFirstName() {
		return plannerFirstName;
	}

	public String getPlannerLastName() {
		return plannerLastName;
	}

	public long getScenarioId() {
		return scenarioId;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public String getSpouseFirstName() {
		return spouseFirstName;
	}

	public String getSpouseGender() {
		return spouseGender;
	}

	public String getSpouseLastName() {
		return spouseLastName;
	}

	public int getSpouseLifeExpectancy() {
		return spouseLifeExpectancy;
	}

	public boolean isSingle() {
		return single;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public void setClientGender(String clientGender) {
		this.clientGender = clientGender;
	}

	public void setClientHeading(String clientHeading) {
		this.clientHeading = clientHeading;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	public void setClientLifeExpectancy(int clientLifeExpectancy) {
		this.clientLifeExpectancy = clientLifeExpectancy;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFontLocation(String fontLocation) {
		this.fontLocation = fontLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setPlannerFirstName(String plannerFirstName) {
		this.plannerFirstName = plannerFirstName;
	}

	public void setPlannerLastName(String plannerLastName) {
		this.plannerLastName = plannerLastName;
	}

	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}

	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}

	public void setSpouseGender(String spouseGender) {
		this.spouseGender = spouseGender;
	}

	public void setSpouseLastName(String spouseLastName) {
		this.spouseLastName = spouseLastName;
	}

	public void setSpouseLifeExpectancy(int spouseLifeExpectancy) {
		this.spouseLifeExpectancy = spouseLifeExpectancy;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAdvisor1() {
		return advisor1;
	}

	public void setAdvisor1(String advisor1) {
		this.advisor1 = advisor1;
	}

	public String getAdvisor2() {
		return advisor2;
	}

	public void setAdvisor2(String advisor2) {
		this.advisor2 = advisor2;
	}

	public String getAdvisor3() {
		return advisor3;
	}

	public void setAdvisor3(String advisor3) {
		this.advisor3 = advisor3;
	}

	public String getAdvisor4() {
		return advisor4;
	}

	public void setAdvisor4(String advisor4) {
		this.advisor4 = advisor4;
	}
	
}
