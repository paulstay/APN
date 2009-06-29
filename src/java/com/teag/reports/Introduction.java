/*
 * Created on May 6, 2005
 *
 */
package com.teag.reports;

import com.estate.pdf.Locations;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 *
 */
public class Introduction extends Page {

	/**
	 * @param document
	 * @param writer
	 */
	
	private String singleIntro[] = {
			"Advanced Estate Planning is much like a chess match with the IRS.  The pieces are your assets.  You and your family win by keeping what you desire and transferring to your family as many of the \"extra\" pieces as possible.  The IRS is your opponent, trying to capture as many pieces (assets) as they can, in the form of various taxes, during your lifetime and upon your death. Most people feel powerless, playing against this monolith.\n ",
			"The combined planning team's job is to empower and assist you and your family in retaining as many of these assets  as possible - by utilizing them to fully meet your own needs and then moving the excess assets safely past the IRS and directing them to those whom you desire and in the manner you wish.\n ",
			"The chess piece depicted in the above logo is a white knight.  While each chess piece moves in a different manner, most pieces move linearly.  The knight is different.  It has very unique moves that, when used skillfully and strategically, can be extremely effective in protecting other valuable chess pieces, especially the Queen and King.  In chess, the player with the white pieces is allowed the first move."			
	};
	
	private String intro[] = {
			"Advanced Estate Planning is much like a chess match with the IRS.  The pieces are your assets.  You and your family win by keeping what you desire and transferring to your family as many of the \"extra\" pieces as possible.  The IRS is your opponent, trying to capture as many pieces (assets) as they can, in the form of various taxes, during your lifetime and upon your death(s). Most people feel powerless, playing against this monolith.\n ",
			"The combined planning team's job is to empower and assist you and your family in retaining as many of these assets  as possible - by utilizing them to fully meet your own needs and then moving the excess assets safely past the IRS and directing them to those whom you desire and in the manner you wish.\n ",
			"The chess piece depicted in the above logo is a white knight.  While each chess piece moves in a different manner, most pieces move linearly.  The knight is different.  It has very unique moves that, when used skillfully and strategically, can be extremely effective in protecting other valuable chess pieces, especially the Queen and King.  In chess, the player with the white pieces is allowed the first move."			
	};
	public Introduction(Document document, PdfWriter writer) {
		super(document, writer);
	}
	
	public void draw()
	{
		page1();
	}
	private void page1()
	{
		drawBorder(pageIcon);

		try
		{
			Image logo = Image.getInstance(Locations.getImageLocation() +  "teaglogo2.png");
			Rectangle rct = new Rectangle(prctTop);
			rct.setTop(rct.getTop() - _1_2TH);
			this.drawDiagram(logo, rct, TOP, 100);
			
			
			rct = new Rectangle(prctFull);
			rct.setLeft(rct.getLeft() + (prctFull.getWidth() *.15f));
			rct.setRight(rct.getRight() - (prctFull.getWidth() *.15f));

			if( userInfo.isSingle()) {
				rct =this.calcSectionRect(rct, "", singleIntro, 0, 14,11.5f);
				float height = rct.getHeight();
				rct.setTop(prctFull.getTop() - ((prctFull.getHeight() - height) / 2));
				rct.setBottom(rct.getTop() - height);
				drawSection(rct, "", singleIntro, 0, 16,12.5f);
				
			} else {
				rct =this.calcSectionRect(rct, "", intro, 0, 14,11.5f);
				float height = rct.getHeight();
				rct.setTop(prctFull.getTop() - ((prctFull.getHeight() - height) / 2));
				rct.setBottom(rct.getTop() - height);
				drawSection(rct, "", intro, 0, 16,12.5f);
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

}
