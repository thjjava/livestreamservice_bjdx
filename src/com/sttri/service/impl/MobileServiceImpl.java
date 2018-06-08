package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblMobile;
import com.sttri.service.IMobileService;

@Service
public class MobileServiceImpl implements IMobileService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(TblMobile.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblMobile.class, array);
	}

	@Override
	public TblMobile getById(Object id) {
		return dao.find(TblMobile.class, id);
	}

	@Override
	public List<TblMobile> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblMobile.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblMobile> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblMobile.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblMobile mobile) {
		dao.save(mobile);
	}

	@Override
	public void update(TblMobile mobile) {
		dao.update(mobile);
	}


}
