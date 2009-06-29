package com.estate.pdf;

import java.awt.Color;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.bean.PdfBean;

/**
 * @author Paul Stay
 * 
 */
public class Page {

	// values for label alignment and anchor
	protected static final int LBL_LEFT = 1;

	protected static final int LBL_CENTER = 2;

	protected static final int LBL_RIGHT = 3;

	protected static final int LBL_TOP = 4;

	protected static final int LBL_BOTTOM = 5;

	protected static final int LBL_FIRST = 6;

	// Values for diagram alignment
	protected static final int LEFT = 1;

	protected static final int RIGHT = 2;

	protected static final int TOP = 4;

	protected static final int BOTTOM = 8;

	protected static final int CNTR_LR = LEFT + RIGHT;

	protected static final int CNTR_TB = TOP + BOTTOM;

	// These are public on purpose
	public static final float _1_2TH = (1f / 2f) * 72f;

	public static final float _1_4TH = (1f / 4f) * 72f;

	public static final float _1_8TH = (1f / 8f) * 72f;

	public static final float _1_16TH = (1f / 16f) * 72f;

	public static final float _1_32TH = (1f / 32f) * 72f;

	public static final float _1_64TH = (1f / 64f) * 72f;

	public static final Rectangle prctLeft = new Rectangle(1.6233f * 72,
			0.854f * 72, 5.5365f * 72, 7.25000f * 72);

	public static final Rectangle prctRight = new Rectangle(5.8939f * 72,
			0.854f * 72, 9.7998f * 72, 7.25000f * 72);

	public static final Rectangle prctTop = new Rectangle(1.6233f * 72,
			(3.901f + .25f) * 72, 9.7998f * 72, 7.25000f * 72);

	public static final Rectangle prctBottom = new Rectangle(1.6233f * 72,
			0.854f * 72, 9.7998f * 72, (3.5496f + .25f) * 72);

	public static final Rectangle prctULeft = new Rectangle(1.6233f * 72,
			(3.901f + .25f) * 72, 5.5365f * 72, 7.25000f * 72);

	public static final Rectangle prctLLeft = new Rectangle(1.6233f * 72,
			0.854f * 72, 5.5365f * 72, (3.5496f + .25f) * 72);

	public static final Rectangle prctURight = new Rectangle(5.8939f * 72,
			(3.901f + .25f) * 72, 9.7998f * 72, 7.25000f * 72);

	public static final Rectangle prctLRight = new Rectangle(5.8939f * 72,
			0.854f * 72, 9.7998f * 72, (3.5496f + .25f) * 72);

	public static final Rectangle prctFull = new Rectangle(1.6233f * 72,
			0.854f * 72, 9.7998f * 72, 7.25000f * 72);

	public static final float horizLineY = (3.7187f + .25f) * 72;

	public static final float verLineX = 5.7212f * 72;

	protected static final DecimalFormat percent = new DecimalFormat("##.##%");

	protected static final DecimalFormat fullPercent = new DecimalFormat("##.####%");

	protected static final DecimalFormat dollar = new DecimalFormat(
			"$#,###,###,###");

	protected static final DecimalFormat integer = new DecimalFormat("#####");

	protected static final DecimalFormat number = new DecimalFormat(
			"#,###,###,###");

	protected static final DecimalFormat decimal = new DecimalFormat("###.####");

	protected static final SimpleDateFormat date = new SimpleDateFormat("M/d/y");
	public static float I2P(float inches) {
		return (inches * 72);
	}

	public static float I2P(int whole, float decimal) {
		float res;

		res = whole + decimal;
		res = res * 72f;
		return res;
	}

	public static float I2P(int whole, int numerator, int denominator) {
		float res;

		res = whole + (numerator / denominator);
		res = res * 72f;
		return res;
	}

	// Variables common to a page.
	protected PdfWriter writer;
	
	protected Document document;

	protected BaseFont font;
	
	protected int pageNum = -1;

	protected int pageCount = 0;

	protected int pageIcon = 0;
	
	protected int pgRed = makeColor(192, 0, 0);

	protected int pgGreen = makeColor(0, 160, 0);

	protected int pgBlue = makeColor(0, 0, 192);

	protected int pgBlack = makeColor(0,0,0);

	protected int LFT = Element.ALIGN_LEFT;
	protected int CTR = Element.ALIGN_CENTER;
	
	protected int RGT = Element.ALIGN_RIGHT;
	public PdfBean userInfo;
	public String clientFirstName = "";
	public String clientLastName = "";
	

