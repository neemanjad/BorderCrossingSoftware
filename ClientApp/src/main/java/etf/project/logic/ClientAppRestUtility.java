package etf.project.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;

import com.google.gson.Gson;

import etf.project.model.container.PasswordContainer;
import etf.project.mylogger.MyLogger;

public class ClientAppRestUtility {
	
	private static String IN_MEMORY_DATABASE_URL;
	private Properties property;
	private static final Scanner scanner = new Scanner(System.in);
	
	public ClientAppRestUtility() {
		
		try {
			
			property = new Properties();
			property.load(new FileInputStream("resources/config.properties"));
			
			IN_MEMORY_DATABASE_URL = property.getProperty("IN_MEMORY_DATABASE_URL");
			
			
		} catch(Exception e) {
			MyLogger.setLogger(ClientAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}	
	}
	
	public boolean validateCredentials(String userName, String password) {
		
		HttpURLConnection connection = null;
		OutputStream outputStream = null;
		
		try {
			URL url = new URL(IN_MEMORY_DATABASE_URL + "validate/" + userName);
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			
			Gson gson = new Gson();
			outputStream = connection.getOutputStream();
		
			outputStream.write(gson.toJson(generateHash(password)).getBytes());
			outputStream.flush();
			
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
				return true;
			return false;
			
			
		} catch(MalformedURLException e) {
			MyLogger.setLogger(ClientAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			try {
				outputStream.close();
				connection.disconnect();
			} catch (IOException e) {
				MyLogger.setLogger(ClientAppRestUtility.class.getName());
				MyLogger.logError(Level.WARNING, e.toString(), e);
			}
		}
	}
	
	public boolean changePassword(String userName) {
		
		HttpURLConnection connection = null;
		OutputStream outputStream = null;
		
		try {
			
			System.out.println("Enter again your old password: ");
			String oldPassword = scanner.nextLine();
			
			System.out.println("Enter the new password: ");
			String newPassword = scanner.nextLine();
			
			if(newPassword.length() > 40 || newPassword.length() <1) {
				System.out.println("Your new password is over or under size. Error. Try again.");
				return false;
			}
			
			URL url = new URL(IN_MEMORY_DATABASE_URL + "change_password/" + userName);
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("PUT");
			
			outputStream = connection.getOutputStream();
			Gson gson = new Gson();
			
			PasswordContainer container = new PasswordContainer(generateHash(oldPassword), generateHash(newPassword));
			outputStream.write(gson.toJson(container).getBytes());
			outputStream.flush();
			
			return connection.getResponseCode() == HttpURLConnection.HTTP_OK ? true : false;
			
		} catch(MalformedURLException e) {
			MyLogger.setLogger(ClientAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} finally {
			try {
				outputStream.close();
				connection.disconnect();
			} catch (IOException e) {
				MyLogger.setLogger(ClientAppRestUtility.class.getName());
				MyLogger.logError(Level.WARNING, e.toString(), e);
			}
		}
	}
	
	private static String generateHash(String password) {
		
		try {
			MessageDigest mdigest = MessageDigest.getInstance("MD5");
			mdigest.update(password.getBytes());
			
			byte[] resultBytes = mdigest.digest();
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < resultBytes.length; i++)
				sb.append(Integer.toString((resultBytes[i] & 0xff) + 0x100, 16).substring(1));
			
			return sb.toString();
		} catch(NoSuchAlgorithmException e) {
			MyLogger.setLogger(ClientAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return "";
		}
	}

}
