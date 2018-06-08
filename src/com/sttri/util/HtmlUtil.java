package com.sttri.util;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	public static Document getDocument(File file){
		Document doc = null;
		try {
			doc = Jsoup.parse(file, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 
	 * @param doc
	 * @param element
	 * @return
	 */
	public static Elements getElements(Document doc,String element){
		return doc.select(element);
	}
	
	/**
	 * 设置html页面某元素中的某属性
	 * @param doc	html
	 * @param id	元素id
	 * @param attr	元素属性
	 * @return
	 */
	public static void setElementAttr(Document doc,String id,String attr,String content){
		doc.getElementById(id).attr(attr,content);
	}
	
	/**
	 * 设置html页面某元素中的内容
	 * @param doc	html
	 * @param id	元素id
	 * @return
	 */
	public static void setElementText(Document doc,String id,String content){
		doc.getElementById(id).text(content);
	}
	
	/**
	 * 得到html页面某元素中的某属性
	 * @param doc	html
	 * @param id	元素id
	 * @param attr	元素属性
	 * @return
	 */
	public static String getElementAttr(Document doc,String id,String attr){
		return doc.getElementById(id).attr(attr);
	}
	
	/**
	 * 得到html页面某元素中的内容
	 * @param doc	html
	 * @param id	元素id
	 * @return
	 */
	public static String getElementText(Document doc,String id){
		return doc.getElementById(id).text();
	}
	
	/**
	 * 得到html页面某元素
	 * @param doc	html
	 * @param id	元素id
	 * @return
	 */
	public static Element getElement(Document doc,String id){
		return doc.getElementById(id);
	}

}
