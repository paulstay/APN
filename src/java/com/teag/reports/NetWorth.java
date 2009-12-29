/**
 * 
 */
package com.teag.reports;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.db.DBObject;
import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.asset.Asset;
import com.teag.asset.AssetList;
import com.teag.db.TableSumation;

/**
 * @author Paul Stay
 *
 */
public class NetWorth extends Page {

    private DBObject dbObj = new DBObject();
    private int clientID;
    private double totalAssets = 0;

    /**
     * @param document
     * @param writer
     */
    public NetWorth(Document document, PdfWriter writer) {
        super(document, writer);
    }

    public float blank(float top) {
        Rectangle rect = calcRect(top);
        Rectangle rct;
        String blank[] = {
            " ",
            "",
            "",
            "[colspan=3][][]"
        };

        float widths[] = {.05f, .50f, .45f};
        rct = doRow(blank, widths, rect, 3);

        rect.setTop(rct.getBottom());
        return rect.getTop();
    }

    private Rectangle calcRect(float top) {
        Rectangle rect = new Rectangle(prctFull);
        rect.setTop(top);
        float adj = rect.getWidth() / 4f;
        rect.setLeft(rect.getLeft() + adj);
        rect.setRight(rect.getRight() - adj);
        return rect;
    }

    private Rectangle doRow(String rowInfo[], float widths[], Rectangle rct, int colCount) {
        String row[][] = new String[1][];
        PageTable t = getTable(rct, colCount);
        setTableFontAndSize(t);
        t.setColumnWidths(widths);
        row[0] = rowInfo;
        t.buildTableEx(row);
        t.drawTable();
        Rectangle r = t.getResultRect();
        return r;
    }

    public void draw() {
        Rectangle pRect = new Rectangle(prctFull);
        drawHeader();
        float top = liquidAssets(pRect.getTop());
        top = blank(top);
        top = ilLiquidAssets(top);
        top = blank(top);
        top = property(top);
        top = blank(top);
        top = genTotal(top);
    }

    private void drawHeader() {
        drawBorder(pageIcon);
        drawHeader(client, "Estimate of Net Worth");
    }

    private float genTotal(float top) {
        Rectangle rect = calcRect(top);
        Rectangle rct;
        TableSumation tsum = new TableSumation();
        float widths[] = {.05f, .50f, .45f};


        String sTotalAssets[] = {
            "TOTAL ASSETS",
            "",
            "",
            "[colspan=2,bold=1][][bold=1,border=t,align=right]"
        };
        String lessLiabilities[] = {
            "LESS LIABILITIES",
            "",
            "",
            "[colspan=2,bold=1][][bold=1,border=t,align=right,color=" + pgRed + "]"
        };
        String estimatedNetWorth[] = {
            "ESTIMATED NET WORTH",
            "",
            "",
            "[colspan=2,bold=1][][bold=1,border=t+b,align=right]"
        };

        sTotalAssets[2] = number.format(totalAssets);
        rct = doRow(sTotalAssets, widths, rect, 3);
        rect.setTop(rct.getBottom());

        tsum.clearTotal();
        tsum.getSum("REAL_ESTATE", "LOAN_BALANCE", "OWNER_ID='" + clientID + "'");
        tsum.getSum("PROPERTY", "LOAN_BALANCE", "OWNER_ID='" + clientID + "'");
        tsum.getSum("DEBT", "VALUE", "OWNER_ID='" + clientID + "'");
        tsum.getSum("NOTE_PAYABLE", "LOAN_AMOUNT", "OWNER_ID='" + clientID + "'");

        lessLiabilities[2] = number.format(tsum.getTotal());
        rct = doRow(lessLiabilities, widths, rect, 3);
        rect.setTop(rct.getBottom());

        estimatedNetWorth[2] = number.format(totalAssets - tsum.getTotal());
        rct = doRow(estimatedNetWorth, widths, rect, 3);
        rect.setTop(rct.getTop());
        return rect.getTop();
    }

    private void goNewPage() {
        newPage();

    }

