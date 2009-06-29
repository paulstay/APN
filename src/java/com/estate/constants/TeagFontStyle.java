package com.estate.constants;

import java.awt.Font;

public enum TeagFontStyle {
	
	PLAIN(1, Font.PLAIN),
	BOLD(2, Font.BOLD),
	ITALIC(3, Font.ITALIC);
	
	int id;
	int style;
	
	TeagFontStyle(int id, int style) {
		this.id = id;
		this.style = style;
	}
	
	public int style() {
		return style;
	}
}
