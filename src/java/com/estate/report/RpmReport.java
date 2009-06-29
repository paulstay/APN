package com.estate.report;

import org.jfree.data.category.DefaultCategoryDataset;

import com.estate.pdf.Page;
import com.estate.pdf.PageTable;
import com.estate.toolbox.Rpm;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.BarChart;

/**
 * @author Paul Stay Description: Toolbox implementation of the RPM
 *         report..... 
 *    Copyright @2008 Teag Software LLC.
 * 
 */

public class RpmReport extends Page {

	Rpm rpm;
	String clientHeading;
	boolean isSingle = false;
	String gender;
	public double table1Rows[][];	// Current
	public double table2Rows[][];	// With RPM
	
	public RpmReport(Document document, PdfWriter writer) {
		super(document, writer);
	}
	
	public void draw() {
		init();
		page1();
		page2();
		page3();
		page4();
		page5();
		page6();
	}
	
	
	public Rpm getRpm() {
		return rpm;
	}
	
	
	
	public void init() {
		clientHeading = userInfo.getFirstName() + " " + userInfo.getLastName();
		if(userInfo.getGender().equalsIgnoreCase("B")) {
			isSingle = false;
		} else {
			gender = userInfo.getGender();
			isSingle = true;
		}
		
		rpm.calculate();
		rpm.calculateRpm();
		
		table1Rows = rpm.getRpTable();
		table2Rows = rpm.getRpmTable();
		
		
		
	}
	
	private String[] makeRow(double cols[], String format)
	{
		String scols[] = new String[cols.length + 1]; // Add one for format info
		int i;
		
		scols[0] = integer.format(cols[0]);		// row
		scols[1] = integer.format(cols[1]);		// Year
		
		for(i = 2; i < 7; i++)
		{
			scols[i] = number.format(cols[i]);
		}
		scols[i] = percent.format(cols[i++]);

		for(; i < cols.length; i++)
		{
			scols[i] = number.format(cols[i]);
		}
		
		
		scols[i] = format;
		
		return scols;
	}
	
	private String[] makeRow2(double cols[], String format)
	{
		String scols[] = new String[cols.length + 1]; // Add one for format info
		int i;

		scols[0] = integer.format(cols[0]);
		scols[1] = integer.format(cols[1]);
		
		for(i = 2; i < cols.length; i++)
		{
			scols[i] = number.format(cols[i]);
		}
		
		scols[i] = format;
		
		return scols;
	}
	
	
	public void page1() {
		drawBorder(pageIcon,"RPM");
		drawHeader(clientHeading, "Your Qualified Retirement Plan Dilemma");

		
		String good[]= {
			"You have Retirement Plan assets that are more than you will spend during your lifetime.  This excess will be available to pass on to your family.",	
		};
		
		String s1; 
		if( isSingle) {
			s1 = "death";
		} else {
			s1 = "the death of the surviving spouse";
		}
		String bad[] = {
			"One of the disadvantages of a qualified retirement plan is that upon " + s1 + ", any remaining balance in the plan is subject to double taxation.",
			"The first of these taxes is the estate tax.  The combined Federal & State estate taxes can transfer from 41% to 60% of the qualified plan balance to this confiscatory tax.",
			"The second of the taxes is the income tax. Depending on the distribution election made by the participant, " + "" +
					"or the cash needs of the estate, the entire balance can be subject to an income taxable distribution following the " +
					s1 + ".  This can cause the tax deferred balance to suddenly become subject to Federal and State Income Taxes during the estate settlement process, referred to as Income in Respect of a Decedent (IRD)."										
			
		};
		
		Rectangle rct = new Rectangle(prctTop);
		rct.setTop(rct.getTop() - _1_4TH);
		Rectangle rctx = this.drawSection(rct, "The Good News", good, 0);
		
		rct.setTop(rctx.getBottom() - _1_4TH);
		
		this.drawSection(rct, "The Bad News", bad, 0);
		
		Image egg = loadImage("eggArrow.png");
		Image eggLock = loadImage("eggLockArrow.png");

		String blank[] ={
				
		};
		rctx = this.drawSection(prctLLeft, "Your Situation in 25 Years", blank, 0);
		rct = new Rectangle(prctLLeft);
		float sr = rct.getWidth()/3;
		rct.setLeft( rct.getLeft() + sr);
		rct.setRight( rct.getRight() + sr);
		
		this.drawTaxPie(rct,100,79,"IRS (Taxes)", "Family");
		rct.setTop(rctx.getBottom() - _1_4TH);
		this.drawDiagram(egg, prctLLeft, LEFT, (72 * 2));
		

		this.drawSection(prctLRight, "Recommendations", blank, 0);
		rct = new Rectangle(prctLRight);
		sr = rct.getWidth()/3;
		rct.setLeft( rct.getLeft() + sr);
		rct.setRight(rct.getRight() + sr);
		
		this.drawTaxPie(rct,100,5,"IRS (Taxes)", "Family");
		rct.setTop(rctx.getBottom() - _1_4TH);
			
		this.drawDiagram(eggLock, prctLRight, LEFT, (72 * 2));
		newPage();
	}
	
