package controller;

import java.sql.*;

import controller.exception.ConnectionException;
import controller.exception.IllegalArgumentException;
import controller.exception.InternalErrorException;
import controller.exception.NoSuchEntryException;

public class DB {

	// JDBC driver name and database URL
	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	private Connection         conn = null;
	private PreparedStatement  stmt = null;
	
	public DB(){}
	
	public void connect(String url, String user, String pass) throws ConnectionException {
		if(url  == null || url.equals("") ) throw new IllegalArgumentException("Illegal url: "  + url);
		if(user == null || user.equals("")) throw new IllegalArgumentException("Illegal user: " + user);
		if(pass == null || pass.equals("")) throw new IllegalArgumentException("Illegal pass: " + pass);
		
		try {
			// Register JDBC driver
			Class.forName(DB.JDBC_DRIVER);
			// Open a connection
			this.conn = DriverManager.getConnection(url, user, pass);

		} 
		catch (ClassNotFoundException | SQLException e) 
		{ throw new ConnectionException(); }
	}
	
	public void close(){
		try {
			this.conn.close();
		} catch (Exception se)
		{ throw new InternalErrorException("Connection already closed!"); }// end finally try
	}
	
	public void put(String key, String value){
		if(key   == null || key.equals("")  ) throw new IllegalArgumentException("Illegal Key: " + key);
		if(value == null || value.equals("")) throw new IllegalArgumentException("Illegal Key: " + value);
		
		try {
			this.stmt = this.conn.prepareStatement("INSERT INTO api_results (k, v) VALUES (?, ?)");
			this.stmt.setString(1, key);
			this.stmt.setString(2, value);
			this.stmt.executeUpdate();
			
			// Clean-up environment
			this.stmt.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			throw new InternalErrorException("Insert operation failed!");
		} finally {
			// finally block used to close resources
			try {
				if (this.stmt != null){
					this.stmt.close();
					
				}
			} catch (SQLException se2) 
			{throw new InternalErrorException("Statement cannot be closed!");}// nothing we can do
			
		}// end try
		
	}

	public String get(String key) {
		if(key   == null || key.equals("")  ) throw new IllegalArgumentException("Illegal Key: " + key);
		
		try {

			// Execute a query
			this.stmt = this.conn.prepareStatement("SELECT v FROM api_results WHERE k=?");
			this.stmt.setString(1, key);
			
			ResultSet rs  = this.stmt.executeQuery();
			String result = null;
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				result = rs.getString("v");
				
			}
			// Clean-up environment
			rs.close();
			this.stmt.close();
			
			if(result == null || result.equals(""))
			{ throw new NoSuchEntryException(key); }
			
			return result;
		} catch (SQLException se) {
			throw new InternalErrorException("Query operation failed!");
			
		} finally {
			// finally block used to close resources
			try {
				if (this.stmt != null){
					this.stmt.close();
				}
			} catch (SQLException se2) 
			{ throw new InternalErrorException("Statement cannot be closed!"); }// nothing we can do
			
		}// end try
		
	}

}
