package com.estate.constants;

public enum Title {
	RLT(1, "RLT", "Revocable Living Trust"),
	IT(2, "IT", "Irrevocable Trust"),
	JT(3, "JT", "Joint Tenancy"),
	TC(4, "TC", "Tenancy in Common"),
	HCP(5, "HCP", "Husbands Community Property"),
	WCP(6, "WCP", "Wife's Community Property"),
	HSP(7, "HSP", "Husband's Separate Property"),
	WSP(8, "WSP", "Wife's Separate Property"),
	H(9, "H", "Husband's Name"),
	W(10, "W", "Wife's Name"),
	NS(11, "NS", "Not Sure"),
	I(12, "I", "Individual"),
	B(13,"", "Blank"),
	SCORP(14,"SCorp","S Corp");
	
	public static Title getType( int id) {
		for( Title t : Title.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private final int id;
	private final String code;
	
	private final String description;
	Title(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public String code() {return code; }
	
	public String description() { return description;}
	
	public int id() {return id;}
}
