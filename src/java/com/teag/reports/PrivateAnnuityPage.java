package com.teag.reports;
/**
 * Author Paul Stay
 * Date sometime in 2007
 */		

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.estate.pdf.CellInfo;
import com.estate.pdf.CellInfoFactory;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PrivateAnnuityPage extends Page {

	int clientAge;
	double amount;
	double basis;
	double le;
	double estateTaxRate;
	double afrRate;
	Date afrDate;
	double annuityFactor;
	double incomeTaxRate;
	double capitalGainsRate;
	double annuityFreq;
	double annuityType;
	double annuityIncrease;
	double annuity;
	double ordinaryIncome;
	double capGains;
	double taxFreeAmount;
	int cAge;
	int sAge;
	
	SimpleDateFormat df = new SimpleDateFormat("M/d/y");
	DecimalFormat dec = new DecimalFormat("###.#####");
	
	
	public PrivateAnnuityPage(Document document, PdfWriter writer) {
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
	
	public Date getAfrDate() {
		return afrDate;
	}
	
	public double getAfrRate() {
		return afrRate;
	}

	public double getAmount() {
		return amount;
	}

	public double getAnnuity() {
		return annuity;
	}

	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public double getAnnuityFreq() {
		return annuityFreq;
	}

	public double getAnnuityIncrease() {
		return annuityIncrease;
	}

	public double getAnnuityType() {
		return annuityType;
	}

	public double getBasis() {
		return basis;
	}

	public int getCAge() {
		return cAge;
	}

	public double getCapGains() {
		return capGains;
	}

	public double getCapitalGainsRate() {
		return capitalGainsRate;
	}

	public int getClientAge() {
		return clientAge;
	}

	public SimpleDateFormat getDf() {
		return df;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}

	public double getLe() {
		return le;
	}

	public double getOrdinaryIncome() {
		return ordinaryIncome;
	}

	public int getSAge() {
		return sAge;
	}

	public double getTaxFreeAmount() {
		return taxFreeAmount;
	}

	private void page1(){
		Rectangle rct;
        
		drawBorder(pageIcon,"PAT");
        
		float ptSize = 12f;
		drawHeader(userInfo.getClientHeading(), "Private Annuity");
		
		String theProblem[] = new String[] {
			"You are age " + Integer.toString(clientAge) + ".  One of your assets is a parcel of real property worth " + dollar.format(amount)+ ".  It has a "+ dollar.format(basis) + " basis but produces no significant income.  You would like to keep this asset in the family but are looking for some increased cash flow.  You are also interested in avoiding federal estate tax on this asset when you die. ",
			"Your life expectancy is " + number.format(le) +" years but you have reason to believe you will live for at least another year but will die well short of "+ number.format(le)+" years.  ",
			"Because of your health and life expectancy, you have decided to transfer this asset to a member of the family who has sufficient excess cash flow to assist with your needs.  In exchange for your transfer of the real property, your family member has agreed to pay an annuity to you for life.",
		};

		rct = new Rectangle(prctTop);
		rct.setTop(rct.getTop() - Page._1_4TH);
		rct.setLeft(rct.getLeft()+ Page._1_2TH);
		rct.setRight(rct.getRight()-(2 *Page._1_2TH));
		Rectangle r = drawSection(rct, "The Problem", theProblem, 2);

		//		 Now we need to display the table
		float left = rct.getLeft() + (rct.getWidth() * .125f);
		float right = rct.getRight() - (rct.getWidth() * .125f);
		
		rct.setLeft(left);
		rct.setRight(right);
		rct.setTop( r.getBottom());
		rct.setBottom(prctTop.getBottom());
		
		// need to insert diagram here  or Table
		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 2);
		float widths[] = { .75f, .25f };
		CellInfoFactory cif = new CellInfoFactory();
		BaseFont baseFont = PageUtils.LoadFont("GARA.TTF");
		BaseFont baseFontBold = PageUtils.LoadFont("GARABD.TTF");
		
		double fmv = amount;
		double taxableGain = amount - basis;
		double capGainsTax = taxableGain * capitalGainsRate;
		double netProceeds = fmv - capGainsTax;
		double eTax = netProceeds * estateTaxRate;
		double netToFamily = netProceeds - eTax;
		
		cif.setDefaultFont(new Font(baseFont, ptSize));
		Color green = new Color(0,176,0);
		Color red = new Color(192,0,0);
		CellInfo cellData[][] = {

				{cif.getCellInfo("Total Value (projected) in projected Estate", new Font(baseFontBold, ptSize, Font.NORMAL)),
					cif.getCellInfo(dollar.format(amount), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL))},
	
				{cif.getCellInfo("Less Cost(Basis)", new Font(baseFontBold, ptSize, Font.NORMAL)),
					cif.getCellInfo(dollar.format(basis), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL))},
		
				{cif.getCellInfo("Taxable Gain", new Font(baseFontBold, ptSize, Font.NORMAL)),
					cif.getCellInfo(dollar.format(taxableGain), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL))},
			
				{cif.getCellInfo("Income Tax Liability", new Font(baseFontBold, ptSize, Font.NORMAL,red)),
					cif.getCellInfo(dollar.format(capGainsTax), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL))},
					
				{cif.getCellInfo("Net Proceeds", new Font(baseFontBold, ptSize, Font.NORMAL)),
					cif.getCellInfo(dollar.format(netProceeds), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL))},
						
				{cif.getCellInfo("Estate Tax (" + percent.format(estateTaxRate) + ")", new Font(baseFontBold, ptSize, Font.NORMAL, red)), 
					cif.getCellInfo(dollar.format(eTax), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL, red))},
				
				{cif.getCellInfo("Net Value Passing to Family", new Font(baseFontBold, ptSize, Font.NORMAL, green)),
					cif.getCellInfo(dollar.format(netToFamily), Element.ALIGN_RIGHT, new Font(baseFontBold, ptSize, Font.NORMAL, green))}
		};
		
		table.setColumnWidths(widths);
		table.setTableData(cellData);
		table.drawTable();
	}

	private void page2() {
		Rectangle rct = new Rectangle(prctTop);
        drawBorder(pageIcon,"PAT");
        
		drawHeader(userInfo.getClientHeading(), "Private Annuity");
		
		String theProcess[] = new String[]  {
				"With a private annuity, transferor sells property to a family member in exchange for an unsecured promise of lifetime payments.  Section 7520 rates and mortality tables are used to make the payments equivalent to the value of the property being sold.  The annuity must be unsecured.",
			};

		String keyFeatures[] = new String[]  {
				"Private Annuities are ideal for persons with less than average life expectancy, but whose health is not so poor that they cannot be expected to survive a year.",
				"If the donor (annuitant) lives longer than actuarial life expectancy, unless the asset appreciates substantially the result may be worse than if there were no private annuity.",
				"The asset must appreciate faster than the Section 7520 rate in effect when the annuity is established, or the annuitant must die prematurely for this technique to work.",
				"The Private Annuity cannot be secured.",
				"Any gain on the transfer (considered a sale in exchange for a lifetime annuity) will be recognized.  "+
				"In the past highly appreciated assets could be transferred in exchange for the annuity, tax-free, with "+
				"the capital grain spread over the annuitant's life expectancy.  However, on October 19, 2006 the IRS issued "+
				"proposed regulations requiring the gain to be recognized on transfer.  These proposed regulation have not as yet become "+
				"implemented.  But, even with the proposed regulations and future increase in the property's fair market value is still "+
				"effectively transferred to the transferee.",
				"See Rev. Rul. 69-74 for a general outline of the income tax rules for private annuities.",
			};

		rct = new Rectangle(prctLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		Rectangle r = drawSection(rct, "The Process", theProcess, 2);

		rct = new Rectangle(prctLeft);
		rct.setTop(r.getBottom() - Page._1_4TH);
		drawSection(rct, "Key Features", keyFeatures, 1);

		
		String benefits[];
		
		benefits =  new String[]{
				"Removes the future appreciation of the asset sold from the estate of the Annuitant for estate tax purposes.",
				"The obligation for the payment of the annuity terminates at the death of the Annuitant.",
				"May improve the liquidity in the Annuitant?s estate and produce a steady income.",
				"The asset is kept in the family.",
		};
		
		rct = new Rectangle(prctRight);
		rct.setTop(rct.getTop() - Page._1_4TH);
		r = drawSection(rct, "Benefits of using this technique", benefits, 2);
		String effects[];

		effects = new String[] {
			"Annuitant must recognize a taxable gain on the sale.",
			"Taxation of each annuity payment is covered by IRC Section 72.  A portion of each payment is not taxable as return of basis, a portion is considered as capital gains and a portion is considered as interest.",
			"Annuitant loses personal control of the assets sold.",
			"The annuity payor receives a  new basis once the private annuity is established.  ",
		};
		
		rct = new Rectangle(prctRight);
		rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight())  );
		r = drawSection(rct, "Side effects/Drawbacks of using this technique", effects, 1);
		
		String[] design = new String[] {
			"Annuitant and annuity payor execute a private annuity agreement.  In this connection a sample agreement is attached.  Please note the features of the note including Section 5 which states that the annuity agreement is unsecured.  ",
			"Because the life expectancy of the annuitant should be at least one year it is recommended that a Medical Certification (see sample attached) be executed by annuitant's physician after an examination."
		};

		rct = new Rectangle(prctRight);
		rct.setTop(r.getBottom());
		r = drawSection(rct, "Design Concepts", design, 1);
		
		
	}

	private void page3() {
		drawBorder(pageIcon, "PAT");
		drawHeader(userInfo.getClientHeading(), "Private Annuity");
		PageTable table = new PageTable(writer);
		table.setTableFormat(prctFull, 6);

		String cells[][] = {
			{
				"When comparing the results of leaving the asset in the Estate or selling the asset to a family member, the difference is truly astounding as the annuitant receives an annuity payment, and the asset is outside of the estate with its full value passing to a family member without estate taxes.",
				"",
				"",
				"",
				"",
				"",
				"[Colspan=6,ptSize=11][][][][][]"
			},
			{
				"Assumptions",
				"",
				"",
				"",
				"",
				"",
				"[align=center,bold=1,colspan=6][][][][][]"
			},		{
				"Asset Value:",
				" ",
				" " + dollar.format(amount),
				" ",
				"7520 Rate",
				"" + percent.format(afrRate),
				"[][][][][align=left][]"
			},		{
				"Asset Basis",
				"",
				"" + dollar.format(basis),
				"",
				"7520 Date",
				"" + df.format(afrDate),
				"[][][][][align=left][]"
			},		{
				"Income Tax Rate",
				"",
				"" + percent.format(incomeTaxRate),
				"",
				"Annuity Payments Per Year",
				"" + number.format(annuityFreq),
				"[][][][][align=left][]"
			},		{
				"Estate Tax Rate",
				"",
				"" + percent.format(estateTaxRate),
				"",
				"Annuity Increase (yearly)", 
				"" + percent.format(annuityIncrease),
				"[][][][][align=left][]"
			},		{
				"Capital Gains Rate",
				"",
				"" + percent.format(capitalGainsRate),
				"",
				"Annuity Payment Begin/End",
				"" + (annuityType == 0 ? "END" : "BEGIN"),
				"[][][][][align=left][]"
			},		{
				"Client Age",
				"",
				"" + number.format(this.clientAge),
				"",
				"Number of Annuitants",
				"1",
				"[][][][][align=left][]"
			}, 	{
				" ","","","","","","[][][][][][]"
			},		{
				"",
				"Up front Capital Gains Tax",
				"",
				"",
				"" + dollar.format(capGains),
				"",
				"[][colspan=3,align=left][][][align=left][]"
			},	{
				" ","","","","","","[][][][][][]"
			},	{
				"Calculations","","","","","","[colspan=6,align=center,bold=1,ptSize=14][][][][][]"
			},		{
				"", 
				"Initial Annuity Payout",
				"",
				"",
				"" + dollar.format(annuity),
				"",
				"[][colspan=2,align=left][][][align=left][]"
			},		{
				"",
				"Annuity Factor",
				"",
				"",
				"" + dec.format(annuityFactor),
				"",
				"[][colspan=2,align=left][][][align=left][]"
			},		{
				"",
				"Ordinary Income",
				"",
				"",
				""+ dollar.format(ordinaryIncome),
				"",
				"[][colspan=2,align=left][][][align=left][]"
			},		{
				"",
				"Tax Free Amount",
				"",
				"",
				"" + dollar.format(taxFreeAmount),
				"",
				"[][colspan=2,align=left][][][align=left][]"
			}		
		};
		float widths[] = {.4f, .05f, .225f, .05f, .4f, .225f};
		int alignments[] = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_LEFT};
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);
		table.setFontSize(10);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		
		table.buildTableEx(cells);
		table.drawTable();

	}

	public void setAfrDate(Date afrDate) {
		this.afrDate = afrDate;
	}

	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityFreq(double annuityFreq) {
		this.annuityFreq = annuityFreq;
	}

	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}

	public void setAnnuityType(double annuityType) {
		this.annuityType = annuityType;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setCAge(int age) {
		cAge = age;
	}

	public void setCapGains(double capGains) {
		this.capGains = capGains;
	}

	public void setCapitalGainsRate(double capitalGainsRate) {
		this.capitalGainsRate = capitalGainsRate;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public void setDf(SimpleDateFormat df) {
		this.df = df;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}

	public void setLe(double le) {
		this.le = le;
	}

	public void setOrdinaryIncome(double ordinaryIncome) {
		this.ordinaryIncome = ordinaryIncome;
	}

	public void setSAge(int age) {
		sAge = age;
	}

	public void setTaxFreeAmount(double taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
	}
	
}
