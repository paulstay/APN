package com.estate.db;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ApplicationConnection {

	DataSource pool;
	
	public ApplicationConnection (){
		init();
	}
	
	public void init() {
		InitialContext ctx = null;
		Connection conn;
		Statement stmt;
		
		try {
			ctx = (InitialContext) new InitialContext().lookup("java:comp/env");
			pool = (DataSource) ctx.lookup("jdbc/apnMysql");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		try {
			conn = pool.getConnection();
			stmt = conn.createStatement();
			stmt.execute("select * from PERSON");
			ResultSet rs = stmt.getResultSet();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
			
		} catch (SQLException sqe) {
			System.out.println("Error in creating pool " + sqe.getMessage());
		}
	}

	public Connection getConnection(){
		Connection conn = null;
		try {
			conn = pool.getConnection();
		} catch(SQLException e){
			System.out.println("Error in connection");
		}
		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch(SQLException e){
			System.out.println("Error in close connection");
		}
	}
}
