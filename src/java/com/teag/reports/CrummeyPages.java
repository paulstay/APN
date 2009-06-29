/*
 * Created on Jun 2, 2005
 *
 */
package com.teag.reports;

import java.text.DecimalFormat;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * 
 */
public class CrummeyPages extends Page {
	double annualGifts = 0;
	double premium = 0;
	double lifeInsurance = 0;

	DecimalFormat money = new DecimalFormat("$###,###,###,###");

	/**
	 * @param document
	 * @param writer
	 */
	public CrummeyPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}

	public void draw() {
		page1();
	}

	public double getAnnualGifts() {
		return annualGifts;
	}

	public double getLifeInsurance() {
		return lifeInsurance;
	}

	public double getPremium() {
		return premium;
	}

	private void page1() {
		drawBorder(pageIcon, "CRUMMEY");
		drawHeader(userInfo.getClientHeading(),
				"Irrevocable Inheritance (Crummey) Trust");
		Rectangle rct = new Rectangle(prctTop);
		rct.setLeft(rct.getLeft() - _1_4TH);
		rct.setBottom(rct.getBottom() - _1_4TH);

		if( userInfo.isSingle()) {
			if(userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("Crummeynomult-M.png", rct, 0);
			}else {
				drawDiagram("Crummeynomult-F.png", rct, 0);
			}
		} else {
			drawDiagram("Crummeynomult.png", rct, 0);
		}


		String sec1[];
		String sec2[];

		if (userInfo.isSingle()) {
			String line2Sub = userInfo.getClientFirstName()	+ "'s life";
			sec1 = new String[] {
					clientFirstName
							+ " make annual gifts of "
							+ money.format(annualGifts)
							+ " to an Irrevocable Inheritance (Crummey) Trust utilizing annual exclusion gifts, which can be made for both children and grandchildren.",
					"The trustee uses the gifts to purchase "
							+ money.format(lifeInsurance)
							+ " of life insurance on " + line2Sub
							+ " with an annual premium of "
							+ money.format(premium) + ".",
					"Upon the death (of "
							+ userInfo.getClientFirstName() 
							+ "), the life insurance proceeds are paid to the Trust free of both income and estate taxes.",
					"Distributions are made to children and grandchildren from both trusts, as directed by instructions from the trust, as directed by instruction from "
							+ userInfo.getClientFirstName() + " ." };

			sec2 = new String[] {
					"Trustee must notify the beneficiaries in writing of the gift made to this trust. (Crummey power withdrawal right letters drafted by attorney)",
					"Beneficiaries have a certain amount of time (e.g. 30 days) to 'demand' their share of the gift. This withdrawal right creates a \"present interest (vs. a \"future interest\") gift\", thus enabling the annual exclusion to apply.",
					"Beneficiaries can elect to receive some or all of their gift, refuse the gift in writing, or waive the gift by allowing their option to lapse." };

			this.drawSection(prctLLeft, "", sec1, 1, 14, 12);
			this.drawSection(prctLRight, "Other Issues", sec2, 2, 14, 12);
		} else {
			String line2Sub = spouseFirstName == null ? clientFirstName
					+ "'s life" : clientFirstName + " and " + spouseFirstName
					+ "'s lives";
			sec1 = new String[] {
					clientFirstName
							+ " & "
							+ spouseFirstName
							+ " make annual gifts of "
							+ money.format(annualGifts)
							+ " to an Irrevocable Inheritance (Crummey) Trust utilizing annual exclusion gifts, which can be made for both children and grandchildren.",
					"The trustee uses the gifts to purchase "
							+ money.format(lifeInsurance)
							+ " of life insurance on " + line2Sub
							+ " with an annual premium of "
							+ money.format(premium) + ".",
					"Upon the second death (of "
							+ clientFirstName
							+ " & "
							+ spouseFirstName
							+ "), the life insurance proceeds are paid to the Trust free of both income and estate taxes.",
					"Distributions are made to children and grandchildren from both trusts, as directed by instructions from the trust, as directed by instruction from "
							+ clientFirstName + " and " + spouseFirstName + ".", };

			sec2 = new String[] {
					"Trustee must notify the beneficiaries in writing of the gift made to this trust. (Crummey power withdrawal right letters drafted by attorney)",
					"Beneficiaries have a certain amount of time (e.g. 30 days) to 'demand' their share of the gift. This withdrawal right creates a \"present interest (vs. a \"future interest\") gift\", thus enabling the annual exclusion to apply.",
					"Beneficiaries can elect to receive some or all of their gift, refuse the gift in writing, or waive the gift by allowing their option to lapse." };

			this.drawSection(prctLLeft, "", sec1, 1, 14, 12);
			this.drawSection(prctLRight, "Other Issues", sec2, 2, 14, 12);
		}
	}

	public void setAnnualGifts(double annualGifts) {
		this.annualGifts = annualGifts;
	}

	public void setLifeInsurance(double lifeInsurance) {
		this.lifeInsurance = lifeInsurance;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}
}
