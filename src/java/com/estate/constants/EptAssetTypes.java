
package com.estate.constants;

/**
 * @author Paul Stay
 *
 */
public enum EptAssetTypes {
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
	NOTEPAYABLE(17, "NOTE_PAYABLE", "ID");
	//VASSET(18,"VASSET", "ID");
	
	public static EptAssetTypes getEptAssetType( int id) {
		for( EptAssetTypes t : EptAssetTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private final int id;
	private final String tableName;
	
	private final String tableId;
	
	EptAssetTypes(int id, String tableName, String tableId) {
		this.id = id;
		this.tableName = tableName;
		this.tableId = tableId;
	}
	public int id() { return id; }
	public String tableId() { return tableId; }
	
	public String tableName() { return tableName; }
}
