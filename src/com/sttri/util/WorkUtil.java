package com.sttri.util;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.sttri.pojo.MediaServer;
import com.sttri.pojo.TblDev;
import com.sttri.pojo.TblUser;
import com.sttri.service.IDevService;
import com.sttri.service.IMediaServerService;
import com.sttri.service.IUserService;

public class WorkUtil {
	
	
	public static JSONObject checkUser(IUserService userService, String account, String pwd){
		JSONObject obj = new JSONObject();
		obj.put("code", -1);
		obj.put("desc", "");
		if(Util.isEmpty(account)){
			obj.put("code", 1);
			obj.put("desc", "帐号为空!");
			return obj;
		}
		if(Util.isEmpty(pwd)){
			obj.put("code", 2);
			obj.put("desc", "密码为空!");
			return obj;
		}
		
		List<TblUser> list = userService.getResultList("o.account=?", null, new Object[]{account});
		if(Util.isNull(list)||list.size()==0){
			obj.put("code", 3);
			obj.put("desc", "没有当前帐号!");
			return obj;
		}
		
		TblUser user = list.get(0);
		if (("".equals(user.getPwd()) || user.getPwd() == null ) && user.getLoginTimes() == 0) {
			obj.put("code", 5);
			obj.put("desc", "该账号是首次登录，请先设置密码!");
			return obj;
		}
		if(!user.getPwd().equals(pwd)){
			obj.put("code", 4);
			obj.put("desc", "密码错误!");
			return obj;
		}
		obj.put("code", 0);
		obj.put("desc", "登录成功!");
		obj.put("user", user);
		return obj;
	}
	
	public static JSONObject checkDev(IDevService devService, String devNo, String devKey){
		JSONObject obj = new JSONObject();
		obj.put("code", -1);
		obj.put("desc", "");
		if(Util.isEmpty(devNo)){
			obj.put("code", 1);
			obj.put("desc", "设备号为空!");
			return obj;
		}
		if(Util.isEmpty(devKey)){
			obj.put("code", 2);
			obj.put("desc", "设备密码为空!");
			return obj;
		}
		List<TblDev> list = devService.getResultList("o.devNo=?", null, new Object[]{devNo});
		if(Util.isNull(list)||list.size()==0){
			obj.put("code", 3);
			obj.put("desc", "没有找到当前设备!");
			return obj;
		}
		
		TblDev dev = list.get(0);
		if (("".equals(dev.getDevKey()) || dev.getDevKey() == null) && dev.getLoginTimes() ==0) {
			obj.put("code", 7);
			obj.put("desc", "该账号是首次登录，请先设置密码!");
			return obj;
		}
		if(!dev.getDevKey().equals(pwdEncrypt(devKey))){
			obj.put("code", 4);
			obj.put("desc", "设备密码错误!");
			return obj;
		}
		if(dev.getIsAble()==1){
			obj.put("code", 5);
			obj.put("desc", "设备未启用!");
			return obj;
		}
		
		obj.put("code", 0);
		obj.put("desc", "");
		obj.put("dev", dev);
		return obj;
	}
	
	/**
	 * 
	 * @param mediaServerService
	 * @param dev
	 * @param protocol:rtsp/rtmp,服务器类型默认为rtsp,protocol=rtmp则serviceType=1反之serviceType=0
	 * @return
	 */
	public static MediaServer currDevMediaServer(IMediaServerService mediaServerService, TblDev dev,String protocol){
		int serviceType =0;
		if("rtmp".equals(protocol)){
			serviceType =1;
		}
		MediaServer mediaServer = null;
		/*
		 * 注释日期：2017-3-15 
		 * 原因：由服务器在线数排序改为设备表在线设备所在的服务器个数排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("devNum", "asc");
		//onLine:服务器是否在线 0：离线 1：在线
		List<MediaServer> list = mediaServerService.getResultList("o.company.id=? and o.serviceType =? and o.onLine=? ", orderby, new Object[]{dev.getCompany().getId(),serviceType,1});
		if(list!=null&&list.size()>0){
			mediaServer = list.get(0);
		}else{
			list = mediaServerService.getResultList("1=1 and o.serviceType=? and o.onLine=? ", orderby,new Object[]{serviceType,1});
			if(list!=null&&list.size()>0){
				for(MediaServer ms:list){
					if(ms.getCompany()==null){
						mediaServer = ms;
						break;
					}
				}
			}
		}*/
		
		/*
		 * 注释日期：2017-5-22 
		 * 原因：在获取所有流媒体服务器的时候，就进行socket验证该服务器是否通，直接返回正常的服务器对象
		 * 
		//服务器为某企业私有
		String sql = "select ms.* from media_server ms where ms.onLine=1 and ms.serviceType="+serviceType+" and ms.comId='"+dev.getCompany().getId()+"' order by (select count(*) from tbl_dev d where d.onLines=0 and ms.id=d.serverId) asc";
		List<MediaServer> list = mediaServerService.getResultList(sql);
		if (list != null && list.size() >0) {
			mediaServer = list.get(0);
		}else {
			//服务器为所有企业共有
			String sql2 = "select ms.* from media_server ms where ms.onLine=1 and ms.ServiceType="+serviceType+" order by (select count(*) from tbl_dev d where d.onlines=0 and ms.id=d.serverId ) asc";
			list = mediaServerService.getResultList(sql2);
			if(list != null && list.size() > 0){
				for(MediaServer ms:list){
					if(ms.getCompany()==null){
						mediaServer = ms;
						break;
					}
				}
			}
		}
		*/
		
		//服务器为某企业私有
		String sql = "select ms.* from media_server ms where ms.onLine=1 and ms.serviceType="+serviceType+" and ms.comId='"+dev.getCompany().getId()+"' order by (select count(*) from tbl_dev d where d.onLines=0 and ms.id=d.serverId) asc";
		List<MediaServer> list = mediaServerService.getResultList(sql);
		if (list != null && list.size() >0) {
			mediaServer = getMediaServerByList(list,1,serviceType);
		}else {
			//服务器为所有企业共有
			String sql2 = "select ms.* from media_server ms where ms.onLine=1 and ms.ServiceType="+serviceType+" order by (select count(*) from tbl_dev d where d.onlines=0 and ms.id=d.serverId ) asc";
			list = mediaServerService.getResultList(sql2);
			if(list != null && list.size() > 0){
				mediaServer = getMediaServerByList(list,0,serviceType);
			}
		}
		return mediaServer;
	}
	
