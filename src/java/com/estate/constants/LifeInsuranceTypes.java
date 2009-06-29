package com.estate.constants;

public enum LifeInsuranceTypes {

	W(1, "W", "Whole Life"),
	U(2, "U", "Universal Life"),
	V(3, "V", "Variable"),
	JS(4, "JS", "joint Survivorship"),
	SP(5, "SP", "Single Premium"),
	T(6, "T", "TERM"),
	Ten(7, "10", "10 yr Term"),
	Twenty(8, "20", "20 yr Term"),
	GP(9, "GP", "Group"),
	OT(10, "OT", "Other");
	
	public static LifeInsuranceTypes getType( int id) {
		for( LifeInsuranceTypes t : LifeInsuranceTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	private String code;
	
	private String description;
	LifeInsuranceTypes(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public String code() { return code; }
	
	public String description() { return description; }
	
	public int id() {return id; }
}
