package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.UserRole;
import com.sttri.service.IUserRoleService;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(UserRole.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(UserRole.class, array);
	}

	@Override
	public UserRole getById(Object id) {
		return dao.find(UserRole.class, id);
	}

	@Override
	public List<UserRole> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(UserRole.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<UserRole> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(UserRole.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(UserRole userRole) {
		dao.save(userRole);
	}

	@Override
	public void update(UserRole userRole) {
		dao.update(userRole);
	}

}