	//获取正常的服务器对象  flag表示服务器是否为某企业私有  flag=0:服务器为所有企业共有 flag=1：服务器为某企业私有
	public static MediaServer getMediaServerByList(List<MediaServer> list,int flag,int serviceType){
		MediaServer mediaServer = null;
		boolean serverFlag = false;
		for (MediaServer ms : list) {
			if (flag == 0) {
				if(ms.getCompany()==null){
					serverFlag = isNormalServer(ms,serviceType);
					if (serverFlag) {
						mediaServer = ms;
						break;
					}
				}
			}else {
				serverFlag = isNormalServer(ms,serviceType);
				if (serverFlag) {
					mediaServer = ms;
					break;
				}
			}
		}
		return mediaServer;
	}
	
	//验证服务器是否正常,serviceType表示服务器类型 0：rtsp服务器 ，1：rtmp服务器。只验证rtsp服务器
	public static boolean isNormalServer(MediaServer ms,int serviceType){
		boolean serverFlag = false;
		try {
			if (serviceType == 0) {
				String playUrl = ms.getRecordPlayUrl();
				URL url = new URL(playUrl.replace("rtmp", "http").replace("rtsp", "http"));
				String ip = url.getHost();
				int port = url.getPort();
				serverFlag = SocketUtils.getSocket(ip, port);
			}else {
				serverFlag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverFlag;
	}
	
	public static String pwdEncrypt(String pwd){
		return MD5Util.MD5Code(pwd);
	}
	
	
	public static boolean isTimeout(String d1, String d2, int tt){
		boolean flag = false;
		if(Util.dealNull(d1).equals("")){
			return flag;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟   
		java.util.Date date = null;
		long dd1 = 0;
		try {
			date=sdf.parse(d1);
			dd1 = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long dd2 = 0;
		try {
			date=sdf.parse(d2);
			dd2 = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(dd2-dd1);
		if((dd2-dd1)/1000>=tt){
			flag = true;
		}
		return flag;
	}
	
	//根据用户账号获取用户对象
	public static TblUser getCurrentUser (IUserService userService, String account){
		List<TblUser> list = userService.getResultList("o.account=?", null, new Object[]{account});
		return list.get(0);
	}
	
	//根据设备编号获取设备对象
	public static TblDev getCurrentDev (IDevService devService, String devNo){
		List<TblDev> list = devService.getResultList("o.devNo=?", null, new Object[]{devNo});
		return list.get(0);
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(isTimeout("2015-07-06 08:20:12", "2015-07-06 09:20:11", 3600));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.parse("2015-07-06 08:20:12").getTime());
		System.out.println(sdf.parse("2015-07-06 09:20:12").getTime());
		System.out.println(sdf.parse("2015-07-06 09:20:12").getTime()-sdf.parse("2015-07-06 08:20:12").getTime());
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(1437554188*1000);
		System.out.println(sdf.format(cd.getTime()));
		
		 Calendar cal = Calendar.getInstance();
	        cal.set(1970, 0, 01);
	     
	     System.out.println(sdf.format(new Date(1437554188000L)));
	}
}
