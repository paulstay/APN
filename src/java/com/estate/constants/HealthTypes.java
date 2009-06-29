package com.estate.constants;

public enum HealthTypes {
	
	NI(1, "Non Insured"),
	ST(2, "Standard"),
	PR(3, "Prefered"),
	SP(4, "Super Prefered"),
	NS(5, "Non Standard");
	
	public static HealthTypes getType( int id) {
		for( HealthTypes t : HealthTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private final int id;
	
	private final String description;
	
	HealthTypes(int id, String description) {
		this.id = id;
		this.description = description;
	}
	public String description() { return description;}

	public int id() { return id; }
}
