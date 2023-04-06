/**
 * CentralRegistryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.service;

public interface CentralRegistryService extends java.rmi.Remote {
    public boolean addTerminal(etf.project.model.customsterminal.CustomsTerminal terminal) throws java.rmi.RemoteException;
    public etf.project.model.customsterminal.CustomsTerminal getTerminal(long idTerminal) throws java.rmi.RemoteException;
    public boolean deleteTerminal(long idTerminal) throws java.rmi.RemoteException;
    public boolean updateTerminal(long idTerminal, etf.project.model.customsterminal.CustomsTerminal terminal) throws java.rmi.RemoteException;
    public void logLogin(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException;
    public void logCheckOut(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException;
    public boolean isTerminalRunning(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException;
    public boolean addCheckedPassenger(etf.project.model.passenger.Passenger passenger, java.lang.String terminalName, java.lang.String direction) throws java.rmi.RemoteException;
    public boolean startRunningTerminal(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException;
    public etf.project.model.container.PassengerContainer[] getCheckedPassengers() throws java.rmi.RemoteException;
    public boolean stopRunningTerminal(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException;
    public boolean isTerminalAndGatewayAvailable(etf.project.model.container.CustomsContainer container, boolean client) throws java.rmi.RemoteException;
    public etf.project.model.passenger.Passenger[] getPassengersOnWarrants() throws java.rmi.RemoteException;
    public boolean addPassengerOnWarrant(etf.project.model.passenger.Passenger passenger) throws java.rmi.RemoteException;
}
