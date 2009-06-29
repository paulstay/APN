package com.teag.chart;

/**
 * @author stay
 * Created on May 23, 2005
 *
 */

import java.awt.Color;
import java.awt.Insets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.CategoryLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;

public class AreaChart {
	/**
     * A custom label generator (returns null for one item as a test).
     */
    static class CustomLabelGenerator implements CategoryLabelGenerator {
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
	
	PlotOrientation orientation  = PlotOrientation.VERTICAL;
	
	Rectangle rect;

	JFreeChart areaChart;
	
	
	public void generateAreaChart() {
		areaChart = ChartFactory.createAreaChart(title, domainAxisLabel, rangeAxisLabel,
				dataSet, orientation, true, true, false);
		
		CategoryPlot plot = areaChart.getCategoryPlot();
		
		areaChart.setBackgroundPaint(Color.white);
        plot.setInsets(new Insets(0,5,5,5));
        plot.setForegroundAlpha(1.0f);
        plot.setOutlinePaint(Color.white);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelsVisible(false);
	}

	public JFreeChart getBarChart() {
		return areaChart;
	}
    
	public Image getBarChartImage() {
		Rectangle page = rect;
        Image img = null;
        try
		{
        	img = Image.getInstance(areaChart.createBufferedImage((int)page.getWidth(), (int)page.getHeight()), null);
		}
        catch( Exception e)
		{
        	System.out.println(e.getMessage());
		}
        return img;
	}
	public CategoryDataset getDataSet() {
		return dataSet;
	}
	public String getDomainAxisLabel() {
		return domainAxisLabel;
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
	
	public void setAreaChart(JFreeChart areaChart) {
		this.areaChart = areaChart;
	}
	
	public void setDataSet(CategoryDataset dataSet) {
		this.dataSet = dataSet;
	}
	
	public void setDomainAxisLabel(String domainAxisLabel) {
		this.domainAxisLabel = domainAxisLabel;
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
}
