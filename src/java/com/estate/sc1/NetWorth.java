package com.estate.sc1;

import java.util.*;

import com.estate.sc.utils.Asset;
import com.estate.sc.utils.*;
import com.teag.util.Function;
import com.teag.bean.*;

public class NetWorth {

	ArrayList<LineObject> networth = new ArrayList<LineObject>();
	
	int currentYear;
	int startYear;
	int ageInc;
	boolean useFiscalYear = true;
	PersonBean client;
	PersonBean spouse;
	PdfBean userInfo = new PdfBean();
	CashFlowBean cfb;
	
	double liability[] = new double[ScenarioConstants.MAX_TABLE];
	double subTotal[] = new double[ScenarioConstants.MAX_TABLE];
	double total[] = new double[ScenarioConstants.MAX_TABLE];
	
	
	public void initNetworth() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		
		if( currentMonth > 1 && useFiscalYear) {
			startYear = cal.get(Calendar.YEAR) + 1;
			ageInc = 0;
		} else {
			startYear = cal.get(Calendar.YEAR);
		}
		loadNetWorth();
	}
	
	
	public void loadNetWorth() {
		loadVCF();
		loadCash();
		loadBonds();
		loadSecurities();
		loadRetirement();
		loadBusiness();
		loadIlliquid();
		loadRealEstate();
		loadPersonal();
		loadNoteReceivable();
		loadNotesPayable();
		loadLife();
		loadDebt();
	}

	public void addPortfolio(ArrayList<LineObject> netPort) {
		for(LineObject port : netPort){
			networth.add(port);
		}
		update();
	}
	
	public void update(){
		//LineObject lineObj = new LineObject();
		//lineObj.setDescription("TOTALS");
		for(int i= 0; i < ScenarioConstants.MAX_TABLE; i++) {
			double t = 0;
			for(LineObject l : networth) {
				t += (l.getValue(i)*1000);
			}
			t -= liability[i]*1000;
			//lineObj.setValue(i, t);
			total[i] = t/1000.0;
		}
		//networth.add(lineObj);
	}
	
	public void loadCash() {
		String qString = VCFBean.OWNER_ID + "='" + client.getId() + "'";
		CashBean cashBean = new CashBean();
		ArrayList<CashBean> cList = cashBean.getBeans(qString);
		for(CashBean c : cList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(c.getName());
			Asset a = new Asset();
			a.setBasis(c.getBasis());
			a.setValue(c.getAssetValue());
			a.setGrowth(c.getGrowth());
			a.setIncome(c.getInterest());
			a.genNetWorth();
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.setValue(i, a.getCashFlow(i));
			}
			networth.add(lineObj);

		}
	}
	
	public void loadDebt() {
		String qString = DebtBean.OWNER_ID + "='" + client.getId() + "'";
		DebtBean debtBean = new DebtBean();
		ArrayList<DebtBean> dList = debtBean.getBeans(qString);
		for(DebtBean d : dList) {
			double presentValue = d.getValue();
			double debtInterest = d.getInterestRate();
			double term = d.getLoanTerm();
			double pmt = Function.PMT(presentValue,debtInterest,term);
			double value = presentValue;
			LineObject lineObj = new LineObject();
			lineObj.setDescription(d.getDescription());
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				double iPayment = Function.IPMT(debtInterest,i, term, value);
				value = value -(pmt -iPayment);
				if( value < 0)
					value = 0;
				lineObj.setValue(i,-value);
			}
			networth.add(lineObj);
		}
	}
	
	public void loadNotesPayable() {
		String qString = NotePayableBean.OWNER_ID + "='" + client.getId() + "'";
		NotePayableBean noteBean = new NotePayableBean();
		ArrayList<NotePayableBean> nList = noteBean.getBeans(qString);
		for(NotePayableBean n : nList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(n.getDescription());
			if(n.getNoteType().equalsIgnoreCase("I")){
				for(int i=0; i < n.getYears(); i++) {
					lineObj.setValue(i,-n.getAssetValue());
				}
			} else if(n.getNoteType().equalsIgnoreCase("A")) {
				double payment = Function.PMT(n.getAssetValue(),n.getAssetIncome()/n.getPaymentsPerYear(),
						n.getYears()* n.getPaymentsPerYear());
				double balance = n.getAssetValue();
				for( int i = 0; i < n.getYears(); i++) {
					for( int j = 0; j < n.getPaymentsPerYear(); j++) {
						double ci = balance * (n.getAssetIncome()/n.getPaymentsPerYear());
						balance = balance - (payment - ci);
					}
					if(balance <= 0){
						lineObj.setValue(i, 0);
						balance = 0;
					} else {
						lineObj.setValue(i,-balance);
					}
				}
			}
			networth.add(lineObj);
		}
	}
	
	public void loadNoteReceivable() {
		String qString = NotesBean.OWNER_ID + "='" + client.getId() + "'";
		NotesBean noteBean = new NotesBean();
		ArrayList<NotesBean> nList = noteBean.getBeans(qString);
		for(NotesBean n : nList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(n.getDescription());
			// Interest only with Balloon at end of term
			if( n.getNoteType().equals("I")){
				double value = n.getAssetValue();
				for( int i=0; i < n.getYears(); i++) {
					lineObj.setValue(i, value);
				}
			} else if( n.getNoteType().equalsIgnoreCase("A")) { // Amoritize
				double payment = Function.PMT(n.getAssetValue(),n.getAssetIncome()/n.getPaymentsPerYear(),
						n.getYears()* n.getPaymentsPerYear());
				double balance = n.getAssetValue();
				for( int i = 0; i < n.getYears(); i++) {
					for( int j = 0; j < n.getPaymentsPerYear(); j++) {
						double ci = balance * (n.getAssetIncome()/n.getPaymentsPerYear());
						balance = balance - (payment - ci);
					}
					lineObj.setValue(i,balance);
				}
			} else {
				for(int i = 0; i < n.getYears(); i++) {
					lineObj.setValue(i,n.getAssetValue());
				}
			}
			networth.add(lineObj);
		}
	}
	
	public void loadRealEstate() {
		String qString = RealEstateBean.OWNER_ID + "='" + client.getId() + "'";
		RealEstateBean realEstate = new RealEstateBean();
		ArrayList<RealEstateBean> rList = realEstate.getBeans(qString);
		for(RealEstateBean r : rList) {
			double value = r.getValue();
			double growth = r.getAssetGrowth();
			LineObject lineObj = new LineObject();
			lineObj.setDescription(r.getDescription());
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				value += value * growth;
			}
			networth.add(lineObj);
			if(r.getLoanBalance()> 0) {
				double cBalance = r.getLoanBalance();
				double cRate = r.getLoanInterest()/12.0;
				double term = r.getLoanTerm();
				double remainingMonths = term;
				double endBalance;
				double principal = r.getLoanPayment()*r.getLoanFreq();
				double interestSum = 0;
				int years = (int) Math.floor(term/12);
				for( int i = 0; i< 12; i++) {
					interestSum += Function.IPMT2(cRate,i,remainingMonths,r.getLoanBalance());
				}
				endBalance = cBalance - (principal - interestSum);
				liability[0] += endBalance/1000;
				remainingMonths -= 12;
				for( int i=1; i < years; i++){
					cBalance = endBalance;
					for(int j=0; j < remainingMonths; j++) {
						interestSum += Function.IPMT2(cRate,i*12+j,remainingMonths,r.getLoanBalance());
					}
					remainingMonths -= 12;
					endBalance = cBalance - (principal - interestSum);
					liability[i] += endBalance/1000;
				}
				
			}
		}
	}
	
	public void loadRetirement() {
		String qString = RetirementBean.OWNER_ID + "='" + client.getId() + "'";
		RetirementBean retirement = new RetirementBean();
		ArrayList<RetirementBean> rList = retirement.getBeans(qString);
		for(RetirementBean r : rList) {
			RetirementCashFlow rcf = new RetirementCashFlow();
			LineObject lineObj = new LineObject();
			lineObj.setDescription(r.getDescription());
			rcf.addPlan(r.getValue(),r.getAssetGrowth());
			double vTable[] = rcf.getTotalValue();
			for(int i= 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.addValue(i, vTable[i]);
			}
			networth.add(lineObj);
		}
	}
	
	public void loadIlliquid() {
		String qString = IlliquidBean.OWNER_ID + "='" + client.getId() + "'";
		IlliquidBean iBean = new IlliquidBean();
		ArrayList<IlliquidBean> iList = iBean.getBeans(qString);
		for(IlliquidBean ill : iList) {
			Asset a = new Asset();
			a.setValue(ill.getValue());
			a.setGrowth(ill.getAssetGrowth());
			LineObject lineObj = new LineObject();
			lineObj.setDescription(ill.getDescription());
			a.genNetWorth();
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
					lineObj.setValue(i,a.getCashFlow(i));
			}
			networth.add(lineObj);
		}
	}
	
	public void loadPersonal() {
		String qString = PropertyBean.OWNER_ID + "='" + client.getId() + "'";
		PropertyBean propBean = new PropertyBean();
		ArrayList<PropertyBean> pList = propBean.getBeans(qString);
		for(PropertyBean p : pList) {
			Asset a = new Asset();
			a.setValue(p.getValue());
			a.setGrowth(p.getAssetGrowth());
			a.genNetWorth();
			LineObject lineObj = new LineObject();
			lineObj.setDescription(p.getDescription());
			double vTable[] = a.getCashFlow();
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.setValue(i,vTable[i]);
			}
			networth.add(lineObj);
			
			if( p.getLoanBalance() > 0) {
				double payment = p.getLoanPayment() * p.getLoanFreq();
				double term = p.getLoanTerm();
				for(int i = 0; i < term; i++) {
					double cBalance = p.getLoanBalance();
					double cRate = p.getLoanInterest() / 12.0;
					double remaingMonths = term;
					double endBalance;
					double principal = payment * 12;
					double interestSum = 0;

					int years = (int) Math.floor(term / 12) + 1;
					for (int j = 0; j < 12; j++) {
						interestSum += Function.IPMT2(cRate, j, remaingMonths,
								p.getLoanBalance());
					}

					endBalance = cBalance - (principal - interestSum);
					liability[0] += endBalance;
					remaingMonths -= 12;

					for (int j = 1; j < years; j++) {
						cBalance = endBalance;
						interestSum = 0;
						for (int k = 0; k < 12; k++) {
							interestSum += Function.IPMT2(cRate, j * 12 + k,
									remaingMonths, p.getLoanBalance());
						}
						endBalance = cBalance - (principal - interestSum);
						liability[i] += endBalance;
					}
				}
			}
		}
	}
	
	public void loadBusiness() {
		String qString = BusinessBean.OWNER_ID + "='" + client.getId() + "'";
		BusinessBean bizBean = new BusinessBean();
		ArrayList<BusinessBean> bList = bizBean.getBeans(qString);
		for(BusinessBean b : bList) {
			// need to calculate the income and growth seperate. Also need to express liquidation.
			Asset a = new Asset();
			a.setValue(b.getValue());
			a.setGrowth(b.getAssetGrowth());
			a.setIncome(b.getAssetIncome());
			a.setBasis(b.getBasis());
			a.genNetWorth();
			LineObject lineObj = new LineObject();
			lineObj.setDescription(b.getDescription());
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
					lineObj.setValue(i, a.getCashFlow(i));
			}
			networth.add(lineObj);
		}
	}
	
	public void loadBonds() {
		String qString = BondBean.OWNER_ID + "='" + client.getId() + "'";
		BondBean bondBean = new BondBean();
		ArrayList<BondBean> bList = bondBean.getBeans(qString);
		for(BondBean b : bList) {
			Asset a = new Asset();
			a.setValue(b.getAssetValue());
			a.setGrowth(b.getGrowth());
			a.setIncome(b.getInterest());
			a.setBasis(b.getBasis());
			a.genNetWorth();
			double vTable[] = a.getCashFlow();
			LineObject lineObj = new LineObject();
			lineObj.setDescription(b.getName());
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.setValue(i,vTable[i]);
			}
			networth.add(lineObj);
		}
	}
	
	public void loadSecurities() {
		String qString = SecuritiesBean.OWNER_ID + "='" + client.getId() + "'";
		SecuritiesBean secBean = new SecuritiesBean();
		ArrayList<SecuritiesBean> sList = secBean.getBeans(qString);
		for(SecuritiesBean s : sList) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(s.getDescription());
			Asset a = new Asset();
			a.setValue(s.getValue());
			a.setGrowth(s.getAssetGrowth());
			a.setIncome(s.getAssetIncome());
			a.setBasis(s.getBasis());
			a.genNetWorth();
			double vTable[] = a.getCashFlow();
			for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				lineObj.setValue(i, vTable[i]);
			}
			networth.add(lineObj);
		}
	}
	
	public void loadVCF() {
		String qString = VCFBean.OWNER_ID + "='" + client.getId() + "'"
		+ " and " + VCFBean.CF_FLAG + "='N'";
		VCFBean vcf = new VCFBean();
		ArrayList<VCFBean> vList = vcf.getBeans(qString);
		for(VCFBean v : vList) {
			if(v.getUseFlag().equals("B")||v.getUseFlag().equals("1")){
				VariableCashFlow vCash = new VariableCashFlow();
				vCash.setValues(v);
				LineObject lineObj = new LineObject();
				lineObj.setDescription(v.getDescription());
				double vTable[] = vCash.getVTable();
				for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
					lineObj.setValue(i,vTable[i]);
				}
				networth.add(lineObj);
			}
		}
	}
	
	public void loadLife() {
		String qString = InsuranceBean.OWNER_ID + "='" + client.getId() + "'";
		InsuranceBean lifeBean = new InsuranceBean();
		ArrayList<InsuranceBean> life = lifeBean.getBeans(qString);
		for(InsuranceBean l : life) {
			LineObject lineObj = new LineObject();
			lineObj.setDescription(l.getDescription());
			double v = l.getValue();
			double f = l.getFutureCashValue();
			double amt = (f-v)/10.0;
			double faceValue = l.getFaceValue();
			for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
				if(v > faceValue)
					v = faceValue;
				lineObj.setValue(i,v);
				v += amt;
			}
			networth.add(lineObj);
		}
	}


	public PersonBean getClient() {
		return client;
	}


	public void setClient(PersonBean client) {
		this.client = client;
	}


	public double[] getLiability() {
		return liability;
	}


	public void setLiability(double[] liability) {
		this.liability = liability;
	}


	public ArrayList<LineObject> getNetworth() {
		return networth;
	}


	public void setNetworth(ArrayList<LineObject> networth) {
		this.networth = networth;
	}


	public PersonBean getSpouse() {
		return spouse;
	}


	public void setSpouse(PersonBean spouse) {
		this.spouse = spouse;
	}


	public double[] getTotal() {
		return total;
	}


	public void setTotal(double[] total) {
		this.total = total;
	}


	public boolean isUseFiscalYear() {
		return useFiscalYear;
	}


	public void setUseFiscalYear(boolean useFiscalYear) {
		this.useFiscalYear = useFiscalYear;
	}


	public PdfBean getUserInfo() {
		return userInfo;
	}


	public void setUserInfo(PdfBean userInfo) {
		this.userInfo = userInfo;
	}


	public CashFlowBean getCfb() {
		return cfb;
	}


	public void setCfb(CashFlowBean cfb) {
		this.cfb = cfb;
	}
	
	
}

