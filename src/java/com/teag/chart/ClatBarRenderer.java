package com.teag.chart;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.category.BarRenderer3D;

public class ClatBarRenderer extends BarRenderer3D  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5380195780296865876L;
	
	Color col0;
	Color col1;
	Color col2;
	Color col3;
	
	@Override
	public Paint getItemPaint(int row, int column) {
		Color c = Color.green;
		if(row==0){
			if(column==0)
				c = col0;
			if(column==1)
				c = col1;
			if(column==2)
				c = col2;
			if(column==3)
				c = col3;
		} else {
			if(column==0)
				c = new Color(col0.getRed()/2, col0.getGreen()/2, col0.getBlue()/2);
			if(column==1)
				c = new Color(col1.getRed()/2, col1.getGreen()/2, col1.getBlue()/2);
			if(column==2)
				c = new Color(col2.getRed()/2, col2.getGreen()/2, col2.getBlue()/2);
			if(column==3)
				c = new Color(col3.getRed()/2, col3.getGreen()/2, col3.getBlue()/2);
		}
		return c;
	}

	public Color getCol0() {
		return col0;
	}

	public void setCol0(Color col0) {
		this.col0 = col0;
	}

	public Color getCol1() {
		return col1;
	}

	public void setCol1(Color col1) {
		this.col1 = col1;
	}

	public Color getCol2() {
		return col2;
	}

	public void setCol2(Color col2) {
		this.col2 = col2;
	}

	public Color getCol3() {
		return col3;
	}

	public void setCol3(Color col3) {
		this.col3 = col3;
	}
}

