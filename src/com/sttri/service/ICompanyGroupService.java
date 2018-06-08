package com.sttri.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.sttri.pojo.CompanyGroup;

public interface ICompanyGroupService {
	/**
     * 根据条件来进行查询
     * @param wherejpql
     * @param orderby
     * @param queryParams
     * @return
     */
	public List<CompanyGroup> getResultList(String wherejpql,LinkedHashMap<String, String> orderby, Object... queryParams);
	
}
