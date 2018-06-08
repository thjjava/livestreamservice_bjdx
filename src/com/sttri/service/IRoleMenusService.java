package com.sttri.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.sttri.bean.QueryResult;
import com.sttri.pojo.RoleMenus;

public interface IRoleMenusService {
	/**
     * 根据条件来进行查询
     * @param wherejpql
     * @param orderby
     * @param queryParams
     * @return
     */
	public List<RoleMenus> getResultList(String wherejpql,LinkedHashMap<String, String> orderby, Object... queryParams);
	
	/**
	 * 分页信息查询
	 * */
	public QueryResult<RoleMenus> getScrollData(int firstindex,int maxresult, String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby);

	public void save(RoleMenus roleMenus);

	public void update(RoleMenus roleMenus);
	
	public RoleMenus getById(Object id);

	public void deletebyids(Object[] array);

	public void deletebyid(Object id);
}
