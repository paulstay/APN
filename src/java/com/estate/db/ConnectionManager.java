package com.estate.db;

/**
 * @author Paul Stay 
 * Created on May 18, 2005 Description, Connecion pooling, create
 *         several connections for the dbobjects, this saves lots of time.
 * Modified to use Vector to be thread safe Jan 2008
 * Needs to be thread safe or an exception might cause the zombie removal to stop.
 * 
 * Update May 2009
 * Need to modify this, if the appliation server has a connection pool use it, otherwise
 * use this one.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Properties;

public class ConnectionManager {

	public static final int DEFAULT_MAX_CONNECTIONS = 450;
	public static int counter = 0;

	public static ConnectionManager dbConnectionManager = null;

	public static void closeConnection(NBConnection connection) {
		if (connection != null) {
			dbConnectionManager.checkinConnection(connection);
		}
	}

	public static NBConnection getConnection(String driver, String url,
			String username, String password) throws SQLException {
		if (dbConnectionManager == null) {
			dbConnectionManager = new ConnectionManager(driver, url, username,
					password, DEFAULT_MAX_CONNECTIONS);
		}
		return dbConnectionManager.checkoutConnection();
	}

	public static String getConnectionStatus() {
		return dbConnectionManager.connectionStats();
	}

	public static void getStatus() {
		dbConnectionManager.printStatus();
	}

	private Vector<NBConnection> pool;

	private int numConnections;

	private int maxConnections;

	private String url;

	private String username;

	private String password;

	private final long timeout = 600;

	public ConnectionManager(String driverName, String url, String username,
			String password, int maxConnections) throws SQLException {
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxConnections = maxConnections;

		numConnections = 0;
		// This needs to be a vector since we need it to allow removal during a loop, and
		// so that it won't cause an array or collection exception!
		pool = new Vector<NBConnection>(maxConnections);

		try {
			Class.forName(driverName).newInstance();
		} catch (Exception e) {
			throw new SQLException();
		}

		// start a zombie monitor to clean out dead connections
		ZombieMonitor zm = new ZombieMonitor(this);
		zm.start();
	}

	public synchronized void checkinConnection(NBConnection connection) {
		connection.expireLease();
	}

	public synchronized NBConnection checkoutConnection() throws SQLException {
		try {
			for (NBConnection connection : pool) {
				if (connection.lease()) {
					return connection;
				}
			}
			
			// The following can be commented out for debugging purposes
			//System.out.println("URL " + url);
			//System.out.println("User " + username);
			//System.out.println("Pass " + password);
			//java.io.PrintWriter pStream = new java.io.PrintWriter("e:\\sql.err");
			//DriverManager.setLogWriter(pStream);

			// We use a properties file, so we can pass other arguments besides just the username and password.
			Properties prop = new Properties();
			prop.put("user", username);
			prop.put("password",password);
			prop.put("zeroDateTimeBehavior","convertToNull");
			
			// Connect to the database!
			Connection c = DriverManager.getConnection(url, prop);
			NBConnection newConnection = new NBConnection(c);
			newConnection.setCnt(ConnectionManager.counter++);
			newConnection.lease();
			pool.addElement(newConnection);
			numConnections++;
			return newConnection;
			
		} catch (SQLException e) {
			throw new SQLException();
		} catch (Exception e1){
			System.err.print(e1.getMessage());
		}

		return null;
	}

	private String connectionStats() {
		StringBuffer sb = new StringBuffer("");
		sb.append("Connection Pool Status for " + url + "\n");
		sb.append("connections created: " + numConnections + " / "
				+ maxConnections + "\n");
		sb.append("connections in pool: " + pool.size() + "\n");
		return sb.toString();
	}

	private void printStatus() {
		System.out.println("Connection Pool Status for " + url);
		System.out.println("connections created: " + numConnections + " / "
				+ maxConnections);
		System.out.println("connections in pool: " + pool.size());
		System.out.println();
	}

	public synchronized void removeZombieConnections() {
		java.util.Date now = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("M/d/y h:mm a");

		// Do some logging here.....
		//printStatus();
		System.out.println("Checking Pool Connections " + df.format(now));

		long stale = System.currentTimeMillis() - timeout;
		int inUse = 0;
		int inPool = 0;
		int count = 0;

		try {
			// This is a thread safe operationg since it is a Vector, other Collections
			// Are not necessarily thread safe!
			Enumeration connList = pool.elements();
			while ((pool !=null) && (connList.hasMoreElements())) {
				NBConnection connection = (NBConnection) connList.nextElement();
				count++;
				inPool++;
				if (connection.inUse()){
					inUse++;
				}
				if (connection.inUse() && stale > connection.getLastUse()) {
					pool.removeElement(connection);	// Problem if not a Vector!
					numConnections--;
				} else {
					if ((!connection.inUse())) {
						pool.removeElement(connection);	// Problem if not a Vector.
						numConnections--;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in removeZombieCOnnections");
			e.printStackTrace();
		}
	}
}

/** Thread for monitoring ConnectionManagers for dead connections. */

class ZombieMonitor extends Thread {
	private ConnectionManager connectionManager;

	public ZombieMonitor(ConnectionManager _connectionManager) {
		super();
		connectionManager = _connectionManager;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(60 * 60 * 1000); // Check for stale connection every
											// hour
				connectionManager.removeZombieConnections();
			}
		} catch (Exception e) {
			System.out.println("Zombie broken ");
		}
	}
}
