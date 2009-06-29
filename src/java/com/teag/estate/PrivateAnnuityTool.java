package com.teag.estate;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.zcalc.zCalc;

public class PrivateAnnuityTool extends EstatePlanningTool {
	
	/*
	 * <Table name="PRIVATE_ANNUITY_TOOL">
		<Field name='ID' size='19' type='long' autoincrement='true' valid='true' />
		<Field name='OWNER_ID' size='19' type='long' valid='true'/>
		<Field name='DESCRIPTION' size='32' type='string' null='true'/>
		<Field name='AFR_RATE' type='number' size='2,6' null='false'/>
		<Field name='AFR_DATE' type='date' null='false'/>
		<Field name='ANNUITY_FREQ' type='number' size='12,2' null='false' />
		<Field name='ANNUITY_INCREASE' type='number' size='12,2' null='false' />
		<Field name='ANNUITY_TYPE' type='number' size='1' null='false' />
		<Field name='ESTATE_TAX_RATE' type='number' size='2,6' null='false'/>
		<Field name='INCOME_TAX_RATE' type='number' size='2,6' null='false'/>
		<Field name='CAPITAL_GAINS_RATE' type='number' size='2,6' null='false'/>
		<Field name='USE_BOTH' type='string' size='1' null='false' />
	</Table>
	 */
	
	public final static String ID = "ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String AFR_RATE = "AFR_RATE";
	public final static String AFR_DATE = "AFR_DATE";
	public final static String ANNUITY_FREQ = "ANNUITY_FREQ";
	public final static String ANNUITY_INCREASE = "ANNUITY_INCREASE";
	public final static String ANNUITY_TYPE = "ANNUITY_TYPE";
	public final static String ESTATE_TAX_RATE = "ESTATE_TAX_RATE";
	public final static String INCOME_TAX_RATE = "INCOME_TAX_RATE";
	public final static String CAPITAL_GAINS_RATE = "CAPITAL_GAINS_RATE";
	public final static String USE_BOTH = "USE_BOTH";
	
	public static void main(String args[]) {
		DecimalFormat df = new DecimalFormat("###,###,###.##");
		PrivateAnnuityTool pat = new PrivateAnnuityTool();
		pat.setAfrDate(new Date());
		pat.setAfrRate(.058);
		pat.setCAge(65);
		pat.setSAge(0);
		pat.setAmount(1000000);
		pat.setBasis(100000);
		pat.setAnnuityIncrease(0);
		pat.setAnnuityType(0);
		pat.setAnnuityFreq(0);
		pat.calculate();
		
		System.out.println("Annuity Payment: " + df.format(pat.getAnnuityPayment()));
		System.out.println("Tax Free Amount: " + df.format(pat.getTaxFreeAmount()));
		System.out.println("Cap Gains Amount: " + df.format(pat.getCapGains()));
		System.out.println("Ordinary Income: " + df.format(pat.getOrdinaryIncome()));

	}
	
	public final String tableName = "PRIVATE_ANNUITY_TOOL";
	long id;
	long owner_id;
	String description;
	double afrRate;
	Date afrDate;
	long annuityFreq;
	double annuityIncrease;
	long annuityType;
	String useBoth;
	// The following are used for reporting
	double incomeTaxRate;
	double estateTaxRate;
	
	double capitalGainsRate;
	long cAge;
	long sAge;
	double annuityPayment;
	double amount;
	double basis;

	double growth;
	double presentValueFactor;
	double ordinaryIncome;
	double capGains;
	
	double taxFreeAmount;
	double taxFreeTable[] = new double[120];
	double capGainsTable[] = new double[120];
	double ordIncome[] = new double[120];
	double annPayment[] = new double[120];
	
	@Override
	public void calculate() {
		
		EstateToolDistribution tad = new EstateToolDistribution();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());
		
		tad.buildToolAssetList();
		basis = tad.getBasis();
		growth = tad.getGrowth();
		
		// Calculate up front capital gains cost, per IRS ruling.
		double upFrontCapGains = (amount-basis) * capitalGainsRate;
		
		double tamount = amount - upFrontCapGains;

		if(annuityFreq == 0)
			annuityFreq = 1;
		
