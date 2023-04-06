package etf.project.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author NemanjaDavidovic
 * @since 02.01.2023.
 *
 * <p> Interface for JAVA RMI. </p>
 *
 */

public interface WarrantInterface extends Remote {
	
	public boolean isWanted(String documentNumber) throws RemoteException;

}
