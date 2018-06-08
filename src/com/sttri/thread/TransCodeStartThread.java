package com.sttri.thread;

import java.util.List;

import cn.sh.yeshine.ns1vauservices.StartTranscodingReq;
import cn.sh.yeshine.ns1vauservices.StartTranscodingRes;
import cn.sh.yeshine.ns1vauservices.VauServicesPortType;

import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblDev;
import com.sttri.util.Base64Util;
import com.sttri.util.PubWebService;

public class TransCodeStartThread implements Runnable{
	
	private String devId;
	private String publishUrl;
	private String webServiceUrl;
	private CommonDao dao;
	private String realPlayUrl;
	

	public TransCodeStartThread(CommonDao dao,String devId, String publishUrl, String webServiceUrl,String realPlayUrl){
		this.devId = devId;
		this.publishUrl = publishUrl;
		this.webServiceUrl = webServiceUrl;
		this.dao = dao;
		this.realPlayUrl = realPlayUrl;
	}

	@Override
	public void run() {
		System.out.println("进入转码服务开始线程!");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<TblDev> dList = dao.getResultList(TblDev.class," o.devNo=?", null, devId);
		if(dList != null && dList.size() >0){
			TblDev dev = dList.get(0);
			//转码服务最终地址
			String desUrl = realPlayUrl+Base64Util.encode((dev.getDevNo()+dev.getDevKey()+System.currentTimeMillis()))+".sdp";
			StartTranscodingReq req = new StartTranscodingReq();
			req.setDevId(devId);
			req.setSourceUrl(publishUrl);
			req.setSourceProto(0);//视频源协议,0--rtsp,1--rtmp,2--http
			req.setSourceTransMode(0);//Rtsp视频源rtp传输模式,0--udp,1--tcp
			req.setDestProto(0);//推流协议类型,0--rtsp,1--rtmp
			req.setDestUrl(desUrl);
			req.setRetryInterval(30);//视频源请求重试间隔时间,默认30,单位秒
			req.setRetryNum(5);//视频源请求重试次数,默认5
			req.setTransType(1);//转码类型,0--不转码,仅拉流分发;1--转码为CIF,15帧率,128kbps;2--转码为D1,15帧率,256kbps;3--转码为720p,,15帧率,512kbps
			VauServicesPortType portType = (VauServicesPortType)PubWebService.getTransCodeService(VauServicesPortType.class, webServiceUrl);
			StartTranscodingRes res = portType.startTranscoding(req);
			if(res.getResult() ==0){
				System.out.println("***转码服务地址：***"+desUrl);
				dev.setSubPublishUrl(desUrl);
			}
			dao.update(dev);
		}
	}
	
}
