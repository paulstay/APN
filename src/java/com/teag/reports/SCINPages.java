package com.teag.reports;

import java.awt.Color;
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
import com.teag.estate.SCINTool;

public class SCINPages extends Page {

	SCINTool scin;
	
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
	
	public SCINPages(Document document, PdfWriter writer) {
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
	
	public SCINTool getScin() {
		return scin;
	}

	private void page1(){
		
		Rectangle rct;
        
		drawBorder(pageIcon,"SCIN");
        
		float ptSize = 12f;
		drawHeader(userInfo.getClientHeading(), "Self Canceling Installment Note");

		amount = scin.getFmv();
		basis = scin.getBasis();
		le = scin.getCLifeExpectancy();

		String theProblem[] = new String[] {
			"You are age " + Integer.toString(clientAge) + 
			".  One of your assets is a parcel of real property worth " 
			+ dollar.format(amount)+ ".  It has a "+ dollar.format(basis) + 
			" basis but produces no significant income.  You would like to keep this asset in "+
			"the family but are looking for some increased cash flow.  You are also interested "+
			"in avoiding federal estate tax on this asset when you die. ",
			"Your life expectancy is " + number.format(le) +" years but you have reason to "+
			"believe you will live for at least another year but will die well short of "+ 
			number.format(le)+" years.  ",
			"Because of your health and life expectancy, you have decided to transfer this "+
			"asset to a member of the family who has sufficient excess cash flow to assist with "+
			"your needs.  In exchange for your transfer of the real property, your family member "+
			"has agreed to pay an annuity to you for life.",
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
		double capGainsTax = taxableGain * .2;
		double netProceeds = fmv - capGainsTax;
		double eTax = netProceeds * .55;
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
	public void page2() {
		Rectangle rct = new Rectangle(prctTop);
        drawBorder(pageIcon,"PAT");
        
		drawHeader(userInfo.getClientHeading(), "Self Canceling Installment Note");
		
		String theProcess[] = new String[]  {
				"The self-cancelling installment note involves a sale of asset(s) to a family member in "+
				"exchange for a note which provides for extinguishment or cancellation if the seller dies "+
				"before the note is paid in full.  See Esate of Moss v. Commissioner, 74 T.C. 1239 (1980).  "+
				"Acq. In result in part, 1981. 1.C.B.  In order to prevent a gift on the sale, however, the "+
				"principal amount of the note or the interest rate must be increased to compensate for the self-canceling feature."  
			};

		String keyFeatures[] = new String[]  {
				"The SCIN works especially well in cases where the seller is not expected to survive the term of the note, but is not so afflicted that the mortality tables cannot be used to set the premium.",
				"The selection of an interest or principal premium will depend on the income tax considerations of the buyer and seller.",
				"It is important that the seller not retain strings which could put the property back into the estate, and that the term of the note be less than the seller's life expectancy to avoid Section 2036 issues and treatment.",
				"The self-canceling installment note can be secured and interest deductible.",
				"Under Estate of Frane v. Commissioner, 98 T.C. 341 (1992), affirmed in part and reversed in part, 998 F.2nd 567 (8th Cir., 1993) unrecognized gain at time of death will be taxable to the estate as income in respect of a decedent."
			};

		rct = new Rectangle(prctLeft);
		rct.setTop(rct.getTop() - Page._1_4TH);
		Rectangle r = drawSection(rct, "The Process", theProcess, 2);

		rct = new Rectangle(prctLeft);
		rct.setTop(r.getBottom() - Page._1_4TH);
		drawSection(rct, "Key Features", keyFeatures, 1);

		
		String benefits[];
		
		benefits =  new String[]{
				"Removes the future appreciation in the assets sold from the estate, for estate tax purposes.",
				"Removes any unpaid principal balance on the note from the estate, for tax purposes, if seller dies before the note has been repaid.",
				"May improve the liquidity in seller's estate and produce a steady income.",
				"The income tax on seller's gain on the sale is spread over several years.",
				"The assets are kept within the family."
		};
		
		rct = new Rectangle(prctRight);
		rct.setTop(rct.getTop() - Page._1_4TH);
		r = drawSection(rct, "Benefits of using this technique", benefits, 2);
		String effects[];

		effects = new String[] {
				"Seller must recognize a taxable gain on the sale even though it may be spread over several years.",
				"The purchase price may be increased above the fair market value of the asset or the stated interest rate may be higher because of the 'SCIN premium' which is required in order to obtain the desired tax treatment.",
				"Seller loses personal control of the assets sold.",
				"Seller is subject to an acceleration of the deferred taxable gain, if buyer resells the assets within two years after the initial sale, or seller gives away the promissory note (even if to a charity)." 
		};
		
		rct = new Rectangle(prctRight);
		rct.setTop(rct.getTop() - (Page._1_4TH + r.getHeight())  );
		r = drawSection(rct, "Side effects/Drawbacks of using this technique", effects, 1);
		
		String[] design = new String[] {
				"Buyer, on the terms and conditions of the sale between seller and buyer executes a \"Self-Canceling Installment Note\".  In this connection a sample note is attached.  Please note the features of the note including 1.3 Cancellation at Lender's Death.  The title of lender can be changed to seller.  This sample note shows security for the note in the form of a deed of trust.",
				"A premium for the self-canceling feature is determined and computed. ",
				"Because the life expectancy of the seller should be at least one year it is recommended that a Medical Certification (see sample attached) be executed by seller's physician after an examination."
		};

		rct = new Rectangle(prctRight);
		rct.setTop(r.getBottom());
		r = drawSection(rct, "Design Concepts", design, 1);
	}
	
	public void page3() {
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		String nType =  "Interest Only";
		if(scin.getNoteType()==0)
			nType = "Level Principal plus Interest";
		if(scin.getNoteType()==1)
			nType = "Amoritizing";

		drawBorder(pageIcon, "SCIN");
		drawHeader(userInfo.getClientHeading(), "Self-Canceling Installment Note (SCIN)");
		PageTable table = new PageTable(writer);
		Rectangle rct = new Rectangle(prctTop);
		rct.setTop(rct.getTop());
		table.setTableFormat(rct, 6);
		
		String cells[][] = {
				{
					"When comparing the results of leaving the asset in the Estate or selling the asset to a family member, the difference is truly astounding as the annuitant receives an annuity payment, and the asset is outside of the estate with its full value passing to a family member without estate taxes.",
					"",
					"",
					"",
					"",
					"",
					"[Colspan=6,ptSize=11][][][][][]"
				},{
					" ","","","","","","[][][][][][]"
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
					" " + dollar.format(scin.getFmv()),
					" ",
					"7520 Rate",
					"" + percent.format(scin.getIrsRate()),
					"[][][][][align=left][]"
				},		{
					"Asset Basis",
					"",
					"" + dollar.format(basis),
					"",
					"7520 Date",
					"" + df.format(scin.getIrsDate()),
					"[][][][][align=left][]"
				},				{
					"Note Type",
					"",
					nType,
					"",
					"Note Term",
					"" + number.format(scin.getTerm()),
					"[][][][][align=left][]"
				},	{
					" ","","","","","","[][][][][][]"
				},	{
					" ","","","","","","[][][][][][]"
				},	{
					" ","","","","","","[][][][][][]"
				},	{
					"    Principal Rish Premium",
					"",
					"",
					"",
					"" + dollar.format(scin.getPrincipalRiskPremium()),
					"",
					"[colspan=4,align=left][][][][align=right][]"
				}, {
					"    Adjusted Principal with Rish Premium",
					"",
					"",
					"",
					"" + dollar.format(scin.getAdjustedPremium()),
					"",
					"[colspan=4,align=left][][][][align=right][]"
				},   {
					"    Adjusted Interest Rate",
					"",
					"",
					"",
					"" + percent.format(scin.getAdjustedInterest()),
					"",
					"[colspan=4,align=left][][][][align=right][]"
				}, {
					"    Note Payment " + (scin.getPaymentType()==0 ? "(Principal Risk Premium)" : "(Interest Risk Premium)"), 
					"",
					"",
					"",
					"" + dollar.format(scin.getPayment()[0]),
					"",
					"[colspan=4,align=left][][][][align=right][]"
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
		
		PageTable leftTable = new PageTable(writer);
		rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() + _1_2TH);
		leftTable.setTableFormat(rct, 5);
		
		String ltHeader[] = {
				"Annual Cash Flow " + (scin.getPaymentType()==1 ? "(Principal Risk Premium)" : "(Interest Risk Premium)"),
				"",
				"",
				"",
				"",
				"[colspan=5,align=center,bold=1,border=b][][][][]"
		};
		
		String ltHeader2[] = {
				"Year",
				"Beginning Principal",
				"Growth",
				"Annual Payment",
				"Ending Principal",
				"[align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b]"
		};

		String row[][] = new String[1][];
		
		row[0] = ltHeader;
		leftTable.buildTableEx(row);
		row[0] = ltHeader2;
		leftTable.buildTableEx(row);
		
		String data[] = new String[6];

		double principal = scin.getFmv();
		double growth = scin.getGrowth();
		double payment[] = scin.getPayment();
		
		for(int i=0; i < scin.getTerm(); i++) {
			data[0] = Integer.toString(i+1); // year
			data[1] = dollar.format(principal);
			data[2] = dollar.format(principal * growth);
			data[3] = dollar.format(payment[i]);
			
			principal = (principal + (principal* growth) - payment[i]);
			
			data[4] = dollar.format(principal);
			data[5] = "[align=center][align=right][align=right][align=right][align=right]";
			row[0] = data;
			leftTable.buildTableEx(row);
		}
		float lwidths[] = {.2f, .4f, .4f, .4f, .4f};
		int lalignments[] = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT};

		leftTable.setColumnWidths(lwidths);
		leftTable.setColumnAlignments(lalignments);

		leftTable.setFontSize(10);
		leftTable.setTableFont("arial.TTF");
		leftTable.setTableFontBold("arialbd.TTF");
		
		leftTable.drawTable();
		rTable();
	}

	public void rTable() {
		PageTable leftTable = new PageTable(writer);
		Rectangle rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() + _1_2TH);
		rct.setRight(rct.getRight() + _1_2TH);
		leftTable.setTableFormat(rct, 6);
		
		String ltHeader[] = {
				"Payment Breakdown " + (scin.getPaymentType()==1 ? "(Principal Risk Premium)" : "(Interest Risk Premium)"),
				"",
				"",
				"",
				"",
				"",
				"[colspan=6,align=center,bold=1,border=b][][][][][]"
		};
		
		String ltHeader2[] = {
				"Year",
				"Payment",
				"Interest Portion",
				"Cap Gains Portion",
				"Tax Free Portion",
				"Note Balance",
				"[align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b]"
		};

		String row[][] = new String[1][];
		
		row[0] = ltHeader;
		leftTable.buildTableEx(row);
		row[0] = ltHeader2;
		leftTable.buildTableEx(row);
		
		String data[] = new String[7];

		double payment[] = scin.getPayment();
		double interest[] = scin.getInterestPayment();
		double cap[] = scin.getCapGains();
		double taxFree[] = scin.getTaxFree();
		double nBalance[] = scin.getNote();
		
		for(int i=0; i < scin.getTerm(); i++) {
			data[0] = Integer.toString(i+1); // year
			data[1] = dollar.format(payment[i]);
			data[2] = dollar.format(interest[i]);
			data[3] = dollar.format(cap[i]);
			data[4] = dollar.format(taxFree[i]);
			data[5] = dollar.format(nBalance[i]);
			data[6] = "[align=center][align=right][align=right][align=right][align=right][align=right]";
			row[0] = data;
			leftTable.buildTableEx(row);
		}
		float lwidths[] = {.3f, .6f, .5f, .6f, .5f, .6f};
		int lalignments[] = {Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, Element.ALIGN_RIGHT, Element.ALIGN_RIGHT};

		leftTable.setColumnWidths(lwidths);
		leftTable.setColumnAlignments(lalignments);

		leftTable.setFontSize(7);
		leftTable.setTableFont("arial.TTF");
		leftTable.setTableFontBold("arialbd.TTF");
		
		leftTable.drawTable();
	}

	public void setScin(SCINTool scin) {
		this.scin = scin;
	}
	
	
}
