package com.estate.report;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.estate.pdf.*;
import com.estate.sc.utils.*;
import java.awt.*;
import java.util.*;

public class Scenario extends Page {

	LinkedList<CFRow> rowList = new LinkedList<CFRow>();
	
	boolean sc1 = true;
	
	int clientLE;
	int spouseLE;
	final static int MAX_COLS = 18;
	String heading = "Estimate of Net Worth and Estate Distribution - Scenario 1 As is (in 000's)";
	String heading2 = "Estimate of Net Worth and Estate Distribution - Scenario 2 Recommended (in 000's)";
	
	public Scenario(Document document, PdfWriter writer) {
		super(document, writer);
	}

	public void setTableFontandSize(PageTable table) {
		table.setTableFont("arial.ttf");
		table.setTableFontBold("arialbd.ttf");
		table.setTableFontItalic("ariali.ttf");
		table.setTableFontBoldItalic("arialbi.ttf");
		table.setFontSize(9);
		table.setPaddingBottom(_1_32TH + _1_64TH);
	}
	

	public float[] calcWidths(float baseWidths[], float tableWidth) {
		float widths[] = new float[baseWidths.length];
		for(int i = 0; i < widths.length; i++) {
			widths[i] = baseWidths[i] / tableWidth;
		}
		return widths;
	}

	public Rectangle doRow(String rowInfo[], float widths[], Rectangle rct, int colCount) {
		String row[][] = new String[1][];
		PageTable t = getTable(rct, colCount);
		setTableFontandSize(t);
		t.setColumnWidths(widths);
		row[0] = rowInfo;
		t.buildTableEx(row);
		t.drawTable();
		Rectangle r = t.getResultRect();
		return r;
	}

	public String[] initStringArray(int cols) {
		String a[] = new String[cols];
		for(int i =0; i < cols; i++)
			a[i] = "";
		return a;
	}
	
	public String[] genHeaderStyle(CFRow r) {
		return r.getStyle();
	}
	
	public String genColStyle(CFCol c) {
		return c.getColStyle();
	}
	
	public float[] initWidths(int colCount) {
		float w[] = new float[colCount];
		
		w[0] = _1_4TH;
		w[1] = _1_4TH;
		w[2] = _1_4TH;
		w[3] = 72; // 1 inch
		
		float colw = ((11.f) / (colCount - 4)) * 72;
		for( int i = 4; i < colCount; i++) {
			w[i] = colw;
		}
		return w;
	}
	
	public String[] fiscalYearList() {
		String list[] = initStringArray(MAX_COLS);
		String style = "[colspan=4,align=right,bold=1][][][]";
		list[0] = "End of Fiscal Year #";
		
		for(int i=4; i < (MAX_COLS-2); i++) {
			list[i] = Integer.toString(i-3);
		}
		
		if( userInfo.isSingle()){
			// Single client
			double cAge = userInfo.getClientLifeExpectancy();
			int cYear = (int) Math.floor(cAge);
			if( cYear > 10) {
				list[14] = Integer.toString(cYear);
				list[15] = Integer.toString(userInfo.getFinalDeath());
			} else {
				list[14] = Integer.toString(userInfo.getFinalDeath());
			}
		} else {
			double cAge = userInfo.getClientLifeExpectancy();
			double sAge = userInfo.getSpouseLifeExpectancy();
			int firstYear = (int) Math.floor(Math.min(cAge, sAge));
			int secondYear = (int) Math.floor(Math.max(cAge, sAge));
			int idx = 14;
			if( firstYear > 10) {
				list[idx++] = Integer.toString(firstYear);  
			}
			if( secondYear > 10) {
				list[idx++] = Integer.toString(secondYear);
			}
			list[idx] = Integer.toString(userInfo.getFinalDeath());
		}
		
		for(int i=4; i< (MAX_COLS-1); i++) {
			style += "[bold=1,align=right,ptsize=9]";
		}
		
		list[MAX_COLS -1] = style;
		
		return list;
	}
	
