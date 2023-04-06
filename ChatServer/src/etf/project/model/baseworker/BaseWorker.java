package etf.project.model.baseworker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import etf.project.model.message.Message;
import etf.project.model.messageuser.MessageUser;
import etf.project.mylogger.MyLogger;

public class BaseWorker {

	private static Connection connection;
	private static Statement statement;
	
	private static final int MESSAGE_STATUS = 0;
	
	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	private static final String DATABASE_URL = "jdbc:h2:file:C:/Users/PC/Desktop/Fakultet/MDP/Project1/ChatServer/Base/Chat";
		
	public BaseWorker() {
		super();
	}
	
	public boolean sendMessage(Message message) {
		
		try {
			
			if(message == null)
				return false;
			
			String typeOfMessage = message.getTypeOfMessage();
			//init();
			
			Class.forName(JDBC_DRIVER);
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			switch(typeOfMessage) {
			
			case "UNICAST":
				int idUnicastSender = this.getMessageUserId(message.getSender());
				int idReceiver = this.getMessageUserId(message.getReceiver());
				
				if(idUnicastSender == -1 || idReceiver == -1)
					return false;
			
				int lastMessageId = getLastMessageId();
				if(lastMessageId == -1)
					return false;
				
				String INSERT_UNICAST_MESSAGE_ORDER = "INSERT INTO MESSAGES VALUES(" + (lastMessageId + 1) 
						+ ", " + idUnicastSender + ", " + idReceiver + ", \'" + message.getData() + "\', " 
						+ MESSAGE_STATUS + ", \'" + message.getDateOfCreating() + "\');";
				
				return statement.executeUpdate(INSERT_UNICAST_MESSAGE_ORDER) == 1 ? true : false;
				
			case "MULTICAST":
				int idMulticastSender = this.getMessageUserId(message.getSender());
				
				if(idMulticastSender == -1)
					return false;
				
				ArrayList<Integer> multicastIds = this.getIdsForMulticast(message.getReceiver().getTerminalName());
				if(multicastIds == null)
					return false;
				
				if(multicastIds.size() == 1)
					return true;
				
				String INSERT_MULTICAST_MESSAGE_ORDER = "";
				int multicastCounter = 0;
				
				for(int i = 0; i < multicastIds.size(); i++) {
					
					int lastMessageIdMul = getLastMessageId();
					if(lastMessageIdMul == -1)
						return false;
					
					INSERT_MULTICAST_MESSAGE_ORDER = "INSERT INTO MESSAGES VALUES(" + (lastMessageIdMul + 1) 
							+ ", " + idMulticastSender + ", " + multicastIds.get(i) + ", \'" + message.getData() 
							+ "\', " + MESSAGE_STATUS + ", \'" + message.getDateOfCreating() + "\');";
				
					multicastCounter+=statement.executeUpdate(INSERT_MULTICAST_MESSAGE_ORDER);
				}
				
				return multicastIds.size() == multicastCounter ? true : false;
				
			case "BROADCAST":
				
				int idBroadcastSender = this.getMessageUserId(message.getSender());
				if(idBroadcastSender == -1)
					return false;
				
				ArrayList<Integer> broadcastIds = this.getIdsForBroadcast();
				if(broadcastIds == null || broadcastIds.size() == 1)
					return false;
				
				String INSERT_BROADCAST_MESSAGE_ORDER = "";
				int broadcastCounter = 0;
				
				for(int i = 0; i < broadcastIds.size(); i++) {
					
					int lastMessageIdBr = getLastMessageId();
					if(lastMessageIdBr == -1)
						return false;
					
					INSERT_BROADCAST_MESSAGE_ORDER = "INSERT INTO MESSAGES VALUES(" + (lastMessageIdBr + 1) 
							+ ", " + idBroadcastSender + ", " + broadcastIds.get(i) + ", \'" + message.getData() 
							+ "\', " + MESSAGE_STATUS + ", \'" + message.getDateOfCreating() + "\');";
					
					broadcastCounter += statement.executeUpdate(INSERT_BROADCAST_MESSAGE_ORDER);
				}
				
				return broadcastIds.size() == broadcastCounter ? true : false;
				
			default:
			
			}
			
			return false;
			
		} catch(SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}

	public ArrayList<Message> getMessages(MessageUser user){
		
		try {
			Class.forName(JDBC_DRIVER);

			int idReceiver = this.getMessageUserId(user);
			if(idReceiver < 0)
				return null;
			
			String GET_MESSAGES_ORDER = "SELECT * FROM MESSAGES WHERE IDRECEIVER = " + idReceiver + ";";

			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(GET_MESSAGES_ORDER);
			
			ArrayList<Message> messages = new ArrayList<>();
			
			while(rs.next()) {
				
				Message message = new Message();
				message.setData(rs.getString("DATA"));
				message.setDateOfCreating(rs.getString("DATE"));
				
				MessageUser sender = this.getMessageUser(rs.getInt("IDSENDER"));
				MessageUser receiver = this.getMessageUser(rs.getInt("IDRECEIVER"));
				
				message.setSender(sender);
				message.setReceiver(receiver);
				
				messages.add(message);
			}
			
			return messages;
			
		} catch(SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} finally {
			freeUpResources();
		}
	}
	
	public boolean addMessageUser(MessageUser receiver) {
		
		try {
			Class.forName(JDBC_DRIVER);
			
			String CHECK_RECEIVER_ORDER = "SELECT IDUSER FROM MESSAGE_USERS WHERE TERMINALNAME=\'" + receiver.getTerminalName() 
						+ "\' AND IDGATEWAY = " + receiver.getIdGateway() 
						+ " AND TYPEOFGATEWAY = \'" + receiver.getTypeOfGateway() 
						+ "\' AND OCCUPATION = \'" + receiver.getOccupation() + "\';";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet res = statement.executeQuery(CHECK_RECEIVER_ORDER);
			if(res.next())
				return true;
			
			int lastId = getLastMessageUserId();
			if(lastId == -1)
				return false;
			
			String INSERT_ORDER = "INSERT INTO MESSAGE_USERS VALUES (" + (lastId + 1) 
					+ ",\'" + receiver.getTerminalName() + "\', " 	+ receiver.getIdGateway() 
					+ ",\'" + receiver.getTypeOfGateway() + "\', \'" + receiver.getOccupation() + "\');";
			
			return statement.executeUpdate(INSERT_ORDER) == 1 ? true : false;
			
		} catch(SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	private MessageUser getMessageUser(int id) {
		
		try {
			Class.forName(JDBC_DRIVER);
			
			String GET_USER_ORDER = "SELECT * FROM MESSAGE_USERS WHERE IDUSER = " + id + ";";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(GET_USER_ORDER);
			
			if(rs.next()) {
				
				MessageUser user = new MessageUser();
				user.setidMessageUser(rs.getInt("IDUSER"));
				user.setTerminalName(rs.getString("TERMINALNAME"));
				user.setIdGateway(rs.getInt("IDGATEWAY"));
				user.setTypeOfGateway(rs.getString("TYPEOFGATEWAY"));
				user.setOccupation(rs.getString("OCCUPATION"));
				
				return user;
			}
			
			return null;
			
		} catch(SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		}
	}
	
	private ArrayList<Integer> getIdsForBroadcast(){
		
		try {
			Class.forName(JDBC_DRIVER);
			String GET_IDS_ORDER = "SELECT IDUSER FROM MESSAGE_USERS;";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet res = statement.executeQuery(GET_IDS_ORDER);
			
			ArrayList<Integer> ids = new ArrayList<>();
			while(res.next())
				ids.add(res.getInt(("IDUSER")));
			
			return ids;
			
		} catch(SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		}
	}
	
	private ArrayList<Integer> getIdsForMulticast(String terminalName) {
		
		try {
			Class.forName(JDBC_DRIVER);
			String GET_IDS_ORDER = "SELECT IDUSER FROM MESSAGE_USERS WHERE TERMINALNAME = \'" + terminalName + "\';";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet res = statement.executeQuery(GET_IDS_ORDER);
			
			ArrayList<Integer> ids = new ArrayList<>();
			while(res.next())
				ids.add(res.getInt(("IDUSER")));
			
			return ids;
			
		} catch(SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		}
	}
	
	private int getMessageUserId(MessageUser user) {
		
		try {
			Class.forName(JDBC_DRIVER);
			
			String GET_ID_ORDER = "SELECT IDUSER FROM MESSAGE_USERS WHERE TERMINALNAME=\'" + user.getTerminalName() 
			+ "\' AND IDGATEWAY = " + user.getIdGateway() 
			+ "  AND TYPEOFGATEWAY = \'" + user.getTypeOfGateway() 
			+ "\' AND OCCUPATION = \'" + user.getOccupation() + "\';";
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet res = statement.executeQuery(GET_ID_ORDER);
			if(res.next())
				return res.getInt("IDUSER");
			
			return -1;
			
		} catch(SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return -1;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return -1;
		} finally {
			freeUpResources();
		}
	}
	
	private int getLastMessageUserId() {
		
		try {
			Class.forName(JDBC_DRIVER);
			
			String GET_LAST_ID_ORDER = "SELECT IDUSER FROM MESSAGE_USERS;";
			int lastId = -1;
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet res = statement.executeQuery(GET_LAST_ID_ORDER);
			while(res.next())
				lastId = res.getInt("IDUSER");
			
			return lastId;
			
		} catch (SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return -1;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return -1;
		}
	}
	
	private int getLastMessageId() {
		
		try {
			Class.forName(JDBC_DRIVER);
			
			String GET_LAST_ID_ORDER = "SELECT IDMESSAGE FROM MESSAGES;";
			int lastId = -1;
			
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			ResultSet res = statement.executeQuery(GET_LAST_ID_ORDER);
			while(res.next())
				lastId = res.getInt("IDMESSAGE");
			
			return lastId;
		} catch (SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return -1;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return -1;
		}
	}
	
	private static void freeUpResources() {
		
		try {

			if(connection != null)
				connection.close();
			
			if(statement != null)
				statement.close();

		} catch (SQLException e) {
			MyLogger.setLogger(BaseWorker.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
}

}
