package com.teag.estate;

/**
 * @author paul stay
 * Created on Apr 18, 2005
 * Modified March 2009,  Changed to Use Term Life Factors.
 * Description Charitable Lead Annuity Tool (CLAT)
 */
import java.util.ArrayList;
import java.util.HashMap;

import com.estate.constants.*;
import com.teag.bean.AssetSqlBean;
import com.zcalc.zCalc;

public class ClatTool extends EstatePlanningTool {

    public final static String ID = "ID";
    public final static String TERM = "TERM";
    public final static String AFR_RATE = "AFR_RATE";
    public final static String AFR_DATE = "AFR_DATE";
    public final static String ANNUITY = "ANNUITY";
    public final static String ANNUITY_FREQ = "ANNUITY_FREQ";
    // Payment is 0 for end of period, 1 for beginning
    public final static String ANNUITY_TYPE = "ANNUITY_TYPE";
    public final static String INCOME_TAX_RATE = "INCOME_TAX_RATE";
    public final static String ESTATE_TAX_RATE = "ESTATE_TAX_RATE";
    public final static String FINAL_DEATH = "FINAL_DEATH";
    public final static String ANNUITY_INCREASE = "ANNUITY_INCREASE";
    public final static String REMAINDER_INTEREST = "REMAINDER_INTEREST";
    // Z = Zero out, R = remainder interest, A = annuity payment
    public final static String CALC_TYPE = "CALC_TYPE";
    public final static String GRANTOR_FLAG = "GRANTOR_FLAG"; // Grantor will
    // pay taxes.
    public final static String CLAT_TYPE = "CLAT_TYPE";
    public final static String LIFE_TYPE = "LIFE_TYPE";
    private long id;
    private String uuid;
    HashMap<String, Object> record;
    private int term;
    private int age1;
    private int age2;
    private double lifeExpectancy;
    private double afrRate;
    private String afrDate;
    private double annuity = 0;
    private double annuityFreq = 4;
    private int annuityType = 0; // Ordinary Annuity payment at end is 0, begin
    // is 1
    private double incomeTaxRate;
    private double estateTaxRate;
    private int finalDeath;
    private String grantorFlag;
    private double annuityIncrease;
    private String calcType;
    // com.estate.constants Enum types for the two following fields.
    private String clatType = "T"; // T = Term, L = Life, S= Shorter, G =
    // Greater
    private String lifeType = "L"; // L = last to die, F = first to die
    private int clatTerm; // THis is the calculated term length
    // Build Asset list will gather all of the assets for inclusion to the tool
    ArrayList<AssetData> assetList = new ArrayList<AssetData>();
    boolean securities = false;
    double annuityFactor;
    double paymentRate;
    double actualPaymentRate;
    double annuityInterest;
    double remainderInterest;
    double totalInterest;
    double nonCharitableInterest;
    double clatDeduction;
    double maxPayment;
    double totalValue;
    double toolValue;
    double growth;
    double income;
    double discountedValue;
    double nonDiscountValue;
    double[][] stdTable;
    double[] clatTaxTable;
    double[] stdTaxTable;
    double[] toCharity;
    double[] clatTrustValue;
    double charityGrowth = .07;
    double[][] cashflow;
    double[] grantorTax;
    double[][] schedule;
    int tableLength = 40;
    boolean useLLC = false;
    boolean annuityExhaustionTest = false;
    boolean presentValueRegs = false; // used if remainder interest is not the
    // same as we specify
    // ToolAssetData assetData[];
    Object[] assetData;

    public static double RND(double x, double prec) {
        double y = Math.pow(10, prec);
        return Math.round(x * y) / y;
    }

