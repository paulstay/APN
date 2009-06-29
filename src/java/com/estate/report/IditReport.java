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

import com.estate.pdf.CellInfo;
import com.estate.pdf.CellInfoFactory;
import com.estate.pdf.Page;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.estate.toolbox.Idit;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.BarChart;

public class IditReport extends Page {

	/**
     * A custom label generator (returns null for one item as a test).
     */
    static class CustomLabelGenerator implements PieSectionLabelGenerator {
        
        /**
         * Generates a label for a pie section.
         * 
         * @param dataset  the dataset (<code>null</code> not permitted).
         * @param key  the section key (<code>null</code> not permitted).
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

	
	Idit idit;
	
	public IditReport(Document document, PdfWriter writer) {
		super(document, writer);
	}
	
	public void draw() {
		page1();
		page2();
		page3();
		page4();
	}
	
	private String[][] getCashFlow() {
		String rows[][] = new String[idit.getFinalDeath()][8];
		double bal[] = idit.getBalance();
		double growth[] = idit.getGrowth();
		double income[] = idit.getIncome();
		double pay[] = idit.getNotePayment();
		double life[] = idit.getLifePayment();
		double end[] = idit.getEndBalance();
		
		for(int i=0; i < idit.getNoteTerm(); i++) {
			rows[i][0] = Integer.toString(i+1);
			rows[i][1] = dollar.format(bal[i]);
			rows[i][2] = dollar.format(growth[i]);
			rows[i][3] = dollar.format(income[i]);
			rows[i][4] = dollar.format(-pay[i]);
			rows[i][5] = dollar.format(-life[i]);
			rows[i][6] = dollar.format(end[i]);
			rows[i][7] = "[border=l+r+b][border=r+b][border=r+b][border=r+b][border=r+b,color="+
				makeColor(0,0,24)+"][border=r+b,color="+makeColor(0,0,24)+"][border=r+b]";
		}

		return rows;
	}
	
	public Idit getIdit() {
		return idit;
	}
	
	public void page1() {
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getFirstName() + " " + userInfo.getLastName(), "The Problem");

		String sec1[] = { "Your assets "
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

		double finalBalance = idit.getFmv();
		
		for(int i=0; i < idit.getFinalDeath(); i++) {
			finalBalance = finalBalance * (1 + idit.getAssetGrowth() + idit.getAssetIncome());
		}
		
		
		// Draw the Table
		PageTable table = getTable(rct, 2);
		String rows[][] = {
				{ "Today's Fair Market Value",
						"" + dollar.format(idit.getFmv()), "[][bold=1]" },
				{ "Projected Years of Growth",
						"" + integer.format(idit.getFinalDeath()),
						"[][bold=1]" },
				{ "Average Growth Rate",
						"" + percent.format(idit.getAssetGrowth()),
						"[][bold=1]" },
				{ "Average Income Rate",
						"" + percent.format(idit.getAssetIncome()),
						"[][bold=1]" },
				{ " ", "", "[colspan=1][]" },
				{ "Total Value (projected) in Taxable Estate",
						"" + dollar.format(finalBalance),
						"[bold=1][bold=1]"

				},
				{ "", "", "[colspan=1][]" },
				{
						"Estate Tax (" + percent.format(idit.getEstateTaxRate()) + ")",
						"" + dollar.format(finalBalance * (idit.getEstateTaxRate())),
						"[Bold=1,Color=" + pgRed + "][Bold=1,Color=" + pgRed
								+ "]" },
				{ "", "", "[colspan=1][]" },
				{
						"Net Value Passing to Family",
						""
								+ dollar
										.format((finalBalance - (finalBalance * idit.getEstateTaxRate()))),
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
		taxPie(prctLLeft, finalBalance,
				finalBalance * .55f, "Estate Tax", "Net to Family");
		// *****************

		drawSection(prctLRight, "", sec2, 0, 14, 14);
		newPage();
	}
	
	public void page2() {
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getFirstName() + " " + userInfo.getLastName(),
				"Intentionally Defective Irrevocable Trust (IDIT)");

		Rectangle rct = new Rectangle(prctTop);
		
		rct.setTop(rct.getTop() - (12*_1_4TH));
		rct.setBottom(rct.getBottom() - _1_4TH);
		rct.setLeft(rct.getLeft() - _1_4TH);

		boolean isSingle = true;
		if(userInfo.getGender().equalsIgnoreCase("B"))
			isSingle = false;
		
		if (idit.isUseLLC()) {
			if (isSingle) {
				if (userInfo.getGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-SIDIT-M2.png", rct, CNTR_TB + LEFT);
				} else {
					drawDiagram("LLC-SIDIT-F2.png", rct, CNTR_TB + LEFT);
				}
			} else {
				drawDiagram("LLC-SIDIT.png", rct, CNTR_TB + CNTR_LR);
			}

		} else {
			if (isSingle) {
				if (userInfo.getGender().equalsIgnoreCase("M")) {
					drawDiagram("SIDIT-M2.png", rct, CNTR_TB + LEFT);
				} else {
					drawDiagram("SIDIT-F2.png", rct, CNTR_TB + LEFT);
				}
			} else {
				drawDiagram("SIDIT.png", rct, CNTR_TB + CNTR_LR);
			}
		}

		
		String process[];

		if (idit.isUseLLC()) {
			if (isSingle) {
				process = new String[] {
						"1 A Limited Liability Company (LLC) is formed with Managing Member Interests (MMI) and Member Interests (MI). Assets are transferred to the LLC with your retaining the  MMI/MI shares.",
						"2,3 MI interests are sold to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted due to the MI interests you have and the underlying illiquid real estate asset. "
								+ "You obtain notes recievable for the discounted value, paying interest only, with a balloon payment due at the end of the term.",
				"Note: Prior to selling assets to the IDIT, that trust should have assets in it that have a value of at least 10 percent of the value of the assets you inted to sell to the trust. In order to meet this requirement, you might consider gifting assets in this amount (10 percent) to the trust.",
								};
			} else {
				process = new String[] {
						"1 A Limited Liability Company (LLC) is formed with Managing Member Interests (MMI) and Member Interests (MI). Assets are transferred to the LLC with each of you recieving one half of the GP/LP shares.",
						"2,3 MI interests are sold by each of you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted due to the MI interests you each have and the underlying illiquid real estate asset. "
								+ "You each obtain notes recievable for the discounted value, paying interest only, with a balloon payment due at the end of the term.",
								"Note: Prior to selling assets to the IDIT, that trust should have assets in it that have a value of at least 10 percent of the value of the assets you inted to sell to the trust. In order to meet this requirement, you might consider gifting assets in this amount (10 percent) to the trust.",
								};
			}
		} else {
			if (isSingle) {
				process = new String[] {
						"1 A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are transferred to the FLP, with you recieving the GP/LP shares.",
						"2,3 LP interests are sold by you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted by 40%, due to the LP interests you have and the underlying illiquid real estate asset. "
								+ "You obtain notes recievable for the discounted value, paying 5% interest only, with a balloon payment due in 15 years.",
				"Note: Prior to selling assets to the IDIT, that trust should have assets in it that have a value of at least 10 percent of the value of the assets you inted to sell to the trust. In order to meet this requirement, you might consider gifting assets in this amount (10 percent) to the trust.",
								};
			} else {
				process = new String[] {
						"1 A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are transferred to the FLP. with each of you recieving one half of the GP/LP shares.",
						"2,3 LP interests are sold by each of you to an Intentionally Defective Irrevocable Trust (IDIT) set up for the benefit of your children, grandchildren, etc. "
								+ "The sale price is discounted by 40%, due to the LP interests you each have and the underlying illiquid real estate asset. "
								+ "You each obtain notes recievable for the discounted value, paying 5% interest only, with a balloon payment due in 15 years.", 
				"Note: Prior to selling assets to the IDIT, that trust should have assets in it that have a value of at least 10 percent of the value of the assets you inted to sell to the trust. In order to meet this requirement, you might consider gifting assets in this amount (10 percent) to the trust.",
								};

			}
		}

		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		drawSection(rct, "The Process", process, 0);
		
		String benefits[];

		if (idit.isUseLLC()) {
			benefits = new String[] {
					"The LLC discounts the asset value.",
					"The IDIT enables the freezing of a rapidly growing value.",
					"Shift the future growth of the asset sold to the younger generation(s) without gift or estate tax.",
					"Cause the seller to pay all income tax on the income of the IDIT",
					"Retain control over the assets with the grantor/seller retaining the right to choose who manages the Manager interest of the LLC.",
					"Life Insurance can be obtained and premiums paid within the IDIT."};
		} else {
			benefits = new String[] {
					"The FLP discounts the asset value.",
					"The IDIT enables the freezing of a rapidly growing value.",
					"Shift the future growth of the asset sold to the younger generation(s) without gift or estate tax.",
					"Cause the seller to pay all income tax on the income of the IDIT",
					"Retain control over the assets with the grantor/seller retaining the right to choose who manages the Gneral Partnership interest of the FLP.",
					"Life Insurance can be obtained and premiums paid within the IDIT."};
		}

		rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() - Page._1_4TH);
		Rectangle r = this.drawSection(rct,
				"Benefits of using this technique.", benefits, 2);

		String effects[] = {
				"Assets are Irrevocably transferred to the family.",
				"Heirs lose the step-up in basis of the assets.",
				"The value of the note is subject to estate taxes."};

		rct.setTop(r.getBottom());
		r = drawSection(rct, "Side effects of this technique", effects, 2);
		newPage();
	}
	
	public void page3() {

		boolean isSingle = true;
		if(userInfo.getGender().equalsIgnoreCase("B"))
			isSingle = false;
		
		boolean useLLC = idit.isUseLLC();
		
		drawBorder(pageIcon, "IDIT");
		drawHeader(userInfo.getFirstName() + " " + userInfo.getLastName(), "Intentionally Defective Irrevocable Trust (IDIT)");
	
		Rectangle rct = new Rectangle(prctTop);
		
		rct.setTop(rct.getTop() - (12*_1_4TH));
		rct.setBottom(rct.getBottom() - _1_4TH);
		rct.setLeft(rct.getLeft() - _1_4TH);

		if(useLLC ) {
			if( isSingle) {
				if( userInfo.getGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-SIDIT-M2.png", rct, CNTR_TB + CNTR_LR);
				} else {
					drawDiagram("LLC-SIDIT-F2.png", rct, CNTR_TB + CNTR_LR);
				}
			} else {
				drawDiagram("LLC-SIDIT.png", rct, CNTR_TB + CNTR_LR);	
			}
			
		} else {
			if( isSingle) {
				if( userInfo.getGender().equalsIgnoreCase("M")) {
					drawDiagram("SIDIT-M2.png", rct, CNTR_TB + CNTR_LR );
				} else {
					drawDiagram("SIDIT-F2.png", rct, CNTR_TB + CNTR_LR);
				}
			} else {
				drawDiagram("SIDIT.png", rct, CNTR_LR + CNTR_TB);
			}
		}
		
		// We now need the values placed on the left hand side of the page!
		// Now do the table.
		Rectangle lrct = new Rectangle(prctLLeft);
		lrct.setTop(lrct.getTop() - (_1_2TH + _1_4TH));
		
		drawSection(lrct, "Comparison in " + Integer.toString(idit.getFinalDeath()) + " Years", new String[0], 0);
		
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
		
		double finalBalance = idit.getFmv();
		
		for(int i=0; i < idit.getFinalDeath(); i++) {
			finalBalance = finalBalance * (1 + idit.getAssetGrowth() + idit.getAssetIncome());
		}
		
		
		double f = finalBalance;
		double t = f * idit.getEstateTaxRate();
		double result = f-t;
		
		double endBalance = idit.getFmv();
		double[] payment = idit.getNotePayment();
		
		endBalance = idit.getEndBalance()[idit.getFinalDeath()-1];
		
		/*
		for(int i=0; i < idit.getFinalDeath(); i++){
			double ipay = 0;
			if(i < idit.getNoteTerm()){
				ipay = payment[i];
			}
			
			endBalance = endBalance *(1 + idit.getAssetGrowth() + idit.getAssetIncome()) - ipay;
		}
		*/
		
