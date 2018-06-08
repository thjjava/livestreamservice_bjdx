package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.DevComment;
import com.sttri.service.IDevCommentService;

@Service
public class DevCommentServiceImpl implements IDevCommentService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(DevComment.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(DevComment.class, array);
	}

	@Override
	public DevComment getById(Object id) {
		return dao.find(DevComment.class, id);
	}

	@Override
	public List<DevComment> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(DevComment.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<DevComment> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(DevComment.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(DevComment devComment) {
		dao.save(devComment);
	}

	@Override
	public void update(DevComment devComment) {
		dao.update(devComment);
	}


}