	public String spouseFirstName = "";
	public String spouseLastName = "";	
	public String client = "";
	public Page(Document document, PdfWriter writer) {
		// Save for later
		this.writer = writer;
		this.document = document;

		font = PageUtils.LoadFont("GARA.ttf");

	}
	protected Rectangle adjustPlacement(Rectangle rctIn) {
		Rectangle rctOut = new Rectangle(rctIn);

		rctOut.setLeft(rctOut.getLeft() - _1_4TH);
		rctOut.setBottom(rctOut.getBottom() - _1_4TH);
		return rctOut;
	}
	protected Rectangle calcSectionRect(Rectangle rct, String heading,
			String lines[], int bullet) {
		Rectangle r;
		Rectangle secRect = new Rectangle(rct);
		PageSection psc = new PageSection(writer);
		r = psc.calcSectionRect(secRect, heading, lines, bullet);
		return r;
	}
	protected Rectangle calcSectionRect(Rectangle rct, String heading,
			String lines[], int bullet, float headingSize, float textSize) {
		Rectangle r;
		Rectangle secRect = new Rectangle(rct);
		PageSection psc = new PageSection(writer);
		float hs = psc.getHeadingSize();
		float ts = psc.getTextSize();

		psc.setHeadingSize(headingSize);
		psc.setTextSize(textSize);
		r = psc.calcSectionRect(secRect, heading, lines, bullet);
		psc.setHeadingSize(hs);
		psc.setTextSize(ts);

		return r;

	}
	protected void drawBorder() {
		drawBorder(pageIcon, "");
	}
	protected void drawBorder(int Icon) {
		drawBorder(Icon, "");
	}
	protected void drawBorder(int Icon, String toolName) {
		// Do the page border
		PageBorder pgb = new PageBorder(writer);
		pgb.setLicense(userInfo.getPlannerFirstName() + " " + userInfo.getPlannerLastName());
		if (pageNum >= 0) {
			pgb.draw(document, Icon, "" + pageNum, toolName);
		} else {
			pgb.draw(document, Icon, "", toolName);
		}

	}
	
	protected void drawDiagram(Image img, Rectangle rct, int align, int DPI) {
		Rectangle diagRect = new Rectangle(rct);
		Diagram d = new Diagram(img);
		d.placeDiagram(diagRect, document, align, DPI);

	}

	protected void drawDiagram(String name, Rectangle rct, int align) {
		Rectangle diagRect = new Rectangle(rct);
		Diagram d = new Diagram(name);
		d.placeDiagram(diagRect, document, align);

	}

	protected void drawDiagram(String name, Rectangle rct, int align, int DPI) {
		Rectangle diagRect = new Rectangle(rct);
		Diagram d = new Diagram(name);
		d.placeDiagram(diagRect, document, align, DPI);

	}

	protected Rectangle drawDiagramLabel(String text, Rectangle rct, int align,
			int anchor) {
		Color textColor = new Color(64, 64, 64); // 98,98,98
		return (this.drawLabel(text, rct, "GARA.TTF", textColor, 10, align,
				anchor));

	}

	protected void drawFilledRect(Rectangle rct, Color color) {
		PdfContentByte cb = writer.getDirectContentUnder();
		// Do the lower left box
		cb.setLineWidth(0);
		cb.setRGBColorStroke(255, 255, 255);
		cb.rectangle(rct.getLeft(), rct.getBottom(), rct.getWidth(), rct.getHeight());
		cb.setColorFill(color);
		cb.closePathFillStroke();
	}

	protected void drawHeader(String header, String title) {
		PageHeader pgh = new PageHeader(writer);
		Rectangle rct = new Rectangle(document.getPageSize());
		pgh.writeHeader(rct, header, title);

	}

	protected Rectangle drawLabel(String text, Rectangle rct, String fontName,
			Color color, float ptSize, int align, int anchor) {
		Rectangle lblRect = new Rectangle(rct);
		Label lbl = new Label(writer);
		BaseFont font = PageUtils.LoadFont(fontName);
		lblRect = lbl.displayLabel(lblRect, text, font, ptSize, color, align,
				anchor);
		return (lblRect);
	}

	protected void drawLine(float x1, float y1, float x2, float y2, Color color) {
		PdfContentByte cb = writer.getDirectContentUnder();
		// Set our line color
		cb.setColorStroke(color);
		cb.setLineWidth(.75f);

		// do the line
		cb.moveTo(x1, y1);
		cb.lineTo(x2, y2);
		cb.stroke();

	}

	protected void drawRectangle(Rectangle rct, Color color) {
		drawLine(rct.getLeft(), rct.getTop(), rct.getLeft(), rct.getBottom(), color); // Left
		drawLine(rct.getLeft(), rct.getTop(), rct.getRight(), rct.getTop(), color); // Top
		drawLine(rct.getRight(), rct.getTop(), rct.getRight(), rct.getBottom(), color); // right
		drawLine(rct.getLeft(), rct.getBottom(), rct.getRight(), rct.getBottom(), color); // Bottom

	}

	protected Rectangle drawSection(Rectangle rct, String heading,
			String lines[], int bullet) {
		Rectangle r;
		Rectangle secRect = new Rectangle(rct);
		PageSection psc = new PageSection(writer);
		r = psc.displaySection(secRect, heading, lines, bullet);
		return r;
	}

