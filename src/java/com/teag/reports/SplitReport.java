/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teag.reports;

import com.estate.pdf.PageTable;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

/**
 *
 * @author Paul
 */
public class SplitReport extends Page {

    String heading = "Split Dollar Loan Inheritance Trust";
    String clients = "Hal and Linda Rosenson";

    public SplitReport(Document document, PdfWriter writer) {
        super(document, writer);
        pageIcon = 2;
    }

    public void draw() {
//        page1();
//        newPage();
        page2();
        newPage();
        page3();
        newPage();
        page4();
    }

    public void page1() {
        Rectangle rct;

        drawBorder(pageIcon, "SPLIT");

        float ptSize = 12f;
        drawHeader(client, heading);

        String line1 = "Hal and Linda make a gift of $400,000 to the Irrevocable Inheritance (Crummey) Trust; this is also designated as a Dynasty/Multi Generational Trust.";
        String line2 = "Two $10 Million life insurance policies on Hal and Linda are purchased within the trust. Hal and Linda lend the trust $65,000 to bind the policies for 45 days.";
        String line3 = "Thirty days after the two $10 million policies on Hal and Linda are in full force, they lend the trust an additional $1,361,000 to pay the balance of the first year premium.";
        String line4 = "At the beginning of the second year, Hal and Linda loan the trust an additional $1,426,800 for the second annual premium. Finally, at the beginning of the third year, they make a final loan of $1,075,000 for the last premium payment. ";
        String line5 = "Upon second death, (of Hal and Linda), the trust life insurance proceeds are paid to the multi-generational trust free from both income and estate taxes.";
        String line6 = "The trust pays the estate of Hal and Linda the notes and accrued interest at 3.6% (AFR mid-term rate).  ";
        String line7 = "Distributions from the remaining proceeds are made to the children and grandchildren from the trust, as directed by instructions from Hal and Linda. ";

        String theProcess[] = new String[]{
            line1, line2, line3, line4, line5, line6, line7
        };

        rct = new Rectangle(this.prctULeft);
        drawSection(rct, "The Process", theProcess, 1);

        String effects[] = new String[]{
            "Assets are irrevocably transferred to the family.",
            "The value of the notes and all accrued interest is subject to estate tax."
        };

        rct = new Rectangle(prctURight);
        rct = drawSection(rct, "Side effects/Draw backs of using this technique", effects, 2);
    }

    public void page2() {
        drawBorder(pageIcon, "SPLIT");
        drawHeader(client, heading);
        this.drawDiagram("MultiG1.png", prctFull, 0);
    }

    public void page3() {
        drawBorder(pageIcon, "SPLIT");
        drawHeader(client, heading);
        Rectangle rct = new Rectangle(prctLeft);
        rct.setLeft(rct.getLeft() - _1_4TH);
        rct.setBottom(rct.getBottom() - _1_4TH);

        if (userInfo.isSingle()) {
            if (userInfo.getClientGender().equalsIgnoreCase("M")) {
                drawDiagram("MultiG3-M.png", prctLeft, BOTTOM + LEFT);
            } else {
                drawDiagram("MultiG3-F.png", prctLeft, BOTTOM + LEFT);
            }
        } else {
            drawDiagram("MultiG3.png", prctLeft, BOTTOM + LEFT);
        }

        String[] clientMultiGenDesc = new String[]{
            "Hal and Linda sell municipal bonds to make a gift ($400,000) and loan (total over 3 years of $3,929,000) to the Split Dollar Loan Inheritance Trust (Trust).",
            "The loan is used by the Trust to purchase two $10 million “survivorship” policies on Hal and Linda’s lives.",
            "Upon second death, (of Hal and Linda), the trust life insurance proceeds are paid to the Trust free from both income and estate taxes.",
            "Distributions from the remaining proceeds are made to the children and Grand children from the trust, as directed by instructions from Hal and Linda.",
            "Trust assets are be protected from creditors, estate taxes and divorced spouses, etc.",
            "Earnings in the Trust can benefit future posterity according to the financial philosophy described by Hal and Linda in the trust instrument.",
            "Trust assets avoid estate taxes as long as the trust is allowed to continue. The length of time is based upon the laws of each state, called the “Rule against Perpetuities”. In Florida, this trust can last for 360 years."
        };

        String[] clientSideEffects = new String[]{
            "Assets are irrecocably transferred to the family.",
            "The value of the notes and all accrued interest is subject to estate tax."
        };

        rct = this.calcSectionRect(prctRight, "", clientMultiGenDesc, 1);
        rct.setTop(prctRight.getTop());
        rct = this.drawSection(rct, "The Process", clientMultiGenDesc, 1, 12, 10.5f);
        rct.setTop(rct.getBottom() - _1_4TH);
        drawSection(rct, "Side Effects/Drawbacks of Using this Technique", clientSideEffects, 1, 12, 10.5f);
    }

