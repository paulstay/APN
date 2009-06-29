package com.estate.constants;

public enum AssetDatabase {
	CASH(1, "CASH", "CASH_ID", "VALUE", "DESCRIPTION"),
	DEBT(2, "DEBT", "DEBT_ID", "VALUE", "DESCRIPTION"),
	BOND (3, "BONDS", "BOND_ID", "VALUE", "DESCRIPTION"),
	SECURITIES(4,"SECURITIES", "SECURITIES_ID", "VALUE", "DESCRIPTION"),
	RETIREMENT(5, "RETIREMENT", "RETIREMENT_ID", "VALUE", "DESCRIPTION"),
	ILLIQUID(6,"ILLIQUID", "ILLIQUID_ID", "VALUE", "DESCRIPTION"),
	REALESTATE(7, "REAL_ESTATE", "REAL_ESTATE_ID", "VALUE", "DESCRIPTION"),
	BUSINESS(8, "BUSINESS", "BUSINESS_ID", "VALUE", "DESCRIPTION"),
	PROPERTY(9, "PROPERTY", "PROPERTY_ID", "VALUE", "DESCRIPTION"),
	LIFEINSURANCE(10,"LIFE_INSURANCE", "LIFE_ID", "FACE_VALUE", "DESCRIPTION"),
	NOTES(11,"NOTES", "ID", "VALUE", "DESCRIPTION"),
	NOTEPAYABLE(12, "NOTE_PAYABLE", "ID", "LOAN_AMOUNT", "DESCRIPTION"),
	GIFTING(13,"GIFTING", "GIFT_ID", "TOTAL_GIFT", "DESCRIPTION")
	;
	
	public static AssetDatabase getAssetDatabaseType( int id) {
		for( AssetDatabase t : AssetDatabase.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private final int id;
	private final String tableName;
	private final String tableId;
	private final String valueId;
	
	private final String description;
	
	AssetDatabase(int id, String tableName, String tableId, String valueId, String description) {
		this.id = id;
		this.tableName = tableName;
		this.tableId = tableId;
		this.valueId = valueId;
		this.description = description;
	}
	public String description() {return description;}
	public int id() { return id; }
	public String tableId() { return tableId; }
	public String tableName() { return tableName; }
	
	public String valueId() { return valueId; }
}
/*
public enum AssetDatabase {
	BOND (1, "BONDS", "BOND_ID"),
	BUSINESS(2, "BUSINESS", "BUSINESS_ID"),
	CASH(3, "CASH", "CASH_ID"),
	DEBT(4, "DEBT", "DEBT_ID"),
	ILLIQUID(5,"ILLIQUID", "ILLIQUID_ID"),
	RETIREMENT(6, "RETIREMENT", "RETIREMENT_ID"),
	SECURITIES(7,"SECURITIES", "SECURITIES_ID"),
	REALESTATE(8, "REAL_ESTATE", "REAL_ESTATE_ID"),
	PROPERTY(9, "PROPERTY", "PROPERTY_ID"),
	LIFEINSURANCE(10,"LIFE_INSURANCE", "LIFE_ID"),
	GIFTING(11,"GIFTING", "GIFT_ID"),
	FLPGP(12, "FLP_GP_TABLE", "ID"),
	FLPLP(13, "FLP_LP_TABLE", "ID"),
	LLCGP(14, "LLC_GP_TABLE", "ID"),
	LLCLP(15, "LLC_LP_TABLE", "ID"),
	NOTES(16,"NOTES", "ID"),
	NOTEPAYABLE(17, "NOTE_PAYABLE", "ID"),
	VASSET(18, "VASSET", "ID")
	;

	private int id;
	private String tableName;
	private String idName;
	
	AssetDatabase(int id, String tableName, String idName) {
		this.id = id;
		this.tableName = tableName;
		this.idName = idName;
	}
	
	public int id() {return id;}
	public String tableName() {return tableName; }
	public String idName() {return idName;}
	
	public static AssetDatabase getAssetType( int id) {
		for( AssetDatabase t : AssetDatabase.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
}
*/