		double balance = endBalance + idit.getLifeDeathBenefit();
		double note = idit.getNoteBalance()[0];
		double noteTax = idit.getEstateTaxRate() * note;
		double noteValueToFamily = note - noteTax;
		
		CellInfo cellData[][] = {
				// Column Headers
				{	cif.getCellInfo(" "), 
					cif.getCellInfo("Keep in the Estate", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo("IDIT", Element.ALIGN_RIGHT, fontBold),
					cif.getCellInfo("Difference", Element.ALIGN_CENTER, fontBold)
					
				},
				{	cif.getCellInfo("Estate/Gift Taxable:"), 
					cif.getCellInfo(dollar.format(f), Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(dollar.format(note), Element.ALIGN_RIGHT, fontBold),
					cif.getCellInfo(dollar.format(f-note), Element.ALIGN_CENTER, fontBold)
				},
				
				{	
					cif.getCellInfo("Total Estate/Gift Taxes:"), 
					cif.getCellInfo(dollar.format(t), Element.ALIGN_CENTER, fontBRed),
					cif.getCellInfo("* " + dollar.format(noteTax), Element.ALIGN_RIGHT, fontBRed),
					cif.getCellInfo(dollar.format(t - noteTax), Element.ALIGN_CENTER, fontBRed),
				},
				{
					cif.getCellInfo("Life Insurance"), 
					cif.getCellInfo(dollar.format(0), Element.ALIGN_CENTER, fontBRed),
					cif.getCellInfo(dollar.format(idit.getLifeDeathBenefit()), Element.ALIGN_RIGHT, fontBRed),
					cif.getCellInfo(dollar.format(idit.getLifeDeathBenefit()), Element.ALIGN_CENTER, fontBRed),					
				},
				{	cif.getCellInfo("Total to Family"),
					cif.getCellInfo(dollar.format(result), Element.ALIGN_CENTER, fontBGreen),
					cif.getCellInfo(dollar.format(balance + noteValueToFamily), Element.ALIGN_RIGHT, fontBGreen),
					cif.getCellInfo(dollar.format((balance+noteValueToFamily)-result), Element.ALIGN_CENTER, fontBGreen),
				},
		};

		// Do the chart
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = "IDIT";
		
		String cat1 = "Estate Taxes";
		String cat2 = "Total to Family";

		dataSet.addValue(t, series1, cat1);
		dataSet.addValue(noteTax, series2, cat1);
		
		dataSet.addValue(result, series1, cat2);
		dataSet.addValue(balance+noteValueToFamily, series2, cat2);

		BarChart barChart = new BarChart();
		
		barChart.setTitle("IDIT Comparison");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		table.setTableData(cellData);
		table.drawTable();

		drawLabel("* Taxable gifts are covered by the lifetime exemption.", prctBottom, "GARA.TTF", new Color(64,64,64), 9, LBL_LEFT, LBL_BOTTOM);
		newPage();
	}
    public void page4() {
		
		drawBorder(pageIcon, "IDIT");
		String clientHeading = userInfo.getFirstName() + " " + userInfo.getLastName();
		drawHeader(clientHeading, "Intentionally Defective Irrevocable Trust (IDIT)");
		
		// Summary
		Rectangle rct = new Rectangle(prctTop);
		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 3);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		table.setFontSize(12);
		float widths[] = {.45f,.1f, .45f};
		int alignments[] = {PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_RIGHT};
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);

