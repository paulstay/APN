package com.estate.db;

/**
 * DBConstants replaces the xml database file, this should speed things up,
 * It should allow a dbobject to be initialized without reading the xml file
 * and without copying!
 * 
 * @author Paul Stay
 * Date September 14, 2006
 * Copyright @ 2006 The Estate Advisory Group.
 *
 */
public class DBConstants {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost/";
	public static final String DATABASE = "apn";
	public static final String USER = "admin";
	//public static final String PASSWORD = ".dordt1&zeroDateTimeBehavior=convertToNull";
	public static final String PASSWORD = ".dordt1";
	public static final String SYS_DATE = "CURRENT_TIMESTAMP";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_BEGIN = "'";
	public static final String DATE_END = "'";
	public static final boolean USEUPDATE = true;
	public static final boolean LOGGING = true;
	public static final String LOGGING_PREFIX = "C:/hosting/logs/log-apn-";
	public static final double VERSION = 2.0;
}