	public String[] leList() {
		String list[] = initStringArray(MAX_COLS);
		StringBuffer style = new StringBuffer("");
		int color = Color.red.getRGB();
		
		for(int i=1; i < MAX_COLS -1; i++) {
			style.append("[align=right,ptsize=7,color=" + Integer.toString(color) +"]");
		}
		
		if(userInfo.getClientLifeExpectancy() > 10) {
			list[13] = Character.toString(userInfo.getClientFirstName().charAt(0));
		} else {
			int idx = (int)Math.floor(userInfo.getClientLifeExpectancy() +3);
			list[idx] = Character.toString(userInfo.getClientFirstName().charAt(0));
		}

		if( !userInfo.isSingle()) {
			if( userInfo.getSpouseLifeExpectancy() > 10) {
				if( userInfo.getSpouseLifeExpectancy() == userInfo.getClientLifeExpectancy()) {
					list[13] += "/" + Character.toString(userInfo.getSpouseFirstName().charAt(0)); 
				} else if( userInfo.getSpouseLifeExpectancy() < userInfo.getClientLifeExpectancy()) {
					list[14] = list[13];	// We already set this above
					list[14] = Character.toString(userInfo.getSpouseFirstName().charAt(0));
				} else {
					list[14] = Character.toString(userInfo.getSpouseFirstName().charAt(0));
				}
			}
		}
		
		list[17] = style.toString();
		return list;
	}
	
	public String[] agesList() {
		String list[] = new String[MAX_COLS];
		
		StringBuffer style = new StringBuffer("[colspan=4,align=right,ptsize=7][][][]");
		int cAge = userInfo.getClientAge();
		int sAge = userInfo.getSpouseAge();
		
		if( !userInfo.isSingle()) {
			list[0] = "Ages: (" + userInfo.getClientFirstName() + "/" +
				userInfo.getSpouseFirstName() + ")";
		} else {
			list[0] = "Age: (" + userInfo.getClientFirstName() + ")";
		}
		
		for(int i=4; i < MAX_COLS -2; i++) {
			int sa = sAge + (i - 4);
			int ca = cAge + (i - 4);
			if(userInfo.isSingle() ) {
				list[i] = Integer.toString(ca);
			} else {
				list[i] = Integer.toString(ca) + "/" + Integer.toString(sa);
			}
			style.append("[ptsize=7,align=right]");
		}
		
		if( userInfo.isSingle()) {
			if( userInfo.getClientLifeExpectancy() > 10) {
				list[14] = Integer.toString(cAge + userInfo.getClientLifeExpectancy()-1);
				style.append("[ptsize=7,align=right]");
			}
			list[15] = Integer.toString(cAge + userInfo.getFinalDeath() -1);
			style.append("[ptsize=7,align=right]");
		} else {
			int cle = userInfo.getClientLifeExpectancy();
			int sle = userInfo.getSpouseLifeExpectancy();
			int f1 = (int) Math.floor(Math.min(cle,sle));
			int f2 = (int) Math.floor(Math.max(cle,sle));
			int idx = 14;
			if( f1 > 10) {
				list[idx++] = Integer.toString(cAge + f1 - 1) + "/" + Integer.toString(sAge + f1 - 1); 
				style.append("[ptsize=7,align=right]");
			}
			
			if( f2 > 10) {
				list[idx++] = Integer.toString(cAge + f2 - 1) + "/" + Integer.toString(sAge + f2 - 1);
				style.append("[ptsize=7,align=right]");
			}
			list[idx] = Integer.toString(cAge + userInfo.getFinalDeath() -1) + "/" +Integer.toString(sAge + userInfo.getFinalDeath() -1);
			style.append("[ptsize=7,align=right]");
		}
		
		list[MAX_COLS-1] = style.toString();
		
		return list;
	}
	
