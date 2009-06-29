package com.estate.pdf;

import java.text.DecimalFormat;

import com.lowagie.text.Rectangle;

public class OutputRect {
	
	public static void printRect(String text, Rectangle rect) {
		DecimalFormat df = new DecimalFormat("###.##");
		System.out.println(text);
		System.out.println("Top	   : " + df.format(rect.getTop()));
		System.out.println("Bottom : " + df.format(rect.getBottom()));;
		System.out.println("Right  : " + df.format(rect.getRight()));
		System.out.println("Left   : " + df.format(rect.getLeft()));
	}
}
