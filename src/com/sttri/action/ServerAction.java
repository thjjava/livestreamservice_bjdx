package com.sttri.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;








import com.sttri.pojo.CompanyGroup;
import com.sttri.pojo.DevComment;
import com.sttri.pojo.DevGood;
import com.sttri.pojo.DevLog;
import com.sttri.pojo.DevRecord;
import com.sttri.pojo.DevRecordFile;
import com.sttri.pojo.DevView;
import com.sttri.pojo.MediaServer;
import com.sttri.pojo.RoleMenus;
import com.sttri.pojo.SensitiveWord;
import com.sttri.pojo.TblBlack;
import com.sttri.pojo.TblControl;
import com.sttri.pojo.TblDev;
import com.sttri.pojo.TblMobile;
import com.sttri.pojo.TblProblem;
import com.sttri.pojo.TblUser;
import com.sttri.pojo.UserGroup;
import com.sttri.pojo.UserPolling;
import com.sttri.pojo.UserRole;
import com.sttri.service.IBlackService;
import com.sttri.service.ICompanyGroupService;
import com.sttri.service.IControlService;
import com.sttri.service.IDevCommentService;
import com.sttri.service.IDevGoodService;
import com.sttri.service.IDevLogService;
import com.sttri.service.IDevRecordFileService;
import com.sttri.service.IDevRecordService;
import com.sttri.service.IDevService;
import com.sttri.service.IDevViewService;
import com.sttri.service.IMediaServerService;
import com.sttri.service.IMobileService;
import com.sttri.service.IProblemService;
import com.sttri.service.IRoleMenusService;
import com.sttri.service.ISensitiveWordService;
import com.sttri.service.ITblIPService;
import com.sttri.service.IUserGroupService;
import com.sttri.service.IUserPollingService;
import com.sttri.service.IUserRoleService;
import com.sttri.service.IUserService;
import com.sttri.thread.IPAddressThread;
import com.sttri.util.Base64Util;
import com.sttri.util.Constant;
import com.sttri.util.JsonUtil;
import com.sttri.util.MD5Util;
import com.sttri.util.SensitiveWordUtil;
import com.sttri.util.Util;
import com.sttri.util.WorkUtil;

public class ServerAction extends BaseAction {
	private static final Logger LOG = Logger.getLogger(ServerAction.class.getName());
	private static final long serialVersionUID = 1L;
	private static final int ERRORTIMES = 3;
	
	@Autowired
	private IDevService devService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDevRecordService devRecordService;
	@Autowired
	private IDevRecordFileService devRecordFileService;
	@Autowired
	private IControlService controlService;
	@Autowired
	private IMediaServerService mediaServerService;
	@Autowired
	private IUserGroupService userGroupService;
	@Autowired
	private ICompanyGroupService groupService;
	@Autowired
	private IUserPollingService userPollingService;
	@Autowired
	private IMobileService mobileService;
	@Autowired
	private IDevLogService devLogService;
	@Autowired
	private IProblemService problemService;
	@Autowired
	private ITblIPService ipService;
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IRoleMenusService roleMenusService;
	@Autowired
	private IDevGoodService devGoodService;
	@Autowired
	private IDevViewService devViewService;
	@Autowired
	private IDevCommentService devCommentService;
	@Autowired
	private IBlackService blackService;
	@Autowired
	private ISensitiveWordService wordService;
	
