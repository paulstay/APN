package com.teag.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class GiftBean extends AssetSqlBean {
	
	private static final long serialVersionUID = 1060911228642888947L;
	public final static String ID= "ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String DATE = "DATE";
	public final static String AMOUNT = "AMOUNT";
	public final static String BY_WHOM = "BY_WHOM";
	public final static String REGULARITY = "REGULARITY";
	public final static String GIFT_PLANNING = "GIFT_PLANNING";
	public final static String EXEMPTION_USED = "EXEMPTION_USED";
	public final static String GIFT_TAX_PAID = "GIFT_TAX_PAID";
	public final static String GIFT_TAX_RETURN_FILED = "GIFT_TAX_RETURN_FILED";
	
	public final static String tableName = "EXCLUSION_GIFT";
	
	private long id;
	private long ownerId;
	private String description;
	private String date;
	private double amount;
	private String byWhom;
	private String regularity;
	private String giftPlanning;
	private String exemptionUsed;
	private String giftTaxPaid;
	private String giftTaxReturnFiled;
	private String uuid;
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public double getAssetBasis() {
		return 0;
	}

	@Override
	public double getAssetGrowth() {
		return 0;
	}
	
	@Override
	public double getAssetIncome() {
		return 0;
	}
	
	@Override
	public double getAssetLiability() {
		return 0;
	}
	
	@Override
	public String getAssetName() {

		return null;
	}

	@Override
	public double getAssetValue() {
		return 0;
	}

	public ArrayList<GiftBean> getBeans(String whereClause) {
		ArrayList<GiftBean> bList = new ArrayList<GiftBean>();
		String sql = "select " + ID + " from " + tableName + " where " + whereClause;
		dbObject.start();
		HashMap<String,Object> ret = dbObject.execute(sql);
		while( ret != null) {
			Object o = ret.get(ID);
			long id = Long.parseLong(o.toString());
			GiftBean nb = new GiftBean();
			nb.setId(id);
			nb.initialize();
			bList.add(nb);
			ret = dbObject.next();
		}
		dbObject.stop();
		return bList;
	}

	public String getByWhom() {
		return byWhom;
	}

	public String getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public String getExemptionUsed() {
		return exemptionUsed;
	}

	public String getGiftPlanning() {
		return giftPlanning;
	}

	public String getGiftTaxPaid() {
		return giftTaxPaid;
	}

	public String getGiftTaxReturnFiled() {
		return giftTaxReturnFiled;
	}

	public long getId() {
		return id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public String getRegularity() {
		return regularity;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public void initialize() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		HashMap<String,Object> rec = null;

		String sql = "select * from " + tableName + " where " + ID + "='" + getId() + "'";

		if( id > 0) {
			rec = dbObject.execute(sql);
		}
		
		if( rec != null) {
			this.setOwnerId(getLong(rec,OWNER_ID));
			this.setAmount(getDouble(rec, AMOUNT));
			this.setGiftPlanning(getString(rec, GIFT_PLANNING));
			this.setByWhom(getString(rec, BY_WHOM));
			this.setDate(getDate(rec, DATE));
			this.setRegularity(getString(rec,REGULARITY));
			this.setDescription(getString(rec, DESCRIPTION));
			this.setExemptionUsed(getString(rec,EXEMPTION_USED));
			this.setGiftTaxPaid(getString(rec, GIFT_TAX_PAID));
			this.setGiftTaxReturnFiled(getString(rec,GIFT_TAX_RETURN_FILED));
		}
		dbObject.stop();
	}

	@Override
	public void insert() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(DESCRIPTION, getDescription());
		dbAddDate(DATE, getDate());
		dbAddField(AMOUNT, getAmount());
		dbAddField(BY_WHOM, getByWhom());
		dbAddField(REGULARITY, getRegularity());
		dbAddField(GIFT_PLANNING, getGiftPlanning());
		dbAddField(EXEMPTION_USED, getExemptionUsed());
		dbAddField(GIFT_TAX_PAID, getGiftTaxPaid());
		dbAddField(GIFT_TAX_RETURN_FILED, getGiftTaxReturnFiled());

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	/**
	 * Query the database records and return a list of beans for life insurance.
	 * @param field
	 * @param value
	 * @return Array of InsuranceBean
	 */
	public GiftBean[] query(String field, String value) {
		GiftBean[] records = null;
		HashMap<String,Object> rec;
		dbObject.start();
		String sqlCount = "select count(*) as cnt from " + tableName + " where " + field + "='" + value + "'";
		rec = dbObject.execute(sqlCount);
		if( rec == null )
			return null;

		long count = 0;
		count = ((Number) rec.get("cnt")).longValue();
		
		if( count > 0) {
			records = new GiftBean[(int)count];
			String sqlStmt = "select " + ID + " from " + tableName + " where " + field + "='" + value + "'";
			int i = 0;
			rec = dbObject.execute(sqlStmt);
			while( rec != null && (i < count)){
				records[i] = new GiftBean();
				records[i].setId(getLong(rec, ID));
				records[i].initialize();
				rec = dbObject.next();
				i++;
			}
		}
		
		dbObject.stop();
		return records;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setByWhom(String byWhom) {
		this.byWhom = byWhom;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExemptionUsed(String exemptionUsed) {
		this.exemptionUsed = exemptionUsed;
	}

	public void setGiftPlanning(String giftPlanning) {
		this.giftPlanning = giftPlanning;
	}

	public void setGiftTaxPaid(String giftTaxPaid) {
		this.giftTaxPaid = giftTaxPaid;
	}

	public void setGiftTaxReturnFiled(String giftTaxReturnFiled) {
		this.giftTaxReturnFiled = giftTaxReturnFiled;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setRegularity(String regularity) {
		this.regularity = regularity;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(DESCRIPTION, getDescription());
		dbAddDate(DATE, getDate());
		dbAddField(AMOUNT, getAmount());
		dbAddField(BY_WHOM, getByWhom());
		dbAddField(REGULARITY, getRegularity());
		dbAddField(GIFT_PLANNING, getGiftPlanning());
		dbAddField(EXEMPTION_USED, getExemptionUsed());
		dbAddField(GIFT_TAX_PAID, getGiftTaxPaid());
		dbAddField(GIFT_TAX_RETURN_FILED, getGiftTaxReturnFiled());

		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}
}
