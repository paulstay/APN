/*
 * Created on May 10, 2005
 *
 */
package com.teag.reports;

import java.awt.Color;
import java.awt.Insets;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.estate.pdf.CellInfo;
import com.estate.pdf.CellInfoFactory;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.BarChart;
import com.teag.reports.GRATPages.CustomLabelGenerator;
import com.teag.reports.GRATPages.CustomToolTipGenerator;

/**
 * @author Paul Stay
 *
 */
public class IDITPages extends Page {
	/*
	private String table1Header[] = {
		"Number",
		"Year",
		"Total Real Estate Value",
		"Total Securities Portfolio Growth",
		"Yield On RE @ 3.00%\nAfter Tax",
		"Yield On Sec. Div'd @ 2.00%\nAfter Tax",
		"Yield On Sec. Gr'wth @ 5.00%\nAfter Tax",
		"Total Taxable Estate",
		"Total Estate Taxes",
		"Total To Estate Taxes",
		"Net To Family",
		"[border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b]"
	};

	//private String table1Styles = "[][][][][][][][][color="+pgRed+"][color="+pgRed+"][color="+pgGreen+"]";
	
	private String table2Header[] = {
			"Number",
			"Year",
			"Total Real Estate Value",
			"Note & Principal Payment From Real Estate",
			"Total Securities Portfolio",
			"Yield On Securities @ 7.00%",
			"GRAT Payment From Securities",
			"End Of Year Total Taxable Estate",
			"Total Estate Taxes",
			"Total To Estate Taxes",
			"Net To Family",
			"[border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b]"
	};
	private String table2Styles = "[][][][][][][][][color="+pgRed+"][color="+pgRed+"][color="+pgGreen+"]";
	
	private String table3Header[] = {
			"Number",
			"Year",
			"Total Estate Taxes Before IDIT",
			"Total Estate Taxes After IDIT",
			"Decrease In Estate Taxes After IDIT",
			"Net To Family Before IDIT",
			"Net To Family After IDIT",
			"Increase To Family After IDIT",
			"[border=b][border=b][border=b][border=b][border=b][border=b][border=b][border=b]"
	};
	
	private String table3Styles = "[][][color="+pgRed+"][color="+pgGreen+"][color="+pgGreen+"][color="+pgRed+"][color="+pgGreen+"][color="+pgGreen+"]";
	*/
	
	private  String assets;
	
	//Page1
	public double marketValue;
	public double yrsGrowth;
	public double avgGrowth;
	public double avgIncome;
	public double totalValueProjected;
	public double netPassingToFamily;
	
	public double before[][];
	public double after[][];
	public double comparison[][];
	
	private  int years;
	private  double gratValue;
	private  double lifeBenefit;
	private  double premium;
	private  double cashValue;
	private  double txSavings;
	private  double protection;
	private  double fet;
	// Generic
	public double estateTaxRate;
	
	private boolean useInsurance;
	private boolean useLLC = false;
	
	
	/**
	 * @param document
	 * @param writer
	 */
	public IDITPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}
	/*
	private void setTableFontandSize(PageTable table)
	{
		table.setTableFont("arial.ttf");
		table.setTableFontBold("arialbd.ttf");
		table.setFontSize(9);
	}
	
	private String[] makePage2Row(double cols[], String format)
	{
		String scols[] = new String[cols.length + 1]; // Add one for format info
		scols[0] = integer.format(cols[0]);
		scols[1] = integer.format(cols[1]);
		scols[2] = number.format(cols[2]);
		scols[3] = number.format(cols[3]);
		scols[4] = number.format(cols[4]);
		scols[5] = number.format(cols[5]);
		scols[6] = number.format(cols[6]);
		scols[7] = number.format(cols[7]);
		scols[8] = number.format(cols[8]);
		scols[9] = percent.format(cols[9]);
		scols[10] = number.format(cols[10]);

		scols[11] = format;
		
		return scols;
	}
	private String[] makeRow(double cols[], String format)
	{
		String scols[] = new String[cols.length + 1]; // Add one for format info
		int i;
		
		for(i=0; i < cols.length; i++) {
			if( i == 0|| i == 1)
				scols[i] = integer.format(cols[i]);
			else
				scols[i] = number.format(cols[i]);
		}
		scols[i] = format;
		
		return scols;
	}
	
	
*/
	
	
	public void draw()
	{
		page1();
		newPage();
		//page3();
		//newPage();
		page2();
		newPage();
		newIditPage();
		newPage();
		//page4();
		//newPage();
		//page5();
		//newPage();
		//page6();
		//newPage();
		page7();
	}
	
	/**
	 * @return Returns the assets.
	 */
	public String getAssets() {
		return assets;
	}	

	/**
	 * @return Returns the avgGrowth.
	 */
	public double getAvgGrowth() {
		return avgGrowth;
	}
