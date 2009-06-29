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
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.BarChart;
import com.teag.estate.SIditTool;
import com.teag.reports.GRATPages.CustomLabelGenerator;
import com.teag.reports.GRATPages.CustomToolTipGenerator;

public class SIditPages extends Page {
	SIditTool sidit;

	boolean useInsurance = false;
	boolean useLLC = false;

	public SIditPages(Document document, PdfWriter writer) {
		super(document, writer);
	}

	public void draw() {
		if (sidit.isUseLLC())
			useLLC = true;
		page1();
		newPage();
		page2();
		newPage();
		page3();
		newPage();
		page4();
	}

	public SIditTool getSidit() {
		return sidit;
	}

	public boolean isUseInsurance() {
		return useInsurance;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	public void page1() {
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(), "The Problem");

		String sec1[] = { "Your assets of "
				+ sidit.getAssetName()
				+ " have the potential to grow very dramatically over the next few years. This is great news until we calculate your future estate tax liability", };
		String sec2[] = {
				"When an asset is growing very rapidly in one's estate, and the estate tax liability is growing faster than can be reduced by normal gifting techniques, a different approach can be taken.",
				" ",
				"Let's examine a combination of leveraging techniques that can be utilized with this circumstance." };

		Rectangle rct = new Rectangle(prctTop);
		Rectangle rctx = this.drawSection(rct, "", sec1, 0, 14, 14);

		rct.setTop(rctx.getBottom());
		rct.setLeft(rct.getLeft() + prctTop.getWidth() * .125f);
		rct.setRight(rct.getRight() - prctTop.getWidth() * .125f);

		// Draw the Table
		PageTable table = this.getTable(rct, 2);
		String rows[][] = {
				{ "Today's Fair Market Value",
						"" + dollar.format(sidit.getFmv()), "[][bold=1]" },
				{ "Projected Years of Growth",
						"" + integer.format(sidit.getFinalDeath()),
						"[][bold=1]" },
				{ "Average Growth Rate",
						"" + percent.format(sidit.getAssetGrowth()),
						"[][bold=1]" },
				{ "Average Income Rate",
						"" + percent.format(sidit.getAssetIncome()),
						"[][bold=1]" },
				{ " ", "", "[colspan=1][]" },
				{ "Total Value (projected) in Taxable Estate",
						"" + dollar.format(sidit.getFinalBalance()),
						"[bold=1][bold=1]"

				},
				{ "", "", "[colspan=1][]" },
				{
						"Estate Tax (" + percent.format(.55) + ")",
						"" + dollar.format(sidit.getFinalBalance() * .55),
						"[Bold=1,Color=" + pgRed + "][Bold=1,Color=" + pgRed
								+ "]" },
				{ "", "", "[colspan=1][]" },
				{
						"Net Value Passing to Family",
						""
								+ dollar
										.format((sidit.getFinalBalance() - (sidit
												.getFinalBalance() * .55))),
						"[Bold=1,Color=" + pgGreen + "][Bold=1,Color="
								+ pgGreen + "]"

				}

		};

		int alignments[] = { LFT, RGT };
		table.setColumnAlignments(alignments);
		table.setFontSize(14);
		table.buildTableEx(rows);
		table.drawTable();

		// Draw the graph ************

		taxPie(prctLLeft, sidit.getFinalBalance(),
				sidit.getFinalBalance() * .55, "Estate Tax", "Net to Family");

		// *****************

		drawSection(prctLRight, "", sec2, 0, 14, 14);
	}

	public void page2() {
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(),
				"Intentionally Defective Irrevocable Trust (IDIT)");

		Rectangle rct = new Rectangle(prctTop);

		rct.setTop(rct.getTop() - (12 * _1_4TH));
		rct.setBottom(rct.getBottom() - _1_4TH);
		rct.setLeft(rct.getLeft() - _1_4TH);

