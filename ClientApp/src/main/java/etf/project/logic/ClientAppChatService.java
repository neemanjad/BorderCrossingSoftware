package etf.project.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import etf.project.model.container.CustomsContainer;
import etf.project.model.message.Message;
import etf.project.model.messageuser.MessageUser;
import etf.project.mylogger.MyLogger;

public class ClientAppChatService {
	
	@SuppressWarnings("unused")
	private static String HOST, KEY_STORE_PATH, KEY_STORE_PASSWORD;
	private static int PORT;
	
	private static final String RESOURCE_PATH = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/ClientApp/resources/config.properties";
	private static final String TRUST_STORE_PATH = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/ClientApp/resources/store.jks";
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static SSLSocket clientSocket;
	
	private static BufferedReader inputStream;
	private static PrintWriter outputStream;
	
	private static OutputStream javaStream;
	private static ObjectOutputStream objectStream;
	
	private static InputStream javaInStream;
	private static ObjectInputStream objectInStream;
	
	private static final int NOT_IMPORTANT_VALUE = -1;
	private static final String MESSAGE_HELPER = "ALL";
	
	public ClientAppChatService() {
		super();
	}
	
	public boolean signUpForChat(CustomsContainer container) {
		
		try {
			if(init()) {
				
				outputStream.println("SIGN UP");
				String serverResponse = inputStream.readLine();
		
				if(serverResponse == null || serverResponse.length() == 0 || !serverResponse.startsWith("WELCOME")) {
					System.out.println("Server unavailable at the stage 1.");
					return false;
				}
				
				MessageUser user = new MessageUser();
				user.setIdGateway(container.getId_gateway());
				user.setOccupation(container.getOccupation());
				user.setTerminalName(container.getTerminalName());
				user.setTypeOfGateway(container.getType());
				user.setidMessageUser(NOT_IMPORTANT_VALUE);
			
				objectStream.writeObject(user);
				serverResponse = inputStream.readLine();
				
				if("OK".equals(serverResponse))
					return true;
				return false;
			}
			return false;
			
		} catch(IOException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	public boolean sendBroadcastMessage(CustomsContainer container) {
		
		try {
			if(init()) {
				
				outputStream.println("BROADCAST");
				String serverResponse = inputStream.readLine();
				
				if(serverResponse == null || "ERROR".equals(serverResponse)) {
					System.out.println("Server is unavailable. Try again later to send message.");
					return false;
				}
				
				System.out.println("Successfully connected to ChatServer.");
				System.out.println("Server response: " + serverResponse);
				
				MessageUser sender = new MessageUser();
				sender.setIdGateway(container.getId_gateway());
				sender.setOccupation(container.getOccupation());
				sender.setTerminalName(container.getTerminalName());
				sender.setTypeOfGateway(container.getType());
				sender.setidMessageUser(NOT_IMPORTANT_VALUE);
				
				MessageUser receiver = new MessageUser();
				
				System.out.println("Enter the message context: ");
				String data = scanner.nextLine();
				
				Message message = new Message();
				message.setSender(sender);
				message.setReceiver(receiver);
				message.setData(data);
				message.setDateOfCreating(new Date().toString());
				message.setTypeOfMessage("BROADCAST");
				
				objectStream.writeObject(message);
				objectStream.flush();
				
				return "OK".equals(inputStream.readLine()) ? true : false;					
				
			} else {
				System.out.println("Initializing failed. ERROR at the start.");
				return false; 
			}
				
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	public boolean sendUnicastMessage(CustomsContainer container) {
		
		try {
			if(init()) {
				
				outputStream.println("UNICAST");
				String serverResponse = inputStream.readLine();
				
				if(serverResponse == null || "ERROR".equals(serverResponse)) {
					System.out.println("Server is unavailable. Try again later to send message.");
					return false;
				}
				
				System.out.println("Successfully connected to ChatServer.");
				System.out.println("Server response: " + serverResponse);
				
				MessageUser sender = new MessageUser();
				sender.setIdGateway(container.getId_gateway());
				sender.setOccupation(container.getOccupation());
				sender.setTerminalName(container.getTerminalName());
				sender.setTypeOfGateway(container.getType());
				sender.setidMessageUser(NOT_IMPORTANT_VALUE);
									
				System.out.println("Enter data about receiver: ");
				System.out.println("Terminal name: ");
				String terminalName = scanner.nextLine();
					
				System.out.println("For Entrance enter ENTRANCE, for Exit enter EXIT.");
				String typeOfGateway = scanner.nextLine();
					
				System.out.println("Enter entrance/exit id: ");
				int idGateway = Integer.parseInt(scanner.nextLine());
					
				System.out.println("For Police enter POLICE, for Custom enter CUSTOMS.");
				String occupation = scanner.nextLine();
					
				if(terminalName.length() < 2 || !("ENTRANCE".equals(typeOfGateway) || "CUSTOMS".equals(typeOfGateway)) 
						|| idGateway < 0 || !("POLICE".equals(occupation) || "CUSTOMS".equals(occupation))) {
					
					System.out.println("You entered illegal data. Try again later.");
					return false;
				}
				
				MessageUser receiver = new MessageUser();
				receiver.setIdGateway(idGateway);
				receiver.setOccupation(occupation);
				receiver.setTerminalName(terminalName);
				receiver.setTypeOfGateway(typeOfGateway);
				receiver.setidMessageUser(NOT_IMPORTANT_VALUE);
					
				Message message = new Message();
				message.setReceiver(receiver);
				message.setSender(sender);
				message.setDateOfCreating(new Date().toString());
				message.setTypeOfMessage("UNICAST");
					
				System.out.println("Enter the content of your message: ");
				String data = scanner.nextLine();
				message.setData(data);
					
				objectStream.writeObject(message);
				objectStream.flush();
					
				return "OK".equals(inputStream.readLine()) ? true : false;					
			
			} else {
				System.out.println("Initializing failed. ERROR at the start.");
				return false; 
			} 
				
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	public boolean sendMulticastMessage(CustomsContainer container) {
		
			try {
				if(init()) {
					
					outputStream.println("MULTICAST");
					String serverResponse = inputStream.readLine();
					
					if(serverResponse == null || "ERROR".equals(serverResponse)) {
						System.out.println("Server is unavailable. Try again later.");
						return false;
					}
					
					System.out.println("Successfully connected to ChatServer.");
					System.out.println("Server response: " + serverResponse);
					
					MessageUser sender = new MessageUser();
					sender.setTerminalName(container.getTerminalName());
					sender.setTypeOfGateway(container.getType());
					sender.setIdGateway(container.getId_gateway());
					sender.setOccupation(container.getOccupation());
					sender.setidMessageUser(NOT_IMPORTANT_VALUE);
					
					System.out.println("Enter the data about receiver: ");
					System.out.println("Terminal name: ");
					String terminalName = scanner.nextLine();
					
					if(terminalName.length() <2) {
						System.out.println("You entered illegal data. Try again later.");
						return false;
					}

					MessageUser receiver = new MessageUser();
					receiver.setTerminalName(terminalName);
					receiver.setIdGateway(NOT_IMPORTANT_VALUE);
					receiver.setidMessageUser(NOT_IMPORTANT_VALUE);
					receiver.setTypeOfGateway(MESSAGE_HELPER);
					receiver.setOccupation(MESSAGE_HELPER);
					
					System.out.println("Enter the message data: ");
					String data = scanner.nextLine();
					
					Message message = new Message();
					message.setSender(sender);
					message.setReceiver(receiver);
					message.setData(data);
					message.setDateOfCreating(new Date().toString());
					message.setTypeOfMessage("MULTICAST");
					
					objectStream.writeObject(message);
					objectStream.flush();
					
					return "OK".equals(inputStream.readLine()) ? true : false; 
					
				} else {
					System.out.println("Initializing failed. ERROR at the start.");
					return false; 
				}
					
			} catch (IOException e) {
				MyLogger.setLogger(ClientAppChatService.class.getName());
				MyLogger.logError(Level.WARNING, e.toString(), e);
				return false;
			} finally {
				freeUpResources();
			}
	}
	
	public boolean informAboutWarrant(CustomsContainer container, String reason) {
		
		try {
			if(init()) {

				outputStream.println("MULTICAST");
				String serverResponse = inputStream.readLine();
				
				if(serverResponse == null || "ERROR".equals(serverResponse)) {
					System.out.println("Server is unavailable. Try again later.");
					return false;
				}
			
				MessageUser sender = new MessageUser();
				sender.setTerminalName(container.getTerminalName());
				sender.setTypeOfGateway(container.getType());
				sender.setIdGateway(container.getId_gateway());
				sender.setOccupation(container.getOccupation());
				sender.setidMessageUser(NOT_IMPORTANT_VALUE);
				
				MessageUser receiver = new MessageUser();
				receiver.setTerminalName(sender.getTerminalName());
				receiver.setIdGateway(NOT_IMPORTANT_VALUE);
				receiver.setidMessageUser(NOT_IMPORTANT_VALUE);
				receiver.setTypeOfGateway(MESSAGE_HELPER);
				receiver.setOccupation(MESSAGE_HELPER);
				
				Message message = new Message();
				message.setSender(sender);
				message.setReceiver(receiver);
				message.setData(reason);
				message.setDateOfCreating(new Date().toString());
				message.setTypeOfMessage("MULTICAST");

				objectStream.writeObject(message);
				objectStream.flush();

				return "OK".equals(inputStream.readLine()) ? true : false;
				
			} else {
				System.out.println("Initializing failed. ERROR at the start.");
				return false; 
			}
			
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			freeUpResources();
		}
	}
	
	public void showMessages(CustomsContainer container){
		
		try {
			if(init()) {
				
				outputStream.println("GET_MESSAGES");
				String serverResponse = inputStream.readLine();
				
				if(serverResponse == null || "ERROR".equals(serverResponse)) {
					System.out.println("Server is unavailable. Try again later.");
					return;
				}
				
				System.out.println("Successfully connected to ChatServer.");
				
				MessageUser sender = new MessageUser();
				sender.setTerminalName(container.getTerminalName());
				sender.setTypeOfGateway(container.getType());
				sender.setIdGateway(container.getId_gateway());
				sender.setOccupation(container.getOccupation());
				sender.setidMessageUser(NOT_IMPORTANT_VALUE);
				
				objectStream.writeObject(sender);
				objectStream.flush();
				
				serverResponse = inputStream.readLine();
				if("ERROR".equals(serverResponse)) {
					System.out.println("Sometnig is wrong on server side. Try again later.");
					return;
				}
				
				if(serverResponse.startsWith("There")) {
					System.out.println("You do not have messages. Try again later.");
					return;
				}
				
				System.out.println(serverResponse);
				
				@SuppressWarnings("unchecked")
				ArrayList<Message> messages = (ArrayList<Message>) objectInStream.readObject();
 				
				int counter = 1;
				for(Message item : messages) {
					
					System.out.println("\nSerial number: " + counter++);
					System.out.println("Date of creating: " + item.getDateOfCreating());
					System.out.println("Message data: " + item.getData());
					System.out.println("Sender terminal name: " + item.getSender().getTerminalName());
					System.out.println("Sender occupation: " + item.getSender().getOccupation());
					System.out.println("Sender type: " + item.getSender().getTypeOfGateway());
					System.out.println("Sender id entrance/exit: " + item.getSender().getIdGateway());
				}
				
			} else
				System.out.println("Initializing failed. ERROR at the start.");			
			
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} finally {
			freeUpResources();
		}
	}	
	
	private static boolean init() {
		
		try {
			
			System.setProperty("javax.net.ssl.trustStore", TRUST_STORE_PATH);
			System.setProperty("javax.net.ssl.trustStorePassword", "securechat");
			
			Properties property = new Properties();
			property.load(new FileInputStream(RESOURCE_PATH));
			
			HOST = property.getProperty("HOST");
			KEY_STORE_PATH = property.getProperty("KEY_STORE_PATH");
			KEY_STORE_PASSWORD = property.getProperty("KEY_STORE_PASSWORD");
			PORT = Integer.parseInt(property.getProperty("PORT"));
			
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			clientSocket = (SSLSocket) socketFactory.createSocket(HOST, PORT);
			
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),true);
			
			javaStream = clientSocket.getOutputStream();
			objectStream = new ObjectOutputStream(javaStream);
			
			javaInStream = clientSocket.getInputStream();
			objectInStream = new ObjectInputStream(javaInStream);
			
			return true;
			
		} catch(FileNotFoundException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}
	
	private static void freeUpResources() {
		
		try {
			clientSocket.close();
			inputStream.close();
			outputStream.close();
			objectStream.close();
			objectInStream.close();
			
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppChatService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}
}
