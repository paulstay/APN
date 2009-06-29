package com.estate.toolbox;

import com.zcalc.zCalc;

public class Mgen {

	private int yearsPerGeneration;
	private double totalValue;
	private double growth;
	private double income;
	private double turnoverRate;
	private double payoutRate;
	private double trusteeRate;
	private double inflationRate;
	private double combinedIncomeTaxRate;
	private double capitalGainsRate;
	private double delawareCombinedTaxRate;
	private double delawareCapitalGainsRate;
	private int lifeExpectancy; 	// Large of the client and the spouse;
	private int currentYear;
	private String trustState;
	private boolean lawOfPerpetuity;
	private int term;
	
	private boolean grantor = true;
	private boolean useLifeInsurance;
	private boolean useCrummey;
	
	private double lifePremium;
	private double lifeDeathBenefit;
	
	private double crummeyInterestRate;
	
	boolean securities = false;
	
	double keepInEstate[][] = new double[4][5];  	// Row = generation, Col = Bgn Amt, Ending Amt, Estate tax, Sum Income, # years
	double currentStateTrust[][] = new double[4][5];
	double delawareTrust[][] = new double[4][5];
	
	public void calcDelawareTrust() {
		double value = totalValue;
		double incomeSum = 0;
				
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateDelawareCapGains(iGrowth,1);
			double incomeTax = calculateDelawareIncomeTax(iValue, managementFee, 1);
			value += (iGrowth + iValue - managementFee - capTax - incomeTax);
		}

		delawareTrust[0][0] = totalValue;
		delawareTrust[0][1] = totalValue * Math.pow((1 + growth + income -trusteeRate), lifeExpectancy);
		delawareTrust[0][2] = 0.0;
		delawareTrust[0][3] = 0.0; //For first generation we don't need this.
		delawareTrust[0][3] = lifeExpectancy;

