package com.teag.bean;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.estate.db.DBObject;

/**
 * @author stay Created on May 24, 2005
 * 
 *         CashFlow variables for use in both scenario's
 * 
 */

public class CashFlowBean {

	public final static String OWNER_ID = "OWNER_ID";
	public final static String SALARY = "SALARY";
	public final static String SALARY_GROWTH = "SALARY_GROWTH";
	public final static String SOCIAL_SECURITY = "SOCIAL_SECUITY";
	public final static String SOCIAL_SECURITY_GROWTH = "SOCIAL_SECURITY_GROWTH";
	public final static String INFLATION = "INFLATION";
	public final static String FINAL_DEATH = "FINAL_DEATH";
	public final static String STATE_TAX_RATE = "STATE_TAX_RATE";
	public final static String CHARITY = "CHARITY";
        public final static String DEPRECIATION = "DEPRECIATION";
        public final static String USE_TAX = "USE_TAX";

	// General formating.
	public static DecimalFormat number = new DecimalFormat("0.00");
	public static DecimalFormat percentformat = new DecimalFormat("0.00%");
	public static DecimalFormat taxformat = new DecimalFormat("0.0000%");
	public static DecimalFormat weight = new DecimalFormat("0.0000");
	public static DecimalFormat dollarformat = new DecimalFormat("0.00");
	public static DecimalFormat moneyformat = new DecimalFormat("$0.00");
	public static SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
	DBObject dbObject = new DBObject();
	private long id;
	private long ownerId;

	private String uuid;

	private double socialSecurity;
	private double socialSecurityGrowth;
	private double inflation;
	private double charitableGifts;
	private double charity; // Tithing
	private int finalDeath;
	private double stateTaxRate;
	private boolean loaded;
        private double depreciation;
        private boolean useTax = true; // default is true
	HashMap<String, Object> rec;

	public CashFlowBean() {
		loaded = false;
	}

	public void initialize() {
		boolean newFlag = false;
		dbObject.start();
		dbObject.setTable("CASHFLOW_VAR");
		rec = null;

		String sql = "select * from CASHFLOW_VAR where OWNER_ID='"
				+ Long.toString(ownerId) + "'";
		rec = dbObject.execute(sql);

		if (rec != null) {
			setSocialSecurity(getDouble(rec, "SOCIAL_SECURITY"));
			setSocialSecurityGrowth(getDouble(rec, "SOCIAL_SECURITY_GROWTH"));
			setInflation(getDouble(rec, "INFLATION"));
			setFinalDeath(getInteger(rec, "FINAL_DEATH"));
			setStateTaxRate(getDouble(rec, "STATE_TAX_RATE"));
			setCharity(getDouble(rec,CHARITY));
			setId(getLong(rec, "ID"));
                        setDepreciation(getDouble(rec,DEPRECIATION));
                        String tax = getString(rec,USE_TAX);
                        if(tax.equals("Y")){
                            setUseTax(true);
                        } else {
                            setUseTax(false);
                        }
                        loaded = true;
		} else {
			setDefault();
			newFlag = true;
		}

		dbObject.stop();
		if (newFlag) {
			insert();
		}
	}

	public void insert() {
		dbObject.start();
		dbObject.setTable("CASHFLOW_VAR");
		dbObject.clearFields();
		dbAddField("OWNER_ID", this.getOwnerId());
		dbAddField("SOCIAL_SECURITY", getSocialSecurity());
		dbAddField("SOCIAL_SECURITY_GROWTH", getSocialSecurityGrowth());
		dbAddField("INFLATION", getInflation());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("STATE_TAX_RATE", getStateTaxRate());
		dbAddField(CHARITY, getCharity());
                dbAddField(DEPRECIATION, getDepreciation());
                dbAddField(USE_TAX, isUseTax() ? "Y" : "N");

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String, Object> ret = dbObject
				.execute("select ID from CASHFLOW_VAR where UUID='" + uuid
						+ "'");
		Object o = ret.get("ID");
		setId(Integer.parseInt(o.toString()));
		loaded = true;
		dbObject.stop();
	}

