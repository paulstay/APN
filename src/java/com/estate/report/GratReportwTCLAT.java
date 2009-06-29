package com.estate.report;

import java.awt.Color;
import java.awt.Insets;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.estate.controller.GratController;
import com.estate.pdf.CellInfo;
import com.estate.pdf.CellInfoFactory;
import com.estate.pdf.Page;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.bean.AdminBean;
import com.teag.chart.BarChart;

public class GratReportwTCLAT extends Page {

	/**
	 * A custom label generator (returns null for one item as a test).
	 */
	static class CustomLabelGenerator implements PieSectionLabelGenerator {

		/**
		 * Generates a label for a pie section.
		 * 
		 * @param dataset
		 *            the dataset (<code>null</code> not permitted).
		 * @param key
		 *            the section key (<code>null</code> not permitted).
		 * 
		 * @return the label (possibly <code>null</code>).
		 */
		public String generateSectionLabel(PieDataset dataset, Comparable key) {
			return null;
		}

	}
	static class CustomToolTipGenerator implements PieToolTipGenerator {
		public String generateToolTip(PieDataset dataset, Comparable key) {
			return "";
		}

	}
	public final static int INCH = 72;
	boolean useLLC = false;
	GratController grat;

	AdminBean admin;

	boolean isSingle = true;

	String gender = "M";

	public GratReportwTCLAT(Document document, PdfWriter writer) {
		super(document, writer);
	}

	public void draw() {
		grat.calculate();
		if (userInfo.getGender().equals("B"))
			isSingle = false;
		gender = userInfo.getGender();
		if (grat.isUseLLC())
			useLLC = true;
		else
			useLLC = false;

		page1();
		page2();
		page3();
		if (grat.isUseInsurance())
			page4();
		page5();
		page6();
	}

	public AdminBean getAdmin() {
		return admin;
	}

	public GratController getGrat() {
		return grat;
	}
	
	private String[][] gratCashFlow() {
		String rows[][] = new String[grat.getTerm()][7];
		double prin = grat.getFmv();
		double growth = grat.getAssetGrowth();
		double income = grat.getAssetIncome();
		double payout = grat.getAnnuityPayment();

		for (int i = 0; i < grat.getTerm(); i++) {
			rows[i][0] = Integer.toString(i + 1);
			rows[i][1] = dollar.format(prin);
			rows[i][2] = dollar.format(prin * growth);
			rows[i][3] = dollar.format(prin * income);
			rows[i][4] = dollar.format(-payout);
			prin = prin * (1 + growth + income) - payout;
			rows[i][5] = dollar.format(prin);
			rows[i][6] = "[border=l+r+b][border=r+b][border=r+b][border=r+b][border=r+b,color="
					+ makeColor(0, 0, 240) + "][border=r+b]";
		}
		return rows;
	}

	/**
	 * The Problem
	 */
	public void page1() {
		// Page 1
		Rectangle rct;

		drawBorder(pageIcon, "GRAT");

		float ptSize = 12f;
		drawHeader("", "The Problem");
		String gratExplanation = "\tDue to your sound financial acumen, your assets (Comm. Prop. of Minot, King Authur Park, Mountain Meadows, Alder Ridge Estates, Hidden Valley) provide steady growth and income. However, the fair market value plus growth and income not spent continue to add to your estate tax liability.";
		String gratExplanation2 = "\tWhen one's estate (and the estate tax liability) is growing faster than can be reduced by personal spending and normal (tax free) gifting to one's family, leveraged gifts can be very effective.  This process can reduce the value of the assets being transferred, often resulting in the reduction of taxes.\n\n\n"
				+ "Let's examine a double discount leveraging technique that is often utilized with this circumstance.";

		rct = drawLabel(gratExplanation, prctTop, "GARA.TTF",
				new Color(0, 0, 0), ptSize, LBL_LEFT, LBL_TOP);

		drawLabel(gratExplanation2, prctLRight, "GARA.TTF", new Color(0, 0, 0),
				ptSize, LBL_LEFT, LBL_CENTER);

		// Now we need to display the table
		float left = rct.getLeft() + (rct.getWidth() * .125f);
		float right = rct.getRight() - (rct.getWidth() * .125f);

		rct.setLeft(left);
		rct.setRight(right);
		rct.setTop(rct.getBottom());
		rct.setBottom(prctTop.getBottom());

		// drawFilledRect(rct, new Color(128,255,128));
		drawLine(prctTop.getLeft(), horizLineY, prctTop.getRight(), horizLineY,
				new Color(0, 72, 117));

		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 2);
		float widths[] = { .75f, .25f };
		CellInfoFactory cif = new CellInfoFactory();
		BaseFont baseFont = PageUtils.LoadFont("GARA.TTF");
		BaseFont baseFontBold = PageUtils.LoadFont("GARABD.TTF");

