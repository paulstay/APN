/*
 * Created on May 4, 2005
 *
 */
package com.teag.reports;

import java.awt.Color;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.AreaChart;
import com.teag.chart.BarChart;
import com.teag.chart.ClatBarRenderer;
import com.teag.chart.LayeredBarChart;

/**
 * @author Paul Stay
 *
 */
public class CRUTPages extends Page {

	private double capitalGainsPercent;
	private double assetValue;	// // Net proceeds from sale.
	private double basis;
	private double taxableGain;
	private double capitalGainsTax;
	private double incomeLossPerYear;
	private double incomeLossProjected;
	private double netProceeds;	// Net remaining to invest
	private double estateTaxPercent;
	private double estateTax;
	private double netToFamily;
	private double totalTaxes;
	private double percentageLost;
	private double preTaxROIPercent;
	private double estateTaxPort;
    //private int finalDeath = 25;
    
    private int cLe;
    private int sLe;
	
	private double capitalGainsTaxCRUT;

	private double taxDeduction;
	private double taxDeductionCRUT;
	
	private double annualIncomeYr1;
	private double annualIncomeYrn;
	private double cumulativeNetIncome;
	private double taxSavedDeduction;
	
	private double lifeIncomePlusTaxSavings;
	private double netToFamilyAfterTax;
	private double benefitToCharity;
	private double total;
	
	
	private double annualIncomeYr1CRUT;
	private double annualIncomeYrnCRUT;
	private double cumulativeNetIncomeCRUT;
	private double taxSavedDeductionCRUT;
	
	private double lifeIncomePlusTaxSavingsCRUT;
	private double netToFamilyAfterTaxCRUT;
	private double benefitToCharityCRUT;
	private double totalCRUT;
	
	private double spendingRate;
	private double insurancePremium;
	private int lifeExpectancy;
	
	
	private double difference;
	
	private double marginalIcomeTaxRate;
	private double sec7520Rate;
	private double charitableDeductionFactor;
	private double charitableDeduction;
	private double adjustedGI;
	private double deductionLimitRate;
	private double portfolioBalance = 6657500;
	private double trustBalance;
	private double liability;
	private double insurance = 10000000;
	
	private double rateOfIncome;
	private double calculatedMITR;
	private double realizedCapitalGain;
	private double totalPremiumsGifted;
	private double extraNetSpendable;
	private double capitalGainsSaved;
	private double incomeTaxSaved;
	private double estateTaxSaved;
	private double totalTaxesSaved;
	
	private String assetName;
	
