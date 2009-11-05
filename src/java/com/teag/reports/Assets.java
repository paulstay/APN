/*
 * Created on May 6, 2005
 *
 */
package com.teag.reports;

import java.awt.Color;

import com.estate.constants.Ownership;
import com.estate.db.DBObject;
import com.estate.pdf.Locations;
import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.bean.BizContractBean;
import com.teag.bean.GiftBean;
import com.teag.bean.InsuranceBean;
import com.teag.bean.RetirementBean;
import java.util.*;

/**
 * @author Paul Stay
 * 
 */
public class Assets extends Page {

    /**
     * @param document
     * @param writer
     */
    int heading = 1;
    private DBObject dbObj = new DBObject();
    private int clientID;

    public Assets(Document document, PdfWriter writer) {
        super(document, writer);
    }

    private float assetHeader(String num, String desc, float top) {
        Rectangle rct = calcRect(top);
        float baseWidths[] = {_1_4TH, rct.getWidth() - _1_4TH};
        float widths[] = calcWidths(baseWidths, rct.getWidth());
        PageTable t = this.getTable(rct, 2);
        String row[][] = {{num, desc,
                "[bolditalic=1,border=l+t+b][bolditalic=1,border=t+r+b]"}};

        this.setTableFontandSize(t);
        t.setFontSize(12);
        t.setColumnWidths(widths);
        t.buildTableEx(row);

        t.drawTable();
        Rectangle r = t.getResultRect();
        this.drawFilledRect(r, new Color(210, 210, 210));

        return (r.getBottom());

    }

