package com.teag.chart;

/**
 * Author Paul Stay
 * Date November 2008
 * Create a layerd bar chart, this will be used in the CRT and the RPM 
 * Graphics where it has been an issue with bleeding colors.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.SortOrder;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import java.awt.Insets;


public class LayeredBarChart {

	CategoryDataset dataSet;
	String title;
	String domainAxisLabel;
	String rangeAxisLabel;
	PlotOrientation orientation = PlotOrientation.VERTICAL;
	Rectangle rect;
	float categoryMargin = .15f;
	float itemMargin = 0;
	boolean legendOn = true;
	boolean lablesOff = false;
	boolean use2D = false;
	float alpha = 1.0f;
	Color c1, c2, c3, c4;

	JFreeChart barChart;

	public JFreeChart generateLayeredChart() {
		Font font = new Font("serif", Font.PLAIN, 5);

		barChart = ChartFactory
					.createBarChart(title, domainAxisLabel, rangeAxisLabel,
							dataSet, orientation, legendOn, true, false);

		CategoryPlot plot = barChart.getCategoryPlot();

		barChart.setBackgroundPaint(Color.white);
		plot.setInsets(new Insets(0, 0, 0, 0));
		plot.setForegroundAlpha(alpha);
		plot.setOutlinePaint(Color.white);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/6.0));
		domainAxis.setLabelFont(font);
		domainAxis.setCategoryMargin(categoryMargin);
		BarRenderer barRender = (BarRenderer) plot.getRenderer();
		barRender.setItemMargin(itemMargin);
		// here is where we get the layered approach!
		LayeredBarRenderer renderer = new LayeredBarRenderer();
		renderer.setDrawBarOutline(false);
		plot.setRenderer(renderer);

		plot.setRowRenderingOrder(SortOrder.DESCENDING);
		
		// For some reason JFree Chart requires that we establish a gradient paint here
		// Need to investigate other paint methods.....
		GradientPaint gp0 = new GradientPaint(0f, 0f, c1, 0f, 0f, c2);
		GradientPaint gp1 = new GradientPaint(0f, 0f, c3, 0f, 0f, c4);

		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		if (lablesOff) {
			// barRender.setLabelGenerator(new CustomLabelGenerator());
			renderer.setItemLabelsVisible(false);
			domainAxis.setVisible(false);
		}
		return barChart;
	}
	
	public Image getBarChartImage() {
		Rectangle page = rect;
		Image img = null;
		try {
			img = Image.getInstance(barChart.createBufferedImage((int) page
					.getWidth(), (int) page.getHeight()), null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return img;
	}

	public CategoryDataset getDataSet() {
		return dataSet;
	}

	public void setDataSet(CategoryDataset dataSet) {
		this.dataSet = dataSet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDomainAxisLabel() {
		return domainAxisLabel;
	}

	public void setDomainAxisLabel(String domainAxisLabel) {
		this.domainAxisLabel = domainAxisLabel;
	}

	public String getRangeAxisLabel() {
		return rangeAxisLabel;
	}

	public void setRangeAxisLabel(String rangeAxisLabel) {
		this.rangeAxisLabel = rangeAxisLabel;
	}

	public PlotOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(PlotOrientation orientation) {
		this.orientation = orientation;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public float getCategoryMargin() {
		return categoryMargin;
	}

	public void setCategoryMargin(float categoryMargin) {
		this.categoryMargin = categoryMargin;
	}

	public float getItemMargin() {
		return itemMargin;
	}

	public void setItemMargin(float itemMargin) {
		this.itemMargin = itemMargin;
	}

	public boolean isLegendOn() {
		return legendOn;
	}

	public void setLegendOn(boolean legendOn) {
		this.legendOn = legendOn;
	}

	public boolean isLablesOff() {
		return lablesOff;
	}

	public void setLablesOff(boolean lablesOff) {
		this.lablesOff = lablesOff;
	}

	public boolean isUse2D() {
		return use2D;
	}

	public void setUse2D(boolean use2D) {
		this.use2D = use2D;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public Color getC1() {
		return c1;
	}

	public void setC1(Color c1) {
		this.c1 = c1;
	}

	public Color getC2() {
		return c2;
	}

	public void setC2(Color c2) {
		this.c2 = c2;
	}

	public Color getC3() {
		return c3;
	}

	public void setC3(Color c3) {
		this.c3 = c3;
	}

	public Color getC4() {
		return c4;
	}

	public void setC4(Color c4) {
		this.c4 = c4;
	}

	public JFreeChart getBarChart() {
		return barChart;
	}

	public void setBarChart(JFreeChart barChart) {
		this.barChart = barChart;
	}

}
