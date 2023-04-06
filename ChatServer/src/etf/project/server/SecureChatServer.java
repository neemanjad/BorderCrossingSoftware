package etf.project.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import etf.project.mylogger.MyLogger;

public class SecureChatServer {
	
	private static int PORT;
	private static String KEY_STORE_PATH, KEY_STORE_PASSWORD;
	
	public static void main(String[] args) {

		try {
			
			if(init()) {
				
				System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
				System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);
				
				SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
				ServerSocket serverSocket = socketFactory.createServerSocket(PORT);
				System.out.println("SecureChatServer is started and running...." + new Date());
				
				while(true) {
					SSLSocket socket = (SSLSocket) serverSocket.accept();
					new SecureChatServerThread(socket).start();;	
				}
				
			} else 
				System.out.println("Initialiting failed. Try again later. SecureChatServer is not running...");
			
		} catch(IOException e) {
			MyLogger.setLogger(SecureChatServer.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}

	private static boolean init() {
		
		try {
			
			Properties property = new Properties();
			property.load(new FileInputStream("resources/config.properties"));
			
			KEY_STORE_PATH = property.getProperty("KEY_STORE_PATH");
			KEY_STORE_PASSWORD = property.getProperty("KEY_STORE_PASSWORD");
			PORT = Integer.parseInt(property.getProperty("PORT"));
		
			return true;
			
		} catch(FileNotFoundException e) {
			MyLogger.setLogger(SecureChatServer.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (IOException e) {
			MyLogger.setLogger(SecureChatServer.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}
	
}
