/**
 * 
 */
package com.estate.constants;

/**
 * @author Paul Stay
 *
 */
public enum ResidenceTypes {
	
	R("P","Primary"),
	S("S", "Secondary"),
	V("V", "Vacation"),
	B("B", "Business");
	
	private final String id;
	private final String description;
	
	ResidenceTypes(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String description() { return description;}
	public String id() { return id; }
}
