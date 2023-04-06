package etf.project.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WarrantInterface extends Remote {
	public boolean isWanted(String documentNumber) throws RemoteException;
}