    public void calculate() {

        EstateToolDistribution tad = new EstateToolDistribution();
        tad.setToolId(id);
        tad.setToolTableId(getToolTableId());
        tad.buildToolAssetList();
        assetData = tad.getAssetToolData();
        useLLC = tad.useLLC;

        totalValue = tad.getTotalValue();
        growth = tad.getGrowth();
        income = tad.getIncome();
        nonDiscountValue = tad.getNonDiscountValue();
        discountedValue = tad.getDiscountValue();
        toolValue = nonDiscountValue + discountedValue;

        // Since we calculate this each time, we need to set them first to
        // false.
        presentValueRegs = false;
        annuityExhaustionTest = false;

        // If the annuity Increase is greater than 20% we need to limit it!
        if (annuityIncrease > .2) {
            annuityIncrease = .2;
        }

        // set the status of the term life factors
        int status = 0;
        int lStatus = 0;

        if (lifeType.equalsIgnoreCase("F")) {
            lStatus = 1;
        }
        if (clatType.equals("G")) {
            status = 2 + lStatus;
        }

        // Test for exhaustion an calculate the real remainder interest!
        zCalc zc = new zCalc();
        zc.StartUp();

        // Calculate Annuity Payment
        if (calcType.equalsIgnoreCase("R")) {
            if (remainderInterest < 0) {
                remainderInterest = 0;
            }

            paymentRate = zc.zANNRATETARGET(toolValue, remainderInterest,
                    afrRate, term, 0, 0, 0, 0, 0, annuityIncrease,
                    (int) annuityFreq, annuityType, 0, 0, 0);
            paymentRate = RND(paymentRate, 7);
            annuity = paymentRate * toolValue;
        } else if (calcType.equalsIgnoreCase("Z")) {
            remainderInterest = 0;
            paymentRate = zc.zANNRATETARGET(toolValue, 0, afrRate, term, 0, 0,
                    0, 0, 0, annuityIncrease, (int) annuityFreq, annuityType,
                    0, 0, 0);
            paymentRate = RND(paymentRate, 7);
            annuity = paymentRate * toolValue;
        } else {
            paymentRate = annuity / toolValue;
        }

        maxPayment = toolValue * zc.zANNRATEMAX(afrRate, term, age1, age2, 0, 0, 0,
                annuityIncrease, (int) annuityFreq, (int) annuityType, 0, 0, 0);

        lifeExpectancy = zc.zLE(age1, age2, 0, 0, 0, status, 0);
        clatDeduction = zc.zANNTERMLIFE(toolValue, paymentRate, afrRate, term,
                age1, age2, 0, 0, 0, annuityIncrease, (long) annuityFreq,
                annuityType, 0, status, 0, 0);
        annuityInterest = clatDeduction;
        nonCharitableInterest = toolValue - clatDeduction;
        remainderInterest = nonCharitableInterest;

        annuityFactor = zc.zTERMLIFE(0, afrRate, term, age1, age2, 0, 0, 0,
                annuityIncrease, (long) annuityFreq, annuityType, 0, status, 0);

        double annuityExhaustion = zc.zEXANN(22, totalValue, annuity, afrRate,
                term, age1, age2, 0, 0, 0, annuityIncrease, (int) annuityFreq,
                annuityType, 0, status, 0);

        zc.ShutDown();

        if (annuityExhaustion == 0) {
            annuityExhaustionTest = false;
            this.presentValueRegs = false;
        } else {
            annuityExhaustionTest = true;
            this.presentValueRegs = true;
        }

        if (clatType.equalsIgnoreCase("T")) {
            clatTerm = term;
        } else if (clatType.equalsIgnoreCase("L")) {
            clatTerm = (int) lifeExpectancy;

            // Since we set the status up above, we should get the appropriate
            // term
        } else if (clatType.equalsIgnoreCase("S")) {
            clatTerm = (int) Math.min(term, lifeExpectancy);
        } else { // Trust Type is "G"
            clatTerm = (int) Math.max(term, lifeExpectancy);
        }

        buildCashFlow();
    }

