/**
 * ClientAppSoapServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.logic;

public class ClientAppSoapServiceServiceLocator extends org.apache.axis.client.Service implements etf.project.logic.ClientAppSoapServiceService {

    public ClientAppSoapServiceServiceLocator() {
    }


    public ClientAppSoapServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ClientAppSoapServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ClientAppSoapService
    private java.lang.String ClientAppSoapService_address = "http://localhost:9000/ClientApp/services/ClientAppSoapService";

    public java.lang.String getClientAppSoapServiceAddress() {
        return ClientAppSoapService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ClientAppSoapServiceWSDDServiceName = "ClientAppSoapService";

    public java.lang.String getClientAppSoapServiceWSDDServiceName() {
        return ClientAppSoapServiceWSDDServiceName;
    }

    public void setClientAppSoapServiceWSDDServiceName(java.lang.String name) {
        ClientAppSoapServiceWSDDServiceName = name;
    }

    public etf.project.logic.ClientAppSoapService getClientAppSoapService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ClientAppSoapService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getClientAppSoapService(endpoint);
    }

    public etf.project.logic.ClientAppSoapService getClientAppSoapService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            etf.project.logic.ClientAppSoapServiceSoapBindingStub _stub = new etf.project.logic.ClientAppSoapServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getClientAppSoapServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setClientAppSoapServiceEndpointAddress(java.lang.String address) {
        ClientAppSoapService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (etf.project.logic.ClientAppSoapService.class.isAssignableFrom(serviceEndpointInterface)) {
                etf.project.logic.ClientAppSoapServiceSoapBindingStub _stub = new etf.project.logic.ClientAppSoapServiceSoapBindingStub(new java.net.URL(ClientAppSoapService_address), this);
                _stub.setPortName(getClientAppSoapServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ClientAppSoapService".equals(inputPortName)) {
            return getClientAppSoapService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://logic.project.etf", "ClientAppSoapServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://logic.project.etf", "ClientAppSoapService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ClientAppSoapService".equals(portName)) {
            setClientAppSoapServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