		zCalc zc = new zCalc();
		zc.StartUp();
		if(useBoth.equalsIgnoreCase("B")){
			annuityPayment = zc.zPRIVANN(0, tamount, tamount, afrRate, cAge, sAge, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			ordinaryIncome = zc.zPRIVANN(1, tamount, tamount, afrRate, cAge, sAge, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			//capGains = zc.zPRIVANN(2, tamount, tamount, afrRate, cAge, sAge, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			taxFreeAmount = zc.zPRIVANN(3, tamount, tamount, afrRate, cAge, sAge, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
		} else if( useBoth.equals("C")) {
			annuityPayment = zc.zPRIVANN(0, tamount, tamount, afrRate, cAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			ordinaryIncome = zc.zPRIVANN(1, tamount, tamount, afrRate, cAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			//capGains = zc.zPRIVANN(2, tamount, tamount, afrRate, cAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			taxFreeAmount = zc.zPRIVANN(3, tamount, tamount, afrRate, cAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			
		} else {
			annuityPayment = zc.zPRIVANN(0, tamount, tamount, afrRate, sAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			ordinaryIncome = zc.zPRIVANN(1, tamount, tamount, afrRate, sAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			//capGains = zc.zPRIVANN(2, tamount, tamount, afrRate, sAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
			taxFreeAmount = zc.zPRIVANN(3, tamount, tamount, afrRate, sAge, 0, annuityIncrease, annuityFreq, annuityType, 0l, 0l, 0l, 0l, 0l, 0l, 0l);
		}
		
		capGains = upFrontCapGains;
		
		int clientAge = 0;
		int spouseAge = 0;
		
		if( useBoth.equalsIgnoreCase("B")){
			clientAge = (int)cAge;
			spouseAge = (int)sAge;
		} else if(useBoth.equalsIgnoreCase("C")){
			clientAge = (int) cAge;
		} else {
			spouseAge = (int) sAge;
		}
		
		for(int i=0; i < 60; i++){
			annPayment[i] = zc.zPRIVANN(0, tamount, tamount, afrRate, clientAge, spouseAge, annuityIncrease, annuityFreq, annuityType, 0l, i, 0, 0, 0, 0, 0);
			ordIncome[i] = zc.zPRIVANN(1, tamount, tamount, afrRate, clientAge, spouseAge, annuityIncrease, annuityFreq, annuityType, 0l, i, 0, 0, 0, 0, 0);
			capGainsTable[i] = zc.zPRIVANN(2, tamount, tamount, afrRate, clientAge, spouseAge, annuityIncrease, annuityFreq, annuityType, 0l, i, 0, 0, 0, 0, 0);
			taxFreeTable[i] = zc.zPRIVANN(3, tamount, tamount, afrRate, clientAge, spouseAge, annuityIncrease, annuityFreq, annuityType, 0l, i, 0, 0, 0, 0, 0);
		}
		zc.ShutDown();
	}

	@Override
	public void delete() {

		dbObj.start();
		dbObj.delete(tableName, ID + getId() + "'");
		dbObj.stop();
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

	public long getAnnuityFreq() {
		return annuityFreq;
	}

	public double getAnnuityIncrease() {
		return annuityIncrease;
	}

	public double getAnnuityPayment() {
		return annuityPayment;
	}

	public long getAnnuityType() {
		return annuityType;
	}

	public double getBasis() {
		return basis;
	}

	public long getCAge() {
		return cAge;
	}

	public double getCapGains() {
		return capGains;
	}

	public double[] getCapGainsTable() {
		return capGainsTable;
	}

	public double getCapitalGainsRate() {
		return capitalGainsRate;
	}

	public String getDescription() {
		return description;
	}

	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getGrowth() {
		return growth;
	}

	public long getId() {
		return id;
	}

	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}

	public double getOrdinaryIncome() {
		return ordinaryIncome;
	}

	public long getOwner_id() {
		return owner_id;
	}

	public double getPresentValueFactor() {
		return presentValueFactor;
	}

	public long getSAge() {
		return sAge;
	}

	public double getTaxFreeAmount() {
		return taxFreeAmount;
	}

	public double[] getTaxFreeTable() {
		return taxFreeTable;
	}

	@Override
	public long getToolTableId() {

		return ToolTableTypes.PANNUITY.id();
	}

	public String getUseBoth() {
		return useBoth;
	}

	@Override
	public void insert() {
		
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		dbAddField(AFR_RATE, getAfrRate());
		dbAddDate(AFR_DATE, dateformat.format(getAfrDate()));
		dbAddField(ANNUITY_FREQ, getAnnuityFreq());
		dbAddField(ANNUITY_TYPE, getAnnuityType());
		dbAddField(ANNUITY_INCREASE, getAnnuityIncrease());
		dbAddField(CAPITAL_GAINS_RATE, getCapitalGainsRate());
		dbAddField(INCOME_TAX_RATE, getIncomeTaxRate());
		dbAddField(ESTATE_TAX_RATE, getEstateTaxRate());
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(OWNER_ID, getOwner_id());
		dbAddField(USE_BOTH, getUseBoth());

		int error = dbObj.insert();

		if( error == 0 ) {
			String uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from " + tableName +"  where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			if( o != null) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();

	}

	@Override
	public void read() {
		HashMap<String,Object> rec;
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable(tableName);
			dbObj.clearFields();
			rec = null;
			String sql = "select * from " + tableName + " where ID='" + id + "'";
			rec = dbObj.execute(sql);
			dbObj.stop();
			
			if( rec != null) {
				String aDate = getDate(rec, AFR_DATE);
				try {
					setAfrDate(dateformat.parse(aDate));
				} catch (Exception e) {
					Date now = new Date();
					setAfrDate(now);
				}
				setAfrRate(getDouble(rec, AFR_RATE));
				setAnnuityFreq(getLong(rec, ANNUITY_FREQ));
				setAnnuityType(getLong(rec, ANNUITY_TYPE));
				setAnnuityIncrease(getDouble(rec, ANNUITY_INCREASE));
				setCapitalGainsRate(getDouble(rec, CAPITAL_GAINS_RATE));
				setEstateTaxRate(getDouble(rec, ESTATE_TAX_RATE));
				setIncomeTaxRate(getDouble(rec, INCOME_TAX_RATE));
				setId(getLong(rec, ID));
				setDescription(getString(rec, DESCRIPTION));
				setOwner_id(getLong(rec, OWNER_ID));
				setUseBoth(getString(rec, USE_BOTH));
			}
		}
	}

	@Override
	public void report() {

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

	public void setAnnuityFreq(long annuityFreq) {
		this.annuityFreq = annuityFreq;
	}

	public void setAnnuityIncrease(double annuityIncrease) {
		this.annuityIncrease = annuityIncrease;
	}

	public void setAnnuityPayment(double annuityPayment) {
		this.annuityPayment = annuityPayment;
	}

	public void setAnnuityType(long annuityType) {
		this.annuityType = annuityType;
	}

	public void setBasis(double basis) {
		this.basis = basis;
	}

	public void setCAge(long age) {
		cAge = age;
	}

	public void setCapGains(double capGains) {
		this.capGains = capGains;
	}

	public void setCapGainsTable(double[] capGainsTable) {
		this.capGainsTable = capGainsTable;
	}

	public void setCapitalGainsRate(double capitalGainsRate) {
		this.capitalGainsRate = capitalGainsRate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}

	public void setOrdinaryIncome(double ordinaryIncome) {
		this.ordinaryIncome = ordinaryIncome;
	}

	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}

	public void setPresentValueFactor(double presentValueFactor) {
		this.presentValueFactor = presentValueFactor;
	}

	public void setSAge(long age) {
		sAge = age;
	}

	public void setTaxFreeAmount(double taxFreeAmount) {
		this.taxFreeAmount = taxFreeAmount;
	}

	public void setTaxFreeTable(double[] taxFreeTable) {
		this.taxFreeTable = taxFreeTable;
	}

	public void setUseBoth(String useBoth) {
		this.useBoth = useBoth;
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		dbAddField(AFR_RATE, getAfrRate());
		dbAddDate(AFR_DATE, dateformat.format(getAfrDate()));
		dbAddField(ANNUITY_FREQ, getAnnuityFreq());
		dbAddField(ANNUITY_TYPE, getAnnuityType());
		dbAddField(ANNUITY_INCREASE, getAnnuityIncrease());
		dbAddField(CAPITAL_GAINS_RATE, getCapitalGainsRate());
		dbAddField(INCOME_TAX_RATE, getIncomeTaxRate());
		dbAddField(ESTATE_TAX_RATE, getEstateTaxRate());
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(OWNER_ID, getOwner_id());
		dbAddField(USE_BOTH, getUseBoth());
		dbAddField(ID, getId());
		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();
	}

	@Override
	public String writeupText() {

		return null;
	}

	public double[] getOrdIncome() {
		return ordIncome;
	}

	public double[] getAnnPayment() {
		return annPayment;
	}
}
