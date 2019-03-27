
package de.uni_stuttgart.iaas.servicewrapper.opalxyzr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="simulationIdentifier" type="{http://www.uni-stuttgart.de/iaas/serviceWrapper/opalXYZR/}tSimulationIdentifier"/&gt;
 *         &lt;element name="resultDataLinks" type="{http://www.uni-stuttgart.de/iaas/serviceWrapper/opalXYZR/}tResultDataList" minOccurs="0"/&gt;
 *         &lt;element name="faultMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "simulationIdentifier",
    "resultDataLinks",
    "faultMessage"
})
@XmlRootElement(name = "callback")
public class Callback {

    @XmlElement(required = true)
    protected TSimulationIdentifier simulationIdentifier;
    protected TResultDataList resultDataLinks;
    protected String faultMessage;

    /**
     * Ruft den Wert der simulationIdentifier-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TSimulationIdentifier }
     *     
     */
    public TSimulationIdentifier getSimulationIdentifier() {
        return simulationIdentifier;
    }

    /**
     * Legt den Wert der simulationIdentifier-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TSimulationIdentifier }
     *     
     */
    public void setSimulationIdentifier(TSimulationIdentifier value) {
        this.simulationIdentifier = value;
    }

    /**
     * Ruft den Wert der resultDataLinks-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TResultDataList }
     *     
     */
    public TResultDataList getResultDataLinks() {
        return resultDataLinks;
    }

    /**
     * Legt den Wert der resultDataLinks-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TResultDataList }
     *     
     */
    public void setResultDataLinks(TResultDataList value) {
        this.resultDataLinks = value;
    }

    /**
     * Ruft den Wert der faultMessage-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * Legt den Wert der faultMessage-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultMessage(String value) {
        this.faultMessage = value;
    }

}
