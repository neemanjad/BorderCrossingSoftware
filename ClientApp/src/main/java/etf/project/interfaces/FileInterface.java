package etf.project.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote {
	public boolean saveDocument(byte[] data, String documentNumber) throws RemoteException;
}
