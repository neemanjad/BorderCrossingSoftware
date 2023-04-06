/**
 * CustomsTerminal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package etf.project.model.customsterminal;

public class CustomsTerminal  implements java.io.Serializable {
    private etf.project.model.customsterminal.Gateway[] entrances;

    private etf.project.model.customsterminal.Gateway[] exits;

    private long idCustomsTerminal;

    private int numberOfEntrances;

    private int numberOfExits;

    private java.lang.String terminalName;

    private boolean terminalOpen;

    private boolean terminalRunning;

    public CustomsTerminal() {
    }

    public CustomsTerminal(
           etf.project.model.customsterminal.Gateway[] entrances,
           etf.project.model.customsterminal.Gateway[] exits,
           long idCustomsTerminal,
           int numberOfEntrances,
           int numberOfExits,
           java.lang.String terminalName,
           boolean terminalOpen,
           boolean terminalRunning) {
           this.entrances = entrances;
           this.exits = exits;
           this.idCustomsTerminal = idCustomsTerminal;
           this.numberOfEntrances = numberOfEntrances;
           this.numberOfExits = numberOfExits;
           this.terminalName = terminalName;
           this.terminalOpen = terminalOpen;
           this.terminalRunning = terminalRunning;
    }


    /**
     * Gets the entrances value for this CustomsTerminal.
     * 
     * @return entrances
     */
    public etf.project.model.customsterminal.Gateway[] getEntrances() {
        return entrances;
    }


    /**
     * Sets the entrances value for this CustomsTerminal.
     * 
     * @param entrances
     */
    public void setEntrances(etf.project.model.customsterminal.Gateway[] entrances) {
        this.entrances = entrances;
    }


    /**
     * Gets the exits value for this CustomsTerminal.
     * 
     * @return exits
     */
    public etf.project.model.customsterminal.Gateway[] getExits() {
        return exits;
    }


    /**
     * Sets the exits value for this CustomsTerminal.
     * 
     * @param exits
     */
    public void setExits(etf.project.model.customsterminal.Gateway[] exits) {
        this.exits = exits;
    }


    /**
     * Gets the idCustomsTerminal value for this CustomsTerminal.
     * 
     * @return idCustomsTerminal
     */
    public long getIdCustomsTerminal() {
        return idCustomsTerminal;
    }


    /**
     * Sets the idCustomsTerminal value for this CustomsTerminal.
     * 
     * @param idCustomsTerminal
     */
    public void setIdCustomsTerminal(long idCustomsTerminal) {
        this.idCustomsTerminal = idCustomsTerminal;
    }


    /**
     * Gets the numberOfEntrances value for this CustomsTerminal.
     * 
     * @return numberOfEntrances
     */
    public int getNumberOfEntrances() {
        return numberOfEntrances;
    }


    /**
     * Sets the numberOfEntrances value for this CustomsTerminal.
     * 
     * @param numberOfEntrances
     */
    public void setNumberOfEntrances(int numberOfEntrances) {
        this.numberOfEntrances = numberOfEntrances;
    }


    /**
     * Gets the numberOfExits value for this CustomsTerminal.
     * 
     * @return numberOfExits
     */
    public int getNumberOfExits() {
        return numberOfExits;
    }


    /**
     * Sets the numberOfExits value for this CustomsTerminal.
     * 
     * @param numberOfExits
     */
    public void setNumberOfExits(int numberOfExits) {
        this.numberOfExits = numberOfExits;
    }


    /**
     * Gets the terminalName value for this CustomsTerminal.
     * 
     * @return terminalName
     */
    public java.lang.String getTerminalName() {
        return terminalName;
    }


    /**
     * Sets the terminalName value for this CustomsTerminal.
     * 
     * @param terminalName
     */
    public void setTerminalName(java.lang.String terminalName) {
        this.terminalName = terminalName;
    }


    /**
     * Gets the terminalOpen value for this CustomsTerminal.
     * 
     * @return terminalOpen
     */
    public boolean isTerminalOpen() {
        return terminalOpen;
    }


    /**
     * Sets the terminalOpen value for this CustomsTerminal.
     * 
     * @param terminalOpen
     */
    public void setTerminalOpen(boolean terminalOpen) {
        this.terminalOpen = terminalOpen;
    }


    /**
     * Gets the terminalRunning value for this CustomsTerminal.
     * 
     * @return terminalRunning
     */
    public boolean isTerminalRunning() {
        return terminalRunning;
    }


    /**
     * Sets the terminalRunning value for this CustomsTerminal.
     * 
     * @param terminalRunning
     */
    public void setTerminalRunning(boolean terminalRunning) {
        this.terminalRunning = terminalRunning;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomsTerminal)) return false;
        CustomsTerminal other = (CustomsTerminal) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.entrances==null && other.getEntrances()==null) || 
             (this.entrances!=null &&
              java.util.Arrays.equals(this.entrances, other.getEntrances()))) &&
            ((this.exits==null && other.getExits()==null) || 
             (this.exits!=null &&
              java.util.Arrays.equals(this.exits, other.getExits()))) &&
            this.idCustomsTerminal == other.getIdCustomsTerminal() &&
            this.numberOfEntrances == other.getNumberOfEntrances() &&
            this.numberOfExits == other.getNumberOfExits() &&
            ((this.terminalName==null && other.getTerminalName()==null) || 
             (this.terminalName!=null &&
              this.terminalName.equals(other.getTerminalName()))) &&
            this.terminalOpen == other.isTerminalOpen() &&
            this.terminalRunning == other.isTerminalRunning();
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
        if (getEntrances() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEntrances());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEntrances(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExits() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExits());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExits(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getIdCustomsTerminal()).hashCode();
        _hashCode += getNumberOfEntrances();
        _hashCode += getNumberOfExits();
        if (getTerminalName() != null) {
            _hashCode += getTerminalName().hashCode();
        }
        _hashCode += (isTerminalOpen() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isTerminalRunning() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomsTerminal.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "CustomsTerminal"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entrances");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "entrances"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "Gateway"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://service.project.etf", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "exits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "Gateway"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://service.project.etf", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCustomsTerminal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "idCustomsTerminal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfEntrances");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "numberOfEntrances"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfExits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "numberOfExits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "terminalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminalOpen");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "terminalOpen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminalRunning");
        elemField.setXmlName(new javax.xml.namespace.QName("http://customsterminal.model.project.etf", "terminalRunning"));
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
