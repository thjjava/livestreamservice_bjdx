package com.sttri.thread;

import java.util.List;

import cn.sh.sttri.ns1hlsliveservices.HlsliveServicesPortType;
import cn.sh.sttri.ns1hlsliveservices.StartRtspToHlsReq;
import cn.sh.sttri.ns1hlsliveservices.StartRtspToHlsRes;

import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblDev;
import com.sttri.util.Constant;
import com.sttri.util.PubWebService;

public class HlsLiveStartThread implements Runnable{
	
	private String devId;
	private String realPlayUrl;
	private String webServiceUrl;
	private CommonDao dao;
	

	public HlsLiveStartThread(CommonDao dao,String devId, String realPlayUrl, String webServiceUrl){
		this.devId = devId;
		this.realPlayUrl = realPlayUrl;
		this.webServiceUrl = webServiceUrl;
		this.dao = dao;
	}

	@Override
	public void run() {
		System.out.println("进入HLS直播开始线程!");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<TblDev> dList = dao.getResultList(TblDev.class," o.devNo=?", null, devId);
		if(dList != null && dList.size() >0){
			TblDev dev = dList.get(0);
			StartRtspToHlsReq req = new StartRtspToHlsReq();
			req.setDevId(devId);
			req.setSourceUrl(realPlayUrl);
			req.setTransferMode(Integer.parseInt(Constant.readKey("rtpsToHlsModel")));//传输方式：0:UDP 1:TCP
			HlsliveServicesPortType portType = (HlsliveServicesPortType)PubWebService.getHlsService(HlsliveServicesPortType.class, webServiceUrl);
			StartRtspToHlsRes res = portType.startRtspToHls(req);
			if(res.getResult() ==0 && res.getHlsUrl()!=""){
				dev.setHlsUrl(res.getHlsUrl());
			}else {
				dev.setHlsUrl("");
			}
			dao.update(dev);
		}
	}
	
}