		if (useLLC) {
			if (userInfo.isSingle()) {
				if (userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-SIDIT-M.png", rct, CNTR_TB + LEFT);
				} else {
					drawDiagram("LLC-SIDIT-F.png", rct, CNTR_TB + LEFT);
				}
			} else {
				drawDiagram("LLC-SIDIT.png", rct, CNTR_TB + CNTR_LR);
			}

		} else {
			if (userInfo.isSingle()) {
				if (userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("SIDIT-M.png", rct, CNTR_TB + LEFT);
				} else {
					drawDiagram("SIDIT-F.png", rct, CNTR_TB + LEFT);
				}
			} else {
				drawDiagram("SIDIT.png", rct, CNTR_TB + CNTR_LR);
			}
		}

		double nRate = sidit.getNoteRate();
		int term = sidit.getNoteTerm();

		String process[];

		if (useLLC) {
			if (userInfo.isSingle()) {
				process = new String[] {
						"1.  A Limited Liability Company (LLC) is formed, with a business purpose, with a small Managing Member Interests (MMI) and a larger Member Interests (MI). Assets are transferred to the LLC with your retaining the  MMI/MI shares.",
						"2,3.  MI interests are sold to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted (where possible) due to the lack of control, and marketability, of the MI interests you have."
								+ "You obtain notes recievable for the discounted value, paying interest only ("+percent.format(nRate)+"/year), with a balloon payment due at the end of the term ("+ Integer.toString(term)+ " years).",
				"Note: Prior to selling assets to the IDIT in exchange for interest only notes, that trust should have assets in it that have a value of at least 10 percent of the value of the assets in the trust after the intended sale. In order to meet this requirement, you might consider gifting assets in an appropriate amount to the trust." };
			} else {
				process = new String[] {
						"1.  A Limited Liability Company (LLC) is formed, with a business purpose, with small Managing Member Interests (MMI) and a larger Member Interests (MI). Assets are transferred to the LLC with each of you recieving one half of the MMI/MI shares.",
						"2,3.  MI interests are sold by each of you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted (where possible) due to the lack of control, and marketability, of the MI interests you each have.  "
								+ "You each obtain notes recievable for the discounted value, paying interest only ("+percent.format(nRate)+"/year), with a balloon payment due at the end of the term ("+ Integer.toString(term)+ " years).",
				"Note: Prior to selling assets to the IDIT in exchange for interest only notes, that trust should have assets in it that have a value of at least 10 percent of the value of the assets in the trust after the intended sale. In order to meet this requirement, you might consider gifting assets in an appropriate amount to the trust." };
			}
		} else {
			if (userInfo.isSingle()) {
				process = new String[] {
						"1.  A Family Limited Partnership (FLP) is formed, with a business purpose, with a small General Partnership (GP) interest and a larger Limited Partnership (LP) interest. Assets are transferred to the FLP, with you recieving the GP/LP shares.",
						"2,3.  LP interests are sold by you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted (where possible) due to the lack of control, and marketability of the LP interests you have."
								+ "You obtain notes recievable for the discounted value, paying interest only ("+percent.format(nRate)+"/year), with a balloon payment due at the end of the term ("+ Integer.toString(term)+ " years).",
				"Note: Prior to selling assets to the IDIT in exchange for interest only notes, that trust should have assets in it that have a value of at least 10 percent of the value of the assets in the trust after the intended sale. In order to meet this requirement, you might consider gifting assets in an appropriate amount to the trust." };

			} else {
				process = new String[] {
						"1.  A Family Limited Partnership (FLP) is formed, with a business purpose, with a small General Partnership (GP) interest and a larger Limited Partnership (LP) interest. Assets are transferred to the FLP. with each of you recieving one half of the GP/LP shares.",
						"2,3.  LP interests are sold by each of you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted (where possible) due to lack of control, and marketability of the LP interests you each have. "
								+ "You each obtain notes recievable for the discounted value, paying interest only ("+percent.format(nRate)+"/year), with a balloon payment due at the end of the term ("+ Integer.toString(term)+ " years).",
				"Note: Prior to selling assets to the IDIT in exchange for interest only notes, that trust should have assets in it that have a value of at least 10 percent of the value of the assets in the trust after the intended sale. In order to meet this requirement, you might consider gifting assets in an appropriate amount to the trust." };
			}
		}

		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		drawSection(rct, "The Process", process, 0);

