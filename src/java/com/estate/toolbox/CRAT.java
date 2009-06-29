/**
 * 	Charitable Remainder Trust
 * 	Author - Paul Stay
 * 	The Estate Advisory Group @2008
 */
	

package com.estate.toolbox;

import java.util.Date;

import com.zcalc.zCalc;

public class CRAT {
	double agiLimitation = .3;
	double irsRate;
	Date irsDate;
	double payoutRate;
	double agi;
	double maxPayout;
	int clientAge;
	int spouseAge;
	double fmv;
	double liability;
	double basis;
	double capitalGainsTax;
	double finalDeath;
	double estateTaxRate;
	double marginalTaxRate;
	double investmentReturn;
	double charitableDeductionFactor;
	double charitableDeduction;
	
	String crtType;
	int pmtPeriod = 4;		// Default is quarterly
	int uniLag = 3;
	double taxGrowth;
	double taxIncome;
	String payoutOption = "P";		// L = Level, M= Match, P = Percentage This is for outright sale....
	
	// For CRAT Calculations
	double annuityFactor;
	double optimalRate;
	double exhaustion;
	double annuityInterest;
	double remainderInterest;
	

	double outrightSale = 0;
	double capitalGains = 0;
	
	double insurancePremium;
	double insuranceBenefit;
	
	double clientLe =0;
	double spouseLe = 0;
	
	double noCrt[][];
	

	double crt[][];

	public void calcCrat() {
		zCalc zc = new zCalc();
		zc.StartUp();
			double payout = payoutRate * fmv;
			annuityFactor = zc.zTERMLIFE(1, irsRate, 0, clientAge, spouseAge, 0, 0, 0, 0, pmtPeriod, 0, 0, 0, 0);
			optimalRate = zc.zCRATMAX(irsRate, 0, clientAge, spouseAge, 0, 0, 0, 0, pmtPeriod, 0, 0, 0, 0);
			exhaustion = zc.zCRATEX(fmv, payout, irsRate, 0, clientAge, spouseAge, 0, 0, 0, pmtPeriod, 0, 0, 0, 0);
			annuityInterest = zc.zANNTERMLIFE(fmv, payout, irsRate, 0, clientAge, spouseAge, 0, 0, 0, 0, pmtPeriod, 0, 0, 0, 0, 0);
			remainderInterest = fmv - annuityInterest;
			charitableDeductionFactor = (remainderInterest)/fmv;
			clientLe = zc.zLE(clientAge, 0, 0, 0, 0, 0, 0);
			spouseLe = zc.zLE(spouseAge, 0, 0, 0, 0, 0, 0);
		zc.ShutDown();
	}
	
	public void calcCRTValues() {
		crt = new double[(int)finalDeath][6];
		double cDed = charitableDeductionFactor * (fmv - liability);
		double taxRate = (taxIncome/payoutRate * marginalTaxRate) + (taxGrowth/payoutRate * capitalGainsTax);
		
		crt[0][5] = fmv - liability;
		
		for(int i=1; i < finalDeath; i++){
			double pay = 0;
			if( crtType.equals("CRAT")) {
				pay = crt[0][5] * payoutRate;
			} else {
				pay = crt[i-1][5] * payoutRate;
			}
			double cDeduction = 0;
			double tax = 0;
			
			if(i < 6) {
				cDeduction = Math.round(Math.min((agi+pay) * agiLimitation , cDed)/1000.0)*1000;
				cDed = Math.max(cDed - cDeduction, 0);
			} else {
				cDeduction = 0;
			}
			
			crt[i][0] = pay;
			crt[i][1] = cDeduction;
			crt[i][2] = insurancePremium;
			tax = (pay - cDeduction) * taxRate;
			crt[i][3] = tax;								// Tax
			crt[i][4] = pay - tax - insurancePremium;		// netSpendable 
			crt[i][5] = crt[i-1][5] *(1 + investmentReturn);
			crt[i][5] -= pay;
		}
	}

	public void calcOutRightSale() {
		noCrt = new double [(int)finalDeath][4];
		double taxRate = (taxIncome * marginalTaxRate) + (taxGrowth * capitalGainsTax);
		
		// First we figure out the capital gains....
		capitalGains = (fmv - (liability + basis) ) * capitalGainsTax;
		noCrt[0][3]= fmv - capitalGains - liability;
		
		for(int i=1; i < finalDeath; i++) {
			noCrt[i][0] = noCrt[i-1][3] * investmentReturn;
			noCrt[i][1] = (noCrt[i-1][3] * taxIncome * marginalTaxRate) + (noCrt[i-1][3] * taxGrowth * capitalGainsTax);
			if( payoutOption.equals("P")) {
				noCrt[i][2] = noCrt[i-1][3] * (payoutRate * (1 - taxRate));
			} else if(payoutOption.equals("M")) {
				noCrt[i][2] = crt[i][0]; 
			} else {
				noCrt[i][2] = noCrt[i][0] - noCrt[i][1];
			}
			noCrt[i][3] = (noCrt[i-1][3] * (1 + investmentReturn)) - noCrt[i][2] - noCrt[i][1];
		}
	}
	
	
	public void calculate() {
		calcCrat();

		charitableDeduction = (fmv - liability)* charitableDeductionFactor;
		calcCRTValues();
		calcOutRightSale();
	}
	
