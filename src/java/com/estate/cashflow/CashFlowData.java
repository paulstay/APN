package com.estate.cashflow;


import java.text.DecimalFormat;

public class CashFlowData {

	public final static int MAX_ROW = 40;
	
	public static void main(String args[]) {
		CashFlowData asset1 = new CashFlowData();
		asset1.setFmv(10000000);
		asset1.setGrowth(.045);
		asset1.setIncome(.06);
		asset1.setStatus(1);
		asset1.setCalcTax(true);
		asset1.setTaxRate(.35);
		asset1.init();
		
		for(int i=0; i < MAX_ROW-1; i++) {
			asset1.addYear();
		}
		
		asset1.printYears(11);
	}
	double receipts[];
	double disbursements[];
	double excessCash[];
	double networth[];
	
	double tax[];
	double fmv;
	double growth;
	
	double income;
	int status;				// 0 = Static calculation; 1 = Calculate
	boolean calcTax = false;
	boolean applyExcess = false;
	int calculationType;
	int currentYear;

	String description;
		
	double taxRate;
	boolean isAsset = false;
	
	boolean isLife = false;
	
	public void addNetworth(double x) {
		this.networth[currentYear] += x;
	}
	
	public void addYear() {
		if(status == 1) {		// We need to update values
			receipts[currentYear+1] = networth[currentYear] * income;
			networth[currentYear+1] = networth[currentYear] * (1 + growth);
			if(calcTax)		// If this is a non grantor trust than use the tax here.
				disbursements[currentYear+1] = receipts[currentYear+1] * taxRate;
			else
				tax[currentYear+1] = receipts[currentYear+1] * taxRate;	// So we can get it for the grantor
		}
		excessCash[currentYear] = receipts[currentYear] - disbursements[currentYear];
		currentYear++;
	}

	public int getCalculationType() {
		return calculationType;
	}
	
	public int getCurrentYear() {
		return currentYear;
	}

	public String getDescription() {
		return description;
	}

	public double getDis() {
		return disbursements[currentYear];
	}
	
	public double[] getDisbursements() {
		return disbursements;
	}
	
	public double[] getExcessCash() {
		return excessCash;
	}

	public double getFmv() {
		return fmv;
	}

	public double getGrowth() {
		return growth;
	}

	public double getIncome() {
		return income;
	}

	public double[] getNetworth() {
		return networth;
	}

	public double getRec() {
		return receipts[currentYear];
	}

	public double[] getReceipts() {
		return receipts;
	}

	public int getStatus() {
		return status;
	}

	public double[] getTax() {
		return tax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	// the zero elements are the initial state of the asset, or payment, when we build the
	// cash flow start with element 1 for each array!
	public void init() {
		receipts = new double[MAX_ROW];
		disbursements = new double[MAX_ROW];
		networth = new double[MAX_ROW];
		excessCash = new double[MAX_ROW];
		tax = new double[MAX_ROW];
		networth[0] = fmv;
		receipts[0] = 0;
		disbursements[0] = 0;
		currentYear = 0;
	}

	public boolean isApplyExcess() {
		return applyExcess;
	}

	public boolean isAsset() {
		return isAsset;
	}

	public boolean isCalcTax() {
		return calcTax;
	}

	public boolean isLife() {
		return isLife;
	}

	public void printYears(int n) {
		DecimalFormat df = new DecimalFormat("###,###");
		System.out.println("Cash Receipts");
		for(int i=1; i < n; i++) {
			System.out.print("\t" + df.format(receipts[i]/1000.0));
		}
		System.out.println();
		System.out.println("Cash Disbursements");
		for(int i=1; i < n; i++) {
			System.out.print("\t" + df.format(disbursements[i]/1000.0));
		}
		System.out.println();
		System.out.println("Excess Cash");
		for(int i=1; i < n; i++) {
			System.out.print("\t" + df.format(excessCash[i]/1000.0));
		}
		System.out.println();
		System.out.println("Net Worth");
		for(int i=1; i < n; i++) {
			System.out.print("\t" + df.format(networth[i]/1000.0));
		}
		System.out.println();
	}

	public void setApplyExcess(boolean applyExcess) {
		this.applyExcess = applyExcess;
	}

	public void setAsset(boolean isAsset) {
		this.isAsset = isAsset;
	}

	public void setCalcTax(boolean calcTax) {
		this.calcTax = calcTax;
	}

	public void setCalculationType(int calculationType) {
		this.calculationType = calculationType;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisbursements(double[] disbursements) {
		for(int i=0; i < disbursements.length; i++) {
			this.disbursements[i] = disbursements[i];	
		}
	}

	public void setExcessCash(double[] excessCash) {
		this.excessCash = excessCash;
	}
	
	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public void setLife(boolean isLife) {
		this.isLife = isLife;
	}

	public void setNetworth(double[] networth) {
		this.networth = networth;
	}

	public void setReceipts(double[] r) {
		for(int i=0; i < r.length; i++)
			this.receipts[i] = r[i];
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTax(double[] tax) {
		this.tax = tax;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	
}
