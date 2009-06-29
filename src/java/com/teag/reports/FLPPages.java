/*
 * Created on May 3, 2005
 *
 */
package com.teag.reports;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul R. Stay
 * 
 */
public class FLPPages extends Page {

	/**
	 * @param document
	 * @param writer
	 */
	public FLPPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}

	public void draw() {
		page1();

	}

	private void page1() {
		drawBorder(pageIcon, "FLP");
		drawHeader(userInfo.getClientHeading(),
				"Family Limited Partnership (FLP)");
		Rectangle rct = new Rectangle(prctTop);
		rct.setLeft(rct.getLeft() - _1_4TH);
		rct.setBottom(rct.getBottom() - _1_4TH);
		if (userInfo.isSingle()) {
			if (userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("FLP-M.png", rct, BOTTOM + LEFT);
			} else {
				drawDiagram("FLP-F.png", rct, BOTTOM + LEFT);
			}
		} else {
			drawDiagram("FLP.png", rct, BOTTOM + LEFT);
		}

		String theProcess[] = {
				"A Family Limited Partnership is created (with a valid business purpose) for techniques to be described subsequently, " +
				        "and assets are transfered to the partnership. Assets can be better protected, management can be centralized, " + 
				        "and children can be trained in the management process.",
				"You can retain or assign the General Partner function, however the GP distribution and liquidation responsibilities must be assigned to a trusted indivdual or entity. "
						+ "Reasonable compensation can be paid for the managing duties.",
				"The Limited Partner interests have no management responsibility, and usually cannot be liquidated, sold, or freely transferred without the consent of the GP. "
						+ "This allows the LP interest(s) to receive a discount for both a lack of marketability and lack of control. The discount values provide leverage for future gifts made to the family." };

		drawSection(prctLLeft, "The Process", theProcess, 1);

		Rectangle rctx = drawSection(prctLRight,
				"Benefits of Utilizing this Technique", new String[0], 0);
		rct = new Rectangle(prctLRight);
		rct.setRight(rct.getRight() + _1_2TH + _1_4TH);

		rct.setTop(rct.getTop() - rctx.getHeight());

		char bullet = 0x2022;
		String taxReasons[] = { bullet + " Valuation discounts",
				"    --Lack of Control", "    --Lack of Marketability",
				bullet + " Leverage 1.5(+/-):1",
				bullet + " Can Pass More to Family...   Tax Free" };

		String businessReasons[] = { bullet + " Control",
				"    --GP shares govern all",
				bullet + " Control Asset Management",
				bullet + " Control Distributions", "    --Amounts",
				"    --Timing", bullet + "Asset Protection" };

		String familyReasons[] = { bullet + " Mentoring",
				"    --Asset Management", "    --Diversification",
				bullet + " Partnership Meetings", "    --Family Gatherings",
				"    --Tax deductible" };

		rctx = new Rectangle(rct.getLeft(), rct.getBottom(), rct.getLeft()
				+ ((rct.getWidth() / 3) - _1_16TH), rct.getTop());
		drawSection(rctx, "Business Reasons", businessReasons, 0);

		rctx.setLeft(rctx.getRight() + (3 * _1_8TH));
		rctx.setRight(rctx.getLeft() + ((rct.getWidth() / 3) - _1_16TH));
		drawSection(rctx, "Family Reasons", familyReasons, 0);

		rctx.setLeft(rctx.getRight() + (_1_8TH) * 3);
		rctx.setRight(rctx.getLeft() + ((rct.getWidth() / 3) - _1_16TH));

		drawSection(rctx, "Tax Reasons", taxReasons, 0);

	}
}
