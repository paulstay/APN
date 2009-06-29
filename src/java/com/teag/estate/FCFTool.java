package com.teag.estate;

import java.util.HashMap;

import com.estate.constants.ToolTableTypes;

public class FCFTool extends EstatePlanningTool {
	/*
	 * create table FAMILY_CHARITY_TOOL(
		ID bigint(19) not null auto_increment,
		OWNER_ID bigint(19) not null,
		DESCRIPTION varchar(32),
		TYPE varchar(1) not null, -- S=Supporting Organization,D=Donar Advise Fund,P=Private Family Foundation
		CASH_AMOUNT decimal(12,1) not null,
		START_YEAR decimal(4) not null,
		END_YEAR decimal(4) not null,
		GROWTH decimal(2,4) not null,
		DISTRIBUTION decimal(2,4) not null,
		DONATION_TYPE varchar(1) not null, -- C=CASH,G=Capital Gains Property
		UUID varchar(36) not null,
		CREATE_USER_NAME varchar(30) not null,
		CREATE_DATE datetime not null,
		UPDATE_USER_NAME varchar(30) not null,
		UPDATE_DATE datetime not null,
		primary key (ID)
	) TYPE = INNODB;
	 */
	public final static String ID="ID";
	public final static String OWNER_ID="OWNER_ID";
	public final static String DESCRIPTION = "DESCRIPTION";
	public final static String TYPE = "TYPE";
	
	private long id;
	private long ownerId;
	private final String description="Family Charitable Foundation";
	private String type;

	private final String tableName = "FAMILY_CHARITY_TOOL";
	
	// There is nothing to calcualte here!
	@Override
	public void calculate() {


	}

	// Delete the tool from the plan
	@Override
	public void delete() {
		dbObj.start();
		dbObj.delete(tableName, "ID='" + id + "'");
		dbObj.stop();
	}

	
	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}


	public long getOwnerId() {
		return ownerId;
	}

	@Override
	public long getToolTableId() {
		return ToolTableTypes.FCF.id();
	}

	public String getType() {
		return type;
	}

	@Override
	public void insert() {
		String uuid;
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		dbAddField(DESCRIPTION, description);
		dbAddField(TYPE, getType());
		dbAddField(OWNER_ID, getOwnerId());
		
		int error = dbObj.insert();

		if( error == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> ret = dbObj.execute("select " + ID + " from " + tableName + " where UUID='" + uuid + "'");
			Object o = ret.get(ID);
			this.id = Integer.parseInt(o.toString());
		}
		dbObj.stop();
	}

	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable(tableName);
			HashMap<String,Object> account = null;
			String sql = "select * from " + tableName + " where " + ID + "='"+ id +"'";
			account = dbObj.execute(sql);
			dbObj.stop();
			
			if(account != null) {
				this.setOwnerId(getLong(account,OWNER_ID));
				this.setType(getString(account,TYPE));
			}
		}
	}

	@Override
	public void report() {

	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void update() {
		dbObj.start();
		dbObj.setTable(tableName);
		dbObj.clearFields();
		dbAddField(OWNER_ID, getOwnerId());
		dbAddField(DESCRIPTION, description);
		dbAddField(TYPE, getType());
		dbObj.setWhere(ID + "='" + id + "'");
		dbObj.update();
		dbObj.stop();

	}

	@Override
	public String writeupText() {
		return null;
	}
}