	public double getAgi() {
		return agi;
	}
	
	public double getAgiLimitation() {
		return agiLimitation;
	}
	
	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public double getAnnuityInterest() {
		return annuityInterest;
	}

	public double getBasis() {
		return basis;
	}

	public double getCapitalGains() {
		return capitalGains;
	}

	public double getCapitalGainsTax() {
		return capitalGainsTax;
	}

	public double getCharitableDeduction() {
		return charitableDeduction;
	}

	public double getCharitableDeductionFactor() {
		return charitableDeductionFactor;
	}

	public int getClientAge() {
		return clientAge;
	}

	public double getClientLe() {
		return clientLe;
	}

	public double[][] getCrt() {
		return crt;
	}

	public double getCRTNetSpendable() {
		double net = 0;
		for(int i =0; i < finalDeath; i++) {
			net += crt[i][4];
		}
		
		return net;
	}

	public String getCrtType() {
		return crtType;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getExhaustion() {
		return exhaustion;
	}

	public double getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public double getInsuranceBenefit() {
		return insuranceBenefit;
	}

	public double getInsurancePremium() {
		return insurancePremium;
	}

	public double getInvestmentReturn() {
		return investmentReturn;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double getLiability() {
		return liability;
	}

	public double getMarginalTaxRate() {
		return marginalTaxRate;
	}

	public double getMaxPayout() {
		return maxPayout;
	}

	public double[][] getNoCrt() {
		return noCrt;
	}

	public double getOptimalRate() {
		return optimalRate;
	}

	public double getOutNetSpendable() {
		double net = 0;
		for(int i=0; i < finalDeath; i++) {
			net += noCrt[i][2];
		}
		return net;
	}

	public double getOutrightSale() {
		return outrightSale;
	}

	public String getPayoutOption() {
		return payoutOption;
	}

	public double getPayoutRate() {
		return payoutRate;
	}

	public int getPmtPeriod() {
		return pmtPeriod;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public double getSpouseLe() {
		return spouseLe;
	}

	public double getTaxGrowth() {
		return taxGrowth;
	}

	public double getTaxIncome() {
		return taxIncome;
	}

	public int getUniLag() {
		return uniLag;
	}

	public void setAgi(double agi) {
		this.agi = agi;
	}

	public void setAgiLimitation(double agiLimitation) {
		this.agiLimitation = agiLimitation;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityInterest(double annuityInterest) {
		this.annuityInterest = annuityInterest;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setCapitalGains(double capitalGains) {
		this.capitalGains = capitalGains;
	}

	public void setCapitalGainsTax(double capitalGainsTax) {
		this.capitalGainsTax = capitalGainsTax;
	}

	public void setCharitableDeduction(double charitableDeduction) {
		this.charitableDeduction = charitableDeduction;
	}

	public void setCharitableDeductionFactor(double charitableDeductionFactor) {
		this.charitableDeductionFactor = charitableDeductionFactor;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public void setClientLe(double le) {
		clientLe = le;
	}

	public void setCrt(double[][] crt) {
		this.crt = crt;
	}

	public void setCrtType(String crtType) {
		this.crtType = crtType;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setExhaustion(double exhaustion) {
		this.exhaustion = exhaustion;
	}

	public void setFinalDeath(double finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setInsuranceBenefit(double insuranceBenefit) {
		this.insuranceBenefit = insuranceBenefit;
	}

	public void setInsurancePremium(double insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

	public void setInvestmentReturn(double investmentReturn) {
		this.investmentReturn = investmentReturn;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setLiability(double liability) {
		this.liability = liability;
	}

	public void setMarginalTaxRate(double marginalTaxRate) {
		this.marginalTaxRate = marginalTaxRate;
	}

	public void setMaxPayout(double maxPayout) {
		this.maxPayout = maxPayout;
	}

	public void setNoCrt(double[][] noCrt) {
		this.noCrt = noCrt;
	}

	public void setOptimalRate(double optimalRate) {
		this.optimalRate = optimalRate;
	}

	public void setOutrightSale(double outrightSale) {
		this.outrightSale = outrightSale;
	}

	public void setPayoutOption(String payoutOption) {
		this.payoutOption = payoutOption;
	}

	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public void setPmtPeriod(int pmtPeriod) {
		this.pmtPeriod = pmtPeriod;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}

	public void setSpouseLe(double le) {
		spouseLe = le;
	}

	public void setTaxGrowth(double taxGrowth) {
		this.taxGrowth = taxGrowth;
	}

	public void setTaxIncome(double taxIncome) {
		this.taxIncome = taxIncome;
	}

	public void setUniLag(int uniLag) {
		this.uniLag = uniLag;
	}

	
}