    public void page4() {

        drawBorder(pageIcon, "Split");
        drawHeader(client, heading);

        // Summary
        Rectangle rct = new Rectangle(prctTop);
        PageTable table = new PageTable(writer);
        table.setTableFormat(rct, 3);
        table.setTableFont("arial.TTF");
        table.setTableFontBold("arialbd.TTF");
        table.setFontSize(12);
        float widths[] = {.45f, .1f, .45f};
        int alignments[] = {PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_RIGHT};
        table.setColumnWidths(widths);
        table.setColumnAlignments(alignments);

        String cells[][] = {
            {"SUMMARY", "", "", "[colspan=3,align=center,bold=1][][]"},
            {"", "", "", "[][][]"},
            {"Asset Value", "", dollar.format(3927000), "[][][]"},
            {"Initial Gift", "", dollar.format(400000), "[][][]"},
            {"Note Value", "", dollar.format(3927000), "[][][]"},
            {"Note Interest Rate", "", percent.format(.036), "[][][]"},
            {"Life Insurance Death Benefit", "", dollar.format(20000000), "[][][]"},
            {"Life Insurance Total Premium Payments", "", dollar.format(4327000), "[][][]"},};
        table.buildTableEx(cells);
        table.drawTable();

        // We need to build a table here showing the $400K growing in the MGEN
        // Build the Cash Flow Table here!!!!
        // Draw cash flow table
        rct = new Rectangle(prctBottom);
        rct.setTop(rct.getTop() + (1.f * 72));
        float widths2[] = {.1f, .2f, .2f, .2f, .2f, .1f, .2f, .2f};
        int alignments2[] = {PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_RIGHT,
            PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT,
            PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_RIGHT};

        PageTable cashFlow = new PageTable(writer);
        cashFlow.setTableFormat(rct, 8);
        cashFlow.setTableFont("arial.TTF");
        cashFlow.setTableFontBold("arialbd.TTF");
        cashFlow.setFontSize(7);

        cashFlow.setColumnWidths(widths2);
        cashFlow.setColumnAlignments(alignments2);

        String tHeading[][] = {
            {"Trust Cash Flow", "", "", "", "", "", "", "",
                "[bold=1,colspan=8,align=center,ptsize=12][][][][][][][]"},
            {" ", "", "", "", "", "", "", "", "[][][][][][][][]"},
            {
                "Year",
                "Beg. Principal",
                "Growth",
                "Income",
                "End Balance",
                "Note",
                "Life Insruance",
                "Amt. to Family *",
                "[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]" + "[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]" + "[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]" + "[bold=1,border=l+t+b+r,align=center][bold=1,border=l+t+b+r,align=center]" + "[bold=1,border=l+t+b+r,align=center]"}};

        String crows[][] = cashFlow();
        cashFlow.buildTableEx(tHeading);
        cashFlow.buildTableEx(crows);
        cashFlow.drawTable();
        // Draw the following label at the bottom
        String lb1 = "* Includes life insurance should death occur at any year minus the value of the note.";
        drawLabel(lb1,
                prctBottom, "GARA.TTF", new java.awt.Color(64, 64, 64), 9, LBL_LEFT,
                LBL_BOTTOM);

    }

    private String[][] cashFlow() {
        String crows[][] = new String[25][9];

        com.teag.EstatePlan.SplitDollarCashFlow sdc = new com.teag.EstatePlan.SplitDollarCashFlow();
        sdc.processSplitDollar();

        double beginBalance = 400000;
        double incomeRate = .03;
        double growthRate = .05;
        double note[] = sdc.getLoanAmount();
        double deathBenefit = sdc.getDeathBenefit();


        for (int i = 0; i < 25; i++) {
            crows[i][0] = this.integer.format(i+1);
            crows[i][1] = dollar.format(beginBalance);
            crows[i][2] = dollar.format(beginBalance * growthRate);
            crows[i][3] = dollar.format(beginBalance * incomeRate);
            beginBalance = beginBalance * (1.0 + (growthRate + incomeRate));
            crows[i][4] = dollar.format(beginBalance); // Now the ending balance
            crows[i][5] = dollar.format(note[i]);
            crows[i][6] = dollar.format(deathBenefit - note[i]);
            crows[i][7] = dollar.format(deathBenefit - note[i] + beginBalance);
            crows[i][8] = "[border=l+r+b][border=r+b][border=r+b][border=r+b][border=r+b][border=r+b,color="
					+ makeColor(0, 0, 24) + "][border=r+b][border=r+b]";
        }
        return crows;
    }
}