    public void calculate2() {
        // ToolAssetDistribution tad = new ToolAssetDistribution();
        double toolValues = 0;
        EstateToolDistribution tad = new EstateToolDistribution();

        // tad.setDbObj(dbObj);
        tad.setToolId(id);
        tad.setToolTableId(getToolTableId());
        tad.buildToolAssetList();
        assetData = tad.getAssetToolData();
        useLLC = tad.useLLC;

        totalValue = tad.getTotalValue();
        growth = tad.getGrowth();
        income = tad.getIncome();
        nonDiscountValue = tad.getNonDiscountValue();
        discountedValue = tad.getDiscountValue();

        zCalc zc = new zCalc();
        zc.StartUp();

        toolValues = nonDiscountValue + discountedValue;

        if (calcType.equals("R")) {
            paymentRate = zc.zANNRATETARGET(toolValues, remainderInterest,
                    afrRate, term, 0, 0, 0, 0, 0, annuityIncrease,
                    (int) annuityFreq, annuityType, 0, 0, 0);
            annuity = paymentRate * toolValues;
        } else if (calcType.equals("Z")) {
            paymentRate = zc.zANNRATEMAX(afrRate, term, 0, 0, 0, 0, 0,
                    annuityIncrease, (int) annuityFreq, annuityType, 0, 0, 0);
            annuity = paymentRate * toolValues;
        } else {
            paymentRate = annuity / toolValues;
        }

        annuityFactor = zc.zTERM(0L, afrRate, term, 0, (int) annuityFreq,
                annuityType, 0);

        actualPaymentRate = annuity / toolValues;
        clatDeduction = zc.zANNTERM(toolValues, actualPaymentRate, afrRate,
                term, annuityIncrease, (int) annuityFreq, annuityType, 0, 0);
        totalInterest = toolValues;

        annuityInterest = clatDeduction;
        nonCharitableInterest = totalInterest - clatDeduction;
        this.remainderInterest = nonCharitableInterest;

        double annuityExhaustion = zc.zEXANN(22, discountedValue, annuity,
                afrRate, term, 0, 0, 0, 0, 0, annuityIncrease,
                (int) annuityFreq, annuityType, 0, 0, 0);

        zc.ShutDown();

        if (annuityExhaustion == 0) {
            annuityExhaustionTest = false;
        } else {
            annuityExhaustionTest = true;
        }

        buildCashFlow();
    }

    public double calcValue(int years) {
        buildAssetList();
        double value = 0;

        for (Object ai : assetList) {
            AssetData item = (AssetData) ai;
            if (item.getAssetType() != AssetData.SECURITY) {
                value += item.getValue() * Math.pow(1 + item.getGrowth(), years);
            }
        }
        return value;
    }

    public void buildAssetList() {

        assetList.clear();
        securities = false;

        if (assetData == null) {
            return;
        }

        for (int i = 0; i < assetData.length; i++) {
            ToolAssetData asset = (ToolAssetData) assetData[i];
            AssetData ad;
            if (asset.getAssetType() == AssetSqlBean.SECURITIES) {
                ad = new AssetData(AssetData.SECURITY);
                securities = true;
            } else {
                ad = new AssetData(AssetData.OTHER);
            }
            ad.setName(asset.getName());
            ad.setValue(asset.getValue());
            ad.setGrowth(asset.getGrowth());
            ad.setIncome(asset.getIncome());
            assetList.add(ad);
        }

        // If there are no securities present, than the income will need to be
        // put into a securities account
        if (!securities) {
            AssetData ad = new AssetData(AssetData.SECURITY);
            ad.setValue(0);
            ad.setGrowth(.05);
            ad.setIncome(.03);
            ad.setName("Internal Securities");
            assetList.add(ad);
        }
    }

    // Build the cash flow table that we will use for the summary page.
    public void buildCashFlow() {
        cashflow = new double[term][7];
        int i = 0;
        double ap = annuity;
        double ai = annuityIncrease;
        double balance = totalValue;
        double gRate = growth;
        double iRate = income;

        for (i = 0; i < term; i++) {
            cashflow[i][0] = i + 1; // Year
            cashflow[i][1] = balance; // Beg. Balance
            cashflow[i][2] = balance * gRate; // Growth
            cashflow[i][3] = balance * iRate; // income
            if (grantorFlag.equals("N")) {
                // Since this is a non grantor trust, we can take a charitable
                // deduction for the payment before we tax!
                double taxAmount = cashflow[i][3] - ap; // Subtract out the
                // payment for the
                // charitable deduction
                if (taxAmount > 0) {
                    cashflow[i][4] = -(taxAmount * incomeTaxRate);
                } else {
                    cashflow[i][4] = 0; // If the above amount is less than zero
                    // there is not tax burden!
                }
            } else {
                cashflow[i][4] = 0; // Grantor pays income taxes
            }
            cashflow[i][5] = -ap;
            cashflow[i][6] = cashflow[i][1] + cashflow[i][2] + cashflow[i][3] + cashflow[i][4] + cashflow[i][5];
            balance = cashflow[i][6];
            ap = ap * (1.0 + ai);
        }

    }

