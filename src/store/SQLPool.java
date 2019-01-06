package store;

import java.sql.*;

import company.Constants;
import utilities.Logger;

public class SQLPool {
	private Connection conn = null;
	
	private static SQLPool instance = null;
	public static SQLPool getInstance() {
		if(instance == null) {
			instance = new SQLPool();
		}
		return instance;
	}
	
	private SQLPool() {

		String host = Constants.Environments.DB_HOST;
		String dbName = Constants.Environments.DB_NAME;
		String user = Constants.Environments.DB_USER;
		String pass = Constants.Environments.DB_PASS;
		
		if(host == null || dbName == null || user == null || pass == null) {
			Logger.fatal("DB_HOST, DB_NAME. DB_USER, DB_PASS ENV IS NOT SETTED!");
			System.exit(1);
		}
		
		String url = "jdbc:mysql://";
		url += host;
		url += "/";
		url += dbName;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);
		}catch(SQLException e) {
			String message = "message: " + e.getMessage() + ", SQLState: " + e.getSQLState();
			switch(e.getSQLState()) {
			case "28000":
				message += "\n Please check DB_USER or DB_PASS";
				break;
			case "42000":
				message += "\n Please create database and glant access to user";
				break;
			default: 
				message += "";
				break;
			}
			Logger.fatal(message);
			System.exit(1);
		}catch(ClassNotFoundException e) {
			String message = "message: " + e.getMessage();
			Logger.fatal(message);
			System.exit(1);
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
}
