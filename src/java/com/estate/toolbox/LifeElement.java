package com.estate.toolbox;

import java.text.DecimalFormat;
import java.util.Scanner;

public class LifeElement {
	int year;
	int age;
	double dividends;
	double netPremium;
	double netAfterTax;
	double cumNet;
	double baseCashValue;
	double cashValueAdds;
	double netCashValue;
	double netDeathBenefit;
	
	String iStr;

	public int getAge() {
		return age;
	}

	public double getBaseCashValue() {
		return baseCashValue;
	}
	
	public double getCashValueAdds() {
		return cashValueAdds;
	}

	public double getCumNet() {
		return cumNet;
	}

	public double getDividends() {
		return dividends;
	}

	double getDouble(String a) {
		DecimalFormat df = new DecimalFormat("###,###,###");
		double x = 0;
		try {
			x = df.parse(a).doubleValue();
		} catch (Exception e) {
			x = 0;
		}
		return x;
	}

	public double getNetAfterTax() {
		return netAfterTax;
	}

	public double getNetCashValue() {
		return netCashValue;
	}

	public double getNetDeathBenefit() {
		return netDeathBenefit;
	}

	public double getNetPremium() {
		return netPremium;
	}

	public int getYear() {
		return year;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setBaseCashValue(double baseCashValue) {
		this.baseCashValue = baseCashValue;
	}

	public void setCashValueAdds(double cashValueAdds) {
		this.cashValueAdds = cashValueAdds;
	}

	public void setCumNet(double cumNet) {
		this.cumNet = cumNet;
	}

	public void setDividends(double dividends) {
		this.dividends = dividends;
	}

	public void setIStr(String str) {
		iStr = str;
		Scanner scan = new Scanner(str);	// Single string for this
		year = scan.nextInt();
		age = scan.nextInt();
		dividends = getDouble(scan.next());
		netPremium = getDouble(scan.next());
		netAfterTax = getDouble(scan.next());
		cumNet = getDouble(scan.next());
		baseCashValue = getDouble(scan.next());
		cashValueAdds = getDouble(scan.next());
		netCashValue = getDouble(scan.next());
		netDeathBenefit = getDouble(scan.next());
	}

	public void setNetAfterTax(double netAfterTax) {
		this.netAfterTax = netAfterTax;
	}

	public void setNetCashValue(double netCashValue) {
		this.netCashValue = netCashValue;
	}

	public void setNetDeathBenefit(double netDeathBenefit) {
		this.netDeathBenefit = netDeathBenefit;
	}

	public void setNetPremium(double netPremium) {
		this.netPremium = netPremium;
	}

	public void setYear(int year) {
		this.year = year;
	}

	
	
	
}
