/*
 * Created on Apr 27, 2005
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

import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.BarChart;
import com.teag.reports.GRATPages.CustomLabelGenerator;
import com.teag.reports.GRATPages.CustomToolTipGenerator;

/**
 * @author Paul Stay
 * 
 */
public class QPRTPages extends Page {

	private String assetName;

	private double assetValue;

	private double lifeExpectancy;

	private double growthRate;

	private double estateTaxRate;

	private double projectedValue;

	private double clientQprtGift;

	private double spouseQprtGift;

	private String rType;

	private int clientTerm;

	private int spouseTerm;

	private int numberOfTrusts;

	private String client;

	public QPRTPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}

	public void draw() {
		page1();
		newPage();
		page2();
		newPage();
		page3();
	}

	/**
	 * @return Returns the assetName.
	 */
	public String getAssetName() {
		return assetName;
	}

	/**
	 * @return Returns the assetValue.
	 */
	public double getAssetValue() {
		return assetValue;
	}

	/**
	 * @return Returns the client.
	 */
	public String getClient() {
		return client;
	}

	public double getClientQprtGift() {
		return clientQprtGift;
	}

	public int getClientTerm() {
		return clientTerm;
	}

	/**
	 * @return Returns the estateTaxRate.
	 */
	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	/**
	 * @return Returns the growthRate.
	 */
	public double getGrowthRate() {
		return growthRate;
	}

	/**
	 * @return Returns the lifeExpectancy.
	 */
	public double getLifeExpectancy() {
		return lifeExpectancy;
	}

	public int getNumberOfTrusts() {
		return numberOfTrusts;
	}

	/**
	 * @return Returns the projectedValue.
	 */
	public double getProjectedValue() {
		return projectedValue;
	}

	public String getRType() {
		return rType;
	}

	public double getSpouseQprtGift() {
		return spouseQprtGift;
	}

	public int getSpouseTerm() {
		return spouseTerm;
	}

	private void page1() {
		int Red = makeColor(192, 0, 0);
		int Green = makeColor(0, 192, 0);
		double _estateTax = this.projectedValue * this.estateTaxRate;
		double _netToFam = this.projectedValue - _estateTax;
		drawBorder(pageIcon, "QPRT");
		drawHeader(client, "The Problem");

		String qprtIntro[] = { "Your "
				+ this.assetName
				+ " has developed financial and sentimental value for your family. However, its fair market value and projected growth ( "
				+ percent.format(this.growthRate)
				+ " per year) continue to add to your estate tax liability.\n\n" };
		Rectangle rct = drawSection(prctTop, "", qprtIntro, 0, 14, 12);

		float left = rct.getLeft() + (rct.getWidth() * .125f);
		float right = rct.getRight() - (rct.getWidth() * .125f);

		rct.setLeft(left);
		rct.setRight(right);
		rct.setTop(rct.getBottom());
		rct.setBottom(prctTop.getBottom());

		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 2);
		float widths[] = { .75f, .25f };
		table.setTableFont("GARA.TTF");
		table.setTableFontBold("GARABD.TTF");
		table.setColumnWidths(widths);

		String cells[][] = {
				{ "Today's Fair Market Value",
						"" + dollar.format(this.assetValue), "[][align=right]" },
				{ "Projected years of life expectancy",
						"" + number.format(this.lifeExpectancy),
						"[][align=right]" },
				{ "Growth Rate", "" + percent.format(this.growthRate),
						"[][align=right]" },
				{ "", "", "[][]" },
				{ "Value (projected) in Taxable Estate",
						"" + dollar.format(this.projectedValue),
						"[][align=right]" },
				{ "", "", "[][]" },
				{
						"Estate Tax on " + this.assetName + " ("
								+ percent.format(this.estateTaxRate) + ")",
						"" + dollar.format(_estateTax),
						"[color=" + Red + "][align=right,color=" + Red + "]" },
				{ "", "", "[][]" },
				{
						"Net Value of " + this.assetName
								+ " passing to family.",
						"" + dollar.format(_netToFam),
						"[color=" + Green + "][align=right,color=" + Green
								+ "]" },

		};
		table.setFontSize(12);
		table.buildTableEx(cells);
		table.drawTable();

		Color green = new Color(Green);
		Color red = new Color(Red);

		int et = (int) (this.estateTaxRate * 100);
		int tofam = 100 - et;
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Estate Tax", et);
		dataset.setValue("Net to Family", tofam);

		PiePlot3D plot = new PiePlot3D(dataset);
		plot.setLabelGenerator(new StandardPieItemLabelGenerator());
		plot.setInsets(new Insets(0, 5, 5, 5));
		plot.setToolTipGenerator(new CustomToolTipGenerator());
		plot.setLabelGenerator(new CustomLabelGenerator());
		plot.setSectionPaint(0, red);
		plot.setSectionPaint(1, green);
		plot.setForegroundAlpha(.6f);
		plot.setOutlinePaint(Color.white);
		plot.setBackgroundPaint(Color.white);

		String title = "Vacation Home";

		if (rType.equalsIgnoreCase("vacation")) {
			title = "Vacation Home";
		} else {
			title = "Residence";
		}
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

		String intro2[] = {
				"When one's estate (and the estate tax liability) is growing faster than can be reduced by spending and normal (tax free) gifting to family, leveraged gifts can be very effective.  This process discounts the value of the assets being transferred, resulting in the reduction of estate taxes.",
				" ",
				"Let's examine a leveraging technique that is often utilized with a personal (including vacation) residence." };

		this.drawSection(prctLRight, "", intro2, 0, 14, 12);

	}

	private void page2() {
		drawBorder(pageIcon, "QPRT");
		drawHeader(client, "Qualified Personal Residence Trust (QPRT)");
		Rectangle rct = new Rectangle(prctTop);
		rct.setBottom(rct.getBottom() - Page._1_4TH);
		rct.setLeft(rct.getLeft() - Page._1_4TH);
		if (userInfo.isSingle() || numberOfTrusts == 1) {
			if (userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("QPRT1-M.png", rct, LEFT + RIGHT);
			} else {
				drawDiagram("QPRT1-F.png", rct, LEFT + RIGHT);
			}
		} else {
			drawDiagram("QPRT1.png", rct, LEFT + RIGHT);
		}
		String[] theProcess;

		if (numberOfTrusts == 1) {
			theProcess = new String[] {
					"A Qualified Personal Residence Trust is established transfering your interest in the "
							+ this.assetName
							+ " to the trust. If partial interest gifts are made, the interest in the trust may be eligible for a fractional interest discout (illustrated at 15%) before gift value calculations.",
					"You retain all of the rights and obligations of ownership for the trust period.",
					"At the end of the trust term your interest in the residence pass to your children without further estate/gift taxation." };
		} else {

			theProcess = new String[] {
					"Two Qualified Personal Residence Trusts are established, one for each of you, and your respective half interests in the "
							+ assetName
							+ " are transferred to the trusts.  When we separate your interests into two shares, the value of each 1/2 interest may be eligible for a fractional interest discount (illustrated at 15%) before gift value calculations.",
					"You each retain all of the rights and obligations of ownership for your respective trust periods.",
					"At the end of the trust terms your respective interests in the residence pass to your children without further estate/gift taxation." };
		}

		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		drawSection(rct, "The Process", theProcess, 1);

		String benefits[] = {
				"Allows the transfer of a residence at a lower taxable gift value.",
				"The longer the trust period, the lower the gift value.",
				"Gift future growth out of the estate.",
				"At the end of the trust period, the full ownership has been transferred to your family." };

		rct = new Rectangle(prctLRight);
		Rectangle r;
		rct.setTop(rct.getTop() - Page._1_4TH);
		rct.setRight(rct.getRight() + Page._1_2TH);
		r = drawSection(rct, "Benefits of Using this Technique", benefits, 2);

		String singleEffects[] = {
				"Should you not survive the trust period, your share of the residence reverts to your taxable estate at the fair market value upon date of death.",
				"Heirs do not receive a step up in basis upon the death of parent, therefore will recognize a capital gain upon subsequent sale." };

		String effects[] = {
				"Should you not survive the trust period, the decedent's share of the residence reverts to your taxable estate at the fair market value upon date of death.",
				"Heirs do not receive a step up in basis upon the death of parent, therefore will recognize a capital gain upon subsequent sale." };

		rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight()));
		if (userInfo.isSingle()) {
			r = drawSection(rct,
					"Side effects/Draw backs of using this technique",
					singleEffects, 2);

		} else {
			r = drawSection(rct,
					"Side effects/Draw backs of using this technique", effects,
					2);
		}
	}

	private void page3() {
		int Red = makeColor(192, 0, 0);
		int Green = makeColor(0, 192, 0);

		drawBorder(pageIcon, "QPRT");
		drawHeader(userInfo.getClientHeading(), ((int) lifeExpectancy)
				+ " Year Comparison");

		PageTable clientTable = new PageTable(writer);
		clientTable.setTableFormat(prctULeft, 5);
		clientTable.setTableFont("GARA.TTF");
		clientTable.setTableFontBold("GARABD.TTF");
		clientTable.setFontSize(10);
		float widths[] = { .5f, .20f, .10f, .20f, .05f };
		clientTable.setColumnWidths(widths);
		String clientRows[][];

		if (userInfo.isSingle() || numberOfTrusts == 1) {
			clientRows = new String[][] {
					{ "" + userInfo.getClientFirstName(), "Keep in the Estate",
							"", number.format(clientTerm) + " Year QPRT", "",
							"[][border=b,align=center][][border=b,align=center][]" },
					{ "Value for Estate/Gift Taxes",
							"" + dollar.format(projectedValue), "",
							"" + dollar.format(clientQprtGift), "",
							"[][align=right][][align=right][]" },
					{
							"Estate/Gift Taxes("
									+ percent.format(this.estateTaxRate) + ")",
							""
									+ dollar.format((projectedValue)
											* estateTaxRate),
							"",
							"" +  "0",//dollar.format(clientQprtGift * estateTaxRate),
							"*",
							"[color=" + Red + "][align=right,color=" + Red
									+ "][][align=right,color=" + Red
									+ "][color=" + Red + "]" },
					{
							"Benefit to Heirs",
							""
									+ dollar
											.format(projectedValue),
							"",
							"[color=" + Green + "][align=right,color=" + Green
									+ "][][align=right,color=" + Green
									+ "][color=" + Green + "]" }, };

		} else {
			clientRows = new String[][] {
					{ "" + userInfo.getClientFirstName() + "'s one half",
							"Keep in the Estate", "",
							number.format(clientTerm) + " Year QPRT", "",
							"[][border=b,align=center][][border=b,align=center][]" },
					{ "Value for Estate/Gift Taxes",
							"" + dollar.format(projectedValue / 2), "",
							"" + dollar.format(clientQprtGift), "",
							"[][align=right][][align=right][]" },
					{
							"Estate/Gift Taxes("
									+ percent.format(this.estateTaxRate) + ")",
							""
									+ dollar.format((projectedValue / 2)
											* estateTaxRate),
							"",
							"0",// + dollar.format(clientQprtGift * estateTaxRate),
							"*",
							"[color=" + Red + "][align=right,color=" + Red
									+ "][][align=right,color=" + Red
									+ "][color=" + Red + "]" },
					{
							"Benefit to Heirs",
							dollar.format((projectedValue / 2)
									- (projectedValue / 2) * estateTaxRate),
							"",
							""
									+ dollar.format(projectedValue/2),
							"",
							"[color=" + Green + "][align=right,color=" + Green
									+ "][][align=right,color=" + Green
									+ "][color=" + Green + "]" },

			};
		}

		clientTable.buildTableEx(clientRows);
		clientTable.drawTable();

		Rectangle r = clientTable.getResultRect();
		Rectangle rct = new Rectangle(prctULeft);
		rct.setBottom(rct.getBottom() - r.getHeight());
		rct.setLeft(rct.getLeft() - _1_4TH);
		if (userInfo.isSingle() || numberOfTrusts == 1) {
			if (userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("QPRT2-M.png", rct, BOTTOM + LEFT);
			} else {
				drawDiagram("QPRT2-F.png", rct, BOTTOM + LEFT);
			}
		} else {
			drawDiagram("QPRT2.png", rct, BOTTOM + LEFT);
		}

		clientTable = new PageTable(writer);
		clientTable.setTableFormat(prctURight, 5);
		clientTable.setTableFont("GARA.TTF");
		clientTable.setTableFontBold("GARABD.TTF");
		clientTable.setFontSize(10);
		clientTable.setColumnWidths(widths);
		if (!userInfo.isSingle() && numberOfTrusts == 2) {
			String spouseRows[][] = {
					{ "" + userInfo.getSpouseFirstName() + "'s one half",
							"Keep in the Estate", "",
							number.format(spouseTerm) + " Year QPRT", "",
							"[][border=b,align=center][][border=b,align=center][]" },
					{ "Value for Estate/Gift Taxes",
							"" + dollar.format(projectedValue / 2), "",
							"" + dollar.format(spouseQprtGift), "",
							"[][align=right][][align=right][]" },
					{
							"Estate/Gift Taxes("
									+ percent.format(this.estateTaxRate) + ")",
							""
									+ dollar.format((projectedValue / 2)
											* estateTaxRate),
							"",
							"0",// + dollar.format(spouseQprtGift * estateTaxRate),
							"*",
							"[color=" + Red + "][align=right,color=" + Red
									+ "][][align=right,color=" + Red
									+ "][color=" + Red + "]" },
					{
							"Benefit to Heirs",
							dollar.format((projectedValue / 2)
									- (projectedValue / 2) * estateTaxRate),
							"",
							""
									+ dollar.format(projectedValue/2),
							"",
							"[color=" + Green + "][align=right,color=" + Green
									+ "][][align=right,color=" + Green
									+ "][color=" + Green + "]" },

			};
			DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			String series1 = "Keep in Estate";
			String series2 = "QPRT";

			String cat1 = userInfo.getClientFirstName();
			String cat2 = userInfo.getSpouseFirstName();

			double estateTax = projectedValue/2 * estateTaxRate;

			dataSet.addValue(projectedValue/2, series1, cat1);
			dataSet.addValue(clientQprtGift, series2, cat1);


			dataSet.addValue(projectedValue/2, series1, cat2);
			dataSet.addValue(spouseQprtGift, series2, cat2);
			BarChart barChart = new BarChart();

			barChart.setTitle("Taxable Transfer");
			barChart.setDataSet(dataSet);

			Rectangle barRect = new Rectangle(prctLRight);
			barRect.setTop(2.939f * 72);
			barRect.setBottom(0.5f * 72);
			barRect.setRight(barRect.getRight() - (2 * _1_2TH));
			barChart.setRect(barRect);
			barChart.generateBarChart();
			barChart.setColor(0,new Color(0xff0000));
			barChart.setColor(1,new Color(0x880000));
			Image img = barChart.getBarChartImage();
			drawDiagram(img, prctLLeft, 0, 72);
			clientTable.buildTableEx(spouseRows);
			clientTable.drawTable();
			
			dataSet = new DefaultCategoryDataset();
			dataSet.addValue(projectedValue/2 - estateTax, series1, cat1);
			dataSet.addValue(projectedValue/2 - (clientQprtGift*estateTaxRate), series2, cat1);
			dataSet.addValue(projectedValue/2 - estateTax, series1, cat2);
			dataSet.addValue(projectedValue/2 - (spouseQprtGift*estateTaxRate), series2, cat2);
			
			barChart = new BarChart();
			barRect = new Rectangle(prctLRight);
			barChart.setTitle("To Heirs");
			barChart.setDataSet(dataSet);

			barRect.setTop(2.939f * 72);
			barRect.setBottom(0.5f * 72);
			barRect.setRight(barRect.getRight() - (2 * _1_2TH));
			barChart.setRect(barRect);
			barChart.generateBarChart();
			barChart.setColor(0,new Color(0x00ff00));
			barChart.setColor(1,new Color(0x008800));
			img = barChart.getBarChartImage();
			drawDiagram(img, prctLRight, 0, 72);
			

			Rectangle lrct = new Rectangle(prctFull);
			lrct.setTop(lrct.getTop() - (2f * 72));
			lrct.setLeft(lrct.getLeft() + (2.35f * 72));
			// r = drawSection(rct, "Benefits of using this technique",
			// benefits,
			// 2);
			drawLabel(
					"1/2 Interest in Residence\n" + clientTerm + " Year Term",
					lrct, "GARA.TTF", Color.gray, 11f, LBL_LEFT, LBL_TOP);

			lrct.setLeft(lrct.getLeft() + I2P(4.26f));
			drawLabel(
					"1/2 Interest in Residence\n" + spouseTerm + " Year Term",
					lrct, "GARA.TTF", Color.gray, 11f, LBL_LEFT, LBL_TOP);
		} else {
			DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			String series1 = "Keep in Estate";
			String series2 = "QPRT";

			String cat1 = userInfo.getClientFirstName();

			double estateTax = projectedValue * estateTaxRate;

			dataSet.addValue(estateTax, series1, cat1);
			dataSet.addValue(clientQprtGift, series2, cat1);

			BarChart barChart = new BarChart();

			barChart.setTitle("Comparison of Taxable Transfer");
			barChart.setDataSet(dataSet);
			Rectangle barRect = new Rectangle(prctLLeft);
			barRect.setTop(3.1939f * 72);
			barRect.setBottom(0.5f * 72);
			barRect.setRight(barRect.getRight() -(2* _1_2TH));
			barChart.setRect(barRect);
			barChart.generateBarChart();
			Image img = barChart.getBarChartImage();
			drawDiagram(img, prctLLeft, Page.LEFT, 72);

			// r = drawSection(rct, "Benefits of using this technique",
			// benefits,
			// 2);
			if (userInfo.getClientGender().equalsIgnoreCase("M")) {
				Rectangle lrct = new Rectangle(prctFull);
				lrct.setTop(lrct.getTop() - (2f * 72));
				lrct.setLeft(lrct.getLeft() + (2.35f * 72));
				drawLabel(
						"Interest in Residence\n" + clientTerm + " Year Term",
						lrct, "GARA.TTF", Color.gray, 11f, LBL_LEFT, LBL_TOP);
			} else {
				Rectangle lrct = new Rectangle(prctFull);
				lrct.setTop(lrct.getTop() - (2f * 72));
				lrct.setLeft(lrct.getLeft() + I2P(4.26f));
				drawLabel(
						"Interest in Residence\n" + clientTerm + " Year Term",
						lrct, "GARA.TTF", Color.gray, 11f, LBL_LEFT, LBL_TOP);
			}
		}
		drawLabel("* Taxable gifts may be covered by the lifetime exemption.",
				prctBottom, "GARA.TTF", new Color(64, 64, 64), 9, LBL_LEFT,
				LBL_BOTTOM);
	}

	/**
	 * @param assetName
	 *            The assetName to set.
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	/**
	 * @param assetValue
	 *            The assetValue to set.
	 */
	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}

	/**
	 * @param client
	 *            The client to set.
	 */
	public void setClient(String client) {
		this.client = client;
	}

	public void setClientQprtGift(double clientQprtGift) {
		this.clientQprtGift = clientQprtGift;
	}

	void setClientTerm(int clientTerm) {
		this.clientTerm = clientTerm;
	}

	/**
	 * @param estateTaxRate
	 *            The estateTaxRate to set.
	 */
	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	/**
	 * @param growthRate
	 *            The growthRate to set.
	 */
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}

	/**
	 * @param lifeExpectancy
	 *            The lifeExpectancy to set.
	 */
	public void setLifeExpectancy(double lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public void setNumberOfTrusts(int numberOfTrusts) {
		this.numberOfTrusts = numberOfTrusts;
	}

	/**
	 * @param projectedValue
	 *            The projectedValue to set.
	 */
	public void setProjectedValue(double projectedValue) {
		this.projectedValue = projectedValue;
	}

	public void setRType(String type) {
		rType = type;
	}

	public void setSpouseQprtGift(double spouseQprtGift) {
		this.spouseQprtGift = spouseQprtGift;
	}

	void setSpouseTerm(int spouseTerm) {
		this.spouseTerm = spouseTerm;
	}
}
