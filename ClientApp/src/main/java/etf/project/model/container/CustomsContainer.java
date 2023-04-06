/**
 * CustomsContainer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.model.container;

public class CustomsContainer  implements java.io.Serializable {
    private int id_gateway;

    private java.lang.String occupation;

    private java.lang.String terminalName;

    private java.lang.String type;

    public CustomsContainer() {
    }

    public CustomsContainer(
           int id_gateway,
           java.lang.String occupation,
           java.lang.String terminalName,
           java.lang.String type) {
           this.id_gateway = id_gateway;
           this.occupation = occupation;
           this.terminalName = terminalName;
           this.type = type;
    }


    /**
     * Gets the id_gateway value for this CustomsContainer.
     * 
     * @return id_gateway
     */
    public int getId_gateway() {
        return id_gateway;
    }


    /**
     * Sets the id_gateway value for this CustomsContainer.
     * 
     * @param id_gateway
     */
    public void setId_gateway(int id_gateway) {
        this.id_gateway = id_gateway;
    }


    /**
     * Gets the occupation value for this CustomsContainer.
     * 
     * @return occupation
     */
    public java.lang.String getOccupation() {
        return occupation;
    }


    /**
     * Sets the occupation value for this CustomsContainer.
     * 
     * @param occupation
     */
    public void setOccupation(java.lang.String occupation) {
        this.occupation = occupation;
    }


    /**
     * Gets the terminalName value for this CustomsContainer.
     * 
     * @return terminalName
     */
    public java.lang.String getTerminalName() {
        return terminalName;
    }


    /**
     * Sets the terminalName value for this CustomsContainer.
     * 
     * @param terminalName
     */
    public void setTerminalName(java.lang.String terminalName) {
        this.terminalName = terminalName;
    }


    /**
     * Gets the type value for this CustomsContainer.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this CustomsContainer.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomsContainer)) return false;
        CustomsContainer other = (CustomsContainer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.id_gateway == other.getId_gateway() &&
            ((this.occupation==null && other.getOccupation()==null) || 
             (this.occupation!=null &&
              this.occupation.equals(other.getOccupation()))) &&
            ((this.terminalName==null && other.getTerminalName()==null) || 
             (this.terminalName!=null &&
              this.terminalName.equals(other.getTerminalName()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        _hashCode += getId_gateway();
        if (getOccupation() != null) {
            _hashCode += getOccupation().hashCode();
        }
        if (getTerminalName() != null) {
            _hashCode += getTerminalName().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomsContainer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://container.model.project.etf", "CustomsContainer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id_gateway");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "id_gateway"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("occupation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "occupation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "terminalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
