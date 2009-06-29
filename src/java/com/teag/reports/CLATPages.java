/*
 * Created on Apr 27, 2005
 *
 */
package com.teag.reports;

import java.awt.Color;
import java.awt.Insets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.ClatBarRenderer;
import com.teag.reports.GRATPages.CustomLabelGenerator;
import com.teag.reports.GRATPages.CustomToolTipGenerator;
import com.teag.reports.messages.Messages;

/**
 * @author Paul Stay
 *
 */
public class CLATPages extends Page{
	
	private double marketValue;
	private double yrsGrowth;
	private double growthRate;
	private double incomeRate;
	
	private double estateTaxPercent;
	
	private double estateTax;
	private double estateTaxCLAT;
	
	
	private double netToFamily;
	private double netToFamilyCLAT;
	
	private double netToCharity;
	private double netToCharityCLAT;

	private double totalValue;
	private double totalCLATValue;
	
	private double taxableValue;
	private double taxableCLATValue;
	
	private double totalIncomeTax;
	private double totalIncomeTaxCLAT;
	
	
	private double discount;
	private double discountPercent;
	private String realEstate = "Real Estate Property #4 and #5";
	
	private int clatTerm;
	private String clatAssetList[];
	private double clatAssetValues[][];
	private double totalOfAssets[][];
	private double securitiesRentalIncome[][];
	private double totalClatValue[][];
	private double secGrowthRate;
	private double mainTable[][];
	
	private double annuity;
	private double annuityIncrease;
	private int growthTerm;
	
	boolean useLLC = false;
	boolean grantor = false;
	boolean escalating = false;
	boolean single = false;
	
	private String pageLabel = "CLAT";
	
	String clatType = "T";	// Default TERM Certain
	
