package com.teag.estate;

import java.util.Date;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.teag.sc.utils.ScenarioConstants;

import com.zcalc.zCalc;

public class SCINTool extends EstatePlanningTool {

	public final static String ID = "ID";
	public final static String NOTE_TYPE = "NOTE_TYPE";
	public final static String IRS_RATE = "IRS_RATE";
	public final static String IRS_DATE = "IRS_DATE";
	public final static String TERM = "TERM";
	public final static String NOTE_RATE = "NOTE_RATE";
	public final static String ACTUARIAL_METHOD = "ACTUARIAL_METHOD";
	public final static String VALUATION_METHOD = "VALUATION_METHOD";
	public final static String DURATION_CALC_METHOD = "DURATION_CALC_METHOD";
	public final static String END_BEGIN = "END_BEGIN";
	public final static String XTERM = "XTERM";
	public final static String YRS_DEFERRED = "YRS_DEFFERED";
	public final static String PAYMENT_TYPE = "PAYMENT_TYPE";

	long id;
	int noteType;			// 0 = Level Prin + Interest, 1 = Self Amoritizing, 2 = Interest Payment
	double irsRate;
	Date irsDate;
	int term;
	double noteRate;
	int actuarialMethod = 0;
	int valuationMethod = 0;;
	int durationCalcMethod = 0;;
	int endBegin = 0;		// End
	int xTerm = 0;
	int yrsDeferred = 0;
	int paymentType = 0;
	
	int toolTableId = ToolTableTypes.SCIN.id();

	int cAge;
	int sAge;
	double cLifeExpectancy;
	double sLifeExpectancy;
	
	double fmv;
	double basis;
	double growth;
	
	double principalRiskPremium;
	double interestRiskPremium;
	double adjustedPremium;
	double adjustedInterest;
	int finalDeath;
	
	double note[];
	double capGains[];
	double taxFree[];
	double payment[];
	double interestPayment[];
	
	@Override
	public void calculate() {
		// We need to get the tool assets here....
		EstateToolDistribution tad = new EstateToolDistribution();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());
		
		tad.buildToolAssetList();
		fmv = tad.getTotalValue();
		basis = tad.getBasis();
		growth = tad.getGrowth();
		
		if(finalDeath == 0)
			finalDeath = term+yrsDeferred;

		payment = new double[finalDeath];
		capGains = new double[finalDeath];
		taxFree = new double[finalDeath];
		payment = new double[finalDeath];
		interestPayment = new double[finalDeath];
		note = new double[finalDeath];

