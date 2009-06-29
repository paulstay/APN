package com.teag.scenario1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.estate.db.DBObject;
import com.teag.bean.BondBean;
import com.teag.bean.BusinessBean;
import com.teag.bean.CashBean;
import com.teag.bean.GiftBean;
import com.teag.bean.NotePayableBean;
import com.teag.bean.RealEstateBean;
import com.teag.bean.RetirementBean;
import com.teag.bean.SecuritiesBean;
import com.teag.bean.VCFBean;
import com.teag.util.Function;

public class ScenarioOne {
	DBObject dbObject = new DBObject();
	CashFlowVars cashFlowVars;
	
	ArrayList<LineObject> cashReceipts = new ArrayList<LineObject>();
	ArrayList<LineObject> cashDisbursements = new ArrayList<LineObject>();
	ArrayList<LineObject> portfolio = new ArrayList<LineObject>();
	
	LineObject taxLine;
	LineObject portFolioBegin;
	LineObject portFolioExcessCash;
	LineObject portFolioGrowth;
	LineObject portFolioEnd;
	LineObject excessCash;
	LineObject socialSecurity;
	
	RealEstateCashFlow realEstate = new RealEstateCashFlow();
	
	public void cashDisbursements() {
		cashDisbursements.addAll(dVCF());
		cashDisbursements.add(dGifts());
	}
	
	public void cashFlowProjection() {
		// loop through each year and calcualte excess cash, tax and portfolio values
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		for( int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
			double totalCashR = 0;
			double taxableCashR = 0.0;
			double dedCashD = 0;
			for(LineObject c : cashReceipts) {
				double ptax = c.getPercentTaxable();
				taxableCashR += c.getValue(i) * ptax;
				totalCashR += c.getValue(i);
			}
			
			for(LineObject d : cashDisbursements) {
				if( d.isCharitableDeduction()) {
					dedCashD += d.getValue(i);
				}
			}
			CalcCashFlowTax cft = new CalcCashFlowTax();
			cft.stateAGI = taxableCashR;
			cft.charitableDed = dedCashD;
			cft.capTaxRate = .3;
			cft.crutCapGains = 0;
			cft.socialSecurity = socialSecurity.getValue(i);
			
			
			
		}
	}
	
	public void cashReceipts() {
		cashReceipts.addAll(cVCF());
		cashReceipts.add(cCash());
		cashReceipts.add(cRetirement());
		cashReceipts.add(cSecurities());
		cashReceipts.add(cRealEstate());
		cashReceipts.add(cNotesPayable());
		cashReceipts.add(cBonds());
		cashReceipts.add(cBusiness());
		socialSecurity = cSocialSecurity();
	}
	
