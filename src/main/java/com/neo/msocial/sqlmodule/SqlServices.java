

package com.neo.msocial.sqlmodule;

public interface SqlServices extends javax.xml.rpc.Service {
    public String getSqlServicesHttpSoap11EndpointAddress();
    public SqlServicesPortType getSqlServicesHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException;
    public SqlServicesPortType getSqlServicesHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getSqlServicesHttpSoap12EndpointAddress();

    public SqlServicesPortType getSqlServicesHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException;

    public SqlServicesPortType getSqlServicesHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}