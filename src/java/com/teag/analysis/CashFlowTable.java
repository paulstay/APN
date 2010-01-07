package com.teag.analysis;

/**
 * @author stay Created on May 25, 2005
 *  
 */
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.teag.bean.ABTrust;
import com.teag.bean.BizContractBean;
import com.teag.bean.ClientBean;
import com.teag.bean.InsuranceBean;
import com.teag.bean.NotePayableBean;
import com.teag.bean.NotesBean;
import com.teag.bean.PropertyBean;
import com.teag.util.Function;
import com.zcalc.zCalc;

public class CashFlowTable extends AnalysisSqlBean {

    /**
     *
     */
    private static final long serialVersionUID = 2333876912893842855L;
    ArrayList<CFRow> rows = new ArrayList<CFRow>();
    Iterator itr;
    String tableName;
    CashFlow cashFlow;
    int finalDeath;
    ClientBean cb;
    private final static int MAX_TABLE = 120;
    double toFamily[] = new double[MAX_TABLE];
    double toCharityLine[] = new double[MAX_TABLE];
    double toCharity[] = new double[MAX_TABLE];
    double toTax[] = new double[MAX_TABLE];
    double taxableEstate[] = new double[MAX_TABLE];
    double split[] = new double[MAX_TABLE];
    double exemptionYear[] = new double[MAX_TABLE];

    /**
     * @return Returns the toCharity.
     */
    public double[] getToCharity() {
        return toCharity;
    }

    /**
     * @return Returns the toFamily.
     */
    public double[] getToFamily() {
        return toFamily;
    }

    public double[] getToEstate() {
        return taxableEstate;
    }

    /**
     * @return Returns the toTax.
     */
    public double[] getToTax() {
        return toTax;
    }

    public CashFlowTable() {
    }

    public void init() {
        cashFlow = new CashFlow();
        cashFlow.setClient(cb);
        cashFlow.buildTables();
        finalDeath = cashFlow.cfb.getFinalDeath();
    }

    public void finalize() {
        cashFlow.finalize();
        cashFlow = null;
        cleanUp();
        toFamily = null;
        toCharityLine = null;
        toTax = null;
    }

    public void genTable() {
        rows.clear();
        genCashFlow();
        genSecurities();
        // genBonds();
        genNetWorth();
        genEstateDist();
        // genEstateCashNeeds();
        genChartData();
    }

