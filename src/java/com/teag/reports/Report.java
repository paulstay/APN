/*
 * Created on Apr 27, 2005
 *
 */
package com.teag.reports;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estate.db.DBObject;
import com.estate.report.Scenario;
import com.estate.sc.utils.CFRow;
import com.estate.sc1.GenScenario1;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.EstatePlan.EstatePlanTable;
import com.teag.analysis.CashFlowTable;
import com.teag.bean.AdvisorBean;
import com.teag.bean.ChildrenBean;
import com.teag.bean.LocationBean;
import com.teag.bean.MarriageBean;
import com.teag.bean.PdfBean;
import com.teag.bean.PersonBean;
import com.teag.client.Children;
import com.teag.client.Marriages;
import com.teag.estate.AssetData;
import com.teag.estate.CharliePlanTool;
import com.teag.estate.ClatTool;
import com.teag.estate.CrtTool;
import com.teag.estate.CrummeyTool;
import com.teag.estate.FCFTool;
import com.teag.estate.GratTool;
import com.teag.estate.GratTrust;
import com.teag.estate.IditTool;
import com.teag.estate.LiquidAssetProtectionTool;
import com.teag.estate.MGTrustTool;
import com.teag.estate.PrivateAnnuityTool;
import com.teag.estate.QprtTool;
import com.teag.estate.QprtTrustTool;
import com.teag.estate.RpmTable;
import com.teag.estate.RpmTool;
import com.teag.estate.SCINTool;
import com.teag.estate.SIditTool;
import com.teag.estate.TClat2;
import com.teag.estate.TClatTool;
import com.teag.util.Utilities;
import com.teag.webapp.EstatePlanningGlobals;

/**
 * @author Paul Stay
 * uses PDF bean to transfer client and planner information.... 
 */

