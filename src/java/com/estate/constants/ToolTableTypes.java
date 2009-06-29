package com.estate.constants;

public enum ToolTableTypes {
	CRT(1, "Charitable Remainder Trust", "CRT_TOOL", "ID"),
	QPRT(2, "Qualified Personal Residence Trust", "QPRT_TOOL", "ID"),
	GRAT(3, "Grantor Retained Annuity Trust", "GRAT_TOOL", "ID"),
	CLAT(4, "Charitable Lead Annuity Trust", "CLAT_TOOL", "ID"),
	FLP(5, "Family Limited Partnership", "FLP_TOOL", "ID"),
	LLC(6, "Limited Liability Coorporation", "LLC_TOOL", "ID"),
	CRUM(7, "Crummy Trust", "CRUM_TOOL", "ID" ),
	MGEN(8, "Dynasty (Multi-Geneartional) Trusts", "MGEN_TOOL", "ID"),
	RPM(9,"Retirement Planning Maximizer", "RPM_TOOL", "ID"),
	TCLAT(10, "Testamentary Charitable Lead Annuity Trust", "TCLAT_TOOL", "ID"),
	IDIT(11, "Intentionaly Defective Irrevocable Trust", "IDIT_TOOL", "ID"),
	LIFE(12, "Life Insurance Trust (Crummy)", "LIFE_TOOL", "ID"),
	AB(13, "AB Trust", "AB_TOOL", "ID"),
	WILL(19, "Simple Will", "WILL_TOOL", "ID"),
	BUY(20, "Buy/Sell", "BUY_TOOL", "ID"),
	FCF(21,"Family Charitable Foundation", "FAMILY_CHARITY_TOOL", "ID"),
	TCLAT2(22,"Testamentary Charitable Lead Annuity Trust II.", "TCLAT2_TOOL", "ID"),
	PANNUITY(23,"Private Annuity", "PRIVATE_ANNUITY_TOOL", "ID"),
	SCIN(24, "Self Canceling Installment Note", "SCIN_TOOL", "ID"),
	LAP(25, "Liquid Asset Protection Plan", "LAP_TOOL", "ID"),
	ERP(26,"Enhanced Retirement Plan", "ERP_TOOL", "ID"),
	CPLAN(26,"Enhanced Retirement Plan", "CP_TOOL", "ID" ),
	SIDIT(27,"Intentioanly Defective Irrevocable Trust (no GRAT)", "SIDIT_TOOL", "ID"),
	SGIFT(28,"Gift to Charity Tool", "SGIFT_TOOL","ID");
	
	public static ToolTableTypes getToolTableType( int id) {
		for( ToolTableTypes t : ToolTableTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	private int id;
	private String description;
	private String toolName;
	
	private String idName;
	
	ToolTableTypes(int id, String description, String toolName, String idName) {
		this.id = id;
		this.description = description;
		this.toolName = toolName;
		this.idName = idName;
	}
	public String description() { return description;}
	public int getId() {
		return id;
	}
	public int id() { return id; }
	
	public String idName() { return idName; }
	
	public String toolName() { return toolName; }
}