    public void genChartData() {
        double baseFamGift = cashFlow.getTaxableGift(0);
        double famGift = baseFamGift;

        double baseCGift = cashFlow.getCharity(0);
        double cGift = 0;
        double vcfValues[] = new double[finalDeath];

        // We need to add the value of the VCF family items as well.
        ArrayList<VariableCashFlow> al = cashFlow.vcfItems;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("F")) {
                double[] vTable = vcf.getVTable();
                for (int i = 0; i < finalDeath; i++) {
                    vcfValues[i] += vTable[i];
                }
            }
        }

        for (int i = 0; i < finalDeath; i++) {
            famGift = baseFamGift + famGift * 1.06;
            cGift = baseCGift + cGift * 1.06;
            // toCharity[i] = cGift + toCharityLine[i];
            //(taxableEstate[i] + rnd(gift)) - rnd(taxes[i]);
            //toFamily[i] = (1000.0 * nw.getTotal(i)) + famGift - taxes[i];
            toFamily[i] = 1000.0 * (taxableEstate[i] + rnd(famGift) - rnd(taxes[i]));
            toFamily[i] += vcfValues[i];

            toTax[i] = taxes[i];
            split[i] = (1000 * nw.getTotal(i)) - taxes[i];
        }
    }

    public void genCashFlow() {
        genCashFlowHeader();

        // Cash Flow
        cashItems();
        // consulting();
        socialSecurity();
        cashInterest();
        cashMuni();
        cashIntDiv();
        cashRetirement();
        cashRealEstate();
        cashBusiness();
        cashBizContract();
        cashIlliquid();
        cashIlliquidSale();
        cashNotes();
        cashTotalRec();

        // Cash Dispursements
        genDispersementHeader();
        dispItems();
        debt();
        mortgage();
        giftsToChildren();
        dispNotes();
        // giftTax();
        charitableCont();
        taxes();
        dispCharitableDeduction();
        dispDepreciation();
        dispersementTotal();
        blankRow();
        excessCash();
    }

    public void blankRow() {
        CFRow r = new CFRow();
        r.setHeader(" ");
        r.setIndentLevel(0);
        r.setTextFill(0); // Left
        r.setFontWeight(Font.BOLD);
        r.setBarTop(true);
        r.setBarBottom(true);
        rows.add(r);
    }

    public void genCashFlowHeader() {
        CFRow r = new CFRow();
        r.setHeader("I.\tCASH FLOW");
        r.setIndentLevel(0);
        r.setTextFill(0); // Left
        r.setFontSize(2);
        r.setFontWeight(Font.BOLD);

        rows.add(r);

        CFRow r2 = new CFRow();
        r2.setHeader("CASH RECEIPTS:");
        r2.setIndentLevel(1);
        r2.setTextFill(0); // Left
        r2.setFontSize(1);
        r2.setFontWeight(Font.BOLD);

        rows.add(r2);
    }

    public void genDispersementHeader() {
        CFRow r2 = new CFRow();
        r2.setHeader("DISBURSEMENTS:");
        r2.setIndentLevel(1);
        r2.setTextFill(0); // Left
        r2.setFontSize(1);
        r2.setFontWeight(Font.BOLD);

        rows.add(r2);

    }

    public void cashItems() {
        ArrayList<VariableCashFlow> al = cashFlow.cashItem;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("C")) {
                CFRow r = new CFRow();
                r.setHeader(vcf.getDescription());
                r.setIndentLevel(1);
                r.setTextFill(0);
                double[] vtable = vcf.getVTable();
                boolean rFlag = false;
                for (int i = 0; i < finalDeath; i++) {
                    double v = vtable[i];
                    CFCol c = new CFCol();
                    if (v != 0) {
                        rFlag = true;
                    }
                    if (v < 0) {
                        c.setStrValue("" + formatNumber(v));
                        c.setFontColor(Color.red);
                    } else {
                        c.setStrValue(formatNumber(v));
                    }
                    c.setTextFill(3);
                    r.addCol(c);
                }
                if (rFlag) {
                    rows.add(r);
                }
            }
        }
    }

    public void consulting() {
        CFRow r = new CFRow();
        r.setHeader("Consulting Income");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getConsulting(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void socialSecurity() {
        CFRow r = new CFRow();
        r.setHeader("Social Security");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left
        boolean rFlag = false;

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getSocial(i);
            CFCol c = new CFCol();
            if (v != 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void cashInterest() {
        CFRow r = new CFRow();
        r.setHeader("Interest On Cash and Equivalents");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getInterestOnCash(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void cashRetirement() {
        CFRow r = new CFRow();
        r.setHeader("Retirement Portfolio");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getCashRetirement(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void cashNotes() {
        CFRow r = new CFRow();
        r.setHeader("Notes Receivable");
        r.setIndentLevel(1);
        r.setTextFill(0);
        boolean rFlag = false;

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getNotes(i);
            CFCol c = new CFCol();
            if (v > 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void dispNotes() {
        CFRow r = new CFRow();
        r.setHeader("Notes Payable");
        r.setIndentLevel(1);
        r.setTextFill(0);
        boolean rFlag = false;

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getNotesPayable(i);
            CFCol c = new CFCol();
            if (v > 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void cashRealEstate() {
        CFRow r = new CFRow();
        r.setHeader("Real Estate Net Income");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getRealEstate(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void cashIlliquid() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Illiquid Income");
        r.setIndentLevel(1);
        r.setTextFill(0);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getIlliquid(i);
            CFCol c = new CFCol();
            if (v > 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue("" + formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void cashBizContract() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Salary from Contracts");
        r.setIndentLevel(1);
        r.setTextFill(0);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getContractSalary(i);
            CFCol c = new CFCol();
            if (v > 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue("" + formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void cashIlliquidSale() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Sale of Illiquid Assets");
        r.setIndentLevel(1);
        r.setTextFill(0);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getIlliquidSale(i);
            CFCol c = new CFCol();
            if (v > 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue("" + formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void cashBusiness() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Business Net Income");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getBusiness(i);
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }

        rFlag = false;
        CFRow s = new CFRow();
        s.setHeader("Sale of Business");
        s.setIndentLevel(1);
        s.setTextFill(0);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getSaleBusiness(i);
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            s.addCol(c);
        }
        if (rFlag) {
            rows.add(s);
        }
    }

    public void cashTotalRec() {
        CFRow r = new CFRow();
        r.setHeader("Total Cash Receipts");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left
        r.setFontWeight(Font.BOLD);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getCRTotalRnd(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + totalFormat(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(totalFormat(v));
            }
            c.setTextFill(3);
            c.setFontWeight(Font.BOLD);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void cashMuni() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Interest on Municipal Bonds");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getMunicipalBonds(i);
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void cashIntDiv() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Int/Div on Securities Portfolio");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getSecurities(i);
            if (v > 0 || v < 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void dispItems() {
        ArrayList<VariableCashFlow> al = cashFlow.dispItem;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("D")) {
                CFRow r = new CFRow();
                r.setHeader(vcf.getDescription());
                r.setIndentLevel(1);
                r.setTextFill(0);
                double[] vtable = vcf.getVTable();
                boolean rFlag = false;
                for (int i = 0; i < finalDeath; i++) {
                    double v = vtable[i];
                    CFCol c = new CFCol();
                    if (v != 0) {
                        rFlag = true;
                    }
                    if (v < 0) {
                        c.setStrValue("" + formatNumber(v));
                        c.setFontColor(Color.red);
                    } else {
                        c.setStrValue(formatNumber(v));
                    }
                    c.setTextFill(3);
                    r.addCol(c);
                }
                if (rFlag) {
                    rows.add(r);
                }
            }
        }
    }

    public void netItems() {
        ArrayList<VariableCashFlow> al = cashFlow.netItem;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("N")) {
                CFRow r = new CFRow();
                r.setHeader(vcf.getDescription());
                r.setIndentLevel(1);
                r.setTextFill(0);
                double[] vtable = vcf.getVTable();
                boolean rFlag = false;
                for (int i = 0; i < finalDeath; i++) {
                    double v = vtable[i];
                    nw.addValue(i, rnd(v));
                    CFCol c = new CFCol();
                    if (v != 0) {
                        rFlag = true;
                    }
                    if (v < 0) {
                        c.setStrValue(formatNumber(v) + "");
                        c.setFontColor(Color.red);
                    } else {
                        c.setStrValue(formatNumber(v));
                    }
                    c.setTextFill(3);
                    r.addCol(c);
                }
                if (rFlag) {
                    rows.add(r);
                }
            }
        }
    }

    public void giftsToChildren() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Annual Gifts");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getTaxableGift(i);
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void giftTax() {
        boolean rFlag = true;
        CFRow r = new CFRow();
        r.setHeader("Gift Tax");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getTaxableGift(i);
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void debt() {
        dbobj.start();
        String sql = "select * from DEBT where owner_id='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);
        while (rec != null) {
            CFRow r = new CFRow();
            r.setHeader((String) rec.get("DESCRIPTION"));
            r.setIndentLevel(1);
            r.setTextFill(0); // Left

            // Calcualte payment
            double presentValue = getDouble(rec, "VALUE");
            double interest = getDouble(rec, "INTEREST_RATE");
            int years = getInt(rec, "LOAN_TERM");
            double pmt = Function.PMT(presentValue, interest, years);
            for (int i = 0; i < finalDeath; i++) {
                if (i < years) {
                    CFCol c = new CFCol();
                    c.setStrValue(formatNumber(pmt));
                    c.setTextFill(3);
                    r.addCol(c);
                }
            }
            rows.add(r);
            rec = dbobj.next();
        }
        dbobj.stop();
    }

    public void mortgage() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Real Estate Mortgage Payments");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getMortgage(i);
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            c.setStrValue(formatNumber(v));
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }

        rFlag = false;
        CFRow r2 = new CFRow();
        r2.setHeader("Personal Mortgage Payments");
        r2.setIndentLevel(1);
        r2.setTextFill(0);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getPersonalMortgage(i);
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            c.setStrValue(formatNumber(v));
            c.setTextFill(3);
            r2.addCol(c);

        }

        if (rFlag) {
            rows.add(r2);
        }

    }

    public void charitableCont() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Charitable Giving");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getCharity(i);
            CFCol c = new CFCol();
            if (v < 0) {
                rFlag = true;
                c.setStrValue("" + formatNumber(v));
                c.setFontColor(Color.red);
            } else if (v > 0) {
                rFlag = true;
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void taxes() {

        if (cashFlow.cfb.isUseTax()) {
            CFRow r = new CFRow();
            r.setHeader("Income Tax");
            r.setIndentLevel(1);
            r.setTextFill(0); // Left

            for (int i = 0; i < finalDeath; i++) {
                double v = cashFlow.getTax(i);
                CFCol c = new CFCol();
                if (v < 0) {
                    c.setStrValue("" + formatNumber(v));
                    c.setFontColor(Color.red);
                } else {
                    c.setStrValue(formatNumber(v));
                }
                c.setTextFill(3);
                r.addCol(c);
            }
            rows.add(r);
        }
    }

    // This is not included in the dispursements, but we show how much was
    // deducted from the cash flow
    // As per henry, we are not going to display this in scenario 1, just
    // scenario 2, and then
    // only if we got a charitable deduction from one of the tools.
    public void dispCharitableDeduction() {
        /*
         * CFRow r = new CFRow(); r.setHeader("Charitable Deduction");
         * r.setIndentLevel(1); r.setTextFill(0); boolean rFlag = false;
         *
         * for(int i=0; i < finalDeath; i++){ double v =
         * cashFlow.charitableDeduction[i]; if(v > 0) rFlag = true; CFCol c =
         * new CFCol(); c.setStrValue("[" + formatNumber(v) + "]");
         * c.setTextFill(3); r.addCol(c); } if(rFlag) rows.add(r);
         */
    }

    // This is not included in the totals, but subtracted from the cash receipts
    public void dispDepreciation() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Depreciation");
        r.setIndentLevel(1);
        r.setTextFill(0);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.depreciation[i];
            if (v > 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            c.setStrValue("[" + formatNumber(v) + "]");
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void dispersementTotal() {
        CFRow r = new CFRow();
        r.setHeader("Total Disbursements");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left
        r.setFontWeight(Font.BOLD);
        r.setBarTop(true);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getDISPTotalRnd(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + totalFormat(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(totalFormat(v));
            }
            c.setTextFill(3);
            c.setFontWeight(Font.BOLD);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void excessCash() {
        CFRow r = new CFRow();
        r.setHeader("EXCESS CASH FLOW");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left
        r.setFontWeight(Font.BOLD);
        r.setBarTop(true);
        r.setBarBottom(true);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getCRTotalRnd(i) - cashFlow.getDISPTotalRnd(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + totalFormat(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(totalFormat(v));
            }
            c.setTextFill(3);
            c.setFontWeight(Font.BOLD);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void genSecurities() {
        securitiesHeader();
        secBegin();
        secGrowth();
        secExcessCash();
        secEnding();
    }

    public void securitiesHeader() {
        CFRow r = new CFRow();
        r.setHeader("II.\tSECURITIES PORTFOLIO");
        r.setIndentLevel(0);
        r.setTextFill(0); // Left
        r.setFontSize(2);
        r.setFontWeight(Font.BOLD);
        rows.add(r);
    }

    public void secBegin() {
        CFRow r = new CFRow();
        r.setHeader("Beginning. Balance Securities Portfolio");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left
        r.setFontWeight(Font.BOLD);

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getSecurityBalance(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void secGrowth() {
        CFRow r = new CFRow();
        r.setHeader("Growth:");
        r.setIndentLevel(2);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getGrowth(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);

    }

    public void secExcessCash() {
        CFRow r = new CFRow();
        r.setHeader("Excess Cash Flow");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left

        for (int i = 0; i < finalDeath; i++) {
            // double v = cashFlow.getCRTotal(i) - cashFlow.getDISPTotal(i);

            double v = cashFlow.getCRTotalRnd(i) - cashFlow.getDISPTotalRnd(i);

            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + totalFormat(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(totalFormat(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);

    }

    public void secEnding() {
        CFRow r = new CFRow();
        r.setHeader("Ending Balance Securities Portfolio");
        r.setIndentLevel(1);
        r.setTextFill(0); // Left
        r.setFontWeight(Font.BOLD);

        for (int i = 0; i < finalDeath; i++) {
            // double excessCash = (cashFlow.getCRTotalRnd(i) -
            // cashFlow.getDISPTotalRnd(i))*1000;
            // double v = cashFlow.getSecurityBalance(i) + excessCash +
            // cashFlow.getGrowth(i);
            double v = cashFlow.getSecurityBalance(i) + cashFlow.getExcessCash(i) + cashFlow.getGrowth(i);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }
    NetWorth nw = new NetWorth();
    double liquidAssets[];

    public void genNetWorth() {
        liquidAssets = new double[finalDeath];
        genNetWorthHeader();
        getCash();
        getMBonds();
        getSecurities();
        getIlliquid();
        getBizContract();
        getRetirementPlans();
        getBusiness();
        getRealEstate();
        getPersonalProperty();
        getNotes();
        getLifeIns();
        netItems();
        getDebts();
        getLiabilities();
        getNotePayable();
        getNetWorth();
    }

    public void genNetWorthHeader() {
        CFRow r = new CFRow();
        r.setHeader("III. Net Worth");
        r.setIndentLevel(0);
        r.setTextFill(0); // Left
        r.setFontSize(2);
        r.setFontWeight(Font.BOLD);
        rows.add(r);
    }

    public void getCash() {
        dbobj.start();
        String sql = "select * from Cash where owner_id='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);
        while (rec != null) {
            String description = (String) (rec.get("DESCRIPTION"));
            double value = getDouble(rec, "VALUE");
            double growth = getDouble(rec, "GROWTH_RATE");
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            for (int i = 0; i < finalDeath; i++) {
                nw.addValue(i, rnd(value));
                liquidAssets[i] += rnd(value);
                CFCol col = new CFCol();
                col.setTextFill(3);
                col.setStrValue(formatNumber(value));
                r.addCol(col);
                value += value * growth;
            }
            rows.add(r);
            rec = dbobj.next();
        }
        dbobj.stop();
    }

    public void getMBonds() {
        CFRow r = new CFRow();
        r.setHeader("Municipal Bonds");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        boolean rFlag = false;
        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getMBonds(i);
            nw.addValue(i, rnd(v));
            liquidAssets[i] += rnd(v);
            CFCol c = new CFCol();
            c.setTextFill(3);
            if (v != 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void getSecurities() {
        CFRow r = new CFRow();
        r.setHeader("Securities Portfolio");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        boolean rFlag = false;
        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getSecurityBalance(i) + cashFlow.getExcessCash(i) + cashFlow.getGrowth(i);
            nw.addValue(i, rnd(v));
            liquidAssets[i] += rnd(v);
            CFCol c = new CFCol();
            if (v != 0) {
                rFlag = true;
            }
            if (v < 0) {
                c.setStrValue("" + formatNumber(v) + "");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void getBizContract() {

        BizContractBean bean = new BizContractBean();
        ArrayList<BizContractBean> bList = bean.getBeans(BizContractBean.OWNER_ID + "='" + cb.getPrimaryId() + "'");

        for (BizContractBean b : bList) {
            String description = (String) b.getDescription();
            double value = b.getValue();
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);
            int sYear = b.getStartYear() - cashFlow.currentYear;
            int eYear = b.getEndYear() - cashFlow.currentYear;

            for (int i = 0; i < finalDeath; i++) {
                CFCol col = new CFCol();
                col.setTextFill(3);
                // If we are liquidating the contract and adding it into the cashflow receipts, we don't need to show a networth here
                if (b.isCashFlow()) {
                    if (i >= sYear && i < eYear) {
                        nw.addValue(i, rnd(value));
                        col.setStrValue(formatNumber(value));
                    } else {
                        col.setStrValue("0");
                    }
                } else {
                    if (i >= sYear && i <= eYear) {
                        nw.addValue(i, rnd(value));
                        col.setStrValue(formatNumber(value));
                    } else {
                        col.setStrValue("0");
                    }
                }
                r.addCol(col);

            }
            rows.add(r);
        }

    }

    public void getIlliquid() {
        dbobj.start();
        String sql = "select * from ILLIQUID where owner_id='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);
        while (rec != null) {
            String description = (String) (rec.get("DESCRIPTION"));
            double value = getDouble(rec, "VALUE");
            double growth = getDouble(rec, "GROWTH_RATE");
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            Date ald = getDate(rec, "ALD");
            int aYear = MAX_TABLE;

            if (ald != null) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(ald);
                aYear = c1.get(Calendar.YEAR);
                aYear = aYear - this.cashFlow.currentYear;
            }

            if (aYear > finalDeath) {
                aYear = finalDeath;
            }
            for (int i = 0; i < aYear; i++) {
                value += value * growth;
                nw.addValue(i, rnd(value));
                CFCol col = new CFCol();
                col.setStrValue(formatNumber(value));
                col.setTextFill(3);
                r.addCol(col);
            }
            rows.add(r);
            rec = dbobj.next();
        }
        dbobj.stop();
    }

    public void getLiabilities() {
        double[] lBalance = new double[MAX_TABLE];
        for (int j = 0; j < MAX_TABLE; j++) {
            lBalance[j] = 0.0;
        }

        dbobj.start();
        dbobj.clearFields();
        String sql = "select * from REAL_ESTATE where OWNER_ID='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);

        while (rec != null) {

            double pmt = getDouble(rec, "LOAN_PAYMENT");
            double term = getDouble(rec, "LOAN_TERM");
            double balance = getDouble(rec, "LOAN_BALANCE");
            double rate = getDouble(rec, "LOAN_INTEREST");
            double freq = getDouble(rec, "LOAN_FREQ");

            if (balance > 0) {
                LoanCalc lc = new LoanCalc();
                lc.setLoanBalance(balance);
                lc.setLoanInterest(rate / freq);
                lc.setLoanPayment(pmt);
                lc.setLoanFreq(freq);
                lc.setTerm((int) term);
                lc.calculate();

                double[] liability = lc.getLiability();
                for (int i = 0; i < liability.length; i++) {
                    lBalance[i] += liability[i];
                }

            }
            rec = dbobj.next();
        }
        dbobj.stop();

        PropertyBean pb = new PropertyBean();

        ArrayList<PropertyBean> pList = pb.getBeans("OWNER_ID='" + cb.getPrimaryId() + "'");

        for (PropertyBean p : pList) {
            if (p.getLoanBalance() > 0) {
                LoanCalc lc = new LoanCalc();
                lc.setLoanBalance(p.getLoanBalance());
                lc.setLoanInterest(p.getLoanInterest() / p.getLoanFreq());
                lc.setLoanPayment(p.getLoanPayment());
                lc.setLoanFreq(p.getLoanFreq());
                lc.calculate();
                double[] iPay = lc.getPayments();
                double[] iAcc = lc.getInterestAcc();
                for (int i = 0; i < iAcc.length; i++) {
                    lBalance[i] += iPay[i];
                }
            }
        }

        String description = ("Mortgages and other Liabilities");
        boolean nMort = false;
        CFRow r = new CFRow();
        r.setHeader(description);
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontColor(Color.RED);
        r.setFontWeight(Font.PLAIN);

        for (int i = 0; i < finalDeath; i++) {
            if (lBalance[i] > 0) {
                nMort = true;
            }
            nw.addValue(i, -rnd(lBalance[i]));
            CFCol col = new CFCol();
            col.setFontColor(Color.red);
            col.setStrValue(formatNumber((lBalance[i] > 0 ? lBalance[i] : 0)));
            col.setTextFill(3);
            r.addCol(col);
        }
        if (nMort) {
            rows.add(r);
        }
    }

    public void getDebts() {
        dbobj.start();
        String sql = "select * from DEBT where owner_id='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);
        while (rec != null) {
            String description = (String) (rec.get("DESCRIPTION"));
            double presentValue = getDouble(rec, "VALUE");
            double interest = getDouble(rec, "INTEREST_RATE");
            int years = getInt(rec, "LOAN_TERM");
            double pmt = Function.PMT(presentValue, interest, years);
            double value = presentValue;

            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            for (int i = 0; i < finalDeath; i++) {
                double interestPayment = Function.IPMT(interest, i, years,
                        value);
                value = value - (pmt - interestPayment);
                if (value < 0) {
                    value = 0;
                }
                nw.addValue(i, -rnd(value));
                CFCol col = new CFCol();
                col.setStrValue(formatNumber(-value));
                col.setTextFill(3);
                r.addCol(col);
            }
            rows.add(r);
            rec = dbobj.next();
        }
        dbobj.stop();
    }

    public void getRetirementPlans() {
        boolean rFlag = false;
        CFRow r = new CFRow();
        r.setHeader("Retirement Plans");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        for (int i = 0; i < finalDeath; i++) {
            double v = cashFlow.getRetirementBalance(i);
            if (rnd(v) > 0) {
                rFlag = true;
            }
            nw.addValue(i, rnd(v));
            liquidAssets[i] = rnd(v);
            CFCol c = new CFCol();
            if (v < 0) {
                c.setStrValue("" + formatNumber(v) + ")");
                c.setFontColor(Color.red);
            } else {
                c.setStrValue(formatNumber(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void getRealEstate() {
        dbobj.start();
        String sql = "select * from REAL_ESTATE where owner_id='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);
        while (rec != null) {
            String description = (String) (rec.get("DESCRIPTION"));
            double value = getDouble(rec, "VALUE");
            double growth = getDouble(rec, "GROWTH_RATE");
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            for (int i = 0; i < finalDeath; i++) {
                value += value * growth;
                nw.addValue(i, rnd(value));
                CFCol col = new CFCol();
                col.setTextFill(3);
                col.setStrValue(formatNumber(value));
                r.addCol(col);
            }
            rows.add(r);
            rec = dbobj.next();
        }
        dbobj.stop();
    }

    public void getNotes() {
        NotesBean nb = new NotesBean();
        String whereClause = "OWNER_ID='" + cb.getPrimaryId() + "'";
        ArrayList<NotesBean> notes = nb.getBeans(whereClause);
        for (NotesBean n : notes) {
            String description = n.getDescription();
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            if (n.getNoteType().equalsIgnoreCase("I")) {
                // Interest Only with balloon payment at end of term
                for (int i = 0; i < n.getYears(); i++) {
                    nw.addValue(i, rnd(n.getLoanAmount()));
                    CFCol col = new CFCol();
                    col.setTextFill(3);
                    col.setStrValue(formatNumber(n.getLoanAmount()));
                    r.addCol(col);
                }
            } else if (n.getNoteType().equalsIgnoreCase("A")) {
                double payment = Function.PMT(n.getLoanAmount(), n.getInterestRate() / n.getPaymentsPerYear(), n.getYears() * n.getPaymentsPerYear());
                double balance = n.getLoanAmount();
                for (int i = 0; i < n.getYears(); i++) {
                    for (int j = 0; j < n.getPaymentsPerYear(); j++) {
                        double ci = balance * (n.getInterestRate() / n.getPaymentsPerYear());
                        balance = balance - (payment - ci);
                    }
                    nw.addValue(i, rnd(balance));
                    CFCol col = new CFCol();
                    col.setTextFill(3);
                    if (balance < 0) {
                        balance = 0;
                    }
                    col.setStrValue(formatNumber(balance));
                    r.addCol(col);
                }
            } else {
                for (int i = 0; i < n.getYears() - 1; i++) {
                    nw.addValue(i, rnd(n.getLoanAmount()));
                    CFCol col = new CFCol();
                    col.setTextFill(3);
                    col.setStrValue(formatNumber(n.getLoanAmount()));
                    r.addCol(col);
                }
            }
            rows.add(r);
        }
    }

    public void getNotePayable() {
        NotePayableBean nb = new NotePayableBean();
        String whereClause = "OWNER_ID='" + cb.getPrimaryId() + "'";
        ArrayList<NotePayableBean> notes = nb.getBeans(whereClause);
        for (NotePayableBean n : notes) {
            String description = n.getDescription();
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            if (n.getNoteType().equalsIgnoreCase("I")) {
                // Interest Only with balloon payment at end of term
                for (int i = 0; i < n.getYears(); i++) {
                    nw.addValue(i, -rnd(n.getLoanAmount()));
                    CFCol col = new CFCol();
                    col.setTextFill(3);
                    col.setStrValue(formatNumber(n.getLoanAmount()));
                    r.addCol(col);
                }
            } else {
                double payment = Function.PMT(n.getLoanAmount(), n.getInterestRate() / n.getPaymentsPerYear(), n.getYears() * n.getPaymentsPerYear());
                double balance = n.getLoanAmount();
                for (int i = 0; i < n.getYears(); i++) {
                    for (int j = 0; j < n.getPaymentsPerYear(); j++) {
                        double ci = balance * (n.getInterestRate() / n.getPaymentsPerYear());
                        balance = balance - (payment - ci);
                    }
                    nw.addValue(i, -rnd(n.getLoanAmount()));
                    CFCol col = new CFCol();
                    col.setTextFill(3);
                    if (balance < 0) {
                        balance = 0;
                    }
                    col.setStrValue(formatNumber(balance));
                    r.addCol(col);
                }
            }
            rows.add(r);
        }
    }

    public void getBusiness() {
        dbobj.start();
        String sql = "select * from Business where owner_id='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);
        while (rec != null) {
            String description = (String) (rec.get("DESCRIPTION"));
            double value = getDouble(rec, "VALUE");
            double growth = getDouble(rec, "GROWTH_RATE");
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            Date ald = getDate(rec, "ALD");
            int aYear = MAX_TABLE;

            if (ald != null) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(ald);
                aYear = c1.get(Calendar.YEAR);
                aYear = aYear - this.cashFlow.currentYear;
            }

            if (aYear > finalDeath) {
                aYear = finalDeath;
            }

            for (int i = 0; i < aYear; i++) {
                value += value * growth;
                nw.addValue(i, rnd(value));
                CFCol col = new CFCol();
                col.setTextFill(3);
                col.setStrValue(formatNumber(value));
                r.addCol(col);
            }
            rows.add(r);
            rec = dbobj.next();
        }
        dbobj.stop();
    }

    public void getLifeIns() {

        InsuranceBean ib = new InsuranceBean();

        InsuranceBean iList[] = ib.query(InsuranceBean.OWNER_ID, Long.toString(cb.getPrimaryId()));

        if (iList != null) {
            for (int i = 0; i < iList.length; i++) {
                boolean zFlag = false;
                CFRow r = new CFRow();
                r.setHeader("Life Insurance " + iList[i].getOwner());
                r.setIndentLevel(1);
                r.setTextFill(0);
                r.setFontWeight(Font.PLAIN);

                double v = iList[i].getValue();
                double f = iList[i].getFutureCashValue();
                double amt = (f - v) / 10.0;
                if (amt < 0) {
                    amt = 0;
                }
                for (int j = 0; j < finalDeath; j++) {
                    if (v > 0) {
                        zFlag = true;
                    }
                    nw.addValue(j, rnd(v));
                    CFCol col = new CFCol();
                    col.setTextFill(3);
                    col.setStrValue(formatNumber(v));
                    r.addCol(col);
                    v += amt;
                }
                if (zFlag) {
                    rows.add(r);
                }
            }
        }
    }

    public void getPersonalProperty() {
        dbobj.start();
        String sql = "select * from PROPERTY where owner_id='" + cb.getPrimaryId() + "'";
        HashMap rec = dbobj.execute(sql);
        while (rec != null) {
            String description = (String) (rec.get("DESCRIPTION"));
            double value = getDouble(rec, "VALUE");
            double growth = getDouble(rec, "GROWTH_RATE");
            CFRow r = new CFRow();
            r.setHeader(description);
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            for (int i = 0; i < finalDeath; i++) {
                value += value * growth;
                nw.addValue(i, rnd(value));
                CFCol col = new CFCol();
                col.setTextFill(3);
                col.setStrValue(formatNumber(value));
                r.addCol(col);
            }
            rows.add(r);
            rec = dbobj.next();
        }
        dbobj.stop();
    }

    public void getNetWorth() {
        CFRow r = new CFRow();
        r.setHeader("END OF YEAR NET WORTH");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.blue);

        for (int i = 0; i < finalDeath; i++) {
            CFCol c = new CFCol();
            c.setFontColor(Color.blue);
            double v = nw.getTotal(i);
            if (v > 0) {
                c.setStrValue(totalFormat(v));
            } else {
                c.setStrValue(totalFormat(v));
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void genEstateDist() {
        calcExemptions();
        getEstateDistHeader();
        getEstateTaxVCF();
        getLifeEstate();
        getABTrust();
        getTaxableEstate();
        getTaxesAtDeath();
        getGiftsToChildren();
        //getEstateDistLineItems();
        getAbToFamily();
        getFamilyVCF();
        getNetToFamily();
        getInflation();
        getToCharityDistLineItems();
        getNetToCharity();
    }

    public void getEstateDistHeader() {
        CFRow r = new CFRow();
        r.setHeader("IV.\tESTATE DISTRIBUTION");
        r.setIndentLevel(0);
        r.setTextFill(0);
        r.setFontSize(2);
        r.setFontWeight(Font.BOLD);
        r.setFontColor(Color.black);
        rows.add(r);

        CFRow r2 = new CFRow();
        r2.setHeader("Estate Net Worth");
        r2.setIndentLevel(1);
        r2.setTextFill(0);
        r2.setFontWeight(Font.BOLD);
        r2.setFontColor(Color.black);

        for (int i = 0; i < finalDeath; i++) {
            CFCol c = new CFCol();
            c.setFontColor(Color.black);
            c.setFontWeight(Font.BOLD);
            double v = nw.getTotal(i);
            c.setStrValue(totalFormat(v));
            c.setTextFill(3);
            r2.addCol(c);
        }
        rows.add(r2);
    }
    double abEstate[] = new double[MAX_TABLE];

    public void getEstateDistLineItems() {
        ArrayList<VariableCashFlow> al = cashFlow.estateItem;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("E")) {
                CFRow r = new CFRow();
                r.setHeader(vcf.getDescription());
                r.setIndentLevel(1);
                r.setTextFill(0);
                double[] vtable = vcf.getVTable();
                for (int i = 0; i < finalDeath; i++) {
                    double v = vtable[i];
                    taxableEstate[i] += rnd(v);
                    nw.addValue(i, rnd(v));
                    CFCol c = new CFCol();
                    if (v < 0) {
                        c.setStrValue("" + formatNumber(v) + "");
                        c.setFontColor(Color.red);
                    } else {
                        c.setStrValue(formatNumber(v));
                    }
                    c.setTextFill(3);
                    r.addCol(c);
                }
                rows.add(r);
            }
        }
    }

    public void getToCharityDistLineItems() {
        ArrayList<VariableCashFlow> al = cashFlow.vcfItems;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("X")) {
                CFRow r = new CFRow();
                r.setHeader(vcf.getDescription());
                r.setIndentLevel(1);
                r.setTextFill(0);
                r.setFontColor(Color.BLUE);
                double[] vtable = vcf.getVTable();
                for (int i = 0; i < finalDeath; i++) {
                    double v = vtable[i];
                    toCharityLine[i] += v;
                    CFCol c = new CFCol();
                    if (v < 0) {
                        c.setStrValue("" + formatNumber(v) + "");
                    } else {
                        c.setStrValue(formatNumber(v));
                    }
                    c.setFontColor(Color.blue);
                    c.setTextFill(3);
                    r.addCol(c);
                }
                rows.add(r);
            }
        }
    }

    public void getFamilyVCF() {
        ArrayList<VariableCashFlow> al = cashFlow.vcfItems;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("F")) {
                CFRow r = new CFRow();
                r.setHeader(vcf.getDescription());
                r.setIndentLevel(1);
                r.setTextFill(0);
                r.setFontColor(Color.green);
                double[] vtable = vcf.getVTable();
                for (int i = 0; i < finalDeath; i++) {
                    double v = vtable[i];
                    toFamily[i] += v;
                    CFCol c = new CFCol();
                    c.setFontColor(Color.green);
                    if (v < 0) {
                        c.setStrValue("" + formatNumber(v) + "");
                        c.setFontColor(Color.red);
                    } else {
                        c.setStrValue(formatNumber(v));
                    }
                    c.setTextFill(3);
                    r.addCol(c);
                }
                rows.add(r);
            }
        }
    }

    public void getEstateTaxVCF() {
        ArrayList<VariableCashFlow> al = cashFlow.estateTax;
        for (VariableCashFlow vcf : al) {
            if (vcf.getCfType().equals("E")) {
                CFRow r = new CFRow();
                r.setHeader(vcf.getDescription());
                r.setIndentLevel(1);
                r.setTextFill(0);
                double[] vtable = vcf.getVTable();
                for (int i = 0; i < finalDeath; i++) {
                    double v = vtable[i];
                    nw.addValue(i, rnd(v));
                    CFCol c = new CFCol();
                    if (v < 0) {
                        c.setStrValue("" + formatNumber(v) + "");
                        c.setFontColor(Color.red);
                    } else {
                        c.setStrValue(formatNumber(v));
                    }
                    c.setTextFill(3);
                    r.addCol(c);
                }
                rows.add(r);
            }
        }
    }

    public void calcExemptions() {
        int year = 0;

        if (this.cashFlow.startYear <= 0) {
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            year = cal.get(Calendar.YEAR);
        } else {
            year = cashFlow.startYear;
        }
        zCalc zc = new zCalc();
        zc.StartUp();
        for (int i = 0; i < finalDeath; i++) {
            double exempt = zc.zAPPEXCLUSION(year, 0, 0, 0);
            exemptionYear[i] = exempt;
            year++;
        }
        zc.ShutDown();
    }

    public void getABTrust() {
        int year = 0;

        if (this.cashFlow.startYear <= 0) {
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            year = cal.get(Calendar.YEAR);
        } else {
            year = cashFlow.startYear;
        }

        ABTrust ab = new ABTrust();
        ab.query(cb.getPrimaryId());

        if (ab.getId() < 0) {
            return;
        }
        if (ab.getUsed().equals("1") || ab.getUsed().equals("B")) {
            zCalc zc = new zCalc();
            int nTrusts = ab.getBeforeTrusts();

            zc.StartUp();
            for (int i = 0; i < finalDeath; i++) {
                double exempt = zc.zAPPEXCLUSION(year, 0, 0, 0) * nTrusts;
                abEstate[i] = exempt;
                year++;
            }
            zc.ShutDown();

            CFRow r = new CFRow();
            r.setHeader("Exemptions(" + nTrusts + ")");
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            for (int j = 0; j < finalDeath; j++) {
                CFCol col = new CFCol();
                col.setTextFill(3);
                col.setStrValue(formatNumber(-abEstate[j]));
                r.addCol(col);
            }
            rows.add(r);
        }
    }
    double estateLife[] = new double[MAX_TABLE];

    public void getLifeEstate() {
        InsuranceBean ib = new InsuranceBean();

        InsuranceBean iList[] = ib.query(InsuranceBean.OWNER_ID, Long.toString(cb.getPrimaryId()));

        for (int i = 0; i < finalDeath; i++) {
            estateLife[i] = 0;
        }

        if (iList != null) {
            for (InsuranceBean life : iList) {
                double v = life.getValue();
                double f = life.getFutureCashValue();
                double face = life.getFaceValue();
                double amt = (f - v) / 10.0;
                if (amt < 0) {
                    amt = 0;
                }
                for (int k = 0; k < finalDeath; k++) {
                    if (life.getTaxableLifeInc().equals("Y")) {
                        estateLife[k] += face - v;
                    } else {
                        estateLife[k] += face;
                    }
                    v += amt;
                }
            }

            CFRow r = new CFRow();
            r.setHeader("Taxable Life Insurance");
            r.setIndentLevel(1);
            r.setTextFill(0);
            r.setFontWeight(Font.PLAIN);

            for (int j = 0; j < finalDeath; j++) {
                CFCol col = new CFCol();
                col.setTextFill(3);
                col.setStrValue(formatNumber(estateLife[j]));
                r.addCol(col);
            }
            rows.add(r);
        }
    }

    public void getTaxableEstate() {
        CFRow r = new CFRow();
        r.setHeader("Taxable Estate");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.black);

        for (int i = 0; i < finalDeath; i++) {
            CFCol c = new CFCol();
            // We need to add one of the exemptions here as the
            // zCalc software automatically readjusts the
            // exemptions for at least one individual.
            double exemptions = 0;

            if (abEstate[i] > 0) {
                exemptions = exemptionYear[i];
            }

            double v = nw.getTotal(i) + rnd(estateLife[i]) - rnd(abEstate[i]) + rnd(exemptions);
            taxableEstate[i] = v;
            if (v > 0) {
                c.setStrValue(totalFormat(v));
            } else {
                c.setStrValue("");
            }
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }
    double taxes[];

    public void getTaxesAtDeath() {
        taxes = new double[MAX_TABLE];
        CFRow r = new CFRow();
        CFRow fet = new CFRow();
        CFRow sit = new CFRow();
        CFRow fit = new CFRow();

        if (cashFlow.isSingle) {
            r.setHeader("TOTAL TAXES AT DEATH");
        } else {
            r.setHeader("TOTAL TAXES AT 2ND DEATH");
        }

        fet.setHeader("   Federal Estate Tax");
        fet.setIndentLevel(1);
        fet.setTextFill(0);
        fet.setFontWeight(Font.PLAIN);
        fet.setFontColor(Color.red);

        sit.setHeader("   State Income Tax");
        sit.setIndentLevel(1);
        sit.setTextFill(0);
        sit.setFontWeight(Font.PLAIN);
        sit.setFontColor(Color.red);

        fit.setHeader("   Federal Income Tax");
        fit.setIndentLevel(1);
        fit.setTextFill(0);
        fit.setFontWeight(Font.PLAIN);
        fit.setFontColor(Color.red);

        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.red);

        int year;

        if (cashFlow.startYear <= 0) {

            Calendar cal = Calendar.getInstance();
            Date date = new Date();
            cal.setTime(date);
            year = cal.get(Calendar.YEAR);
        } else {
            year = cashFlow.startYear;
        }

        for (int i = 0; i < finalDeath; i++) {
            CalcEstateTax cet = new CalcEstateTax();
            double rp = cashFlow.getRetirementBalance(i);
            cet.setRpBalance(rp);
            cet.setTaxableEstate((nw.getTotal(i) * 1000) + estateLife[i] - abEstate[i]);
            cet.setStateTaxRate(cashFlow.cfb.getStateTaxRate());
            cet.setYear(year + i);
            cet.calculateTax();
            double v = cet.getTotalIncomeTax();
            taxes[i] = v;

            CFCol fc = new CFCol();
            fc.setStrValue(formatNumber(cet.getEstateTax()));
            fc.setFontColor(Color.red);
            fc.setTextFill(3);
            fet.addCol(fc);

            CFCol sc = new CFCol();
            sc.setStrValue(formatNumber(cet.getStateTax()));
            sc.setFontColor(Color.red);
            sc.setTextFill(3);
            sit.addCol(sc);

            CFCol ic = new CFCol();
            ic.setStrValue(formatNumber(cet.getFederalIncomeTax()));
            ic.setFontColor(Color.red);
            ic.setTextFill(3);
            fit.addCol(ic);

            CFCol c = new CFCol();
            c.setStrValue(formatNumber(v));
            c.setFontColor(Color.red);
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(fet);
        rows.add(fit);
        rows.add(sit);
        rows.add(r);
    }

    public void getGiftsToChildren() {
        CFRow r = new CFRow();
        r.setHeader("Gifts to Children (accum@5.0%)");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);

        double baseGift = cashFlow.getTaxableGift(0);
        double gift = baseGift;
        boolean rFlag = false;
        for (int i = 0; i < finalDeath; i++) {
            if (gift != 0) {
                rFlag = true;
            }
            CFCol c = new CFCol();
            c.setStrValue(formatNumber(gift));
            c.setTextFill(3);
            r.addCol(c);
            taxableEstate[i] += rnd(gift);
            gift = baseGift + gift * 1.06;
        }
        if (rFlag) {
            rows.add(r);
        }
    }

    public void getAbToFamily() {
        CFRow r = new CFRow();
        r.setHeader("Exemption to Family");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.green);

        for (int i = 0; i < finalDeath; i++) {
            CFCol c = new CFCol();
            c.setStrValue(formatNumber(abEstate[i]));
            taxableEstate[i] += rnd(abEstate[i]);
            c.setFontColor(Color.green);
            c.setTextFill(3);
            r.addCol(c);
        }
        // rows.add(r);
    }

    public void getNetToFamily() {
        CFRow r = new CFRow();
        r.setHeader("NET TO FAMILY");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.BOLD);
        r.setFontColor(Color.green);

        double baseGift = 0;// cashFlow.getGift(0);
        double gift = baseGift;
        for (int i = 0; i < finalDeath; i++) {
            double v;
            v = (taxableEstate[i] + rnd(gift)) - rnd(taxes[i]);
            toFamily[i] += v * 1000;

            CFCol c = new CFCol();
            c.setStrValue(totalFormat(rnd(toFamily[i])));
            c.setFontColor(Color.green);
            c.setTextFill(3);
            r.addCol(c);
            gift = baseGift + gift * 1.06;
        }
        rows.add(r);
    }

    public void getLLC2() {
        CFRow r = new CFRow();
        r.setHeader("LLC2 Note to Charity");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.BOLD);
        r.setFontColor(Color.blue);

        double baseGift = 2100000;
        double gift = baseGift;
        for (int i = 0; i < finalDeath; i++) {
            toCharityLine[i] += gift;
            CFCol c = new CFCol();
            c.setStrValue(formatNumber(gift));
            c.setFontColor(Color.blue);
            c.setTextFill(3);
            r.addCol(c);
            gift = gift + baseGift;
        }
        rows.add(r);

    }

    public void getNetToCharity() {
        CFRow r = new CFRow();
        r.setHeader("TOTAL TO CHARITY");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.BOLD);
        r.setFontColor(Color.blue);

        double gift = 0;
        double cGift[] = new double[MAX_TABLE];
        // Do this in two steps, since we want to show a 6% growth with the
        // above value, we need to calculate it here.
        for (int i = 0; i < finalDeath; i++) {
            cGift[i] += cashFlow.getTotalCharity(i) + gift;
            gift = cGift[i] * 1.06;
        }

        for (int i = 0; i < finalDeath; i++) {
            CFCol c = new CFCol();
            double fV = cGift[i] + toCharityLine[i];
            toCharity[i] = fV;
            c.setStrValue(formatNumber(fV));
            c.setFontColor(Color.blue);
            c.setTextFill(3);
            r.addCol(c);
        }

        rows.add(r);
    }

    public void getInflation() {
        // inflation = amount * Math.pow((1.0 + inflationRate), -years);
        CFRow r = new CFRow();
        r.setHeader("What's this really worth? - Inflation adjusted (3%) \nNet to Family");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.green);

        double baseGift = cashFlow.getTaxableGift(0);
        double gift = baseGift;

        for (int i = 0; i < finalDeath; i++) {
            double v = (taxableEstate[i] + rnd(gift)) - rnd(taxes[i]);
            double inflation = 0;
            if (i == 0) {
                inflation = v;
            } else {
                inflation = v * Math.pow((1 + cashFlow.cfb.getInflation()), -i);
            }
            CFCol c = new CFCol();
            c.setFontColor(Color.green);
            c.setStrValue(totalFormat(inflation));
            c.setTextFill(3);
            r.addCol(c);
            gift = baseGift + gift * 1.06;
        }
        rows.add(r);
    }

    public void genEstateCashNeeds() {
        getCashNeedsHeader();
        getCashNeededAtDeath();
        cashAvailable();
    }

    public void getCashNeedsHeader() {
        CFRow r = new CFRow();
        r.setHeader("V.\tEstate CASH NEEDS");
        r.setIndentLevel(0);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.red);
    }

    public void getCashNeededAtDeath() {
        CFRow r = new CFRow();
        r.setHeader("Cash Needed At Death");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.red);

        for (int i = 0; i < finalDeath; i++) {
            CFCol c = new CFCol();
            double v = taxes[i];
            c.setStrValue(formatNumber(v));
            c.setFontColor(Color.red);
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void cashAvailable() {
        CFRow r = new CFRow();
        r.setHeader("Total Liquid Assets Available");
        r.setIndentLevel(1);
        r.setTextFill(0);
        r.setFontWeight(Font.PLAIN);
        r.setFontColor(Color.red);

        for (int i = 0; i < finalDeath; i++) {
            CFCol c = new CFCol();
            double v = liquidAssets[i];
            c.setStrValue(totalFormat(v));
            c.setFontColor(Color.red);
            c.setTextFill(3);
            r.addCol(c);
        }
        rows.add(r);
    }

    public void reset() {
        itr = rows.iterator();
    }

    public CFRow getRow() {
        CFRow row = null;
        if (itr.hasNext()) {
            row = (CFRow) itr.next();
        }
        return row;
    }

    public double rnd(double v) {
        return (Math.rint(v / 1000.0));
    }

    public String formatNumber(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(rnd(value));
    }

    public String totalFormat(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    public int getInt(HashMap r, String field) {
        Object obj = r.get(field);
        if (obj != null) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    public double getDouble(HashMap r, String field) {
        Object obj = r.get(field);
        if (obj != null) {
            return ((Number) obj).doubleValue();
        }
        return 0.0;
    }

    public float getFloat(HashMap r, String field) {
        Object obj = r.get(field);
        if (obj != null) {
            return ((Number) obj).floatValue();
        }
        return 0.0f;
    }

    public Date getDate(HashMap rec, String field) {
        Date rDate = (Date) rec.get(field);
        if (rDate != null) {
            try {
                return rDate;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * @return Returns the cashFlow.
     */
    public CashFlow getCashFlow() {
        return cashFlow;
    }

    /**
     * @param cashFlow
     *            The cashFlow to set.
     */
    public void setCashFlow(CashFlow cashFlow) {
        this.cashFlow = cashFlow;
    }

    /**
     * @return Returns the cb.
     */
    public ClientBean getCb() {
        return cb;
    }

    /**
     * @param cb
     *            The cb to set.
     */
    public void setCb(ClientBean cb) {
        this.cb = cb;
    }

    /**
     * @return Returns the finalDeath.
     */
    public int getFinalDeath() {
        return finalDeath;
    }

    /**
     * @param finalDeath
     *            The finalDeath to set.
     */
    public void setFinalDeath(int finalDeath) {
        this.finalDeath = finalDeath;
    }

    public void cleanUp() {
        Iterator rItr = rows.iterator();
        while (rItr.hasNext()) {
            CFRow r = (CFRow) rItr.next();
            r.cleanUp();
        }
        rows.clear();
    }
}
