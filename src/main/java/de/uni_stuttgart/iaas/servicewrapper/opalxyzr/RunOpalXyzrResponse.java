
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
 *         &lt;element name="simulationID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="started" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "simulationID",
    "started",
    "message"
})
@XmlRootElement(name = "runOpalXyzrResponse")
public class RunOpalXyzrResponse {

    @XmlElement(required = true)
    protected String simulationID;
    protected boolean started;
    @XmlElement(required = true)
    protected String message;

    /**
     * Ruft den Wert der simulationID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimulationID() {
        return simulationID;
    }

    /**
     * Legt den Wert der simulationID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimulationID(String value) {
        this.simulationID = value;
    }

    /**
     * Ruft den Wert der started-Eigenschaft ab.
     * 
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Legt den Wert der started-Eigenschaft fest.
     * 
     */
    public void setStarted(boolean value) {
        this.started = value;
    }

    /**
     * Ruft den Wert der message-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Legt den Wert der message-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
