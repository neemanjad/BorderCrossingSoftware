/**
 * PassengerContainer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.model.container;

public class PassengerContainer  implements java.io.Serializable {
    private java.lang.String dateAsString;

    private java.lang.String direction;

    private etf.project.model.passenger.Passenger passenger;

    private java.lang.String terminalName;

    public PassengerContainer() {
    }

    public PassengerContainer(
           java.lang.String dateAsString,
           java.lang.String direction,
           etf.project.model.passenger.Passenger passenger,
           java.lang.String terminalName) {
           this.dateAsString = dateAsString;
           this.direction = direction;
           this.passenger = passenger;
           this.terminalName = terminalName;
    }


    /**
     * Gets the dateAsString value for this PassengerContainer.
     * 
     * @return dateAsString
     */
    public java.lang.String getDateAsString() {
        return dateAsString;
    }


    /**
     * Sets the dateAsString value for this PassengerContainer.
     * 
     * @param dateAsString
     */
    public void setDateAsString(java.lang.String dateAsString) {
        this.dateAsString = dateAsString;
    }


    /**
     * Gets the direction value for this PassengerContainer.
     * 
     * @return direction
     */
    public java.lang.String getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this PassengerContainer.
     * 
     * @param direction
     */
    public void setDirection(java.lang.String direction) {
        this.direction = direction;
    }


    /**
     * Gets the passenger value for this PassengerContainer.
     * 
     * @return passenger
     */
    public etf.project.model.passenger.Passenger getPassenger() {
        return passenger;
    }


    /**
     * Sets the passenger value for this PassengerContainer.
     * 
     * @param passenger
     */
    public void setPassenger(etf.project.model.passenger.Passenger passenger) {
        this.passenger = passenger;
    }


    /**
     * Gets the terminalName value for this PassengerContainer.
     * 
     * @return terminalName
     */
    public java.lang.String getTerminalName() {
        return terminalName;
    }


    /**
     * Sets the terminalName value for this PassengerContainer.
     * 
     * @param terminalName
     */
    public void setTerminalName(java.lang.String terminalName) {
        this.terminalName = terminalName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PassengerContainer)) return false;
        PassengerContainer other = (PassengerContainer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateAsString==null && other.getDateAsString()==null) || 
             (this.dateAsString!=null &&
              this.dateAsString.equals(other.getDateAsString()))) &&
            ((this.direction==null && other.getDirection()==null) || 
             (this.direction!=null &&
              this.direction.equals(other.getDirection()))) &&
            ((this.passenger==null && other.getPassenger()==null) || 
             (this.passenger!=null &&
              this.passenger.equals(other.getPassenger()))) &&
            ((this.terminalName==null && other.getTerminalName()==null) || 
             (this.terminalName!=null &&
              this.terminalName.equals(other.getTerminalName())));
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
        if (getDateAsString() != null) {
            _hashCode += getDateAsString().hashCode();
        }
        if (getDirection() != null) {
            _hashCode += getDirection().hashCode();
        }
        if (getPassenger() != null) {
            _hashCode += getPassenger().hashCode();
        }
        if (getTerminalName() != null) {
            _hashCode += getTerminalName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PassengerContainer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://container.model.project.etf", "PassengerContainer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateAsString");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "dateAsString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passenger");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "passenger"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://passenger.model.project.etf", "Passenger"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://container.model.project.etf", "terminalName"));
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
