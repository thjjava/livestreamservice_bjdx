package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.UserLog;
import com.sttri.service.IUserLogService;

@Service
public class UserLogServiceImpl implements IUserLogService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(UserLog.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(UserLog.class, array);
	}

	@Override
	public UserLog getById(Object id) {
		return dao.find(UserLog.class, id);
	}

	@Override
	public List<UserLog> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(UserLog.class, wherejpql, orderby, queryParams);
	}
	
	@Override
	public QueryResult<UserLog> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(UserLog.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(UserLog userLog) {
		dao.save(userLog);
	}

	@Override
	public void update(UserLog userLog) {
		dao.update(userLog);
	}


}