		String noteStructure = "Level Principal + Interest";
		
		if(idit.getNoteType()==1)
			noteStructure = "Amoritized";
		if(idit.getNoteType()==2)
			noteStructure = "Interest + Balloon";
		
		String cells[][] = {
				{"SUMMARY","","","[colspan=3,align=center,bold=1][][]"},
				{"","","","[][][]"},
				{"Asset Value","",dollar.format(idit.getFmv()),"[][][]"},
				{"Discount","",percent.format(idit.getDiscount()),"[][][]"},
				{"Initial Gift","",dollar.format(idit.getGiftAmount()),"[][][]"},
				{"Note Value","",dollar.format(idit.getDmv()),"[][][]"},
				{"Note Term","", number.format(idit.getNoteTerm()),"[][][]"},
				{"Note Interest Rate","", percent.format(idit.getNoteRate()),"[][][]"},
				{"Note Payment Structure","", noteStructure,"[][][]"},
				{"Life Insurance Death Benefit","",dollar.format(idit.getLifeDeathBenefit()),"[][][]"},
				{"Life Insurance Premium","",dollar.format(idit.getLifePremium()),"[][][]"},
				{"Number of Years to Pay Premium","",Integer.toString(idit.getLifePremiumYears()),"[][][]"}
		};
		
