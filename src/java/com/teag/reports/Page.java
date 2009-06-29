package com.teag.reports;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

public class Page extends com.estate.pdf.Page {

	public String client = "";

	public Page(Document document, PdfWriter writer) {
		super(document, writer);
	}
	

	@Override
	public String getClient() {
		return client;
	}
	@Override
	public void setClient(String client) {
		this.client = client;
	}
}
