
package cn.sh.yeshine.ns1vauservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DevId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SourceUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SourceProto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SourceTransMode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RetryNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RetryInterval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TransType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DestUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DestProto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "devId",
    "sourceUrl",
    "sourceProto",
    "sourceTransMode",
    "retryNum",
    "retryInterval",
    "transType",
    "destUrl",
    "destProto"
})
@XmlRootElement(name = "startTranscodingReq")
public class StartTranscodingReq {

    @XmlElement(name = "DevId", required = true)
    protected String devId;
    @XmlElement(name = "SourceUrl", required = true)
    protected String sourceUrl;
    @XmlElement(name = "SourceProto")
    protected int sourceProto;
    @XmlElement(name = "SourceTransMode")
    protected int sourceTransMode;
    @XmlElement(name = "RetryNum")
    protected int retryNum;
    @XmlElement(name = "RetryInterval")
    protected int retryInterval;
    @XmlElement(name = "TransType")
    protected int transType;
    @XmlElement(name = "DestUrl", required = true)
    protected String destUrl;
    @XmlElement(name = "DestProto")
    protected int destProto;

    /**
     * Gets the value of the devId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevId() {
        return devId;
    }

    /**
     * Sets the value of the devId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevId(String value) {
        this.devId = value;
    }

    /**
     * Gets the value of the sourceUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * Sets the value of the sourceUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceUrl(String value) {
        this.sourceUrl = value;
    }

    /**
     * Gets the value of the sourceProto property.
     * 
     */
    public int getSourceProto() {
        return sourceProto;
    }

    /**
     * Sets the value of the sourceProto property.
     * 
     */
    public void setSourceProto(int value) {
        this.sourceProto = value;
    }

    /**
     * Gets the value of the sourceTransMode property.
     * 
     */
    public int getSourceTransMode() {
        return sourceTransMode;
    }

    /**
     * Sets the value of the sourceTransMode property.
     * 
     */
    public void setSourceTransMode(int value) {
        this.sourceTransMode = value;
    }

    /**
     * Gets the value of the retryNum property.
     * 
     */
    public int getRetryNum() {
        return retryNum;
    }

    /**
     * Sets the value of the retryNum property.
     * 
     */
    public void setRetryNum(int value) {
        this.retryNum = value;
    }

    /**
     * Gets the value of the retryInterval property.
     * 
     */
    public int getRetryInterval() {
        return retryInterval;
    }

    /**
     * Sets the value of the retryInterval property.
     * 
     */
    public void setRetryInterval(int value) {
        this.retryInterval = value;
    }

    /**
     * Gets the value of the transType property.
     * 
     */
    public int getTransType() {
        return transType;
    }

    /**
     * Sets the value of the transType property.
     * 
     */
    public void setTransType(int value) {
        this.transType = value;
    }

    /**
     * Gets the value of the destUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestUrl() {
        return destUrl;
    }

    /**
     * Sets the value of the destUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestUrl(String value) {
        this.destUrl = value;
    }

    /**
     * Gets the value of the destProto property.
     * 
     */
    public int getDestProto() {
        return destProto;
    }

    /**
     * Sets the value of the destProto property.
     * 
     */
    public void setDestProto(int value) {
        this.destProto = value;
    }

}
