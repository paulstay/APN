package com.teag.reports;

import java.awt.Color;

import com.estate.db.DBObject;
import com.estate.pdf.HeadingText;
import com.estate.pdf.Locations;
import com.estate.pdf.PageBorder;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.analysis.CFCol;
import com.teag.analysis.CFRow;
import com.teag.analysis.CashFlowTable;
import com.teag.bean.ClientBean;
import com.teag.bean.PersonBean;
import com.teag.util.Utilities;

/**
 * @author Paul Stay
 * May 2005 
 */
public class CashFlows extends Page {

	private final static int MAX_ROWS = 18;
	private final static int MAX_TABLE = 120;

	private DBObject dbObj = new DBObject();

	private int clientID;

	private int spouseID;

	private int clientLe;

	private int spouseLe;

	private boolean isSingle = false;

	/**
	 * @param document
	 * @param writer
	 */
	public CashFlows(Document document, PdfWriter writer) {
		super(document, writer);
	}

	private String[] agesList() {

		String list[] = this.initStringArray(MAX_ROWS);
		String style = "[colspan=4,align=right,ptsize=7][][][]";
		PersonBean cb = new PersonBean();
		PersonBean sb = new PersonBean();

		cb.setDbObject();
		sb.setDbObject();
		cb.setId(clientID);
		cb.initialize();
		int cAge = Utilities.CalcAge(cb.getBirthDate());
		int sAge = 0;

		if (spouseID == 0)
			isSingle = true;

		if (!isSingle) {
			sb.setId(spouseID);
			sb.initialize();
			sAge = Utilities.CalcAge(sb.getBirthDate());
		}

		if (isSingle) {
			list[0] = "Age (" + userInfo.getClientFirstName() + ")";
		} else {
			list[0] = "Ages: (" + userInfo.getClientFirstName() + "/"
					+ userInfo.getSpouseFirstName() + ")";
		}

		for (int i = 4; i < 16; i++) {
			int sa = sAge + (i - 4)+1;
			int ca = cAge + (i - 4)+1;
			if (isSingle) {
				list[i] = Integer.toString(ca);
			} else {
				list[i] = Integer.toString(ca) + "/" + Integer.toString(sa);
			}
			style += "[ptsize=7,align=right]";
		}
		if (isSingle) {
			int ca = cAge + userInfo.getClientLifeExpectancy();
			list[14] = Integer.toString(ca);
			list[15] = Integer
					.toString(cAge + userInfo.getFinalDeath());
			style += "[ptsize=7,align=right]";
			style += "[ptsize=7,align=right]";
		} else {
			int f1 = (int) Math.floor(Math.min(userInfo
					.getClientLifeExpectancy(), userInfo
					.getSpouseLifeExpectancy()));
			int f2 = (int) Math.floor(Math.max(userInfo
					.getClientLifeExpectancy(), userInfo
					.getSpouseLifeExpectancy()));

			list[14] = Integer.toString(cAge + f1 - 1) + "/"
					+ Integer.toString(sAge + f1 - 1);
			list[15] = Integer.toString(cAge + f2 - 1) + "/"
					+ Integer.toString(sAge + f2 - 1);
			list[16] = Integer.toString(cAge + userInfo.getFinalDeath()) + "/"
					+ Integer.toString(sAge + userInfo.getFinalDeath());
			style += "[ptsize=7,align=right]";
			style += "[ptsize=7,align=right]";
			style += "[ptsize=7,align=right]";

		}

		list[MAX_ROWS - 1] = style;

		return list;
	}

	private String[] buildRow(CFRow row, int colCount) {

		int i = 0;
		row.reset();
		CFCol col;
		String styleList[] = initStringArray(MAX_TABLE);
		String pdfRow[] = initStringArray(colCount);
		String tempRow[] = initStringArray(MAX_TABLE);
		String style;

		String hdr[] = genHeaderStyle(row);

		i = 0;
		while ((col = row.getCol()) != null && i < MAX_TABLE) {
			styleList[i] = genColStyle(col);
			tempRow[i++] = col.getStrValue();

		}

		// Copy the row header info.
		style = hdr[4]; // save the style
		for (i = 0; i < 4; i++) {
			pdfRow[i] = hdr[i];
		}

		int j;
		for (j = 0; j < 10; j++) {
			pdfRow[i++] = tempRow[j];
			style += styleList[j];
		}

		if (!userInfo.isSingle()) {
			int cLe = userInfo.getClientLifeExpectancy();
			int sLe = userInfo.getSpouseLifeExpectancy();
			pdfRow[i++] = tempRow[Math.min(cLe, sLe) - 1];
			pdfRow[i++] = tempRow[Math.max(cLe, sLe) - 1];
			pdfRow[i++] = tempRow[userInfo.getFinalDeath() - 1];
			style += styleList[Math.min(cLe, sLe) - 1];
			style += styleList[Math.max(cLe, sLe) - 1];
			style += styleList[userInfo.getFinalDeath() - 1];
		} else {
			int cLe = userInfo.getClientLifeExpectancy();
			pdfRow[i++] = tempRow[cLe - 1];
			pdfRow[i++] = tempRow[userInfo.getFinalDeath() - 1];
			style += styleList[cLe - 1];
			style += styleList[userInfo.getFinalDeath() - 1];
		}

		pdfRow[colCount - 1] = style;

		return pdfRow;
	}

