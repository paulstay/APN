package com.teag.estate;


import java.util.Date;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.zcalc.zCalc;


public class TClat2 extends EstatePlanningTool {
/*
 * <Table name='TCLAT2_TOOL'>
		<Field name='ID' size='19' type='long' autoincrement='true' valid='true'/>
		<Field name='TERM' type='number' size='2' null='false'/>
		<Field name='AFR_RATE' type='number' size='2,6' null='false'/>
		<Field name='AFR_DATE' type='date' null='false'/>
		<Field name='ANNUITY_FREQ' type='number' size='12,2' null='false' />
		<Field name='ANNUITY_TYPE' type='number' size='1' null='false' />
		<Field name='AMOUNT' type='number' size='14,2' null='false'/>
		<Field name='GROWTH_RATE' type='number' size='2,6' null='false'/>
		<Field name='INTEREST_RATE' type='number' size='2,6' null='false'/>
		<Field name='REMAINDER_INTEREST' type='number' size='14,2' null='false'/>
		<PrimaryKey field='ID'/>
	</Table>
 */
	
	public static final String ID = "ID";
	public static final String TERM = "TERM";
	public static final String AFR_RATE = "AFR_RATE";
	public static final String AFR_DATE = "AFR_DATE";
	public static final String ANNUITY_FREQ = "ANNUITY_FREQ";
	public static final String ANNUITY_TYPE = "ANNUITY_TYPE";
	public static final String AMOUNT = "AMOUNT";
	public static final String GROWTH_RATE = "GROWTH_RATE";
	public static final String INTEREST_RATE = "INTEREST_RATE";
	public static final String REMAINDER_INTEREST = "REMAINDER_INTEREST";
	
	public static final String tableName = "TCLAT2_TOOL";
	
	public static void main(String[] args) {
		TClat2 tclat = new TClat2();
		tclat.amount = 40000000;
		tclat.afrRate = .056;
		tclat.remainderInterest = 56000;
		tclat.term = 20;
		tclat.annuityFreq = 4;
		tclat.annuityType = 0;
		tclat.growthRate = .06;
		tclat.interestRate = .03;
		tclat.calculate();
		tclat.calculateTotals();
		System.out.println("Payout Rate \t\t" + percentformat.format(tclat.payoutRate));
		System.out.println("Annuity \t\t" + dollarformat.format(tclat.annuity));
		System.out.println("Clat Deduction \t\t" + dollarformat.format(tclat.clatDeduction));
		System.out.println("Remainder Interest \t" + dollarformat.format(tclat.remainderInterest));		
		System.out.println("Calc Remainder Interest \t" + dollarformat.format(tclat.amount - tclat.clatDeduction));
		System.out.println("Annuity Factor \t\t" + weight.format(tclat.annuityFactor));
		System.out.println("To Charity \t\t" + weight.format(tclat.toCharity));
		System.out.println("Remaining Value\t\t" + weight.format(tclat.getRemainingTrustValue()));
		System.out.println(moneyformat.format(tclat.annuityFactor * tclat.annuity));
		
		System.exit(0);
	}
	
	long id;
	double amount;
	double remainderInterest;
	double afrRate;
	Date afrDate;
	double growthRate;
	double interestRate;
	int term;
	int annuityFreq;
	
	int annuityType;
	double payoutRate;
	double annuity;
	double annuityFactor;
	double clatDeduction;
	
	double actualPayoutRate;
	long scenarioId = 0;
	
	int finalDeath;
	double remainingTrustValue;

	double toCharity;
	
