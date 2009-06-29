/**
 * 
 */
package com.estate.report;

import java.awt.Color;
import java.awt.Insets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.estate.pdf.CellInfo;
import com.estate.pdf.CellInfoFactory;
import com.estate.pdf.Page;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.estate.report.messages.Messages;
import com.estate.toolbox.Clat;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.bean.PdfBean;
import com.teag.chart.ClatBarRenderer;

/**
 * @author Paul
 * 
 */
public class ClatReport extends Page {

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

	Clat clat;
	double marketValue;
	int yrsGrowth;
	double growthRate;
	double incomeRate;
	double totalValue;
	
	
	double totalTax;

	double estateTax;
	
	boolean grantor = false;
	boolean escalating = false;
	boolean useLLC = false;
	String pageLabel = "CLAT";
	String pageHeading = "Charitable Lead Annuity Trust";
	
	public ClatReport(Document document, PdfWriter writer) {
		super(document, writer);
	}

	private String[][] clatCashFlow(){
		String rows[][] = new String[clat.getClatTermLength()][7];
		double prin = clat.getFmv();
		double growth = clat.getAssetGrowth();
		double income = clat.getAssetIncome();
		double payout = clat.getAnnuityPayment();
		double payIncrease = clat.getAnnuityIncrease();
		
		for(int i = 0; i < clat.getClatTermLength(); i++) {
			rows[i][0] = Integer.toString(i+1);
			rows[i][1] = dollar.format(prin);
			rows[i][2] = dollar.format(prin*growth);
			rows[i][3] = dollar.format(prin*income);
			rows[i][4] = dollar.format(-payout);
			prin = prin *(1+growth+income) - payout;
			rows[i][5] = dollar.format(prin);
			rows[i][6] = "[border=l+r+b][border=r+b][border=r+b][border=r+b][border=r+b,color="+makeColor(0,0,240)+"][border=r+b]";
			payout = payout * (1 + payIncrease);
		}
		return rows;
	}

	public void draw() {
		clat.calculate(); // Make sure we have calculated all of the inputs......
		if(clat.isGrantor())
			grantor = true;
		if(clat.isUseLLC())
			useLLC = true;
		if(clat.getAnnuityIncrease()> 0)
			escalating = true;
		
		if(grantor) {
			pageLabel= "G-CLAT";
			pageHeading = "Grantor " + pageHeading;
		}
		
		if(escalating)
			pageLabel = pageLabel + "/Esc";
		
		page1();
		page2();
		page3();
		//page6();
		page4();
	}
	
	public Clat getClat() {
		return clat;
	}

	public PdfBean getUserInfo() {
		return userInfo;
	}