		delawareTrust[1][0] = delawareTrust[0][1] - delawareTrust[0][2];
		value = delawareTrust[1][0];
		incomeSum = 0;

		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateDelawareCapGains(iGrowth,2);
			double incomeTax = calculateDelawareIncomeTax(iValue, managementFee, 2);
			double payOut = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payOut);
		}

		delawareTrust[1][1] = value;
		delawareTrust[1][2] = 0;
		delawareTrust[1][3] = incomeSum/yearsPerGeneration;
		delawareTrust[1][4] = delawareTrust[0][4] + yearsPerGeneration;
		
		delawareTrust[2][0] = delawareTrust[1][1] - delawareTrust[1][2];
		
		value = delawareTrust[2][0];
		incomeSum = 0;
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateDelawareCapGains(iGrowth,3);
			double incomeTax = calculateDelawareIncomeTax(iValue,managementFee,3);
			double payOut = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payOut);
		}
		
		delawareTrust[2][1] = value;
		delawareTrust[2][2] = 0;
		delawareTrust[2][3] = incomeSum/yearsPerGeneration;
		delawareTrust[2][4] = delawareTrust[1][4] + yearsPerGeneration;
		
		delawareTrust[3][0] = delawareTrust[2][1] - delawareTrust[2][2];
		
		value = delawareTrust[3][0];
		incomeSum = 0;
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateDelawareCapGains(iGrowth,4);
			double incomeTax = calculateDelawareIncomeTax(iValue, managementFee, 4);
			double payOut = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payOut);
		}
		
		delawareTrust[3][1] = value;
		delawareTrust[3][2] = 0;
		delawareTrust[3][3] = incomeSum/yearsPerGeneration;
		delawareTrust[3][4] = delawareTrust[2][4] + yearsPerGeneration;
	}
	
	public void calcKeepInEstate() {
		keepInEstate[0][0] = totalValue;
		keepInEstate[0][1] = totalValue * Math.pow((1 + growth + income), lifeExpectancy);
		keepInEstate[0][2] = keepInEstate[0][1] * getEstateTax(currentYear + lifeExpectancy);
		keepInEstate[0][3] = 0.0; //For first generation we don't need this.
		keepInEstate[0][4] = lifeExpectancy;

		keepInEstate[1][0] = keepInEstate[0][1] - keepInEstate[0][2];
		
		double value = keepInEstate[1][0];
		double incomeSum = 0;
		
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = iGrowth * turnoverRate * capitalGainsRate;
			double incomeTax = iValue * combinedIncomeTaxRate;
			double payout = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payout);
		}

		keepInEstate[1][1] = value;
		keepInEstate[1][2] = value * getEstateTax(currentYear + lifeExpectancy + yearsPerGeneration);
		keepInEstate[1][3] = incomeSum/yearsPerGeneration;
		keepInEstate[1][4] = keepInEstate[0][4] + yearsPerGeneration;
		
		keepInEstate[2][0] = keepInEstate[1][1] - keepInEstate[1][2];
		
		value = keepInEstate[2][0];
		incomeSum = 0;
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = iGrowth * turnoverRate * capitalGainsRate;
			double incomeTax = iValue * combinedIncomeTaxRate;
			double payout = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payout);
		}
		
		keepInEstate[2][1] = value;
		keepInEstate[2][2] = value * getEstateTax(currentYear + lifeExpectancy + yearsPerGeneration);
		keepInEstate[2][3] = incomeSum/yearsPerGeneration;
		keepInEstate[2][4] = keepInEstate[1][4] + yearsPerGeneration;
		
		keepInEstate[3][0] = keepInEstate[2][1] - keepInEstate[2][2];
		
		value = keepInEstate[3][0];
		incomeSum = 0;
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = iGrowth * turnoverRate * capitalGainsRate;
			double incomeTax = iValue * combinedIncomeTaxRate;
			double payout = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payout);
		}
		
		keepInEstate[3][1] = value;
		keepInEstate[3][2] = value * getEstateTax(currentYear + lifeExpectancy + yearsPerGeneration);
		keepInEstate[3][3] = incomeSum/yearsPerGeneration;
		keepInEstate[3][4] = keepInEstate[2][4] + yearsPerGeneration;
		
	}
	
	public void calcStateTrust() {
		double value = totalValue;
		double incomeSum = 0;
				
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateCapGains(iGrowth,1);
			double incomeTax = calculateIncomeTax(iValue, managementFee, 1);
			value += (iGrowth + iValue - managementFee - capTax - incomeTax);
		}

		currentStateTrust[0][0] = totalValue;
		currentStateTrust[0][1] = totalValue * Math.pow((1 + growth + income -trusteeRate), lifeExpectancy);
		currentStateTrust[0][2] = 0.0;
		currentStateTrust[0][3] = 0.0; //For first generation we don't need this.
		currentStateTrust[0][4] = lifeExpectancy;

		currentStateTrust[1][0] = currentStateTrust[0][1] - currentStateTrust[0][2];
		value = currentStateTrust[1][0];
		incomeSum = 0;

		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateCapGains(iGrowth,2);
			double incomeTax = calculateIncomeTax(iValue, managementFee, 2);
			double payOut = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payOut);
		}

		currentStateTrust[1][1] = value;
		currentStateTrust[1][2] = 0;
		currentStateTrust[1][3] = incomeSum/yearsPerGeneration;
		currentStateTrust[1][4] = currentStateTrust[0][4] + yearsPerGeneration;
		
		currentStateTrust[2][0] = currentStateTrust[1][1] - currentStateTrust[1][2];
		
		value = currentStateTrust[2][0];
		incomeSum = 0;
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateCapGains(iGrowth,3);
			double incomeTax = calculateIncomeTax(iValue,managementFee,3);
			double payOut = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payOut);
		}
		
		currentStateTrust[2][1] = value;
		currentStateTrust[2][2] = 0;
		currentStateTrust[2][3] = incomeSum/yearsPerGeneration;
		currentStateTrust[2][4] = currentStateTrust[1][4] + yearsPerGeneration;
		
		currentStateTrust[3][0] = currentStateTrust[2][1] - currentStateTrust[2][2];
		
		value = currentStateTrust[3][0];
		incomeSum = 0;
		for(int i = 0; i < yearsPerGeneration; i++) {
			double iGrowth = value * growth;
			double iValue = value * income;
			incomeSum += iValue;
			double managementFee = value * trusteeRate;
			double capTax = calculateCapGains(iGrowth,4);
			double incomeTax = calculateIncomeTax(iValue, managementFee, 4);
			double payOut = value * payoutRate;
			value += (iGrowth + iValue - managementFee - capTax - incomeTax - payOut);
		}
		
		currentStateTrust[3][1] = value;
		if( lawOfPerpetuity) {
			currentStateTrust[3][2] = value * getEstateTax(currentYear + lifeExpectancy + yearsPerGeneration);
		} else {
			currentStateTrust[3][2] = 0;
		}
		currentStateTrust[3][3] = incomeSum/yearsPerGeneration;
		currentStateTrust[3][4] = currentStateTrust[2][4] + yearsPerGeneration;
	}
	public void calculate() {
		calcKeepInEstate();
		calcStateTrust();
		calcDelawareTrust();
	}
	
	
	public double calculateCapGains(double amount, int generation) {
		if(grantor) {
			if( generation > 1)
				return amount * turnoverRate * capitalGainsRate;
			return 0;
		}
		return amount * turnoverRate * capitalGainsRate;
	}
	
	public double calculateDelawareCapGains(double amount, int generation) {
		if(grantor) {
			if( generation > 1)
				return amount * turnoverRate * delawareCapitalGainsRate;
			return 0;
		}
		return amount * turnoverRate * delawareCapitalGainsRate;
	}
	
	//	 Need to put in Florida, Maryland tax calculations here.
	public double calculateDelawareIncomeTax(double amount, double trustee, int generation) {
		if( grantor) {
			if( generation > 1)
				return delawareCombinedTaxRate * (amount - trustee);
			return 0;
		}
		return combinedIncomeTaxRate * amount;
	}
	
	// Need to put in Florida, Maryland tax calculations here.
	public double calculateIncomeTax(double amount, double trustee, int generation) {
		if( grantor) {
			if( generation > 1)
				return combinedIncomeTaxRate * (amount - trustee);
			return 0;
		}
		return combinedIncomeTaxRate * amount;
	}
	public double getCapitalGainsRate() {
		return capitalGainsRate;
	}
	
	
	
	public double getCombinedIncomeTaxRate() {
		return combinedIncomeTaxRate;
	}
	public double getCrummeyInterestRate() {
		return crummeyInterestRate;
	}
	public double[][] getCurrentStateTrust() {
		return currentStateTrust;
	}
	public int getCurrentYear() {
		return currentYear;
	}
	public double getDelawareCapitalGainsRate() {
		return delawareCapitalGainsRate;
	}
	public double getDelawareCombinedTaxRate() {
		return delawareCombinedTaxRate;
	}
	public double[][] getDelawareTrust() {
		return delawareTrust;
	}
	public double getEstateTax(int year)  {
		zCalc zc = new zCalc();
		zc.StartUp();
		double fet = zc.zFETMAXRATE(year,0);
		zc.ShutDown();
		return fet;
	}
	public double getGrowth() {
		return growth;
	}
	public double getIncome() {
		return income;
	}
	public double getInflationRate() {
		return inflationRate;
	}
	public double[][] getKeepInEstate() {
		return keepInEstate;
	}
	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}
	public int getLifeExpectancy() {
		return lifeExpectancy;
	}
	public double getLifePremium() {
		return lifePremium;
	}
	public double getPayoutRate() {
		return payoutRate;
	}
	public int getTerm() {
		return term;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public double getTrusteeRate() {
		return trusteeRate;
	}
	public String getTrustState() {
		return trustState;
	}
	public double getTurnoverRate() {
		return turnoverRate;
	}
	public int getYearsPerGeneration() {
		return yearsPerGeneration;
	}
	public boolean isGrantor() {
		return grantor;
	}
	public boolean isLawOfPerpetuity() {
		return lawOfPerpetuity;
	}
	public boolean isSecurities() {
		return securities;
	}
	public boolean isUseCrummey() {
		return useCrummey;
	}
	public boolean isUseLifeInsurance() {
		return useLifeInsurance;
	}
	public void setCapitalGainsRate(double capitalGainsRate) {
		this.capitalGainsRate = capitalGainsRate;
	}
	public void setCombinedIncomeTaxRate(double combinedIncomeTaxRate) {
		this.combinedIncomeTaxRate = combinedIncomeTaxRate;
	}
	public void setCrummeyInterestRate(double crummeyInterestRate) {
		this.crummeyInterestRate = crummeyInterestRate;
	}
	public void setCurrentStateTrust(double[][] currentStateTrust) {
		this.currentStateTrust = currentStateTrust;
	}
	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}
	public void setDelawareCapitalGainsRate(double delawareCapitalGainsRate) {
		this.delawareCapitalGainsRate = delawareCapitalGainsRate;
	}
	public void setDelawareCombinedTaxRate(double delawareCombinedTaxRate) {
		this.delawareCombinedTaxRate = delawareCombinedTaxRate;
	}
	public void setDelawareTrust(double[][] delawareTrust) {
		this.delawareTrust = delawareTrust;
	}
	public void setGrantor(boolean grantor) {
		this.grantor = grantor;
	}
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public void setInflationRate(double inflationRate) {
		this.inflationRate = inflationRate;
	}
	public void setKeepInEstate(double[][] keepInEstate) {
		this.keepInEstate = keepInEstate;
	}
	public void setLawOfPerpetuity(boolean lawOfPerpetuity) {
		this.lawOfPerpetuity = lawOfPerpetuity;
	}
	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}
	public void setLifeExpectancy(int lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}
	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
	}
	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}
	public void setSecurities(boolean securities) {
		this.securities = securities;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public void setTrusteeRate(double trusteeRate) {
		this.trusteeRate = trusteeRate;
	}
	public void setTrustState(String trustState) {
		this.trustState = trustState;
	}
	public void setTurnoverRate(double turnoverRate) {
		this.turnoverRate = turnoverRate;
	}
	public void setUseCrummey(boolean useCrummey) {
		this.useCrummey = useCrummey;
	}
	public void setUseLifeInsurance(boolean useLifeInsurance) {
		this.useLifeInsurance = useLifeInsurance;
	}
	public void setYearsPerGeneration(int yearsPerGeneration) {
		this.yearsPerGeneration = yearsPerGeneration;
	}
	
	
	
	
	
	
	
	
	
}