	public void update() {
		dbObject.start();
		dbObject.setTable("CASHFLOW_VAR");
		dbObject.clearFields();
		dbAddField("SOCIAL_SECURITY", getSocialSecurity());
		dbAddField("SOCIAL_SECURITY_GROWTH", getSocialSecurityGrowth());
		dbAddField("INFLATION", getInflation());
		dbAddField("FINAL_DEATH", getFinalDeath());
		dbAddField("STATE_TAX_RATE", getStateTaxRate());
		dbAddField(CHARITY, getCharity());
                dbAddField(DEPRECIATION, getDepreciation());
                dbAddField(USE_TAX, isUseTax() ? "Y" : "N");
		dbObject.setWhere("OWNER_ID='" + Long.toString(getOwnerId()) + "'");
		dbObject.update();
		dbObject.stop();
	}

	public void setDefault() {
		socialSecurity = 1342; // monthly
		socialSecurityGrowth = .03;
		inflation = .03;
		finalDeath = 25;
		stateTaxRate = .093; // California
		charity = 0;
                useTax = true;
	}

	public void delete() {
		loaded = false;
		dbObject.start();
		dbObject.setTable("CASHFLOW_VAR");
		dbObject.delete("CASHFLOW_VAR", "ID='" + Long.toString(id) + "'");
	}

	public void dbAddDate(String field, String date) {
		Date nDate = dateformat.parse(date, new ParsePosition(0));
		dbObject.addField(field, dbObject.dbDate(nDate));
	}

	public void dbAddField(String field, boolean value) {
		String str = value == true ? "true" : "false";
		dbAddField(field, str, false);
	}

	public void dbAddField(String field, double value) {
		String str = weight.format(value);
		dbAddField(field, str, false);
	}

	public void dbAddField(String field, float value) {
		String str = number.format(value);
		dbAddField(field, str, false);
	}

	public void dbAddField(String field, int value) {
		String str = number.format(value);
		dbAddField(field, str, false);
	}

	public void dbAddField(String field, String value) {
		dbAddField(field, value, false);
	}

	public void dbAddField(String field, String value, boolean nFlag) {
		if (value != null && value.length() > 0) {
			dbObject.addField(field, DBObject.dbString(value));
		} else if (nFlag) {
			dbObject.addField(field, DBObject.dbString(""));
		} else {
			dbObject.addField(field, DBObject.dbString("NULL"));
		}
	}

	public boolean getBoolean(HashMap<String, Object> rec, String field) {
		Object obj = rec.get(field);
		if (obj != null
				&& (((String) (obj)).equals("T") || ((String) (obj))
						.equals("t"))) {
			return true;
		}
		return false;
	}

	public double getCharitableGifts() {
		return charitableGifts;
	}

	public String getDate(HashMap<String, Object> rec, String field) {
		Date rDate = (Date) rec.get(field);
		if (rDate != null) {
			try {
				return dateformat.format(rDate);
			} catch (Exception e) {
			}
		}
		return "";
	}

	public double getDouble(HashMap<String, Object> r, String field) {
		Object obj = r.get(field);
		if (obj != null) {
			return ((Number) obj).doubleValue();
		}
		return 0.0;
	}

	public int getFinalDeath() {
		return finalDeath;
	}

	public long getId() {
		return id;
	}

	public double getInflation() {
		return inflation;
	}

	public int getInteger(HashMap<String, Object> r, String field) {
		Object obj = r.get(field);
		if (obj != null) {
			return ((Number) obj).intValue();
		}
		return 0;
	}

	public long getLong(HashMap<String, Object> r, String field) {
		Object obj = r.get(field);
		if (obj != null) {
			return ((Number) obj).longValue();
		}
		return 0l;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public double getSocialSecurity() {
		return socialSecurity;
	}

	public double getSocialSecurityGrowth() {
		return socialSecurityGrowth;
	}

	public double getStateTaxRate() {
		return stateTaxRate;
	}

	public String getString(HashMap<String, Object> rec, String field) {
		Object obj = rec.get(field);
		if (obj != null) {
			return ((String) obj);
		}
		return null;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setCharitableGifts(double charitableGifts) {
		this.charitableGifts = charitableGifts;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInflation(double inflation) {
		this.inflation = inflation;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setSocialSecurity(double socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public void setSocialSecurityGrowth(double socialSecurityGrowth) {
		this.socialSecurityGrowth = socialSecurityGrowth;
	}

	public void setStateTaxRate(double stateTaxRate) {
		this.stateTaxRate = stateTaxRate;
	}

	public double getCharity() {
		return charity;
	}

	public void setCharity(double charity) {
		this.charity = charity;
	}

    public double getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(double depreciation) {
        this.depreciation = depreciation;
    }

    public boolean isUseTax() {
        return useTax;
    }

    public void setUseTax(boolean useTax) {
        this.useTax = useTax;
    }


}
