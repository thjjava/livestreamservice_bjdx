package com.sttri.util;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建者 zhuhong  E-mail: zhuhong719@126.com
 * 创建时间：Nov 16, 2012 11:25:18 AM
 * 版本：
 * 类说明：
 * 修改者：
 * 修改时间：Nov 16, 2012 11:25:18 AM
 * 修改说明：
 */
public class XmlFileUtil {
	/**
	 * 从request请求中获取参数格式为xml的内容
	 * @param request
	 * @return
	 */
	public static String getXmlFile(HttpServletRequest request){
		String xmlfile="";
		try {
			int iContentLen = request.getContentLength();
			byte sContent[] = new byte[iContentLen];
			BufferedInputStream buf = new BufferedInputStream(request.getInputStream());
			buf.read(sContent, 0, iContentLen);
			xmlfile = new String(sContent,0,iContentLen,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlfile;
	}
}
