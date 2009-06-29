package com.estate.pdf;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * 
 */

public class PageHeader {

	private PdfWriter writer;

	public PageHeader(PdfWriter writer) {
		this.writer = writer;
	}

	public void doGrid(Rectangle rct) {
		PdfContentByte cb = writer.getDirectContent();

		cb.setLineWidth(.5f);
		cb.setRGBColorStroke(192, 192, 192);
		for (float x = 0; x < rct.getRight(); x += 4.5) {
			cb.moveTo(x, 0);
			cb.lineTo(x, rct.getTop());
		}

		for (float y = 0; y < rct.getTop(); y += 4.5) {
			cb.moveTo(0, y);
			cb.lineTo(rct.getRight(), y);
		}

		cb.stroke();

		cb.setRGBColorStroke(255, 0, 192);
		for (float x = 0; x < rct.getRight(); x += 72 * .5) {
			cb.moveTo(x, 0);
			cb.lineTo(x, rct.getTop());
		}

		for (float y = 0; y < rct.getTop(); y += 72 * .5) {
			cb.moveTo(0, y);
			cb.lineTo(rct.getRight(), y);
		}

		cb.stroke();

	}

	public void writeHeader(Rectangle rct, String header, String title) {
		// BaseFont font = PageUtils.font;
		try {
			BaseFont font = BaseFont.createFont(Locations.getFontLocation()
					+ "GARA.TTF", BaseFont.CP1252, BaseFont.EMBEDDED);
			Rectangle rect = new Rectangle(rct);
			rect.setBottom(rct.getTop() - (72 * .6f));
			rect.setLeft(1.75f * 72);
			rect.setRight(rct.getRight() - (72 * .5f));
			HeadingText ht = new HeadingText(this.writer);
			ht.setColor(144, 108, 75);
			ht.setCapsColor(144, 108, 75);
			ht.display(rect, 12f, font, 3f, header, HeadingText.DHT_CENTER);
			rect.setBottom(rect.getBottom() - (72 * .33f));
			ht.setColor(57, 57, 57);
			ht.setCapsColor(98, 98, 98);
			ht.display(rect, 18f, font, 5f, title, HeadingText.DHT_CENTER);

		} catch (DocumentException de) {
			System.out.println(de.getMessage());
		} catch (IOException ioe) {

		}

	}

}
