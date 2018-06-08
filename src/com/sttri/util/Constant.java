package com.sttri.util;

import java.io.IOException;
import java.util.Properties;

//解析资源文件
public class Constant {
	public static String ROOTPATH="";
	
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(Constant.class.getClassLoader()
					.getResourceAsStream("constants.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readKey(String key) {
		return (String) properties.get(key);
	}
	
}
