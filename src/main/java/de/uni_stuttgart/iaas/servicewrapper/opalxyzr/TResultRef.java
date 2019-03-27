
package de.uni_stuttgart.iaas.servicewrapper.opalxyzr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für tResultRef complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tResultRef"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataObjectName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataElementName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="linkToDataValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="directLinkToData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tResultRef", propOrder = {
    "dataObjectName",
    "dataElementName",
    "linkToDataValue",
    "directLinkToData"
})
public class TResultRef {

    @XmlElement(required = true)
    protected String dataObjectName;
    @XmlElement(required = true)
    protected String dataElementName;
    @XmlElement(required = true)
    protected String linkToDataValue;
    @XmlElement(required = true)
    protected String directLinkToData;

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
     * Ruft den Wert der linkToDataValue-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToDataValue() {
        return linkToDataValue;
    }

    /**
     * Legt den Wert der linkToDataValue-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToDataValue(String value) {
        this.linkToDataValue = value;
    }

    /**
     * Ruft den Wert der directLinkToData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectLinkToData() {
        return directLinkToData;
    }

    /**
     * Legt den Wert der directLinkToData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectLinkToData(String value) {
        this.directLinkToData = value;
    }

}
