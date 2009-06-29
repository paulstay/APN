/*
 * Created on May 3, 2005
 *
 */
package com.teag.reports;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 *
 */
public class Objectives extends Page {

	/**
	 * @param document
	 * @param writer
	 */
	
	private String objectives[];
	public Objectives(Document document, PdfWriter writer) {
		super(document, writer);
	}
	
	
	public void draw()
	{
		page1();
	}
	
	/**
	 * @return Returns the objectives.
	 */
	public String[] getObjectives() {
		return objectives;
	}
	
	

	private void page1()
	{
		drawBorder(pageIcon);
		drawHeader(client, "Objectives");
		if(objectives != null)
		{
			drawSection(prctTop, " ", objectives, 1, 14,11f);
		}
		
	}
	/**
	 * @param objectives The objectives to set.
	 */
	public void setObjectives(String[] objectives) {
		this.objectives = objectives;
	}
}
