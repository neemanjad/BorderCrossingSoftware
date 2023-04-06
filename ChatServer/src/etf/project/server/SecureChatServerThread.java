package etf.project.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

import org.json.JSONException;

import etf.project.model.baseworker.BaseWorker;
import etf.project.model.message.Message;
import etf.project.model.messageuser.MessageUser;
import etf.project.mylogger.MyLogger;

public class SecureChatServerThread extends Thread {

	private BufferedReader inputStream;
	private PrintWriter outputStream;
	
	private Socket serverSocket;
	
	private static InputStream javaStream;
	private static ObjectInputStream objectStream;
	
	private static OutputStream javaOutStream;
	private static ObjectOutputStream objectOutStream;
	
	public SecureChatServerThread(Socket socket) {
		super();
		try {
			this.serverSocket = socket;
			inputStream = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			outputStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream())),true);
			
			javaStream = this.serverSocket.getInputStream();
			objectStream = new ObjectInputStream(javaStream);
			
			javaOutStream = this.serverSocket.getOutputStream();
			objectOutStream = new ObjectOutputStream(javaOutStream);
			
		} catch(IOException e) {
			MyLogger.setLogger(SecureChatServerThread.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}
	
	@Override
	public void run() {
		
		try {
			
			BaseWorker baseWorker = new BaseWorker();
			String clientLine = inputStream.readLine();
			
			if(clientLine == null || clientLine.length() == 0) {
				outputStream.println("ERROR.");
				return;
			}
			
			switch(clientLine) {
			
			case "SIGN UP":
				outputStream.println("WELCOME. Introduce yourself.");
				MessageUser tempUser = (MessageUser) objectStream.readObject();
				
				boolean userInBase =  baseWorker.addMessageUser(tempUser);
				if(userInBase)
					outputStream.println("OK");
				else
					outputStream.println("NOT OK");
				break;
			
			case "UNICAST":
				outputStream.println("WELCOME. You can send messages now...");
				Message unicastMessage = (Message) objectStream.readObject();
				
				boolean unicastSenderInBase = baseWorker.addMessageUser(unicastMessage.getSender());
				boolean receiverInBase = baseWorker.addMessageUser(unicastMessage.getReceiver());
				
				if(!unicastSenderInBase || !receiverInBase) {
					outputStream.println("NOT OK.");
					return;
				}
				
				boolean unicastMessageSent = baseWorker.sendMessage(unicastMessage);
				if(unicastMessageSent)
					outputStream.println("OK");
				else
					outputStream.println("NOT OK");
				
				break;
				
			case "MULTICAST":
				outputStream.println("WELCOME. You can send messages now...");
				Message multicastMessage = (Message) objectStream.readObject();
				
				boolean multicastSenderInBase = baseWorker.addMessageUser(multicastMessage.getSender());
				if(!multicastSenderInBase) {
					outputStream.println("NOT OK");
					return;
				}	
				
				boolean multicastMessageSent = baseWorker.sendMessage(multicastMessage);
				if(multicastMessageSent)
					outputStream.println("OK");
				else
					outputStream.println("NOT OK");
				
				break;
				
			case "BROADCAST":
				outputStream.println("WELCOME. You can send messages now....");
				Message broadcastMessage = (Message) objectStream.readObject();
				
				boolean broadcastSenderInBase = baseWorker.addMessageUser(broadcastMessage.getSender());
				if(!broadcastSenderInBase) {
					outputStream.println("NOT OK");
					return;
				}
				
				boolean broadcastMessageSent = baseWorker.sendMessage(broadcastMessage);
				if(broadcastMessageSent)
					outputStream.println("OK");
				else
					outputStream.println("NOT OK");
				
				break;
				
			case "GET_MESSAGES":
				outputStream.println("WELCOME.Introduce yourself.");
				MessageUser user = (MessageUser) objectStream.readObject();
				
				if(user == null) {
					outputStream.println("ERROR");
					return;
				}
				
				ArrayList<Message> messages = baseWorker.getMessages(user);
				if(messages == null || messages.size() == 0) {
					outputStream.println("There is no messages for you. Try again later.");
					return;
				}
				
				outputStream.println("You have " + messages.size() + " message(s)");
				objectOutStream.writeObject(messages);
				
				break;
				
			default:
			
			}
			
		} catch (JSONException e) {
			MyLogger.setLogger(SecureChatServerThread.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return;
		} catch (IOException e) {
			MyLogger.setLogger(SecureChatServerThread.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return;
		} catch (ClassNotFoundException e) {
			MyLogger.setLogger(SecureChatServerThread.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return;
		} finally {
			try {
				serverSocket.close();
				inputStream.close();
				outputStream.close();
				objectStream.close();
				objectOutStream.close();
			} catch (IOException e) {
				MyLogger.setLogger(SecureChatServerThread.class.getName());
				MyLogger.logError(Level.WARNING, e.toString(), e);
			}
		}
	}
}