/**
	 * @return Returns the avgIncome.
	 */
	public double getAvgIncome() {
		return avgIncome;
	}
	
	/**
	 * @return Returns the cashValue.
	 */
	public double getCashValue() {
		return cashValue;
	}
	
	/**
	 * @return Returns the estateTaxRate.
	 */
	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	/**
	 * @return Returns the fet.
	 */
	public double getFet() {
		return fet;
	}
	/**
	 * @return Returns the gratValue.
	 */
	public double getGratValue() {
		return gratValue;
	}
	/**
	 * @return Returns the lifeBenefit.
	 */
	public double getLifeBenefit() {
		return lifeBenefit;
	}
	/**
	 * @return Returns the marketValue.
	 */
	public double getMarketValue() {
		return marketValue;
	}
	/**
	 * @return Returns the netPassingToFamily.
	 */
	public double getNetPassingToFamily() {
		return netPassingToFamily;
	}
	/**
	 * @return Returns the premium.
	 */
	public double getPremium() {
		return premium;
	}
	/**
	 * @return Returns the protection.
	 */
	public double getProtection() {
		return protection;
	}
	/**
	 * @return Returns the totalValueProjected.
	 */
	public double getTotalValueProjected() {
		return totalValueProjected;
	}
	/**
	 * @return Returns the txSavings.
	 */
	public double getTxSavings() {
		return txSavings;
	}
	/**
	 * @return Returns the years.
	 */
	public int getYears() {
		return years;
	}
	/**
	 * @return Returns the yrsGrowth.
	 */
	public double getYrsGrowth() {
		return yrsGrowth;
	}
	/**
	 * @return Returns the useInsurance.
	 */
	public boolean isUseInsurance() {
		return useInsurance;
	}
	public boolean isUseLLC() {
		return useLLC;
	}
	public void newIditPage() {

		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "Intentionally Defective Irrevocable Trust (IDIT)");
		
		Rectangle rct = adjustPlacement(prctTop);
		rct.setBottom(rct.getBottom() - _1_4TH);
		rct.setLeft(rct.getLeft() - _1_4TH);

		if(useLLC ) {
			if( userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-IDIT-M.png", rct, CNTR_TB + LEFT, 400);
				} else {
					drawDiagram("LLC-IDIT-F.png", rct, CNTR_TB + LEFT, 400);
				}
			} else {
				drawDiagram("LLC-IDIT.png", rct, CNTR_TB + LEFT, 400);	
			}
			
		} else {
			if( userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("IDIT-M.png", rct, CNTR_TB + LEFT, 400);
				} else {
					drawDiagram("IDIT-F.png", rct, CNTR_TB + LEFT, 400);
				}
			} else {
				drawDiagram("IDIT.png", rct, CNTR_LR + CNTR_TB, 425);
			}
		}
		
		// We now need the values placed on the left hand side of the page!
