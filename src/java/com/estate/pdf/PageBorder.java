package com.estate.pdf;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * 
 */
public class PageBorder {

	private PdfWriter writer;

	private String icons[] = { "queen.png", "pawn.png", "knight.png",
			"castle.png", "king.png" };

	private String license = "";

	public PageBorder(PdfWriter writer) {
		this.writer = writer;
	}

	public void draw(Document doc, int iconNum, String pageNum) {
		draw(doc, iconNum, pageNum, "");
	}

	public void draw(Document doc, int iconNum, String pageNum, String toolName) {
		try {
			PdfContentByte cb = writer.getDirectContentUnder();
			BaseFont fontBold = BaseFont.createFont(Locations.getFontLocation()
					+ "timesbd.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);
			BaseFont font = BaseFont.createFont(Locations.getFontLocation()
					+ "times.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);
			Rectangle rct = new Rectangle(doc.getPageSize());
			float iconBase = (1.25f * 72); // This is the base of the icons on
											// the page
			Image icon = Image.getInstance(Locations.getImageLocation()
					+ icons[iconNum]);
			// Image box = Image.getInstance(Locations.ImageLocation() +
			// "blueBOX.png");
			icon.scalePercent(23);
			float scale = .23f;
			float iconLeft = (icon.getWidth() / 2) * scale;
			float boxSize = (.1875f * 72);

			// Adjust the top
			rct.setTop(rct.getTop() - (.5f * 72));

			// Place the Icon
			icon.setAbsolutePosition((1.25f * 72) - iconLeft, rct.getTop()
					- iconBase);
			doc.add(icon);

			// Set our line color
			cb.setRGBColorStroke(0, 72, 117);

			cb.setLineWidth(.75f);

			// do the bottom line
			cb.moveTo(1.25f * 72, (.5f * 72));
			cb.lineTo(rct.getRight() - (.5f * 72), (.5f * 72));

			cb.moveTo(1.25f * 72, (.5f * 72));
			cb.lineTo(1.25f * 72, rct.getTop());

			// stroke the lines
			cb.stroke();

			// Do the lower left box
			cb.rectangle((1.25f - 0.09375f) * 72, (.5f - 0.09375f) * 72,
					boxSize, boxSize);
			cb.setRGBColorFill(0, 72, 117);
			cb.closePathFillStroke();

			// Do the lower right box
			Rectangle pnRect = new Rectangle(0, 0);
			pnRect.setLeft(rct.getRight() - ((.5f + 0.09375f) * 72));
			pnRect.setTop((.5f - 0.09375f) * 72);
			pnRect.setRight(pnRect.getLeft() + boxSize);
			pnRect.setBottom(pnRect.getTop() - boxSize);

			// cb.rectangle(rct.getRight() - ((.5f + 0.09375f) * 72), (.5f -
			// 0.09375f) * 72, (.1875f * 72), (.1875f * 72));
			cb.rectangle(pnRect.getLeft(), pnRect.getTop(), boxSize, boxSize);
			cb.setRGBColorFill(0, 72, 117);
			cb.closePathFillStroke();

			// Now we do the page number if one is supplied.
			if (pageNum.length() > 0) {
				float pnHeight = fontBold.getAscentPoint(pageNum, 9)
						- fontBold.getDescentPoint(pageNum, 9);
				float pnWidth = fontBold.getWidthPoint(pageNum, 9);

				float l = pnRect.getLeft() + ((boxSize - pnWidth) / 2);
				float b = pnRect.getTop() + ((boxSize - pnHeight) / 2);

				cb.beginText();
				cb.setFontAndSize(fontBold, 9);
				cb.setRGBColorFill(255, 255, 255);
				cb.setTextMatrix(l, b);
				cb.showText(pageNum);
				cb.endText();

			}

			// Display the copyright
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			char cs = 0x00a9; // Unicode for the copyright symbol
			String copyRight = com.estate.constants.StringConstants.copyRight + cs + " "
					+ df.format(new Date());
			float crWidth;
			float crLeft;

			crWidth = font.getWidthPoint(copyRight, 8);
			cb.beginText();

			cb.setFontAndSize(font, 8);
			cb.setRGBColorFill(0, 0, 0);

			crLeft = (doc.getPageSize().getRight() - crWidth) / 2;
			cb.setTextMatrix(crLeft, .375f * 72); // Place the base of the
													// copyright at 3/8" up from
													// the bottom
			cb.showText(copyRight);
			cb.endText();

			if (toolName.length() > 0) {
				cb.beginText();

				cb.setFontAndSize(font, 8);
				cb.setRGBColorFill(0, 0, 0);

				cb.setTextMatrix((1.25f * 72) + boxSize, .375f * 72);
				cb.showText(toolName);
				cb.endText();
			}

			// Fix a licensee at left
			cb.beginText();
			cb.setFontAndSize(font, 8);
			cb.setRGBColorFill(0, 0, 0);
			cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, " Licensee: "
					+ getLicense(), (10.2f * 72), .375f * 72, 0);
			cb.endText();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void drawNoBorder(Document doc, String pageNum) {
		try {
			PdfContentByte cb = writer.getDirectContentUnder();
			BaseFont fontBold = BaseFont.createFont(Locations.getFontLocation()
					+ "timesbd.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);
			BaseFont font = BaseFont.createFont(Locations.getFontLocation()
					+ "times.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);
			Rectangle rct = new Rectangle(doc.getPageSize());
			float boxSize = (.1875f * 72);

			// Adjust the top
			rct.setTop(rct.getTop() - (.5f * 72));

			// Do the lower right box
			Rectangle pnRect = new Rectangle(0, 0);
			pnRect.setLeft(rct.getRight() - ((.5f + 0.09375f) * 72));
			pnRect.setTop(((.5f - 0.09375f) * 72) - 9);
			pnRect.setRight(pnRect.getLeft() + boxSize);
			pnRect.setBottom(pnRect.getTop() - boxSize);

			cb.rectangle(pnRect.getLeft(), pnRect.getTop(), boxSize, boxSize);
			cb.setRGBColorFill(0, 72, 117);
			cb.closePathFillStroke();

			// Now we do the page number if one is supplied.
			if (pageNum.length() > 0) {
				float pnHeight = fontBold.getAscentPoint(pageNum, 9)
						- fontBold.getDescentPoint(pageNum, 9) - 4.5f;
				float pnWidth = fontBold.getWidthPoint(pageNum, 9);

				float l = pnRect.getLeft() + ((boxSize - pnWidth) / 2);
				float b = pnRect.getTop() + ((boxSize - pnHeight) / 2) - 4.5f;

				cb.beginText();
				cb.setFontAndSize(fontBold, 9);
				cb.setRGBColorFill(255, 255, 255);
				cb.setTextMatrix(l, b);
				cb.showText(pageNum);
				cb.endText();
			}

			// Display the copyright
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			char cs = 0x00a9; // Unicode for the copyright symbol
			String copyRight = "Advanced Practice Network " + cs + " "
					+ df.format(new Date());
			float crWidth;
			float crLeft;

			crWidth = font.getWidthPoint(copyRight, 8);
			cb.beginText();

			cb.setFontAndSize(font, 8);
			cb.setRGBColorFill(0, 0, 0);

			crLeft = (doc.getPageSize().getRight() - crWidth) / 2;
			cb.setTextMatrix(crLeft, .375f * 72); // Place the base of the
													// copyright at 3/8" up from
													// the bottom
			cb.showText(copyRight);
			cb.endText();

			if (license != null) {
				// Fix a licensee at left
				cb.beginText();
				cb.setFontAndSize(font, 8);
				cb.setRGBColorFill(0, 0, 0);
				cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, " Licensee: "
						+ getLicense(), (10.2f * 72), .375f * 72, 0);
				cb.endText();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

}
