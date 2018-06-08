package com.sttri.util;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;


public class PubWebService {
	/**
	 * 获取webservice
	 * @param entityClass	webservice对象
	 * @param syncUrl		地址
	 * @return
	 */
	public static Object getWebService(Class<?> entityClass,String syncUrl){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(entityClass);
		factory.setAddress(syncUrl);
		Object service = factory.create();
		return service;
	}
	
	/**
	 * 获取hlsService
	 * @param entityClass	webservice对象
	 * @param syncUrl		地址
	 * @return
	 */
	public static Object getHlsService(Class<?> entityClass,String syncUrl){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(entityClass);
		factory.setAddress(syncUrl);
		Object service = factory.create();
		return service;
	}
	
	/**
	 * 获取transCodeService
	 * @param entityClass	webservice对象
	 * @param syncUrl		地址
	 * @return
	 */
	public static Object getTransCodeService(Class<?> entityClass,String syncUrl){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(entityClass);
		factory.setAddress(syncUrl);
		Object service = factory.create();
		return service;
	}
	
	public static void main(String[] args) {
		
	}
}