	public LineObject cBonds() {
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Interest on Municipal Bonds");
		BondBean c = new BondBean();
		ArrayList<BondBean> cList = c.getBeans(BondBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'" );
		for(BondBean cb : cList) {
			double value = cb.getCurrentValue();
			double iRate = cb.getInterest();
			for(int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
				line.addValue(i, value * iRate);
			}
		}
		return line;
	}
	
	public LineObject cBusiness() {
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Business Income");
		BusinessBean b = new BusinessBean();
		String whereClause = BusinessBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'";
		ArrayList<BusinessBean> bList = b.getBeans(whereClause);
				
		for(BusinessBean bb : bList) {
			double income = bb.getAnnualIncome();
			double incomeGrowth = bb.getIncomeGrowth();
			for(int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
				line.addValue(i, income);
				income += income * incomeGrowth;
			}
		}
		return line;
	}
	
	public LineObject cCash() {
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Interest on Cash and Equivalents");
		CashBean c = new CashBean();
		ArrayList<CashBean> cList = c.getBeans(CashBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'" );
		for(CashBean cb : cList) {
			double value = cb.getCurrentValue();
			double iRate = cb.getInterest();
			for(int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
				line.addValue(i, value * iRate);
			}
		}
		
		return line;
	}

	public LineObject cNotesPayable() {
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Notes Payable");
		NotePayableBean pb = new NotePayableBean();
		String whereClause = NotePayableBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'";
		ArrayList<NotePayableBean> bList = pb.getBeans(whereClause);
		for(NotePayableBean n : bList) {
			if(n.getNoteType().equals("I")){
				for( int i = 0; i < n.getYears(); i++) {
					line.addValue(i, n.getLoanAmount() * n.getInterestRate());
				}
				line.addValue(n.getYears()-1, n.getLoanAmount());
			} else {
				double payment = Function.PMT(n.getLoanAmount(), n.getInterestRate() / n.getPaymentsPerYear(), 
						n.getYears() * n.getPaymentsPerYear()) * n.getPaymentsPerYear();
				for( int i=0; i < n.getYears(); i++) {
					line.addValue(i,payment);
				}
			}
		}
		return line;
	}
	
	public LineObject cRealEstate() {
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Real Estate Income");
		RealEstateBean r = new RealEstateBean();
		ArrayList<RealEstateBean> rList = r.getBeans(RealEstateBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'");
		for(RealEstateBean rb : rList) {
			realEstate.addRealEstate(rb);
		}
		for( int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
			line.addValue(i, realEstate.income[i]);
		}
		return line;
	}
	
	public LineObject cRetirement() {
		RetirementCashFlow rcf = new RetirementCashFlow();
		rcf.setClientAge(cashFlowVars.getCAge());
		if( !cashFlowVars.isSingle) {
			rcf.setSpouseAge(cashFlowVars.getSAge());
		}
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Retirement Portfolio");
		
		RetirementBean r = new RetirementBean();
		ArrayList<RetirementBean> rList = r.getBeans(RetirementBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'");
		for(RetirementBean rb : rList) {
			double value = rb.getValue();
			double growth = rb.getGrowthRate();
			rcf.addPlan(value, growth);
		}
		double distTable[] = rcf.getDispersements();
		for(int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
			line.setValue(i,distTable[i]);
		}
		return line;
	}
	
	public LineObject cSecurities() {
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Int/Div on Securities Portfolio");
		SecuritiesBean c = new SecuritiesBean();
		ArrayList<SecuritiesBean> cList = c.getBeans(SecuritiesBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'" );
		for(SecuritiesBean cb : cList) {
			double value = cb.getValue();
			double iRate = cb.getDivInt();
			double growth = cb.getGrowthRate();
			for(int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
				value = value + (value * growth);
				line.addValue(i, value * iRate);
			}
		}
		return line;
	}
	
	public LineObject cSocialSecurity() {
		LineObject line = null;

		if( cashFlowVars.useSocialSecurity) {
			line = new LineObject(cashFlowVars.getFinalDeath());
			line.setDescription("Social Security");
			int cAge = cashFlowVars.getCAge();
			int sAge = -1;
			int bSocial = cashFlowVars.getClientStartSocial();
			int sSocial = cashFlowVars.getSpouseStartSocial();
			double socialSecurity = cashFlowVars.getSocialSecurity();
			double sInc = cashFlowVars.getSocialSecurityGrowth();
			
			if( !cashFlowVars.isSingle) {
				sAge = cashFlowVars.getSAge();
			}
			for (int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
				double totalSS = 0;
				if (cAge++ >= bSocial) {
					totalSS += 12 * socialSecurity;
				}
				if (sAge++ >= sSocial) {
					totalSS += (12 * socialSecurity) * .50;
				}
				if (cAge > bSocial || sAge>  sSocial) {
					socialSecurity += socialSecurity * sInc;
				}
				line.addValue(i, totalSS);
			}
			
		}
		return line;
	}
	
	public ArrayList<LineObject> cVCF() {
		ArrayList<LineObject> list = new ArrayList<LineObject>();
		VCFBean v = new VCFBean();
		v.setDbObject();
		String whereClause = VCFBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'";
		
		ArrayList<VCFBean> vList = v.getBeans(whereClause);
		for(VCFBean vcf : vList) {
			if(vcf.getCfFlag().equals("C")) {
				LineObject line = new LineObject(cashFlowVars.getFinalDeath());
				line.setDescription(vcf.getDescription());
				VariableCashFlow vc = new VariableCashFlow();
				vc.setValues(vcf);
				vc.initialize();
				double[] table = vc.getVTable();
				for( int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
					line.addValue(i, table[i] * vc.getPercentTaxable());
				}
				list.add(line);
			}
		}
		return list;
	}
	
	
	public LineObject dGifts() {
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		int currentYear = calendar.get(Calendar.YEAR);
		
		LineObject line = new LineObject(cashFlowVars.getFinalDeath());
		line.setDescription("Gifts");
		line.setCharitableDeduction(false);
		GiftBean g = new GiftBean();
		String whereClause = GiftBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'";
		ArrayList<GiftBean> gList = g.getBeans(whereClause);
		for(GiftBean gb : gList) {
			int freq = 1;
			double gift = gb.getAmount();
			if(gb.getRegularity().equals("B")) {
				freq = 2;
			}
			if( gb.getRegularity().equals("T")) {
				freq = 3;
			}
			if( gb.getRegularity().equals("0")) {
				freq = 0;
			}
			
			Date date;
			try {
				date = df.parse(gb.getDate());
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
			
			if( freq == 0 && idx >= 0) {
				line.addValue(idx, gift);
				/*
					zCalc zc = new zCalc();
					zc.StartUp();
					double gTax = zc.zFGT(gift, exemptionUsed, 0, 0,
							currentYear + idx, 0, 0);
					zc.ShutDown();
					giftTax[idx] += gTax;
				*/
				continue;
			}
			
			for (int j = 0; j < cashFlowVars.getFinalDeath(); j += freq) {
				line.addValue(j, gift);
				/*
				exemptionUsed += gift;
				zCalc zc = new zCalc();
				zc.StartUp();
				double gTax = zc.zFGT(gift, exemptionUsed, 0, 0,
						currentYear + j, 0, 0);
				zc.ShutDown();
				giftTax[j] += gTax;
				*/
			}
		}
		return line;
	}
	
	public ArrayList<LineObject> dVCF() {
		ArrayList<LineObject> list = new ArrayList<LineObject>();
		VCFBean v = new VCFBean();
		String whereClause = VCFBean.OWNER_ID + "='" + cashFlowVars.getClient().getId() + "'";
		
		ArrayList<VCFBean> vList = v.getBeans(whereClause);
		for(VCFBean vcf : vList) {
			if(vcf.getCfFlag().equals("D")){
				LineObject line = new LineObject(cashFlowVars.getFinalDeath());
				line.setDescription(vcf.getDescription());
				VariableCashFlow vc = new VariableCashFlow();
				vc.setValues(vcf);
				vc.initialize();
				if(vc.getCdType().equals('Y')){
					line.setCharitableDeduction(true);
				}
				double[] table = vc.getVTable();
				for( int i = 0; i < cashFlowVars.getFinalDeath(); i++) {
					line.addValue(i, table[i]);
				}
				list.add(line);
			}
		}
		return list;
	}
	
	public ArrayList<LineObject> getCash() {
		return cashReceipts;
	}
	
	public CashFlowVars getCashFlowVars() {
		return cashFlowVars;
	}
	
	public void initialize() {
		taxLine = new LineObject(cashFlowVars.getFinalDeath());
		portFolioBegin = new LineObject(cashFlowVars.getFinalDeath());
		portFolioExcessCash = new LineObject(cashFlowVars.getFinalDeath());
		portFolioGrowth = new LineObject(cashFlowVars.getFinalDeath());
		portFolioEnd = new LineObject(cashFlowVars.getFinalDeath());
		excessCash = new LineObject(cashFlowVars.getFinalDeath());
	}

	public void setCashFlowVars(CashFlowVars cashFlowVars) {
		this.cashFlowVars = cashFlowVars;
	}
}