	@Override
	public void calculate() {
		zCalc zc = new zCalc();
		zc.StartUp();
		annuity = 0;
		if( remainderInterest > 0) {
			payoutRate = zc.zANNRATETARGET(amount, remainderInterest, afrRate, term, 
					0, 0, 0, 0, 0, 0, annuityFreq, annuityType, 0,0, 0);
		} else {
			payoutRate = zc.zANNRATEMAX(afrRate, term, 0, 0, 0, 0, 0, 0, annuityFreq, annuityType, 0, 0, 0);
		}
		annuity = payoutRate * amount;
		
		annuityFactor = zc.zTERM(0L,afrRate, term, 0, annuityFreq, annuityType, 0);
		double actualPayout = annuity/amount;
		clatDeduction = zc.zANNTERM(amount, actualPayout, afrRate, term, 0, annuityFreq, annuityType, 0, 0);

		zc.ShutDown();
		
		if( remainderInterest <= 0)
			remainderInterest = amount - clatDeduction;
		calculateTotals();
	}
	
	
	public void calculateTotals() {
		double currentValue = amount;
		double payment = annuity;
		
		for( int i= 0; i < term; i++) {
			if( annuityType == 0) { // end of year
				currentValue += currentValue * growthRate;
				currentValue += currentValue * interestRate;
				currentValue -= payment;
			} else {
				currentValue -= payment;
				currentValue += currentValue * growthRate;
				currentValue += currentValue * interestRate;
			}
		}
		remainingTrustValue = currentValue;
		toCharity = term * payment;
	}


	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete(tableName,ID + "='" + getId());
	}

	public double getActualPayoutRate() {
		return actualPayoutRate;
	}


	public Date getAfrDate() {
		return afrDate;
	}

	public double getAfrRate() {
		return afrRate;
	}

	public double getAmount() {
		return amount;
	}

	public double getAnnuity() {
		return annuity;
	}
	
	public double getAnnuityFactor() {
		return annuityFactor;
	}

	public int getAnnuityFreq() {
		return annuityFreq;
	}

	public int getAnnuityType() {
		return annuityType;
	}

	public double getClatDeduction() {
		return clatDeduction;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public double getGrowthRate() {
		return growthRate;
	}

	public long getId() {
		return id;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public double getPayoutRate() {
		return payoutRate;
	}

	public double getRemainderInterest() {
		return remainderInterest;
	}

	public double getRemainingTrustValue() {
		return remainingTrustValue;
	}

	public long getScenarioId() {
		return scenarioId;
	}

	public int getTerm() {
		return term;
	}

	public double getToCharity() {
		return toCharity;
	}

	@Override
	public long getToolTableId() {
		return ToolTableTypes.TCLAT2.id();
		
	}

	@Override
	public void insert() {
		dbObj.start();
		dbObj.clearFields();
		dbObj.setTable(tableName);
		dbAddField(AFR_RATE, getAfrRate());
		String date = dateformat.format(getAfrDate());
		dbAddDate(AFR_DATE, date);
		dbAddField(AMOUNT, getAmount());
		dbAddField(ANNUITY_FREQ, getAnnuityFreq());
		dbAddField(ANNUITY_TYPE, getAnnuityType());
		dbAddField(GROWTH_RATE, getGrowthRate());
		dbAddField(INTEREST_RATE, getInterestRate());
		dbAddField(REMAINDER_INTEREST, getRemainderInterest());
		dbAddField(TERM, getTerm());
		
		dbObj.insert();
		String uuid = dbObj.getUUID();
		HashMap<String,Object> rec = dbObj.execute("select " + ID + " from " + tableName + 
				" where UUID='" + uuid + "'");
		Object o = rec.get("ID");
		if( o!= null) {
			id = Integer.parseInt(o.toString());
		}
		dbObj.stop();

	}

	@Override
	public void read() {
		if( getId() > 0) {
			dbObj.start();
			dbObj.setTable(tableName);
			dbObj.clearFields();
			HashMap<String,Object> rec = null;
			
			String sql = "select * from " + tableName + " where " + ID + "='" +
				getId() + "'";
			
			rec = dbObj.execute(sql);
			
			if( rec != null) {
				setAfrRate(getDouble(rec, AFR_RATE));
				try {
					setAfrDate(dateformat.parse(getDate(rec, AFR_DATE)));
				} catch(Exception e) {
					Date now = new Date();
					setAfrDate(now);
				}
				setId(getLong(rec, ID));
				setAmount(getDouble(rec, AMOUNT));
				setGrowthRate(getDouble(rec, GROWTH_RATE));
				setInterestRate(getDouble(rec, INTEREST_RATE));
				setAnnuityFreq(getInteger(rec, ANNUITY_FREQ));
				setAnnuityType(getInteger(rec, ANNUITY_TYPE));
				setRemainderInterest(getDouble(rec,REMAINDER_INTEREST));
				setTerm(getInteger(rec,TERM));
			}
			dbObj.stop();
		}
	}

	@Override
	public void report() {
	}

	public void setActualPayoutRate(double actualPayoutRate) {
		this.actualPayoutRate = actualPayoutRate;
	}

	public void setAfrDate(Date afrDate) {
		this.afrDate = afrDate;
	}

	public void setAfrRate(double afrRate) {
		this.afrRate = afrRate;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setAnnuity(double annuity) {
		this.annuity = annuity;
	}

	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
	}

	public void setAnnuityFreq(int annuityFreq) {
		this.annuityFreq = annuityFreq;
	}

	public void setAnnuityType(int annuityType) {
		this.annuityType = annuityType;
	}

	public void setClatDeduction(double clatDeduction) {
		this.clatDeduction = clatDeduction;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public void setPayoutRate(double payoutRate) {
		this.payoutRate = payoutRate;
	}

	public void setRemainderInterest(double remainderInterest) {
		this.remainderInterest = remainderInterest;
	}

	public void setRemainingTrustValue(double remainingTrustValue) {
		this.remainingTrustValue = remainingTrustValue;
	}

	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setToCharity(double toCharity) {
		this.toCharity = toCharity;
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.clearFields();
		dbObj.setTable(tableName);
		
		dbAddField(ID, getId());
		dbAddField(AFR_RATE, getAfrRate());
		String date = dateformat.format(getAfrDate());
		dbAddDate(AFR_DATE, date);
		dbAddField(AMOUNT, getAmount());
		dbAddField(ANNUITY_FREQ, getAnnuityFreq());
		dbAddField(ANNUITY_TYPE, getAnnuityType());
		dbAddField(GROWTH_RATE, getGrowthRate());
		dbAddField(INTEREST_RATE, getInterestRate());
		dbAddField(REMAINDER_INTEREST, getRemainderInterest());
		dbAddField(TERM, getTerm());
		
		dbObj.setWhere(ID + "='" + getId() + "'");
		
		dbObj.update();
		dbObj.stop();
	}

	@Override
	public String writeupText() {
		return null;
	}

}
