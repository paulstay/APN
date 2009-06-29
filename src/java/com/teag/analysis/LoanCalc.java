package com.teag.analysis;

/**
 * Use this to calculate the yearly interest, number of months, etc. for a personal Loan on home, etc.
 * 
 * @author Paul
 *
 */

import java.text.DecimalFormat;
import java.util.Vector;


public class LoanCalc {

	double loanBalance = 0;
	double loanInterest = 0;
	double loanPayment = 0;
	double loanFreq = 12;		// Default months
	int term;
	
	double[] interestAcc;
	double[] payments;
	double[] liability;
	
	public void calculate() {
		// Do a double pass, first get freq, then convert to years!
		double iRate = loanInterest;
		double cBalance = getLoanBalance();
		double cPayment = getLoanPayment();
		Vector<Double> iPay = new Vector<Double>();
		Vector<Double> iAcc = new Vector<Double>();
		Vector<Double> tBalance = new Vector<Double>();
		term = 0;
		while(cBalance > 0) {
			double calculatedInterest = cBalance * iRate;
			if(calculatedInterest >= cPayment) {
				break;
			}

			if(term> CashFlow.MAX_TABLE * 12)
				break;
			
			cBalance = cBalance - (cPayment - calculatedInterest);
			iAcc.add(new Double(calculatedInterest));
			iPay.add(new Double(loanPayment));
			tBalance.add(new Double(cBalance));
			term++;
		}
		// Convert the interestAcc and Payments to years!
		int year = 0;
		interestAcc = new double[(int)(term/loanFreq) +1];
		payments = new double[(int)(term/loanFreq) +1];
		liability = new double[(int)(term/loanFreq) +1];
		for(int i=0; i < term; i++) {
			if(i > 0 && (i % loanFreq)==0) {
				liability[year] = tBalance.get(i-1);
				year++;
			}
			interestAcc[year] += iAcc.get(i).doubleValue();
			payments[year] += iPay.get(i).doubleValue();
		}
	}
	
	public static void main(String args[]) {
		double loanBalance = 440000;
		double loanInterest = .07;
		double loanFreq = 12;
		double loanPayment = 4524.88;
		
		LoanCalc lc = new LoanCalc();
		lc.setLoanBalance(loanBalance);
		lc.setLoanFreq(loanFreq);
		lc.setLoanInterest(loanInterest/loanFreq);
		lc.setLoanPayment(loanPayment);
		lc.calculate();
		System.out.println("loan term " + lc.term);
		
		DecimalFormat df = new DecimalFormat("###,###");

		for(int i=0; i < lc.getTerm()/12; i++){
			System.out.print(df.format(lc.getInterestAcc()[i]) + "\t");
		}
		System.out.println();

		for(int i=0; i < lc.getTerm()/12; i++){
			System.out.print(df.format(lc.getPayments()[i]) + "\t");
		}

		System.out.println();

		for(int i=0; i < lc.getTerm()/12; i++){
			System.out.print(df.format(lc.getLiability()[i]) + "\t");
		}
	}

	
	
	public double[] getLiability() {
		return liability;
	}

	public void setLiability(double[] liability) {
		this.liability = liability;
	}

	public double[] getInterestAcc() {
		return interestAcc;
	}

	public double[] getPayments() {
		return payments;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getLoanBalance() {
		return loanBalance;
	}
	public void setLoanBalance(double loanBalance) {
		this.loanBalance = loanBalance;
	}
	public double getLoanInterest() {
		return loanInterest;
	}
	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}
	public double getLoanPayment() {
		return loanPayment;
	}
	public void setLoanPayment(double loanPayment) {
		this.loanPayment = loanPayment;
	}
	public double getLoanFreq() {
		return loanFreq;
	}
	public void setLoanFreq(double loanFreq) {
		this.loanFreq = loanFreq;
	}
	
	
	
	
	
	
}
