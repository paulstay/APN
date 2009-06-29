package com.teag.analysis;

import java.io.Serializable;

import com.estate.db.DBObject;

/**
 * @author Paul Stay Description Parent class for estate analysis for base line
 *         Keeps DbObject routines for access database
 */
public class AnalysisSqlBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -961763176612742273L;

	DBObject dbobj = new DBObject();

	public AnalysisSqlBean() {

	}
}
