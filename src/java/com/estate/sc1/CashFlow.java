package com.estate.sc1;

import com.estate.constants.TaxableIncome;
import com.estate.sc.utils.*;
import com.teag.bean.*;
import java.util.*;
import com.teag.util.*;

public class CashFlow {

	ArrayList<LineObject> receipts = new ArrayList<LineObject>();
	ArrayList<LineObject> disbursements = new ArrayList<LineObject>();
	ArrayList<LineObject> netPortfolio = new ArrayList<LineObject>();
	
	ArrayList<Portfolio> portfolio = new ArrayList<Portfolio>();

	/**
	 * The following three arrays, hold the tax information for each year,
	 * as each asset is processed the appropriate tax values are inserted into
	 * the arrays. Then the calculations are done after all assets are processed.
	 */
	double taxDeduction[] = new double[ScenarioConstants.MAX_TABLE];
	double taxableIncome[] = new double[ScenarioConstants.MAX_TABLE];
	double capGainsIncome[] =  new double[ScenarioConstants.MAX_TABLE];

	/**
	 * The incomeTax, totalReceipts, totalDisbursements, and excessCash are used to store the final
	 * totals. We also add a contribution to charity here.
	 */
	double incomeTax[] = new double[ScenarioConstants.MAX_TABLE];
	double totalReceipts[] = new double[ScenarioConstants.MAX_TABLE];
	double totalDisbursements[] = new double[ScenarioConstants.MAX_TABLE];
	double excessCash[] = new double[ScenarioConstants.MAX_TABLE];
	double toCharity[] = new double[ScenarioConstants.MAX_TABLE];
	/**
	 * The SocialSecurty is used in the tax calculations.
	 */
	double socialSecurity[] = new double[ScenarioConstants.MAX_TABLE];
	/**
	 * The charitableDed array is used if there are any deductions in the disbursements.
	 * And it is eventually subtracted out at the tax calculations
	 */
	double charitableDed[] =  new double[ScenarioConstants.MAX_TABLE];
	double portfolioIncome[] = new double[ScenarioConstants.MAX_TABLE];
	
	PdfBean userInfo;
	CashFlowBean cfb;
	PersonBean client;
	PersonBean spouse;

	int startYear = 0;
	int currentMonth = -1;

	int ageInc = 0;

	boolean useFiscalYear = true;

