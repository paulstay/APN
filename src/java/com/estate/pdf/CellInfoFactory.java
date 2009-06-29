package com.estate.pdf;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;

/**
 * @author Paul Stay
 * 
 */
public class CellInfoFactory {

	private Font defaultFont = FontFactory.getFont(FontFactory.HELVETICA, 14,
			Font.NORMAL);
	private int defaultHorizAlignment = -1;
	private int defaultVertAlignment = Element.ALIGN_BOTTOM;
	private int defaultBorder = 0;

	public CellInfo getCellInfo(String text) {
		return (getCellInfo(text, defaultHorizAlignment, defaultFont,
				defaultVertAlignment));
	}

	public CellInfo getCellInfo(String text, Font font) {

		return (getCellInfo(text, defaultHorizAlignment, font,
				defaultVertAlignment));
	}

	public CellInfo getCellInfo(String text, Font font, int vAlign) {
		return (getCellInfo(text, defaultHorizAlignment, font, vAlign));
	}

	public CellInfo getCellInfo(String text, int hAlign) {
		return (getCellInfo(text, hAlign, defaultFont, defaultVertAlignment));
	}

	public CellInfo getCellInfo(String text, int hAlign, Font font) {
		return (getCellInfo(text, hAlign, font, defaultVertAlignment));
	}

	public CellInfo getCellInfo(String text, int hAlign, Font font, int vAlign) {
		CellInfo ci = new CellInfo();
		ci.horizAlignment = hAlign;
		ci.vertAlignment = vAlign;
		ci.border = defaultBorder;
		ci.font = font;
		ci.text = text;
		ci.colSpan = 0;
		return (ci);
	}

	public CellInfo getCellInfo(String text, int hAlign, int vAlign) {
		return (getCellInfo(text, hAlign, defaultFont, vAlign));
	}

	/**
	 * @return Returns the defaultAlignment.
	 */
	public int getDefaultAlignment() {
		return defaultHorizAlignment;
	}

	/**
	 * @return Returns the defaultFont.
	 */
	public Font getDefaultFont() {
		return defaultFont;
	}

	/**
	 * @param defaultAlignment
	 *            The defaultAlignment to set.
	 */
	public void setDefaultAlignment(int defaultAlignment) {
		this.defaultHorizAlignment = defaultAlignment;
	}

	/**
	 * @param defaultFont
	 *            The defaultFont to set.
	 */
	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}
}
