package com.estate.constants;

public enum NoteReceivableTypes {
	I(1,"I", "Interest Only w/Balloon"),
	A(2, "A", "Amoratize"),
	P(3, "P", "Principal Only"),
	S(3, "S", "Single Payment");
	
	public static NoteReceivableTypes getCodeType(String code) {
		for( NoteReceivableTypes t : NoteReceivableTypes.values()) {
			if( t.code().equals(code)) 
				return t;
		}
		return null;
	}
	public static NotePayableTypes getType( int id) {
		for( NotePayableTypes t : NotePayableTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	
	private String code;
	private String description;
	NoteReceivableTypes(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	
	public String code() {return code;}
	
	public String description() {return description; }
	
	public int id() { return id; }
}
