/**
 * ClientAppSoapServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.logic;

public interface ClientAppSoapServiceService extends javax.xml.rpc.Service {
    public java.lang.String getClientAppSoapServiceAddress();

    public etf.project.logic.ClientAppSoapService getClientAppSoapService() throws javax.xml.rpc.ServiceException;

    public etf.project.logic.ClientAppSoapService getClientAppSoapService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
