package com.sttri.action;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import com.opensymphony.xwork2.ActionSupport;
public class BaseAction extends ActionSupport implements ServletResponseAware,
        ServletRequestAware, ServletContextAware {
    private static final long serialVersionUID = -7493241864514155959L;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ServletContext application;
    
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    public void setServletContext(ServletContext application) {
        this.application = application; 
    }
}
