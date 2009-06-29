/*
 * Created on May 7, 2005
 *
 */
package com.teag.reports;

import java.util.Vector;

import org.jfree.data.category.DefaultCategoryDataset;

import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.BarChart;

/**
 * @author Paul Stay
 *
 */
public class MultiGenPages extends Page {

	private boolean llcFlag = false;
	private String clientMultiGenDesc[];
	private Vector<String[]> rows;
	private double fmv;
	private double yearsPerGen;
	private double fetRate;
	private double inflationRate;
	private double cLe;
	private double sLe;
	private double incomeRate;
	private double growthRate;
	private double payoutRate;
	private double trusteeFee;
	private double annualStateGrowth;
	private double annualDelGrowth;
	private String state;
	private String mgenState = "South Dakota";
	private double option1;
	private double option2;
	private double option3;
	
	
	/**
	 * @param document
	 * @param writer
	 */
	public MultiGenPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon=2;
		rows = new Vector<String[]>();
	}

	public void addFinalRowToGenerationTable(String label, String col1, String col2, String col3, String border, int color, String style)
    {
        String ds[] = {"$","$","$"};
        String format = "color=" + color;
        
        if(border.length() > 0 )
        {
            format += "," + border;
        }

        if(style.length() > 0 )
        {
            format += "," + style;
        }

        //if(col1 == 0) ds[0] = " "; if(col2 == 0) ds[1] = " "; if(col3 == 0) ds[2] = " ";
        
        
        String row[] = {
                    label,
                    "",
                    ds[0],
                    col1,
                    "",
                    ds[1],
                    col2,
                    "",
                    ds[2],
                    col3,
                    "[colspan=2][][" + format + "][" + format + "][][" + format + "][" + format + "][][" + format + "][" + format + "]"
            };
        rows.add(row);
    }
	
	public void addRowToGenerationTable(String label, double col1, double col2, double col3, String border, int color, String style)
	{
		String ds[] = {"$","$","$"};
		String format = "color=" + color;
		
		if(border.length() > 0 )
		{
			format += "," + border;
		}

		if(style.length() > 0 )
		{
			format += "," + style;
		}

		if(col1 == 0) ds[0] = " "; if(col2 == 0) ds[1] = " "; if(col3 == 0) ds[2] = " ";
		
		
		String row[] = {
					label,
					"",
					ds[0],
					"" + number.format(col1),
					"",
					ds[1],
					""+ number.format(col2),
					"",
					ds[2],
					""+ number.format(col3),
					"[colspan=2][][" + format + "][" + format + "][][" + format + "][" + format + "][][" + format + "][" + format + "]"
			};
		rows.add(row);
	}
	
	public void draw()
	{
		page1();
		newPage();
		page2();
		newPage();
		page3();
	}
	
	public void endGenerationTable()
	{
		String row[] = {
				
					" ",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"[colspan=10,ptsize=3][][][][][][][][][]"
				
		};
		
		rows.add(row);
	}
	
	public double getAnnualDelGrowth() {
		return annualDelGrowth;
	}
	
	
	public double getAnnualStateGrowth() {
		return annualStateGrowth;
	}
	
	
	public double getCLe() {
		return cLe;
	}

	/**
	 * @return Returns the clientMultiGenDesc.
	 */
	public String[] getClientMultiGenDesc() {
		return clientMultiGenDesc;
	}
	
	
	public double getFetRate() {
		return fetRate;
	}
	public double getFmv() {
		return fmv;
	}
	public double getGrowthRate() {
		return growthRate;
	}
	public double getIncomeRate() {
		return incomeRate;
	}
	public double getInflationRate() {
		return inflationRate;
	}
	public double getOption1() {
		return option1;
	}
	public double getOption2() {
		return option2;
	}
	public double getOption3() {
		return option3;
	}
	public double getPayoutRate() {
		return payoutRate;
	}
	public Vector<String[]> getRows() {
		return rows;
	}
	
	public double getSLe() {
		return sLe;
	}
	public String getState() {
		return state;
	}
	public double getTrusteeFee() {
		return trusteeFee;
	}
	public double getYearsPerGen() {
		return yearsPerGen;
	}
	/**
	 * @return Returns the llcFlag.
	 */
	public boolean isLlcFlag() {
		return llcFlag;
	}
	private void page1()
	{
		drawBorder(pageIcon, "MGT");
		drawHeader(userInfo.getClientHeading(), "Multigenerational Trust");
		this.drawDiagram("MultiG1.png",prctFull,0);
		
	}
	
	private void page2()
	{
		drawBorder(pageIcon, "MGT");
		drawHeader(userInfo.getClientHeading(), "Multigenerational Trust");
		Rectangle rct = new Rectangle(prctLeft);
		rct.setLeft(rct.getLeft() - _1_4TH);
		rct.setBottom(rct.getBottom() - _1_4TH);
		
		if( userInfo.isSingle()){
			if( userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("MultiG3-M.png", prctLeft, BOTTOM + LEFT);
			} else {
				drawDiagram("MultiG3-F.png", prctLeft, BOTTOM + LEFT);
			}
		} else {
			drawDiagram("MultiG3.png", prctLeft, BOTTOM + LEFT);
		}
		
		rct = this.calcSectionRect(prctRight, "", this.clientMultiGenDesc, 1);
		rct.setTop(prctRight.getTop() - ((prctRight.getHeight() - rct.getHeight()) / 2));
		this.drawSection(rct, "", this.clientMultiGenDesc, 1, 12, 10.5f);
	}
	private void page3()
	{
		drawBorder(pageIcon, "MGT");
		drawHeader(userInfo.getClientHeading(), "Grantor Multigenerational Trust");
		int l = Element.ALIGN_LEFT;
		int c = Element.ALIGN_CENTER;
		int r = Element.ALIGN_RIGHT;
		
		int rowCount = rows.size();
		String cells[][] = new String[rowCount][0];
		
		for(int i = 0; i < rowCount; i++)
		{
			cells[i] = (String[])rows.get(i);
			
		}
		
		PageTable table = new PageTable(writer);
		float widths[] = {30.5f,11,2.5f,15,3,2.5f,15,3,2.5f,15};
		int genAlignments[] = {l,l,c,r,c,c,r,c,c,r};
		table.setTableFont("arial.ttf");
		table.setTableFontBold("arialbd.ttf");
		Rectangle rct = new Rectangle(prctFull);
		rct.setRight(rct.getLeft() + ((prctFull.getWidth() *.66f)) - _1_8TH);
		table.setTableFormat(rct, 10);
		table.setFontSize(7.5f);
		// here we do the assumptions
		
		
		// now we do the generations summary
		table.setColumnAlignments(genAlignments);
		table.setColumnWidths(widths);

		String heading[][] = {
				{
					"",
					"",
					"Option 1",
					"", // colspan
					"",
					"Option 2",
					"", // colspan
					"",
					"Option 3",
					"",
					"[][][ptsize=8,align=center,colspan=2,bold=1,underline=1][][][ptsize=8,align=center,colspan=2,bold=1,underline=1][][][ptsize=8,align=center,colspan=2,bold=1,underline=1][]"
					
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
					"[colspan=10][][][][][][][][][]"

				},
				{
					"",
					"",
					"Normal Estate Distribution",
					"", // colspan
					"",
					"Multigenerational Trust in " + state,
					"", // colspan
					"",
					"Multigenerational Trust in " + mgenState,
					"",
					"[][][align=center,valign=center,colspan=2][][][align=center,valign=center,colspan=2][][][align=center,valign=center,colspan=2][]"
					
				}			
		};
		
		table.buildTableEx(heading);
		table.buildTableEx(cells);
		
		table.drawTable();
		
		table = new PageTable(writer);
		float widths2[] = {.75f, .25f};
		int genAlignments2[] = {l,c};
		table.setTableFont("arial.ttf");
		table.setTableFontBold("arialbd.ttf");
		
		rct = new Rectangle(prctFull);
		rct.setLeft(rct.getLeft() + ((prctFull.getWidth() *.66f)) + _1_8TH);
		rct.setRight(rct.getRight() + _1_2TH);
		table.setTableFormat(rct, 2);
		table.setFontSize(8f);
		table.setColumnAlignments(genAlignments2);
		table.setColumnWidths(widths2);

		
		String cells2[][] = {
				{
					"ASSUMPTIONS FOR COMPARISON",
					"",
					"[border=b,colspan=2,ptsize=9,align=center][]"
				},
				{
					"Current FMV of security Porfolio",
					number.format(fmv),
					"[][bold=1]"
				},
				{
					"Years per Generation(2nd thru 4th)",
					number.format(yearsPerGen),
					"[][bold=1]"
				},
				{
					"Estate Tax Rate",
					percent.format(fetRate),
					"[][bold=1]"
				},
				{
					"Inflation Rate",
					percent.format(inflationRate),
					"[][bold=1]"
				},
				{
					clientFirstName + " Life Expectancy",
					number.format(cLe),
					"[][bold=1]"
				},
				{
					userInfo.isSingle() ? "" : spouseFirstName + " Life Expectancy",
					userInfo.isSingle() ? "" : number.format(sLe),
					"[][bold=1]"
				},
				{
					"Annual Rate of Income (gross)",
					percent.format(incomeRate),
					"[][bold=1]"
				},
				{
					"Annual Rate of Growth (gross)",
					percent.format(growthRate),
					"[][bold=1]"
				},
				{
					"Annual Payout Rate (2nd - 4th generations)",
					percent.format(payoutRate),
					"[][bold=1]"
				},
				{
					"Trustee Fee Rate",
					percent.format(trusteeFee),
					"[][bold=1]"
				},
				{
					"Net Annual Growth (" + state + ")",
					percent.format(annualStateGrowth),
					"[][bold=1]"
				},
				{
					"Net Annual Growth (SD)",
					percent.format(annualDelGrowth),
					"[][bold=1]"
				},

		};
		table.setPaddingTop(2);
		table.buildTableEx(cells2);
		table.drawTable();
		Rectangle barRect = table.getResultRect();
		barRect.setTop(barRect.getBottom()-72f);
		barRect.setBottom(prctFull.getBottom());
		//barRect.setLeft(barRect.getLeft() + _1_4TH);
		//barRect.setRight(barRect.getRight() - _1_4TH);

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "Option 1";
		String series2 = "Option 2";
		String series3 = "Option 3";
		
		dataSet.addValue(option1, 	series1, series1);
		dataSet.addValue(option2,	series2, series2);
		dataSet.addValue(option3, 	series3, series3);
		
		BarChart barChart = new BarChart();
		barChart.setTitle("Comparison (000's)");
		barChart.setDataSet(dataSet);
		barChart.setItemMargin(.01f);
		barChart.setCategoryMargin(0.005f);
		barChart.setRect(barRect);
		barChart.setLablesOff(true);
		barChart.generateBarChart();
		barChart.setColor(0,java.awt.Color.RED);
		barChart.setColor(1,java.awt.Color.YELLOW);
		barChart.setColor(2,java.awt.Color.GREEN);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, barRect, 0, 72);
	}
	public void setAnnualDelGrowth(double annualDelGrowth) {
		this.annualDelGrowth = annualDelGrowth;
	}
	public void setAnnualStateGrowth(double annualStateGrowth) {
		this.annualStateGrowth = annualStateGrowth;
	}
	public void setCLe(double le) {
		cLe = le;
	}
	/**
	 * @param clientMultiGenDesc The clientMultiGenDesc to set.
	 */
	public void setClientMultiGenDesc(String[] clientMultiGenDesc) {
		this.clientMultiGenDesc = clientMultiGenDesc;
	}
	public void setFetRate(double fetRate) {
		this.fetRate = fetRate;
	}
	public void setFmv(double fmv) {
		this.fmv = fmv;
	}
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	public void setIncomeRate(double incomeRate) {
		this.incomeRate = incomeRate;
	}

	public void setInflationRate(double inflationRate) {
		this.inflationRate = inflationRate;
	}
	/**
	 * @param llcFlag The llcFlag to set.
	 */
	public void setLlcFlag(boolean llcFlag) {
		this.llcFlag = llcFlag;
	}
	public void setOption1(double option1) {
		this.option1 = option1;
	}
	public void setOption2(double option2) {
		this.option2 = option2;
	}
	public void setOption3(double option3) {
		this.option3 = option3;
	}
	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public void setRows(Vector<String[]> rows) {
		this.rows = rows;
	}

	public void setSLe(double le) {
		sLe = le;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTrusteeFee(double trusteeFee) {
		this.trusteeFee = trusteeFee;
	}

	public void setYearsPerGen(double yearsPerGen) {
		this.yearsPerGen = yearsPerGen;
	}

	public void startGenerationTable(String header)
	{
		int Blue = makeColor(0,0,192);
		
		String row[] = {
			
				header,
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"[colspan=10,border=t+b,color="+Blue+",align=center][][][][][][][][][]"
			
		};
		
		rows.add(row);
	}

	public String getMgenState() {
		return mgenState;
	}

	public void setMgenState(String mgenState) {
		this.mgenState = mgenState;
	}
	
}