	/**
	 * pc客户端
	 * @return
	 */
	@ResponseBody
	public void login(){
		LOG.info("Executing operation login");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String sourceType = Util.dealNull(request.getParameter("sourceType"));
		String conType = Util.dealNull(request.getParameter("conType"));
		String conVer = Util.dealNull(request.getParameter("conVer"));
		try {
			JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
			if(obj.optInt("code", -1)!=0 && obj.optInt("code", -1)!=4){
				obj.put("upgradeStatus", 0);
				obj.put("downUrl", "");
				JsonUtil.jsonString(response, obj.toString());
				return;
			}
			TblUser currentUser = WorkUtil.getCurrentUser(userService, account);
			int hasErrorLogin = currentUser.getErrorLoginTimes();
			String hasErrorLoginTime = currentUser.getErrorLoginTime();
			String now = Util.dateToStr(new Date());
			if (!"".equals(hasErrorLoginTime) && hasErrorLoginTime != null) {
				int dateDiff = (int)Util.datediff(hasErrorLoginTime, now, "yyyy-MM-dd HH:mm:ss");
				if (dateDiff <= 10*60*1000 && hasErrorLogin >= ERRORTIMES) {
					obj.put("code", 9);
					obj.put("desc", "登录失败的次数已达上限,请10分钟后再登录！");
					JsonUtil.jsonString(response, obj.toString());
					return;
				}
			}
			if (hasErrorLogin < ERRORTIMES) {
				if(obj.optInt("code", -1) == 4){
					if (hasErrorLogin == 0) {
						currentUser.setErrorLoginTime(Util.dateToStr(new Date()));
					}
					hasErrorLogin +=1;
					int lastLoginTimes = ERRORTIMES-hasErrorLogin;
					currentUser.setErrorLoginTimes(hasErrorLogin);
					this.userService.update(currentUser);
					obj.put("code", 8);
					obj.put("desc", "密码不正确,剩余登录次数还有"+lastLoginTimes+"次！");
					JsonUtil.jsonString(response, obj.toString());
					return;
				}else{
					currentUser.setErrorLoginTimes(0);
					currentUser.setErrorLoginTime("");
					this.userService.update(currentUser);
				}
			}else {
				currentUser.setErrorLoginTimes(0);
				currentUser.setErrorLoginTime("");
				this.userService.update(currentUser);
			}
			
			//获取该用户的权限
			TblUser user = (TblUser) JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
			obj.remove("user");
			obj.put("expiredFlag", "0");
			String modifyPwdTime = user.getModifyPwdTime();
			int daysOfTwo = Long.valueOf(Util.datediff(modifyPwdTime, Util.dateToStr(new Date()), "yyyy-MM-dd HH:mm:ss")/(1000*3600*24)).intValue();
			if (daysOfTwo >= 90) {
				obj.put("expiredFlag", "1");//提示客户端需要修改密码了
			}
			int loginTimes = user.getLoginTimes();
			loginTimes +=1;
			user.setLoginTimes(loginTimes);
			this.userService.update(user);
			if(Util.notEmpty(sourceType)&&Util.notEmpty(conType)&&Util.notEmpty(conVer)){
				TblControl control = this.controlService.checkVer(Integer.valueOf(sourceType), Integer.valueOf(conType), conVer);
				if(control!=null){
					obj.put("upgradeStatus", control.getUpgradeStatus());
					obj.put("downUrl", Constant.readKey("appDownUrl")+control.getConPath());
				}else{
					obj.put("upgradeStatus", 0);
					obj.put("downUrl", "");
				}
			}
			String userId = user.getId();
			List<UserRole> uRoles = this.userRoleService.getResultList(" o.user.id=?", null, new Object[]{userId});
			if (uRoles != null && uRoles.size()>0) {
				List<RoleMenus> roleMenus = this.roleMenusService.getResultList(" o.role.id=?", null, new Object[]{uRoles.get(0).getRole().getId()});
				for (RoleMenus menus : roleMenus) {
					if("下载".equals(menus.getMenus().getName())){
						obj.put("downPermission", "1");//有权限
					}else if ("删除".equals(menus.getMenus().getName())) {
						obj.put("delPermission", "1");
					}
				}
			}
			JsonUtil.jsonString(response, obj.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 手机客户端登录
	 * @return
	 */
	public void devLogin(){
		LOG.info("Executing operation devLogin");
		try {
			JSONObject param = (JSONObject)new XMLSerializer().readFromStream(request.getInputStream());
			System.out.println("devLogin接收参数:"+param.toString());
			JSONObject obj = WorkUtil.checkDev(devService, param.optString("DevID", ""), param.optString("DevKey", ""));
			int upgradeStatus = 0;
			String downUrl = "", newVer = "", logDesc = "", expiredFlag = "0";
			TblDev dev = null;
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("LOGIN_RES");
			doc.setRootElement(root);

			TblDev currentDev = WorkUtil.getCurrentDev(devService, param.optString("DevID", ""));
			int hasErrorLogin = currentDev.getErrorLoginTimes();
			String hasErrorLoginTime = currentDev.getErrorLoginTime();
			String now = Util.dateToStr(new Date());
			if (!"".equals(hasErrorLoginTime) && hasErrorLoginTime != null) {
				int dateDiff = (int)Util.datediff(hasErrorLoginTime, now, "yyyy-MM-dd HH:mm:ss");
				if (dateDiff <= 10*60*1000 && hasErrorLogin >= ERRORTIMES) {
					root.addElement("Result").addText("9");
					root.addElement("desc").addText("登录失败的次数已达上限,请10分钟后再登录！");
					JsonUtil.jsonString(response, doc.asXML());
					return;
				}
			}
			
			if(obj.optInt("code", -1)==0){
				dev = (TblDev) JSONObject.toBean(obj.optJSONObject("dev"), TblDev.class);
				int onLine = dev.getOnLines();//账号在线标识 0：在线； 1：离线
				if (onLine == 0) {
					obj.put("code", 6);
					logDesc = dev.getDevNo()+",登录失败,该账号已登录!";
				}else {
					String modifyPwdTime = dev.getModifyPwdTime();
					if ("".equals(modifyPwdTime)) {
						modifyPwdTime = dev.getAddTime();
					}
					int daysOfTwo = Long.valueOf(Util.datediff(modifyPwdTime, Util.dateToStr(new Date()), "yyyy-MM-dd HH:mm:ss")/(1000*3600*24)).intValue();
					if (daysOfTwo >= 90) {
						expiredFlag = "1";//提示客户端需要修改密码了
					}
					//dev.setOnLines(0);
					dev.setLastLoginTime(Util.dateToStr(new Date()));
					int loginTimes = dev.getLoginTimes();
					loginTimes +=1;
					dev.setLoginTimes(loginTimes);
					this.devService.update(dev);
					
					String sourceType = param.optString("SourceType","");
					String conType = param.optString("ConType", "");
					String conVer = param.optString("ConVer", "");
					if(Util.notEmpty(sourceType)&&Util.notEmpty(conType)&&Util.notEmpty(conVer)){
						TblControl control = controlService.checkVer(Integer.valueOf(sourceType), Integer.valueOf(conType), conVer);
						if(control!=null){
							upgradeStatus = control.getUpgradeStatus();
							downUrl = Constant.readKey("appDownUrl")+control.getConPath();
							newVer = control.getConVer();
						}else{
							control = controlService.checkVer(Integer.valueOf(sourceType), Integer.valueOf(conType), "");
							if(control!=null){
								newVer = control.getConVer();
							}
						}
					}
					
					if(Util.isEmpty(downUrl)){
						logDesc = dev.getDevNo()+",登录成功!";
					}else {
						logDesc = dev.getDevNo()+",登录失败,版本过低,需要升级!";
					}
				}
			}else {
				logDesc = param.optString("DevID", "")+",登录失败,"+obj.optString("desc", "");
				if (hasErrorLogin < ERRORTIMES) {
					if(obj.optInt("code", -1) == 4){
						if (hasErrorLogin == 0) {
							currentDev.setErrorLoginTime(Util.dateToStr(new Date()));
						}
						hasErrorLogin +=1;
						int lastLoginTimes = ERRORTIMES-hasErrorLogin;
						//更新错误登录次数
						currentDev.setErrorLoginTimes(hasErrorLogin);
						this.devService.update(currentDev);
						
						root.addElement("Result").addText("8");
						root.addElement("desc").addText("密码不正确,剩余登录次数还有"+lastLoginTimes+"次！");
						JsonUtil.jsonString(response, doc.asXML());
						return;
					}else{
						//更新错误登录次数
						currentDev.setErrorLoginTimes(0);
						currentDev.setErrorLoginTime("");
						this.devService.update(currentDev);
					}
				}else {
					//更新错误登录次数
					currentDev.setErrorLoginTimes(0);
					currentDev.setErrorLoginTime("");
					this.devService.update(currentDev);
				}
			}
			obj.remove("dev");
			
			//保存登录日志
			String clientIP = getIpAddr(request);
			saveDevLog(dev, clientIP,2, logDesc);
			
			root.addElement("Result").addText(obj.optInt("code", -1)+"");
			root.addElement("UpgradeStatus").addText(upgradeStatus+"");
			root.addElement("NewVer").addText(newVer);
			root.addElement("expiredFlag").addText(expiredFlag);
			if(Util.notEmpty(downUrl))
				root.addElement("DownUrl").addText(downUrl);
			System.out.println("devLogin返回结果:"+doc.asXML());
			JsonUtil.jsonString(response, doc.asXML());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 手机登出
	 * @return
	 */
	public void devLogout(){
		LOG.info("Executing operation devLogout");
		try {
			JSONObject param = (JSONObject)new XMLSerializer().readFromStream(request.getInputStream());
			JSONObject obj = WorkUtil.checkDev(devService, param.optString("DevID", ""), param.optString("DevKey", ""));
			
			if(obj.optInt("code", -1)==0){
				TblDev dev = (TblDev) JSONObject.toBean(obj.optJSONObject("dev"), TblDev.class);
				dev.setOnLines(1);
				devService.update(dev);
			}
			
			obj.remove("dev");
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("LOGOUT_RES");
			doc.setRootElement(root);
			root.addElement("Result").addText(obj.optInt("code", -1)+"");
			System.out.println("devLogout返回结果:"+doc.asXML());
			JsonUtil.jsonString(response, doc.asXML());
			//JsonUtil.jsonBeanToString(response, obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * pc获取设备列表
	 * @return
	 */
	@RequestMapping()
	public String getDevList(){
		LOG.info("Executing operation getDevList");
		System.out.println(request.getMethod());
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code")==0){
			TblUser user = (TblUser)JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
			List<TblDev> list = devService.getResultList("o.company.id=?", null, new Object[]{user.getCompany().getId()});
			if(Util.isNull(list)||list.size()==0){
				JsonUtil.jsonString(response, "[]");
				return null;
			}
			JSONArray array = new JSONArray();
			JSONObject ob = null;
//			String d2 = Util.dateToStr(new Date());
			for(TblDev dev:list){
				ob = new JSONObject();
				/*if(WorkUtil.isTimeout(dev.getLastLoginTime(), d2, 3600)){
					dev.setOnLines(1);
					devService.update(dev);
				}*/
				ob.put("id", dev.getId());
				ob.put("devNo", dev.getDevNo());
				ob.put("name", dev.getDevName());
				ob.put("onLine", dev.getOnLines());
				array.add(ob);
			}
			JsonUtil.jsonString(response, array.toString());
		}else{
			JsonUtil.jsonString(response, "[]");
		}
		return null;
	}
	
	/**
	 * 实时刷新状态
	 * @return
	 */
	public String refreshOnline(){
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		JSONArray array = new JSONArray();
		if(obj.optInt("code")!=0){
			JsonUtil.jsonString(response, array.toString());
		}else{
			String data = Util.dealNull(request.getParameter("data"));
			System.out.println(data);
			array = JSONArray.fromObject(data);
			JSONObject ob = null;
			TblDev dev = null;
//			String d2 = Util.dateToStr(new Date());
			for(int i=0;i<array.size();i++){
				ob = array.getJSONObject(i);
				dev = devService.getById(ob.get("id"));
				/*if(WorkUtil.isTimeout(dev.getLastLoginTime(), d2, 3600)){
					dev.setOnLines(1);
					devService.update(dev);
				}*/
				int online = dev == null?1:dev.getOnLines();
				ob.put("onLine", online);
				array.set(i, ob);
			}
			JsonUtil.jsonString(response, array.toString());
		}
		return null;
	}
	
	/**
	 * 根据用户的组织id，查询该组织所有子节点的id
	 */
	public JSONArray getArray(String id,JSONArray array){
		array.add(id);
		//查询组织表中，该ID的根节点下的所有子节点
		List<CompanyGroup> gList = this.groupService.getResultList(" o.pid=?", null, id);
		if(gList != null && gList.size()>0){
			for (CompanyGroup companyGroup : gList) {
				String gid = companyGroup.getId();
				array.add(gid);
				getArray(gid,array);//递归查询gid该节点的子节点
			}
		}
		return array;
	}
	
	/**
	 * pc获取分组
	 * @return
	 */
	public String group(){
		LOG.info("Executing operation group");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code",-1)!=0){
			JsonUtil.jsonString(response, "[]");
			return null;
		}
		
		TblUser user = (TblUser)JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
		String groupId = Util.dealNull(request.getParameter("groupId"));
		List<Object> param = new ArrayList<Object>();
		String where = "o.company.id=? ";
		param.add(user.getCompany().getId());
		if(groupId.equals("")){
			groupId = "0";
		}
		if(groupId.equals("0")){
			where += "and (o.groupId is null or o.groupId='')";
		}else{
			where += "and o.groupId=?";
			param.add(groupId);
		}
		
		JSONArray array = new JSONArray();
		JSONObject ob = null;
//		String d2 = Util.dateToStr(new Date());
		List<TblDev> devlist = this.devService.getResultList(where, null, param.toArray());
		for(TblDev dev:devlist){
			ob = new JSONObject();
			/*if(WorkUtil.isTimeout(dev.getLastLoginTime(), d2, 3600)){
				dev.setOnLines(1);
				devService.update(dev);
			}*/
			ob.put("id", dev.getId());
			ob.put("name", dev.getDevName());
			ob.put("devNo", dev.getDevNo());
			ob.put("type", 0);
			ob.put("onLine", dev.getOnLines());
			array.add(ob);
		}
		
		JSONArray gArray = null;
		if(groupId.equals("0")){
			//获取节点下所有的设备数和在线设备数
			gArray = new JSONArray();
			gArray = getArray(groupId, gArray);
			String jpqlStr = gArray.toString().replace("[", "(").replace("]", ")").replaceAll("\"", "'");
			List<TblDev> total = this.devService.getResultList(" o.company.id=? and o.groupId in "+jpqlStr, null,new Object[]{ user.getCompany().getId()});
			List<TblDev> onLine = this.devService.getResultList(" o.company.id=? and o.onLines=? and o.groupId in "+jpqlStr, null,new Object[]{ user.getCompany().getId(),0});
			
			List<UserGroup> uglist = this.userGroupService.getResultList("o.user.account=?", null, account);
			for(UserGroup ug:uglist){
				if(ug.getGroup()==null)
					continue;
				ob = new JSONObject();
				ob.put("id", ug.getGroup().getId());
				ob.put("name", ug.getGroup().getGroupName());
				ob.put("type", 1);
				ob.put("total", total.size());
				ob.put("onLineCount", onLine.size());
				array.add(ob);
			}
		}else{
			List<CompanyGroup> gplist = groupService.getResultList("o.pid=?", null, groupId);
			for(CompanyGroup g:gplist){
				ob = new JSONObject();
				ob.put("id", g.getId());
				ob.put("name", g.getGroupName());
				ob.put("type", 1);
				
				//获取节点下所有的设备数和在线设备数
				gArray = new JSONArray();
				gArray = getArray(g.getId(), gArray);
				String jpqlStr = gArray.toString().replace("[", "(").replace("]", ")").replaceAll("\"", "'");
				List<TblDev> total = this.devService.getResultList(" o.company.id=? and o.groupId in "+jpqlStr, null,new Object[]{ user.getCompany().getId()});
				List<TblDev> onLine = this.devService.getResultList(" o.company.id=? and o.onLines=? and o.groupId in "+jpqlStr, null,new Object[]{ user.getCompany().getId(),0});
				
				ob.put("total", total.size());
				ob.put("onLineCount", onLine.size());
				array.add(ob);
			}
		}
		System.out.println(array.toString());
		JsonUtil.jsonString(response, array.toString());
		return null;
	}
	
	/**
	 * pc获取实时播放地址
	 * @return
	 */
	public String getUrl(){
		LOG.info("Executing operation getUrl");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		obj.put("url", "");
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return null;
		}
		obj.remove("user");
		String id = Util.dealNull(request.getParameter("id"));
		TblDev dev = devService.getById(id);
		if(dev==null){
			obj.put("code", 1);
			obj.put("desc", "没有找到当前设备");
			JsonUtil.jsonString(response, obj.toString());
			return null;
		}
		obj.put("url", Util.dealNull(dev.getPublishUrl()));
		obj.put("devName", dev.getDevName());
		obj.put("onLine", dev.getOnLines());
		JsonUtil.jsonString(response, obj.toString());
		return null;
	}
	
	/**
	 * 南京-获取实时播放地址
	 * @return
	 */
	public void getUrl1(){
		LOG.info("Executing operation getUrl1");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		obj.put("url", "");
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		obj.remove("user");
		String id = Util.dealNull(request.getParameter("devNo"));
		List<TblDev> devList = devService.getResultList("o.devNo=?", null, id);
		if(devList == null || devList.size() == 0){
			obj.put("code", 1);
			obj.put("desc", "没有找到当前设备");
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		TblDev dev = devList.get(0);
		obj.put("url", Util.dealNull(dev.getPublishUrl()));
		obj.put("onLine", dev.getOnLines());
		JsonUtil.jsonString(response, obj.toString());
	}
	
	/**
	 * pc获取录像列表
	 * @return
	 */
	public String getRecordList(){
		LOG.info("Executing operation getRecordList");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return null;
		}
		obj.remove("user");
		String devId = Util.dealNull(request.getParameter("devId"));
		String startTime = Util.dealNull(request.getParameter("startTime"));
		String endTime = Util.dealNull(request.getParameter("endTime"));
		List<Object> param = new ArrayList<Object>();
		LinkedHashMap<String,String> orderby = new LinkedHashMap<String,String>();
		orderby.put("recordStartTime", "asc");
		String where = " 1=1 ";
		if(Util.isEmpty(devId)){
			obj.put("code", -1);
			obj.put("desc", "请选择设备!");
			JsonUtil.jsonString(response, obj.toString());
			return null;
		}
		where += "and o.dev.id=? ";
		param.add(devId);
		if(Util.notEmpty(startTime)){
			where += "and o.recordStartTime>=? ";
			param.add(startTime);
		}
		if(Util.notEmpty(endTime)){
			endTime = endTime.substring(0,10)+" 23:59:59";
			where += "and o.recordStartTime<=? ";
			param.add(endTime);
		}
		List<DevRecord> drlist = devRecordService.getResultList(where, orderby, param.toArray());
		JSONArray array = new JSONArray();
		if(drlist==null||drlist.size()==0){
			JsonUtil.jsonString(response, array.toString());
			return null;
		}
		
		JSONObject ob = null;
		List<DevRecordFile> drflist = null;
		MediaServer mediaServer = null;
		for(DevRecord dr:drlist){
			drflist = devRecordFileService.getResultList("o.drId=?", orderby, new Object[]{dr.getId()});
			if(drflist==null || drflist.size()==0)
				continue;
			mediaServer = mediaServerService.getById(dr.getMediaServerId());
			if(mediaServer == null)
				continue;
			for(DevRecordFile drf:drflist){
				if(drf.getRecordSize()<10000)
					continue;
				ob = new JSONObject();
				ob.put("id", drf.getId());
				ob.put("name", drf.getRecordName());
				ob.put("startTime", drf.getRecordStartTime());
				ob.put("endTime", drf.getRecordEndTime());
				ob.put("recordSize", drf.getRecordSize());
				ob.put("playUrl", mediaServer.getRecordPlayUrl()+drf.getRecordName());
				ob.put("downUrl", mediaServer.getRecordDownUrl()+drf.getRecordName());
				array.add(ob);
			}
		}
		
		JsonUtil.jsonString(response, array.toString());
		return null;
	}
	
	/**
	 * 南京
	 * @return
	 */
	public void getRecordList1(){
		LOG.info("Executing operation getRecordList1");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		obj.remove("user");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String startTime = Util.dealNull(request.getParameter("startTime"));
		String endTime = Util.dealNull(request.getParameter("endTime"));
		List<Object> param = new ArrayList<Object>();
		LinkedHashMap<String,String> orderby = new LinkedHashMap<String,String>();
		orderby.put("recordStartTime", "asc");
		String where = " 1=1 ";
		if(Util.isEmpty(devNo)){
			obj.put("code", -1);
			obj.put("desc", "请选择设备!");
			JsonUtil.jsonString(response, obj.toString());
			return ;
		}
		List<TblDev> devList = devService.getResultList("o.devNo=?", null, devNo);
		if(devList==null || devList.size()==0){
			obj.put("code", -1);
			obj.put("desc", "没有找到当前设备!");
			JsonUtil.jsonString(response, obj.toString());
			return ;
		}
		where += "and o.dev.devNo=? ";
		param.add(devNo);
		if(Util.notEmpty(startTime)){
			where += "and o.recordStartTime>=? ";
			param.add(startTime);
		}
		if(Util.notEmpty(endTime)){
			where += "and o.recordStartTime<=? ";
			param.add(endTime);
		}
		List<DevRecord> drlist = devRecordService.getResultList(where, orderby, param.toArray());
		JSONArray array = new JSONArray();
		if(drlist==null||drlist.size()==0){
			JsonUtil.jsonString(response, array.toString());
			return ;
		}
		
		JSONObject ob = null;
		List<DevRecordFile> drflist = null;
		MediaServer mediaServer = null;
		for(DevRecord dr:drlist){
			drflist = devRecordFileService.getResultList("o.drId=?", orderby, new Object[]{dr.getId()});
			if(drflist==null || drflist.size()==0)
				continue;
			mediaServer = mediaServerService.getById(dr.getMediaServerId());
			for(DevRecordFile drf:drflist){
				if(drf.getRecordSize()<10000)
					continue;
				ob = new JSONObject();
				ob.put("id", drf.getId());
				ob.put("name", drf.getRecordName());
				ob.put("startTime", drf.getRecordStartTime());
				ob.put("endTime", drf.getRecordEndTime());
				ob.put("recordSize", drf.getRecordSize());
				ob.put("playUrl", mediaServer.getRecordPlayUrl()+drf.getRecordName());
				ob.put("downUrl", mediaServer.getRecordDownUrl()+drf.getRecordName());
				array.add(ob);
			}
		}
		
		JsonUtil.jsonString(response, array.toString());
	}
	
	/**
	 * 根据录像任务编号recordTaskNo获取录像列表
	 * @return
	 */
	public void getReordListByTaskNo(){
		LOG.info("Executing operation getReordListByTaskNo");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return ;
		}
		obj.remove("user");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String recordTaskNo = Util.dealNull(request.getParameter("recordTaskNo"));
		List<Object> param = new ArrayList<Object>();
		LinkedHashMap<String,String> orderby = new LinkedHashMap<String,String>();
		orderby.put("recordStartTime", "asc");
		String where = " 1=1 ";
		if(Util.isEmpty(devNo)){
			obj.put("code", -1);
			obj.put("desc", "请选择设备!");
			JsonUtil.jsonString(response, obj.toString());
			return ;
		}
		where += "and o.dev.devNo=? ";
		param.add(devNo);
		if(Util.notEmpty(recordTaskNo)){
			where += "and o.id = ? ";
			param.add(recordTaskNo);
		}
		List<DevRecord> drlist = devRecordService.getResultList(where, orderby, param.toArray());
		JSONArray array = new JSONArray();
		if(drlist==null||drlist.size()==0){
			JsonUtil.jsonString(response, array.toString());
			return ;
		}
		
		JSONObject ob = null;
		List<DevRecordFile> drflist = null;
		MediaServer mediaServer = null;
		for(DevRecord dr:drlist){
			drflist = devRecordFileService.getResultList("o.drId=?", orderby, new Object[]{dr.getId()});
			if(drflist==null || drflist.size()==0)
				continue;
			mediaServer = mediaServerService.getById(dr.getMediaServerId());
			if(mediaServer == null)
				continue;
			for(DevRecordFile drf:drflist){
				if(drf.getRecordSize()<10000)
					continue;
				ob = new JSONObject();
				ob.put("id", drf.getId());
				ob.put("name", drf.getRecordName());
				ob.put("startTime", drf.getRecordStartTime());
				ob.put("endTime", drf.getRecordEndTime());
				ob.put("recordSize", drf.getRecordSize());
				ob.put("playUrl", mediaServer.getRecordPlayUrl()+drf.getRecordName());
				ob.put("downUrl", mediaServer.getRecordDownUrl()+drf.getRecordName());
				array.add(ob);
			}
		}
		
		JsonUtil.jsonString(response, array.toString());
	}
	
	/**
	 * pc删除录像
	 * @return
	 */
	public void removeRecord(){
		LOG.info("Executing operation removeRecord");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		//获取该用户的权限
		TblUser user = (TblUser) JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
		obj.remove("user");
		obj.put("code", -1);
		String ids = Util.dealNull(request.getParameter("ids"));
		if(ids.isEmpty()){
			obj.put("desc", "请选择要删除的录像!");
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		String userId = user.getId();
		String logDesc = account+"录像删除成功";;
		List<UserRole> uRoles = this.userRoleService.getResultList(" o.user.id=?", null, new Object[]{userId});
		if (uRoles != null && uRoles.size()>0) {
			List<RoleMenus> roleMenus = this.roleMenusService.getResultList(" o.role.id=?", null, new Object[]{uRoles.get(0).getRole().getId()});
			for (RoleMenus menus : roleMenus) {
				if ("删除".equals(menus.getMenus().getName())) {
					devRecordFileService.deletebyids(ids.split(","));
					obj.put("code", 0);
					obj.put("desc", "删除成功!");
				}
			}
		}else {
			obj.put("code", 5);
			obj.put("desc", "该用户没有删除权限，删除失败!");
			logDesc = account+"该用户没有删除权限，删除失败!";
		}
		saveUserLog(user,logDesc);
		JsonUtil.jsonString(response, obj.toString());
	}
	
	/**
	 * 手机开启直播
	 * @return
	 * @修改日期 2017-7-31 09:30:10
	 * @修改原因：平台新增最大直播并发数，判断当前正在直播数是否达到最大并发数，如果达到就开启直播失败
	 */
	public void recordStart(){
		LOG.info("Executing operation recordStart");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject param = (JSONObject)new XMLSerializer().readFromStream(request.getInputStream());
			System.out.println("recordStart接收参数:"+param.toString());
			JSONObject obj = WorkUtil.checkDev(devService, param.optString("DevID", ""), param.optString("DevKey", ""));
			int result = -1;
			String url = "", recordTaskNo = "",logDesc="";
			if(obj.optInt("code", -1)==0){
				TblDev dev = (TblDev) JSONObject.toBean(obj.optJSONObject("dev"), TblDev.class);
				//获取当前正在直播的设备数
				List<TblDev> onLineDevs = this.devService.getResultList(" o.onLines=?", null, new Object[]{0});
				String maxDevs = Constant.readKey("maxDevs");//获取平台直播设备最大并发数
				if (onLineDevs.size() > Integer.parseInt(maxDevs)) {
					result =2;
					logDesc=dev.getDevNo()+",开始直播失败!当前直播的并发数已达上限!";
				}else {
					dev.setImsi(obj.optString("Imsi", ""));
					String protocol = param.optString("Protocol","");//请求的协议类型
					JSONObject ob = this.devService.videoStart(dev,protocol);
					if(ob.optBoolean("flag", false)){
						result = 0;
						url = ob.optString("url", "");
					}
					recordTaskNo = ob.optString("recordTaskNo","");
					logDesc=dev.getDevNo()+",开始直播失败!";
				}
				//保持日志
				String clientIP = getIpAddr(request);
				saveDevLog(dev, clientIP,0, logDesc);//保存日志
			}
			
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("STARTUPLIVE_RES");
			doc.setRootElement(root);
			root.addElement("Result").addText(result+"");
			root.addElement("RecordTaskNo").addText(recordTaskNo);
			root.addElement("PublishUrl").addText(url);
			root.addElement("AudioRtpPort").addText("0");
			root.addElement("AudioRtcpPort").addText("0");
			root.addElement("VideoRtpPort").addText("0");
			root.addElement("VideoRtcpPort").addText("0");
			System.out.println("recordStart返回结果:"+doc.asXML());
			JsonUtil.jsonString(response, doc.asXML());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手机结束直播
	 * @return
	 */
	public void recordEnd(){
		LOG.info("Executing operation recordEnd");
		response.setCharacterEncoding("UTF-8");
		try {
			JSONObject param = (JSONObject) new XMLSerializer().readFromStream(request.getInputStream());
			System.out.println("recordEnd接收参数:" + param.toString());
			JSONObject obj = WorkUtil.checkDev(devService, param.optString("DevID", ""),param.optString("DevKey", ""));
			String recordTaskNo = param.optString("RecordTaskNo", "");
			int result = -1;
			String logDesc = "";
			if (obj.optInt("code", -1) == 0) {
				TblDev dev = (TblDev) JSONObject.toBean(obj.optJSONObject("dev"), TblDev.class);
				if (dev.getOnLines() == 0) {
					boolean flag = this.devService.videoEnd(dev, recordTaskNo);
					if (flag) {
						result = 0;
						logDesc = dev.getDevNo() + ",停止直播!";
					}
				} else {
					result = 0;
					logDesc = dev.getDevNo() + ",设备已经离线,停止直播!";
				}

				//保存日志
				String clientIP = getIpAddr(this.request);
				saveDevLog(dev, clientIP, 1, logDesc);
			}

			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("STOPLIVE_RES");
			doc.setRootElement(root);
			root.addElement("Result").addText(result + "");
			System.out.println("recordEnd返回结果:" + doc.asXML());
			JsonUtil.jsonString(response, doc.asXML());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取HLS播放地址
	 * @return 0:成功 1：没有找到设备 2:该设备所属企业不支持HLS直播
	 */
	public String getHlsUrl(){
		LOG.info("Executing operation getHlsUrl");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String devNo = Util.dealNull(request.getParameter("devNo"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		obj.put("hlsurl", "");
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return null;
		}
		obj.remove("user");
		List<TblDev> dev = this.devService.getResultList(" o.devNo =? ", null, devNo);
		if(dev==null || dev.size() ==0){
			obj.put("code", 1);
			obj.put("desc", "没有找到当前设备");
			JsonUtil.jsonString(response, obj.toString());
			return null;
		}
		if(dev.get(0).getCompany().getHlsLiveFlag()==0){//判断该设备所属的企业是否支持HLS直播
			obj.put("code", 2);
			obj.put("desc", "该设备不支持HLS直播!");
			obj.put("onLine", Util.dealNull(dev.get(0).getOnLines()));//是否在线(0-在线/1-下线)
			obj.put("hlsurl", "");
			JsonUtil.jsonString(response, obj.toString());
			return null;
		}
		obj.put("code", 0);
		obj.put("desc", "获取HLS播放地址成功!");
		obj.put("onLine", Util.dealNull(dev.get(0).getOnLines()));//是否在线(0-在线/1-下线)
		obj.put("hlsurl", Util.dealNull(dev.get(0).getHlsUrl()));
		JsonUtil.jsonString(response, obj.toString());
		return null;
	}
	
	/**
	 * 今麦郎项目定制接口
	 * 获取该设备用户的所有领导的设备编号
	 * @return
	 */
	public void getLeaderDevList() {
		LOG.info("Executing operation getLeaderDevList");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String devKey = Util.dealNull(request.getParameter("pwd"));
		String sourceType = Util.dealNull(request.getParameter("sourceType"));
		String conType = Util.dealNull(request.getParameter("conType"));
		String conVer = Util.dealNull(request.getParameter("conVer"));
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("result", -1);
		object.put("msg", "");
		object.put("devList", null);
		if(Util.isEmpty(devNo)){
			object.put("result", 1);
			object.put("msg", "设备号为空!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		if(Util.isEmpty(devKey)){
			object.put("result", 2);
			object.put("msg", "设备密码为空!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		List<TblDev> list = devService.getResultList("o.devNo=?", null, new Object[]{devNo});
		if(Util.isNull(list)||list.size()==0){
			object.put("result", 3);
			object.put("msg", "没有找到当前设备!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		TblDev dev = list.get(0);
		if(dev.getIsAble()==1){
			object.put("result", 5);
			object.put("msg", "设备未启用!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		if(!dev.getDevKey().equals(Base64Util.encode(devKey))){
			object.put("result", 4);
			object.put("msg", "设备密码错误!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		if(Util.notEmpty(sourceType)&&Util.notEmpty(conType)&&Util.notEmpty(conVer)){
			TblControl control = controlService.checkVer(Integer.valueOf(sourceType), Integer.valueOf(conType), conVer);
			if(control!=null){
				object.put("result", 5);
				object.put("msg", "有新版本需要升级!");
				object.put("upgradeStatus", control.getUpgradeStatus());
				object.put("downUrl", Constant.readKey("appDownUrl")+control.getConPath());
				JsonUtil.jsonString(response, object.toString());
				return;
			}
		}
		
		String comId = dev.getCompany().getId();
		String curGroupId = dev.getGroupId();
		String url = dev.getSubPublishUrl();
		if("".equals(url) || url == null){
			url = dev.getPublishUrl();
		}
		List<CompanyGroup> cgList = this.groupService.getResultList(" o.id=?", null, curGroupId);
		if(cgList != null && cgList.size()>0){
			CompanyGroup companyGroup = cgList.get(0);//设备所在组织
			String pId = companyGroup.getPid();//设备所在组织的父ID
			JSONObject ob = null;
			if("0".equals(pId)){
				ob = new JSONObject();
				ob.put("leaderDevNo", dev.getDevNo());
				ob.put("name", dev.getDevName());
				ob.put("mainFalg", "0");
				ob.put("onLine", dev.getOnLines());
				ob.put("url", url==null?"":url);//url==null JSONObject在调用put方法的时候会把该key参数删掉
				ob.put("fullFlag", dev.getFullFlag()==null?0:dev.getFullFlag());
				array.add(ob);
			}else {
				array = getArray(pId, comId,array);
				if (array == null || array.size() <= 0) {
					ob = new JSONObject();
					ob.put("leaderDevNo", dev.getDevNo());
					ob.put("name", dev.getDevName());
					ob.put("mainFalg", "0");
					ob.put("onLine", dev.getOnLines());
					ob.put("url", url==null?"":url);//url==null JSONObject在调用put方法的时候会把该key参数删掉
					ob.put("fullFlag", dev.getFullFlag()==null?0:dev.getFullFlag());
					array.add(ob);
				}
			}
			object.put("result", "0");
			object.put("msg", "获取设备列表成功");
			object.put("devList", array.toString());
			System.out.println(object.toString());
			JsonUtil.jsonString(response, object.toString());
		}else {
			object.put("result", "-1");
			object.put("msg", "获取设备列表失败");
			object.put("devList", array.toString());
			JsonUtil.jsonString(response, object.toString());
		}
		//保存日志
		String clientIP = getIpAddr(request);
		saveDevLog(dev, clientIP, 5, devNo+",登录远程客户端成功!");
	}
	
	//远程客户端登录后，每隔15秒刷新设备列表
	public void refreshLeaderDevList() {
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String devKey = Util.dealNull(request.getParameter("pwd"));
		String sourceType = Util.dealNull(request.getParameter("sourceType"));
		String conType = Util.dealNull(request.getParameter("conType"));
		String conVer = Util.dealNull(request.getParameter("conVer"));
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("result", -1);
		object.put("msg", "");
		object.put("devList", null);
		if(Util.isEmpty(devNo)){
			object.put("result", 1);
			object.put("msg", "设备号为空!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		if(Util.isEmpty(devKey)){
			object.put("result", 2);
			object.put("msg", "设备密码为空!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		List<TblDev> list = devService.getResultList("o.devNo=?", null, new Object[]{devNo});
		if(Util.isNull(list)||list.size()==0){
			object.put("result", 3);
			object.put("msg", "没有找到当前设备!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		TblDev dev = list.get(0);
		if(dev.getIsAble()==1){
			object.put("result", 5);
			object.put("msg", "设备未启用!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		if(!dev.getDevKey().equals(Base64Util.encode(devKey))){
			object.put("result", 4);
			object.put("msg", "设备密码错误!");
			JsonUtil.jsonString(response, object.toString());
			return;
		}
		if(Util.notEmpty(sourceType)&&Util.notEmpty(conType)&&Util.notEmpty(conVer)){
			TblControl control = controlService.checkVer(Integer.valueOf(sourceType), Integer.valueOf(conType), conVer);
			if(control!=null){
				object.put("result", 5);
				object.put("msg", "有新版本需要升级!");
				object.put("upgradeStatus", control.getUpgradeStatus());
				object.put("downUrl", Constant.readKey("appDownUrl")+control.getConPath());
				JsonUtil.jsonString(response, object.toString());
				return;
			}
		}
		
		String comId = dev.getCompany().getId();
		String curGroupId = dev.getGroupId();
		String url = dev.getSubPublishUrl();
		if("".equals(url) || url == null){
			url = dev.getPublishUrl();
		}
		List<CompanyGroup> cgList = this.groupService.getResultList(" o.id=?", null, curGroupId);
		if(cgList != null && cgList.size()>0){
			CompanyGroup companyGroup = cgList.get(0);//设备所在组织
			String pId = companyGroup.getPid();//设备所在组织的父ID
			JSONObject ob = null;
			if("0".equals(pId)){
				ob = new JSONObject();
				ob.put("leaderDevNo", dev.getDevNo());
				ob.put("name", dev.getDevName());
				ob.put("mainFalg", "0");
				ob.put("onLine", dev.getOnLines());
				ob.put("url", url==null?"":url);//url==null JSONObject在调用put方法的时候会把该key参数删掉
				ob.put("fullFlag", dev.getFullFlag()==null?0:dev.getFullFlag());
				array.add(ob);
			}else {
				array = getArray(pId, comId,array);
				if (array == null || array.size() <= 0) {
					ob = new JSONObject();
					ob.put("leaderDevNo", dev.getDevNo());
					ob.put("name", dev.getDevName());
					ob.put("mainFalg", "0");
					ob.put("onLine", dev.getOnLines());
					ob.put("url", url==null?"":url);//url==null JSONObject在调用put方法的时候会把该key参数删掉
					ob.put("fullFlag", dev.getFullFlag()==null?0:dev.getFullFlag());
					array.add(ob);
				}
			}
			object.put("result", "0");
			object.put("msg", "获取设备列表成功");
			object.put("devList", array.toString());
			System.out.println(object.toString());
			JsonUtil.jsonString(response, object.toString());
		}else {
			object.put("result", "-1");
			object.put("msg", "获取设备列表失败");
			object.put("devList", array.toString());
			JsonUtil.jsonString(response, object.toString());
		}
	}
	
	public JSONArray getArray(String id,String comId,JSONArray array){
		JSONObject ob = null;
		List<CompanyGroup> gList = this.groupService.getResultList(" o.id=?", null, id);
		if(gList != null && gList.size()>0){
			CompanyGroup pGroup = gList.get(0);
			String pgId = pGroup.getPid();
			List<TblDev> list = this.devService.getResultList("o.company.id=? and o.groupId=?", null, new Object[]{comId,id});
			for(TblDev tblDev:list){
				String url = tblDev.getSubPublishUrl();
				if("".equals(url) || url == null){
					url = tblDev.getPublishUrl();
				}
				ob = new JSONObject();
				ob.put("leaderDevNo", tblDev.getDevNo());
				ob.put("name", tblDev.getDevName());
				ob.put("mainFalg", "0");
				ob.put("onLine", tblDev.getOnLines());
				ob.put("url", url==null?"":url);//url==null JSONObject在调用put方法的时候会把该key参数删掉
				ob.put("fullFlag", tblDev.getFullFlag()==null?0:tblDev.getFullFlag());
				array.add(ob);
			}
			if(!"0".equals(pgId)){//判断当前设备所在组织不是根组织
				getArray(pgId, comId,array);
			}
		}
		return array;
	}
	
	/**
	 * 今麦郎项目定制接口
	 * 设置正在直播的设备在PC端的窗口中是否全屏显示
	 */
	public void setFull(){
		LOG.info("Executing operation setFull");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String devKey = Util.dealNull(request.getParameter("pwd"));
		try {
			JSONObject ob = new JSONObject();
			ob.put("code", 1);
			ob.put("desc", "设置失败!");
			List<TblDev> list = this.devService.getResultList("o.devNo=? and o.devKey=?", null, new Object[]{devNo,devKey});
			if (list!=null && list.size()>0) {
				TblDev dev = list.get(0);
				dev.setFullFlag(1);//窗口全屏标志 0-不全屏 1-全屏
				this.devService.update(dev);
				ob.put("code", 0);
				ob.put("desc", "设置成功!");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 今麦郎项目定制接口
	 * 设置正在直播的设备在PC端的窗口中退出全屏显示
	 */
	public void closeFull(){
		LOG.info("Executing operation closeFull");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String devKey = Util.dealNull(request.getParameter("pwd"));
		try {
			JSONObject ob = new JSONObject();
			ob.put("code", 1);
			ob.put("desc", "设置失败!");
			List<TblDev> list = this.devService.getResultList("o.devNo=? and o.devKey=?", null, new Object[]{devNo,devKey});
			if (list!=null && list.size()>0) {
				TblDev dev = list.get(0);
				dev.setFullFlag(0);//窗口全屏标志 0-不全屏 1-全屏
				this.devService.update(dev);
				ob.put("code", 0);
				ob.put("desc", "设置成功!");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 今麦郎项目定制接口
	 * 获取轮询策略
	 */
	public void getPollingList(){
		LOG.info("Executing operation getPollingList");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		try {
			JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
			if(obj.optInt("code")==0){
				TblUser user = (TblUser)JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
				List<UserPolling> list = this.userPollingService.getResultList(" o.user.id=?", null, new Object[]{user.getId()});
				if(Util.isNull(list)||list.size()==0){
					JsonUtil.jsonString(response, "[]");
					return;
				}
				JSONArray array = new JSONArray();
				JSONObject ob = null;
				for(UserPolling userPolling:list){
					ob = new JSONObject();
					ob.put("id", userPolling.getId());
					ob.put("pollingName", userPolling.getPollingName());
					ob.put("timeLen", userPolling.getTimeLen());
					ob.put("addTime", userPolling.getAddTime());
					ob.put("devList", userPolling.getDevList());
					array.add(ob);
				}
				JsonUtil.jsonString(response, array.toString());
			}else{
				JsonUtil.jsonString(response, "[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 今麦郎项目定制接口
	 * 添加轮询策略
	 */
	public void addPolling(){
		LOG.info("Executing operation addPolling");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String pollingName = Util.dealNull(request.getParameter("pollingName"));
		String timeLen = Util.dealNull(request.getParameter("timeLen"));
		String devList = Util.dealNull(request.getParameter("devList"));
		JSONObject ob = new JSONObject();
		try {
			JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
			if(obj.optInt("code")==0){
				TblUser user = (TblUser)JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
				UserPolling userPolling = new UserPolling();
				String pollingId = Util.getUUID(6);
				userPolling.setId(pollingId);
				userPolling.setPollingName(pollingName);
				userPolling.setUser(user);
				userPolling.setTimeLen(Integer.parseInt(timeLen));
				userPolling.setDevList(devList);
				userPolling.setAddTime(Util.dateToStr(new Date()));
				this.userPollingService.save(userPolling);
				ob.put("code", 0);
				ob.put("desc", "添加成功!");
				ob.put("pollingId", pollingId);
			}else{
				ob.put("code", 1);
				ob.put("desc", "该用户不存在!");
				ob.put("pollingId", "");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 今麦郎项目定制接口
	 * 删除轮询策略
	 */
	public void removePolling(){
		LOG.info("Executing operation removePolling");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String pollingId = Util.dealNull(request.getParameter("pollingId"));
		JSONObject ob = new JSONObject();
		try {
			JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
			if(obj.optInt("code", -2)!=0){
				JsonUtil.jsonString(response, obj.toString());
				return;
			}
			if(pollingId.isEmpty()){
				ob.put("code", 1);
				ob.put("desc", "请选择要删除的策略!");
				JsonUtil.jsonString(response, ob.toString());
				return ;
			}
			userPollingService.deletebyid(pollingId);
			ob.put("code", 0);
			ob.put("desc", "删除成功!");
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改设备密码
	 */
	public void modifyPwd(){
		LOG.info("Executing operation modifyPwd");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String oldPwd = Util.dealNull(request.getParameter("oldPwd"));
		String newPwd = Util.dealNull(request.getParameter("newPwd"));
		try {
			JSONObject ob = new JSONObject();
			ob.put("code", 1);
			ob.put("desc", "修改失败!");
			/*if (!Util.isNormalPwd(newPwd)) {
				ob.put("code", 9);
				ob.put("desc", "密码必须满足大写字母，小写字母，数字，特殊符号四选三!");
				JsonUtil.jsonString(response, ob.toString());
				return ;
			}*/
			List<TblDev> dList = this.devService.getResultList(" o.devNo=? and o.devKey=?", null, new Object[]{devNo,oldPwd});
			if(dList != null && dList.size() > 0){
				TblDev dev = dList.get(0);
				dev.setDevKey(newPwd);
				dev.setModifyPwdTime(Util.dateToStr(new Date()));
				this.devService.update(dev);
				ob.put("code", 0);
				ob.put("desc", "修改成功!");
			}else {
				ob.put("code", 2);
				ob.put("desc", "用户名或原始密码错误!");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存用户手机硬件信息
	 */
	public void saveMobileInfo(){
		LOG.info("Executing operation saveMobileInfo");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String devKey = Util.dealNull(request.getParameter("devKey"));
		String mobileName = Util.dealNull(request.getParameter("mobileName"));
		String version = Util.dealNull(request.getParameter("version"));
		String netType = Util.dealNull(request.getParameter("netType"));
		String operatorName = Util.dealNull(request.getParameter("operatorName"));
		String imsi = Util.dealNull(request.getParameter("imsi"));
		try {
			JSONObject obj = WorkUtil.checkDev(devService, devNo, devKey);
			JSONObject ob = new JSONObject();
			ob.put("code", obj.opt("code"));
			ob.put("desc", obj.opt("desc"));
			if(obj.optInt("code", -1)==0){
				TblDev dev = (TblDev) JSONObject.toBean(obj.optJSONObject("dev"), TblDev.class);
				TblMobile mobile = new TblMobile();
				mobile.setId(Util.getUUID(6));
				mobile.setDev(dev);
				mobile.setMobileName(mobileName);
				mobile.setVersion(version);
				mobile.setNetType(netType);
				mobile.setOperatorName(operatorName);
				mobile.setImsi(imsi);
				mobile.setAddTime(Util.dateToStr(new Date()));
				this.mobileService.save(mobile);
				ob.put("code", 0);
				ob.put("desc", "保存成功!");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存设备操作日志
	 */
	public void saveDevLog(TblDev dev,String clientIP,int logType,String logDesc){
		LOG.info("Executing operation saveDevLog");
		DevLog devLog = new DevLog();
		try {
			MediaServer server = null;
			if (dev != null) {
				String serverId = dev.getServerId();
				server = this.mediaServerService.getById(serverId);
			}
			String id = Util.getUUID(6);
			devLog.setId(id);
			devLog.setDev(dev);
			devLog.setMediaServer(server);
			devLog.setClientIP(clientIP);
			devLog.setOperatorName("");
			devLog.setOperatorCode("");
			devLog.setLogType(logType);
			devLog.setLogDesc(logDesc);
			devLog.setAddTime(Util.dateToStr(new Date()));
			this.devLogService.save(devLog);
			//IP地址解析线程
			IPAddressThread rsthread = new IPAddressThread(devLogService,id,clientIP,ipService);
			Thread thread = new Thread(rsthread);
			thread.start();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取远程IP地址
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getRemoteAddr();
       }
       return ip;
   }
	
	/**
	 * 保存用户反馈问题
	 */
	public void saveProblem(){
		LOG.info("Executing operation saveProblem");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String mobileModel = Util.dealNull(request.getParameter("mobileModel"));
		String systemOS = Util.dealNull(request.getParameter("systemOS"));
		String netType = Util.dealNull(request.getParameter("netType"));
		String content = Util.dealNull(request.getParameter("content"));
		try {
			JSONObject ob = new JSONObject();
			ob.put("code", 1);
			ob.put("desc", "保存失败!");
			List<TblDev> dList = this.devService.getResultList(" o.devNo=?", null, new Object[]{devNo});
			if(dList != null && dList.size() > 0){
				TblProblem problem = new TblProblem();
				problem.setId(Util.getUUID(6));
				problem.setDevNo(dList.get(0).getDevNo());
				problem.setMobileModel(mobileModel);
				problem.setSystemOS(systemOS);
				problem.setNetType(Integer.parseInt(netType));
				problem.setContent(content);
				problem.setAddTime(Util.dateToStr(new Date()));
				this.problemService.save(problem);
				ob.put("code", 0);
				ob.put("desc", "保存成功!");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改PC端用户密码
	 */
	public void modifyUserPwd(){
		LOG.info("Executing operation modifyUserPwd");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String oldPwd = Util.dealNull(request.getParameter("oldPwd"));
		String newPwd = Util.dealNull(request.getParameter("newPwd"));
		try {
			JSONObject ob = new JSONObject();
			ob.put("code", 1);
			ob.put("desc", "修改失败!");
			/*if (!Util.isNormalPwd(newPwd)) {
				ob.put("code", 9);
				ob.put("desc", "密码必须满足大写字母，小写字母，数字，特殊符号四选三!");
				JsonUtil.jsonString(response, ob.toString());
				return ;
			}*/
			List<TblUser> uList = this.userService.getResultList(" o.account=? and o.pwd=?", null, new Object[]{account,oldPwd});
			if(uList != null && uList.size() > 0){
				TblUser user = uList.get(0);
				user.setPwd(newPwd);
				user.setModifyPwdTime(Util.dateToStr(new Date()));
				this.userService.update(user);
				ob.put("code", 0);
				ob.put("desc", "修改成功!");
			}else {
				ob.put("code", 2);
				ob.put("desc", "用户名或原始密码错误!");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存用户反馈问题
	 */
	public void saveError(){
		LOG.info("Executing operation saveError");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String clientIP = Util.dealNull(request.getParameter("clientIP"));
		String logDesc = Util.dealNull(request.getParameter("logDesc"));
		try {
			JSONObject ob = new JSONObject();
			ob.put("code", 1);
			ob.put("desc", "保存失败!");
			List<TblDev> dList = this.devService.getResultList(" o.devNo=?", null, new Object[]{devNo});
			if (dList != null && dList.size() >0) {
				saveDevLog(dList.get(0), clientIP, 4, logDesc);
				ob.put("code", 0);
				ob.put("desc", "保存成功!");
			}
			JsonUtil.jsonString(response, ob.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/*-------------------------------北京项目部分方法开始-----------------------------------------*/
	/**
	 * 点赞
	 */
	public void addLike(){
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String devId = Util.dealNull(request.getParameter("devId"));
		String clientIP = Util.dealNull(request.getParameter("clientIP"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		TblUser user = (TblUser) JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
		obj.remove("user");
		obj.put("code", -1);
		obj.put("desc", "点赞失败!");
		try {
			TblDev dev = this.devService.getById(devId);
			if(dev==null ){
				obj.put("code", 1);
				obj.put("desc", "没有找到当前设备");
				JsonUtil.jsonString(response, obj.toString());
				return;
			}
			List<DevGood> dList = this.devGoodService.getResultList(" o.user.id=? and o.dev.id=?", null, new Object[]{user.getId(),devId});
			if (dList == null || dList.size() ==0) {
				DevGood devGood = new DevGood();
				devGood.setId(Util.getUUID(6));
				devGood.setDev(dev);
				devGood.setUser(user);
				devGood.setClientIP(clientIP);
				devGood.setGoodTime(Util.dateToStr(new Date()));
				this.devGoodService.save(devGood);
				obj.put("code", 0);
				obj.put("desc", "点赞成功!");
			}
			List<DevGood> list = this.devGoodService.getResultList(" o.dev.id=?", null, new Object[]{dev.getId()});
			obj.put("total", list.size());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println(obj.toString());
		JsonUtil.jsonString(response, obj.toString());
	}
	
	/**
	 * 获取实时播放地址
	 * @return
	 */
	public void getPlayUrl(){
		LOG.info("Executing operation getPlayUrl");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		String clientIP = Util.dealNull(request.getParameter("clientIP"));
		TblUser user = (TblUser) JSONObject.toBean(obj.optJSONObject("user"),TblUser.class);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		obj.remove("user");
		String id = Util.dealNull(request.getParameter("id"));
		TblDev dev = devService.getById(id);
		if(dev==null){
			obj.put("code", 1);
			obj.put("desc", "没有找到当前设备");
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		obj.put("code", 0);
		obj.put("desc", "获取播放地址成功!");
		obj.put("url", Util.dealNull(dev.getPublishUrl()));
		obj.put("devId", id);
		//查询是否已被点赞过
		List<DevGood> devGoods = this.devGoodService.getResultList(" o.dev.id=? and o.user.account=?", null, new Object[]{id,account});
		boolean goodFlag = false;
		if (devGoods == null || devGoods.size() ==0) {
			goodFlag = true;//可以点赞
		}
		obj.put("goodFlag",goodFlag);
		//查询被点赞数量
		List<DevGood> goodList = this.devGoodService.getResultList(" o.dev.id=?", null, new Object[]{dev.getId()});
		obj.put("goodTotal", goodList.size());
		//查询被浏览数
		List<DevView> devViews = this.devViewService.getResultList(" o.dev.id=? ", null, new Object[]{id});
		obj.put("views", devViews.size());
		//新增浏览记录
		DevView devView = new DevView();
		devView.setId(Util.getUUID(6));
		devView.setUser(user);
		devView.setDev(dev);
		devView.setViewTime(Util.dateToStr(new Date()));
		devView.setClientIP(clientIP);
		this.devViewService.save(devView);
		JsonUtil.jsonString(response, obj.toString());
	}
	
	/**
	 * 添加评论
	 */
	public void addComment(){
		LOG.info("Executing operation addComment");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String devId = Util.dealNull(request.getParameter("devId"));
		String clientIP = Util.dealNull(request.getParameter("clientIP"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		TblUser user = (TblUser) JSONObject.toBean(obj.optJSONObject("user"), TblUser.class);
		obj.remove("user");
		obj.put("code", -1);
		obj.put("desc", "点赞失败!");
		try {
			String content = URLDecoder.decode(Util.dealNull(request.getParameter("content")),"UTF-8");
			TblDev dev = this.devService.getById(devId);
			if(dev==null ){
				obj.put("code", 1);
				obj.put("desc", "没有找到当前设备");
				JsonUtil.jsonString(response, obj.toString());
				return;
			}
			List<String> sList = getSensitiveWords();
			List<TblBlack> blacks = this.blackService.getResultList(" o.user.id=?", null, new Object[]{user.getId()});
			DevComment devComment = new DevComment();
			devComment.setUserId(user.getId());
			devComment.setDevId(devId);
			devComment.setId(Util.getUUID(6));
			devComment.setRealContent(content);
			devComment.setContent(SensitiveWordUtil.replaceSensitiveWord(content, 2, "*", sList));
			devComment.setCommentTime(Util.dateToStr(new Date()));
			devComment.setClientIP(clientIP);
			if (blacks != null && blacks.size() >0) {
				devComment.setIsLegal(1);
			}else {
				devComment.setIsLegal(0);
			}
			this.devCommentService.save(devComment);
			obj.put("code", 0);
			obj.put("desc", "评论成功!");
			JsonUtil.jsonString(response, obj.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<String> getSensitiveWords(){
		List<String> sList = new ArrayList<String>();
		try {
			List<SensitiveWord> swList = this.wordService.getResultList(" 1=1", null);
			for (SensitiveWord word : swList) {
				sList.add(word.getSensitiveWord());
			}
			//使用jdk8stream流，获取只含有敏感词的list
//			sList = swList.stream().map(s -> s.getSensitiveWord()).collect(Collectors.toList());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sList;
	}
	
	/**
	 * 删除评论
	 */
	public void removeComment(){
		LOG.info("Executing operation removeComment");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String commentId = Util.dealNull(request.getParameter("commentId"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		obj.remove("user");
		obj.put("code", -1);
		obj.put("desc", "删除失败!");
		try {
			this.devCommentService.deletebyid(commentId);
			obj.put("code", 0);
			obj.put("desc", "删除成功!");
			JsonUtil.jsonString(response, obj.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询某个直播设备下的所有评论-播放端使用和PC监控端使用
	 */
	public void queryCommentList(){
		LOG.info("Executing operation queryCommentList");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String pwd = Util.dealNull(request.getParameter("pwd"));
		String devId = Util.dealNull(request.getParameter("devId"));
		String startTime = Util.dealNull(request.getParameter("startTime"));
		String endTime = Util.dealNull(request.getParameter("endTime"));
		JSONObject obj = WorkUtil.checkUser(userService, account, pwd);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		obj.remove("user");
		obj.put("code", -1);
		obj.put("desc", "查询失败!");
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("commentTime", "asc");
			List<DevComment> devComments = this.devCommentService.getResultList(" o.devId=? and o.isLegal=? and o.commentTime >? and o.commentTime <=?", orderby, new Object[]{devId,0,startTime,endTime});
			obj.put("code", 0);
			obj.put("desc", "查询成功!");
			obj.put("total", devComments.size());
			obj.put("list", devComments);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(obj.toString());
		JsonUtil.jsonString(response, obj.toString());
	}
	
	/**
	 * 查询某个正在直播设备下的所有评论-采集端
	 */
	public void queryComments(){
		LOG.info("Executing operation queryCommentList");
		response.setCharacterEncoding("UTF-8");
		String devId = Util.dealNull(request.getParameter("devId"));
		String devKey = Util.dealNull(request.getParameter("devKey"));
		String startTime = Util.dealNull(request.getParameter("startTime"));
		String endTime = Util.dealNull(request.getParameter("endTime"));
		JSONObject obj = WorkUtil.checkDev(devService, devId,devKey);
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		TblDev dev = (TblDev) JSONObject.toBean(obj.optJSONObject("dev"), TblDev.class);
		obj.remove("dev");
		obj.put("code", -1);
		obj.put("desc", "查询失败!");
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("commentTime", "asc");
			List<DevComment> devComments = this.devCommentService.getResultList(" o.devId=? and o.isLegal=? and o.commentTime >? and o.commentTime <=?", orderby, new Object[]{dev.getId(),0,startTime,endTime});
			obj.put("code", 0);
			obj.put("desc", "查询成功!");
			obj.put("total", devComments.size());
			obj.put("list", devComments);
			System.out.println(obj.toString());
			JsonUtil.jsonString(response, obj.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置密码
	 * @param account:账号 password:密码 type:账号类型 0-采集端 1-PC端或播放端
	 */
	public void setPwd(){
		LOG.info("Executing operation setPwd");
		response.setCharacterEncoding("UTF-8");
		String account = Util.dealNull(request.getParameter("account"));
		String password = Util.dealNull(request.getParameter("password"));
		String type = Util.dealNull(request.getParameter("type"));
		String code = Util.dealNull(request.getParameter("code"));
		try {
			JSONObject obj = new JSONObject();
			obj.put("code", 0);
			obj.put("desc", "设置成功!");
			if (!code.equals(MD5Util.MD5Code(account+password))) {
				obj.put("code", 9);
				obj.put("desc", "账号和验证码验证不正确，请重新设置密码!");
				JsonUtil.jsonString(response, obj.toString());
				return ;
			}
			if ("0".equals(type)) {
				List<TblDev> devs = this.devService.getResultList(" o.devNo=?", null, new Object[]{account});
				if (devs != null && devs.size()>0) {
					TblDev dev = devs.get(0);
					dev.setDevKey(password);
					dev.setModifyPwdTime(Util.dateToStr(new Date()));
					dev.setEditTime(Util.dateToStr(new Date()));
					this.devService.update(dev);
				}else {
					obj.put("code", 1);
					obj.put("desc", "该账号不存在!");
				}
			}else if ("1".equals(type)) {
				List<TblUser> users = this.userService.getResultList(" o.account=?", null, new Object[]{account});
				if (users != null && users.size() >0) {
					TblUser user = users.get(0);
					user.setPwd(password);
					user.setModifyPwdTime(Util.dateToStr(new Date()));
					user.setEditTime(Util.dateToStr(new Date()));
					this.userService.update(user);
				}else {
					obj.put("code", 1);
					obj.put("desc", "该账号不存在!");
				}
			}else {
				obj.put("code", -1);
				obj.put("desc", "设置失败!");
			}
			JsonUtil.jsonString(response, obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据账号和密码获取该账号的直播地址 onLine 0-在线 1-离线
	 */
	public void getDevPlayUrl(){
		LOG.info("Executing operation getUrl1");
		response.setCharacterEncoding("UTF-8");
		String devNo = Util.dealNull(request.getParameter("devNo"));
		String devKey = Util.dealNull(request.getParameter("devKey"));
		JSONObject obj = WorkUtil.checkDev(devService, devNo, devKey);
		obj.put("url", "");
		if(obj.optInt("code", -2)!=0){
			JsonUtil.jsonString(response, obj.toString());
			return;
		}
		TblDev dev = (TblDev) JSONObject.toBean(obj.optJSONObject("dev"), TblDev.class);
		obj.remove("dev");
		obj.put("url", Util.dealNull(dev.getPublishUrl()));
		obj.put("onLine", dev.getOnLines());
		JsonUtil.jsonString(response, obj.toString());
	}
	/*-------------------------------北京项目部分方法结束-----------------------------------------*/
}
