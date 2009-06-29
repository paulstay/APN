package com.teag.estate;

import java.util.ArrayList;
import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.AssetSqlBean;
import com.teag.bean.InsuranceBean;

public class LifeTool extends EstatePlanningTool {

	public final static String ID = "ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	
	private long id;
	private String description;
	private final String tablename = ToolTableTypes.LIFE.toolName();
	private String uuid;
	double faceValue;
	String assetName;
	
	ArrayList<AssetData> assetList = new ArrayList<AssetData>();
	Object assetData[];

	public void buildAssetList() {
//		 Clear the list so we won't duplicate
		assetList.clear();
		// Get all of the tool asset ditributions
		ToolAssetDistribution toolAssets = new ToolAssetDistribution();
		toolAssets.setDbObject();
		toolAssets.setToolId( getId());
		toolAssets.setToolTableId(getToolTableId());
		toolAssets.calculate();
		
		faceValue = 0;

		AssetSqlBean[] asb = toolAssets.getAssetData();

		for( int i = 0; i < asb.length; i++) {
			if( asb[i] instanceof InsuranceBean ) {
				InsuranceBean ib = (InsuranceBean) asb[i];
				faceValue += ib.getFaceValue();
			}
		}
	}
	
	@Override
	public void calculate() {
		buildAssetList();
		
	}


	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete("LIFE_TOOL", ID + "'" + getId() + "'");
		dbObj.stop();

	}


	public String getAssetName() {
		return assetName;
	}


	public String getDescription() {
		return description;
	}


	public double getFaceValue() {
		return faceValue;
	}


	public long getId() {
		return id;
	}

	@Override
	public long getToolTableId() {
		return ToolTableTypes.LIFE.id();
	}

	@Override
	public void insert() {
		dbObj.start();
		dbObj.setTable(tablename);
		dbObj.clearFields();
		dbAddField(DESCRIPTION, getDescription());
		int err = dbObj.insert();
		if( err == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> rec = dbObj.execute("select " + ID + " from " + tablename + " where UUID='" + uuid + "'");
			Object obj = rec.get(ID);
			if( obj != null) {
				id = Integer.parseInt(obj.toString());
			}
		}
		dbObj.stop();

	}


	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable(tablename);
			dbObj.clearFields();
			String sql = "select * from " + tablename + " where " + ID +  "='" + getId() +
				"'";
			HashMap<String,Object> rec = dbObj.execute(sql);
			if( rec != null) {
				setDescription(getString(rec, DESCRIPTION));
			}
			dbObj.stop();
		}

	}


	@Override
	public void report() {


	}


	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable(tablename);
		dbAddField(DESCRIPTION, getDescription());
		dbObj.setWhere(ID + "='" + getId() + "'");
		dbObj.update();
		dbObj.stop();
	}

	@Override
	public String writeupText() {
		return null;
	}

}
