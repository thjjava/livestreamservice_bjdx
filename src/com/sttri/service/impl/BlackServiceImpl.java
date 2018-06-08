package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblBlack;
import com.sttri.service.IBlackService;

@Service
public class BlackServiceImpl implements IBlackService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(TblBlack.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblBlack.class, array);
	}

	@Override
	public TblBlack getById(Object id) {
		return dao.find(TblBlack.class, id);
	}

	@Override
	public List<TblBlack> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblBlack.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblBlack> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblBlack.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblBlack tblBlack) {
		dao.save(tblBlack);
	}

	@Override
	public void update(TblBlack tblBlack) {
		dao.update(tblBlack);
	}


}
