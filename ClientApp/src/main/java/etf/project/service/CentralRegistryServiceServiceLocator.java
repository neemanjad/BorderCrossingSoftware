/**
 * CentralRegistryServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.service;

public class CentralRegistryServiceServiceLocator extends org.apache.axis.client.Service implements etf.project.service.CentralRegistryServiceService {

    public CentralRegistryServiceServiceLocator() {
    }


    public CentralRegistryServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CentralRegistryServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CentralRegistryService
    private java.lang.String CentralRegistryService_address = "http://localhost:9000/CentralRegistry/services/CentralRegistryService";

    public java.lang.String getCentralRegistryServiceAddress() {
        return CentralRegistryService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CentralRegistryServiceWSDDServiceName = "CentralRegistryService";

    public java.lang.String getCentralRegistryServiceWSDDServiceName() {
        return CentralRegistryServiceWSDDServiceName;
    }

    public void setCentralRegistryServiceWSDDServiceName(java.lang.String name) {
        CentralRegistryServiceWSDDServiceName = name;
    }

    public etf.project.service.CentralRegistryService getCentralRegistryService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CentralRegistryService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCentralRegistryService(endpoint);
    }

    public etf.project.service.CentralRegistryService getCentralRegistryService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            etf.project.service.CentralRegistryServiceSoapBindingStub _stub = new etf.project.service.CentralRegistryServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCentralRegistryServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCentralRegistryServiceEndpointAddress(java.lang.String address) {
        CentralRegistryService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (etf.project.service.CentralRegistryService.class.isAssignableFrom(serviceEndpointInterface)) {
                etf.project.service.CentralRegistryServiceSoapBindingStub _stub = new etf.project.service.CentralRegistryServiceSoapBindingStub(new java.net.URL(CentralRegistryService_address), this);
                _stub.setPortName(getCentralRegistryServiceWSDDServiceName());
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
        if ("CentralRegistryService".equals(inputPortName)) {
            return getCentralRegistryService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.project.etf", "CentralRegistryServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.project.etf", "CentralRegistryService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CentralRegistryService".equals(portName)) {
            setCentralRegistryServiceEndpointAddress(address);
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
