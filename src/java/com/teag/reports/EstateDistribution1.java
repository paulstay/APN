/*
 * Created on May 6, 2005
 *
 */
package com.teag.reports;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 *
 */
public class EstateDistribution1 extends Page {

	private static JFreeChart createChart(CategoryDataset dataset) 
	{
	//		 create the chart...
			JFreeChart chart = ChartFactory.createBarChart(
			"Estimated Estate Distribution - year 25", // chart title
			"", // domain axis label
			"", // range axis label
			dataset, // data
			PlotOrientation.VERTICAL, // orientation
			true, // include legend
			true, // tooltips?
			false // URLs?
			);
			
	//		 NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
	//		 set the background color for the chart...
			chart.setBackgroundPaint(Color.white);
			
	//		 get a reference to the plot for further customisation...
			Color green = new Color(0,176,0);
			Color red = new Color(192,0,0);
			Color blue = new Color(0,0,192);

			CategoryPlot plot = chart.getCategoryPlot();
			plot.setBackgroundPaint(Color.white);
			plot.setDomainGridlinePaint(Color.red);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinePaint(Color.black);
			
			plot.setRenderer(new BarRenderer3D());
	//		 set the range axis to display integers only...
			final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	//		 disable bar outlines...
			BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
			renderer.setDrawBarOutline(false);
	
			renderer.setSeriesPaint(0, red);
			renderer.setSeriesPaint(1, green);
			renderer.setSeriesPaint(2, blue);
			CategoryAxis domainAxis = plot.getDomainAxis();

			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
			//			domainAxis.setCategoryLabelPositions(
//			CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
			//);
	//		 OPTIONAL CUSTOMISATION COMPLETED.
			return chart;
	}

	/**
	 * @param document
	 * @param writer
	 */
	public EstateDistribution1(Document document, PdfWriter writer) {
		super(document, writer);
	}
	
	/**
	* Returns a sample dataset.
	*
	* @return The dataset.
	*/
	private CategoryDataset createDataset() {
	//	 row keys...
		String series1 = "Taxes";
		String series2 = "Family";
		String series3 = "Charity";
	//	 column keys...
		String category1 = "Before (Sc 1)";
		String category2 = "After (Sc 2)";
	//	 create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(28117941, series1, category1);
		dataset.addValue(0, series1, category2);
		dataset.addValue(25273004, series2, category1);
		dataset.addValue(61947405, series2, category2);
		dataset.addValue(0, series3, category1);
		dataset.addValue(9928231, series3, category2);
		return dataset;
	}
	
	public void draw()
	{
		
		page1();
		
		
	}
	private void page1()
	{
		drawBorder();
		drawHeader(client, "Estate Distribution Comparison");
		CategoryDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
        
		Rectangle page = new Rectangle(prctLeft);
		
		try
		{
        	Image img = Image.getInstance(chart.createBufferedImage((int)page.getWidth(), (int)page.getHeight()), null);
        	drawDiagram(img, prctLeft, 0, 72);
		}
        catch( Exception e)
		{
        	System.out.println(e.getMessage());
		}		

		
	}
}
