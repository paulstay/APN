package com.estate.constants;

public enum Ownership {
	CP(1, "CP", "Community Property"),
	HSP(2, "HSP", "Husband's Separate Property"),
	WSP(3, "WSP", "Wife's Separate Property"),
	JP(4, "JP", "Joint Property"),
	IN(5, "IN", "Individual Property");
	
	public static Ownership getType( int id) {
		for( Ownership t : Ownership.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private final int id;
	private final String code;
	
	private final String description;
	Ownership(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public String code() { return code; }
	
	public String description() { return description;}
	
	public int id() { return id; }
}