	private String buildStyleDef(String styles[]) {
		String style = "";

		for (int i = 0; i < styles.length; i++) {
			style += "[" + styles[i] + "]";
		}
		return style;
	}

	private float[] calcWidths(float baseWidths[], float tableWidth) {
		float widths[] = new float[baseWidths.length];

		for (int i = 0; i < widths.length; i++) {
			widths[i] = baseWidths[i] / tableWidth;
		}

		return widths;
	}

	private Rectangle doRow(String rowInfo[], float widths[], Rectangle rct,
			int colCount) {
		String row[][] = new String[1][];

		PageTable t = this.getTable(rct, colCount);

		this.setTableFontandSize(t);
		t.setColumnWidths(widths);
		row[0] = rowInfo;
		t.buildTableEx(row);
		t.drawTable();
		Rectangle r = t.getResultRect();
		return r;
	}

	public void draw() {
		page1();
	}

	private void drawLifeExp() {
		PdfContentByte cb = writer.getDirectContentUnder();
		char c = userInfo.getClientFirstName().charAt(0);
		char s = ' ';
		if( !userInfo.isSingle()) {
			s = userInfo.getSpouseFirstName().charAt(0);
		}
		
		String cText = Character.toString(c) + ": " + userInfo.getClientFirstName()
				+ " actuarial life expectancy - "
				+ userInfo.getClientLifeExpectancy() + " years";
		String sText = Character.toString(s) + ": " + userInfo.getSpouseFirstName()
				+ " actuarial life expectancy - "
				+ userInfo.getSpouseLifeExpectancy() + " years";
		try {
			BaseFont font = BaseFont.createFont(Locations.getFontLocation()
					+ "times.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);

			cb.beginText();
			cb.setFontAndSize(font, 8);
			cb.setRGBColorFill(255, 0, 0);
			cb.setTextMatrix(72 * .5F, .5f * 72);
			cb.showText(cText);
			cb.endText();
			if (!userInfo.isSingle()) {
				cb.beginText();
				cb.setFontAndSize(font, 8);
				cb.setRGBColorFill(255, 0, 0);
				cb.setTextMatrix(72 * .5F, .35f * 72);
				cb.showText(sText);
				cb.endText();
			}
		} catch (Exception e) {
			System.err.println("Error printing Life Expectancy");
		}
	}

	private String[] fiscalYearList() {
		String list[] = this.initStringArray(MAX_ROWS);
		String style = "[colspan=4,align=right,bold=1][][][]";
		list[0] = "End of Fiscal Year #";

		for (int i = 4; i < 16; i++) {
			list[i] = Integer.toString(i - 3);
		}

		// Need to adjust for Life Expectancy

		if (!userInfo.isSingle()) {
			double cAge = userInfo.getClientLifeExpectancy();
			double sAge = userInfo.getSpouseLifeExpectancy();
			list[14] = Integer.toString((int) Math.floor(Math.min(sAge, cAge)));
			list[15] = Integer.toString((int) Math.floor(Math.max(sAge, cAge)));
			list[16] = Integer.toString(userInfo.getFinalDeath());
		} else {
			double cAge = userInfo.getClientLifeExpectancy();
			list[14] = Integer.toString((int) cAge);
			list[15] = Integer.toString(userInfo.getFinalDeath());
		}

		for (int i = 4; i < 17; i++) {
			style += "[bold=1,align=right,ptsize=9]";
		}

		list[17] = style;

		return list;
	}

	private String genColStyle(CFCol col) {
		float basePTSize = 9;
		float ptSize;
		Color c;
		String style = "";

		String comma = "";

		if (col.getSpan() > 1) {
			style = "colspan=" + col.getSpan();
			comma = ",";
		}

		if (col.getFontSize() > 0) {
			ptSize = ((.1f * col.getFontSize()) + 1) * basePTSize;
			style += comma + "ptsize=" + ptSize;
			comma = ",";
		}
		if (col.getFontWeight() > 0) {
			style += comma + "bold=1";
			comma = ",";
		}

		if (col.getFontColor() != null) {
			c = col.getFontColor();

			style += comma + "color=" + c.getRGB();
			comma = ",";
		}
		int a = col.getTextFill();
		switch (a) {
		case 1: // left
			style += comma + "align=left";
			comma = ",";
			break;
		case 2: // center
			style += comma + "align=center";
			comma = ",";
			break;
		case 3: // right
			style += comma + "align=right";
			comma = ",";
			break;
		}

		style = "[" + style + "]";
		return style;

	}

	private String[] genHeaderStyle(CFRow row) {
		float basePTSize = 9;
		float ptSize;
		Color c;
		String style = "";
		String styleList[] = initStringArray(4);
		String headerInf[] = { "", "", "", "", "Style" };
		String comma = "";
		int col = row.getIndentLevel();

		if (row.getColNumber() > 0) {

			if (col < 3) {
				style = "colspan=" + (4 - col);
				comma = ",";
			}
		} else {
			style = "colspan=10";
			comma = ",";

		}
		headerInf[col] = row.getHeader();

		if (row.getFontSize() > 0) {
			ptSize = ((.1f * row.getFontSize()) + 1) * basePTSize;
			style += comma + "ptsize=" + ptSize;
			comma = ",";
		}
		if (row.getFontWeight() > 0) {
			style += comma + "bold=1";
			comma = ",";
		}

		if (row.getFontColor() != null) {
			c = row.getFontColor();

			style += comma + "color=" + c.getRGB();
			comma = ",";
		}

		styleList[col] = style;

		headerInf[4] = buildStyleDef(styleList);

		return headerInf;

	}

	/**
	 * @return Returns the clientID.
	 */
	public int getClientID() {
		return clientID;
	}

	/**
	 * @return Returns the clientLe.
	 */
	public int getClientLe() {
		return clientLe;
	}

	/**
	 * @return Returns the dbObj.
	 */
	public DBObject getDbObj() {
		return dbObj;
	}

	/**
	 * @return Returns the spouseID.
	 */
	public int getSpouseID() {
		return spouseID;
	}

	/**
	 * @return Returns the spouseLe.
	 */
	public int getSpouseLe() {
		return spouseLe;
	}

	private String[] initStringArray(int colCount) {
		String a[] = new String[colCount];
		for (int i = 0; i < a.length; i++) {
			a[i] = "";
		}

		return a;
	}

	private float[] initWidths(int colCount) {
		float w[] = new float[colCount];

		w[0] = _1_4TH;
		w[1] = _1_4TH;
		w[2] = _1_4TH;
		w[3] = 72; // 1 inch.

		float colw = (11.f) / (colCount - 4);

		for (int i = 4; i < colCount; i++) {
			w[i] = colw * 72;
		}

		return (w);

	}

	private String[] leList() {
		String list[] = initStringArray(18);
		String style;
		Color red = Color.RED;
		int color = red.getRGB();
		if (userInfo.isSingle()) {
			list[13] = Character.toString(userInfo.getClientFirstName().charAt(0));
			style = "[colspan=4,align=right,ptsize=7][][][][][][][][][][][][]"
					+ "[ptsize=7,align=right,color=" + Integer.toString(color)
					+ "][][][]";

		} else {
			String p1;
			String p2;
			if (userInfo.getClientLifeExpectancy() <= userInfo
					.getSpouseLifeExpectancy()) {
				p1 = Character.toString(userInfo.getClientFirstName().charAt(0));
				p2 = Character.toString(userInfo.getSpouseFirstName().charAt(0));
			} else {
				p1 = Character.toString(userInfo.getSpouseFirstName().charAt(0));
				p2 = Character.toString(userInfo.getClientFirstName().charAt(0));
			}
			list[13] = p1;
			list[14] = p2;
			style = "[colspan=4,align=right,ptsize=7][][][][][][][][][][][][]"
					+ "[ptsize=7,align=right,color=" + Integer.toString(color)
					+ "]" + "[ptsize=7,align=right,color="
					+ Integer.toString(color) + "][][]";
		}
		list[17] = style;

		return list;
	}

	private void page1() {
		CashFlowTable cft = new CashFlowTable();
		ClientBean cb = new ClientBean();
		CFRow row;
		CFRow continueRow = null;
		float tallest = 0;

		cb.setPrimaryId(clientID);

		cft.setCb(cb);
		cft.init();
		cft.genTable();

		cft.reset();
		int colCount = MAX_ROWS;

		drawHeader(userInfo.getClientHeading(), "");
		Rectangle agt = placeHeading();

		float baseWidths[] = this.initWidths(colCount - 1);
		float widths[] = this.calcWidths(baseWidths, 11.f * 72);

		Rectangle rct = new Rectangle(prctFull);
		rct.setLeft(_1_4TH);
		rct.setRight(rct.getLeft() + 756);

		Rectangle r = doRow(leList(), widths, agt, 17);
		rct.setTop(r.getBottom());
		r = doRow(agesList(), widths, rct, 17);

		rct.setTop(r.getBottom());

		r = doRow(fiscalYearList(), widths, rct, 17);
		rct.setTop(r.getBottom());

		tallest = tallest < r.getHeight() ? r.getHeight() : tallest;

		while ((row = cft.getRow()) != null) {
			boolean flag;
			if (row.getColNumber() == 0 && row.getIndentLevel() == 0) {
				continueRow = row;
			}

			do {
				flag = false;
				boolean nPage = false;
				String pdfRow[] = buildRow(row, colCount);
				if (row.getHeader().startsWith("III.")
						&& !row.getHeader().endsWith("(cont.)")) {
					nPage = true;
				}
				if (row.getHeader().startsWith("IV.")
						&& !row.getHeader().endsWith("(cont.)")) {
					nPage = true;
				}

				// Rectangle r = doSimpleRow(pdfRow, rct);
				if (nPage) {
					// need to span to a new page
					newPage();
					drawHeader(client, "");
					agt = placeHeading();
					rct.setLeft(_1_4TH);
					rct.setRight(rct.getLeft() + 756);

					r = doRow(leList(), widths, agt, 17);
					rct.setTop(r.getBottom());
					r = doRow(agesList(), widths, rct, 17);
					rct.setTop(r.getBottom());
					r = doRow(fiscalYearList(), widths, rct, 17);
					rct.setTop(r.getBottom());
					tallest = tallest < r.getHeight() ? r.getHeight() : tallest;
					if (continueRow != null) {
						if (!row.equals(continueRow)) {
							row = continueRow;
							row.setHeader(row.getHeader() + " (cont.)");
							flag = true;

						}
					}
					nPage = false;
				}

				r = this.doRow(pdfRow, widths, rct, colCount - 1);
				tallest = tallest < r.getHeight() ? r.getHeight() : tallest;
				rct.setTop(r.getBottom());

				if (rct.getTop() < (.5f * 72) + tallest) {
					// need to span to a new page
					newPage();
					drawHeader(client, "");
					agt = placeHeading();

					rct.setLeft(_1_4TH);
					rct.setRight(rct.getLeft() + 756);

					r = doRow(leList(), widths, agt, 17);
					rct.setTop(r.getBottom());
					r = doRow(agesList(), widths, rct, 17);
					rct.setTop(r.getBottom());
					r = doRow(fiscalYearList(), widths, rct, 17);
					rct.setTop(r.getBottom());
					tallest = tallest < r.getHeight() ? r.getHeight() : tallest;

					if (continueRow != null) {
						if (!row.equals(continueRow)) {
							row = continueRow;
							row.setHeader(row.getHeader() + " (cont.)");
							flag = true;
						}
					}
				}
			} while (flag == true);
		}
		cft.cleanUp();
	}

	private Rectangle placeHeading() {

		String header = "Estimate Of Net Worth & Estate Distribution - Scenario #1 As Is (in 000's)";
		BaseFont font = PageUtils.LoadFont("GARA.TTF");

		Rectangle rect = new Rectangle(document.getPageSize());
		rect.setBottom(rect.getTop() - (72 * .6f));
		HeadingText ht = new HeadingText(this.writer);
		rect.setBottom(rect.getBottom() - (72 * .33f));
		ht.setColor(57, 57, 57);
		ht.setCapsColor(98, 98, 98);
		ht.display(rect, 12f, font, 3f, header, HeadingText.DHT_CENTER);
		rect.setTop(rect.getBottom() - _1_8TH);
		rect.setBottom(prctFull.getBottom());
		rect.setLeft(_1_2TH);
		rect.setRight(rect.getLeft() + 11.f * 72);
		PageBorder pb = new PageBorder(writer);
		pb.setLicense(userInfo.getPlannerFirstName() + " "
				+ userInfo.getPlannerLastName());
		pb.drawNoBorder(document, Integer.toString(pageNum));
		drawLifeExp();
		return (rect);
	}

	/**
	 * @param clientID
	 *            The clientID to set.
	 */
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	/**
	 * @param clientLe
	 *            The clientLe to set.
	 */
	public void setClientLe(int clientLe) {
		this.clientLe = clientLe;
	}

	/**
	 * @param spouseID
	 *            The spouseID to set.
	 */
	public void setSpouseID(int spouseID) {
		this.spouseID = spouseID;
	}

	/**
	 * @param spouseLe
	 *            The spouseLe to set.
	 */
	public void setSpouseLe(int spouseLe) {
		this.spouseLe = spouseLe;
	}

	private void setTableFontandSize(PageTable table) {
		table.setTableFont("arial.ttf");
		table.setTableFontBold("arialbd.ttf");
		table.setTableFontItalic("ariali.ttf");
		table.setTableFontBoldItalic("arialbi.ttf");
		table.setFontSize(8);
		table.setPaddingBottom(_1_32TH + _1_64TH);

	}
}
