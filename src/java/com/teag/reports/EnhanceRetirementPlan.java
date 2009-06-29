package com.teag.reports;

import java.awt.Color;
import java.awt.Insets;
import java.text.DecimalFormat;

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
import com.teag.estate.CharliePlanTool;
import com.teag.reports.GRATPages.CustomLabelGenerator;
import com.teag.reports.GRATPages.CustomToolTipGenerator;

public class EnhanceRetirementPlan extends Page {

	
	CharliePlanTool erp;
	
	public EnhanceRetirementPlan(Document document, PdfWriter writer) {
		super(document, writer);
	}

	public void draw() {
		page1();
		newPage();
		page2();
		newPage();
		page3();
		newPage();
	}

	public CharliePlanTool getErp() {
		return erp;
	}

	private void page1() {
		// Page 1
		Rectangle rct;
		DecimalFormat df = new DecimalFormat("##.##%");

		drawBorder(pageIcon, "ERP");

		float ptSize = 12f;
		drawHeader(userInfo.getClientHeading(),
				"Earned Income Capital Punishment Plan");
		String exp1 = "The vast majority of a very successful person's earned income is taxed at least twice, with both income and estate taxes, and sometimes many more times on the growth as the accumulated assets are repositioned. "
				+ "These combined taxes, (" + df.format(erp.getTaxRate()) + ") for federal and state income taxes and then (55%) for federal and state estate taxes, can often be a minimum of (74%) and frequently even higher.";


		String exp2 = "Let's explore an approach that is available to the high income earner who controls his or her income"+
			" stream through their own entrepreneurial activity.\n This control and reallocation ability can "+
			"enable an annual income tax deduction initially, tax protected growth, and then the ability to "+
			"pass substantial assets to future generations free of subsequent taxes.";
		
		rct = drawLabel(exp1, prctTop, "GARA.TTF", new Color(0, 0, 0), ptSize,
				LBL_LEFT, LBL_TOP);
		drawLabel(exp2, prctLRight, "GARA.TTF", new Color(0, 0, 0), ptSize,
				LBL_LEFT, LBL_CENTER);
//		 Now we need to display the table
		float left = rct.getLeft() + (rct.getWidth() * .125f);
		float right = rct.getRight() - (rct.getWidth() * .125f);
		
		rct.setLeft(left);
		rct.setRight(right);
		rct.setTop( rct.getBottom());
		rct.setBottom(prctTop.getBottom());
		
//		drawFilledRect(rct, new Color(128,255,128));
		drawLine(prctTop.getLeft(), horizLineY, prctTop.getRight(), horizLineY, new Color(0,72,117));
		
		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 2);
		float widths[] = { .75f, .25f };
		CellInfoFactory cif = new CellInfoFactory();
		BaseFont baseFont = PageUtils.LoadFont("GARA.TTF");
		BaseFont baseFontBold = PageUtils.LoadFont("GARABD.TTF");
		
		cif.setDefaultFont(new Font(baseFont, ptSize));
		Color green = new Color(0,176,0);
		Color red = new Color(192,0,0);
		double contribution = (erp.getClientContr() + erp.getSpouseContr());
		double taxes = contribution * erp.getTaxRate();
		double totalValue = 0;
		for(int i = 0; i < erp.getFinalDeath(); i++) {
			if(i < erp.getTerm())
				totalValue += contribution - taxes;
			totalValue = totalValue * 1.06;
		}
		
		CellInfo cellData[][] = {
				{cif.getCellInfo("Current Excess Annual Earned Income"), 
					cif.getCellInfo(dollar.format(contribution), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo("Total Federal & State Income Tax ("+df.format(erp.getTaxRate())+")",new Font(baseFontBold, ptSize, Font.NORMAL,red) ), 
					cif.getCellInfo(dollar.format(taxes), Element.ALIGN_RIGHT,new Font(baseFontBold, ptSize, Font.NORMAL,red))},
				
				{cif.getCellInfo("Cash for investment per year for " + number.format(erp.getTerm()) + " years", new Font(baseFontBold, ptSize, Font.BOLD,Color.black)), 
					cif.getCellInfo(number.format(contribution - taxes), Element.ALIGN_RIGHT,new Font(baseFontBold, ptSize, Font.BOLD,Color.black))},
				{cif.getCellInfo(" "),
					cif.getCellInfo(" ")},
				{cif.getCellInfo("Projected for " + Integer.toString(erp.getFinalDeath()) + " years.",
						new Font(baseFontBold, ptSize, Font.NORMAL, Color.black))	,cif.getCellInfo(" ")},
				{cif.getCellInfo("Total Value of Investments (6% Growth)", new Font(baseFontBold, ptSize, Font.BOLD, Color.black)),
							cif.getCellInfo(dollar.format(totalValue), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL, Color.black))
				},
				{cif.getCellInfo("Estate Tax", new Font(baseFontBold, ptSize, Font.BOLD, Color.red)),
							cif.getCellInfo(dollar.format(totalValue * .55), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL, red))
				},
				{cif.getCellInfo("Net Value Passing to Family", new Font(baseFontBold, ptSize, Font.NORMAL)),
					cif.getCellInfo(dollar.format(totalValue - (totalValue * .55)), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL,green))},
		};
