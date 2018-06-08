
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
 *         &lt;element name="ServerNo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MemTotal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MemFree" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MemCached" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CpuUser" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CpuSys" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CpuIdle" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DevNum" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "serverNo",
    "memTotal",
    "memFree",
    "memCached",
    "cpuUser",
    "cpuSys",
    "cpuIdle",
    "devNum"
})
@XmlRootElement(name = "serverHeartbeatReq")
public class ServerHeartbeatReq {

    @XmlElement(name = "ServerNo")
    protected int serverNo;
    @XmlElement(name = "MemTotal")
    protected int memTotal;
    @XmlElement(name = "MemFree")
    protected int memFree;
    @XmlElement(name = "MemCached")
    protected int memCached;
    @XmlElement(name = "CpuUser")
    protected int cpuUser;
    @XmlElement(name = "CpuSys")
    protected int cpuSys;
    @XmlElement(name = "CpuIdle")
    protected int cpuIdle;
    @XmlElement(name = "DevNum")
    protected int devNum;

    /**
     * Gets the value of the serverNo property.
     * 
     */
    public int getServerNo() {
        return serverNo;
    }

    /**
     * Sets the value of the serverNo property.
     * 
     */
    public void setServerNo(int value) {
        this.serverNo = value;
    }

    /**
     * Gets the value of the memTotal property.
     * 
     */
    public int getMemTotal() {
        return memTotal;
    }

    /**
     * Sets the value of the memTotal property.
     * 
     */
    public void setMemTotal(int value) {
        this.memTotal = value;
    }

    /**
     * Gets the value of the memFree property.
     * 
     */
    public int getMemFree() {
        return memFree;
    }

    /**
     * Sets the value of the memFree property.
     * 
     */
    public void setMemFree(int value) {
        this.memFree = value;
    }

    /**
     * Gets the value of the memCached property.
     * 
     */
    public int getMemCached() {
        return memCached;
    }

    /**
     * Sets the value of the memCached property.
     * 
     */
    public void setMemCached(int value) {
        this.memCached = value;
    }

    /**
     * Gets the value of the cpuUser property.
     * 
     */
    public int getCpuUser() {
        return cpuUser;
    }

    /**
     * Sets the value of the cpuUser property.
     * 
     */
    public void setCpuUser(int value) {
        this.cpuUser = value;
    }

    /**
     * Gets the value of the cpuSys property.
     * 
     */
    public int getCpuSys() {
        return cpuSys;
    }

    /**
     * Sets the value of the cpuSys property.
     * 
     */
    public void setCpuSys(int value) {
        this.cpuSys = value;
    }

    /**
     * Gets the value of the cpuIdle property.
     * 
     */
    public int getCpuIdle() {
        return cpuIdle;
    }

    /**
     * Sets the value of the cpuIdle property.
     * 
     */
    public void setCpuIdle(int value) {
        this.cpuIdle = value;
    }

    /**
     * Gets the value of the devNum property.
     * 
     */
    public int getDevNum() {
        return devNum;
    }

    /**
     * Sets the value of the devNum property.
     * 
     */
    public void setDevNum(int value) {
        this.devNum = value;
    }

}
