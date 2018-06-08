package com.sttri.thread;

import java.util.List;

import com.sttri.pojo.DevLog;
import com.sttri.pojo.TblIP;
import com.sttri.service.IDevLogService;
import com.sttri.service.ITblIPService;

public class IPAddressThread implements Runnable{
	private String logId;
	private String clientIP;
	private IDevLogService devLogService;
	private ITblIPService ipService;
	
	public IPAddressThread(IDevLogService devLogService,String logId, String clientIP,ITblIPService ipService){
		this.devLogService = devLogService;
		this.logId = logId;
		this.clientIP = clientIP;
		this.ipService = ipService;
	}

	@Override
	public void run() {
		System.out.println("进入IP地址解析线程!");
		try {
			//查询tblIP地址库里是否存在某个IP地址段包含clientIP
			List<TblIP> ips = ipService.getResultList(" INET_ATON('"+clientIP+"') >= INET_ATON(o.ipstart) and INET_ATON('"+clientIP+"') <= INET_ATON(o.ipend) ", null);
			if (ips != null && ips.size() > 0) {
				DevLog devLog = devLogService.getById(logId);
				devLog.setOperatorName(ips.get(0).getIsp());
				devLogService.update(devLog);
			}
			/*//根据IP获取数据库已存在，而且operatorName不为空，不为NULL的数据，然后将新增的该IP的数据的operatorName为数据库中已存在的值
			List<DevLog> dList = devLogService.getResultList(" o.clientIP=? and o.operatorName is not null and o.operatorName !='' ",null, new Object[]{clientIP});
			if (dList != null && dList.size() >0) {
				devLog.setOperatorName(dList.get(0).getOperatorName());
			}else {
				String isp = IPAddressUtils.getResult("ip="+clientIP+"\n", "UTF-8", "isp");
				devLog.setOperatorName(isp);
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
