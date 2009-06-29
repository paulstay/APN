package com.estate.constants;

public enum TeagTextFill {
	
	LEFT(1, 0),
	CENTER(2, 1),
	RIGHT(3, 2);
	
	int id;
	int fill;
	
	TeagTextFill(int id, int fill) {
		this.id = id;
		this.fill = fill;
	}
}
