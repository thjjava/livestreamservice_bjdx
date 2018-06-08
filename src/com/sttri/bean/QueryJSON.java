package com.sttri.bean;

import java.util.List;
/**
 * 用于生成 JSON
 * */
@SuppressWarnings("unchecked")
public class QueryJSON {
	private long total;
	private List rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
