package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblMenus;
import com.sttri.service.IMenusService;

@Service
public class MenusServiceImpl implements IMenusService {
	@Autowired
	private CommonDao dao;

	@Override
	public void deletebyid(Object id) {
		dao.delete(TblMenus.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblMenus.class, array);
	}

	@Override
	public TblMenus getById(Object id) {
		return dao.find(TblMenus.class, id);
	}

	@Override
	public List<TblMenus> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblMenus.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblMenus> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblMenus.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblMenus menus) {
		dao.save(menus);
	}

	@Override
	public void update(TblMenus menus) {
		dao.update(menus);
	}

}
