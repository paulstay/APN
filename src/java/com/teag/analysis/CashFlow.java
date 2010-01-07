package com.teag.analysis;

/**
 * @author Paul Stay
 *	Description - Create Cash Flow analysis from database for Estate Planning Group,
 *  Creates a 30 year cash flow analysis.
 *  After initialization, will be able to query data for 30 years.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.teag.bean.*;
import com.teag.client.Marriages;
import com.teag.util.Function;
import com.teag.util.TeagDefines;
import com.teag.util.Utilities;
import com.zcalc.zCalc;

public class CashFlow extends AnalysisSqlBean {

    private static final long serialVersionUID = -4646463706823749750L;
    ClientBean cb = null;
    CashFlowBean cfb = null;
    RetirementCashFlow rcf = null;
    PersonBean person = new PersonBean();
    PersonBean spouse = new PersonBean();
    int retirementAge;
    int currentAge;
    int sAge;
    int currentYear;
    public int startYear = 0;
    boolean isSingle = false;
    // used for cashflow variables for both cash receipts, and cash
    // disbursements
    ArrayList<VariableCashFlow> cashItem = new ArrayList<VariableCashFlow>();
    ArrayList<VariableCashFlow> dispItem = new ArrayList<VariableCashFlow>();
    ArrayList<VariableCashFlow> netItem = new ArrayList<VariableCashFlow>();
    ArrayList<VariableCashFlow> estateItem = new ArrayList<VariableCashFlow>();
    ArrayList<VariableCashFlow> estateTax = new ArrayList<VariableCashFlow>();
    ArrayList<VariableCashFlow> vcfItems = new ArrayList<VariableCashFlow>();
    ArrayList<VariableCashFlow> toCharity = new ArrayList<VariableCashFlow>();
    // Store hashmaps for the client data for each cash flow item
    HashMap<String, Object> cashMap = new HashMap<String, Object>();
    // Store data for dispursemenst
    HashMap<String, Object> dispMap = new HashMap<String, Object>();
    // Store Securities data
    HashMap<String, Object> sMap = new HashMap<String, Object>();
    // Store Bond data
    HashMap<String, Object> bMap = new HashMap<String, Object>();
    // Store Retirement Data
    HashMap<String, Object> rMap = new HashMap<String, Object>();
    public final static int MAX_TABLE = 120;
    // use this to calculate the interest per year so we can deduct from cash
    // flow income as well as other cash deductions
    double[] taxDeduction = new double[MAX_TABLE];
    // Charities
    double[] charitableDeduction = new double[MAX_TABLE];
    double[] charitableGifts = new double[MAX_TABLE];
    double[] charitableCont = new double[MAX_TABLE];
    // Depreciation Values
    double[] depreciation = new double[MAX_TABLE];

    /**
     * CashFlow constructor
     */
    public CashFlow() {
    }

    /**
     * This is where we start the whole process.
     */
    public void buildTables() {
        // Initialize the variables, tables
        initialization();
        // Call each group of assets and start building
        // the cash flow tables
        buildAssets();
        // Call this routine to fix the tax, charitable Deductions. etc.
        buildTable();
    }

    public void buildAssets() {
        cashFlowVariables();
        socialSecurity();
        cashInterest();
        municipal();
        securities();
        bizContracts();
        illiquid();
        cashRetirement();
        notes();
        realEstate();
        dispFlowVariables();
        taxableGifts();
        mortgage();
        business();
        liabilities();
        debt();
        notePayable();
        charity();
    }

    public void buildTable() {
        tax();	// We do this only once, only for the first year.
        securityBalance();
        excessCash();
        securityGrowth();
        mBonds();
        netFlowVariables();
        estateTaxVariables();
        estateFlowVariables();
        fixTable();
    }

    public void initialization() {
        if (startYear <= 0) {
            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            currentYear = cal.get(Calendar.YEAR);
        } else {
            currentYear = startYear;
        }

        retirementAge = TeagDefines.RETIREMENT_AGE;
        person.setId(cb.getPrimaryId());

        person.setDbObject();
        person.initialize();

        Marriages m = new Marriages();
        m.setPrimaryId(person.getId());
        m.initialize();
        MarriageBean mb = m.getCurrentMarriage();

        // If the marriage bean is null than this is probably a single person.
        if (mb != null) {
            spouse.setId(mb.getSpouseId());
            spouse.initialize();
        } else {
            isSingle = true;
        }

        currentAge = Utilities.CalcAge(person.getBirthDate());
        if (!isSingle) {
            sAge = Utilities.CalcAge(spouse.getBirthDate());
        } else {
            sAge = 0;
        }

        // Initialize the tax Balance for tax deductions
        for (int j = 0; j < MAX_TABLE; j++) {
            taxDeduction[j] = 0.0;
        }

        // Load all Variable Cash Flow line items
        initVFC();
    }

    public void setClient(ClientBean cb) {
        this.cb = cb;
        cfb = new CashFlowBean();
        //cfb.setDbObject(dbobj);
        cfb.setOwnerId(cb.getPrimaryId());
        cfb.initialize();
    }

    public void initVFC() {
        // need to get the new line items from the database.
        VCFBean vcfBean = new VCFBean();
        vcfBean.setDbObject();
        VCFBean vcf[] = vcfBean.query(VCFBean.OWNER_ID, "" + cfb.getOwnerId());
        if (vcf != null) {
            for (int i = 0; i < vcf.length; i++) {
                if (vcf[i].getUseFlag().equalsIgnoreCase("1") || vcf[i].getUseFlag().equalsIgnoreCase("B")) {
                    VariableCashFlow cf = new VariableCashFlow();
                    cf.setValues(vcf[i]);
                    vcfItems.add(cf);
                }
            }
        }
    }

    /*
     * This is the section where we will place all of the methods for building each of the assets!
     */
    /*
     * CashInterest - Calculate the interest earned from a cash asset.
     * This method no longer calles the db database directly!
     */
    public void cashInterest() {
        double iTable[] = new double[MAX_TABLE];
        CashBean cBean = new CashBean();
        ArrayList<CashBean> cList = cBean.getBeans(CashBean.OWNER_ID + "='" + cb.getPrimaryId() + "'");
        for (CashBean c : cList) {
            double value = c.getAssetValue();
            double growth = c.getAssetGrowth();
            double interest = c.getAssetIncome();
            for (int i = 0; i < MAX_TABLE; i++) {
                iTable[i] += value * interest;
                value += value * growth;
            }
        }
        cashMap.put("Interest on Cash", iTable);
    }

    /**
     * Get the Biz contract and assoicated values
     */
    public void bizContracts() {
        double[] rTable = new double[MAX_TABLE];
        BizContractBean bean = new BizContractBean();
        ArrayList<BizContractBean> bList = bean.getBeans(BizContractBean.OWNER_ID + "='" + cb.getPrimaryId() + "'");

        for (BizContractBean b : bList) {
            double salary = b.getSalary();
            int sYear = b.getStartYear();
            int eYear = b.getEndYear();

            int idx1 = sYear - currentYear;
            int idx2 = eYear - currentYear;

            if (idx1 < 0) {
                idx1 = 0;
            }

            if (idx2 < 0) {
                idx2 = 0;
            }

            for (int i = 0; i < MAX_TABLE; i++) {
                if (i >= idx1 && i <= idx2) {
                    rTable[i] += salary;
                } else {
                    rTable[i] += 0;
                }
            }

            // if this is true, than at the end of the contract we
            // add the value to the cash flow receipts!
            if (b.isCashFlow()) {
                rTable[idx2] += b.getValue();
            }
        }
        cashMap.put("Contract Salary", rTable);
    }

    /**
     * Get the illiquid assets, if we have any growth, than save that
     *
     */
    public void illiquid() {
        double[] rTable = new double[MAX_TABLE];
        double[] sellTable = new double[MAX_TABLE];	// used for sale of business
        IlliquidBean iBean = new IlliquidBean();
        ArrayList<IlliquidBean> iList = iBean.getBeans(IlliquidBean.OWNER_ID + "='" + cb.getPrimaryId() + "'");

//		for(IlliquidBean iRec : iList){
//			double value = iRec.getAssetValue();
//			double interest = iRec.getAssetIncome();
//			double growth = iRec.getAssetGrowth();
//			for(int i=0; i < MAX_TABLE; i++){
//				iTable[i] += value*interest;
//				value += value*growth;
//			}
//		}

        for (IlliquidBean iRec : iList) {
            double rValue = iRec.getAssetValue();
            double income = iRec.getAssetIncome();
            double rGrowth = iRec.getAssetGrowth();

            String ald = iRec.getAld();
            int aYear = MAX_TABLE;

            if (ald != null) {
                SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
                try {
                    Date ld = df.parse(ald);
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(ld);
                    aYear = c1.get(Calendar.YEAR);
                    aYear = aYear - currentYear;
                    double sellValue = iRec.getValue();
                    for (int i = 0; i <= aYear; i++) {
                        sellValue += sellValue * (iRec.getAssetGrowth());
                        //sellValue = sellValue + sellValue*(1.0 * b.getAssetGrowth());
                    }
                    sellTable[aYear] += sellValue;
                } catch (Exception e) {
                    aYear = MAX_TABLE;
                }
            }

            for (int i = 0; i < aYear; i++) {
                rTable[i] += rValue * income;
                rValue += rValue * rGrowth;
            }
        }

        cashMap.put("Illiquid", rTable);
        cashMap.put("Sale of Illiquid Assets", sellTable);
    }

    /**
     * Build the securities
     */
    public void securities() {
        String qString = SecuritiesBean.OWNER_ID + "='" + cb.getPrimaryId() + "'";
        SecuritiesBean sBean = new SecuritiesBean();
        ArrayList<SecuritiesBean> sList = sBean.getBeans(qString);
        double[] iTable = new double[MAX_TABLE];

        for (SecuritiesBean sRec : sList) {
            double interest = sRec.getValue() * sRec.getAssetIncome();
            for (int i = 0; i < MAX_TABLE; i++) {
                iTable[i] += interest;
            }
        }
        cashMap.put("Securities", iTable);
    }

    /*
     * Mucipal Bonds, uses Bean info to get data!
     */
    public void municipal() {
        double[] iTable = new double[MAX_TABLE];
        BondBean bean = new BondBean();
        ArrayList<BondBean> bList = bean.getBeans(BondBean.OWNER_ID + "='" + cb.getPrimaryId() + "'");
        for (BondBean b : bList) {
            double value = b.getAssetValue();
            double growth = b.getAssetGrowth();
            double interest = b.getAssetIncome();
            for (int i = 0; i < MAX_TABLE; i++) {
                iTable[i] += value * interest;
                bTable[i] += value * interest;
                value += value * growth;
            }
        }
        cashMap.put("Municipal Bonds", iTable);
    }

    /**
     * Get the cash flow from the retirement plans
     */
    public void cashRetirement() {
        rcf = new RetirementCashFlow();

        double distTable[];
        double rTable[];

        rcf.setClientAge(currentAge);
        rcf.setSpouseAge(sAge);
        rcf.setSingle(isSingle);

        String qString = RetirementBean.OWNER_ID + "='" + cb.getPrimaryId() + "'";

        RetirementBean rBean = new RetirementBean();
        ArrayList<RetirementBean> rList = rBean.getBeans(qString);

        for (RetirementBean r : rList) {
            double value = r.getAssetValue();
            double growth = r.getAssetGrowth();
            rcf.addPlan(value, growth);
        }

        distTable = rcf.getDispersements();
        rTable = rcf.getTotalValue();
        cashMap.put("cRetirement", distTable);
        rMap.put("Balance", rTable);
    }

    /**
     * Get Income from any Real Estate
     * Also, calculate the depreciation
     */
    public void realEstate() {
        RealEstateBean rb = new RealEstateBean();
        ArrayList<RealEstateBean> rList;

        double[] rTable = new double[MAX_TABLE];

        rList = rb.getBeans("OWNER_ID='" + Long.toString(cb.getPrimaryId()) + "'");
        for (RealEstateBean realEstate : rList) {
            double rents = realEstate.getGrossRents();
            double expenses = realEstate.getOperatingExpenses();
            double rGrowth = realEstate.getGrossRentsGrowth();
            double eGrowth = realEstate.getGrowthExpenses();
            double income = rents - expenses;

            // Calcualte the income for this asset
            for (int i = 0; i < MAX_TABLE; i++) {
                rTable[i] += income;
                rents += rents * rGrowth;
                expenses += expenses * eGrowth;
                income = rents - expenses;
            }

            if (realEstate.getDepreciationValue() > 0 && realEstate.getDepreciationYears() > 0) {
                double dep = realEstate.getDepreciationValue();
                double sv = realEstate.getSalvageValue();
                int years = (int) realEstate.getDepreciationYears();
                double dValue = (dep - sv) / years;

                for (int i = 0; i < years; i++) {
                    depreciation[i] += dValue;
                }
            }
        }

        // We need to add the global depreciation here, and later move this out to its own method.
        for (int i = 0; i < MAX_TABLE; i++) {
            depreciation[i] += cfb.getDepreciation();
        }
        cashMap.put("Real Estate Income", rTable);
    }

    /**
     * Liabilities is really getting the information form the following assets:
     * 	Real Estate
     */
    public void liabilities() {

        String qString = RealEstateBean.OWNER_ID + "='" + cb.getPrimaryId() + "'";
        RealEstateBean rBean = new RealEstateBean();
        ArrayList<RealEstateBean> rList = rBean.getBeans(qString);

        for (RealEstateBean r : rList) {
            double pmt = r.getLoanPayment();
            double term = r.getLoanTerm();
            double balance = r.getLoanBalance();
            double rate = r.getLoanInterest();
            double freq = r.getLoanFreq();

            if (balance > 0) {
                LoanCalc lc = new LoanCalc();
                lc.setLoanBalance(balance);
                lc.setLoanInterest(rate / freq);
                lc.setLoanPayment(pmt);
                lc.setLoanFreq(freq);
                lc.setTerm((int) term);
                lc.calculate();

                double[] interestPayments = lc.getInterestAcc();

                for (int i = 0; i < interestPayments.length; i++) {
                    taxDeduction[i] += interestPayments[i];
                }
            }
        }
    }

    /**
     * This is the notes receivable
     */
    public void notes() {
        double[] nTable = new double[MAX_TABLE];
        NotesBean nb = new NotesBean();
        String whereClause = "OWNER_ID='" + cb.getPrimaryId() + "'";
        ArrayList<NotesBean> notes = nb.getBeans(whereClause);
        for (NotesBean n : notes) {
            if (n.getNoteType().equalsIgnoreCase("I")) {
                // Interest Only with balloon payment at end of term
                for (int i = 0; i < n.getYears(); i++) {
                    nTable[i] += n.getLoanAmount() * n.getInterestRate();
                }
                nTable[n.getYears() - 1] += n.getLoanAmount();
            } else if (n.getNoteType().equalsIgnoreCase("A")) {
                double payment = Function.PMT(n.getLoanAmount(), n.getInterestRate() / n.getPaymentsPerYear(),
                        n.getYears() * n.getPaymentsPerYear()) * n.getPaymentsPerYear();
                for (int i = 0; i < n.getYears(); i++) {
                    nTable[i] += payment;
                }
            } else {
                nTable[n.getYears() - 1] = n.getLoanAmount();
            }
        }
        cashMap.put("Notes", nTable);
    }

    /**
     * Calculate the business income, and if there is a sale,
     * add in the sale to the cash flow.
     */
    public void business() {
        BusinessBean bean = new BusinessBean();
        ArrayList<BusinessBean> bList = bean.getBeans(BusinessBean.OWNER_ID + "='" + cb.getPrimaryId() + "'");
        double[] rTable = new double[MAX_TABLE];
        double[] sTable = new double[MAX_TABLE];	// used for sale of business

        for (BusinessBean b : bList) {

            double rValue = b.getAnnualIncome();
            double rGrowth = b.getIncomeGrowth();

            String ald = b.getAld();
            int aYear = MAX_TABLE;

            if (ald != null) {
                SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
                try {
                    Date ld = df.parse(ald);
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(ld);
                    aYear = c1.get(Calendar.YEAR);
                    aYear = aYear - currentYear;
                    double sellValue = b.getValue();
                    for (int i = 0; i <= aYear; i++) {
                        sellValue += sellValue * (b.getAssetGrowth());
                        //sellValue = sellValue + sellValue*(1.0 * b.getAssetGrowth());
                    }
                    sTable[aYear] += sellValue;
                } catch (Exception e) {
                    aYear = MAX_TABLE;
                }
            }

            for (int i = 0; i < aYear; i++) {
                rTable[i] += rValue;
                rValue += rValue * rGrowth;
            }
        }
        cashMap.put("Business Income", rTable);
        cashMap.put("Sale of Business", sTable);
    }

    /**
     * Get the notes payable, this is for disbursements
     */
    public void notePayable() {
        double[] nTable = new double[MAX_TABLE];
        NotePayableBean nb = new NotePayableBean();
        String whereClause = "OWNER_ID='" + cb.getPrimaryId() + "'";
        ArrayList<NotePayableBean> notes = nb.getBeans(whereClause);
        for (NotePayableBean n : notes) {
            if (n.getNoteType().equalsIgnoreCase("I")) {
                // Interest Only with balloon payment at end of term
                for (int i = 0; i < n.getYears(); i++) {
                    nTable[i] += n.getLoanAmount() * n.getInterestRate();
                }
                nTable[n.getYears() - 1] += n.getLoanAmount();
            } else if (n.getNoteType().equalsIgnoreCase("A")) {
                double payment = Function.PMT(n.getLoanAmount(), n.getInterestRate() / n.getPaymentsPerYear(),
                        n.getYears() * n.getPaymentsPerYear()) * n.getPaymentsPerYear();
                for (int i = 0; i < n.getYears(); i++) {
                    nTable[i] += payment;
                }
            } else {
                nTable[n.getYears() - 1] = n.getLoanAmount();
            }
        }
        dispMap.put("Notes Payable", nTable);
    }

    /**
     * Get the mortgage data, for Real Estate and Personal Properties
     */
    public void mortgage() {

        String qString = RealEstateBean.OWNER_ID + "='" + cb.getPrimaryId() + "'";
        RealEstateBean rBean = new RealEstateBean();
        ArrayList<RealEstateBean> rList = rBean.getBeans(qString);
        double[] mort = new double[MAX_TABLE];

        for (RealEstateBean r : rList) {
            double freq = r.getLoanFreq();
            double payment = r.getLoanPayment() * freq;
            double term = r.getLoanTerm();
            int years = (int) Math.floor(term / 12.0) + 1;
            for (int i = 0; i < years; i++) {
                mort[i] += payment;
            }
        }

        dispMap.put("Real Estate Mortgage Principle Payments", mort);

        double[] personalMortgage = new double[MAX_TABLE];

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
                    if (p.getIntDed().equalsIgnoreCase("Y")) {
                        taxDeduction[i] += iAcc[i];
                    }
                    personalMortgage[i] += iPay[i];
                }
            }
        }

        dispMap.put("Personal Mortgage", personalMortgage);
    }

    /**
     * Get the Debt Payments
     */
    public void debt() {
        String qString = DebtBean.OWNER_ID + "='" + cb.getPrimaryId() + "'";
        double debtValues[] = new double[120];
        DebtBean bean = new DebtBean();
        ArrayList<DebtBean> dList = bean.getBeans(qString);
        for (DebtBean d : dList) {
            double presentValue = d.getAssetValue();
            double interest = d.getAssetIncome();
            int years = (int) d.getLoanTerm();
            double pmt = Function.PMT(presentValue, interest, years);
            for (int i = 0; i < years; i++) {
                debtValues[i] += pmt;
            }
        }
        dispMap.put("Debt Payments", debtValues);
    }

    public void cashFlowVariables() {
        for (VariableCashFlow v : vcfItems) {
            if (v.getCfType().equals("C")) {
                cashItem.add(v);
            }
        }
    }

    public void dispFlowVariables() {
        for (VariableCashFlow v : vcfItems) {
            if (v.getCfType().equals("D")) {
                dispItem.add(v);
            }
        }
    }

    public void netFlowVariables() {
        for (VariableCashFlow v : vcfItems) {
            if (v.getCfType().equals("N")) {
                netItem.add(v);
            }
        }
    }

    public void estateFlowVariables() {
        for (VariableCashFlow v : vcfItems) {
            if (v.getCfType().equals("E")) {
                estateItem.add(v);
            }
        }
    }

    public void estateTaxVariables() {
        for (VariableCashFlow v : vcfItems) {
            if (v.getCfType().equals("T")) {
                estateTax.add(v);
            }
        }
    }

    public void charityFlowVariables() {
        for (VariableCashFlow v : vcfItems) {
            if (v.getCfType().equals("X")) {
                toCharity.add(v);
            }
        }
    }

    /**
     * ToDo - Need to get the salary data from database for now we will use the
     * defines.
     */
    public void consulting() {
    }

    public double getContractSalary(int year) {
        double[] cTable = (double[]) cashMap.get("Contract Salary");
        return cTable[year];
    }

    public double getConsulting(int year) {
        double[] cTable = (double[]) cashMap.get("Consulting Income");
        return cTable[year];
    }
    double[] socialTable = new double[MAX_TABLE];

    public void socialSecurity() {
        double socialSecurity = cfb.getSocialSecurity();
        double sInc = cfb.getSocialSecurityGrowth();
        int age = currentAge;
        int spouseAge;
        int spouseBeginSocial;
        int primaryBeginSocial = TeagDefines.SOCIAL_START_PRIMARY;
        if (isSingle) {
            spouseAge = -1;
            spouseBeginSocial = 150;
        } else {
            spouseAge = sAge;
            spouseBeginSocial = TeagDefines.SOCIAL_START_SPOUSE;
        }

        for (int i = 0; i < MAX_TABLE; i++) {
            double totalSS = 0;
            if (age++ >= primaryBeginSocial) {
                totalSS += 12 * socialSecurity;
            }
            if (spouseAge++ >= spouseBeginSocial) {
                totalSS += (12 * socialSecurity) * .50;
            }
            if (age > primaryBeginSocial || spouseAge > spouseBeginSocial) {
                socialSecurity += socialSecurity * sInc;
            }
            socialTable[i] = totalSS;
        }
        cashMap.put("Social Security", socialTable);
    }

    public double getSocial(int year) {
        double[] cTable = (double[]) cashMap.get("Social Security");
        return cTable[year];
    }

    public double getInterestOnCash(int year) {
        double[] iTable = (double[]) cashMap.get("Interest on Cash");
        return iTable[year];
    }
    double bTable[] = new double[MAX_TABLE];

    public double getMunicipalBonds(int year) {
        double[] iTable = (double[]) cashMap.get("Municipal Bonds");
        return iTable[year];
    }

    public double getIlliquid(int year) {
        double[] iTable = (double[]) cashMap.get("Illiquid");
        return iTable[year];
    }

    public double getIlliquidSale(int year) {
        double[] itable = (double[]) cashMap.get("Sale of Illiquid Assets");
        return itable[year];
    }

    public double getSecurities(int year) {
        double[] iTable = (double[]) cashMap.get("Securities");
        return (iTable[year]);
    }

    public double getRealEstate(int year) {
        double iTable[] = (double[]) cashMap.get("Real Estate Income");
        return (iTable[year]);
    }

    public double getNotesPayable(int year) {
        double[] iTable = (double[]) dispMap.get("Notes Payable");
        return iTable[year];
    }

    public double getNotes(int year) {
        double[] iTable = (double[]) cashMap.get("Notes");
        return iTable[year];
    }

    public double getBusiness(int year) {
        double[] iTable = (double[]) cashMap.get("Business Income");
        return (iTable[year]);
    }

    public double getSaleBusiness(int year) {
        double[] iTable = (double[]) cashMap.get("Sale of Business");
        return (iTable[year]);
    }

    public double getLivingExpenses(int year) {
        double iTable[] = (double[]) dispMap.get("Living Expenses");
        return iTable[year];
    }

    public void taxableGifts() {
        double[] iTable = new double[MAX_TABLE];
        double[] giftTax = new double[MAX_TABLE];
        SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
        double exemptionUsed = 0;
        GiftBean gb = new GiftBean();

        // first get life expectancy
        //double life1 = Utilities.getLifeExp(currentAge, "M");
        //double life2 = Utilities.getLifeExp(sAge, "F");

        /*
        for(int i = 0; i < life1; i++)
        iTable[i] = 48000.0;
        for(int i = 0; i < life2; i++)
        iTable[i] += 48000.0;
         */
        GiftBean giftBean[] = gb.query(GiftBean.OWNER_ID, Long.toString(cb.getPrimaryId()));

        if (giftBean != null) {
            // Get previous gifts
            for (int i = 0; i < giftBean.length; i++) {
                if (giftBean[i].getGiftPlanning().equals("B") || giftBean[i].getGiftPlanning().equals("1")) {
                    Date date;
                    try {
                        date = df.parse(giftBean[i].getDate());
                    } catch (Exception e) {
                        continue;
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    if (cal.get(Calendar.YEAR) < currentYear) {
                        exemptionUsed += giftBean[i].getAmount();
                    }
                }
            }

            for (int i = 0; i < giftBean.length; i++) {
                if (giftBean[i].getGiftPlanning().equals("B") || giftBean[i].getGiftPlanning().equals("1")) {

                    double gift = giftBean[i].getAmount();
                    int freq = 1;
                    if (giftBean[i].getRegularity().equals("B")) {
                        freq = 2;
                    }
                    if (giftBean[i].getRegularity().equals("T")) {
                        freq = 3;
                    }
                    if (giftBean[i].getRegularity().equals("O")) {
                        freq = 0;
                    }
                    Date date;
                    try {
                        date = df.parse(giftBean[i].getDate());
                    } catch (Exception e) {
                        date = new Date();
                    }

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);

                    int idx = year - currentYear;
                    if (idx < 0) {
                        continue;
                    }

                    if (freq == 0 && idx >= 0) {
                        iTable[idx] += gift;
                        zCalc zc = new zCalc();
                        zc.StartUp();
                        double gTax = zc.zFGT(gift, exemptionUsed, 0, 0,
                                currentYear + idx, 0, 0);
                        zc.ShutDown();
                        giftTax[idx] += gTax;
                        continue;
                    }

                    for (int j = 0; j < MAX_TABLE; j += freq) {
                        iTable[j] += gift;
                        exemptionUsed += gift;
                        zCalc zc = new zCalc();
                        zc.StartUp();
                        double gTax = zc.zFGT(gift, exemptionUsed, 0, 0,
                                currentYear + j, 0, 0);
                        zc.ShutDown();
                        giftTax[j] += gTax;
                    }
                }
            }
        }

        dispMap.put("Gifts", iTable);
        //dispMap.put("gift tax", giftTax);
    }

    public double getTaxableGift(int year) {
        double iTable[] = (double[]) dispMap.get("Gifts");
        return iTable[year];
    }

    public double getGiftTax(int year) {
        double iTable[] = (double[]) dispMap.get("gift tax");
        return iTable[year];
    }

    public void charity() {
        double iTable[] = new double[MAX_TABLE];
        double ch = cfb.getCharitableGifts();
        double chg = TeagDefines.CHARITABLE_CASH_GROWTH;

        for (int i = 0; i < MAX_TABLE; i++) {
            iTable[i] += ch;
            ch += ch * chg;
        }

        for (int i = 0; i < MAX_TABLE; i++) {
            charitableGifts[i] += iTable[i];
            charitableCont[i] += iTable[i];
        }
    }

    public double getCharity(int year) {
        //double[] iTable = (double[]) dispMap.get("Charitable Contributions");
        //return iTable[year] + getVcfCharity(year);
        return charitableCont[year];
    }

    public double getTotalCharity(int year) {
        return charitableCont[year] + getVcfCharity(year);
    }

    public double getMortgage(int year) {
        double[] iTable = (double[]) dispMap.get("Real Estate Mortgage Principle Payments");
        return iTable[year];
    }

    public double getPersonalMortgage(int year) {
        double[] iTable = (double[]) dispMap.get("Personal Mortgage");
        return iTable[year];
    }

    public void tax2(int year) {
        double[] iTable = (double[]) dispMap.get("Income Tax");

        if (cfb.isUseTax()) {
            CalcCashFlowTax ccf = new CalcCashFlowTax();
            double lessTax = taxDeduction[year] + getVcfTax(year);
            ccf.totalCashInflows = getCRTotal(year) - lessTax;
            ccf.stateIncomeTaxRate = cfb.getStateTaxRate();
            ccf.stateAGI = getCRTotal(year) - lessTax - socialTable[year] - bTable[year] - depreciation[year];
            double maxAllowed = ccf.stateAGI * .30;		// Use 30% of Cash Flow Receipts
            charitableCont[year] = getCRTotal(year) * cfb.getCharity();
            charitableGifts[year] += charitableCont[0];
            double vCharity = 0;

            for (VariableCashFlow v : vcfItems) {
                if (v.getCdType().equals("Y")) {
                    vCharity += v.getVTable()[year];
                }
            }

            ccf.charitableDed = Math.min(maxAllowed, charitableCont[year] + vCharity);
            charitableDeduction[year] = ccf.charitableDed;
            ccf.socialSecurity = socialTable[year];

            ccf.capTaxRate = .2;
            ccf.calculate(currentYear);
            if (ccf.totalTax > 0) {
                iTable[year] = ccf.totalTax;
            } else {
                iTable[year] = 0;
            }
        }
    }

    public void tax() {
        double[] iTable = new double[MAX_TABLE];

        for (int i = 0; i < MAX_TABLE; i++) {
            iTable[i] = 0.0;
        }

        if (cfb.isUseTax()) {
            CalcCashFlowTax ccf = new CalcCashFlowTax();
            double lessTax = taxDeduction[0] + getVcfTax(0);
            ccf.totalCashInflows = getCRTotal(0) - lessTax;
            ccf.stateIncomeTaxRate = cfb.getStateTaxRate();
            // Need to get social security values and add them in here.
            ccf.stateAGI = getCRTotal(0) - lessTax - socialTable[0] - bTable[0] - depreciation[0];
            double maxAllowed = ccf.stateAGI * .30;

            //double[] charity = (double[]) dispMap.get("Charitable Contributions");

            //charity[0] += getCRTotal(0) * cfb.getCharity();
            charitableCont[0] += getCRTotal(0) * cfb.getCharity();
            //charitableGifts[0] += charity[0];
            charitableGifts[0] += charitableCont[0];

            double vCharity = 0;
            for (VariableCashFlow v : vcfItems) {
                if (v.getCdType().equals("Y")) {
                    //charity[0] += v.getVTable()[0];
                    vCharity += v.getVTable()[0];
                }
            }

            //ccf.charitableDed = Math.min(maxAllowed, charity[0]);
            ccf.charitableDed = Math.min(maxAllowed, charitableCont[0] + vCharity);
            charitableDeduction[0] = ccf.charitableDed;
            ccf.socialSecurity = socialTable[0];
            ccf.capTaxRate = .2;
            ccf.calculate(currentYear);
            if (ccf.totalTax > 0) {
                iTable[0] = ccf.totalTax;
            } else {
                iTable[0] = 0;
            }
        }
        dispMap.put("Income Tax", iTable);
    }

    public double getVcfTax(int year) {
        double total = 0.0;
        for (VariableCashFlow v : vcfItems) {
            total += v.getTaxableValue(year);
        }
        return total;
    }

    public double getVcfCharity(int year) {
        double total = 0.0;
        for (VariableCashFlow v : vcfItems) {
            if (v.getCdType().equals("Y")) {
                total += v.getVTable()[year];
            }
        }
        return total;
    }

    public void fixTax(int year) {
        double[] iTable = (double[]) dispMap.get("Income Tax");
        if (cfb.isUseTax()) {
            CalcCashFlowTax ccf = new CalcCashFlowTax();
            double lessTax = taxDeduction[year] + getVcfTax(year);
            ccf.totalCashInflows = getCRTotal(year) - lessTax;
            ccf.stateIncomeTaxRate = cfb.getStateTaxRate();

            // Need to get social security values and add them in here.
            ccf.stateAGI = getCRTotal(year) - lessTax - socialTable[year] - bTable[year] - depreciation[year];
            double maxAllowed = ccf.stateAGI * .30;
            double charity = charitableGifts[year];
            for (VariableCashFlow v : vcfItems) {
                if (v.getCdType().equals("Y")) {
                    charity += v.getVTable()[year];
                }
            }

            ccf.charitableDed = Math.min(maxAllowed, charity);
            charitableDeduction[year] = ccf.charitableDed;
            ccf.capTaxRate = .2;
            ccf.socialSecurity = socialTable[year];
            ccf.calculate(currentYear + year);
            if (ccf.totalTax > 0) {
                iTable[year] = ccf.totalTax;
            } else {
                iTable[year] = 0;
            }
        }
    }

    public double getTax(int year) {
        double[] iTable = (double[]) dispMap.get("Income Tax");
        return iTable[year];
    }

    public double getCRTotal(int year) {
        Iterator i = cashMap.values().iterator();
        double total = 0.0;
        while (i.hasNext()) {
            double[] cMap = (double[]) i.next();
            total += cMap[year];
        }

        return total + getCashItemTotal(year);
    }

    public double getCashItemTotal(int year) {
        double v = 0;
        for (VariableCashFlow vcf : cashItem) {
            if (vcf.getCfType().equals("C")) {
                v += vcf.getVTable()[year];
            }
        }
        return v;
    }

    public double getCRTotalRnd(int year) {
        // Print out the cash flow map for this year:
        Iterator i = cashMap.values().iterator();

        double total = 0.0;
        while (i.hasNext()) {
            double[] cMap = (double[]) i.next();
            total += cMap[year];
        }

        total += getCashItemTotal(year);

        return Math.rint(total / 1000);

    }

    public double getCashRetirement(int year) {
        double[] iTable = (double[]) cashMap.get("cRetirement");
        return iTable[year];
    }

    public double getRetirementBalance(int year) {
        double[] iTable = (double[]) rMap.get("Balance");
        return iTable[year];
    }

    public double getDISPTotal(int year) {
        Iterator i = dispMap.values().iterator();
        double total = 0.0;
        while (i.hasNext()) {
            double[] dMap = (double[]) i.next();
            total += dMap[year];
        }
        return total + getDispItemTotal(year) + charitableCont[year];
    }

    public double getDispItemTotal(int year) {
        double v = 0;
        for (VariableCashFlow vcf : dispItem) {
            if (vcf.getCfType().equals("D")) {
                v += vcf.getVTable()[year];
            }
        }
        return v;
    }

    public double getDISPTotalRnd(int year) {
        Iterator i = dispMap.values().iterator();
        double total = 0.0;
        total += Math.rint(charitableCont[year] / 1000.0);			// Add in the chairable contribution here.
        while (i.hasNext()) {
            double[] dMap = (double[]) i.next();
            total += Math.rint(dMap[year] / 1000.0);
        }
        return total + Math.rint(getDispItemTotal(year) / 1000.0);
    }

    public void securityBalance() {
        double[] iTable = new double[MAX_TABLE];
        String qString = SecuritiesBean.OWNER_ID + "='" + cb.getPrimaryId() + "'";
        SecuritiesBean sBean = new SecuritiesBean();
        ArrayList<SecuritiesBean> sList = sBean.getBeans(qString);

        for (SecuritiesBean s : sList) {
            double value = s.getAssetValue();
            for (int i = 0; i < MAX_TABLE; i++) {
                iTable[i] += value;
            }
        }

        sMap.put("Securities Balance", iTable);
        dbobj.stop();
    }

    public double getSecurityBalance(int year) {
        double[] iTable = (double[]) sMap.get("Securities Balance");
        return iTable[year];
    }

    public void excessCash() {
        double[] iTable = new double[MAX_TABLE];
        for (int i = 0; i < MAX_TABLE; i++) {
            iTable[i] = 0.0;
        }

        double eCash = (getCRTotal(0) - getDISPTotal(0));

        /*
         * if( eCash > 0) { iTable[0] = eCash * .7; } else { iTable[0] = eCash; }
         */
        iTable[0] = eCash;
        sMap.put("Excess Cash", iTable);
    }

    public double getExcessCash(int year) {
        double[] iTable = (double[]) sMap.get("Excess Cash");
        return iTable[year];
    }
    double[] bondTable = new double[MAX_TABLE];

    public void mBonds() {
        String qString = BondBean.OWNER_ID + "='" + cb.getPrimaryId() + "'";
        BondBean bean = new BondBean();
        ArrayList<BondBean> bList = bean.getBeans(qString);

        for (BondBean b : bList) {
            double value = b.getAssetValue();
            double growth = b.getAssetGrowth();
            for (int i = 0; i < MAX_TABLE; i++) {
                value += value * growth;
                bondTable[i] += value;
            }
        }

        bMap.put("Bond Balance", bondTable);
    }

    public double getMBonds(int year) {

        double[] iTable = (double[]) bMap.get("Bond Balance");
        return iTable[year];

    }

    public void fixTable() {
        // Calculate weighted growth and interest rates

        // We used to calculate this weighted growth here, but now we want to pull that
        // from the cash flow variables.

        double wGrowth = .035;
        double wInt = .02;

        SecuritiesBean sBean = new SecuritiesBean();
        ArrayList<SecuritiesBean> sList = sBean.getBeans(SecuritiesBean.OWNER_ID + "='" + cb.getPrimaryId() + "'");
        double tGrowth = 0;
        double tIncome = 0;
        double tBalance = 0;
        for (SecuritiesBean s : sList) {
            tGrowth += s.getValue() * s.getGrowthRate();
            tIncome += s.getValue() * s.getDivInt();
            tBalance += s.getValue();
        }

        // Since we have a larger than zero we have securities
        if (tBalance > 0) {
            wGrowth = tGrowth / tBalance;
            wInt = tIncome / tBalance;
        }

        // Clear the list as we don't need it anymore and we can garbage collect.
        sList.clear();


        double[] cashS = (double[]) cashMap.get("Securities");
        double[] spTotal = (double[]) sMap.get("Securities Balance");
        double[] exTotal = (double[]) sMap.get("Excess Cash");
        double[] growth = (double[]) sMap.get("Growth");
        //double[] charity = (double[]) dispMap.get("Charitable Contributions");

        if (spTotal[0] <= 0) {
            spTotal[0] = 0;
        }

        growth[0] = (spTotal[0]) * wGrowth;


        exTotal[0] = (getCRTotal(0) - (getDISPTotal(0)));

        for (int i = 1; i < MAX_TABLE; i++) {
            spTotal[i] = (spTotal[i - 1] + (exTotal[i - 1]) + growth[i - 1]);

            cashS[i] = wInt * (spTotal[i]);

            double cr = getCRTotal(i);
            //charity[i] = cr * cfb.getCharity();
            //charitableGifts[i] += charity[i];
            charitableCont[i] = cr * cfb.getCharity();
            charitableGifts[i] += charitableCont[i];

            fixTax(i);

            double eCash = (getCRTotal(i) - getDISPTotal(i));

            exTotal[i] = eCash;
            growth[i] = (spTotal[i] * wGrowth);
        }
    }

    public void securityGrowth() {
        double[] sg = new double[MAX_TABLE];
        for (int i = 0; i < MAX_TABLE; i++) {
            sg[i] = 0.0;
        }
        sMap.put("Growth", sg);
    }

    public double getGrowth(int year) {
        double[] v = (double[]) sMap.get("Growth");
        return v[year];
    }

    public void finalize() {
        cashItem.clear();
        dispItem.clear();
        netItem.clear();
        estateItem.clear();
        vcfItems.clear();
        toCharity.clear();
        cashMap.clear();
        dispMap.clear();
    }
}
