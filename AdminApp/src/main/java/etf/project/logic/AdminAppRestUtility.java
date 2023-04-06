package etf.project.logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import etf.project.model.PasswordContainer.PasswordContainer;
import etf.project.model.user.User;
import etf.project.mylogger.MyLogger;

/**
 * @author NemanjaDavidovic
 * @since 14.01.2023.
 * 
 * <p> All activities related to REST methods from other projects are implemented here. </p>
 * 
 */

public class AdminAppRestUtility {
	
	private static Properties PROPERTY;
	private static String IN_MEMORY_DATABASE_URL;
	private static String FILE_SERVER_URL;
	private static String CENTRAL_REGISTRY_URL;
	private static Scanner scanner;
	
	public AdminAppRestUtility() {
		super();
		
		try {
			PROPERTY = new Properties();
			PROPERTY.load(new FileInputStream("resources/config.properties"));
			
			IN_MEMORY_DATABASE_URL = PROPERTY.getProperty("IN_MEMORY_DATABASE_URL");
			FILE_SERVER_URL = PROPERTY.getProperty("FILE_SERVER_URL");
			CENTRAL_REGISTRY_URL = PROPERTY.getProperty("CENTRAL_REGISTRY_URL");
			
			scanner = new Scanner(System.in);
			
		}catch(Exception e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}
	
	//InMemoryDataBase - adding new User to database
	public void addUser() {
		
		System.out.println("Please, enter the next data.");
		System.out.println("Name: ");
		String name = scanner.nextLine();
		
		System.out.println("Last name: ");
		String lastName = scanner.nextLine();
		
		System.out.println("Username: ");
		String userName = scanner.nextLine();
		
		System.out.println("Password: ");
		String password = scanner.nextLine();
		
		try {
			
			User user = new User(userName, generateHash(password), name, lastName);
			URL url = new URL(IN_MEMORY_DATABASE_URL);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			
			Gson gson = new Gson();
			OutputStream outputStream = connection.getOutputStream();
			
			outputStream.write(gson.toJson(user).getBytes());
			outputStream.flush();
			
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				
				System.out.println("User is not added. ERROR during ading.");
				
				outputStream.close();
				connection.disconnect();
				
				return;
			}
			
			outputStream.close();
			connection.disconnect();
			
			System.out.println("User successfully added to the InMemoryDataBase.");
		
		} catch (MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			System.err.println("ERROR ADDING USER: " + e.toString());
		} catch (IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			System.err.println("ERROR ADDING USER: " + e.toString());
		}
	}
	
	//InMemoryDataBase - get all Users from database
	public void getUsers(){
		
		InputStream inputStream = null;
		
		try {
			
			URL url = new URL(IN_MEMORY_DATABASE_URL);
			inputStream = url.openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String jsonText = readAll(reader);
			JSONArray jsonUsers = new JSONArray(jsonText);
			
			for(int i = 0; i < jsonUsers.length(); i++) {
				
				JSONObject user = jsonUsers.getJSONObject(i);
				System.out.println("\nUser: " + (i+1));
				
				System.out.println("Username: " + user.getString("userName"));
				System.out.println("Password: " + user.getString("password"));
				System.out.println("Name: " + user.getString("name"));
				System.out.println("Last name: " + user.getString("lastName"));
				
			}
			
		} catch(MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(JSONException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);	
		} finally {
			if(inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					MyLogger.setLogger(AdminAppRestUtility.class.getName());
					MyLogger.logError(Level.WARNING, e.toString(), e);
				}
		}
	}
	
