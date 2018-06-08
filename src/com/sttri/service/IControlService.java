package com.sttri.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.sttri.bean.QueryResult;
import com.sttri.pojo.TblControl;

public interface IControlService {
	/**
     * 根据条件来进行查询
     * @param wherejpql
     * @param orderby
     * @param queryParams
     * @return
     */
	public List<TblControl> getResultList(String wherejpql,LinkedHashMap<String, String> orderby, Object... queryParams);
	
	/**
	 * 分页信息查询
	 * */
	public QueryResult<TblControl> getScrollData(int firstindex,int maxresult, String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby);

	public void save(TblControl control);

	public void update(TblControl control);
	
	public TblControl getById(Object id);

	public void deletebyids(Object[] array);

	public void deletebyid(Object id);
	
	public TblControl checkVer(int sourceType, int conType, String conVer);
}
