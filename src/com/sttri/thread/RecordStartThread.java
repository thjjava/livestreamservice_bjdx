package com.sttri.thread;

import cn.sh.sttri.ns1recordservices.DevStartRecordReq;
import cn.sh.sttri.ns1recordservices.DevStartRecordRes;
import cn.sh.sttri.ns1recordservices.RecordServicesPortType;

import com.sttri.util.PubWebService;

public class RecordStartThread implements Runnable{
	
	private String id;
	private String realPlayUrl;
	private String webServiceUrl;
	
	public RecordStartThread(String id, String realPlayUrl, String webServiceUrl){
		this.id = id;
		this.realPlayUrl = realPlayUrl;
		this.webServiceUrl = webServiceUrl;
	}

	@Override
	public void run() {
		System.out.println("进入录像开始线程!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DevStartRecordReq req = new DevStartRecordReq();
		req.setRecordId(id);
		req.setRecordUrl(realPlayUrl);
		RecordServicesPortType recordWebService = (RecordServicesPortType)PubWebService.getWebService(RecordServicesPortType.class, webServiceUrl);
		DevStartRecordRes res = recordWebService.devStartRecord(req);
		if (res.getResult() !=0) {
			System.out.println("***任务编号开启录像线程失败***:"+id+"&&,流媒体服务器连接失败！--"+webServiceUrl);
		}
	}
	
}
