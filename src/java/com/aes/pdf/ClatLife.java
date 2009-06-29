package com.aes.pdf;

import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.estate.beans.AssetList;
import com.estate.beans.UserAssets;
import com.estate.cashflow.CashFlowData;
import com.estate.pdf.CellInfo;
import com.estate.pdf.CellInfoFactory;
import com.estate.pdf.Page;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.estate.toolbox.Clat;
import com.estate.toolbox.LifeTool;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.chart.BarChart;

/**
 * @author Paul Stay Description: Super Charged CLAT with Life Insurance
 *         (Gaurdian Product) Date: 27 June 2008 Version 1.0
 * 
 * Generates the CLAT PDF pages.
 */
public class ClatLife extends Page {

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
	LifeTool lifeInsurance;
	AssetList aList;
	double marketValue;
	int yrsGrowth;
	double growthRate;
	double incomeRate;
	double totalValue;
	double totalTax;

	double estateTax;

	ArrayList<CashFlowData> cashFlow = new ArrayList<CashFlowData>();

	public ClatLife(Document document, PdfWriter writer) {
		super(document, writer);

	}

	public void cashFlow2(Rectangle r) {
		// Write Header here
		r.setTop(r.getBottom() - 72);
		r.setLeft(_1_4TH);
		r.setRight(r.getLeft() + 756);
		
		r = this.drawLabel("LLC (000's)", r, "arialbd.ttf", new Color(0,0,0), 14, LBL_CENTER, LBL_CENTER);
		
		Rectangle rect = new Rectangle(prctFull);
		rect.setTop(r.getBottom() - _1_8TH);
		rect.setLeft(14f);
		rect.setRight(rect.getLeft() + 756);
		
		PageTable cashFlow = new PageTable(writer);
		cashFlow.setTableFormat(rect, 9);
		cashFlow.setTableFont("arial.TTF");
		cashFlow.setTableFontBold("arialbd.TTF");
		cashFlow.setFontSize(10);
		float widths2[] = {.1f,.1f,.1f,.1f,.1f,.1f,.1f,.1f,.1f};
		int alignments2[] = {
				PdfPCell.ALIGN_RIGHT,				PdfPCell.ALIGN_RIGHT, 	PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT,				PdfPCell.ALIGN_RIGHT, 	PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_RIGHT,				PdfPCell.ALIGN_RIGHT, 	PdfPCell.ALIGN_RIGHT
				};
		cashFlow.setColumnWidths(widths2);
		cashFlow.setColumnAlignments(alignments2);
		float pb = cashFlow.getPaddingBottom();
		cashFlow.setPaddingBottom(pb + 12.5f);		// Add a bit of space between the header and the data.
		
		String heading[][] = {
				{
					"Year",
					"Beg. Principal",
					"Income",
					"Growth",
					"Premium",
					"Clat Payment",
					"End Balance",
					"Loan Liability (" + percent.format(.06) + ")",
					"Life Ins. CV",
					"[bold=1,align=right][bold=1,align=right][bold=1,align=right]" +
					"[bold=1,align=right][bold=1,align=right][bold=1,align=right]" +
					"[bold=1,align=right][bold=1,align=center][bold=1,align=right]"
				},
		};
		
		cashFlow.buildTableEx(heading);
		cashFlow.setPaddingBottom(pb);
		cashFlow.buildTableEx(clatCashFlow());
		cashFlow.drawTable();
		
		newPage();
		
	}
	
