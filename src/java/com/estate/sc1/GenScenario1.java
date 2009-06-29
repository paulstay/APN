package com.estate.sc1;

import com.teag.bean.*;
import com.estate.sc.utils.*;
import java.text.*;
import java.util.*;
import java.awt.*;

public class GenScenario1 {

	PersonBean client = new PersonBean();
	PersonBean spouse = new PersonBean();
	PdfBean userInfo;
	CashFlow cashFlow = new CashFlow();
	NetWorth networth = new NetWorth();
	EstateDistribution estate = new EstateDistribution();
	
	ArrayList<LineObject> netPortfolio = null;
	
	LinkedList<CFRow> rows = new LinkedList<CFRow>();
	DecimalFormat df = new DecimalFormat("#,###,###");
	
	double[] taxes;
	double[] family;
	double[] charity;
	
	public GenScenario1() {
		
	}
	
	public void buildScenario1() {
		// Initialize the cash flow bean, where we get all of the deafults!
		CashFlowBean cfb = new CashFlowBean();
		cfb.setOwnerId(client.getId());
		cfb.initialize();

		cashFlow.setCfb(cfb);
		networth.setCfb(cfb);
		estate.setCfb(cfb);
		
		genCashFlow();
		genNetworth();
		genEstateDist();
	}

	/*
	 * Convert the Cash Flow data into CFRows and CFCols, as
	 * well as the new portfolio data.
	 */
	public void genCashFlow() {
		cashFlow.setClient(client);
		cashFlow.setSpouse(spouse);
		cashFlow.setUserInfo(userInfo);
		cashFlow.initCashFlow();
		genHeader("I.\tCASH FLOW",2,false);
		genHeader("CASH RECEIPTS:", 1, false);
		ArrayList<LineObject> receipts = cashFlow.getReceipts();
		
		for(LineObject line : receipts) {
			processLineObject(line);
		}
		double tReceipts[] = cashFlow.getTotalReceipts();
		processData("Total Cash Receipts", tReceipts, Color.black, true);

		blankLine();
		
		receipts.clear();
		
		genHeader("CASH DISBURSEMENTS:",1,true);
		ArrayList<LineObject> disbursements = cashFlow.getDisbursements();
		
		blankLine();
		
		for(LineObject line : disbursements) {
			processLineObject(line);
		}

		disbursements.clear();
		double[] toCharity = cashFlow.toCharity;
		processData("Charitable Gift", toCharity, Color.green, true);
		
		double[] tTaxes = cashFlow.getIncomeTax();
		processData("Income Tax", tTaxes, Color.red, true);
		
		double[] tDisbursements = cashFlow.getTotalDisbursements();
		processData("Total Disbursements", tDisbursements, Color.black, true);
		
		blankLine();
		
		double[] eCash = cashFlow.getExcessCash();
		processData("Excess Cash", eCash, Color.black, true);

		ArrayList<Portfolio> pList = cashFlow.getPortfolio();
		
		blankLine();
		blankLine();
		genHeader("II. Portfolios", 2, true);
		
		for(Portfolio p : pList ) {
			genHeader(p.getDescription(), 1, false);
			processData("Beginning Balance", p.getBalance(), Color.black, false);
			processData("Growth", p.getGrowth(), Color.black, false);
			processData("Excess Cash", p.getExcessCash(), Color.black, false);
			processData("Ending Balance", p.getEndBalance(), Color.black, false);
			blankLine();
			blankLine();
		}
		netPortfolio = cashFlow.getNetPortfolio();
	}
	
	public void blankLine(){
		CFRow r = new CFRow();
		r.setHeader("");
		r.setTextFill(0);
		r.setIndentLevel(1);		// If set to 0, than its seems to think that this is a headers.
		r.setFontColor(Color.black);
		r.setFontSize(2);
		rows.add(r);
	}
	
