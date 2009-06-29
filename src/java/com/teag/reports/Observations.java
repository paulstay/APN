/*
 * Created on May 4, 2005
 *
 */
package com.teag.reports;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * Date April 16, 2007  Changed to handle multiple pages.
 *
 */
public class Observations extends Page {

	private String observations[] = null;
	
	/**
	 * @param document
	 * @param writer
	 */
	public Observations(Document document, PdfWriter writer) {
		super(document, writer);
	}
	
	public void draw()
	{
		page1();
	}
	
	
	/**
	 * @return Returns the observations.
	 */
	public String[] getObservations() {
		return observations;
	}

	private void page1() {
		if( observations != null) {
			float headingSize = 13f;
			float textSize = 11f;
			drawBorder(pageIcon);
			drawHeader(client, "Observations");
			Rectangle rct = new Rectangle(prctFull);
			for( int i=0; i < observations.length; i++) {
				String txt[] = new String[1];
				txt[0] = Integer.toString(i+1) + ". " +observations[i];
				Rectangle rctx = calcSectionRect(rct, " ", txt, 0, headingSize, textSize);
				if( (rct.getTop() - rctx.getHeight()) < prctFull.getBottom()) {
					newPage();
					drawBorder(pageIcon);
					drawHeader(client,"Observations (cont.)");
					rct = new Rectangle(prctFull);
				}
				rctx = drawSection(rct, " ", txt, 0, 14, 11f);
				rct.setTop(rct.getTop() - rctx.getHeight() + (_1_4TH + _1_8TH));
			}
			
		}
	}
	/**
	 * @param observations The observations to set.
	 */
	public void setObservations(String[] observations) {
		this.observations = observations;
	}
}
