
package de.uni_stuttgart.iaas.servicewrapper.opalxyzr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für tDataElementRef complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tDataElementRef"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataObjectName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataElementName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="isCollectionDataElement" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDataElementRef", propOrder = {
    "dataObjectName",
    "dataElementName"
})
public class TDataElementRef {

    @XmlElement(required = true)
    protected String dataObjectName;
    @XmlElement(required = true)
    protected String dataElementName;
    @XmlAttribute(name = "isCollectionDataElement")
    protected Boolean isCollectionDataElement;

    /**
     * Ruft den Wert der dataObjectName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataObjectName() {
        return dataObjectName;
    }

    /**
     * Legt den Wert der dataObjectName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataObjectName(String value) {
        this.dataObjectName = value;
    }

    /**
     * Ruft den Wert der dataElementName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataElementName() {
        return dataElementName;
    }

    /**
     * Legt den Wert der dataElementName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataElementName(String value) {
        this.dataElementName = value;
    }

    /**
     * Ruft den Wert der isCollectionDataElement-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCollectionDataElement() {
        return isCollectionDataElement;
    }

    /**
     * Legt den Wert der isCollectionDataElement-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCollectionDataElement(Boolean value) {
        this.isCollectionDataElement = value;
    }

}