		zCalc zc = new zCalc();
		zc.StartUp();
			cLifeExpectancy = zc.zLE(cAge, 0, 0, 0, 0, 0, 0);
			sLifeExpectancy = zc.zLE(sAge, 0, 0, 0, 0, 0, 0);
			principalRiskPremium = zc.zSCIN(0, fmv, basis, irsRate, cAge, noteRate, term, noteType, xTerm, endBegin, yrsDeferred,0, 0, sAge, 0);
			interestRiskPremium = zc.zSCIN(1, fmv, basis, irsRate, cAge, noteRate, term, noteType, xTerm, endBegin, yrsDeferred,0, 0, sAge, 0);
			adjustedPremium = fmv + principalRiskPremium;
			adjustedInterest = interestRiskPremium + noteRate;
			double useRate = noteRate;
			double usePremium = adjustedPremium;
			if(paymentType == 1) {
				useRate = adjustedInterest;
				usePremium = fmv;
				
			}
			for(int i = 0; i < term+yrsDeferred; i++){
				payment[i] = zc.zINSTALL(0, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeferred);
				interestPayment[i] = zc.zINSTALL(1, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeferred);
				capGains[i] = zc.zINSTALL(2, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeferred);
				taxFree[i] = zc.zINSTALL(3, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeferred);
				note[i] = zc.zINSTALL(4, usePremium, basis, useRate, term, i+1, noteType, xTerm, endBegin, yrsDeferred);
				
			}
		zc.ShutDown();
	}

	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("SCIN_TOOL", "ID='" + id + "'");
		dbObj.stop();
	}

	public int getActuarialMethod() {
		return actuarialMethod;
	}

	public double getAdjustedInterest() {
		return adjustedInterest;
	}
	
	public double getAdjustedPremium() {
		return adjustedPremium;
	}

	public double getBasis() {
		return basis;
	}

	public int getCAge() {
		return cAge;
	}

	public double[] getCapGains() {
		return capGains;
	}

	public double getCLifeExpectancy() {
		return cLifeExpectancy;
	}

	public int getDurationCalcMethod() {
		return durationCalcMethod;
	}

	public int getEndBegin() {
		return endBegin;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public double getGrowth() {
		return growth;
	}

	public long getId() {
		return id;
	}

	public double[] getInterestPayment() {
		return interestPayment;
	}

	public double getInterestRiskPremium() {
		return interestRiskPremium;
	}

	public Date getIrsDate() {
		return irsDate;
	}

	public double getIrsRate() {
		return irsRate;
	}

	public double[] getNote() {
		return note;
	}

	public double getNoteRate() {
		return noteRate;
	}

	public int getNoteType() {
		return noteType;
	}

	public double[] getPayment() {
		return payment;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public double getPrincipalRiskPremium() {
		return principalRiskPremium;
	}

	public int getSAge() {
		return sAge;
	}

	public double getSLifeExpectancy() {
		return sLifeExpectancy;
	}

	public double[] getTaxFree() {
		return taxFree;
	}

	public int getTerm() {
		return term;
	}

	@Override
	public long getToolTableId() {
		return com.estate.constants.ToolTableTypes.SCIN.id();
	}

	public int getValuationMethod() {
		return valuationMethod;
	}

	public int getXTerm() {
		return xTerm;
	}

	public int getYrsDeferred() {
		return yrsDeferred;
	}

	@Override
	public void insert() {

		dbObj.start();
		dbObj.setTable("SCIN_TOOL");
		dbObj.clearFields();
		dbAddField(IRS_RATE, getIrsRate());
		dbAddDate(IRS_DATE, dateformat.format(getIrsDate()));
		dbAddField(NOTE_TYPE, getNoteType());
		dbAddField(TERM, getTerm());
		dbAddField(NOTE_RATE, getNoteRate());
		dbAddField(ACTUARIAL_METHOD, getActuarialMethod());
		dbAddField(VALUATION_METHOD, getValuationMethod());
		dbAddField(DURATION_CALC_METHOD, getDurationCalcMethod());
		dbAddField(END_BEGIN, getEndBegin());
		dbAddField(XTERM, getXTerm());
		dbAddField(PAYMENT_TYPE, getPaymentType());
		dbAddField(YRS_DEFERRED, getYrsDeferred());

		int error = dbObj.insert();
		if( error == 0 ) {
			String uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from SCIN_TOOL where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			if( o != null) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();
	}

	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("GRAT_TOOL");
			dbObj.clearFields();
			HashMap<String,Object> rec = null;
			String sql = "select * from SCIN_TOOL where ID='" + id + "'";
			rec = dbObj.execute(sql);
			dbObj.stop();
			
			if( rec != null ) {
				setId(getLong(rec,"ID"));
				setIrsRate(getDouble(rec,IRS_RATE));
				String iDate = getDate(rec,IRS_DATE);
				try {
					setIrsDate(dateformat.parse(iDate));
				} catch (Exception e) {
					setIrsDate(new Date());
				}
				setTerm(getInteger(rec,TERM));
				setNoteRate(getDouble(rec,NOTE_RATE));
				setNoteType(Integer.parseInt(getString(rec,NOTE_TYPE)));
				setPaymentType(Integer.parseInt(getString(rec,PAYMENT_TYPE)));
				/*
				setActuarialMethod(getInteger(rec,ACTUARIAL_METHOD));
				setValuationMethod(getInteger(rec,VALUATION_METHOD));
				setDurationCalcMethod(getInteger(rec,DURATION_CALC_METHOD));
				*/
				setEndBegin(getInteger(rec,END_BEGIN));
				setXTerm(getInteger(rec,XTERM));
				setYrsDeferred(getInteger(rec,YRS_DEFERRED));
			}
		}
	}

	@Override
	public void report() {

	}

	public void setActuarialMethod(int actuarialMethod) {
		this.actuarialMethod = actuarialMethod;
	}

	public void setAdjustedInterest(double adjustedInterest) {
		this.adjustedInterest = adjustedInterest;
	}

	public void setAdjustedPremium(double adjustedPremium) {
		this.adjustedPremium = adjustedPremium;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setCAge(int age) {
		cAge = age;
	}

	public void setCapGains(double[] capGains) {
		this.capGains = capGains;
	}

	public void setCLifeExpectancy(double lifeExpectancy) {
		cLifeExpectancy = lifeExpectancy;
	}

	public void setDurationCalcMethod(int durationCalcMethod) {
		this.durationCalcMethod = durationCalcMethod;
	}

	public void setEndBegin(int endBegin) {
		this.endBegin = endBegin;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInterestPayment(double[] interestPayment) {
		this.interestPayment = interestPayment;
	}

	public void setInterestRiskPremium(double interestRiskPremium) {
		this.interestRiskPremium = interestRiskPremium;
	}

	public void setIrsDate(Date irsDate) {
		this.irsDate = irsDate;
	}

	public void setIrsRate(double irsRate) {
		this.irsRate = irsRate;
	}

	public void setNote(double[] note) {
		this.note = note;
	}

	public void setNoteRate(double noteRate) {
		this.noteRate = noteRate;
	}

	public void setNoteType(int noteType) {
		this.noteType = noteType;
	}

	public void setPayment(double[] payment) {
		this.payment = payment;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public void setPrincipalRiskPremium(double principalRiskPremium) {
		this.principalRiskPremium = principalRiskPremium;
	}

	public void setSAge(int age) {
		sAge = age;
	}

	public void setSLifeExpectancy(double lifeExpectancy) {
		sLifeExpectancy = lifeExpectancy;
	}

	public void setTaxFree(double[] taxFree) {
		this.taxFree = taxFree;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setToolTableId(int toolTableId) {
		this.toolTableId = toolTableId;
	}

	public void setValuationMethod(int valuationMethod) {
		this.valuationMethod = valuationMethod;
	}

	public void setXTerm(int term) {
		xTerm = term;
	}

	public void setYrsDeferred(int yrsDeferred) {
		this.yrsDeferred = yrsDeferred;
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable("GRAT_TOOL");
		dbObj.clearFields();
		dbAddField(IRS_RATE, getIrsRate());
		dbAddDate(IRS_DATE, dateformat.format(getIrsDate()));
		dbAddField(NOTE_TYPE, getNoteType());
		dbAddField(TERM, getTerm());
		dbAddField(NOTE_RATE, getNoteRate());
		dbAddField(ACTUARIAL_METHOD, getActuarialMethod());
		dbAddField(VALUATION_METHOD, getValuationMethod());
		dbAddField(DURATION_CALC_METHOD, getDurationCalcMethod());
		dbAddField(END_BEGIN, getEndBegin());
		dbAddField(XTERM, getXTerm());
		dbAddField(YRS_DEFERRED, getYrsDeferred());
		dbAddField(PAYMENT_TYPE, getPaymentType());
		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();

		dbObj.stop();
	}

	@Override
	public String writeupText() {
		return null;
	}
	
	
	
}
