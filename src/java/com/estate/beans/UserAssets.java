package com.estate.beans;

public class UserAssets {

	int id;
	String description;
	double fmv;
	double basis;
	double growth;
	double income;

	public double getBasis() {
		return basis;
	}

	public String getDescription() {
		return description;
	}


	public double getFmv() {
		return fmv;
	}

	public double getGrowth() {
		return growth;
	}

	public int getId() {
		return id;
	}

	public double getIncome() {
		return income;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIncome(double income) {
		this.income = income;
	}
}