    // *************************************************
    private float businessInterests(float startTop) {
        String style;

        int colCount = 11;
        int cnt = 0;
        cnt += countRows("BUSINESS", clientID);

        if (cnt == 0) {
            return startTop;
        }

        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Business Interests", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        style = new String("[colspan=2,border=l]" + "[]" + "[align=center,border=l+b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b+r]");

        String header[] = {" ", "", "Ownership", "Title", "Type Entity",
            "Personal Cost Basis", "MarketValue", "Percent Owned",
            "", "Growth % *", "Cash Flow", style};

        float baseWidths[] = {_1_4TH, // Letter
            (2.5f * 72) - _1_8TH, // Desc.
            _1_2TH + _1_8TH, // Ownership
            _1_2TH, // Title
            .75f * 72, // Type entity
            .75f * 72, // Personal Cost Basis
            .85f * 72, // Market Value
            .5f * 72, // Percent Owned
            .75f * 72, // Personal Equity
            .5f * 72, // Growth %
            .75f * 72 // Cash Flow
        };

        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        style = new String("[align=right,border=l]" + // Letter
                "[]" + // Desc
                "[align=center]" + // Ownership
                "[align=center]" + // Title
                "[align=center]" + // Type Entity
                "[align=right]" + // Personal Cost Basis
                "[align=right]" + // Market Value
                "[align=center]" + // Percent Owned
                "[align=right]" + // Personal Equity
                "[align=center]" + // Growth %
                "[align=right,border=r]" // Cash Flow
                );

        String rowInfo[] = {"", "", "CP", "H/W", "350,000", "120000", "????",
            "2%", "", "", "", style};

        char ltr = 'a';
        dbObj.start();

        String sqlStatement = "select * from BUSINESS a, OWNERSHIP b, TITLE c, BUSINESS_TYPES d where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id and d.BUSINESS_TYPES_ID=a.BUSINESS_TYPE_ID";
        HashMap<String, Object> rec = dbObj.execute(sqlStatement);
        double dBasis;
        double dMktValue;
        double dEquity;
        double dPcntOwned;
        double dGrowth;
        double dAnnualIncome;

        double dBasisSum = 0;
        double dMktValueSum = 0;
        double dEquitySum = 0;

        int i = 0;
        while (rec != null) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            dMktValue = ((Number) (rec.get("VALUE"))).doubleValue();
            dBasis = ((Number) (rec.get("BASIS"))).doubleValue();
            dPcntOwned = ((Number) (rec.get("PERCENT_OWNED"))).doubleValue();
            dGrowth = ((Number) (rec.get("GROWTH_RATE"))).doubleValue();
            dAnnualIncome = ((Number) (rec.get("ANNUAL_INCOME"))).doubleValue();

            dEquity = dMktValue * dPcntOwned;

            dBasisSum += dBasis;
            dMktValueSum += dMktValue;
            dEquitySum += dEquity;

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = (String) rec.get("BUSINESS_TYPES_DESCR");
            rowInfo[5] = dBasis == 0 ? "" : number.format(dBasis);
            rowInfo[6] = dMktValue == 0 ? "" : number.format(dMktValue);
            rowInfo[7] = percent.format(dPcntOwned);
            //rowInfo[8] = dEquity == 0 ? "" : number.format(dEquity);
            rowInfo[9] = percent.format(dGrowth);
            rowInfo[10] = dAnnualIncome == 0 ? "" : number.format(dAnnualIncome);

            r = this.doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Business Interests (cont.)", prctFull.getTop());
                rct.setTop(top);

                r = columnHeader(header, widths, rct, colCount);

                i = 0;

            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();

        // Do totals
        style = new String("[colspan=5,border=l+b]" + "[]" + "[]" + "[]" + "[]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[border=b]" + "[bold=1,align=right,border=b]" + "[border=b]" + "[border=b+r]");

        String totals[] = {"", "", "", "", "", "", "", "", "", "", "", style};

        totals[5] = dBasisSum == 0 ? "" : number.format(dBasisSum);
        totals[6] = dMktValueSum == 0 ? "" : number.format(dMktValueSum);
        //totals[8] = dEquitySum == 0 ? "" : number.format(dEquitySum);

        r = this.doRow(totals, widths, rct, colCount);
        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    private Rectangle calcRect(float top) {
        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        return rct;
    }

    private float[] calcWidths(float baseWidths[], float tableWidth) {
        float widths[] = new float[baseWidths.length];

        for (int i = 0; i < widths.length; i++) {
            widths[i] = baseWidths[i] / tableWidth;
        }

        return widths;
    }

    private float cashAndEquivalents(float startTop) {
        String style;
        int colCount = 8;
        double dBalanceSum = 0;
        double dAnnualIncomeSum = 0;
        double dBasisSum = 0;

        double daBasis;
        double daBalance;
        double daInterRate;

        double dAnnualIncome;

        int cnt = 0;
        cnt += countRows("CASH", clientID);

        if (cnt == 0) {
            return startTop;
        }
        String h1 = Integer.toString(heading++) + ".";
        float top = assetHeader(h1, "Cash & Equivalents", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        style = new String(
                "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]");

        String header[] = {" ", "", "Ownership", "Title", "Cost Basis",
            "Current Balance", "Annual Income", "Interest Rate *", style};

        float baseWidths[] = {_1_4TH, 3 * 72, _1_4TH * 3, _1_4TH * 3, 1 * 72,
            1 * 72, 1 * 72, .75f * 72};
        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = this.columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        style = new String(
                "[align=right,border=l][][align=center][align=center][align=right][align=right][align=right][align=center,border=r]");

        String rowInfo[] = {"", "", "CP", "H/W", "350,000", "120000", "????",
            "2%", style};
        char ltr = 'a';
        dbObj.start();
        String sqlStatement = "select * from CASH a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id";
        HashMap<String, Object> rec = dbObj.execute(sqlStatement);
        int i = 0;
        while (rec != null) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            daBasis = rec.get("BASIS") == null ? 0 : ((Number) (rec.get("BASIS"))).doubleValue();
            dBasisSum += daBasis;
            daBalance = rec.get("VALUE") == null ? 0 : ((Number) (rec.get("VALUE"))).doubleValue();
            daInterRate = rec.get("INTEREST_RATE") == null ? 0 : ((Number) (rec.get("INTEREST_RATE"))).doubleValue();
            dAnnualIncome = daBalance * daInterRate;
            dBalanceSum += daBalance;
            dAnnualIncomeSum += dAnnualIncome;

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = daBasis == 0 ? "" : number.format(daBasis);
            rowInfo[5] = daBalance == 0 ? "" : number.format(daBalance);
            rowInfo[6] = dAnnualIncome == 0 ? "" : number.format(dAnnualIncome);
            rowInfo[7] = percent.format(daInterRate);

            r = this.doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Cash & Equivalents (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = this.columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();
        // Do totals
        String totals[] = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "[colspan=4,border=l+b][][][][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][border=b+r]"};

        totals[4] = dBasisSum == 0 ? "" : number.format(dBasisSum);
        totals[5] = dBalanceSum == 0 ? "" : number.format(dBalanceSum);
        totals[6] = dAnnualIncomeSum == 0 ? "" : number.format(dAnnualIncomeSum);

        r = this.doRow(totals, widths, rct, colCount);

        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    private float checkNextTable(float top) {
        float val = top;
        if (top < prctFull.getBottom() + 72) {
            goNewPage();
            val = prctFull.getTop();
        }

        return (val);
    }

    private Rectangle columnHeader(String header[], float widths[],
            Rectangle rct, int colCount) {
        String row[][] = new String[1][];
        PageTable t = this.getTable(rct, colCount);
        this.setTableFontandSize(t);
        t.setFontSize(7);
        t.setColumnWidths(widths);
        row[0] = header;
        t.buildTableEx(row);
        t.drawTable();
        Rectangle r = t.getResultRect();

        return r;
    }

    private int countRows(String table, long ownerId) {
        String sqlStatement = "select count(*) as x from " + table + " where OWNER_ID='" + ownerId + "'";
        dbObj.start();
        HashMap<String, Object> rec = dbObj.execute(sqlStatement);
        int nAC = 0;
        if (rec != null) {
            nAC = ((Number) (rec.get("x"))).intValue();
        }
        dbObj.stop();
        return nAC;
    }

    private float debt(float startTop) {
        String style;
        int colCount = 8;
        double dBalanceSum = 0;

        double daBalance;
        double daInterRate;

        int cnt = 0;
        cnt += countRows("DEBT", clientID);

        if (cnt == 0) {
            return startTop;
        }
        String h1 = Integer.toString(heading++) + ".";
        float top = assetHeader(h1, "DEBT", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        style = new String(
                "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]");

        String header[] = {" ", "", "Ownership", "Title", "Loan Amount",
            "Interest Rate *", "Years", "", style};

        float baseWidths[] = {_1_4TH, 3 * 72, _1_4TH * 3, _1_4TH * 3, 1 * 72,
            1 * 72, 1 * 72, .75f * 72};
        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = this.columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        style = new String(
                "[align=right,border=l][][align=center][align=center][align=right][align=right][align=right][align=center,border=r]");

        String rowInfo[] = {"", "", "CP", "H/W", "350,000", "120000", "????",
            "2%", style};
        char ltr = 'a';
        dbObj.start();
        String sqlStatement = "select * from DEBT a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id";
        HashMap<String, Object> rec = dbObj.execute(sqlStatement);
        int i = 0;
        while (rec != null) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            daBalance = rec.get("VALUE") == null ? 0 : ((Number) (rec.get("VALUE"))).doubleValue();
            daInterRate = rec.get("INTEREST_RATE") == null ? 0 : ((Number) (rec.get("INTEREST_RATE"))).doubleValue();
            int daYear = rec.get("LOAN_TERM") == null ? 0 : ((Number) (rec.get("LOAN_TERM"))).intValue();
            dBalanceSum += daBalance;

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = daBalance == 0 ? "" : number.format(daBalance);
            rowInfo[5] = percent.format(daInterRate);
            rowInfo[6] = daYear == 0 ? "" : Integer.toString(daYear);
            rowInfo[7] = "";

            r = this.doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Debt (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = this.columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();
        // Do totals
        String totals[] = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "[colspan=4,border=l+b][][][][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][border=b+r]"};

        totals[4] = number.format(dBalanceSum);
        totals[5] = "";
        totals[6] = "";

        r = this.doRow(totals, widths, rct, colCount);

        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    private Rectangle doRow(String rowInfo[], float widths[], Rectangle rct,
            int colCount) {
        String row[][] = new String[1][];

        PageTable t = this.getTable(rct, colCount);

        this.setTableFontandSize(t);
        t.setColumnWidths(widths);
        row[0] = rowInfo;
        t.buildTableEx(row);
        t.drawTable();
        Rectangle r = t.getResultRect();
        return r;
    }

    public void draw() {
        page1();
    }

    public float drawAssumption() {
        String assumption = "* Growth and income rates are assumptions only as estimated by the client, and are neither guarantees nor assurances of future performance.";
        PdfContentByte cb = writer.getDirectContentUnder();
        try {
            BaseFont font = BaseFont.createFont(Locations.getFontLocation() + "times.ttf", BaseFont.CP1252, BaseFont.EMBEDDED);

            cb.beginText();
            cb.setFontAndSize(font, 8);
            cb.setRGBColorFill(255, 0, 0);
            cb.setTextMatrix(72 * 1.5F, .75f * 72);
            cb.showText(assumption);
            cb.endText();
        } catch (Exception e) {
            System.err.println("Error printing Life Expectancy");
        }
        return 0;
    }

    private float familyGifting(float startTop) {
        String style;
        double dTotalGift;
        double dTotalGiftSum = 0;

        int colCount = 6;

        GiftBean gift = new GiftBean();
        GiftBean giftBean[] = gift.query(gift.OWNER_ID, Integer.toString(clientID));

        if (giftBean == null) {
            return startTop;
        }

        int cnt = 0;
        for (int i = 0; i < giftBean.length; i++) {
            cnt += giftBean[i].getGiftPlanning().equals("Y") ? 1 : 0;
        }

        if (cnt == 0) {
            return startTop;
        }

        startTop = checkNextTable(startTop);

        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Annual Exclusion Gifts", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        style = new String("[align=center,border=l]" + "[align=center,border=l+b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]");

        String header[] = {"", "Description", "By Whom", "Date", "Amount",
            "Regularity", style};

        float baseWidths[] = {_1_2TH, // Letter
            (1.75f * 72), // Description
            (_1_4TH * 3), // By Whom
            (_1_4TH * 3), // Date
            .75f * 72, // Amount
            .75f * 72 // regularity
        };

        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = this.columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        style = new String("[border=l]" + // letter
                "[align=center]" + // Desc
                "[align=center]" + // By Whome
                "[align=center]" + // Date
                "[align=right]" + // Amount
                "[align=center,border=r]" // regularity
                );
        String rowInfo[] = {"", "test desc", "Paul", "11/24/2005", "11,000",
            "Annual", style};
        char ltr = 'a';

        for (int i = 0; i < giftBean.length; i++) {
            if (!giftBean[i].getGiftPlanning().equals("Y")) {
                continue;
            }
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            dTotalGift = giftBean[i].getAmount();

            dTotalGiftSum += dTotalGift;

            rowInfo[1] = giftBean[i].getDescription();
            rowInfo[2] = giftBean[i].getByWhom();
            rowInfo[3] = giftBean[i].getDate();
            rowInfo[4] = number.format(giftBean[i].getAmount());
            String regular = "Annual";
            if (giftBean[i].getRegularity().equals("B")) {
                regular = "Bi Annual";
            }
            if (giftBean[i].getRegularity().equals("T")) {
                regular = "Tri Annual";
            }
            if (giftBean[i].getRegularity().equals("O")) {
                regular = "One Time";
            }
            rowInfo[5] = regular;

            r = this.doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Annual Exclusion Gifts (cont.)",
                        prctFull.getTop());
                rct.setTop(top);
                r = this.columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            i++;
        }

        style = new String("[border=l+b]" + // letter
                "[align=center,border=b]" + // Desc
                "[align=center,border=b]" + // By Whome
                "[align=center,border=b]" + // Date
                "[align=right,border=b]" + // Amount
                "[align=center,border=b+r]" // regularity
                );

        String rInfo[] = {"", "", "", "", "", "", style};
        r = this.doRow(rInfo, widths, rct, 6);
        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    // **************************************************
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

    private void goNewPage() {
        newPage();
        this.drawBorder(pageIcon);
        this.drawHeader(client, "Asset and Liability Details (cont.)");

    }

    private float contracts(float startTop){
       int colCount = 9;

        int cnt = 0;

        BizContractBean bb = new BizContractBean();
        ArrayList<BizContractBean> bList = bb.getBeans(BizContractBean.OWNER_ID + "='" + clientID + "'");

        cnt = bList.size()-1;

        if (cnt <= 0) {
            return startTop;
        }

        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Business Contracts", startTop);

        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);

        rct.setTop(top);

        rct.setRight(rct.getLeft() + 8.5f * 72);

        String header[] = {
            " ",
            "Descritpion",
            "Value",
            "Salary",
            "Starting Year",
            "Ending Year",
            " ",
            "",
            "",
            "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]"};

        float baseWidths[] = {_1_4TH, 2.25f * 72, _1_4TH * 3, _1_4TH * 3,
            1 * 72, 1 * 72, 1 * 72, .75f * 72, .75f * 72};

        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        String rowInfo[] = {
            " ",
            " ",
            " ",
            " ",
            " ",
            " ",
            " ",
            " ",
            " ",
            "[align=right,border=l][][align=center][align=center][align=right][align=right][align=right][align=center][align=center,border=r]"};

        char ltr = 'a';

        int i =0;
        for(BizContractBean biz : bList){

            rowInfo[0] = "" + ltr + ".";

            ltr++;

            // Now grab all the pertinent numbers

            rowInfo[1] = biz.getDescription();
            rowInfo[2] = number.format(biz.getValue());
            rowInfo[3] = number.format(biz.getSalary());
            rowInfo[4] = Integer.toString(biz.getStartYear());
            rowInfo[5] = Integer.toString(biz.getEndYear());

            r = doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }

            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

            if ((r.getBottom() - tallest) < prctFull.getBottom()) {

                // we need to go to a new page.

                goNewPage();

                top = assetHeader("2.", "Business Contracts (cont.)",
                        prctFull.getTop());

                rct.setTop(top);

                r = columnHeader(header, widths, rct, colCount);

                i = 0;
            }
            rct.setTop(r.getBottom());
            i++;
        }
        rct.setTop(r.getBottom() - _1_8TH);
        return rct.getTop();
    }

    private float illiquidInterest(float startTop) {
        int colCount = 9;

        int cnt = 0;

        cnt += countRows("ILLIQUID", clientID);



        if (cnt == 0) {

            return startTop;

        }



        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Illiquid Investments", startTop);

        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);

        rct.setTop(top);

        rct.setRight(rct.getLeft() + 8.5f * 72);

        // Do header;

        String header[] = {
            " ",
            "",
            "Ownership",
            "Title",
            "Cost Basis",
            "Market Value",
            "Annual Income",
            "Div/Int % *",
            "Growth % *",
            "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]"};

        float baseWidths[] = {_1_4TH, 2.25f * 72, _1_4TH * 3, _1_4TH * 3,
            1 * 72, 1 * 72, 1 * 72, .75f * 72, .75f * 72};

        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        String rowInfo[] = {
            "",
            "",
            "CP",
            "H/W",
            "350,000",
            "120000",
            "????",
            "2%",
            "",
            "[align=right,border=l][][align=center][align=center][align=right][align=right][align=right][align=center][align=center,border=r]"};

        char ltr = 'a';

        dbObj.start();

        String sqlStatement = "select count(*) as x from ILLIQUID where OWNER_ID='" + clientID + "'";

        HashMap<String, Object> rec = dbObj.execute(sqlStatement);

        int nAC = ((Number) (rec.get("x"))).intValue();



        double dValueSum = 0;

        double dAnnualIncomeSum = 0;

        double dBasisSum = 0;



        int i = 0;

        double[] daBasis = new double[nAC];

        double[] daValue = new double[nAC];

        double[] daDivInt = new double[nAC];

        double[] daGrowth = new double[nAC];

        double dAnnualIncome;



        sqlStatement = "select * from ILLIQUID a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id";

        rec = dbObj.execute(sqlStatement);



        while (rec != null) {

            rowInfo[0] = "" + ltr + ".";

            ltr++;

            // Now grab all the pertinent numbers

            daBasis[i] = ((Number) (rec.get("BASIS"))).doubleValue();

            daValue[i] = ((Number) (rec.get("VALUE"))).doubleValue();

            daDivInt[i] = ((Number) (rec.get("DIV_INT"))).doubleValue();

            daGrowth[i] = ((Number) (rec.get("GROWTH_RATE"))).doubleValue();



            dAnnualIncome = daValue[i] * daDivInt[i];



            // SUMS

            dValueSum += daValue[i];

            dAnnualIncomeSum += dAnnualIncome;

            dBasisSum += daBasis[i];



            rowInfo[1] = (String) rec.get("DESCRIPTION");

            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");

            rowInfo[3] = (String) rec.get("TITLE_CODE");

            rowInfo[4] = daBasis[i] == 0 ? "" : number.format(daBasis[i]); // Basis

            rowInfo[5] = daValue[i] == 0 ? "" : number.format(daValue[i]); // value

            rowInfo[6] = dAnnualIncome == 0 ? "" : number.format(dAnnualIncome); // annual

            // income

            rowInfo[7] = percent.format(daDivInt[i]); // Div/Int%

            rowInfo[8] = percent.format(daGrowth[i]);



            r = doRow(rowInfo, widths, rct, colCount);



            if ((i & 1) == 1) {

                drawFilledRect(r, new Color(239, 239, 239));

            }

            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

            if ((r.getBottom() - tallest) < prctFull.getBottom()) {

                // we need to go to a new page.

                goNewPage();

                top = assetHeader("2.", "Illiquid Investments (cont.)",
                        prctFull.getTop());

                rct.setTop(top);

                r = columnHeader(header, widths, rct, colCount);

                i = 0;



            }

            rct.setTop(r.getBottom());

            rec = dbObj.next();

            i++;

        }

        dbObj.stop();

        // Do totals

        String totals[] = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "[colspan=4,border=l+b][][][][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][bold=1,align=center,border=b+t][bold=1,align=center,border=b+r+t]"};



        double dGrowthSum = 0;

        double dDivIntSum = 0;



        for (int rdx = 0; rdx < i; rdx++) {

            // Calculation for Growth

            dGrowthSum += daGrowth[rdx] * daValue[rdx] / dValueSum;

            // Calculation for div/int sum

            dDivIntSum += daDivInt[rdx] * daValue[rdx] / dValueSum;



        }



        totals[4] = dBasisSum == 0 ? "" : number.format(dBasisSum);

        totals[5] = dValueSum == 0 ? "" : number.format(dValueSum);

        totals[6] = dAnnualIncomeSum == 0 ? "" : number.format(dAnnualIncomeSum);

        totals[7] = "";// percent.format(dDivIntSum);

        totals[8] = "";// percent.format(dGrowthSum);



        r = doRow(totals, widths, rct, colCount);



        rct.setTop(r.getBottom() - _1_8TH);



        return rct.getTop();


    }

    private float lifeInsurance(float startTop) {
        String style;
        double dFaceVal;
        double dCashVal;
        double dLoans;

        double dFaceSum = 0;
        double dCashValSum = 0;

        String strBeneficiary;
        String strOwner;

        int colCount = 11;
        int cnt = 0;
        cnt += countRows("LIFE_INSURANCE", clientID);

        if (cnt == 0) {
            return startTop;
        }

        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Life Insurance", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        // Style for Header
        style = new String("[colspan=2,border=l,align=center]" + "[]" + "[align=center,border=l+b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b+r]");

        String header[] = {"Company Name", "", "Insured", "Date Acq'd",
            "Policy #", "Face Amount", "Cash Value", "Type of Policy",
            "Owner", "Named Benefic", "Loans", style};

        float baseWidths[] = {_1_4TH, // Letter
            (1f * 72), // Company
            (1f * 72), // Insured
            (_1_4TH * 3), // Date
            (_1_4TH * 3), // Policy #
            .75f * 72, // Face Amount
            .75f * 72, // Cash Value
            .75f * 72, // Policy Type
            .75f * 72, // Owner
            .75f * 72, // Beneficiary
            .75f * 72 // Loans
        };
        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = this.columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        // Style for the data
        style = new String("[align=right,border=l]" + // Letter
                "[]" + // Company
                "[align=center]" + // Insured
                "[align=center]" + // Date
                "[align=center]" + // Policy #
                "[align=right]" + // Face Amount
                "[align=right]" + // Cash Value
                "[align=right]" + // Policy Type
                "[align=center]" + // Owner
                "[align=center]" + // Beneficiary
                "[align=center,border=r]" // Loans
                );

        String rowInfo[] = {"", "", "", "CP", "H/W", "350,000", "120000",
            "????", "2%", "", "", style};
        char ltr = 'a';

        InsuranceBean ib = new InsuranceBean();

        InsuranceBean[] iList = ib.query(InsuranceBean.OWNER_ID, Long.toString(clientID));

        int i = 0;

        if (iList != null) {
            for (int j = 0; j < iList.length; j++) {
                rowInfo[0] = "" + ltr + ".";
                ltr++;
                // Now grab all the pertinent numbers
                dCashVal = iList[j].getValue();
                dFaceVal = iList[j].getFaceValue();
                dLoans = iList[j].getLoans();
                strOwner = iList[j].getOwner();
                strBeneficiary = iList[j].getBeneficiary();
                // String lifeType =
                // iList[j].getIrec.get("INSURANCE_TYPES_DESCR") == null ? "" :
                // (String)rec.get("INSURANCE_TYPES_DESCR");

                dFaceSum += dFaceVal;
                dCashValSum += dCashVal;
                rowInfo[1] = iList[j].getInsuranceCompany();
                rowInfo[2] = iList[j].getInsured();
                rowInfo[3] = iList[j].getDateAcquired();
                rowInfo[4] = iList[j].getPolicyNumber();
                rowInfo[5] = dFaceVal == 0 ? "" : number.format(dFaceVal);
                rowInfo[6] = dCashVal == 0 ? "" : number.format(dCashVal);
                rowInfo[7] = "";// lifeType;
                rowInfo[8] = strOwner;
                rowInfo[9] = strBeneficiary;
                rowInfo[10] = number.format(dLoans);

                r = this.doRow(rowInfo, widths, rct, colCount);

                if ((i & 1) == 1) {
                    drawFilledRect(r, new Color(239, 239, 239));
                }
                tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
                if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                    // we need to go to a new page.
                    goNewPage();
                    top = assetHeader(h1, "Life Insurance (cont.)", prctFull.getTop());
                    rct.setTop(top);
                    r = this.columnHeader(header, widths, rct, colCount);
                    i = 0;
                }
                rct.setTop(r.getBottom());
                i++;
            }
        }

        // Do totals
        style = new String("[colspan=4,border=l+b]" + "[border=b]" + "[border=b]" + "[border=b]" + "[border=b]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[border=b]" + "[border=b]" + "[border=b+r]");
        String totals[] = {"", "", "", "", "", "", "", "", "", "", "", style};

        totals[5] = dFaceSum == 0 ? "" : number.format(dFaceSum);
        totals[6] = dCashValSum == 0 ? "" : number.format(dCashValSum);

        r = doRow(totals, widths, rct, colCount);

        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    private float marketableSecurities(float startTop) {
        int colCount = 9;
        int cnt = 0;
        cnt += countRows("SECURITIES", clientID);
        cnt += countRows("BONDS", clientID);
        if (cnt == 0) {
            return startTop;
        }
        String h1 = Integer.toString(heading++) + ".";
        float top = assetHeader(h1, "Marketable Securities", startTop);
        float tallest = 0.0f;
        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;
        String header[] = {
            " ",
            "",
            "Ownership",
            "Title",
            "Cost Basis",
            "Market Value",
            "Annual Income",
            "Div/Int % *",
            "Growth % *",
            "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]"};
        float baseWidths[] = {_1_4TH, 2.25f * 72, _1_4TH * 3, _1_4TH * 3,
            1 * 72, 1 * 72, 1 * 72, .75f * 72, .75f * 72};
        float widths[] = calcWidths(baseWidths, rct.getWidth());
        Rectangle r = columnHeader(header, widths, rct, colCount);
        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
        rct.setTop(r.getBottom());
        String rowInfo[] = {
            "",
            "",
            "CP",
            "H/W",
            "350,000",
            "120000",
            "????",
            "2%",
            "",
            "[align=right,border=l][][align=center][align=center][align=right][align=right][align=right][align=center][align=center,border=r]"};
        char ltr = 'a';
        dbObj.start();
        String sqlStatement = "select count(*) as x from SECURITIES where OWNER_ID='" + clientID + "'";
        HashMap<String, Object> rec = dbObj.execute(sqlStatement);
        int nAC = ((Number) (rec.get("x"))).intValue();
        sqlStatement = "select count(*) as x from BONDS where OWNER_ID='" + clientID + "'";
        rec = dbObj.execute(sqlStatement);
        nAC += ((Number) (rec.get("x"))).intValue();

        double dValueSum = 0;
        double dAnnualIncomeSum = 0;
        double dBasisSum = 0;

        double[] daBasis = new double[nAC];
        double[] daValue = new double[nAC];
        double[] daDivInt = new double[nAC];
        double[] daGrowth = new double[nAC];
        double dAnnualIncome;

        sqlStatement = "select * from BONDS a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id";
        rec = dbObj.execute(sqlStatement);
        int i = 0;
        while (rec != null) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            daBasis[i] = ((Number) (rec.get("BASIS"))).doubleValue();
            daValue[i] = ((Number) (rec.get("VALUE"))).doubleValue();
            daDivInt[i] = ((Number) (rec.get("INTEREST_RATE"))).doubleValue();
            daGrowth[i] = ((Number) (rec.get("GROWTH_RATE"))).doubleValue();

            dAnnualIncome = daValue[i] * daDivInt[i];

            // SUMS
            dValueSum += daValue[i];
            dAnnualIncomeSum += dAnnualIncome;
            dBasisSum += daBasis[i];

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = daBasis[i] == 0 ? "" : number.format(daBasis[i]); // Basis
            rowInfo[5] = daValue[i] == 0 ? "" : number.format(daValue[i]); // value
            rowInfo[6] = dAnnualIncome == 0 ? "" : number.format(dAnnualIncome); // annual
            // income
            rowInfo[7] = percent.format(daDivInt[i]); // Div/Int%
            rowInfo[8] = percent.format(daGrowth[i]);

            r = doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Marketable Securities (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        sqlStatement = "select * from SECURITIES a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id";
        rec = dbObj.execute(sqlStatement);

        while (rec != null) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            daBasis[i] = ((Number) (rec.get("BASIS"))).doubleValue();
            daValue[i] = ((Number) (rec.get("VALUE"))).doubleValue();
            daDivInt[i] = ((Number) (rec.get("DIV_INT"))).doubleValue();
            daGrowth[i] = ((Number) (rec.get("GROWTH_RATE"))).doubleValue();

            dAnnualIncome = daValue[i] * daDivInt[i];

            // SUMS
            dValueSum += daValue[i];
            dAnnualIncomeSum += dAnnualIncome;
            dBasisSum += daBasis[i];

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = daBasis[i] == 0 ? "" : number.format(daBasis[i]); // Basis
            rowInfo[5] = daValue[i] == 0 ? "" : number.format(daValue[i]); // value
            rowInfo[6] = dAnnualIncome == 0 ? "" : number.format(dAnnualIncome); // annual
            // income
            rowInfo[7] = percent.format(daDivInt[i]); // Div/Int%
            rowInfo[8] = percent.format(daGrowth[i]);

            r = doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader("2.", "Marketable Securities (cont.)",
                        prctFull.getTop());
                rct.setTop(top);
                r = columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();
        // Do totals
        String totals[] = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "[colspan=4,border=l+b][][][][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][bold=1,align=center,border=b+t][bold=1,align=center,border=b+r+t]"};

        double dGrowthSum = 0;
        double dDivIntSum = 0;

        for (int rdx = 0; rdx < i; rdx++) {
            // Calculation for Growth
            dGrowthSum += daGrowth[rdx] * daValue[rdx] / dValueSum;
            // Calculation for div/int sum
            dDivIntSum += daDivInt[rdx] * daValue[rdx] / dValueSum;

        }

        totals[4] = dBasisSum == 0 ? "" : number.format(dBasisSum);
        totals[5] = dValueSum == 0 ? "" : number.format(dValueSum);
        totals[6] = dAnnualIncomeSum == 0 ? "" : number.format(dAnnualIncomeSum);
        totals[7] = "";// percent.format(dDivIntSum);
        totals[8] = "";// percent.format(dGrowthSum);

        r = doRow(totals, widths, rct, colCount);

        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    private float notes(float startTop) {
        int colCount = 8;

        int cnt = 0;
        cnt += countRows("NOTES", clientID);
        if (cnt == 0) {
            return startTop;
        }

        String h1 = Integer.toString(heading++) + ".";
        float top = assetHeader(h1, "Notes Receivable", startTop);
        float tallest = 0.0f;
        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);

        String header[] = {
            " ",
            "",
            "Ownership",
            "Title",
            "Loan Amount",
            "Interest Rate",
            "Years",
            "Type",
            "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b]" + "[align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]"};
        float baseWidths[] = {_1_4TH, 2.25f * 72, _1_4TH * 3, _1_4TH * 3, 72,
            72, 72, 72};
        float widths[] = calcWidths(baseWidths, rct.getWidth());
        Rectangle r = columnHeader(header, widths, rct, colCount);
        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
        rct.setTop(r.getBottom());
        String rowInfo[] = {
            "",
            "",
            "CP",
            "H/W",
            "50,000",
            "5.6%",
            "5",
            "Interest Only",
            "[align=right,border=l][][align=center][align=center][align=right][align=right][align=center][align=center,border=r]"};
        char ltr = 'a';
        dbObj.start();
        String sql = "Select count(*) as x from NOTES where OWNER_ID='" + clientID + "'";
        HashMap<String, Object> rec = dbObj.execute(sql);

        double dLoans = 0;
        double dInterest = 0;
        double dYears = 0;
        double dSum = 0;
        sql = "select * from Notes a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.OWNERSHIP_ID=b.OWNERSHIP_ID and a.TITLE_ID=c.TITLE_ID";
        rec = dbObj.execute(sql);
        int i = 0;
        while (rec != null) {
            rowInfo[0] = Character.toString(ltr) + ".";
            ltr++;
            dLoans = ((Number) (rec.get("VALUE"))).doubleValue();
            dInterest = ((Number) (rec.get("INTEREST_RATE"))).doubleValue();
            dYears = ((Number) (rec.get("YEARS"))).doubleValue();
            String nType = (String) rec.get("NOTE_TYPE");

            dSum += dLoans;

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = dLoans == 0 ? "" : number.format(dLoans);
            rowInfo[5] = dInterest == 0 ? "" : percent.format(dInterest);
            rowInfo[6] = dYears == 0 ? "" : number.format(dYears);
            if (nType.equals("I")) {
                rowInfo[7] = "Interest Only";
            } else if (nType.equals("A")) {
                rowInfo[7] = "Amortization";
            } else {
                rowInfo[7] = "Principal Only";
            }

            rowInfo[7] = nType.equalsIgnoreCase("I") ? "Interest Only"
                    : "Amortization";

            r = doRow(rowInfo, widths, rct, colCount);
            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if (r.getBottom() - tallest < prctFull.getBottom()) {
                goNewPage();
                top = assetHeader(h1, "Notes Receivable (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = columnHeader(header, widths, rct, colCount);
                i = 0;
            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();
        String totals[] = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "[colspan=4,border=l+b][][][][bold=1,align=right,border=t+b][bold=1,border=b+t][bold=1,border=b+t][border=b+r]"};
        totals[4] = number.format(dSum);

        r = doRow(totals, widths, rct, colCount);
        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    private float notesPayable(float startTop) {
        int colCount = 8;

        int cnt = 0;
        cnt += countRows("NOTE_PAYABLE", clientID);
        if (cnt == 0) {
            return startTop;
        }

        String h1 = Integer.toString(heading++) + ".";
        float top = assetHeader(h1, "Notes Payable", startTop);
        float tallest = 0.0f;
        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);

        String header[] = {
            " ",
            "",
            "Ownership",
            "Title",
            "Loan Amount",
            "Interest Rate",
            "Years",
            "Type",
            "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b]" + "[align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]"};
        float baseWidths[] = {_1_4TH, 2.25f * 72, _1_4TH * 3, _1_4TH * 3, 72,
            72, 72, 72};
        float widths[] = calcWidths(baseWidths, rct.getWidth());
        Rectangle r = columnHeader(header, widths, rct, colCount);
        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
        rct.setTop(r.getBottom());
        String rowInfo[] = {
            "",
            "",
            "CP",
            "H/W",
            "50,000",
            "5.6%",
            "5",
            "Interest Only",
            "[align=right,border=l][][align=center][align=center][align=right][align=right][align=center][align=center,border=r]"};
        char ltr = 'a';
        dbObj.start();
        String sql = "Select count(*) as x from NOTE_PAYABLE where OWNER_ID='" + clientID + "'";
        HashMap<String, Object> rec = dbObj.execute(sql);

        double dLoans = 0;
        double dInterest = 0;
        double dYears = 0;
        double dSum = 0;
        sql = "select * from NOTE_PAYABLE a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.OWNERSHIP_ID=b.OWNERSHIP_ID and a.TITLE_ID=c.TITLE_ID";
        rec = dbObj.execute(sql);
        int i = 0;
        while (rec != null) {
            rowInfo[0] = Character.toString(ltr) + ".";
            ltr++;
            dLoans = ((Number) (rec.get("LOAN_AMOUNT"))).doubleValue();
            dInterest = ((Number) (rec.get("INTEREST_RATE"))).doubleValue();
            dYears = ((Number) (rec.get("YEARS"))).doubleValue();
            String nType = (String) rec.get("NOTE_TYPE");

            dSum += dLoans;

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = dLoans == 0 ? "" : number.format(dLoans);
            rowInfo[5] = dInterest == 0 ? "" : percent.format(dInterest);
            rowInfo[6] = dYears == 0 ? "" : number.format(dYears);
            rowInfo[7] = nType.equalsIgnoreCase("I") ? "Interest Only"
                    : "Amoritization";

            r = doRow(rowInfo, widths, rct, colCount);
            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if (r.getBottom() - tallest < prctFull.getBottom()) {
                goNewPage();
                top = assetHeader(h1, "Notes Payable (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = columnHeader(header, widths, rct, colCount);
                i = 0;
            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();
        String totals[] = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "[colspan=4,border=l+b][][][][bold=1,align=right,border=t+b][bold=1,border=b+t][bold=1,border=b+t][border=b+r]"};
        totals[4] = number.format(dSum);

        r = doRow(totals, widths, rct, colCount);
        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    private void page1() {
        this.drawBorder(pageIcon);
        this.drawHeader(client, "Asset and Liability Details");
        float top = cashAndEquivalents(prctFull.getTop());
        top = checkNextTable(top);
        top = debt(top);
        top = checkNextTable(top);
        top = notes(top);
        top = checkNextTable(top);
        top = notesPayable(top);
        top = checkNextTable(top);
        top = marketableSecurities(top);
        top = checkNextTable(top);
        top = illiquidInterest(top);
        top = checkNextTable(top);
        top = retirementPlans(top);
        top = checkNextTable(top);
        top = realEstate(top);
        top = checkNextTable(top);
        top = businessInterests(top);
        top = contracts(top);
        top = checkNextTable(top);
        top = personalUseProperty(top);
        top = checkNextTable(top);
        top = lifeInsurance(top);
        top = familyGifting(top);
        top = taxableGifts(top);
        top = drawAssumption();
    }

    private float personalUseProperty(float startTop) {
        String style;
        double dBasis;
        double dMktValue;
        double dLoanBal;
        double dEquity;
        double dGrowth;

        double dBasisSum = 0;
        double dMktValueSum = 0;
        double dLoanBalSum = 0;
        double dEquitySum = 0;

        int colCount = 10;

        int cnt = 0;
        cnt += countRows("PROPERTY", clientID);

        if (cnt == 0) {
            return startTop;
        }

        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Personal Use Property", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);

        // Do header;
        style = new String("[colspan=2,border=l]" + "[]" + "[align=center,border=l+b]" + "" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b+r]");

        String header[] = {" ", "", "Ownership", "Title", "Cost Basis",
            "Market Value", "Loan Balance", "", "Equity",
            "Growth % *", style};

        float baseWidths[] = {_1_4TH, // Letter
            (2f * 72) - _1_8TH, // Desc
            (_1_4TH * 2) + _1_8TH, // Ownership
            (_1_4TH * 2), // Title
            1f * 72, // Cost Basis
            1 * 72, // Market Value
            1 * 72, // Loan Balance
            .75f * 72, // APR & term
            .75f * 72, // Equity
            .75f * 72 // Growth
        };
        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = this.columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        style = new String("[align=right,border=l]" + // Letter
                "[]" + // Descr
                "[align=center]" + // Ownership
                "[align=center]" + // Title
                "[align=right]" + // Cost Basis
                "[align=right]" + // Market Value
                "[align=right]" + // Loan Balance
                "[align=center]" + // apr & term
                "[align=center]" + // equity
                "[align=center,border=r]" // growth
                );
        String rowInfo[] = {"", "", "CP", "H/W", "350,000", "120000", "????",
            "", "", "", style};
        char ltr = 'a';
        dbObj.start();

        String sqlStatement = "select * from PROPERTY a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id";
        HashMap<String, Object> rec = dbObj.execute(sqlStatement);
        int i = 0;
        while (rec != null) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            dBasis = rec.get("BASIS") == null ? 0
                    : ((Number) (rec.get("BASIS"))).doubleValue();
            dMktValue = ((Number) (rec.get("VALUE"))).doubleValue();
            dLoanBal = ((Number) (rec.get("LOAN_BALANCE"))).doubleValue();
            dGrowth = ((Number) (rec.get("GROWTH_RATE"))).doubleValue();
            dEquity = dMktValue - dLoanBal;

            dBasisSum += dBasis;
            dMktValueSum += dMktValue;
            dLoanBalSum += dLoanBal;
            dEquitySum += dEquity;
            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = dBasis == 0 ? "" : number.format(dBasis);
            rowInfo[5] = dMktValue == 0 ? "" : number.format(dMktValue);
            rowInfo[6] = dLoanBal == 0 ? "" : number.format(dLoanBal);
            /*
            if (dLoanBal != 0) {
            rowInfo[7] = number.format(((Number) (rec.get("GROWTH_RATE")))
            .doubleValue())
            + "Months @ "
            + percent.format(((Number) (rec.get("LOAN_INTEREST")))
            .doubleValue());
            } else {
            rowInfo[7] = "";
            }
             */
            rowInfo[8] = dEquity == 0 ? "" : number.format(dEquity);
            rowInfo[9] = percent.format(dGrowth);

            r = this.doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Personal Use Property (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = this.columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();
        // Do totals
        style = new String("[colspan=4,border=l+b]" + "[]" + "[]" + "[]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[border=b]" + "[border=b]" + "[border=b+r]");
        String totals[] = {"", "", "", "", "", "", "", "", "", "", style};

        totals[4] = dBasisSum == 0 ? "" : number.format(dBasisSum);
        totals[5] = dMktValueSum == 0 ? "" : number.format(dMktValueSum);
        totals[6] = dLoanBalSum == 0 ? "" : number.format(dLoanBalSum);

        r = doRow(totals, widths, rct, colCount);

        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    // ***********************************************************
    private float realEstate(float startTop) {
        String style;
        double dMktValue;
        double dBasis;
        double dLoanBal;
        double dEquity;
        double dGrowth;

        double dMktValueSum = 0;
        double dBasisSum = 0;
        double dEquitySum = 0;
        double dLoanBalSum = 0;
        int colCount = 9;
        int cnt = 0;
        cnt += countRows("REAL_ESTATE", clientID);

        if (cnt == 0) {
            return startTop;
        }
        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Real Estate", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        style = new String("[colspan=2,border=l]" + "[]" + "[align=center,border=l+b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b+r]");

        String header[] = {" ", "", "Ownership", "Title", "Cost Basis",
            "Market Value", "Loan Balance", "Equity", "Growth % *", style};

        float baseWidths[] = {_1_4TH, // Letter
            (2.5f * 72) - _1_8TH, // Desc
            (_1_4TH * 2) + _1_8TH, // Ownership
            .5f * 72, // Title
            1 * 72, // Cost Basis
            1 * 72, // Market Value
            1 * 72, // Loan Balance
            1 * 72, // Equity
            .75f * 72 // growth
        };
        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = this.columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        style = new String("[align=right,border=l]" + // Letter
                "[]" + // Descr
                "[align=center]" + // Ownership
                "[align=right]" + // Title
                "[align=right]" + // Cost Basis
                "[align=right]" + // Market Value
                "[align=right]" + // Loan Balance
                "[align=right]" + // Loan Balance
                "[align=center,border=r]" // Growth %
                );
        String rowInfo[] = {"", "", "CP", "H/W", "350,000", "120000", "????",
            "2%", "", style};
        char ltr = 'a';
        dbObj.start();

        String sqlStatement = "select * from REAL_ESTATE a, OWNERSHIP b, TITLE c where OWNER_ID='" + clientID + "' and a.ownership_id=b.ownership_id and a.title_id=c.title_id";

        HashMap<String, Object> rec = dbObj.execute(sqlStatement);
        int i = 0;
        while (rec != null) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            dMktValue = ((Number) (rec.get("VALUE"))).doubleValue();
            dBasis = ((Number) (rec.get("BASIS"))).doubleValue();
            dLoanBal = ((Number) (rec.get("LOAN_BALANCE"))).doubleValue();
            dGrowth = rec.get("GROWTH_RATE") == null ? 0 : ((Number) (rec.get("GROWTH_RATE"))).doubleValue();
            dEquity = dMktValue - dLoanBal;

            dMktValueSum += dMktValue;
            dBasisSum += dBasis;
            dLoanBalSum += dLoanBal;
            dEquitySum += dEquity;

            rowInfo[1] = (String) rec.get("DESCRIPTION");
            rowInfo[2] = (String) rec.get("OWNERSHIP_CODE");
            rowInfo[3] = (String) rec.get("TITLE_CODE");
            rowInfo[4] = dBasis == 0 ? "" : number.format(dBasis);
            rowInfo[5] = dMktValue == 0 ? "" : number.format(dMktValue);
            rowInfo[6] = dLoanBal == 0 ? "" : number.format(dLoanBal);
            rowInfo[7] = dEquity == 0 ? "" : number.format(dEquity);
            rowInfo[8] = percent.format(dGrowth);

            r = this.doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Real Estate (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = this.columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            rec = dbObj.next();
            i++;
        }
        dbObj.stop();

        // Do totals
        style = new String("[colspan=4,border=l+b]" + "[]" + "[]" + "[]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[bold=1,align=right,border=t+b]" + "[border=b+r]");
        String totals[] = {"", "", "", "", "", "", "", "", "", style};
        /*
         * " ", "", "Ownership", "Title", "Cost Basis", "Market Value", "Loan
         * Balance", "Equity", "Growth %",
         */
        totals[4] = dBasisSum == 0 ? "" : number.format(dBasisSum);
        totals[5] = dMktValueSum == 0 ? "" : number.format(dMktValueSum);
        totals[6] = dLoanBalSum == 0 ? "" : number.format(dLoanBalSum);
        totals[7] = dEquitySum == 0 ? "" : number.format(dEquitySum);
        r = doRow(totals, widths, rct, colCount);

        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    // /******************************************************
    private float retirementPlans(float startTop) {

        int colCount = 9;

        int cnt = 0;
        cnt += countRows("RETIREMENT", clientID);

        if (cnt == 0) {
            return startTop;
        }

        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Retirement Plans", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        String header[] = {
            " ",
            "",
            "Type of Plan",
            "Annual Contrib.",
            "MarketValue",
            "Projected Ret. Inc.",
            "Name & Beneficiary",
            "Growth % *",
            "Life Exp. Calc",
            "[colspan=2,border=l][][align=center,border=l+b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b][align=center,border=b+r]"};

        float baseWidths[] = {_1_4TH, 2.25f * 72, _1_4TH * 3, _1_4TH * 3,
            1 * 72, 1 * 72, 1 * 72, .75f * 72, .75f * 72};
        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        String rowInfo[] = {
            "",
            "",
            "CP",
            "H/W",
            "350,000",
            "120000",
            "????",
            "2%",
            "",
            "[align=right,border=l][][align=center][align=right][align=right][align=right][align=right][align=center][align=center,border=r]"};
        char ltr = 'a';

        RetirementBean rBean = new RetirementBean();

        ArrayList<RetirementBean> rList = rBean.getBeans("OWNER_ID='" + Integer.toString(clientID) + "'");

        double dAnnualContrib = 0;
        double dMarketValue = 0;
        double dAnnualContribSum = 0;
        double dMarketValueSum = 0;
        double dGrowthRate = 0;

        int i = 0;
        for (RetirementBean retirement : rList) {
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            dAnnualContrib = retirement.getAnnualContrib();
            dAnnualContribSum += dAnnualContrib;
            dMarketValue = retirement.getValue();
            dMarketValueSum += dMarketValue;
            dGrowthRate = retirement.getGrowthRate();

            String lifeExpCalcDesc[] = {"Recalc. Both", "Recalc. Owner",
                "Recalc. Benef.", "Recalc. Neither"};

            String lifeExpCodes = "bofn";

            int lifeExpIdx = lifeExpCodes.indexOf(retirement.getLifeExpCalc().toLowerCase());

            rowInfo[1] = retirement.getDescription();
            long ownershipId = retirement.getOwnershipId();
            Ownership ow = Ownership.getType((int) ownershipId);
            if (ow != null) {
                rowInfo[2] = ow.description();
            }
            rowInfo[3] = dAnnualContrib == 0 ? "" : number.format(dAnnualContrib); // annual contrib
            rowInfo[4] = dMarketValue == 0 ? "" : number.format(dMarketValue); // Market
            // Value
            rowInfo[5] = ""; // proj, ret income
            rowInfo[6] = retirement.getNamedBeneficiary();
            rowInfo[7] = percent.format(dGrowthRate); // Growth %
            rowInfo[8] = lifeExpCalcDesc[lifeExpIdx]; // life exp calc

            r = doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Retirement Plans (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            i++;
        }
        // Do totals
        String totals[] = {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "[colspan=3,border=l+b][][][bold=1,align=right,border=t+b][bold=1,align=right,border=t+b][border=b][border=b][border=b][border=b+r]"};

        totals[3] = dAnnualContribSum == 0 ? "" : number.format(dAnnualContribSum);
        totals[4] = dMarketValueSum == 0 ? "" : number.format(dMarketValueSum);

        r = doRow(totals, widths, rct, colCount);

        rct.setTop(r.getBottom() - _1_8TH);

        return rct.getTop();
    }

    /**
     * @param clientID
     *            The clientID to set.
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    private void setTableFontandSize(PageTable table) {
        table.setTableFont("arial.ttf");
        table.setTableFontBold("arialbd.ttf");
        table.setTableFontItalic("ariali.ttf");
        table.setTableFontBoldItalic("arialbi.ttf");
        table.setFontSize(9);
        table.setPaddingBottom(_1_32TH + _1_64TH);

    }

    private float taxableGifts(float startTop) {
        String style;
        double totalAmount = 0;
        int colCount = 9;

        GiftBean gift = new GiftBean();
        GiftBean giftBean[] = gift.query(gift.OWNER_ID, Integer.toString(clientID));
        if (giftBean == null) {
            return startTop;
        }

        int cnt = 0;
        for (int k = 0; k < giftBean.length; k++) {
            cnt += giftBean[k].getGiftPlanning().equals("N") ? 1 : 0;
        }

        if (cnt == 0) {
            return startTop;
        }

        startTop = checkNextTable(startTop);

        String h1 = Integer.toString(heading++) + ".";

        float top = assetHeader(h1, "Taxable Gifts", startTop);
        float tallest = 0.0f;

        Rectangle rct = new Rectangle(prctFull);
        rct.setTop(top);
        rct.setRight(rct.getLeft() + 8.5f * 72);
        // Do header;

        style = new String("[align=center,border=l]" + "[align=center,border=l+b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b]" + "[align=center,border=b+r]");

        String header[] = {"", "Description", "By Whom", "Date", "Amount",
            "Regularity", "Exemption Used", "Gift Tax Paid",
            "Return Filed", style};

        float baseWidths[] = {_1_2TH, // Letter
            (1.75f * 72), // Description
            (_1_4TH * 3), // By Whom
            (_1_4TH * 3), // Date
            .75f * 72, // Amount
            .75f * 72, // regularity
            .75f * 72, // Exemption used
            .75f * 72, // Gift tax Paid
            .75f * 72 // Tax return Filed
        };

        float widths[] = calcWidths(baseWidths, rct.getWidth());

        Rectangle r = this.columnHeader(header, widths, rct, colCount);

        tallest = r.getHeight() > tallest ? r.getHeight() : tallest;

        rct.setTop(r.getBottom());

        style = new String("[border=l]" + // letter
                "[align=center]" + // Desc
                "[align=center]" + // By Whome
                "[align=center]" + // Date
                "[align=right]" + // Amount
                "[align=center]" + // regularity
                "[align=center]" + // exemption used
                "[align=center]" + // gift tax paid
                "[align=center,border=r]" // return filed
                );
        String rowInfo[] = {"", "test desc", "Paul", "11/24/2005", "11,000",
            "Annual", "Y", "Y", "Y", style};
        char ltr = 'a';

        for (int i = 0; i < giftBean.length; i++) {
            if (!giftBean[i].getGiftPlanning().equals("N")) {
                continue;
            }
            rowInfo[0] = "" + ltr + ".";
            ltr++;
            // Now grab all the pertinent numbers
            totalAmount += giftBean[i].getAmount();
            rowInfo[1] = giftBean[i].getDescription();
            rowInfo[2] = giftBean[i].getByWhom();
            rowInfo[3] = giftBean[i].getDate();
            rowInfo[4] = number.format(giftBean[i].getAmount());
            String regular = "Annual";
            if (giftBean[i].getRegularity().equals("B")) {
                regular = "Bi Annual";
            }
            if (giftBean[i].getRegularity().equals("T")) {
                regular = "Tri Annual";
            }
            if (giftBean[i].getRegularity().equals("O")) {
                regular = "One Time";
            }
            rowInfo[5] = regular;
            rowInfo[6] = "" + giftBean[i].getExemptionUsed();
            rowInfo[7] = giftBean[i].getGiftTaxPaid();
            rowInfo[8] = giftBean[i].getGiftTaxReturnFiled();

            r = this.doRow(rowInfo, widths, rct, colCount);

            if ((i & 1) == 1) {
                drawFilledRect(r, new Color(239, 239, 239));
            }
            tallest = r.getHeight() > tallest ? r.getHeight() : tallest;
            if ((r.getBottom() - tallest) < prctFull.getBottom()) {
                // we need to go to a new page.
                goNewPage();
                top = assetHeader(h1, "Taxable Gifts (cont.)", prctFull.getTop());
                rct.setTop(top);
                r = this.columnHeader(header, widths, rct, colCount);
                i = 0;

            }
            rct.setTop(r.getBottom());
            i++;
        }
        // Do totals
        style = new String("[border=l+b]" + // letter
                "[align=center,border=b]" + // Desc
                "[align=center,border=b]" + // By Whome
                "[align=center,border=b]" + // Date
                "[align=right,border=t+b]" + // Amount
                "[align=center,border=b]" + // regularity
                "[align=center,border=b]" + // exemption used
                "[align=center,border=b]" + // gift tax paid
                "[align=center,border=b+r]" // return filed
                );

        // String totals[] = {"","","","","","","","","", "", style};
        String totals[] = {"", "", "", "", "0.00", "", "", "", "", style};

        totals[4] = number.format(totalAmount);

        r = this.doRow(totals, widths, rct, 9);
        rct.setTop(r.getBottom() - _1_8TH);
        return rct.getTop();
    }
}
