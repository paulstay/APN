/**
 * 
 */
package com.estate.report;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.data.category.DefaultCategoryDataset;

import com.estate.pdf.Page;
import com.estate.pdf.PageTable;
import com.estate.toolbox.CRT;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.AreaChart;
import com.teag.chart.BarChart;

/**
 * @author Paul
 * 
 */
public class CRTReport extends Page {

	CRT crt;

	String clientHeading = "";
	boolean isSingle = true;

	/**
	 * @param document
	 * @param writer
	 */
	public CRTReport(Document document, PdfWriter writer) {
		super(document, writer);
	}

	public void draw() {
		crt.calculate();
		page1();
		page2();
		page3();
		page4();
		page5();
	}

	public CRT getCrt() {
		return crt;
	}

	public void page1() {

		clientHeading = userInfo.getFirstName() + " " + userInfo.getLastName();
		if (userInfo.getGender().equals("B")) {
			isSingle = true;
		}

		String s1, s2, s3;

		drawBorder(pageIcon, "CRUT");
		drawHeader(clientHeading, "The Problem");
		PageTable table = new PageTable(writer);
		table.setTableFormat(prctFull, 4);
		int Red = makeColor(192, 0, 0);

		if (isSingle) {
			s1 = " and that you live ";
			s2 = "age";
			s3 = "your death";
		} else {
			s1 = " and that at least one of you lives ";
			s2 = "ages";
			s3 = "both of your deaths";
		}
		String cells[][] = {
				/* row 0 */{
						"",
						"You have built several very successful assets, over many years, with much physical and emotional effort. This effort brings both good news and bad news. The good news is that you can sell them at a tremendous profit. The bad news is that the IRS wants to keep much of this profit in the form of taxes.  Let's look at the result of these taxes in three areas:",
						"", "", "[][Colspan=2][][bold=1]" },
				/* row 1 */{ "", "", "", "", "[colspan=4][][][]" },

				/* row 2 */{ "A. Income Taxes", "", "", "", "[Colspan=2][][][]" },
				/* row 3 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 4 */{
						"",
						"Capital Gains Taxes Destroy "
								+ percent.format(crt.getCapitalGainsTax())
								+ "[1] of your profit:", "", "",
						"[][Colspan=3][][]" },
				/* row 5 */{ "", "", "Value of Assets ",
						"" + dollar.format(crt.getFmv()), null },
				/* row 6 */{ " ", " ", "Less: Cost(Basis)",
						"" + dollar.format(crt.getBasis()), null },
				/* row 7 */{ " ", " ", "Taxable Gain",
						"" + dollar.format((crt.getFmv() - crt.getBasis())),
						null },
				/* row 8 */{ " ", " ", "Income (Capital Gains) Tax Liability",
						"" + dollar.format(crt.getCapitalGains()),
						"[][][Color=" + Red + "][Color=" + Red + "]" },
				/* row 9 */{ "", "", "", "", "[colspan=4]" },
				/* row 10 */{
						"",
						"This loss of taxes also creates another loss.  All of the future earnings that could have been received had this tax amount been invested, are also lost in perpetuity!  Let's assume a return on investment of "
								+ percent.format(crt.getInvestmentReturn())
								+ s1
								+ Integer.toString((int) crt.getFinalDeath())
								+ " years.", "", "", "[][Colspan=3][][]" },
				/* row 11 */{ "", "", "", "", "[colspan=4][][][]" },

				/* row 12 */{ "B. Income Loss", "", " ", " ",
						"[Colspan=2][][][]" },
				/* row 13 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 14 */{
						" ",
						" ",
						"Loss of income** potential each year for life!",
						""
								+ dollar.format(crt.getCapitalGains()
										* crt.getInvestmentReturn()), null },
				/* row 15 */{
						" ",
						" ",
						"At your " + s2 + ", this projected loss of income is",
						""
								+ dollar.format(crt.getCapitalGains()
										* crt.getInvestmentReturn()
										* crt.getClientLe()), null },
				/* row 16 */{ " ", "And, the problem gets worse...", "", "",
						"[][colspan=3][][]" },
				/* row 17 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 18 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 19 */{
						" ",
						"Upon "
								+ s3
								+ ", the remaining amount of these proceeds (along with your other assets) will be subjected to an estate tax.  This is the largest tax of all.",
						"", "", "[][Colspan=3][][]" },
				/* row 20 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 21 */{ "C. Estate Taxes", "", " ", " ",
						"[Colspan=2,Underline=1][][][]" },
				/* row 22 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 23 */{
						" ",
						" ",
						"Net Proceeds (after Capital Gains Taxes)",
						""
								+ dollar.format(crt.getFmv()
										- crt.getCapitalGains()), null },
				/* row 24 */{
						" ",
						" ",
						"Estate Tax (" + percent.format(crt.getEstateTaxRate())
								+ ")",
						""
								+ dollar
										.format(crt.getEstateTaxRate()
												* (crt.getFmv() - crt
														.getCapitalGains())),
						"[][][Color=" + Red + "][Color=" + Red + "]" },
				/* row 25 */{
						" ",
						" ",
						"Net to Family",
						""
								+ dollar.format((crt.getFmv()-(crt.getCapitalGains())) -
										(crt.getEstateTaxRate() * (crt.getFmv() - crt.getCapitalGains()))), null},
				/* row 26 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 27 */{ "D. Where Did All The Money Go?", "", " ", " ",
						"[Colspan=3,Underline=1][][][]" },
				/* row 28 */{ "", "", "", "", "[colspan=4][][][]" },
				/* row 29 */{ " ", " ", "Sales Price",
						"" + dollar.format(crt.getFmv()), null },
				/* row 30 */{
						" ",
						" ",
						"Total Taxes (Capital Gains plus Estate Tax)",
						""
								+ dollar.format(crt.getCapitalGains()
										+ (crt.getEstateTaxRate() * (crt
												.getFmv() - crt
												.getCapitalGains()))),
						"[][][Color=" + Red + "][Color=" + Red + "]" },
				/* row 31 */{
						" ",
						" ",
						"Net to Family",
						""
								+ dollar.format(crt.getFmv()
										- crt.getCapitalGains()
										- (crt.getEstateTaxRate() * (crt
												.getFmv() - crt
												.getCapitalGains()))), null },
				/* row 32 */{
						" ",
						" ",
						"Percentage Lost to Taxes",
						""
								+ percent
										.format((crt.getCapitalGains() + (crt
												.getEstateTaxRate() * (crt
												.getFmv() - crt
												.getCapitalGains())))
												/ crt.getFmv()),
						"[][][Bold=1][Bold=1]" },
				/* row 33 */{ " ", "", "", "", "[colspan=4][][][]" },
				/* row 34 */{ "(The IRS' version of Capital Punishment!)", "",
						"", "", "[Colspan=4,Align=center][][][]" },
				/* row 26 */{ " ", "", "", "", "[colspan=4][][][]" },
				/* row 35 */{ "[1] Federal plus State Capital Gains taxes", "",
						"", "", "[Colspan=4,Align=left][][][]" },
				/* row 35 */{
						"** Assuming a pre-tax return on investment at "
								+ percent.format(crt.getInvestmentReturn())
								+ ". ("
								+ dollar.format(crt.getCapitalGains())
								+ " @ "
								+ percent.format(crt.getInvestmentReturn())
								+ " = a loss of  "
								+ dollar.format(crt.getCapitalGains()
										* crt.getInvestmentReturn())
								+ " each year of life)", "", "", "",
						"[Colspan=4,Align=left][][][]" },

		};

		table.setTableFont("GARA.TTF");
		table.setTableFontBold("GARABD.TTF");
		float widths[] = { .10f, .10f, .60f, .20f };
		int alignments[] = { Element.ALIGN_LEFT, Element.ALIGN_LEFT,
				Element.ALIGN_LEFT, Element.ALIGN_RIGHT };
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);
		table.buildTableEx(cells);
		table.drawTable();