//		 Now do the table.
		Rectangle lrct = new Rectangle(prctLLeft);
		lrct.setTop(lrct.getTop() - (_1_2TH + _1_4TH));
		
		drawSection(lrct, "Comparison in " + (int)yrsGrowth + " Years", new String[0], 0);
		
		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - (_1_2TH + _1_2TH));
		
		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 4);
		CellInfoFactory cif = new CellInfoFactory();
		BaseFont baseFont = PageUtils.LoadFont("GARA.TTF");
		BaseFont baseFontBold = PageUtils.LoadFont("GARABD.TTF");
		
		float ptSize = 10f;
		cif.setDefaultFont(new Font(baseFont, ptSize));
		Color green = new Color(0,176,0);
		Color red = new Color(192,0,0);
		Font fontBold = new Font(baseFontBold, ptSize, Font.NORMAL);
		Font fontBGreen = new Font(baseFontBold, ptSize, Font.NORMAL, green);
		Font fontBRed = new Font(baseFontBold, ptSize, Font.NORMAL, red);
		
		double iditValue = after[(int)yrsGrowth-1][10];
		double iditRI = 45000;
		double iditRItax = iditRI * estateTaxRate;

		CellInfo cellData[][] = {
				// Column Headers
				{	cif.getCellInfo(" "), 
					cif.getCellInfo("Keep in the Estate", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo("IDIT", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo("Difference", Element.ALIGN_CENTER, fontBold)
					
				},
				{	cif.getCellInfo("Estate/Gift Taxable:"), 
					cif.getCellInfo(dollar.format(this.totalValueProjected), Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(dollar.format(iditRI), Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(dollar.format(totalValueProjected), Element.ALIGN_CENTER, fontBold)
				},
				
				{	cif.getCellInfo("Total Estate/Gift Taxes:"), 
					cif.getCellInfo(dollar.format(this.totalValueProjected * this.estateTaxRate), Element.ALIGN_CENTER, fontBRed),
					cif.getCellInfo(dollar.format(0) + " *", Element.ALIGN_CENTER, fontBRed),
					cif.getCellInfo(dollar.format(totalValueProjected * estateTaxRate), Element.ALIGN_CENTER, fontBRed),
				},
				
				{	cif.getCellInfo("Total to Family"),
					cif.getCellInfo(dollar.format(this.netPassingToFamily), Element.ALIGN_CENTER, fontBGreen),
					cif.getCellInfo(dollar.format(iditValue), Element.ALIGN_CENTER, fontBGreen),
					cif.getCellInfo(dollar.format(iditValue -(netPassingToFamily + (iditRI - iditRItax))), Element.ALIGN_CENTER, fontBGreen),
				},
		};
/*
		rct = toPage(orgX, orgY, new Rectangle(I2P(1.5f), I2P(3.5f), I2P(1.75f), I2P(2.0f)));
		drawLabel("*Value transfered minus retained income.", rct, "GARA.TTF", textColor,10f, LBL_CENTER, LBL_BOTTOM);		
*/		
		// Do the chart
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = "IDIT";
		
		String cat1 = "Estate Taxes";
		String cat2 = "Total to Family";
		
		dataSet.addValue(totalValueProjected*estateTaxRate, series1, cat1);
		dataSet.addValue(0, series2, cat1);
		
		dataSet.addValue(netPassingToFamily, series1, cat2);
		dataSet.addValue(iditValue, series2, cat2);


		BarChart barChart = new BarChart();
		
		barChart.setTitle("IDIT Comparison");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.setLegendOn(false);
		barChart.generateBarChart();
		barChart.setSeriesColor(new Color(0xff0000),new Color(0x00ff00));
		barChart.setColor(0,Color.red);
		barChart.setColor(1,Color.green);
		barChart.setLablesOff(true);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		table.setTableData(cellData);
		table.drawTable();

		drawLabel("* Taxable gifts are covered by the lifetime exemption.", prctBottom, "GARA.TTF", new Color(64,64,64), 9, LBL_LEFT, LBL_BOTTOM);

		
		
		
	}
	private void page1()
	{
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "The Problem");
		
		int lcomma = assets.lastIndexOf(',');
		String assetsWithoutComma;
		if( lcomma > 0) {
			assetsWithoutComma = assets.substring(0,lcomma);
		} else { 
			assetsWithoutComma = assets;
		}
		
		String sec1[] = {
			"Your assets (" + assetsWithoutComma + ") have the potential to grow very dramatically over the next few years. This is great news until we calculate your future estate tax liability",
		};
		String sec2[] = {
			"When an asset is growing very rapidly in one's estate, and the estate tax liability is growing faster than can be reduced by normal gifting techniques, a different approach can be taken.",
			" ",
			"Let's examine a combination of leveraging techniques that can be utilized with this circumstance."
		};
		
		Rectangle rct = new Rectangle(prctTop);
		Rectangle rctx = this.drawSection(rct, "", sec1, 0, 14, 14);
		
		rct.setTop(rctx.getBottom());
		rct.setLeft(rct.getLeft() + prctTop.getWidth() * .125f);
		rct.setRight(rct.getRight() - prctTop.getWidth() * .125f );
		
		// Draw the Table
		PageTable table = this.getTable(rct, 2);
		String rows[][] = {
				{
					"Today's Fair Market Value",
					"" + dollar.format(marketValue),
					"[][bold=1]"
				},
				{
					"Projected Years of Growth",
					"" + integer.format(this.yrsGrowth),
					"[][bold=1]"
				},
				{
					"Average Growth Rate",
					"" + percent.format(this.avgGrowth),
					"[][bold=1]"
				},
				{
					"Average Income Rate",
					"" + percent.format(this.avgIncome),
					"[][bold=1]"
				},
				{
					" ",
					"",
					"[colspan=1][]"
				},
				{
					"Total Value (projected) in Taxable Estate",
					"" + dollar.format(this.totalValueProjected),
					"[bold=1][bold=1]"
					
				},
				{
					"",
					"",
					"[colspan=1][]"
				},
				{
					"Estate Tax ("+percent.format(this.estateTaxRate)+")",
					"" + dollar.format(this.totalValueProjected * this.estateTaxRate),
					"[Bold=1,Color="+pgRed+"][Bold=1,Color="+pgRed+"]"
				},
				{
					"",
					"",
					"[colspan=1][]"
				},
				{
					"Net Value Passing to Family",
					"" + dollar.format(this.netPassingToFamily),
					"[Bold=1,Color="+pgGreen+"][Bold=1,Color="+pgGreen+"]"
					
				}
				
		};
		
		int alignments[]= {LFT, RGT};
		table.setColumnAlignments(alignments);
		table.setFontSize(14);
		table.buildTableEx(rows);
		table.drawTable();
		
		// Draw the graph ************
		
		taxPie(prctLLeft, this.totalValueProjected, this.totalValueProjected * this.estateTaxRate, "Estate Tax", "Net to Family");
		
		// *****************
		
		drawSection(prctLRight, "", sec2, 0, 14, 14);
	}
	private void page2()
	{
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "Intentionally Defective Irrevocable Trust (IDIT)");
		
		Rectangle rct = adjustPlacement(prctFull);
		rct.setRight(rct.getRight() - (6.5f * 72));
		
		if(useLLC ) {
			if( userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-IDIT-M.png", rct, BOTTOM + LEFT);
				} else {
					drawDiagram("LLC-IDIT-F.png", rct, BOTTOM + LEFT);
				}
			} else {
				drawDiagram("LLC-IDIT.png", rct, BOTTOM + LEFT);	
			}
			
		} else {
			if( userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("IDIT-M.png", rct, BOTTOM + LEFT);
				} else {
					drawDiagram("IDIT-F.png", rct, BOTTOM + LEFT);
				}
			} else {
				drawDiagram("IDIT.png", rct, BOTTOM + LEFT);
			}
		}
		
		String process[];
		
		if( useLLC) {
			if( userInfo.isSingle()) {
				process = new String[] { "1. A Limited Liability Company (LLC) is formed with Managing Member Interests (MMI) and Member Interests (MI). Assets are transferred to the LLC with your retaining the  MMI/MI shares" +
						", however the distribution and liquidation powers must be assigned to someone else.",
						"2,3. MI interests are sold to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc., " + 
						"which needs to be previously funded with at least 1/9th of the value of the assets sold to this trust. " +
						"The sale price is assumed to be discounted due to the MI interests you have and the underlying illiquid real estate asset. " + 
						"You obtain notes recievable for the discounted value, paying interest only, with a balloon payment due at the end of the term.",
						"4,5,6,7. The notes are then combined with Securities Portfolio assets and placed in another LLC. " + "" +
								"You then transfer your MI interest in the LLC into a GRAT, for " + this.years + " years and then payable to the children."
					};
			} else {
				process = new String[] { "1. A Limited Liability Company (LLC) is formed with Managing Member Interests (MMI) and Member Interests (MI). Assets are transferred to the LLC with each of you recieving one half of the GP/LP shares"
						+ ", however the distribution and liquidation powers must be assigned to someone else.",
						"2,3. MI interests are sold by each of you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc., " +
						"which needs to be previously funded with at least 1/9th of the value of the assets sold to this trust. "+
						"The sale price is assumed to be discounted due to the MI interests you each have and the underlying illiquid real estate asset. " + 
						"You each obtain notes recievable for the discounted value, paying interest only, with a balloon payment due at the end of the term.",
						"4,5,6,7. The notes are then combined with Securities Portfolio assets and placed in another LLC. " + "" +
								"You each then transfer your respective MI interest in the LLC into separate GRATS, for " + this.years + " years and then payable to the children."
					};
			}
		} else {
			if( userInfo.isSingle()) {
				process = new String[] { "1. A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are transferred to the FLP, with you recieving the GP/LP shares" 
						+ ", however the distribution and liquidation powers must be assigned to someone else.",
						"2,3. LP interests are sold by you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc., " +
						"which needs to be previously funded with at least 1/9th of the value of the assets sold to this trust. " +
						"The sale price is assumed to be discounted by (40%)n, due to the LP interests you have and the underlying illiquid real estate asset. " + 
						"You obtain notes recievable for the discounted value, paying 5% interest only, with a balloon payment due in 15 years.",
						"4,5,6,7. The notes are then combined with Securities Portfolio assets and placed in another FLP. " + "" +
								"You then transfer your LP interest in the FLP into a GRAT, set up for 10 years and then payable to the children."
					};
			} else {
				process = new String[] { "1. A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are transferred to the FLP. with each of you recieving one half of the GP/LP shares"
						+ ", however the distribution and liquidation powers must be assigned to someone else.",
						"2,3. LP interests are sold by each of you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc., " +
						"which needs to be previously funded with at least 1/9th of the value of the assets sold to this trust. " +
						"The sale price is assumed to be discounted by (40%), due to the LP interests you each have and the underlying illiquid real estate asset. " + 
						"You each obtain notes recievable for the discounted value, paying 5% interest only, with a balloon payment due in 15 years.",
						"4,5,6,7. The notes are then combined with Securities Portfolio assets and placed in another FLP. " + "" +
								"You each then transfer your respective 49% LP interest in the FLP into separate GRATS, set up for 10 years and then payable to the children."
					};
				
			}
		}
		
		Rectangle myRct = new Rectangle(prctRight);
		myRct.setLeft(7.75f * 72);
		myRct.setRight(myRct.getRight() + _1_2TH);
		myRct.setTop(myRct.getTop() - _1_4TH);
		Rectangle rctx = this.drawSection(myRct, "The Process", process, 0); 
		myRct.setTop(rctx.getBottom());
		
		String benefits[] ;
		
		if( useLLC) {
			benefits = new String[] {
					"The LLC discounts the asset value.",
					"The IDIT enables the freezing of a rapidly growing value.",
					"Then the GRATS remove the remaining value from the taxable estate"
			};
		} else {
			benefits = new String[] {
					"The FLP discounts the asset value.",
					"The IDIT enables the freezing of a rapidly growing value.",
					"Then the GRATS remove the remaining value from the taxable estate"
			};
		}
		
		Rectangle r = this.drawSection(myRct, "Benefits of using this technique.", benefits, 2);
		
		String effects[] = {
				"Assets are Irrevocably transferred to the family.",
				"Heirs lose the step-up in basis of the assets.",
			};
			
		myRct.setTop(r.getBottom());
		r = drawSection(myRct, "Side effects of this technique", effects, 2);

	}
	/*
	private void page3()
	{
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "Estate Taxes Before Tax Planning With IDIT");
		Rectangle rct = new Rectangle(prctFull);
		rct.setRight( rct.getRight() + _1_2TH);
		PageTable table = this.getTable(prctFull, table1Header.length - 1);
		this.setTableFontandSize(table);
		float widths[] = {.06f, .05f, .1f, .09f, .1f, .1f, .1f, .125f, .1f, .05f, .1f};
		int alignments[] ={RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,};
		int alignmentsHdr[] ={CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,};
		
		String row[][] = new String[1][];
		
		row[0] = table1Header;
		table.setColumnAlignments(alignmentsHdr);
		table.setColumnWidths(widths);
		table.buildTableEx(row);
		table.setColumnAlignments(alignments);
		
		for(int i = 0; i < 30  && i < before.length; i++)
		{
			//double cols[] ={i + 1,0,0,0,0,0,0,0,0,0,0};
			
			row[0] = makePage2Row(before[i], table1Styles);
			table.buildTableEx(row);
		}
		
		table.drawTable();
	}
	
	private void page4()
	{
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "Estate Taxes After Tax Planning With IDIT");
		Rectangle rct = new Rectangle(prctFull);
		rct.setRight( rct.getRight() + _1_2TH);
		PageTable table = this.getTable(prctFull, table2Header.length - 1);
		this.setTableFontandSize(table);
		float widths[] = {.06f, .05f, .1f, .09f, .1f, .1f, .1f, .125f, .1f, .05f, .1f};
		int alignments[] ={RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,};
		int alignmentsHdr[] ={CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,};
		
		String row[][] = new String[1][];
		
		row[0] = table2Header;
		table.setColumnAlignments(alignmentsHdr);
		table.setColumnWidths(widths);
		table.buildTableEx(row);
		table.setColumnAlignments(alignments);
		
		for(int i = 0; i < 30 && i < after.length; i++)
		{
			//double cols[] ={i + 1,0,0,0,0,0,0,0,0,0,0};
			
			row[0] = makePage2Row(after[i], table2Styles);
			table.buildTableEx(row);
		}
		
		table.drawTable();		
	}

	private void page5()
	{
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "Before and After With IDIT Estate Tax Planning");
		Rectangle rct = new Rectangle(prctFull);
		rct.setRight( rct.getRight() + _1_2TH);
		PageTable table = this.getTable(prctFull, table3Header.length - 1);
		this.setTableFontandSize(table);
		int alignments[] ={RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,RGT,};
		int alignmentsHdr[] ={RGT,RGT,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,CTR,};
		
		String row[][] = new String[1][];
		
		row[0] = table3Header;
		table.setColumnAlignments(alignmentsHdr);
		table.buildTableEx(row);
		table.setColumnAlignments(alignments);
		
		for(int i = 0; i < 30; i++)
		{
			double cols[] = new double[table3Header.length -1];
										
			cols[0] = before[i][0];
			cols[1] = before[i][1];
			cols[2] = before[i][8];
			cols[3] = after[i][8];
			cols[4] = before[i][8] - after[i][8];
			cols[5] = before[i][10];
			cols[6] = after[i][10];
			cols[7] = after[i][10] - before[i][10];
									 
			row[0] = makeRow(cols, table3Styles);
			table.buildTableEx(row);
		}
		
		table.drawTable();			
	}
	
	private void page6() {
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "Intentionally Defective Irrevocable Trust Comparison");
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Traditional";
		String series2 = "IDIT";

		for(int i = 0; i < 30; i++){
			String cat = (i+1) + "";
			dataSet.addValue(before[i][10], series1, cat);
			dataSet.addValue(after[i][10], series2, cat);
		}

		BarChart barChart = new BarChart();
		
		barChart.setTitle("Traditional vs IDIT");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctFull);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		barChart.setColor(0,java.awt.Color.RED);
		barChart.setColor(1,java.awt.Color.GREEN);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
	}
*/
	private void page7()
	{
        int gratTerm = years + 1;
		drawBorder(pageIcon, "IDIT - GRAT/Insurance");
		drawHeader(userInfo.getClientHeading(), "Eliminating the GRAT Side Effects");
		
		String t1[] =
		{
			"By Strategically combining the tax advantages of the " + gratTerm + " Year GRAT with life insurance, your plan may eliminate the side effects (see below) of the GRAT technique. The insurance is designed to provide you with " 
                + percent.format(1.0 - txSavings) +" of the tax savings, regardless of when deaths occur."
		};
		
		Rectangle rctx = this.drawSection(prctFull, "", t1, 0, 14, 12);
		Rectangle rct = new Rectangle(prctFull);
		
		rct.setTop(rctx.getBottom());
		
		String t2[] ={};
		
		rctx = this.drawSection(rct, "How Does This Work", t2, 0, 14, 12);
		
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());
		PageTable table = getTable(rct, 3);
		float widths[] = { .6f, .10f, .30f};
		table.setColumnWidths(widths);
		

		
		
		String rows[][] = {
			{
				"Growth and Income of the GRAT (Year " + years + ")",
				"" ,
				""+dollar.format(gratValue),
				"[][][align=right,bold=1]",
			},
			{
				"Estate Taxes (if death occurs too soon)",
				"",
				"" + number.format(gratValue * fet),
				"[][align=right][align=right,bold=1]",
			},
			{
				"Insurance Death Benefit",
				"(a)",
				"" + number.format(lifeBenefit),
				"[][align=right][align=right,bold=1]",
			},
			{
				"Annual Insurance Premium",
				"",
				"" + number.format(premium),
				"[][][align=right,bold=1]",
			},
			{
				"Accumulated Cost of Insurance ("+dollar.format(premium)+" x "+years+" years)",
				"(b)",
				"" + dollar.format(premium * years),
				"[][align=right][align=right,bold=1]",
			},
			{
				"Cash Value of Life Insurance",
				"(c)",
				"" + dollar.format(cashValue),
				"[][align=right][align=right,bold=1]",
			},
			{
				"Cost to Preserve Tax Savings (Insurance)",
				"(d)=(b-c)",
				"" + dollar.format(protection),
				"[][align=right][align=right,bold=1]",
			},
			{
				"Cost of Protection as a Percent of Tax Savings",
				"(d)/(a)",
				""+ percent.format(txSavings),
				"[][align=right][align=right,bold=1]",
			},
			{
				"Percent of Tax Savings Realized",
				"",
				"" + percent.format(1.0 - txSavings),
				"[][][align=right,bold=1]",
			},
		
		};
		
		table.buildTableEx(rows);
		table.drawTable();
		
		rctx = table.getResultRect();
		
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom() - _1_2TH);
		
		String t3[] = {"If death occurs before the end of the GRAT term, GRAT payments and the assets are included in the taxable estate not as cash, but as continued annuity payments, thus not available to pay the tax."};
		String t4[] = {"Insurance proceeds replace the tax savings if early death occurs. These tax-free proceeds (when insurance is owned outside the estate) are in addition to any assets passing through the estate."};
		String t5[] = {"Professionals often refer to the Grantor Retained Annuity Trust (GRAT) as a \"heads you win, tails you tie\" scenario. However, combining the power of the GRAT with life insurance yields a \"heads you win, tails you win\" outcome for you and for your family."};
		
		rctx = this.drawSection(rct, "Side Effect", t3, 0, 14,12);
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());
		
		rctx = this.drawSection(rct, "Solution", t4, 0, 14,12);
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());

		rctx = this.drawSection(rct, "Summary", t5, 0, 14,12);
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());

	}
	/**
	 * @param assets The assets to set.
	 */
	public void setAssets(String assets) {
		this.assets = assets;
	}
	/**
	 * @param avgGrowth The avgGrowth to set.
	 */
	public void setAvgGrowth(double avgGrowth) {
		this.avgGrowth = avgGrowth;
	}
	/**
	 * @param avgIncome The avgIncome to set.
	 */
	public void setAvgIncome(double avgIncome) {
		this.avgIncome = avgIncome;
	}
	/**
	 * @param cashValue The cashValue to set.
	 */
	public void setCashValue(double cashValue) {
		this.cashValue = cashValue;
	}
	/**
	 * @param estateTaxRate The estateTaxRate to set.
	 */
	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}
	/**
	 * @param fet The fet to set.
	 */
	public void setFet(double fet) {
		this.fet = fet;
	}
	/**
	 * @param gratValue The gratValue to set.
	 */
	public void setGratValue(double gratValue) {
		this.gratValue = gratValue;
	}
	/**
	 * @param lifeBenefit The lifeBenefit to set.
	 */
	public void setLifeBenefit(double lifeBenefit) {
		this.lifeBenefit = lifeBenefit;
	}
	/**
	 * @param marketValue The marketValue to set.
	 */
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	/**
	 * @param netPassingToFamily The netPassingToFamily to set.
	 */
	public void setNetPassingToFamily(double netPassingToFamily) {
		this.netPassingToFamily = netPassingToFamily;
	}
	/**
	 * @param premium The premium to set.
	 */
	public void setPremium(double premium) {
		this.premium = premium;
	}
	/**
	 * @param protection The protection to set.
	 */
	public void setProtection(double protection) {
		this.protection = protection;
	}
	/**
	 * @param totalValueProjected The totalValueProjected to set.
	 */
	public void setTotalValueProjected(double totalValueProjected) {
		this.totalValueProjected = totalValueProjected;
	}
	/**
	 * @param txSavings The txSavings to set.
	 */
	public void setTxSavings(double txSavings) {
		this.txSavings = txSavings;
	}
	/**
	 * @param useInsurance The useInsurance to set.
	 */
	public void setUseInsurance(boolean useInsurance) {
		this.useInsurance = useInsurance;
	}
	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}
	/**
	 * @param years The years to set.
	 */
	public void setYears(int years) {
		this.years = years;
	}


	/**
	 * @param yrsGrowth The yrsGrowth to set.
	 */
	public void setYrsGrowth(double yrsGrowth) {
		this.yrsGrowth = yrsGrowth;
	}


	private void taxPie(Rectangle rct, double totalValue, double tax, String taxLabel, String netLabel)
	{
		double taxPercent = (tax / totalValue) * 100;
		double netValuePercent = 100 - taxPercent;
		
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue(taxLabel, taxPercent);
        dataset.setValue(netLabel, netValuePercent);

        PiePlot3D plot = new PiePlot3D(dataset);
        plot.setLabelGenerator(new StandardPieItemLabelGenerator());
        plot.setInsets(new Insets(0,5,5,5));
        plot.setToolTipGenerator(new CustomToolTipGenerator());
        plot.setLabelGenerator(new CustomLabelGenerator());
        plot.setSectionPaint(0,new Color(pgRed));
        plot.setSectionPaint(1,new Color(pgGreen));
        plot.setForegroundAlpha(.6f);
        plot.setOutlinePaint(Color.white);
        plot.setBackgroundPaint(Color.white);
        
        JFreeChart chart = new JFreeChart("Asset Distribution",JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        chart.setBackgroundPaint(Color.white);
        chart.setAntiAlias(true);

        Rectangle page = rct;
        
        
        try
		{
        	Image img = Image.getInstance(chart.createBufferedImage((int)page.getWidth(), (int)page.getHeight()), null);
        	drawDiagram(img, rct, 0, 72);
		}
        catch( Exception e)
		{
        	System.out.println(e.getMessage());
		}		
		
		
	}
}
