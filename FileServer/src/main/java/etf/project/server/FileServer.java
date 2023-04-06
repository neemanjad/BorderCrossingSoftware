package etf.project.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.logging.Level;

import etf.project.interfaces.FileInterface;
import etf.project.mylogger.MyLogger;

public class FileServer implements FileInterface {
	
	private static final String DOCUMENTS_PATH = "C:/Users/PC/Desktop/Fakultet/MDP/Project1/FileServer/DOCUMENTS";
	private static int PORT = 60_000;
	
	static {
		File documents = new File(DOCUMENTS_PATH);
		if(!documents.isDirectory())
			documents.mkdir();
	}
	
	public static void main(String[] args) {
		
		try{
			System.setProperty("java.security.policy", "resources/server_policyfile.txt");
			
			//if(System.getSecurityManager() == null)
				//System.setSecurityManager(new SecurityManager());
			
			FileServer server = new FileServer();
			FileInterface stub = (FileInterface) UnicastRemoteObject.exportObject(server, 0);
			
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("Documents", stub);
			
			System.out.println("File server is started at " + new Date() + " and running...");
			
		} catch(RemoteException e) {
			MyLogger.setLogger(FileServer.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
		}
	}

	@Override
	public boolean saveDocument(byte[] data, String documentNumber) throws RemoteException {

		try {
			
			String filePath = DOCUMENTS_PATH + File.separator + documentNumber;
			
			File tempFile = new File(filePath);
			if(tempFile.exists())
				tempFile.delete();
			
			FileOutputStream stream = new FileOutputStream(filePath);
			
			stream.write(data);
			stream.close();
	
			return true;
			
		} catch(IOException e) {
			MyLogger.setLogger(FileServer.class.getName());
			MyLogger.logError(Level.WARNING, e.toString(), e);
			return false;
		}
	}

}
