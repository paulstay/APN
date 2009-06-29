package com.estate.constants;

public enum PremiumFreq {
	
	M(1, "12", "Monthly"),
	Q(2, "4", "Quarterly"),
	S(3, "2", "Semi Annual"),
	A(4, "1", "Annual");
	
	public static PremiumFreq getType( int id) {
		for( PremiumFreq t : PremiumFreq.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	private String code;
	
	private String description;
	PremiumFreq(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public String code() { return code;}
	
	public String description() { return description;}
	
	public int id() { return id;}
}
