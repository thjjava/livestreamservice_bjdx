package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblRole;
import com.sttri.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private CommonDao dao;

	@Override
	public void deletebyid(Object id) {
		dao.delete(TblRole.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblRole.class, array);
	}

	@Override
	public TblRole getById(Object id) {
		return dao.find(TblRole.class, id);
	}

	@Override
	public List<TblRole> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblRole.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblRole> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblRole.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblRole role) {
		dao.save(role);
	}

	@Override
	public void update(TblRole role) {
		dao.update(role);
	}

}
