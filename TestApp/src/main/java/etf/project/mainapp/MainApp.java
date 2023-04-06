package etf.project.mainapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.Year;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.zip.DeflaterOutputStream;

import javax.xml.rpc.ServiceException;

import com.google.gson.Gson;

import etf.project.logic.ClientAppSoapService;
import etf.project.logic.ClientAppSoapServiceServiceLocator;
import etf.project.model.client.Client;
import etf.project.model.container.ClientContainer;
import etf.project.model.document.Document;
import etf.project.mylogger.MyLogger;

public class MainApp {
	
	private static final Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		try {
			
			System.setProperty("java.security.policy", "resources/client_policyfile.txt");
			
			System.out.println("Welcome");
			System.out.println("To correct use of the system, please enter all requested information true.");
			
			System.out.println("Terminal name: ");
			String terminalName = scanner.nextLine();
			
			System.out.println("Id entrance/exit: ");
			int idGateway = Integer.parseInt(scanner.nextLine());
			
			System.out.println("For entrance enter ENTRANCE, for exit enter EXIT");
			String typeOfGateway = scanner.nextLine();
			
			ClientContainer container = new ClientContainer(idGateway,"",terminalName,typeOfGateway);
			
			ClientAppSoapServiceServiceLocator locator = new ClientAppSoapServiceServiceLocator();
			ClientAppSoapService service = locator.getClientAppSoapService();
			
			if(service.checkTerminal(container)) {
				
				System.out.println("Unfortunately, this is an old system, so you will have to enter your information manually.");
				
				System.out.println("Name: ");
				String name = scanner.nextLine();
				
				System.out.println("Last name: ");
				String lastName = scanner.nextLine();
				
				System.out.println("Document number: ");
				String documentNumber = scanner.nextLine();
				
				System.out.println("Issuer of your document: ");
				String issuer = scanner.nextLine();
				
				System.out.println("Note: ");
				String note = scanner.nextLine();
				
				System.out.println("Valid year: ");
				String validYear = scanner.nextLine();
				
				int yearFromDocument = Integer.parseInt(validYear);
				int yearNow = Year.now().getValue();
				
				if(yearFromDocument < yearNow) {
					System.out.println("Your document is out of date.");
					return;
				}
				
				if((name.length() < 1 || name.length() > 30) || 
						(lastName.length() < 1 || lastName.length() > 30) || (documentNumber.length() < 5 || documentNumber.length() > 20)) {
					System.out.println("You entered unallowed data. Sorry. Try again later.");
					return;
				}
				
				Client client = new Client(documentNumber, lastName, name);
				
				if(!service.policeCheck(client, container)) {
					System.out.println("You have not passed the police check. Sorry. Try again later. Stay where you are...");
					return;
				}
				

				Document document = new Document(documentNumber, issuer, name, lastName, note, validYear);
				byte[] documentInBytes = prepareDocument(document);

				if(!service.customsCheck(documentInBytes, documentNumber)) {
					System.out.println("You have not passed the customs check. Sorry. Try again later.");
					return;
				}
				
				System.out.println("You passed successfully. Good luck.");
				
			} else
				System.out.println("Sorry, you can not go any further.");
			
			
			
		} catch(NumberFormatException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch (ServiceException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} catch (RemoteException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		} finally {
			scanner.close();
		}
	}
	
	private static byte[] prepareDocument(Document document) {
		
		try {
			Gson gson = new Gson();
			String inputPath = System.getProperty("user.dir") + File.separator + document.getDocumentNumber();
			String outputPath = System.getProperty("user.dir") + File.separator + document.getDocumentNumber() + "COMPRESSED";
			
			FileWriter fileWriter = new FileWriter(inputPath);
			fileWriter.write(gson.toJson(document));
			fileWriter.close();
			
			FileInputStream inputStream = new FileInputStream(inputPath);
			FileOutputStream outputStream = new FileOutputStream(outputPath);
			
			DeflaterOutputStream compresser = new DeflaterOutputStream(outputStream);
			int contents;
			
			while((contents = inputStream.read()) != -1)
				compresser.write(contents);
			
			compresser.close();
			inputStream.close();
			outputStream.close();
			
			byte[] array = Files.readAllBytes(Paths.get(outputPath));
			
			File fileGson = new File(inputPath);
			File fileCompressed = new File(outputPath);
			
			if(fileGson.exists())
				fileGson.delete();
			
			if(fileCompressed.exists())
				fileCompressed.delete();
			
			return array;
			
		} catch (FileNotFoundException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		} catch (IOException e) {
			MyLogger.setLogger(MainApp.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return null;
		}
	}
}