    // This is for a non grantor clat, we will walk through each asset, and
    // create the trust income, payments, etc.
    public void buildStdClat() {
        buildAssetList(); // We build this each time we enter here to initialize
        // the assets.

        if (finalDeath <= 0) {
            finalDeath = 25; // Just in case we need to specify a final death
        }

        /*
         * schedule[x][0] = beginning balance schedule[x][1] = growth
         * schedule[x][2] = income schedule[x][3] = annuity payment (may be
         * increase per each year schedule[x][4] = tax (if any) schedule[x][5] =
         * End Balance
         */
        schedule = new double[clatTerm][6];

        double annPayment = getAnnuity(); // Annuity Payment
        double annInc = getAnnuityIncrease(); // Annuity Annual Increase (cannot
        // exceed 20%

        double income = 0; // yearly income from all assets
        double growth = 0; // yearly growth from all assets
        double trustValue = 0;

        for (AssetData a : assetList) {
            trustValue += a.getValue();
            income = Math.max(income, a.getIncome());
            growth = Math.max(income, a.getGrowth());
        }

        for (int i = 0; i < clatTerm; i++) {

            if (i == 0) {
                schedule[i][0] = trustValue;
            } else {
                schedule[i][0] = schedule[i - 1][5];
            }
            schedule[i][1] = schedule[i][0] * growth;
            schedule[i][2] = schedule[i][0] * income;
            schedule[i][3] = annPayment;

            double stdTax = 0;
            if (this.getGrantorFlag().equalsIgnoreCase("N")) {
                // We get a charitable Deduction since this is a non grantor
                // trust and the income is taxed
                // inside the trust. We can deduct the full amount of the amount
                // paid to charity!
                double taxAmount = schedule[i][2] - annPayment;

                // Since there is more income left after the deduction we
                // tax the remainder
                if (taxAmount > 0) {
                    stdTax = -(taxAmount * incomeTaxRate);
                }

                schedule[i][4] = stdTax;
            } else {
                schedule[i][4] = 0;
            }

            schedule[i][5] = schedule[i][0] + schedule[i][1] + schedule[i][2] + stdTax - annPayment;

            annPayment = annPayment * (1.0 + annInc);
        }
    }

    public void buildClat2Table() {

        buildAssetList();

        double accTax = 0; // Accululate the tax inside the clat

        if (finalDeath <= 0) {
            finalDeath = 25;
        }

        tableLength = 120;

        clatTaxTable = new double[tableLength];
        stdTaxTable = new double[tableLength];
        toCharity = new double[tableLength];
        clatTrustValue = new double[tableLength];
        grantorTax = new double[tableLength];

        // We start out with the current total value at year 1
        clatTrustValue[0] = totalValue;
        toCharity[0] = 0;

        double annPayment = getAnnuity();
        double aInc = annuityIncrease;

        for (int i = 1; i < tableLength; i++) {

            double income = 0;
            double growth = 0;

            // Get the growth, and income from each asset
            for (AssetData a : assetList) {
                growth += a.getGrowthValue();
                income += a.getIncomeValue();
            }

            if (this.grantorFlag.equals("Y") && i <= clatTerm) {
                grantorTax[i - 1] = income * incomeTaxRate;
            }

            double stdTax = 0;

            if (grantorFlag.equals("N")) {
                stdTax = income * getIncomeTaxRate();
            }

            stdTaxTable[i] = stdTax;

            if (i < getTerm()) {
                income -= annPayment;
            }

            double clatTax = (income) * incomeTaxRate;

            if (clatTax < 0) {
                clatTaxTable[i] = 0;
                clatTax = 0;
            } else {
                clatTaxTable[i] = clatTax;
                accTax += clatTax;
            }

            if (income > 0 && grantorFlag.equals("N")) {
                income -= clatTax;
            }

            if (annuityType == 0) { // End of period payment
                toCharity[i] = toCharity[i - 1] + (toCharity[i - 1] * charityGrowth);
                if (i <= term) {
                    toCharity[i] += annPayment;
                }
            } else { // Begining of period Payment
                if (i <= term) {
                    toCharity[i] = (toCharity[i - 1] + annPayment) + ((toCharity[i - 1] + annPayment) * charityGrowth);
                } else {
                    toCharity[i] = toCharity[i - 1] + ((toCharity[i - 1]) * charityGrowth);
                }
            }

            // Calcualte the total estate value.
            clatTrustValue[i] = clatTrustValue[i - 1] + growth + income;

            if (i < getTerm()) {
                annPayment += annPayment * aInc;
            } else {
                annPayment = 0;
            }

            // Update each asset item, and then add the excess income to the
            // securities
            for (AssetData a : assetList) {
                a.update();
                if (a.getAssetType() == AssetData.SECURITY) {
                    a.addIncome(income);
                }
            }
        }
    }

