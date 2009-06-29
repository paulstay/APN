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
public class LLCPages extends Page {

	/**
	 * @param document
	 * @param writer
	 */
	public LLCPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}
	
	
	public void draw()
	{
		page1();
		
	}

	private void page1()
	{
		drawBorder(pageIcon,"LLC");
		drawHeader(userInfo.getClientHeading(), "Limited Liability Company (LLC)");
		Rectangle rct = new Rectangle(prctTop);
		rct.setLeft(rct.getLeft() - _1_4TH);
		rct.setBottom(rct.getBottom() - _1_4TH);
		if( userInfo.isSingle()) {
			if( userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("LLC-M.png", rct, BOTTOM + LEFT);
			} else {
				drawDiagram("LLC-F.png", rct, BOTTOM + LEFT);
			}
		} else {
			drawDiagram("LLC-FLP.png", rct, BOTTOM + LEFT);
		}
		
		String theProcess[] = {
			"A Limited Liability Company (LLC)  is created (with a valid business purpose) for techniques to be described subsequently, and assets are transfered to it.",
			"You can retain or assign the Managing Member Interest, which has the asset management responsibilities and control," +
			" however the MMI distribution and liquidation function responsibilities must be assigned to a trusted individual or entity.  Reasonable compensation can be paid for the managing duties." ,
			"The Member Interests have no management responsibility, and usually cannot be liquidated, sold, or freely transferred without the consent of the Managing Member. "
			+ "This allows the Member Interest(s) to receive a discount for both a lack of marketability and lack of control. The discount values provide leverage for future gifts made to your family."
		};
		
		drawSection(prctLLeft, "The Process", theProcess, 1);
		
		Rectangle rctx = drawSection(prctLRight, "Benefits of Utilizing this Technique", new String[0], 0);
		rct = new Rectangle(prctLRight);
		rct.setRight( rct.getRight() + _1_2TH + _1_4TH);
		
		rct.setTop(rct.getTop() - rctx.getHeight());
		
		char bullet = 0x2022;
		String taxReasons[] = {
			bullet + " Valuation discounts",
			"    --Lack of Control",
			"    --Lack of Marketability",
			bullet + " Leverage 1.5(+/-):1",
			bullet + " Can Pass More to Family...   Tax Free"
		};
		rctx = new Rectangle(rct.getLeft(), rct.getBottom(), rct.getLeft() + ((rct.getWidth() / 3) - _1_16TH), rct.getTop());
		drawSection(rctx, "Tax Reasons", taxReasons, 0);
		

		String businessReasons[] = {
			bullet + " Control",
			"    --MM Interest governs all",
			bullet + " Control Asset Management",
			bullet + " Control Distributions",
			"    --Amounts",
			"    --Timing"
		};
		rctx.setLeft(rctx.getRight() + (_1_8TH));
		rctx.setRight(rctx.getLeft() + ((rct.getWidth() / 3) - _1_16TH));
		drawSection(rctx, "Business Reasons", businessReasons, 0);
		
		String familyReasons[] = {
			bullet + " Mentoring",
			"    --Asset Management",
			"    --Diversification",
			bullet + " Member Meetings",
			"    --Family Gatherings",
			"    --Tax deductible"
		};
		
		rctx.setLeft(rctx.getRight() + (_1_8TH) * 3);
		rctx.setRight(rctx.getLeft() + ((rct.getWidth() / 3) - _1_16TH));
		
		drawSection(rctx, "Family Reasons", familyReasons, 0);
		
	}
}
