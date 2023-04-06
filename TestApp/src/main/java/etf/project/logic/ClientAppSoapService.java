/**
 * ClientAppSoapService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.logic;

public interface ClientAppSoapService extends java.rmi.Remote {
    public boolean policeCheck(etf.project.model.client.Client person, etf.project.model.container.ClientContainer client) throws java.rmi.RemoteException;
    public boolean customsCheck(byte[] data, java.lang.String documentName) throws java.rmi.RemoteException;
    public boolean checkTerminal(etf.project.model.container.ClientContainer client) throws java.rmi.RemoteException;
}
