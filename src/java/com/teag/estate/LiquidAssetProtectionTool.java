package com.teag.estate;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;

public class LiquidAssetProtectionTool extends EstatePlanningTool {
	/*
	 * <Table name="LAP_TOOL" comments="Liquid Asset Protection Plan">
		<Field name="ID" type='long' size='19' null='false' autoincrement='true' value='true' />
		<Field name='OWNER_ID' type='long'  size='19' null='false' />
		<Field name='DESCRIPTION' type='string' size='120' null='true'/>
		<Field name="ANNUITY" type="number" size="12,2" />
		<Field name="LIFE_FACE" type="number" size="12,2" />
		<Field name="LIFE_PREMIUM" type="number" size="12,2" />
		<Field name="ANNUITY_EXCLUSION_RATION" type="number" size="12,2" />
		<Field name="INCOME_TAX_RATE" type="number" size="3,4" />
		<Field name="ESTATE_TAX_RATE" type="number" size="3,4" />
		<PrimaryKey field="ID"/>
	</Table>
	 */
	public static final String ID = "ID";
	public static final String OWNER_ID = "OWNER_ID";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String ANNUITY = "ANNUITY";
	public static final String LIFE_FACE = "LIFE_FACE";
	public static final String LIFE_PREMIUM = "LIFE_PREMIUM";
	public static final String ANNUITY_EXCLUSION_RATIO = "ANNUITY_EXCLUSION_RATIO";
	public static final String INCOME_TAX_RATE = "INCOME_TAX_RATE";
	public static final String ESTATE_TAX_RATE = "ESTATE_TAX_RATE";
	public static final String LIFE_OVER_FUNDED = "LIFE_OVER_FUNDED";
	
	public static final String tableName = ToolTableTypes.LAP.toolName();
	
	public static void main(String args[]) {
		LiquidAssetProtectionTool lap = new LiquidAssetProtectionTool();
		lap.setId(1);
		/*
		lap.setAmount(1000000);
		lap.setAnnuityMonthlyIncome(10430);
		lap.setAnnuityExclusionRatio(.75);
		lap.setLifeFaceValue(1000000);
		lap.setLifePremium(54000);
		lap.setEstateTaxRate(.45);
		lap.setIncomeTaxRate(.35);
		
		lap.insert();
		*/
		lap.read();
		lap.setAmount(1000000);
		lap.calculate();
		DecimalFormat df = new DecimalFormat("$###,###,###");
		System.out.println("Income from Liquid Assets\t\t\t" + df.format(lap.getAnnualAnnuity()));
		double tax = (lap.getAnnualAnnuity() * (1 - lap.getAnnuityExclusionRatio()) * lap.getIncomeTaxRate())*(-1);
		System.out.println("Income Tax Due           \t\t\t" + df.format(tax));
		System.out.println("Net After Tax Income     \t\t\t" + df.format(lap.getAnnualAnnuity()+tax));
		System.out.println("Less Insurance Premium   \t\t\t" + df.format(-lap.getLifePremium()));
		System.out.println("Net Spendable            \t\t\t" + df.format((lap.getAnnualAnnuity()+tax-lap.getLifePremium())));
	
	}
	long id;
	String description;
	double amount;
	double incomeTaxRate;
	double annuityExclusionRatio;
	double annuityMonthlyIncome;
	double estateTaxRate;
	double cAge;
	double sAge;
	double lifeFaceValue;
	double lifePremium;
	
	boolean overFunded = true;
	double amountToAnnuity;
	double percentToAnnuity;
	double annualAnnuity;
	double excludedIncome;
	double taxBasisRecovered;
	
	double taxDue;
	double growth;
	

	double income;

	@Override
	public void calculate() {
		EstateToolDistribution tad = new EstateToolDistribution();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());
		
		tad.buildToolAssetList();
		amount = tad.getTotalValue();
		growth = tad.getGrowth();
		income = tad.getIncome();
		
