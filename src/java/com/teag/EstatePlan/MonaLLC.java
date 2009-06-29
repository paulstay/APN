package com.teag.EstatePlan;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonaLLC {
	class Asset {
		double balance = 0;
		double growth = 0;
		double income = 0;
		boolean security;

		void modify(double r) {
			balance += r;
		}
		
		double update() {
			double incomeGenerated = balance * income;
			balance = balance * ( 1 + growth);
			return incomeGenerated;
		}
	}

	static DecimalFormat df = new DecimalFormat("$###,###,###");
	ArrayList<Asset> assets = new ArrayList<Asset>();
	double payout;
	double term;
	double taxes;
	double charity;
	
	double llcTotal[];
	
	
	public MonaLLC() {
		super();
		this.term = 20;
		llcTotal = new double[20];
		addAsset(25000000, .05, .02, true);
		addAsset(21000000, .07, 0, false);
		addAsset(4000000, .04, 0, false);
		addAsset(20000000, .06, .06, false);
		payout  = 3500000;
		charity = 2100000;
		taxes = 1400000;
		calculate();
	}
	
	public void addAsset(double balance, double growth, double income, boolean sFlag) {
		Asset a = new Asset();
		a.balance = balance;
		a.growth = growth;
		a.income = income;
		a.security = sFlag;
		assets.add(a);
	}
	
	public void calculate() {
		for( int i = 0; i < term; i++) {
			double inc = 0;
			for(Asset a : assets) {
				inc += a.update();
			}
			
			double sValue = -payout + inc;
			for( Asset f : assets) {
				if( f.security) {
					f.modify(sValue);
				}
			}
			for( Asset g : assets) {
				llcTotal[i] += g.balance;
			}
		}
	}

	public double[] getTotals() {
		return llcTotal;
	}
}
