package com.teag.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.SortOrder;


public class TestLayeredChart extends ApplicationFrame{

	private static final long serialVersionUID = 4613306548061811726L;

	public TestLayeredChart(String arg0) {
		super(arg0);
		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new Dimension(500,200));
		setContentPane(chartPanel);
	}

	public static JPanel createDemoPanel(){
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}
	
	public static CategoryDataset createDataset() {
		String series1 = "first";
		String series2 = "second";
		String series3 = "third";
		
		String cat1 = "C1";
		String cat2 = "C2";
		String cat3 = "C3";
		String cat4 = "C4";
		String cat5 = "C5";
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		dataset.addValue(1.0, series1, cat1);
		dataset.addValue(2.0, series1, cat2);
		dataset.addValue(3.0, series1, cat3);
		dataset.addValue(4.0, series1, cat4);
		dataset.addValue(5.0, series1, cat5);

		dataset.addValue(6.0, series2, cat1);
		dataset.addValue(7.0, series2, cat2);
		dataset.addValue(8.0, series2, cat3);
		dataset.addValue(9.0, series2, cat4);
		dataset.addValue(10.0, series2, cat5);

		dataset.addValue(8.0, series3, cat1);
		dataset.addValue(3.0, series3, cat2);
		dataset.addValue(6.0, series3, cat3);
		dataset.addValue(2.0, series3, cat4);
		dataset.addValue(9.0, series3, cat5);

		return dataset;
	}

	public static JFreeChart createChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart("Layered", "Category", "Value", dataset, PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.white);
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		LayeredBarRenderer renderer = new LayeredBarRenderer();
		renderer.setDrawBarOutline(false);
		plot.setRenderer(renderer);
		
		plot.setRowRenderingOrder(SortOrder.DESCENDING);
		
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,0.0f, new Color(0,0,64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, new Color(64,0,0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0,64,0));

		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);
		return chart;
	}

	public static void main(String args[]){
		TestLayeredChart demo = new TestLayeredChart("test");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}
