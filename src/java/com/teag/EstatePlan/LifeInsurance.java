/*
 * Created on Jun 1, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author Paul Stay
 *
 */
import com.teag.db.TableSumation;
public class LifeInsurance extends EstatePlanSqlBean {
	double cashValue;
	long ownerId;
	
	public void calculate() {
		TableSumation ts = new TableSumation();
		cashValue = ts.getSum("LIFE_INSURANCE", "CASH_VALUE", "OWNER_ID='" + ownerId + "'");
	}
	
	public double getCashValue() {
		return cashValue;
	}
	
	public void initialize() {
		cashValue = 0;
	}
}
