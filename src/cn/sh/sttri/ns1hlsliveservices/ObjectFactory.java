
package cn.sh.sttri.ns1hlsliveservices;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.sh.sttri.ns1hlsliveservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.sh.sttri.ns1hlsliveservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StartRtspToHlsRes }
     * 
     */
    public StartRtspToHlsRes createStartRtspToHlsRes() {
        return new StartRtspToHlsRes();
    }

    /**
     * Create an instance of {@link StartRtspToHlsReq }
     * 
     */
    public StartRtspToHlsReq createStartRtspToHlsReq() {
        return new StartRtspToHlsReq();
    }

    /**
     * Create an instance of {@link StopRtspToHlsRes }
     * 
     */
    public StopRtspToHlsRes createStopRtspToHlsRes() {
        return new StopRtspToHlsRes();
    }

    /**
     * Create an instance of {@link StopRtspToHlsReq }
     * 
     */
    public StopRtspToHlsReq createStopRtspToHlsReq() {
        return new StopRtspToHlsReq();
    }

}