    public void buildStdTable() {
        buildAssetList();

        if (finalDeath <= 0) {
            finalDeath = 25;
        }

        tableLength = finalDeath + 1;

        // Store the results of the spread sheet for the tool....
        stdTable = new double[tableLength][5];

        stdTable[0][4] = 0.0;

        for (Object itr : assetList) {
            AssetData itm = (AssetData) itr;
            if (itm.getAssetType() == AssetData.SECURITY) {
                stdTable[0][4] += itm.getValue();
            }
        }

        for (int i = 1; i < tableLength; i++) {
            double income = 0;
            double earnings = 0;
            double secIncome = 0;
            for (Object ai : assetList) {
                AssetData item = (AssetData) ai;
                if (item.getAssetType() == AssetData.SECURITY) {
                    earnings += item.getValue() * item.getGrowth();
                    secIncome += item.getIncomeValue();
                } else {
                    income += item.getIncomeValue();
                }
            }
            stdTable[i][0] = i;
            stdTable[i][1] = earnings;
            stdTable[i][2] = income;
            stdTable[i][3] = -(getIncomeTaxRate() * income);
            stdTable[i][4] = stdTable[i - 1][4] + stdTable[i][1] + stdTable[i][2] + stdTable[i][3];

            for (Object ai : assetList) {
                AssetData item = (AssetData) ai;
                item.update();
                if (item.getAssetType() == AssetData.SECURITY) {
                    item.addIncome(stdTable[i][2] + stdTable[i][3]);
                }
            }
        }
        assetList.clear();
    }

    public double calcSecurity(int years) {
        return stdTable[years - 1][4];
    }

    @Override
    public void insert() {
        dbObj.start();
        dbObj.setTable("CLAT_TOOL");
        dbObj.clearFields();
        record = null;

        dbAddField("AFR_RATE", getAfrRate());
        dbAddDate("AFR_DATE", getAfrDate());
        dbAddField("TERM", getTerm());
        dbAddField("ANNUITY", getAnnuity());
        dbAddField("ANNUITY_FREQ", getAnnuityFreq());
        dbAddField("ANNUITY_TYPE", getAnnuityType());
        dbAddField("INCOME_TAX_RATE", getIncomeTaxRate());
        dbAddField("Estate_TAX_RATE", getEstateTaxRate());
        dbAddField("FINAL_DEATH", getFinalDeath());
        dbAddField("CALC_TYPE", getCalcType());
        dbAddField("ANNUITY_INCREASE", getAnnuityIncrease());
        dbAddField("GRANTOR_FLAG", getGrantorFlag());
        dbAddField("REMAINDER_INTEREST", getRemainderInterest());
        dbAddField(CLAT_TYPE, getClatType());
        dbAddField(LIFE_TYPE, getLifeType());
        dbObj.start();
        dbObj.setTable("CLAT_TOOL");
        dbObj.clearFields();
        record = null;
        int error = dbObj.insert();

        if (error == 0) {
            uuid = dbObj.getUUID();
            record = dbObj.execute("select ID from CLAT_TOOL where UUID='" + uuid + "'");
            Object o = record.get("ID");
            if (o != null) {
                id = Integer.parseInt(o.toString());
            }
        }
        dbObj.stop();
    }

    @Override
    public void delete() {
        dbObj.start();
        dbObj.delete("CLAT_TOOL", "ID='" + id + "'");
        dbObj.stop();
    }

    /**
     * Read the information from the database
     */
    @Override
    public void read() {
        if (id > 0L) {
            dbObj.start();
            dbObj.setTable("CLAT_TOOL");
            dbObj.clearFields();
            record = null;

            String sql = "select * from CLAT_TOOL where ID='" + id + "'";
            record = dbObj.execute(sql);
            dbObj.stop();

            if (record != null) {
                setAfrRate(getDouble(record, "AFR_RATE"));
                setAfrDate(getDate(record, "AFR_DATE"));
                setId(getLong(record, "ID"));
                setTerm(getInteger(record, "TERM"));
                setAnnuity(getDouble(record, "ANNUITY"));
                setAnnuityFreq(getDouble(record, "ANNUITY_FREQ"));
                setAnnuityType(getInteger(record, "ANNUITY_TYPE"));
                setIncomeTaxRate(getDouble(record, "INCOME_TAX_RATE"));
                setFinalDeath(getInteger(record, "FINAL_DEATH"));
                setEstateTaxRate(getDouble(record, "ESTATE_TAX_RATE"));
                setRemainderInterest(getDouble(record, REMAINDER_INTEREST));
                setCalcType(getString(record, CALC_TYPE));
                setGrantorFlag(getString(record, GRANTOR_FLAG));
                setAnnuityIncrease(getDouble(record, ANNUITY_INCREASE));
                setRemainderInterest(getDouble(record, REMAINDER_INTEREST));
                setLifeType(getString(record, LIFE_TYPE));
                setClatType(getString(record, CLAT_TYPE));
            }
        }
    }

