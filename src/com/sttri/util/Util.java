package com.sttri.util;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具类 提供一些常用功能。<br>
 * 一些常用的数据处理如中文转化等。 Copyright: Copyright (c) 2004 Company: yeshine Inc.
 * @author: 王定强
 * @create date: 2011.11.15
 */
@SuppressWarnings("unchecked")
public class Util implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6735079334693077404L;

	/**
	 * 将输入的字符串转换 "sb1#sb2"  --->  'sb1','sb2'
	 * */
	public static String getBuildStr(String str) {
		String returnValue = "";
		if(str.indexOf("#")!=-1){
			String[] strs = str.split("#");
			for(int i=0;i<strs.length;i++){
				if(returnValue.equals("")){
					returnValue = "'"+strs[i]+"'";
				}else{
					returnValue += ",'"+strs[i]+"'";
				}
			}
		}else{
			returnValue = "'"+str+"'";
		}
		return returnValue;
	}
	/**
	 * 将输入的字符串转换 "sb1,b2"  --->  'sb1','sb2'
	 * */
	public static String getBuildStr(String str,String type) {
		String returnValue = "";
		if(str.indexOf(",")!=-1){
			String[] strs = str.split(",");
			for(int i=0;i<strs.length;i++){
				if(returnValue.equals("")){
					returnValue = "'"+strs[i]+"'";
				}else{
					returnValue += ",'"+strs[i]+"'";
				}
			}
		}else{
			returnValue = "'"+str+"'";
		}
		return returnValue;
	}
	/**
	 * 将输入的字符串转换 数字类"1#2"  --->  1,2
	 * */
	public static String getBuildInt(String str) {
		String returnValue = "";
		if(str.indexOf("#")!=-1){
			String[] strs = str.split("#");
			for(int i=0;i<strs.length;i++){
				if(returnValue.equals("")){
					returnValue = ""+strs[i]+"";
				}else{
					returnValue += ","+strs[i]+"";
				}
			}
		}else{
			returnValue = ""+str+"";
		}
		return returnValue;
	}
	/**
	 * 信息点处理
	 * */
	public static String getBuildPoint(String str) {
		String returnValue = "";
		String[] value = null;
		if(str.indexOf("#")!=-1){
			String[] strs = str.split("#");			
			for(int i=0;i<strs.length;i++){
				value = strs[i].split("@");
				if(returnValue.equals("")){
					returnValue = ""+value[1]+"";
				}else{
					returnValue += "#"+value[1]+"";
				}
			}
		}else{
			if(str.indexOf("@")==-1){
				returnValue = ""+str+"";
			}else{
				value = str.split("@");
				returnValue = ""+value[1]+"";
			}			
		}
		return returnValue;
	}
	/**
	 * 信息点端口处理
	 * */
	public static String getBuildPort(String str) {
		String returnValue = "";
		String[] value = null;
		if(str.indexOf("#")!=-1){
			String[] strs = str.split("#");			
			for(int i=0;i<strs.length;i++){
				value = strs[i].split(",");
				if(returnValue.equals("")){
					returnValue = ""+value[1]+"";
				}else{
					returnValue += "#"+value[1]+"";
				}
			}
		}else{
			if(str.indexOf(",")==-1){
				returnValue = ""+str+"";
			}else{
				value = str.split(",");
				returnValue = ""+value[1]+"";
			}			
		}
		return returnValue;
	}
	/**
	 * 中文转换:GBK
	 * @param str 需要转换成中文的字符串
	 * @return 转换后的字符串
	 */
	public static String convertChinese(String str) {
		if (str == null)
			return "";
		try {
			return new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 中文转换 :UTF-8
	 * 
	 * @param str
	 *            需要转换成中文的字符串
	 * @return 转换后的字符串
	 */
	public static String convertToUTF8(String str) {
		if (str == null)
			return "";
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 文本格式转换为HTML格式.
	 * 
	 * @param instr
	 *            要转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String changeToHTML(String inStr) {
		String outStr = "";
		if (inStr != null) {
			inStr = inStr.replaceAll("\n", "<br>");
			char[] contents = inStr.toCharArray();
			for (int i = 0; i < contents.length; i++) {
				char ch = contents[i];
				if ((ch != '\"') && (ch != '<') && (ch != '>') && (ch != '&')
						&& (ch != ' ')) {
					outStr += ch;
				} else {
					if (ch == '\"') {
						outStr += "&quot;";
					}
					if (ch == '<') {
						// outStr += "&lt;";
						outStr += "<";
					}
					if (ch == '>') {
						// outStr += "&gt;";
						outStr += ">";
					}
					if (ch == '&') {
						outStr += "&amp;";
					}
					if (ch == ' ') {
						outStr += "&nbsp;";
					}
				}
			}
			contents = null;
		}
		inStr = null;
		return outStr;
	}

	/**
	 * 转换编码,GBK到ISO-8859-1 .
	 * 
	 * @param inStr
	 *            要转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String changeTo8859(String inStr) {
		if (inStr == null)
			return null;
		try {
			return new String(inStr.getBytes("GBK"), "ISO-8859-1");
		} catch (java.io.UnsupportedEncodingException ue) {
			return "";
		}
	}

	/**
	 * 根据给定的分隔符，把字符串分隔开来.
	 * 
	 * @param str
	 *            要拆分的字符串 <br>
	 * @param div
	 *            给定的分隔符
	 * @return String[] 返回拆分后的字符串组 <br>
	 */
	public static String[] split(String str, String div) {
		int divLength = div.length();
		if (str == null || str.equals("")) {
			return null;
		}
		str = str.trim();
		Vector v = new Vector(3, 2);
		String temp = "";
		int pos = str.indexOf(div);
		while (pos != -1) {
			temp = str.substring(0, pos);
			v.add(temp);
			str = str.substring(pos + divLength);
			pos = str.indexOf(div);
		}
		v.add(str.trim());
		String[] rtnArr = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			rtnArr[i] = (String) v.elementAt(i);
		}
		v = null;
		temp = null;
		str = null;
		return rtnArr;
	}

	/**
	 * 日期对象转换为字符串 .返回格式比如：2004-01-01 09:18:13
	 * 
	 * @param Date
	 *            dt对象
	 * @return String 时间的字符串表示
	 */
	public static String dateToStr(Date dt) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dt);
		dt = null;
		return dateToStr(gc);
	}

	/**
	 * 日期对象转换为字符串 .返回格式比如：2004-01-01 09:18:13
	 * 
	 * @param inCalendar
	 *            Calendar对象
	 * @return String 时间的字符串表示
	 */
	public static String dateToStr(Calendar inCalendar) {
		String calendarStr = String.valueOf(inCalendar.get(Calendar.YEAR))
				+ "-";
		if ((String.valueOf(inCalendar.get(Calendar.MONTH) + 1)).length() == 1) {
			calendarStr += "0";
		}
		calendarStr += String.valueOf(inCalendar.get(Calendar.MONTH) + 1) + "-";
		if ((String.valueOf(inCalendar.get(Calendar.DAY_OF_MONTH))).length() == 1) {
			calendarStr += "0";
		}
		calendarStr += String.valueOf(inCalendar.get(Calendar.DAY_OF_MONTH))
				+ " ";
		if ((String.valueOf(inCalendar.get(Calendar.HOUR_OF_DAY))).length() == 1) {
			calendarStr += "0";
		}
		calendarStr += String.valueOf(inCalendar.get(Calendar.HOUR_OF_DAY))
				+ ":";
		if ((String.valueOf(inCalendar.get(Calendar.MINUTE))).length() == 1) {
			calendarStr += "0";
		}
		calendarStr += String.valueOf(inCalendar.get(Calendar.MINUTE)) + ":";
		if ((String.valueOf(inCalendar.get(Calendar.SECOND))).length() == 1) {
			calendarStr += "0";
		}
		calendarStr += String.valueOf(inCalendar.get(Calendar.SECOND));
		inCalendar = null;
		return calendarStr;
	}

	/**
	 * 如果字符串为NULL，返回空字符串.
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String dealNull(String inStr) {
		if (inStr == null)
			return "";
		return inStr;
	}

	/**
	 * 处理字符串：如果为null则是空字符串，否则去掉左右空格并且截取相关长度
	 * 
	 * @param s
	 *            要处理的字符串
	 * @param len返回字符串的最大长度 @
	 *            return String 转换后的字符串
	 */
	public static String dealNull(String s, int len) {
		if (s == null)
			return "";
		if (s.length() > len)
			return s.substring(0, len);
		else
			return s;
	}

	/**
	 * 如果字符串为NULL，返回空字符串.[前提：obj为字符串对象]
	 * 
	 * @param obj
	 *            要转换的字符串
	 * @return String 转换后的字符串
	 */
	public static String dealNull(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	/**
	 * 如果Integer对象为NULL，返回空字符串.
	 * 
	 * @param i
	 *            Integer对象
	 * @return String 转换后的字符串
	 */
	public static String dealNull(Integer i) {
		if (i == null)
			return "";
		return "" + i.intValue();
	}

	/**
	 * 如果Long对象为NULL，返回空字符串.
	 * 
	 * @param l
	 *            Long对象
	 * @return String 转换后的字符串
	 */
	public static String dealNull(Long l) {
		if (l == null)
			return "";
		return "" + l.longValue();
	}

	/**
	 * 如果Double对象为NULL，返回空字符串.
	 * 
	 * @param d
	 *            Double对象
	 * @return String 转换后的字符串
	 */
	public static String dealNull(Double d) {
		if (d == null)
			return "";
		return "" + d.doubleValue();
	}

	/**
	 * 如果字符数组为NULL，返回空字符串.[前提：c为字符数组对象]
	 * 
	 * @param c
	 *            要转换的字符数组
	 * @return String 转换后的字符串
	 */
	public static String dealNull(char c[]) {
		if (c == null || c.length == 0)
			return "";
		try {
			return String.valueOf(c);
		} catch (Exception e) {
			c = null;
			return "";
		}
	}

	/**
	 * 处理字符串：如果为null则是空字符串，否则去掉左右空格并且截取相关长度[前提：obj为字符串对象]
	 * 
	 * @param obj
	 *            要处理的对象
	 * @param len返回字符串的最大长度 @
	 *            return String 转换后的字符串
	 */
	public static String dealNull(Object obj, int len) {
		if (obj == null)
			return "";
		return dealNull(obj.toString(), len);
	}

	/**
	 * 中文化，null转换成空字符串
	 * 
	 * @param param
	 *            要转换的字符串
	 * @return 转换后的字符
	 */
	public static String doParameter(String param) {
		if (param == null)
			return "";
		if (param.indexOf("\n") == -1)
			param = param.trim();// 如果没有换行，则认为不是Textarea的值，去掉左右空格。
		return convertChinese(param);
	}

	/**
	 * 中文化，null转换成空字符串 :web2.0 转化成UTF-8的格式
	 * 
	 * @param param
	 *            要转换的字符串
	 * @return 转换后的字符
	 */
	public static String doParameterToUTF8(String param) {
		if (param == null)
			return "";
		if (param.indexOf("\n") == -1)
			param = param.trim();// 如果没有换行，则认为不是Textarea的值，去掉左右空格。
		return convertToUTF8(param);
	}

	/**
	 * 将数字字符转换成相应的中文
	 * 
	 * @Param s
	 *            待转换的字符串
	 * @return String 返回转换之后的字符串 例如金钱的转换
	 */
	public static String convertToChineseNum(String s) {
		if (s == null)
			return "";
		s = s.trim();
		String rtnStr = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '0')
				rtnStr += "零";
			else if (s.charAt(i) == '1')
				rtnStr += "一";
			else if (s.charAt(i) == '2')
				rtnStr += "二";
			else if (s.charAt(i) == '3')
				rtnStr += "三";
			else if (s.charAt(i) == '4')
				rtnStr += "四";
			else if (s.charAt(i) == '5')
				rtnStr += "五";
			else if (s.charAt(i) == '6')
				rtnStr += "六";
			else if (s.charAt(i) == '7')
				rtnStr += "七";
			else if (s.charAt(i) == '8')
				rtnStr += "八";
			else if (s.charAt(i) == '9')
				rtnStr += "九";
			else
				rtnStr += s.charAt(i);
		}
		s = null;
		return rtnStr;
	}

	/**
	 * 把字符串转换为整型 .
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return int 整型数值 String s 必须是：1，2，。。这样的字符串 否则返回0
	 */
	public static int convertToInt(String s) {
		if (s == null)
			return 0;
		if (s.equals(""))
			return 0;
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			s = null;
			return 0;
		}
	}

	/**
	 * 把字符串转换为长整型 .
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return long 长整型数值
	 */
	public static long convertToLong(String s) {
		if (s == null)
			return 0;
		if (s.equals(""))
			return 0;
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			s = null;
			return 0;
		}
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double convertToDouble(double v, int scale) {
		try {
			if (scale < 0)
				return v;
			BigDecimal b = new BigDecimal(Double.toString(v));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param str
	 *            需要四舍五入的字符串
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double convertToDouble(String str, int scale) {
		double v = 0;
		try {
			v = Double.parseDouble(str);
			str = null;
			return convertToDouble(v, scale);
		} catch (Exception e) {
			str = null;
			return 0;
		}
	}

	/**
	 * 把字符串转化成数字
	 * 
	 * @param str
	 *            待转化的字符串
	 * @return 转化后的数字
	 */
	public static double convertToDouble(String str) {
		return convertToDouble(str, -1);
	}

	// 几个转化成为对象的方法
	/**
	 * 将字符串转化成对Long象，转化失败返回null
	 * 
	 * @param s
	 * @return
	 */
	public static Long convertToLongObject(String s) {
		if (s == null || s.equals(""))
			return null;
		try {
			return new Long(s);
		} catch (Exception e) {
			s = null;
			return null;
		}
	}

	/**
	 * 将字符串转化成对Double象，转化失败返回null
	 * 
	 * @param s
	 * @return
	 */
	public static Double convertToDoubleObject(String s) {
		if (s == null || s.equals(""))
			return null;
		try {
			return new Double(s);
		} catch (Exception e) {
			s = null;
			return null;
		}
	}

	/**
	 * 直接将当前日期时间转化成String类型
	 * 
	 * @param sep
	 *            表示时间区段类型 默认为GMT
	 * @return 返回时间String
	 */
	public static String nowDateToString(String sep) {
		if (Util.dealNull(sep).equals(""))
			sep = "GMT+08:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone(sep));
		sep = null;
		return sdf.format(new Date());
	}

	/**
	 * 将时间字符串格式化成日期对象
	 * 
	 * @param dateSte
	 *            待转换的字符串对象
	 * @param sep
	 *            分隔符 ，默认为 "-" 2003-1-1,2003/1/1,2003.1.1 .... 2003-1-1 10
	 *            2003-1-1 10:23 2003-1-1 13:14:16
	 * @return Date 时间对象 (Tue Sep 08 00:00:00 CST 2009)
	 */
	public static Date convertToDate(String dateStr, String sep) {
		if (dateStr == null || dateStr.equals("")) {// 如果为空，返回当前时间
			dateStr = null;
			sep = null;
			return new Date();
		}
		dateStr = dateStr.trim();// 去除空格
		String timeStr = "";// 时间字符串
		if (dateStr.indexOf(" ") != -1) {
			timeStr = dateStr.substring(dateStr.indexOf(" ") + 1);
			dateStr = dateStr.substring(0, dateStr.indexOf(" "));
			timeStr = timeStr.trim();
		}
		String year = dateStr.substring(0, 4);
		dateStr = dateStr.substring(4 + sep.length());
		String month = dateStr.substring(0, dateStr.indexOf(sep));
		String day = dateStr.substring(dateStr.indexOf(sep) + 1);
		int hour = 0;
		int minutes = 0;
		int second = 0;
		if (!timeStr.equals("")) {
			String[] timeArr = Util.split(timeStr, ":");
			if (timeArr != null) {
				if (timeArr.length > 0)
					hour = Util.convertToInt(timeArr[0]);
				if (timeArr.length > 1)
					minutes = Util.convertToInt(timeArr[1]);
				if (timeArr.length > 2)
					second = Util.convertToInt(timeArr[2]);
			}
			timeArr = null;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Util.convertToInt(year), Util.convertToInt(month) - 1, Util
				.convertToInt(day), hour, minutes, second);
		year = null;
		month = null;
		day = null;
		dateStr = null;
		sep = null;
		return cal.getTime();
	}

	/**
	 * 将时间字符串格式化成日期对象
	 * 
	 * @param dateStr
	 *            待转换的字符串对象
	 * @return Date 时间对象
	 */
	public static Date convertToDate(String dateStr) {
		return convertToDate(dateStr, "-");
	}

	/**
	 * 将字符串s当中的 beReplaced 部分用 replace代替 [备注]由于jdk的replaceAll的功能限制，不能替换形如
	 * "/aaa/bb"的字符串，故加入本方法。
	 * 
	 * @param s
	 *            被替换的字符串
	 * @param beReplaced
	 *            被替换部分
	 * @param replace
	 *            用来替换的字符串
	 * @return String 替换的结果
	 */
	public static String replaceStr(String s, String beReplaced, String replace) {
		if (s == null || s.equals(""))
			return "";
		if (s.indexOf(beReplaced) != -1) {
			String s1 = s.substring(0, s.indexOf(beReplaced));
			String s2 = "";
			if (s.indexOf(beReplaced) + beReplaced.length() + 1 <= s.length())
				s2 = s.substring(s.indexOf(beReplaced) + beReplaced.length());
			return s1 + replace + replaceStr(s2, beReplaced, replace);
		}
		return s;
	}

	/**
	 * 得到日期是一年中的第几周，其中参数必须是如2007-02-01
	 * 
	 * @param currDate
	 * @return
	 */
	public static int getWeekOfYear(Date currDate) {
		Calendar c = new GregorianCalendar();
		currDate = null;
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		date = null;
		return c.getTime();
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		c = null;
		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		c = null;
		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		date = null;
		return c.getTime();
	}

	/**
	 * 取的两个日期之间的差值
	 * 
	 * @param int
	 * @return
	 */
	public static int daysOfTwo(Date sDate, Date eDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);
		//int week = calendar.get(Calendar.DAY_OF_WEEK);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(eDate);
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);
		// 求出两日期相隔天数
		int days = day2 - day1;
		int n = days % 7;
		/*
		 * if (n+week==7 || week==7) n = (n-1)>0?n-1:0; else if(n+week>7) n =
		 * n-2; //除去星期六、天 days = (days - days%7)*5/7 + n;
		 */
		days = n;
		calendar = null;
		return days;
	}

	/**
	 * 当前日期是本周的第几天
	 */
	public static int dayOfWeek() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据日期得到物理第几周
	 * 
	 * @param dt
	 * @return
	 */
	public static int getPhsicalWeek(java.util.Date dt) {
		return getWeekOfYear(dt);
	}

	public static int getPhsicalWeek(String dtStr) {
		return getPhsicalWeek(convertToDate(dtStr));
	}

	/**
	 * 和时间2相比，dt1处于第几周 (dt2的时间作为第1周)
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static int getRelativeWeek(java.util.Date dt1, java.util.Date dt2) {
		return getWeekOfYear(dt1) - getWeekOfYear(dt2) + 1;
	}

	/**
	 * 得到当前时间相对 dt2所处第几周
	 * 
	 * @param dt2
	 * @return
	 */
	public static int getRelativeWeek(java.util.Date dt2) {
		return getRelativeWeek(new java.util.Date(), dt2);
	}

	/**
	 * 把任意时间dt转化成有效的时间类型。
	 * 
	 * @param dt
	 *            任意时间字符串
	 * @return 有效的时间字符串 比如:2006-1-1
	 * 
	 * 举例 ----------------------------------------- 2006 -> 2006-1-1 2006年 ->
	 * 2006-1-1 2006年1月 -> 2006-1-1 2006年1月1日 -> 2006-1-1 2006/1/1 -> 2006-1-1
	 * 2006-1-1 -> 2006-1-1 2006-1 -> 2006-1-1 2006.1.1 -> 2006-1-1 2006,1,1 ->
	 * 2006-1-1 2006。1。1 -> 2006-1-1 06年1月 -> 2006-1-1 06,1 -> 2006-1-1 200607 ->
	 * 2006-07-1 200611 -> 2006-11-1 20060221 -> 2006-2-2 2006330 -> 2006-3-3
	 * 20060180 -> 2006-3-21 0000 -> ２００６年１月１日 -> 2006-1-1 ...
	 */
	public static String chgDate(String dt) {
		if (dt == null)
			return "";
		// 先转换全角字符
		if (dt.indexOf("０") != -1)
			dt = Util.replaceStr(dt, "０", "0");
		if (dt.indexOf("１") != -1)
			dt = Util.replaceStr(dt, "１", "1");
		if (dt.indexOf("２") != -1)
			dt = Util.replaceStr(dt, "２", "2");
		if (dt.indexOf("３") != -1)
			dt = Util.replaceStr(dt, "３", "3");
		if (dt.indexOf("４") != -1)
			dt = Util.replaceStr(dt, "４", "4");
		if (dt.indexOf("５") != -1)
			dt = Util.replaceStr(dt, "５", "5");
		if (dt.indexOf("６") != -1)
			dt = Util.replaceStr(dt, "６", "6");
		if (dt.indexOf("７") != -1)
			dt = Util.replaceStr(dt, "７", "7");
		if (dt.indexOf("８") != -1)
			dt = Util.replaceStr(dt, "８", "8");
		if (dt.indexOf("９") != -1)
			dt = Util.replaceStr(dt, "９", "9");
		String year = "";
		String month = "";
		String day = "";
		int i = 0;
		for (i = 0; i < dt.length() && i < 4; i++) {
			if (dt.charAt(i) > '9' || dt.charAt(i) < '0')
				break;
			year += dt.charAt(i);
		}
		if (year.equals(""))
			return "";
		else if (Util.convertToInt(year) == 0)
			return "";

		// 现在检测年度
		if (year.length() == 1) {
			year = "200" + year;
		} else if (year.length() == 2) {
			year = "20" + year;
		} else if (year.length() == 3) {
			year = "2" + year;
		}

		if (dt.length() > i) {
			dt = dt.substring(i);// 得到比如 "-9-1" 或者0901
		} else {
			return year + "-1-1";
		}
		// 然后得到月份的可能字符串
		if (dt.charAt(0) > '9' || dt.charAt(0) < '0') {
			for (i = 0; i < dt.length(); i++) {
				if (dt.charAt(i) > '9' || dt.charAt(i) < '0')
					continue;
				break;// 之后就是月份啦！
			}
			if (dt.length() > i) {
				dt = dt.substring(i);// 得到比如 "9-1"
			} else {
				return year + "-1-1";
			}
		}
		// 解析月份
		for (i = 0; i < dt.length() && i < 2; i++) {
			if (dt.charAt(i) > '9' || dt.charAt(i) < '0')
				break;
			month += dt.charAt(i);
		}
		String rtnStr = "";
		if (month.equals(""))
			return year + "-1-1";
		else if (Util.convertToInt(month) == 0)
			return year + "-1-1";

		// 如果月份大于12 比如 200699 就认为是 2006年9月9日
		if (Util.convertToInt(month) > 12) {
			return year + "-" + month.charAt(0) + "-" + month.charAt(1);
		}
		rtnStr = year + "-" + month;
		// 最后要处理日期了
		if (dt.length() > i) {
			dt = dt.substring(i);// 得到比如 "-1" "月1日"
		} else {
			return rtnStr + "-1";
		}

		// 然后得到日期的可能字符串
		if (dt.charAt(0) > '9' || dt.charAt(0) < '0') {
			for (i = 0; i < dt.length(); i++) {
				if (dt.charAt(i) > '9' || dt.charAt(i) < '0')
					continue;
				break;
			}
			if (dt.length() > i) {
				dt = dt.substring(i);// 得到比如 "-1"
			} else {
				return rtnStr + "-1";
			}
		}

		// 解析日期
		for (i = 0; i < dt.length() && i < 2; i++) {
			if (dt.charAt(i) > '9' || dt.charAt(i) < '0')
				break;
			day += dt.charAt(i);
		}

		if (day.equals(""))
			return rtnStr + "-1";

		rtnStr = year + "-" + month + "-" + day;

		// 注意，只是将数据有效格式化，返回值可能包含非法日期比如 ： 2006-3-89

		java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
		gc.set(Util.convertToInt(year), Util.convertToInt(month) - 1, Util
				.convertToInt(day));
		year = null;
		month = null;
		day = null;
		rtnStr = null;
		dt = null;
		return "" + gc.get(java.util.Calendar.YEAR) + "-"
				+ (gc.get(java.util.Calendar.MONTH) + 1) + "-"
				+ gc.get(java.util.Calendar.DAY_OF_MONTH);

	}

	/**
	 * 得到web应用名称。比如对于部署的名称如果是aaa，访问的时候是 http://192.168.0.1:7778/aaa/index.html
	 * 则得到 /aaa (注意已经带了斜线)
	 * 
	 * @param request
	 *            传入的request对象
	 * @return String web名称，如果得不到，将返回空的字符串，因此，在页面可以放心的写如下的相对路径：
	 *         <%=Util.getWebApplicationName(request)%>/info/info_list.jsp
	 *         因request.getContextPath();有可能得不到值
	 */
	public static String getWebApplicationName(HttpServletRequest request) {
		String rtnStr = "";
		try {
			rtnStr = request.getContextPath();
		} catch (Exception e) {
		}
		request = null;
		return rtnStr;
	}

	/**
	 * 格式化文件大小
	 * 
	 * @param fileSize
	 * @return
	 */
	public static String formatFileSize(String fileSize) {
		double size = Util.convertToDouble(fileSize);
		if (size < 1024)
			return "" + size + "B";
		else if (size < 1024000)
			return "" + Util.convertToDouble(size / 1024, 2) + "K";
		else if (size < 1024000000)
			return "" + Util.convertToDouble(size / 1024000, 2) + "M";
		else
			return "" + Util.convertToDouble(size / 1024000000, 2) + "G";
	}

	// 从带HTML标签的字符串content中截取len长度字符串
	// 注意：截取之后，将去除html标签
	public static String getPreviewContent(String content, int MAX_COUNT) {
		if (content == null || content.equals(""))
			return "";
		Pattern p = Pattern.compile("<[^<]+>"); // /<[^<]+>/gi
		String[] fields = p.split(content);
		String rtnStr = "";
		for (int i = 0; i < fields.length; i++)
			rtnStr += fields[i];
		p = null;
		fields = null;
		content = null;
		if (rtnStr == null)
			return "";
		return rtnStr;
	}

	/**
	 * 将字符串数组转换成字符串
	 * 
	 */
	public static String convertArray2String(String[] _strArray) {
		if (_strArray != null) {
			String retStr = "";
			for (int i = 0; i < _strArray.length; i++) {
				if (i < (_strArray.length - 1))
					retStr += _strArray[i].toString() + "|_|";
				else
					retStr += _strArray[i].toString();
			}
			_strArray = null;
			return retStr;
		} else {
			_strArray = null;
			return "";
		}
	}

	/**
	 * 将_src字符串的最后一个非零字母置成零，然后返回新字符串 example:setZero("010203000")="010200000"
	 */
	public static String setZero(String _src) {
		String tempStr = "";
		for (int i = 0; i < _src.length(); i++)
			tempStr += "0";
		String retStr = "";
		String tempChar = "";
		for (int i = _src.length(); i > 2; i--) {
			tempChar = _src.substring(i - 1, i);
			if (!tempChar.equals("0")) {
				retStr = _src.substring(0, i - 1)
						+ tempStr.substring(i - 1, _src.length());
			}
		}
		_src = null;
		tempChar = null;
		return retStr;
	}

	/**
	 * do URL encoding
	 * 字符串加密
	 * @param str
	 *            转化的源路径
	 * @return 转化后的路径
	 */
	public static String urlEncode(String str) {
		if (str == null || str.length() == 0)
			return str;

		StringBuffer sb = new StringBuffer();
		int length = str.length();

		for (int i = 0; i < length; i++) {
			char ch = str.charAt(i);

			if (ch == ' ')
				sb.append('+');
			else if (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z'
					|| ch >= 'A' && ch <= 'Z' || ch == '_')
				sb.append(ch);
			else {
				if (ch < 0x10)
					sb.append("%0" + Integer.toHexString(ch & 0xFF));
				else
					sb.append('%' + Integer.toHexString(ch & 0xFF));
			}
		}

		str = null;
		return sb.toString();
	}


	
	/**
	 * 判断是否是内网IP isInnerIP(访问者ip，开始ip，结束ip) 如：10.0.0.0-10.255.255.255
	 * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
	 * 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
	 * @param ipAddress IP地址
	 * @param ipBeginAddress    开始地址
	 * @param ipEndAddress     结束地址
	 * @return true/false
	 */
	public static boolean isInnerIP(String ipAddress, String ipBeginAddress,
			String ipEndAddress) {
		boolean isInnerIp = false;
		long ipNum = getIpNum(ipAddress);
		/**
		 * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
		 * 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
		 */
		isInnerIp = isInner(ipNum, getIpNum(ipBeginAddress),
				getIpNum(ipEndAddress));
		ipAddress = null;
		ipBeginAddress = null;
		ipEndAddress = null;
		return isInnerIp;
	}

	/**
	 * ava 代码获取IP数
	 * @param ipAddress  IP地址
	 * @return 得到IP数
	 */
	private static long getIpNum(String ipAddress) {
		String[] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);
		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		ipAddress = null;
		ip = null;
		return ipNum;
	}
	/**
	 * 根据IP数来判断是否为内网
	 * @param userIp          用户IP数
	 * @param begin         开始IP数
	 * @param end         结束IP数
	 * @return true/false
	 */
	private static boolean isInner(long userIp, long begin, long end) {
		return (userIp >= begin) && (userIp <= end);
	}

	

	/**
	 * 截取字符串
	 * @param str 待截取字符串
	 * @param len 长度
	 * @param str2 替换内容
	 * @return 如果str长度小于len返回原值,其余要待截长度到len位,并添加str2内容
	 * 将str字符截取len长度后再加上str2字符串
	 */
	public static String getStr(String str, int len, String str2) {
		try {
			if (str.length() > len)
				return str.substring(0, len) + str2;
			else {
				str2 = null;
				return str;
			}
		} catch (Exception ex) {
			str2 = null;
			return str;
		}
	}

	/**
	 * 截取字符串
	 * @param str
	 * @param begin        开始位置
	 * @param end          结束位置
	 * @return 截取字符串
	 */
	public static String getSubString(String str, int begin, int end) {
		try {
			if (str.length() > end)
				return str.substring(begin, end);
			else
				return str.substring(begin, str.length());
		} catch (Exception ex) {
			return str;
		}
	}
	/**
	 * "Asia/Shanghai"可以得到上海地区的时间,输入Asia/meiguo 可以得到美国的时间
	 * @param str        地址参数
	 * @return 地区时间
	 */
	public static String getZoneDate(String str) {
		try {
			TimeZone timeZone = TimeZone.getTimeZone(str);
			String pattern = "yyyy-MM-dd HH:mm:ss";
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					pattern);
			df.setTimeZone(timeZone);
			Date today = new Date();
			pattern = null;
			timeZone = null;
			str = null;
			return Util.dealNull(df.format(today));
		} catch (Exception ex) {
			str = null;
			return "";
		}
	}
	/**
	 * HTML转换
	 * @param str 特转换的字符串
	 * @return 返回转换后的字符串
	 */
	public static String inHTML(String str) {
		String sTemp;
		sTemp = str;
		sTemp = sTemp.replaceAll("&", "&amp;");
		sTemp = sTemp.replaceAll("<", "&lt;");
		sTemp = sTemp.replaceAll(">", "&gt;");
		sTemp = sTemp.replaceAll("\"", "&quot;");
		str = null;
		return sTemp;
	}
	/**				
     * 去掉字符串左右两端的全半角空格		
     *					
     * @param str 			需要去空格的字符串			
     * @return 
     * 					
     */	
    public static String trimStr(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        else {
            return str.replaceAll("^[　 ]+|[　 ]+$", "");
        }
    }
    /**
     * 把字符格式化成金額格式
     * 
     * @param num 			需要格式化的数字
     * @return				格式化以后的字符串	
     */
    public static String toMoneyFormat(int num) {
        String str = Integer.toString(num);
        if (str == null || str.length() == 0) {
            return str;
        }
        else {
            NumberFormat f = NumberFormat.getCurrencyInstance();
            str = f.format(num);
            return str;
        }
    }

    /**
     * 把字符格式化成金額格式
     * 
     * @param num 			需要格式化的数字
     * @return				格式化以后的字符串
     */
    public static String toMoneyFormat(long num) {
        String str = String.valueOf(num);
        if (str == null || str.length() == 0) {
            return str;
        }
        else {
            //NumberFormat f = NumberFormat.getCurrencyInstance(); // 有金钱符号
            NumberFormat f = NumberFormat.getInstance(Locale.JAPAN);
            str = f.format(num);
            return str;
        }
    }

    /**
     * 把字符格式化成金額格式
     * 
     * @param num 			需要格式化的数字
     * @return				格式化以后的字符串	
     */
    public static String toMoneyFormatFloat(float num) {
        String str = Float.toString(num);
        if (str == null || str.length() == 0) {
            return str;
        }
        else {
            NumberFormat f = NumberFormat.getCurrencyInstance();
            str = f.format(num);
            return str;
        }
    }

    /**
     * 把字符格式化成金額格式
     * 
     * @param num 			需要格式化的数字
     * @return				格式化以后的字符串	
     */
    public static String toMoneyFormatDouble(Double num) {
        String str = String.valueOf(num);
        if (str == null || str.length() == 0) {
            return str;
        }
        else {
            NumberFormat f = NumberFormat.getInstance(Locale.JAPAN);
            str = f.format(num);
            return str;
        }
    }
 // 短整形转换为字节数组
	public static byte[] shortToByteArray(short s) {
		byte[] shortBuf = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (shortBuf.length - 1 - i) * 8;
			shortBuf[i] = (byte) ((s >>> offset) & 0xff);
		}
		return shortBuf;
	}

	// 字节数组转换为16进制字符串
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	public static char Byte2ToChar(byte[] paramArrayOfByte, int startIndex) {
		int result = ((0xFF & paramArrayOfByte[startIndex]) << 8)
				+ paramArrayOfByte[startIndex + 1];
		return (char) result;
	}

	// Char类型转换为字节数组
	public static byte[] CharToBytes(char paramChar) {
		byte[] paramArrayOfByte = new byte[2];
		paramArrayOfByte[1] = (byte) (0xFF & paramChar);
		paramArrayOfByte[0] = (byte) ((0xFF00 & paramChar) >> 8);
		return paramArrayOfByte;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 字节数组转整形
	 */
	public static int bytesToInt(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}

	/**
	 * int转换为byte
	 */
	public static byte[] intToByteArray(int value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}

	/**
	 * 注释：字节数组到short的转换！
	 * 
	 * @param b
	 * @return
	 */
	public static short byteToShort(byte[] b) {
		short s = 0;
		short s0 = (short) (b[0] & 0xff);// 最低位
		short s1 = (short) (b[1] & 0xff);
		s0 <<= 8;
		s = (short) (s0 | s1);
		return s;
	}

	/**
	 * long ---> byte
	 */
	public static byte[] longToByte(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/**
	 * byte数组转成long
	 */
	public static long byteToLong(byte[] b) {
		long s = 0;
		long s0 = b[0] & 0xff;// 最低位
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// 最低位
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;
		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	/**
	 * 注释：short到字节数组的转换！
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] shortToByte(short number) {
		byte[] result = new byte[2];
		result[0] = (byte) (number>>8);
		result[1] = (byte) number;
		return result;
	}

	/**
	 * 注释：int到字节数组的转换！
	 * 
	 * @param number
	 * @return
	 */
	public static byte[] intToByte(int number) {
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	/**
	 * 注释：字节数组到int的转换！
	 * 
	 * @param b
	 * @return
	 */
	public static int byteToInt(byte[] b) {
		int s = 0;
		int s0 = b[0] & 0xff;// 最低位
		int s1 = b[1] & 0xff;
		int s2 = b[2] & 0xff;
		int s3 = b[3] & 0xff;
		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;
		s = s0 | s1 | s2 | s3;
		return s;
	}

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		//String returnValue = new String(baos.toByteArray());
		return new String(new String(baos.toByteArray()));
	}
	
	/**
	 * 两个日期差--毫秒
	 * @param d1 
	 * @param d2 
	 * @param s
	 * @return
	 */
	public static long datediff(String d1, String d2, String s){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(s);
			return sdf.parse(d2).getTime()-sdf.parse(d1).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取自动编号
	 * 
	 * @param int
	 *            随机数的位数
	 * @return String
	 */
	public static String getUUID(int f) {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddHHmmss");
		String id = tempDate.format(new java.util.Date()) + validateCode(f);
		return id;
	}

	/**
	 * 
	 * 获取随即数字
	 * 
	 * @param code_len
	 * @reutrn String
	 * 
	 */
	public static String validateCode(int code_len) {
		int count = 0;
		char str[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < code_len) {
			int i = Math.abs(r.nextInt(10));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
	
	/**
	 * 获取文件后缀名
	 * param：文件名称
	 * return：文件后缀
	 * */
	public static String getExtendName(String fileName){
		String returnValue = "";
		if(Util.notEmpty(fileName) && Util.isExistence(fileName, ".")){
			returnValue = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		}
		return returnValue;
	}
	
	/**
	 * 判断字符串中是否存在给定的字符
	 */
	public static boolean isExistence(String str, String beStr) {
		boolean returnValue = false;
		if (str.indexOf(beStr) != -1) {
			returnValue = true;
		}
		return returnValue;
	}
	
	public static boolean notNull(Object obj) {
		return obj != null;
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean notEmpty(String str) {
		return notNull(str) && !"".equals(str.trim());
	}

	public static boolean isEmpty(String str) {
		return "".equals(str);
	}
	
	/**  
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,  
     * @return String  
     */  
	public static String getRandColorCode(){   
	  String r,g,b;   
	  Random random = new Random();   
	  r = Integer.toHexString(random.nextInt(256)).toUpperCase();   
	  g = Integer.toHexString(random.nextInt(256)).toUpperCase();   
	  b = Integer.toHexString(random.nextInt(256)).toUpperCase();   
	     
	  r = r.length()==1 ? "0" + r : r ;   
	  g = g.length()==1 ? "0" + g : g ;   
	  b = b.length()==1 ? "0" + b : b ;   
	     
	  return r+g+b;   
	}
	
	public static int nextInt(int min, int max){
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp%(max-min+1)+min;
	}
	
	/**
	 * 获取本地的格林威治时间
	 * @param gmt 表示时区，如：北京时间gmt=GMT+8
	 * @param gmt的值与Local.CHINA相关，随Locale.CHINA变化而变化，如果是Locale.US，则gmt=GMT
	 * @return
	 */
	public static String getGMTTime(String gmt){
		Calendar cd = Calendar.getInstance();  
		SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.CHINA);  
		sdf.setTimeZone(TimeZone.getTimeZone(gmt)); //设置时区  
	    String str = sdf.format(cd.getTime());
		return str;
	}
	
	/**
     * 判断是否含有特殊字符
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    
	/**
	 * 密码含有大小写字母，数字，特殊字符，限制长度
	 * @param pwd
	 * @return
	 */
	public static boolean isNormalPwd(String pwd){
//		String re = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{8,20}$";
		//大写字母，小写字母，数字，特殊符号必须四选三
		String re = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,}$";
		return pwd.matches(re);
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(nextInt(10000, 60000));
		System.out.println(getGMTTime("GMT+8"));
		String string = "/9z/q2hpQz228esVn7pGNg==";
		System.out.println(string.substring(0,string.indexOf("==")));
		System.out.println(isSpecialChar("/9z/q2hpQz228esVn7pGNg"));
		
		System.out.println(datediff("2017-01-16 16:25:15", "2018-01-16 16:25:16", "yyyy-MM-dd HH:mm:ss")/(1000*3600*24));
	}
}
	