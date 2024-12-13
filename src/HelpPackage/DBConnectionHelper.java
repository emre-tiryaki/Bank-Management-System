package HelpPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {
	
	private String username = "root";
	private String password = "atlantis55";
	private String dbUrl = "jdbc:mysql://localhost:3306/bank";
	
	Connection connection = null;
	
	public DBConnectionHelper() {
		
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(dbUrl, username, password);
	}
	
}
