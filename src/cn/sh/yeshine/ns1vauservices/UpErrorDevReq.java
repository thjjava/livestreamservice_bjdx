
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
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "errorCode"
})
@XmlRootElement(name = "upErrorDevReq")
public class UpErrorDevReq {

    @XmlElement(name = "DevId", required = true)
    protected String devId;
    @XmlElement(name = "ErrorCode")
    protected int errorCode;

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
     * Gets the value of the errorCode property.
     * 
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

}
