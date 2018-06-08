package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.DevGood;
import com.sttri.service.IDevGoodService;

@Service
public class DevGoodServiceImpl implements IDevGoodService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(DevGood.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(DevGood.class, array);
	}

	@Override
	public DevGood getById(Object id) {
		return dao.find(DevGood.class, id);
	}

	@Override
	public List<DevGood> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(DevGood.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<DevGood> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(DevGood.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(DevGood devGood) {
		dao.save(devGood);
	}

	@Override
	public void update(DevGood devGood) {
		dao.update(devGood);
	}


}
