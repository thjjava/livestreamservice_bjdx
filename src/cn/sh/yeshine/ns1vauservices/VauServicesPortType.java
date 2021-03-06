package cn.sh.yeshine.ns1vauservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.5.5
 * 2016-12-07T16:25:00.563+08:00
 * Generated source version: 2.5.5
 * 
 */
@WebService(targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/", name = "VauServicesPortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface VauServicesPortType {

    @WebResult(name = "startTranscodingRes", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/", partName = "startTranscodingRes")
    @WebMethod
    public StartTranscodingRes startTranscoding(
        @WebParam(partName = "startTranscodingReq", name = "startTranscodingReq", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/")
        StartTranscodingReq startTranscodingReq
    );

    @WebResult(name = "upErrorDevRes", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/", partName = "upErrorDevRes")
    @WebMethod
    public UpErrorDevRes upErrorDev(
        @WebParam(partName = "upErrorDevReq", name = "upErrorDevReq", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/")
        UpErrorDevReq upErrorDevReq
    );

    @WebResult(name = "serverHeartbeatRes", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/", partName = "serverHeartbeatRes")
    @WebMethod
    public ServerHeartbeatRes serverHeartbeat(
        @WebParam(partName = "serverHeartbeatReq", name = "serverHeartbeatReq", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/")
        ServerHeartbeatReq serverHeartbeatReq
    );

    @WebResult(name = "stopTranscodingRes", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/", partName = "stopTranscodingRes")
    @WebMethod
    public StopTranscodingRes stopTranscoding(
        @WebParam(partName = "stopTranscodingReq", name = "stopTranscodingReq", targetNamespace = "http://www.yeshine.sh.cn/ns1VauServices/")
        StopTranscodingReq stopTranscodingReq
    );
}
