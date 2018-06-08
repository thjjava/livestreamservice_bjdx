package com.sttri.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sttri.bean.QueryResult;
import com.sttri.dao.CommonDao;
import com.sttri.pojo.DevRecord;
import com.sttri.service.IDevRecordService;

@Service
public class DevRecordServiceImpl implements IDevRecordService {
	@Autowired
	private CommonDao dao;
	
	@Override
	public void deletebyid(Object id) {
		dao.delete(DevRecord.class, id);
	}

	@Override
	public void deletebyids(Object[] array) {
		dao.delete(DevRecord.class, array);
	}

	@Override
	public DevRecord getById(Object id) {
		return dao.find(DevRecord.class, id);
	}

	@Override
	public List<DevRecord> getResultList(String wherejpql,
			LinkedHashMap<String, String> orderby, Object... queryParams) {
		return dao.getResultList(DevRecord.class, wherejpql, orderby, queryParams);
	}

	@Override
	public QueryResult<DevRecord> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return dao.getScrollData(DevRecord.class, firstindex, maxresult, wherejpql, queryParams, orderby);
	}

	@Override
	public void save(DevRecord devRecord) {
		dao.save(devRecord);
	}

	@Override
	public void update(DevRecord devRecord) {
		dao.update(devRecord);
	}

}
