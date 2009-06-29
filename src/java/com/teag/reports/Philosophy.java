/*
 * Created on May 4, 2005
 *
 */
package com.teag.reports;

import java.util.Vector;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
/**
 * @author Paul Stay
 *
 */
public class Philosophy extends Page {

	private Vector<String> philosophy;
	private Vector<String> headings;	
	/**
	 * @param document
	 * @param writer
	 */
	public Philosophy(Document document, PdfWriter writer) {
		super(document, writer);
		philosophy = new Vector<String>();
		headings = new Vector<String>();
	}
	
	public void addPhilosophy(String heading, String text)
	{
		
		headings.add(heading);
		philosophy.add(text);
	}
	
	
	public void draw()
	{
		page1();
	}

	private void page1()
	{
		float headingSize = 13f;
		float textSize = 11f;
		drawBorder(pageIcon);
		drawHeader(client, "Family Financial Philosophy");
		Object h[];
		Object t[];
		Rectangle rct = new Rectangle(prctFull);		
		h = headings.toArray();
		t = philosophy.toArray();
		
		for(int i = 0; i < h.length; i++)
		{
			String txt[] = new String[1];
			txt[0] = (String)t[i];
			Rectangle rctx = calcSectionRect(rct, (String)h[i], txt, 0, headingSize, textSize);
			if((rct.getTop() - rctx.getHeight()) < prctFull.getBottom())
			{
				// Wont fit
				newPage();
				drawBorder(pageIcon);
				drawHeader(client, "Family Financial Philosophy (cont.)");
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
