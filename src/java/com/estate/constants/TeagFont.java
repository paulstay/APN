package com.estate.constants;

import java.awt.Color;
import java.awt.Font;

public enum TeagFont {

	BoldRed(Color.red, Font.BOLD),
	BoldGreen( Color.green, Font.BOLD),
	BoldBlue(Color.blue, Font.BOLD),
	BoldBlack(Color.black, Font.BOLD),
	NormRed(Color.red, Font.PLAIN),
	NormGreen(Color.green, Font.PLAIN),
	NormBlue(Color.blue, Font.PLAIN),
	NormBlack(Color.black, Font.PLAIN),
	ItalicRed(Color.red, Font.ITALIC),
	ItalicGreen(Color.green, Font.ITALIC),
	ItalicBlue(Color.blue, Font.ITALIC),
	ItalicBlack(Color.black, Font.ITALIC);

	Color color;
	int weight;
	
	TeagFont(Color c, int weight) {
		this.color = c;
		this.weight = weight;
	}
	public Color color() {return color;}
	
	public int weight() {return weight;}
}
