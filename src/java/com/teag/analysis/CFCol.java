package com.teag.analysis;

import java.awt.Color;
import java.awt.Font;

/**
 * @author stay
 * Created on May 25, 2005
 */

public class CFCol {
	private String strValue;
	private int indentLevel = 0;
	private Color backgroundColor = Color.WHITE;
	private int span = 1;
	private int textFill = 0;  // 0 = left, 1 = center, 2 = right
	
	// Font characteristics
	private String fontfamily = "serif";
	private int fontSize = 0;
	private Color fontColor = Color.black;
	private int fontWeight = Font.PLAIN;
	
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
	 * @return Returns the span.
	 */
	public int getSpan() {
		return span;
	}
	/**
	 * @param span The span to set.
	 */
	public void setSpan(int span) {
		this.span = span;
	}
	/**
	 * @return Returns the strValue.
	 */
	public String getStrValue() {
		return strValue;
	}
	/**
	 * @param strValue The strValue to set.
	 */
	public void setStrValue(String strValue) {
		this.strValue = strValue;
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
}