	public String[][] clatCashFlow() {
		String cash[][] = new String[clat.getTerm()][10];
		
		double bal = aList.getTotal();
		double growthRate = aList.getGrowth();
		double incomeRate = aList.getIncome();
		double clatPayment = clat.getAnnuityPayment();
		double clatIncrease = clat.getAnnuityIncrease();
		double premiums[] = lifeInsurance.getPremiums();
		double loan = 0;
		
		for( int i=0; i < clat.getTerm(); i++) {
			double ir = bal * incomeRate;
			double ig = bal * growthRate;
			double p = 0;
			if( i < premiums.length)
				p = premiums[i];
			else 
				p = 0;
			double eBal = bal + ir + ig - p;
			loan = (loan * 1.06) + clatPayment;
			cash[i][0] = number.format(i+1);
			cash[i][1] = number.format(bal/1000);
			cash[i][2] = number.format(ir/1000);
			cash[i][3] = number.format(ig/1000);
			cash[i][4] = number.format(p/1000);
			//cash[i][5] = number.format(clatPayment/1000);
			cash[i][5] = number.format(-clatPayment/1000);
			cash[i][6] = number.format(eBal/1000);
			cash[i][7] = number.format(-loan/1000);
			cash[i][8] = number.format(lifeInsurance.getCashValue()[i]/1000);
			cash[i][9] = "[][bold=1][][][][][bold=1][][]";
			bal = eBal;
			clatPayment = clatPayment * (1 + clatIncrease);
		}
		
		return cash;
		
	}
	
