package com.teag.db;

/**
 * @author Paul Stay
 * Description, given a table, field, and where statement, return the summation of the fields
 * only works for numerical fields.
 *
 */

import java.util.HashMap;

import com.estate.db.DBObject;

public class TableSumation {
	DBObject db = null;
	double total = 0.0;
	
	public TableSumation() {
		total = 0.0;
	}

	public void clearTotal() {
		total = 0.0;
	}
	
	public double getSum(String table, String field, String whereClause) {
		DBObject db = new DBObject();
		db.start();
		db.setTable(table);
		String sqlStmt = "select round(sum(" + field + "),3) as TOTAL from " + table + " where "
			+ whereClause;
		HashMap<String,Object> ret = db.execute( sqlStmt);
		db.stop();
		
		if( ret != null ) {
			Object t = ret.get("TOTAL");
			double amt = 0.0;
			if( t != null) {
				amt =  ((Number)(ret.get("TOTAL"))).doubleValue();
				total += amt;
			}
			return amt;
		}
		return 0.0;
	}
	
	public double getTotal() {
		return total;
	}
}