		amountToAnnuity = amount - (lifePremium);
		percentToAnnuity = amountToAnnuity/amount;
		annualAnnuity = annuityMonthlyIncome * percentToAnnuity * 12;
		excludedIncome = annualAnnuity * annuityExclusionRatio;
		taxBasisRecovered = amount/(annuityMonthlyIncome * annuityExclusionRatio)/12;
		taxDue = (getAnnualAnnuity() * (1 - getAnnuityExclusionRatio()) * getIncomeTaxRate())*(-1);
		
	}


	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete(tableName, ID + getId() + "'");
		dbObj.stop();
	}


	public double getAmount() {
		return amount;
	}


	public double getAmountToAnnuity() {
		return amountToAnnuity;
	}


	public double getAnnualAnnuity() {
		return annualAnnuity;
	}


	public double getAnnuityExclusionRatio() {
		return annuityExclusionRatio;
	}

	public double getAnnuityMonthlyIncome() {
		return annuityMonthlyIncome;
	}

	public double getCAge() {
		return cAge;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getEstateTaxRate() {
		return estateTaxRate;
	}

	public double getExcludedIncome() {
		return excludedIncome;
	}

	public double getGrowth() {
		return growth;
	}

	public long getId() {
		return id;
	}

	public double getIncome() {
		return income;
	}

	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}

	public double getLifeFaceValue() {
		return lifeFaceValue;
	}

	public double getLifePremium() {
		return lifePremium;
	}

	public double getPercentToAnnuity() {
		return percentToAnnuity;
	}

	public double getSAge() {
		return sAge;
	}

	public double getTaxBasisRecovered() {
		return taxBasisRecovered;
	}

	public double getTaxDue() {
		return taxDue;
	}

	@Override
	public long getToolTableId() {
		return ToolTableTypes.LAP.id();
	}

	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		dbAddField(ANNUITY, getAnnuityMonthlyIncome());
		dbAddField(ANNUITY_EXCLUSION_RATIO, getAnnuityExclusionRatio());
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(ESTATE_TAX_RATE, getEstateTaxRate());
		dbAddField(INCOME_TAX_RATE, getIncomeTaxRate());
		dbAddField(LIFE_FACE, getLifeFaceValue());
		dbAddField(LIFE_PREMIUM, getLifePremium());
		if(overFunded) {
			dbAddField(LIFE_OVER_FUNDED, "Y");
		} else {
			dbAddField(LIFE_OVER_FUNDED, "F");
		}
		int error = dbObj.insert();

		if( error == 0 ) {
			String uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select ID from LAP_TOOL where UUID='" + uuid + "'");
			Object o = ret.get("ID");
			if( o != null) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();

	}

	public boolean isOverFunded() {
		return overFunded;
	}

	@Override
	public void read() {
		HashMap<String,Object> rec;
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable(tableName);
			dbObj.clearFields();
			rec = null;
			String sql = "select * from LAP_TOOL where ID='" + id + "'";
			rec = dbObj.execute(sql);
			dbObj.stop();
			
			if( rec != null) {
				setAnnuityMonthlyIncome(getDouble(rec, ANNUITY));
				setAnnuityExclusionRatio(getDouble(rec, ANNUITY_EXCLUSION_RATIO));
				setDescription(getString(rec, DESCRIPTION));
				setEstateTaxRate(getDouble(rec,ESTATE_TAX_RATE));
				setIncomeTaxRate(getDouble(rec,INCOME_TAX_RATE));
				setLifeFaceValue(getDouble(rec,LIFE_FACE));
				setLifePremium(getDouble(rec,LIFE_PREMIUM));
				String of = getString(rec,LIFE_OVER_FUNDED);
				if(of.equalsIgnoreCase("Y")){
					overFunded = true;
				}	else { 
					overFunded = false;
				}
			}
		}

	}

	@Override
	public void report() {


	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setAmountToAnnuity(double amountToAnnuity) {
		this.amountToAnnuity = amountToAnnuity;
	}

	public void setAnnualAnnuity(double annualAnnuity) {
		this.annualAnnuity = annualAnnuity;
	}

	public void setAnnuityExclusionRatio(double annuityExclusionRatio) {
		this.annuityExclusionRatio = annuityExclusionRatio;
	}

	public void setAnnuityMonthlyIncome(double annuityMonthlyIncome) {
		this.annuityMonthlyIncome = annuityMonthlyIncome;
	}

	public void setCAge(double age) {
		cAge = age;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEstateTaxRate(double estateTaxRate) {
		this.estateTaxRate = estateTaxRate;
	}

	public void setExcludedIncome(double excludedIncome) {
		this.excludedIncome = excludedIncome;
	}

	public void setGrowth(double growth) {
		this.growth = growth;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}

	public void setLifeFaceValue(double lifeFaceValue) {
		this.lifeFaceValue = lifeFaceValue;
	}

	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
	}

	public void setOverFunded(boolean overFunded) {
		this.overFunded = overFunded;
	}

	public void setPercentToAnnuity(double percentToAnnuity) {
		this.percentToAnnuity = percentToAnnuity;
	}

	public void setSAge(double age) {
		sAge = age;
	}

	public void setTaxBasisRecovered(double taxBasisRecovered) {
		this.taxBasisRecovered = taxBasisRecovered;
	}

	public void setTaxDue(double taxDue) {
		this.taxDue = taxDue;
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		dbAddField(ANNUITY, getAnnuityMonthlyIncome());
		dbAddField(ANNUITY_EXCLUSION_RATIO, getAnnuityExclusionRatio());
		dbAddField(DESCRIPTION, getDescription());
		dbAddField(ESTATE_TAX_RATE, getEstateTaxRate());
		dbAddField(INCOME_TAX_RATE, getIncomeTaxRate());
		dbAddField(LIFE_FACE, getLifeFaceValue());
		dbAddField(LIFE_PREMIUM, getLifePremium());
		if(overFunded) {
			dbAddField(LIFE_OVER_FUNDED, "Y");
		} else {
			dbAddField(LIFE_OVER_FUNDED, "F");
		}
		
		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();
	}

	@Override
	public String writeupText() {
		return null;
	}
	
}