    /**
     * Update the database with the changes
     */
    @Override
    public void update() {
        dbObj.start();
        dbObj.setTable("CLAT_TOOL");
        dbObj.clearFields();
        record = null;

        dbAddField("AFR_RATE", getAfrRate());
        dbAddDate("AFR_DATE", getAfrDate());
        dbAddField("TERM", getTerm());
        dbAddField("ANNUITY", getAnnuity());
        dbAddField("ANNUITY_FREQ", getAnnuityFreq());
        dbAddField("ANNUITY_TYPE", getAnnuityType());
        dbAddField("INCOME_TAX_RATE", getIncomeTaxRate());
        dbAddField("Estate_TAX_RATE", getEstateTaxRate());
        dbAddField("FINAL_DEATH", getFinalDeath());
        dbAddField("CALC_TYPE", getCalcType());
        dbAddField("ANNUITY_INCREASE", getAnnuityIncrease());
        dbAddField("GRANTOR_FLAG", getGrantorFlag());
        dbAddField("REMAINDER_INTEREST", getRemainderInterest());
        dbAddField(CLAT_TYPE, getClatType());
        dbAddField(LIFE_TYPE, getLifeType());
        dbObj.setWhere("ID='" + id + "'");

        dbObj.update();

        dbObj.stop();
    }

    @Override
    public void report() {
    }

    public boolean isUseLLC() {
        return useLLC;
    }

    @Override
    public long getToolTableId() {
        return ToolTableTypes.CLAT.id();
    }

    /**
     * @return Returns the totalInterest.
     */
    public double getTotalInterest() {
        return totalInterest;
    }

    /**
     * @return Returns the totalValue.
     */
    public double getTotalValue() {
        return totalValue;
    }

    /**
     * @param actualPaymentRate
     *            The actualPaymentRate to set.
     */
    public void setActualPaymentRate(double actualPaymentRate) {
        this.actualPaymentRate = actualPaymentRate;
    }

    /**
     * @param afrDate
     *            The afrDate to set.
     */
    public void setAfrDate(String afrDate) {
        this.afrDate = afrDate;
    }

    /**
     * @param afrRate
     *            The afrRate to set.
     */
    public void setAfrRate(double afrRate) {
        this.afrRate = afrRate;
    }

    /**
     * @param annuity
     *            The annuity to set.
     */
    public void setAnnuity(double annuity) {
        this.annuity = annuity;
    }

    /**
     * @param annuityFactor
     *            The annuityFactor to set.
     */
    public void setAnnuityFactor(double annuityFactor) {
        this.annuityFactor = annuityFactor;
    }

    /**
     * @param annuityFreq
     *            The annuityFreq to set.
     */
    public void setAnnuityFreq(double annuityFreq) {
        this.annuityFreq = annuityFreq;
    }

    /**
     * @param annuityInterest
     *            The annuityInterest to set.
     */
    public void setAnnuityInterest(double annuityInterest) {
        this.annuityInterest = annuityInterest;
    }

    /**
     * @param annuityType
     *            The annuityType to set.
     */
    public void setAnnuityType(int annuityType) {
        this.annuityType = annuityType;
    }

    /**
     * @param assetList
     *            The assetList to set.
     */
    public void setAssetList(ArrayList<AssetData> assetList) {
        this.assetList = assetList;
    }

    public String getGrantorFlag() {
        return grantorFlag;
    }

    public void setGrantorFlag(String grantorFlag) {
        this.grantorFlag = grantorFlag;
    }

    public double getAnnuityIncrease() {
        return annuityIncrease;
    }

    public void setAnnuityIncrease(double annuityIncrease) {
        this.annuityIncrease = annuityIncrease;
    }

