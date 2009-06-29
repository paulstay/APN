package com.estate.constants;

import java.awt.Color;

public enum TeagColor {

	RED(1, Color.red),
	GREEN(2, Color.green),
	Blue(3,Color.blue),
	Black(3,Color.black);
	
	int id;
	Color color;
	
	TeagColor(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	
	public Color color() {
		return color;
	}
	
}