	public Rectangle placeHeading() {
		Rectangle rect = new Rectangle(document.getPageSize());
		BaseFont font = PageUtils.LoadFont("GARA.TTF");
		rect.setBottom(rect.getTop() - (72 * .6f));
		HeadingText ht = new HeadingText(this.writer);
		rect.setBottom(rect.getBottom() - (72 * .33f));
		ht.setColor(57, 57, 57);
		ht.setCapsColor(98, 98, 98);
		if( sc1)
			ht.display(rect, 12f, font, 3f, heading, HeadingText.DHT_CENTER);
		else 
			ht.display(rect, 12f, font, 3f, heading2, HeadingText.DHT_CENTER);
		
		rect.setTop(rect.getBottom() - _1_8TH);
		rect.setBottom(prctFull.getBottom());
		rect.setLeft(_1_2TH);
		rect.setRight(rect.getLeft() + (11.f * 72));
		PageBorder pb = new PageBorder(writer);
		pb.setLicense(userInfo.getPlannerFirstName() + " " + userInfo.getPlannerLastName());
		pb.drawNoBorder(document, Integer.toString(pageNum));
		
		try {
			Rectangle rct = new Rectangle(document.getPageSize());
			float iconBase = (.75f * 72); // This is the base of the icons on
			// the page
			Image icon = Image.getInstance(Locations.getImageLocation()
					+ "pawn.png");
			// 	Image box = Image.getInstance(Locations.ImageLocation() +
			// 	"blueBOX.png");
			icon.scalePercent(23);
			float scale = .23f;
			float iconLeft = (icon.getWidth() / 2) * scale;

			// Adjust the top
			rct.setTop(rct.getTop() );
	
			// Place the Icon
			icon.setAbsolutePosition((.5f * 72) - iconLeft, rct.getTop()
						- iconBase);
			document.add(icon);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		drawLifeExp();
		
		return rect;
	}
	
	public void drawLifeExp() {
		PdfContentByte cb = writer.getDirectContent();
		char c = userInfo.getClientFirstName().charAt(0);
		char s = ' ';
		
		if( !userInfo.isSingle()) {
			s = userInfo.getSpouseFirstName().charAt(0);
		}
		String cText = Character.toString(c) + ":" + userInfo.getClientFirstName()
			+ " actuarial life expectancy - " +
			Integer.toString(userInfo.getClientLifeExpectancy()) + " years";
	
		String sText = Character.toString(s) + ":" + userInfo.getSpouseFirstName()
			+ " actuarial life expectancy - " +
			Integer.toString(userInfo.getSpouseLifeExpectancy()) + " years";
		
		try {
			BaseFont font = BaseFont.createFont(Locations.getFontLocation() 
					+ "times.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);
			cb.beginText();
			cb.setFontAndSize(font, 8);
			cb.setRGBColorFill(255, 0, 0);
			cb.setTextMatrix(72 * .5f, .5f * 72);
			cb.showText(cText);
			cb.endText();
			if( !userInfo.isSingle()){
				cb.beginText();
				cb.setFontAndSize(font, 8);
				cb.setRGBColorFill(255, 0, 0);
				cb.setTextMatrix(72 * .5f, .35f * 72);
				cb.showText(sText);
				cb.endText();
			}
		} catch(Exception e) {
			System.err.println("Error printing Life Expectancy");
		}
	}
	
	public String[] buildRow(CFRow row, int colCount) {
		int i = 0;
		row.reset();
		CFCol col;
		String styleList[] = initStringArray(40);
		String pdfRow[] = initStringArray(colCount);
		String tempRow[] = initStringArray(40);
		StringBuffer style = new StringBuffer("");
		
		String hdr[] = genHeaderStyle(row);
		
		while((col = row.getCol()) != null && i < 40) {
			styleList[i] = genColStyle(col);
			tempRow[i++] = col.getStrValue();
		}
		
		style.append(hdr[4]);
		pdfRow[0] = hdr[0];
		pdfRow[1] = hdr[1];
		pdfRow[2] = hdr[2];
		pdfRow[3] = hdr[3];
		
		i = 4;
		for(int j =0; j < 10; j++) {
			pdfRow[i++] = tempRow[j];
			style.append(styleList[j]);
		}
		
		if( userInfo.isSingle()) {
			if(userInfo.getClientLifeExpectancy() > 10 ) {
				pdfRow[i++] = tempRow[userInfo.getClientLifeExpectancy()-1];
				style.append(styleList[userInfo.getClientLifeExpectancy()-1]);
			}
			pdfRow[i++] = tempRow[userInfo.getFinalDeath()-1];
			style.append(styleList[userInfo.getFinalDeath() -1]);
		} else {
			int cle = userInfo.getClientLifeExpectancy();
			int sle = userInfo.getSpouseLifeExpectancy();
			int f1 = (int) Math.floor(Math.min(cle,sle));
			int f2 = (int) Math.floor(Math.max(cle,sle));
			int idx = i;
			if( f1 > 10) {
				pdfRow[idx++] = tempRow[f1-1];
				style.append(styleList[f1-1]);
			}
			if(f2 > 10) {
				pdfRow[idx++] = tempRow[f2-1];
				style.append(styleList[f2-1]);
			}
			pdfRow[idx] = tempRow[userInfo.getFinalDeath()-1];
			style.append(styleList[userInfo.getFinalDeath() -1]);
		}
		
		pdfRow[colCount-1] = style.toString();
		return pdfRow;
	}
	
	public void page1() {
		float tallest = 0;
		int colCount = MAX_COLS;
		CFRow continueRow = null;
		float baseWidths[] = initWidths(colCount-1);
		float widths[] = calcWidths(baseWidths, 11f*72);
		drawHeader(userInfo.getClientHeading(), "");
		
		Rectangle agt = placeHeading();
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
		
		for(CFRow row : rowList) {
			boolean flag = false;
			if( row.getColNumber() == 0 && row.getIndentLevel() == 0){
				continueRow = row;
			}

			do {
				flag = false;
				String pdfRow[] = buildRow(row, colCount);
				if( row.isNewPage()) {
					newPage();
					drawHeader(userInfo.getClientHeading(), "");
					
					agt = placeHeading();
					//rct = new Rectangle(prctFull);
					rct.setLeft(_1_4TH);
					rct.setRight(rct.getLeft() + 756);
					r = doRow(leList(), widths, agt, 17);
					rct.setTop(r.getBottom());
					r = doRow(agesList(), widths, rct, 17);
					rct.setTop(r.getBottom());
					r = doRow(fiscalYearList(), widths, rct, 17);
					rct.setTop(r.getBottom());

					tallest = tallest < r.getHeight() ? r.getHeight() : tallest;
					if( continueRow != null) {
						if(!row.equals(continueRow)) {
							row = continueRow;
							row.setHeader(row.getHeader() + " (cont.)");
							flag = true;
						}
					}
				}
					
				r = doRow(pdfRow, widths, rct, colCount-1);
				rct.setTop(r.getBottom());
				tallest = tallest < r.getHeight() ? r.getHeight() : tallest;
				
			} while(flag == true);
		}
	}
	
	public Rectangle nextPage(float[] widths) {
		newPage();
		String user = userInfo.getClientHeading();
		drawHeader(user, "");
		Rectangle agt = placeHeading();
		Rectangle rct = new Rectangle(prctFull);
		rct.setLeft(_1_4TH);
		rct.setRight(rct.getLeft() + 756);
		Rectangle r = null;
		r = doRow(leList(), widths, agt, 17);
		rct.setTop(r.getBottom());
		r = doRow(agesList(), widths, rct, 17);
		rct.setTop(r.getBottom());
		rct.setTop(r.getBottom());
		r = doRow(fiscalYearList(), widths, rct, 17);
		rct.setTop(r.getBottom());
		return rct;
	}
	
	public void draw() {
		page1();
	}

	public void setRowList(LinkedList<CFRow> rowList) {
		this.rowList = rowList;
	}

	public boolean isSc1() {
		return sc1;
	}

	public void setSc1(boolean sc1) {
		this.sc1 = sc1;
	}
	
	
}