	public void initCashFlow() {

		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		currentMonth = cal.get(Calendar.MONTH) +1;

		if (currentMonth > 1 && useFiscalYear) {
			startYear = cal.get(Calendar.YEAR) + 1;
			ageInc = 0;
		} else {
			startYear = cal.get(Calendar.YEAR);
		}

		PortfolioBean p = new PortfolioBean();
		ArrayList<PortfolioBean> portList = p.query(PortfolioBean.OWNER_ID
				+ "='" + client.getId() + "'");
		if(portList.size() ==0) {
			p.setDescription("Securities");
			p.setValue(0);
			p.setGrowth(.05);
			p.setIncome(.02);
			p.setExcessCashFlow(1);
			portList.add(p);
		}
		
		for (PortfolioBean port : portList) {
			Portfolio pAsset = new Portfolio();
			pAsset.setGrowthRate(port.getGrowth());
			pAsset.setIncomeRate(port.getIncome());
			pAsset.setDescription(port.getDescription());
			pAsset.setPctExcess(port.getExcessCashFlow());
			pAsset.setBeginingBalance(port.getValue()/1000.0);
			portfolio.add(pAsset);
		}
		for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
			taxDeduction[i] = 0;
			taxableIncome[i] = 0;
			capGainsIncome[i] = 0;
			charitableDed[i] = 0;
		}
		loadReceipt();
		loadDisbursements();
		calcTax();
		buildPortfolioNetworth();
	}
	
	public void buildPortfolioNetworth() {
		for(Portfolio p : portfolio) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(p.getDescription() + " Portfolio");
			double eBal[] = p.getEndBalance();
			for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.setValue(i,eBal[i]*1000.0);
			}
			netPortfolio.add(lineObj);
		}
	}
	
	public void calcTax() {
		for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
			double cr = 0;
			double dr = 0;
			for(LineObject l : receipts) {
				cr += l.getValue(i);
			}
			for(LineObject l : disbursements) {
				dr += l.getValue(i);
			}
			taxableIncome[i] -= (taxDeduction[i]);
			taxableIncome[i] += portfolioIncome[i];
			
			// Add a charitable deduction, this is specified in the CashFlowBean as a percentage of the Cash Receipts.
			toCharity[i] +=(cr * cfb.getCharity());
			charitableDed[i] += toCharity[i]; 
			
			TaxCalculator tc = new TaxCalculator();
			tc.setYear(startYear +i);
			tc.setOrdinaryIncome(taxableIncome[i]*1000);
			tc.setAdjustmentsToGross(0);
			//tc.setDividendIncome(dividendIncome[i]);
			tc.setCapGains15(capGainsIncome[i] * 1000);
			tc.setStateTaxRate(cfb.getStateTaxRate());
			tc.setSocialSecurity(socialSecurity[i]);
			tc.setItemizedDeductions(charitableDed[i]);
			tc.calculate();
			incomeTax[i] = rnd(tc.getTotalTax());
			
			cr += portfolioIncome[i];
			totalReceipts[i] = cr;
			totalDisbursements[i] = dr + incomeTax[i] + toCharity[i];
			excessCash[i] = cr - (dr + incomeTax[i]+toCharity[i]);
			buildPortfolio(i,excessCash[i]);
		}

		// Add the portfolio income to the cash receipts
		for(Portfolio p : portfolio) {
			LineObject lineObj = new LineObject();
			double income[] = p.getIncome();
			lineObj.setDescription(p.getDescription()+ " Income");
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.addValue(i,income[i]*1000);
			}
			receipts.add(lineObj);
		}
	}
	
	public void buildPortfolio(int i, double excessCash) {
		double eCash = excessCash;
		for(Portfolio p : portfolio) {
			if( eCash > 0)
				p.genYear(i, excessCash * p.getPctExcess());
			eCash -= (excessCash * p.getPctExcess());
			if( i < ScenarioConstants.MAX_TABLE -1)
				portfolioIncome[i+1] += p.getIncomeValue(i+1);
		}
	}

	public void loadReceipt() {
		receipts.clear();
		recVCFItems();
		recSocialSecurity();
		recCashItems();
		recBonds();
		recBusiness();
		recSecurities();
		recIlliquid();
		recRealEstate();
		recRetirement();
		recNotesReceivable();
	}
	
	
	public void recVCFItems() {
		String queryString = VCFBean.OWNER_ID + "='" + client.getId() + "'"
				+ " and " + VCFBean.CF_FLAG + "='C'";
		// Variable Cash Flow Items
		VCFBean vAsset = new VCFBean();
		ArrayList<VCFBean> vList = vAsset.getBeans(queryString);
		for (VCFBean v : vList) {
			if(v.getUseFlag().equals("1") || v.getUseFlag().equals("B")) {
				VariableCashFlow vcf = new VariableCashFlow();
				vcf.setValues(v);
				LineObject lineObj = new LineObject();
				lineObj.setDescription(v.getDescription());
				lineObj.setPrctTaxable(1.0);
				double vTable[] = vcf.getVTable();
				for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
					lineObj.setValue(i, vTable[i]);
					capGainsIncome[i] += lineObj.getTaxableDividend(i);
					taxableIncome[i] += lineObj.getTaxableIncome(i);
				}
				receipts.add(lineObj);
			}
		}
	}

	public void recCashItems() {
		String queryString = CashBean.OWNER_ID + "='" + client.getId() + "'";
		CashBean cBean = new CashBean();
		ArrayList<CashBean> cList = cBean.getBeans(queryString);
		for (CashBean c : cList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(c.getName());
			lineObj.setTaxableIncome(Integer.toString(TaxableIncome.O.id()));
			lineObj.setTaxablePercent(1.0);
			Asset a = new Asset();
			a.setBasis(c.getBasis());
			a.setValue(c.getAssetValue());
			a.setGrowth(c.getGrowth());
			a.setIncome(c.getInterest());
			a.setDescription(c.getName());
			a.setTaxable(1.0);
			a.genIncomeFlow();
			for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.addValue(i, a.getCashFlow(i));
				capGainsIncome[i] += lineObj.getTaxableDividend(i);
				taxableIncome[i] += lineObj.getTaxableIncome(i);
			}
			receipts.add(lineObj);
		}
	}

	public void recSocialSecurity() {
		double v = cfb.getSocialSecurity() * 12;
		if( v <= 0)
			return;
		double increase = cfb.getSocialSecurityGrowth();
		int cAge = userInfo.getClientAge() + ageInc;
		int sAge = -1;
		int startSpouse = 150;
		if (!userInfo.isSingle()) {
			sAge = userInfo.getSpouseAge() + ageInc;
			startSpouse = ScenarioConstants.SOCIAL_START;
		}
		if (v > 0) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription("SocialSecurity");
			lineObj.setPrctTaxable(1.0);
			for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				double total = 0;
				if (cAge >= ScenarioConstants.SOCIAL_START) {
					total = v;
				}
				if (sAge >= startSpouse) {
					total += (v) * .5;
				}
				lineObj.setValue(i, total);
				socialSecurity[i] = total;
				v += v * increase;
			}
			receipts.add(lineObj);
		}
	}

	public void recBonds() {
		String qString = BondBean.OWNER_ID + "='" + client.getId() + "'";
		BondBean bondBean = new BondBean();
		ArrayList<BondBean> bList = bondBean.getBeans(qString);
		for (BondBean b : bList) {
			Asset a = new Asset();
			a.setValue(b.getAssetValue());
			a.setGrowth(b.getAssetGrowth());
			a.setIncome(b.getAssetIncome());
			a.setReceipt(true);
			a.genIncomeFlow();
			double vTable[] = a.getCashFlow();
			LineObject lineObj = new LineObject();
			lineObj.setDescription(b.getName());
			lineObj.setPrctTaxable(1.0);
			//lineObj.setTaxableIncome("O");
			//lineObj.setTaxablePercent(1.0);
			for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.setValue(i, vTable[i]);
				capGainsIncome[i] += lineObj.getTaxableDividend(i);
				taxableIncome[i] += lineObj.getTaxableIncome(i);
			}
			receipts.add(lineObj);
		}
	}

	public void recSecurities() {
		String qString = SecuritiesBean.OWNER_ID + "='" + client.getId() + "'";
		SecuritiesBean secBean = new SecuritiesBean();
		ArrayList<SecuritiesBean> sList = secBean.getBeans(qString);
		for (SecuritiesBean s : sList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(s.getDescription());
			Asset a = new Asset();
			a.setDescription(s.getDescription());
			a.setValue(s.getValue());
			a.setBasis(s.getBasis());
			a.setGrowth(s.getAssetGrowth());
			a.setIncome(s.getAssetIncome());
			a.setReceipt(true);
			a.genIncomeFlow();
			double vTable[] = a.getCashFlow();
			for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.addValue(i, vTable[i]);
				capGainsIncome[i] += lineObj.getTaxableDividend(i);
				taxableIncome[i] += lineObj.getTaxableIncome(i);
			}
			receipts.add(lineObj);
		}
	}

	// This should not be genreating any income, but we need to see if we 
	// need to liquidate it as some point!
	public void recIlliquid() {
		String qString = IlliquidBean.OWNER_ID + "='" + client.getId() + "'";
		IlliquidBean ib = new IlliquidBean();
		ArrayList<IlliquidBean> iList = ib.getBeans(qString);
		for(IlliquidBean b : iList) {
			double value = b.getValue();
			double growth = b.getAssetGrowth();

			LineObject lObj = new LineObject();
			lObj.setDescription(b.getDescription());

			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				value += value * growth;
			}
			receipts.add(lObj);
		}
	}
	
	public void recRealEstate() {
		String qString = RealEstateBean.OWNER_ID + "='" + client.getId() + "'";
		RealEstateBean realEstateBean = new RealEstateBean();
		ArrayList<RealEstateBean> rList = realEstateBean.getBeans(qString);
		for (RealEstateBean r : rList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(r.getDescription());
			double rents = r.getGrossRents();
			double rentsGrowth = r.getGrossRentsGrowth();
			double operatingExpenses = r.getOperatingExpenses();
			double operatingGrowth = r.getGrowthExpenses();				// Operating Expenses Growth
			// Note: Since we are starting out with the end of the first year, we need to 
			// add the rents growth first!, as well as the operating expenses.
			for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				rents += rents * rentsGrowth;
				operatingExpenses += operatingExpenses * operatingGrowth;
				double income = rents - operatingExpenses;
				lineObj.addValue(i, income);
				capGainsIncome[i] += lineObj.getTaxableDividend(i);
				taxableIncome[i] += lineObj.getTaxableIncome(i);				
			}
			receipts.add(lineObj);
		}
	}

	public void recBusiness() {
		String qString = BusinessBean.OWNER_ID + "='" + client.getId() + "'";
		BusinessBean bizBean = new BusinessBean();
		ArrayList<BusinessBean> bList = bizBean.getBeans(qString);
		for (BusinessBean b : bList) {
			double income = b.getAssetIncome();
			double iGrowth = b.getIncomeGrowth();
			LineObject lineObj = new LineObject();
			lineObj.setDescription(b.getDescription());
			// We need to specify cash sell here as well
			for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				income += income*iGrowth;
				lineObj.setValue(i,0);
				taxableIncome[i] += lineObj.getTaxableIncome(i);				
			}
			receipts.add(lineObj);
		}
	}

	public void recNotesReceivable() {
		String qString = NotesBean.OWNER_ID + "='" + client.getId() + "'";
		NotesBean recBean = new NotesBean();
		ArrayList<NotesBean> nList = recBean.getBeans(qString);
		for (NotesBean n : nList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(n.getDescription());
			lineObj.setPrctTaxable(0);
			if (n.getNoteType().equalsIgnoreCase("I")) {
				for (int i = 0; i < n.getYears(); i++) {
					lineObj.setValue(i,n.getAssetValue() * n.getInterestRate());
				}
				double v = lineObj.getValue((int) n.getYears() -1)*1000;
				lineObj.addValue((int) n.getYears() - 1, v);
			} else if (n.getNoteType().equalsIgnoreCase("A")) {
				double payment = Function.PMT(n.getAssetValue(), n.getInterestRate()
						/ n.getPaymentsPerYear(), n.getYears()
						* n.getPaymentsPerYear());
				for (int i = 0; i < n.getYears() + 1; i++) {
					lineObj.setValue(i, payment * n.getPaymentsPerYear());
					capGainsIncome[i] += lineObj.getTaxableDividend(i);
					taxableIncome[i] += lineObj.getTaxableIncome(i);				
				}

			} else {
				int idx = (int) n.getYears() -1;
				lineObj.setValue(idx, n.getAssetValue());
				capGainsIncome[idx] += lineObj.getTaxableDividend(idx);
				taxableIncome[idx] += lineObj.getTaxableIncome(idx);				
			}
			receipts.add(lineObj);
		}
	}
	
	
	
	public void recRetirement() {
		RetirementCashFlow rcf = new RetirementCashFlow();
		rcf.setClientAge(userInfo.getClientAge());
		rcf.setSingle(userInfo.isSingle());
		if( !userInfo.isSingle()){
			rcf.setSpouseAge(userInfo.getSpouseAge());
		}
		String qString = RetirementBean.OWNER_ID + "='" + client.getId() + "'";
		RetirementBean retirement = new RetirementBean();
		ArrayList<RetirementBean> rList = retirement.getBeans(qString);
		for(RetirementBean r : rList) {
			rcf.addPlan(r.getValue(), r.getAssetGrowth());
		}
		
		double vTable[] = rcf.getDispersements();
		LineObject lineObj = new LineObject();
		lineObj.setDescription("Retirement Plan Distributions");
		lineObj.setPrctTaxable(1.0);
		for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
			lineObj.setValue(i,vTable[i]);
			capGainsIncome[i] += lineObj.getTaxableDividend(i);
			taxableIncome[i] += lineObj.getTaxableIncome(i);						}
		
		receipts.add(lineObj);
	}

	public void loadDisbursements() {
		disVCFItems();
		disRealEstate();
		disRetirement();
		disPersonalProperty();
		disNotesPayable();
	}
	
	public void disVCFItems() {
		String queryString = VCFBean.OWNER_ID + "='" + client.getId() + "'"
		+ " and " + VCFBean.CF_FLAG + "='D'";
		// Variable Cash Flow Items - Disbursements
		VCFBean vAsset = new VCFBean();
		ArrayList<VCFBean> vList = vAsset.getBeans(queryString);
		for (VCFBean v : vList) {
			if(v.getUseFlag().equals("B") || v.getUseFlag().equals("1")){
				VariableCashFlow vcf = new VariableCashFlow();
				vcf.setValues(v);
				LineObject lineObj = new LineObject();
				lineObj.setDescription(v.getDescription());
				double vTable[] = vcf.getVTable();
				for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
					lineObj.setValue(i, vTable[i]);
					if(v.getCharitableDed().equals("Y")){
						//taxDeduction[i] += rnd(vTable[i]);
						charitableDed[i] += (vTable[i]);
					}
				}
				disbursements.add(lineObj);
			}
		}
	}
	
	public void disRealEstate() {
		String qString = RealEstateBean.OWNER_ID + "='" + client.getId() + "'";
		RealEstateBean realEstateBean = new RealEstateBean();
		ArrayList<RealEstateBean> rList = realEstateBean.getBeans(qString);
		LineObject lineObj = new LineObject();
		lineObj.setDescription("Real Estate Loans");;
		lineObj.setPrctTaxable(1.0);
		for (RealEstateBean r : rList) {
			if(r.getLoanBalance()> 0) {
				double payment = r.getLoanPayment();
				double freq = r.getLoanFreq();
				for (int i = 0; i < r.getLoanTerm()/12; i++) {
					lineObj.addValue(i,(payment*freq));
					//taxDeduction[i] += rnd(payment*freq);
				}
			}
		}
		disbursements.add(lineObj);
	}
	
	public void disRetirement() {
		/*
		String qString = RetirementBean.OWNER_ID + "='" + client.getId() + "'";
		RetirementBean rb = new RetirementBean();
		ArrayList<RetirementBean> rList = rb.query(qString);
		LineObject lineObj = new LineObject(); 
		lineObj.setDescription("Retirement Annual Cont.");
		lineObj.setPrctTaxable(1);
		for(RetirementBean r : rList) {
			if( r.getAnnualContrib() > 0)
				for( int i=0; i < r.getAnnualYears(); i++ ) {
					lineObj.addValue(i,r.getAnnualCont());
					taxDeduction[i] += rnd(r.getAnnualCont());
				}
		}
		disbursements.add(lineObj);
		*/
		
	}
	
	public void disPersonalProperty() {
		String qString = PropertyBean.OWNER_ID + "='" + client.getId() + "'";
		PropertyBean property = new PropertyBean();
		ArrayList<PropertyBean> pList = property.getBeans(qString);
		LineObject lineObj = new LineObject();
		lineObj.setDescription("Mortgages");
		for(PropertyBean p : pList) {
			if( p.getLoanBalance() > 0 ) {
				double payment = p.getLoanPayment() * p.getLoanFreq();
				double term = p.getLoanTerm();
				for(int i = 0; i < term; i++) {
					lineObj.addValue(i, payment);
				}
			}
		}
		disbursements.add(lineObj);
	}
	
	public void disNotesPayable() {
		String qString = NotePayableBean.OWNER_ID + "='" + client.getId() + "'";
		NotePayableBean notePayable = new NotePayableBean();
		ArrayList<NotePayableBean> nList = notePayable.getBeans(qString);
		for(NotePayableBean n : nList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(n.getDescription());
			lineObj.setPrctTaxable(1.0);
			if(n.getNoteType().equalsIgnoreCase("I")) {
				// Interest only with balloon payment at the end
				for(int i = 0; i < n.getYears(); i++) {
					lineObj.setValue(i,n.getAssetValue() * n.getInterestRate());
				}
				lineObj.addValue((int)n.getYears()-1, n.getAssetValue());
			} else if( n.getNoteType().equalsIgnoreCase("A")){
				double payment = Function.PMT(n.getAssetValue(),n.getInterestRate()/n.getPaymentsPerYear(), n.getYears()* n.getPaymentsPerYear());
				for(int i = 0; i < n.getYears(); i++) {
					lineObj.setValue(i,payment*n.getPaymentsPerYear());
				}
			} else {
				lineObj.setValue((int)n.getYears()-1,n.getAssetValue());
			}
			
		}
		
	}
	
	private double rnd(double v) {
		return Math.rint(v/1000.0);
	}
	
	public PdfBean getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(PdfBean userInfo) {
		this.userInfo = userInfo;
	}

	public PersonBean getClient() {
		return client;
	}

	public void setClient(PersonBean client) {
		this.client = client;
	}

	public PersonBean getSpouse() {
		return spouse;
	}

	public void setSpouse(PersonBean spouse) {
		this.spouse = spouse;
	}

	public ArrayList<LineObject> getReceipts() {
		return receipts;
	}

	public ArrayList<LineObject> getDisbursements() {
		return disbursements;
	}

	public void setDisbursements(ArrayList<LineObject> disbursements) {
		this.disbursements = disbursements;
	}

	public void setReceipts(ArrayList<LineObject> receipts) {
		this.receipts = receipts;
	}

	public double[] getExcessCash() {
		return excessCash;
	}

	public void setExcessCash(double[] excessCash) {
		this.excessCash = excessCash;
	}

	public double[] getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(double[] incomeTax) {
		this.incomeTax = incomeTax;
	}

	public double[] getTaxableIncome() {
		return taxableIncome;
	}

	public void setTaxableIncome(double[] taxableIncome) {
		this.taxableIncome = taxableIncome;
	}

	public double[] getTaxDeduction() {
		return taxDeduction;
	}

	public void setTaxDeduction(double[] taxDeduction) {
		this.taxDeduction = taxDeduction;
	}

	public double[] getTotalDisbursements() {
		return totalDisbursements;
	}

	public void setTotalDisbursements(double[] totalDisbursements) {
		this.totalDisbursements = totalDisbursements;
	}

	public double[] getTotalReceipts() {
		return totalReceipts;
	}

	public void setTotalReceipts(double[] totalReceipts) {
		this.totalReceipts = totalReceipts;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public ArrayList<Portfolio> getPortfolio() {
		return portfolio;
	}

	public ArrayList<LineObject> getNetPortfolio() {
		return netPortfolio;
	}

	public void setNetPortfolio(ArrayList<LineObject> netPortfolio) {
		this.netPortfolio = netPortfolio;
	}

	public double[] getCharitableDed() {
		return charitableDed;
	}

	public void setCharitableDed(double[] charitableDed) {
		this.charitableDed = charitableDed;
	}

	public double[] getPortfolioIncome() {
		return portfolioIncome;
	}

	public void setPortfolioIncome(double[] portfolioIncome) {
		this.portfolioIncome = portfolioIncome;
	}

	public void setPortfolio(ArrayList<Portfolio> portfolio) {
		this.portfolio = portfolio;
	}

	public CashFlowBean getCfb() {
		return cfb;
	}

	public void setCfb(CashFlowBean cfb) {
		this.cfb = cfb;
	}
}
