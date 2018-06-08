package com.sttri.thread;

import java.util.Date;

import cn.sh.sttri.ns1recordservices.DevEndRecordReq;
import cn.sh.sttri.ns1recordservices.DevEndRecordRes;
import cn.sh.sttri.ns1recordservices.RecordServicesPortType;

import com.sttri.pojo.DevRecord;
import com.sttri.service.IDevRecordService;
import com.sttri.util.PubWebService;
import com.sttri.util.Util;

public class RecordEndThread implements Runnable{
	
	private String drId;
	private IDevRecordService devRecordService;
	private String webServiceUrl;
	
	public RecordEndThread(IDevRecordService devRecordService, String drId, String webServiceUrl){
		this.devRecordService = devRecordService;
		this.drId = drId;
		this.webServiceUrl = webServiceUrl;
	}

	@Override
	public void run() {
		System.out.println("进入录像停止线程!");
		DevEndRecordReq req = new DevEndRecordReq();
		req.setRecordId(drId);
		RecordServicesPortType recordWebService = (RecordServicesPortType)PubWebService.getWebService(RecordServicesPortType.class, webServiceUrl);
		DevEndRecordRes res = recordWebService.devEndRecord(req);
		DevRecord dr = devRecordService.getById(drId);
		dr.setRecordEndTime(Util.dateToStr(new Date()));
		if(res.getResult()==0){
			dr.setRecordStatus(2);
		}else{
			dr.setRecordStatus(3);
		}
		devRecordService.update(dr);
	}
	
}
