package com.teag.EstatePlan;

/**
 * @author stay
 */
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.zcalc.zCalc;

public class GiftTax extends EstatePlanSqlBean {
	double taxableGifts[] = new double[EstatePlanTable.MAX_TABLE];
	double crumTable[] = new double[EstatePlanTable.MAX_TABLE];
	double prevTable[] = new double[EstatePlanTable.MAX_TABLE];
	double giftTax[] = new double[EstatePlanTable.MAX_TABLE];
	
	double previousGifts;
	double crumGift;
	double crumTerm;
	int heirs;
	long ownerId;
	int currentYear;
	double taxableGift;
	double giftTotal;
	double totalNonExclusion = 0;

	public void buildCrum() {
		for(int i = 0; i < crumTerm; i++){
			crumTable[i] = crumGift;
		}
	}
	
	public void buildTGifts() {
		zCalc zc = new zCalc();
		zc.StartUp();
		for(int i = 0; i < finalDeath; i++ ) {
			double annualEx = zc.zGIFTX(heirs, currentYear+i, .03);
			double tGift = crumTable[i] - annualEx;
			if( tGift > 0) {
				taxableGifts[i] = tGift;
			} else {
				taxableGifts[i] = 0;
			}
		}
		zc.ShutDown();
	}

	public double calculate(double value, int i) {
		double total = taxableGifts[i] + value + prevTable[i];
		zCalc zc = new zCalc();
		zc.StartUp();
		giftTax[i] = zc.zFGT(total-prevTable[i], prevTable[i], 0,0,currentYear + i,0,0);
		zc.ShutDown();
		prevTable[i+1] = total;
		return giftTax[i];
	}
	
	public double getGiftTax(int year) {
		return giftTax[year];
	}
	
	public void getHeirs() {
		dbobj.start();
		dbobj.setTable("HEIRS");
		dbobj.clearFields();
		String sql = "select * from HEIRS where OWNER_ID='" + ownerId + "'";
		HashMap<String,Object> rec = dbobj.execute(sql);
		while( rec != null) {
			heirs = getInteger(rec,"NUMBER_OF_HEIRS");
			rec = dbobj.next();
		}
		dbobj.stop();
	}

	public void init() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		currentYear = cal.get(Calendar.YEAR);
		
		getHeirs();
		buildCrum();
		buildTGifts();
	}
}