		cif.setDefaultFont(new Font(baseFont, ptSize));
		Color green = new Color(0, 176, 0);
		Color red = new Color(192, 0, 0);

		double balance = grat.getFmv();
		int fd = userInfo.getFinalDeath();
		if (fd <= 0)
			fd = grat.getTerm();
		for (int i = 0; i < fd; i++) {
			double g = balance * grat.getAssetGrowth();
			double d = balance * grat.getAssetIncome();
			balance += g + d;
		}

		CellInfo cellData[][] = {
				{
						cif.getCellInfo("Todays Fair Market Value"),
						cif.getCellInfo(dollar.format(grat.getFmv()),
								Element.ALIGN_RIGHT) },

				{
						cif.getCellInfo("Projected Years of Growth"),
						cif.getCellInfo("" + number.format(fd),
								Element.ALIGN_RIGHT) },

				{
						cif.getCellInfo("Average Growth Rate"),
						cif.getCellInfo(percent.format(grat.getAssetGrowth()),
								Element.ALIGN_RIGHT) },

				{
						cif.getCellInfo("Average Income Rate"),
						cif.getCellInfo(percent.format(grat.getAssetIncome()),
								Element.ALIGN_RIGHT) },

				{ cif.getCellInfo(" "), cif.getCellInfo(" ") },

				{
						cif.getCellInfo(
								"Total Value (projected) in Taxable Estate",
								new Font(baseFontBold, ptSize, Font.NORMAL)),
						cif.getCellInfo(dollar.format(balance),
								Element.ALIGN_RIGHT, new Font(baseFontBold,
										ptSize, Font.NORMAL)) },

				{
						cif
								.getCellInfo("Estate Tax ("
										+ percent.format(grat
												.getEstateTaxRate()) + ")",
										new Font(baseFontBold, ptSize,
												Font.NORMAL, red)),
						cif.getCellInfo(dollar.format(balance
								* grat.getEstateTaxRate()),
								Element.ALIGN_RIGHT, new Font(baseFontBold,
										ptSize, Font.NORMAL, red)) },

				{
						cif.getCellInfo("Net Value Passing to Family",
								new Font(baseFontBold, ptSize, Font.NORMAL,
										green)),
						cif.getCellInfo(dollar
								.format((balance - (balance * grat
										.getEstateTaxRate()))),
								Element.ALIGN_RIGHT, new Font(baseFontBold,
										ptSize, Font.NORMAL, green)) } };

		// Do the chart

		table.setColumnWidths(widths);
		table.setTableData(cellData);
		table.drawTable();

		int et = (int) (grat.getEstateTaxRate() * 100);
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

		JFreeChart chart = new JFreeChart("Asset Distribution",
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

		newPage();
	}

	/**
	 * The process
	 */

