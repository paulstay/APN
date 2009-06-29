package com.estate.constants;

public enum VAssetTypes {

	S(1, "S", "Securities"),
	M(2, "M", "Bonds"),
	P(3, "P", "Personal Property"),
	I(4, "I", "Illiquid Assets"),
	B(5, "B", "Business"),
	R(6, "R", "Real Estate");
	
	public static VAssetTypes getCodeType(String code) {
		for( VAssetTypes t : VAssetTypes.values()) {
			if( t.code().equals(code)) 
				return t;
		}
		return null;
	}
	public static VAssetTypes getType( int id) {
		for( VAssetTypes t : VAssetTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	
	private String code;
	private String description;
	VAssetTypes(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	
	public String code() {return code;}
	
	public String description() {return description;}
	
	public int id() { return id; }
}
