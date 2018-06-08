package com.sttri.bean;

/**
 * 分页工具类
 * */

import java.util.List;

public class PageView<T> {
	/** 分页数据 **/
	private List<T> records;
	/** 页码开始索引和结束索引 **/
	private PageIndex pageIndex;
	/** 总页数 **/
	private long totalPage = 1;
	/** 每页显示记录数 **/
	private int maxResult = 15;
	/** 当前页 **/
	private int currentPage = 1;
	/** 总记录数 **/
	private long totalRecord;
	/** 页码数量 **/
	private int pageCode = 10;
	
	/**
	 * 获取页码数量
	 * */
	public int getPageCode() {
		return pageCode;
	}
	/**
	 * 设置页码数量
	 * */
	public void setPagecode(int pageCode) {
		this.pageCode = pageCode;
	}
	/**
	 * 初始化每页显示记录数和当前页
	 * */
	public PageView(int maxResult, int currentPage) {
		this.maxResult = maxResult;
		this.currentPage = currentPage;
	}
	
	public void setQueryResult(QueryResult<T> qr){
		setTotalRecord(qr.getTotalRecord());
		setRecords(qr.getResultList());
	}
	
	public long getTotalRecord() {
		return totalRecord;
	}
	
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord%this.maxResult==0? this.totalRecord/this.maxResult : this.totalRecord/this.maxResult+1);
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public PageIndex getPageIndex() {
		return pageIndex;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
		this.pageIndex = WebTool.getPageIndex(pageCode, currentPage, totalPage);
	}
	public int getMaxResult() {
		return maxResult;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int getFirstSize(int pageNo, int pageSize) {
		int firstSize = 0;
		firstSize = (pageNo - 1) * pageSize;
		return firstSize;
	}
}
