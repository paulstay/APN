/*
 * Created on Apr 28, 2005
 *
 */
package com.teag.reports;

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

/**
 * @author Paul R. Stay
 *
 */
public class GRATPages extends Page {

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
	private double marketValue;
	private double yrsGrowth;
	private double growthRate;
	private double incomeRate;
	private double estateTax;
	private double estateTaxGRAT;
	private double estateTaxPercent;
	private double netToFamily;
	private double netToFamilyGRAT;
	private double totalValue;
	private double totalGRATValue;
	
	private double taxableGRATValue;
	private double trusts = 2;
	private double discount = 1688213;
	private double discountPercent = .35;
	private double clientTerm = 10;
	private double spouseTerm = 10;
	private double clientIncome = 363570;
	
	private double spouseIncome = 363570;
	private String realEstate = "Real Estate Properties";
	private int years;
	private double gratValue;
	private double estateTaxRate;
	private double lifeBenefit;
	private double premium;
	private double cashValue;
	
	private double protection;
	
	private double taxSavings;
	
	private boolean useInsurance = false;

	
	private boolean useLLC = true;
	
	
	
	public GRATPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}
//	***************************************************************************************************	
	
	
	public void draw()
	{

		page1();
		newPage();
		page2();
		newPage();
		page3();
		if(useInsurance)
		{
			newPage();
			page4();
		}
	}

	/**
	 * @return Returns the cashValue.
	 */
	public double getCashValue() {
		return cashValue;
	}
	
	/**
	 * @return Returns the clientIncome.
	 */
	public double getClientIncome() {
		return clientIncome;
	}	
	
	
	
	
	
	/**
	 * @return Returns the clientTerm.
	 */
	public double getClientTerm() {
		return clientTerm;
	}
	/**
	 * @return Returns the discount.
	 */
	public double getDiscount() {
		return discount;
	}
	/**
	 * @return Returns the discountPercent.
	 */
	public double getDiscountPercent() {
		return discountPercent;
	}
	/**
	 * @return Returns the extateTax.
	 */
	public double getEstateTax() {
		return estateTax;
	}
	/**
	 * @return Returns the estateTaxGRAT.
	 */
	public double getEstateTaxGRAT() {
		return estateTaxGRAT;
	}
	/**
	 * @return Returns the estateTaxPErcent.
	 */
	public double getEstateTaxPercent() {
		return estateTaxPercent;
	}
	/**
	 * @return Returns the estateTaxRate.
	 */
	public double getEstateTaxRate() {
		return estateTaxRate;
	}
	/**
	 * @return Returns the gratValue.
	 */
	public double getGratValue() {
		return gratValue;
	}
	/**
	 * @return Returns the growthRate.
	 */
	public double getGrowthRate() {
		return growthRate;
	}
	/**
	 * @return Returns the incomeRate.
	 */
	public double getIncomeRate() {
		return incomeRate;
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
	 * @return Returns the netToFamily.
	 */
	public double getNetToFamily() {
		return netToFamily;
	}
	/**
	 * @return Returns the netToFamilyGRAT.
	 */
	public double getNetToFamilyGRAT() {
		return netToFamilyGRAT;
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
	 * @return Returns the realEstate.
	 */
	public String getRealEstate() {
		return realEstate;
	}
	/**
	 * @return Returns the spouseIncome.
	 */
	public double getSpouseIncome() {
		return spouseIncome;
	}
	/**
	 * @return Returns the spouseTerm.
	 */
	public double getSpouseTerm() {
		return spouseTerm;
	}
	/**
	 * @return Returns the taxableGRATValue.
	 */
	public double getTaxableGRATValue() {
		return taxableGRATValue;
	}
	/**
	 * @return Returns the taxSavings.
	 */
	public double getTaxSavings() {
		return taxSavings;
	}
	/**
	 * @return Returns the totalGRATValue.
	 */
	public double getTotalGRATValue() {
		return totalGRATValue;
	}
	/**
	 * @return Returns the totalValue.
	 */
	public double getTotalValue() {
		return totalValue;
	}
	public double getTrusts() {
		return trusts;
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
	private void page1()
	{
		// Page 1
		Rectangle rct;
        
		drawBorder(pageIcon,"GRAT");
        
		float ptSize = 12f;
		drawHeader(userInfo.getClientHeading(), "The Problem");
		String gratExplanation = "Due to your sound financial acumen, your " + realEstate + " property(ies) provides steady growth and income. However, the fair market value plus growth and income not spent continue to add to your estate tax liability.";
		String gratExplanation2 = "When one's estate (and the estate tax liability) is growing faster than can be reduced by personal spending and normal (tax free) gifting to one's family, leveraged gifts can be very effective.  This process can reduce the value of the assets being transferred, often resulting in the reduction of taxes.\n\n\n" + 
				"Let's examine a double discount leveraging technique that is often utilized with this circumstance.";
		
		rct = drawLabel(gratExplanation,prctTop, "GARA.TTF",  new Color(0,0,0), ptSize, LBL_LEFT, LBL_TOP);
		
		
		drawLabel(gratExplanation2, prctLRight, "GARA.TTF",  new Color(0,0,0), ptSize, LBL_LEFT, LBL_CENTER);
		
		// Now we need to display the table
		float left = rct.getLeft() + (rct.getWidth() * .125f);
		float right = rct.getRight() - (rct.getWidth() * .125f);
		
		rct.setLeft(left);
		rct.setRight(right);
		rct.setTop( rct.getBottom());
		rct.setBottom(prctTop.getBottom());
		
		//drawFilledRect(rct, new Color(128,255,128));
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

		CellInfo cellData[][] = {
				{cif.getCellInfo("Today's Fair Market Value"),
					cif.getCellInfo(dollar.format(marketValue), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo("Projected Years of Growth"), 
					cif.getCellInfo("" + number.format(yrsGrowth), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo("Average Growth Rate"), 
					cif.getCellInfo(percent.format(growthRate), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo("Average Income Rate"), 
					cif.getCellInfo(percent.format(incomeRate), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo(" "),
					cif.getCellInfo(" ")},
					
				{cif.getCellInfo("Total Value (projected) in Taxable Estate", new Font(baseFontBold, ptSize, Font.NORMAL)),
					cif.getCellInfo(dollar.format(totalValue), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL))},
				
				{cif.getCellInfo("Estate Tax (" + percent.format(estateTaxRate) + ")", new Font(baseFontBold, ptSize, Font.NORMAL, red)), 
					cif.getCellInfo(dollar.format(estateTax), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL, red))},
				
				{cif.getCellInfo("Net Value Passing to Family", new Font(baseFontBold, ptSize, Font.NORMAL, green)),
					cif.getCellInfo(dollar.format(netToFamily), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL, green))}
		};
		
		
		// Do the chart
		
		table.setColumnWidths(widths);
		table.setTableData(cellData);
		table.drawTable();

		int et = (int)(estateTaxRate * 100);
		int tofam = 100 - et;
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Estate Tax", et);
        dataset.setValue("Net to Family", tofam);

        PiePlot3D plot = new PiePlot3D(dataset);
        plot.setLabelGenerator(new StandardPieItemLabelGenerator());
        plot.setInsets(new Insets(0,5,5,5));
        plot.setToolTipGenerator(new CustomToolTipGenerator());
        plot.setLabelGenerator(new CustomLabelGenerator());
        plot.setSectionPaint(0,red);
        plot.setSectionPaint(1,green);
        plot.setForegroundAlpha(.6f);
        plot.setOutlinePaint(Color.white);
        plot.setBackgroundPaint(Color.white);
        
        JFreeChart chart = new JFreeChart("Asset Distribution",JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        chart.setBackgroundPaint(Color.white);
        chart.setAntiAlias(true);

        Rectangle page = prctLLeft;
        
        
        try
		{
        	Image img = Image.getInstance(chart.createBufferedImage((int)page.getWidth(), (int)page.getHeight()), null);
        	drawDiagram(img, prctLLeft, 0, 72);
		}
        catch( Exception e)
		{
        	System.out.println(e.getMessage());
		}
		
	}
	private void page2()
	{
		// Page 2
        drawBorder(pageIcon,"GRAT");
        
		drawHeader(userInfo.getClientHeading(), "Grantor Retained Annuity Trust (GRAT)");
		
		Rectangle rct = new Rectangle(prctTop);
		rct.setBottom(rct.getBottom() - Page._1_4TH);
		rct.setLeft(rct.getLeft() - Page._1_4TH);
		if( userInfo.isSingle()) {
			if(( userInfo.getClientGender().equalsIgnoreCase("M"))) {
				if( useLLC) {
					drawDiagram("LLC-GRAT-M.png", rct, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-M.png", rct, CNTR_LR + CNTR_TB);
				}
			} else {
				if( useLLC){
					drawDiagram("LLC-GRAT-F.png", rct, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-F.png", rct, CNTR_LR + CNTR_TB);
				}
			}
		} else {
			if( useLLC) {
				drawDiagram("LLC-GRAT1.png",rct, LEFT + BOTTOM);
			} else {
				drawDiagram("GRAT1.png",rct, LEFT + BOTTOM);
			}
		}
		
		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		
		if( userInfo.isSingle()) {
			String theProcess[];
			if( useLLC) {
				theProcess = new String[] {
						"A Limited Liability Company (LLC) is formed with Managing Member Interest (MMI) and Member Interest (MI). Assets are then transferred to the LLC.",
						"A Grantor Retained Annuity Trust (GRAT) is established for " + userInfo.getClientFirstName() + ", and MI interests are gifted to the trust. The gifts then receive a substantial discount from their original value.",
						userInfo.getClientFirstName() + " receives a fixed annual payment from the income generated by the assets transferred to the LLC/GRAT.",
						"At the end of the GRAT term, the property plus any appreciation and accumulated earnings not distrubuted, pass estate-tax free to the family.",
					};
				
			} else {
				theProcess = new String[] {
						"A Family Limited Partnership (FLP) is formed with General Partnership (GP) interest and Limited Partnership (LP) interest. Assets are then transferred to the FLP.",
						"A Grantor Retained Annuity Trust (GRAT) is established for " + userInfo.getClientFirstName() + ", and LP interests are gifted to the trust. The gifts then receive a substantial discount from their original value.",
						userInfo.getClientFirstName() + " receives a fixed annual payment from the income generated by the assets transferred to the FLP/GRAT.",
						"At the end of the GRAT term, the property plus any appreciation and accumulated earnings not distrubuted, pass estate-tax free to the family.",
					};
			}
			
				drawSection(rct, "The Process", theProcess, 1);
		} else {
			String theProcess[];
			if( useLLC) {
				theProcess = new String[] {
						"A Limited Liability Company (LLC) is formed with Managing Member Interest (MMI) and Member Interests (MI). Assets are then transferred to the LLC.",
						"Two Grantor Retained Annuity Trusts (GRATs) are also established, one each for " + userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + " and Member Interests are gifted to the trusts. These gifts then receive a substantial discount from their original value.",
						userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + " each receive a fixed annual payment from the income generated by the assets transferred to the LLC/GRATs.",
						"At the end of the GRAT terms, the property plus any appreciation and accumulated earnings not distrubuted, pass estate-tax free to the family.",
					};
			} else {
				theProcess = new String[] {
						"A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are then transferred to the FLP.",
						"Two Grantor Retained Annuity Trusts (GRATs) are also established, one each for " + userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + " and LP interests are gifted to the trusts. These gifts then receive a substantial discount from their original value.",
						userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + " each receive a fixed annual payment from the income generated by the assets transferred to the FLP/GRATs.",
						"At the end of the GRAT terms, the property plus any appreciation and accumulated earnings not distrubuted, pass estate-tax free to the family.",
					};
				
			}
				drawSection(rct, "The Process", theProcess, 1);
			
		}
		Rectangle r;
		
		if( userInfo.isSingle()) {
			String benefits[];
			if( useLLC) {
				benefits =  new String[]{
						"Valuation discounts are made possible by the LLC.",
						"Leveraged gifts are made to the heirs through the GRAT, thus further reducing the taxable gift on the assets transferred.",
						"Estate taxes are greatly reduced.",
						"Grantor receives a fixed income stream for the term of the trust.",
						"Excess earnings and growth in the value of the assets add to the benefit to the family"
				};				
			} else {
				benefits =  new String[]{
						"Valuation discounts are made possible by the FLP.",
						"Leveraged gifts are made to the heirs through the GRAT, thus further reducing the taxable gift on the assets transferred.",
						"Estate taxes are greatly reduced.",
						"Grantor receives a fixed income stream for the term of the trust.",
						"Excess earnings and growth in the value of the assets add to the benefit to the family"
				};
			}
			
			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - Page._1_4TH);
			r = drawSection(rct, "Benefits of using this technique", benefits, 2);
			String effects[];
			if( useLLC) {
				effects = new String[] {
						"If a death occurs before the end of the GRAT term, assets in the GRAT revert (by formula) to the taxable estate of the deceased.",
						"At the end of the GRAT term, the LLC Interests retain your tax basis." 
					};
				
			} else {
				effects = new String[] {
						"If a death occurs before the end of the GRAT term, assets in the GRAT revert (by formula) to the taxable estate of the deceased.",
						"At the end of the GRAT term, the Partnership Interests retain your tax basis." 
					};
			}
			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight())  );
			r = drawSection(rct, "Side effects/Draw backs of using this technique", effects, 2);
		} else {
			String benefits[] = {
					"Valuation discounts are made possible by the " + (useLLC ? "LLC" : "FLP")+".",
					"Leveraged gifts are made to the heirs through the GRATs, thus further reducing the taxable gift on the assets transferred.",
					"Estate taxes are greatly reduced.",
					"Grantors receive a fixed income stream for the term of the trust.",
					"Excess earnings and growth in the value of the assets add to the benefit to the family"
			};
			
			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - Page._1_4TH);
			r = drawSection(rct, "Benefits of using this technique", benefits, 2);
			String effects[] = {
					"If a death occurs before the end of the GRAT term, assets in the GRAT revert (by formula) to the taxable estate of the deceased.",
					"At the end of the GRAT terms, the Partnership Interests retain your tax basis." 
			};
				
			rct = new Rectangle(prctLRight);
			rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight())  );
			r = drawSection(rct, "Side effects/Draw backs of using this technique", effects, 2);
		}

		
		
	}
	//	***************************************************************************************************
	private void page3()
	{
		
		// Page 3
        drawBorder(pageIcon,"GRAT");
        
		drawHeader(userInfo.getClientHeading(), "Grantor Retained Annuity Trust (GRAT)");
		
		Rectangle diag = new Rectangle(prctTop);
		diag.setBottom(diag.getBottom() - _1_4TH);
		diag.setLeft(diag.getLeft() - _1_4TH);
		
		//drawFilledRect(rct, new Color(255,192,192));
		
		if( userInfo.isSingle()) {
			if( userInfo.getClientGender().equalsIgnoreCase("M")) {
				if( useLLC) {
					drawDiagram("LLC-GRAT-M.png", diag, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-M.png", diag, CNTR_LR + CNTR_TB);
				}
			} else {
				if( useLLC) {
					drawDiagram("LLC-GRAT-F.png", diag, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT-F.png", diag, CNTR_LR + CNTR_TB);
				}
			}
		} else {
			if( trusts == 1) {
				if( useLLC) {
					drawDiagram("LLC-GRAT1.png", diag, CNTR_LR + CNTR_TB);
				} else {
					drawDiagram("GRAT1.png", diag, CNTR_LR + CNTR_TB);
				}
			} else {
				if( useLLC) {
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
		Rectangle rct = toPage(orgX, orgY, new Rectangle(0,0, I2P(4.1609f), I2P(2.9445f) ));
//		drawLabel("Discounted Value to GRAT\n(@ " + percent.format(discountPercent) + ") " + dollar.format(discount), rct, "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_TOP);
		
		// HERS
		rct = toPage(orgX, orgY, new Rectangle(0, 0, I2P(8.4298f), I2P(2.9445f)));
//		drawLabel("Discounted Value to GRAT\n(@ " + percent.format(discountPercent) + ") " + dollar.format(discount), rct, "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_TOP);
		
		// HIS Asset transfer amount
		rct = toPage(orgX, orgY, new Rectangle(I2P(1.1207f), I2P(1.6741f), I2P(1.8082f), I2P(8.5f)));
//		drawLabel(dollar.format((marketValue / 2)), rct, "GARA.TTF",textColor,10f, LBL_CENTER, LBL_BOTTOM);
		
		// HERS Asset transfer amount
		rct = toPage(orgX, orgY, new Rectangle(I2P(5.3897f), I2P(1.6741f), I2P(6.0772f), I2P(8.5f)));
//		drawLabel(dollar.format((marketValue / 2)), rct, "GARA.TTF",textColor,10f, LBL_CENTER, LBL_BOTTOM);
		
		// HIS annuity amount		
		rct = toPage(orgX, orgY, new Rectangle(I2P(1.4641f), I2P(1.4018f), I2P(3.2498f), I2P(8.5f)));
//		drawLabel(dollar.format(clientIncome) + " per Year for " + integer.format(clientTerm) + " Years", rct, "GARA.TTF",textColor,10f, LBL_CENTER, LBL_BOTTOM);
		
		// HERS annuity amount
		rct = toPage(orgX, orgY, new Rectangle(I2P(5.7331f), I2P(1.4018f), I2P(7.5188f), I2P(8.5f)));
//		drawLabel(dollar.format(clientIncome) + " per Year for " + integer.format(spouseTerm) + " Years", rct, "GARA.TTF",textColor,10f, LBL_CENTER, LBL_BOTTOM);		
		
		// HIS Double Discount
		rct = toPage(orgX, orgY, new Rectangle(0, I2P(0.256F), I2P(1.3019F), I2P(8.5F)));
//		drawLabel("Double\nDiscounted Gift\nto Family", rct, "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_BOTTOM);

		// HERS Double Discount
		rct = toPage(orgX, orgY, new Rectangle(0, I2P(0.256F), I2P(5.5709F), I2P(8.5F)));
//		drawLabel("Double\nDiscounted Gift\nto Family", rct, "GARA.TTF",textColor,10f, LBL_RIGHT, LBL_BOTTOM);
		
		//Rectangle rctx = drawLabel("Comparison in 25 years", prctLLeft, "GARA.TTF", new Color(98,98,98), 14f, LBL_LEFT, LBL_TOP);
		Rectangle rctx = drawSection(prctLLeft, "Comparison in " + userInfo.getFinalDeath() + " Years", new String[0], 0);
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
		Color green = new Color(0,176,0);
		Color red = new Color(192,0,0);
		Font fontBold = new Font(baseFontBold, ptSize, Font.NORMAL);
		Font fontBGreen = new Font(baseFontBold, ptSize, Font.NORMAL, green);
		Font fontBRed = new Font(baseFontBold, ptSize, Font.NORMAL, red);
		
		String grat;
		if(userInfo.isSingle()) {
			grat = "GRAT";
		} else {
			grat = "GRATS";
		}
		CellInfo cellData[][] = {
				// Column Headers
				{	cif.getCellInfo(" "), 
					cif.getCellInfo("Keep in the Estate", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(grat, Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo("Difference", Element.ALIGN_CENTER, fontBold)
					
				},
/*				
				{	cif.getCellInfo("Total Value:"), 
					cif.getCellInfo(dollar.format(totalValue), Element.ALIGN_CENTER),
					cif.getCellInfo(dollar.format(totalGRATValue) + "*", Element.ALIGN_CENTER),
					cif.getCellInfo(" ")
				},
*/			
				{	cif.getCellInfo("Estate/Gift Taxable:"), 
					cif.getCellInfo(dollar.format(totalValue), Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(dollar.format(taxableGRATValue), Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(dollar.format(totalValue - taxableGRATValue), Element.ALIGN_CENTER, fontBold)
				},
				
				{	cif.getCellInfo("Total Estate/Gift Taxes:"), 
					cif.getCellInfo(dollar.format(estateTax), Element.ALIGN_CENTER, fontBRed),
					cif.getCellInfo(dollar.format(0) + " *", Element.ALIGN_CENTER, fontBRed),
					cif.getCellInfo(dollar.format(estateTax), Element.ALIGN_CENTER, fontBRed),
				},
				
				{	cif.getCellInfo("Total to Family"),
					cif.getCellInfo(dollar.format(netToFamily), Element.ALIGN_CENTER, fontBGreen),
					cif.getCellInfo(dollar.format(netToFamilyGRAT), Element.ALIGN_CENTER, fontBGreen),
					cif.getCellInfo(dollar.format(Math.abs(netToFamilyGRAT - netToFamily)), Element.ALIGN_CENTER, fontBGreen)
				},
		};
/*
		rct = toPage(orgX, orgY, new Rectangle(I2P(1.5f), I2P(3.5f), I2P(1.75f), I2P(2.0f)));
		drawLabel("*Value transfered minus retained income.", rct, "GARA.TTF", textColor,10f, LBL_CENTER, LBL_BOTTOM);		
*/		
		// Do the chart
		
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Estate Taxes";
		String series2 = "Total To Family";
		
		String cat1 = "Estate Taxes";
		String cat2 = "Total to Family";
		
		dataSet.addValue(estateTax, series1, cat1);
		dataSet.addValue(estateTaxGRAT, series2, cat1);
		
		dataSet.addValue(netToFamily, series1, cat2);
		dataSet.addValue(netToFamilyGRAT, series2, cat2);


		BarChart barChart = new BarChart();
		
		barChart.setTitle("GRAT Comparison");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.setAlpha(1.0f);
		barChart.setDomainAxisLabel("");
		barChart.setLegendOn(false);
		barChart.generateBarChart();

		barChart.setSeriesColor(new Color(0xff0000),new Color(0x00ff00));
		barChart.setColor(0,Color.red);
		barChart.setColor(1,Color.green);
		barChart.setLablesOff(true);
		//barChart.setColor(0,new Color(0xff0000));
		//barChart.setColor(1, new Color(0x00FF00));
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		table.setTableData(cellData);
		table.drawTable();

		drawLabel("* Taxable gifts may be covered by the lifetime exemption.", prctBottom, "GARA.TTF", new Color(64,64,64), 9, LBL_LEFT, LBL_BOTTOM);

	}
	//***************************************************************************************************
	private void page4()
	{
        drawBorder(pageIcon,"GRAT/Insurance");
		drawHeader(userInfo.getClientHeading(), "Eliminating the GRAT Side Effects");
		
		String t1[] =
		{
			"By strategically combining the tax advantages of the " + ((int)getClientTerm()) +" Year GRAT with life insurance, your plan may eliminate the side effects (see below) of the GRAT technique. The insurance is designed to provide you with " + percent.format(1.0 - taxSavings) + " of the tax savings, regardless of when deaths occur."
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
				"" + number.format(gratValue * estateTaxRate),
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
				""+ percent.format(taxSavings),
				"[][align=right][align=right,bold=1]",
			},
			{
				"Percent of Tax Savings Realized",
				"",
				"" + percent.format(1.0 - taxSavings),
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
	 * @param cashValue The cashValue to set.
	 */
	public void setCashValue(double cashValue) {
		this.cashValue = cashValue;
	}
	/**
	 * @param clientIncome The clientIncome to set.
	 */
	public void setClientIncome(double clientIncome) {
		this.clientIncome = clientIncome;
	}
	/**
	 * @param clientTerm The clientTerm to set.
	 */
	public void setClientTerm(double clientTerm) {
		this.clientTerm = clientTerm;
	}
	/**
	 * @param discount The discount to set.
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}
    /**
	 * @param discountPercent The discountPercent to set.
	 */
	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}
    /**
	 * @param extateTax The extateTax to set.
	 */
	public void setEstateTax(double extateTax) {
		this.estateTax = extateTax;
	}


	/**
	 * @param estateTaxGRAT The estateTaxGRAT to set.
	 */
	public void setEstateTaxGRAT(double estateTaxGRAT) {
		this.estateTaxGRAT = estateTaxGRAT;
	}
	/**
	 * @param estateTaxPErcent The estateTaxPErcent to set.
	 */
	public void setEstateTaxPercent(double estateTaxPercent) {
		this.estateTaxPercent = estateTaxPercent;
	}
	/**
	 * @param estateTaxRate The estateTaxRate to set.
	 */
	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}
	/**
	 * @param gratValue The gratValue to set.
	 */
	public void setGratValue(double gratValue) {
		this.gratValue = gratValue;
	}
	/**
	 * @param growthRate The growthRate to set.
	 */
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	/**
	 * @param incomeRate The incomeRate to set.
	 */
	public void setIncomeRate(double incomeRate) {
		this.incomeRate = incomeRate;
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
	 * @param netToFamily The netToFamily to set.
	 */
	public void setNetToFamily(double netToFamily) {
		this.netToFamily = netToFamily;
	}
	/**
	 * @param netToFamilyGRAT The netToFamilyGRAT to set.
	 */
	public void setNetToFamilyGRAT(double netToFamilyGRAT) {
		this.netToFamilyGRAT = netToFamilyGRAT;
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
	 * @param realEstate The realEstate to set.
	 */
	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}
	/**
	 * @param spouseIncome The spouseIncome to set.
	 */
	public void setSpouseIncome(double spouseIncome) {
		this.spouseIncome = spouseIncome;
	}
	/**
	 * @param spouseTerm The spouseTerm to set.
	 */
	public void setSpouseTerm(double spouseTerm) {
		this.spouseTerm = spouseTerm;
	}
	/**
	 * @param taxableGRATValue The taxableGRATValue to set.
	 */
	public void setTaxableGRATValue(double taxableGRATValue) {
		this.taxableGRATValue = taxableGRATValue;
	}
	/**
	 * @param taxSavings The taxSavings to set.
	 */
	public void setTaxSavings(double taxSavings) {
		this.taxSavings = taxSavings;
	}
	/**
	 * @param totalGRATValue The totalGRATValue to set.
	 */
	public void setTotalGRATValue(double totalGRATValue) {
		this.totalGRATValue = totalGRATValue;
	}
	/**
	 * @param totalValue The totalValue to set.
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public void setTrusts(double trusts) {
		this.trusts = trusts;
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
}