		table.buildTableEx(cells);
		table.drawTable();
		
		// Build the Cash Flow Table here!!!!
		// Draw cash flow table		newPage();

		rct = new Rectangle(prctBottom);
		rct.setTop(rct.getTop() + (1.f * 72));
		float widths2[] = { .05f, .2f, .1f, .1f, .1f,.1f, .2f };
		int alignments2[] = { PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT };

		PageTable cashFlow = new PageTable(writer);
		cashFlow.setTableFormat(rct,7);
		cashFlow.setTableFont("arial.TTF");
		cashFlow.setTableFontBold("arialbd.TTF");
		cashFlow.setFontSize(12);

		cashFlow.setColumnWidths(widths2);
		cashFlow.setColumnAlignments(alignments2);

		String heading[][] = {
				{ "Trust Cash Flow", "","","","","","","[bold=1,colspan=7,align=center][][][][][][]"},
				{ " ", "", "", "", "", "", "", "[][][][][][][]" },
				{
						"Year",
						"Beg. Principal",
						"Growth",
						"Income",
						"Payout",
						"Premium",
						"End Principal",
						"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"+
						"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"+
						"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]" } };

		String rows[][] = getCashFlow();
		String singleRow[][] = new String[1][8];

		String cont[][] = {
				{ "[Continued on Next Page]", "", "","","","","","[bold=1,colspan=7,align=center][][][][][][]"},
				{ " ", "", "", "", "","","", "[][][][][][][]" }};
		