		newPage();
	}

	public void page2() {
		drawBorder(pageIcon, "CRUT");
		drawHeader(clientHeading, "Charitable Remainder Trust");
		Rectangle rct = new Rectangle(prctTop);
		rct.setBottom(rct.getBottom() - Page._1_4TH);
		rct.setLeft(rct.getLeft() - Page._1_4TH);
		if (isSingle) {
			if (userInfo.getGender().equalsIgnoreCase("M")) {
				drawDiagram("WPT-M.png", rct, CNTR_LR + BOTTOM);
			} else {
				drawDiagram("WPT-F.png", rct, CNTR_LR + BOTTOM);
			}

		} else {
			drawDiagram("WPT.png", rct, CNTR_LR + BOTTOM);
		}

		String wptIntro[] = {
				"Fortunately, a tool that can help solve this problem has existed for many years, although it has not been widely publicized. Created over 100 years ago, and clarified by Congress in 1969, this trust has been utilized by many top tax advisors for the benefit of their affluent clients.",
				"Many individuals who have learned about this trust thought it was too good to be true and wrote the IRS requesting clarification. Because of the burden of responding, the IRS, in 1990, published 16 versions of the trust as authorized prototypes." };
		this.drawSection(prctLLeft, " ", wptIntro, 0, 12, 10.5f);

		if (isSingle) {
			String genderStr = userInfo.getGender().equals("M") ? "his" : "her";
			String howItWorks[] = new String[] {
					"The Charitable Remainder Trust is established and certain assets are transferred to it.",
					"Assets are then sold by the trustee (this can be "
							+ userInfo.getFirstName()
							+ ") and proceeds are reinvested (as trustee directs) into income producing assets.",
					"Cash is paid to "
							+ userInfo.getFirstName()
							+ " as the income beneficiary for "
							+ genderStr
							+ " lifetime. Upon "
							+ genderStr
							+ " death, the full amount is paid to the survivor.",
					"Gifts are regularly made to the Family (or in trust for them) of excess cash flow, which protects their inheritance with life insurance on the life of "
							+ userInfo.getFirstName() + ".",
					"Upon the death of "
							+ userInfo.getFirstName()
							+ ", the remaining (CRT) assets pass to the named charities.", };
			this.drawSection(prctLRight, "Here's How It Works", howItWorks, 1,
					12, 10.5f);
		} else {
			String howItWorks[] = new String[] {
					"The Charitable Remainder Trust is established and certain assets are transferred to it.",
					"Assets are then sold by the trustee (this can be "
							+ userInfo.getFirstName()
							+ ") and proceeds are reinvested (as trustee directs) into income producing assets.",
					"Cash is paid to "
							+ userInfo.getFirstName()
							+ " as the income beneficiaries for their lifetimes. Upon the first death, the full amount is paid to the survivor.",
					"Gifts are regularly made to the Family (or in trust for them) of excess cash flow, which protects their inheritance with life insurance on the lives of "
							+ userInfo.getFirstName() + ".",
					"Upon the deaths of "
							+ userInfo.getFirstName()
							+ ", the remaining (CRT) assets pass to the named charities.", };
			this.drawSection(prctLRight, "Here's How It Works", howItWorks, 1,
					12, 10.5f);
		}
		newPage();
	}

	public void page3() {
		drawBorder(pageIcon, "CRUT");
		drawHeader(clientHeading, "The Comparison");
		PageTable table = new PageTable(writer);
		table.setTableFormat(prctFull, 6);
		int Red = makeColor(192, 0, 0);
		int Green = makeColor(0, 192, 0);
		int Blue = makeColor(0, 0, 192);

		String s1;
		if (isSingle) {
			s1 = "death";
		} else {
			s1 = "deaths";
		}

		double crtValues[][] = crt.getCrt();
		double noCrt[][] = crt.getNoCrt();
		double trustNetSpendable = 0;
		double portNetSpendable = 0;
		double totalDeduction = 0;

		int maxComp = (int) crt.getFinalDeath();

		for (int i = 0; i < maxComp; i++) {
			trustNetSpendable += crtValues[i][4];
			portNetSpendable += noCrt[i][2];
			totalDeduction += crtValues[i][1];
		}

		String cells[][] = {
				{
						"",
						"When comparing the results of a traditional outright sale to the use of a Charitable Remainder Trust (CRT) and inheritance trust for the family, the difference is truly astounding.",
						"", "", "", "",
						"[][Colspan=5,ptSize=11,font=GARA.TTF][][][][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{ "Income Taxes", " ", "Outright Sale", " ", "CRT", " ",
						"[colspan=2,bold=1][][align=center,border=b][][align=center,bold=1,border=b][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{ "", "Net Proceeds from Sale",
						"" + dollar.format(crt.getFmv()), "",
						"" + dollar.format(crt.getFmv()), "",
						"[][][][][bold=1][]" },
				{
						"",
						"Capital Gains Tax (up front)",
						""
								+ dollar.format(crt.getCapitalGains()),
						"",
						"" + dollar.format(0),
						"",
						"[][Color=" + Red + "][Color=" + Red + "][][Color="
								+ Red + "]" },
				{
						"",
						"Net Remaining to Invest",
						""
								+ dollar.format(crt.getFmv()
										- crt.getLiability()
										- (crt.getCapitalGains())), "",
						"" + dollar.format(crt.getFmv()), "", "[][][][][][]" },
				{ "", "Income Tax Deduction", "" + dollar.format(0), "",
						"" + dollar.format(crt.getCharitableDeduction()), "",
						"[][][][][][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{ "Benefits During Lifetime of " + userInfo.getFirstName(), "",
						"Outright Sale", "", "CRT", "",
						"[colspan=2,bold=1][][align=center,border=b][][align=center,bold=1,border=b][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{ "", "Annual Income 1st Yr. (Net after tax)",
						"" + dollar.format(noCrt[1][2]), "(1)",
						"" + dollar.format(crtValues[1][4]), "(2)(3)",
						"[][][][][Bold=1][]" },
				{
						"",
						"Annual Income " + Integer.toString((int)crt.getFinalDeath())
								+ "th Yr. (Net after tax)",
						"" + dollar.format(noCrt[(int) crt.getFinalDeath()-1][2]),
						"(1)",
						""
								+ dollar.format(crtValues[(int) crt
										.getFinalDeath()-1][4]), "(2)",
						"[][][][][bold=1][]" },
				{ "", "Cumulative Net Life Income",
						"" + dollar.format(portNetSpendable), "",
						"" + dollar.format(trustNetSpendable), "",
						"[][][][][bold=1][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{ "", "Tax Saved from Deduction", "" + dollar.format(0), "",
						"" + dollar.format(totalDeduction), "",
						"[][][][][bold=1][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{
						"Summary at life expectancy ("
								+ Integer.toString((int) crt.getClientLe())
								+ " years)", "", "Outright Sale", "", "CRT",
						"",
						"[colspan=2,Bold=1][][align=center,border=b][][align=center,bold=1,border=b][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{ "", "Life Income plus Income Tax Savings",
						"" + dollar.format(portNetSpendable), "",
						"" + dollar.format(trustNetSpendable), "",
						"[][][][][bold=1][]" },
				{
						"",
						"Net passing to Family - after tax (4)",
						""
								+ dollar
										.format(noCrt[(int) crt.getFinalDeath() - 1][3]
												- (noCrt[(int) crt
														.getFinalDeath() - 1][3] * crt
														.getEstateTaxRate())),
						"",
						"" + dollar.format(crt.getInsuranceBenefit()),
						"",
						"[][][color=" + Green + "][][bold=1,color=" + Green
								+ "][]" },
				{
						"",
						"Benefit to Charity(ies) (5)",
						"" + dollar.format(0),
						"",
						""
								+ dollar.format(crtValues[(int) crt
										.getFinalDeath() - 1][5]),
						"",
						"[][][color=" + Blue + "][][bold=1,color=" + Blue
								+ "][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{
						"",
						"Total to " + userInfo.getFirstName()
								+ ", Family & Charity",
						""
								+ dollar
										.format(portNetSpendable
												+ noCrt[(int) crt
														.getFinalDeath() - 1][3]
												- (noCrt[(int) crt
														.getFinalDeath() - 1][3] * crt
														.getEstateTaxRate())),
						"",
						""
								+ dollar
										.format(trustNetSpendable
												+ crt.getInsuranceBenefit()
												+ crtValues[(int) crt
														.getFinalDeath() - 1][5]),
						"", "[][][][][bold=1][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{
						"",
						"Difference",
						"",
						"",
						""
								+ dollar
										.format((trustNetSpendable
												+ crt.getInsuranceBenefit() + crtValues[(int) crt
												.getFinalDeath() - 1][5])
												- (portNetSpendable
														+ noCrt[(int) crt
																.getFinalDeath() - 1][3] - (noCrt[(int) crt
														.getFinalDeath() - 1][3] * crt
														.getEstateTaxRate()))),
						"", "[][][][][bold=1][]" },
				{ " ", "", "", "", "", "", "[colspan=6][][][][][]" },
				{
						"1.",
						"Assumes a pretax investment return of "
								+ percent.format(crt.getInvestmentReturn())
								+ " and a spending rate of "
								+ percent.format(crt.getPayoutRate())
								+ " (less tax). The balance (after tax) is reinvested in a growing portfolio.",
						"",
						"",
						"",
						"",
						"[align=right,valign=top,ptSize=11,font=GARA.TTF][colspan=4,valign=top,ptSize=11,font=GARA.TTF][][][][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{
						"2.",
						"Assumes a pretax investment return of "
								+ percent.format(crt.getInvestmentReturn())
								+ " and a payout rate of "
								+ percent.format(crt.getPayoutRate())
								+ ", reduced by taxes and a life insurance premium of "
								+ dollar.format(crt.getInsuranceBenefit())
								+ ". Balance, 1%, grows inside the trust.",
						"",
						"",
						"",
						"",
						"[align=right,valign=top,ptSize=11,font=GARA.TTF][colspan=4,valign=top,ptSize=11,font=GARA.TTF][][][][]" },
				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },

				{
						"3.",
						"In the early years, income is increased due to income tax savings.",
						"",
						"",
						"",
						"",
						"[align=right,valign=top,ptSize=11,font=GARA.TTF][colspan=4,valign=top,ptSize=11,font=GARA.TTF][][][][]" },

				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{
						"4.",
						"Equals the growing portfolio, less estate tax vs. tax free life insurance.",
						"",
						"",
						"",
						"",
						"[align=right,valign=top,ptSize=11,font=GARA.TTF][colspan=4,valign=top,ptSize=11,font=GARA.TTF][][][][]" },

				{ "", "", "", "", "", "", "[colspan=6][][][][][]" },
				{
						"5.",
						"Amount left in the Charitable Remainder Trust upon projected "
								+ s1
								+ " in "
								+ Integer
										.toString((int) crt.getFinalDeath())
								+ " years.",
						"",
						"",
						"",
						"",
						"[align=right,valign=top,ptSize=11,font=GARA.TTF][colspan=4,valign=top,ptSize=11,font=GARA.TTF][][][][]" },

		};
		float widths[] = { .05f, .40f, .225f, .05f, .225f, .05f };
		int alignments[] = { Element.ALIGN_LEFT, Element.ALIGN_LEFT,
				Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT,
				Element.ALIGN_LEFT };
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);
		// table.setTableFont("GARA.TTF");
		// table.setTableFontBold("GARABD.TTF");
		table.setFontSize(10);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");

		table.buildTableEx(cells);
		table.drawTable();

		newPage();
	}

	public void page4() {
		drawBorder(pageIcon, "CRUT");
		drawHeader(clientHeading, "Charitable Remainder Trust");

		int lifeExpectancy = (int) crt.getClientLe();

		double wTable[][] = crt.getCrt();
		double oTable[][] = crt.getNoCrt();

		// Draw first chart
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Outright Sale";
		String series2 = "CRT";

		for (int i = 0; i < lifeExpectancy; i++) {
			dataSet.addValue(wTable[i][5], series2, Integer.toString(i));
			dataSet.addValue(oTable[i][3], series1, Integer.toString(i));
		}

		Color t1 = new Color(57, 202, 100);
		Color t2 = new Color(57, 137, 250);
		BarChart barChart = new BarChart();
		barChart.setTitle("Investment Portfolio");
		barChart.setDataSet(dataSet);
		Rectangle bRect = new Rectangle(prctULeft);
		barChart.setRect(bRect);
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);
		barChart.generateBarChart();
		// barChart.setColor(0, Color.GREEN);
		barChart.setColor(0, t1);
		// barChart.setColor(1,Color.RED);
		barChart.setColor(1, t2);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, bRect, 0, 72);

		AreaChart areaChart1 = new AreaChart();
		areaChart1.setTitle("Investment Portfolio");
		areaChart1.setDataSet(dataSet);
		Rectangle cRect = new Rectangle(prctULeft);
		areaChart1.setRect(cRect);
		areaChart1.setDomainAxisLabel("Years");
		areaChart1.generateAreaChart();
		// Image img = areaChart1.getBarChartImage();
		// drawDiagram(img, cRect, 0,72);

		// Draw second Chart
		dataSet.clear();
		barChart = null;
		img = null;
		barChart = new BarChart();
		cRect = new Rectangle(prctURight);
		barChart.setRect(cRect);
		barChart.setTitle("Net Spendable");
		barChart.setDomainAxisLabel("Years");
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);

		for (int i = 0; i < lifeExpectancy; i++) {
			dataSet.addValue(wTable[i][4], series2, Integer.toString(i));
			dataSet.addValue(oTable[i][2], series1, Integer.toString(i));
		}

		barChart.setDataSet(dataSet);
		barChart.generateBarChart();
		barChart.setColor(0, t1);
		barChart.setColor(1, t2);
		img = barChart.getBarChartImage();
		drawDiagram(img, cRect, 0, 72);

		// Draw Third Chart
		dataSet.clear();
		barChart = null;
		img = null;
		barChart = new BarChart();
		cRect = new Rectangle(prctLLeft);
		barChart.setRect(cRect);
		barChart.setTitle("Net Amount Passing to Family");
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);

		for (int i = 0; i < lifeExpectancy; i++) {
			dataSet.addValue(crt.getInsuranceBenefit(), series2, Integer
					.toString(i));
			dataSet.addValue(oTable[i][3] * (1 - crt.getEstateTaxRate()),
					series1, Integer.toString(i));
		}

		barChart.setDataSet(dataSet);

		barChart.generateBarChart();
		barChart.setColor(0, t1);
		barChart.setColor(1, t2);
		img = barChart.getBarChartImage();
		drawDiagram(img, cRect, 0, 72);

		// Draw Fourth Chart
		dataSet.clear();
		barChart = null;
		img = null;
		barChart = new BarChart();
		cRect = new Rectangle(prctLRight);
		barChart.setRect(cRect);
		barChart.setTitle("Amount Passing to Charity");
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);

		for (int i = 0; i < lifeExpectancy; i++) {
			dataSet.addValue(wTable[i][5], series2, Integer.toString(i));
			// dataSet.addValue(0, series1, Integer.toString(i));
		}
		barChart.setDataSet(dataSet);
		barChart.generateBarChart();
		barChart.setColor(0, t1);
		// barChart.setColor(1,Color.RED);
		img = barChart.getBarChartImage();
		drawDiagram(img, cRect, 0, 72);
		newPage();
	}

	public void page5() {
		Date now = new Date();

		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		DecimalFormat cdf = new DecimalFormat("##.#####");

		drawBorder(pageIcon, "CRUT");
		drawHeader(clientHeading, "Charitable Remainder Trust");

		PageTable table = new PageTable(writer);
		Rectangle rctTable = new Rectangle(prctFull);
		Rectangle rctTable2 = new Rectangle(prctFull);

		rctTable.setRight(rctTable.getLeft() + (rctTable.getWidth() * .75f));
		rctTable2.setLeft((rctTable2.getRight() - rctTable2.getWidth() * .25f)
				+ _1_8TH);
		rctTable2.setRight(rctTable2.getRight() + (_1_4TH * 3));

		table.setTableFormat(rctTable, 13);
		int Red = makeColor(202, 137, 157);
		int Green = makeColor(137, 202, 157);
		int Blue = makeColor(0, 0, 128);
		
		String spouseString = "";
		
		if(crt.getSpouseAge()>0){
			spouseString = "(" + crt.getSpouseLe() + ")";
		}
		
		String cells[][] = {
				{
						"Assumptions",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"[border=b,Colspan=13,align=center,ptsize=8,Bold=1][][][][][][][][][][][][]",

				},

				{ "", "Property Value", "", "", "",
						"" + number.format(crt.getFmv()), "",
						"Date Calculated", "", "", "", "", "" + df.format(now),
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{ "", "Liability", "", "", "",
						"" + number.format(crt.getLiability()), "",
						"IRC Sec. 7520(b) rate used", "", "", "", "",
						"" + percent.format(crt.getIrsRate()),
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{
						"",
						"Basis",
						"",
						"",
						"",
						"" + number.format(crt.getBasis()),
						"",
						"Charitable Deduction Factor",
						"",
						"",
						"",
						"",
						""
								+ cdf.format(crt
										.getCharitableDeductionFactor()),
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{ "", "Maginal Income Tax Rate (Fed and State)", "", "", "",
						"" + percent.format(crt.getMarginalTaxRate()), "",
						"Charitable Deduction", "", "", "", "",
						"" + number.format(crt.getCharitableDeduction()),
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{ "", "Future Estate Tax Rate", "", "", "",
						"" + percent.format(crt.getEstateTaxRate()), "",
						"Adjusted Gross Income (AGI) before payout", "", "",
						"", "", "" + number.format(crt.getAgi()),
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{ "", "Capital Gains Rate (Fed and State)", "", "", "",
						"" + percent.format(crt.getCapitalGainsTax()), "",
						"Deduction Limitation of AGI", "", "", "", "",
						"" + percent.format(.3),
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{
						"",
						"Investment Return",
						"",
						"",
						"",
						"" + percent.format(crt.getInvestmentReturn()),
						"",
						"Donor" + (isSingle ? "(s)" : ""),
						"",
						"",
						"",
						"" + userInfo.getFirstName(),
						"",
						"[][colspan=4][][][][][][colspan=3][][][colspan=2,align=right][]", },
				{ "", "Spending/Payout Rate(1)", "", "", "",
						"" + percent.format(crt.getPayoutRate()), "",
						"Life Expectancy (Average per tables)(A)", "", "", "",
						"", "" + "(" + crt.getClientLe() + ")" + spouseString,
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{
						"",
						"Capital Gains Tax",
						"",
						"",
						"",
						""
								+ number.format(crt.getCapitalGains()), "",
						"Insurance(4)", "", "", "", "",
						"" + number.format(crt.getInsuranceBenefit()),
						"[][colspan=4][][][][][][colspan=5][][][][][]",

				},
				{ "", "", "", "", "", "", "", "", "", "", "", "", "",
						"[colspan=13,border=t][][][][][][][][][][][][]",

				},

				{
						"Outright Sale",
						"",
						"",
						"",
						"",
						"",
						"Sale With Charitable Remainder Trust (CRT)",
						"",
						"",
						"",
						"",
						"",
						"",
						"[align=center,ptsize=8,Bold=1,colspan=6,border=r][][][][][][align=center,ptsize=8,Bold=1,colspan=7][][][][][][]",

				},
				{
						"Year",
						"",
						"Investment Return",
						"Income Tax(2)",
						"Net(1) Spendable",
						"Portfolio Balance",
						"Year",
						"Annual Payout",
						"Charitable Deduction (3)",
						"Insurance Premiums",
						"Income Tax(2)",
						"Net Spendable",
						"Trust Balance",
						"[colspan=2,border=b,align=right][][border=b,align=right][border=b,align=right][border=b,align=right][border=r+b,align=right][border=b,align=right][border=b,align=right][border=b,align=right][border=b,align=right][border=b,align=right][border=b,align=right][border=b,align=right]",

				},

		};

		table.setFontSize(6);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		float widths[] = { .9f, .30f, 1, 1, 1, 1, .5f, 1, 1, 1, 1, 1, 1 };

		table.setColumnWidths(widths);
		table.buildTableEx(cells);

		double wTable[][] = crt.getCrt();
		double oTable[][] = crt.getNoCrt();

		double wnet = 0;
		double onet = 0;
		double iSaved = 0;
		for (int i = 0; i < crt.getFinalDeath(); i++) {
			wnet += wTable[i][4];
			onet += oTable[i][2];
			iSaved += wTable[i][2];
		}

		iSaved = iSaved * crt.getMarginalTaxRate();

		String singleRow[][] = { { "", "0", "", "", "",
				"" + number.format(oTable[0][3]), "0", "", "", "", "", "",
				"" + number.format(crt.getFmv()),
				"[][border=r][][][][border=r][border=r][][][][][][]" } };

		int lifeExpectancy = (int) crt.getFinalDeath();

		int alignments[] = { PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT };
		table.setColumnAlignments(alignments);
		// table.buildTableEx(singleRow);

		double oTotal = 0;
		double wTotal = 0;

		for (int i = 0; i < crt.getFinalDeath(); i++) {
			oTotal += oTable[i][2];
			wTotal += wTable[i][4];
			StringBuffer arg = new StringBuffer("");

			if (i == (int) crt.getClientLe())
				arg.append("(A)");

			String sr[] = { arg.toString(), "" + i,
					"" + number.format(oTable[i][0]),
					"" + number.format(oTable[i][1]),
					"" + number.format(oTable[i][2]),
					"" + number.format(oTable[i][3]), "" + i,
					"" + number.format(wTable[i][0]),
					"" + number.format(wTable[i][1]),
					"" + number.format(wTable[i][2]),
					"" + number.format(wTable[i][3]),
					"" + number.format(wTable[i][4]),
					"" + number.format(wTable[i][5]),
					"[][border=r][][][][border=r][border=r][][][][][][]"

			};

			singleRow[0] = sr;
			table.buildTableEx(singleRow);

		}
		String sTotal[] = { "", "", "", "", "" + number.format(oTotal), "", "",
				"", "", "", "", "" + number.format(wTotal), "",
				"[][][][][border=t][][][][][][][border=t][]" };

		singleRow[0] = sTotal;
		table.buildTableEx(singleRow);

		table.drawTable();

		double mitr = ((crt.getTaxIncome() / crt.getPayoutRate()) * crt
				.getMarginalTaxRate())
				+ ((crt.getTaxGrowth() / crt.getPayoutRate()) * crt
						.getCapitalGainsTax());

		table = new PageTable(writer);
		table.setTableFormat(rctTable2, 5);
		table.setFontSize(6);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");

		String cells2[][] = {
				{ "", "Summary of Outright Sale", "", "", "",
						"[][bold=1,colspan=6][][][]" },

				{ "", "Estate Impact @ Death:", "", "",
						"" + integer.format(lifeExpectancy) + "th year",
						"[][bold=1,colspan=3,border=t+r+b+l,][][][bold=1,align=right,border=t+r+b+l]" },
				{ "", "Portfolio Balance", "", "",
						"" + number.format(oTable[(int)crt.getFinalDeath()-1][3]),
						"[][bold=1,colspan=3,border=l+r][][][border=r,align=right]" },
				{
						"",
						"Estate Tax",
						"",
						"",
						""
								+ number
										.format((oTable[(int)crt.getFinalDeath() - 1][3] * crt
												.getEstateTaxRate())),
						"[][bold=1,colspan=3,border=l+r,color=" + Red
								+ "][][][border=r,align=right,color=" + Red
								+ "]" },

				{
						"",
						"Net to Family",
						"",
						"",
						""
								+ number.format(oTable[(int)crt.getFinalDeath()-1][3]
										- (oTable[(int)crt.getFinalDeath() - 1][3] * crt
												.getEstateTaxRate())),
						"[][bold=1,colspan=3,border=l+r,color=" + Green
								+ "][][][border=r,align=right,color=" + Green
								+ "]" },
				{
						"",
						"Net To Charity",
						"",
						"",
						"" + number.format(0),
						"[][bold=1,colspan=3,border=l+r+b,color=" + Blue
								+ "][][][border=r+b,align=right,color=" + Blue
								+ "]" },
				{ " ", "", "", "", "", "[][][][][]" },
				{
						"1)",
						"Net spendable is figured by taking a spendable rate ("
								+ percent.format(crt.getPayoutRate())
								+ ") multiplied by the period beginning balance and subtracting the income tax. The spendable rate is comparable to the CRT payout rate. The unspent growth is added to the portfolio balance.",
						"", "", "", "[align=right,valign=top][colspan=4][][][]" },
				{
						"2)",
						"Income tax is figured by taking the rate of ordinary income ("
								+ percent.format(crt.getTaxIncome()) 
								+ ") multiplied by the marginal income tax rate ("
								+ percent.format(crt.getMarginalTaxRate())
								+ ") and adding that to the realized capital gain ("
								+ percent.format(crt.getPayoutRate()) + " - "
								+ percent.format(crt.getTaxIncome())
								+ " = "
								+ percent.format(crt.getTaxGrowth())
								+ ") multiplied by the capital gains rate ("
								+ percent.format(crt.getCapitalGainsTax())
								+ "). This yields a marginal tax rate of "
								+ percent.format(mitr) + ".", "", "", "",
						"[align=right,valign=top][colspan=4][][][]" },

				{
						"3)",
						"Deduction is subject to 30% of AGI; 5 year carry forward.",
						"", "", "", "[align=right,valign=top][colspan=4][][][]" },

				{
						"4)",
						"Insurance amounts/value/premium duration based upon current assumptions, major carrier, NOT GUARANTEED",
						"", "", "", "[align=right,valign=top][colspan=4][][][]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ "", "Summary of Sale with CRT", "", "", "",
						"[][bold=1,colspan=6][][][]" },

				{
						"",
						"Estate Impact @ Death:",
						"",
						"",
						"" + integer.format((int) crt.getClientLe())
								+ "th year",
						"[][colspan=3,bold=1,border=r+t+l+b][][][bold=1,border=r+t+l+b,align=center]" },

				{ "", "Trust Balance", "", "",
						"" + number.format(wTable[lifeExpectancy - 1][5]),
						"[][colspan=3,bold=1,border=r+l][][][border=r+l,align=right]" },
				{
						"",
						"Estate Tax",
						"",
						"",
						"0",
						"[][Colspan=3,border=l+r,color=" + Red
								+ "][][][align=right,border=l+r,color=" + Red
								+ "]" },
				{
						"",
						"Net to Family (Ins.)(4)",
						"",
						"",
						"" + number.format(crt.getInsuranceBenefit()),
						"[][Colspan=3,border=l+r,color=" + Green
								+ "][][][align=right,border=l+r,color=" + Green
								+ "]" },
				{
						"",
						"Net to Charity",
						"",
						"",
						"" + number.format(wTable[lifeExpectancy - 1][5]),
						"[][Colspan=3,border=l+r+b,color=" + Blue
								+ "][][][align=right,border=l+r+b,color="
								+ Blue + "]" },

				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{ "", "Extra Net Spendable(cumul.) w/CRT", "", "",
						"" + number.format(wnet - onet),
						"[][bold=1,colspan=3,border=r+t+l+b][][][border=r+t+l+b,align=Right]" },
				{
						"",
						"Premiums Gifted (4)",
						"",
						"",
						""
								+ number.format(crt.getFinalDeath()
										* crt.getInsurancePremium()),
						"[][bold=1,colspan=3,border=l+b+r][][][align=right,border=l+b+r]" },
				{ " ", "", "", "", "", "[colspan=5][][][][]" },
				{
						"",
						"Taxes Saved",
						"",
						"",
						"" + integer.format((int) crt.getFinalDeath())
								+ "th year",
						"[][colspan=3,bold=1,border=l+t+r+b][][][align=center,bold=1,border=l+t+r+b]" },
				{
						"",
						"Capital Gains Tax",
						"",
						"",
						""
								+ number.format(crt.getCapitalGains()
										* crt.getCapitalGainsTax()),
						"[][colspan=3,border=l+r][][][align=right,border=l+r]" },

				{ "", "Income Tax (3)", "", "", "" + number.format(iSaved),
						"[][colspan=3,border=l+r][][][align=right,border=l+r]" },

				{
						"",
						"Estate Tax",
						"",
						"",
						""
								+ number.format(oTable[lifeExpectancy - 1][3]
										* crt.getEstateTaxRate()),
						"[][colspan=3,border=l+r][][][align=right,border=l+r]" },
				{
						"",
						"Total Taxes Saved",
						"",
						"",
						""
								+ number.format((crt.getCapitalGains() * crt
										.getCapitalGainsTax())
										+ iSaved
										+ (oTable[lifeExpectancy - 1][3] * crt
												.getEstateTaxRate())),
						"[][colspan=3,border=l+b+r+t][][][align=right,border=l+b+r+t]" },

		};
		float widths2[] = { .25f, 1, 1, 1, 1 };
		table.setColumnWidths(widths2);
		table.buildTableEx(cells2);
		table.setCellLeading(1.25f);
		table.drawTable();
		newPage();
	}

	public void setCrt(CRT crt) {
		this.crt = crt;
	}
}
