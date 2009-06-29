/*
 * Created on May 8, 2005
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
public class TestCLATPages extends Page {

	/**
	 * @param document
	 * @param writer
	 */

	public int term = 20; // Default

	boolean useLLC = true;

	public TestCLATPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;

	}

	public void draw() {
		page1();
	}

	public int getTerm() {
		return term;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	private void page1() {
		drawBorder(pageIcon, "Test. CLAT");
		drawHeader(userInfo.getClientHeading(),
				"Testamentary Charitable Lead Annuity Trust");
		if (userInfo.isSingle()) {
			if (userInfo.getClientGender().equalsIgnoreCase("M")) {
				if (useLLC) {
					drawDiagram("LLC-T-Clat-M.png", prctTop, 0);
				} else {
					drawDiagram("T-Clat-M.png", prctTop, 0);
				}
			} else {
				if (useLLC) {
					drawDiagram("LLC-T-Clat-F.png", prctTop, 0);
				} else {
					drawDiagram("T-Clat-F.png", prctTop, 0);
				}
			}
		} else {
			drawDiagram("T-Clat.png", prctTop, 0);
		}

		if (userInfo.isSingle()) {
			String sec1[] = {
					"Upon your death, a Testamentary Charitable Lead Annuity Trust (Test. CLAT), which will be described in your Living Trust, becomes operative. Except for personal effects, some assets covered by remaining exemption(s), and enough liquidity to pay administrative expenses, all other remaining assets (some of which could be in an LLC) are then transferred to the Test. CLAT. Your estate will receive a substantial deduction from the taxable estate based on the term of the trust and the payout to charity.",
					"The family foundation (discussed next) and subsequently the charity(ies) of choice receive(s) a fixed annual payment for "
							+ Integer.toString(term)
							+ " years from the income generated by the use of assets transferred to the Test. CLAT.",
					"At the end of the Test. CLAT term, the assets plus any appreciation pass estate-tax free to the family." };

			String benefits[] = {
					"A substantial discount is obtained by your estate through utilizing the Test. CLAT.",
					userInfo.getClientFirstName()
							+ " has full control of assets while living, and can choose the level of control by the family after death.",
					"Full value, of the assets remaining in the trust at the end of the period, is later transferred to the family without additional tax.",
					"Excess earnings and growth in the value of assets add to the benefit to the family.",
					"The substantial tax savings are shared by the family and selected charities.",
					"Assets transferred to the Test. CLAT receive a step up in basis at death."

			};

			String effects[] = { "Heirs do not receive the benefit of the assets until after the CLAT term expires." };

			drawSection(prctLLeft, " ", sec1, 1);
			Rectangle rctx = new Rectangle(prctLRight);
			rctx.setRight(rctx.getRight() + _1_2TH);
			rctx = this.drawSection(rctx, "Benefits of Using this Technique",
					benefits, 2);

			Rectangle rct = new Rectangle(prctLRight);
			rct.setRight(rct.getRight() + _1_2TH);
			rct.setTop(rctx.getBottom());

			drawSection(rct,
					"Side Effects / Draw backs of using this technique",
					effects, 2);
		} else {
			String sec1[] = {
					"Upon both of your deaths, a Testamentary Charitable Lead Annuity Trust (Test. CLAT), which will be described in your Living Trust, becomes operative. Except for personal effects, some assets covered by remaining exemption(s), and enough liquidity to pay administrative expenses, all other remaining assets are then transferred to the Test. CLAT. Your estate will receive a substantial deduction from the taxable estate based on the term of the trust and the payout to charity.",
					"A family foundation and subsequently the charity(ies) of choice receive(s) a fixed annual payment for "
							+ Integer.toString(term)
							+ " years from the income generated by the use of assets transferred to the Test. CLAT.",
					"At the end of the Test. CLAT term, the assets plus any appreciation pass estate-tax free to the family."};
					

			String benefits[] = {
					"A substantial discount is obtained by your estate through utilizing the Test. CLAT.",
					clientFirstName
							+ " and "
							+ spouseFirstName
							+ " have full control of assets while living, and can choose the level of control by the family after death.",
					"Full value, of the assets remaining in the trust at the end of the period, is later transferred to the family without additional tax.",
					"Excess earnings and growth in the value of assets add to the benefit to the family.",
					"The substantial tax savings are shared by the family and selected charities.",
					"Assets transferred to Test. CLAT receive a step up in basis at death."

			};

			String effects[] = { "Heirs do not receive the benefit of the assets until after the CLAT term expires." };

			drawSection(prctLLeft, " ", sec1, 1);
			Rectangle rctx = new Rectangle(prctLRight);
			rctx.setRight(rctx.getRight() + _1_2TH);
			rctx = drawSection(rctx, "Benefits of Using this Technique",
					benefits, 2);

			Rectangle rct = new Rectangle(prctLRight);
			rct.setRight(rct.getRight() + _1_2TH);
			rct.setTop(rctx.getBottom());

			drawSection(rct,
					"Side Effects / Draw backs of using this technique",
					effects, 2);
		}

	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}
}
