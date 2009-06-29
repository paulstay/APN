package com.estate.constants;

public enum CalculationTypes {

	Normal(1,"Normal"),
	Remainder(2,"Remainder"),
	Zero(3,"Zero Out");
	

	private int id;
	
	private String cType;
	CalculationTypes(int id, String cType) {
		this.id = id;
		this.cType = cType;
	}
	
	public String cType() {return cType;}
	public int id() {return id;}
	
}
