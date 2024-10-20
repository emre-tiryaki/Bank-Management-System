package HelpPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {
	
	private String username = "Your-initial-database-username";
	private String password = "Your-initial-database-password";
	private String dbUrl = "Your-initial-database-URL";
	
	Connection connection = null;
	
	public DBConnectionHelper() {
		
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(dbUrl, username, password);
	}
	
}
