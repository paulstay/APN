/*
 * Created on May 4, 2005
 *
 */
package com.teag.reports;

import java.util.Vector;

import com.estate.pdf.Locations;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 *
 */
public class Recommendations extends Page {

	private Vector<String> recommendation;
	private Vector<String> headings;	
	private int count;
	
	
	/**
	 * 
	 * @param document
	 * @param writer
	 */
	public Recommendations(Document document, PdfWriter writer) {
		super(document, writer);
		recommendation = new Vector<String>();
		headings = new Vector<String>();
		count = 1;
		pageIcon = 2;
	}
	
	
	public void addRecommendation(String heading, String text)
	{
		String h = "";
		if(heading.length() > 0)
		{
			h +=  count + ". " + heading;
			count++;
		}
		headings.add(h);
		recommendation.add(text);
	}
	
	public void draw()
	{
		page1();
		newPage();
		page2();
	}
	
	
	private void page1()
	{
		float headingSize = 14f;
		float textSize = 12.5f;

		drawBorder(pageIcon);
		String info[];
		
		if( userInfo.isSingle()) {
			info = new String[] 
          		{
       				"The Advanced Estate Planning described in our metaphor of the chess match with the IRS will be greatly accomplished with the following recommended moves. Here are several effective tax saving moves your planning team suggests we can make currently. These moves still enable you to retain great control over both management and distribution of your estate during your lifetime.\n ",
       				"Fortunately, these recommended changes enable us to significantly reduce the projected estate tax liability. Some of your assets (chess pieces) will change in the future, as will your objectives and circumstances. These first strategic moves are important, and show significant improvement, laying the foundation for preserving even more of your family's wealth in the future as your assets continue to grow.\n ",
      				"As we update our strategies and techniques on an annual basis, additional observations and solutions will be recommended as appropriate.\n ",
          		};
	
		} else {
			info = new String[] 
			          		{
			          				"The Advanced Estate Planning described in our metaphor of the chess match with the IRS will be greatly accomplished with the following recommended moves. Here are several effective tax saving moves your planning team suggests we can make currenty. These moves still enable you to retain great control over both management and distribution of your estate during your lifetime(s).\n ",
			          				"Fortunately, these recommended changes enable us to significantly reduce the projected estate tax liability. Some of your assets (chess pieces) will change in the future, as will your objectives and circumstances. These first strategic moves are important, and show significant improvement, laying the foundation for preserving even more of your family's wealth in the future as your assets continue to grow.\n ",
			          				"As we update our strategies and techniques on an annual basis, additional observations and solutions will be recommended as appropriate.\n ",
			          		};
			
		}
		
		try
		{
			Image logo = Image.getInstance(Locations.getImageLocation() +  "teaglogo2.png");
			Rectangle rct = new Rectangle(prctTop);
			rct.setTop(rct.getTop() - _1_2TH);
			this.drawDiagram(logo, rct, TOP, 100);
			
			
			rct = new Rectangle(prctFull);
			rct.setLeft(rct.getLeft() + (prctFull.getWidth() *.15f));
			rct.setRight(rct.getRight() - (prctFull.getWidth() *.15f));
			
			
			
			
			rct =this.calcSectionRect(rct, "", info, 0, headingSize, textSize);
			float height = rct.getHeight();
			rct.setTop(prctFull.getTop() - ((prctFull.getHeight() - height) / 2));
			rct.setBottom(rct.getTop() - height);
			this.drawSection(rct, "Combined Planning Team Recommendations", info, 0, headingSize, textSize);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}

	private void page2()
	{
		float headingSize = 13f;
		float textSize = 11f;
		drawBorder(pageIcon);
		drawHeader(client, "Recommendations");
		Object h[];
		Object t[];
		Rectangle rct = new Rectangle(prctFull);		
		h = headings.toArray();
		t = recommendation.toArray();
		
		for(int i = 0; i < h.length; i++)
		{
			String txt[] = new String[1];
			txt[0] = (String)t[i];
			Rectangle rctx = calcSectionRect(rct, (String)h[i], txt, 0, headingSize, textSize);
			if((rct.getTop() - rctx.getHeight()) < (prctFull.getBottom() - _1_8TH))
			{
				// Wont fit
				newPage();
				drawBorder(pageIcon);
				drawHeader(client, "Recommendations (cont.)");
				rct = new Rectangle(prctFull);
				rctx = drawSection(rct, (String)h[i], txt, 0, headingSize, textSize);
			}
			else
			{
				rctx = drawSection(rct, (String)h[i], txt, 0, headingSize, textSize);
			}
			rct.setTop(rct.getTop() - rctx.getHeight() - _1_16TH);
		}
	}	
}
