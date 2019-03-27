
package de.uni_stuttgart.iaas.servicewrapper.opalxyzr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für tDataModelReference complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tDataModelReference"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="namespaceURI" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="localName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDataModelReference", propOrder = {
    "namespaceURI",
    "localName"
})
public class TDataModelReference {

    @XmlElement(required = true)
    protected String namespaceURI;
    @XmlElement(required = true)
    protected String localName;

    /**
     * Ruft den Wert der namespaceURI-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    /**
     * Legt den Wert der namespaceURI-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespaceURI(String value) {
        this.namespaceURI = value;
    }

    /**
     * Ruft den Wert der localName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Legt den Wert der localName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalName(String value) {
        this.localName = value;
    }

}
