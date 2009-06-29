package com.estate.report;

/*
 * Author Paul Stay
 * Created on Apr 26, 2005
 *
 */
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.estate.pdf.Label;
import com.estate.pdf.Locations;
import com.estate.pdf.Page;
import com.estate.pdf.PageTable;
import com.estate.pdf.PageUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class TitlePage extends Page {

	private PdfWriter writer;
	private Document doc;
	private BaseFont font;

	private String client;
	private String date;

	private Vector<String[]> advisors;

	String title;

	private String icons[] = { "queen.png", "pawn.png", "knight.png",
			"castle.png", "king.png" };
	private String labels[] = { "INTRODUCTION", "CURRENT PLAN",
			"PROPOSED PLAN", "SUPPORTING\nDOCUMENTS", "IMPLEMENTATION" };

	public TitlePage(Document doc, PdfWriter writer) {
		super(doc, writer);
		// Save for later
		this.writer = writer;
		this.doc = doc;

		font = PageUtils.LoadFont("times.ttf");
		advisors = new Vector<String[]>();

	}

	public void addAdvisor(String type, String name, String address) {
		String[] advisor = new String[3];
		advisor[0] = type;
		advisor[1] = name;
		advisor[2] = address;
		advisors.add(advisor);
	}

	public void draw() {
		PdfContentByte cbu = writer.getDirectContentUnder();
		PdfContentByte cb = writer.getDirectContent();

		// First thing is do left graphic.

		// Line on left
		float scale = .23f;

		// float height = icon.getHeight() * scale;
		// float iconLeft = (icon.getWidth() / 2) * scale;

		float height;
		float iconLeft;
		try {
			Rectangle rct = new Rectangle(doc.getPageSize());
			float iconBase = (1.25f * 72); // This is the base of the icons on
											// the page
			Image icon[] = new Image[icons.length];
			// Adjust the top
			rct.setTop(rct.getTop() - (.5f * 72));

			// Load the icons
			for (int i = 0; i < icons.length; i++) {

				icon[i] = Image.getInstance(Locations.imageLocation + icons[i]);
				icon[i].scalePercent(23);
			}
			// Display the icons
			for (int i = 0; i < icons.length; i++) {
				iconLeft = (icon[i].getWidth() / 2) * scale;
				icon[i].setAbsolutePosition((1.25f * 72) - iconLeft, rct.getTop()
						- iconBase);

				// Place the Icon
				doc.add(icon[i]);

				// Make adjustment to base for next icon, if on last icon skip
				// it.
				if (i < icons.length - 1) {
					height = icon[i + 1].getHeight() * scale;
					iconBase += (height + (.25 * 72));
				}

			}
			// Do the left line
			// Set our line color
			cbu.setRGBColorStroke(0, 72, 117);

			cbu.setLineWidth(.5f);

			cbu.moveTo(1.25f * 72, (.5f * 72));
			cbu.lineTo(1.25f * 72, rct.getTop());

			cbu.stroke();

			cbu.setRGBColorStroke(255, 255, 255);

			cbu.setLineWidth(.5f);

			cbu.moveTo(1.25f * 72, rct.getTop() - (5.9f * 72));
			cbu.lineTo(1.25f * 72, rct.getTop() - (6.625f * 72));

			cbu.stroke();

			// Now do the list down the side
			Label lbl = new Label(writer);
			lbl.setLeadingAdjust(.80f);
			iconBase = (1.25f * 72); // This is the base of the icons on the
										// page
			for (int i = 0; i < icons.length; i++) {
				// Set the positioning
				Rectangle lblRct = new Rectangle(0, 0);
				lblRct.setLeft(1.5f * 72);
				lblRct.setRight(rct.getLeft() + 3f * 72);
				lblRct.setBottom(rct.getTop()
						- (iconBase - ((icon[i].getHeight() * scale) * .55f)));
				Color color = new Color(0, 0, 0);
				lbl.displayLabel(lblRct, labels[i], font, 7, color,
						Label.LBL_LEFT, Label.LBL_FIRST);
				if (i < icons.length - 1) {
					height = icon[i + 1].getHeight() * scale;
					iconBase += (height + (.25 * 72));
				}

			}
			rct = new Rectangle(prctFull);
			// rct.setTop(resRct.getBottom() - _1_2TH);

			// Do the lines for "PLANNING STRATEGIES"
			rct = new Rectangle(doc.getPageSize());
			rct.setTop(rct.getTop() - 72);
			rct.setLeft(4.125f * 72);

			cbu.setRGBColorStroke(0, 69, 119);

			cbu.setLineWidth(.5f);

			cbu.moveTo(rct.getLeft(), rct.getTop());
			cbu.lineTo(rct.getLeft() + 4.125f * 72, rct.getTop());

			cbu.moveTo(rct.getLeft(), rct.getTop() - (0.28125f * 72));
			cbu.lineTo(rct.getLeft() + 4.125f * 72, rct.getTop() - (0.28125f * 72));

			cbu.stroke();

			// set up to do the text down the middle
			Rectangle lblRct = new Rectangle(rct);

			lblRct.setBottom(lblRct.getTop() - (0.21875f * 72));

			lblRct.setRight(lblRct.getLeft() + (4.125f * 72));

			Color color = new Color(0, 69, 119);
			lbl.displayLabel(lblRct, title, font, 16, color, Label.LBL_CENTER,
					Label.LBL_BOTTOM);

			color = new Color(0, 0, 0);
			lblRct.setBottom(doc.getPageSize().getTop() - (1.875f * 72));
			lbl.displayLabel(lblRct, "Strategies For:", font, 13, color,
					Label.LBL_CENTER, Label.LBL_BOTTOM);

			color = new Color(0, 69, 119);
			lblRct.setBottom(doc.getPageSize().getTop() - (3.25f * 72));
			lbl.displayLabel(lblRct, this.client, font, 22, color,
					Label.LBL_CENTER, Label.LBL_BOTTOM);

			color = new Color(0, 0, 0);
			lblRct.setBottom(doc.getPageSize().getTop() - (3.625f * 72));
			if (date == null) {
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
						"M/d/yyyy");
				date = df.format(new Date());
			}

			lbl.displayLabel(lblRct, this.date, font, 13, color,
					Label.LBL_CENTER, Label.LBL_BOTTOM);

			color = new Color(0, 0, 0);

			lblRct.setBottom(doc.getPageSize().getTop() - (4.875f * 72));
			lbl.displayLabel(lblRct, "PRESENTED BY:", font, 12, color,
					Label.LBL_CENTER, Label.LBL_BOTTOM);

			Rectangle aRect = new Rectangle(prctFull);
			aRect.setTop(lblRct.getBottom() - _1_2TH);
			drawAdvisorsTable(aRect);

			lblRct.setLeft(1.1875f * 72);
			lblRct.setTop(doc.getPageSize().getTop() - (6.4625f * 72));
			lbl.displayLabel(lblRct, "The", font, 14, color, Label.LBL_LEFT,
					Label.LBL_TOP);

			lblRct.setLeft(lblRct.getLeft() + font.getWidthPoint("The ", 14));
			lbl.displayLabel(lblRct, "Wealth", font, 30, color, Label.LBL_LEFT,
					Label.LBL_TOP);

			lbl.setLeadingAdjust(.60f);
			lblRct.setTop(doc.getPageSize().getTop() - (6.775f * 72));
			lblRct.setLeft(1.0625f * 72);
			lblRct.setRight(lblRct.getLeft()
					+ font.getWidthPoint("Preservation ", 24));
			lbl.displayLabel(lblRct, "Preservation\nBlueprint", font, 24,
					color, Label.LBL_CENTER, Label.LBL_TOP);

			// Display the copyright
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			// String copyRight = "The Estate Advisory Group ? " + df.format(new
			// Date());
			char cs = 0x00a9; // Unicode for the copyright symbol
			String copyRight = "The Estate Advisory Group " + cs + " "
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
		} catch (Exception e) {

		}

	}

	private void drawAdvisorsTable(Rectangle rct) {
		// Vector rows = new Vector();
		String rows[][] = new String[3][advisors.size() + 1];

		Color color = new Color(0, 69, 119);
		String colorStyle = "color=" + color.getRGB();

		String styles[] = { "[bold=1,align=center," + colorStyle + "]",
				"[bold=1,align=center,valign=top," + colorStyle + "]",
				"[valign=top,align=center," + colorStyle + "]" };

		for (int r = 0; r < rows.length; r++) {
			int col;
			for (col = 0; col < advisors.size(); col++) {
				String sCol[] = advisors.get(col);
				rows[r][col] = sCol[r];
			}
		}

		for (int r = 0; r < rows.length; r++) {
			String format = "";
			for (int c = 0; c < rows[r].length - 1; c++) {
				format += styles[r];

			}
			rows[r][rows[r].length - 1] = format;

		}
		PageTable table = new PageTable(writer);

		table.setTableFont("GARA.ttf");
		table.setTableFontBold("GARABD.ttf");
		table.setFontSize(14);

		rct.setLeft(rct.getLeft() + (_1_2TH) * 2.5f);
		rct.setRight(rct.getRight() - (_1_8TH) * 2.5f);

		table.setTableFormat(rct, rows[0].length - 1);
		table.setFontSize(table.getFontSize() - 1);

		table.buildTableEx(rows);
		table.drawTable();

	}

	public String getTitle() {
		return title;
	}

	/**
	 * @param client
	 *            The client to set.
	 */
	@Override
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @param date
	 *            The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