	public CLATPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}		

	private String[][] CLATAssets()
	{
		String assetRows[][] = new String[clatAssetList.length + 3][7]; // Table width + format
		
		totalOfAssets = new double[1][2];
		totalClatValue = new double[1][2];
		int row;
		for( row = 0; row < clatAssetList.length; row++)
		{
				assetRows[row][0] = clatAssetList[row];
				assetRows[row][1] = "";
				assetRows[row][2] = number.format(clatAssetValues[row][0]);
				assetRows[row][3] = number.format(clatAssetValues[row][1]);
				assetRows[row][4] = "";
				assetRows[row][5] = "";
				assetRows[row][6] = "[][][][][][]";
				
				totalOfAssets[0][0] += clatAssetValues[row][0];
				totalOfAssets[0][1] += clatAssetValues[row][1];

		}

		
		totalClatValue[0][0] = this.securitiesRentalIncome[0][0] + totalOfAssets[0][0];
		totalClatValue[0][1] = this.securitiesRentalIncome[0][1] + totalOfAssets[0][1];
		
		assetRows[row][0] = "Total of Assets";
		assetRows[row][1] = "";
		assetRows[row][2] = number.format(totalOfAssets[0][0]);
		assetRows[row][3] = number.format(totalOfAssets[0][1]);
		assetRows[row][4] = "";
		assetRows[row][5] = "";
		assetRows[row][6] = "[][][border=b+t][border=b+t][][]";

		row++;
		assetRows[row][0] = "Securities & Rental Income";
		assetRows[row][1] = "";
		assetRows[row][2] = number.format(this.securitiesRentalIncome[0][0]);
		assetRows[row][3] = number.format(this.securitiesRentalIncome[0][1]);
		assetRows[row][4] = "";
		assetRows[row][5] = "";
		assetRows[row][6] = "[][][border=b][border=b][][]";
		
		row++;
		assetRows[row][0] = "Total Value";
		assetRows[row][1] = "";
		assetRows[row][2] = number.format(totalClatValue[0][0]);
		assetRows[row][3] = number.format(totalClatValue[0][1]);
		assetRows[row][4] = "";
		assetRows[row][5] = "";
		assetRows[row][6] = "[][][border=b][border=b][][]";

		return assetRows;
	}
	
	private String[][] CLATTable()
	{
		int numRows = mainTable.length > 30 ? 30 : mainTable.length;
		String rows[][] = new String[numRows][7];
		//String rows[][] = new String[20][7];
		int row = 0;
		int Red = makeColor(192,0,0);

		rows[row][0] ="";
		rows[row][1] ="";
		rows[row][2] = "";
		rows[row][3] = "";
		rows[row][4] = "";
		rows[row][5] = "" + number.format(mainTable[row][4]);
		rows[row][6] = "[][][][color="+Red+"][][]";
		
		
		for( row = 1; row < rows.length ; row++)
		{
			rows[row][0] ="";
			rows[row][1] ="" + number.format(mainTable[row][0]);
			rows[row][2] = "" + number.format(mainTable[row][2]);
			rows[row][3] = mainTable[row][3] < 0 ? "(" + number.format(Math.abs(mainTable[row][3])) + ")" : number.format(mainTable[row][3]);
			rows[row][4] = "" + number.format(mainTable[row][1]);
			rows[row][5] = "" + number.format(mainTable[row][4]);
			rows[row][6] = "[][][][color="+Red+"][][]";
		}
		return(rows);
	}
	
	public void draw()
	{
		if(grantor)
			pageLabel = "G-CLAT";
		if(escalating)
			pageLabel = pageLabel + "/Esc";
		
		page1();
		newPage();
		page2();
		newPage();
		page3();
		newPage();
		page4();
	}
	/**
	 * @return Returns the annuity.
	 */
	public double getAnnuity() {
		return annuity;
	}
	
	/**
	 * @return Returns the clatAssetList.
	 */
	public String[] getClatAssetList() {
		return clatAssetList;
	}
	
	/**
	 * @return Returns the clatAssetValues.
	 */
	public double[][] getClatAssetValues() {
		return clatAssetValues;
	}
	
	/**
	 * @return Returns the clatTerm.
	 */
	public int getClatTerm() {
		return clatTerm;
	}

	/**
	 * @return Returns the discount.
	 */
	public double getDiscount() {
		return discount;
	}
	/**
	 * @return Returns the estateTax.
	 */
	public double getEstateTax() {
		return estateTax;
	}
	/**
	 * @return Returns the estateTaxCLAT.
	 */
	public double getEstateTaxCLAT() {
		return estateTaxCLAT;
	}
	/**
	 * @return Returns the estateTaxPercent.
	 */
	public double getEstateTaxPercent() {
		return estateTaxPercent;
	}
	/**
	 * @return Returns the growthRate.
	 */
	public double getGrowthRate() {
		return growthRate;
	}
	/**
	 * @return Returns the growthTerm.
	 */
	public int getGrowthTerm() {
		return growthTerm;
	}
	/**
	 * @return Returns the incomeRate.
	 */
	public double getIncomeRate() {
		return incomeRate;
	}
	/**
	 * @return Returns the mainTable.
	 */
	public double[][] getMainTable() {
		return mainTable;
	}
	/**
	 * @return Returns the marketValue.
	 */
	public double getMarketValue() {
		return marketValue;
	}
	/**
	 * @return Returns the netToCharity.
	 */
	public double getNetToCharity() {
		return netToCharity;
	}
	/**
	 * @return Returns the netToCharityCLAT.
	 */
	public double getNetToCharityCLAT() {
		return netToCharityCLAT;
	}
	/**
	 * @return Returns the netToFamily.
	 */
	public double getNetToFamily() {
		return netToFamily;
	}
	/**
	 * @return Returns the netToFamilyCLAT.
	 */
	public double getNetToFamilyCLAT() {
		return netToFamilyCLAT;
	}
	/**
	 * @return Returns the realEstate.
	 */
	public String getRealEstate() {
		return realEstate;
	}
	/**
	 * @return Returns the secGrowthRate.
	 */
	public double getSecGrowthRate() {
		return secGrowthRate;
	}
	/**
	 * @return Returns the securitiesRentalIncome.
	 */
	public double[][] getSecuritiesRentalIncome() {
		return securitiesRentalIncome;
	}
	/**
	 * @return Returns the taxableCLATValue.
	 */
	public double getTaxableCLATValue() {
		return taxableCLATValue;
	}
	/**
	 * @return Returns the taxableValue.
	 */
	public double getTaxableValue() {
		return taxableValue;
	}
	
	
	/**
	 * @return Returns the totalCLATValue.
	 */
	public double getTotalCLATValue() {
		return totalCLATValue;
	}
	/**
	 * @return Returns the totalIncomeTax.
	 */
	public double getTotalIncomeTax() {
		return totalIncomeTax;
	}
	/**
	 * @return Returns the totalIncomeTaxCLAT.
	 */
	public double getTotalIncomeTaxCLAT() {
		return totalIncomeTaxCLAT;
	}
	/**
	 * @return Returns the totalValue.
	 */
	public double getValue() {
		return totalValue;
	}
	/**
	 * @return Returns the yrsGrowth.
	 */
	public double getYrsGrowth() {
		return yrsGrowth;
	}
	public boolean isUseLLC() {
		return useLLC;
	}
	private void page1()
	{
		StringBuffer assetNames = new StringBuffer("");
		
		for(int i=0; i < clatAssetList.length; i++, assetNames.append(",")) {
			assetNames.append(" " + clatAssetList[i]);
		}
		
		realEstate = assetNames.toString();
		// Page 1
		Rectangle rct;
		//Rectangle rctx;
		drawBorder(pageIcon,pageLabel);

			
		float ptSize = 12f;
		drawHeader(client, "The Problem");
		String gratExplanation = "Due to your sound financial acumen, your assets (" + realEstate + ") provide steady growth and income. However, the fair market value plus growth and income not spent continue to add to your estate tax liability.";
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
		Font fontBold = new Font(baseFontBold, ptSize, Font.NORMAL);
		Font fontBGreen = new Font(baseFontBold, ptSize, Font.NORMAL, green);
		Font fontBRed = new Font(baseFontBold, ptSize, Font.NORMAL, red);
		
		CellInfo cellData[][] = {
				{cif.getCellInfo("Today's Fair Market Value"),
					cif.getCellInfo(dollar.format(marketValue), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo("Projected Years of Growth"), 
					cif.getCellInfo("" + yrsGrowth, Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo("Average Growth Rate"), 
					cif.getCellInfo(percent.format(growthRate), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo("Average Income Rate"), 
					cif.getCellInfo(percent.format(incomeRate), Element.ALIGN_RIGHT)},
				
				{cif.getCellInfo(" "),
					cif.getCellInfo(" ")},
					
				{cif.getCellInfo("Total Value (projected) in Taxable Estate", fontBold),
					cif.getCellInfo(dollar.format(totalValue), Element.ALIGN_RIGHT, fontBold)},
				
				{cif.getCellInfo("Estate Tax (" + percent.format(estateTaxPercent) + ")", fontBRed), 
					cif.getCellInfo(dollar.format(estateTax), Element.ALIGN_RIGHT, fontBRed)},
				
				{cif.getCellInfo("Net Value Passing to Family", fontBGreen),
					cif.getCellInfo(dollar.format(totalValue - estateTax), Element.ALIGN_RIGHT, fontBGreen)}
		};
		
		
		// Do the chart
		
		//int align[] = {Element.ALIGN_LEFT, Element.ALIGN_RIGHT};
		table.setColumnWidths(widths);
		table.setTableData(cellData);
		table.drawTable();

		int et = (int)(estateTaxPercent * 100);
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
		drawBorder(pageIcon,pageLabel);
			
		drawHeader(client,"Current Situation");
		
		Rectangle rct = new Rectangle(prctLeft);
		int Red = makeColor(192,0,0);
		int Green = makeColor(0, 192, 0);
		int Blue = makeColor(0, 0, 192);
		
		PageTable table = new PageTable(writer);
		table.setTableFormat(rct, 6);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		table.setFontSize(8);
		float widths[] = { .40f, .10f, .24f, .24f, .01f, .01f};
		int alignments[] = {PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT};
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);
		
		String cells[][]={
			{
				"Assets",
				"",
				"Current Full Value",
				"Grown for " + integer.format(growthTerm) + " years",
				"",
				"",
				"[Bold=1,colspan=2,border=b][][border=b][border=b][][]"
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
		};			

		table.buildTableEx(cells);
		table.buildTableEx(CLATAssets());
		
		
		String cells3[][] = 
		{
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
				"Value for Estate Tax Purpose",
				"",
				"" + number.format(this.totalClatValue[0][0]),
				"" +  number.format(this.totalOfAssets[0][1]),
				"",
				"",
				"[][][][][][]"
			},
			{
				"Value of excess cash invested",
				"",
				"-",
				"" + number.format(this.securitiesRentalIncome[0][1]),
				"",
				"",
				"[][][border=b][border=b][][]"
			},
			{
				"Total value in estate",
				"",
				""+ number.format(this.totalClatValue[0][0]),
				""+ number.format(this.totalClatValue[0][1]),
				"",
				"",
				"[][][][][][]"
			},
			{
				"Estate Tax",
				"",
				"(" + number.format(this.totalClatValue[0][0] * this.estateTaxPercent) + ")",
				"(" + number.format(this.totalClatValue[0][1] * this.estateTaxPercent) + ")",
				"",
				"",
				"[][][color="+Red+"][color="+Red+"][][]"
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
				"Amount to Family",
				"",
				"" + dollar.format(this.totalClatValue[0][0] - (this.totalClatValue[0][0] * this.estateTaxPercent)),
				"" + dollar.format(this.totalClatValue[0][1] - (this.totalClatValue[0][1] * this.estateTaxPercent)),
				"",
				"",
				"[][][color="+Green+",border=t+b][color="+Green+",border=t+b][][]"
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
				"Amount to Charity",
				"",
				"" + dollar.format(0),
				"" + dollar.format(0),
				"",
				"",
				"[][][color="+Blue+"][color="+Blue+"][][]"
			},
			
		};
		
		table.buildTableEx(cells3);
		table.drawTable();
		
		rct = new Rectangle(prctRight);
		rct.setRight(rct.getRight());
		table = new PageTable(writer);
		table.setTableFormat(rct, 6);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		table.setFontSize(8);
//		float widths[] = { .25f, .11f, .16f, .16f, .16f, .16f};
//		int alignments[] = {PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT};
		float widths2[] = { .01f, .11f, .22f, .22f, .22f, .22f};
		table.setColumnWidths(widths2);
		table.setColumnAlignments(alignments);
		
		String cells2[][]=
		{
			{
				"\nSecurities + accum. Net Rental Income + accum. Securities",
				"",
				"",
				"",
				"",
				"",
				"[colspan=6,Bold=1,align=center,border=b][][][][][]"
			},
			{
				"",
				"",
				"Rental Income From Assets",
				"Income Taxes on Rents",
				"Net Earnings on Sec. Portfolio (" + percent.format(this.secGrowthRate) + ")",
				"Portfolio Balance",
				//"[][][align=center][align=center][align=center][align=center]"
				"[][][][][][]"
			},

			
		};
		table.buildTableEx(cells2);
		
		table.buildTableEx(CLATTable());
		table.drawTable();

		
	}
	
	private void page3()
	{
		drawBorder(pageIcon, pageLabel);
			
		drawHeader(client, "Charitable Lead Annuity Trust (CLAT)");

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
		
		String termLife="";
		
		if(clatType.equals("S") || clatType.equals("L")){
			termLife=", (or the death of the grantor, whichever comes first)";
		}
		
		
		if(grantor) {
			if(escalating){
				if(useLLC){
					theProcess = new String[] {
							String.format(Messages.getString("process.llc.g.e.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.llc.g.e.2"),
							String.format(Messages.getString("process.llc.g.e.3"),percent.format(getAnnuityIncrease()), getClatTerm(),termLife),
							Messages.getString("process.llc.g.e.4"),
							Messages.getString("process.llc.g.e.5")
					};
				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.g.e.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.g.e.2"),
							String.format(Messages.getString("process.flp.g.e.3"),percent.format(getAnnuityIncrease()), getClatTerm(),termLife),
							Messages.getString("process.flp.g.e.4"),
							Messages.getString("process.flp.g.e.5")
					};
				}
			} else {
				if(useLLC){
					theProcess = new String[] {
							String.format(Messages.getString("process.llc.g.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.llc.g.ne.2"),
							String.format(Messages.getString("process.llc.g.ne.3"), getClatTerm(),termLife),
							Messages.getString("process.llc.g.ne.4"),
							Messages.getString("process.llc.g.ne.5")
					};

				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.g.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.g.ne.2"),
							String.format(Messages.getString("process.flp.g.ne.3"),getClatTerm(),termLife),
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
							String.format(Messages.getString("process.llc.ng.e.3"),percent.format(getAnnuityIncrease()), getClatTerm(),termLife),
							Messages.getString("process.llc.ng.e.4"),
							Messages.getString("process.llc.ng.e.5")
					};
				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.ng.e.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.ng.e.2"),
							String.format(Messages.getString("process.flp.ng.e.3"),percent.format(getAnnuityIncrease()), getClatTerm(),termLife),
							Messages.getString("process.flp.ng.e.4"),
							Messages.getString("process.flp.ng.e.5")
					};
				}
			} else {
				if(useLLC){
					theProcess = new String[] {
							String.format(Messages.getString("process.llc.ng.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.llc.ng.ne.2"),
							String.format(Messages.getString("process.llc.ng.ne.3"), getClatTerm(),termLife),
							Messages.getString("process.llc.ng.ne.4"),
							Messages.getString("process.llc.ng.ne.5")
					};

				} else {
					theProcess = new String[] {
							String.format(Messages.getString("process.flp.ng.ne.1"),percent.format(.02),percent.format(.98)),
							Messages.getString("process.flp.ng.ne.2"),
							String.format(Messages.getString("process.flp.ng.ne.3"),getClatTerm(),termLife),
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

	}
	
	
	private void page4()
	{
		drawBorder(pageIcon, pageLabel);
		drawHeader(client, "The Comparison");

		Rectangle diag = new Rectangle(prctTop);
		diag.setBottom(diag.getBottom() - _1_4TH);
		diag.setLeft(diag.getLeft() - _1_4TH);
		

		if( useLLC) {
			if( userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
					drawDiagram("LLC-CLAT2-M.png", diag, LEFT + BOTTOM);
				} else {
					drawDiagram("LLC-CLAT2-L.png", diag, LEFT + BOTTOM);
				}
			} else {
				drawDiagram("LLC-CLAT2.png", diag, LEFT + BOTTOM);
			}
		} else {
			if( userInfo.isSingle()) {
				if( userInfo.getClientGender().equalsIgnoreCase("M")) {
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
		this.drawDiagramLabel("Total Value\nof Assets\n(" + dollar.format(marketValue) + ")", lblRct, LBL_CENTER, LBL_BOTTOM);
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(0),I2P(.256f), I2P(2.9862f), I2P(4)));
		this.drawDiagramLabel("Double Discounted\nGift to Family (" + dollar.format(taxableCLATValue) + ")", lblRct, LBL_RIGHT, LBL_BOTTOM);
		
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(6.4432f),I2P(.3403f), I2P(7.2927f), I2P(4)));
		this.drawDiagramLabel("Annual\nGift to\nCharity\n(" + dollar.format(annuity) + ")", lblRct, LBL_CENTER, LBL_BOTTOM);
		
		discountPercent = 1 - (this.discount / this.marketValue);
		lblRct = toPage(orgX, orgY, new Rectangle(I2P(0),I2P(0), I2P(8.4231f), I2P(2.9381f)));
		this.drawDiagramLabel("Discounted value to CLAT\n(@ " + percent.format(this.discountPercent) + ") " + dollar.format(this.discount), lblRct, LBL_RIGHT, LBL_TOP);
		
		
		Rectangle rctx = drawSection(prctLLeft, "Comparison in " + number.format(this.getYrsGrowth()) + " Years", new String[0], 0);
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
		
		///// This is the OLD way of building a table.
		CellInfo cellData[][] = {
				// Column Headers
				{	cif.getCellInfo(" "), 
					cif.getCellInfo("Keep in\nthe Estate", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo(this.clatTerm + " yr CLAT", Element.ALIGN_CENTER, fontBold),
					cif.getCellInfo("Difference", Element.ALIGN_CENTER, fontBold)
					
				},
				
				{	cif.getCellInfo("Total Income Taxes:"), 
					cif.getCellInfo(dollar.format(totalIncomeTax), Element.ALIGN_RIGHT),
					cif.getCellInfo(dollar.format(totalIncomeTaxCLAT), Element.ALIGN_RIGHT),
					cif.getCellInfo(dollar.format(totalIncomeTax - totalIncomeTaxCLAT), Element.ALIGN_RIGHT)
				},
				
				{	cif.getCellInfo("Estate/Gift Taxable:"), 
					cif.getCellInfo(dollar.format(totalValue), Element.ALIGN_RIGHT, fontBold),
					cif.getCellInfo(dollar.format(taxableCLATValue), Element.ALIGN_RIGHT, fontBold),
					cif.getCellInfo(dollar.format(totalValue - taxableCLATValue), Element.ALIGN_RIGHT, fontBold)
				},
				
				{	cif.getCellInfo("Total Estate/Gift Taxes:"), 
					cif.getCellInfo(dollar.format(estateTax), Element.ALIGN_RIGHT, fontBRed),
					cif.getCellInfo(dollar.format(0) + "*", Element.ALIGN_RIGHT, fontBRed),
					cif.getCellInfo(dollar.format(estateTax - estateTaxCLAT), Element.ALIGN_RIGHT, fontBRed),
				},
				
				{	cif.getCellInfo("Total to Family"),
					cif.getCellInfo(dollar.format(totalValue - estateTax), Element.ALIGN_RIGHT, fontBGreen),
					cif.getCellInfo(dollar.format(netToFamilyCLAT), Element.ALIGN_RIGHT, fontBGreen),
					cif.getCellInfo(dollar.format(Math.abs(netToFamilyCLAT - (totalValue - estateTax))), Element.ALIGN_RIGHT, fontBGreen)
				}, 

				{	cif.getCellInfo("Total to Charity"),
					cif.getCellInfo(dollar.format(netToCharity), Element.ALIGN_RIGHT, fontBBlue),
					cif.getCellInfo(dollar.format(netToCharityCLAT), Element.ALIGN_RIGHT, fontBBlue),
					cif.getCellInfo(dollar.format(netToCharityCLAT - netToCharity), Element.ALIGN_RIGHT, fontBBlue)
				}
				
		};
		
		// Do the chart
		//drawDiagram("clatchart.png", prctLRight, 0);
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Keep in Estate";
		String series2 = Integer.toString(clatTerm) + "yr CLAT";
		
		String cat1 = "IncTax";
		String cat2 = "FET";
		String cat3 = "Family";
		String cat4 = "Charity";
		
		dataSet.addValue(totalIncomeTax, series1, cat1);
		dataSet.addValue(totalIncomeTaxCLAT, series2, cat1);
		
		dataSet.addValue(estateTax, series1, cat2);
		dataSet.addValue(estateTaxCLAT, series2, cat2);
		
		dataSet.addValue(totalValue - estateTax, series1, cat3);
		dataSet.addValue(netToFamilyCLAT, series2, cat3);

		dataSet.addValue(netToCharity, series1, cat4);
		dataSet.addValue(netToCharityCLAT, series2, cat4);

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
	
	/**
	 * @param annuity The annuity to set.
	 */
	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}
	/**
	 * @param clatAssetList The clatAssetList to set.
	 */
	public void setClatAssetList(String[] clatAssetList) {
		this.clatAssetList = clatAssetList;
	}
	/**
	 * @param clatAssetValues The clatAssetValues to set.
	 */
	public void setClatAssetValues(double[][] clatAssetValues) {
		this.clatAssetValues = clatAssetValues;
	}
	/**
	 * @param clatTerm The clatTerm to set.
	 */
	public void setClatTerm(int clatTerm) {
		this.clatTerm = clatTerm;
	}
	/**
	 * @param discount The discount to set.
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	/**
	 * @param estateTax The estateTax to set.
	 */
	public void setEstateTax(double estateTax) {
		this.estateTax = estateTax;
	}
	/**
	 * @param estateTaxCLAT The estateTaxCLAT to set.
	 */
	public void setEstateTaxCLAT(double estateTaxCLAT) {
		this.estateTaxCLAT = estateTaxCLAT;
	}
	/**
	 * @param estateTaxPercent The estateTaxPercent to set.
	 */
	public void setEstateTaxPercent(double estateTaxPercent) {
		this.estateTaxPercent = estateTaxPercent;
	}
	/**
	 * @param growthRate The growthRate to set.
	 */
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	/**
	 * @param growthTerm The growthTerm to set.
	 */
	public void setGrowthTerm(int growthTerm) {
		this.growthTerm = growthTerm;
	}
	/**
	 * @param incomeRate The incomeRate to set.
	 */
	public void setIncomeRate(double incomeRate) {
		this.incomeRate = incomeRate;
	}
	/**
	 * @param mainTable The mainTable to set.
	 */
	public void setMainTable(double[][] mainTable) {
		this.mainTable = mainTable;
	}
	/**
	 * @param marketValue The marketValue to set.
	 */
	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	/**
	 * @param netToCharity The netToCharity to set.
	 */
	public void setNetToCharity(double netToCharity) {
		this.netToCharity = netToCharity;
	}
	/**
	 * @param netToCharityCLAT The netToCharityCLAT to set.
	 */
	public void setNetToCharityCLAT(double netToCharityCLAT) {
		this.netToCharityCLAT = netToCharityCLAT;
	}
	/**
	 * @param netToFamily The netToFamily to set.
	 */
	public void setNetToFamily(double netToFamily) {
		this.netToFamily = netToFamily;
	}
	/**
	 * @param netToFamilyCLAT The netToFamilyCLAT to set.
	 */
	public void setNetToFamilyCLAT(double netToFamilyCLAT) {
		this.netToFamilyCLAT = netToFamilyCLAT;
	}
	/**
	 * @return Returns the taxableGRATValue.
	 */
	/**
	 * @param realEstate The realEstate to set.
	 */
	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}
	/**
	 * @param secGrowthRate The secGrowthRate to set.
	 */
	public void setSecGrowthRate(double secGrowthRate) {
		this.secGrowthRate = secGrowthRate;
	}
	/**
	 * @param securitiesRentalIncome The securitiesRentalIncome to set.
	 */
	public void setSecuritiesRentalIncome(double[][] securitiesRentalIncome) {
		this.securitiesRentalIncome = securitiesRentalIncome;
	}
	/**
	 * @param taxableCLATValue The taxableCLATValue to set.
	 */
	public void setTaxableCLATValue(double taxableCLATValue) {
		this.taxableCLATValue = taxableCLATValue;
	}
	/**
	 * @param taxableValue The taxableValue to set.
	 */
	public void setTaxableValue(double taxableValue) {
		this.taxableValue = taxableValue;
	}
	/**
	 * @return Returns the totalGRATValue.
	 */
	/**
	 * @param totalCLATValue The totalCLATValue to set.
	 */
	public void setTotalCLATValue(double totalCLATValue) {
		this.totalCLATValue = totalCLATValue;
	}
	/**
	 * @param totalIncomeTax The totalIncomeTax to set.
	 */
	public void setTotalIncomeTax(double totalIncomeTax) {
		this.totalIncomeTax = totalIncomeTax;
	}
	/**
	 * @param totalIncomeTaxCLAT The totalIncomeTaxCLAT to set.
	 */
	public void setTotalIncomeTaxCLAT(double totalIncomeTaxCLAT) {
		this.totalIncomeTaxCLAT = totalIncomeTaxCLAT;
	}
	/**
	 * @param totalValue The totalValue to set.
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}

	/**
	 * @param yrsGrowth The yrsGrowth to set.
	 */
	public void setYrsGrowth(double yrsGrowth) {
		this.yrsGrowth = yrsGrowth;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public double[][] getTotalOfAssets() {
		return totalOfAssets;
	}

	public void setTotalOfAssets(double[][] totalOfAssets) {
		this.totalOfAssets = totalOfAssets;
	}

	public double[][] getTotalClatValue() {
		return totalClatValue;
	}

	public void setTotalClatValue(double[][] totalClatValue) {
		this.totalClatValue = totalClatValue;
	}

	public boolean isGrantor() {
		return grantor;
	}

	public void setGrantor(boolean grantor) {
		this.grantor = grantor;
	}

	public boolean isEscalating() {
		return escalating;
	}

	public void setEscalating(boolean escalating) {
		this.escalating = escalating;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public double getAnnuityIncrease() {
		return annuityIncrease;
	}

	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}

	public String getClatType() {
		return clatType;
	}

	public void setClatType(String clatType) {
		this.clatType = clatType;
	}
	
}