	public void cashFlow(Rectangle r) {
		int term = clat.getTerm();
		boolean useMax = false;

		// Write Header here
		r.setTop(r.getBottom() - 72);
		r.setLeft(_1_4TH);
		r.setRight(r.getLeft() + 756);

		r = this
				.drawLabel("G-CLAT Cash Flow and Asset Networth (000's)", r,
						"arialbd.ttf", new Color(0, 0, 255), 14, LBL_CENTER,
						LBL_CENTER);

		//
		Rectangle rect = new Rectangle(prctFull);

		rect.setTop(r.getBottom() - _1_8TH); // Start at the bottom of the
											// rectangle we just drew.
		rect.setLeft(54f);
		rect.setRight(rect.getLeft() + 756); // Gives us 1/4 inch on right hand
											// of the page

		float w[] = initWidths(17);
		int x = 1;
		String row[] = new String[18];

		StringBuffer align = new StringBuffer(
				"[][colspan=3,align=right,bold=1][][]");
		row[1] = "Years";
		for (int i = 4; i < 17; i++) {
			if(x <11)
				row[i] = Integer.toString(x++);
			else {
				row[i] = "";
			}
			align.append("[align=right]");
		}

		if(useMax) {
			row[14] = Integer.toString(term);
		}

		row[17] = align.toString();

		Rectangle rct = doRow(row, w, rect, 17);

		row = new String[18];
		row[0] = "Cash Receipts";
		row[17] = "[colspan=4,ptsize=10,bold=1][][][][][][][][][][][][][][][][][]";

		rect.setTop(rct.getBottom());
		rct = doRow(row, w, rect, 17);

		double cr[] = new double[CashFlowData.MAX_ROW];
		double dis[] = new double[CashFlowData.MAX_ROW];

		// List all of the cash receipts here
		for (CashFlowData c : cashFlow) {
			double data[] = c.getReceipts();
			// Need to adjust this for
			java.text.DecimalFormat cf = new java.text.DecimalFormat("###,###");
			row[0] = "";
			if(c.isLife()){
				row[1] = "Life Ins. Dividends";
			} else {
				row[1] = c.getDescription() + "Income";
			}
			int xt = 4;
			StringBuffer a2 = new StringBuffer("[][colspan=3,align=right][][]");
			boolean rFlag = false;
			for (int i = 0; i < 10; i++) {
				cr[i + 1] += data[i + 1];
				if (data[i + 1] > 0)
					rFlag = true;
				row[xt++] = cf.format(data[i + 1] / 1000);
				a2.append("[align=right]");
			}
			if (rFlag) {
				row[17] = a2.toString();
				rect.setTop(rct.getBottom());
				rct = doRow(row, w, rect, 17);
			}
		}

		row = new String[18];
		row[0] = "Disbursements";
		row[17] = "[colspan=4,ptsize=10,bold=1][][][][][][][][][][][][][][][][][]";

		rect.setTop(rct.getBottom());
		rct = doRow(row, w, rect, 17);

		// List all of the cash receipts here
		for (CashFlowData c : cashFlow) {
			double data[] = c.getDisbursements();
			// Need to adjust this for
			java.text.DecimalFormat cf = new java.text.DecimalFormat("###,###");
			row[0] = "";
			row[1] = c.getDescription();
			int xt = 4;
			StringBuffer a2 = new StringBuffer("[][colspan=3,align=right][][]");
			boolean rFlag = false;
			for (int i = 0; i < 10; i++) {
				dis[i + 1] += data[i + 1];
				if (data[i + 1] > 0)
					rFlag = true;
				row[xt++] = cf.format(data[i + 1] / 1000);
				a2.append("[align=right]");
			}
			if (rFlag) {
				row[17] = a2.toString();
				rect.setTop(rct.getBottom());
				rct = doRow(row, w, rect, 17);
			}
		}

		// List Excess Cash Flow
		row = new String[18];
		row[0] = "Excess Cash Flow";
		row[17] = "[colspan=4,ptsize=10,bold=1][][][][][][][][][][][][][][][][][]";

		rect.setTop(rct.getBottom());
		rct = doRow(row, w, rect, 17);

		row[0] = "";
		align = new StringBuffer("[][][][]");
		int x1 = 4;
		for (int i = 0; i < 10; i++) {
			java.text.DecimalFormat cf = new java.text.DecimalFormat("###,###");
			row[x1++] = cf.format((cr[i + 1] - dis[i + 1]) / 1000);
			align.append("[align=right]");
		}
		row[17] = align.toString();

		rect.setTop(rct.getBottom());
		rct = doRow(row, w, rect, 17);

		// Blank Line
		rect.setTop(rct.getBottom());
		rct = doCashHeader("", 0, w, 17, rect);
		rect.setTop(rct.getBottom());
		// Assets Networth
		for (CashFlowData c : cashFlow) {
			java.text.DecimalFormat cf = new java.text.DecimalFormat("###,###");
			if (c.isAsset()) {
				rct = doCashHeader(c.getDescription() + "Value", 0, w, 17, rect);
				boolean rFlag = false;
				rect.setTop(rct.getBottom());
				double data[] = c.getNetworth();
				row = new String[18];
				row[1] = "Begin";
				align = new StringBuffer("[][colspan=3][][]");
				int x2 = 4;
				for (int i = 0; i < 10; i++) {
					if (data[i] > 0)
						rFlag = true;
					row[x2++] = cf.format(data[i] / 1000);
					align.append("[align=right]");
				}
				row[17] = align.toString();
				if (rFlag) {
					rct = doRow(row, w, rect, 17);
					rect.setTop(rct.getBottom());
				} else {
					break;
				}

				row[1] = "Growth";
				align = new StringBuffer("[][colspan=3][][]");
				x2 = 4;
				for (int i = 0; i < 10; i++) {
					row[x2++] = cf.format(data[i] * c.getGrowth() / 1000);
					align.append("[align=right]");
				}

				row[17] = align.toString();
				rct = doRow(row, w, rect, 17);
				rect.setTop(rct.getBottom());

				if (c.isApplyExcess()) {
					row[1] = "Excess Cash";
					align = new StringBuffer("[][colspan=3][][]");
					x2 = 4;
					for (int i = 0; i < 10; i++) {
						row[x2++] = cf
								.format((cr[i + 1] - dis[i + 1]) / 1000.0);
						align.append("[align=right]");
					}
					row[17] = align.toString();
					rct = doRow(row, w, rect, 17);
					rect.setTop(rct.getBottom());
				}

				row[1] = "End";
				align = new StringBuffer("[][colspan=3][][]");
				x2 = 4;
				for (int i = 0; i < 10; i++) {
					row[x2++] = cf.format(data[i + 1] / 1000);
					align.append("[align=right]");
				}

				row[17] = align.toString();
				rct = doRow(row, w, rect, 17);
				rect.setTop(rct.getBottom());
				// Blank Line
				rct = doCashHeader("", 0, w, 17, rect);
				rect.setTop(rct.getBottom());
				
				row = new String[18];
			} else if(c.isLife()) {
				row = new String[18];
				row[0] = c.getDescription();
				align= new StringBuffer("[colspan=4,bold=1][][][]");
				double data[] = c.getNetworth();
				int x3 = 4;
				for(int i=1; i <= 10; i++){
					row[x3++] = cf.format(data[i]/1000);
					if(data[i] < 0) {
						align.append("[align=right,color="+makeColor(240,0,0)+"]");
					} else {
						align.append("[align=right]");
					}
				}
				row[17] = align.toString();
				rct = doRow(row,w,rect,17);
				rect.setTop(rct.getBottom());
			}
		}
	}

