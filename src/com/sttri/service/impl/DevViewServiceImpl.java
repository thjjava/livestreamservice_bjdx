package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.DevView;
import com.sttri.service.IDevViewService;

@Service
public class DevViewServiceImpl implements IDevViewService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(DevView.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(DevView.class, array);
	}

	@Override
	public DevView getById(Object id) {
		return dao.find(DevView.class, id);
	}

	@Override
	public List<DevView> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(DevView.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<DevView> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(DevView.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(DevView devView) {
		dao.save(devView);
	}

	@Override
	public void update(DevView devView) {
		dao.update(devView);
	}


}
