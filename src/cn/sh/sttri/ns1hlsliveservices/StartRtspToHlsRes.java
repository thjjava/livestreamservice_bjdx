
package cn.sh.sttri.ns1hlsliveservices;

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
 *         &lt;element name="Result" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HlsUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ThumbnailUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "result",
    "hlsUrl",
    "thumbnailUrl"
})
@XmlRootElement(name = "startRtspToHlsRes")
public class StartRtspToHlsRes {

    @XmlElement(name = "Result")
    protected int result;
    @XmlElement(name = "HlsUrl", required = true)
    protected String hlsUrl;
    @XmlElement(name = "ThumbnailUrl", required = true)
    protected String thumbnailUrl;

    /**
     * Gets the value of the result property.
     * 
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     */
    public void setResult(int value) {
        this.result = value;
    }

    /**
     * Gets the value of the hlsUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHlsUrl() {
        return hlsUrl;
    }

    /**
     * Sets the value of the hlsUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHlsUrl(String value) {
        this.hlsUrl = value;
    }

    /**
     * Gets the value of the thumbnailUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * Sets the value of the thumbnailUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThumbnailUrl(String value) {
        this.thumbnailUrl = value;
    }

}
