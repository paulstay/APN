package com.estate.sc.utils;

/**
 * @author Paul Stay
 * Created on March 6, 2007
 */

import java.util.*;
import java.awt.Color;
import java.awt.Font;

public class CFRow {
	private ArrayList<CFCol> cols = new ArrayList<CFCol>();
	private Iterator itr;
	private String header;
	private int indentLevel = 0;
	private Color backgroundColor = Color.WHITE;
	private int textFill = 0;  // 0 = left, 1 = center, 2 = right
	private boolean newPage = false;
	
	private boolean barTop = false;
	private boolean barBottom = false;
	
	// Font characteristics
	private String fontfamily = "serif";
	private int fontSize = 0;
	private Color fontColor = Color.black;
	private int fontWeight = Font.PLAIN;

	public CFRow() {
		cols.clear();
	}
	
	public void cleanUp() {
		cols.clear();
	}
	
	public void reset() {
		itr = cols.iterator();
	}
	
	public CFCol getCol() {
		CFCol col = null;
		if( itr.hasNext()) {
			col = (CFCol) itr.next();
		}
		return col;
	}
	
	public int getColNumber() {
		return cols.size();
	}
	
	public Font getFont() {
		Font font = new Font(fontfamily, fontWeight, fontSize);
		return font;
	}
	
	/**
	 * @return Returns the backgroundColor.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	/**
	 * @param backgroundColor The backgroundColor to set.
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	/**
	 * @return Returns the fontColor.
	 */
	public Color getFontColor() {
		return fontColor;
	}
	/**
	 * @param fontColor The fontColor to set.
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	/**
	 * @return Returns the fontfamily.
	 */
	public String getFontfamily() {
		return fontfamily;
	}
	/**
	 * @param fontfamily The fontfamily to set.
	 */
	public void setFontfamily(String fontfamily) {
		this.fontfamily = fontfamily;
	}
	/**
	 * @return Returns the fontSize.
	 */
	public int getFontSize() {
		return fontSize;
	}
	/**
	 * @param fontSize The fontSize to set.
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	/**
	 * @return Returns the fontWeight.
	 */
	public int getFontWeight() {
		return fontWeight;
	}
	/**
	 * @param fontWeight The fontWeight to set.
	 */
	public void setFontWeight(int fontWeight) {
		this.fontWeight = fontWeight;
	}
	/**
	 * @return Returns the header.
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header The header to set.
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	/**
	 * @return Returns the indentLevel.
	 */
	public int getIndentLevel() {
		return indentLevel;
	}
	/**
	 * @param indentLevel The indentLevel to set.
	 */
	public void setIndentLevel(int indentLevel) {
		this.indentLevel = indentLevel;
	}
	/**
	 * @return Returns the textFill.
	 */
	public int getTextFill() {
		return textFill;
	}
	/**
	 * @param textFill The textFill to set.
	 */
	public void setTextFill(int textFill) {
		this.textFill = textFill;
	}
	/**
	 * @return Returns the barBottom.
	 */
	public boolean isBarBottom() {
		return barBottom;
	}
	/**
	 * @param barBottom The barBottom to set.
	 */
	public void setBarBottom(boolean barBottom) {
		this.barBottom = barBottom;
	}
	/**
	 * @return Returns the barTop.
	 */
	public boolean isBarTop() {
		return barTop;
	}
	/**
	 * @param barTop The barTop to set.
	 */
	public void setBarTop(boolean barTop) {
		this.barTop = barTop;
	}
	
	public void addCol(CFCol c) {
		cols.add(c);
	}
	
	public String[] getStyle() {
		float basePTSize = 9;
		float ptSize;
		StringBuffer style = new StringBuffer("");
		String headerInfo[] = {"","","","","Style"};
		String styleList[] = {"","","",""};
		String comma = "";
		
		int col = getIndentLevel();

		headerInfo[col] = getHeader();

		if( getColNumber() > 0) {
			if(col < 3 ) {
				style.append("colspan=" + Integer.toString((4 -col)));
				comma = ",";
			}
		} else {
			style.append("colspan=10");
			comma = ",";
		}
		
		if( getFontSize() > 0) {
			ptSize = ((.1f * getFontSize()) + 1) * basePTSize;
			style.append(comma + "ptsize=" + Float.toString(ptSize));
			comma = ",";
		}
		
		if( getFontWeight() > 0) {
			if(getFontWeight() == Font.BOLD)
				style.append(comma + "bold=1");

			if(getFontWeight() == Font.ITALIC)
				style.append(comma + "italic=1");
				
			comma = ",";
		}
		
		if( getFontColor() != null) {
			style.append(comma + "color=" + Integer.toString(getFontColor().getRGB()));
			comma = ",";
		}
		
		styleList[col] = style.toString();
		headerInfo[4] = this.buildStyleList(styleList);
		
		return headerInfo;
	}
	
	public String buildStyleList(String style[]) {
		String s = "";
		for(int i=0; i < style.length; i++) {
			s += "[" + style[i] + "]";
		}
		return s;
	}

	public boolean isNewPage() {
		return newPage;
	}

	public void setNewPage(boolean newPage) {
		this.newPage = newPage;
	}
	
	public void info() {
		System.out.println("CFROW : " + this.getHeader());
		System.out.println("\tcols : " + this.getColNumber());
	}
}