	Rectangle doCashHeader(String header, int indent, float[] widths, int cols,
			Rectangle rect) {
		Rectangle r = null;
		String row[] = new String[cols + 1];
		row[indent] = header;
		StringBuffer align = new StringBuffer("");
		for (int i = 0; i < cols; i++) {
			if (i == indent) {
				align.append("[colspan=" + Integer.toString(4 - i) + "]");
			} else {
				align.append("[]");
			}
		}
		row[cols] = align.toString();
		r = doRow(row, widths, rect, cols);

		return r;
	}

	public Rectangle doRow(String rowInfo[], float widths[], Rectangle rect,
			int colCount) {
		String row[][] = new String[1][];

		PageTable rowTable = getTable(rect, colCount);
		rowTable.setTableFont("arial.ttf");
		rowTable.setTableFontBold("arialbd.ttf");
		rowTable.setTableFontItalic("ariali.ttf");
		rowTable.setTableFontBoldItalic("arialbi.ttf");
		rowTable.setFontSize(8);
		rowTable.setPaddingBottom(_1_32TH + _1_64TH);

		rowTable.setColumnWidths(widths);
		row[0] = rowInfo;
		rowTable.buildTableEx(row);
		rowTable.drawTable();
		return rowTable.getResultRect();
	}

	public void draw() {
		page1();
		page2();
		page3();
		page4();
		//page5();
	}