		int idx = 0;
		
		if(idit.getNoteTerm()>17){
			cashFlow.buildTableEx(heading);
			while(idx < 12){
				singleRow[0] = rows[idx++];
				cashFlow.buildTableEx(singleRow);
			}
			cashFlow.buildTableEx(cont);
			cashFlow.drawTable();

			newPage();	// We need to go to a new page
			
			// Redraw heading
			drawBorder(pageIcon, "IDIT");
			drawHeader(clientHeading, "Intentionaly Defective Irrevocable Trust");
			
			// Readraw Table Heading
			rct = new Rectangle(prctTop);
			cashFlow = new PageTable(writer);
			cashFlow.setTableFormat(rct, 7);
			cashFlow.setTableFont("arial.TTF");
			cashFlow.setTableFontBold("arialbd.TTF");
			cashFlow.setFontSize(12);
			cashFlow.setColumnWidths(widths2);
			cashFlow.setColumnAlignments(alignments2);
			
			heading[0][0] = "Trust Cash Flow (Cont.)";
			cashFlow.buildTableEx(heading);
			
			// Include the missing rows
			while(idx < idit.getNoteTerm()){
				singleRow[0] = rows[idx++];
				cashFlow.buildTableEx(singleRow);
			}
			cashFlow.drawTable();
		} else {
			cashFlow.buildTableEx(heading);
			cashFlow.buildTableEx(getCashFlow());
			cashFlow.drawTable();
		}
	}

    public void setIdit(Idit idit) {
		this.idit = idit;
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
