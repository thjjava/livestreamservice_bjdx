package com.sttri.action;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.sttri.pojo.TblUser;
import com.sttri.pojo.UserLog;
import com.sttri.service.IUserLogService;
import com.sttri.util.Util;
public class BaseAction extends ActionSupport implements ServletResponseAware,
        ServletRequestAware, ServletContextAware {
    private static final long serialVersionUID = -7493241864514155959L;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ServletContext application;
    @Autowired
    private IUserLogService userLogService;
    
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    public void setServletContext(ServletContext application) {
        this.application = application; 
    }
    
    //保存操作日志
    public void saveUserLog(TblUser user,String logDesc){
    	UserLog userLog = new UserLog();
    	userLog.setId(Util.getUUID(6));
    	if (user == null) {
    		userLog.setCompany(null);
        	userLog.setOperator("");
		}else {
			userLog.setCompany(user.getCompany());
	    	userLog.setOperator(user.getAccount());
		}
    	userLog.setLogDesc(logDesc);
    	userLog.setAddTime(Util.dateToStr(new Date()));
    	this.userLogService.save(userLog);
    }
}
