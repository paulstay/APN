/**
 * 
 */
package com.teag.estate;

/**
 * @author Paul Stay
 * Description - IDIT Tool, Without using a GRAT
 * Date Jan 2009
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.EPTAssetDistBean;
import com.zcalc.zCalc;

public class SIditTool extends EstatePlanningTool {

	public final static String ID = "ID";
	public final static String NOTE_RATE = "NOTE_RATE";
	public final static String NOTE_TERM = "NOTE_TERM";
	public final static String NOTE_TYPE = "NOTE_TYPE";
	public final static String TAX_RATE = "TAX_RATE";
	public final static String FINAL_DEATH = "FINAL_DEATH";
	public final static String LIFE_DEATH_BENEFIT = "LIFE_DEATH_BENEFIT";
	public final static String LIFE_PREMIUM = "LIFE_PREMIUM";
	public final static String LIFE_PREMIUM_YEARS = "LIFE_PREMIUM_YEARS";
	public final static String GIFT_TYPE = "GIFT_TYPE";
	public final static String GIFT_AMOUNT = "GIFT_AMOUNT";

	public final static String tableName = ToolTableTypes.SIDIT.toolName();

	long id;
	double noteRate;
	int noteTerm;
	int noteType = 0; // Initially we will use only a promisary interest note
	// with a balloon payment, later we can add a SCIN
	double taxRate; // Combined federal and state for summary purposes.
	int finalDeath;
	double lifeDeathBenefit;
	double lifePremium;
	int lifePremiumYears;
	String giftType = "C"; 	// Initial Gift to IDIT, C = Client, A = Assets from
							// Children, N = Note From Children
	double giftAmount = 0;

	// For calculations.....
	double fmv; // Fair Market Value
	double dmv; // Discounted market value
	double payment;

	double[] balance;
	double[] growth;
	double[] income;
	double[] notePayment;
	double[] noteBalance;

	double[] tax;

	String assetName;
	double assetGrowth;
	double assetIncome;

	double finalBalance;
	boolean useLLC = false;

	@Override
	public void calculate() {
		EstateToolDistribution tad = new EstateToolDistribution();
		tad.setToolId(id);
		tad.setToolTableId(getToolTableId());

		// Call this to get the amount of the gift!
		initAssets();

		// Get the distributions, so we can figure out the gift

		tad.buildToolAssetList();
		
		useLLC = tad.isUseLLC();
		assetName = tad.getAssetNames();
		assetGrowth = tad.getGrowth();
		assetIncome = tad.getIncome();

		balance = new double[finalDeath];
		growth = new double[finalDeath];
		income = new double[finalDeath];
		notePayment = new double[finalDeath];
		noteBalance = new double[finalDeath];
		tax = new double[finalDeath];

		fmv = tad.getTotalValue();
		dmv = tad.getDiscountValue();

		if(dmv <=0){
			dmv = fmv - giftAmount;
		}
		
		double bal = fmv;
		for (int i = 0; i < finalDeath; i++) {
			double g = bal * assetGrowth;
			double d = bal * assetIncome;
			bal += g + d;
		}

		finalBalance = bal;

		double b = fmv;
		double lifePayment = lifePremium;
		zCalc zc = new zCalc();
		zc.StartUp();
		for (int i = 0; i < finalDeath; i++) {
			if (i >= lifePremiumYears) {
				lifePayment = 0;
			}

			balance[i] = b;
			growth[i] = b * tad.getGrowth();
			income[i] = b * tad.getIncome();
			notePayment[i] = zc.zINSTALL(0, dmv, 0, noteRate, noteTerm, i + 1,
					noteType, 0, 0, 0);
			noteBalance[i] = zc.zINSTALL(4, dmv, 0, noteRate, noteTerm, i + 1,
					noteType, 0, 0, 0);

			tax[i] = income[i] * taxRate;
			b += growth[i] + income[i] - lifePayment - notePayment[i];
		}
		zc.ShutDown();
	}

	// Get Gift totals, and separate them from the rest of the assets.
	public void initAssets() {

		ArrayList<EPTAssetDistBean> oList = new ArrayList<EPTAssetDistBean>();

		EPTAssetDistBean ea = new EPTAssetDistBean();
		oList = ea.getBeans("TOOL_ID='" + getId() + "' and TOOL_TYPE_ID='"
				+ getToolTableId() + "'");

		double gifts = 0;

		for (EPTAssetDistBean ept : oList) {
			if (ept.isGift())
				gifts += ept.getValue();
		}
		giftAmount = gifts;
	}

	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete(tableName, ID + getId() + "'");
		dbObj.stop();
	}

	public double getAssetGrowth() {
		return assetGrowth;
	}

	public double getAssetIncome() {
		return assetIncome;
	}

	public String getAssetName() {
		return assetName;
	}

	public double[] getBalance() {
		return balance;
	}

	public double getDmv() {
		return dmv;
	}

	public double getFinalBalance() {
		return finalBalance;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public double getFmv() {
		return fmv;
	}

	public double[] getGrowth() {
		return growth;
	}

	public long getId() {
		return id;
	}

	public double[] getIncome() {
		return income;
	}

	public double getLifeDeathBenefit() {
		return lifeDeathBenefit;
	}

	public double getLifePremium() {
		return lifePremium;
	}

	public int getLifePremiumYears() {
		return lifePremiumYears;
	}

	public double[] getNotePayment() {
		return notePayment;
	}

	public double getNoteRate() {
		return noteRate;
	}

	public int getNoteTerm() {
		return noteTerm;
	}

	public int getNoteType() {
		return noteType;
	}

	public double getPayment() {
		return payment;
	}

	public double[] getTax() {
		return tax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	@Override
	public long getToolTableId() {
		return ToolTableTypes.SIDIT.id();
	}

	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		HashMap<String, Object> record = null;

		dbAddField(NOTE_RATE, getNoteRate());
		dbAddField(NOTE_TERM, getNoteTerm());
		dbAddField(NOTE_TYPE, getNoteType());
		dbAddField(TAX_RATE, getTaxRate());
		dbAddField(FINAL_DEATH, getFinalDeath());
		dbAddField(LIFE_DEATH_BENEFIT, getLifeDeathBenefit());
		dbAddField(LIFE_PREMIUM, getLifePremium());
		dbAddField(LIFE_PREMIUM_YEARS, getLifePremiumYears());
		dbAddField(GIFT_TYPE, getGiftType());
		dbAddField(GIFT_AMOUNT, getGiftAmount());

		int error = dbObj.insert();

		if (error == 0) {
			String uuid = dbObj.getUUID();
			record = dbObj.execute("select ID from " + tableName
					+ " where UUID='" + uuid + "'");
			Object o = record.get("ID");
			if (o != null) {
				id = Integer.parseInt(o.toString());
			}
		}
		dbObj.stop();
	}

	@Override
	public void read() {
		if (id > 0) {
			dbObj.start();
			dbObj.setTable(tableName);
			dbObj.clearFields();
			HashMap<String, Object> record = null;

			String sql = "select * from " + tableName + " where ID='" + id
					+ "'";
			record = dbObj.execute(sql);
			dbObj.stop();

			if (record != null) {
				setId(getLong(record, ID));
				setNoteRate(getDouble(record, NOTE_RATE));
				setNoteTerm(getInteger(record, NOTE_TERM));
				setNoteType(getInteger(record, NOTE_TYPE));
				setTaxRate(getDouble(record, TAX_RATE));
				setFinalDeath(getInteger(record, FINAL_DEATH));
				setLifeDeathBenefit(getDouble(record, LIFE_DEATH_BENEFIT));
				setLifePremium(getDouble(record, LIFE_PREMIUM));
				setLifePremiumYears(getInteger(record, LIFE_PREMIUM_YEARS));
				setGiftType(getString(record, GIFT_TYPE));
				setGiftAmount(getDouble(record, GIFT_AMOUNT));
			}
		}
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();

		dbAddField(NOTE_RATE, getNoteRate());
		dbAddField(NOTE_TERM, getNoteTerm());
		dbAddField(NOTE_TYPE, getNoteType());
		dbAddField(TAX_RATE, getTaxRate());
		dbAddField(FINAL_DEATH, getFinalDeath());
		dbAddField(LIFE_DEATH_BENEFIT, getLifeDeathBenefit());
		dbAddField(LIFE_PREMIUM, getLifePremium());
		dbAddField(LIFE_PREMIUM_YEARS, getLifePremiumYears());
		dbAddField(GIFT_TYPE, getGiftType());
		dbAddField(GIFT_AMOUNT, getGiftAmount());

		dbObj.setWhere("ID='" + id + "'");

		dbObj.update();

		dbObj.stop();

	}

	@Override
	public void report() {

	}

	public void setAssetGrowth(double assetGrowth) {
		this.assetGrowth = assetGrowth;
	}

	public void setAssetIncome(double assetIncome) {
		this.assetIncome = assetIncome;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public void setBalance(double[] balance) {
		this.balance = balance;
	}

	public void setDmv(double dmv) {
		this.dmv = dmv;
	}

	public void setFinalBalance(double finalBalance) {
		this.finalBalance = finalBalance;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setFmv(double fmv) {
		this.fmv = fmv;
	}

	public void setGrowth(double[] growth) {
		this.growth = growth;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIncome(double[] income) {
		this.income = income;
	}

	public void setLifeDeathBenefit(double lifeDeathBenefit) {
		this.lifeDeathBenefit = lifeDeathBenefit;
	}

	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
	}

	public void setLifePremiumYears(int lifePremiumYears) {
		this.lifePremiumYears = lifePremiumYears;
	}

	public void setNotePayment(double[] notePayment) {
		this.notePayment = notePayment;
	}

	public void setNoteRate(double noteRate) {
		this.noteRate = noteRate;
	}

	public void setNoteTerm(int noteTerm) {
		this.noteTerm = noteTerm;
	}

	public void setNoteType(int noteType) {
		this.noteType = noteType;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public void setTax(double[] tax) {
		this.tax = tax;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public String writeupText() {
		return null;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public double getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(double giftAmount) {
		this.giftAmount = giftAmount;
	}

	public boolean isUseLLC() {
		return useLLC;
	}

	public void setUseLLC(boolean useLLC) {
		this.useLLC = useLLC;
	}

	public double[] getNoteBalance() {
		return noteBalance;
	}

	public void setNoteBalance(double[] noteBalance) {
		this.noteBalance = noteBalance;
	}

}
