package HelpPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {
	
	private String username = "Your-Database-Username";
	private String password = "Your-Database-pass";
	private String dbUrl = "Your-Database-URL";
	
	Connection connection = null;
	
	public DBConnectionHelper() {
		
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(dbUrl, username, password);
	}
	
}
