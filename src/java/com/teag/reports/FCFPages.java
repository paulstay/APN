/*
 * Created on May 9, 2005
 *
 */
package com.teag.reports;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * 
 */
public class FCFPages extends Page {

	/**
	 * @param document
	 * @param writer
	 */
	private String fcfType;
	
	public FCFPages(Document document, PdfWriter writer) {
		super(document, writer);
		pageIcon = 2;
	}

	public void draw() {
		page1();
	}

	public String getFcfType() {
		return fcfType;
	}

	private void page1() {
		drawBorder(pageIcon, "FCF");
		drawHeader(userInfo.getClientHeading(), "Family Charitable Foundation");

		String sec1[];

		if (userInfo.isSingle()) {
			if( fcfType.equalsIgnoreCase("S")) {
				sec1 = new String[] {
						"A Family Charitable Foundation is created in the form of a Supporting Organization (SO), which operates under the sponsorship umbrella of a Public Charitable Foundation. This sponsor provides the necessary reporting paperwork and also favorable tax treatment. "
								+ userInfo.getClientFirstName()
								+ " funds this SO, possibly in several stages (see other recommendations), "
								+ " helps oversee the SO and directs distributions to charities from the SO during "  + (userInfo.getClientGender().equalsIgnoreCase("M") ? "his" : "her") + " lifetime.",
						"Children, grandchildren and beyond can become involved in both the management of the SO as well as the awarding of distributions to various charities. As this is done, their hearts can be touched and enlarged as they observe the beneficial effects of their humanitarian efforts.",
						"As these distributions are made, family members have the opportunity to participate in a wonderful process. In addition to benefiting mankind and their respective communities, important relationships can be formed with community leaders, which can open many other opportunities and positive experiences for family members."
					};
			} else if( fcfType.equalsIgnoreCase("D")) {
				sec1 = new String[] {
						"A Family Charitable Foundation is created in the form of a Donor Advised Fund (DAF), which operates under the sponsorship umbrella of a Public Charitable Foundation. This sponsor provides the necessary reporting paperwork and also favorable tax treatment. "
								+ userInfo.getClientFirstName()
								+ " funds this DAF, possibly in several stages (see other recommendations). " + userInfo.getClientFirstName()
								+ " can help oversee the DAF in cooperation of the Public Charity and also recommends distributions distributions to charities from the DAF during his/her lifetime.",
						"Children, grandchildren and beyond can become involved in both the management of the DAF (through a separate family entity) as also recommend distributions to various charities. As this is done, their hearts can be touched and enlarged as they observe the beneficial effects of their humanitarian efforts.",
						"As these distributions are made, family members have the opportunity to participate in a wonderful process. In addition to benefiting mankind and their respective communities, important relationships can be formed with community leaders, which can open many other opportunities and positive experiences for family members."
					};				
			} else {
				sec1 = new String[] {
						"A Family Charitable Foundation is created in the form of a Private Foundation (PF), which operates under the sponsorship umbrella of a Public Charitable Foundation. This sponsor provides the necessary reporting paperwork and also favorable tax treatment. "
								+ userInfo.getClientFirstName()
								+ " funds this PA, possibly in several stages (see other recommendations), "
								+ " helps oversee the PA and directs distributions to charities from the PF during his/her lifetime.",
						"Children, grandchildren and beyond can become involved in both the management of the PF as well as the awarding of distributions to various charities. As this is done, their hearts can be touched and enlarged as they observe the beneficial effects of their humanitarian efforts.",
						"As these distributions are made, family members have the opportunity to participate in a wonderful process. In addition to benefiting mankind and their respective communities, important relationships can be formed with community leaders, which can open many other opportunities and positive experiences for family members."
					};				
			}

		} else {
			if( fcfType.equalsIgnoreCase("S")) {
				sec1 = new String[] {
						"A Family Charitable Foundation is created in the form of a Supporting Organization (SO), which operates under the sponsorship umbrella of a Public Charitable Foundation. This sponsor provides the necessary reporting paperwork and also favorable tax treatment. "
								+ userInfo.getClientFirstName()
								+ " and "
								+ userInfo.getSpouseFirstName()
								+ " can fund this SO, possibly in several stages (see recommendations), "
								+ " and"
								+ " can help oversee the SO and direct distributions to charities from the SO during their lifetimes.",
						"Children, grandchildren and beyond can become involved in both the management of the SO as well as the awarding of distributions to various charities. As this is done, their hearts can be touched and enlarged as they observe the beneficial effects of their humanitarian efforts.",
						"As these distributions are made, family members have the opportunity to participate in a wonderful process. In addition to benefiting mankind and their respective communities, important relationships can be formed with community leaders, which can open many other opportunities and positive experiences for family members."

					};
			} else if( fcfType.equalsIgnoreCase("D")) {
				sec1 = new String[] {
						"A Family Charitable Foundation is created in the form of a Donor Advised Fund (DAF), which operates under the sponsorship umbrella of a Public Charitable Foundation. This sponsor provides the necessary reporting paperwork and also favorable tax treatment. "
								+ userInfo.getClientFirstName()
								+ " and "
								+ userInfo.getSpouseFirstName()
								+ " can fund this DAF, possibly in several stages (see recommendations), "
								+ " and"
								+ " can help oversee the DAF and in cooperation of the Public Charity also recommends distributions to charities from the SO during their lifetimes.",
						"Children, grandchildren and beyond can become involved in both the management of the DAF (through a separate family entity) as well as the awarding of distributions to various charities. As this is done, their hearts can be touched and enlarged as they observe the beneficial effects of their humanitarian efforts.",
						"As these distributions are made, family members have the opportunity to participate in a wonderful process. In addition to benefiting mankind and their respective communities, important relationships can be formed with community leaders, which can open many other opportunities and positive experiences for family members."

					};
				
			} else {
				sec1 = new String[] {
						"A Family Charitable Foundation is created in the form of a Private Foundation (PF), which operates under the sponsorship umbrella of a Public Charitable Foundation. This sponsor provides the necessary reporting paperwork and also favorable tax treatment. "
								+ userInfo.getClientFirstName()
								+ " and "
								+ userInfo.getSpouseFirstName()
								+ " can fund this PF, possibly in several stages (see recommendations), "
								+ " and"
								+ " direct distributions to charities from the PF during their lifetimes.",
						"Children, grandchildren and beyond can become involved in both the management of the PF as well as the awarding of distributions to various charities. As this is done, their hearts can be touched and enlarged as they observe the beneficial effects of their humanitarian efforts.",
						"As these distributions are made, family members have the opportunity to participate in a wonderful process. In addition to benefiting mankind and their respective communities, important relationships can be formed with community leaders, which can open many other opportunities and positive experiences for family members."

					};
			}
		}

		Rectangle rct = new Rectangle(prctLeft);
		rct.setBottom(rct.getBottom() - _1_4TH);
		rct.setLeft(rct.getLeft() - _1_4TH);

		if( userInfo.isSingle()) {
			if( userInfo.getClientGender().equalsIgnoreCase("M")) {
				drawDiagram("FCF-M.png", rct, BOTTOM + LEFT);
			} else {
				drawDiagram("FCF-F.png", rct, BOTTOM + LEFT);	
			}
		} else {
			drawDiagram("FCF.png", rct, BOTTOM + LEFT);
		}
		rct = new Rectangle(prctRight);
		rct.setTop(rct.getTop() - 72);
		this.drawSection(rct, "", sec1, 1, 14, 12);
	}

	public void setFcfType(String fcfType) {
		this.fcfType = fcfType;
	}

}
