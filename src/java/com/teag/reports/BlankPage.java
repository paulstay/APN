package com.teag.reports;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

public class BlankPage extends Page {
	public String title = "Blank Page";
	/**
	 * @param document
	 * @param writer
	 */
	public BlankPage(Document document, PdfWriter writer) {
		super(document, writer);
	}
	
	public void draw()
	{
		page1();
	}
	
	private void page1()
	{
		drawBorder(pageIcon);
		drawHeader(client, "");
	}
}
