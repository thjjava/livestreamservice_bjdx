package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.DevRecordFile;
import com.sttri.service.IDevRecordFileService;

@Service
public class DevRecordFileServiceImpl implements IDevRecordFileService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(DevRecordFile.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(DevRecordFile.class, array);
	}

	@Override
	public DevRecordFile getById(Object id) {
		return dao.find(DevRecordFile.class, id);
	}

	@Override
	public List<DevRecordFile> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(DevRecordFile.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<DevRecordFile> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(DevRecordFile.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(DevRecordFile devRecord) {
		dao.save(devRecord);
	}

	@Override
	public void update(DevRecordFile devRecord) {
		dao.update(devRecord);
	}

}
