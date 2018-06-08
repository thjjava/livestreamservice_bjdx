package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.UserGroup;
import com.sttri.service.IUserGroupService;

@Service
public class UserGroupServiceImpl implements IUserGroupService {
	@Autowired
	private CommonDao dao;
	@Override
	public void deletebyid(Object id) {
		dao.delete(UserGroup.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(UserGroup.class, array);
	}

	@Override
	public UserGroup getById(Object id) {
		return dao.find(UserGroup.class, id);
	}

	@Override
	public List<UserGroup> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(UserGroup.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<UserGroup> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(UserGroup.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(UserGroup userGroup) {
		dao.save(userGroup);
	}

	@Override
	public void update(UserGroup userGroup) {
		dao.update(userGroup);
	}

}
