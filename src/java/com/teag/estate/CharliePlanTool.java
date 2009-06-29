package com.teag.estate;

import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.zcalc.zCalc;


public class CharliePlanTool extends EstatePlanningTool {

	public final static String ID = "ID";
	public final static String tableName = ToolTableTypes.CPLAN.toolName();
	public final static String CLIFE_PREMIUM = "CLIFE_PREMIUM";
	public final static String SLIFE_PREMIUM = "SLIFE_PREMIUM";
	public final static String CLIENT_DEATH_BENEFIT = "CLIENT_DEATH_BENEFIT";
	public final static String SPOUSE_DEATH_BENEFIT = "SPOUSE_DEATH_BENEFIT";
	public final static String CLIENT_CONTR = "CLIENT_CONTR";
	public final static String SPOUSE_CONTR = "SPOUSE_CONTR";
	public final static String TERM = "TERM";
	public final static String TAX_RATE = "TAX_RATE";
	public final static String FINAL_DEATH = "FINAL_DEATH";
	
	long id;
	private String uuid;
	HashMap<String,Object> record;
	
	double clientContr;
	double spouseContr;
	double spouseLife;
   	double clientLife;
	double clientDeathBenefit;
	double spouseDeathBenefit;
	int term;
	double taxRate;
	double sideFundGrowth;
	double clientMonthly;
	double spouseMonthly;
	double stdGrowth = .06;
	double capGrowth = .0713;
	int finalDeath;
	int clientAge;
	int spouseAge;
	
	double before[] = new double[25];
	double pension[] = new double[25];
	double contribution[] = new double[25];
	double cPs58Tax[] = new double[25];
	double sPs58Tax[] = new double[25];
	double sideFund[] = new double[25];
	double lifeIns[] = new double[25];
	double toFamily[] = new double[25];
	
	public void buildContribution() {
		for(int i=0; i< 10; i++) {
			contribution[i] = spouseContr + clientContr;
		}
	}
	
	public void buildLife() {
		double life = clientDeathBenefit + spouseDeathBenefit;
		for(int i=0; i < finalDeath; i++){
			lifeIns[i] = life;
			life = life * 1.03;
		}
	}
	
	public void buildPension() {
		for(int i = 0; i < finalDeath; i++){
			if( i < 10) {
				pension[i] = 0;
			}else {
				pension[i] = (clientMonthly + spouseMonthly)*12;
			}
		}
	}
	
	public void buildPS58() {
		for(int i = 0; i < finalDeath; i++) {
			if( i < 15)
				cPs58Tax[i] = GETPS58Tax(clientAge+i,0) * clientDeathBenefit/1000 * taxRate; 
			sPs58Tax[i] = (GETPS58Tax(spouseAge+i,0)*spouseDeathBenefit/1000)*taxRate; 
		}
	}
	
	public void buildSideFund() {
		double s1 = spouseContr - spouseLife;
		double s2 = clientContr - clientLife;
		double temp = 0;
		for(int i=0; i < finalDeath; i++) {
			if( i < term) {
				temp+= s1+s2;
			}
			temp = temp*(1 + capGrowth);
			temp -= pension[i];
			sideFund[i] = temp;
			temp = sideFund[i];
		}
	}
	
	public void buildStd() {
		double temp = 0;
		
		for(int i=0; i < finalDeath; i++) {
			double fund = 0;
			double tax = 0;
			if( i < term) {
				fund = clientContr + spouseContr;
				tax = (clientContr + spouseContr) * taxRate;
			}
			before[i] = (temp + fund - tax)*1.06;
			temp = before[i];
		}
	}