	public void page2() {
		drawBorder(pageIcon,"RPM");
		drawHeader(clientHeading, "Current Retirement Plan");

		String rpmTable1Header[] = {
		"",
		"Year",
		"Plan Balance Beg Of Year",
		"Earning @" + percent.format(rpm.getPlanGrowth()),
		"Minimum Distribution",
		"Balance End of Year",
		"Retirement Plan Taxes at Death",
		"% to Taxes",
		"Accum'd in Investment",
		"Plan and Investment Money",
		"Total Taxes",
		"Net To Family",
		"[bold=1][bold=1][bold=1][bold=1][bold=1][bold=1][bold=1,color="+pgRed+"][bold=1,color="+pgRed+"][bold=1][bold=1][bold=1,color="+pgRed+"][bold=1,color="+pgGreen+"]"
		};
		
		String rpmTable1Styles=	"[][border=l][border=l][border=l][border=l][border=l][border=l,color="+pgRed+"][border=l,color="+pgRed+"][border=l][border=l][border=l,color="+pgRed+"][border=l+r,color="+pgGreen+"]";
		

		String row[][] = new String[1][];
		row[0] = rpmTable1Header;
		
		Rectangle rct = new Rectangle(prctFull);
		rct.setRight(rct.getRight() + _1_2TH);
		PageTable t = this.getTable(rct, rpmTable1Header.length-1);
		this.setTableFontandSize(t);

		float widths[] = {.03125f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f};
		t.setColumnWidths(widths);
		{
			int alignments[] = {CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR};
			t.setColumnAlignments(alignments);
		}
		
		t.buildTableEx(row);
		int alignments[] = {RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT};
		t.setColumnAlignments(alignments);
		
		int maxPage = table1Rows.length > 35 ? 35 : table1Rows.length;
		
		for(int i = 0; i < maxPage; i++)
		{
			row[0] = makeRow(table1Rows[i], rpmTable1Styles);
			t.buildTableEx(row);
		}
		
		t.drawTable();
		newPage();
	}
	
