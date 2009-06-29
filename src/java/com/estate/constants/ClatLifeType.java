package com.estate.constants;

/**
 * Clat Life Type
 * @author Paul Stay
 * Date March 2009
 * Specify the two different Clat Life Types
 */
public enum ClatLifeType {
	L(1, "L", "Last to Die"),
	F(2, "F", "First to Die");
	
	int id;
	String flag;
	String description;
	
	/**
	 * Constructor for this enum
	 * @param id
	 * @param flag
	 * @param description
	 */
	ClatLifeType(int id, String flag, String description){
		this.id = id;
		this.flag = flag;
		this.description = description;
	}
	
	/**
	 * Given a ID, return the assoicated enum
	 * @param id
	 * @return
	 */
	public ClatLifeType getType(int id){
		for( ClatLifeType t : ClatLifeType.values()) {
			if( t.id() == id) 
				return t;
		}
		return null;
	}
	
	/**
	 * return the id of the current ClatLifeType
	 * @return
	 */
	public int id() {
		return id;
	}
	
	/**
	 * Return the flag for the current ClatLifeType
	 * @return
	 */
	public String flag() {
		return flag;
	}
	
	/**
	 * Return the current description for this ClatLifeType
	 * @return
	 */
	public String description() {
		return description;
	}
}