	public void genData() {
		UserAssets a[] = aList.getAssetList();
		for (int i = 0; i < a.length; i++) {
			marketValue += a[i].getFmv();
			// While we are at it, create the cash flow for the assets
			CashFlowData cfd = new CashFlowData();
			cfd.setDescription(a[i].getDescription());
			cfd.setFmv(a[i].getFmv());
			cfd.setGrowth(a[i].getGrowth());
			cfd.setIncome(a[i].getIncome());
			cfd.setCalculationType(0);
			cfd.setApplyExcess(true);
			cfd.setAsset(true);
			cfd.setTaxRate(clat.getTaxRate());
			cfd.setStatus(1);
			if (i == 0) {
				cfd.setApplyExcess(true);
			} else {
				cfd.setApplyExcess(false);
			}
			cfd.init();
			cashFlow.add(cfd);
		}

		// calculate the weighted growth and income
		for (int i = 0; i < a.length; i++) {
			double d = a[i].getFmv() / marketValue;
			growthRate += d * a[i].getGrowth();
			incomeRate += d * a[i].getIncome();
		}
		

		// Genearte Cash Flow Data for Disbursements
		CashFlowData dis = new CashFlowData();
		double clatPayment = clat.getAnnuityPayment();
		double clatPaymentGrowth = clat.getAnnuityIncrease();
		double payments[] = new double[CashFlowData.MAX_ROW];

		for (int i = 1; i <= clat.getTerm(); i++) {
			payments[i] = clatPayment;
			clatPayment = clatPayment * (1 + clatPaymentGrowth);
		}
		
		// Setup a loan for the clat payments
		CashFlowData loan = new CashFlowData();
		loan.init();
		loan.setDescription("Loan For Clat Payment");
		double loanCash[] = new double[CashFlowData.MAX_ROW];
		for(int i=1; i <= clat.getTerm(); i++) {
			loanCash[i] = payments[i];
		}
		loan.setReceipts(loanCash);
		loan.setStatus(0);
		cashFlow.add(loan);
		
		dis.init();
		dis.setDisbursements(payments);
		dis.setStatus(0);
		dis.setDescription("Clat Payment");
		cashFlow.add(dis);

		// Life Insurance Payments
		if (lifeInsurance != null) {
			double premiums[] = lifeInsurance.getPremiums();
			double cashValue[] = lifeInsurance.getCashValue();
			double dividends[] = lifeInsurance.getDividends();
			double d[] = new double[CashFlowData.MAX_ROW];
			double p[] = new double[CashFlowData.MAX_ROW];
			
			double bound = Math.min(dividends.length,CashFlowData.MAX_ROW);
			for(int i=1; i<bound; i++){
				d[i] = dividends[i-1];
			}
			
			for(int i=1; i<= premiums.length; i++){
				p[i] = premiums[i-1];
			}
			
			CashFlowData cvd = new CashFlowData();
			cvd.init();
			cvd.setLife(true);
			cvd.setStatus(0);
			cvd.setDescription("Life Insurance");
			cvd.setDisbursements(p);
			cvd.setNetworth(cashValue);
			cvd.setReceipts(d);
			cashFlow.add(cvd);
			
			
			
		}

		double excessCash[] = new double[CashFlowData.MAX_ROW];
		for (int i = 1; i <= CashFlowData.MAX_ROW-1; i++) {
			double cr = 0.0;
			double d = 0.0;
			for (CashFlowData c : cashFlow) {
				c.addYear();
				cr += c.getRec();
				d += c.getDis();
			}
			excessCash[i] = cr - d;

			for (CashFlowData c : cashFlow) {
				if (c.isApplyExcess()) {
					c.addNetworth(excessCash[i]);
					break; // We don't need to continue on as we have applied
							// the excess Cash
				}
			}
		}
		
		CashFlowData lvd = new CashFlowData();
		lvd.init();
		lvd.setLife(true);
		lvd.setStatus(0);
		lvd.setDescription("Loan Liability");
		double lp[] = new double[CashFlowData.MAX_ROW];
		
		for(int i=1; i <= clat.getTerm(); i++){
			if(i ==1)
				lp[i] = payments[i];
			else
				lp[i] = (lp[i-1] * 1.06) + payments[i];
			
		}
		lvd.setNetworth(lp);
		cashFlow.add(lvd);
	}

	public AssetList getAList() {
		return aList;
	}

	public Clat getClat() {
		return clat;
	}

	public LifeTool getLifeInsurance() {
		return lifeInsurance;
	}

	float[] initWidths(int colCount) {
		float w[] = new float[colCount];
		float pageWidth = 11.0f * 72f;

		w[0] = _1_4TH / pageWidth; // Normalize to the page width! A pAGE IS 72
									// * 11 INCHES RO POINTS!
		w[1] = _1_4TH / pageWidth;
		w[2] = _1_4TH / pageWidth;
		w[3] = _1_2TH / pageWidth;

		float colw = (11.0f) / (colCount - 4f);

		for (int i = 4; i < colCount; i++) {
			w[i] = (colw * 72f) / pageWidth;
		}

		return w;
	}

