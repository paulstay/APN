/*
 * Created on Jun 3, 2005
 *
 */
package com.teag.reports;

import java.awt.Color;

import org.jfree.data.category.DefaultCategoryDataset;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.EstatePlan.EstatePlanTable;
import com.teag.analysis.CashFlowTable;
import com.teag.chart.BarChart;
public class CashFlowGraph extends Page {
	
	CashFlowTable cashFlows;
	EstatePlanTable estatePlanTable;
	
	boolean scenario1Only = false;
	
	/**
	 * @param document
	 * @param writer
	 */
	public CashFlowGraph(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon=1;
		
	}
	
	public void draw() {
	    if( isScenario1Only()) {
            page1();
            newPage();
            page2();
            //newPage();
        } else {
            page1();
            newPage();
            page2();
            newPage();
    		//page3();
    		//newPage();
    		page4();
        }
	}
	/**
     * @return Returns the scenario1Only.
     */
    public boolean isScenario1Only() {
        return scenario1Only;
    }

	private void page1() {
		drawBorder(pageIcon, "");
		drawHeader(client, "Estate Distribution Summary - SCENARIO 1");
		double taxes[] = cashFlows.getToTax();
		double family[] = cashFlows.getToFamily();
		double charity[] = cashFlows.getToCharity();
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Total Taxes at 2nd Death";
		String series2 = "Net To Family";
		String series3 = "Total to Charity";

		for(int i = 0; i < cashFlows.getFinalDeath(); i++){
			String cat = (i+1) + "";
			dataSet.addValue(taxes[i], series1, cat);
			dataSet.addValue(family[i], series2, cat);
			dataSet.addValue(charity[i], series3, cat);
		}

		BarChart barChart = new BarChart();
		
		barChart.setTitle("Estimated Estate Distribution Years 1 - " + cashFlows.getFinalDeath());
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctFull);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		barChart.setColor(0, Color.red);
		barChart.setColor(1, Color.green);
		barChart.setColor(2, Color.blue);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		
	}
	
	private void page2() {
		double taxes[] = cashFlows.getToTax();
		double family[] = cashFlows.getToFamily();
		double charity[] = cashFlows.getToCharity();
		drawBorder(pageIcon, "");
		drawHeader(client, "Estate Distribution Comparison");
		DefaultCategoryDataset dataSet2 = new DefaultCategoryDataset();
		String s1 = "Taxes";
		String s2 = "Family";
		String s3 = "Charity";
		
		int finalDeath = cashFlows.getFinalDeath();
		dataSet2.addValue(taxes[finalDeath-1], s1, s1);
		dataSet2.addValue(family[finalDeath-1], s2, s2);
		dataSet2.addValue(charity[finalDeath-1], s3, s3);
		
		
		BarChart b2 = new BarChart();
		b2.setTitle("Estimated Estate Distribution - Year " + cashFlows.getFinalDeath() + "(Scenario 1)");
		b2.setDataSet(dataSet2);
		b2.setItemMargin(.0f);
		b2.setCategoryMargin(.05f);
		Rectangle b2Rect = new Rectangle(prctFull);
		b2Rect.setRight(b2Rect.getRight()- _1_2TH);
		b2Rect.setLeft(b2Rect.getLeft() + _1_2TH);
		b2Rect.setTop(b2Rect.getTop() - _1_2TH);
		b2.setRect(b2Rect);
		b2.generateBarChart();
		b2.setColor(0, Color.red);
		b2.setColor(1, Color.green);
		b2.setColor(2, Color.blue);
		Image img2 = b2.getBarChartImage();
		drawDiagram(img2, b2Rect, 0, 72);
	}

    /*
	private void page3() {
		drawBorder(pageIcon, "");
		drawHeader(client, "Estate Distribution Comparison");
		double taxes[] = estatePlanTable.getChartTax();
		double family[] = estatePlanTable.getChartFamily();
		double charity[] = estatePlanTable.getChartCharity();
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Taxes";
		String series2 = "Family";
		String series3 = "Charity";

		for(int i = 0; i < cashFlows.getFinalDeath(); i++){
			String cat = (i+1) + "";
			dataSet.addValue(taxes[i], series1, cat);
			dataSet.addValue(family[i], series2, cat);
			dataSet.addValue(charity[i], series3, cat);
		}

		BarChart barChart = new BarChart();
		
		barChart.setTitle("Estimated Estate Distribution Years 1 - " + cashFlows.getFinalDeath() + " (Scenario 2)");
		
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctFull);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		barChart.setColor(0, Color.red);
		barChart.setColor(1, Color.green);
		barChart.setColor(2, Color.blue);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		
	}
	*/
	private void page4() {
		drawBorder(pageIcon, "");
		drawHeader(client, "Estate Distribution Comparison");
		double et[] = estatePlanTable.getChartTax();
		double ef[] = estatePlanTable.getChartFamily();
		double ec[] = estatePlanTable.getChartCharity();
		double taxes[] = cashFlows.getToTax();
		double family[] = cashFlows.getToFamily();
		double charity[] = cashFlows.getToCharity();
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Taxes";
		String series2 = "Family";
		String series3 = "Charity";
		String cat1 = "Before (Sc1)";
		String cat2 = "After (Sc2)";
		
		int fd = cashFlows.getFinalDeath()-1;
		
		dataSet.addValue(taxes[fd], series1, cat1);
		dataSet.addValue(family[fd], series2, cat1);
		dataSet.addValue(charity[fd], series3, cat1);

		dataSet.addValue(et[fd], series1, cat2);
		dataSet.addValue(ef[fd], series2, cat2);
		dataSet.addValue(ec[fd], series3, cat2);

		
		BarChart barChart = new BarChart();
		
		barChart.setTitle("Estimated Estate Distribution Year " + cashFlows.getFinalDeath() + 
				" (Scenario 1 vs Scenario 2)");
		
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctFull);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		//barChart.setItemMargin(.1f);
		barChart.generateBarChart();
		barChart.setColor(0, Color.red);
		barChart.setColor(1, Color.green);
		barChart.setColor(2, Color.blue);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
	}
    /**
	 * @param cashFlows The cashFlows to set.
	 */
	public void setCashFlows(CashFlowTable cashFlows) {
		this.cashFlows = cashFlows;
	}
	/**
	 * @param estatePlanTable The estatePlanTable to set.
	 */
	public void setEstatePlanTable(EstatePlanTable estatePlanTable) {
		this.estatePlanTable = estatePlanTable;
	}
	/**
     * @param scenario1Only The scenario1Only to set.
     */
    public void setScenario1Only(boolean scenario1Only) {
        this.scenario1Only = scenario1Only;
    }
}
