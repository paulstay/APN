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

/**
 * @author Paul Stay
 *
 */
public class FinalGraph extends Page {
	CashFlowTable cashFlows;
	EstatePlanTable estatePlanTable;

	/**
	 * @param document
	 * @param writer
	 */
	public FinalGraph(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon=2;
	}
	
	public void draw() {
		page1();
		newPage();
		page2();
		newPage();
		page3();
	}
	
	private void page1() {
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
	
	private void page2() {
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
		barChart.generateBarChart();
		barChart.setColor(0, Color.red);
		barChart.setColor(1, Color.green);
		barChart.setColor(2, Color.blue);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
	}
	
	private void page3() {
		drawBorder(pageIcon, "");
		drawHeader(client, "Estate Distribution Comparison");
		Color beforeC = new Color(0xef9c00);
		Color afterC = new Color(0xb57600);

		double ef[] = estatePlanTable.getChartFamily();
		double ec[] = estatePlanTable.getChartCharity();
		double es[] = estatePlanTable.getChartTax();
		double ee[] = estatePlanTable.getChartEstate();
		
		double family[] = cashFlows.getToFamily();
		double charity[] = cashFlows.getToCharity();
		double split[] = cashFlows.getToTax();
		double estate[] = cashFlows.getToEstate();

		int fd = cashFlows.getFinalDeath() - 1;
		
		DefaultCategoryDataset d1 = new DefaultCategoryDataset();
		d1.addValue(estate[fd]*1000, "Before", "Taxable Estate");
		d1.addValue(ee[fd], "After", "Taxable Estate");
		
		BarChart b1 = new BarChart();
		b1.setTitle("Taxable Estate");
		b1.setDataSet(d1);
		Rectangle r1 = new Rectangle(prctULeft);
		r1.setRight(r1.getRight() + _1_2TH);
		b1.setRect(r1);
		b1.setItemMargin(.25f);
		b1.generateBarChart();
		b1.setColor(0, beforeC);
		b1.setColor(1, afterC);
		Image img1 = b1.getBarChartImage();
		drawDiagram(img1, r1, 0, 72);
		
		DefaultCategoryDataset d2 = new DefaultCategoryDataset();
		d2.addValue(split[fd], "Before", "Taxes");

		if(es[fd] < 0)
			d2.addValue(0, "After", "Taxes");
		else
			d2.addValue(es[fd], "After", "Taxes");
		
		BarChart b2 = new BarChart();
		b2.setTitle("To IRS");
		b2.setDataSet(d2);
		Rectangle r2 = new Rectangle(prctURight);
		r2.setRight(r2.getRight() + _1_2TH);
		b2.setRect(r2);
		b2.setItemMargin(.25f);
		b2.generateBarChart();
		b2.setColor(0, new Color(0xff0000));
		b2.setColor(1, new Color(0x880000));
		Image img2 = b2.getBarChartImage();
		drawDiagram(img2, r2, 0, 72);
		
		DefaultCategoryDataset d3 = new DefaultCategoryDataset();
		d3.addValue(family[fd], "Before", "To Family");
		d3.addValue(ef[fd], "After", "To Family");
		
		BarChart b3 = new BarChart();
		b3.setTitle("To Family in Year " + cashFlows.getFinalDeath());
		b3.setDataSet(d3);
		Rectangle r3 = new Rectangle(prctLLeft);
		r3.setRight(r3.getRight() + _1_2TH);
		b3.setRect(r3);
		b3.setItemMargin(.25f);
		b3.generateBarChart();
		b3.setColor(0, new Color(0x00ff00));
		b3.setColor(1, new Color(0x008800));
		Image img3 = b3.getBarChartImage();
		drawDiagram(img3, r3, 0, 72);
		
		DefaultCategoryDataset d4 = new DefaultCategoryDataset();
		d4.addValue(charity[fd], "Before", "To Charity");
		d4.addValue(ec[fd], "After", "To Charity");
		
		BarChart b4 = new BarChart();
		b4.setTitle("Amount to Charity");
		b4.setDataSet(d4);
		Rectangle r4 = new Rectangle(prctLRight);
		r4.setRight(r4.getRight() + _1_2TH);
		b4.setRect(r4);
		b4.setItemMargin(.25f);
		b4.generateBarChart();
		b4.setColor(0, new Color(0x00B5ef));
		b4.setColor(1, new Color(0x2d73b9));
		Image img4 = b4.getBarChartImage();
		drawDiagram(img4, r4, 0, 72);
		
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
}