// Do the chart
		
		table.setColumnWidths(widths);
		table.setTableData(cellData);
		table.drawTable();
		
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Estate Tax", 740);
		dataset.setValue("Net to Family", 260);

		PiePlot3D plot = new PiePlot3D(dataset);
		plot.setLabelGenerator(new StandardPieItemLabelGenerator());
		plot.setInsets(new Insets(0, 5, 5, 5));
		plot.setToolTipGenerator(new CustomToolTipGenerator());
		plot.setLabelGenerator(new CustomLabelGenerator());
		plot.setSectionPaint(0, Color.red);
		plot.setSectionPaint(1, Color.green);
		plot.setForegroundAlpha(.6f);
		plot.setOutlinePaint(Color.white);
		plot.setBackgroundPaint(Color.white);

		String title = "Asset";

		JFreeChart chart = new JFreeChart(title + " Distribution",
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		chart.setBackgroundPaint(Color.white);
		chart.setAntiAlias(true);

		Rectangle page = prctLLeft;

		try {
			Image img = Image.getInstance(chart.createBufferedImage((int) page
					.getWidth(), (int) page.getHeight()), null);
			drawDiagram(img, prctLLeft, 0, 72);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void page2() {
		Rectangle rct = new Rectangle(prctTop);
		drawBorder(pageIcon, "ERP");

		drawHeader(userInfo.getClientHeading(),
				"Enhanced Retirement Plan (ERP)");

		drawDiagram("ERP.jpg", rct, CNTR_LR + CNTR_TB);

		String theProcess[] = new String[] {
			"An income stream is continued for the client as an employee of client's business. This income stream is subject to normal income taxes.",
			"An Enhanced Retirement Plan is established with the client (and spouse) as employee(s). Income directed to this plan is deductible to the business for both federal and state purposes, and flows into two (2) accounts: an investment account and an insurance account",
			"As each employee reaches the stated retirement age, an income stream (for life) is directed to him or her. If married, this income stream will continue as long as either spouse is alive, at the full amount for the survivor. This retirement income stream is taxable.",
			"Life insurance is obtained by the retirement plan, with income tax deductable premiums, on the life of each participant. Proper planning can allow these proceeds to pass into a special trust for the family,  avoiding both subsequent income and estate taxes.",
			"The plan can be administered in such a way that a growing asset base can be perpetuated for the family through several generations. This core asset base can avoid being subjected to any taxes."
		};

		rct = new Rectangle(prctLeft);
		rct.setTop(rct.getTop() - Page._1_4TH * 13);
		drawSection(rct, "The Process", theProcess, 1);


		String benefits[];

		benefits = new String[] {
			"Enables taxable income to become tax deductible through a transfer to the retirement plan.",
			"Assets in the retirement plan grow free of tax until withdrawn as a retirement income.",
			"Life Insurance on the life of the participant(s) can pass tax free to a trust for the participant's family.",
			"Excess assets not used for retirement income can become a perpetual income generating endowment fund for future generation participants."
		};

		rct = new Rectangle(prctRight);
		rct.setTop(rct.getTop() - Page._1_4TH * 13);
		Rectangle r = drawSection(rct, "Benefits of using this technique",
				benefits, 2);
		String effects[];

		effects = new String[] {
			"Numerous other employees of the business can require retirement plan contributions than can reduce the economic benefit to the clients.",
			"Current income tax recognition on the economic benefit of the life insurance coverage is important in order to provide the income tax free death benefit."
		};

		rct = new Rectangle(prctRight);
		rct.setTop(rct.getTop() - ((Page._1_4TH * 13) + r.getHeight()));
		r = drawSection(rct, "Side effects/Drawbacks of using this technique",
				effects, 2);

	}

	public void page3() {
		Rectangle rct = new Rectangle(prctTop);
		java.text.DecimalFormat df = new java.text.DecimalFormat("$###,###,###");

		double before[] = erp.getBefore();
		double sideFund[] = erp.getToFamily();
		float ptSize = 12f;
		
		drawBorder(pageIcon, "ERP");

		int red = makeColor(255,0,0);
		int green = makeColor(0,255,0);
		
		String txt1 = "After " + Integer.toString(erp.getTerm()) + " years, " +userInfo.getClientFirstName() + " and " + userInfo.getSpouseAge() + " receive a monthly income stream of " +
			dollar.format(erp.getClientMonthly()+erp.getSpouseMonthly()) + " per month that continues as long as either spouse is alive. This retirement income stream is taxable. " +
			"The additional excess cash in the estate from these payments can be zero'ed out using techniques such as a Testamentary Charitable Lead Annuity Trust (TCLAT).";
		

		drawBorder(pageIcon, "ERP");
		drawHeader(userInfo.getClientHeading(), "Enhanced Retirement Plan");
		
		rct = drawLabel(txt1, prctTop, "GARA.TTF", new Color(0, 0, 0), ptSize,
				LBL_LEFT, LBL_TOP);
		
		PageTable table = new PageTable(writer);
		Rectangle rctTable = new Rectangle(prctFull);
		rctTable.setTop(rct.getBottom() - (2* _1_2TH));
		rctTable.setLeft(rctTable.getLeft()+_1_2TH);
		rctTable.setRight(rctTable.getRight()-_1_2TH);
		
		table.setTableFormat(rctTable, 4);
		String cells[][] = {
				{
					"Totals after " + Integer.toString(erp.getFinalDeath()) + " years",
					"",
					"",
					"",
					"[colspan=4,ptsize=14,Bold=1][][][]",
				},
				{
					" ", " ", " ", " ", "[][][][]"
				},
				{
					"Total Plan Contributions",
					"",
					df.format((erp.getClientContr()+erp.getSpouseContr())*erp.getTerm()),
					"",
					"[align=left][][align=right][]"
				},
				{
					"Monthly Income Stream",
					"",
					df.format((erp.getClientMonthly()+erp.getSpouseMonthly())),
					"",
					"[align=left][][align=right][]"
				},

				{
					"Life Ins. tax Free Death Benefit",
					"",
					df.format(erp.getClientDeathBenefit()+erp.getSpouseDeathBenefit()),
					"",
					"[align=left][][align=right][]"
				},
				{
					"Family Endowment Fund",
					"",
					df.format(sideFund[erp.getFinalDeath()-1]),
					"",
					"[align=left][][align=right][]"
				},
		};
		table.setFontSize(10);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		float widths[] = {2.5f,.5f,3.5f,.5f};
		
		table.setColumnWidths(widths);
		table.buildTableEx(cells);
		table.setCellLeading(1.25f);
		table.drawTable();

		PageTable table2 = new PageTable(writer);
		rctTable = new Rectangle(prctLLeft);
		rctTable.setTop(rctTable.getTop() - (_1_2TH*2));
		
		table2.setTableFormat(rctTable, 5);

		String cells2[][] = {
				{
					"Comparison in 25 Years",
					"",
					"",
					"",
					"",
					"[bold=1,align=left,colspan=5][][][][]"
				},
				{
					"Keep in Estate",
					"",
					"",
					"Enhanced Retirement Plan",
					"",
					"[bold=1,colspan=2,border=b][border=b][border=b][border=b,bold=1,colspan=2][]"
				},
				{
					"Asset Value",
					df.format(before[24]),
					"",
					"Life Ins.",
					df.format((erp.getClientDeathBenefit()+erp.getSpouseDeathBenefit())),
					"[align=left][align=right][][align=left][align=right]"
				},
				{
					"",
					"",
					"",
					"Endowment",
					df.format(sideFund[24]),
					"[][][][][align=right]"
				},
				{
					"Estate Tax",
					df.format(.55* before[24]),
					"",
					"Estate Tax",
					df.format(0),
					"[align=left,color="+red+"][align=right,color="+red+"][][align=left,color="+red+"][align=right,color="+red+"]"
				},
				{
					"To Family",
					df.format(before[24]*.45),
					"",
					"To Family",
					df.format(((erp.getClientDeathBenefit() + erp.getSpouseDeathBenefit()))+(sideFund[24])),
					"[color="+green+"][align=right,color="+green+"][color="+green+"][][align=right,color="+green+"]"
				}
				
		};

		table2.setFontSize(10);
		table2.setTableFont("arial.TTF");
		table2.setTableFontBold("arialbd.TTF");
		float widths2[] = {1f,1f,.5f,1f,1f};
		
		table2.setColumnWidths(widths2);
		table2.buildTableEx(cells2);
		table2.setCellLeading(1.25f);
		table2.drawTable();
// Do the chart
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = "Enhanced Retirement Plan";
		
		String cat1 = "Estate Taxes";
		String cat2 = "Total to Family";
		
		dataSet.addValue(before[24]*.55, series1, cat1);
		dataSet.addValue(0, series2, cat1);
		
		dataSet.addValue(before[24]*.45, series1, cat2);
		dataSet.addValue(((erp.getClientDeathBenefit() + erp.getSpouseDeathBenefit()))+(sideFund[24]), series2, cat2);


		BarChart barChart = new BarChart();
		
		barChart.setTitle("Comparison");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
	}

	public void setErp(CharliePlanTool erp) {
		this.erp = erp;
	}
}
