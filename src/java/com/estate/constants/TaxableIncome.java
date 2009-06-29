package com.estate.constants;

public enum TaxableIncome {
	
	O(1, "Ordinary Income"),
	D(2, "Dividend Income Only"),
	B(3, "Both"),
	N(4, "Non Taxable");
	
	public static TaxableIncome getType( int id) {
		for( TaxableIncome t : TaxableIncome.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	
	private String description;
	TaxableIncome(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String description() { return description; }
	
	public int id() {return id; }
}