	private double oTable[][];
	private double wTable[][];
	
	
	/**
	 * @param document
	 * @param writer
	 */
	public CRUTPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
		
	}
	
	public void draw()
	{
		page1();
		newPage();
		page2();
		newPage();
		page3();
		newPage();
		page4();
		newPage();
		page5();
	}
	
	/**
	 * @return Returns the adjustedGI.
	 */
	public double getAdjustedGI() {
		return adjustedGI;
	}
	
	/**
	 * @return Returns the annualIncomeYr1.
	 */
	public double getAnnualIncomeYr1() {
		return annualIncomeYr1;
	}
	
	/**
	 * @return Returns the annualIncomeYr1CRUT.
	 */
	public double getAnnualIncomeYr1CRUT() {
		return annualIncomeYr1CRUT;
	}
	
	/**
	 * @return Returns the annualIncomeYrn.
	 */
	public double getAnnualIncomeYrn() {
		return annualIncomeYrn;
	}	
	/**
	 * @return Returns the annualIncomeYrnCRUT.
	 */
	public double getAnnualIncomeYrnCRUT() {
		return annualIncomeYrnCRUT;
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
	 * @return Returns the basis.
	 */
	public double getBasis() {
		return basis;
	}
	/**
	 * @return Returns the benefitToCharity.
	 */
	public double getBenefitToCharity() {
		return benefitToCharity;
	}
	/**
	 * @return Returns the benefitToCharityCRUT.
	 */
	public double getBenefitToCharityCRUT() {
		return benefitToCharityCRUT;
	}
	/**
	 * @return Returns the calculatedMITR.
	 */
	public double getCalculatedMITR() {
		return calculatedMITR;
	}
	/**
	 * @return Returns the capitalGainsPercent.
	 */
	public double getCapitalGainsPercent() {
		return capitalGainsPercent;
	}
	/**
	 * @return Returns the capitalGainsSaved.
	 */
	public double getCapitalGainsSaved() {
		return capitalGainsSaved;
	}
	/**
	 * @return Returns the incomeTaxLiability.
	 */
	public double getCapitalGainsTax() {
		return capitalGainsTax;
	}
	/**
	 * @return Returns the capitalGainsTaxCRUT.
	 */
	public double getCapitalGainsTaxCRUT() {
		return capitalGainsTaxCRUT;
	}
	/**
	 * @return Returns the charitableDeduction.
	 */
	public double getCharitableDeduction() {
		return charitableDeduction;
	}
	/**
	 * @return Returns the charitableDeductionFactor.
	 */
	public double getCharitableDeductionFactor() {
		return charitableDeductionFactor;
	}
	/**
	 * @return Returns the cumulativeNetIncome.
	 */
	public double getCumulativeNetIncome() {
		return cumulativeNetIncome;
	}
	/**
	 * @return Returns the cumulativeNetIncomeCRUT.
	 */
	public double getCumulativeNetIncomeCRUT() {
		return cumulativeNetIncomeCRUT;
	}
	/**
	 * @return Returns the deductionLimitRate.
	 */
	public double getDeductionLimitRate() {
		return deductionLimitRate;
	}
	/**
	 * @return Returns the difference.
	 */
	public double getDifference() {
		return difference;
	}
	/**
	 * @return Returns the estateTax.
	 */
	public double getEstateTax() {
		return estateTax;
	}
	/**
	 * @return Returns the estateTaxPercent.
	 */
	public double getEstateTaxPercent() {
		return estateTaxPercent;
	}
	/**
	 * @return Returns the estateTaxPort.
	 */
	public double getEstateTaxPort() {
		return estateTaxPort;
	}
	/**
	 * @return Returns the estateTaxSaved.
	 */
	public double getEstateTaxSaved() {
		return estateTaxSaved;
	}
	/**
	 * @return Returns the extraNetSpendable.
	 */
	public double getExtraNetSpendable() {
		return extraNetSpendable;
	}
	/**
	 * @return Returns the incomeLossPerYear.
	 */
	public double getIncomeLossPerYear() {
		return incomeLossPerYear;
	}
	/**
	 * @return Returns the incomeLossProjected.
	 */
	public double getIncomeLossProjected() {
		return incomeLossProjected;
	}
	/**
	 * @return Returns the incomeTaxSaved.
	 */
	public double getIncomeTaxSaved() {
		return incomeTaxSaved;
	}
	/**
	 * @return Returns the insurance.
	 */
	public double getInsurance() {
		return insurance;
	}
	/**
	 * @return Returns the insurancePremium.
	 */
	public double getInsurancePremium() {
		return insurancePremium;
	}
	/**
	 * @return Returns the liability.
	 */
	public double getLiability() {
		return liability;
	}
	/**
	 * @return Returns the lifeExpectancy.
	 */
	public int getLifeExpectancy() {
		return lifeExpectancy;
	}
	/**
	 * @return Returns the lifeIncomePlusTaxSavings.
	 */
	public double getLifeIncomePlusTaxSavings() {
		return lifeIncomePlusTaxSavings;
	}
	/**
	 * @return Returns the lifeIncomePlusTaxSavingsCRUT.
	 */
	public double getLifeIncomePlusTaxSavingsCRUT() {
		return lifeIncomePlusTaxSavingsCRUT;
	}
	/**
	 * @return Returns the marginalIcomeTaxRate.
	 */
	public double getMarginalIcomeTaxRate() {
		return marginalIcomeTaxRate;
	}
	/**
	 * @return Returns the netProceeds.
	 */
	public double getNetProceeds() {
		return netProceeds;
	}
	/**
	 * @return Returns the netToFamily.
	 */
	public double getNetToFamily() {
		return netToFamily;
	}
	/**
	 * @return Returns the netToFamilyAfterTax.
	 */
	public double getNetToFamilyAfterTax() {
		return netToFamilyAfterTax;
	}
	/**
	 * @return Returns the netToFamilyAfterTaxCRUT.
	 */
	public double getNetToFamilyAfterTaxCRUT() {
		return netToFamilyAfterTaxCRUT;
	}
	/**
	 * @return Returns the oTable.
	 */
	public double[][] getOTable() {
		return oTable;
	}
	/**
	 * @return Returns the percentageLost.
	 */
	public double getPercentageLost() {
		return percentageLost;
	}
	/**
	 * @return Returns the portfolioBalance.
	 */
	public double getPortfolioBalance() {
		return portfolioBalance;
	}
	/**
	 * @return Returns the preTaxROIPercent.
	 */
	public double getPreTaxROIPercent() {
		return preTaxROIPercent;
	}
	/**
	 * @return Returns the rateOfIncome.
	 */
	public double getRateOfIncome() {
		return rateOfIncome;
	}
	/**
	 * @return Returns the realizedCapitalGain.
	 */
	public double getRealizedCapitalGain() {
		return realizedCapitalGain;
	}
	/**
	 * @return Returns the sec7520Rate.
	 */
	public double getSec7520Rate() {
		return sec7520Rate;
	}
	/**
	 * @return Returns the spendingRate.
	 */
	public double getSpendingRate() {
		return spendingRate;
	}
	/**
	 * @return Returns the taxableGain.
	 */
	public double getTaxableGain() {
		return taxableGain;
	}
	/**
	 * @return Returns the taxDeduction.
	 */
	public double getTaxDeduction() {
		return taxDeduction;
	}
	/**
	 * @return Returns the taxDeductionCRUT.
	 */
	public double getTaxDeductionCRUT() {
		return taxDeductionCRUT;
	}
	/**
	 * @return Returns the taxSavedDeduction.
	 */
	public double getTaxSavedDeduction() {
		return taxSavedDeduction;
	}
	/**
	 * @return Returns the taxSavedDeductionCRUT.
	 */
	public double getTaxSavedDeductionCRUT() {
		return taxSavedDeductionCRUT;
	}
	/**
	 * @return Returns the total.
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @return Returns the totalCRUT.
	 */
	public double getTotalCRUT() {
		return totalCRUT;
	}
	/**
	 * @return Returns the totalPremiumsGifted.
	 */
	public double getTotalPremiumsGifted() {
		return totalPremiumsGifted;
	}
	/**
	 * @return Returns the totalTaxes.
	 */
	public double getTotalTaxes() {
		return totalTaxes;
	}
	/**
	 * @return Returns the totalTaxesSaved.
	 */
	public double getTotalTaxesSaved() {
		return totalTaxesSaved;
	}
	/**
	 * @return Returns the trustBalance.
	 */
	public double getTrustBalance() {
		return trustBalance;
	}
	/**
	 * @return Returns the wTable.
	 */
	public double[][] getWTable() {
		return wTable;
	}
	private void page1()	// Problem
	{
		drawBorder(pageIcon, "CRUT");
		drawHeader(userInfo.getClientHeading(), "The Problem");
		PageTable table = new PageTable(writer);
		table.setTableFormat(prctFull, 4);
		int Red = makeColor(192,0,0);
		

		String s1;
		String s2;
		String s3;
		
		
		if( userInfo.isSingle()) {
			s1 = " and that you live ";
			s2 = "age";
			s3 = "your death";
		} else {
			s1 = " and that at least one of you lives ";
			s2 = "ages";
			s3 = "both of your deaths";
		}
		String cells[][] = {
/*row 0*/	{	"",
				"You have built several very successful assets, over many years, with much physical and emotional effort. This effort brings both good news and bad news. The good news is that you can sell them at a tremendous profit. The bad news is that the IRS wants to keep much of this profit in the form of taxes.  Let's look at the result of these taxes in three areas:",
				"",
				"",
				"[][Colspan=2][][bold=1]"
			},
/* row 1*/	{
				"",
				"",
				"",
				"",
				"[colspan=4][][][]"
			},

/*row 2*/	{	
				"A. Income Taxes",
				"",
				"",
				"",
				"[Colspan=2][][][]"
			},
/*row 3*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 4*/	{
				"",
				"Capital Gains Taxes Destroy " + percent.format(this.capitalGainsPercent) + "[1] of your profit:",
				"",
				"",
				"[][Colspan=3][][]"
			},
/*row 5*/	{
				"",
				"",
				"Value of " + this.assetName,
				"" + dollar.format(this.assetValue),
				null
			},
			/*row 6*/	{
				" "," ","Less: Cost(Basis)","" + dollar.format(this.basis),null
			},
/*row 7*/	{
				" "," ","Taxable Gain","" + dollar.format(this.taxableGain), null
			},
/*row 8*/	{
				" "," ","Income (Capital Gains) Tax Liability","" + dollar.format(this.capitalGainsTax), "[][][Color=" + Red +"][Color=" + Red +"]"
			},
/*row 9*/	{
				"","","","","[colspan=4]"
			},
/*row 10*/	{
				"","This loss of taxes also creates another loss.  All of the future earnings that could have been received had this tax amount been invested, are also lost in perpetuity!  Let's assume a return on investment of " + percent.format(this.preTaxROIPercent) + s1 + Integer.toString(userInfo.getFinalDeath()) + " years.",
				"","","[][Colspan=3][][]"
			},
/*row 11*/	{
				"","","","","[colspan=4][][][]"
			},

/*row 12*/	{	
				"B. Income Loss",""," "," ","[Colspan=2][][][]"
			},
/*row 13*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 14*/	{
				" "," ","Loss of income** potential each year for life!","" + dollar.format(this.incomeLossPerYear),null
			},
/*row 15*/	{
				" "," ","At your " + s2 + ", this projected loss of income is", "" + dollar.format(this.incomeLossProjected),null
			},
/*row 16*/	{
				" ","And, the problem gets worse...","","","[][colspan=3][][]"
			},
/*row 17*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 18*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 19*/	{
				" ","Upon " + s3 + ", the remaining amount of these proceeds (along with your other assets) will be subjected to an estate tax.  This is the largest tax of all.","","", "[][Colspan=3][][]"
			},
/*row 20*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 21*/	{	
				"C. Estate Taxes",""," "," ","[Colspan=2,Underline=1][][][]"
			},
/*row 22*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 23*/	{
				" "," ","Net Proceeds (after Capital Gains Taxes)","" + dollar.format(this.netProceeds),null
			},
/*row 24*/	{
				" "," ","Estate Tax (" + percent.format(this.estateTaxPercent)+ ")","" + dollar.format(this.estateTax),
				"[][][Color=" + Red + "][Color=" + Red + "]"
			},
/*row 25*/	{
				" "," ","Net to Family","" + dollar.format(this.netToFamily),null
			},
			/*row 26*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 27*/	{	
				"D. Where Did All The Money Go?",""," "," ", "[Colspan=3,Underline=1][][][]"
			},
/*row 28*/	{
				"","","","","[colspan=4][][][]"
			},
/*row 29*/	{
				" "," ","Sales Price","" + dollar.format(this.assetValue), null
			},
/*row 30*/	{
				" "," ","Total Taxes (Capital Gains plus Estate Tax)","" + dollar.format(this.totalTaxes), "[][][Color=" + Red + "][Color=" + Red + "]"
			},
/*row 31*/	{
				" "," ","Net to Family","" + dollar.format(this.netToFamily),null
			},
/*row 32*/	{
				" "," ","Percentage Lost to Taxes","" + percent.format(this.percentageLost),"[][][Bold=1][Bold=1]"
			},
/*row 33*/	{
				" ","","","","[colspan=4][][][]"
			},
			/*row 34*/	{
				"(The IRS' version of Capital Punishment!)","","","","[Colspan=4,Align=center][][][]"
			},
			/*row 26*/	{
				" ","","","","[colspan=4][][][]"
			},
			/*row 35*/	{
				"[1] Federal plus State Capital Gains taxes","","","","[Colspan=4,Align=left][][][]"
			},
			/*row 35*/	{
				"** Assuming a pre-tax return on investment at " +  percent.format(this.preTaxROIPercent)  + ". (" + dollar.format(this.capitalGainsTax) + " @ " + percent.format(this.preTaxROIPercent) + " = a loss of  " + dollar.format(this.incomeLossPerYear)+ " each year of life)","","","","[Colspan=4,Align=left][][][]"
			},

		};
		
		
		
		table.setTableFont("GARA.TTF");
		table.setTableFontBold("GARABD.TTF");
		float widths[] = {.10f, .10f, .60f, .20f};
		int alignments[] = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);
		table.buildTableEx(cells);
		table.drawTable();
	}
	public void page2()
	{
		drawBorder(pageIcon, "CRUT");
		drawHeader(userInfo.getClientHeading(), "Charitable Remainder Trust");
		Rectangle rct = new Rectangle(prctTop);
		rct.setBottom(rct.getBottom() - Page._1_4TH);
		rct.setLeft(rct.getLeft() - Page._1_4TH);
		if( userInfo.isSingle()) {
			if( userInfo.getClientGender().equalsIgnoreCase("M")){
				drawDiagram("WPT-M.png",rct, CNTR_LR + BOTTOM);	
			} else {
				drawDiagram("WPT-F.png",rct, CNTR_LR + BOTTOM);
			}
				
		} else {
			drawDiagram("WPT.png",rct, CNTR_LR + BOTTOM);
		}
		String wptIntro[] = {
			"Fortunately, a tool that can help solve this problem has existed for many years, although it has not been widely publicized. Created over 100 years ago, and clarified by Congress in 1969, this trust has been utilized by many top tax advisors for the benefit of their affluent clients.",
			"Many individuals who have learned about this trust thought it was too good to be true and wrote the IRS requesting clarification. Because of the burden of responding, the IRS, in 1990, published 16 versions of the trust as authorized prototypes."
		};
		this.drawSection(prctLLeft, " ", wptIntro, 0, 12, 10.5f);
		
		if( userInfo.isSingle()) {
			String genderStr = userInfo.getClientGender().equals("M") ? "his" : "her";
			String howItWorks[] = new String[] {
					"The Charitable Remainder Trust is established and certain assets are transferred to it.",
					"Assets are then sold by the trustee (this can be " + userInfo.getClientFirstName()+ ") and proceeds are reinvested (as trustee directs) into income producing assets.",
					"Cash is paid to " + userInfo.getClientFirstName() + " as the income beneficiary for "+ genderStr + " lifetime. Upon "+ genderStr + " death, the full amount continues to be paid to the survivor.",
					"Gifts are regularly made to the Family (or in trust for them) of excess cash flow, which protects their inheritance with life insurance on the life of " + userInfo.getClientFirstName() +".",
					"Upon the death of " + userInfo.getClientFirstName() + ", the remaining (CRT) assets pass to the named charities.",
				};
			this.drawSection(prctLRight, "Here's How It Works", howItWorks, 1, 12, 10.5f);
		} else {
			String howItWorks[] = new String[] {
					"The Charitable Remainder Trust is established and certain assets are transferred to it.",
					"Assets are then sold by the trustee (this can be " + userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + ") and proceeds are reinvested (as trustee directs) into income producing assets.",
					"Cash is paid to " + userInfo.getClientFirstName() + " & " + userInfo.getSpouseFirstName() + " as the income beneficiaries for their lifetimes. Upon the first death, the full amount is paid to the survivor.",
					"Gifts are regularly made to the Family (or in trust for them) of excess cash flow, which protects their inheritance with life insurance on the lives of " + userInfo.getClientFirstName() + " and " + userInfo.getSpouseFirstName() + ".",
					"Upon the deaths of " + userInfo.getClientFirstName() + " & " + userInfo.getSpouseFirstName() + ", the remaining (CRT) assets pass to the named charities.",
				};
			this.drawSection(prctLRight, "Here's How It Works", howItWorks, 1, 12, 10.5f);
		}
	}
	public void page3()
	{
		drawBorder(pageIcon, "CRUT");
		drawHeader(userInfo.getClientHeading(), "The Comparison");
		PageTable table = new PageTable(writer);
		table.setTableFormat(prctFull, 6);
		int Red = makeColor(192,0,0);
		int Green = makeColor(0,192,0);
		int Blue = makeColor(0,0,192);

		String s1;
		if( userInfo.isSingle()) {
			s1 = "death";
		} else {
			s1 = "deaths";
		}
		String cells[][] = {
			{
				"",
				"When comparing the results of a traditional outright sale to the use of a Charitable Remainder Trust (CRT) and Inheritance Trust for the family, the difference is truly astounding.",
				"",
				"",
				"",
				"",
				"[][Colspan=5,ptSize=11,font=GARA.TTF][][][][]"
			},
			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},		{
				"Taxes",
				" ",
				"Outright Sale",
				" ",
				"CRT",
				" ",
				"[colspan=2,bold=1][][align=center,border=b][][align=center,bold=1,border=b][]"
			},		{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},		{
				"",
				"Net Proceeds from Sale",
				"" + dollar.format(this.assetValue),
				"",
				""+ dollar.format(this.assetValue),
				"",
				"[][][][][bold=1][]"
			},		{
				"",
				"Capital Gains Tax (up front)",
				"" + dollar.format(this.capitalGainsTax),
				"",
				"" + dollar.format(this.capitalGainsTaxCRUT),
				"",
				"[][Color=" + Red + "][Color=" + Red + "][][Color=" + Red + "]"
			},		{
				"",
				"Net Remaining to Invest",
				"" + dollar.format(this.netProceeds),
				"",
				"" + dollar.format(this.assetValue),
				"",
				"[][][][][][]"
			},		{
				"",
				"Income Tax Deduction",
				"" + dollar.format(this.taxDeduction),
				"",
				"" + dollar.format(this.taxDeductionCRUT),
				"",
				"[][][][][][]"
			},{
				"",
				"Estate Tax (@55%)",
				"" + dollar.format(this.estateTax),
				"",
				"" + dollar.format(0),
				"",
				"[][Color=" + Red + "][Color=" + Red + "][Color=" + Red + "][Color=" + Red + "][Color=" + Red + "]"
				
			},			
			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},		{
				"Benefits During Lifetime of " + userInfo.getClientFirstName() + (!userInfo.isSingle() ? " & " + userInfo.getSpouseFirstName() : ""),
				"",
				"Outright Sale",
				"",
				"CRT",
				"",
				"[colspan=2,bold=1][][align=center,border=b][][align=center,bold=1,border=b][]"
			},		{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},		{
				"",
				"Annual Income 1st Yr. (Net after tax)",
				"" + dollar.format(this.annualIncomeYr1),
				"(1)",
				""+ dollar.format(this.annualIncomeYr1CRUT),
				"(2)(3)",
				"[][][][][Bold=1][]"
			},		{
				"",
				"Annual Income " + this.lifeExpectancy + "th Yr. (Net after tax)",
				"" + dollar.format(this.annualIncomeYrn),
				"(1)",
				"" + dollar.format(this.annualIncomeYrnCRUT),
				"(2)",
				"[][][][][bold=1][]"
			},		{
				"",
				"Cumulative Net Life Income",
				"" + dollar.format(this.cumulativeNetIncome),
				"",
				"" + dollar.format(this.cumulativeNetIncomeCRUT),
				"",
				"[][][][][bold=1][]"
			},		{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},		{
				"",
				"Tax Saved from Deduction",
				"" + dollar.format(this.taxSavedDeduction),
				"",
				"" + dollar.format(this.incomeTaxSaved),
				"",
				"[][][][][bold=1][]"
			},		{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},	
			{
				"Summary at life expectancy (" + this.lifeExpectancy + " years)",
				"",
				"Outright Sale",
				"",
				"CRT",
				"",
				"[colspan=2,Bold=1][][align=center,border=b][][align=center,bold=1,border=b][]"
			},		{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},		{
				"",
				"Life Income plus Income Tax Savings",
				"" + dollar.format(this.lifeIncomePlusTaxSavings),
				"",
				"" + dollar.format(this.lifeIncomePlusTaxSavingsCRUT),
				"",
				"[][][][][bold=1][]"
			},		{
				"",
				"Net passing to Family - after tax (4)",
				"" + dollar.format(this.netToFamilyAfterTax),
				"",
				"" + dollar.format(this.netToFamilyAfterTaxCRUT),
				"",
				"[][][color=" + Green + "][][bold=1,color=" + Green + "][]"
			},		
			{
				"",
				"Benefit to Charity(ies) (5)",
				"" + dollar.format(this.benefitToCharity),
				"",
				"" + dollar.format(this.benefitToCharityCRUT),
				"",
				"[][][color="+Blue+"][][bold=1,color="+Blue+"][]"
			},
			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},
			{
					"",
					"Total to " + userInfo.getClientFirstName() + (!userInfo.isSingle() ? " & " + userInfo.getSpouseFirstName() : "") + ", Family & Charity",
					"" + dollar.format(this.total),
					"" ,
					""+ dollar.format(this.totalCRUT),
					"",
					"[][][][][bold=1][]"
			},
			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},
			{
				"",
				"Difference",
				"",
				"",
				"" + dollar.format(this.difference),
				"",
				"[][][][][bold=1][]"
			},
			{
				" ",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},
			{
				"1.",
				"Assumes a pretax investment return of " + percent.format(this.preTaxROIPercent) + " and a spending rate of " + percent.format(spendingRate) + " (less tax). The balance (after tax) is reinvested in a growing portfolio.",
				"",
				"",
				"",
				"",
				"[align=right,valign=top,ptSize=11,font=GARA.TTF][valign=top,ptSize=11,font=GARA.TTF][][][][]"
			},
			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},
			{
				"2.",
				"Assumes a pretax investment return of " + percent.format(this.preTaxROIPercent) + " and a payout rate of "+ percent.format(spendingRate) +", reduced by taxes and a life insurance premium of " + dollar.format(insurancePremium) +". Balance, 1%, grows inside the trust.",
				"",
				"",
				"",
				"",
				"[align=right,valign=top,ptSize=11,font=GARA.TTF][valign=top,ptSize=11,font=GARA.TTF][][][][]"
			},
			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},
			
			{
				"3.",
				"In the early years, income is increased due to income tax savings.",
				"",
				"",
				"",
				"",
				"[align=right,valign=top,ptSize=11,font=GARA.TTF][valign=top,ptSize=11,font=GARA.TTF][][][][]"
			},

			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},
			{
				"4.",
				"Equals the growing portfolio, less estate tax vs. tax free life insurance.",
				"",
				"",
				"",
				"",
				"[align=right,valign=top,ptSize=11,font=GARA.TTF][valign=top,ptSize=11,font=GARA.TTF][][][][]"
			},
			
			{
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6][][][][][]"
			},
			{
				"5.",
				"Amount left in the Charitable Remainder Trust upon projected " + s1 + " in " + this.lifeExpectancy +" years.",
				"",
				"",
				"",
				"",
				"[align=right,valign=top,ptSize=11,font=GARA.TTF][valign=top,ptSize=11,font=GARA.TTF][][][][]"
			},
			
			
		};
		float widths[] = {.05f, .40f, .225f, .05f, .225f, .05f};
		int alignments[] = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_LEFT};
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);
		table.setFontSize(10);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		
		table.buildTableEx(cells);
		table.drawTable();
		
		// Insert chart here in lower right quadrant. Showing before and after Taxes, Family, and Charity.
		
		String series1 = "Before";
		String series2 = "After";
		
		String cat1 = "Tax";
		String cat2 = "Family";
		String cat3 = "Charity";

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		
		// Tax, Include capital Gains, and Estate Tax
		dataSet.addValue(estateTax + capitalGainsTax, series1, cat1);
		dataSet.addValue(0, series2, cat1);	// this should always be zero!
		
		// Family
		dataSet.addValue(netToFamilyAfterTax, series1, cat2);
		dataSet.addValue(netToFamilyAfterTaxCRUT, series2, cat2);
		
		// Charity
		dataSet.addValue(benefitToCharity, series1, cat3);
		dataSet.addValue(benefitToCharityCRUT, series2, cat3);
		
		JFreeChart chart = crtBarChart(dataSet);
		
		// We want the chart to be in the lower right quadrant
		Rectangle barRect = new Rectangle(prctLRight);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barRect.setTop(barRect.getTop() - _1_2TH);
		barRect.setBottom(barRect.getBottom() - _1_2TH);
		Image img = null;
		try {
			img = Image.getInstance(chart.createBufferedImage((int) barRect
					.getWidth(), (int) barRect.getHeight()), null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		drawDiagram(img, barRect, 0, 72);
		
		
		//drawLabel("Note: The Wealth Preservation Trust is also known as a Charitable Remainder Trust (CRT)", prctBottom, "GARA.TTF", new Color(32,32,32),10, LBL_LEFT, LBL_BOTTOM);
	}
	
	public void page4()
	{
		drawBorder(pageIcon, "CRUT");
		drawHeader(userInfo.getClientHeading(), "Charitable Remainder Trust");

		int inc = 1;
		
		// Draw first chart
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Outright Sale";
		String series2 = "CRT";
		
		for(int i = 0; i < lifeExpectancy; i+=inc) {
			dataSet.addValue(wTable[i][5], series2, Integer.toString(i));
			dataSet.addValue(oTable[i][3], series1, Integer.toString(i));
		}

		Color t1 = new Color(0,202, 0);
		Color t2 = new Color(0,125, 0);
		BarChart barChart = new BarChart();
		barChart.setTitle("Investment Portfolio");
		barChart.setDataSet(dataSet);
		Rectangle bRect = new Rectangle(prctULeft);
		barChart.setRect(bRect);
		barChart.setDomainAxisLabel("Years");
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setAlpha(1.0f);
		barChart.setCategoryMargin(0f);
		barChart.generateBarChart();
		barChart.setColor(0, t1);
		barChart.setColor(1,t2);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, bRect, 0,72);
		
		AreaChart areaChart1 = new AreaChart();
		areaChart1.setTitle("Investment Portfolio");
		areaChart1.setDataSet(dataSet);
		Rectangle cRect = new Rectangle(prctULeft);
		areaChart1.setRect(cRect);
		areaChart1.setDomainAxisLabel("Years");
		areaChart1.generateAreaChart();
		//Image img = areaChart1.getBarChartImage();
		//drawDiagram(img, cRect, 0,72);
		
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
		
		for(int i= 0; i < lifeExpectancy; i+=inc) {
			dataSet.addValue(wTable[i][4], series2, Integer.toString(i));
			dataSet.addValue(oTable[i][2], series1, Integer.toString(i));
		}
		
		barChart.setDataSet(dataSet);
		barChart.generateBarChart();
		barChart.setColor(0, t1);
		barChart.setColor(1,t2);
		img = barChart.getBarChartImage();
		drawDiagram(img, cRect, 0,72);
		
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
		
		for(int i= 0; i < lifeExpectancy; i+=inc) {
			dataSet.addValue(this.getInsurance(), series2, Integer.toString(i));
			dataSet.addValue(oTable[i][3] * ( 1 - this.getEstateTaxPercent()), series1, Integer.toString(i));
		}
		
		barChart.setDataSet(dataSet);

		barChart.generateBarChart();
		barChart.setColor(0, t1);
		barChart.setColor(1,t2);
		img = barChart.getBarChartImage();
		drawDiagram(img, cRect, 0,72);

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
		
		for(int i= 0; i < lifeExpectancy; i+=inc) {
			dataSet.addValue(wTable[i][5], series2, Integer.toString(i));
			//dataSet.addValue(0, series1, Integer.toString(i));
		}
		barChart.setDataSet(dataSet);
		barChart.generateBarChart();
		barChart.setColor(0, Color.blue);
		//barChart.setColor(1,Color.RED);
		img = barChart.getBarChartImage();
		drawDiagram(img, cRect, 0,72);
		
	}
	
	
	public void page4s()
	{
		drawBorder(pageIcon, "CRUT");
		drawHeader(userInfo.getClientHeading(), "Charitable Remainder Trust");

		// Draw first chart
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Outright Sale";
		String series2 = "CRT";
		
		for(int i = 0; i < lifeExpectancy; i+=2) {
			dataSet.addValue(wTable[i][5], series2, Integer.toString(i));
			dataSet.addValue(oTable[i][3], series1, Integer.toString(i));
		}

		Color t1 = new Color(0x08000);
		Color t2 = new Color(0x00A54E);
		LayeredBarChart lChart = new LayeredBarChart();
		lChart.setTitle("Investment Portfolio");
		lChart.setDataSet(dataSet);
		Rectangle bRect = new Rectangle(prctULeft);
		lChart.setRect(bRect);
		lChart.setDomainAxisLabel("Years");
		lChart.setLablesOff(true);
		lChart.setCategoryMargin(0f);
		lChart.setItemMargin(0f);
		lChart.setC1(t1);
		lChart.setC2(t1);
		lChart.setC3(t2);
		lChart.setC4(t2);
		lChart.generateLayeredChart();
		Image img = lChart.getBarChartImage();
		drawDiagram(img, bRect, 0,72);
		
		// Draw second Chart
		dataSet.clear();
		img = null;
		lChart = new LayeredBarChart();
		Rectangle cRect = new Rectangle(prctURight);
		lChart.setRect(cRect);
		lChart.setTitle("Net Spendable");
		lChart.setDomainAxisLabel("Years");
		lChart.setDomainAxisLabel("Years");
		lChart.setAlpha(1.0f);
		lChart.setLablesOff(true);
		lChart.setUse2D(true);
		lChart.setCategoryMargin(0f);
		
		for(int i= 0; i < lifeExpectancy; i+=2) {
			dataSet.addValue(wTable[i][4], series2, Integer.toString(i));
			dataSet.addValue(oTable[i][2], series1, Integer.toString(i));
		}
		
		lChart.setDataSet(dataSet);
		lChart.setC1(t1);
		lChart.setC2(t1);
		lChart.setC3(t2);
		lChart.setC4(t2);
		lChart.generateLayeredChart();
		img = lChart.getBarChartImage();
		drawDiagram(img, cRect, 0,72);
		
		// Draw Third Chart
		dataSet.clear();
		img = null;
		lChart = new LayeredBarChart();
		cRect = new Rectangle(prctLLeft);
		lChart.setRect(cRect);
		lChart.setTitle("Net Amount Passing to Family");
		lChart.setDomainAxisLabel("Years");
		lChart.setLablesOff(true);
		lChart.setCategoryMargin(0f);
		
		for(int i= 0; i < lifeExpectancy; i++) {
			dataSet.addValue(this.getInsurance(), series2, Integer.toString(i));
			dataSet.addValue(oTable[i][3] * ( 1 - this.getEstateTaxPercent()), series1, Integer.toString(i));
		}
		
		lChart.setDataSet(dataSet);
		lChart.setC1(t1);
		lChart.setC2(t1);
		lChart.setC3(t2);
		lChart.setC4(t2);
		lChart.generateLayeredChart();
		
		img = lChart.getBarChartImage();
		drawDiagram(img, cRect, 0,72);

		// Draw Fourth Chart
		dataSet.clear();
		BarChart barChart = null;
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
		
		for(int i= 0; i < lifeExpectancy; i++) {
			dataSet.addValue(wTable[i][5], series2, Integer.toString(i));
			//dataSet.addValue(0, series1, Integer.toString(i));
		}
		barChart.setDataSet(dataSet);
		barChart.generateBarChart();
		barChart.setColor(0, Color.blue);
		//barChart.setColor(1,Color.RED);
		img = barChart.getBarChartImage();
		drawDiagram(img, cRect, 0,72);
		
	}
	public void page5()
	{
		Date now = new Date();
		
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		
		drawBorder(pageIcon, "CRUT");
		drawHeader(userInfo.getClientHeading(), "Charitable Remainder Trust");

		PageTable table = new PageTable(writer);
		Rectangle rctTable = new Rectangle(prctFull);
		Rectangle rctTable2 = new Rectangle(prctFull);
		
		rctTable.setRight(rctTable.getLeft() + (rctTable.getWidth() * .75f));
		rctTable2.setLeft((rctTable2.getRight() - rctTable2.getWidth() * .25f) + _1_8TH);
		rctTable2.setRight(rctTable2.getRight() + (_1_4TH * 3));
		
		table.setTableFormat(rctTable, 13);
		int Red = makeColor(202,137,157);
		int Green = makeColor(137,202,157);
		int Blue = makeColor(0,0,128);
		

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

			{
				"",
				"Property Value",
				"",
				"",
				"",
				"" + number.format(this.assetValue),
				"",
				"Date Calculated",
				"",
				"",
				"",
				"",
				"" + df.format(now) ,
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
				"",
				"Liability",
				"",
				"",
				"",
				"" + number.format(this.liability),
				"",
				"IRC Sec. 7520(b) rate used",
				"",
				"",
				"",
				"",
				"" + percent.format(this.sec7520Rate),
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
				"",
				"Basis",
				"",
				"",
				"",
				"" + number.format(this.basis),
				"",
				"Charitable Deduction Factor",
				"",
				"",
				"",
				"",
				"" + this.charitableDeductionFactor,
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
				"",
				"Maginal Income Tax Rate (Fed and State)",
				"",
				"",
				"",
				"" + percent.format(this.marginalIcomeTaxRate),
				"",
				"Charitable Deduction",
				"",
				"",
				"",
				"",
				"" + number.format(this.charitableDeduction),
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
				"",
				"Future Estate Tax Rate",
				"",
				"",
				"",
				"" + percent.format(this.estateTaxPercent),
				"",
				"Adjusted Gross Income (AGI) before payout",
				"",
				"",
				"",
				"",
				"" + number.format(this.adjustedGI),
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
				"",
				"Capital Gains Rate (Fed and State)",
				"",
				"",
				"",
				"" + percent.format(this.capitalGainsPercent),
				"",
				"Deduction Limitation of AGI",
				"",
				"",
				"",
				"",
				"" + percent.format(this.deductionLimitRate),
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
				"",
				"Investment Return",
				"",
				"",
				"",
				"" + percent.format(this.preTaxROIPercent),
				"",
				"Donor" + (!userInfo.isSingle() ? "(s)" : ""),
				"",
				"",
				"",
				""  + userInfo.getClientFirstName() + (!userInfo.isSingle() ? " & " + userInfo.getSpouseFirstName() : ""),
				"",
				"[][colspan=4][][][][][][colspan=3][][][]"+ 
					(userInfo.isSingle() ? "[colspan=2,align=right][]" :"[colspan=3,align=right][][]"),
				
			},
			{
				"",
				"Spending/Payout Rate(1)",
				"",
				"",
				"",
				"" + percent.format(this.spendingRate),
				"",
				"Life Expectancy (Average per tables)(A)"+ (userInfo.isSingle() ? "" : "(B)"),
				"",
				"",
				"",
				"",
				"" + "("  + cLe  + ")"+ (userInfo.isSingle() ? "" : " (" + sLe + ")"),
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
				"",
				"Capital Gains Tax",
				"",
				"",
				"",
				"" + number.format(this.capitalGainsTax),
				"",
				"Insurance(4)",
				"",
				"",
				"",
				"",
				"" + number.format(insurance),
				"[][colspan=4][][][][][][colspan=5][][][][][]",
				
			},
			{
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
				"",
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
		float widths[] = {.9f,.30f,1,1,1,1,.5f,1,1,1,1,1,1};
		
		table.setColumnWidths(widths);
		table.buildTableEx(cells);
		
		String singleRow[][] = {
			{
				"",
				"0",
				"",
				"",
				"",
				"" + number.format(this.portfolioBalance),
				"0",
				"",
				"",
				"",
				"",
				"",
				"" + number.format(this.assetValue),
				"[][border=r][][][][border=r][border=r][][][][][][]"
			}
		};
		int alignments[] = {
				PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT};
		table.setColumnAlignments(alignments);
		//table.buildTableEx(singleRow);
		
		double oTotal = 0;
		double wTotal = 0;
		for(int i = 0; i <= lifeExpectancy; i++)
		{
		    oTotal += oTable[i][2];
		    wTotal += wTable[i][4];
            StringBuffer arg = new StringBuffer("");
            if( i == cLe)
                arg.append("(A)");
            if( i == sLe && !userInfo.isSingle())
                arg.append("(B)");
            
			String sr[] = 
			{
				arg.toString(),
				"" + i,
				"" + number.format(oTable[i][0]),
				"" + number.format(oTable[i][1]),
				"" + number.format(oTable[i][2]),
				"" + number.format(oTable[i][3]),
				"" + i,
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
		String sTotal[] = 
		{
			"",
			"",
			"",
			"",
			"" + number.format(oTotal),
			"",
			"",
			"",
			"",
			"",
			"",
			"" + number.format(wTotal),
			"",
			"[][][][][border=t][][][][][][][border=t][]"
		};

		singleRow[0] = sTotal;
		table.buildTableEx(singleRow);
		
		table.drawTable();
		
        /*
        Rectangle rctFootNote = new Rectangle(table.getResultRect());
		rctFootNote.setTop(rctFootNote.getBottom() - _1_4TH);
		rctFootNote.setBottom(0);
        rctFootNote.setRight(prctFull.getRight());
        drawLabel("* Insurance amounts/value/premium duration based upon current assumptions, major carrier, NOT GUARANTEED", rctFootNote, "GARA.TTF",new Color(0,0,0), 9, LBL_LEFT, LBL_TOP);
        */
		
				
		table = new PageTable(writer);
		table.setTableFormat(rctTable2, 5);
		table.setFontSize(6);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		
		String cells2[][] = {
			{
				"",
				"Summary of Outright Sale",
				"",
				"",
				"",
				"[][bold=1,colspan=6][][][]"
			},

			{
				"",
				"Estate Impact @ Death:",
				"",
				"",
				"" + integer.format(this.lifeExpectancy) + "th year",
				"[][bold=1,colspan=3,border=t+r+b+l,][][][bold=1,align=right,border=t+r+b+l]"
			},
			{
				"",
				"Portfolio Balance",
				"",
				"",
				"" + number.format(this.portfolioBalance),
				"[][bold=1,colspan=3,border=l+r][][][border=r,align=right]"
			},
			{
				"",
				"Estate Tax",
				"",
				"",
				"" + number.format(this.estateTaxPort),
				"[][bold=1,colspan=3,border=l+r,color=" + Red +"][][][border=r,align=right,color=" + Red +"]"
			},

			{
				"",
				"Net to Family",
				"",
				"",
				"" + number.format(portfolioBalance - estateTaxPort),
				"[][bold=1,colspan=3,border=l+r,color=" + Green +"][][][border=r,align=right,color=" + Green +"]"
			},
			{
				"",
				"Net To Charity",
				"",
				"",
				"" + number.format(this.benefitToCharity),
				"[][bold=1,colspan=3,border=l+r+b,color=" + Blue +"][][][border=r+b,align=right,color=" + Blue +"]"
			},
			{
				" ",
				"",
				"",
				"",
				"",
				"[][][][][]"
			},
			{
				"1)",
				"Net spendable is figured by taking a spendable rate (" + percent.format(this.spendingRate)+") multiplied by the period beginning balance and subtracting the income tax. The spendable rate is comparable to the CRT payout rate. The unspent growth is added to the portfolio balance.",
				"",
				"",
				"",
				"[align=right,valign=top][colspan=4][][][]"
			},
			{
				"2)",
				"Income tax is figured by taking the rate of ordinary income (" + percent.format(this.rateOfIncome) + ") multiplied by the marginal income tax rate ("+percent.format(this.marginalIcomeTaxRate)+") and adding that to the realized capital gain ("+percent.format(this.spendingRate)+" - "+percent.format(this.rateOfIncome)+" = "+percent.format(this.realizedCapitalGain)+") multiplied by the capital gains rate ("+percent.format(this.capitalGainsPercent)+"). This yields a marginal tax rate of "+percent.format(this.calculatedMITR)+".",
				"",
				"",
				"",
				"[align=right,valign=top][colspan=4][][][]"
			},

			{
				"3)",
				"Deduction is subject to 30% of AGI; 5 year carry forward.",
				"",
				"",
				"",
                "[align=right,valign=top][colspan=4][][][]"
			},

			{
				"4)",
				"Insurance amounts/value/premium duration based upon current assumptions, major carrier, NOT GUARANTEED",
				"",
				"",
				"",
                "[align=right,valign=top][colspan=4][][][]"
			},
            {
                " ",
                "",
                "",
                "",
                "",
                "[colspan=5][][][][]"
            },
            {
                " ",
                "",
                "",
                "",
                "",
                "[colspan=5][][][][]"
            },
			{
				" ",
				"",
				"",
				"",
				"",
				"[colspan=5][][][][]"
			},
			{
				" ",
				"",
				"",
				"",
				"",
				"[colspan=5][][][][]"
			},
			{
				" ",
				"",
				"",
				"",
				"",
				"[colspan=5][][][][]"
			},
			{
				" ",
				"",
				"",
				"",
				"",
				"[colspan=5][][][][]"
			},
			{
				" ",
				"",
				"",
				"",
				"",
				"[colspan=5][][][][]"
			},
			{
				"",
				"Summary of Sale with CRT",
				"",
				"",
				"",
				"[][bold=1,colspan=6][][][]"
			},

			{
				"",
				"Estate Impact @ Death:",
				"",
				"",
				"" + integer.format(this.lifeExpectancy) + "th year",
				"[][colspan=3,bold=1,border=r+t+l+b][][][bold=1,border=r+t+l+b,align=center]"
			},

			{
				"",
				"Trust Balance",
				"",
				"",
				"" + number.format(this.trustBalance),
				"[][colspan=3,bold=1,border=r+l][][][border=r+l,align=right]"
			},
			{
				"",
				"Estate Tax",
				"",
				"",
				"0",
				"[][Colspan=3,border=l+r,color="+Red+"][][][align=right,border=l+r,color="+Red+"]"
			},
			{
				"",
				"Net to Family (Ins.)(4)",
				"",
				"",
				"" + number.format(this.insurance),
				"[][Colspan=3,border=l+r,color="+Green+"][][][align=right,border=l+r,color="+Green+"]"
			},
			{
				"",
				"Net to Charity",
				"",
				"",
				"" + number.format(this.benefitToCharityCRUT),
				"[][Colspan=3,border=l+r+b,color="+Blue+"][][][align=right,border=l+r+b,color="+Blue+"]"
			},

			{
				" ",
				"",
				"",
				"",
				"",
				"[colspan=5][][][][]"
			},
			{
				"",
				"Extra Net Spendable(cumul.) w/CRT",
				"",
				"",
				"" + number.format(this.extraNetSpendable),
				"[][bold=1,colspan=3,border=r+t+l+b][][][border=r+t+l+b,align=Right]"
			},
			{
				"",
				"Premiums Gifted (4)",
				"",
				"",
				"" + number.format(this.totalPremiumsGifted),
				"[][bold=1,colspan=3,border=l+b+r][][][align=right,border=l+b+r]"
			},
			{
				" ",
				"",
				"",
				"",
				"",
				"[colspan=5][][][][]"
			},
			{
				"",
				"Taxes Saved",
				"",
				"",
				"" + integer.format(this.lifeExpectancy) + "th year",
				"[][colspan=3,bold=1,border=l+t+r+b][][][align=center,bold=1,border=l+t+r+b]"
			},
			{
				"",
				"Capital Gains Tax",
				"",
				"",
				"" + number.format(this.capitalGainsSaved),
				"[][colspan=3,border=l+r][][][align=right,border=l+r]"
			},
			
			{
				"",
				"Income Tax (3)",
				"",
				"",
				"" + number.format(this.incomeTaxSaved),
				"[][colspan=3,border=l+r][][][align=right,border=l+r]"
			},

			{
				"",
				"Estate Tax",
				"",
				"",
				"" + number.format(this.estateTaxSaved),
				"[][colspan=3,border=l+r][][][align=right,border=l+r]"
			},
			{
				"",
				"Total Taxes Saved",
				"",
				"",
				"" + number.format(this.totalTaxesSaved),
				"[][colspan=3,border=l+b+r+t][][][align=right,border=l+b+r+t]"
			},
			
		};
		float widths2[] ={.25f, 1, 1, 1, 1};
		table.setColumnWidths(widths2);
		table.buildTableEx(cells2);
		table.setCellLeading(1.25f);
		table.drawTable();
		//drawFilledRect(table.getResultRect(), new Color(255,255,128));
		
	}
	
	public JFreeChart crtBarChart(DefaultCategoryDataset dataSet) {
		JFreeChart barChart = ChartFactory.createBarChart3D("CRT Comparison", "",
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
		eRend.setCol1(Color.green);
		eRend.setCol2(Color.blue);
		plot.setRenderer(eRend);
		
		return barChart;
	}
	
	
	
	/**
	 * @param adjustedGI The adjustedGI to set.
	 */
	public void setAdjustedGI(double adjustedGI) {
		this.adjustedGI = adjustedGI;
	}
	/**
	 * @param annualIncomeYr1 The annualIncomeYr1 to set.
	 */
	public void setAnnualIncomeYr1(double annualIncomeYr1) {
		this.annualIncomeYr1 = annualIncomeYr1;
	}
	/**
	 * @param annualIncomeYr1CRUT The annualIncomeYr1CRUT to set.
	 */
	public void setAnnualIncomeYr1CRUT(double annualIncomeYr1CRUT) {
		this.annualIncomeYr1CRUT = annualIncomeYr1CRUT;
	}
	/**
	 * @param annualIncomeYrn The annualIncomeYrn to set.
	 */
	public void setAnnualIncomeYrn(double annualIncomeYrn) {
		this.annualIncomeYrn = annualIncomeYrn;
	}
	/**
	 * @param annualIncomeYrnCRUT The annualIncomeYrnCRUT to set.
	 */
	public void setAnnualIncomeYrnCRUT(double annualIncomeYrnCRUT) {
		this.annualIncomeYrnCRUT = annualIncomeYrnCRUT;
	}
	/**
	 * @param assetName The assetName to set.
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	/**
	 * @param assetValue The assetValue to set.
	 */
	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}
	/**
	 * @param basis The basis to set.
	 */
	public void setBasis(double basis) {
		this.basis = basis;
	}
	/**
	 * @param benefitToCharity The benefitToCharity to set.
	 */
	public void setBenefitToCharity(double benefitToCharity) {
		this.benefitToCharity = benefitToCharity;
	}
	/**
	 * @param benefitToCharityCRUT The benefitToCharityCRUT to set.
	 */
	public void setBenefitToCharityCRUT(double benefitToCharityCRUT) {
		this.benefitToCharityCRUT = benefitToCharityCRUT;
	}
	/**
	 * @param calculatedMITR The calculatedMITR to set.
	 */
	public void setCalculatedMITR(double calculatedMITR) {
		this.calculatedMITR = calculatedMITR;
	}
	/**
	 * @param capitalGainsPercent The capitalGainsPercent to set.
	 */
	public void setCapitalGainsPercent(double capitalGainsPercent) {
		this.capitalGainsPercent = capitalGainsPercent;
	}
	/**
	 * @param capitalGainsSaved The capitalGainsSaved to set.
	 */
	public void setCapitalGainsSaved(double capitalGainsSaved) {
		this.capitalGainsSaved = capitalGainsSaved;
	}
	/**
	 * @param incomeTaxLiability The incomeTaxLiability to set.
	 */
	public void setCapitalGainsTax(double capitalGainsTax) {
		this.capitalGainsTax = capitalGainsTax;
	}
	/**
	 * @param capitalGainsTaxCRUT The capitalGainsTaxCRUT to set.
	 */
	public void setCapitalGainsTaxCRUT(double capitalGainsTaxCRUT) {
		this.capitalGainsTaxCRUT = capitalGainsTaxCRUT;
	}
	/**
	 * @param charitableDeduction The charitableDeduction to set.
	 */
	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}
	/**
	 * @param charitableDeductionFactor The charitableDeductionFactor to set.
	 */
	public void setCharitableDeductionFactor(double charitableDeductionFactor) {
		this.charitableDeductionFactor = charitableDeductionFactor;
	}
	/**
     * @param le The cLe to set.
     */
    public void setCLe(int le) {
        cLe = le;
    }
	/**
	 * @param cumulativeNetIncome The cumulativeNetIncome to set.
	 */
	public void setCumulativeNetIncome(double cumulativeNetIncome) {
		this.cumulativeNetIncome = cumulativeNetIncome;
	}
	/**
	 * @param cumulativeNetIncomeCRUT The cumulativeNetIncomeCRUT to set.
	 */
	public void setCumulativeNetIncomeCRUT(double cumulativeNetIncomeCRUT) {
		this.cumulativeNetIncomeCRUT = cumulativeNetIncomeCRUT;
	}
	/**
	 * @param deductionLimitRate The deductionLimitRate to set.
	 */
	public void setDeductionLimitRate(double deductionLimitRate) {
		this.deductionLimitRate = deductionLimitRate;
	}
	/**
	 * @param difference The difference to set.
	 */
	public void setDifference(double difference) {
		this.difference = difference;
	}
	/**
	 * @param estateTax The estateTax to set.
	 */
	public void setEstateTax(double estateTax) {
		this.estateTax = estateTax;
	}
	/**
	 * @param estateTaxPercent The estateTaxPercent to set.
	 */
	public void setEstateTaxPercent(double estateTaxPercent) {
		this.estateTaxPercent = estateTaxPercent;
	}
	/**
	 * @param estateTaxPort The estateTaxPort to set.
	 */
	public void setEstateTaxPort(double estateTaxPort) {
		this.estateTaxPort = estateTaxPort;
	}
	/**
	 * @param estateTaxSaved The estateTaxSaved to set.
	 */
	public void setEstateTaxSaved(double estateTaxSaved) {
		this.estateTaxSaved = estateTaxSaved;
	}
	/**
	 * @param extraNetSpendable The extraNetSpendable to set.
	 */
	public void setExtraNetSpendable(double extraNetSpendable) {
		this.extraNetSpendable = extraNetSpendable;
	}
	/**
	 * @param incomeLossPerYear The incomeLossPerYear to set.
	 */
	public void setIncomeLossPerYear(double incomeLossPerYear) {
		this.incomeLossPerYear = incomeLossPerYear;
	}
	/**
	 * @param incomeLossProjected The incomeLossProjected to set.
	 */
	public void setIncomeLossProjected(double incomeLossProjected) {
		this.incomeLossProjected = incomeLossProjected;
	}
	/**
	 * @param incomeTaxSaved The incomeTaxSaved to set.
	 */
	public void setIncomeTaxSaved(double incomeTaxSaved) {
		this.incomeTaxSaved = incomeTaxSaved;
	}
	/**
	 * @param insurance The insurance to set.
	 */
	public void setInsurance(double insurance) {
		this.insurance = insurance;
	}
	/**
	 * @param insurancePremium The insurancePremium to set.
	 */
	public void setInsurancePremium(double insurancePremium) {
		this.insurancePremium = insurancePremium;
	}
	/**
	 * @param liability The liability to set.
	 */
	public void setLiability(double liability) {
		this.liability = liability;
	}
	/**
	 * @param lifeExpectancy The lifeExpectancy to set.
	 */
	public void setLifeExpectancy(int lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}
	/**
	 * @param lifeIncomePlusTaxSavings The lifeIncomePlusTaxSavings to set.
	 */
	public void setLifeIncomePlusTaxSavings(double lifeIncomePlusTaxSavings) {
		this.lifeIncomePlusTaxSavings = lifeIncomePlusTaxSavings;
	}
	/**
	 * @param lifeIncomePlusTaxSavingsCRUT The lifeIncomePlusTaxSavingsCRUT to set.
	 */
	public void setLifeIncomePlusTaxSavingsCRUT(
			double lifeIncomePlusTaxSavingsCRUT) {
		this.lifeIncomePlusTaxSavingsCRUT = lifeIncomePlusTaxSavingsCRUT;
	}
	/**
	 * @param marginalIcomeTaxRate The marginalIcomeTaxRate to set.
	 */
	public void setMarginalIcomeTaxRate(double marginalIcomeTaxRate) {
		this.marginalIcomeTaxRate = marginalIcomeTaxRate;
	}
	/**
	 * @param netProceeds The netProceeds to set.
	 */
	public void setNetProceeds(double netProceeds) {
		this.netProceeds = netProceeds;
	}
	/**
	 * @param netToFamily The netToFamily to set.
	 */
	public void setNetToFamily(double netToFamily) {
		this.netToFamily = netToFamily;
	}
	/**
	 * @param netToFamilyAfterTax The netToFamilyAfterTax to set.
	 */
	public void setNetToFamilyAfterTax(double netToFamilyAfterTax) {
		this.netToFamilyAfterTax = netToFamilyAfterTax;
	}
	/**
	 * @param netToFamilyAfterTaxCRUT The netToFamilyAfterTaxCRUT to set.
	 */
	public void setNetToFamilyAfterTaxCRUT(double netToFamilyAfterTaxCRUT) {
		this.netToFamilyAfterTaxCRUT = netToFamilyAfterTaxCRUT;
	}
	/**
	 * @param table The oTable to set.
	 */
	public void setOTable(double[][] table) {
		oTable = table;
	}
	/**
	 * @param percentageLost The percentageLost to set.
	 */
	public void setPercentageLost(double percentageLost) {
		this.percentageLost = percentageLost;
	}
	/**
	 * @param portfolioBalance The portfolioBalance to set.
	 */
	public void setPortfolioBalance(double portfolioBalance) {
		this.portfolioBalance = portfolioBalance;
	}
	/**
	 * @param preTaxROIPercent The preTaxROIPercent to set.
	 */
	public void setPreTaxROIPercent(double preTaxROIPercent) {
		this.preTaxROIPercent = preTaxROIPercent;
	}
	/**
	 * @param rateOfIncome The rateOfIncome to set.
	 */
	public void setRateOfIncome(double rateOfIncome) {
		this.rateOfIncome = rateOfIncome;
	}
	/**
	 * @param realizedCapitalGain The realizedCapitalGain to set.
	 */
	public void setRealizedCapitalGain(double realizedCapitalGain) {
		this.realizedCapitalGain = realizedCapitalGain;
	}
	/**
	 * @param sec7520Rate The sec7520Rate to set.
	 */
	public void setSec7520Rate(double sec7520Rate) {
		this.sec7520Rate = sec7520Rate;
	}
	/**
     * @param le The sLe to set.
     */
    public void setSLe(int le) {
        sLe = le;
    }
	/**
	 * @param spendingRate The spendingRate to set.
	 */
	public void setSpendingRate(double spendingRate) {
		this.spendingRate = spendingRate;
	}
	/**
	 * @param taxableGain The taxableGain to set.
	 */
	public void setTaxableGain(double taxableGain) {
		this.taxableGain = taxableGain;
	}
	/**
	 * @param taxDeduction The taxDeduction to set.
	 */
	public void setTaxDeduction(double taxDeduction) {
		this.taxDeduction = taxDeduction;
	}
	/**
	 * @param taxDeductionCRUT The taxDeductionCRUT to set.
	 */
	public void setTaxDeductionCRUT(double taxDeductionCRUT) {
		this.taxDeductionCRUT = taxDeductionCRUT;
	}
	/**
	 * @param taxSavedDeduction The taxSavedDeduction to set.
	 */
	public void setTaxSavedDeduction(double taxSavedDeduction) {
		this.taxSavedDeduction = taxSavedDeduction;
	}
	/**
	 * @param taxSavedDeductionCRUT The taxSavedDeductionCRUT to set.
	 */
	public void setTaxSavedDeductionCRUT(double taxSavedDeductionCRUT) {
		this.taxSavedDeductionCRUT = taxSavedDeductionCRUT;
	}
	/**
	 * @param total The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	/**
	 * @param totalCRUT The totalCRUT to set.
	 */
	public void setTotalCRUT(double totalCRUT) {
		this.totalCRUT = totalCRUT;
	}
	/**
	 * @param totalPremiumsGifted The totalPremiumsGifted to set.
	 */
	public void setTotalPremiumsGifted(double totalPremiumsGifted) {
		this.totalPremiumsGifted = totalPremiumsGifted;
	}
	/**
	 * @param totalTaxes The totalTaxes to set.
	 */
	public void setTotalTaxes(double totalTaxes) {
		this.totalTaxes = totalTaxes;
	}
	/**
	 * @param totalTaxesSaved The totalTaxesSaved to set.
	 */
	public void setTotalTaxesSaved(double totalTaxesSaved) {
		this.totalTaxesSaved = totalTaxesSaved;
	}
    /**
	 * @param trustBalance The trustBalance to set.
	 */
	public void setTrustBalance(double trustBalance) {
		this.trustBalance = trustBalance;
	}
    /**
	 * @param table The wTable to set.
	 */
	public void setWTable(double[][] table) {
		wTable = table;
	}
}