	public void page1() {

		// Page 1
		Rectangle rct;
		// Rectangle rctx;
		drawBorder(pageIcon, pageLabel);
		float ptSize = 12f;
		String client = userInfo.getFirstName() + " " + userInfo.getLastName();
		drawHeader(client, "The Problem");
		String gratExplanation = "Due to your sound financial acumen, your assets provide steady growth and income. However, the fair market value plus growth and income not spent continue to add to your estate tax liability.";
		String gratExplanation2 = "When one's estate (and the estate tax liability) is growing faster than can be reduced by personal spending and normal (tax free) gifting to one's family, leveraged gifts can be very effective.  This process can reduce the value of the assets being transferred, often resulting in the reduction of taxes.\n\n\n"
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
		Font fontBold = new Font(baseFontBold, ptSize, Font.NORMAL);
		Font fontBGreen = new Font(baseFontBold, ptSize, Font.NORMAL, green);
		Font fontBRed = new Font(baseFontBold, ptSize, Font.NORMAL, red);

		marketValue = clat.getFmv();
		yrsGrowth = userInfo.getFinalDeath();
		growthRate = clat.getAssetGrowth();
		incomeRate = clat.getAssetIncome();

		totalValue = clat.getFmv();
		totalTax = 0;
		
		for (int i = 0; i < userInfo.getFinalDeath(); i++) {
			double tax = (totalValue * incomeRate) * clat.getTaxRate();
			totalTax += tax;
			totalValue += (totalValue * (growthRate + incomeRate)) -tax;
		}

		estateTax = clat.getEstateTaxRate() * totalValue;

		CellInfo cellData[][] = {
				{
						cif.getCellInfo("Todays Fair Market Value"),
						cif.getCellInfo(dollar.format(marketValue),
								Element.ALIGN_RIGHT) },

				{ cif.getCellInfo("Projected Years of Growth"),
						cif.getCellInfo("" + yrsGrowth, Element.ALIGN_RIGHT) },

				{
						cif.getCellInfo("Average Growth Rate"),
						cif.getCellInfo(percent.format(growthRate),
								Element.ALIGN_RIGHT) },

				{
						cif.getCellInfo("Average Income Rate"),
						cif.getCellInfo(percent.format(incomeRate),
								Element.ALIGN_RIGHT) },

				{ cif.getCellInfo(" "), cif.getCellInfo(" ") },

				{
						cif.getCellInfo(
								"Total Value (projected) in Taxable Estate",
								fontBold),
						cif.getCellInfo(dollar.format(totalValue),
								Element.ALIGN_RIGHT, fontBold) },

				{
						cif.getCellInfo(
								"Estate Tax ("
										+ percent.format(clat
												.getEstateTaxRate()) + ")",
								fontBRed),
						cif.getCellInfo(dollar.format(estateTax),
								Element.ALIGN_RIGHT, fontBRed) },

				{
						cif.getCellInfo("Net Value Passing to Family",
								fontBGreen),
						cif.getCellInfo(dollar.format(totalValue - estateTax),
								Element.ALIGN_RIGHT, fontBGreen) } };

		// Do the chart

		// int align[] = {Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
		table.setColumnWidths(widths);
		table.setTableData(cellData);
		table.drawTable();

		int et = (int) (clat.getEstateTaxRate() * 100);
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

	private void page2()
	{
		drawBorder(pageIcon, pageLabel);
			
		drawHeader(client, pageHeading);

		Rectangle diag = new Rectangle(prctTop);
		diag.setBottom(diag.getBottom() - _1_4TH);
		diag.setLeft(diag.getLeft() - _1_4TH);

		if( !useLLC) {
			if( userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("CLAT1-M.png",diag,LEFT + BOTTOM);
				} else {
					drawDiagram("CLAT1-F.png",diag,LEFT + BOTTOM);
				}
			} else {
				drawDiagram("CLAT1.png",diag,LEFT + BOTTOM);
			}
		} else {
			if(userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-CLAT1-M.png",diag,LEFT + BOTTOM);
				} else {
					drawDiagram("LLC-CLAT1-F.png",diag,LEFT + BOTTOM);
				}
			} else {
				drawDiagram("LLC-CLAT1.png",diag,LEFT + BOTTOM);
			}
		}
			
		// Get values to be used for placing labels.
		float orgX = diag.getLeft();
		float orgY = diag.getBottom();

		Rectangle lblRct = toPage(orgX, orgY, new Rectangle(I2P(2.2122f),I2P(1.5174f), I2P(2.9532f), I2P(4)));
		this.drawDiagramLabel("Transfer of Assets", lblRct, LBL_CENTER, LBL_BOTTOM);
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(6.4606f), I2P(1.3229f), I2P(7.2244f),I2P(4)));
		this.drawDiagramLabel("Annual Payment", lblRct, LBL_CENTER, LBL_BOTTOM);			
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(6.605f), I2P(0.6944f), I2P(7.3689f),I2P(4)));
		this.drawDiagramLabel("Deduction", lblRct, LBL_CENTER, LBL_BOTTOM);			
		
		lblRct = toPage(orgX, orgY, new Rectangle(0, I2P(0.256f), I2P(2.9866f),I2P(4)));
		this.drawDiagramLabel("Assets Pass\nto Family", lblRct, LBL_RIGHT, LBL_BOTTOM);			
		
		String theProcess[];
		String termLife = "";
		
