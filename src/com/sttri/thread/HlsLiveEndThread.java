package com.sttri.thread;

import java.util.List;

import cn.sh.sttri.ns1hlsliveservices.HlsliveServicesPortType;
import cn.sh.sttri.ns1hlsliveservices.StopRtspToHlsReq;

import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblDev;
import com.sttri.util.PubWebService;

public class HlsLiveEndThread implements Runnable{
	
	private CommonDao dao;
	private String webServiceUrl;
	private String devId;
	

	public HlsLiveEndThread(String webServiceUrl,String devId,CommonDao dao){
		this.webServiceUrl = webServiceUrl;
		this.devId = devId;
		this.dao = dao;
	}

	@Override
	public void run() {
		System.out.println("进入Hls直播停止线程!");
		StopRtspToHlsReq req = new StopRtspToHlsReq();
		req.setDevId(devId);
		HlsliveServicesPortType portType = (HlsliveServicesPortType)PubWebService.getHlsService(HlsliveServicesPortType.class, webServiceUrl);
		portType.stopRtspToHls(req);
		List<TblDev> dList = dao.getResultList(TblDev.class," o.devNo=?", null, devId);
		TblDev dev = dList.get(0);
		dev.setHlsUrl("");
		dao.update(dev);
	}
	
}