    private float ilLiquidAssets(float top) {
        double v;
        double rAmt = 0;
        float tallest = 0.f;

        TableSumation tsum = new TableSumation();
        Rectangle rect = calcRect(top);
        float widths[] = {.05f, .50f, .45f};
        String workRow[] = new String[4];


        String liquidAssets[] = {
            "ILLIQUID ASSETS", "", "", "[colspan=2, bold=1][][]}"
        };

        String total[] = {
            " ", "", "", "[][][bold=1,border=t,align=right]"
        };

        tsum.clearTotal();

        Rectangle rct = doRow(liquidAssets, widths, rect, 3);
        rect.setTop(rct.getBottom());

        tallest = rct.getHeight() > tallest ? rct.getHeight() : tallest;

        v = tsum.getSum("RETIREMENT", "VALUE", "OWNER_ID='" + clientID + "'");
        if (v > 0) {
            workRow[0] = "";
            workRow[1] = "Retirement Plans";
            workRow[2] = number.format(v);
            workRow[3] = "[][][align=right]";

            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
        }

        v = tsum.getSum("ILLIQUID", "VALUE", "OWNER_ID='" + clientID + "'");
        if (v > 0) {
            workRow[0] = "";
            workRow[1] = "Illiquid Investments";
            workRow[2] = number.format(v);
            workRow[3] = "[][][align=right]";

            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
        }

        // Do real estate

        dbObj.start();
        String sqlString = "select description, value from REAL_ESTATE where OWNER_ID='" + clientID + "'";
        HashMap<String, Object> rec = dbObj.execute(sqlString);
        while (rec != null) {
            double amount = ((Number) (rec.get("value"))).doubleValue();
            rAmt += amount;
            workRow[0] = "";
            workRow[1] = (String) rec.get("description");
            workRow[2] = number.format(amount);
            workRow[3] = "[][][align=right]";
            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
            // Put a check here for new page
            tallest = rct.getHeight() > tallest ? rct.getHeight() : tallest;
            if ((rct.getBottom() - tallest) < prctFull.getBottom()) {
                goNewPage();
                drawHeader();
                rect.setTop(prctFull.getTop());
            }
            rec = dbObj.next();
        }

        dbObj.stop();

        // Do Business

        dbObj.start();
        sqlString = "select description, value from BUSINESS where OWNER_ID='" + clientID + "'";

        rec = dbObj.execute(sqlString);
        while (rec != null) {
            double amount = ((Number) (rec.get("value"))).doubleValue();
            rAmt += amount;
            workRow[1] = (String) rec.get("description");
            workRow[2] = number.format(amount);
            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
            // Put a check here for new page
            tallest = rct.getHeight() > tallest ? rct.getHeight() : tallest;
            if ((rct.getBottom() - tallest) < prctFull.getBottom()) {
                goNewPage();
                drawHeader();
                rect.setTop(prctFull.getTop());
            }
            rec = dbObj.next();
        }
        dbObj.stop();

        // Do contracts
        dbObj.start();
        sqlString = "select description, value from BIZ_CONTRACT where OWNER_ID='" + clientID + "'";

        rec = dbObj.execute(sqlString);
        while (rec != null) {
            double amount = ((Number) (rec.get("value"))).doubleValue();
            rAmt += amount;
            workRow[1] = (String) rec.get("description");
            workRow[2] = number.format(amount);
            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
            // Put a check here for new page
            tallest = rct.getHeight() > tallest ? rct.getHeight() : tallest;
            if ((rct.getBottom() - tallest) < prctFull.getBottom()) {
                goNewPage();
                drawHeader();
                rect.setTop(prctFull.getTop());
            }
            rec = dbObj.next();
        }
        totalAssets += rAmt + tsum.getTotal();

        total[2] = number.format(rAmt + tsum.getTotal());
        rct = doRow(total, widths, rect, 3);
        rect.setTop(rct.getBottom());

        dbObj.stop();
        return rect.getTop();
    }

