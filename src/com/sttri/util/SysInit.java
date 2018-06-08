package com.sttri.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SysInit implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		
	}

	public void contextInitialized(ServletContextEvent event) {
		String cRootPath = event.getServletContext().getRealPath("/");
		if(cRootPath!=null) {
			cRootPath = cRootPath.replaceAll("\\\\", "/");
		}else {
			cRootPath = "/";
		}
		if (!cRootPath.endsWith("/")) {
			cRootPath = cRootPath + "/";
		}
		Constant.ROOTPATH = cRootPath;
	}

}