	public void page1() {
		// Page 1
		Rectangle rct;
		// Rectangle rctx;
		//drawBorder(pageIcon, "CLAT");
		float ptSize = 12f;
		String client = userInfo.getFirstName() + " " + userInfo.getLastName();
		drawHeader(client, "The Problem");

		String clatExplanation = "\t Due to your sound financial acumen, your assets provide steady growth and income."
				+ " However, the fair market value plus growth and income not spent continue to add to your estate tax liability.";

		String clatExplanation2 = "\t When one's estate (and the estate tax liability) is growing faster than "
				+ "can be reduced by personal spending and normal (tax free) gifting to one's family, leveraged gifts can be "
				+ "very effective.  This process can reduce the taxable value of the assets being transferred, often resulting in the "
				+ "reduction of taxes.\n\n\n"
				+ "Let's examine a double discount leveraging technique that is often utilized with this circumstance.";

		rct = drawLabel(clatExplanation, prctTop, "GARA.TTF",
				new Color(0, 0, 0), ptSize, LBL_LEFT, LBL_TOP);

		drawLabel(clatExplanation2, prctLRight, "GARA.TTF", new Color(0, 0, 0),
				ptSize, LBL_LEFT, LBL_CENTER);

		// Now we need to display the table
		float left = rct.getLeft() + (rct.getWidth() * .125f);
		float right = rct.getRight() - (rct.getWidth() * .125f);

		rct.setLeft(left);
		rct.setRight(right);
		rct.setTop(rct.getBottom());
		rct.setBottom(prctTop.getBottom());

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
		yrsGrowth = 25;
		// growthRate = clat.getAssetGrowth();
		// incomeRate = clat.getAssetIncome();

		totalValue = clat.getFmv();
		totalTax = 0;

		for (int i = 0; i < 25; i++) {
			double tax = (totalValue * incomeRate) * clat.getTaxRate();
			totalTax += tax;
			totalValue += (totalValue * (growthRate + incomeRate)) - tax;
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

	public void page2() {
		// Draw the heading
		String client = userInfo.getFirstName() + " " + userInfo.getLastName();
		boolean isSingle = true;

		if (userInfo.getGender() != null && userInfo.getGender().equals("B"))
			isSingle = false;

		//drawBorder(0, "CLAT");
		drawHeader(client, "Grantor Charitable Lead Annuity Trust (G-CLAT)");

		// Draw the diagram
		// We will postpone this for a while

		// Draw the process
		String theProcess[];

		if (clat.isUseLLC()) {
			theProcess = new String[] {
					"A Limited Liability Corporation (LLC) is formed with a Managing Member Interest  (MMI) and Member Interests (MI). Assets are then transferred to the LLC.",
					"The LLC obtains Life insurance for the client, and pays the premiums for the insurance.",
					"A Grantor Charitable Lead Annuity Trust (G-CLAT) is also established and MI interests are gifted to the trust. This gift receives a substantial discount from its original value.",
					"The charity(ies) of your choice receive(s) a fixed annual payment for "
							+ Integer.toString(clat.getTerm())
							+ " years from the income generated by the assets transferred to the LLC/G-CLAT.",
							"The Grantor receives an income tax charitable deduction for the payments made to charity(ies). This payment starts at a lower rate and then increases by " +
							percent.format(clat.getAnnuityIncrease()) + " each year during the deration of the trust.",
							"The Grantor pays the income tax on the income generated inside the trust.",
					"At the end of the G-CLAT term, the assets plus any appreciation and non spent income pass estate-tax free to your family." };
		} else {
			theProcess = new String[] {
					"A Family Limited Partnership (FLP) is formed with a 2% General Partnership (GP) interest and 98% Limited Partnership (LP) interest. Assets are then transferred to the FLP.",
					"A Grantor Charitable Lead Annuity Trust (G-CLAT) is also established and LP interests are gifted to the trust. This gift receives a substantial discount from its original value.",
					"The charity(ies) of your choice receive(s) a fixed annual payment for "
							+ Integer.toString(clat.getTerm())
							+ " years from the income generated by the assets transferred to the FLP/GCLAT.",
					"The Grantor receives an income tax charitable deduction for the payments made to charity(ies).",
					"The Grantor pays the income tax on the income generated inside the trust.",
					"At the end of the GCLAT term, the assets plus any appreciation and excess income pass estate-tax free to your family." };
		}

		Rectangle rct = new Rectangle(prctLLeft);
		rct.setTop(rct.getTop() - _1_4TH);
		drawSection(rct, "The Process", theProcess, 1);

		// Benefits
		String benefits[];

		if (clat.isUseLLC()) {
			benefits = new String[] {
					"Valuation discounts are made possible by the LLC. An additional discount is obtained by the G-CLAT.",
					"Only a portion of the full value of the assets is subject to estate/gift taxes.",
					"Full value of the net assets remaining in the G-CLAT is later transferred to the heirs without additional tax.",
					"Excess earnings and growth in the value of the assets add to the benefit for the family.",
					"The substantial tax savings are shared by the heirs and the selected charities.", };
		} else {
			benefits = new String[] {
					"Valuation discounts are made possible by the FLP. An additional discount is obtained by the G-CLAT.",
					"Only a portion of the full value of the assets is subject to estate/gift taxes.",
					"Full value of the net assets remaining in the G-CLAT is later transferred to the heirs without additional tax.",
					"Excess earnings and growth in the value of the assets add to the benefit for the family.",
					"The substantial tax savings are shared by the heirs and the selected charities.", 
					"Early deaths(s) by the grantor(s) do(es) not result in an estate tax."
			};
		}

		rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() - _1_4TH);
		Rectangle r = drawSection(rct, "Benefits of Using this Technique",
				benefits, 1);

		// Side effects
		String s1 = "parents death(s).";

		if (isSingle) {
			s1 = "parent's death";
		}

		String effects[] = new String[] {
				"All income from these assets is directed to charity or retained in the trust.",
				"Heirs lose the step-up in basis of the assets upone the " + s1, };

		rct = new Rectangle(prctLRight);
		rct.setTop(rct.getTop() - (_1_4TH + r.getHeight()));
		r = drawSection(rct,
				"Side effects/Draw backs of using this Technique.", effects, 2);
		newPage();
	}

	public void page3() {
		// Draw Header
		//drawBorder(0, "CLAT");
		String client = userInfo.getFirstName() + " " + userInfo.getLastName();
		drawHeader(client, "Grantor Charitable Lead Annuity Trust Summary");

		// Draw Assumptions
		Rectangle rect = new Rectangle(prctFull);

		PageTable table = new PageTable(writer);
		table.setTableFormat(rect, 5);
		table.setTableFont("arial.TTF");
		table.setTableFontBold("arialbd.TTF");
		table.setFontSize(12);
		float widths[] = { .25f, .20f, .10f, .25f, .20f };
		int alignments[] = { PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_RIGHT,
				PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_LEFT,
				PdfPCell.ALIGN_RIGHT };
		table.setColumnWidths(widths);
		table.setColumnAlignments(alignments);

		String aType1 = "[align=left,border=l][align=right][][align=left][align=right,border=r]";
		String aType2 = "[border=l+b][align=right,border=b][border=b][align=left,border=b][align=right,border=r+b]";

		String cells[][] = {
				{ "Assumptions", "", "", "", "",
						"[colspan=5,align=center,bold=1][][][][]" },
				{ "", "", "", "", "", aType2 },
				{ "Beginning Principal", dollar.format(clat.getFmv()), "",
						"7520 Rate", fullPercent.format(clat.getIrsRate()),
						aType1 },
				{ "Discounted Principal",
						dollar.format(clat.getDiscountValue()), "",
						"Transfer Date", date.format(clat.getIrsDate()), aType1 },
				{ "Discount Rate",
						percent.format(clat.getDiscount()), "", 
						"G-Clat Term ", number.format(clat.getTerm()), aType1},
				{ "Asset Income Rate ",
						percent.format(clat.getAssetIncome()), "", 
						"Remainder Interest ", dollar.format(clat.getRemainderInterest()), aType1},
				{ "Asset Growth Rate", percent.format(clat.getAssetGrowth()), "",
						"Annuity Factor",decimal.format(clat.getAnnuityFactor()), aType1 },
				{ "Life Insurance Premium",
						dollar.format(lifeInsurance.getPremiums()[0]), "",
						"Annuity Payment", dollar.format(clat.getAnnuityPayment()), aType1 },
				{ "Life Ins. Death Benefit", dollar.format(lifeInsurance.getDeathBenefit()[0]), "",
						"Annuity Increase", fullPercent.format(clat.getAnnuityIncrease()), aType1 },
				{ "Charitable Deduction",dollar.format(clat.getCharitableDeduction()), "",
						"Optimal Rate",fullPercent.format(clat.getOptimalRate()), aType2 },
		};

		table.buildTableEx(cells);
		table.drawTable();
		rect = table.getResultRect();

		// Generate the Cash Flow!
		cashFlow2(rect);

		newPage();
	}

	public void page4() {
		// Upper right hand corner we will display the Clat Payments
		// Draw first chart
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String series1 = "G-Clat Payments";

		double payment = clat.getAnnuityPayment();
		for (int i = 0; i < clat.getTerm(); i++) {
			dataSet.addValue(payment, series1, Integer.toString(i));
			payment *= (1 + clat.getAnnuityIncrease());
		}

		Color t1 = new Color(57, 202, 100);
		Color t2 = new Color(100, 100, 136);
		BarChart barChart = new BarChart();
		barChart.setTitle("G-Clat Payments");
		barChart.setDataSet(dataSet);
		Rectangle bRect = new Rectangle(prctULeft);
		barChart.setRect(bRect);
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);
		barChart.generateBarChart();
		barChart.setColor(0, t2);
		Image img = barChart.getBarChartImage();
		drawDiagram(img, bRect, 0, 72);

		// Upper left hand corner we will display the Life Insurance Death Benefit
		dataSet = new DefaultCategoryDataset();
		series1 = "Life Death Benefit";
		
		double life[] = lifeInsurance.getDeathBenefit();
		
		for(int i=0; i<life.length; i++) {
			dataSet.addValue(life[i], series1, Integer.toString(i));
		}
	
		barChart = new BarChart();
		barChart.setTitle("Life Ins. Death Benefit");
		barChart.setDataSet(dataSet);
		bRect = new Rectangle(prctURight);
		barChart.setRect(bRect);
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);
		barChart.generateBarChart();
		barChart.setColor(0, t1);
		barChart.setColor(1, t2);
		img = barChart.getBarChartImage();
		drawDiagram(img, bRect, 0, 72);
		
