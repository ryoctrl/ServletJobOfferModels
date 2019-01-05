package store;

import java.sql.*;

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

		String host = System.getenv("DB_HOST");
		String dbName = System.getenv("DB_NAME");
		String user = System.getenv("DB_USER");
		String pass = System.getenv("DB_PASS");
		
		String url = "jdbc:mysql://";
		url += host;
		url += "/";
		url += dbName;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pass);
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
}
