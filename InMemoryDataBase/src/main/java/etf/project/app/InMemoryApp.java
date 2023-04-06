package etf.project.app;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import org.h2.tools.Server;

import etf.project.mylogger.MyLogger;

public class InMemoryApp {
	
	public static void main(String[] args) {

		try {
			
			Properties property = new Properties();
			property.load(new FileInputStream("resources/config.properties"));
			
			String JDBC_DRIVER = property.getProperty("JDBC_DRIVER");
			String USER = property.getProperty("USER");
			String PASSWORD = property.getProperty("PASSWORD");
			String DATABASE_URL = property.getProperty("DATABASE_URL");
			
			Class.forName(JDBC_DRIVER);
			
			Server inMemoryServer = Server.createTcpServer("-tcpPort", "3421", "-tcpAllowOthers");
			inMemoryServer.start();
			
			System.out.println("Connecting to InMemoryDataBaseServer...");
			Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			System.out.println("Connected to InMemoryDataBaseServer!");
			
			String CREATE_TABLE_COMMAND = "CREATE TABLE APP_USERS "
					+ "(USERNAME VARCHAR(100) PRIMARY KEY, "
					+ "PASSWORD VARCHAR(100), "
					+ "NAME VARCHAR(100), "
					+ "LASTNAME VARCHAR(100));";
						
			Statement statement = connection.createStatement();
			statement.executeUpdate(CREATE_TABLE_COMMAND);

			System.out.println("The table APP_USERS in given InMemoryDataBase was created.");
			
			statement.close();
			connection.close();
			inMemoryServer.stop();
			
		}catch(SQLException e) {
			MyLogger.setLogger(InMemoryApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}catch(Exception e) {
			MyLogger.setLogger(InMemoryApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
		System.out.println("OVER: Goodbye.");
	}
}
