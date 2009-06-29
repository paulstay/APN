package com.teag.bean;

import java.util.HashMap;

public class ABTrust extends AssetSqlBean {
	private static final long serialVersionUID = 4489265993288407335L;

	public final static String ID = "ID";
	public final static String OWNER_ID = "OWNER_ID";
	public final static String USED = "USED";
	public final static String TRUSTS = "TRUSTS";
	public final static String BEFORE_TRUSTS = "BEFORE_TRUSTS";
	public final static String AFTER_TRUSTS = "AFTER_TRUSTS";
	
	public static String tableName = "AB_TRUST";
	private long id = -1;
	private long ownerId;
	private String used;
	private int trusts;
	private int afterTrusts;
	
	private int beforeTrusts;
	
	public void delete() {
		dbObject.start();
		String whereClause = ID + "='" + getId() + "'";
		dbObject.delete(tableName, whereClause);
		dbObject.stop();
	}

	public int getAfterTrusts() {
		return afterTrusts;
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
		return "";
	}

	@Override
	public double getAssetValue() {
		return 0;
	}

	public int getBeforeTrusts() {
		return beforeTrusts;
	}

	public long getId() {
		return id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public int getTrusts() {
		return trusts;
	}

	public String getUsed() {
		return used;
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
			this.setId(getLong(rec,ID));
			this.setOwnerId(getLong(rec,OWNER_ID));
			this.setUsed(getString(rec,USED));
			this.setTrusts(getInteger(rec,TRUSTS));
			this.setAfterTrusts(getInteger(rec,AFTER_TRUSTS));
			this.setBeforeTrusts(getInteger(rec,BEFORE_TRUSTS));
		}
		dbObject.stop();
	}

	@Override
	public void insert() {
		String uuid;
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(USED, getUsed());
		dbAddField(TRUSTS, getTrusts());
		dbAddField(BEFORE_TRUSTS, getBeforeTrusts());
		dbAddField(AFTER_TRUSTS, getAfterTrusts());

		dbObject.insert();
		uuid = dbObject.getUUID();
		HashMap<String,Object> ret = dbObject.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
		Object o = ret.get(ID);
		id = Integer.parseInt(o.toString());
		dbObject.stop();
	}

	public void query(long owner) {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();
		HashMap<String,Object> rec = null;

		String sql = "select * from " + tableName + " where " + OWNER_ID + "='" + owner + "'";

		rec = dbObject.execute(sql);
		
		dbObject.stop();
		if( rec != null) {
			this.setId(getLong(rec,ID));
			this.setOwnerId(getLong(rec,OWNER_ID));
			this.setUsed(getString(rec,USED));
			this.setTrusts(getInteger(rec,TRUSTS));
			this.setAfterTrusts(getInteger(rec,AFTER_TRUSTS));
			this.setBeforeTrusts(getInteger(rec,BEFORE_TRUSTS));
		} else {
			setOwnerId(owner);
			setUsed("0");
			setTrusts(1);
			setAfterTrusts(0);
			setBeforeTrusts(0);
			insert();
		}
	}

	public void setAfterTrusts(int afterTrusts) {
		this.afterTrusts = afterTrusts;
	}

	public void setBeforeTrusts(int beforeTrusts) {
		this.beforeTrusts = beforeTrusts;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setTrusts(int trusts) {
		this.trusts = trusts;
	}

	public void setUsed(String flag) {
		used = flag;
	}

	@Override
	public void update() {
		dbObject.start();
		dbObject.setTable(tableName);
		dbObject.clearFields();

		dbAddField(ID,getId());
		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(USED, getUsed());
		dbAddField(TRUSTS, getTrusts());
		dbAddField(BEFORE_TRUSTS, getBeforeTrusts());
		dbAddField(AFTER_TRUSTS, getAfterTrusts());

		dbObject.setWhere(ID + "='" + id + "'");
		dbObject.update();
		dbObject.stop();
	}
}
