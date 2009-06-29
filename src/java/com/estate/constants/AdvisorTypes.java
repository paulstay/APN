package com.estate.constants;

public enum AdvisorTypes {

	A(1, "Attorney"),
	B(2, "Banker"),
	C(3, "CPA"),
	I(4, "Investment Advisor"),
	T(5, "Trustee"),
	W(6, "Wealth Preservation Strategist"),
	L(7, "Insurance Advisor" );
	
	
	public static AdvisorTypes getType( int id) {
		for( AdvisorTypes t : AdvisorTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private final int id;
	
	private final String description;
	
	AdvisorTypes(int id, String description) {
		this.id = id;
		this.description = description;
	}
	public String description() { return description;}

	public int id() { return id; }
}