/*
		if(clat.getClatType().equals("L")|| clat.getClatType().equals("S")){
			termLife = "(or the death of the grantor(s), whichever comes first)";
		}
*/
		
		if(grantor) {
			if(escalating){
				if(useLLC){
					theProcess = new String[] {
							String.format(Messages.getString("process.llc.g.e.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.llc.g.e.2"),
							String.format(Messages.getString("process.llc.g.e.3"),percent.format(clat.getAnnuityIncrease()), clat.getTerm(),termLife),
							Messages.getString("process.llc.g.e.4"),
							Messages.getString("process.llc.g.e.5")
					};
				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.g.e.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.g.e.2"),
							String.format(Messages.getString("process.flp.g.e.3"),percent.format(clat.getAnnuityIncrease()), clat.getTerm(),termLife),
							Messages.getString("process.flp.g.e.4"),
							Messages.getString("process.flp.g.e.5")
					};
				}
			} else {
				if(useLLC){
					theProcess = new String[] {
							String.format(Messages.getString("process.llc.g.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.llc.g.ne.2"),
							String.format(Messages.getString("process.llc.g.ne.3"), clat.getTerm(),termLife),
							Messages.getString("process.llc.g.ne.4"),
							Messages.getString("process.llc.g.ne.5")
					};

				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.g.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.g.ne.2"),
							String.format(Messages.getString("process.flp.g.ne.3"),clat.getTerm(),termLife),
							Messages.getString("process.flp.g.ne.4"),
							Messages.getString("process.flp.g.ne.5")
					};
				}
			}
		} else {
			if(escalating){
				if(useLLC){
					theProcess = new String[] {
							String.format(Messages.getString("process.llc.ng.e.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.llc.ng.e.2"),
							String.format(Messages.getString("process.llc.ng.e.3"),percent.format(clat.getAnnuityIncrease()), clat.getTerm(),termLife),
							Messages.getString("process.llc.ng.e.4"),
							Messages.getString("process.llc.ng.e.5")
					};
				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.ng.e.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.ng.e.2"),
							String.format(Messages.getString("process.flp.ng.e.3"),percent.format(clat.getAnnuityIncrease()), clat.getTerm(),termLife),
							Messages.getString("process.flp.ng.e.4"),
							Messages.getString("process.flp.ng.e.5")
					};
				}
			} else {
				if(useLLC){
					theProcess = new String[] {
							String.format(Messages.getString("process.llc.ng.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.llc.ng.ne.2"),
							String.format(Messages.getString("process.llc.ng.ne.3"), clat.getTerm(),termLife),
							Messages.getString("process.llc.ng.ne.4"),
							Messages.getString("process.llc.ng.ne.5")
					};

				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.ng.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.ng.ne.2"),
							String.format(Messages.getString("process.flp.ng.ne.3"),clat.getTerm(),termLife),
							Messages.getString("process.flp.ng.ne.4"),
							Messages.getString("process.flp.ng.ne.5")
					};

				}
			}
		}
		
		Rectangle rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		drawSection(rct, "The Process", theProcess, 1);
		
		String benefits[];
		
		if( useLLC) {
			benefits = new String[] {
					"Valuation discounts are made possible by the LLC. An additional discount is obtained by the CLAT.",
					"Only a portion of the full value of the assets is subject to estate/gift taxes.",
					"Full value of the net assets remaining in the CLAT is later transferred to the heirs without additional tax.",
					"Excess earnings and growth in the value of the assets add to the benefit for the family.",
					"The substantial tax savings are shared by the heirs and the selected charities.",
				};
		} else {
			benefits = new String[] {
					"Valuation discounts are made possible by the FLP. An additional discount is obtained by the CLAT.",
					"Only a portion of the full value of the assets is subject to estate/gift taxes.",
					"Full value of the net assets remaining in the CLAT is later transferred to the heirs without additional tax.",
					"Excess earnings and growth in the value of the assets add to the benefit for the family.",
					"The substantial tax savings are shared by the heirs and the selected charities.",
				};
		}
		
		rct = new Rectangle(prctLRight);
		Rectangle r;
		rct.setTop(rct.getTop() - Page._1_4TH);
		rct.setRight(rct.getRight() + Page._1_2TH);
		r = drawSection(rct, "Benefits of Using this Technique", benefits, 2);
		
		String s1;
		if( userInfo.isSingle()) {
			s1 = "parent's death.";
		} else {
			s1 = "parents death(s).";
		}
		
		String effects[] = {
			"All income from these assets is directed to charity or retained in the trust.",
			"Heirs lose the step-up in basis of the assets upon the " + s1,
		};
		
		rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight())  );
		r = drawSection(rct, "Side effects/Draw backs of using this technique", effects, 2);
		
		newPage();

	}
	
	
	
	public void page2Old() {
		
		String client = userInfo.getFirstName() + "  " + userInfo.getLastName();
		boolean useLLC = clat.isUseLLC();
		boolean isSingle = true;
		if(userInfo.getGender().equals("B"))
			isSingle = false;
		String gender = userInfo.getGender();
		
		drawBorder(pageIcon, pageLabel);

		drawHeader(client, "Charitable Lead Annuity Trust (CLAT)");

		Rectangle diag = new Rectangle(prctTop);
		diag.setBottom(diag.getBottom() - _1_4TH);
		diag.setLeft(diag.getLeft() - _1_4TH);

		if( !useLLC) {
			if( isSingle ) {
				if( gender.equalsIgnoreCase("M")) {
					drawDiagram("CLAT1-M.png",diag,LEFT + BOTTOM);
				} else {
					drawDiagram("CLAT1-F.png",diag,LEFT + BOTTOM);
				}
			} else {
				drawDiagram("CLAT1.png",diag,LEFT + BOTTOM);
			}
		} else {
			if(isSingle) {
				if( gender.equalsIgnoreCase("M")) {
					drawDiagram("LLC-CLAT1-M.png",diag,LEFT + BOTTOM);
				} else {
					drawDiagram("LLC-CLAT1-F.png",diag,LEFT + BOTTOM);
				}
			} else {
				drawDiagram("LLC-CLAT1.png",diag,LEFT + BOTTOM);
			}
		}
			
		// Get values to be used for placing labels.
		float orgX = diag.getLeft();
		float orgY = diag.getBottom();

		Rectangle lblRct = toPage(orgX, orgY, new Rectangle(I2P(2.2122f),I2P(1.5174f), I2P(2.9532f), I2P(4)));
		this.drawDiagramLabel("Transfer of Assets", lblRct, LBL_CENTER, LBL_BOTTOM);
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(6.4606f), I2P(1.3229f), I2P(7.2244f),I2P(4)));
		this.drawDiagramLabel("Annual Payment", lblRct, LBL_CENTER, LBL_BOTTOM);			
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(6.605f), I2P(0.6944f), I2P(7.3689f),I2P(4)));
		this.drawDiagramLabel("Deduction", lblRct, LBL_CENTER, LBL_BOTTOM);			
		
		lblRct = toPage(orgX, orgY, new Rectangle(0, I2P(0.256f), I2P(2.9866f),I2P(4)));
		this.drawDiagramLabel("Assets Pass\nto Family", lblRct, LBL_RIGHT, LBL_BOTTOM);			
		
		String theProcess[];

		if( useLLC) {
			theProcess =  new String[] {
					"A Limited Liability Corporation (LLC) is formed with Manager with Member Interest  (MMI) and Member Interest (MI). Assets are then transferred to the LLC.",
					"A Charitable Lead Annuity Trust (CLAT) is also established and MI interests are gifted to the trust. This gift receives a substantial discount from its original value.",
					"The charity(ies) of your choice receive(s) a fixed annual payment for " + Integer.toString(clat.getTerm()) + " years from the income generated by the assets transferred to the LLC/CLAT.",
					"The CLAT receives an income tax charitable deduction for the payments made to charity(ies). Any excess income is taxed and the net accumulates in the trust.",
					"At the end of the CLAT term, the assets plus any appreciation and excess income pass estate-tax free to your family."
				};
		} else {
			theProcess =  new String[] {
					"A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are then transferred to the FLP.",
					"A Charitable Lead Annuity Trust (CLAT) is also established and LP interests are gifted to the trust. This gift receives a substantial discount from its original value.",
					"The charity(ies) of your choice receive(s) a fixed annual payment for " + Integer.toString(clat.getTerm()) + " years from the income generated by the assets transferred to the FLP/CLAT.",
					"The CLAT receives an income tax charitable deduction for the payments made to charity(ies). Any excess income is taxed and the net accumulates in the trust.",
					"At the end of the CLAT term, the assets plus any appreciation and excess income pass estate-tax free to your family."
				};
		}
		
		Rectangle rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		drawSection(rct, "The Process", theProcess, 1);
		
		String benefits[];
		
		if( useLLC) {
			benefits = new String[] {
					"Valuation discounts are made possible by the LLC. An additional discount is obtained by the CLAT.",
					"Only a portion of the full value of the assets is subject to estate/gift taxes.",
					"Full value of the net assets remaining in the CLAT is later transferred to the heirs without additional tax.",
					"Excess earnings and growth in the value of the assets add to the benefit for the family.",
					"The substantial tax savings are shared by the heirs and the selected charities.",
				};
		} else {
			benefits = new String[] {
					"Valuation discounts are made possible by the FLP. An additional discount is obtained by the CLAT.",
					"Only a portion of the full value of the assets is subject to estate/gift taxes.",
					"Full value of the net assets remaining in the CLAT is later transferred to the heirs without additional tax.",
					"Excess earnings and growth in the value of the assets add to the benefit for the family.",
					"The substantial tax savings are shared by the heirs and the selected charities.",
				};
		}
		
		rct = new Rectangle(prctLRight);
		Rectangle r;
		rct.setTop(rct.getTop() - Page._1_4TH);
		rct.setRight(rct.getRight() + Page._1_2TH);
		r = drawSection(rct, "Benefits of Using this Technique", benefits, 2);
		
		String s1;
		if( isSingle) {
			s1 = "parent's death.";
		} else {
			s1 = "parents death(s).";
		}
		
		String effects[] = {
			"All income from these assets is directed to charity or retained in the trust.",
			"Heirs lose the step-up in basis of the assets upon the " + s1,
		};
		
		rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight())  );
		r = drawSection(rct, "Side effects/Draw backs of using this technique", effects, 2);
		newPage();
	}

	public void page3() {
		
		String client = userInfo.getFirstName() + "  " + userInfo.getLastName();
		boolean useLLC = clat.isUseLLC();
		boolean isSingle = true;
		if(userInfo.getGender().equals("B"))
			isSingle = false;
		String gender = userInfo.getGender();
		
		drawBorder(pageIcon, pageLabel);
		drawHeader(client, "The Comparison");

		Rectangle diag = new Rectangle(prctTop);
		diag.setBottom(diag.getBottom() - _1_4TH);
		diag.setLeft(diag.getLeft() - _1_4TH);
		

		if( useLLC) {
			if( isSingle) {
				if( gender.equalsIgnoreCase("M")) {
					drawDiagram("LLC-CLAT2-M.png", diag, LEFT + BOTTOM);
				} else {
					drawDiagram("LLC-CLAT2-L.png", diag, LEFT + BOTTOM);
				}
			} else {
				drawDiagram("LLC-CLAT2.png", diag, LEFT + BOTTOM);
			}
		} else {
			if( isSingle) {
				if( gender.equalsIgnoreCase("M")) {
					drawDiagram("CLAT2-M.png", diag, LEFT + BOTTOM);
				} else {
					drawDiagram("CLAT2-F.png", diag, LEFT + BOTTOM);
				}
			} else {
				drawDiagram("CLAT2.png", diag, LEFT + BOTTOM);
			}
		}
	
		// Get values to be used for placing labels.
		float orgX = diag.getLeft();
		float orgY = diag.getBottom();

		Rectangle lblRct = toPage(orgX, orgY, new Rectangle(I2P(2.2122f),I2P(1.3507f), I2P(2.9532f), I2P(4)));
		this.drawDiagramLabel("Total Value\nof Assets\n(" + dollar.format(clat.getFmv()) + ")", lblRct, LBL_CENTER, LBL_BOTTOM);
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(0),I2P(.256f), I2P(2.9862f), I2P(4)));
		this.drawDiagramLabel("Double Discounted\nGift to Family (" + dollar.format(clat.getRemainderInterest()) + ")", lblRct, LBL_RIGHT, LBL_BOTTOM);
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(6.4432f),I2P(.3403f), I2P(7.2927f), I2P(4)));
		this.drawDiagramLabel("Annual\nGift to\nCharity\n(" + dollar.format(clat.getAnnuityPayment()) + ")", lblRct, LBL_CENTER, LBL_BOTTOM);
		
		double discountPercent = clat.getDiscount();
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(0),I2P(0), I2P(8.4231f), I2P(2.9381f)));
		this.drawDiagramLabel("Discounted value to CLAT\n(@ " + percent.format(discountPercent) + ") " + dollar.format(clat.getDiscountValue()), lblRct, LBL_RIGHT, LBL_TOP);
		
		
		Rectangle rctx = drawSection(prctLLeft, "Comparison in " + number.format(userInfo.getFinalDeath()) + " Years", new String[0], 0);
		Rectangle rct = new Rectangle(prctLLeft);
		
		rct.setTop(rct.getTop() - rctx.getHeight());
		
		// Now do the table.
		PageTable table = new PageTable(writer);
		rct.setRight(rct.getRight() - _1_2TH);
		table.setTableFormat(rct, 4);
		CellInfoFactory cif = new CellInfoFactory();
		BaseFont baseFont = PageUtils.LoadFont("GARA.TTF");
		BaseFont baseFontBold = PageUtils.LoadFont("GARABD.TTF");
		
		float ptSize = 10f;
		cif.setDefaultFont(new Font(baseFont, ptSize));
		Color green = new Color(0,176,0);
		Color red = new Color(192,0,0);
		Color blue = new Color(0,0,192);
		Font fontBold = new Font(baseFontBold, ptSize, Font.NORMAL);
		Font fontBGreen = new Font(baseFontBold, ptSize, Font.NORMAL, green);
		Font fontBRed = new Font(baseFontBold, ptSize, Font.NORMAL, red);
		Font fontBBlue = new Font(baseFontBold, ptSize, Font.NORMAL, blue);
		
		double fmv = clat.getFmv();
		double taxes = 0;
		
		for(int i=0; i < userInfo.getFinalDeath(); i++ ){
			double t = (fmv * clat.getAssetIncome()) * clat.getTaxRate();
			taxes += t;
			//System.out.println("Taxes year " + Integer.toString(i) + " = " + number.format(t) +  "    Accum. = " + number.format(taxes));
			fmv = (fmv * (1 + clat.getAssetGrowth() + clat.getAssetIncome())) - t;
		}
		double totalValue = fmv;
		
		
		fmv = clat.getFmv();
		double cTaxes = 0;
		double netToCharity = 0;
		
		for(int i=0; i < userInfo.getFinalDeath(); i++) {
			double itax = 0;
			
			// Calculate tax from income and if still in clat term, subtract the annuity payment
			if(i<clat.getTerm()) {
				itax = ((fmv * clat.getAssetIncome()) - clat.getAnnuityPayment()) * clat.getTaxRate();
			} else {
				itax = (fmv * clat.getAssetIncome()) * clat.getTaxRate();
			}

			// We might have negative taxes, so just set it to zero
			if( itax < 0) {
				itax = 0;
			}
			
			// Accumulate the taxes!
			cTaxes += itax;
			
			//System.out.println("Taxes year " + Integer.toString(i) + " = " + number.format(itax) +  "    Accum. = " + number.format(cTaxes));
						
			// Now update the fmv and add growth, income, tax, and subtract annuity payment if still in clat term
			if( i < clat.getTerm()) {
				fmv = (fmv * (1 + clat.getAssetGrowth() + clat.getAssetIncome())) - clat.getAnnuityPayment() - itax;
				netToCharity = (netToCharity * 1.05) + clat.getAnnuityPayment();
			} else {
				fmv = (fmv * (1 + clat.getAssetGrowth() + clat.getAssetIncome())) - itax;
				netToCharity = (netToCharity * 1.05);
			}
		}

		double clatValue = fmv;
		
		///// This is the OLD way of building a table.
		CellInfo cellData[][] = {
				// Column Headers
				{	cif.getCellInfo(" "), 
					cif.getCellInfo("Keep in\nthe Estate", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(Integer.toString(clat.getTerm())+ " yr CLAT", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo("Difference", Element.ALIGN_CENTER, fontBold)
					
				},
				{	cif.getCellInfo("Total Income Taxes:"), 
					cif.getCellInfo(dollar.format(taxes), Element.ALIGN_RIGHT),
					cif.getCellInfo(dollar.format(cTaxes), Element.ALIGN_RIGHT),
					cif.getCellInfo(dollar.format(taxes - cTaxes), Element.ALIGN_RIGHT)
				},
				{	cif.getCellInfo("Estate/Gift Taxable:"), 
					cif.getCellInfo(dollar.format(totalValue), Element.ALIGN_RIGHT, fontBold),
					cif.getCellInfo(dollar.format(clat.getRemainderInterest()), Element.ALIGN_RIGHT, fontBold),
					cif.getCellInfo(dollar.format(totalValue - clat.getRemainderInterest()), Element.ALIGN_RIGHT, fontBold)
				},
				{	cif.getCellInfo("Total Estate/Gift Taxes:"), 
					cif.getCellInfo(dollar.format(totalValue * clat.getEstateTaxRate()), Element.ALIGN_RIGHT, fontBRed),
					cif.getCellInfo(dollar.format(0) + "*", Element.ALIGN_RIGHT, fontBRed),
					cif.getCellInfo(dollar.format((totalValue * clat.getEstateTaxRate()) - (clat.getRemainderInterest()*clat.getEstateTaxRate())), Element.ALIGN_RIGHT, fontBRed),
				},
				
				{	cif.getCellInfo("Total to Family"),
					cif.getCellInfo(dollar.format(totalValue - estateTax), Element.ALIGN_RIGHT, fontBGreen),
					cif.getCellInfo(dollar.format(clatValue), Element.ALIGN_RIGHT, fontBGreen),
					cif.getCellInfo(dollar.format(Math.abs(clatValue - (totalValue - estateTax))), Element.ALIGN_RIGHT, fontBGreen)
				}, 
				{	cif.getCellInfo("Total to Charity"),
					cif.getCellInfo(dollar.format(0), Element.ALIGN_RIGHT, fontBBlue),
					cif.getCellInfo(dollar.format(netToCharity), Element.ALIGN_RIGHT, fontBBlue),
					cif.getCellInfo(dollar.format(netToCharity), Element.ALIGN_RIGHT, fontBBlue)
				}
		};

		// Do the chart
		//drawDiagram("clatchart.png", prctLRight, 0);
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = Integer.toString(clat.getTerm()) + "yr CLAT";
		
		String cat1 = "IncTax";
		String cat2 = "FET";
		String cat3 = "Family";
		String cat4 = "Charity";
		
		dataSet.addValue(taxes, series1, cat1);
		dataSet.addValue(cTaxes, series2, cat1);
		
		dataSet.addValue(estateTax, series1, cat2);
		dataSet.addValue(clat.getRemainderInterest() * clat.getEstateTaxRate(), series2, cat2);
		
		dataSet.addValue(totalValue - estateTax, series1, cat3);
		dataSet.addValue(clatValue - (clat.getRemainderInterest() * clat.getEstateTaxRate()), series2, cat3);

		dataSet.addValue(0, series1, cat4);
		dataSet.addValue(netToCharity, series2, cat4);

		JFreeChart barChart = clatBarChart(dataSet);

		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight()+ _1_2TH);

		Image img = null;
		try {
			img = Image.getInstance(barChart.createBufferedImage((int) barRect
					.getWidth(), (int) barRect.getHeight()), null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		drawDiagram(img, barRect, 0, 72);
		
		table.setTableData(cellData);
		table.drawTable();
		
		// add footnote
		drawLabel("* Taxable gifts are covered by the lifetime exemption.", prctBottom, "GARA.TTF", new Color(64,64,64), 9, LBL_LEFT, LBL_BOTTOM);

		/*
		// Do the chart
		//drawDiagram("clatchart.png", prctLRight, 0);
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = Integer.toString(clat.getTerm()) + "yr CLAT";
		
		String cat1 = "IncTax";
		String cat2 = "FET";
		String cat3 = "Family";
		String cat4 = "Charity";
		
		dataSet.addValue(taxes, series1, cat1);
		dataSet.addValue(cTaxes, series2, cat1);
		
		dataSet.addValue(totalValue * clat.getEstateTaxRate(), series1, cat2);
		dataSet.addValue(clat.getRemainderInterest() * clat.getEstateTaxRate(), series2, cat2);
		
		dataSet.addValue(totalValue - (totalValue * clat.getEstateTaxRate()), series1, cat3);
		dataSet.addValue(clatValue - (clat.getRemainderInterest() * clat.getEstateTaxRate()), series2, cat3);

		dataSet.addValue(0, series1, cat4);
		dataSet.addValue(netToCharity, series2, cat4);

		BarChart barChart = new BarChart();
		
		barChart.setTitle("CLAT Comparison");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		
		table.setTableData(cellData);
		table.drawTable();
		
		// add footnote
		drawLabel("* Taxable gifts are covered by the lifetime exemption.", prctBottom, "GARA.TTF", new Color(64,64,64), 9, LBL_LEFT, LBL_BOTTOM);
		*/
		newPage();
	}

	public void page4() {
		drawBorder(pageIcon, pageLabel);
		String clientHeading = userInfo.getFirstName() + " "
				+ userInfo.getLastName();
		drawHeader(clientHeading, pageHeading);

		// Draw Assumptions
		Rectangle rct = new Rectangle(prctTop);

		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 5);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		table.setFontSize(12);
		float widths[] = { .25f, .20f, .05f, .25f, .20f };
		int alignments[] = { PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_RIGHT };
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);

		String cells[][] = {
				{ "SUMMARY", "", "", "", "",
				"[colspan=5,align=center,bold=1][][][][]" },
				{ " ", " ", " ", " ", " ",
					"[align=right,bold=1,border=b][border=b][border=b][border=b][border=b]" },
				{ "Current Age(s)", "("+Integer.toString(clat.getAge1()) + ") (" + Integer.toString(clat.getAge2())+")"
						, "", "Section 7520 Rate", "" + percent.format(clat.getIrsRate()),
					"[align=left,border=l][align=right][][align=left][align=right,border=r]" }, 
				{ "Beginning Principal", dollar.format(clat.getFmv()), "", "Estimated Transfer Date", date.format(clat.getIrsDate()),
					"[align=left,border=l][align=right][][align=left][align=right,border=r]" }, 
				{ "Discount Rate", percent.format(clat.getDiscount()), "", "Remainder Interest", dollar.format(clat.getRemainderInterest()),
					"[align=left,border=l][align=right][][align=left][align=right,border=r]" }, 
				{ "Discounted Principal", dollar.format(clat.getDiscountValue()), "", "Annuity Factor", decimal.format(clat.getAnnuityFactor()),
				"[align=left,border=l][align=right][][align=left][align=right,border=r]" }, 
				{ "Principal Growth Rate", percent.format(clat.getAssetGrowth()), "", "Annuity Payment", dollar.format(clat.getAnnuityPayment()),
					"[align=left,border=l][align=right][][align=left][align=right,border=r]" }, 
				{ "Principal Income Rate", percent.format(clat.getAssetIncome()), "", "Optimal Payment Rate", percent.format(clat.getOptimalRate()), 
					"[align=left,border=l][align=right][][align=left][align=right,border=r]" }, 
				{ "CLAT Term", number.format(clat.getClatTermLength()), "",  "Actual Payment Rate", percent.format(clat.getPayoutRate()),
					"[align=left,border=l][align=right][][align=left][align=right,border=r]" }, 
				{ "Charitable Deduction", dollar.format(clat.getCharitableDeduction()), "", "Leverage", "Infinity : 1",
					"[align=left,border=l+b][align=right,border=b][border=b][align=left,border=b][align=right,border=b+r]" }
		};

		table.buildTableEx(cells);
		table.drawTable();
		
		// Build the Cash Flow Table here!!!!
		// Draw cash flow table
		rct = new Rectangle(prctBottom);
		rct.setTop(rct.getTop() + (1.2f * 72));
		PageTable cashFlow = new PageTable(writer);
		cashFlow.setTableFormat(rct, 6);
		cashFlow.setTableFont("arial.TTF");
		cashFlow.setTableFontBold("arialbd.TTF");
		cashFlow.setFontSize(12);
		float widths2[] = {.1f,.2f,.2f,.2f,.2f,.2f};
		int alignments2[] = {PdfPCell.ALIGN_CENTER,
				PdfPCell.ALIGN_RIGHT,				PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT,				PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT
				};
		cashFlow.setColumnWidths(widths2);
		cashFlow.setColumnAlignments(alignments2);
		
		String heading[][] = {
				{
					"Charitable Lead Annuity Trust Cash Flow",
					"",
					"",
					"",
					"",
					"",
					"[bold=1,size=22,colspan=6,align=center][][][][][]"
				},
				{
					" ",
					"","","","","","[][][][][][]"
				},
				{
					"Year",
					"Beg. Principal",
					"Growth",
					"Income",
					"Payout",
					"End Principal",
					"[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]"
				}
		};
		
		//Add code here to warp to next page if greater than 17 rows.
		
		int idx = 0;
		String rows[][] = clatCashFlow();
		String singleRow[][] = new String[1][7];
		String cont[][] = {
				{"[Continued on Next Page]","","","","","","[bold=1,colspan=7,align=center][][][][][]"},
				{"","","","","","","[][][][][][]"}
		};
		
		if(clat.getClatTermLength()>20){
			cashFlow.buildTableEx(heading);
			while(idx < 15){
				singleRow[0] = rows[idx++];
				cashFlow.buildTableEx(singleRow);
			}
			cashFlow.buildTableEx(cont);
			cashFlow.drawTable();
			
			newPage();
			
			drawBorder(pageIcon,"CLAT");
			drawHeader(clientHeading, "Charitable Lead Annuity Trust");
			
			rct = new Rectangle(prctTop);
			cashFlow = new PageTable(writer);
			cashFlow.setTableFormat(rct, 6);
			cashFlow.setTableFont("arial.TTF");
			cashFlow.setTableFontBold("arialbd.TTF");
			cashFlow.setFontSize(12);
			cashFlow.setColumnWidths(widths);
			cashFlow.setColumnAlignments(alignments2);
			heading[0][0] = "Charitable Lead Annuity Trust Cash Flow (cont.)";
			cashFlow.buildTableEx(heading);
			
			// include missing rows
			while(idx < clat.getClatTermLength()){
				singleRow[0] = rows[idx++];
				cashFlow.buildTableEx(singleRow);
			}
			cashFlow.drawTable();
		} else {
			cashFlow.buildTableEx(heading);
			cashFlow.buildTableEx(clatCashFlow());
			cashFlow.drawTable();
		}
		newPage();
	}

	public void page6() {
		drawBorder(pageIcon, "T. CLAT");
		String clientHeading = userInfo.getFirstName() + " "
		+ userInfo.getLastName();
		
		drawHeader(clientHeading, "Charitable Lead Annuity Trust");

		drawDiagram("T-Clat.png", prctTop, 0);

		String sec1[] = {
					"Upon both of your deaths, a Testamentary Charitable Lead Annuity Trust (Test. CLAT), which will be described in your Living Trust, becomes operative. Except for personal effects, specific bequests, and enough liquidity to pay administrative expenses, all other remaining assets are then transferred to the Test. CLAT. Your estate will receive a substantial deduction from the taxable estate based on the term of the trust and the payout to charity.",
					"A family foundation and subsequently the charity(ies) of choice receive(s) a fixed annual payment for "
							+ " 15 or more years from the income generated by the use of assets transferred to the Test. CLAT.",
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
		newPage();
		}

	public void setClat(Clat clat) {
		this.clat = clat;
	}
	
	public JFreeChart clatBarChart(DefaultCategoryDataset dataSet) {
		JFreeChart barChart = ChartFactory.createBarChart3D("CLAT Comparison", "",
				"", dataSet,PlotOrientation.VERTICAL ,false, true, false);
		
		CategoryPlot plot = barChart.getCategoryPlot();

		barChart.setBackgroundPaint(Color.white);
		plot.setInsets(new Insets(0, 5, 5, 5));
		plot.setOutlinePaint(Color.white);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		ClatBarRenderer eRend = new ClatBarRenderer();

		eRend.setCol0(Color.red);
		eRend.setCol1(Color.red);
		eRend.setCol2(Color.green);
		eRend.setCol3(Color.blue);
		plot.setRenderer(eRend);
		
		return barChart;
	}
	
}