	//InMemoryDataBase - get User with given userName
	public User getUser() {
		
		System.out.println("Please, enter the username of user you want: ");
		String userName = scanner.nextLine();
		
		if(userName == null || userName.length() == 0) {
			System.out.println("WRONG USERNAME. END");
			return null;
		}
		
		InputStream inputStream = null;
		try {
			
			inputStream = new URL(IN_MEMORY_DATABASE_URL + userName).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			
			String jsonText = readAll(reader);
			JSONObject user = new JSONObject(jsonText);
			
			System.out.println("Username: " + user.getString("userName"));
			System.out.println("Password: " + user.getString("password"));
			System.out.println("Name: " + user.getString("name"));
			System.out.println("Last name: " + user.getString("lastName"));
			
			return getUserFromJSON(user);
			
		} catch(MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch(IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch(JSONException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} finally {
			if(inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					MyLogger.setLogger(AdminAppRestUtility.class.getName());
					MyLogger.logError(Level.WARNING, e.toString(), e);
				}
		}
	}
	
	//InMemoryDataBase - update User with given userName
	public void updateUser() {
		
		try {
			
			User userForUpdate = this.getUser();
			
			System.out.println("On this way, you can change next data: name, lastName, userName.");
			System.out.println("Update name - 1");
			System.out.println("Update lastName - 2");
			System.out.println("Update userName - 3");
			System.out.println("Passwor - 4");
			
			System.out.println("Enter the option for updating.");
			System.out.println("OPTION: ");
			String option = scanner.nextLine();
			
			switch(option) {
			
			case "1":
				System.out.println("Enter the new name: ");
				String name = scanner.nextLine();
				userForUpdate.setName(name);
				break;
				
			case "2":
				System.out.println("Enter the new lastName: ");
				String lastName = scanner.nextLine();
				userForUpdate.setLastName(lastName);
				break;
				
			case "3":
				System.out.println("Enter the new userName: ");
				String userNameForUpdate = scanner.nextLine();
				
				if(userNameForUpdate == null || userNameForUpdate.length() == 0) {
					System.out.println("ERROR DURING UPDATE USER. WRONG NEW USERNAME.");
					return;
				}
				
				userForUpdate.setUserName(userNameForUpdate);
				break;
				
			case "4":
				System.out.println("Enter the new password: ");
				String passwordForUpdate = scanner.nextLine();
				
				if(passwordForUpdate.length() < 5) {
					System.out.println("ERROR DURING UPDATE USER. WRON NEW PASSWORD.");
					return;
				}
				
				userForUpdate.setPassword(passwordForUpdate);
				break;
				
			default:
				System.out.println("WRONG OPTION. ANYTHING WILL CHANGED AND UPDATED.");
				return;
			}
			
			System.out.println("Enter again the username of the the user you want to update: ");
			String userName = scanner.nextLine();
			
			if(userName == null || "".equals(userName)) {
				System.out.println("WRONG USERNAME DURING UPDATING USER.");
				return;
			}
			
			URL url = new URL(IN_MEMORY_DATABASE_URL + userName);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestMethod("PUT");
			
			Gson gson = new Gson();
			String userInputJSONString = gson.toJson(userForUpdate);
			
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(userInputJSONString.getBytes());
			outputStream.flush();
			
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				
				System.out.println("User " + userName + " is not updated. ERROR on the server side.");
				outputStream.close();
				connection.disconnect();
			}
			
			outputStream.close();
			connection.disconnect();
			
			System.out.println("User " + userForUpdate.getUserName() + " successfully update to the InMemoryDataBase.");
		
		} catch(MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(JSONException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} 
	}
	
	//InMemoryDataBase - change password for user with userName
	public void changePassword() {
		
		System.out.println("Enter the username of the user you want to change password: ");
		String userName = scanner.nextLine();
		
		if(userName == null || userName.length() == 0) {
			System.out.println("ERROR DURING CHANGING PASSWORD. WRONG USERNAME ON CLIENT SIDE.");
			return;
		}
			
		try {
			
			URL url = new URL(IN_MEMORY_DATABASE_URL + "change_password/" + userName);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoOutput(true);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			
			System.out.println("Enter the user " + userName + " old password: ");
			String oldPassword = scanner.nextLine();
			
			System.out.println("Enter the user " + userName + " new password you want: ");
			String newPassword = scanner.nextLine();
			
			PasswordContainer container = new PasswordContainer(generateHash(oldPassword), generateHash(newPassword));
			Gson gson = new Gson();
			
			String jsonContainerString = gson.toJson(container);
			OutputStream outputStream = connection.getOutputStream();
			
			outputStream.write(jsonContainerString.getBytes());
			outputStream.flush();
			
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				
				System.out.println("Password for user " + userName + " is not changed. ERROR on the server side.");
				outputStream.close();
				connection.disconnect();
				return;
			}
			
			outputStream.close();
			connection.disconnect();
			
			System.out.println("Password for user " + userName + " successfully changed on the InMemoryDataBase.");
			
		} catch(MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(JSONException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} 
		
	}
	
	//InMemoryDataBase - delete User with userName from database
	public void deleteUser() {
		
		System.out.println("Enter the username of the user you want to delete: ");
		String userName = scanner.nextLine();
		
		try {
			
			String PATH_FOR_DELETE = IN_MEMORY_DATABASE_URL + userName;
			URL url = new URL(PATH_FOR_DELETE);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			
			connection.setRequestMethod("DELETE");
			OutputStream outputStream = connection.getOutputStream();
			
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				
				System.out.println("User " + userName + "is not deleted. ERROR ON THE SERVER SIDE.");
				connection.disconnect();
				
				outputStream.close();
				return;	
			}
			
			connection.disconnect();
			outputStream.close();
			
			System.out.println("User " + userName + " successfully deleted.");
		
		} catch(MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}
	
	//FileServer - get all customs - passenger documents
	public void getDocuments(){
		
		InputStream inputStream = null;
		try {
		
			URL url = new URL(FILE_SERVER_URL);
			inputStream = url.openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String jsonText = readAll(reader);
			
			JSONArray jsonDocuments = new JSONArray(jsonText);
			for(int i = 0; i < jsonDocuments.length(); i++) {
				
				JSONObject document = jsonDocuments.getJSONObject(i);
				System.out.println("Document: " + (i+1));
				
				System.out.println("Document number: " + document.getString("documentNumber"));
				System.out.println("Issuer: " + document.getString("issuer"));
				System.out.println("Name: " + document.getString("name"));
				System.out.println("Last name: " + document.getString("lastName"));
				System.out.println("Note: " + document.getString("note"));
				System.out.println("Year valid: " + document.getString("validYear"));
				System.out.println("\n");
			}
			
		} catch(MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch(IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} finally {
			if(inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					MyLogger.setLogger(AdminAppRestUtility.class.getName());
					MyLogger.logError(Level.WARNING, e.toString(), e);
				}
		}
	}
	
	//CentralRegistry - get all passengers on warrants
	public void getPassengersOnWarrant(){
		
		InputStream inputStream = null;
		try {
			
			URL url = new URL(CENTRAL_REGISTRY_URL);
			inputStream = url.openStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String jsonText = readAll(reader);
			
			JSONArray jsonPassengers = new JSONArray(jsonText);
			for(int i = 0; i < jsonPassengers.length(); i++) {
				
				JSONObject passenger = jsonPassengers.getJSONObject(i);
				System.out.println("Passenger: " + (i+1));
				
				System.out.println("Name: " + passenger.getString("name"));
				System.out.println("Last name: " + passenger.getString("lastName"));
				System.out.println("Document number: " + passenger.getString("documentNumber"));
				System.out.println("\n");
			}
			
		} catch(MalformedURLException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);	
		} catch(IOException e) {
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} finally {
			if(inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					MyLogger.setLogger(AdminAppRestUtility.class.getName());
					MyLogger.logError(Level.WARNING, e.toString(), e);
				}
		}
	}
	
	private static String readAll(Reader reader) throws IOException {
		
		StringBuilder stringBuilder = new StringBuilder();
		int cp;
		
		while((cp = reader.read()) != -1)
			stringBuilder.append((char) cp);
		
		return stringBuilder.toString();
	}
	
	private static User getUserFromJSON(JSONObject jsonUser) {
		
		return new User(jsonUser.getString("userName"), jsonUser.getString("password"), 
				jsonUser.getString("name"), jsonUser.getString("lastName"));
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
			MyLogger.setLogger(AdminAppRestUtility.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return "";
		}
	}
	
}