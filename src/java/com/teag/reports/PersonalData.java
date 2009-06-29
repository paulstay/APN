/*
 * Created on May 6, 2005
 *
 */
package com.teag.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.estate.db.DBObject;
import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Paul Stay
 * 
 */
public class PersonalData extends Page {

	private DBObject dbObj = new DBObject();

	private int clientID;

	/**
	 * @param document
	 * @param writer
	 */

	private Vector<String[]> rows;

	private Vector<String[]> addrRows;

	private Vector<String[]> advisors;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy");

	String header[] = { "Clients", "Birth Dates",
			"Age in " + df.format(new Date()),
			"Life Expectancy\n(Avg. per tables)", "", "",
			"[bold=1][align=center][align=center][align=center][][]" };

	String childrenHeader[] = { "Children / Occupation\n ", "", "",
			"Spouse / Occupation\n ", "Number of Grandchildren\n ",
			"Grandchildren\n ", "[bold=1][][][align=center][align=center][]"

	};

	/*
	 * { "", "", "", "", "", "", "[][][][][][]" }
	 * 
	 */

	public PersonalData(Document document, PdfWriter writer) {
		super(document, writer);
		rows = new Vector<String[]>();
		addrRows = new Vector<String[]>();
		advisors = new Vector<String[]>();
	}

	public void addAddress(String work, String home, String other, String email) {
		String address[] = new String[5];

		address[0] = work;
		address[1] = home;
		address[2] = other;
		address[3] = email;
		address[4] = "[valign=top][valign=top][valign=top][valign=top]";

		addrRows.add(address);

	}

	public void addAdvisor(String type, String name, String address) {
		String advisor[] = new String[3];

		advisor[0] = type;
		advisor[1] = name;
		advisor[2] = address;

		advisors.add(advisor);
	}

	public void addChildInfo(String Name, String occupation, String bDay,
			String age, String spouse, String spouseOccupation,
			String numChildren, String children) {

		String spouseInfo;
		String spouseStyle;

		if (spouse.length() == 0) {
			spouseInfo = "none";
			spouseStyle = "[align=center,border=b]";
		} else {
			spouseInfo = spouse + " / " + spouseOccupation;
			spouseStyle = "[align=center,border=b]";
		}

		String row[] = {
				Name + " / " + occupation,
				bDay,
				age,
				spouseInfo,
				numChildren,
				children,
				"[border=b][border=b,align=center][border=b,align=center]"
						+ spouseStyle + "[border=b,align=center][border=b]" };

		rows.add(row);
	}

	public void addClientInfo(String name, String bDay, String age, String le) {
		String row[] = { name, bDay, age, le, "", "",
				"[][align=center][align=center][align=center][][]" };

		rows.add(row);
	}

	public void draw() {
		page1();
	}

	private void drawAdvisorsTable(Rectangle rct) {
		// Vector rows = new Vector();
		String rows[][] = new String[3][advisors.size() + 1];

		String styles[] = { "[bold=1]", "[underline=1,valign=top]",
				"[valign=top]" };

		for (int r = 0; r < rows.length; r++) {
			int col;
			for (col = 0; col < advisors.size(); col++) {
				String sCol[] = advisors.get(col);
				rows[r][col] = sCol[r];
			}
		}

		for (int r = 0; r < rows.length; r++) {
			String format = "";
			for (int c = 0; c < rows[r].length - 1; c++) {
				format += styles[r];

			}
			rows[r][rows[r].length - 1] = format;

		}
		PageTable table = new PageTable(writer);
		table.setTableFont("GARA.ttf");
		table.setTableFontBold("GARABD.ttf");
		rct.setRight(rct.getRight() + _1_2TH);

		table.setTableFormat(rct, 1);
		String advHeader[][] = { { "Advisors\n ", "[align=center,bold=1]" } };
		table.setFontSize(table.getFontSize() + 2);
		table.buildTableEx(advHeader);
		table.drawTable();

		Rectangle rctx = table.getResultRect();

		rct = new Rectangle(prctFull);
		rct.setTop(rctx.getBottom());

		table = new PageTable(writer);
		table.setTableFont("GARA.ttf");
		table.setTableFontBold("GARABD.ttf");
		rct.setRight(rct.getRight() + _1_2TH);

		table.setTableFormat(rct, rows[0].length - 1);
		table.setFontSize(table.getFontSize() - 1);

		table.buildTableEx(rows);
		table.drawTable();

	}

	/**
	 * @return Returns the clientID.
	 */
	public int getClientID() {
		return clientID;
	}

	/**
	 * @return Returns the dbObj.
	 */
	public DBObject getDbObj() {
		return dbObj;
	}

	private void page1() {
		this.drawBorder(pageIcon);
		this.drawHeader(client, "Personal Data");

		PageTable table = new PageTable(writer);
		table.setTableFont("GARA.ttf");
		table.setTableFontBold("GARABD.ttf");
		Rectangle rct = new Rectangle(prctFull);
		rct.setRight(rct.getRight() + _1_2TH);
		table.setTableFormat(rct, 6);
		table.setFontSize(table.getFontSize() - 1);
		float widths[] = { .35f, .10f, .07f, .21f, .12f, .15f };
		table.setColumnWidths(widths);

		String cells[][] = new String[rows.size()][0];

		for (int i = 0; i < cells.length; i++) {
			cells[i] = rows.get(i);
		}
		table.setPaddingBottom(3);
		table.buildTableEx(cells);
		table.drawTable();
		Rectangle resRct = table.getResultRect();

		rct = new Rectangle(prctFull);
		rct.setTop(resRct.getBottom() - _1_4TH);
		table = new PageTable(writer);
		table.setTableFont("GARA.ttf");
		table.setTableFontBold("GARABD.ttf");
		rct.setRight(rct.getRight() + _1_2TH);
		table.setTableFormat(rct, 4);
		table.setFontSize(table.getFontSize() - 1);

		cells = new String[addrRows.size()][];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = addrRows.get(i);
		}

		table.buildTableEx(cells);
		table.drawTable();

		resRct = table.getResultRect();
		rct = new Rectangle(prctFull);
		rct.setTop(resRct.getBottom() + _1_8TH);

		drawAdvisorsTable(rct);

	}

	/**
	 * @param clientID
	 *            The clientID to set.
	 */
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public void setMarriageDate(String date) {
		String marriage[] = { "Anniversary - " + date, "", "", "", "", "",
				"[bold=1][][][][][]" };
		rows.add(marriage);

	}

	public void startAddressTable() {
		String addrHeader[] = new String[5];

		addrHeader[0] = "Work Address\n ";
		addrHeader[1] = "Home Address\n ";
		addrHeader[2] = "Other Residence\n ";
		// addrHeader[3] = "Email address\n ";
		addrHeader[3] = " ";
		addrHeader[4] = "[bold=1][bold=1][bold=1][bold=1]";

		addrRows.add(addrHeader);

	}

	public void startChildrenTable() {
		rows.add(childrenHeader);
	}

	public void startClientTable() {
		rows.add(header);
	}
}