    // Draw each individual section seperately so we can
    // check if we are off the page.
    private float liquidAssets(float top) {
        double v;
        TableSumation tsum = new TableSumation();
        Rectangle rect = calcRect(top);
        float widths[] = {.05f, .50f, .45f};

        String liquidAssets[] = {
            "LIQUID ASSETS", "", "", "[colspan=2, bold=1][][]}"
        };

        String total[] = {
            " ", "", "", "[][][bold=1,border=t,align=right]"
        };

        Rectangle rct = doRow(liquidAssets, widths, rect, 3);
        rect.setTop(rct.getBottom());

        String workRow[] = new String[4];
        workRow[0] = "";
        workRow[1] = "Cash and Equivalents";
        workRow[2] = number.format(tsum.getSum("CASH", "VALUE", "OWNER_ID='" + clientID + "'"));
        workRow[3] = "[][][align=right]";

        rct = doRow(workRow, widths, rect, 3);
        rect.setTop(rct.getBottom());

        v = tsum.getSum("BONDS", "VALUE", "OWNER_ID='" + clientID + "'");
        if (v > 0) {
            workRow[0] = "";
            workRow[1] = "Municipal Bonds";
            workRow[2] = number.format(v);

            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
        }

        v = tsum.getSum("SECURITIES", "VALUE", "OWNER_ID='" + clientID + "'");
        if (v > 0) {
            workRow[0] = "";
            workRow[1] = "Securities";
            workRow[2] = number.format(v);
            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
        }

        v = tsum.getSum("LIFE_INSURANCE", "VALUE", "OWNER_ID='" + clientID + "'");
        if (v > 0) {
            workRow[0] = "";
            workRow[1] = "Life Insurance";
            workRow[2] = number.format(v);

            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
        }

        v = tsum.getSum("NOTES", "VALUE", "OWNER_ID='" + clientID + "'");

        if (v > 0) {
            workRow[0] = "";
            workRow[1] = "Notes Receivable";
            workRow[2] = number.format(v);

            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
        }

        totalAssets += tsum.getTotal();
        total[2] = number.format(tsum.getTotal());
        rct = doRow(total, widths, rect, 3);
        rect.setTop(rct.getBottom());
        return rect.getTop();
    }

    private float property(float top) {
        double amt = 0;
        float tallest = 0.f;

        Rectangle rect = calcRect(top);
        float widths[] = {.05f, .50f, .45f};

        String propertyAssets[] = {
            "PERSONAL USE ASSETS", "", "", "[colspan=2, bold=1][][]}"
        };

        String total[] = {
            " ", "", "", "[][][bold=1,border=t,align=right]"
        };

        Rectangle rct = doRow(propertyAssets, widths, rect, 3);
        rect.setTop(rct.getBottom());

        String workRow[] = new String[4];
        workRow[0] = "";
        workRow[1] = "";
        workRow[2] = "";
        workRow[3] = "[][][align=right]";

        rct = doRow(workRow, widths, rect, 3);
        rect.setTop(rct.getBottom());

        tallest = rct.getHeight() > tallest ? rct.getHeight() : tallest;

        // Do Personal Propertry

        dbObj.start();
        String sqlString = "select description, value from PROPERTY where OWNER_ID='" + clientID + "'";

        HashMap<String, Object> rec = dbObj.execute(sqlString);
        while (rec != null) {
            double amount = ((Number) (rec.get("value"))).doubleValue();
            amt += amount;
            workRow[1] = (String) rec.get("description");
            workRow[2] = number.format(amount);
            rct = doRow(workRow, widths, rect, 3);
            rect.setTop(rct.getBottom());
            // Put a check here for new page
            tallest = rct.getHeight() > tallest ? rct.getHeight() : tallest;
            if ((rct.getBottom() - tallest) < prctFull.getBottom()) {
                goNewPage();
                drawHeader();
                rect.setTop(prctFull.getTop());
            }
            rec = dbObj.next();
        }
        dbObj.stop();

        totalAssets += amt;

        total[2] = number.format(amt);
        rct = doRow(total, widths, rect, 3);
        rect.setTop(rct.getBottom());
        return rect.getTop();
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    private void setTableFontAndSize(PageTable table) {
        table.setTableFont("GARA.TTF");
        table.setTableFontBold("GARABD.TTF");
        table.setFontSize(10);
    }
}