    public String getCalcType() {
        return calcType;
    }

    public void setCalcType(String calcType) {
        this.calcType = calcType;
    }

    /**
     * @param charityGrowth
     *            The charityGrowth to set.
     */
    public void setCharityGrowth(double charityGrowth) {
        this.charityGrowth = charityGrowth;
    }

    /**
     * @param clatDeduction
     *            The clatDeduction to set.
     */
    public void setClatDeduction(double clatDeduction) {
        this.clatDeduction = clatDeduction;
    }

    /**
     * @param clatTaxTable
     *            The clatTaxTable to set.
     */
    public void setClatTaxTable(double[] clatTaxTable) {
        this.clatTaxTable = clatTaxTable;
    }

    /**
     * @param clatTrustValue
     *            The clatTrustValue to set.
     */
    public void setClatTrustValue(double[] clatTrustValue) {
        this.clatTrustValue = clatTrustValue;
    }

    /**
     * @param discountedValue
     *            The discountedValue to set.
     */
    public void setDiscountedValue(double discountedValue) {
        this.discountedValue = discountedValue;
    }

    /**
     * @param estateTaxRate
     *            The estateTaxRate to set.
     */
    public void setEstateTaxRate(double estateTaxRate) {
        this.estateTaxRate = estateTaxRate;
    }

    /**
     * @param finalDeath
     *            The finalDeath to set.
     */
    public void setFinalDeath(int finalDeath) {
        this.finalDeath = finalDeath;
    }

    /**
     * @param growth
     *            The growth to set.
     */
    public void setGrowth(double growth) {
        this.growth = growth;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param income
     *            The income to set.
     */
    public void setIncome(double income) {
        this.income = income;
    }

    /**
     * @param incomeTaxRate
     *            The incomeTaxRate to set.
     */
    public void setIncomeTaxRate(double incomeTaxRate) {
        this.incomeTaxRate = incomeTaxRate;
    }

    /**
     * @param nonCharitableInterest
     *            The nonCharitableInterest to set.
     */
    public void setNonCharitableInterest(double nonCharitableInterest) {
        this.nonCharitableInterest = nonCharitableInterest;
    }

    public void setNonDiscountValue(double nonDiscountValue) {
        this.nonDiscountValue = nonDiscountValue;
    }

    /**
     * @param paymentRate
     *            The paymentRate to set.
     */
    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
    }

    /**
     * @param remainderInterest
     *            The remainderInterest to set.
     */
    public void setRemainderInterest(double remainderInterest) {
        this.remainderInterest = remainderInterest;
    }

    /**
     * @param stdTable
     *            The stdTable to set.
     */
    public void setStdTable(double[][] stdTable) {
        this.stdTable = stdTable;
    }

    /**
     * @param stdTaxTable
     *            The stdTaxTable to set.
     */
    public void setStdTaxTable(double[] stdTaxTable) {
        this.stdTaxTable = stdTaxTable;
    }

    /**
     * @param term
     *            The term to set.
     */
    public void setTerm(int term) {
        this.term = term;
    }

    /**
     * @param toCharity
     *            The toCharity to set.
     */
    public void setToCharity(double[] toCharity) {
        this.toCharity = toCharity;
    }

    /**
     * @param totalInterest
     *            The totalInterest to set.
     */
    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    /**
     * @param totalValue
     *            The totalValue to set.
     */
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public void setUseLLC(boolean useLLC) {
        this.useLLC = useLLC;
    }

    /**
     * @return Returns the actualPaymentRate.
     */
    public double getActualPaymentRate() {
        return actualPaymentRate;
    }

    /**
     * @return Returns the afrDate.
     */
    public String getAfrDate() {
        return afrDate;
    }

    /**
     * @return Returns the afrRate.
     */
    public double getAfrRate() {
        return afrRate;
    }

    /**
     * @return Returns the annuity.
     */
    public double getAnnuity() {
        return annuity;
    }

    /**
     * @return Returns the annuityFactor.
     */
    public double getAnnuityFactor() {
        return annuityFactor;
    }

    /**
     * @return Returns the annuityFreq.
     */
    public double getAnnuityFreq() {
        return annuityFreq;
    }

    /**
     * @return Returns the annuityInterest.
     */
    public double getAnnuityInterest() {
        return annuityInterest;
    }

