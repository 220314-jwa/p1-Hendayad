package net.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private static ConnectionFactory connectionFactory = null;
	private static Properties properties;

	/*private ConnectionFactory() {
		InputStream stream = ConnectionFactory.class.getClassLoader().getResourceAsStream("database.properties");
		try {
			properties = new Properties();
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
}
*/	
	public static ConnectionFactory getConnectionFactory() {
		if (connectionFactory==null) connectionFactory = new ConnectionFactory();
		return connectionFactory;
	}
	
    // return our connection to the database:
    public Connection getConnection() {
    	Connection connection = null;
    	String url = "jdbc:postgresql://localhost:5432/postgres";
    		
        String username = "postgres";
        String password = "Ahmed_2020";

        // try connecting to the database:
        try {
            // get connection
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            // if something goes wrong, view the stack trace
            e.printStackTrace();
        }
        return connection;
    }
}
