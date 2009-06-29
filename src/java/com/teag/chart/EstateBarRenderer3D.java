package com.teag.chart;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.category.BarRenderer3D;

public class EstateBarRenderer3D extends BarRenderer3D {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7274729112363064169L;

	Color series1 = Color.red;
	Color series2 = Color.green;

	public EstateBarRenderer3D() {
		
	}
	
	@Override
	public Paint getItemPaint(int row, int column) {
		Color c = Color.green;
		
		if(row == 0){
			if(column == 0)
				c = series1;
			else
				c = series2;
		} else {
			if(column == 0)
				c = new Color(series1.getRed()/2,series1.getGreen()/2, series1.getBlue()/2);				
			else
				c = new Color(series2.getRed()/2,series2.getGreen()/2, series2.getBlue()/2);
		}
		return c;
	}

	public Color getSeries1() {
		return series1;
	}

	public void setSeries1(Color series1) {
		this.series1 = series1;
	}

	public Color getSeries2() {
		return series2;
	}

	public void setSeries2(Color series2) {
		this.series2 = series2;
	}
	
	
}