	public void buildToFamily() {
		double life = clientDeathBenefit + spouseDeathBenefit;
		for( int i=0; i < finalDeath; i++) {
			toFamily[i] = (lifeIns[i] - life) + sideFund[i];
		}
	}
	
	
	@Override
	public void calculate() {
		setClientMonthly(getClientDeathBenefit()/100);
		setSpouseMonthly(getSpouseDeathBenefit()/100);
		buildStd();
		buildPS58();
		buildPension();
		buildContribution();
		buildSideFund();
		buildLife();
		buildToFamily();
	}


	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete(tableName, ID + getId() + "'");
		dbObj.stop();
	}


	public double[] getBefore() {
		return before;
	}


	public double getCapGrowth() {
		return capGrowth;
	}

	public int getClientAge() {
		return clientAge;
	}

	public double getClientContr() {
		return clientContr;
	}


	public double getClientDeathBenefit() {
		return clientDeathBenefit;
	}


	public double getClientLife() {
		return clientLife;
	}


	public double getClientMonthly() {
		return clientMonthly;
	}


	public double[] getContribution() {
		return contribution;
	}

	public double[] getCPs58Tax() {
		return cPs58Tax;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public long getId() {
		return id;
	}

	public double[] getLifeIns() {
		return lifeIns;
	}

	public double[] getPension() {
		return pension;
	}

	private double GETPS58Tax(int cAge, int sAge) {
		zCalc zc = new zCalc();
		zc.StartUp();
			double p58 = zc.zTABLE2001(cAge, sAge, 0);
		zc.ShutDown();
		return p58;
	}

	public HashMap<String,Object> getRecord() {
		return record;
	}

	public double[] getSideFund() {
		return sideFund;
	}

	public double getSideFundGrowth() {
		return sideFundGrowth;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public double getSpouseContr() {
		return spouseContr;
	}

	public double getSpouseDeathBenefit() {
		return spouseDeathBenefit;
	}

	public double getSpouseLife() {
		return spouseLife;
	}

	public double getSpouseMonthly() {
		return spouseMonthly;
	}

	public double[] getSPs58Tax() {
		return sPs58Tax;
	}

	public double getStdGrowth() {
		return stdGrowth;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public int getTerm() {
		return term;
	}

	public double[] getToFamily() {
		return toFamily;
	}

	@Override
	public long getToolTableId() {
		return ToolTableTypes.CPLAN.id();
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		record = null;
		
		dbAddField(CLIENT_CONTR, getClientContr());
		dbAddField(SPOUSE_CONTR, getSpouseContr());
		dbAddField(CLIENT_DEATH_BENEFIT, getClientDeathBenefit());
		dbAddField(SPOUSE_DEATH_BENEFIT, getSpouseDeathBenefit());
		dbAddField(FINAL_DEATH, getFinalDeath());
		dbAddField(CLIFE_PREMIUM, getClientLife());
		dbAddField(SLIFE_PREMIUM, getSpouseLife());
		dbAddField(TERM, getTerm());
		dbAddField(TAX_RATE, getTaxRate());
		
		int error = dbObj.insert();
		
		if( error == 0) {
			uuid = dbObj.getUUID();
			record = dbObj.execute("select ID from CP_TOOL where UUID='" + uuid + "'");
			Object o = record.get("ID");
			if( o != null ) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();
	}

	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable(tableName);
			dbObj.clearFields();
			record = null;
			
			String sql = "select * from "+ tableName + " where ID='" + id + "'";
			record = dbObj.execute(sql);
			dbObj.stop();
			
			if( record != null ) {
				setId(getLong(record,"ID"));
				setClientLife(getDouble(record,"CLIFE_PREMIUM"));
				setSpouseLife(getDouble(record,"SLIFE_PREMIUM"));
				setClientDeathBenefit(getDouble(record,"CLIENT_DEATH_BENEFIT"));
				setSpouseDeathBenefit(getDouble(record,"SPOUSE_DEATH_BENEFIT"));
				setClientContr(getDouble(record,"CLIENT_CONTR"));
				setSpouseContr(getDouble(record,"SPOUSE_CONTR"));
				setTerm(getInteger(record, "TERM"));
				setTaxRate(getDouble(record,"TAX_RATE"));
				setFinalDeath(getInteger(record,"FINAL_DEATH"));
			}
		}
	}

	@Override
	public void report() {

	}

	public void setBefore(double[] before) {
		this.before = before;
	}

	public void setCapGrowth(double capGrowth) {
		this.capGrowth = capGrowth;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public void setClientContr(double clientContr) {
		this.clientContr = clientContr;
	}

	public void setClientDeathBenefit(double clientDeathBenefit) {
		this.clientDeathBenefit = clientDeathBenefit;
	}

	public void setClientLife(double clientLife) {
		this.clientLife = clientLife;
	}

	public void setClientMonthly(double clientMonthly) {
		this.clientMonthly = clientMonthly;
	}

	public void setContribution(double[] contribution) {
		this.contribution = contribution;
	}

	public void setCPs58Tax(double[] ps58Tax) {
		cPs58Tax = ps58Tax;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLifeIns(double[] lifeIns) {
		this.lifeIns = lifeIns;
	}

	public void setPension(double[] pension) {
		this.pension = pension;
	}

	public void setRecord(HashMap<String,Object> record) {
		this.record = record;
	}

	public void setSideFund(double[] sideFund) {
		this.sideFund = sideFund;
	}

	public void setSideFundGrowth(double sideFundGrowth) {
		this.sideFundGrowth = sideFundGrowth;
	}

	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}

	public void setSpouseContr(double spouseContr) {
		this.spouseContr = spouseContr;
	}

	public void setSpouseDeathBenefit(double spouseDeathBenefit) {
		this.spouseDeathBenefit = spouseDeathBenefit;
	}

	public void setSpouseLife(double spouseLife) {
		this.spouseLife = spouseLife;
	}

	public void setSpouseMonthly(double spouseMonthly) {
		this.spouseMonthly = spouseMonthly;
	}

	public void setSPs58Tax(double[] ps58Tax) {
		sPs58Tax = ps58Tax;
	}

	public void setStdGrowth(double stdGrowth) {
		this.stdGrowth = stdGrowth;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setToFamily(double[] toFamily) {
		this.toFamily = toFamily;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		record = null;
		
		dbAddField(CLIENT_CONTR, getClientContr());
		dbAddField(SPOUSE_CONTR, getSpouseContr());
		dbAddField(CLIENT_DEATH_BENEFIT, getClientDeathBenefit());
		dbAddField(SPOUSE_DEATH_BENEFIT, getSpouseDeathBenefit());
		dbAddField(FINAL_DEATH, getFinalDeath());
		dbAddField(CLIFE_PREMIUM, getClientLife());
		dbAddField(SLIFE_PREMIUM, getSpouseLife());
		dbAddField(TERM, getTerm());
		dbAddField(TAX_RATE, getTaxRate());
		
		dbObj.setWhere("ID='" + id + "'");
		
		dbObj.update();

		dbObj.stop();
	}

	@Override
	public String writeupText() {

		return null;
	}

	

}
