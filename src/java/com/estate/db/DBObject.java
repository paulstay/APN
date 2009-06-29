package com.estate.db;

import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class DBObject implements Serializable {
	public static final boolean DEBUG = false;
	private static final long serialVersionUID = 1L;
	public static final int ERROR_NONE = 0;

	public static final int ERROR_NO_DRIVER = 1;
	public static final int ERROR_STATEMENT = 2;
	public static final int ERROR_UPDATE_NO_RECORD = 3;
	public static final int TYPE_INT = 1;

	public static final int TYPE_NUMBER = 2;
	public static final int TYPE_DATE = 3;
	public static char[] encvalue = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P' };

	public static boolean idIsLocked = false;

	public static String changeQuotes(Object value) {
		String ret = value.toString().trim();

		if (ret.indexOf("'") != -1) {
			StringBuffer sb = new StringBuffer();
			char ch;

			for (int i = 0; i < ret.length(); i++) {
				ch = ret.charAt(i);
				switch (ch) {
				case '\'':
					sb.append("''");
					break;
				default:
					sb.append(ch);
					break;
				}
			}

			ret = sb.toString();
		}

		return ret;
	}

	public static String checkString(Object obj) {
		if (obj == null)
			return "NULL";
		String str = obj.toString();
		if (str.length() == 0)
			return "NULL";
		return checkValue("'" + str + "'");
	}
	public static String checkValue(Object value) {
		String ret = value.toString().trim();

		if (ret.charAt(0) == '\'') {
			StringBuffer sb = new StringBuffer();
			char ch;

			sb.append('\'');
			for (int i = 1; i < ret.length() - 1; i++) {
				ch = ret.charAt(i);
				switch (ch) {
				case '\'':
					sb.append("''");
					break;
				default:
					sb.append(ch);
					break;
				}
			}
			sb.append('\'');

			ret = sb.toString();
		}

		return ret;
	}
	public static String dbNumber(Object obj) {
		if (obj == null)
			return "NULL";
		String str = obj.toString();
		if (str.length() == 0)
			return "NULL";
		return str;
	}
	public static String dbString(Object obj) {
		if (obj == null)
			return "NULL";
		String str = obj.toString();
		if (str.length() == 0)
			return "NULL";
		return "'" + str + "'";
	}
	public boolean initDb = false;
	protected String driver;

	protected String url;
	protected String name;
															protected String user;
	protected String password;
									protected String error_message = null;
	protected String date = "CURRENT_DATE";
																		protected String dateformatstr = "yyyy-MM-dd HH:mm:ss"; // used by

	// formatDate()
	protected String datebegin = "'"; // used by formatDate() //"to_date('";
	protected String dateend = "'"; // used by formatDate() //"', 'yyyy:mm:dd
	// HH24:mi:ss')
	protected SimpleDateFormat dateformat = new SimpleDateFormat(
			"yyyy:MM:dd HH:mm:ss"); // used by formatDate()
	protected DecimalFormat decimalformat = new DecimalFormat("000"); // used
	// by
																		// getIncID()
	protected DecimalFormat idformat = new DecimalFormat("0"); // used by
																// dbID()

	protected String m_uuid = "";
	protected ResultSet result;
	protected NBConnection conn;

	protected Statement stmt;
	protected ResultSetMetaData data; // keep current record around
	protected boolean useUpdateFields = false;
	protected boolean logChanges = false;
	protected String log_filename;

	protected String user_name = "none";

	protected String table;

	protected String whereclause;

	protected ArrayList<String> fields;

	protected ArrayList<String> values;

	public DBObject() {
		initialize();
		fields = new ArrayList<String>();
		values = new ArrayList<String>();
		table = "";
		whereclause = "";
	}

	/**
	 * Adds the System wide fields and values to the current fields array.
	 * 
	 * @param insert
	 *            true if this is for an insert statement, else false for an
	 *            update statement
	 */
	public void addExtraFields(boolean insert) {
		if (useUpdateFields) {
			if (insert) {
				m_uuid = generateUUID();
				fields.add("UUID");
				values.add("'" + m_uuid + "'");
				fields.add("CREATE_USER_NAME");
				values.add("'" + user_name + "'");
				fields.add("CREATE_DATE");
				values.add(formatDate(new Date()));
			}
			fields.add("UPDATE_USER_NAME");
			values.add("'" + user_name + "'");
			fields.add("UPDATE_DATE");
			values.add(formatDate(new Date()));
		}
	}

	public void addField(String field, String value) {
		fields.add(field);
		values.add(value);
	}

	public void clearFields() {
		fields.clear();
		values.clear();
	}

	public String dbDate(Object obj) {
		if (obj == null)
			return "NULL";
		if (obj instanceof GregorianCalendar)
			return formatDate((GregorianCalendar) obj);
		else if (obj instanceof String)
			return formatDate((String) obj);
		return formatDate((Date) obj);
	}

	public String dbID(Object obj) {
		if (obj instanceof String)
			return obj.toString();
		return idformat.format(obj);
	}

	public String dbList(String field, String table, String where) {
		StringBuffer list = new StringBuffer();
		HashMap<String,Object> res = execute("select " + field + " from " + table + " where "
				+ where);
		while (res != null) {
			if (list.length() > 0)
				list.append(",");
			list.append(res.get(field).toString());
			res = next();
		}
		if (list.length() == 0)
			list.append("null");
		return list.toString();
	}

	public int delete(String table, String where) {
		String sql = "";
		try {
			if ((where == null) || (where.length() == 0))
				sql = "delete from " + table;
			else
				sql = "delete from " + table + " where " + where;
			// stmt.executeUpdate(sql);
			stmt.execute(sql);
			log(sql);
		} catch (Exception e) {
			println("Delete Error:" + sql);
			println("Exception:" + e);
			error_message = e.getMessage();
			return ERROR_STATEMENT;
		}
		return ERROR_NONE;
	}

	public HashMap<String,Object> execute(String sql) {
		HashMap<String,Object> ret = null;

		try {
			result = stmt.executeQuery(sql);
			ret = next();
		} catch (Exception e) {
			println("execute query Error:" + sql);
			println("Exception:" + e);
			if (DEBUG)
				e.printStackTrace();
		}
		return ret;
	}

	public boolean executeStatement(String sql) {
		boolean ret = false;

		try {
			ret = stmt.execute(sql);
			log(sql);
			ret = true;
		} catch (Exception e) {
			println("executeStatement Error:" + sql);
			println("Exception:" + e);
			if (DEBUG)
				e.printStackTrace();
		}
		return ret;
	}

	public boolean executeStatementNoLog(String sql) {
		boolean ret = false;

		try {
			ret = stmt.execute(sql);
			ret = true;
		} catch (Exception e) {
			println("executeStatement Error:" + sql);
			println("Exception:" + e);
			if (DEBUG)
				e.printStackTrace();
		}
		return ret;
	}

	@Override
	protected void finalize() {
		stop();
	}

	public String formatDate(Date date) {
		return datebegin + dateformat.format(date) + dateend;
	}

	public String formatDate(GregorianCalendar date) {
		return datebegin + dateformat.format(date.getTime()) + dateend;
	}

	public String formatDate(String date) {
		if ((date == null) || (date.length() == 0))
			return "NULL";
		return datebegin + dateformat.parse(date, new ParsePosition(0))
				+ dateend;
	}

	/**
	 * Generate a Universal Unique ID(UUID) from the Database.
	 * 
	 * @return a 36 character UUID.
	 */
	public String generateUUID() {
		HashMap<String,Object> record = execute("select UUID()");
		if (record.get("UUID()") != null)
			return record.get("UUID()").toString();
		return "";
	}

	public String getDateID() {
		return date;
	}

	public String getDriver() {
		return driver;
	}

	public String getErrorMessage() {
		return error_message;
	}

	public String getField(int index) {
		if ((index < 0) || (index >= fields.size()))
			return "";
		return fields.get(index);
	}

	public boolean getLogging() {
		return logChanges;
	}

	public String getName() {
		return name;
	}

	public String getURL() {
		return url;
	}

	/**
	 * Returns the current UUID from the last statement. This is changed when an
	 * insert or query is executed. It only applies to the current record.
	 * 
	 * @return a 36 character UUID.
	 */
	public String getUUID() {
		return m_uuid;
	}

	public String getValue(int index) {
		if ((index < 0) || (index >= fields.size()))
			return "";
		return values.get(index);
	}

	public String getValue(String field) {
		for (int i = 0; i < fields.size(); i++)
			if (field.equals(fields.get(i)))
				return values.get(i);
		return null;
	}

	public void initialize() {
		if (!initDb) {
			setDriver(DBConstants.DRIVER);
			setURL(DBConstants.URL);
			setName(DBConstants.DATABASE);
			setUser(DBConstants.USER);
			setPassword(DBConstants.PASSWORD);
			setDateID(DBConstants.SYS_DATE);
			setDateFormat(DBConstants.DATE_FORMAT);
			setDateBegin(DBConstants.DATE_BEGIN);
			setDateEnd(DBConstants.DATE_END);
			setLogging(DBConstants.LOGGING);
			if (DBConstants.LOGGING) {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String filename = DBConstants.LOGGING_PREFIX
						+ df.format(new Date()) + ".sql";
				setLoggingFilename(filename);
			}
			setUseUpdateFields(DBConstants.USEUPDATE);
			initDb = true;
		}
	}

	public int insert() {
		String sql = "";
		StringBuffer sbf = new StringBuffer();
		StringBuffer sbv = new StringBuffer();
		addExtraFields(true);
		for (int i = 0; i < fields.size(); i++) {
			sbf.append((i == 0 ? "" : ",") + fields.get(i));
			sbv.append((i == 0 ? "" : ",") + checkValue(values.get(i)));
		}
		try {
			sql = "insert into " + table + " (" + sbf + ") values (" + sbv
					+ ")";
			stmt.executeUpdate(sql);
			log(sql);
			return ERROR_NONE;
		} catch (Exception e) {
			println("Statement Error:" + sql);
			println("Exception" + e);
			error_message = e.getMessage();
			if (DEBUG)
				e.printStackTrace();
			return ERROR_STATEMENT;
		}
	}

	public boolean isAvailable() {
		boolean ret = false;
		try {
			conn = ConnectionManager.getConnection(driver, url + name, user,
					password);
			conn.close();
			ret = true;
		} catch (Exception e) {
		}
		return ret;
	}

	public boolean isConnected() {
		return (conn != null);
	}

	public boolean isFieldValid(String field, boolean required, int type) {
		String value = getValue(field);

		if ((value == null) && required)
			return false;
		if (value != null) {
			if ((value.length() == 0) && required) // if empty it is not valid
				return false;
			try {
				switch (type) {
				case TYPE_INT:
					Integer.parseInt(value);
					break;
				case TYPE_NUMBER:
					Double.parseDouble(value);
					break;
				case TYPE_DATE:
					dateformat.parse(value);
					break;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public void log(String statement) {
		if(this.DEBUG)
			System.out.println(statement);
	}

	public HashMap<String,Object> next() {
		HashMap<String,Object> ret = null;

		try {
			data = null;
			if (result.next()) {
				data = result.getMetaData();

				ret = new HashMap<String,Object>();
				for (int i = 0; i < data.getColumnCount(); i++){
					ret.put(data.getColumnName(i + 1), result.getObject(data
							.getColumnName(i + 1)));
				}
			}
			m_uuid = "";
			if ((ret != null) && (ret.get("UUID") != null))
				m_uuid = ret.get("UUID").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	protected void println(String str) {
			System.out.println(str);
	}

	public void setDateBegin(String datebegin) {
		this.datebegin = datebegin;
	}

	public void setDateEnd(String dateend) {
		this.dateend = dateend;
	}

	public void setDateFormat(String dateformat) {
		this.dateformatstr = dateformat;
		this.dateformat = new SimpleDateFormat(dateformat);
	}

	public void setDateID(String date) {
		this.date = date;
	}

	public int setDriver(String driver) {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			println("Could not find JDBC Driver " + driver);
			return ERROR_NO_DRIVER;
		}
		this.driver = driver;
		return ERROR_NONE;
	}

	public void setLogging(boolean logging) {
		this.logChanges = logging;
	}

	public void setLoggingFilename(String filename) {
		log_filename = filename;
		if (log_filename != null) {
			File file = new File(log_filename);
			if (DEBUG)
				System.out.println("Database Logging to:"
						+ file.getAbsolutePath());
		}
	}

	public int setName(String name) {
		this.name = name;
		return ERROR_NONE;
	}

	public int setPassword(String password) {
		this.password = password;
		return ERROR_NONE;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public int setURL(String url) {
		this.url = url;
		return ERROR_NONE;
	}

	public int setUser(String user) {
		this.user = user;
		return ERROR_NONE;
	}

	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	public void setUseUpdateFields(boolean useUpdateFields) {
		this.useUpdateFields = useUpdateFields;
	}

	public void setWhere(String where) {
		this.whereclause = where;
	}

	public int start() {
		clearFields();
		if (isConnected()) {
			System.out
					.println("DBObject Something is wrong closing open connection");
			ConnectionManager.closeConnection(conn);
			conn = null;
		}
		try {
			conn = ConnectionManager.getConnection(driver, url + name, user,
					password);
			clearFields();
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println("error:" + e);
			return 1;
		}
		return 0;
	}

	public int stop() {
		try {
			stmt.close();
			// conn.close();
			ConnectionManager.closeConnection(conn);
			conn = null;
			stmt = null;
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	public int update() {
		String sql = "";
		StringBuffer sb = new StringBuffer();
		try {
			addExtraFields(false);
			for (int i = 0; i < fields.size(); i++)
				sb.append((i == 0 ? "" : ",") + fields.get(i) + "="
						+ checkValue(values.get(i)));
			sql = "update " + table + " set " + sb + " where " + whereclause;
			ResultSet res = stmt.executeQuery("SELECT * FROM " + table
					+ " WHERE " + whereclause);
			if (res.next()) // if there is a record
			{
				stmt.executeUpdate(sql);
				log(sql);
				return ERROR_NONE;
			}
		} catch (Exception e) {
			println("Update Error:" + sql);
			println("Exception:" + e);
			error_message = e.getMessage();
			if (DEBUG)
				e.printStackTrace();
			return ERROR_STATEMENT;
		}
		return ERROR_UPDATE_NO_RECORD;
	}

	public int update(String sql) {
		try {
			stmt.executeUpdate(sql);
			log(sql);
			return ERROR_NONE;
		} catch (Exception e) {
			println("Update Error: " + sql);
			println("Exception:" + e);
		}
		return ERROR_STATEMENT;
	}
}
