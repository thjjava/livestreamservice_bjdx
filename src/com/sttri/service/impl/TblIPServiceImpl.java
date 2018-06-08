package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblIP;
import com.sttri.service.ITblIPService;

@Service
public class TblIPServiceImpl implements ITblIPService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(TblIP.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblIP.class, array);
	}

	@Override
	public TblIP getById(Object id) {
		return dao.find(TblIP.class, id);
	}

	@Override
	public List<TblIP> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblIP.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblIP> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblIP.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblIP tblIP) {
		dao.save(tblIP);
	}

	@Override
	public void update(TblIP tblIP) {
		dao.update(tblIP);
	}


}
