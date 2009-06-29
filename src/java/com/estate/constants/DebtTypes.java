package com.estate.constants;

public enum DebtTypes {

	H(1, "Home Equity"),
	C(2, "Credit Card"),
	O(3, "Obligation"),
	L(4, "Letter of Credit"),
	P(5, "Personal Gaurantees");
	
	public static DebtTypes getType( int id) {
		for( DebtTypes t : DebtTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	
	private String description;
	DebtTypes(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String description() { return description; }
	
	public int id() {return id; }
}
