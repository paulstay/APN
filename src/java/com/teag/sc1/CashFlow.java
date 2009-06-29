package com.teag.sc1;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.teag.bean.CashFlowBean;
import com.teag.bean.ClientBean;
import com.teag.bean.PdfBean;
import com.teag.bean.PersonBean;
import com.teag.sc.utils.LineObject;
import com.teag.sc.utils.Portfolio;
import com.teag.sc.utils.ScenarioConstants;

public class CashFlow {

	ArrayList<LineObject> receipts = new ArrayList<LineObject>();
	ArrayList<LineObject> disbursements = new ArrayList<LineObject>();
	ArrayList<LineObject> netPortfolio = new ArrayList<LineObject>();
	ArrayList<Portfolio> portfolio = new ArrayList<Portfolio>();
	
	/**
	 * The following three arrays are used to store the tax information for each year,
	 * as each assets is processed the appropriate tax values are added to the array. Tax 
	 * calculations are done after all assets are processed in the current year.
	 */
	double taxDeduction[] = new double[ScenarioConstants.MAX_TABLE];
	double taxableIncome[] = new double[ScenarioConstants.MAX_TABLE];
	double capGainsIncome[] = new double[ScenarioConstants.MAX_TABLE];		// Dividend income
	
	/*
	 * The incomeTax, totalReceipts, totalDisbursements, and excessCash are used to store the final totals.
	 */
	double incomeTax[] = new double[ScenarioConstants.MAX_TABLE];
	double totalReceipts[] = new double[ScenarioConstants.MAX_TABLE];
	double totalDisbursements[] = new double[ScenarioConstants.MAX_TABLE];
	double excessCash[] = new double[ScenarioConstants.MAX_TABLE];
	
	/*
	 * Social Security is used in the tax calculation
	 */
	double socialSecurity[] = new double[ScenarioConstants.MAX_TABLE];
	
	/*
	 * The charitable Deduction array is used if there are any deductions in the disbursements.
	 * And it is eventually subtracted out at the tax calculations.
	 */
	double charitableDed[] = new double[ScenarioConstants.MAX_TABLE];
	double portfolioIncome[] = new double[ScenarioConstants.MAX_TABLE];
	
	/*
	 * varaibles used in the calculations, in particular if the start year is greater than 2005, then we use it as the starting 
	 * year for the cash flow table.
	 */
	
	int startYear = 0;
	int currentMonth = -1;
	int currentYear = 0;
	boolean useFiscalYear = true;
	
	
	PdfBean userInfo;
	CashFlowBean cfb;
	ClientBean cb;
	PersonBean client;
	PersonBean spouse;
	
	
	public void buildPortNetworth() {
		
	}
	
	public void calcTax() {
		
	}
	
	public void initCashFlow() {
		
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		currentMonth = cal.get(Calendar.MONTH) + 1;		// January is 0
		
		if(startYear > 2005) {
			currentYear = startYear;
		} else if(useFiscalYear && currentMonth > 1) {
			startYear = cal.get(Calendar.YEAR) + 1;
		} else {
			startYear = cal.get(Calendar.YEAR);
		}
		
		// Initialize the Cash Flow Bean, which contains several variable for the cash flow analysis!
		CashFlowBean c = new CashFlowBean();
		c.setOwnerId(cb.getPrimaryId());
		c.initialize();
		
		// Initialize the portfolio(s) here
		
		// Load Receipts
		loadReceipts();
		loadDisbursements();
		calcTax();
		buildPortNetworth();
	}
	
	public void loadDisbursements() {
		
	}
	
	public void loadReceipts() {
		
	}
	
	
	
	
}
