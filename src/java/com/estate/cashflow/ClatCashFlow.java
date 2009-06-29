package com.estate.cashflow;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class ClatCashFlow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<CashFlowData> xls = new ArrayList<CashFlowData>();

		CashFlowData cfd1 = new CashFlowData();
			cfd1.setDescription("Asset1");
			cfd1.setFmv(6000000);
			cfd1.setGrowth(.06);
			cfd1.setIncome(.04);
			cfd1.setCalculationType(0);
			cfd1.setTaxRate(.35);
			cfd1.setStatus(1);
			cfd1.init();
			cfd1.applyExcess = true;
			xls.add(cfd1);
		CashFlowData cfd2 = new CashFlowData();
			cfd2.setDescription("Asset2");
			cfd2.setFmv(4000000);
			cfd2.setGrowth(.055);
			cfd2.setIncome(.03);
			cfd2.setCalculationType(0);
			cfd2.setTaxRate(.35);
			cfd2.init();
			cfd2.setStatus(1);
			xls.add(cfd2);
		CashFlowData clt = new CashFlowData();
			clt.init();
			double dis[] = new double[CashFlowData.MAX_ROW];
			for(int i=1; i <= 10; i++){
				dis[i] = 78000;
			}
			clt.setDisbursements(dis);
			clt.setStatus(0);
			clt.setDescription("CLAT");
			xls.add(clt);
		CashFlowData life = new CashFlowData();
			life.init();
			life.setDescription("Premium");
			double lp[] = new double[CashFlowData.MAX_ROW];
			for(int i=1; i <= 7; i++) {
				lp[i] = 1000000;
			}
			life.setDisbursements(lp);
			double lcv[] = new double[CashFlowData.MAX_ROW];
			double cv = 134000;
			for(int i=0; i < CashFlowData.MAX_ROW; i++) {
				lcv[i] = cv;
				cv *= 1.06;
			}
			xls.add(life);
			
		double excessCash[] = new double[CashFlowData.MAX_ROW];
		for(int i=1; i < 11; i++) {
			double cr = 0;
			double d = 0;
			for(CashFlowData c : xls) {
				c.addYear();
				cr += c.getRec();
				d += c.getDis();
			}
			
			excessCash[i] = cr - d;
			
			for(CashFlowData c : xls) {
				if(c.applyExcess) {
					c.addNetworth(excessCash[i]);
					break;
				}
			}
		}
		
		// Print out cash receipts
		System.out.println("RECEIPTS");
		for(CashFlowData c : xls) {
			printRow(c.getDescription(), c.getReceipts(), 10);
		}
		
		System.out.println("DISBURSEMENTS");
		for(CashFlowData c : xls) {
			printRow(c.getDescription(), c.getDisbursements(), 10);
		}
		
		printRow("Excess", excessCash, 10);
		
		System.out.println(cfd1.getDescription());
		DecimalFormat df = new DecimalFormat("###,###");
		double row[] = cfd1.getNetworth();
		System.out.print("Begin"+ "\t\t");
		for(int i=1; i <= 10; i++) {
			System.out.print("\t" + df.format(row[i-1]/1000.0));
		}
		System.out.println();
		
		System.out.print("Growth" + "\t\t");
		for(int i=1; i <=10; i++){
			System.out.print("\t" + df.format(row[i-1] * cfd1.getGrowth()/1000.0));
		}
		System.out.println();

		printRow("Excess", excessCash,10);
		
		printRow("End", cfd1.getNetworth(), 10);

		
		System.out.println("NET WORTH");
		for(CashFlowData c : xls) {
			printRow(c.getDescription(), c.getNetworth(), 10);
		}
		
		
	}
	
	public static void printRow(String description, double[] row, int years) {
		boolean rFlag = false;
		for(int i=1; i <= years; i++) {
			if(row[i] > 0)
				rFlag = true;
		}
		
		if(!rFlag)
			return;
		
		DecimalFormat df = new DecimalFormat("###,###");
		System.out.print(description + "\t\t");
		for(int i=1; i <= years; i++) {
			System.out.print("\t" + df.format(row[i]/1000.0));
		}
		System.out.println();
	}
}
