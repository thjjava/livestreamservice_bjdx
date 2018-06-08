package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.RoleMenus;
import com.sttri.service.IRoleMenusService;

@Service
public class RoleMenusServiceImpl implements IRoleMenusService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(RoleMenus.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(RoleMenus.class, array);
	}

	@Override
	public RoleMenus getById(Object id) {
		return dao.find(RoleMenus.class, id);
	}

	@Override
	public List<RoleMenus> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(RoleMenus.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<RoleMenus> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(RoleMenus.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(RoleMenus roleMenus) {
		dao.save(roleMenus);
	}

	@Override
	public void update(RoleMenus roleMenus) {
		dao.update(roleMenus);
	}

}
