package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.dao.CommonDao;
import com.sttri.pojo.CompanyGroup;
import com.sttri.service.ICompanyGroupService;

@Service
public class CompanyGroupServiceImpl implements ICompanyGroupService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public List<CompanyGroup> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(CompanyGroup.class, wherejpql, orderby, queryParams);
	}

}
