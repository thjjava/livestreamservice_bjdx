package com.sttri.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.TblControl;
import com.sttri.pojo.TblDev;
import com.sttri.service.IControlService;
import com.sttri.util.Util;

@Service
public class ControlServiceImpl implements IControlService {
	@Autowired
	private CommonDao dao;
	@Override
	public void deletebyid(Object id) {
		dao.delete(TblDev.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(TblDev.class, array);
	}

	@Override
	public TblControl getById(Object id) {
		return dao.find(TblControl.class, id);
	}

	@Override
	public List<TblControl> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(TblControl.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<TblControl> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(TblControl.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(TblControl control) {
		dao.save(control);
	}

	@Override
	public void update(TblControl control) {
		dao.update(control);
	}

	@Override
	public TblControl checkVer(int sourceType, int conType, String conVer) {
		TblControl control = null;
		String where = "o.sourceType=? and o.conType=? ";
		List<Object> param = new ArrayList<Object>();
		param.add(sourceType);
		param.add(conType);
		if(Util.notEmpty(conVer)){
			where += "and o.conVer>?";
			param.add(conVer);
		}
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("conVer", "desc");
		List<TblControl> clist = getResultList(where, orderby, param.toArray());
		if(clist!=null && clist.size()>0){
			control = clist.get(0);
		}
		return control;
	}

}
