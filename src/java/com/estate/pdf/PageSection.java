package com.estate.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * 
 */
public class PageSection {

	public static final int BT_NUMBER = 1;

	public static final int BT_BULLET = 2;

	private PdfWriter writer;

	private float headingSize = 11f;

	private float textSize = 9.5f;

	private boolean flag = false; // Used to calculate rects

	public PageSection(PdfWriter writer) {
		this.writer = writer;
	}

	public Rectangle calcSectionRect(Rectangle rct, String header,
			String lines[], int bulletType) {
		Rectangle resRect;
		flag = true;
		resRect = displaySection(rct, header, lines, bulletType);
		flag = false;
		return resRect;
	}

	/***************************************************************************
	 * displaySection
	 * 
	 * @param rct
	 * @param header
	 * @param lines
	 * @param bulletType
	 * @return
	 */
	public Rectangle displaySection(Rectangle rct, String header,
			String lines[], int bulletType) {

		float indent;
		// Copy to a work rect.
		Rectangle rect = new Rectangle(rct);
		// Laod our base font.
		BaseFont headingFont = PageUtils.LoadFont("GARABD.TTF");
		BaseFont font = PageUtils.LoadFont("GARA.TTF");
		// Create a hi level font, we use this for line spacing info.
		Font fnt = new Font(font, textSize);
		Font fntHeading = new Font(font, headingSize);
		Paragraph p = new Paragraph("Hy", fnt);
		Paragraph ph = new Paragraph("Hy", fntHeading);
		p.setLeading(p.getLeading() * .80f);

		PdfContentByte cb = writer.getDirectContent();
		// Now we set the bottom

		if (header.length() > 0) {
			rect.setBottom(rect.getTop() - font.getAscentPoint("H", headingSize));
			// Create a Heading Text object

			HeadingText ht = new HeadingText(this.writer);

			// set up the colors for the heading text.
			ht.setColor(98, 98, 98); // Magic Numbers.....
			ht.setCapsColor(98, 98, 98); // Magic Numbers....
			// Display our header for the section
			if (flag == false) {
				ht.display(rect, headingSize, headingFont, 2f, header,
						HeadingText.DHT_LEFT);
			}
			rect.setBottom(rect.getBottom() - ph.getLeading());
		} else {
			rect.setBottom(rect.getTop());
		}

		// Now we will display the sections text.
		cb.beginText();
		cb.setFontAndSize(font, textSize);
		cb.setRGBColorFill(32, 32, 32);

		String num = "";

		switch (bulletType) {

		case 1: {
			num = "" + lines.length + ".";
			break;

		}
		case 2: {
			char uni = 0x2022;
			num = "" + uni;
			break;
		}

		}
		indent = font.getWidthPoint(num, textSize) + Page._1_32TH; // get size
																	// of indent

		if (lines.length == 0) {
			rect.setBottom(rect.getTop() - ph.getLeading());
		}

		for (int i = 0; i < lines.length; i++) {
			String formatted[];

			switch (bulletType) {
			case 1: {
				num = "" + (i + 1) + ".";
				break;

			}
			case 2: {
				char uni = 0x2022;
				num = "" + uni;
				break;
			}

			}
			cb.setTextMatrix(rect.getLeft(), rect.getBottom());
			if (flag == false) {
				cb.showText(num);
			}
			cb.setTextMatrix(rect.getLeft() + indent, rect.getBottom());
			formatted = PageUtils.formatToWidth(rect.getWidth() - indent,
					lines[i], font, textSize);
			if (formatted.length == 0) {
				rect.setBottom(rect.getTop() - ph.getLeading());
			}
			for (int ii = 0; ii < formatted.length; ii++) {
				if (flag == false) {
					cb.showText(formatted[ii]);
				}
				rect.setBottom(rect.getBottom() - p.getLeading()); // 10.5
				cb.setTextMatrix(rect.getLeft() + indent, rect.getBottom());
			}

			rect.setBottom(rect.getBottom() - 4f); // Add 4 points to space the
												// bullet lines
		}
		cb.endText();
		return (rect); // Return the rect that was displayed to.
	}

	/**
	 * @return Returns the headingSize.
	 */
	public float getHeadingSize() {
		return headingSize;
	}

	/**
	 * @return Returns the textSize.
	 */
	public float getTextSize() {
		return textSize;
	}

	/**
	 * @param headingSize
	 *            The headingSize to set.
	 */
	public void setHeadingSize(float headingSize) {
		this.headingSize = headingSize;
	}

	/**
	 * @param textSize
	 *            The textSize to set.
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}
}
