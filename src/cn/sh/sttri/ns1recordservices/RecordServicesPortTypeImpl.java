
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package cn.sh.sttri.ns1recordservices;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.sttri.pojo.DevLog;
import com.sttri.pojo.DevRecord;
import com.sttri.pojo.DevRecordFile;
import com.sttri.pojo.MediaServer;
import com.sttri.pojo.TblDev;
import com.sttri.service.IDevLogService;
import com.sttri.service.IDevRecordFileService;
import com.sttri.service.IDevRecordService;
import com.sttri.service.IDevService;
import com.sttri.service.IMediaServerService;
import com.sttri.util.Util;

/**
 * This class was generated by Apache CXF 2.5.2
 * 2015-05-17T14:15:07.054+08:00
 * Generated source version: 2.5.2
 * 
 */

@javax.jws.WebService(
                      serviceName = "RecordServices",
                      portName = "RecordServicesHttpPort",
                      targetNamespace = "http://www.sttri.sh.cn/ns1RecordServices/",
                      endpointInterface = "cn.sh.sttri.ns1recordservices.RecordServicesPortType")
                      
public class RecordServicesPortTypeImpl implements RecordServicesPortType {

    private static final Logger LOG = Logger.getLogger(RecordServicesPortTypeImpl.class.getName());
    
    @Autowired
    private IDevRecordService devRecordService;
    @Autowired
    private IDevRecordFileService devRecordFileService;
    @Autowired
    private IDevService devService;
    @Autowired
    private IMediaServerService serverService;
    @Autowired
    private IDevLogService devLogService;

    /* (non-Javadoc)
     * @see cn.sh.sttri.ns1recordservices.RecordServicesPortType#devEndRecord(cn.sh.sttri.ns1recordservices.DevEndRecordReq  devEndRecordReq )*
     */
    public cn.sh.sttri.ns1recordservices.DevEndRecordRes devEndRecord(DevEndRecordReq devEndRecordReq) { 
        LOG.info("Executing operation devEndRecord");
        System.out.println(devEndRecordReq);
        try {
            cn.sh.sttri.ns1recordservices.DevEndRecordRes _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * 十分钟一个录像文件
     * @see cn.sh.sttri.ns1recordservices.RecordServicesPortType#devRecordUp(cn.sh.sttri.ns1recordservices.DevRecordUpReq  devRecordUpReq )*
     */
    public cn.sh.sttri.ns1recordservices.DevRecordUpRes devRecordUp(DevRecordUpReq devRecordUpReq) { 
        LOG.info("Executing operation devRecordUp");
        System.out.println(devRecordUpReq);
        try {
        	DevRecordFile drf = new DevRecordFile();
        	drf.setId(Util.getUUID(8));
        	drf.setDrId(devRecordUpReq.getRecordId());
        	DevRecord dr = devRecordService.getById(devRecordUpReq.getRecordId());
        	drf.setComId(dr.getComId());
        	drf.setMediaServerId(dr.getMediaServerId());
        	drf.setRecordName(devRecordUpReq.getRecordName());
        	drf.setRecordStartTime(devRecordUpReq.getRecordStartTime());
        	drf.setRecordEndTime(devRecordUpReq.getRecordEndTime());
        	drf.setRecordSize(devRecordUpReq.getRecordSize());
        	devRecordFileService.save(drf);
            cn.sh.sttri.ns1recordservices.DevRecordUpRes _return = new DevRecordUpRes();
            _return.setResult(0);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see cn.sh.sttri.ns1recordservices.RecordServicesPortType#devStartRecord(cn.sh.sttri.ns1recordservices.DevStartRecordReq  devStartRecordReq )*
     */
    public cn.sh.sttri.ns1recordservices.DevStartRecordRes devStartRecord(DevStartRecordReq devStartRecordReq) { 
        LOG.info("Executing operation devStartRecord");
        System.out.println(devStartRecordReq);
        try {
            cn.sh.sttri.ns1recordservices.DevStartRecordRes _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

	@Override
	public DevStreamErrorRes devStreamError(DevStreamErrorReq devStreamErrorReq) {
		// TODO Auto-generated method stub
		 LOG.info("Executing operation devRecordUp");
	     System.out.println(devStreamErrorReq);
	     DevStreamErrorRes res = new DevStreamErrorRes();
	     try {
			String recordId = devStreamErrorReq.getRecordId();
			/*
			 * 考虑到用户在遇到开启直播失败会再次点击开启直播时，
			 * 由于通过录像对象获取设备对象会造成粗暴更新设备的离线状态，
			 * 所以改成通过该录像编号直接获取拥有该录像编号的设备对象，
			 * 这样就避免了，更新设备离线状态时，该设备是在正常直播，
			 * 更新成离线状态后，就立即更新该设备的录像编号为空
			 */
//			DevRecord devRecord = this.devRecordService.getById(recordId);
			List<TblDev> dList = this.devService.getResultList(" o.drId=?", null, new Object[]{recordId});
			if(dList !=null && dList.size() > 0){
				TblDev dev = dList.get(0);
				//设备设置离线状态后，同时更新流媒体服务器上的在线设备数
				String serverId = dev.getServerId();
				if (serverId != null && !"".equals(serverId)) {
					MediaServer server = this.serverService.getById(serverId);
					int devNum = server.getDevNum();
					devNum = devNum-1<0?0:devNum-1;
					server.setDevNum(devNum);
					this.serverService.update(server);
					LOG.info("***更新流媒体服务器的在线设备数***:"+dev.getDevNo()+"&&&"+server.getServerName());
				}
				//设置该设备录像任务结束
				DevRecord devRecord = this.devRecordService.getById(recordId);
				devRecord.setRecordEndTime(Util.dateToStr(new Date()));
				devRecord.setRecordStatus(2);
				this.devRecordService.update(devRecord);
				//更新设备的在线状态
				dev.setOnLines(1);
				dev.setDrId("");
				dev.setServerId("");
				dev.setEditTime(Util.dateToStr(new Date()));
				this.devService.update(dev);
				//记录日志
				saveDevLog(dev, 3, dev.getDevNo()+",直播过程中出现网络异常导致中断!");
				res.setResult(0);
			}else {
				res.setResult(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			res.setResult(-1);
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 保存设备操作日志
	 */
	public void saveDevLog(TblDev dev,int logType,String logDesc){
		DevLog devLog = new DevLog();
		try {
			MediaServer server = null;
			if (dev != null) {
				String serverId = dev.getServerId();
				server = this.serverService.getById(serverId);
			}
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("addTime", "desc");
			String clientIP ="", operatorName="";
			List<DevLog> dLogs = this.devLogService.getResultList(" o.dev.id=? and o.logType=? ", orderby, new Object[]{dev.getId(),0});
			if (dLogs !=null && dLogs.size() >0) {
				clientIP = dLogs.get(0).getClientIP();
				operatorName = dLogs.get(0).getOperatorName();
			}
			String id = Util.getUUID(6);
			devLog.setId(id);
			devLog.setDev(dev);
			devLog.setMediaServer(server);
			devLog.setClientIP(clientIP);
			devLog.setOperatorName(operatorName);
			devLog.setOperatorCode("");
			devLog.setLogType(logType);
			devLog.setLogDesc(logDesc);
			devLog.setAddTime(Util.dateToStr(new Date()));
			this.devLogService.save(devLog);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}