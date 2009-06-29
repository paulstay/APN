package com.teag.scenario1;

import java.util.Calendar;
import java.util.Date;

import com.teag.bean.PersonBean;

public class CashFlowVars {
	enum PortfolioType {
			SECURITIES, BUSINESS, REAL_ESTATE, BONDS
	}
	
	int finalDeath;
	double portfolioGrowth;
	double portfolioInterest;
	String portfolioName;
	PersonBean client;
	PersonBean spouse;
	int cAge;
	int sAge;
	int retirementAge;
	boolean isSingle;
	PortfolioType pType = PortfolioType.SECURITIES;
	Date now = new Date();
	int currentYear;
	int currentMonth;
	double socialSecurity;
	double socialSecurityGrowth;
	boolean useSocialSecurity;
	int clientStartSocial;
	int spouseStartSocial;
	double fedTaxPre = .35;
	double fedTaxPost = .39;
	
	
	public CashFlowVars() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		currentYear = cal.get(Calendar.YEAR);
		currentMonth = cal.get(Calendar.MONTH);
	}

	public int getCAge() {
		return cAge;
	}

	public PersonBean getClient() {
		return client;
	}

	public int getClientStartSocial() {
		return clientStartSocial;
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public Date getNow() {
		return now;
	}

	public double getPortfolioGrowth() {
		return portfolioGrowth;
	}

	public double getPortfolioInterest() {
		return portfolioInterest;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public PortfolioType getPType() {
		return pType;
	}

	public int getRetirementAge() {
		return retirementAge;
	}

	public int getSAge() {
		return sAge;
	}

	public double getSocialSecurity() {
		return socialSecurity;
	}

	public double getSocialSecurityGrowth() {
		return socialSecurityGrowth;
	}

	public PersonBean getSpouse() {
		return spouse;
	}

	public int getSpouseStartSocial() {
		return spouseStartSocial;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public boolean isUseSocialSecurity() {
		return useSocialSecurity;
	}

	public void setCAge(int age) {
		cAge = age;
	}

	public void setClient(PersonBean client) {
		this.client = client;
	}

	public void setClientStartSocial(int clientStartSocial) {
		this.clientStartSocial = clientStartSocial;
	}

	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public void setPortfolioGrowth(double portfolioGrowth) {
		this.portfolioGrowth = portfolioGrowth;
	}

	public void setPortfolioInterest(double portfolioInterest) {
		this.portfolioInterest = portfolioInterest;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public void setPType(PortfolioType type) {
		pType = type;
	}

	public void setRetirementAge(int retirementAge) {
		this.retirementAge = retirementAge;
	}

	public void setSAge(int age) {
		sAge = age;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public void setSocialSecurity(double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public void setSocialSecurityGrowth(double socialSecurityGrowth) {
		this.socialSecurityGrowth = socialSecurityGrowth;
	}

	public void setSpouse(PersonBean spouse) {
		this.spouse = spouse;
	}

	public void setSpouseStartSocial(int spouseStartSocial) {
		this.spouseStartSocial = spouseStartSocial;
	}

	public void setUseSocialSecurity(boolean useSocialSecurity) {
		this.useSocialSecurity = useSocialSecurity;
	}
	
}