	protected Rectangle drawSection(Rectangle rct, String heading,
			String lines[], int bullet, float headingSize, float textSize) {
		Rectangle r;
		Rectangle secRect = new Rectangle(rct);
		PageSection psc = new PageSection(writer);
		float hs = psc.getHeadingSize();
		float ts = psc.getTextSize();

		psc.setHeadingSize(headingSize);
		psc.setTextSize(textSize);
		r = psc.displaySection(secRect, heading, lines, bullet);
		psc.setHeadingSize(hs);
		psc.setTextSize(ts);

		return r;
	}

	protected void drawSpacingGrid(Rectangle rct, float spacing, Color color) {
		PdfContentByte cb = writer.getDirectContentUnder();

		cb.setLineWidth(.25f);
		cb.setColorStroke(color);
		for (float x = 0; x < rct.getRight(); x += spacing) {
			cb.moveTo(x, 0);
			cb.lineTo(x, rct.getTop());
		}

		for (float y = 0; y < rct.getTop(); y += spacing) {
			cb.moveTo(0, y);
			cb.lineTo(rct.getRight(), y);
		}

		cb.stroke();

	}

	protected void drawSpacingGrid(Rectangle rct, float spacings[],
			Color colors[]) {
		for (int i = 0; i < spacings.length; i++) {
			drawSpacingGrid(rct, spacings[i], colors[i]);
		}
	}

	protected void drawTaxPie(Rectangle rct, double totalValue, double tax,
			String taxLabel, String netLabel) {
		double taxPercent = (tax / totalValue) * 100;
		double netValuePercent = 100 - taxPercent;

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue(taxLabel, taxPercent);
		dataset.setValue(netLabel, netValuePercent);

		PiePlot3D plot = new PiePlot3D(dataset);
		plot.setLabelGenerator(new StandardPieItemLabelGenerator());
		plot.setInsets(new Insets(0, 5, 5, 5));
		plot.setToolTipGenerator(new CustomeGenerators.CustomToolTipGenerator());
		plot.setLabelGenerator(new CustomeGenerators.CustomLabelGenerator());
		plot.setSectionPaint(0, new Color(pgRed));
		plot.setSectionPaint(1, new Color(pgGreen));
		plot.setForegroundAlpha(.6f);
		plot.setOutlinePaint(Color.white);
		plot.setBackgroundPaint(Color.white);

		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,
				plot, true);

		chart.setBackgroundPaint(Color.white);
		chart.setAntiAlias(true);

		Rectangle page = rct;

		try {
			Image img = Image.getInstance(chart.createBufferedImage((int) page
					.getWidth(), (int) page.getHeight()), null);
			drawDiagram(img, rct, 0, 72);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public String getClient() {
		return client;
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	/**
	 * @return Returns the pageCount.
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @return Returns the pageNum.
	 */
	public int getPageNum() {
		return pageNum;
	}

	public String getSpouseFirstName() {
		return spouseFirstName;
	}

	public String getSpouseLastName() {
		return spouseLastName;
	}

	protected PageTable getTable(Rectangle rct, int columns) {
		PageTable t = new PageTable(writer);
		t.setTableFormat(rct, columns);
		t.setTableFont("GARA.TTF");
		t.setTableFontBold("GARABD.TTF");
		return t;
	}

	public PdfBean getUserInfo() {
		return userInfo;
	}

	protected Image loadImage(String name) {
		Image img = null;
		try {
			img = Image.getInstance(Locations.getImageLocation() + name);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return img;
	}

	public int makeColor(int Red, int Green, int Blue) {
		int color = (Red << 16) + (Green << 8) + Blue;
		return color;
	}

	protected void newPage() {
		try {
			document.newPage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		pageCount++;
		pageNum++;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	/**
	 * @param pageNum
	 *            The pageNum to set.
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}

	public void setSpouseLastName(String spouseLastName) {
		this.spouseLastName = spouseLastName;
	}
	
	public void setUserInfo(PdfBean userInfo) {
		this.userInfo = userInfo;
	}
	protected Rectangle toPage(float orgX, float orgY, Rectangle orgRect) {
		Rectangle rct = new Rectangle(orgRect);

		rct.setLeft(rct.getLeft() + orgX);
		rct.setRight(rct.getRight() + orgX);
		rct.setTop(rct.getTop() + orgY);
		rct.setBottom(rct.getBottom() + orgY);

		return rct;
	}
	protected Rectangle translateRect(Rectangle rct) {
		Rectangle rctPage = new Rectangle(document.getPageSize());

		rct = new Rectangle(rct.getLeft(), rctPage.getTop() - rct.getBottom(), rct
				.getRight(), rctPage.getTop() - rct.getTop());
		return (rct);
	}
	
}
