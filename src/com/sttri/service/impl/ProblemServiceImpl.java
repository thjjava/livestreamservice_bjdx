package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblProblem;
import com.sttri.service.IProblemService;

@Service
public class ProblemServiceImpl implements IProblemService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(TblProblem.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblProblem.class, array);
	}

	@Override
	public TblProblem getById(Object id) {
		return dao.find(TblProblem.class, id);
	}

	@Override
	public List<TblProblem> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblProblem.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblProblem> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblProblem.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblProblem problem) {
		dao.save(problem);
	}

	@Override
	public void update(TblProblem problem) {
		dao.update(problem);
	}


}
