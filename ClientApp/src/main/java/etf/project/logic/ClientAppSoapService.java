package etf.project.logic;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.rpc.ServiceException;

import etf.project.interfaces.FileInterface;
import etf.project.interfaces.WarrantInterface;
import etf.project.model.client.Client;
import etf.project.model.container.ClientContainer;
import etf.project.model.container.CustomsContainer;
import etf.project.model.passenger.Passenger;
import etf.project.mylogger.MyLogger;
import etf.project.service.CentralRegistryService;
import etf.project.service.CentralRegistryServiceServiceLocator;

public class ClientAppSoapService {

	private static int WARRANT_REGISTRY_PORT = 60_001;
	private static int FILE_SERVER_PORT = 60_000;
	
	private static final String POLICY_FILE_PATH = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/ClientApp/resources/client_policyfile.txt";
	private static final String POLICE_SIREN_PATH = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/ClientApp/resources/policeSiren.wav";
	
	public boolean checkTerminal(ClientContainer client) {
		
		try {
			CustomsContainer container = convertContainers(client);
			
			CentralRegistryServiceServiceLocator locator = new CentralRegistryServiceServiceLocator();
			CentralRegistryService service = locator.getCentralRegistryService();
			
			if(container == null)
				return false;
			
			container.setOccupation("POLICE");
			
			boolean policePart = service.isTerminalAndGatewayAvailable(container, true);
			if(!policePart)
				return false;
			
			container.setOccupation("CUSTOMS");
			return service.isTerminalAndGatewayAvailable(container, true); 
 
		} catch(RemoteException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ServiceException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}
	
	public boolean policeCheck(Client person, ClientContainer client) {
		
		try {
			
			Passenger passenger = convertPassenger(person);
			CustomsContainer container = convertContainers(client);
			
			CentralRegistryServiceServiceLocator locator = new CentralRegistryServiceServiceLocator();
			CentralRegistryService service = locator.getCentralRegistryService();

			System.setProperty("java.security.policy", POLICY_FILE_PATH);
			//if(System.getSecurityManager() == null)
				//System.setSecurityManager(new SecurityManager());
			
			if(!service.isTerminalRunning(container))
				return false; 

			Registry registry = LocateRegistry.getRegistry(WARRANT_REGISTRY_PORT);
			WarrantInterface warrantService = (WarrantInterface) registry.lookup("Warrants");

			boolean isWanted = warrantService.isWanted(passenger.getDocumentNumber());
			if(isWanted) {
				

				service.stopRunningTerminal(container);
				ClientAppChatService chatService = new ClientAppChatService();
				String WARRANT_ORDER = "WARRANT. "
						+ "The person with document number: " + passenger.getDocumentNumber() + " is here. Stop with work.";
				
				chatService.informAboutWarrant(container, WARRANT_ORDER);
				service.addPassengerOnWarrant(passenger);

				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(POLICE_SIREN_PATH).getAbsoluteFile());
				Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
		       
		       Thread.sleep(4000);
		       
		       service.startRunningTerminal(container);
		       String CONTINUE_ORDER = "CONTINUE (" + passenger.getDocumentNumber() + ") with your work.";
		       chatService.informAboutWarrant(container, CONTINUE_ORDER);
		       
				return false;
			}

			return service.addCheckedPassenger(passenger, container.getTerminalName(), container.getType());
						
		} catch (NotBoundException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (RemoteException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (UnsupportedAudioFileException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (IOException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (LineUnavailableException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (InterruptedException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (ServiceException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}		
	}
		
	public boolean customsCheck(byte[] data, String documentName) {
		
		try {
			
			System.setProperty("java.security.policy", "resources/server_policyfile.txt");
			//if(System.getSecurityManager() == null)
				//System.setSecurityManager(new SecurityManager());
			
			Registry registry = LocateRegistry.getRegistry(FILE_SERVER_PORT);
			FileInterface service = (FileInterface) registry.lookup("Documents");
			
			return service.saveDocument(data, documentName);
			
		} catch (NotBoundException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		} catch (RemoteException e) {
			MyLogger.setLogger(ClientAppSoapService.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}		
	}
	
	private CustomsContainer convertContainers(ClientContainer client) {
		
		if(client == null)
			return null;
		
		CustomsContainer customs = new CustomsContainer();
		customs.setTerminalName(client.getTerminalName());
		customs.setOccupation(client.getOccupation());
		customs.setId_gateway(client.getId_gateway());
		customs.setType(client.getType());
		
		return customs;
	}
	
	private Passenger convertPassenger(Client client) {
		
		if(client == null)
			return null;
		
		return new Passenger(client.getDocumentNumber(), client.getLastName(), client.getName());
	}
}
