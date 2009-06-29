package com.estate.constants;

public enum VariableLineItems {

	C(1, "C", "Cash Flow Receipt"),
	D(2, "D", "Cash Flow Disbursement"),
	N(3, "N", "Net Worth"),
	T(4, "T", "Estate Distribution - Taxable"),
	E(5, "E", "Estate Distribution - To Family"),
	X(6, "X", "Estate Distribution - To Charity");
		
	private int id;
	private String code;
	private String description;
	
	VariableLineItems(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public String code() { return code; }
	public String description() { return description;}
	
	public int id() { return id; }
}