	public void page2() {
		// Page 2
		drawBorder(pageIcon, "GRAT");

		String clientHeading = userInfo.getFirstName() + " "
				+ userInfo.getLastName();
		drawHeader(clientHeading, "Grantor Retained Annuity Trust (GRAT)");

		Rectangle rct = new Rectangle(prctTop);
		rct.setBottom(rct.getBottom() - Page._1_4TH);
		rct.setLeft(rct.getLeft() - Page._1_4TH);
		if (isSingle) {
			if ((gender.equalsIgnoreCase("M"))) {
				if (useLLC) {
					drawDiagram("LLC-GRAT-M.png", rct, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-M.png", rct, CNTR_LR + CNTR_TB);
				}
			} else {
				if (useLLC) {
					drawDiagram("LLC-GRAT-F.png", rct, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-F.png", rct, CNTR_LR + CNTR_TB);
				}
			}
		} else {
			if (useLLC) {
				drawDiagram("LLC-GRAT1.png", rct, LEFT + BOTTOM);
			} else {
				drawDiagram("GRAT1.png", rct, LEFT + BOTTOM);
			}
		}

		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);

		if (isSingle) {
			String theProcess[];
			if (useLLC) {
				theProcess = new String[] {
						"A Limited Liability Company (LLC) is formed with Managing Member Interest (MMI) and Member Interest (MI). Assets are then transferred to the LLC.",
						"A Grantor Retained Annuity Trust (GRAT) is established for "
								+ userInfo.getFirstName()
								+ ", and MI interests are gifted to the trust. The gifts then receive a substantial discount from their original value.",
						userInfo.getFirstName()
								+ " receives a fixed annual payment from the income generated by the assets transferred to the LLC/GRAT.",
						"At the end of the GRAT term, the property plus any appreciation and accumulated earnings not distrubuted, pass estate-tax free to the family.", };

			} else {
				theProcess = new String[] {
						"A Family Limited Partnership (FLP) is formed with General Partnership (GP) interest and Limited Partnership (LP) interest. Assets are then transferred to the FLP.",
						"A Grantor Retained Annuity Trust (GRAT) is established for "
								+ userInfo.getFirstName()
								+ ", and LP interests are gifted to the trust. The gifts then receive a substantial discount from their original value.",
						userInfo.getFirstName()
								+ " receives a fixed annual payment from the income generated by the assets transferred to the FLP/GRAT.",
						"At the end of the GRAT term, the property plus any appreciation and accumulated earnings not distrubuted, pass estate-tax free to the family.", };
			}

			drawSection(rct, "The Process", theProcess, 1);
		} else {
			String theProcess[];
			if (useLLC) {
				theProcess = new String[] {
						"A Limited Liability Company (LLC) is formed with Managing Member Interest (MMI) and Member Interests (MI). Assets are then transferred to the LLC.",
						"12 year Grantor Retained Annuity Trusts (GRATs) are established, for "
								+ userInfo.getFirstName()
								+ ", and "
								+ "Member Interests are gifted to the trusts. These gifts then receive a substantial discount from their original value.",
						userInfo.getFirstName()
								+ " "
								+ "each receive a fixed annual payment from the income generated by the assets transferred to the LLC/GRATs.",
						"At the end of the GRAT terms, the property plus any appreciation and accumulated earnings not distributed, pass estate-tax free to the family.", };
			} else {
				theProcess = new String[] {
						"A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are then transferred to the FLP.",
						"A Grantor Retained Annuity Trust (GRAT) is established, for "
								+ userInfo.getFirstName()
								+ " and "
								+ " LP interests are gifted to the trusts. These gifts then receive a substantial discount from their original value.",
						userInfo.getFirstName()
								+ " and "
								+ "they receive a fixed annual payment from the income generated by the assets transferred to the FLP/GRATs.",
						"At the end of the GRAT terms, the property plus any appreciation and accumulated earnings not distrubuted, pass estate-tax free to the family.", };

			}
			drawSection(rct, "The Process", theProcess, 1);

		}
		Rectangle r;

		if (isSingle) {
			String benefits[];
			if (useLLC) {
				benefits = new String[] {
						"Valuation discounts are made possible by the LLC.",
						"Leveraged gifts are made to the heirs through the GRAT, thus further reducing the taxable gift on the assets transferred.",
						"Estate taxes are greatly reduced.",
						"Grantor receives a fixed income stream for the term of the trust.",
						"Excess earnings and growth in the value of the assets add to the benefit to the family" };
			} else {
				benefits = new String[] {
						"Valuation discounts are made possible by the FLP.",
						"Leveraged gifts are made to the heirs through the GRAT, thus further reducing the taxable gift on the assets transferred.",
						"Estate taxes are greatly reduced.",
						"Grantor receives a fixed income stream for the term of the trust.",
						"Excess earnings and growth in the value of the assets add to the benefit to the family" };
			}

			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - Page._1_4TH);
			r = drawSection(rct, "Benefits of using this technique", benefits,
					2);
			String effects[];
			if (useLLC) {
				effects = new String[] {
						"If a death occurs before the end of the GRAT term, assets in the GRAT revert to the taxable estate of the deceased.",
						"At the end of the GRAT term, the LLC Interests retain your tax basis." };

			} else {
				effects = new String[] {
						"If a death occurs before the end of the GRAT term, assets in the GRAT revert to the taxable estate of the deceased.",
						"At the end of the GRAT term, the Partnership Interests retain your tax basis." };
			}
			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight()));
			r = drawSection(rct,
					"Side effects/Draw backs of using this technique", effects,
					2);
		} else {
			String benefits[] = {
					"Valuation discounts are made possible by the "
							+ (useLLC ? "LLC" : "FLP") + ".",
					"Leveraged gifts are made to the heirs through the GRATs, thus further reducing the taxable gift on the assets transferred.",
					"Estate taxes are greatly reduced.",
					"Grantors receive a fixed income stream for the term of the trust.",
					"Excess earnings and growth in the value of the assets add to the benefit to the family" };

			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - Page._1_4TH);
			r = drawSection(rct, "Benefits of using this technique", benefits,
					2);
			String effects[];
			if(useLLC) {
				effects =  new String[]{
						"If a death occurs before the end of the GRAT term, assets in the GRAT revert to the taxable estate of the deceased.",
						"At the end of the GRAT terms, the LLC Interests retain your tax basis." };
			} else {
				effects =  new String[]{
						"If a death occurs before the end of the GRAT term, assets in the GRAT revert to the taxable estate of the deceased.",
						"At the end of the GRAT terms, the Partnership Interests retain your tax basis." };
				
			}

			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight()));
			r = drawSection(rct,
					"Side effects/Draw backs of using this technique", effects,
					2);
		}

		newPage();
	}

	/**
	 * The Numbers
	 */

	public void page3() {
		int trusts = 1;

		int fd = userInfo.getFinalDeath();
		if (fd <= 0)
			fd = grat.getTerm();

		drawBorder(pageIcon, "GRAT");
		String clientHeading = userInfo.getFirstName() + " "
				+ userInfo.getLastName();
		drawHeader(clientHeading, "Grantor Retained Annuity Trust (GRAT)");

		Rectangle diag = new Rectangle(prctTop);
		diag.setBottom(diag.getBottom() - _1_4TH);
		diag.setLeft(diag.getLeft() - _1_4TH);

		// drawFilledRect(rct, new Color(255,192,192));

		if (isSingle) {
			if (gender.equalsIgnoreCase("M")) {
				if (useLLC) {
					drawDiagram("LLC-GRAT-M.png", diag, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-M.png", diag, CNTR_LR + CNTR_TB);
				}
			} else {
				if (useLLC) {
					drawDiagram("LLC-GRAT-F.png", diag, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-F.png", diag, CNTR_LR + CNTR_TB);
				}
			}
		} else {
			if (trusts == 1) {
				if (useLLC) {
					drawDiagram("LLC-GRAT1.png", diag, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT1.png", diag, CNTR_LR + CNTR_TB);
				}
			} else {
				if (useLLC) {
					drawDiagram("LLC-GRAT2.png", diag, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT2.png", diag, CNTR_LR + CNTR_TB);
				}
			}
		}

		// Get values to be used for placing labels.
		float orgX = diag.getLeft();
		float orgY = diag.getBottom();

		// HIS
		Rectangle rct = toPage(orgX, orgY, new Rectangle(0, 0, I2P(4.1609f),
				I2P(2.9445f)));
		// drawLabel("Discounted Value to GRAT\n(@ " +
		// percent.format(discountPercent) + ") " + dollar.format(discount),
		// rct, "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_TOP);

		// HERS
		rct = toPage(orgX, orgY,
				new Rectangle(0, 0, I2P(8.4298f), I2P(2.9445f)));
		// drawLabel("Discounted Value to GRAT\n(@ " +
		// percent.format(discountPercent) + ") " + dollar.format(discount),
		// rct, "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_TOP);

		// HIS Asset transfer amount
		rct = toPage(orgX, orgY, new Rectangle(I2P(1.1207f), I2P(1.6741f),
				I2P(1.8082f), I2P(8.5f)));
		// drawLabel(dollar.format((marketValue / 2)), rct,
		// "GARA.TTF",textColor,10f, LBL_CENTER, LBL_BOTTOM);

		// HERS Asset transfer amount
		rct = toPage(orgX, orgY, new Rectangle(I2P(5.3897f), I2P(1.6741f),
				I2P(6.0772f), I2P(8.5f)));
		// drawLabel(dollar.format((marketValue / 2)), rct,
		// "GARA.TTF",textColor,10f, LBL_CENTER, LBL_BOTTOM);

		// HIS annuity amount
		rct = toPage(orgX, orgY, new Rectangle(I2P(1.4641f), I2P(1.4018f),
				I2P(3.2498f), I2P(8.5f)));
		// drawLabel(dollar.format(clientIncome) + " per Year for " +
		// integer.format(clientTerm) + " Years", rct, "GARA.TTF",textColor,10f,
		// LBL_CENTER, LBL_BOTTOM);

		// HERS annuity amount
		rct = toPage(orgX, orgY, new Rectangle(I2P(5.7331f), I2P(1.4018f),
				I2P(7.5188f), I2P(8.5f)));
		// drawLabel(dollar.format(clientIncome) + " per Year for " +
		// integer.format(spouseTerm) + " Years", rct, "GARA.TTF",textColor,10f,
		// LBL_CENTER, LBL_BOTTOM);

		// HIS Double Discount
		rct = toPage(orgX, orgY, new Rectangle(0, I2P(0.256F), I2P(1.3019F),
				I2P(8.5F)));
		// drawLabel("Double\nDiscounted Gift\nto Family", rct,
		// "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_BOTTOM);

		// HERS Double Discount
		rct = toPage(orgX, orgY, new Rectangle(0, I2P(0.256F), I2P(5.5709F),
				I2P(8.5F)));
		// drawLabel("Double\nDiscounted Gift\nto Family", rct,
		// "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_BOTTOM);

		// Rectangle rctx = drawLabel("Comparison in 25 years", prctLLeft,
		// "GARA.TTF", new Color(98,98,98), 14f, LBL_LEFT, LBL_TOP);
		Rectangle rctx = drawSection(prctLLeft, "Comparison in "
				+ Integer.toString(fd) + " Years", new String[0], 0);
		rct = new Rectangle(prctLLeft);

		rct.setTop(rct.getTop() - rctx.getHeight());

		// Now do the table.
		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 4);
		CellInfoFactory cif = new CellInfoFactory();
		BaseFont baseFont = PageUtils.LoadFont("GARA.TTF");
		BaseFont baseFontBold = PageUtils.LoadFont("GARABD.TTF");

		float ptSize = 10f;
		cif.setDefaultFont(new Font(baseFont, ptSize));
		Color green = new Color(0, 176, 0);
		Color red = new Color(192, 0, 0);
		Font fontBold = new Font(baseFontBold, ptSize, Font.NORMAL);
		Font fontBGreen = new Font(baseFontBold, ptSize, Font.NORMAL, green);
		Font fontBRed = new Font(baseFontBold, ptSize, Font.NORMAL, red);

		double balance = grat.getFmv();
		for (int i = 0; i < fd; i++) {
			double g = balance * grat.getAssetGrowth();
			double d = balance * grat.getAssetIncome();
			balance += g + d;
		}

		double gratBalance = grat.getFmv();
		for (int i = 0; i < fd; i++) {
			double g = gratBalance * grat.getAssetGrowth();
			double d = gratBalance * grat.getAssetIncome();
			if (i < grat.getTerm())
				gratBalance += g + d - grat.getAnnuityPayment();
			else
				gratBalance += g + d;
		}

		double totalValue = balance;
		double taxableGRATValue = grat.getRemainderInterest();
		double estateTax = totalValue * grat.getEstateTaxRate();
		double estateTaxGRAT = taxableGRATValue * grat.getEstateTaxRate();
		double netToFamily = totalValue - estateTax;
		double netToFamilyGRAT = taxableGRATValue - estateTaxGRAT + gratBalance;

		String grat2;
		if (isSingle) {
			grat2 = "GRAT";
		} else {
			grat2 = "GRATS";
		}
		CellInfo cellData[][] = {
				// Column Headers
				{
						cif.getCellInfo(" "),
						cif.getCellInfo("Keep in the Estate",
								Element.ALIGN_CENTER, fontBold),
						cif.getCellInfo(grat2, Element.ALIGN_CENTER, fontBold),
						cif.getCellInfo("Difference", Element.ALIGN_CENTER,
								fontBold)

				},
				/*
				 * { cif.getCellInfo("Total Value:"),
				 * cif.getCellInfo(dollar.format(totalValue),
				 * Element.ALIGN_CENTER),
				 * cif.getCellInfo(dollar.format(totalGRATValue) + "*",
				 * Element.ALIGN_CENTER), cif.getCellInfo(" ") },
				 */
				{
						cif.getCellInfo("Estate/Gift Taxable:"),
						cif.getCellInfo(dollar.format(totalValue),
								Element.ALIGN_CENTER, fontBold),
						cif.getCellInfo(dollar.format(taxableGRATValue),
								Element.ALIGN_CENTER, fontBold),
						cif.getCellInfo(dollar.format(totalValue
								- taxableGRATValue), Element.ALIGN_CENTER,
								fontBold) },

				{
						cif.getCellInfo("Total Estate/Gift Taxes:"),
						cif.getCellInfo(dollar.format(estateTax),
								Element.ALIGN_CENTER, fontBRed),
						cif.getCellInfo(dollar.format(0) + " *",
								Element.ALIGN_CENTER, fontBRed),
						cif.getCellInfo(dollar
								.format(estateTax - estateTaxGRAT),
								Element.ALIGN_CENTER, fontBRed), },

				{
						cif.getCellInfo("Total to Family"),
						cif.getCellInfo(dollar.format(netToFamily),
								Element.ALIGN_CENTER, fontBGreen),
						cif.getCellInfo(dollar.format(netToFamilyGRAT + 50000000),
								Element.ALIGN_CENTER, fontBGreen),
						cif.getCellInfo(dollar.format(Math.abs(netToFamilyGRAT + 50000000
								- netToFamily)), Element.ALIGN_CENTER,
								fontBGreen) }, };
		/*
		 * rct = toPage(orgX, orgY, new Rectangle(I2P(1.5f), I2P(3.5f),
		 * I2P(1.75f), I2P(2.0f))); drawLabel("*Value transfered minus retained
		 * income.", rct, "GARA.TTF", textColor,10f, LBL_CENTER, LBL_BOTTOM);
		 */
		// Do the chart
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = "GRAT";

		String cat1 = "Estate Taxes";
		String cat2 = "Total to Family";

		dataSet.addValue(estateTax, series1, cat1);
		dataSet.addValue(estateTaxGRAT, series2, cat1);

		dataSet.addValue(netToFamily, series1, cat2);
		dataSet.addValue(netToFamilyGRAT  + 50000000, series2, cat2);

		BarChart barChart = new BarChart();

		barChart.setTitle("GRAT Comparison");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight() + _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		table.setTableData(cellData);
		table.drawTable();

		drawLabel("* Taxable gifts are covered by the lifetime exemption.",
				prctBottom, "GARA.TTF", new Color(64, 64, 64), 9, LBL_LEFT,
				LBL_BOTTOM);

		newPage();
	}

	/**
	 * Insurance
	 */
	public void page4() {

		double balance = grat.getFmv();
		for (int i = 0; i < grat.getTerm(); i++) {
			double g = balance * grat.getAssetGrowth();
			double d = balance * grat.getAssetIncome();
			balance += g + d;
		}

		double gratValue = grat.getBalance()[grat.getTerm() - 1];
		double estateTaxRate = grat.getEstateTaxRate();
		int years = grat.getTerm() - 1;
		double lifeBenefit = grat.getLifeDeathBenefit();
		double premium = grat.getLifePremium();
		double cashValue = grat.getLifeCashValue();
		double protection = (premium * (years)) - cashValue;
		double taxSavings = protection / lifeBenefit;

		drawBorder(pageIcon, "GRAT/Insurance");
		String clientHeading = userInfo.getFirstName() + " "
				+ userInfo.getLastName();
		drawHeader(clientHeading, "Eliminating the GRAT Side Effects");

		String t1[] = { "By strategically combining the tax advantages of the "
				+ ((int) grat.getTerm())
				+ " Year GRAT with life insurance, your plan may eliminate the side effects (see below) of the GRAT technique. The insurance is designed to provide you with "
				+ percent.format(1.0 - taxSavings)
				+ " of the tax savings, regardless of when deaths occur." };

		Rectangle rctx = this.drawSection(prctFull, "", t1, 0, 14, 12);
		Rectangle rct = new Rectangle(prctFull);

		rct.setTop(rctx.getBottom());

		String t2[] = {};

		rctx = this.drawSection(rct, "How Does This Work", t2, 0, 14, 12);

		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());
		PageTable table = getTable(rct, 3);
		float widths[] = { .6f, .10f, .30f };
		table.setColumnWidths(widths);

		String rows[][] = {
				{ "Growth and Income of the GRAT (Year " + years + ")", "",
						"" + dollar.format(gratValue),
						"[][][align=right,bold=1]", },
				{ "Estate Taxes (if death occurs too soon)", "",
						"" + number.format(gratValue * estateTaxRate),
						"[][align=right][align=right,bold=1]", },
				{ "Insurance Death Benefit", "(a)",
						"" + number.format(lifeBenefit),
						"[][align=right][align=right,bold=1]", },
				{ "Annual Insurance Premium", "", "" + number.format(premium),
						"[][][align=right,bold=1]", },
				{
						"Accumulated Cost of Insurance ("
								+ dollar.format(premium) + " x " + years
								+ " years)", "(b)",
						"" + dollar.format(premium * years),
						"[][align=right][align=right,bold=1]", },
				{ "Cash Value of Life Insurance", "(c)",
						"" + dollar.format(cashValue),
						"[][align=right][align=right,bold=1]", },
				{ "Cost to Preserve Tax Savings (Insurance)", "(d)=(b-c)",
						"" + dollar.format(protection),
						"[][align=right][align=right,bold=1]", },
				{ "Cost of Protection as a Percent of Tax Savings", "(d)/(a)",
						"" + percent.format(taxSavings),
						"[][align=right][align=right,bold=1]", },
				{ "Percent of Tax Savings Realized", "",
						"" + percent.format(1.0 - taxSavings),
						"[][][align=right,bold=1]", },

		};

		table.buildTableEx(rows);
		table.drawTable();

		rctx = table.getResultRect();

		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom() - _1_2TH);

		String t3[] = { "If death occurs before the end of the GRAT term, GRAT payments and the assets are included in the taxable estate not as cash, but as continued annuity payments, thus not available to pay the tax." };
		String t4[] = { "Insurance proceeds replace the tax savings if early death occurs. These tax-free proceeds (when insurance is owned outside the estate) are in addition to any assets passing through the estate." };
		String t5[] = { "Professionals often refer to the Grantor Retained Annuity Trust (GRAT) as a \"heads you win, tails you tie\" scenario. However, combining the power of the GRAT with life insurance yields a \"heads you win, tails you win\" outcome for you and for your family." };

		rctx = this.drawSection(rct, "Side Effect", t3, 0, 14, 12);
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());

		rctx = this.drawSection(rct, "Solution", t4, 0, 14, 12);
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());

		rctx = this.drawSection(rct, "Summary", t5, 0, 14, 12);
		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());
		newPage();
	}

	public void page5() {
		drawBorder(pageIcon, "GRAT");
		String clientHeading = userInfo.getFirstName() + " "
				+ userInfo.getLastName();
		drawHeader(clientHeading, "Grantor Retained Annuity Trust (GRAT)");

		// Draw Assumptions
		Rectangle rct = new Rectangle(prctTop);

		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 5);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		table.setFontSize(12);
		float widths[] = { .25f, .20f, .05f, .25f, .20f };
		int alignments[] = { PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_LEFT,
				PdfPCell.ALIGN_RIGHT };
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);

		String cells[][] = {
				{ "SUMMARY", "", "", "", "",
						"[colspan=5,align=center,bold=1][][][][]" },
				{ " ", " ", " ", " ", " ",
						"[align=right,bold=1,border=b][border=b][border=b][border=b][border=b]" },
				{ "Current Age", Integer.toString(userInfo.getAge()), "",
						"Section 7520 Rate",
						"" + percent.format(grat.getIrsRate()),
						"[align=left,border=l][align=right][][align=left][align=right,border=r]" },
				{ "Beginning Principal", dollar.format(grat.getFmv()), "",
						"Estimated Transfer Date",
						date.format(grat.getIrsDate()),
				"[align=left,border=l][align=right][][align=left][align=right,border=r]" },
				{ "Assumed Discount", percent.format(grat.getDiscount()), "",
						"Remainder Interest (Gift)",
						dollar.format(grat.getRemainderInterest()),
						"[align=left,border=l][align=right][][align=left][align=right,border=r]"			
				},
				{ "Discounted Principal",
						dollar.format(grat.getDiscountValue()), "",
						"Annuity Factor",
						decimal.format(grat.getAnnuityFactor()),
						"[align=left,border=l][align=right][][align=left][align=right,border=r]" },
				{ "Principal Growth Rate",
						percent.format(grat.getAssetGrowth()), "",
						"Annuity Payment",
						dollar.format(grat.getAnnuityPayment()),
						"[align=left,border=l][align=right][][align=left][align=right,border=r]" },
				{ "Principal Income Rate",
						percent.format(grat.getAssetIncome()), "",
						"Optimal Payment Rate",
						percent.format(grat.getOptimalPaymentRate()),
						"[align=left,border=l][align=right][][align=left][align=right,border=r]" },
				{ "GRAT Term", number.format(grat.getTerm()), "",
						"Actual Payment Rate",
						percent.format(grat.getPayoutRate()),
						"[align=left,border=l][align=right][][align=left][align=right,border=r]" },
				{
						"Annuity Interest",
						dollar.format(grat.getAnnuityInterest()),
						"",
						"Gift Leverage",
						decimal.format(Math.floor(grat.getFmv()/grat.getRemainderInterest())) + " : 1",
						"[align=left,border=l+b][align=right,border=b][border=b]"+
						"[align=left,border=b][align=right,border=b+r]" }
				};

		table.buildTableEx(cells);
		table.drawTable();

		// Build the Cash Flow Table here!!!!
		// Draw cash flow table
		rct = new Rectangle(prctBottom);
		rct.setTop(rct.getTop() + (1.25f * INCH));
		PageTable cashFlow = new PageTable(writer);
		cashFlow.setTableFormat(rct, 6);
		cashFlow.setTableFont("arial.TTF");
		cashFlow.setTableFontBold("arialbd.TTF");
		cashFlow.setFontSize(12);
		float widths2[] = { .1f, .2f, .2f, .2f, .2f, .2f };
		int alignments2[] = { PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT };
		cashFlow.setColumnWidths(widths2);
		cashFlow.setColumnAlignments(alignments2);

		String heading[][] = {
				{ "Grantor Retained Annuity Trust Cash Flow", "", "", "", "",
						"", "[bold=1,size=22,colspan=6,align=center][][][][][]" },
				{ " ", "", "", "", "", "", "[][][][][][]" },
				{
						"Year",
						"Beg. Principal",
						"Growth",
						"Income",
						"Payout",
						"End Principal",
						"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"+
						"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"+
						"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]" } };
		cashFlow.buildTableEx(heading);
		cashFlow.buildTableEx(gratCashFlow());
		cashFlow.drawTable();

		newPage();
	}

	public void page6() {
		drawBorder(pageIcon, "Test. CLAT");
		String clientHeading = userInfo.getFirstName() + " "
		+ userInfo.getLastName();
		
		drawHeader(clientHeading, "Testamentary Charitable Lead Annuity Trust");

		if (isSingle) {
			if (gender.equalsIgnoreCase("M")) {
				if( useLLC) {
					drawDiagram("LLC-T-Clat-M.png", prctTop, 0);
				} else {
					drawDiagram("T-Clat-M.png", prctTop, 0);
				}
			} else {
				if( useLLC) {
					drawDiagram("LLC-T-Clat-F.png", prctTop, 0);
				} else {
					drawDiagram("T-Clat-F.png", prctTop, 0);
				}
			}
		} else {
			drawDiagram("T-Clat.png", prctTop, 0);
		}

		if (isSingle) {
			String sec1[] = {
					"Upon your death, a Testamentary Charitable Lead Annuity Trust (Test. CLAT), which will be described in your Living Trust, becomes operative. Except for personal effects and enough liquidity to pay administrative expenses, all other remaining assets (some of which could be in an LLC) are then transferred to the Test. CLAT. Your estate will receive a substantial deduction from the taxable estate based on the term of the trust and the payout to charity.",
					"The family foundation (discussed next) and subsequently the charity(ies) of choice receive(s) a fixed annual payment for "
							+ " 10 or more years from the income generated by the use of assets transferred to the Test. CLAT.",
					"At the end of the Test. CLAT term, the assets plus any appreciation pass estate-tax free to the family." };

			String benefits[] = {
					"A substantial discount is obtained by your estate through utilizing the Test. CLAT.",
					userInfo.getFirstName()
							+ " has full control of assets while living, and can choose the level of control by the family after death.",
					"Full value of the assets is later transferred to the family without additional tax.",
					"Excess earnings and growth in the value of assets add to the benefit to the family.",
					"The substantial tax savings are shared by the family and selected charities.",
					"Assets transferred to the Test. CLAT receive a step up in basis."

			};

			String effects[] = { "Heirs do not receive the benefit of the assets until after the CLAT term expires." };

			drawSection(prctLLeft, " ", sec1, 1);
			Rectangle rctx = new Rectangle(prctLRight);
			rctx.setRight(rctx.getRight() + _1_2TH);
			rctx = this.drawSection(rctx, "Benefits of Using this Technique",
					benefits, 2);

			Rectangle rct = new Rectangle(prctLRight);
			rct.setRight(rct.getRight() + _1_2TH);
			rct.setTop(rctx.getBottom());

			drawSection(rct,
					"Side Effects / Draw backs of using this technique",
					effects, 2);
		} else {
			String sec1[] = {
					"Upon both of your deaths, a Testamentary Charitable Lead Annuity Trust (Test. CLAT), which will be described in your Living Trust, becomes operative. Except for personal effects, specific bequests, and enough liquidity to pay administrative expenses, all other remaining assets are then transferred to the Test. CLAT. Your estate will receive a substantial deduction from the taxable estate based on the term of the trust and the payout to charity.",
					"A family foundation and subsequently the charity(ies) of choice receive(s) a fixed annual payment for "
							+ " 10 or more years from the income generated by the use of assets transferred to the Test. CLAT.",
					"At the end of the Test. CLAT term, the assets plus any appreciation pass estate-tax free to the family." };

			String benefits[] = {
					"A substantial discount is obtained by your estate through utilizing the Test. CLAT.",
					userInfo.getFirstName()
							+ " have full control of assets while living, and can choose the level of control by the family after death.",
					"Full value of the assets is later transferred to the family without additional tax.",
					"Excess earnings and growth in the value of assets add to the benefit to the family.",
					"The substantial tax savings are shared by the family and selected charities.",
					"Assets transferred to the Test. CLAT receive a step up in basis."

			};

			String effects[] = { "Heirs do not receive the benefit of the assets until after the CLAT term expires." };

			drawSection(prctLLeft, " ", sec1, 1);
			Rectangle rctx = new Rectangle(prctLRight);
			rctx.setRight(rctx.getRight() + _1_2TH);
			rctx = drawSection(rctx, "Benefits of Using this Technique",
					benefits, 2);

			Rectangle rct = new Rectangle(prctLRight);
			rct.setRight(rct.getRight() + _1_2TH);
			rct.setTop(rctx.getBottom());

			drawSection(rct,
					"Side Effects / Draw backs of using this technique",
					effects, 2);
		}

	}

	public void setAdmin(AdminBean admin) {
		this.admin = admin;
	}

	public void setGrat(GratController grat) {
		this.grat = grat;
	}

}
