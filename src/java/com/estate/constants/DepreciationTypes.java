package com.estate.constants;

public enum DepreciationTypes {
	
	S(1, "S", "Straight Line Method"),
	Y(2, "Y", "Sum of the Years Digits");
	
	public static DepreciationTypes getCodeType(String code) {
		for( DepreciationTypes t : DepreciationTypes.values()) {
			if( t.code().equals(code)) 
				return t;
		}
		return null;
	}
	public static DepreciationTypes getType( int id) {
		for( DepreciationTypes t : DepreciationTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	
	private String code;
	
	private String description;
	
	DepreciationTypes(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	
	public String code() {
		return this.code;
	}
	
	public String description() {
		return this.description;
	}
	
	public int id() {
		return this.id;
	}
}
