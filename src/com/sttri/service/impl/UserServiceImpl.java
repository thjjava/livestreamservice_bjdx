package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblDev;
import com.sttri.pojo.TblUser;
import com.sttri.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private CommonDao dao;
	@Override
	public void deletebyid(Object id) {
		dao.delete(TblDev.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblDev.class, array);
	}

	@Override
	public TblUser getById(Object id) {
		return dao.find(TblUser.class, id);
	}

	@Override
	public List<TblUser> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblUser.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblUser> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblUser.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblUser user) {
		dao.save(user);
	}

	@Override
	public void update(TblUser user) {
		dao.update(user);
	}

}
