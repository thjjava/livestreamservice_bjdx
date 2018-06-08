package cn.sh.sttri.ns1recordservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.5.5
 * 2016-03-18T13:46:43.283+08:00
 * Generated source version: 2.5.5
 * 
 */
@WebService(targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/", name = "RecordServicesPortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface RecordServicesPortType {

    @WebMethod
    @WebResult(name = "devEndRecordRes", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/", partName = "devEndRecordRes")
    public DevEndRecordRes devEndRecord(
        @WebParam(partName = "devEndRecordReq", name = "devEndRecordReq", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/")
        DevEndRecordReq devEndRecordReq
    );

    @WebMethod
    @WebResult(name = "devStreamErrorRes", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/", partName = "devStreamErrorRes")
    public DevStreamErrorRes devStreamError(
        @WebParam(partName = "devStreamErrorReq", name = "devStreamErrorReq", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/")
        DevStreamErrorReq devStreamErrorReq
    );

    @WebMethod
    @WebResult(name = "devRecordUpRes", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/", partName = "devRecordUpRes")
    public DevRecordUpRes devRecordUp(
        @WebParam(partName = "devRecordUpReq", name = "devRecordUpReq", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/")
        DevRecordUpReq devRecordUpReq
    );

    @WebMethod
    @WebResult(name = "devStartRecordRes", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/", partName = "devStartRecordRes")
    public DevStartRecordRes devStartRecord(
        @WebParam(partName = "devStartRecordReq", name = "devStartRecordReq", targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/")
        DevStartRecordReq devStartRecordReq
    );
}
