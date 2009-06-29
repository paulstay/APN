package com.teag.tbox;

import com.zcalc.zCalc;

public class CalcGratPay {
	double fmv;
	double discount;
	double discountValue;
	double iFmv;
	double irsRate;
	double fGrowth;
	double fIncome;
	double iGrowth;
	double iIncome;
	int freq;
	int term;
	double guess = .7;
	double optimalRate = 0;
	
	public void calculate() {
		int itr = 0;
		 
		zCalc zc = new zCalc();
		zc.StartUp();
			optimalRate = zc.zGRATZO(irsRate, term, 0, freq, 0);
		zc.ShutDown();
		
		iFmv =fmv * guess;
		while(true && itr++ < 25) {
		}
		
	}
}