		// Lower right hand corner we will display the trust networth
		double nw[] = new double[CashFlowData.MAX_ROW];
		
		for(CashFlowData c : cashFlow){
			double x[] = c.getNetworth();
			for(int i=0; i < x.length; i++){
				nw[i] += x[i];
			}
		}

		dataSet = new DefaultCategoryDataset();
		series1 = "LLC Net Worth";
		
		for(int i=0; i<life.length; i++) {
			dataSet.addValue(nw[i], series1, Integer.toString(i));
		}
	
		barChart = new BarChart();
		barChart.setTitle("LLC Net Worth");
		barChart.setDataSet(dataSet);
		bRect = new Rectangle(prctLLeft);
		barChart.setRect(bRect);
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);
		barChart.generateBarChart();
		barChart.setColor(0, t1);
		img = barChart.getBarChartImage();
		drawDiagram(img, bRect, 0, 72);
		
		// Lower left hand corner we will display the Life Cash Value
		dataSet = new DefaultCategoryDataset();
		series1 = "Cash Value";
		
		life = lifeInsurance.getCashValue();
		
		for(int i=0; i<life.length; i++) {
			dataSet.addValue(life[i], series1, Integer.toString(i));
		}
	
		t1 = new Color(0xf38044);
		barChart = new BarChart();
		barChart.setTitle("Life Ins. Cash Value");
		barChart.setDataSet(dataSet);
		bRect = new Rectangle(prctLRight);
		barChart.setRect(bRect);
		barChart.setDomainAxisLabel("Years");
		barChart.setAlpha(1.0f);
		barChart.setLablesOff(true);
		barChart.setUse2D(true);
		barChart.setCategoryMargin(0f);
		barChart.generateBarChart();
		barChart.setColor(0, t1);
		img = barChart.getBarChartImage();
		drawDiagram(img, bRect, 0, 72);
		
		newPage();
	}

	public void page5() {

	}

	public void setAList(AssetList list) {
		aList = list;
	}

	public void setClat(Clat clat) {
		this.clat = clat;
	}

	public void setLifeInsurance(LifeTool lifeInsurance) {
		this.lifeInsurance = lifeInsurance;
	}
}
