package com.aes.pdf;

import java.awt.Color;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.estate.pdf.Label;
import com.estate.pdf.Locations;
import com.estate.pdf.Page;
import com.estate.pdf.PageUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * Date June 25, 2008
 * Description, Tital page for "Advance Estate Strategies"
 *
 */

public class TitlePage extends Page {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//app_root += "\\webapp\\images\\";
		String appRoot = "E:\\Dev\\Teag\\";
		Locations.imageLocation = appRoot + "\\webapp\\images\\";
		Locations.fontLocation = appRoot + "\\webapp\\fonts\\";
		Document doc = new Document(PageSize.LETTER.rotate());
		try {
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("HelloWorld.pdf"));
			writer.setPdfVersion(PdfWriter.VERSION_1_6);
			doc.open();
			TitlePage tp = new TitlePage(doc, writer);
			tp.setFirstName("Paul and Diane");
			tp.setLastName("Stay");
			tp.setAdvisor("Pacific Advisors");
			tp.draw();
			doc.close();
		} catch (Exception e) {
			
		}
	}
	BaseFont font;
	String firstName;
	String lastName;
	
	String advisor;

	public TitlePage(Document document, PdfWriter writer) {
		super(document, writer);
		this.document = document;
		this.writer = writer;
		
		font = PageUtils.LoadFont("times.ttf");
	}
	
	public void draw() {
		page1();
		newPage();
	}
	
	public String getAdvisor() {
		return advisor;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void page1() {
		PdfContentByte cb = writer.getDirectContent();
		
		// Display the AES image

		try {
			// Print the AES logo on the page
			Rectangle rect = new Rectangle(document.getPageSize());
			Rectangle lRect;
			Image aes = Image.getInstance(Locations.imageLocation + "AESLogo_Lrg.png");
			aes.scalePercent(40f);
			document.add(aes);
			
			// Print out the Client Information
			
			rect = new Rectangle(prctFull);
			rect.setTop(rect.getTop() - (rect.getTop()/4 +72f));
			Label label = new Label(writer);
			
			Color clr = new Color(0xF1A629);
			//lRect = label.displayLabel(rect, "Planning Strategies For", font, 24, clr, Label.LBL_CENTER, Label.LBL_TOP);
			//rect.setTop(lRect.getBottom());
			String client = userInfo.getFirstName() + " " + userInfo.getLastName();
			lRect = label.displayLabel(rect, client, font, 24, clr, Label.LBL_CENTER, Label.LBL_TOP);
			
			// Print out Advisor or Firm and date of presentation
			
			clr = new Color(0,0,0);
			rect.setTop(lRect.getBottom() - _1_2TH);
			lRect = label.displayLabel(rect, "Prepared By", font, 8, clr, Label.LBL_CENTER, Label.LBL_TOP);
			rect.setTop(lRect.getBottom()- _1_4TH);
			lRect = label.displayLabel(rect, userInfo.getAdvisor1(), font, 20, clr, Label.LBL_CENTER, Label.LBL_TOP);
			rect.setTop(lRect.getBottom());
			lRect = label.displayLabel(rect, userInfo.getAdvisor2(), font, 20, clr, Label.LBL_CENTER, Label.LBL_TOP);
			rect.setTop(lRect.getBottom());
			lRect = label.displayLabel(rect, userInfo.getAdvisor3(), font, 20, clr, Label.LBL_CENTER, Label.LBL_TOP);
			rect.setTop(lRect.getBottom());
			lRect = label.displayLabel(rect, userInfo.getAdvisor4(), font, 20, clr, Label.LBL_CENTER, Label.LBL_TOP);
			rect.setTop(lRect.getBottom());
			lRect = label.displayLabel(rect, date.format(new Date()), font, 14, clr, Label.LBL_CENTER, Label.LBL_TOP);
			
			// Print copyright symbol and AES at the bottom of the page
			char cs = 0x00a9;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			String copyRight = "Advance Estate Strategies " + cs + " " + dateFormat.format(new Date());
			float crWidth;
			float crLeft;
			
			crWidth = font.getWidthPoint(copyRight,8);
			cb.beginText();
			cb.setFontAndSize(font,	8);
			cb.setRGBColorFill(0, 0, 0);
			
			crLeft = (document.getPageSize().getRight() - crWidth)/2;
			cb.setTextMatrix(crLeft, .375f * 72);
			cb.showText(copyRight);
			cb.endText();
		} catch (Exception e) {
			
		}
	}

	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
