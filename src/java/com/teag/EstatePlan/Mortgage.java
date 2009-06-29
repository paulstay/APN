/*
 * Created on Jun 1, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author stay
 *
 */
import java.util.HashMap;

import com.teag.util.Function;

public class Mortgage extends EstatePlanSqlBean {
	long ownerId;
	double[] mort = new double[EstatePlanTable.MAX_TABLE];
	double[] lBalance = new double[EstatePlanTable.MAX_TABLE];
	
	public void calculate() {
		dbobj.start();
    	dbobj.clearFields();
    	String sql = "select * from REAL_ESTATE where OWNER_ID='" + ownerId + "'";
    	HashMap<String,Object> rec = dbobj.execute(sql);

    	for( int j = 0; j < finalDeath; j++) { 
    		mort[j] = 0;
    	}
    	while( rec != null ) {
    		double payment = getDouble(rec,"LOAN_PAYMENT") * 12;
    		double term = getDouble(rec,"LOAN_TERM");
            int years = (int)Math.floor(term/12.0) + 1;
    		for(int i = 0; i < years; i++) {
    			mort[i] += payment;
    		}
    		rec = dbobj.next(); 
    	}
    	dbobj.stop();
    	
    	netWorthCalc();
	}
	
	public double getLiability(int year) {
		return lBalance[year];
	}
	
	public double getMortgage(int year) {
		return mort[year];
	}
	
	public void netWorthCalc() {
		// Take and get liabilities for each year
		for (int j = 0; j < 60; j++) {
			lBalance[j] = 0.0;
		}

		dbobj.start();
		dbobj.clearFields();
		String sql = "select * from REAL_ESTATE where OWNER_ID='"
				+ ownerId + "'";
		HashMap<String,Object> rec = dbobj.execute(sql);

		while (rec != null) {

			double pmt = getDouble(rec, "LOAN_PAYMENT");
			double term = getDouble(rec, "LOAN_TERM");
			double balance = getDouble(rec, "LOAN_BALANCE");
			double rate = getDouble(rec, "LOAN_INTEREST");

			if (balance > 0) {
				double cBalance = balance;
				double cRate = rate / 12.0;
				double remaingMonths = term;
				double endBalance;
				double principal = pmt * 12;
				double interestSum = 0;

				int years = (int) Math.floor(term / 12) + 1;
				for (int i = 0; i < 12; i++) {
					interestSum += Function.IPMT2(cRate, i, balance,
							pmt);
				}

				endBalance = cBalance - (principal - interestSum);
				lBalance[0] += endBalance;
				remaingMonths -= 12;

				for (int i = 1; i < years; i++) {
					cBalance = endBalance;
					interestSum = 0;
					for (int j = 0; j < 12; j++) {
						interestSum += Function.IPMT2(cRate, i * 12 + j,
								balance, pmt);
					}
					endBalance = cBalance - (principal - interestSum);
					lBalance[i] += endBalance;
				}
			}

			rec = dbobj.next();
		}
		dbobj.stop();
		
	}
}
