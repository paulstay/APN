package com.estate.constants;

/**
 * 
 * @author Paul Stay
 * Date March 2009
 * Create all of the possible CLAT Trust types
 * 	Term Certain - Specify the number of years available for the CLAT
 *  Life - Specify that the term of the CLAT is based on one or two lives
 *  Shorter - Determin the trust term length based on the shorter of either the term or one or two lives.
 *  Greater - Determin the trust term length based on the greater of either the term of one or two lives.
 *
 */
public enum ClatTrustTypes {
	T(1, "T", "Term Certain"),
	L(2, "L", "Life"),
	S(3, "S", "Shorter of"),
	G(4, "G", "Greater of");
	
	int id;
	String flag;
	String description;

	/**
	 * Contructor for this ENUM, see all of the above types
	 * @param id
	 * @param flag
	 * @param description
	 */
	ClatTrustTypes(int id, String flag, String description){
		this.id = id;
		this.flag = flag;
		this.description = description;
	}
	
	/**
	 * Given the id of a trust type, return the appropriate ClatTrustType Enum object
	 * @param id
	 * @return
	 */
	public static ClatTrustTypes getType( int id) {
		for( ClatTrustTypes t : ClatTrustTypes.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	
	/**
	 * return the id of the ClatTrust Type
	 * @return
	 */
	public int id() {
		return id;
	}
	
	/**
	 * Return the flag, for the current ClatTrust Type
	 * @return
	 */
	public String flag() {
		return flag;
	}
	
	/**
	 * return the description for the current ClatTrust Type
	 * @return
	 */
	public String description() {
		return description;
	}
}
