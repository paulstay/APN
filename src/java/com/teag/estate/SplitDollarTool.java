package com.teag.estate;

import com.estate.constants.ToolTableTypes;
import java.util.HashMap;

/**
 *
 * @author Paul Stay
 * Date November 2009
 * This tool is very specific for Marc Sheridan for a Split Dollar
 * transfer to an ILIT, and life insurance, the premium payments are
 * transfered to this tool in the form of a gift and a loan using the latest
 * AFR rate.
 */
public class SplitDollarTool extends EstatePlanningTool {

    public static final String ID = "ID";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String OWNER_ID = "OWNER_ID";

    String description;
    long ownerId;
    String uuid;

    String tableName = "SPLIT_DOLLAR_TOOL";

    long id = 0l;
    HashMap<String, Object> record;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void calculate() {
        // Nothing to calculate we are doing everything at the estate planning report for now
    }

    @Override
    public void delete() {
        if(id>0L){
            dbObj.start();
            dbObj.delete("SPLIT_DOLLAR_TOOL", "ID='" + id + "'");
            dbObj.stop();
        }
    }

    @Override
    public long getToolTableId() {
                return ToolTableTypes.SPLIT.id();
    }

    @Override
    public void insert() {
        dbObj.start();
        dbObj.setTable("SPLIT_DOLLAR_TOOL");
        dbObj.clearFields();
        record = null;

        dbAddField(DESCRIPTION, "Split Dollar Tool");
        dbAddField(OWNER_ID, ownerId);

        int error = dbObj.insert();

        if (error == 0) {
            uuid = dbObj.getUUID();
            record = dbObj.execute("select ID from SPLIT_DOLLAR_TOOL where UUID='" + uuid + "'");
            Object o = record.get("ID");
            if (o != null) {
                id = Integer.parseInt(o.toString());
            }
        }
        dbObj.stop();
    }

    @Override
    public void read() {
        if (id > 0L) {
            dbObj.start();
            dbObj.setTable("SPLIT_DOLLAR_TOOL");
            dbObj.clearFields();
            record = null;

            String sql = "select * from SPLIT_DOLLAR_TOOL where ID='" + id + "'";
            record = dbObj.execute(sql);
            dbObj.stop();

            if (record != null) {
                description = getString(record, DESCRIPTION);
                ownerId = getLong(record, OWNER_ID);
            }
        }

    }

    @Override
    public void update() {

        dbObj.start();
        dbObj.setTable("SPLIT_DOLLAR_TOOL");
        dbObj.clearFields();
        record = null;

        dbAddField(DESCRIPTION, description);
        dbAddField(OWNER_ID, ownerId);

        dbObj.setWhere("ID='" + id + "'");

        dbObj.update();

        dbObj.stop();
    }

    @Override
    public void report() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String writeupText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    
}
