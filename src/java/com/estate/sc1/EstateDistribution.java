package com.estate.sc1;

import com.teag.bean.*;
import com.estate.sc.utils.*;
import com.estate.constants.*;
import java.util.*;
import com.zcalc.*;

public class EstateDistribution {

	
	PersonBean client;
	PersonBean spouse;
	PdfBean userInfo;
	LinkedList<LineObject> estate = new LinkedList<LineObject>();
	
	double estateNetWorth[] = new double[ScenarioConstants.MAX_TABLE];
	double taxableEstate[] = new double[ScenarioConstants.MAX_TABLE];
	double retirementValue[] = new double[ScenarioConstants.MAX_TABLE];
	double totalTax[] = new double[ScenarioConstants.MAX_TABLE];
	double totalLife[] = new double[ScenarioConstants.MAX_TABLE];
	double toFamily[] =  new double[ScenarioConstants.MAX_TABLE];
	double toCharity[] =  new double[ScenarioConstants.MAX_TABLE];
	double costs[] = new double[ScenarioConstants.MAX_TABLE];
	double additionsToEstate[] = new double[ScenarioConstants.MAX_TABLE];
	
	int startYear;
	boolean useFiscalYear = true;
	CashFlowBean cfb;
	
	public void init() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		startYear = cal.get(Calendar.YEAR) +1;
		