public class Report extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PdfWriter writer;

	private Document document;

	private int pageNum = 1;

	//private String clientFirstName;

	//private String userInfo.getSpouseFirstName();

	public boolean initialized = false;
	
	public boolean isSingle = false;

	DBObject dbObj = new DBObject();

	int clientID;

	int spouseID;

	HttpSession session = null;

	private EstatePlanningGlobals epg;
	
	private PdfBean userInfo = new PdfBean();
	
	boolean useLLC = false;
	
	boolean useCrum = false;

	/***************************************************************************
	 * Construct a report and provide the output stream. The output stream is
	 * most likely retrieved from the response object of a servlet.
	 * 
	 * @param out
	 */
	public Report(HttpServletResponse response) {
		// We need to start the document.
		// creat a document-object
		Document.compress = true;
		document = new Document(PageSize.LETTER.rotate());

		try {
			// BaseFont font = PageUtils.LoadFont("times.ttf");
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file

			writer = PdfWriter
					.getInstance(document, response.getOutputStream());

			// step 3: we open the document
			document.open();
			initialized = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/***************************************************************************
	 * Construct a Report Object and
	 * 
	 * @param name
	 */
	public Report(String name) {
		// We need to start the document.
		// creat a document-object
		Document.compress = true;
		document = new Document(PageSize.LETTER.rotate());

		try {
			// BaseFont font = PageUtils.LoadFont("times.ttf");
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file

			writer = PdfWriter
					.getInstance(document, new FileOutputStream(name));

			// step 3: we open the document
			document.open();
			initialized = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}
	
	public void error(String text) {
		// No ID so we can't generate a report.
		try {
			Paragraph para = new Paragraph(text);

			document.add(para);
			document.close();
		} catch (Exception e) {

		}

	}
	
	public void finish() {
		document.close();
	}

	public void genAssets(String client) {

		Assets assets = new Assets(document, writer);
		assets.setUserInfo(getUserInfo());
		assets.setClient(client);
		assets.setPageNum(pageNum);
		assets.setClientID(this.clientID);
		assets.draw();
		pageNum += assets.getPageCount();
		newPage();
	}

	public void genBlank(String client) {
		BlankPage bp = new BlankPage(document, writer);
		bp.setUserInfo(getUserInfo());
		bp.setClient(client);
		bp.draw();
		newPage();
	}

	public void genCashFlowGraph(String client, CashFlowTable cft,
			EstatePlanTable ept) {
		CashFlowGraph cfg = new CashFlowGraph(document, writer);
		cfg.setUserInfo(getUserInfo());
		cfg.setCashFlows(cft);
		cfg.setEstatePlanTable(ept);
		cfg.setClient(client);
		cfg.setClientFirstName(userInfo.getClientFirstName());
		cfg.setSpouseFirstName(userInfo.getSpouseFirstName());
		cfg.setPageNum(pageNum);
		cfg.draw();
		pageNum += cfg.getPageCount();
		newPage();
	}

	public void genCashFlows(String client) {
		CashFlows cf = new CashFlows(document, writer);
		cf.setUserInfo(getUserInfo());
		cf.setClient(client);
		cf.setPageNum(pageNum);
		cf.setClientID(this.clientID);
		cf.setSpouseID(this.spouseID);
		cf.setClientFirstName(this.userInfo.getClientFirstName());
		cf.setSpouseFirstName(userInfo.getSpouseFirstName());
		cf.draw();
		pageNum += cf.getPageCount();
		newPage();
	}
	
	public void genScenario1() {
		// generate CFRows, and then send to Scenario!
		GenScenario1 gc = new GenScenario1();
		PersonBean client = new PersonBean();
		client.setId(this.clientID);
		client.initialize();
		gc.setUserInfo(userInfo);
		gc.setClient(client);
		gc.buildScenario1();
		LinkedList<CFRow> rows = gc.getRows();
		Scenario sc = new Scenario(document,writer);
		sc.setUserInfo(userInfo);
		sc.setPageNum(pageNum);
		sc.setRowList(rows);
		sc.draw();
		newPage();
		/*
		Scenario1Graphs sg = new Scenario1Graphs(document,writer);
		sg.setClient(userInfo.getClientHeading());
		sg.setUserInfo(userInfo);
		sg.setPageNum(sc.getPageNum()+1);
		sg.setTaxes(gc.getTaxes());
		sg.setToFamily(gc.getFamily());
		sg.setToCharity(gc.getCharity());
		sg.draw();
		pageNum = sg.getPageNum();
		newPage();
		*/
	}
	
	public void genCLAT(String client) {
		ClatTool clatTool = (ClatTool) session.getAttribute("clat");

		clatTool.calculate();

		clatTool.buildStdTable();
		double stdTable[][] = clatTool.getStdTable();

		clatTool.buildAssetList();
		ArrayList<AssetData> assetList = clatTool.getAssetList();

		// used for first part of asset table....
		double cTotalAssets = 0.0;
		double fTotalAssets = 0.0;

		double fTotalValue = 0.0;

		double cSecuritiesValue = stdTable[0][4];
		double fSecuritiesValue = stdTable[clatTool.getFinalDeath()][4];
		double tempAssetVal[][] = new double[100][2];
		String tempListOfAssets[] = new String[100];
		int cnt = 0;
		for(AssetData item : assetList ) {
			String desc = item.getName();
			double cValue = item.getValue();
			double fValue = item.getValue()
					* Math.pow(1 + item.getGrowth(), clatTool.getFinalDeath());

            /*
            if (item.getAssetType() == AssetData.SECURITY) {
				continue;
			}
            */

			cTotalAssets += cValue;
			fTotalAssets += fValue;

			tempListOfAssets[cnt] = desc;
			tempAssetVal[cnt][0] = cValue;
			tempAssetVal[cnt++][1] = fValue;
		}

		CLATPages clat = new CLATPages(document, writer);
		clat.setUserInfo(getUserInfo());
		
		clat.setPageNum(pageNum);
		clat.setClient(client);
		clat.setUseLLC(clatTool.isUseLLC());

		clat.setMarketValue(cTotalAssets + cSecuritiesValue);
		clat.setYrsGrowth(clatTool.getFinalDeath());
		clat.setGrowthRate(clatTool.getGrowth());
		clat.setIncomeRate(clatTool.getIncome());
		clat.setTotalValue(fTotalAssets + fSecuritiesValue);

		clat.setEstateTax((fTotalAssets + fSecuritiesValue)
				* clatTool.getEstateTaxRate());
		clat.setEstateTaxCLAT(0);

		clat.setEstateTaxPercent(clatTool.getEstateTaxRate());

		clatTool.calculate();
		clatTool.buildStdTable();
		clatTool.buildClat2Table();

		double stdTax[] = clatTool.getStdTaxTable();
		double clatTax[] = clatTool.getClatTaxTable();
		double estateValue[] = clatTool.getClatTrustValue();
		double sTax = 0;
		double cTax = 0;
		for (int i = 0; i <= clatTool.getTerm(); i++) {
			sTax += stdTax[i];
			cTax += clatTax[i];
		}

		clat.setNetToFamily((fTotalAssets + fSecuritiesValue)
				- clat.getEstateTax());

		double toCharity[] = clatTool.getToCharity();
		clat.setNetToCharity(0);
		clat.setNetToCharityCLAT(toCharity[clatTool.getFinalDeath()]);
		double estateTaxable = fTotalValue + fSecuritiesValue;
		double estateFederalEstateTax = (fTotalValue + fSecuritiesValue)
				* clatTool.getEstateTaxRate();
		double clatFederalEstateTax = clatTool.getNonCharitableInterest()
				* clatTool.getEstateTaxRate();
		double estateToFamily = estateTaxable - estateFederalEstateTax;
		double clatToFamily = estateValue[clatTool.getFinalDeath() - 1]
				- clatFederalEstateTax;

		clat.setNetToFamily(estateToFamily);
		clat.setNetToFamilyCLAT(clatToFamily);
		clat.setTotalIncomeTax(sTax);
		clat.setTotalIncomeTaxCLAT(cTax);

		clat.setTaxableCLATValue(clatTool.getNonCharitableInterest());
		clat.setEstateTaxCLAT(clatFederalEstateTax);

		double assetValues[][] = new double[cnt][2];
		for (int i = 0; i < cnt; i++)
			assetValues[i] = tempAssetVal[i];

		String assetDesc[] = new String[cnt];
		for (int i = 0; i < cnt; i++)
			assetDesc[i] = tempListOfAssets[i];

		double securitiesRentalIncome[][] = { { cSecuritiesValue,
				fSecuritiesValue } };
		clat.setMainTable(stdTable);
		clat.setSecuritiesRentalIncome(securitiesRentalIncome);
		clat.setClatAssetList(assetDesc);
		clat.setClatAssetValues(assetValues);
		clat.setClatTerm(clatTool.getTerm());

		clat.setAnnuity(clatTool.getAnnuity());
		clat.setAnnuityIncrease(clatTool.getAnnuityIncrease());
		
		clat.setSecGrowthRate(clatTool.getGrowth());
		clat.setDiscount(clatTool.getDiscountedValue());
		clat.setGrowthTerm(clatTool.getFinalDeath());

		String gFlag = clatTool.getGrantorFlag();
		if(gFlag.equalsIgnoreCase("Y")){
			clat.setGrantor(true);
		}
		
		if(clatTool.getAnnuityIncrease()> 0){
			clat.setEscalating(true);
		}
		
		clat.setClatType(clatTool.getClatType());	// Term, Life, or Shorter
		
		clat.draw();
		pageNum += clat.getPageCount();
		newPage();
	}
	
	public void genCrummyWMulti(String client) {
		CrummeyTool tool = (CrummeyTool) session.getAttribute("crum");

		if (tool.isWithMgt()) {
			CrummeyWMultiPages c = new CrummeyWMultiPages(document, writer);
			c.setUserInfo(getUserInfo());
			c.setClient(client);
			c.setClientFirstName(userInfo.getClientFirstName());
			c.setSpouseFirstName(userInfo.getSpouseFirstName());
			c.setPageNum(pageNum);
			c.setAnnualGifts(tool.getAnnualGift());
			c.setPremium(tool.getLifePremium());
			c.setLifeInsurance(tool.getLifeDeathBenefit());

			c.draw();
			pageNum += c.getPageCount();
			newPage();
		} else {
			CrummeyPages c = new CrummeyPages(document, writer);
			c.setUserInfo(getUserInfo());
			c.setClient(client);
			c.setClientFirstName(userInfo.getClientFirstName());
			c.setSpouseFirstName(userInfo.getSpouseFirstName());
			c.setPageNum(pageNum);
			c.setAnnualGifts(tool.getAnnualGift());
			c.setPremium(tool.getLifePremium());
			c.setLifeInsurance(tool.getLifeDeathBenefit());

			c.draw();
			pageNum += c.getPageCount();
			newPage();
		}
		useCrum = true;
	}

	public void genCRUT(){

		CrtTool tool = (CrtTool) session.getAttribute("crt");
		tool.calculate();

		double trustNetSpendable = 0;
		double portNetSpendable = 0;
		double totalDeduction = 0;
		double[][] oTable = tool.getOutRightSale();
		double[][] wTable = tool.getCrtTable();

		int maxComp = oTable.length-1;

		for (int i = 0; i <= maxComp; i++) {
			trustNetSpendable += wTable[i][4];
			portNetSpendable += oTable[i][2];
			totalDeduction += wTable[i][1];
		}

		double portBalance = oTable[maxComp][3];
		double portEstateTax = portBalance * tool.getEstateTaxRate();
		double portNetToFamily = portBalance - portEstateTax;
		double annualIncomeYr1 = ((int) (oTable[1][2]));
		double annualIncomeYrN = ((int) (oTable[maxComp][2]));

		double annualCRUTIncomeYr1 = ((int) (wTable[1][4]));
		double annualCRUTIncomeYrN = ((int) (wTable[maxComp][4]));

		double trustBalance = wTable[maxComp][5];
		double netToCharity = trustBalance;

		// Taxes Saved
		double capGainesTax = (tool.getAssetValue() - tool.getAssetBasis())
				* tool.getCapitalGainsRate();
		double incomeTaxSaved = 0;

		for (int i = 0; i < 6; i++) {
			incomeTaxSaved += wTable[i][1];
		}

		incomeTaxSaved *= tool.getTaxRate();

		double totalTaxSaved = capGainesTax + incomeTaxSaved + portEstateTax;

		CRUTPages crut = new CRUTPages(document, writer);
		crut.setUserInfo(getUserInfo());
		crut.setPageNum(pageNum);

		crut.setCLe(userInfo.getClientLifeExpectancy());
		crut.setSLe(userInfo.getSpouseLifeExpectancy());

		crut.setOTable(oTable);
		crut.setWTable(wTable);
		crut.setInsurancePremium(tool.getLifePremium());
		crut.setAssetValue(tool.getAssetValue());
		crut.setLiability(tool.getAssetLiability());
		crut.setBasis(tool.getAssetBasis());
		crut.setMarginalIcomeTaxRate(tool.getTaxRate());
		crut.setEstateTaxPercent(tool.getEstateTaxRate());
		crut.setCapitalGainsPercent(tool.getCapitalGainsRate());
		crut.setPreTaxROIPercent(tool.getInvestmentReturn());
		crut.setSpendingRate(tool.getPayoutRate());
		crut.setCapitalGainsTax((tool.getAssetValue() - tool.getAssetBasis())
				* tool.getCapitalGainsRate());
		crut.setSec7520Rate(tool.getIrsRate());
		crut.setCharitableDeductionFactor(tool.getCharitableDeductionFactor());
		crut.setCharitableDeduction(tool.getCharitableDeduction());
		crut.setAdjustedGI(tool.getAdjustedGrossIncome());
		crut.setDeductionLimitRate(.30);
		crut.setInsurance(tool.getLifeDeathBenefit());

		crut.setPortfolioBalance(portBalance);
		crut.setEstateTaxPort(portEstateTax);
		crut.setNetToFamilyAfterTax(portNetToFamily);
		crut.setTrustBalance(trustBalance);
		crut.setBenefitToCharityCRUT(netToCharity);
		crut.setTotalPremiumsGifted(tool.getLifePremium() * maxComp);
		crut.setExtraNetSpendable(Math
				.abs(trustNetSpendable - portNetSpendable));
		crut.setCapitalGainsSaved(capGainesTax);
		crut.setIncomeTaxSaved(incomeTaxSaved);
		crut.setEstateTaxSaved(portEstateTax);
		crut.setTotalTaxesSaved(totalTaxSaved);

		crut.setAssetName("The asset");
		crut.setLifeExpectancy(tool.getCrtTable().length-1);

		// Problem
		// assetValue
		// basis
		crut.setTaxableGain(crut.getAssetValue() - crut.getBasis());
		// capital gains
		crut.setIncomeLossPerYear(crut.getCapitalGainsTax()
				* crut.getPreTaxROIPercent());
		crut.setIncomeLossProjected(crut.getIncomeLossPerYear()
				* crut.getLifeExpectancy());
		crut.setNetProceeds(crut.getAssetValue() - crut.getCapitalGainsTax());
		crut.setEstateTax(crut.getNetProceeds() * crut.getEstateTaxPercent());

		crut.setNetToFamily(crut.getNetProceeds() - crut.getEstateTax());
		crut.setTotalTaxes(crut.getCapitalGainsTax() + crut.getEstateTax());
		crut.setPercentageLost(crut.getTotalTaxes() / crut.getAssetValue());

		// Comparison
		// capitalGains
		crut.setCapitalGainsTaxCRUT(0);
		crut.setTaxDeduction(0);
		crut.setTaxDeductionCRUT(totalDeduction);
		crut.setAnnualIncomeYr1(annualIncomeYr1);
		crut.setAnnualIncomeYr1CRUT(annualCRUTIncomeYr1);
		crut.setAnnualIncomeYrn(annualIncomeYrN);
		crut.setAnnualIncomeYrnCRUT(annualCRUTIncomeYrN);
		crut.setCumulativeNetIncome(portNetSpendable);
		crut.setCumulativeNetIncomeCRUT(trustNetSpendable);
		crut.setTaxSavedDeduction(0);
		double tsd = ((tool.getPayoutIncome() / tool.getPayoutRate()) * tool
				.getTaxRate())
				+ ((tool.getPayoutGrowth() / tool.getTaxRate()) * tool
						.getCapitalGainsRate());
		tsd = (int) ((totalDeduction * tsd) / 1000) * 1000;
		crut.setTaxSavedDeductionCRUT(tsd);
		crut.setLifeIncomePlusTaxSavings(portNetSpendable);
		crut.setLifeIncomePlusTaxSavingsCRUT(trustNetSpendable);

		crut.setNetToFamilyAfterTaxCRUT(tool.getLifeDeathBenefit());
		crut.setBenefitToCharity(0);
		crut.setBenefitToCharityCRUT(trustBalance);
		crut.setTotal(crut.getCumulativeNetIncome()
				+ crut.getNetToFamilyAfterTax());
		crut.setTotalCRUT(crut.getLifeIncomePlusTaxSavingsCRUT()
				+ crut.getNetToFamilyAfterTaxCRUT()
				+ crut.getBenefitToCharityCRUT());
		crut.setDifference(crut.getTotalCRUT() - crut.getTotal());

		crut.setRateOfIncome(.02);
		crut.setRealizedCapitalGain(crut.getSpendingRate()
				- crut.getRateOfIncome());

		crut.setCalculatedMITR(((crut.getRateOfIncome() / crut
						.getSpendingRate()) * crut.getMarginalIcomeTaxRate())
						+ ((crut.getRealizedCapitalGain() / crut
								.getSpendingRate()) * crut
								.getCapitalGainsPercent()));

		crut.draw();
		pageNum += crut.getPageCount();
		newPage();
	}

	public void genERP(String client) {
		CharliePlanTool cp = (CharliePlanTool) session.getAttribute("cplan");
		cp.calculate();
		EnhanceRetirementPlan erp = new EnhanceRetirementPlan(document,writer);
		erp.setErp(cp);
		erp.setUserInfo(userInfo);
		erp.setClient(client);
		erp.draw();
		pageNum += erp.getPageCount();
		newPage();
	}

	public void genEstateDistribution1(String client) {
		EstateDistribution1 ed1 = new EstateDistribution1(document, writer);
		ed1.setUserInfo(getUserInfo());
		ed1.setClient(client);
		ed1.setPageNum(pageNum);
		ed1.draw();
		pageNum += ed1.getPageCount();
		newPage();
	}

	public void genEstatePlan(String client) {
		EstatePlan ep = new EstatePlan(document, writer);
		ep.setUserInfo(getUserInfo());
		ep.setEpg(this.epg);
		ep.setClient(client);
		ep.setPageNum(pageNum);
		ep.setClientID(this.clientID);
		ep.setSpouseID(this.spouseID);
		ep.setClientFirstName(userInfo.getClientFirstName());
		ep.setSpouseFirstName(userInfo.getSpouseFirstName());
		ep.draw();
		pageNum += ep.getPageCount();
		newPage();
	}

	
	
	public void genFCF(String client) {
		FCFTool tool = (FCFTool) session.getAttribute("fcf");
		
		FCFPages fcf = new FCFPages(document, writer);
		fcf.setUserInfo(getUserInfo());
		fcf.setClient(client);
		fcf.setClientFirstName(userInfo.getClientFirstName());
		fcf.setSpouseFirstName(userInfo.getSpouseFirstName());
		fcf.setPageNum(pageNum);
		fcf.setFcfType(tool.getType());
		fcf.draw();
		pageNum += fcf.getPageCount();
		newPage();
	}

	public void genFinalGraph(String client, CashFlowTable cft,
			EstatePlanTable ept) {
		FinalGraph cfg = new FinalGraph(document, writer);
		cfg.setUserInfo(getUserInfo());
		cfg.setCashFlows(cft);
		cfg.setEstatePlanTable(ept);
		cfg.setClient(client);
		cfg.setClientFirstName(userInfo.getClientFirstName());
		cfg.setSpouseFirstName(userInfo.getSpouseFirstName());
		cfg.setPageNum(pageNum);
		cfg.draw();
		pageNum += cfg.getPageCount();
		newPage();
	}
	
	public void genFLP() {
		FLPPages flp = new FLPPages(document, writer);
		flp.setUserInfo(getUserInfo());
		flp.setPageNum(pageNum);
		flp.draw();
		newPage();
		pageNum += flp.getPageCount();
	}

	public void genGRAT(String client) {
		GratTool tool = (GratTool) session.getAttribute("grat");

		GRATPages grat = new GRATPages(document, writer);
		grat.setUserInfo(getUserInfo());
		grat.setUseLLC(isUseLLC());

		tool.calculate();
		GratTrust cGrat = tool.getClientTrust();
		GratTrust sGrat = null;

		if (tool.getNumTrusts() > 1) {
			sGrat = tool.getSpouseTrust();
		}

		tool.buildSCTable();
		tool.buildCFTable();

		int years = (int) (cGrat.getTerm() - 1);
		double premium = tool.getLifeInsPremium();
		double cashValue = tool.getLifeCashValue();
		double lifeBenefit = tool.getLifeDeathBenefit();
		double gratValue = tool.getCFTotalValue(years);
		double protection = premium * years - cashValue;
		double taxSavings = protection / lifeBenefit;

		grat.setUseInsurance(tool.isUseLife());
		grat.setYears(years);
		grat.setPremium(premium);
		grat.setCashValue(cashValue);
		grat.setLifeBenefit(lifeBenefit);
		grat.setGratValue(gratValue);
		grat.setProtection(protection);
		grat.setTaxSavings(taxSavings);

		grat.setRealEstate(tool.getAssetNames());

		grat.setMarketValue(tool.getTotalValue());
		grat.setYrsGrowth(tool.getClientLifeExp());
		grat.setGrowthRate(tool.getWeightedGrowth());
		grat.setIncomeRate(tool.getWeightedIncome());
		tool.calculate();
		grat.setTotalValue(tool.getSCTable(tool.getFinalDeath())); // Go out to
																	// 25
		grat.setEstateTaxRate(tool.getEstateTaxRate());
		grat.setEstateTax(grat.getTotalValue() * grat.getEstateTaxRate());
		grat.setNetToFamily(grat.getTotalValue() - grat.getEstateTax());
		grat.setYrsGrowth(tool.getFinalDeath());

		// final page
		if (tool.getNumTrusts() > 1) {
			sGrat = tool.getSpouseTrust();
		}

		tool.buildSCTable();
		tool.buildCFTable();

		double estateTotalValue = tool.getSCTable(tool.getFinalDeath());

		double gratToFamily = tool.getCFTotalValue(tool.getFinalDeath());
		double gratTaxableGifts = cGrat.getTaxableGift();
		if (tool.getNumTrusts() > 1) {
			gratTaxableGifts += sGrat.getTaxableGift();
		}
		double gratEstateTax = gratTaxableGifts * tool.getEstateTaxRate();
		double gratTotalValue = gratToFamily + gratEstateTax;

		grat.setTotalGRATValue(gratTotalValue);
		grat.setTotalValue(estateTotalValue);
		grat.setTaxableGRATValue(gratTaxableGifts);
		grat.setNetToFamilyGRAT(gratToFamily);
		grat.setEstateTaxGRAT(gratEstateTax);
		grat.setTrusts(tool.getNumTrusts());

		grat.setPageNum(pageNum);
		grat.setClient(client);
		grat.setClientFirstName(userInfo.getClientFirstName());
		grat.setSpouseFirstName(userInfo.getSpouseFirstName());

		grat.draw();
		pageNum += grat.getPageCount();
		newPage();
	}

	public void genIDIT(String client) {
		IditTool iTool = (IditTool) session.getAttribute("idit");

		IDITPages idit = new IDITPages(document, writer);
		idit.setUserInfo(getUserInfo());
		
		idit.setClient(client);
		idit.setPageNum(pageNum);
		idit.setUseLLC(useLLC);

		iTool.buildBeforeTable();
		iTool.calculate();

		idit.before = iTool.getStdTable();
		idit.after = iTool.getIditTable();
		idit.setUseInsurance(iTool.isUseInsurance());
		idit.setAssets(iTool.getAssetNames());
		idit.setMarketValue(idit.before[0][2]+ idit.before[0][3]);
		idit.setYrsGrowth(25);
		idit.setAvgGrowth(iTool.getRGrowth());
		idit.setAvgIncome(iTool.getRIncome());
		idit.setTotalValueProjected(idit.before[24][7]);
		idit.setEstateTaxRate(idit.before[24][9]);

		idit.setNetPassingToFamily(idit.getTotalValueProjected()
				- (idit.getTotalValueProjected() * idit.getEstateTaxRate()));

		double[][] iditTable = iTool.getIditTable();
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		int currentYear = cal.get(Calendar.YEAR);

		int years = (int) iTool.getGratTerm() - 1;
		double fet = Utilities.getFetMaxRate(currentYear + years);
		double premium = iTool.getLifePremium();
		double cashValue = iTool.getLifeCashValue();
		double lifeBenefit = iTool.getLifeDeathBenefit();
		double gratValue = iditTable[years][2] + iditTable[years][4];
		double protection = premium * years - cashValue;
		double taxSavings = protection / lifeBenefit;

		idit.setFet(fet);
		idit.setPremium(premium);
		idit.setLifeBenefit(lifeBenefit);
		idit.setGratValue(gratValue);
		idit.setProtection(protection);
		idit.setTxSavings(taxSavings);
		idit.setYears(years);
		idit.setCashValue(cashValue);

		idit.draw();

		pageNum += idit.getPageCount();
		newPage();
	}

	public void genIntroduction() {
		Introduction intro = new Introduction(document, writer);
		intro.setUserInfo(getUserInfo());
		intro.setPageNum(pageNum);
		intro.draw();
		pageNum += intro.getPageCount();
		newPage();
	}

	public void genLap() {
		LiquidAssetProtectionTool lap = (LiquidAssetProtectionTool) session.getAttribute("lap");
		lap.calculate();
		LAPPages lp = new LAPPages(document, writer);
		lp.setUserInfo(userInfo);
		lp.setAnnualIncome(lap.getAmount() * lap.getIncome() );
		lp.setIncomeTax(-(lap.getAmount() * lap.getIncome()) * lap.getIncomeTaxRate());
		lp.setNetIncome((lap.getAmount()*lap.getIncome()) -(lap.getAmount() * lap.getIncome()) * lap.getIncomeTaxRate());
		lp.setNetSpendable(lp.getNetIncome());
		
		lp.setLapIncome(lap.getAnnualAnnuity());
		lp.setLapTax(lap.getTaxDue());
		lp.setLapNetIncome(lap.getAnnualAnnuity() + lap.getTaxDue());
		lp.setLapInsPremium(-lap.getLifePremium());
		lp.setLapNetSpendable(lap.getAnnualAnnuity() + lap.getTaxDue() - lap.getLifePremium());
		lp.setLapRatio(lp.getLapNetSpendable()/lp.getNetSpendable());
		
		lp.setMarketValue(lap.getAmount());
		lp.setMarketIncome(lap.getIncome());
		lp.setAnnualIncomeRate(lap.getIncome());
		lp.setEstateTaxRate(lap.getEstateTaxRate());
		lp.setLifeFaceValue(lap.getLifeFaceValue());
		lp.setPageNum(pageNum);
		lp.draw();
		pageNum += lp.getPageCount();
		newPage();
	}

	public void genLife() {
		if(useCrum)
			return;
		LifePages c = new LifePages(document, writer);
		c.setUserInfo(getUserInfo());
		c.setPageNum(pageNum);
		c.draw();
		pageNum += c.getPageCount();
		newPage();
	}

	public void genLLC() {
		LLCPages lp = new LLCPages(document, writer);
		lp.setUserInfo(getUserInfo());
		lp.setPageNum(pageNum);
		lp.draw();
		newPage();
		pageNum += lp.getPageCount();
	}

	public void genMultiGen(String client) {
		MGTrustTool mgTool = (MGTrustTool) session.getAttribute("mgen");

		mgTool.calculate();
		DecimalFormat percent = new DecimalFormat("#.##%");
		DecimalFormat number = new DecimalFormat("###.#");
		double[][] stateTrust = mgTool.getCurrentStateTrust();
		double[][] keepIn = mgTool.getKeepInEstate();
		double[][] delawareTrust = mgTool.getDelawareTrust();

		MultiGenPages mgp = new MultiGenPages(document, writer);
		mgp.setUserInfo(getUserInfo());
		mgp.setClient(client);
		mgp.setPageNum(pageNum);
		mgp.setClientFirstName(userInfo.getClientFirstName());
		mgp.setSpouseFirstName(userInfo.getSpouseFirstName());

		int iYears = mgTool.getLifeExpectancy();

		mgp.setAnnualDelGrowth(.0271);
		mgp.setAnnualStateGrowth(.0253);
		mgp.setCLe(userInfo.getClientLifeExpectancy());
		mgp.setSLe(userInfo.getSpouseLifeExpectancy());
		mgp.setFetRate(mgTool.getEstateTax(2005 + 25));
		mgp.setFmv(stateTrust[0][0]);
		mgp.setGrowthRate(mgTool.getGrowth());
		mgp.setIncomeRate(mgTool.getIncome());
		mgp.setInflationRate(mgTool.getInflationRate());
		mgp.setPayoutRate(mgTool.getPayoutRate());
		mgp.setState(mgTool.getTrustState());
		mgp.setYearsPerGen(mgTool.getYearsPerGeneration());
		mgp.setTrusteeFee(mgTool.getTrusteeRate());
		mgp.setMgenState(mgTool.getMgenTrustState());

		String desc[];
		
		if( userInfo.isSingle()) {
			desc = new String[] {
						userInfo.getClientFirstName()
							+ " transfers Marketable Securities to a Family Limited Partnership (FLP) or a Limited Liability Corporation (LLC).",
					"LP/MI interests are then gifted to the Multigenerational Trust, utilizing Generation-Skipping Tax Exemptions. The FLP/LLC interests could receive discounts for a lack of marketability and a lack of control.",
					"Assets can be protected for family from divorced spouses, creditors, and estate taxes.",
					"Earnings of the Multigenerational Trust can benefit future posterity according to the financial philosophy described by "
							+ userInfo.getClientFirstName()
							+ " in the trust instrument.",
					"Upon the deaths of children, grandchildren, etc., assets remaining in the trust can avoid estate taxes as long as the trust is allowed to continue. "
							+ "This length of time is based upon the laws of each state, called the \"Rule against perpetuities\". In many states, this trust could last as long as 100 years or so,"
							+ " whereas in others such as South Dakota, Delaware,or Alaska it could last indefinitely.",
					"The Multigenerational Trust can also use Life Insurance and other assets to leverage, hedge and add to the gifted capital. (Please refer to Recommendations for details.)", };
			
		} else {
			desc = new String[] {
					userInfo.getClientFirstName()
							+ " and "
							+ userInfo.getSpouseFirstName()
							+ " transfer Marketable Securities to a Family Limited Partnership (FLP) or a Limited Liability Corporation (LLC).",
					"LP/MI interests are then gifted to the Multigenerational Trust, utilizing Generation-Skipping Tax Exemptions. The FLP/LLC interests could receive discounts for a lack of marketability and a lack of control.",
					"Assets can be protected for family from divorced spouses, creditors, and estate taxes.",
					"Earnings of the Multigenerational Trust can benefit future posterity according to the financial philosophy described by "
							+ userInfo.getClientFirstName()
							+ " and "
							+ userInfo.getSpouseFirstName()
							+ " in the trust instrument.",
					"Upon the deaths of children, grandchildren, etc., assets remaining in the trust can avoid estate taxes as long as the trust is allowed to continue. "
							+ "This length of time is based upon the laws of each state, called the \"Rule against perpetuities\". In many states, this trust could last as long as 100 years or so,"
							+ " whereas in others such as South Dekota, Delaware, or Alaska it could last indefinitely.",
					"The Multigenerational Trust can also use Life Insurance and other assets to leverage, hedge and add to the gifted capital, as well as to save income taxes. (Please refer to Recommendations for details.)", };
			}
		mgp.setClientMultiGenDesc(desc);

		// Here we set up generations summary

		mgp.startGenerationTable("First Generation (" + iYears + " years)");
		mgp.addRowToGenerationTable("Starting Value", keepIn[0][0],
				stateTrust[0][0], delawareTrust[0][0], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Net Growth of Assets", keepIn[0][1]
				- keepIn[0][0], stateTrust[0][1] - stateTrust[0][0],
				delawareTrust[0][1] - delawareTrust[0][0], "border=l+r+b", 0,
				"");
		mgp.addRowToGenerationTable("Value At Death", keepIn[0][1],
				stateTrust[0][1], delawareTrust[0][1], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Less Estate Tax", keepIn[0][2], 0, 0,
				"border=l+r+b", mgp.makeColor(192, 0, 0), "");
		mgp.addRowToGenerationTable("Total Benefit to family", keepIn[0][1]
				- keepIn[0][2], stateTrust[0][1] - stateTrust[0][2],
				delawareTrust[0][1] - delawareTrust[0][2], "border=l+r+b", 0,
				"bold=1");
		mgp.addRowToGenerationTable(
				"Present Value of Amount to family (3% Infl.)",
				mgTool.calculateInflation(keepIn[0][1] - keepIn[0][2], iYears),
				mgTool.calculateInflation(stateTrust[0][1] - stateTrust[0][2],
						iYears), mgTool.calculateInflation(delawareTrust[0][1]
						- delawareTrust[0][2], iYears), "border=l+r+b", mgp
						.makeColor(0, 128, 0), "");
		mgp.endGenerationTable();

		iYears += mgTool.getYearsPerGeneration();
		mgp.startGenerationTable("Second Generation ("
				+ mgTool.getYearsPerGeneration() + " years)");
		mgp.addRowToGenerationTable("Starting Value", keepIn[1][0],
				stateTrust[1][0], delawareTrust[1][0], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Average Annual Income "
				+ percent.format(mgTool.getPayoutRate()) + " per year",
				keepIn[1][3], stateTrust[1][3], delawareTrust[1][3],
				"border=l+r+b", 0, "");
		mgp.addRowToGenerationTable("PV of Average Income ("
				+ percent.format(mgTool.getInflationRate()) + " Infl)", mgTool
				.calculateInflation(keepIn[1][3], iYears), mgTool
				.calculateInflation(stateTrust[1][3], iYears), mgTool
				.calculateInflation(delawareTrust[1][3], iYears),
				"border=l+r+b", mgp.makeColor(0, 128, 0), "");
		mgp.addRowToGenerationTable("Net Growth of Assets", keepIn[1][1]
				- keepIn[1][0], stateTrust[1][1] - stateTrust[1][0],
				delawareTrust[1][1] - delawareTrust[1][0], "border=l+r+b", 0,
				"");
		mgp.addRowToGenerationTable("Value At Death", keepIn[1][1],
				stateTrust[1][1], delawareTrust[1][1], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Less Estate Tax", keepIn[1][2], 0, 0,
				"border=l+r+b", mgp.makeColor(192, 0, 0), "");
		mgp.addRowToGenerationTable("Total Benefit to family", keepIn[1][1]
				- keepIn[1][2], stateTrust[1][1] - stateTrust[1][2],
				delawareTrust[1][1] - delawareTrust[1][2], "border=l+r+b", 0,
				"bold=1");
		mgp.addRowToGenerationTable(
				"Present Value of Amount to family (3% Infl.)",
				mgTool.calculateInflation(keepIn[1][1] - keepIn[1][2], iYears),
				mgTool.calculateInflation(stateTrust[1][1] - stateTrust[1][2],
						iYears), mgTool.calculateInflation(delawareTrust[1][1]
						- delawareTrust[1][2], iYears), "border=l+r+b", mgp
						.makeColor(0, 128, 0), "");
		mgp.endGenerationTable();

		iYears += mgTool.getYearsPerGeneration();
		mgp.startGenerationTable("Third Generation ("
				+ mgTool.getYearsPerGeneration() + " years)");
		mgp.addRowToGenerationTable("Starting Value", keepIn[2][0],
				stateTrust[2][0], delawareTrust[2][0], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Average Annual Income "
				+ percent.format(mgTool.getPayoutRate()) + " per year",
				keepIn[2][3], stateTrust[2][3], delawareTrust[2][3],
				"border=l+r+b", 0, "");
		mgp.addRowToGenerationTable("PV of Average Income ("
				+ percent.format(mgTool.getInflationRate()) + " Infl)", mgTool
				.calculateInflation(keepIn[2][3], iYears), mgTool
				.calculateInflation(stateTrust[2][3], iYears), mgTool
				.calculateInflation(delawareTrust[2][3], iYears),
				"border=l+r+b", mgp.makeColor(0, 128, 0), "");
		mgp.addRowToGenerationTable("Net Growth of Assets", keepIn[2][1]
				- keepIn[2][0], stateTrust[2][1] - stateTrust[2][0],
				delawareTrust[2][1] - delawareTrust[2][0], "border=l+r+b", 0,
				"");
		mgp.addRowToGenerationTable("Value At Death", keepIn[2][1],
				stateTrust[2][1], delawareTrust[2][1], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Less Estate Tax", keepIn[2][2], 0, 0,
				"border=l+r+b", mgp.makeColor(192, 0, 0), "");
		mgp.addRowToGenerationTable("Total Benefit to family", keepIn[2][1]
				- keepIn[2][2], stateTrust[2][1], delawareTrust[2][1],
				"border=l+r+b", 0, "bold=1");
		mgp.addRowToGenerationTable(
				"Present Value of Amount to family (3% Infl.)",
				mgTool.calculateInflation(keepIn[2][1] - keepIn[2][2], iYears),
				mgTool.calculateInflation(stateTrust[2][1] - stateTrust[2][2],
						iYears), mgTool.calculateInflation(delawareTrust[2][1]
						- delawareTrust[2][2], iYears), "border=l+r+b", mgp
						.makeColor(0, 128, 0), "");
		mgp.endGenerationTable();

		iYears += mgTool.getYearsPerGeneration();
		mgp.startGenerationTable("Fourth Generation ("
				+ mgTool.getYearsPerGeneration() + " years)");
		mgp.addRowToGenerationTable("Starting Value", keepIn[3][0],
				stateTrust[3][0], delawareTrust[3][0], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Average Annual Income "
				+ percent.format(mgTool.getPayoutRate()) + " per year",
				keepIn[3][3], stateTrust[3][3], delawareTrust[3][3],
				"border=l+r+b", 0, "");
		mgp.addRowToGenerationTable("PV of Average Income ("
				+ percent.format(mgTool.getInflationRate()) + " Infl)", mgTool
				.calculateInflation(keepIn[3][3], iYears), mgTool
				.calculateInflation(stateTrust[3][3], iYears), mgTool
				.calculateInflation(delawareTrust[3][3], iYears),
				"border=l+r+b", mgp.makeColor(0, 128, 0), "");
		mgp.addRowToGenerationTable("Net Growth of Assets", keepIn[3][1]
				- keepIn[3][0], stateTrust[3][1] - stateTrust[3][0],
				delawareTrust[3][1] - delawareTrust[3][0], "border=l+r+b", 0,
				"");
		mgp.addRowToGenerationTable("Value At Death", keepIn[3][1],
				stateTrust[3][1], delawareTrust[3][1], "border=l+r", 0, "");
		mgp.addRowToGenerationTable("Less Estate Tax", keepIn[3][2],
				stateTrust[3][2], 0, "border=l+r+b", mgp.makeColor(192, 0, 0),
				"");
		mgp.addRowToGenerationTable("Total Benefit to family", keepIn[3][1]
				- keepIn[3][2], stateTrust[3][1] - stateTrust[3][2],
				delawareTrust[3][1], "border=l+r+b", 0, "bold=1");
		mgp.addRowToGenerationTable(
				"Present Value of Amount to family (3% Infl.)",
				mgTool.calculateInflation(keepIn[3][1] - keepIn[3][2], iYears),
				mgTool.calculateInflation(stateTrust[3][1] - stateTrust[3][2],
						iYears), mgTool.calculateInflation(delawareTrust[3][1]
						- delawareTrust[3][2], iYears), "border=l+r+b", mgp
						.makeColor(0, 128, 0), "");
		mgp.endGenerationTable();

		double s1 = keepIn[3][1] - keepIn[3][2];
		double s2 = (stateTrust[3][1] - stateTrust[3][2]);
		double s3 = (delawareTrust[3][1] - delawareTrust[3][2]);
		mgp.startGenerationTable("COMPARISON OF PRINCIPAL ACCOUNTS");
		mgp
				.addRowToGenerationTable("Unadjusted Balance", s1, s2, s3, "",
						0, "");
		mgp.addRowToGenerationTable("Inflation Adjusted", mgTool
				.calculateInflation(s1, iYears), mgTool.calculateInflation(s2,
				iYears), mgTool.calculateInflation(s3, iYears), "", 0, "");
		mgp.addFinalRowToGenerationTable(
				"Effectiveness Index (compared to Sc1)", "1", number.format(s2
						/ s1)
						+ " : 1", number.format(s3 / s1) + " : 1", "", 0,
				"bold=1");
		mgp.endGenerationTable();

		mgp.setOption1(s1 / 1000.0);
		mgp.setOption2(s2 / 1000.0);
		mgp.setOption3(s3 / 1000.0);

		mgp.draw();
		pageNum += mgp.getPageCount();
		newPage();
	}

	public void genNetWorth(String client) {
		NetWorth nw = new NetWorth(document, writer);
		nw.setUserInfo(getUserInfo());
		nw.setClient(client);
		nw.setPageNum(pageNum);
		nw.setClientID(this.clientID);
		nw.draw();
		pageNum += nw.getPageCount();
		newPage();
	}

	public void genObjectives(String client) {

		Objectives obj = new Objectives(document, writer);
		obj.setUserInfo(getUserInfo());

		String sql = "select * from OBJECTIVES where OWNER_ID='"
				+ this.clientID + "'";
		String sqlCount = "select count(*) as TOTAL from OBJECTIVES where OWNER_ID='"
				+ this.clientID + "'";
		HashMap<String,Object> rec;

		dbObj.start();
		rec = dbObj.execute(sqlCount);
		dbObj.stop();
		int i = ((Number) (rec.get("TOTAL"))).intValue();
		if (i > 0) {
			String objs[] = new String[i];
			obj.setPageNum(pageNum);
			obj.setClient(client);

			dbObj.start();
			rec = dbObj.execute(sql);

			for (i = 0; rec != null; i++) {
				objs[i] = (String) rec.get("OBJECTIVE");
				rec = dbObj.next();
			}
			dbObj.stop();
			obj.setObjectives(objs);
			obj.draw();
			pageNum += obj.getPageCount();
			newPage();
		}

	}

	public void genObservations(String client) {

		Observations obj = new Observations(document, writer);
		obj.setUserInfo(getUserInfo());

		String sql = "select * from OBSERVATIONS where OWNER_ID='"
				+ this.clientID + "'";
		String sqlCount = "select count(*) as TOTAL from OBSERVATIONS where OWNER_ID='"
				+ this.clientID + "'";
		HashMap<String,Object> rec;

		dbObj.start();
		rec = dbObj.execute(sqlCount);
		dbObj.stop();
		int i = ((Number) (rec.get("TOTAL"))).intValue();
		if (i > 0) {
			String objs[] = new String[i];

			obj.setPageNum(pageNum);
			obj.setClient(client);

			dbObj.start();
			rec = dbObj.execute(sql);

			for (i = 0; rec != null; i++) {
				objs[i] = (String) rec.get("OBSERVATION");
				rec = dbObj.next();
			}
			dbObj.stop();
			obj.setObservations(objs);
			obj.draw();
			newPage();
		}
	}

	public void genPat() {
		PrivateAnnuityTool pat = (PrivateAnnuityTool) session.getAttribute("pat");
		pat.setCAge(userInfo.getClientAge());
		pat.setSAge(userInfo.getSpouseAge());
		pat.calculate();
		
		PrivateAnnuityPage pap = new PrivateAnnuityPage(document, writer);
		pap.setLe(userInfo.getClientLifeExpectancy());
		pap.setClientAge(userInfo.getClientAge());
		pap.setAmount(pat.getAmount());
		pap.setBasis(pat.getBasis());
		pap.setEstateTaxRate(pat.getEstateTaxRate());
		pap.setUserInfo(userInfo);
		pap.setAfrRate(pat.getAfrRate());
		pap.setAfrDate(pat.getAfrDate());
		pap.setAnnuityFactor(pat.getAmount()/ pat.getAnnuityPayment());
		pap.setAnnuity(pat.getAnnuityPayment());
		pap.setAnnuityFreq(pat.getAnnuityFreq());
		pap.setAnnuityIncrease(pat.getAnnuityIncrease());
		pap.setAnnuityType(pat.getAnnuityType());
		pap.setIncomeTaxRate(pat.getIncomeTaxRate());
		pap.setCapitalGainsRate(pat.getCapitalGainsRate());
		pap.setOrdinaryIncome(pat.getOrdinaryIncome());
		pap.setTaxFreeAmount(pat.getTaxFreeAmount());
		pap.setCapGains(pat.getCapGains());
		pap.setPageNum(pageNum);
		pap.draw();
		pageNum += pap.getPageCount();
		newPage();
	}

	public void genPersonalData(String client) {
		LocationBean lb = new LocationBean();
		lb.setDbObject();
		PersonBean pb = new PersonBean();
		pb.setDbObject();
		pb.setId(clientID);
		pb.initialize();
		
		PersonBean sb = new PersonBean();
		sb.setDbObject();
		if( spouseID == 0){
			isSingle = true;
		} else {
			sb.setId(this.spouseID);
			sb.initialize();
		}

		Marriages marriages = new Marriages();
		marriages.setPrimaryId(this.clientID);
		marriages.initialize();

		Children children = new Children();
		children.setPrimaryId(clientID);

		String home = "";
		String work = "";
		String other = "";
		String email = "";

		String sql = "select LOCATION_ID from LOCATION where OWNER_ID='"
				+ clientID + "';";

		dbObj.start();
		HashMap<String,Object> rec = dbObj.execute(sql);
		while (rec != null) {
			lb.setId((((Number) (rec.get("LOCATION_ID"))).longValue()));
			lb.initialize();
			String addr2 = "";
			String FAX = "";

			if (lb.getAddress2().length() > 0) {
				addr2 = lb.getAddress2() + "\n";
			}

			if (lb.getFax().length() > 0) {
				FAX = "\nFax " + lb.getFax() + "\n";
			}

			if (lb.getType().equalsIgnoreCase("p")) {
				home = lb.getName() + "\n" + lb.getAddress1() + "\n" + addr2
						+ lb.getCity() + ", " + lb.getState() + " "
						+ lb.getZip() + "\n" + lb.getPhone() + FAX;
			} else if (lb.getType().equalsIgnoreCase("s")) {
				other = lb.getName() + "\n" + lb.getAddress1() + "\n" + addr2
						+ lb.getCity() + ", " + lb.getState() + " "
						+ lb.getZip() + "\n" + lb.getPhone() + FAX;
			} else if (lb.getType().equalsIgnoreCase("b")) {
				work = lb.getName() + "\n" + lb.getAddress1() + "\n" + addr2
						+ lb.getCity() + ", " + lb.getState() + " "
						+ lb.getZip() + "\n" + lb.getPhone() + FAX;
			}
			rec = dbObj.next();
		}
		dbObj.stop();

		PersonalData pd = new PersonalData(document, writer);
		pd.setUserInfo(getUserInfo());
		pd.setClient(client);
		pd.setPageNum(pageNum);

		int cAge = 0;
		int sAge = 0;
		int cLe = 0;
		int sLe = 0;

		cAge = Utilities.CalcAge(pb.getBirthDate());
		cLe = (int)(Utilities.getLifeExp(cAge, userInfo.getClientGender()));
		
		if( !isSingle) {
			sAge = Utilities.CalcAge(sb.getBirthDate());
			sLe = (int)(Utilities.getLifeExp(sAge,userInfo.getSpouseGender()));
		}

		pd.startClientTable();
		pd.addClientInfo(userInfo.getClientFirstName(), pb.getBirthDate(), "" + cAge, ""
				+ cLe);
		if( !isSingle) {
			pd.addClientInfo(sb.getFirstName(), sb.getBirthDate(), "" + sAge, ""
				+ sLe);
			int indx =  marriages.getCurrentMarriage().getDate().lastIndexOf('/');
			String anniversary = marriages.getCurrentMarriage().getDate().substring(0,indx);
			pd.setMarriageDate(anniversary);
		}
		pd.addClientInfo(" ", "", "", ""); // Blank line
		pd.startChildrenTable();
		PersonBean child = children.getFirstChild();
		while (child != null) {
			Marriages mg = new Marriages();
			mg.setPrimaryId(child.getId());
			mg.initialize();
			ChildrenBean gk = new ChildrenBean();
			gk.setDbObject();
			gk.setOwnerId(child.getId());
			gk.initialize();

			MarriageBean cmb = mg.getCurrentMarriage();
			PersonBean csb;
			if (cmb == null) {
				csb = null;
			} else {
				csb = cmb.getSpouse();
			}

			pd.addChildInfo(child.getFirstName(), child.getOccupation(), child
					.getBirthDate(), ""
					+ Utilities.CalcAge(child.getBirthDate()), csb == null ? ""
					: csb.getFirstName(), csb == null ? "" : csb
					.getOccupation(), "" + gk.getNumChildren(), gk.getNotes());

			child = children.getNextChild();
		}

		pd.startAddressTable();
		pd.addAddress(work, home, other, email);

		sql = "select ADVISOR_ID from advisor where OWNER_ID='" + clientID
				+ "'";

		dbObj.start();

		rec = dbObj.execute(sql);
		while (rec != null) {
			AdvisorBean ab = new AdvisorBean();
			ab.setDbObject();
			ab.setId(((Number) (rec.get("ADVISOR_ID"))).longValue());
			ab.initialize();
			String addr2 = ab.getAddress2().equals("") ? "" : ab.getAddress2()
					+ "\n";
			String FAX = "";
			if(ab.getFax().length()>0)
				FAX = "FAX:" + ab.getFax() + "\n";
			String adEmail = (ab.getEmail().equals("") || ab.getEmail()
					.equalsIgnoreCase("-")) ? "" : ab.getEmail();
			String city = ab.getCity().equals("") ? "" : ab.getCity() + ", ";
			String state = ab.getState().equals("")  ? "" : ab.getState() + " ";
			String zip = ab.getZip().equals("") ? "" : ab.getZip() + " ";

			String cityState = city + state + zip + "\n";
			String suffix = ab.getSuffix().length() > 0 ? ", " + ab.getSuffix() : "";

			if (ab.getType().equalsIgnoreCase("attorney")) {
				pd.addAdvisor("Legal - Tax\n ", ab.getFirstName() + " "
						+ ab.getLastName() + suffix + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("banker")) {
				pd.addAdvisor("Banker\n ", ab.getFirstName() + " "
						+ ab.getLastName() + suffix + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("cpa")) {
				pd.addAdvisor("Accounting - Tax\n ", ab.getFirstName() + " "
						+ ab.getLastName() + suffix + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase(
					"Wealth Preservation Strategist")) {
				pd.addAdvisor("Wealth Preservation Strategist\n ", ab
						.getFirstName()
						+ " "
						+ ab.getLastName()
						+ suffix
						+ "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("investment advisor")) {
				pd.addAdvisor("Investment Advisor\n ", ab.getFirstName() + " "
						+ ab.getLastName() + ", " + ab.getSuffix() + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("insurance advisor")) {
				pd.addAdvisor("Insurance Advisor\n ", ab.getFirstName() + " "
						+ ab.getLastName() + ", " + ab.getSuffix() + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			}

			rec = dbObj.next();

		}
		dbObj.stop();

		pd.draw();
		pageNum += pd.getPageCount();
		newPage();
	}

	public void genPhilosophy(String client) {
		Philosophy p = new Philosophy(document, writer);
		p.setUserInfo(getUserInfo());
		String sqlCount = "select count(*) as TOTAL from PHILOSOPHIES where OWNER_ID='"
				+ this.clientID + "'";
		String sql = "select * from PHILOSOPHIES where OWNER_ID='"
				+ this.clientID + "'";
		HashMap<String,Object> rec;

		dbObj.start();
		rec = dbObj.execute(sqlCount);
		dbObj.stop();

		int i = ((Number) (rec.get("TOTAL"))).intValue();
		if (i > 0) {
			p.setClient(client);
			p.setPageNum(pageNum);

			dbObj.start();
			rec = dbObj.execute(sql);
			for (i = 0; rec != null; i++) {
				String info = (String) rec.get("PHILOSOPHY");

				info = info == null ? "" : info;
				String header = "";
				String para[] = new String[60];
				int idx;

				idx = info.indexOf("\r\n\r\n");
				header = info.substring(0, idx);
				info = info.substring(idx + 4);

				idx = info.indexOf("\r\n");
				int j = 0;
				if (idx == -1 && info.length() > 0) {
					para[j] = info;
				}
				while (idx > -1) {
					para[j++] = info.substring(0, idx);
					info = info.substring(idx + 2);
					idx = info.indexOf("\r\n");
					if (idx == -1 && info.length() > 0) {
						para[j] = info;
					}
				}

				p.addPhilosophy(header, para[0]);
				for (j = 1; para[j] != null && j < para.length; j++) {
					p.addPhilosophy("", para[j]);
				}

				rec = dbObj.next();
			}
			p.draw();
			pageNum += p.getPageCount();
			dbObj.stop();
			newPage();
		}

	}

	public void genQPRT(String client) {
		QprtTool tool = (QprtTool) session.getAttribute("qprt");
		tool.calculate();
		int i = tool.getNumberOfTrusts();

		QprtTrustTool cTrust = tool.getClientQprt();
		QprtTrustTool sTrust = tool.getSpouseQprt();

		QPRTPages qprt = new QPRTPages(document, writer);
		qprt.setUserInfo(getUserInfo());
		
		if( sTrust != null) {
			qprt.setSpouseTerm((int) sTrust.getTermLength());
			qprt.setSpouseQprtGift(sTrust.getTaxableGiftValue());
			qprt.setSpouseFirstName(this.userInfo.getSpouseFirstName());
		}
		
		qprt.setClientFirstName(this.userInfo.getClientFirstName());
		qprt.setPageNum(pageNum);
		qprt.setAssetName(tool.getAssetName());
		qprt.setClient(client);
		qprt.setNumberOfTrusts(i);
		qprt.setClientTerm((int) cTrust.getTermLength());
		qprt.setEstateTaxRate(tool.getEstateTaxRate());
		qprt.setGrowthRate(tool.getGrowth());
		qprt.setLifeExpectancy(tool.getFinalDeath());
		qprt.setAssetValue(tool.getValue());
		qprt.setClientQprtGift(cTrust.getTaxableGiftValue());
		qprt.setProjectedValue(qprt.getAssetValue()
				* (Math.pow((1 + qprt.getGrowthRate()), tool.getFinalDeath())));
		if (tool.getTypeOfHome() == 1)
			qprt.setRType("Vacation");
		else
			qprt.setRType("Personal");
		qprt.draw();
		pageNum += qprt.getPageCount();
		newPage();
	}

	public void genRecomendations(String client) {

		Recommendations r = new Recommendations(document, writer);
		r.setUserInfo(getUserInfo());
		r.setClient(client);
		r.setPageNum(pageNum);
		r.setClientFirstName(this.userInfo.getClientFirstName());
		r.setSpouseFirstName(this.userInfo.getSpouseFirstName());

		String sqlCount = "select count(*) as TOTAL from RECOMMENDATIONS where OWNER_ID='"
				+ this.clientID + "'";
		String sql = "select * from RECOMMENDATIONS where OWNER_ID='"
				+ this.clientID + "'";
		HashMap<String,Object> rec;

		dbObj.start();
		rec = dbObj.execute(sqlCount);
		dbObj.stop();

		int i = ((Number) (rec.get("TOTAL"))).intValue();
		if (i > 0) {
			// String philos[][] = new String[i][2];

			dbObj.start();
			rec = dbObj.execute(sql);
			for (i = 0; rec != null; i++) {
				String info = (String) rec.get("RECOMMENDATION");

				info = info == null ? "" : info;
				String header = "";
				String para[] = new String[10];
				int idx;

				idx = info.indexOf("\r\n\r\n");
				header = info.substring(0, idx);
				info = info.substring(idx + 4);

				idx = info.indexOf("\r\n");
				int j = 0;
				if (idx == -1 && info.length() > 0) {
					para[j] = info;
				}
				while (idx > -1) {
					para[j++] = info.substring(0, idx);
					info = info.substring(idx + 2);
					idx = info.indexOf("\r\n");
					if (idx == -1 && info.length() > 0) {
						para[j] = info;
					}
				}

				r.addRecommendation(header, para[0]);
				for (j = 1; para[j] != null && j < para.length; j++) {
					r.addRecommendation("", para[j]);
				}
				rec = dbObj.next();
			}
			dbObj.stop();
			r.draw();
			newPage();
			pageNum += r.getPageCount();
		}
	}

	public void genRPM(String client) {

		RpmTool tool = (RpmTool) session.getAttribute("rpm");
		tool.calculate();

		RpmTable rpTable = tool.getRpTable();
		double table[][] = rpTable.getRpTable();

		double cGrowth = rpTable.getPlanGrowth();

		RPMPages rpm = new RPMPages(document, writer);
		rpm.setUserInfo(getUserInfo());
		
		rpm.setClient(client);
		rpm.setClientFirstName(userInfo.getClientFirstName());
		rpm.setSpouseFirstName(userInfo.getSpouseFirstName());
		rpm.setPageNum(pageNum);
		rpm.setTerm(tool.getTerm());

		rpm.table1Rows = table;

		rpTable.calculateRpm();
		double rGrowth = rpTable.getPlanGrowth();
		table = rpTable.getRpmTable();

		rpm.table2Rows = table;
		rpm.setCurrentEarning(cGrowth);
		rpm.setRpmEarning(rGrowth);
		rpm.draw();

		pageNum += rpm.getPageCount();
		newPage();
	}

	public void genSc1Graphs(String client, CashFlowTable cft) {
		CashFlowGraph cfg = new CashFlowGraph(document, writer);
		cfg.setUserInfo(getUserInfo());
		cfg.setCashFlows(cft);
		cfg.setClient(client);
		cfg.setClientFirstName(userInfo.getClientFirstName());
		cfg.setSpouseFirstName(userInfo.getSpouseFirstName());
		cfg.setPageNum(pageNum);
		cfg.setScenario1Only(true);
		cfg.draw();
		pageNum += cfg.getPageCount();
		newPage();
	}

        public void genSplit() {
            SplitReport split = new SplitReport(document, writer);
            split.setUserInfo(getUserInfo());
            split.setPageNum(pageNum);
            split.draw();
            pageNum += split.getPageCount();
            newPage();
        }
	
	public void genScin() {
		SCINTool scin= (SCINTool) session.getAttribute("scin");
		scin.calculate();
		
		SCINPages sPage = new SCINPages(document, writer);
		sPage.setScin(scin);
		sPage.setUserInfo(userInfo);
		sPage.setPageNum(pageNum);
		sPage.draw();
		pageNum += sPage.getPageCount();
		newPage();

	}
	
	public void genSIDIT(String client) {
		SIditTool sTool = (SIditTool) session.getAttribute("sidit");
		SIditPages sPages = new SIditPages(document,writer);
		sPages.setClient(client);
		sPages.setUserInfo(userInfo);
		sPages.setUseLLC(useLLC);
		sPages.setPageNum(pageNum);

		sTool.calculate();
		sPages.setSidit(sTool);

		sPages.draw();
		
		pageNum += sPages.getPageCount();
		newPage();
	}
	
	public void genTestCLAT(String client) {
		TClatTool tClat = (TClatTool) session.getAttribute("tclat");
		TestCLATPages tc = new TestCLATPages(document, writer);
		tc.setUserInfo(getUserInfo());
		tc.setUseLLC(isUseLLC());
		tc.setClient(client);
		tc.setTerm(tClat.getTerm());
		tc.setClientFirstName(userInfo.getClientFirstName());
		tc.setSpouseFirstName(userInfo.getSpouseFirstName());
		tc.setPageNum(pageNum);
		tc.draw();
		pageNum += tc.getPageCount();
		newPage();
	}
	
	public void genTestCLAT2(String client) {
		TClat2 tClat = (TClat2) session.getAttribute("tclat2");
		tClat.calculate();
		TCLAT2Pages tc = new TCLAT2Pages(document, writer);
		tc.setUserInfo(getUserInfo());
		tc.setUseLLC(isUseLLC());
		tc.setClient(client);
		tc.setTerm(tClat.getTerm());
		tc.setAmount(tClat.getAmount());
		tc.setAnnuityPayment(tClat.getAnnuity());
		tc.setCharitableDeduction(tClat.getClatDeduction());
		tc.setRemainderInterest(tClat.getRemainderInterest());
		tc.setClientFirstName(userInfo.getClientFirstName());
		tc.setSpouseFirstName(userInfo.getSpouseFirstName());
		tc.setPageNum(pageNum);
		tc.draw();
		pageNum += tc.getPageCount();
		newPage();
	}

	public void genTitlePage(String client) {
		SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		String date = df.format(new Date());
		TitlePage titlePage = new TitlePage(document, writer);
		titlePage.setClient(client);
		titlePage.setDate(date);
		String sql = "select ADVISOR_ID from advisor where OWNER_ID='"
				+ clientID + "'";

		dbObj.start();

		HashMap<String,Object> rec = dbObj.execute(sql);
		while (rec != null) {
			AdvisorBean ab = new AdvisorBean();
			ab.setDbObject();
			ab.setId(((Number) (rec.get("ADVISOR_ID"))).longValue());
			ab.initialize();
			String addr2 = ab.getAddress2() == "" ? "" : ab.getAddress2()
					+ "\n";
			String city = ab.getCity() == "" ? "" : ab.getCity() + ", ";
			String state = ab.getState() == "" ? "" : ab.getState() + " ";
			String zip = ab.getZip() == "" ? "" : ab.getZip() + " ";

			String cityState = city + state + zip + "\n";
			String FAX = "";

			if(ab.getFax().length()> 0){
				FAX = ab.getFax() == "" ? "" : "FAX: " + ab.getFax() + "\n";
			}

			String adEmail = (ab.getEmail() == "" || ab.getEmail()
					.equalsIgnoreCase("-")) ? "" : ab.getEmail();

			if (ab.getType().equalsIgnoreCase("attorney")) {
				String suffix = ab.getSuffix().length() > 0 ? ", " + ab.getSuffix() : "";
				titlePage.addAdvisor("Legal - Tax\n ", ab.getFirstName() + " "
						+ ab.getLastName() + suffix + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("banker")) {
				String suffix = ab.getSuffix().length() > 0 ? ", " + ab.getSuffix() : "";
				titlePage.addAdvisor("Banker\n ", ab.getFirstName() + " "
						+ ab.getLastName() + suffix + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState + "\n"
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("cpa")) {
				String suffix = ab.getSuffix().length() > 0 ? ", " + ab.getSuffix() : "";
				titlePage.addAdvisor("Accounting - Tax\n ", ab.getFirstName()
						+ " " + ab.getLastName() + suffix + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("investment advisor")) {
				String suffix = ab.getSuffix().length() > 0 ? ", " + ab.getSuffix() : "";
				titlePage.addAdvisor("Investment Advisor\n ", ab.getFirstName()
						+ " " + ab.getLastName() + suffix + "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase(
					"Wealth Preservation Strategist")) {
				String suffix = ab.getSuffix().length() > 0 ? ", " + ab.getSuffix() : "";
				titlePage.addAdvisor("Wealth Preservation Strategist\n ", ab
						.getFirstName()
						+ " "
						+ ab.getLastName()
						+ suffix
						+ "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			} else if (ab.getType().equalsIgnoreCase("Insurance Advisor")) {
				String suffix = ab.getSuffix().length() > 0 ? ", " + ab.getSuffix() : "";
				titlePage.addAdvisor("Insurance Advisor\n ", ab
						.getFirstName()
						+ " "
						+ ab.getLastName()
						+ suffix
						+ "\n"
						+ ab.getTitle() + "\n\n", ab.getFirmName() + "\n"
						+ ab.getAddress1() + "\n" + addr2 + cityState
						+ ab.getPhone() + "\n" + FAX + adEmail);
			}

			rec = dbObj.next();

		}
		dbObj.stop();
		titlePage.draw();

		newPage();

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

	/**
	 * @return Returns the epg.
	 */
	public EstatePlanningGlobals getEpg() {
		return epg;
	}

	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @return Returns the session.
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @return Returns the spouseID.
	 */
	public int getSpouseID() {
		return spouseID;
	}

	public PdfBean getUserInfo() {
		return userInfo;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	public void newPage() {
		try {
			document.newPage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		pageNum++;
	}

	/**
	 * @param clientID
	 *            The clientID to set.
	 */
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	/**
	 * @param epg
	 *            The epg to set.
	 */
	public void setEpg(EstatePlanningGlobals epg) {
		this.epg = epg;
	}

	public void setPage(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @param session
	 *            The session to set.
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * @param spouseID
	 *            The spouseID to set.
	 */
	public void setSpouseID(int spouseID) {
		this.spouseID = spouseID;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}

	public void setUserInfo(PdfBean userInfo) {
		this.userInfo = userInfo;
	}
}
