
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package cn.sh.sttri.ns1hlsliveservices;

import java.util.logging.Logger;

/**
 * This class was generated by Apache CXF 2.5.5
 * 2015-12-15T09:57:39.743+08:00
 * Generated source version: 2.5.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "HlsliveServices",
                      portName = "HlsliveServicesHttpPort",
                      targetNamespace = "http://www.sttri.sh.cn/ns1HlsliveServices/",
                      wsdlLocation = "HlsliveService.wsdl",
                      endpointInterface = "cn.sh.sttri.ns1hlsliveservices.HlsliveServicesPortType")
                      
public class HlsliveServicesPortTypeImpl implements HlsliveServicesPortType {

    private static final Logger LOG = Logger.getLogger(HlsliveServicesPortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see cn.sh.sttri.ns1hlsliveservices.HlsliveServicesPortType#stopRtspToHls(cn.sh.sttri.ns1hlsliveservices.StopRtspToHlsReq  stopRtspToHlsReq )*
     */
    public cn.sh.sttri.ns1hlsliveservices.StopRtspToHlsRes stopRtspToHls(StopRtspToHlsReq stopRtspToHlsReq) { 
        LOG.info("Executing operation stopRtspToHls");
        System.out.println(stopRtspToHlsReq);
        try {
            cn.sh.sttri.ns1hlsliveservices.StopRtspToHlsRes _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see cn.sh.sttri.ns1hlsliveservices.HlsliveServicesPortType#startRtspToHls(cn.sh.sttri.ns1hlsliveservices.StartRtspToHlsReq  startRtspToHlsReq )*
     */
    public cn.sh.sttri.ns1hlsliveservices.StartRtspToHlsRes startRtspToHls(StartRtspToHlsReq startRtspToHlsReq) { 
        LOG.info("Executing operation startRtspToHls");
        System.out.println(startRtspToHlsReq);
        try {
            cn.sh.sttri.ns1hlsliveservices.StartRtspToHlsRes _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}