	public void page3(){
		char tm = 0x2122;
		drawBorder(pageIcon,"RPM");
		drawHeader(clientHeading, "Retirement Plan Maximizer" + tm);
		
		if( isSingle) {
			if( userInfo.getGender().equalsIgnoreCase("M")) {
				drawDiagram("RPM-M.png", prctTop, 0);
			} else {
				drawDiagram("RPM-F.png", prctTop, 0);
			}
		} else {
			drawDiagram("RPM.png", prctTop, 0);	
		}
		
		
		String sec1[];
		
		if(isSingle) {
			String gender;
			if( userInfo.getGender().equalsIgnoreCase("M")){
				gender = "his"; 
			} else {
				gender = "her";
			}
			sec1 = new String[] {
					userInfo.getFirstName() + " begins taking an income from "+gender+" retirement program. The amount taken in this example is designed to deplete their assets at the end of the " + rpm.getTerm() + "th year.",
					"The after tax income is gifted to an Inheritance Trust. It is assumed that there are no gift taxes paid on the gifts due to the number of heirs and the annual exclusions under the Crummey provision.",
					"The Trust uses the assets and income of the Trust to purchase a Life Insurance policy on " + 
					 userInfo.getFirstName()+"'s life. The trust is owner and beneficiary of the policy.",
					"The Trust, at death, receives the proceeds of the life insurance free of income and estate taxes."
			};
			
		} else {
			String f1;
			if(isSingle){
				if( userInfo.getGender().equalsIgnoreCase("M")){
					f1 = "begins taking an income from his retirement program.";
				}else {
					f1 = "begins taking an income from her retirement program.";
				}
			} else {
				f1 = " and " + userInfo.getFirstName() + " begin taking an imcome from his/her respective retirement programs.";
			}
			sec1 = new String[] {
					"" + userInfo.getFirstName() + f1 + " The amount taken in this example is designed to deplete their assets at the end of the " + rpm.getTerm() + "th year.",
					"The after tax income is gifted to an Inheritance Trust. It is assumed that there are no gift taxes paid on the gifts due to the number of heirs and the annual exclusions under the Crummey provision.",
					"The Trust uses the assets and income of the Trust to purchase a Life Insurance policy on " + 
					userInfo.getFirstName() + " lives. The trust is owner and beneficiary of the policy.",
					"The Trust, at the death of the parents, receives the proceeds of the life insurance free of income and estate taxes."
			};
		}
		
		Rectangle rct = new Rectangle(prctBottom);
		
		rct.setLeft(rct.getLeft() + prctBottom.getWidth() * .125f);
		rct.setRight(rct.getRight() - prctBottom.getWidth() * .125f);
		
		this.drawSection(rct, "The Process", sec1, 1);
		newPage();
	}
	
	public void page4() {
		drawBorder(pageIcon,"RPM");
		char tm = 0x2122;
		drawHeader(clientHeading, "Retirement Plan Maximizer" + tm + " Summary");
		
		
		String rpmTabelHeadr[] = {
			"The RETIREMENT PLAN MAXIMIZER (RPM)",
			"",
			"",
			"",
			"",
			"",
			"DISTRIBUTION AFTER BOTH DEATHS",
			"",
			"",
			"",
			"",
			"",
			"[colspan=6,align=center,border=b,bold=1][][][][][][colspan=6,align=center,border=b,bold=1][][][][][]"
		};
		
		String rpmTabel2Headr[] = {
			"",
			"Year",
			"Plan Balance Beg Of Year",
			"Earning @" + percent.format(rpm.getPlanGrowth()),
			"Plan Distribution",
			"Balance End of Year",
			"Total Taxes",
			"Net Distribution to Trust",
			"Trust Owned Ins Premium",
			"Year End Value of Trust",
			"Life Insurance Death Benefit",
			"Net To Family",
			"[bold=1][bold=1][bold=1][bold=1][bold=1][bold=1][bold=1,color="+pgRed+"][bold=1][bold=1][bold=1][bold=1][bold=1,color="+pgGreen+"]"
				
		};
		String rpmTable1Styles=	"[border=r][border=r][border=r][border=r][border=r][border=r][border=r,color="+pgRed+"][border=r][border=r][border=r][border=r][border=r,color="+pgGreen+"]";
		String row[][] = new String[1][];
		
		row[0] = rpmTabelHeadr;
		
		Rectangle rct = new Rectangle(prctFull);
		rct.setRight(rct.getRight() + _1_2TH);

		PageTable t = this.getTable(rct, rpmTabelHeadr.length-1);
		this.setTableFontandSize(t);

		float widths[] = {.03125f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f,.088f};
		t.setColumnWidths(widths);
		
		t.buildTableEx(row);
		{
			int alignments[] = {CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR, CTR};
			t.setColumnAlignments(alignments);
		}
		
		row[0] = rpmTabel2Headr;
		t.buildTableEx(row);

		int alignments[] = {RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT, RGT};
		t.setColumnAlignments(alignments);
		
		int maxPage = table1Rows.length > 35 ? 35 : table1Rows.length;
		
		for(int i = 0; i < maxPage; i++)
		{
			row[0] = makeRow2(table2Rows[i], rpmTable1Styles);
			t.buildTableEx(row);
		}
		
		t.drawTable();
		newPage();
	}
	
