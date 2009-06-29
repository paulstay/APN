package com.teag.asset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.estate.constants.AssetDatabase;
import com.estate.db.DBObject;

public class AssetList {
	
	public static void main(String args[]) {
		AssetList al = new AssetList();
		al.listAssets();
	}
	
	public long ownerId;

	DBObject dbObject = new DBObject();
	
	public AssetList() {
		super();
	}
	
	public boolean getBoolean(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if(obj!= null && (((String)(obj)).equals("T") || ((String)(obj)).equals("t"))) {
				return true;
		}
		return false;
	}
	
	public String getDate(HashMap<String,Object> rec, String field) {
		//SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-M-d");
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		String fDate = "";
		Date d = (Date) rec.get(field);
		try {
			fDate = df.format(d);
		} catch (Exception e) {
			
		}
		return fDate;
	}
	
	public DBObject getDbObject(){
		return dbObject;
	}
	
	
	public double getDouble(HashMap<String,Object> r, String field) {
    	Object obj = r.get(field);
    	if( obj != null) {
    		return ((Number)obj).doubleValue();
    	}
    	return 0.0;
    }
	
	public int getInteger(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if( obj != null) {
			return ((Number) obj).intValue();
		}
		return 0;
	}

	public long getLong(HashMap<String,Object> r, String field) {
		Object obj = r.get(field);
		if( obj != null) {
			return ((Number)obj).longValue();
		}
		return 0l;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public String getString(HashMap<String,Object> rec, String field) {
		Object obj = rec.get(field);
		if( obj != null) {
			return ((String) obj);
		}
		return null;
	}
	public  ArrayList<Asset> listAssets() {
		ArrayList<Asset> alist = new ArrayList<Asset>();
		for( AssetDatabase a : AssetDatabase.values() ) {
			String stmt = selectStmt(a);
			dbObject.start();
			dbObject.setTable(a.tableName());
			HashMap<String,Object> ret = dbObject.execute( stmt);
			while( ret != null) {
				
				Asset asset = new Asset();
				asset.setId(getLong(ret, a.tableId()));
				asset.setName(getString(ret, a.description()));
				asset.setValue(getDouble(ret, a.valueId()));
				asset.setAsset(a);
				asset.setAddToEcorp(false);
				alist.add(asset);
				ret = dbObject.next();
			}
			dbObject.stop();
		}
		return alist;
	}
	public void processList(ArrayList<Asset> aList, long ownerId) {
		long newOwnerId = - ownerId;
		dbObject.start();
		// Set Foreign_key_Checks = 0 so we can set owner Id to -Id of ECorp
		dbObject.executeStatement("Set FOREIGN_KEY_CHECKS=0");
		for(Asset a : aList){
			if( a.isAddToEcorp()) {
				AssetDatabase ad = a.getAsset();
	
				String u = "Update " + ad.tableName() + " set OWNER_ID='" +
					Long.toString(newOwnerId) + "' where " + ad.id() + "='" +
						Long.toString(a.getId()) + "'";
				dbObject.executeStatement(u);
			}
		}
		// Set Foreign_key_Checks = 1 to go back to original state
		dbObject.executeStatement("Set FOREIGN_KEY_CHECKS=1");
		dbObject.stop();
	}

	public void revertList(ArrayList<Asset> aList, long ownerId) {
		long newOwnerId = ownerId;
		dbObject.start();
		// Set Foreign_key_Checks = 0 so we can set owner Id to -Id of ECorp
		dbObject.executeStatement("Set FOREIGN_KEY_CHECKS=0");
		for(Asset a : aList){
			AssetDatabase ad = a.getAsset();

			String u = "Update " + ad.tableName() + " set OWNER_ID='" +
				Long.toString(newOwnerId) + "' where " + ad.tableId() + "='" +
					Long.toString(a.getId()) + "'";
			dbObject.executeStatement(u);
		}
		// Set Foreign_key_Checks = 1 to go back to original state
		dbObject.executeStatement("Set FOREIGN_KEY_CHECKS=1");
		dbObject.stop();
	}
	
	public String selectStmt(AssetDatabase a) {
		String s = "select " + a.tableId() + ", " +a.valueId() + ", " + a.description() + " from " 
			+ a.tableName() + " where OWNER_ID=" + Long.toString(getOwnerId());
		return s;
	}
	
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
}
