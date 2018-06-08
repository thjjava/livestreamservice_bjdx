package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.DevLog;
import com.sttri.service.IDevLogService;

@Service
public class DevLogServiceImpl implements IDevLogService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(DevLog.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(DevLog.class, array);
	}

	@Override
	public DevLog getById(Object id) {
		return dao.find(DevLog.class, id);
	}

	@Override
	public List<DevLog> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(DevLog.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<DevLog> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(DevLog.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(DevLog devLog) {
		dao.save(devLog);
	}

	@Override
	public void update(DevLog devLog) {
		dao.update(devLog);
	}


}