	public void page5() {
		drawBorder(pageIcon,"RPM");
		char tm = 0x2122;
		drawHeader(clientHeading, "Traditional vs. Retirement Plan Maximizer" + tm);
		
		String rpmTableHeader[] = {
			"Net (after all taxes) Amount To Family",
			"",
			"",
			"",
			"",
			"[colspan=5,align=center,border=b,bold=1][][][][]"
				
		};
		
		
		String rpmTableHeader2[] = {
			"",
			"Year",
			"Amount to Family \"Before\"",
			"Amount to Family \"RPM\"",
			"RPM vs Before Advantage",
			"[bold=1][bold=1][bold=1][bold=1][bold=1,color="+pgGreen+"]"
		};
		
		String style = "[border=r][border=r][border=r][border=r][border=r,color="+pgGreen+"]";
		
		Rectangle rct = new Rectangle(prctFull);
		float adj = rct.getWidth() / 4;
		rct.setRight(rct.getRight() - adj);
		rct.setLeft(rct.getLeft() + adj);

		PageTable t = this.getTable(rct, rpmTableHeader.length-1);
		this.setTableFontandSize(t);

		float widths[] = {.06f, .13f, .27f, .27f, .27f};
		t.setColumnWidths(widths);
		
		String row[][] = new String[1][];
		
		row[0] = rpmTableHeader;
		t.buildTableEx(row);

		{
			int alignments[] = {CTR, CTR, CTR, CTR, CTR};
			t.setColumnAlignments(alignments);
			row[0] = rpmTableHeader2;
			t.buildTableEx(row);

		}
		
		int alignments[] = {RGT, RGT, RGT, RGT, RGT};
		t.setColumnAlignments(alignments);
		
		int maxPage = table1Rows.length > 35 ? 35 : table1Rows.length;

		for(int i = 0; i < maxPage; i++)
		{
			row[0][0] = integer.format(table1Rows[i][0]);
			row[0][1] = integer.format(table1Rows[i][1]);
			row[0][2] = number.format(table1Rows[i][11]);
			row[0][3] = number.format(table2Rows[i][11]);
			row[0][4] = number.format(table2Rows[i][11] - table1Rows[i][11]);
			row[0][5] = style;
			t.buildTableEx(row);
		}
		
		t.drawTable();
			
		newPage();
	}
	
	public void page6() {
		drawBorder(pageIcon,"RPM");
		char tm = 0x2122;
		drawHeader(clientHeading, "Traditional vs Retirement Plan Maximizer" + tm);
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Traditional";
		String series2 = "RPM";

		int maxPage = table1Rows.length > 30? 30: table1Rows.length;

		for(int i = 0; i < maxPage; i++){
			String cat = (i+1) + "";
			dataSet.addValue(table1Rows[i][11], series1, cat);
			dataSet.addValue(table2Rows[i][11], series2, cat);
		}

		BarChart barChart = new BarChart();
		
		barChart.setTitle("Traditional vs. RPM");
		barChart.setDataSet(dataSet);
		Rectangle barRect = new Rectangle(prctFull);
		barRect.setRight(barRect.getRight()+ _1_2TH);
		barChart.setRect(barRect);
		barChart.generateBarChart();
		barChart.setColor(0,java.awt.Color.RED);
		barChart.setColor(1,java.awt.Color.GREEN);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
		

		newPage();
	}

	public void setRpm(Rpm rpm) {
		this.rpm = rpm;
	}

	private void setTableFontandSize(PageTable table)
	{
		table.setTableFont("arial.ttf");
		table.setTableFontBold("arialbd.ttf");
		table.setFontSize(9);
		
	}
	
}
