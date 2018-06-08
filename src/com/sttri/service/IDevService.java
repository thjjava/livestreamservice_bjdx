package com.sttri.service;

import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.sttri.bean.QueryResult;
import com.sttri.pojo.TblDev;

public interface IDevService {
	/**
     * 根据条件来进行查询
     * @param wherejpql
     * @param orderby
     * @param queryParams
     * @return
     */
	public List<TblDev> getResultList(String wherejpql,LinkedHashMap<String, String> orderby, Object... queryParams);
	
	/**
	 * 分页信息查询
	 * */
	public QueryResult<TblDev> getScrollData(int firstindex,int maxresult, String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby);

	public void save(TblDev dev);

	public void update(TblDev dev);
	
	public TblDev getById(Object id);

	public void deletebyids(Object[] array);

	public void deletebyid(Object id);
	
	public JSONObject videoStart(TblDev dev,String protocol);
	
	public boolean videoEnd(TblDev dev,String recordTaskNo);
}
