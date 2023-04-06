/**
 * Gateway.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.model.customsterminal;

public class Gateway  implements java.io.Serializable {
    private boolean customs;

    private int idGateway;

    private boolean open;

    private boolean police;

    public Gateway() {
    }

    public Gateway(
           boolean customs,
           int idGateway,
           boolean open,
           boolean police) {
           this.customs = customs;
           this.idGateway = idGateway;
           this.open = open;
           this.police = police;
    }


    /**
     * Gets the customs value for this Gateway.
     * 
     * @return customs
     */
    public boolean isCustoms() {
        return customs;
    }


    /**
     * Sets the customs value for this Gateway.
     * 
     * @param customs
     */
    public void setCustoms(boolean customs) {
        this.customs = customs;
    }


    /**
     * Gets the idGateway value for this Gateway.
     * 
     * @return idGateway
     */
    public int getIdGateway() {
        return idGateway;
    }


    /**
     * Sets the idGateway value for this Gateway.
     * 
     * @param idGateway
     */
    public void setIdGateway(int idGateway) {
        this.idGateway = idGateway;
    }


    /**
     * Gets the open value for this Gateway.
     * 
     * @return open
     */
    public boolean isOpen() {
        return open;
    }


    /**
     * Sets the open value for this Gateway.
     * 
     * @param open
     */
    public void setOpen(boolean open) {
        this.open = open;
    }


    /**
     * Gets the police value for this Gateway.
     * 
     * @return police
     */
    public boolean isPolice() {
        return police;
    }


    /**
     * Sets the police value for this Gateway.
     * 
     * @param police
     */
    public void setPolice(boolean police) {
        this.police = police;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Gateway)) return false;
        Gateway other = (Gateway) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.customs == other.isCustoms() &&
            this.idGateway == other.getIdGateway() &&
            this.open == other.isOpen() &&
            this.police == other.isPolice();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += (isCustoms() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getIdGateway();
        _hashCode += (isOpen() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isPolice() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Gateway.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "Gateway"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customs");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "customs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idGateway");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "idGateway"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("open");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "open"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("police");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "police"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
