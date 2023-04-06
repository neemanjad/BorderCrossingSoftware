package etf.project.logic;

public class ClientAppSoapServiceProxy implements etf.project.logic.ClientAppSoapService {
  private String _endpoint = null;
  private etf.project.logic.ClientAppSoapService clientAppSoapService = null;
  
  public ClientAppSoapServiceProxy() {
    _initClientAppSoapServiceProxy();
  }
  
  public ClientAppSoapServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initClientAppSoapServiceProxy();
  }
  
  private void _initClientAppSoapServiceProxy() {
    try {
      clientAppSoapService = (new etf.project.logic.ClientAppSoapServiceServiceLocator()).getClientAppSoapService();
      if (clientAppSoapService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)clientAppSoapService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)clientAppSoapService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (clientAppSoapService != null)
      ((javax.xml.rpc.Stub)clientAppSoapService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public etf.project.logic.ClientAppSoapService getClientAppSoapService() {
    if (clientAppSoapService == null)
      _initClientAppSoapServiceProxy();
    return clientAppSoapService;
  }
  
  public boolean policeCheck(etf.project.model.client.Client person, etf.project.model.container.ClientContainer client) throws java.rmi.RemoteException{
    if (clientAppSoapService == null)
      _initClientAppSoapServiceProxy();
    return clientAppSoapService.policeCheck(person, client);
  }
  
  public boolean customsCheck(byte[] data, java.lang.String documentName) throws java.rmi.RemoteException{
    if (clientAppSoapService == null)
      _initClientAppSoapServiceProxy();
    return clientAppSoapService.customsCheck(data, documentName);
  }
  
  public boolean checkTerminal(etf.project.model.container.ClientContainer client) throws java.rmi.RemoteException{
    if (clientAppSoapService == null)
      _initClientAppSoapServiceProxy();
    return clientAppSoapService.checkTerminal(client);
  }
  
  
}