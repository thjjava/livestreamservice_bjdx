package com.sttri.thread;

import java.util.List;

import cn.sh.yeshine.ns1vauservices.StopTranscodingReq;
import cn.sh.yeshine.ns1vauservices.VauServicesPortType;

import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblDev;
import com.sttri.util.PubWebService;

public class TransCodeEndThread implements Runnable{
	
	private CommonDao dao;
	private String webServiceUrl;
	private String devId;
	

	public TransCodeEndThread(String webServiceUrl,String devId,CommonDao dao){
		this.webServiceUrl = webServiceUrl;
		this.devId = devId;
		this.dao = dao;
	}

	@Override
	public void run() {
		System.out.println("进入转码服务停止线程!");
		StopTranscodingReq req = new StopTranscodingReq();
		req.setDevId(devId);
		VauServicesPortType portType = (VauServicesPortType)PubWebService.getTransCodeService(VauServicesPortType.class, webServiceUrl);
		portType.stopTranscoding(req);
		List<TblDev> dList = dao.getResultList(TblDev.class," o.devNo=?", null, devId);
		TblDev dev = dList.get(0);
		dev.setSubPublishUrl("");
		dao.update(dev);
	}
	
}
