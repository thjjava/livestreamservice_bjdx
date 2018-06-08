
package cn.sh.sttri.ns1recordservices;

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
 *         &lt;element name="RecordId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecordName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecordSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RecordStartTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecordEndTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "recordId",
    "recordName",
    "recordSize",
    "recordStartTime",
    "recordEndTime"
})
@XmlRootElement(name = "devRecordUpReq")
public class DevRecordUpReq {

    @XmlElement(name = "RecordId", required = true)
    protected String recordId;
    @XmlElement(name = "RecordName", required = true)
    protected String recordName;
    @XmlElement(name = "RecordSize")
    protected int recordSize;
    @XmlElement(name = "RecordStartTime", required = true)
    protected String recordStartTime;
    @XmlElement(name = "RecordEndTime", required = true)
    protected String recordEndTime;

    /**
     * Gets the value of the recordId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * Sets the value of the recordId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordId(String value) {
        this.recordId = value;
    }

    /**
     * Gets the value of the recordName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordName() {
        return recordName;
    }

    /**
     * Sets the value of the recordName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordName(String value) {
        this.recordName = value;
    }

    /**
     * Gets the value of the recordSize property.
     * 
     */
    public int getRecordSize() {
        return recordSize;
    }

    /**
     * Sets the value of the recordSize property.
     * 
     */
    public void setRecordSize(int value) {
        this.recordSize = value;
    }

    /**
     * Gets the value of the recordStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordStartTime() {
        return recordStartTime;
    }

    /**
     * Sets the value of the recordStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordStartTime(String value) {
        this.recordStartTime = value;
    }

    /**
     * Gets the value of the recordEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordEndTime() {
        return recordEndTime;
    }

    /**
     * Sets the value of the recordEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordEndTime(String value) {
        this.recordEndTime = value;
    }

}