    /**
     * @return Returns the annuityType.
     */
    public int getAnnuityType() {
        return annuityType;
    }

    /**
     * @return Returns the assetList.
     */
    public ArrayList<AssetData> getAssetList() {
        return assetList;
    }

    /**
     * @return Returns the charityGrowth.
     */
    public double getCharityGrowth() {
        return charityGrowth;
    }

    /**
     * @return Returns the clatDeduction.
     */
    public double getClatDeduction() {
        return clatDeduction;
    }

    /**
     * @return Returns the clatTaxTable.
     */
    public double[] getClatTaxTable() {
        return clatTaxTable;
    }

    /**
     * @return Returns the clatTrustValue.
     */
    public double[] getClatTrustValue() {
        return clatTrustValue;
    }

    /**
     * @return Returns the discountedValue.
     */
    public double getDiscountedValue() {
        return discountedValue;
    }

    /**
     * @return Returns the estateTaxRate.
     */
    public double getEstateTaxRate() {
        return estateTaxRate;
    }

    /**
     * @return Returns the finalDeath.
     */
    public int getFinalDeath() {
        return finalDeath;
    }

    /**
     * @return Returns the growth.
     */
    public double getGrowth() {
        return growth;
    }

    /**
     * @return Returns the id.
     */
    public long getId() {
        return id;
    }

    /**
     * @return Returns the income.
     */
    public double getIncome() {
        return income;
    }

    /**
     * @return Returns the incomeTaxRate.
     */
    public double getIncomeTaxRate() {
        return incomeTaxRate;
    }

    /**
     * @return Returns the nonCharitableInterest.
     */
    public double getNonCharitableInterest() {
        return nonCharitableInterest;
    }

    public double getNonDiscountValue() {
        return nonDiscountValue;
    }

    /**
     * @return Returns the paymentRate.
     */
    public double getPaymentRate() {
        return paymentRate;
    }

    /**
     * @return Returns the remainderInterest.
     */
    public double getRemainderInterest() {
        return remainderInterest;
    }

    /**
     * @return Returns the stdTable.
     */
    public double[][] getStdTable() {
        return stdTable;
    }

    /**
     * @return Returns the stdTaxTable.
     */
    public double[] getStdTaxTable() {
        return stdTaxTable;
    }

    /**
     * @return Returns the term.
     */
    public int getTerm() {
        return term;
    }

    /**
     * @return Returns the toCharity.
     */
    public double[] getToCharity() {
        return toCharity;
    }

    @Override
    public String writeupText() {
        return null;
    }

    public double[][] getCashflow() {
        return cashflow;
    }

    public void setCashflow(double[][] cashflow) {
        this.cashflow = cashflow;
    }

    public boolean isAnnuityExhaustionTest() {
        return annuityExhaustionTest;
    }

    public void setAnnuityExhaustionTest(boolean annuityExhaustionTest) {
        this.annuityExhaustionTest = annuityExhaustionTest;
    }

    public double[] getGrantorTax() {
        return grantorTax;
    }

    public void setGrantorTax(double[] grantorTax) {
        this.grantorTax = grantorTax;
    }

    public String getClatType() {
        return clatType;
    }

    public void setClatType(String clatType) {
        this.clatType = clatType;
    }

    public String getLifeType() {
        return lifeType;
    }

    public void setLifeType(String lifeType) {
        this.lifeType = lifeType;
    }

    public int getAge1() {
        return age1;
    }

    public void setAge1(int age1) {
        this.age1 = age1;
    }

    public int getAge2() {
        return age2;
    }

    public void setAge2(int age2) {
        this.age2 = age2;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public double getToolValue() {
        return toolValue;
    }

    public void setToolValue(double toolValue) {
        this.toolValue = toolValue;
    }

    public int getClatTerm() {
        return clatTerm;
    }

    public void setClatTerm(int clatTerm) {
        this.clatTerm = clatTerm;
    }

    public double[][] getSchedule() {
        return schedule;
    }

    public void setSchedule(double[][] schedule) {
        this.schedule = schedule;
    }

    public boolean isPresentValueRegs() {
        return presentValueRegs;
    }

    public void setPresentValueRegs(boolean presentValueRegs) {
        this.presentValueRegs = presentValueRegs;
    }

    public double getMaxPayment() {
        return maxPayment;
    }

    public void setMaxPayment(double maxPayment) {
        this.maxPayment = maxPayment;
    }
}
