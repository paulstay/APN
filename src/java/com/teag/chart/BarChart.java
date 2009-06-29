/*
 * Created on May 16, 2005
 *
 */
package com.teag.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.CategoryLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;

/**
 * @author Paul Stay
 * 
 */
public class BarChart {
	/**
	 * A custom label generator (returns null for one item as a test).
	 */
	static class CustomLabelGenerator implements CategoryLabelGenerator {

		/**
		 * Generates a label for a pie section.
		 * 
		 * @param dataset
		 *            the dataset (<code>null</code> not permitted).
		 * @param key
		 *            the section key (<code>null</code> not permitted).
		 * 
		 * @return the label (possibly <code>null</code>).
		 */
		public String generateLabel(CategoryDataset dataset, int i, int j) {
			return null;
		}

	}

	static class CustomToolTipGenerator implements CategoryToolTipGenerator {
		public String generateToolTip(CategoryDataset dataset, int i, int j) {
			return "";
		}

	}

	CategoryDataset dataSet;
	String title;
	String domainAxisLabel;
	String rangeAxisLabel;
	PlotOrientation orientation = PlotOrientation.VERTICAL;
	Rectangle rect;
	float categoryMargin = .15f;
	float itemMargin = 0;
	float alpha = .6f;
	
	boolean legendOn = true;

	boolean lablesOff = false;

	boolean use2D = false;

	JFreeChart barChart;

	public BarChart() {
	}

	public void generateBarChart() {
		Font font = new Font("serif", Font.PLAIN, 5);
		if (use2D) {
			barChart = ChartFactory.createBarChart(title, domainAxisLabel,
					rangeAxisLabel, dataSet, orientation, legendOn, true, false);
		} else {
			barChart = ChartFactory.createBarChart3D(title, domainAxisLabel,
					rangeAxisLabel, dataSet, orientation, legendOn, true, false);
		}

		CategoryPlot plot = barChart.getCategoryPlot();

		barChart.setBackgroundPaint(Color.white);
		plot.setInsets(new Insets(0, 5, 5, 5));
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
		if (lablesOff) {
			// barRender.setLabelGenerator(new CustomLabelGenerator());
			barRender.setItemLabelsVisible(false);
			domainAxis.setVisible(false);
		}
	}

	public float getAlpha() {
		return alpha;
	}

	public JFreeChart getBarChart() {
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

	/**
	 * @return Returns the categoryMargin.
	 */
	public float getCategoryMargin() {
		return categoryMargin;
	}

	public CategoryDataset getDataSet() {
		return dataSet;
	}

	public String getDomainAxisLabel() {
		return domainAxisLabel;
	}

	/**
	 * @return Returns the itemMargin.
	 */
	public float getItemMargin() {
		return itemMargin;
	}

	public PlotOrientation getOrientation() {
		return orientation;
	}

	public String getRangeAxisLabel() {
		return rangeAxisLabel;
	}

	public Rectangle getRect() {
		return rect;
	}

	public String getTitle() {
		return title;
	}

	public boolean isLablesOff() {
		return lablesOff;
	}

	public boolean isUse2D() {
		return use2D;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public void setBarChart(JFreeChart barChart) {
		this.barChart = barChart;
	}

	/**
	 * @param categoryMargin
	 *            The categoryMargin to set.
	 */
	public void setCategoryMargin(float categoryMargin) {
		this.categoryMargin = categoryMargin;
	}

	public void setSeriesColor(Color c1, Color c2) {
		EstateBarRenderer3D eRend = new EstateBarRenderer3D();
		CategoryPlot plot = barChart.getCategoryPlot();
		eRend.setSeries1(c1);
		eRend.setSeries2(c2);
		plot.setRenderer(eRend);
	}
	
	public void setColor(int series, Color color) {
		CategoryPlot plot = barChart.getCategoryPlot();
		BarRenderer barRender = (BarRenderer) plot.getRenderer();
		barRender.setSeriesPaint(series, color);
	}

	public void setDataSet(CategoryDataset dataSet) {
		this.dataSet = dataSet;
	}

	public void setDomainAxisLabel(String domainAxisLabel) {
		this.domainAxisLabel = domainAxisLabel;
	}

	/**
	 * @param itemMargin
	 *            The itemMargin to set.
	 */
	public void setItemMargin(float itemMargin) {
		this.itemMargin = itemMargin;
	}

	public void setLablesOff(boolean lablesOff) {
		this.lablesOff = lablesOff;
	}

	public void setOrientation(PlotOrientation orientation) {
		this.orientation = orientation;
	}

	public void setRangeAxisLabel(String rangeAxisLabel) {
		this.rangeAxisLabel = rangeAxisLabel;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUse2D(boolean use2D) {
		this.use2D = use2D;
	}

	public boolean isLegendOn() {
		return legendOn;
	}

	public void setLegendOn(boolean legendOn) {
		this.legendOn = legendOn;
	}
	
	
}
