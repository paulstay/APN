package com.teag.reports;

import java.text.DecimalFormat;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 */
public class CrummeyWMultiPages extends Page {
	double annualGifts = 0;

	double premium = 0;

	double lifeInsurance = 0;

	DecimalFormat money = new DecimalFormat("$###,###,###,###");

	/**
	 * @param document
	 * @param writer
	 */
	public CrummeyWMultiPages(Document document, PdfWriter writer) {
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
		drawBorder(pageIcon, "CRUMMEY/MGT");
		drawHeader(userInfo.getClientHeading(),
				"Irrevocable Inheritance (Crummey) Trust");
		Rectangle rct = new Rectangle(prctTop);
		rct.setLeft(rct.getLeft() - _1_4TH);
		rct.setBottom(rct.getBottom() - _1_4TH);
		
		if( userInfo.isSingle()) {
			if(userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("Crummey-M.png", rct, 0);
			}else {
				drawDiagram("Crummey-F.png", rct, 0);
			}
		} else {
			drawDiagram("Crummey.png", rct, 0);
		}
			

		if (userInfo.isSingle()) {

			String line2Sub = userInfo.getClientFirstName() + "'s life";
					
			String sec1[] = {
					userInfo.getClientFirstName() 
							+ " makes annual gifts of "
							+ money.format(annualGifts)
							+ " to an Irrevocable Inheritance (Crummey) Trust utilizing annual exclusion gifts made for children and grandchildren.",
					"The trustee uses the gifts to loan money to the Multigenerational Trust which then obtains "
							+ money.format(lifeInsurance)
							+ " of life insurance on "
							+ line2Sub
							+ " with an annual premium of "
							+ money.format(premium) + ".",
					"Upon the death (of "
							+ userInfo.getClientFirstName()
							+ "), the life insurance proceeds are paid to the Multigenerational Trust free of both income and estate taxes. Trust income provides the difference.",
					"The Multigenerational Trust then uses insurance proceeds to repay the loan to this trust.",
					"Distributions are made to children and grandchildren from both trusts, as directed by instructions from "
							+ userInfo.getClientFirstName() };

			String sec2[] = {
					"Trustee must notify the beneficiaries in writing of the gift made to this trust. (Crummey power withdrawal right letters drafted by attorney)",
					"Beneficiaries have a certain amount of time (e.g. 30 days) to 'demand' their share of the gift. This withdrawal right creates a \"present interest (vs. a \"future interest\") gift\", thus enabling the annual exclusion to apply.",
					"Beneficiaries can elect to receive some or all of their gift, refuse the gift in writing, or waive the gift by allowing their option to lapse." };

			this.drawSection(prctLLeft, "", sec1, 1, 14, 12);
			this.drawSection(prctLRight, "Other Issues", sec2, 2, 14, 12);
		} else {

			String line2Sub = spouseFirstName == null ? clientFirstName
					+ "'s life" : clientFirstName + " and " + spouseFirstName
					+ "'s lives";
			String sec1[] = {
					clientFirstName
							+ " & "
							+ spouseFirstName
							+ " make annual gifts of "
							+ money.format(annualGifts)
							+ " to an Irrevocable Inheritance (Crummey) Trust utilizing annual exclusion gifts made for children and grandchildren.",
					"The trustee uses the gifts to loan money to the Multigenerational Trust which then obtains "
							+ money.format(lifeInsurance)
							+ " of life insurance on "
							+ line2Sub
							+ " with an annual premium of "
							+ money.format(premium) + ".  Trust income provides the difference.",
					"Upon the second death (of "
							+ clientFirstName
							+ " & "
							+ spouseFirstName
							+ "), the life insurance proceeds are paid to the Multigenerational Trust free of both income and estate taxes.",
					"The Multigenerational Trust then uses insurance proceeds to repay the loan to this trust.",
					"Distributions are made to children and grandchildren from both trusts, as directed by instructions from "
							+ clientFirstName + " and " + spouseFirstName };

			String sec2[] = {
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
