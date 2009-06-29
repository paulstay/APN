package com.estate.constants;

public enum BusinessTypes {
	
	C(1,"C","C Corp"),
	S(2, "S", "S Corp"),
	L(3, "L", "Limited Liability Company"),
	P(4, "P", "Partnership"),
	GP(5, "GP", "General Partnership"),
	PR(6, "PR", "Propriatorship"),
	O(7, "O", "Other");
	
	public static BusinessTypes getType( int id) {
		for( BusinessTypes t : BusinessTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	int id;
	String code;
	
	String description;
	BusinessTypes(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
		
	}
	public String code() { return code; }
	public String description() {return description; }
	
	public int id() { return id; }

}
