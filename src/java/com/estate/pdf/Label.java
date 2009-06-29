package com.estate.pdf;

import java.awt.Color;

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
public class Label {
	public static final int LBL_LEFT = 1;
	public static final int LBL_CENTER = 2;
	public static final int LBL_RIGHT = 3;
	public static final int LBL_TOP = 4;
	public static final int LBL_BOTTOM = 5;
	public static final int LBL_FIRST = 6;
	private PdfWriter writer;
	private float leadingAdjust = .80f;

	public Label(PdfWriter writer) {
		this.writer = writer;
	}

	public Rectangle displayLabel(Rectangle rct, String label, BaseFont font,
			float ptSize, Color color, int align, int anchor) {
		String formatted[];
		float lft = 0;
		PdfContentByte cb = writer.getDirectContent();
		// Set the font and size
		cb.beginText();
		cb.setFontAndSize(font, ptSize);
		cb.setColorFill(color);
		Font fnt = new Font(font, ptSize);
		Paragraph p = new Paragraph("Hy", fnt);
		p.setLeading(p.getLeading() * leadingAdjust);
		float baseLine;

		formatted = PageUtils.formatToWidth(rct.getRight() - rct.getLeft(), label,
				font, ptSize);

		if (formatted.length == 0) {
			return null;
		}
		if (anchor == LBL_BOTTOM) {
			// Build from the bottom up
			baseLine = rct.getBottom();
			for (int i = formatted.length - 1; i > -1; i--) {
				float width;
				switch (align) {
				case LBL_LEFT:
					lft = rct.getLeft();
					break;
				case LBL_CENTER:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = (((rct.getRight() - rct.getLeft()) - width) / 2)
							+ rct.getLeft();
					break;
				case LBL_RIGHT:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = rct.getRight() - width;
					break;
				}

				cb.setTextMatrix(lft, baseLine);
				cb.showText(formatted[i]);

				baseLine += p.getLeading();
			}
			cb.endText();
			rct.setTop(baseLine);
		} else if (anchor == LBL_TOP) {
			// Build from the top down
			baseLine = (rct.getTop() - font.getAscentPoint(formatted[0], ptSize));
			for (int i = 0; i < formatted.length; i++) {
				float width;
				switch (align) {
				case LBL_LEFT:
					lft = rct.getLeft();
					break;
				case LBL_CENTER:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = (((rct.getRight() - rct.getLeft()) - width) / 2)
							+ rct.getLeft();
					break;
				case LBL_RIGHT:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = rct.getRight() - width;
					break;
				}

				cb.setTextMatrix(lft, baseLine);
				cb.showText(formatted[i]);

				baseLine -= p.getLeading();
			}

			rct.setBottom(baseLine);
			cb.endText();

		} else if (anchor == LBL_CENTER) {
			// Need to calc the height of the text.
			float height = p.getLeading() * formatted.length;
			float top = rct.getTop() - ((rct.getHeight() - height) / 2);
			Rectangle tempRct = new Rectangle(rct.getLeft(), top - height, rct
					.getRight(), top);
			// Build from the bottom up
			baseLine = tempRct.getBottom();
			for (int i = formatted.length - 1; i > -1; i--) {
				float width;
				switch (align) {
				case LBL_LEFT:
					lft = tempRct.getLeft();
					break;
				case LBL_CENTER:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = (((tempRct.getRight() - tempRct.getLeft()) - width) / 2)
							+ tempRct.getLeft();
					break;
				case LBL_RIGHT:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = tempRct.getRight() - width;
					break;
				}

				cb.setTextMatrix(lft, baseLine);
				cb.showText(formatted[i]);

				baseLine += p.getLeading();
			}
			cb.endText();
			rct = tempRct;

		} else if (anchor == LBL_FIRST) {
			// Build from the top down
			baseLine = rct.getBottom();
			rct.setTop(baseLine + font.getAscentPoint("H", ptSize));
			for (int i = 0; i < formatted.length; i++) {
				float width;
				switch (align) {
				case LBL_LEFT:
					lft = rct.getLeft();
					break;
				case LBL_CENTER:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = (((rct.getRight() - rct.getLeft()) - width) / 2)
							+ rct.getLeft();
					break;
				case LBL_RIGHT:
					width = font.getWidthPoint(formatted[i], ptSize);
					lft = rct.getRight() - width;
					break;
				}

				cb.setTextMatrix(lft, baseLine);
				cb.showText(formatted[i]);

				// rct.setBottom(rct.getBottom() - p.getLeading());
				baseLine -= p.getLeading();
			}
			cb.endText();
			rct.setBottom(baseLine + p.getLeading());
		}

		return (new Rectangle(rct));
	}

	/**
	 * @return Returns the leadingAdjust.
	 */
	public float getLeadingAdjust() {
		return leadingAdjust;
	}

	/**
	 * @param leadingAdjust
	 *            The leadingAdjust to set.
	 */
	public void setLeadingAdjust(float leadingAdjust) {
		this.leadingAdjust = leadingAdjust;
	}
}