		String benefits[];

		if (useLLC) {
			benefits = new String[] {
					"The LLC is often eligible for discounts on the asset value.",
					"The IDIT enables the freezing of a rapidly growing value (appraisal needed).",
					"Future growth of the asset(s) sold is shifted to the younger generation(s) without gift or estate tax.",
					"The Grantor pays all income tax on the taxable income of the IDIT",
					"Grantor retains control over the assets with the Grantor/Seller retaining the right to choose who manages the Manager Interest of the LLC.",
					"Life Insurance can be obtained and premiums paid within the IDIT." };
		} else {
			benefits = new String[] {
					"The FLP is often eligible for discounts on the asset value (appraisal needed).",
					"The IDIT enables the freezing of a rapidly growing value.",
					"Future growth of the asset(s) sold is shifted to the younger generation(s) without gift or estate tax.",
					"The Grantor pays all income tax on the taxable income of the IDIT",
					"Grantor retains control over the assets with the Grantor/Seller retaining the right to choose who manages the General Partnership interest of the FLP.",
					"Life Insurance can be obtained and premiums paid within the IDIT." };
		}

		rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() - Page._1_4TH);
		Rectangle r = this.drawSection(rct,
				"Benefits of using this technique.", benefits, 2);

		String effects[] = {
				"Assets are Irrevocably transferred to the family.",
				"Heirs lose the step-up in basis of the assets.",
				"The value of the note is subject to estate taxes." };

		rct.setTop(r.getBottom());
		r = drawSection(rct, "Side effects of this technique", effects, 2);

	}

	public void page3() {
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(),
				"Intentionally Defective Irrevocable Trust (IDIT)");

		Rectangle rct = new Rectangle(prctTop);

		rct.setTop(rct.getTop() - (12 * _1_4TH));
		rct.setBottom(rct.getBottom() - _1_4TH);
		rct.setLeft(rct.getLeft() - _1_4TH);

		if (useLLC) {
			if (userInfo.isSingle()) {
				if (userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-SIDIT-M.png", rct, CNTR_TB + CNTR_LR);
				} else {
					drawDiagram("LLC-SIDIT-F.png", rct, CNTR_TB + CNTR_LR);
				}
			} else {
				drawDiagram("LLC-SIDIT.png", rct, CNTR_TB + CNTR_LR);
			}

		} else {
			if (userInfo.isSingle()) {
				if (userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("SIDIT-M.png", rct, CNTR_TB + CNTR_LR);
				} else {
					drawDiagram("SIDIT-F.png", rct, CNTR_TB + CNTR_LR);
				}
			} else {
				drawDiagram("SIDIT.png", rct, CNTR_LR + CNTR_TB);
			}
		}

		// We now need the values placed on the left hand side of the page!
		// Now do the table.
		Rectangle lrct = new Rectangle(prctLLeft);
		lrct.setTop(lrct.getTop() - (_1_2TH + _1_4TH));

		drawSection(lrct, "Comparison in "
				+ Integer.toString(sidit.getFinalDeath()) + " Years",
				new String[0], 0);

		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - (_1_2TH + _1_2TH));

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

		double f = sidit.getFinalBalance();
		double t = f * .55;
		double result = f - t;

		double balance = sidit.getBalance()[sidit.getFinalDeath() - 1]
				+ sidit.getLifeDeathBenefit();

		double note = sidit.getNoteBalance()[0];
		double noteTax = note * .55;
		double noteValueToFamily = note - noteTax;

		CellInfo cellData[][] = {
				// Column Headers
				{
						cif.getCellInfo(" "),
						cif.getCellInfo("Keep in the Estate",
								Element.ALIGN_CENTER, fontBold),
						cif.getCellInfo("IDIT", Element.ALIGN_CENTER, fontBold),
						cif.getCellInfo("Difference", Element.ALIGN_CENTER,
								fontBold)

				},
				{
						cif.getCellInfo("Estate/Gift Taxable:"),
						cif.getCellInfo(dollar.format(f), Element.ALIGN_CENTER,
								fontBold),
						cif.getCellInfo(dollar.format(note) + " **",
								Element.ALIGN_CENTER, fontBold),
						cif.getCellInfo(dollar.format(f - note),
								Element.ALIGN_CENTER, fontBold) },

				{
						cif.getCellInfo("Total Estate/Gift Taxes:"),
						cif.getCellInfo(dollar.format(t), Element.ALIGN_CENTER,
								fontBRed),
						cif.getCellInfo(dollar.format(noteTax),
								Element.ALIGN_CENTER, fontBRed),
						cif.getCellInfo(dollar.format(t - noteTax),
								Element.ALIGN_CENTER, fontBRed), },
				{
						cif.getCellInfo("Life Insurance:"),
						cif.getCellInfo("0", Element.ALIGN_CENTER, fontBGreen),
						cif.getCellInfo(dollar.format(sidit
								.getLifeDeathBenefit()), Element.ALIGN_CENTER,
								fontBGreen),
						cif.getCellInfo(dollar.format(sidit
								.getLifeDeathBenefit()), Element.ALIGN_CENTER,
								fontBGreen), },
				{
						cif.getCellInfo("Total to Family"),
						cif.getCellInfo(dollar.format(result),
								Element.ALIGN_CENTER, fontBGreen),
						cif.getCellInfo(dollar.format(balance
								+ noteValueToFamily), Element.ALIGN_CENTER,
								fontBGreen),
						cif.getCellInfo(
								dollar.format((balance + noteValueToFamily)
										- result), Element.ALIGN_CENTER,
								fontBGreen), }, };

		// Do the chart

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = "IDIT";

		String cat1 = "Estate Taxes";
		String cat2 = "Total to Family";

		dataSet.addValue(t, series1, cat1);
		dataSet.addValue(noteTax, series2, cat1);

		dataSet.addValue(result, series1, cat2);
		dataSet.addValue(balance + noteValueToFamily, series2, cat2);

		BarChart barChart = new BarChart();

		barChart.setTitle("IDIT Comparison");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight() + _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		barChart.setSeriesColor(new Color(0xff0000), new Color(0x00ff00));
		barChart.setColor(0, Color.red);
		barChart.setColor(1, Color.green);
		barChart.setLablesOff(true);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		table.setTableData(cellData);
		table.drawTable();

		drawLabel("** The value(s) of the note is kept inside the taxable estate.",
				prctBottom, "GARA.TTF", new Color(64, 64, 64), 9, LBL_LEFT,
				LBL_BOTTOM);
	}

	// Include table
	public void page4() {
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getClientHeading(),
				"Intentionally Defective Irrevocable Trust (IDIT)");

		// Summary
		Rectangle rct = new Rectangle(prctTop);
		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 3);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		table.setFontSize(12);
		float widths[] = { .45f, .1f, .45f };
		int alignments[] = { PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER,
				PdfPCell.ALIGN_RIGHT };
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);

		String noteStructure = "Annual Level Principal + Interest";

		if (sidit.getNoteType() == 1)
			noteStructure = "Annual Amoritized";
		if (sidit.getNoteType() == 2)
			noteStructure = "Annual Interest + Balloon";

		String cells[][] = {
				{ "SUMMARY", "", "", "[colspan=3,align=center,bold=1][][]" },
				{ "", "", "", "[][][]" },
				{ "Value of Assets to be Sold (pre discount)", "", dollar.format(sidit.getFmv()-sidit.getGiftAmount()),"[][][]"},
				{ "Discount", "", percent.format(1.0 - (sidit.getDmv() / (sidit.getFmv() - sidit.getGiftAmount()))),"[][][]"},
				{ "Net Value of Assets Sold", "", dollar.format(sidit.getDmv()),"[][][]"},
				{ "Amount of Gift to Trust", "", dollar.format(sidit.getGiftAmount()),"[][][]"},
				{ "Total Amount of Trust Assets", "", dollar.format(sidit.getFmv()),"[][][]"},
				{ "Yield on Trust Assets(Growth/Income)", "", percent.format(sidit.getAssetGrowth() + sidit.getAssetIncome()),"[][][]"},
				{ "Note Value", "", dollar.format(sidit.getDmv()),"[][][]"},
				{ "Note Term", "", number.format(sidit.getNoteTerm()),"[][][]"},
				{ "Note Interest Rate per Year", "", percent.format(sidit.getNoteRate()),"[][][]"},
				{ "Note Payout Structure", "", noteStructure,"[][][]"},
				{ "Life Premium (paid for "+ Integer.toString(sidit.getNoteTerm())+" years)", "", dollar.format(sidit.getLifePremium()),"[][][]"},
				{ "Life Death Benefit", "", dollar.format(sidit.getLifeDeathBenefit()),"[][][]"}};

		table.buildTableEx(cells);
		table.drawTable();

		// Build the Cash Flow Table here!!!!
		// Draw cash flow table
		rct = new Rectangle(prctBottom);
		rct.setTop(rct.getTop() + (1.f * 72));
		float widths2[] = { .1f, .2f, .2f, .2f, .2f, .1f, .2f, .2f };
		int alignments2[] = { PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfCell.ALIGN_RIGHT };

		PageTable cashFlow = new PageTable(writer);
		cashFlow.setTableFormat(rct, 8);
		cashFlow.setTableFont("arial.TTF");
		cashFlow.setTableFontBold("arialbd.TTF");
		cashFlow.setFontSize(8);

		cashFlow.setColumnWidths(widths2);
		cashFlow.setColumnAlignments(alignments2);

		String heading[][] = {
				{ "Trust Cash Flow", "", "", "", "", "", "","",
						"[bold=1,colspan=8,align=center,ptsize=12][][][][][][][]" },
				{ " ", "", "", "", "", "", "","", "[][][][][][][][]" },
				{
						"Year",
						"Beg. Principal",
						"Growth",
						"Income",
						"Payout",
						"Premium Payment",
						"End Principal",
						"Amt. to Family *",
						"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"
								+ "[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"
								+ "[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"
								+ "[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]" } };

		String rows[][] = getCashFlow();
		String singleRow[][] = new String[1][7];

		String cont[][] = {
				{ "[Continued on Next Page]", "", "", "", "", "", "",
						"[bold=1,colspan=7,align=center][][][][][][][]" },
				{ " ", "", "", "", "", "", "", "[][][][][][][][]" } };

		int idx = 0;

		if (sidit.getNoteTerm() > 17) {
			cashFlow.buildTableEx(heading);
			while (idx < 12) {
				singleRow[0] = rows[idx++];
				cashFlow.buildTableEx(singleRow);
			}
			cashFlow.buildTableEx(cont);
			cashFlow.drawTable();

			// Draw the following label at the bottom
			String lb1 = "* Includes life insurance should death occur at any year minus the value of the note.";
			
			drawLabel(lb1,
					prctBottom, "GARA.TTF", new Color(64, 64, 64), 9, LBL_LEFT,
					LBL_BOTTOM);
			
			newPage(); // We need to go to a new page

			// Redraw heading
			drawBorder(pageIcon, "IDIT");
			drawHeader(userInfo.getClientHeading(),
					"Intentionaly Defective Irrevocable Trust");

			// Readraw Table Heading
			rct = new Rectangle(prctTop);
			cashFlow = new PageTable(writer);
			cashFlow.setTableFormat(rct, 8);
			cashFlow.setTableFont("arial.TTF");
			cashFlow.setTableFontBold("arialbd.TTF");
			cashFlow.setFontSize(8);
			cashFlow.setColumnWidths(widths2);
			cashFlow.setColumnAlignments(alignments2);

			heading[0][0] = "Trust Cash Flow (Cont.)";
			cashFlow.buildTableEx(heading);

			// Include the missing rows
			while (idx < sidit.getNoteTerm()) {
				singleRow[0] = rows[idx++];
				cashFlow.buildTableEx(singleRow);
			}
			cashFlow.drawTable();
			drawLabel(lb1,
					prctBottom, "GARA.TTF", new Color(64, 64, 64), 9, LBL_LEFT,
					LBL_BOTTOM);
		} else {
			cashFlow.buildTableEx(heading);
			cashFlow.buildTableEx(getCashFlow());
			cashFlow.drawTable();
			// Draw the following label at the bottom
			String lb1 = "* Includes life insurance should death occur at any year minus the value of the note.";
			drawLabel(lb1,
					prctBottom, "GARA.TTF", new Color(64, 64, 64), 9, LBL_LEFT,
					LBL_BOTTOM);
		}
	}

	private String[][] getCashFlow() {
		String rows[][] = new String[sidit.getNoteTerm()][9];
		double prin = sidit.getFmv();
		double growth = sidit.getAssetGrowth();
		double income = sidit.getAssetIncome();
		double pay[] = sidit.getNotePayment();
		double premium = sidit.getLifePremium();
		double lifeYears = sidit.getLifePremiumYears();
        double noteBalance[] = sidit.getNoteBalance();

		for (int i = 0; i < sidit.getNoteTerm(); i++) {
			rows[i][0] = Integer.toString(i + 1);
			rows[i][1] = dollar.format(prin);
			rows[i][2] = dollar.format(prin * growth);
			rows[i][3] = dollar.format(prin * income);
			rows[i][4] = dollar.format(-pay[i]);
			if (i < lifeYears) {
				rows[i][5] = dollar.format(-premium);
			} else {
				rows[i][5] = "";
				premium = 0;
			}

			prin = prin * (1 + growth + income) - pay[i] - premium;
			rows[i][6] = dollar.format(prin);
			rows[i][7] = dollar.format(prin + sidit.getLifeDeathBenefit()-noteBalance[i]);
			rows[i][8] = "[border=l+r+b][border=r+b][border=r+b][border=r+b][border=r+b][border=r+b,color="
					+ makeColor(0, 0, 24) + "][border=r+b][border=r+b]";
		}

		return rows;
	}

	public void setSidit(SIditTool sidit) {
		this.sidit = sidit;
	}

	public void setUseInsurance(boolean useInsurance) {
		this.useInsurance = useInsurance;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}

	private void taxPie(Rectangle rct, double totalValue, double tax,
			String taxLabel, String netLabel) {
		double taxPercent = (tax / totalValue) * 100;
		double netValuePercent = 100 - taxPercent;

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue(taxLabel, taxPercent);
		dataset.setValue(netLabel, netValuePercent);

		PiePlot3D plot = new PiePlot3D(dataset);
		plot.setLabelGenerator(new StandardPieItemLabelGenerator());
		plot.setInsets(new Insets(0, 5, 5, 5));
		plot.setToolTipGenerator(new CustomToolTipGenerator());
		plot.setLabelGenerator(new CustomLabelGenerator());
		plot.setSectionPaint(0, new Color(pgRed));
		plot.setSectionPaint(1, new Color(pgGreen));
		plot.setForegroundAlpha(.6f);
		plot.setOutlinePaint(Color.white);
		plot.setBackgroundPaint(Color.white);

		JFreeChart chart = new JFreeChart("Asset Distribution",
				JFreeChart.DEFAULT_TITLE_FONT, plot, true);

		chart.setBackgroundPaint(Color.white);
		chart.setAntiAlias(true);

		Rectangle page = rct;

		try {
			Image img = Image.getInstance(chart.createBufferedImage((int) page
					.getWidth(), (int) page.getHeight()), null);
			drawDiagram(img, rct, 0, 72);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