	public void processData(String description, double[] data, Color c, boolean weight) {
		CFRow r = new CFRow();
		r.setHeader(description);
		r.setIndentLevel(1);
		r.setTextFill(0);
		r.setFontColor(c);
		if(weight)
			r.setFontWeight(Font.BOLD);
		for(int i =0; i < ScenarioConstants.MAX_TABLE; i++) {
			CFCol col = new CFCol();
			col.setTextFill(3);
			if(data[i] < 0)
				col.setFontColor(Color.red);
			col.setStrValue(df.format(data[i]));
			if( weight)
				col.setFontWeight(Font.BOLD);
				col.setFontColor(c);
			r.addCol(col);
		}
		rows.add(r);
	}
	
	public void genNetworth() {
		networth.setClient(client);
		networth.setUserInfo(userInfo);
		networth.initNetworth();
		networth.addPortfolio(netPortfolio);
		ArrayList<LineObject> nList = networth.getNetworth();
		
		genHeader("III. Networth",2,true);
		
		for(LineObject line : nList) {
			processLineObject(line);
		}
		
		double[] liabilities = networth.getLiability();
		processData("Liabilities", liabilities, Color.red, true);
		
		double[] total = networth.getTotal();
		processData("Totals", total, Color.black, true);
	}
	
	public void genEstateDist() {
		estate.setClient(client);
		estate.setUserInfo(userInfo);
		estate.setUseFiscalYear(true);
		estate.setEstateNetWorth(networth.getTotal());
		estate.addCharity(cashFlow.getCharitableDed());
		estate.addCharity(cashFlow.toCharity);
		estate.init();
		LinkedList<LineObject> eList = estate.getEstate();
	
		genHeader("IV. Estate Distribution", 2, true);
		
		for(LineObject line : eList) {
			processLineObject(line);
		}
		
		taxes = estate.getTotalTax();
		family = estate.getToFamily();
		charity = estate.getToCharity();
	}
	
	public void processLineObject(LineObject l) {
		boolean rFlag = false;
		CFRow r = new CFRow();
		r.setHeader(l.getDescription());
		r.setIndentLevel(1);
		r.setTextFill(0);
		if(l.getF()!= null) {
			r.setFontColor(l.getF().color());
			r.setFontWeight(l.getF().weight());
		}
		
		for(int i=0; i < ScenarioConstants.MAX_TABLE; i++) {
			CFCol c = new CFCol();
			if(l.getF()!=null) {
				c.setFontColor(l.getF().color());
				c.setFontWeight(l.getF().weight());
			}
			double value = l.getValue(i);
			if( value > 0 || value < 0){
				rFlag = true;
			}
			if(value >= 0) {
				c.setStrValue(df.format(value));
			} else {
				c.setStrValue(df.format(value));
				//c.setFontColor(Color.red);
			}
			c.setTextFill(3);
			r.addCol(c);
		}
		if( rFlag)
			rows.add(r);
	}
	
	/**
	 * genHeader(String description, int fontSize, boolean newPage)
	 * @param description
	 * @param fontSize
	 * @param newPage
	 * Generate a new Header such as Cash Receipts, Cash Disbursements, etc.
	 * If you want to start a new page than set the newPage. For instance, we 
	 * want to always start a new page on the Networth section.
	 */
	public void genHeader(String description,int fontSize,  boolean newPage) {
		CFRow r = new CFRow();
		r.setHeader(description);
		r.setNewPage(newPage);
		r.setFontWeight(Font.BOLD);
		r.setFontColor(Color.black);
		r.setIndentLevel(0);
		r.setFontSize(fontSize);
		r.setTextFill(0);		// Always left
		rows.add(r);
	}
	
	public PersonBean getClient() {
		return client;
	}

	public void setClient(PersonBean client) {
		this.client = client;
	}

	public PdfBean getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(PdfBean userInfo) {
		this.userInfo = userInfo;
	}

	public LinkedList<CFRow> getRows() {
		return rows;
	}

	public void setRows(LinkedList<CFRow> rows) {
		this.rows = rows;
	}

	public double[] getCharity() {
		return charity;
	}

	public double[] getFamily() {
		return family;
	}

	public double[] getTaxes() {
		return taxes;
	}
}
