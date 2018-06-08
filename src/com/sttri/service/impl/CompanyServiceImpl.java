package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.Company;
import com.sttri.service.ICompanyService;

@Service
public class CompanyServiceImpl implements ICompanyService {
	@Autowired
	private CommonDao dao;
	@Override
	public void deletebyid(Object id) {
		dao.delete(Company.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(Company.class, array);
	}

	@Override
	public Company getById(Object id) {
		return dao.find(Company.class, id);
	}

	@Override
	public List<Company> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(Company.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<Company> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(Company.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(Company company) {
		dao.save(company);
	}

	@Override
	public void update(Company company) {
		dao.update(company);
	}

}
