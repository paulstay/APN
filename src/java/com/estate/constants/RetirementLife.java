package com.estate.constants;

public enum RetirementLife {
	b(1,"b", "Recalc. Both"),
	o(2,"o", "Recalc. Owner"),
	f(3,"f", "Recalc. Beneficiary"),
	n(4,"n", "Neither");
	
	public static RetirementLife getCodeType(String code) {
		for( RetirementLife t : RetirementLife.values()) {
			if( t.code().equalsIgnoreCase(code)) 
				return t;
		}
		return null;
	}
	public static RetirementLife getType( int id) {
		for( RetirementLife t : RetirementLife.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	int id;
	
	String code;
	String description;
	RetirementLife(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	
	public String code() {return code;}
	
	public String description() {return description;}
	
	public int id() { return id; }
}
