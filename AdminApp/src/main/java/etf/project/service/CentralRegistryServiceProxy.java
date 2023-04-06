package etf.project.service;

public class CentralRegistryServiceProxy implements etf.project.service.CentralRegistryService {
  private String _endpoint = null;
  private etf.project.service.CentralRegistryService centralRegistryService = null;
  
  public CentralRegistryServiceProxy() {
    _initCentralRegistryServiceProxy();
  }
  
  public CentralRegistryServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCentralRegistryServiceProxy();
  }
  
  private void _initCentralRegistryServiceProxy() {
    try {
      centralRegistryService = (new etf.project.service.CentralRegistryServiceServiceLocator()).getCentralRegistryService();
      if (centralRegistryService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)centralRegistryService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)centralRegistryService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (centralRegistryService != null)
      ((javax.xml.rpc.Stub)centralRegistryService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public etf.project.service.CentralRegistryService getCentralRegistryService() {
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService;
  }
  
  public boolean addTerminal(etf.project.model.customsterminal.CustomsTerminal terminal) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.addTerminal(terminal);
  }
  
  public etf.project.model.customsterminal.CustomsTerminal getTerminal(long idTerminal) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.getTerminal(idTerminal);
  }
  
  public boolean deleteTerminal(long idTerminal) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.deleteTerminal(idTerminal);
  }
  
  public boolean updateTerminal(long idTerminal, etf.project.model.customsterminal.CustomsTerminal terminal) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.updateTerminal(idTerminal, terminal);
  }
  
  public void logLogin(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    centralRegistryService.logLogin(container);
  }
  
  public void logCheckOut(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    centralRegistryService.logCheckOut(container);
  }
  
  public boolean isTerminalRunning(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.isTerminalRunning(container);
  }
  
  public boolean addCheckedPassenger(etf.project.model.passenger.Passenger passenger, java.lang.String terminalName, java.lang.String direction) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.addCheckedPassenger(passenger, terminalName, direction);
  }
  
  public boolean startRunningTerminal(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.startRunningTerminal(container);
  }
  
  public etf.project.model.container.PassengerContainer[] getCheckedPassengers() throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.getCheckedPassengers();
  }
  
  public boolean stopRunningTerminal(etf.project.model.container.CustomsContainer container) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.stopRunningTerminal(container);
  }
  
  public boolean isTerminalAndGatewayAvailable(etf.project.model.container.CustomsContainer container, boolean client) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.isTerminalAndGatewayAvailable(container, client);
  }
  
  public etf.project.model.passenger.Passenger[] getPassengersOnWarrants() throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.getPassengersOnWarrants();
  }
  
  public boolean addPassengerOnWarrant(etf.project.model.passenger.Passenger passenger) throws java.rmi.RemoteException{
    if (centralRegistryService == null)
      _initCentralRegistryServiceProxy();
    return centralRegistryService.addPassengerOnWarrant(passenger);
  }
  
  
}