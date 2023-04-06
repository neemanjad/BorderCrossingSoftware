package etf.project.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import etf.project.model.user.User;
import etf.project.mylogger.MyLogger;

public class InMemoryDataBaseUtility {
	
	private static Connection connection;
	private static Statement statement;
	
	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	private static final String DATABASE_URL = "jdbc:h2:file:C:/Users/PC/Desktop/Fakultet/MDP/Project1/InMemoryDataBase/Base/APP_USERS";

	public InMemoryDataBaseUtility() {
		super();
	}
	
	public boolean addUser(User user) {
				
		try {
			//init();
			Class.forName(JDBC_DRIVER);
			String INSERT_QUERY = "INSERT INTO APP_USERS VALUES (\'" + user.getUserName() + "\', \'" 
					+ user.getPassword() + "\', \'" + user.getName() + "\', \'" + user.getLastName() + "\');";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			int baseAnswer = statement.executeUpdate(INSERT_QUERY);
			
			if(baseAnswer == 1)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	public boolean removeUser(String userName) {
		
		try {
			Class.forName(JDBC_DRIVER);
			String REMOVE_QUERY = "DELETE FROM APP_USERS WHERE USERNAME = \'" + userName + "\';";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			int baseAnswer = statement.executeUpdate(REMOVE_QUERY);
			
			if(baseAnswer == 1)
				return true;
			else
				return false;
			
		} catch(SQLException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	public boolean updateUser(String userName, User user) {
		
		if("".equals(userName))
			return false;
		
		if(user == null)
			return false;
		
		boolean removeAnswer = removeUser(userName);
		if(!removeAnswer)
			return false;
		
		boolean addAnswer = addUser(user);
		if(addAnswer)
			return true;
		return false;
	}
	
	public boolean changePassword(String userName, String oldPassword, String newPassword) {
		
		if("".equals(userName) || "".equals(newPassword) || "".equals(oldPassword))
			return false;
		
		try {
			Class.forName(JDBC_DRIVER);
			String GET_PASSWORD_ORDER = "SELECT PASSWORD FROM APP_USERS WHERE USERNAME = \'" + userName + "\';";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(GET_PASSWORD_ORDER);
			
			if(!resultSet.next())
				return false;
			
			String oldHashPassword = resultSet.getString("PASSWORD");
			if(!oldHashPassword.equals(oldPassword))
				return false;
			
			String UPDATE_PASSWORD_ORDER = "UPDATE APP_USERS SET PASSWORD = \'" 
					+ newPassword + "\' WHERE USERNAME = \'" + userName + "\';";
			int result = statement.executeUpdate(UPDATE_PASSWORD_ORDER);
			
			if(result == 1)
				return true;
			else
				return false;
			
		} catch(SQLException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	public ArrayList<User> getUsers(){
		
		try {
			
			ArrayList<User> users = new ArrayList<>();
			Class.forName(JDBC_DRIVER);
			String GET_USERS_ORDER = "SELECT * FROM APP_USERS;";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet res = statement.executeQuery(GET_USERS_ORDER);
				
			while(res.next()) {
				User tempUser = new User(res.getString("USERNAME"), res.getString("PASSWORD"), 
						res.getString("NAME"), res.getString("LASTNAME"));
				users.add(tempUser);	
			}
				
			return users;
			
		} catch(SQLException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} finally {
			freeUpResources();
		}
	}
	
	public User getUser(String userName) {
		
		try {
			Class.forName(JDBC_DRIVER);
			String GET_USER_ORDER = "SELECT * FROM APP_USERS WHERE USERNAME = \'" + userName +"\';";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(GET_USER_ORDER);
	
			if(result.next())
				return new User(result.getString("USERNAME"), result.getString("PASSWORD"), 
						result.getString("NAME"), result.getString("LASTNAME"));
			return null;
			
		} catch(SQLException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} finally {
			freeUpResources();
		}
	}
	
	public boolean validateLogin(String userName, String password) {
		
		try {
			Class.forName(JDBC_DRIVER);
			String GET_HASH_PASS = "SELECT PASSWORD FROM APP_USERS WHERE USERNAME = \'" + userName + "\';";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(GET_HASH_PASS);
			
			if(!result.next())
				return false;
			
			
			String passwordFromBase = result.getString(1);
			
			if(password.equals("\""+passwordFromBase+"\""))
				return true;
			return false;
			
		} catch(SQLException e){
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	private static void freeUpResources() {
		
			try {

				if(connection != null)
					connection.close();
				
				if(statement != null)
					statement.close();

			} catch (SQLException e) {
				MyLogger.setLogger(InMemoryDataBaseUtility.class.getName());
				MyLogger.logError(Level.WARNING, e.toString(), e);
			}
	}
	
	/*private static void init() {
		try {
			
			Properties property = new Properties();
			property.load(new FileInputStream("resources/config.properties"));
			
			JDBC_DRIVER = property.getProperty("JDBC_DRIVER");
			USER = property.getProperty("USER");
			PASSWORD = property.getProperty("PASSWORD");
			DATABASE_URL = property.getProperty("DATABASE_URL");
			
			Class.forName(JDBC_DRIVER);
						
		}catch(FileNotFoundException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
		}catch(Exception e) {
			LOGGER.log(Level.FINE, e.toString(), e);
		}
	}*/
}
