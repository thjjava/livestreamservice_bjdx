package com.sttri.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建者 zhuhong  E-mail: zhuhong719@126.com
 * 创建时间：Apr 25, 2012 2:22:27 PM
 * 版本：
 * 类说明：
 * 修改者：
 * 修改时间：Apr 25, 2012 2:22:27 PM
 * 修改说明：
 */
public class CookiesUtil {
	/**
	 * 设置cookies内容，并且将该保存到客户端 ,并且该cookies紧跟随浏览器时间。当浏览器关闭时间cookies失效
	 * @param cName 用户名
	 * @param cValue cookies值
	 * @param request 传递过来的已经实例化好的HttpServletRequest对象
	 * @param response 传递过来的已经实例化好的HttpServletResponse对象
	 * @since 0.01
	 */
	public static void setString(String cName, String cValue,HttpServletRequest request,HttpServletResponse response) {
		setString(cName, cValue, "/", 0, null, request, response);
	}
	/**
	 * 设置cookies内容，并且将该保存到客户端 ，全部的参数
	 * @param cName 用户名
	 * @param cValue cookies值
	 * @param cPath 表示要设置cookies保存的路径
	 * @param iTimeout 表示cookies超时时间
	 * @param cDomain 表示要设置的cookies的作用站点域
	 * @param request 传递过来的已经实例化好的HttpServletRequest对象
	 * @param response 传递过来的已经实例化好的HttpServletResponse对象
	 * @since 0.01
	 */
	public static void setString(String cName, String cValue, String cPath, int iTimeout,String cDomain,HttpServletRequest request,HttpServletResponse response) {
		if("".equals(cName) || null==cName) {
			return;
		}
		Delete(cName, request, response);
		Cookie cookie = new Cookie(cName, cValue);
		cookie.setPath(cPath);
		if(cDomain!=null) {
			cookie.setDomain(cDomain);
		}
		if(iTimeout >0) {
			cookie.setMaxAge(iTimeout);
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 删除该cookies
	 * @param cName 表示要删除的cookies名称
	 * @param request 传递过来的已经实例化好的HttpServletRequest对象
	 * @param response 传递过来的已经实例化好的HttpServletResponse对象
	 * @return 删除成功,返回true,否则返回false
	 * @since 0.01
	 */
	public static boolean Delete(String cName,HttpServletRequest request,HttpServletResponse response) {
		 Cookie[] cookies = request.getCookies();
		 if(cookies==null){
			 return false;
		 }
		 for (int i = 0; i < cookies.length; i++){
			 if (cookies[i].getName().equals(cName)){
				 cookies[i].setValue("");
				 cookies[i].setMaxAge(0);
				 response.addCookie(cookies[i]);
				 return true;
			 }
		 }
		 cookies = null;
		 return false;
	}
	
	/**
	 * 从Cookies中获取指定名称、指定域名的COOKIES的值
	 * @param cName 标识名称
	 * @param cDomain 标识域名
	 * @param request  传递过来的已经实例化好的HttpServletRequest对象
	 * @return 如果有值返回该值，否则返回null
	 * @author :叶明
	 * @dateTime :@2010-9-9 -- 上午09:53:42
	 * @since 0.01
	 */
	public static String getString(String cName,String cDomain,HttpServletRequest request) {
		 if("".equals(cName) || null==cName) {
			return null;
		 }
		 Cookie[] cookies = request.getCookies();
		 if(cookies==null){
			 return null;
		 }
		 for (int i = 0; i < cookies.length; i++){
			 if(cookies[i].getName().equalsIgnoreCase(cName)){
				 if(null !=cDomain) {
					 if(cookies[i].getDomain().equalsIgnoreCase(cDomain)) {
						 String v = cookies[i].getValue();
						 if(v==null){
							 cookies = null;
							 return null;
						 }else{
							 cookies = null;
							 return v;
						 }
					 }else {
						 return null;
					 }
				 }else {
					 String v = cookies[i].getValue();
					 if(v==null){
						 cookies = null;
						 return null;
					 }else{
						 cookies = null;
						 return v;
					 }
				 }
			 }
		 }
		 cookies = null;
	     return null;
	}
}
