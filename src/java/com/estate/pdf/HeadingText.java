package com.estate.pdf;

/**
 * @author Paul Stay
 *
 */

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class HeadingText {
	public static final int DHT_LEFT = 1;
	public static final int DHT_CENTER = 2;
	public static final int DHT_RIGHT = 3;
	private PdfWriter writer;
	private int capsRed;
	private int capsGreen;
	private int capsBlue;

	private int red;
	private int green;
	private int blue;

	public HeadingText(PdfWriter writer) {
		this.writer = writer;
	}

	private float _CalcWidth(float fontsize, BaseFont font, float spacing,
			String text) {
		float width = 0;
		String txt;

		for (int x = 0; x < text.length(); x++) {
			txt = text.substring(x, x + 1).toUpperCase();
			if (txt.equals(text.substring(x, x + 1))) { // Was upper case
				width += font.getWidthPoint(txt, fontsize) + spacing;
			} else { // was lowercase
				width += font.getWidthPoint(txt, fontsize * .7f) + spacing;
			}
		}

		return (width);
	}

	public void display(Rectangle rct, float fontsize, BaseFont font,
			float spacing, String text, int align) {

		PdfContentByte cb = writer.getDirectContent();

		String txt;
		float xp = 0;
		float fs = fontsize;
		// We must calculate the width of the string;
		float width = this._CalcWidth(fontsize, font, spacing, text);
		// Now we get the left position
		if (align == HeadingText.DHT_RIGHT) {
			xp = rct.getRight() - width;
		} else if (align == HeadingText.DHT_LEFT) {
			xp = rct.getLeft();

		} else {
			xp = (((rct.getRight() - rct.getLeft()) - width) / 2) + rct.getLeft();
		}
		cb.beginText();

		// Interate through each character
		for (int x = 0; x < text.length(); x++) {
			cb.setTextMatrix(xp, rct.getBottom()); // Set the position
			txt = text.substring(x, x + 1).toUpperCase(); // Get the character
															// and make it upper
															// case
			if (txt.equals(text.substring(x, x + 1))) // Was it upper case
														// originaly?
			{ // Ok, the chracter was upper case
				fs = fontsize;
				cb.setRGBColorFill(capsRed, capsGreen, capsBlue);
				cb.setFontAndSize(font, fs);
			} else { // Character was lower case.
				fs = fontsize * .7f;
				cb.setRGBColorFill(red, green, blue);
				cb.setFontAndSize(font, fs);

			}
			cb.showText(txt);

			xp = xp + font.getWidthPoint(txt, fs) + spacing; // Move along to
																// the next
																// position

		}

		cb.endText();

	}

	public void setCapsColor(int red, int green, int blue) {
		this.capsRed = red;
		this.capsGreen = green;
		this.capsBlue = blue;
	}

	public void setColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
}
