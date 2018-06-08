package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.dao.CommonDao;
import com.sttri.pojo.UserPolling;
import com.sttri.service.IUserPollingService;

@Service
public class UserPollingServiceImpl implements IUserPollingService {
	@Autowired
	private CommonDao dao;

	@Override
	public List<UserPolling> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		// TODO Auto-generated method stub
		return dao.getResultList(UserPolling.class, wherejpql, orderby, queryParams);
	}

	@Override
	public void save(UserPolling userPolling) {
		// TODO Auto-generated method stub
		dao.save(userPolling);
	}

	@Override
	public void update(UserPolling userPolling) {
		// TODO Auto-generated method stub
		dao.update(userPolling);
	}

	@Override
	public UserPolling getById(Object id) {
		// TODO Auto-generated method stub
		return dao.find(UserPolling.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		// TODO Auto-generated method stub
		dao.delete(UserPolling.class, array);
	}

	@Override
	public void deletebyid(Object id) {
		// TODO Auto-generated method stub
		dao.delete(UserPolling.class, id);
	}
	
}
