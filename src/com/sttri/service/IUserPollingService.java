package com.sttri.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.sttri.pojo.UserPolling;

public interface IUserPollingService {
	/**
     * 根据条件来进行查询
     * @param wherejpql
     * @param orderby
     * @param queryParams
     * @return
     */
	public List<UserPolling> getResultList(String wherejpql,LinkedHashMap<String, String> orderby, Object... queryParams);
	
	public void save(UserPolling userPolling);

	public void update(UserPolling userPolling);
	
	public UserPolling getById(Object id);

	public void deletebyids(Object[] array);

	public void deletebyid(Object id);
}
