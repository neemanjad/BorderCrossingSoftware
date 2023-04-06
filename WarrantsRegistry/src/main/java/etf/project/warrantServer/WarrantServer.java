package etf.project.warrantServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.logging.Level;

import etf.project.interfaces.WarrantInterface;
import etf.project.model.passenger.Passenger;
import etf.project.mylogger.MyLogger;

public class WarrantServer implements WarrantInterface {

	private static final String PATH_OF_WARRANT = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/WarrantsRegistry/WARRANT/";
	private static int PORT = 60_001;


	public WarrantServer() {
		super();
	}
	
	@Override
	public boolean isWanted(String documentNumber) throws RemoteException {
		try {
			return new File(PATH_OF_WARRANT + documentNumber).exists();
		} catch(NullPointerException e) {
			MyLogger.setLogger(WarrantServer.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}

	public static void main(String[] args){

		try {
			System.setProperty("java.security.policy", "resources/server_policyfile.txt");
			
			//if(System.getSecurityManager() == null)
				//System.setSecurityManager(new SecurityManager());
			
			init();
			
			WarrantServer server = new WarrantServer();
			WarrantInterface stub = (WarrantInterface) UnicastRemoteObject.exportObject(server, 0);
			
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("Warrants", stub);
			
			System.out.println("Warrants server is started at " + new Date() + " and running");	
			
		} catch(IOException e) {
			MyLogger.setLogger(WarrantServer.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
			
	}
	
	private static void init() throws IOException {
		
		addPassengerOnWarrant(new Passenger("Petar", "Petrovic", "B40T93A56"), "EUROPOL: CRIMINAL ACITIVITIES");
		addPassengerOnWarrant(new Passenger("Marko", "Markovic", "T98345J89"), "SIPA: ELECTORAL FRAUD");
		addPassengerOnWarrant(new Passenger("Janko", "Jankovic", "K78T33445"), "MUP RS: MURDER");
		addPassengerOnWarrant(new Passenger("Vito", "Vitic", "A98J21789"), "MUP FBIH: TRAFFIC FRAUD");
	
	}
	
	private static void addPassengerOnWarrant(Passenger p, String reason) throws IOException {
		
		File warrantFolder = new File(PATH_OF_WARRANT);
		if(!warrantFolder.isDirectory())
			warrantFolder.mkdir();
		
		FileWriter writer = new FileWriter(PATH_OF_WARRANT + p.getDocumentNumber());
		
		writer.write(p.getName() + " " + p.getLastName() + " " + p.getDocumentNumber() + " " + reason + " " + new Date());
		writer.close();
		
		System.out.println("Passenger " + p.getName() + " with document number " + p.getDocumentNumber() 
			+ " successfully added on the warrant list.");
	}
	
	/*
	private static void removePassengerFromWarrant(Passenger p) {
		
		File fileForRemove = new File(PATH_OF_WARRANT + p.getDocumentNumber());
		
		if(fileForRemove.exists())
			fileForRemove.delete();
		
		System.out.println("Passenger " + p.getName() + " with document number " + p.getDocumentNumber() 
		+ " successfully removed from the warrant list.");	
	}
	*/
}
