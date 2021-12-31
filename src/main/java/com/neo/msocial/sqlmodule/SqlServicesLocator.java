/**
 * SqlServicesLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.neo.msocial.sqlmodule;

public class SqlServicesLocator extends org.apache.axis.client.Service implements SqlServices {

    public SqlServicesLocator() {
    }


    public SqlServicesLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SqlServicesLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SqlServicesHttpSoap11Endpoint
    private String SqlServicesHttpSoap11Endpoint_address = "http://10.252.11.244:8083/services/SqlServices.SqlServicesHttpSoap11Endpoint/";

    public String getSqlServicesHttpSoap11EndpointAddress() {
        return SqlServicesHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private String SqlServicesHttpSoap11EndpointWSDDServiceName = "SqlServicesHttpSoap11Endpoint";

    public String getSqlServicesHttpSoap11EndpointWSDDServiceName() {
        return SqlServicesHttpSoap11EndpointWSDDServiceName;
    }

    public void setSqlServicesHttpSoap11EndpointWSDDServiceName(String name) {
        SqlServicesHttpSoap11EndpointWSDDServiceName = name;
    }

    public SqlServicesPortType getSqlServicesHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SqlServicesHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSqlServicesHttpSoap11Endpoint(endpoint);
    }

    public SqlServicesPortType getSqlServicesHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SqlServicesSoap11BindingStub _stub = new SqlServicesSoap11BindingStub(portAddress, this);
            _stub.setPortName(getSqlServicesHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSqlServicesHttpSoap11EndpointEndpointAddress(String address) {
        SqlServicesHttpSoap11Endpoint_address = address;
    }


    // Use to get a proxy class for SqlServicesHttpSoap12Endpoint
    private String SqlServicesHttpSoap12Endpoint_address = "http://10.252.11.244:8083/services/SqlServices.SqlServicesHttpSoap12Endpoint/";

    public String getSqlServicesHttpSoap12EndpointAddress() {
        return SqlServicesHttpSoap12Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private String SqlServicesHttpSoap12EndpointWSDDServiceName = "SqlServicesHttpSoap12Endpoint";

    public String getSqlServicesHttpSoap12EndpointWSDDServiceName() {
        return SqlServicesHttpSoap12EndpointWSDDServiceName;
    }

    public void setSqlServicesHttpSoap12EndpointWSDDServiceName(String name) {
        SqlServicesHttpSoap12EndpointWSDDServiceName = name;
    }

    public SqlServicesPortType getSqlServicesHttpSoap12Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SqlServicesHttpSoap12Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSqlServicesHttpSoap12Endpoint(endpoint);
    }

    public SqlServicesPortType getSqlServicesHttpSoap12Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SqlServicesSoap12BindingStub _stub = new SqlServicesSoap12BindingStub(portAddress, this);
            _stub.setPortName(getSqlServicesHttpSoap12EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSqlServicesHttpSoap12EndpointEndpointAddress(String address) {
        SqlServicesHttpSoap12Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SqlServicesPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SqlServicesSoap11BindingStub _stub = new SqlServicesSoap11BindingStub(new java.net.URL(SqlServicesHttpSoap11Endpoint_address), this);
                _stub.setPortName(getSqlServicesHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
            if (SqlServicesPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                SqlServicesSoap12BindingStub _stub = new SqlServicesSoap12BindingStub(new java.net.URL(SqlServicesHttpSoap12Endpoint_address), this);
                _stub.setPortName(getSqlServicesHttpSoap12EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
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
        String inputPortName = portName.getLocalPart();
        if ("SqlServicesHttpSoap11Endpoint".equals(inputPortName)) {
            return getSqlServicesHttpSoap11Endpoint();
        }
        else if ("SqlServicesHttpSoap12Endpoint".equals(inputPortName)) {
            return getSqlServicesHttpSoap12Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://vms.neo", "SqlServices");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://vms.neo", "SqlServicesHttpSoap11Endpoint"));
            ports.add(new javax.xml.namespace.QName("http://vms.neo", "SqlServicesHttpSoap12Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("SqlServicesHttpSoap11Endpoint".equals(portName)) {
            setSqlServicesHttpSoap11EndpointEndpointAddress(address);
        }
        else
if ("SqlServicesHttpSoap12Endpoint".equals(portName)) {
            setSqlServicesHttpSoap12EndpointEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