		//adminCosts();
		getEstateVCF();
		getLife();
		addRetirement();		// For Tax calculations
		abTrust();
		// We should have the taxable estate here!
		// Calculate the Estate Tax, Federal and State IRD
		calculateTaxes();
		// Calculate the family share of the estate, as well as an inflation
		netToFamily();
		netToCharity();
	}
	
	public void adminCosts() {
		
	}
	
	public void getEstateVCF() {
		String queryString = VCFBean.OWNER_ID + "='" + client.getId() + "'"
			+ " and " + VCFBean.CF_FLAG + "='T'";
			// Variable Cash Flow Items
		VCFBean vAsset = new VCFBean();
		ArrayList<VCFBean> vList = vAsset.getBeans(queryString);
		for (VCFBean v : vList) {
			if(v.getUseFlag().equals("B") || v.getUseFlag().equals("1")) {
				VariableCashFlow vcf = new VariableCashFlow();
				vcf.setValues(v);
				LineObject lineObj = new LineObject();
				lineObj.setDescription(v.getDescription());
				double vTable[] = vcf.getVTable();
				for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
					lineObj.setValue(i, vTable[i]);
					taxableEstate[i] += vTable[i];
					additionsToEstate[i] += vTable[i];
				}
				estate.add(lineObj);
			}
		}
	}
	
	public void abTrust() {
		ABTrust abTrust = new ABTrust();
		abTrust.query(client.getId());
		
		int year = startYear;
		double nTrusts = abTrust.getBeforeTrusts();
		LineObject lineObj = new LineObject();
		lineObj.setF(TeagFont.ItalicBlack);
		lineObj.setDescription("Exemptions (" + Integer.toString((int)nTrusts) + ")");
		if( abTrust.getUsed().equals("1") || abTrust.getUsed().equals("B")) {
			zCalc zc = new zCalc();
			zc.StartUp();
			for( int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
				double exempt = zc.zAPPEXCLUSION(year, 0,0,0) * nTrusts;
				lineObj.setValue(i,-exempt);
				taxableEstate[i] += -exempt;
				year++;
			}
			zc.ShutDown();
		}
		estate.add(lineObj);
	}
	
	
	public void getFamilyVCF() {
		String queryString = VCFBean.OWNER_ID + "='" + client.getId() + "'"
		+ " and " + VCFBean.CF_FLAG + "='F'";
		// Variable Cash Flow Items
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
					toFamily[i] += vTable[i];
				}
				estate.add(lineObj);
			}
		}
	}
	
	public void stateFreeze(int freeze) {
		LineObject lineObj = new LineObject();
		lineObj.setDescription("State Death Tax");
		int year = startYear;
		zCalc zc = new zCalc();
		zc.StartUp();
		for(int i = 0, yr = startYear; i < ScenarioConstants.MAX_TABLE; i++, yr++){
			double sdt = zc.zSDT(taxableEstate[i],0, 0, 0, year++,0, 0, 0,0, 0, freeze);
			lineObj.setValue(i,-sdt);
			taxableEstate[i] -= sdt;
			costs[i] += sdt;
		}
		estate.add(lineObj);
		zc.ShutDown();
	}
	
	public void getLife() {
		String qString = InsuranceBean.OWNER_ID + "='" + client.getId() + "'";
		InsuranceBean life = new InsuranceBean();
		ArrayList<InsuranceBean> list = life.getBeans(qString);
		LineObject lineObj = new LineObject();
		lineObj.setDescription("Life Insurance");
		for(InsuranceBean l : list) {
			double v = l.getValue();
			double f = l.getFutureCashValue();
			double amt = (f-v)/10.0;
			double faceValue = l.getFaceValue();
			for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
				if(v > faceValue)
					v = faceValue;
				lineObj.addValue(i, faceValue - v);
				taxableEstate[i] += faceValue - v;
				totalLife[i] += faceValue -v;	// We need to add this back into the family calculation
				v += amt;
			}
		}
		estate.add(lineObj);
	}
	
	public void addRetirement() {
		String qString = RetirementBean.OWNER_ID + "='" + client.getId() + "'";
		RetirementBean retire = new RetirementBean();
		ArrayList<RetirementBean> rList = retire.getBeans(qString);
		RetirementCashFlow rcf = new RetirementCashFlow();
		for(RetirementBean r : rList) {
			rcf.addPlan(r.getValue(), r.getAssetGrowth());
		}
		double total[] = rcf.getTotalValue();
		for(int i=0; i < ScenarioConstants.MAX_TABLE; i++){
			retirementValue[i] = total[i];
		}
	}
	
	
	public void netToFamily() {
		getFamilyVCF();
		LineObject lineObj = new LineObject();
		lineObj.setDescription("Net To Family");
		lineObj.setF(TeagFont.BoldGreen);
		for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
			lineObj.setValue(i,estateNetWorth[i]);
			lineObj.addValue(i,additionsToEstate[i]);
			lineObj.addValue(i,totalLife[i]);
			lineObj.addValue(i,-costs[i]);
			lineObj.addValue(i,-totalTax[i]);
			lineObj.addValue(i,toFamily[i]);
			toFamily[i] = lineObj.getValue(i) * 1000;

		}
		estate.add(lineObj);
		LineObject inflation = new LineObject();
		inflation.setF(TeagFont.BoldGreen);
		inflation.setDescription("What is this really worth? inflation adjusted 3%");
		for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
			double amt = toFamily[i] * Math.pow(1.03, -i);
			inflation.setValue(i,amt);
		}
		estate.add(inflation);
	}
	
	// We need to fill this in from the Cash Disbursements, or from the vcf
	// records.
	public void netToCharity() {
		String queryString = VCFBean.OWNER_ID + "='" + client.getId() + "'"
		+ " and " + VCFBean.CF_FLAG + "='X'";
		// Variable Cash Flow Items
		VCFBean vAsset = new VCFBean();
		ArrayList<VCFBean> vList = vAsset.getBeans(queryString);
		for (VCFBean v : vList) {
			if(v.getUseFlag().equals("B") || v.getUseFlag().equals("1")){
				VariableCashFlow vcf = new VariableCashFlow();
				vcf.setValues(v);
				LineObject lineObj = new LineObject();
				lineObj.setDescription(v.getDescription());
				lineObj.setF(TeagFont.BoldBlue);
				double vTable[] = vcf.getVTable();
				for (int i = 0; i < ScenarioConstants.MAX_TABLE; i++) {
					lineObj.setValue(i, vTable[i]);
					toCharity[i] += vTable[i];
				}
				estate.add(lineObj);
			}
		}
		
		LineObject charityLine = new LineObject();
		charityLine.setF(TeagFont.BoldBlue);
		charityLine.setDescription("To Charity");
		for(int i= 0; i < ScenarioConstants.MAX_TABLE; i++) {
			charityLine.setValue(i,toCharity[i]*1000);
		}
		estate.add(charityLine);
		
		// If we want to accumulate the charity do it here....
		LineObject accCharity = new LineObject();
		accCharity.setF(TeagFont.BoldBlue);
		accCharity.setDescription("To Charity (Accum @3%)");
		double accValue = toCharity[0];
		for(int i=0; i < ScenarioConstants.MAX_TABLE; i++){
			accValue += toCharity[i];
			accCharity.setValue(i, accValue*1000);
			accValue = 1.03 * accValue;
		}
		estate.add(accCharity);
	}

	public void calculateTaxes() {
		LineObject fet = new LineObject();
		LineObject fit = new LineObject();
		LineObject sit = new LineObject();
		LineObject tTax = new LineObject();
		
		
		fet.setF(TeagFont.ItalicRed);
		fit.setF(TeagFont.ItalicRed);
		sit.setF(TeagFont.ItalicRed);
		tTax.setF(TeagFont.ItalicRed);
		
		fet.setDescription("Federal Estate Tax");
		fit.setDescription("Federal Income Tax");
		sit.setDescription("State Income Tax");
		tTax.setDescription("Total Taxes at 2nd Death");
		
		LineObject lineObj = new LineObject();
		lineObj.setF(TeagFont.BoldBlack);
		lineObj.setDescription("Taxable Estate");
		for(int i= 0; i < ScenarioConstants.MAX_TABLE; i++) {
			lineObj.setValue(i, taxableEstate[i]);
		}
		
		estate.add(lineObj);
		
		for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
			CalcEstateTaxes cet = new CalcEstateTaxes();
			cet.setStateTaxRate(cfb.getStateTaxRate());
			cet.setRpBalance(retirementValue[i]);
			cet.setYear(startYear + i);
			cet.setTaxableEstate(taxableEstate[i]);
			cet.calc();
			fet.setValue(i,cet.getEstateTax());
			fit.setValue(i,cet.getFederalIncomeTax());
			sit.setValue(i,cet.getStateTax());
			totalTax[i] = cet.getEstateTax() + cet.getFederalIncomeTax() + 
				cet.getStateTax();
			tTax.setValue(i,totalTax[i]);
		}
		estate.add(fet);
		estate.add(fit);
		estate.add(sit);
		estate.add(tTax);
	}
	
	
	public void setEstateNetWorth(double[] totals){
		LineObject lineObj = new LineObject();
		lineObj.setDescription("Estate Net Worth");
		lineObj.setF(TeagFont.BoldBlack);
		for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
			estateNetWorth[i] = totals[i]*1000;
			taxableEstate[i] = totals[i]*1000;
			lineObj.setValue(i,totals[i]*1000);
		}
		estate.add(lineObj);
	}
	
	// We grow this at 6% each year.
	public void addCharity(double[] total) {
		boolean optionA = false;
		double accum = 0;
		for(int i = 0; i < ScenarioConstants.MAX_TABLE; i++ ){
			if(optionA)
				accum *= 1.06;
			else 
				accum = 0;
			toCharity[i] = (total[i]) + accum;
			accum = toCharity[i];
		}
	}

	public void setClient(PersonBean client) {
		this.client = client;
	}

	public LinkedList<LineObject> getEstate() {
		return estate;
	}

	public void setSpouse(PersonBean spouse) {
		this.spouse = spouse;
	}

	public double[] getTaxableEstate() {
		return taxableEstate;
	}

	public double[] getToCharity() {
		return toCharity;
	}

	public void setToCharity(double[] toCharity) {
		this.toCharity = toCharity;
	}

	public double[] getToFamily() {
		return toFamily;
	}

	public double[] getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double[] totalTax) {
		this.totalTax = totalTax;
	}

	public boolean isUseFiscalYear() {
		return useFiscalYear;
	}

	public void setUseFiscalYear(boolean useFiscalYear) {
		this.useFiscalYear = useFiscalYear;
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
