package com.sttri.bean;

import java.util.List;

/**
 * 分页参数
 * @author wdq
 * */
@SuppressWarnings("unchecked")
public class PageBean {
	private int AllSize;  //总条数
	private int pageNo ;  //开始条数  下标
	private int pageSize ;    //页面显示条数
	private boolean k;    //用于区分统计总条数  还是查询   true 查询   false 统计总条数
	public int getAllSize() {
		return AllSize;
	}
	public void setAllSize(int allSize) {
		AllSize = allSize;
	}
	
	public boolean isK() {
		return k;
	}
	public void setK(boolean k) {
		this.k = k;
	}
	/**
	 * 计算总页数
	 * */
	public static int retunAllSize(List list){
		int AllSize=0;
		if(list !=null){
			AllSize=list.size();
		}
		return AllSize;
	}
	/**
	 * 判断是否为NULL 
	 * @return 
	 * */
	public static String dealNull(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}
	
	public static String dealNulls(Object obj) {
		if (obj == null)
			return "0";
		return obj.toString();
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

	/**
	 * 获得当前分页开始的记录数
	 * @author dubaohui
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页面大小
	 * @return 当前分页开始记录数
	 * 
	 */
	public static int getFirstSize(int pageNo, int pageSize) {
		int firstSize = 0;
		firstSize = (pageNo - 1) * pageSize;
		return firstSize;
	}
}
