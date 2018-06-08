package com.sttri.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.et.mvc.JsonView;

public class JsonUtil {
	/**
	 * 发送json格式数据
	 * @param response
	 * @param obj		数据只有一层
	 */
	public static void jsonRetrun(HttpServletResponse response,Object obj){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(new JsonView(obj));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送字符串
	 * @param response
	 * @param json
	 */
	public static void jsonString(HttpServletResponse response,String json){
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * List多层数据转换josn
	 * @param response
	 * @param list		集合
	 */
	public static void jsonListToString(HttpServletResponse response,List<Object> list){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(JSONArray.fromObject(list).toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * bean多层数据转换json
	 * @param response
	 * @param obj
	 */
	public static void jsonBeanToString(HttpServletResponse response,Object obj){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(JSONObject.fromObject(obj).toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * string转json
	 * @param args
	 */
	public static JSONObject stringToJson(String str){
		JSONObject object = null;
		try {
			object = JSONObject.fromObject(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	
	public static void main(String[] args) {

		String string = "{ y: 117.15, color: 12 }";
		JSONObject obj = JSONObject.fromObject(string);
		System.out.println(obj.toString());
	}
}
