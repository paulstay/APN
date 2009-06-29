package com.teag.reports;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * 
 */
public class LifePages extends Page {
	/**
	 * @param document
	 * @param writer
	 */
	public LifePages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}

	public void draw() {
		page1();
	}

	private void page1() {
		drawBorder(pageIcon, "LIFE");
		drawHeader(userInfo.getClientHeading(),
				"Irrevocable Life Insurance (Crummey) Trust");
		Rectangle rct = new Rectangle(prctTop);
		rct.setLeft(rct.getLeft() - _1_4TH);
		rct.setBottom(rct.getBottom() - _1_4TH);

		if (userInfo.isSingle()) {
			if (userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("Crummey-M.png", rct, 0);
			} else {
				drawDiagram("Crummey-F.png", rct, 0);
			}
		} else {
			drawDiagram("Crummey.png", rct, 0);
		}

		String sec1[];
		String sec2[];
		
		String proNoun;
		
		if( userInfo.isSingle()) {
			if(userInfo.getClientGender().equalsIgnoreCase("M")){
				proNoun = "his";
			} else {
				proNoun = "her";
			}
		} else
			proNoun = "";

		if (userInfo.isSingle()) {
			sec1 = new String[] {
					userInfo.getClientFirstName()
							+ " gifts "+ proNoun + " Life Insurance to an Irrevocable Inheritance (Crummey) Trust.",
					"The life insurance policies on "
							+ userInfo.getClientFirstName()
							+ " are then owned by the trust, and upon death proceeds are distributed to their heirs, according to the language in the trust."};

			sec2 = new String[] {
					"Trustee must notify the beneficiaries in writing of the gift made to this trust. (Crummey power withdrawal right letters drafted by attorney)",
					"Beneficiaries have a certain amount of time (e.g. 30 days) to 'demand' their share of the gift. This withdrawal right creates a \"present interest (vs. a \"future interest\") gift\".",
					"Beneficiaries can elect to receive some or all of their gift, refuse the gift in writing, or waive the gift by allowing their option to lapse." };
		
		} else {
			sec1 = new String[] {
					userInfo.getClientFirstName()
							+ " & "
							+ userInfo.getSpouseFirstName()
							+ " gift their Life Insurance to an Irrevocable Inheritance (Crummey) Trust.",
					"The life insurance policies on both "
							+ userInfo.getClientFirstName()
							+ " and "
							+ userInfo.getSpouseFirstName()
							+ " are then owned by the trust, and upon death, proceeds are distributed to their heirs according to the language in the trust."};

			sec2 = new String[] {
					"Trustee must notify the beneficiaries in writing of the gift made to this trust. (Crummey power withdrawal right letters drafted by attorney)",
					"Beneficiaries have a certain amount of time (e.g. 30 days) to 'demand' their share of the gift. This withdrawal right creates a \"present interest (vs. a \"future interest\") gift\".",
					"Beneficiaries can elect to receive some or all of their gift, refuse the gift in writing, or waive the gift by allowing their option to lapse." };
		}

		this.drawSection(prctLLeft, "", sec1, 1, 14, 12);
		this.drawSection(prctLRight, "Other Issues", sec2, 2, 14, 12);
	}
}
