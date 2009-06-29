package com.teag.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class TestBarChart extends ApplicationFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1591250858220950428L;
	

	public TestBarChart(String title) {
		super(title);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		String series1 = "Estate Taxes";
		String series2 = "To Family";
		
		String cat1 = "Estate Taxes";
		String cat2 = "To Family";
		
		dataset.addValue(50000,series1, cat1);
		dataset.addValue(0,series2, cat1);

		dataset.addValue(43000,series1, cat2);
		dataset.addValue(84000,series2, cat2);

		JFreeChart chart = ChartFactory.createBarChart("Bar Chart Demo", // chart title
				"", // domain axis label
				"", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend
				true, // tooltips?
				false // URLs?
				);
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CustomBarRenderer3D cr = new CustomBarRenderer3D();
		cr.setSeriesPaint(0,Color.red);
		cr.setSeriesPaint(1,Color.green);
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setVisible(true);
		
		plot.setRenderer(cr);
		

		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);
	}

	public static void main(String[] args) {
		TestBarChart demo = new TestBarChart("Bar Demo 1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

	}

	static class CustomBarRenderer3D extends BarRenderer3D {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CustomBarRenderer3D() {
			
		}
		
		@Override
		public Paint getItemPaint(int row, int column) {
			Color c = Color.green;
			
			if(row == 0){
				if(column == 0)
					c = Color.red;
				else
					c = Color.green;
			} else {
				if(column == 0)
					c = new Color(128,0,0);
				else
					c = new Color(0,128,0);
				
			}

			return c;
		}
	}

	
	
}
