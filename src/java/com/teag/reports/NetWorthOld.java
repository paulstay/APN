/*
 * Created on May 6, 2005
 *
 */
package com.teag.reports;

import java.util.HashMap;

import com.estate.db.DBObject;
import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.db.TableSumation;


/**
 * @author Paul Stay
 * 
 */
public class NetWorthOld extends Page {

	private DBObject dbObj = new DBObject();

	private int clientID;

	/**
	 * @param document
	 * @param writer
	 */
	public NetWorthOld(Document document, PdfWriter writer) {
		super(document, writer);
	}

	public void draw() {
		page1();
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
		double totalAssets = 0.0;
		this.drawBorder(pageIcon);
		this.drawHeader(client, "Estimate of Net Worth");
		Rectangle rct = new Rectangle(prctFull);
		TableSumation ts = new TableSumation();

		float adj = rct.getWidth() / 4;
		rct.setLeft(rct.getLeft() + adj);
		rct.setRight(rct.getRight() - adj);
		PageTable t = this.getTable(rct, 3);
		this.setTableFontandSize(t);
		float widths[] = { .05f, .50f, .45f };
		t.setColumnWidths(widths);

		String liquidAssets[] = { "LIQUID ASSETS", "", "",
				"[colspan=2,bold=1][][]"

		};
		String illiquidAssets[] = { "ILLIQUID ASSETS", "", "",
				"[colspan=2,bold=1][][]"

		};

		String personalAssets[] = { "PERSONAL USE ASSETS", "", "",
				"[colspan=2,bold=1][][]"

		};
		String sTotalAssets[] = { "TOTAL ASSSETS:", "", "",
				"[colspan=2,bold=1][][bold=1,underline=1,align=right]"

		};

		String lessLiabilities[] = {
				"LESS LIABILITIES:",
				"",
				"",
				"[colspan=2,bold=1][][bold=1,underline=1,align=right,color="
						+ pgRed + "]"

		};

		String estimatedNetWorth[] = { "ESTIMATED NET WORTH:", "", "",
				"[colspan=2,bold=1][][bold=1,underline=1,align=right]"

		};
		String blank[] = { " ", "", "", "[colspan=3][][]" };
		String total[] = { " ", "", "", "[][][bold=1,underline=1,align=right]" };

		String row[][] = new String[1][];

		row[0] = liquidAssets;

		t.buildTableEx(row);

		String workRow[] = new String[4];

		workRow[0] = "";
		workRow[3] = "[][][align=right]";

		workRow[1] = "Cash and Equivalents";
		workRow[2] = number.format(ts.getSum("CASH", "VALUE", "OWNER_ID="
				+ this.clientID));
		row[0] = workRow;
		t.buildTableEx(row);

		workRow[1] = "Municipal Bonds";
		workRow[2] = number.format(ts.getSum("BONDS", "VALUE", "OWNER_ID="
				+ this.clientID));
		row[0] = workRow;
		t.buildTableEx(row);

		workRow[1] = "Securities";
		workRow[2] = number.format(ts.getSum("SECURITIES", "VALUE", "OWNER_ID="
				+ this.clientID));
		row[0] = workRow;
		t.buildTableEx(row);

		workRow[1] = "Life Insurance";
		workRow[2] = number.format(ts.getSum("LIFE_INSURANCE", "VALUE",
				"OWNER_ID=" + this.clientID));
		row[0] = workRow;
		t.buildTableEx(row);

		totalAssets += ts.getTotal();
		total[2] = number.format(ts.getTotal());
		row[0] = total;
		t.buildTableEx(row);

		ts.clearTotal();

		row[0] = illiquidAssets;
		t.buildTableEx(row);

		workRow[1] = "Retirement Plans";
		workRow[2] = number.format(ts.getSum("RETIREMENT", "VALUE", "OWNER_ID="
				+ this.clientID));
		row[0] = workRow;
		t.buildTableEx(row);
		workRow[1] = "Illiquid Investments";
		workRow[2] = number.format(ts.getSum("ILLIQUID", "VALUE", "OWNER_ID="
				+ this.clientID));
		row[0] = workRow;
		t.buildTableEx(row);

		this.dbObj.start();
		HashMap<String,Object> rec = dbObj
				.execute("select description, value from REAL_ESTATE where owner_id='"
						+ this.clientID + "'");
		double realEstateAmount = 0.0;

		while (rec != null) {
			double amt = ((Number) (rec.get("value"))).doubleValue();
			realEstateAmount += amt;
			workRow[1] = (String) rec.get("description");
			workRow[2] = number.format(amt);
			row[0] = workRow;
			t.buildTableEx(row);
			rec = dbObj.next();
		}

		rec = dbObj
				.execute("select description, value from BUSINESS where owner_id='"
						+ this.clientID + "'");

		while (rec != null) {
			double amt = ((Number) (rec.get("value"))).doubleValue();
			realEstateAmount += amt;
			workRow[1] = (String) rec.get("description");
			workRow[2] = number.format(amt);
			row[0] = workRow;
			t.buildTableEx(row);
			rec = dbObj.next();

		}

		totalAssets += realEstateAmount + ts.getTotal();
		total[2] = number.format(realEstateAmount + ts.getTotal());
		row[0] = total;
		t.buildTableEx(row);

		row[0] = personalAssets;
		t.buildTableEx(row);
		ts.clearTotal();

		rec = dbObj
				.execute("select description, value from PROPERTY where owner_id='"
						+ clientID + "'");

		double pAmount = 0.0;

		while (rec != null) {
			double amt = ((Number) (rec.get("value"))).doubleValue();
			pAmount += amt;
			workRow[1] = (String) rec.get("description");
			workRow[2] = number.format(amt);
			row[0] = workRow;
			t.buildTableEx(row);
			rec = dbObj.next();

		}
		totalAssets += (pAmount + ts.getTotal());
		total[2] = number.format(pAmount + ts.getTotal());
		row[0] = total;
		t.buildTableEx(row);

		row[0] = blank;
		t.buildTableEx(row);

		sTotalAssets[2] = number.format(totalAssets);
		row[0] = sTotalAssets;
		t.buildTableEx(row);

		dbObj.stop();

		double liabilities = 0.0;
		ts.clearTotal();
		ts.getSum("REAL_ESTATE", "LOAN_BALANCE", "OWNER_ID=" + this.clientID);
		ts.getSum("PROPERTY", "LOAN_BALANCE", "OWNER_ID=" + this.clientID);
		liabilities = ts.getTotal();

		lessLiabilities[2] = number.format(liabilities);
		row[0] = lessLiabilities;
		t.buildTableEx(row);

		estimatedNetWorth[2] = number.format(totalAssets - liabilities);
		row[0] = estimatedNetWorth;
		t.buildTableEx(row);

		t.drawTable();

	}

	/**
	 * @param clientID
	 *            The clientID to set.
	 */
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	private void setTableFontandSize(PageTable table) {
		table.setTableFont("GARA.TTF");
		table.setTableFontBold("GARABD.TTF");
		table.setFontSize(10);

	}
}
